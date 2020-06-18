//
//$Log: MetaRoleList.java,v $
//Revision 1.20  2009/03/20 15:38:06  wendy
//Check for null before rs.close()
//
//Revision 1.19  2005/03/08 23:15:46  dave
//Jtest checkins from today and last ngith
//
//Revision 1.18  2002/12/18 18:51:41  gregg
//more in new constructor
//
//Revision 1.17  2002/12/18 18:06:44  gregg
//constructor allowing ALL roles to be included
//
//Revision 1.16  2002/11/18 21:20:24  gregg
//ignore case logic on performFilter
//
//Revision 1.15  2002/11/18 18:41:30  gregg
//more wildcard for filter
//
//Revision 1.14  2002/09/06 17:57:53  gregg
//more sort/filter
//
//Revision 1.13  2002/09/06 17:38:46  gregg
//SortFilterInfo now uses String sort/filter key constants (were ints)
//
//Revision 1.12  2002/07/18 18:11:16  gregg
//moved SortFilterInfo into its own class
//
//Revision 1.11  2002/07/18 16:51:57  gregg
//some filter adjusting..
//
//Revision 1.10  2002/07/17 21:48:37  gregg
//Sort Filter stuff
//
//Revision 1.9  2002/06/14 22:29:35  gregg
//sortByRoleCode, sortByRoleDesc methods
//
//Revision 1.8  2002/04/11 20:05:51  gregg
//set rs = null
//
//Revision 1.7  2002/04/11 00:36:58  minhthy
//*** empty log message ***
//
//Revision 1.6  2002/03/25 21:05:23  gregg
//alphabetize()
//
//Revision 1.5  2002/03/22 18:56:16  gregg
//puMetaRole()
//
//Revision 1.4  2002/03/20 23:18:26  gregg
//removeMetaRole(MetaRole)
//
//Revision 1.3  2002/03/20 23:12:55  gregg
//removeMetaRole() methods
//
//Revision 1.2  2002/03/20 19:10:40  gregg
//setActiveMetaRole(), getActiveMetaRole()
//
//Revision 1.1  2002/03/13 17:52:47  gregg
//initial load
//
//

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * Manages a MetaRole and the list of Roles which it can maintain
 * Preserve sort/filter states
 */
public class MetaRoleList extends EANMetaEntity {

    /**
     * FIELD
     */
    public static String FILTER_BY_ROLECODE = "Role Code";
    /**
     * FIELD
     */
    public static String FILTER_BY_ROLEDESC = "Role Description";

    private SortFilterInfo m_SFInfo = null;
    private MetaRole m_oActiveRole = null;

    /**
     * create an empty one
     *
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    protected MetaRoleList(Profile _prof) throws MiddlewareRequestException {
        super(null, _prof, _prof.toString() + "MetaRoleList");
        m_SFInfo = new SortFilterInfo(MetaRole.SORT_BY_ROLEDESC, true, MetaRoleList.FILTER_BY_ROLEDESC, null);
    }

    /**
     * Get A MetaRole List containing ALL ROLES IN DATABASE (not using Role/Maintainer record)
     *
     * @param _db
     * @param _oProfile
     * @param _bAllRoles
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaRoleList(Database _db, Profile _oProfile, boolean _bAllRoles) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_oProfile);
        //super(null,_oProfile,_oProfile.toString() + "MetaRoleList");

        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            ReturnDataResultSet rdrsInner = null;

            String strEnterprise = _oProfile.getEnterprise();
            String strRoleCode = _oProfile.getRoleCode();
            int iNLSID = _oProfile.getReadLanguage().getNLSID();
            int iOPWGID = _oProfile.getOPWGID();
            String strValEffOn = _oProfile.getValOn();
            Vector vctHolder = new Vector();

            //ALL ROLES
            if (_bAllRoles) {
                //1) get all Role EntityTypes
                try {
                    rs = _db.callGBL7529(new ReturnStatus(-1), strEnterprise, "Role", strValEffOn, strValEffOn);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                	if (rs!=null){
                		rs.close();
                		rs = null;
                	}
                    _db.commit();
                    _db.freeStatement();
                    _db.isPending();
                }
                //2)get Descriptions
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strEntType = rdrs.getColumn(row, 0);
                    try {
                        rs = _db.callGBL7534(new ReturnStatus(-1), strEnterprise, strEntType, "Role", strValEffOn, strValEffOn);
                        rdrsInner = new ReturnDataResultSet(rs);
                    } finally {
                    	if (rs!=null){
                    		rs.close();
                    		rs = null;
                    	}
                        _db.commit();
                        _db.freeStatement();
                        _db.isPending();
                    }
                    for (int rowInner = 0; rowInner < rdrsInner.getRowCount(); rowInner++) {
                        int iNLSIDinner = rdrsInner.getColumnInt(rowInner, 2);
                        String strLongDesc = rdrsInner.getColumn(rowInner, 1);
                        if (iNLSIDinner == getProfile().getReadLanguage().getNLSID()) {
                            String[] sa = new String[] { strEntType, strLongDesc };
                            vctHolder.addElement(sa);
                        }
                    }
                }
            }
            //MaINTAINERS ONLY 
            else {
                //get list of all maintainable roles
                rs = _db.callGBL7500(new ReturnStatus(), strEnterprise, strRoleCode, iNLSID, iOPWGID, strValEffOn);
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                rs = null;
                _db.freeStatement();
                _db.isPending();
                for (int row = 0; row < rdrs.getRowCount(); row++) {
                    String strCode = rdrs.getColumn(row, 1);
                    String strDesc = rdrs.getColumn(row, 0);
                    String[] sa = new String[] { strCode, strDesc };
                    vctHolder.addElement(sa);
                }
            }
            for (int row = 0; row < vctHolder.size(); row++) {
                String[] sa = (String[]) vctHolder.elementAt(row);
                String strCode = sa[0];
                String strDesc = sa[1];
                MetaRole mr = new MetaRole(this, _oProfile, strCode, strDesc);
                putMeta(mr);
            }

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        alphabetize();
        return;
    }

    /**
     * Get A MetaRole List consisting ONLY of Maintainable MetaRoles.
     *
     * @param _db
     * @param _oProfile
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public MetaRoleList(Database _db, Profile _oProfile) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_db, _oProfile, false);
    }

    ////////
    // SORT FILTER INFO
    ////////

    /**
     * dumpSFInfo
     *
     * @return
     *  @author David Bigelow
     */
    public String dumpSFInfo() {
        return getSFInfo().dump();
    }

