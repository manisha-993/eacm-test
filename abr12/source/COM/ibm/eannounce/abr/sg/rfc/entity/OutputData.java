/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.sg.rfc.entity;

import java.util.HashMap;
import java.util.List;

/**
 * this class is used to store the return value from RFC, and move from RFC client jar
 * @author will
 *
 */
public class OutputData
{
    public static final int ERROR_MSG_LIMIT = 320;
    
    private int RFCRC;
    private String ERROR_TEXT;
    private HashMap<String, HashMap<String, String>> RETURN_OBJ;
    private HashMap<String, List<HashMap<String, String>>> RETURN_MULTIPLE_OBJ;
    private String FL_WARNING;

    public String getFL_WARNING()
    {
        return FL_WARNING;
    }

    public void setFL_WARNING(String fL_WARNING)
    {
        FL_WARNING = fL_WARNING;
    }

    public int getRFCRC()
    {
        return RFCRC;
    }

    public void setRFCRC(int rFCRC)
    {
        RFCRC = rFCRC;
    }

    public String getERROR_TEXT()
    {
        if (ERROR_TEXT.length() > ERROR_MSG_LIMIT)
            return ERROR_TEXT.substring(0, ERROR_MSG_LIMIT);
        return ERROR_TEXT;
    }

    public void setERROR_TEXT(String eRROR_TEXT)
    {
        ERROR_TEXT = eRROR_TEXT;
    }

    public HashMap<String, HashMap<String, String>> getRETURN_OBJ()
    {
        return RETURN_OBJ;
    }

    public void setRETURN_OBJ(HashMap<String, HashMap<String, String>> rETURN_OBJ)
    {
        RETURN_OBJ = rETURN_OBJ;
    }

    public HashMap<String, List<HashMap<String, String>>> getRETURN_MULTIPLE_OBJ()
    {
        return RETURN_MULTIPLE_OBJ;
    }

    public void setRETURN_MULTIPLE_OBJ(HashMap<String, List<HashMap<String, String>>> rETURN_MULTIPLE_OBJ)
    {
        RETURN_MULTIPLE_OBJ = rETURN_MULTIPLE_OBJ;
    }
}
