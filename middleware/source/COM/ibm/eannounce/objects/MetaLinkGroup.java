//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaLinkGroup.java,v $
// Revision 1.26  2012/11/08 21:59:53  wendy
// rollback if error instead of commit
//
// Revision 1.25  2009/05/04 15:46:01  wendy
// check for rs!=null before close()
//
// Revision 1.24  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.23  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.22  2004/01/14 18:35:41  dave
// dumping short description in Ml
//
// Revision 1.21  2004/01/13 17:56:33  dave
// more squeezing
//
// Revision 1.20  2003/04/08 02:38:06  dave
// commit()
//
// Revision 1.19  2003/01/07 00:40:11  dave
// more Orphan Checking
//
// Revision 1.18  2002/03/05 01:50:34  dave
// more throws fixing
//
// Revision 1.17  2002/03/05 01:31:16  dave
// more syntax fixes
//
// Revision 1.16  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.15  2002/02/18 18:42:17  dave
// adding cart methods
//
// Revision 1.14  2002/02/11 09:00:50  dave
// more dump rearrangements
//
// Revision 1.13  2002/02/01 00:42:33  dave
// more foundation fixes for passing _prof
//
// Revision 1.12  2002/01/28 20:11:57  dave
// syntax D.EBUG_ERR
//
// Revision 1.11  2002/01/28 20:04:08  dave
// rearranged to ensure that we have the proper entitytypes
// represented in the MetaLink in the right order
//
// Revision 1.10  2002/01/22 22:02:18  dave
// more NavigateObject Integration...
//
// Revision 1.9  2002/01/22 00:10:31  dave
// fixes for syntax
//
// Revision 1.8  2002/01/21 23:58:54  dave
// added the logic to createe MetaLinks out of the Meta Link Group
//
// Revision 1.7  2002/01/21 23:20:36  dave
// more syntax.. closing in on it
//
// Revision 1.6  2002/01/21 23:04:30  dave
// more fixes for imports
//
// Revision 1.5  2002/01/21 22:39:00  dave
// found the duplicate
//
// Revision 1.4  2002/01/21 22:07:51  dave
// static int fix
//
// Revision 1.3  2002/01/21 20:59:51  dave
// bit the bullet and use profile to imply NLSItem
//
// Revision 1.2  2002/01/21 20:37:42  dave
// minor syntax fixes
//
// Revision 1.1  2002/01/21 20:26:51  dave
// insert of new object to better manage linking for NavObjects
//
//
package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.D;
 
