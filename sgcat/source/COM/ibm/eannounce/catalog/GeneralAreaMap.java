/*
* $Log: GeneralAreaMap.java,v $
* Revision 1.4  2011/05/05 11:21:34  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:20  jingb
* no message
*
* Revision 1.3  2006/05/31 22:23:21  bala
* comments
*
* Revision 1.2  2006/05/31 22:06:33  bala
* "Dave" fix for genarea
*
* Revision 1.1.1.1  2006/03/30 17:36:29  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.21  2006/02/27 23:38:04  dave
* back it out
*
* Revision 1.19  2006/02/24 15:26:34  dave
* <No Comment Entered>
*
* Revision 1.18  2005/06/22 17:10:42  joan
* fixes
*
* Revision 1.17  2005/06/21 18:20:19  joan
* fix compile
*
* Revision 1.16  2005/06/21 18:13:50  joan
* fix compile
*
* Revision 1.15  2005/06/21 17:58:51  joan
* add CountryList in gamap table
*
* Revision 1.14  2005/06/02 04:49:55  dave
* more clean up
*
* Revision 1.13  2005/05/23 14:14:32  dave
* adding source, type, and interval sigs to keys
* adding getGami to Catalog statics
*
* Revision 1.12  2005/05/22 23:04:36  dave
* Added CollectionId
* addind Catalog Interval
* Placed enterprise in the Gami
*
* Revision 1.11  2005/05/18 01:06:00  dave
* trying to write a main test
*
* Revision 1.10  2005/03/30 00:51:54  dave
* adding taxonomy object
*
* Revision 1.9  2005/03/25 16:02:57  roger
* found another
*
* Revision 1.8  2005/03/24 22:16:56  roger
* fix
*
* Revision 1.7  2005/03/24 21:46:58  roger
* found one
*
* Revision 1.6  2005/03/24 21:38:03  roger
* make them pretty
*
* Revision 1.5  2005/03/24 21:29:26  roger
* again
*
* Revision 1.4  2005/03/24 21:23:26  roger
* Fix
*
* Revision 1.3  2005/03/24 21:16:37  roger
* Fix
*
* Revision 1.2  2005/03/24 21:08:12  roger
* try this
*
* Revision 1.1  2005/03/24 18:24:48  roger
* New classes
*
*/
package COM.ibm.eannounce.catalog;

import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.Profile;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * GeneralAreaMap
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GeneralAreaMap {
    private static HashMap c_hmGeneralAreaMap = new HashMap();
    private static String COUNTRYLIST = new String("COUNTRYLIST");
    private static String WW = new String("WW");


    /**
     * GeneralAreaMap
     *
     * @param _db
     *  @author David Bigelow
     */
    public GeneralAreaMap(String _strEnterprise, Database _db) {

        ArrayList alGeneralAreaMap = new ArrayList();
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        String strMethod = "GeneralAreaMap constructor";
        String strSP = "gbl9976";

        try {

            int iNLSID = 1;
            GeneralAreaMapItem gami = null;

            String strCountry = null;
            String strLanguage = null;
            String strPreviousGeneralArea = "";
            String strGeneralArea = "";
            String strCountryList = "";
            rs = _db.callGBL9976(returnStatus, _strEnterprise);

            while (rs.next()) {

                strGeneralArea = rs.getString(1).trim();
                if (strPreviousGeneralArea.length() == 0) {
                    strPreviousGeneralArea = strGeneralArea;
                }

                strCountry = rs.getString(2).trim();
                strLanguage = rs.getString(3).trim();
                iNLSID = rs.getInt(4);
                strCountryList = rs.getString(5).trim();
                D.ebug(D.EBUG_DETAIL,
                           "gbl9976 answers, " + strGeneralArea + ":" + strCountry + ":" + strLanguage + ":" + iNLSID + ":" +
                           strCountryList);

                gami = new GeneralAreaMapItem(_strEnterprise, strGeneralArea, strCountry, strLanguage, iNLSID, strCountryList);

                if (!strGeneralArea.equals(strPreviousGeneralArea)) {
                    c_hmGeneralAreaMap.put(strGeneralArea, alGeneralAreaMap.clone());
                    alGeneralAreaMap.clear();
                    strPreviousGeneralArea = strGeneralArea;
                }
                alGeneralAreaMap.add(gami);

            }

            c_hmGeneralAreaMap.put(strGeneralArea, alGeneralAreaMap.clone());
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Exception trapped at: " + strMethod + "." + strSP + " " + x);
        } finally {
            try {
                rs.close();
                _db.commit();
                _db.freeStatement();
                _db.isPending();

                rs = null;
            } catch (Exception x) {
                // do nothing
            }
        }
    }
    /**
     * lookupGeneralArea
     *
     * @param _ga
     * @return
     *  @author David Bigelow
     */
    public GeneralAreaMapGroup lookupGeneralArea(String _ga) {

        GeneralAreaMapGroup gamg = new GeneralAreaMapGroup();
        GeneralAreaMapItem gami = null;
        ArrayList al = (ArrayList) c_hmGeneralAreaMap.get(_ga);

        if (al != null) {
            Iterator it = al.iterator();

            while (it.hasNext()) {
                gami = (GeneralAreaMapItem) it.next();

                gamg.add(gami);
            }
        }

        return gamg;
    }

    public boolean isWorldWide(Database _db, Profile _prof, EntityItem _ei, String _strCountryList) {
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
                ReturnDataResultSet rdrs = null;
                String strValOn = _prof.getValOn();
                String strEffOn = _prof.getEffOn();
                String strEnterprise = _prof.getEnterprise();
                try {
            rs = _db.callGBL7668(returnStatus, strEnterprise, COUNTRYLIST, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
            boolean bFound = false;
            for (int i = 0; i < rdrs.size(); i++) {
                String strEntityType = rdrs.getColumn(i, 0);
                                if (strEntityType.equals(_ei.getEntityType())) {
                                        bFound = true;
                                        break;
                                }
                        }

                        if (!bFound) {
                                //if entity doesn't have COUNTRYLIST as attribute, it is worldwide
                                return true;
                        } else {
                                GeneralAreaMapItem gami = null;
                                ArrayList al = (ArrayList) c_hmGeneralAreaMap.get(WW);

                                if (al != null) {
                                        Iterator it = al.iterator();
                                                while (it.hasNext()) {
                                                gami = (GeneralAreaMapItem) it.next();
                                                String strCountryList = gami.getCountryList();
                                                if (strCountryList.equals(_strCountryList)) {
                                                        return true;
                                                }
                                        }
                                }
                        }
                } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Exception trapped at isWorldWide ");
        } finally {
                        return false;
        }
        }
}
