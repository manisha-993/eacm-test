/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_dep_ident;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depdat;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depdescr;
import COM.ibm.eannounce.abr.sg.rfc.entity.RdhDepd_depsource;

/**
 * creates/updates an object dependency definition
 * @author wangyul
 *
 */
public class ChwDepdMaintain extends RdhBase
{
    @SerializedName("DEP_IDENT")
    private List<RdhDepd_dep_ident> dep_ident;
    @SerializedName("DEPDAT")
    private List<RdhDepd_depdat> depdat;
    @SerializedName("DEPDESCR")
    private List<RdhDepd_depdescr> depdescr;
    @SerializedName("DEPSOURCE")
    private List<RdhDepd_depsource> depsource;
    
    /**
     * The product schedule which supplies the handle and details of the product data feed
     * @param productSchedule
     * @param dep_extern
     * @param dep_type
     * @param descript
     */
    public ChwDepdMaintain (String obj_id, String dep_extern, String dep_type, String descript ){
        super(obj_id,"Z_DM_SAP_DEPD_MAINTAIN".toLowerCase(),null);
        this.pims_identity = "H";
        this.rfa_num = obj_id;
        
        dep_ident.get(0).setDep_extern(dep_extern);
        depdat.get(0).setDep_type(dep_type);
        depdat.get(0).setStatus("1");
        depdescr.get(0).setLanguage("E");
        depdescr.get(0).setDescript(descript);
    }
    
    

    /* (non-Javadoc)
     * @see com.ibm.sdpi.cmd.interfaces.rdh.esw.caller.RdhBase#setDefaultValues()
     */
    @Override
    protected void setDefaultValues()
    {
        dep_ident = new ArrayList<RdhDepd_dep_ident>();
        RdhDepd_dep_ident indent = new RdhDepd_dep_ident();
        dep_ident.add(indent);
        
        depdat = new ArrayList<RdhDepd_depdat>();
        RdhDepd_depdat dat = new RdhDepd_depdat();
        dat.setStatus("1");
        depdat.add(dat);
        
        depdescr = new ArrayList<RdhDepd_depdescr>();
        RdhDepd_depdescr descr = new RdhDepd_depdescr();
        descr.setLanguage("E");
        depdescr.add(descr);
        
        depsource = new ArrayList<RdhDepd_depsource>();
    }



    @Override
    protected boolean isReadyToExecute()
    {
        if(this.checkFieldsNotEmplyOrNull(dep_ident.get(0), "dep_extern"))
        {
            
            if(this.checkFieldsNotEmplyOrNull(depdat.get(0), "dep_type"))
            {
                
                return this.checkFieldsNotEmplyOrNull(depdescr.get(0), "descript");
            }
        }
        return false;

    }
    
    public void addSourceLineCondition(String sourceLine)
    {
        RdhDepd_depsource source = new RdhDepd_depsource();
        source.setLine(sourceLine);
        depsource.add(source);
    }

}
