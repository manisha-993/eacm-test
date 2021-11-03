/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhDepd_dep_ident
{
    @SerializedName("DEP_EXTERN")
    private String dep_extern;

    /**
     * @return the dep_extern
     */
    public String getDep_extern()
    {
        return dep_extern;
    }

    /**
     * @param dep_extern the dep_extern to set
     */
    public void setDep_extern(String dep_extern)
    {
        this.dep_extern = dep_extern;
    }
}
