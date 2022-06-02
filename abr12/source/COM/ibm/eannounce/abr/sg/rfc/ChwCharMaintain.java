/* Copyright IBM Corp. 2021 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.entity.Char_descrTable;
import COM.ibm.eannounce.abr.sg.rfc.entity.Char_valsTable;
import COM.ibm.eannounce.abr.sg.rfc.entity.CharactsTable;
import COM.ibm.eannounce.abr.sg.rfc.entity.Chv_descrTable;

import com.google.gson.annotations.SerializedName;


public class ChwCharMaintain extends RdhBase {
	@SerializedName("REFRESH_VALS") 
	private String REFRESH_VALS;
	@SerializedName("CHARACT")
	private String charact;
	
	@SerializedName("CHARACTS")
	private List<CharactsTable> characts = new ArrayList<CharactsTable>();
	private List<Char_descrTable> CHAR_DESCR = new ArrayList<Char_descrTable>();
	private List<Char_valsTable> CHAR_VALS = new ArrayList<Char_valsTable>();
	private List<Chv_descrTable> CHV_DESCR = new ArrayList<Chv_descrTable>();
	
	public ChwCharMaintain (
			String obj_id, String charact, String datatype, int charnumber, 
			String decplaces, String casesens, 	String neg_vals, String group, 
			String valassignm, String no_entry, String no_display, String addit_vals, 
			String chdescr) {	
		super(obj_id, "z_dm_sap_char_maintain", null);
		this.pims_identity = "H";
		this.rfa_num = obj_id;
		this.REFRESH_VALS = "";
		this.charact = charact;
		
		CharactsTable chars = new CharactsTable();
		characts.add(chars);
		chars.setCHARACT(charact);
		chars.setDATATYPE(datatype);
		chars.setCHARNUMBER(Integer.toString(charnumber));
		chars.setDECPLACES(decplaces !=null && decplaces.length() > 0 ? decplaces : "");
		chars.setCASESENS(casesens !=null && casesens.length() > 0 ? casesens : "");
		chars.setNEG_VALS(neg_vals !=null && neg_vals.length() > 0 ? neg_vals : "");
		chars.setSTATUS("1");
		chars.setGROUP(group !=null && group.length() > 0 ? group : "");
		chars.setVALASSIGNM(valassignm !=null && valassignm.length() > 0 ? valassignm : "");
		chars.setNO_ENTRY(no_entry !=null && no_entry.length() > 0 ? no_entry : "");
		chars.setNO_DISPLAY(no_display !=null && no_display.length() > 0 ? no_display : "");
		chars.setADDIT_VALS(addit_vals !=null && addit_vals.length() > 0 ? addit_vals : "");
		if(chdescr != null && chdescr.length() > 0){
//			char_descr = new ArrayList<RdhChar_char_descr>();
			Char_descrTable descr = new Char_descrTable();
			CHAR_DESCR.add(descr);
			descr.setCHARACT(charact);
			descr.setLANGUAGE("E");
			descr.setCHDESCR(chdescr);
		}

	}
	
	public ChwCharMaintain (String obj_id, String charact, String value, String valdescr)
	{
		super(obj_id, "z_dm_sap_char_maintain", null);
		addValue(value,valdescr);
	}
	
	public void addValue (String value, String valdescr )
	{
		Char_valsTable val = new Char_valsTable();
		CHAR_VALS.add(val);
		val.setCHARACT(charact);
		val.setVALUE(value);
		Chv_descrTable descr = new Chv_descrTable();
		CHV_DESCR.add(descr);
		descr.setCHARACT(charact);
		descr.setLANGUAGE("E");
		descr.setVALUE(value);
		descr.setVALDESCR(valdescr);
	}
	

	@Override
	protected void setDefaultValues() {
	}

	@Override
	protected boolean isReadyToExecute() {
	    List<String> charFields = new ArrayList<String>();
	    charFields.add("charact");
	    charFields.add("datatype");
	    charFields.add("charnumber");
	    if(this.checkFieldsNotEmplyOrNullInCollection(characts, charFields)){
	        List<String> descFields = new ArrayList<String>();
	        descFields.add("charact");
	        descFields.add("value");
	        return this.checkFieldsNotEmplyOrNullInCollection(CHV_DESCR, descFields);
	    }
	    return false;
	}
}
