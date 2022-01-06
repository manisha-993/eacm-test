/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSABRSTATUS;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhBase_zdm_geo_to_class;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_api_ausp;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_klah;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_kssk;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_mara;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_object_key;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhClaf_rcuco;
import COM.ibm.eannounce.abr.sg.rfc.entity.Rdhzdmprktbl;
import COM.ibm.eannounce.abr.util.DateUtility;
import COM.ibm.eannounce.abr.util.RfcConfigProperties;

import com.google.gson.annotations.SerializedName;


/**
 *
 */
public class UpdateParkStatus extends RdhBase
{
    @SerializedName("ZDMPRKTBL")
    List<Rdhzdmprktbl>  rdhzdmprktbls = null;
    @SerializedName("Z_GEO")
    String zgeo = null;

    /**
     * Constructor
     * @param productSchedule
     * @param obj_id
     * @param class_name
     * @param class_type
     * @param enablementprocess
     */
    public UpdateParkStatus (String zdmclass,String zdmrelnum)
    {
    	super(zdmrelnum,"Z_DM_SAP_PARK_STATUS ".toLowerCase(),null);
    	rdhzdmprktbls = new ArrayList<Rdhzdmprktbl>();
    	Rdhzdmprktbl rdhzdmprktbl = new Rdhzdmprktbl();
    	rdhzdmprktbl = new Rdhzdmprktbl();
    	rdhzdmprktbl.setZdmclass(zdmclass);
    	rdhzdmprktbl.setZdmrelnum(zdmrelnum);
    	rdhzdmprktbl.setZdmstatus(RfcConfigProperties.getZdmstatus());
    	rdhzdmprktbls.add(rdhzdmprktbl);
    	zgeo = "WW";
    }
    
   
 
    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
        super.setDefaultValues();
       
    }

    @Override
    protected boolean isReadyToExecute()
    {
        
        return true;

    }

}
