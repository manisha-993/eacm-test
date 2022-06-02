/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import com.google.gson.annotations.SerializedName;

public class RdhDepd_depsource
{
    @SerializedName("LINE")
    private String line;

    /**
     * @return the line
     */
    public String getLine()
    {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(String line)
    {
        this.line = line;
    }
    
    
}