    /**
     * getSFInfo
     *
     * @return
     *  @author David Bigelow
     */
    protected SortFilterInfo getSFInfo() {
        return m_SFInfo;
    }

    /**
     * set FilterType on MetaRoles
     *
     * @param _strFilterType 
     */
    public void setFilterType(String _strFilterType) {
        getSFInfo().setFilterType(_strFilterType);
    }

    /**
     * getFilterType
     *
     * @return
     *  @author David Bigelow
     */
    public String getFilterType() {
        return getSFInfo().getFilterType();
    }

    /**
     * set Filter on MetaRoles
     *
     * @param _strFilter 
     */
    public void setFilter(String _strFilter) {
        getSFInfo().setFilter(_strFilter);
    }

    /**
     * getFilter
     *
     * @return
     *  @author David Bigelow
     */
    public String getFilter() {
        return getSFInfo().getFilter();
    }

    /**
     * reset Filter on MetaRoles
     */
    public void resetFilter() {
        getSFInfo().resetFilter();
        resetDisplayableMetaRoles();
    }

    /**
     * set the sort type
     *
     * @param _s 
     */
    public void setSortType(String _s) {
        getSFInfo().setSortType(_s);
    }

    /**
     * getSortType
     *
     * @return
     *  @author David Bigelow
     */
    public String getSortType() {
        return getSFInfo().getSortType();
    }

    /**
     * setAscending
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setAscending(boolean _b) {
        getSFInfo().setAscending(_b);
    }

    /**
     * isAscending
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isAscending() {
        return getSFInfo().isAscending();
    }

    private void resetDisplayableMetaRoles() {
        for (int i = 0; i < getMetaRoleCount(); i++) {
            getMetaRole(i).setDisplayable(true);
        }
    }

    /**
     * the way we want to do this is:
     *   - retain last sort by until explicitely set
     *   - if the last sort was the same type: if asc -> do desc, and visa-versa
     *   - retain last filter/filter type
     *   - if m_strFilter != null -> apply filter
     *
     * @concurrency $none
     * @param _bUseWildcardsForFilter 
     */
    public synchronized void performSortFilter(boolean _bUseWildcardsForFilter) {
        //first go through and set the displayable 
        //  - note that it is up to the calling party to check for isDisplayable on MetaRoles
        resetDisplayableMetaRoles();
        if (getSFInfo().getFilter() != null) {
            String strFilter = getSFInfo().getFilter();
            for (int i = 0; i < getMetaRoleCount(); i++) {
                String strToCompare = null;
                if (getSFInfo().getFilterType().equals(FILTER_BY_ROLECODE)) {
                    strToCompare = getMetaRoleCode(i);
                
                } else {
                    strToCompare = getMetaRoleDesc(i);
                }
                if (!_bUseWildcardsForFilter) {
                    if (strToCompare.length() < strFilter.length()) {
                        getMetaRole(i).setDisplayable(false);
                    } else if (!strToCompare.substring(0, strFilter.length()).equalsIgnoreCase(strFilter)) {
                        getMetaRole(i).setDisplayable(false);
                    }
                } else {
                    if (!SortFilterInfo.equalsWithWildcards(strFilter, strToCompare, new char[] { '*', '%' }, true)) {
                        getMetaRole(i).setDisplayable(false);
                    }
                }
            }
        }

        //now do sort
        if (getSFInfo().getSortType().equals(MetaRole.SORT_BY_ROLECODE)) {
            sortListByRoleCode(getSFInfo().isAscending());
        
        } else {
            sortListByRoleDesc(getSFInfo().isAscending());
        }
    }

