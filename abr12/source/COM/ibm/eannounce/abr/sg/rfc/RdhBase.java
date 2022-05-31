/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.OutputData;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBase_zdm_geo_to_class;
import COM.ibm.eannounce.abr.util.EACMWebServiceUtil;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;


/**
 * The RdhBase java class is the superclass for all of the RDH RFC
 * callers/proxies. This java class is abstract.
 * 
 * @author will
 * 
 */
public abstract class RdhBase
{

    @SerializedName("PIMS_IDENTITY")
    protected String pims_identity;
    @SerializedName("RFA_NUM")
    protected String rfa_num;
    @SerializedName("DEFAULT_MANDT")
    protected String default_mandt;
    @SerializedName("CLIENT_NAME")
    private String client_name;
    @Foo
    private String rfc_name;
    @Foo
    protected RdhBase_zdm_geo_to_class zdm_geo_to_class;

    @Foo
    private String input = null;
    // magic field for RFC input
    @SerializedName("N45BZDM_GEO_TO_CLASS")
    private List<RdhBase_zdm_geo_to_class> geo_to_class_list;

    @Foo
    private int rfcrc;
    @Foo
    private String error_text;
    
    private HashMap<String, List<HashMap<String, String>>> RETURN_MULTIPLE_OBJ;

    /**
     * Constructor
     * 
     * @param event The identifier to be used to uniquely tag a set of parking
     * table entries associated with a product feed.
     * @param rfc_name The name of the RDH remote function. For example:
     * "Z_DM_SAP_MATM_CREATE".
     * @param enablementprocess it was used to set pims_identify and geo based on enablementprocess. 
     * if it is null, will set to pims_identify and geo as ESW
     * UDT will set it to null since it is only for ESW
     */
    public RdhBase(String event, String rfc_name, String enablementprocess)
    {
        this.pims_identity = "T";// Set to "E" as default ESW.
        this.rfa_num = event;
        this.rfc_name = rfc_name;
        this.default_mandt = RfcConfigProperties.getPropertys("rdh.service.default.Mandt");
        this.client_name = RfcConfigProperties.getPropertys("rdh.service.client.name");
        this.zdm_geo_to_class = new RdhBase_zdm_geo_to_class("WW");
        this.geo_to_class_list = new ArrayList<RdhBase_zdm_geo_to_class>();
        geo_to_class_list.add(zdm_geo_to_class);
        setDefaultValues();
    }

    /**
     * This method sets attributes to default values. This method is usually
     * overridden by the concrete classes.
     */
    protected void setDefaultValues()
    {
        // Do nothing in this class
    }

    /**
     * Verifies that all required attributes of the RDH RFC caller have been set
     * to a value before the execute() operation attempts to invoke the
     * corresponding RDH RFC web service. This method is abstract. It must be
     * defined on each RDH RFC caller class.
     * 
     * @return false if not all required attributes have been set
     */
    protected abstract boolean isReadyToExecute();

    /**
     * If isReadyToExecute() == TRUE, then: Call the RdhBase.generateJson()
     * method to generate the JSON input. Call the RDH RFC web service which
     * corresponds to the RDH RFC caller class. Call the
     * RdhBase.saveRfcResults() method to store the RFC result values. If
     * isReadyToExecute() == FALSE, then: Throw an java exception to indicate
     * all required parameters have not been set. The RDH RFC web service is not
     * called. Set the <rfcrc> attribute to "8" (error). Set the <error_text> to
     * "All required parameters have not been set.  The RDH RFC web service is not called."
     * Indicate which parameter names are missing values in the <error_text>.
     * 
     * @throws Exception
     */
    public void execute() throws Exception
    {
        if (isReadyToExecute())
        {  
            try
            {
                input = generateJson();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.disableHtmlEscaping().create();
                OutputData output = gson
                        .fromJson(EACMWebServiceUtil.callService(input, rfc_name), OutputData.class);
                saveRfcResults(output);
                if(!isValidRfcrc()){//some caller think 4 is valid and not need to throw it
                    throw new Exception(error_text);
                }
            } catch (Exception e)
            {
                rfcrc = 8;
                error_text = "Something wrong when calling webservice: " + rfc_name + " - " + e.getMessage();
                throw new Exception(createLogEntry());
            }
        } else
        {
            // Should be done in isReadyToExecute() by child class
            // rfcrc = 8;
            // error_text =
            // "All required parameters have not been set.  The RDH RFC web service is not called.";
            // Indicate which parameter names are missing values in the <error_text>
            throw new Exception(createLogEntry());
        }

    }

