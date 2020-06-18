//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaFlagAttributeList.java,v $
// Revision 1.21  2011/10/05 00:12:39  wendy
// use stringbuffer.tostring instead of new string
//
// Revision 1.20  2005/03/31 23:13:44  dave
// Fixing Null Pointer
//
// Revision 1.19  2005/03/07 23:00:56  dave
// more Jtest
//
// Revision 1.18  2004/01/26 19:09:04  dave
// syntax on trace
//
// Revision 1.17  2004/01/26 19:01:31  dave
// added some trace to see what the passed profile looks
// like
//
// Revision 1.16  2002/05/03 18:24:13  gregg
// replaced gbl6013 w/ gbl8613
//
// Revision 1.15  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.14  2002/01/31 22:57:19  dave
// more Foundation Cleanup
//
// Revision 1.13  2002/01/21 20:59:50  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.12  2001/11/26 18:42:48  dave
// carry forward for metaflag fix
//
// Revision 1.11  2001/10/12 00:30:48  dave
// more fixes
//
// Revision 1.10  2001/10/12 00:27:05  dave
// fixes
//
// Revision 1.9  2001/10/12 00:18:18  dave
// test for MetaFlagAttributeList
//
// Revision 1.8  2001/10/09 16:12:10  dave
// more fixes
//
// Revision 1.7  2001/10/09 16:02:35  dave
// fixes
//
// Revision 1.6  2001/10/08 20:53:51  dave
// added final touch to MetaFlagAttributeList
//
// Revision 1.5  2001/10/04 22:21:14  dave
// fixes
//
// Revision 1.4  2001/10/04 21:09:15  dave
// fixes
//
// Revision 1.3  2001/10/04 21:04:43  dave
// more fixes
//
// Revision 1.2  2001/10/04 20:58:13  dave
// more massive changes to eannounce objects
//
// Revision 1.1  2001/10/04 20:29:41  dave
// serveral new adds and renames
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.D;

/**
* This manages A List of MetaFlagAttributes related by dependent choice fields
*/
public class MetaFlagAttributeList extends EANMetaEntity {

    private MetaFlagAttribute m_mfa = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /*
    * Instantiates a new object
    * the passed String is the Root Attribute Code to start from
    * @param _db  database
    * @param _prof Login Profile
    * @param _s Root Attribute Code  (which is the unique identifier of this object)
    */
    /**
     * MetaFlagAttributeList
     *
     * @param _db
     * @param _prof
     * @param _s
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     *  @author David Bigelow
     */
    public MetaFlagAttributeList(Database _db, Profile _prof, String _s) throws SQLException, MiddlewareException {
        super(null, _prof, _s);

        m_mfa = new MetaFlagAttribute(this, _db, _prof, _s);
        super.putData(m_mfa);

        try {

            MetaFlagAttribute mfa1 = null;
            MetaFlagAttribute mfa2 = null;
            MetaFlag mf1 = null;
            MetaFlag mf2 = null;

            ReturnStatus returnStatus = new ReturnStatus(-1);
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            
            try {
                // Get the description
                rs = _db.callGBL8613(returnStatus, _prof.getOPWGID(), _prof.getEnterprise().trim(), _s, _prof.getValOn(), _prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.freeStatement();
                _db.isPending();
            }
            
            for (int x = 0; x < rdrs.size(); x++) {
                int i1 = rdrs.getColumnInt(x, 0);
                String s2 = rdrs.getColumn(x, 1);
                String s3 = rdrs.getColumn(x, 2);
                String s4 = rdrs.getColumn(x, 3);
                String s5 = rdrs.getColumn(x, 4);
            
                mfa1 = (MetaFlagAttribute) super.getData(s2);
                mfa2 = (MetaFlagAttribute) super.getData(s4);
                
                _db.debug(D.EBUG_SPEW, "gbl8613 answers:" + i1 + ":" + s2 + ":" + s3 + ":" + s4 + ":" + s5);

                if (mfa1 == null) {
                    mfa1 = new MetaFlagAttribute(this, _db, _prof, s2);
                    putData(mfa1);
                }
                if (mfa2 == null) {
                    mfa2 = new MetaFlagAttribute(this, _db, _prof, s4);
                    putData(mfa2);
                }
                
                mf1 = mfa1.getMetaFlag(s3);
                mf2 = mfa2.getMetaFlag(s5);
            
                if (mf1 == null) {
                    _db.debug(D.EBUG_ERR, "cannot find corresponding flag value in mf1: " + s2 + ":" + s3);
                    continue;
                }
                if (mf2 == null) {
                    _db.debug(D.EBUG_ERR, "cannot find corresponding flag value in mf2: " + s4 + ":" + s5);
                    continue;
                }

                // O.K. Link them up

                mf1.addController(mf2);
                mf2.addFilter(mf1);
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }

    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode() {
        return getKey();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("*MetaFlagAttributeList: " + getKey());
            strbResult.append(NEW_LINE + "LongDescription:" + getLongDescription());
            strbResult.append(NEW_LINE + "ShortDescription:" + getShortDescription());
            strbResult.append(NEW_LINE + "*BaseMetaFlagAttribute: " + m_mfa.dump(_bBrief));
            for (int x = 0; x < getMetaFlagAttributeCount(); x++) {
                strbResult.append(NEW_LINE + TAB + getMetaFlagAttribute(x).dump(_bBrief));
            }
        }
        return strbResult.toString();
    }

    /**
     * getParent
     *
     * @param _mf
     * @return
     *  @author David Bigelow
     */
    public MetaFlagAttribute getParent(MetaFlag _mf) {
        return (MetaFlagAttribute) _mf.getParent();
    }

    /**
     * getMetaFlagAttribute
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaFlagAttribute getMetaFlagAttribute(int _i) {
        return (MetaFlagAttribute) super.getData(_i);
    }

    /**
     * getMetaFlagAttribute
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public MetaFlagAttribute getMetaFlagAttribute(String _s) {
        return (MetaFlagAttribute) super.getData(_s);
    }

    /**
     * getMetaFlagAttributeCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaFlagAttributeCount() {
        return super.getDataCount();
    }

    /*
    * used to extract the top most metaflagattribute object
    * to peruse any dependent flag settings
    */
    /**
     * getBaseMetaFlagAttribute
     *
     * @return
     *  @author David Bigelow
     */
    public MetaFlagAttribute getBaseMetaFlagAttribute() {
        return m_mfa;
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaFlagAttributeList.java,v 1.21 2011/10/05 00:12:39 wendy Exp $";
    }
}
