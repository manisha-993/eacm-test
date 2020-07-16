package COM.ibm.eannounce.catalog;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.sql.*;
import COM.ibm.opicmpdh.objects.*;

public class LanguageMapper {

    private Hashtable m_hash = null;

    public LanguageMapper(Database _db, String _strEnterprise, String _strEntity, String _strAttFrom, String _strAttTo) throws
        SQLException, MiddlewareException {
        D.ebug(D.EBUG_SPEW, "LanguageMapper constructor...");
        m_hash = new Hashtable();
        ResultSet rs = null;
        try {
            _db.debug(D.EBUG_DETAIL, "gbl4026:params:" + _strEnterprise + ":" + _strEntity + ":" + _strAttFrom + ":" + _strAttTo);
            rs = _db.callGBL4026(new ReturnStatus( -1), _strEnterprise, _strEntity, _strAttFrom, _strAttTo);
            ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
            _db.debug(D.EBUG_SPEW, "gbl4026:size:" + rdrs.getRowCount());
            for (int i = 0; i < rdrs.getRowCount(); i++) {
                String strFC_key = rdrs.getColumn(i, 0);
                String strVal = rdrs.getColumn(i, 1);
                String strFC = rdrs.getColumn(i, 2);
                _db.debug(D.EBUG_DETAIL, "gbl4026:answer: FC (key):" + strFC_key + ":Mapped Val:" + strVal + ":Mapped FC:" + strFC);
                String strKey = _strAttFrom + ":" + strFC_key;
                String strValHash = strVal + ":" + strFC;
                m_hash.put(strKey, strValHash);
            }
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    public String[] getMapping(String _strAttCode, String _strVal, String _strFC) {
        String strVal = (String) m_hash.get(_strAttCode + ":" + _strFC);
        System.out.println("getMapping():looking at:_strAttCode:" + _strAttCode + ":_strFC:" + _strFC + ":strVal:" + strVal);
        if (strVal == null) {
            System.out.println("getMapping(): no value found here....using original values...");
            return new String[] {
                _strVal, _strFC};
        }
        StringTokenizer st = new StringTokenizer(strVal, ":");
        String s1 = st.nextToken();
        String s2 = st.nextToken();
        String[] sa = new String[] {
            s1, s2};
        return sa;
    }

}