/**
 * MetaLinkGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MetaLinkGroup extends EANMetaEntity {

    static final long serialVersionUID = 20011106L;

    // Constants
    /**
     * FIELD
     */
    public static final int DOWN = 0;
    /**
     * FIELD
     */
    public static final int UP = 1;

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /*
    * Version info
    */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: MetaLinkGroup.java,v 1.26 2012/11/08 21:59:53 wendy Exp $";
    }

    /**
     * This represents an Meta Link Group Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strEntityType
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException 
     */
    public MetaLinkGroup(EANMetaFoundation _emf, Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareRequestException, MiddlewareException {

        super(_emf, _prof, _strEntityType);

        try {

            // Future.. This builds a list of MetaLinks based upon Role and EntityType
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            String strEnterprise = getProfile().getEnterprise();
            String strRoleCode = getProfile().getRoleCode();
            String strValOn = getProfile().getValOn();
            String strEffOn = getProfile().getEffOn();

            int iNLSID = getNLSID();
            int iDirection = -1;
            boolean success = false;

            // we have to get the group description here..
            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7005(returnStatus, strEnterprise, strRoleCode, getEntityType(), iNLSID, strValOn, strEffOn);
                rdrs = new ReturnDataResultSet(rs);
                success = true;
            } finally {
            	if (rs !=null){
            		rs.close();
            		rs = null;
            	}
            	if(success){
            		_db.commit();
            	}else{
            		_db.rollback();
            	}
                _db.freeStatement();
                _db.isPending();
            }

            // We need to get the description of the link group here...
            for (int i = 0; i < rdrs.size(); i++) {
                
                MetaLink ml = null;

                String str1 = rdrs.getColumn(i, 0); // U or D
                String str2 = rdrs.getColumn(i, 1); // Relator Tag (Key)
                String str3 = rdrs.getColumn(i, 2); // R, W, C (Capability of link)
                int i4 = rdrs.getColumnInt(i, 3); // NLSIDLongDescription
                String str5 = rdrs.getColumn(i, 4); // ShortDescription
                String str6 = rdrs.getColumn(i, 5); // LongDescription
                String str7 = rdrs.getColumn(i, 6); // Linkto EntityType
                String strOrphanCheck = rdrs.getColumn(i, 7); // OrphanCheck?

                _db.debug(D.EBUG_SPEW, "gbl7005 answers:" + str1 + ":" + str2 + ":" + str3 + ":" + i4 + ":" + str5 + ":" + str6 + ":" + str7 + ":" + strOrphanCheck + ":");

                iDirection = (str1.charAt(0) == 'U') ? UP : DOWN;
                ml = getMetaLink(iDirection, str2);

                if (ml == null) {
                    if (iDirection == UP) {
                        ml = new MetaLink(this, _prof, str2, str7, _strEntityType, str3, strOrphanCheck.equals("YES"));
                    } else {
                        ml = new MetaLink(this, _prof, str2, _strEntityType, str7, str3, strOrphanCheck.equals("YES"));
                    }
                    putMetaLink(iDirection, ml);
                }

                //ml.putShortDescription(i4,str5);
                ml.putLongDescription(i4, str6);

            }

            // We now have everything...

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return getKey();
    }

    /**
     * getMetaLinkCount
     *
     * @param _ii
     * @return
     *  @author David Bigelow
     */
    public int getMetaLinkCount(int _ii) {
        switch (_ii) {
        case DOWN :
            return getMetaCount();
        case UP :
            return getDataCount();
        default :
            break;
        }
        return 0;
    }

    /**
     * getMetaLink
     *
     * @param _ii
     * @param _iy
     * @return
     *  @author David Bigelow
     */
    public MetaLink getMetaLink(int _ii, int _iy) {
        switch (_ii) {
        case DOWN :
            return (MetaLink) getMeta(_iy);
        case UP :
            return (MetaLink) getData(_iy);
        default :
            break;
        }
        return null;
    }

    /**
     * getMetaLink
     *
     * @param _ii
     * @param _str1
     * @return
     *  @author David Bigelow
     */
    public MetaLink getMetaLink(int _ii, String _str1) {
        switch (_ii) {
        case DOWN :
            return (MetaLink) getMeta(_str1);
        case UP :
            return (MetaLink) getData(_str1);
        default :
            break;
        }
        return null;
    }

    /**
     * getMetaLink
     *
     * @param _ii
     * @return
     *  @author David Bigelow
     */
    public EANList getMetaLink(int _ii) {
        switch (_ii) {
        case DOWN :
            return getMeta();
        case UP :
            return getData();
        default :
            break;
        }
        return null;
    }

    /**
     * putMetaLink
     *
     * @param _ii
     * @param _ml
     *  @author David Bigelow
     */
    protected void putMetaLink(int _ii, MetaLink _ml) {
        switch (_ii) {
        case DOWN :
            putMeta(_ml);
            break;
        case UP :
            putData(_ml);
            break;
        default :
            break;
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {

        StringBuffer strbResult = new StringBuffer();

        if (_bBrief) {
            strbResult.append(getKey());
        } else {
            strbResult.append("MetaLinkGroup: " + getKey());
            strbResult.append(":ShortDesc: " + getShortDescription());
            strbResult.append(":LongDesc: " + getLongDescription());
            strbResult.append(NEW_LINE + "MetaLink Down");
            for (int i = 0; i < getMetaLinkCount(DOWN); i++) {
                MetaLink ml = getMetaLink(DOWN, i);
                strbResult.append(NEW_LINE + "" + i + ":" + ml.dump(_bBrief));
            }
            strbResult.append(NEW_LINE + "MetaLink Up");
            for (int i = 0; i < getMetaLinkCount(UP); i++) {
                MetaLink ml = getMetaLink(UP, i);
                strbResult.append(NEW_LINE + "" + i + ":" + ml.dump(_bBrief));
            }
        }

        return strbResult.toString();

    }

}