    ////////
    // ACCESSORS
    ////////

    /**
     * getActiveMetaRole
     *
     * @return
     *  @author David Bigelow
     */
    public MetaRole getActiveMetaRole() {
        return m_oActiveRole;
    }

    /**
     * getMetaRoleCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getMetaRoleCount() {
        return getMetaCount();
    }

    /**
     * getMetaRole
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public MetaRole getMetaRole(int _i) {
        return (MetaRole) getMeta(_i);
    }

    /**
     * getMetaRole
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public MetaRole getMetaRole(String _s) {
        return (MetaRole) getMeta(_s);
    }

    /**
     * getMetaRoleCode
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getMetaRoleCode(int _i) {
        MetaRole mr = (MetaRole) getMeta(_i);
        return mr.getRoleCode();
    }

    /**
     * getMetaRoleDesc
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getMetaRoleDesc(int _i) {
        MetaRole mr = (MetaRole) getMeta(_i);
        return mr.getLongDescription();
    }

    /**
     * getMetaRoleDesc
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public String getMetaRoleDesc(String _s) {
        MetaRole mr = (MetaRole) getMeta(_s);
        return mr.getLongDescription();
    }

    /**
     * get a subset of this MetaRoleList only including MetaRoles
     *
     * @param _mr 
     */

    ///////////
    // MUTATORS
    ///////////

    public void setActiveMetaRole(MetaRole _mr) {
        m_oActiveRole = _mr;
        return;
    }

    /**
     * Remove a MetaRole from the list
     *
     * @return MetaRole at the given index
     * @param _mr 
     */
    public MetaRole removeMetaRole(MetaRole _mr) {
        return (MetaRole) removeMeta(_mr);
    }

    /**
     * Add a new MetaRole to the list
     *
     * @param _mr 
     */
    public void putMetaRole(MetaRole _mr) {
        putMeta(_mr);
        alphabetize();
        return;
    }

    ///////////
    // SORT
    ///////////

    /**
     * rearrange the list so that it is sorted alphabetically by rolecode
     *
     * @concurrency $none
     * @param _bAscending 
     */
    protected synchronized void sortListByRoleCode(boolean _bAscending) {
        MetaRole[] aMr = new MetaRole[getMetaRoleCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getMetaRoleCount(); i++) {
            MetaRole mr = getMetaRole(i);
            mr.setSortType(MetaRole.SORT_BY_ROLECODE);
            aMr[i] = mr;
        }
        Arrays.sort(aMr, ec);
        resetMeta();
        for (int i = 0; i < aMr.length; i++) {
            MetaRole mr = aMr[i];
            putMeta(mr);
        }
        return;
    }

    /**
     * rearrange the list so that it is sorted alphabetically by rolecode
     *
     * @concurrency $none
     * @param _bAscending 
     */
    protected synchronized void sortListByRoleDesc(boolean _bAscending) {
        MetaRole[] aMr = new MetaRole[getMetaRoleCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getMetaRoleCount(); i++) {
            MetaRole mr = getMetaRole(i);
            mr.setSortType(MetaRole.SORT_BY_ROLEDESC);
            aMr[i] = mr;
        }
        Arrays.sort(aMr, ec);
        resetMeta();
        for (int i = 0; i < aMr.length; i++) {
            MetaRole mr = aMr[i];
            putMeta(mr);
        }
        return;
    }

    ///////////
    // MISC
    ///////////

    /**
     * Aphabetize the list
     * Do this on initial build & when new Roles are added...
     */
    private void alphabetize() {
        Vector sorted = new Vector();
        int counter = 0;
        int errorCheck = 0;
        while (counter < getMetaRoleCount() && errorCheck < 9999) {
            errorCheck++;
            for (int i = 0; i < getMetaRoleCount(); i++) {
                boolean isLowest = true;
                String s1 = getMetaRole(i).getLongDescription();
                //
                if (!sorted.contains(getMetaRole(i))) {
                    for (int j = 0; j < getMetaRoleCount(); j++) {
                        String s2 = getMetaRole(j).getLongDescription();
                        //
                        if (!sorted.contains(getMetaRole(j))) {
                            if (s2.compareToIgnoreCase(s1) < 0) {
                                isLowest = false;
                                j = getMetaRoleCount(); //break
                            }
                        }
                    }
                    if (isLowest) {
                        sorted.add(getMetaRole(i));
                        counter++;
                    }
                }
            }
        }
        resetMeta();
        for (int i = 0; i < sorted.size(); i++) {
            MetaRole mr = (MetaRole) sorted.elementAt(i);
            putMeta(mr);
        }
        return;
    }

    /**
     * dump contents
     *
     * @return String
     * @param _bBrief 
     */
    public String dump(boolean _bBrief) {
        return new String();
    }

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaRoleList.java,v 1.20 2009/03/20 15:38:06 wendy Exp $";
    }

}
