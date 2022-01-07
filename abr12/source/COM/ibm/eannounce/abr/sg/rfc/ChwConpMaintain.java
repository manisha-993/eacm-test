/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpdep_dat;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_cpro_attr;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_object_key;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhConp_rcuco;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_dep_ident;

/**
 * creates a configuration profile for a material master
 * @author wangyul
 *
 */
public class ChwConpMaintain extends RdhBase
{
    @SerializedName("OBJECT_KEY")
    private List<RdhConp_object_key> object_key ; 
    @SerializedName("CPRO_ATTR")
    private List<RdhConp_cpro_attr> cpro_attr; 
    @SerializedName("CPDEP_DAT")
    private List<RdhConp_cpdep_dat> cpdep_dat; 
    @SerializedName("RCUCO")
    private List<RdhConp_rcuco> rcuco ;
    
    public ChwConpMaintain(String obj_id, String c_profile, 
    		String bomappl, String bomexpl, String design)
    {
        super(obj_id, "Z_DM_SAP_CONP_MAINTAIN".toLowerCase(), null);
        this.pims_identity = "H";
        this.rfa_num = obj_id;
        
        object_key.get(0).setKey_feld("MATNR");
        object_key.get(0).setKpara_valu(obj_id);
        if(c_profile != null){
            cpro_attr.get(0).setC_profile(c_profile);
        }
        
        cpro_attr.get(0).setClasstype("300");
        cpro_attr.get(0).setOrgareas("");
        cpro_attr.get(0).setStatus("1");
        
        
        if(bomappl != null && bomexpl != null && Integer.parseInt(bomexpl) > 0)
        {
//            RdhConp_cpro_attr   bomappl Copy from <bomappl> parameter.
            cpro_attr.get(0).setBomappl(bomappl);
//            RdhConp_cpro_attr   bomexpl Copy from <bomexpl> parameter.
            cpro_attr.get(0).setBomexpl(bomexpl);
        }
        cpro_attr.get(0).setTasklexpl("");
        cpro_attr.get(0).setInitscreen("");
        cpro_attr.get(0).setFlassembly("");
        cpro_attr.get(0).setFlresult("");
        cpro_attr.get(0).setFlmdata("");
        cpro_attr.get(0).setFlcasonly("");
        cpro_attr.get(0).setA_laiso("");
        cpro_attr.get(0).setDesign(design);
        cpro_attr.get(0).setPrio("00");
        cpro_attr.get(0).setKz_browser("X");
        
        rcuco.get(0).setObtab("MARA");
        
        cpdep_dat = new ArrayList<RdhConp_cpdep_dat>();
    }

    @Override
    protected boolean isReadyToExecute()
    { 
        if(this.checkFieldsNotEmplyOrNull(object_key.get(0), "kpara_valu")){
            return this.checkFieldsNotEmplyOrNullInCollection(cpdep_dat, "dep_intern");
        }
        return false;
    }
    
    public void addConfigDependency(String dep_intern, String fldelete)
    {
        RdhConp_cpdep_dat dat = new RdhConp_cpdep_dat();
        cpdep_dat.add(dat);
        dat.setC_profile(cpro_attr.get(0).getC_profile());
        dat.setDep_intern(dep_intern);
        dat.setFldelete(fldelete);
        dat.setStatus("1");
    }

    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
    	object_key = new ArrayList<RdhConp_object_key>();
    	RdhConp_object_key rdhConp_object_key = new RdhConp_object_key();
    	object_key.add(rdhConp_object_key);
    	
    	cpro_attr = new ArrayList<RdhConp_cpro_attr>();
    	RdhConp_cpro_attr rdhConp_cpro_attr = new RdhConp_cpro_attr();
    	cpro_attr.add(rdhConp_cpro_attr);
    	
    	cpdep_dat = new ArrayList<RdhConp_cpdep_dat>();
    	RdhConp_cpdep_dat rdhConp_cpdep_dat = new RdhConp_cpdep_dat();
    	cpdep_dat.add(rdhConp_cpdep_dat);
    	
    	rcuco = new ArrayList<RdhConp_rcuco>();
    	RdhConp_rcuco rdhConp_rcuco = new RdhConp_rcuco();
    	rcuco.add(rdhConp_rcuco);
    	
        
    }
    
    
}