    /**
     * Generates the JSON structure/data to be used as input by the RDH RFC web
     * service.
     * 
     * @return
     * @throws Exception 
     */
    public String generateJson() throws Exception
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setExclusionStrategies(new RdhExclusionStrategy()).registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).disableHtmlEscaping().create();

        return gson.toJson(this);
    }

    /**
     * Saves the results returned by the executed RDH RFC web service.
     * 
     * @param output
     */
    protected void saveRfcResults(OutputData output)
    {
        this.rfcrc = output.getRFCRC();
        this.error_text = output.getERROR_TEXT();
        this.RETURN_MULTIPLE_OBJ = output.getRETURN_MULTIPLE_OBJ();
    }

    /**
     * Sets one or more attributes to indicate a delete action should be
     * performed. Since the setDeleteAction() operation only applies to some of
     * the RDH RFC callers, this method should be overloaded by those RDH RFC
     * callers which support a delete operation.
     */
    public void setDeleteAction()
    {

    }

    /**
     * This method should return a character string so it can be written to a
     * log where the user can be view the results of their submitted data feed.
     * The character string should contain: The name of the RDH remote function.
     * The JSON used to invoke the corresponding RDH RFC web service. The JSON
     * should be compressed but contain a blank space after each comma. The
     * result of execution (ex. "Success" or "Failed") If the RFC web service
     * fails, error text should be returned which describes why the web service
     * failed.
     * @throws Exception 
     */
    public String createLogEntry() 
    {
        // The JSON should be compressed but contain a blank space after each
        // comma.
        String json = "";
        try{
            json = generateJson();
        }catch(Exception e){
            rfcrc = 8;
            error_text = "Something wrong when generated Json: " + e.getMessage();
        }
        return "RFC Result: "
                + (this.isValidRfcrc() ? "Success" : ("FAILED" + " Reason: " + this.error_text)) 
                + " " + System.getProperty("line.separator") 
                + " Remote function " + this.rfc_name + " : " + addBlankToJson(json);
    }

    // no boolean type was required right now.
    private String addBlankToJson(String jsondata)
    {
        // return jsondata.replaceAll("\",", "\", ").replaceAll("],",
        // "], ").replaceAll("},", "}, ");
        return jsondata.replaceAll("([0-9]+|\"|]|}),", "$1, ")
                .replace("\\u003d", "=").replace("\\u0027", "'")
                .replace("\\u003c", "<").replace("\\u003e", ">");
    }

    /**
     * 
     * @return RFC return code
     */
    public int getRfcrc()
    {
        return rfcrc;
    }

    /**
     * 
     * @param rfcrc RFC return code
     */
    public void setRfcrc(int rfcrc)
    {
        this.rfcrc = rfcrc;
    }

    /**
     * 
     * @return Error Message
     */
    public String getError_text()
    {
        return error_text;
    }

    /**
     * 
     * @param error_text Error Message for RFC call
     */
    public void setError_text(String error_text)
    {
        this.error_text = error_text;
    }
    
    
    
    /**
     * utility method that is used to check null or empty string
     * @param attr
     * @return
     */
    protected boolean isNullOrBlank(String attr)
    {
    	return attr == null || attr.length() == 0;
    }
    
    /**
     * check if the return code is valid and the call is successful
     * @return normal caller will return 0 as valid, but some callers may be different, depend on the child class
     */
    protected boolean isValidRfcrc(){
        return this.rfcrc == 0;
    }

    /**
     * that is used to check if the value of fields are null or empty string in specified class
     * @param checkFileds
     * @param isSingle it is about the error message - if yes, use 'The', otherwise use 'One'
     * @param clazz
     * @return
     */
    protected boolean checkFieldsNotEmplyOrNull(Object obj, List<String> checkFileds, boolean isSingle)
    {
        try{
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field : fields)
            {
                if(checkFileds.contains(field.getName())){
                    field.setAccessible(true);
                    Object object = field.get(obj);
                    if(object == null || object.toString().length() == 0)
                    {
                    	//invalid data issue ,set RFC return code to 8
                        this.setRfcrc(8);
                        String header = "The ";
                        if(!isSingle)
                        {
                            header = "One ";
                        }
                        this.setError_text(header + obj.getClass().getSimpleName() + "."
                                + field.getName()
                                + " attribute is not set to a value");
                        return false;
                    }
                }
            }
        }catch(Exception e){
            this.setRfcrc(8);
            this.setError_text("something wrong when check Null or Empty : " + this.getClass().getName());
            return false;
        }
        return true;
    }
    
    /**
     * that is used to check if the value of fields are null or empty string in specified class
     * @param <T>
     * @param objs
     * @param checkFileds
     * @return
     */
    protected <T> boolean checkFieldsNotEmplyOrNullInCollection(List<T> objs, List<String> checkFileds)
    {
        for(Object obj : objs)
        {
            boolean result = checkFieldsNotEmplyOrNull(obj, checkFileds, false);
            if(!result)
            {
                return result;
            }
        }
        return true;
    }
    
    /**
     * that is used to check if the value of fields are null or empty string in specified class
     * @param <T>
     * @param objs
     * @param checkFileds
     * @return
     */
    protected <T> boolean checkFieldsNotEmplyOrNullInCollection(List<T> objs, String checkFiled)
    {
        List<String> checkFileds = new ArrayList<String>();
        checkFileds.add(checkFiled);
        for(Object obj : objs)
        {
            boolean result = checkFieldsNotEmplyOrNull(obj, checkFileds, false);
            if(!result)
            {
                return result;
            }
        }
        return true;
    }
    public String getRFCName() {
    	return rfc_name;
    }
    /**
     * check if the field value of object is null or empty string 
     * @param obj
     * @param checkFiled
     * @return
     */
    protected boolean checkFieldsNotEmplyOrNull(Object obj, String checkFiled)
    {
        List<String> checkFileds = new ArrayList<String>();
        checkFileds.add(checkFiled);
        return checkFieldsNotEmplyOrNull(obj, checkFileds, true);
    }
    public String getInput() {
    	return input;
    }

	public HashMap<String, List<HashMap<String, String>>> getRETURN_MULTIPLE_OBJ() {
		return RETURN_MULTIPLE_OBJ;
	}

	public void setRETURN_MULTIPLE_OBJ(HashMap<String, List<HashMap<String, String>>> rETURN_MULTIPLE_OBJ) {
		RETURN_MULTIPLE_OBJ = rETURN_MULTIPLE_OBJ;
	}
    
}
