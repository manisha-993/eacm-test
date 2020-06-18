//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SearchActionItem.java,v $
// Revision 1.110  2014/04/17 18:25:47  wendy
// RCQ 216919 EACM BH - Add New Attributes to Search
//
// Revision 1.109  2013/10/24 17:33:56  wendy
// picklist perf updates
//
// Revision 1.108  2012/09/05 14:34:35  wendy
//  Added verifyReadLanguage()
//
// Revision 1.107  2010/08/25 16:39:57  wendy
// Expose disableUIAfterCache() for ABRUtil usage
//
// Revision 1.106  2009/05/18 23:10:12  wendy
// Support dereference for memory release
//
// Revision 1.105  2009/05/11 14:00:47  wendy
// Support turning off domain check for all actions
//
// Revision 1.104  2009/03/12 14:59:13  wendy
// Add methods for metaui access
//
// Revision 1.103  2008/12/16 19:06:04  wendy
// Check for rs != null before rs.close()
//
// Revision 1.102  2007/08/02 21:01:31  wendy
// RQ0713072645 Enhancement 2
//
// Revision 1.101  2005/08/30 21:00:54  joan
// remove comments made by cvs to compile
//
// Revision 1.100  2005/08/30 17:39:14  dave
// new cat comments
//
// Revision 1.97  2005/07/22 19:47:08  joan
// fixes
//
// Revision 1.96  2005/07/15 16:31:41  joan
// work on filter search on entity
//
// Revision 1.99  2005/08/10 16:14:24  tony
// improved catalog viewer functionality.
//
// Revision 1.98  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.97  2005/07/22 19:47:08  joan
// fixes
//
// Revision 1.96  2005/07/15 16:31:41  joan
// work on filter search on entity
//
// Revision 1.95  2005/03/11 21:54:59  dave
// more jtest cleanup
//
// Revision 1.94  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.93  2004/11/24 18:06:37  gregg
// remove debugs
//
// Revision 1.92  2004/11/23 23:06:21  gregg
// more colorder control on Search ... EntityList this time
//
// Revision 1.91  2004/11/23 18:20:20  gregg
// more for Column Orders on SearchActionItems
//
// Revision 1.90  2004/11/22 23:09:05  gregg
// some exception catching
//
// Revision 1.89  2004/11/22 23:01:06  gregg
// MetaColumnOrderGroup on SearchBinder
//
// Revision 1.88  2004/10/14 23:08:11  dave
// more syntax
//
// Revision 1.87  2004/10/14 22:34:15  dave
// trying to get enforced Domain in search
//
// Revision 1.86  2004/10/11 23:21:08  dave
// removed trace
//
// Revision 1.85  2004/10/11 22:09:17  dave
// syntax
//
// Revision 1.84  2004/10/11 22:04:57  dave
// here is the search action item more trace
//
// Revision 1.83  2004/08/18 00:02:30  joan
// work on search
//
// Revision 1.82  2004/08/17 22:43:31  joan
// work on search
//
// Revision 1.81  2004/07/30 22:46:46  joan
// work on use Root EI
//
// Revision 1.80  2004/07/30 16:05:39  joan
// work on use Root EI
//
// Revision 1.79  2004/07/15 17:28:59  joan
// add Single input
//
// Revision 1.78  2004/06/24 17:57:16  joan
// add ParentLess for UI
//
// Revision 1.77  2004/04/21 19:49:38  gregg
// m_iGrabEntityID = _ai.getGrabEntityID();
//
// Revision 1.76  2004/03/31 23:59:26  gregg
// set/getGrabEntityID
//
// Revision 1.75  2004/03/31 23:44:32  gregg
// isGrabByEntityID
//
// Revision 1.74  2003/10/30 02:35:04  dave
// another null pointer fix
//
// Revision 1.73  2003/10/30 01:22:52  dave
// double defined
//
// Revision 1.72  2003/10/30 01:09:08  dave
// fix for nul nul
//
// Revision 1.71  2003/10/30 00:56:17  dave
// more profile fixes
//
// Revision 1.70  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.69  2003/10/16 20:39:13  joan
// try to make PDG run faster
//
// Revision 1.68  2003/10/09 21:10:34  dave
// lifting search restriction
//
// Revision 1.67  2003/09/24 16:50:41  joan
// fixes for dynamic search domain
//
// Revision 1.66  2003/08/25 21:52:39  joan
// add SearchStringMin
//
// Revision 1.65  2003/08/25 20:57:35  dave
// clean up on remote di
//
// Revision 1.64  2003/08/25 20:22:32  dave
// Some cleanup on streamlining searchactionitem
//
// Revision 1.63  2003/08/25 20:06:47  dave
// streamlining the SearchActionItem to not
// carry the full entityGroup upon creation, but to
// go get it .. when the search action item is actually invoked
// with an exec or rexec
//
// Revision 1.62  2003/08/21 23:49:59  joan
// work on general search
//
// Revision 1.61  2003/06/25 22:53:51  dave
// minor changes
//
// Revision 1.60  2003/06/24 23:47:54  dave
// Translation part I
//
// Revision 1.59  2003/06/06 20:20:34  joan
// move changes from v111
//
// Revision 1.58  2003/06/06 00:04:20  joan
// move changes from v111
//
// Revision 1.57  2003/05/11 02:54:28  dave
// more clarity on doesHaveParents
//
// Revision 1.56  2003/05/11 02:44:19  dave
// trying to make sense of setting parent info...
//
// Revision 1.55  2003/05/11 02:30:57  dave
// removing the parent set on the search action item
// in dynasearch (is not setting correctly.. do we need?)
//
// Revision 1.54  2003/05/11 02:16:37  dave
// more trace
//
// Revision 1.53  2003/05/10 05:33:31  dave
// optional likes processing
//
// Revision 1.52  2003/05/09 22:56:45  dave
// syntax
//
// Revision 1.51  2003/05/09 22:49:46  dave
// more segmenting the UI function so back end function
// is not burdon'ed with un needed objects
//
// Revision 1.50  2003/05/09 22:40:13  dave
// minor syntax errors
//
// Revision 1.49  2003/05/09 21:18:01  dave
// introducing the concept of turning off not needed things in
// action item execution
//
// Revision 1.48  2003/04/12 22:31:41  dave
// clean up and reformatting.
// Search Lite weight II
//
// Revision 1.47  2003/04/08 02:58:36  dave
// commit()
//
// Revision 1.46  2003/04/04 00:12:37  joan
// put the match entity in m_el in setParentEntityItems
//
// Revision 1.45  2003/03/10 17:18:01  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.44  2003/02/06 23:05:44  dave
// changed search length from 4 to 3
//
// Revision 1.43  2003/02/06 21:14:38  joan
// adjust message
//
// Revision 1.42  2002/12/27 21:20:16  gregg
// made isDomainControlled method public
//
// Revision 1.41  2002/12/20 19:19:46  dave
// simple auto flag filter on dyna search
//
// Revision 1.40  2002/12/13 22:30:32  dave
// adding domain controlled search
//
// Revision 1.39  2002/11/13 22:07:24  dave
// more f ixes
//
// Revision 1.38  2002/11/13 21:57:50  dave
// putting wrong parent in list
//
// Revision 1.37  2002/11/13 21:13:25  dave
// syntax fixes
//
// Revision 1.36  2002/11/13 21:04:31  dave
// need to select the right parent for the searchActionItem
// its the entitytype = of the ActionGroup tied to the
// SearchActionItem
//
// Revision 1.35  2002/11/04 21:06:12  dave
// syntax
//
// Revision 1.34  2002/11/04 20:50:58  dave
// closing hole is SearchActionItem dyna form
//
// Revision 1.33  2002/10/28 21:09:27  dave
// string bounds exception error fixed
//
// Revision 1.32  2002/10/28 20:48:17  dave
// misc syntax
//
// Revision 1.31  2002/10/28 20:39:44  dave
// Feedback 22529.  Changed column title of searchBinder
// and tried to remove the asterek from the get when
// we are in DynaTable mode
//
// Revision 1.30  2002/10/10 20:57:54  dave
// syntax fix
//
// Revision 1.29  2002/10/10 20:48:30  dave
// minor mods for SearchActionItem and DynaSearch
//
// Revision 1.28  2002/10/10 19:09:44  dave
// fixed some errors
//
// Revision 1.27  2002/10/10 18:54:45  dave
// Class Cast exception fix + isPickList
//
// Revision 1.26  2002/10/10 18:12:48  dave
// Wave fixes II
//
// Revision 1.25  2002/10/10 18:03:48  dave
// Syntax fixes wave i
//
// Revision 1.24  2002/10/10 17:13:56  dave
// Localizing parent info on Search Action Item
//
// Revision 1.23  2002/10/10 17:00:28  dave
// Final Search II changes
//
// Revision 1.22  2002/10/09 17:00:53  dave
// Dyna Search II fixes
//
// Revision 1.21  2002/10/09 15:50:53  dave
// sintax
//
// Revision 1.20  2002/10/09 15:41:26  dave
// sintax
//
// Revision 1.19  2002/10/09 15:33:26  dave
// DynaSearch II
//
// Revision 1.18  2002/10/08 19:16:42  dave
// adding isFormEnabled
//
// Revision 1.17  2002/10/08 18:18:35  dave
// needed additional imports
//
// Revision 1.16  2002/10/08 18:05:06  dave
// more fixes hopefully
//
// Revision 1.15  2002/10/08 17:41:55  dave
// hopefully another fix
//
// Revision 1.14  2002/10/08 16:58:50  dave
// compile fixes
//
// Revision 1.13  2002/10/08 16:20:53  dave
// putting in the backend stub for DynaSearch
//
// Revision 1.12  2002/09/27 22:14:31  dave
// syntax fix
//
// Revision 1.11  2002/09/27 21:40:03  dave
// Finishing off dyna search user stub
//
// Revision 1.10  2002/09/27 20:53:24  dave
// syntax fix
//
// Revision 1.9  2002/09/27 20:35:46  dave
// new DynaSearch Function Stub
//
// Revision 1.8  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.7  2002/08/23 21:29:44  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.6  2002/08/12 20:31:26  joan
// add code for get all attributes
//
// Revision 1.5  2002/08/12 18:16:06  joan
// add code to pull all attributes in SearchActionItem
//
// Revision 1.4  2002/03/05 23:30:21  dave
// syntax fixes
//
// Revision 1.3  2002/03/05 23:21:32  dave
// added SearchString prop to SearchAPI
//
// Revision 1.2  2002/03/05 23:15:41  dave
// SearchAPI fixes 1
//
// Revision 1.1  2002/03/05 23:08:34  dave
// first pass at the new Search Action Item function for EntityList
//
// Revision 1.16  2002/03/05 21:13:20  dave
// nullpointer fix
//
// Revision 1.15  2002/03/05 21:02:48  dave
// missing rdrs def
//
// Revision 1.14  2002/03/05 20:43:00  dave
// missing }
//
// Revision 1.13  2002/03/05 19:25:57  dave
// adding MetaLink to NavActionItem in case of the Picklist
// is used so we single out the sole relator that can be used
// when pulling things off the picklist
//
// Revision 1.12  2002/03/01 23:23:15  dave
// column number wrong for pulling 7030 in ActionItem objects
//
// Revision 1.11  2002/03/01 22:50:56  dave
// fix to ensure characteristics on getNavItem constructur for clone
// is pulling all the other information
//
// Revision 1.10  2002/03/01 22:03:34  dave
// more NAVActionItem fixing
//
// Revision 1.9  2002/03/01 19:21:58  dave
// syntax for action item changes
//
// Revision 1.8  2002/03/01 19:00:53  dave
// merged conflicts
//
// Revision 1.7  2002/02/12 23:35:37  dave
// added purpose to the NavActionItem
//
// Revision 1.6  2002/02/12 19:18:38  dave
// extra debugging to isolate Class problem for action item
//
// Revision 1.5  2002/02/12 18:51:44  dave
// more changes to dump
//
// Revision 1.4  2002/02/12 18:10:23  dave
// more changes
//
// Revision 1.3  2002/02/11 18:55:04  dave
// more dump rearranges
//
// Revision 1.2  2002/02/09 21:50:41  dave
// more syntax
//
// Revision 1.1  2002/02/09 20:48:25  dave
// re-arrannging the ActionItem to abstract a common base
//
//

package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.StringTokenizer;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.transactions.NLSItem;
import COM.ibm.opicmpdh.transactions.OPICMList;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
/**
 *  Description of the Class
 *
 * @author     davidbig
 * @created    April 11, 2003
 */
public class SearchActionItem extends EANActionItem {

    final static long serialVersionUID = 20011106L;

    // The search Target is this EntityGroup
    private String m_strEntityGroupKey = null;
    private String m_strSearch = null;
    private String m_strPurpose = "Navigate";

    private boolean m_bPullAttributes = false;
    private boolean m_bDynaSearch = false;
    private String m_strDynaForm = null;
    private boolean m_bFormEnabled = false;
    private EntityGroup m_eg = null;
    /**
     * FIELD
     */
    protected EntityGroup m_eg1 = null;
    /**
     * FIELD
     */
    protected EntityGroup m_eg2 = null;
    private boolean m_bPicklist = false;
    private EANList m_el = new EANList();
    private String m_strParentEntityType = "";
    private boolean m_bDomainControlled = false;
    /**
     * FIELD
     */
    protected boolean m_bWorkflow = true;
    /**
     * FIELD
     */
    protected boolean m_bUI = true;
    /**
     * FIELD
     */
    protected boolean m_bLikeMatching = false;

    /**
     * FIELD
     */
    public static final int SEARCHLIMIT = 2500;
    /**
     * FIELD
     */
    protected boolean m_bCheckLimit = true;
    /**
     * FIELD
     */
    protected boolean m_bQueueSearch = false;
    /**
     * FIELD
     */
    protected boolean m_bParentLess = false;

    /**
     * FIELD
     */
    protected String m_strQueueSearch = "";
    /**
     * FIELD
     */
    protected String m_strDomainAttrCode = "";
    protected String m_strWGDomainAttrCode = ""; // RQ0713072645

    private SearchRequest m_sr = null;
    /**
     * FIELD
     */
    protected boolean m_bUseSearchRequest = true;
    private int m_iSearchStringMin = 4;

    private boolean m_bGrabByEntityID = false;
    private int m_iGrabEntityID = -1;

    private boolean m_bGrabRelator = false;
    private boolean m_bUseRelator = false; //RCQ216919 use relator attributes in search too

    private boolean m_bEnforceWGDomain = true;
    /**
     * FIELD
     */
    protected OPICMList m_showRelParentChild = new OPICMList();

    /**
     * FIELD
     */
    protected OPICMList m_filterSearch = new OPICMList();

    /**
     *  Description of the Field
     */
    protected Vector m_vctAutoFlagFilter = new Vector();
    private MetaColumnOrderGroup mcog = null; // hold for reuse to improve perf

    public void dereference(){
    	super.dereference();
    	
    	mcog = null; 
    	
       	if (m_el !=null){
    		/*these may be shared
    		 * for (int i=0; i<m_el.size(); i++){
    			EntityItem mt = (EntityItem) m_el.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}*/
    		m_el.clear();
    		m_el = null;
    	}
       	m_strEntityGroupKey = null;
        m_strSearch = null;
        m_strPurpose = null;

        m_strDynaForm = null;
        
        if (m_eg != null){
        	m_eg.dereference();
        	m_eg = null;
        }      
        if (m_eg1 != null){
        	m_eg1.dereference();
        	m_eg1 = null;
        }
        if (m_eg2 != null){
        	m_eg2.dereference();
        	m_eg2 = null;
        }
        
        m_strParentEntityType = null;
        m_strQueueSearch = null;
        m_strDomainAttrCode = null;
        m_strWGDomainAttrCode = null;
        
        if (m_showRelParentChild!= null){
        	m_showRelParentChild.clear();
        	m_showRelParentChild = null;
        }
        if (m_filterSearch!= null){
        	m_filterSearch.clear();
        	m_filterSearch = null;
        }
        if (m_sr!= null){
        	m_sr.dereference();
        	m_sr = null;
        }
       
        if (m_vctAutoFlagFilter!= null){
        	m_vctAutoFlagFilter.clear();
        	m_vctAutoFlagFilter = null;
        }      	
    }
    
    
    /**
     * Main method which performs a simple test of this class
     *
     * @param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     *  Gets the version attribute of the SearchActionItem object
     *
     * @return    The version value
     */
    public String getVersion() {
        return "$Id: SearchActionItem.java,v 1.110 2014/04/17 18:25:47 wendy Exp $";
    }

    /**
     *Constructor for the SearchActionItem object
     *
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public SearchActionItem(SearchActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setTargetEntityGroup(_ai.getTargetEntityGroup());
        setSearchString(_ai.getSearchString());
        setDynaSearch(_ai.isDynaSearchEnabled());
        setDynaForm(_ai.getDynaForm());
        setEntityGroup(_ai.getEntityGroup());
        setParentEntityItems(_ai.getParentEntityItems());
        setParentEntityType(_ai.getParentEntityType());
        setPicklist(_ai.isPicklist());
        setPullAttributes(_ai.pullAttributes());
        setDomainControlled(_ai.isDomainControlled());
        // Dynamic Filter
        m_vctAutoFlagFilter = _ai.m_vctAutoFlagFilter;
        m_bWorkflow = _ai.m_bWorkflow;
        m_bUI = _ai.m_bUI;
        m_bLikeMatching = _ai.m_bLikeMatching;
        setCheckLimit(_ai.checkLimit());
        m_bQueueSearch = _ai.m_bQueueSearch;
        m_strQueueSearch = _ai.m_strQueueSearch;
        m_strDomainAttrCode = _ai.m_strDomainAttrCode;
        m_strWGDomainAttrCode = _ai.m_strWGDomainAttrCode;  //RQ0713072645
        setSearchRequest(_ai.getSearchRequest());
        m_bUseSearchRequest = _ai.m_bUseSearchRequest;
        setSearchStringMin(_ai.getSearchStringMin());
        m_bGrabByEntityID = _ai.isGrabByEntityID();
        m_iGrabEntityID = _ai.getGrabEntityID();
        m_bParentLess = _ai.m_bParentLess;
        m_bGrabRelator = _ai.m_bGrabRelator;
        m_bUseRelator = _ai.m_bUseRelator; //RCQ216919
        m_eg1 = _ai.m_eg1;
        m_eg2 = _ai.m_eg2;
        mcog= _ai.mcog;
        m_showRelParentChild = _ai.m_showRelParentChild;
        m_filterSearch = _ai.m_filterSearch;

    }

    /**
     *Constructor for the SearchActionItem object
     *
     * @param  _mf                             Description of the Parameter
     * @param  _ai                             Description of the Parameter
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public SearchActionItem(EANMetaFoundation _mf, SearchActionItem _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
        setTargetEntityGroup(_ai.getTargetEntityGroup());
        setSearchString(_ai.getSearchString());
        setDynaSearch(_ai.isDynaSearchEnabled());
        setDynaForm(_ai.getDynaForm());
        setEntityGroup(_ai.getEntityGroup());
        setParentEntityItems(_ai.getParentEntityItems());
        setParentEntityType(_ai.getParentEntityType());
        setPicklist(_ai.isPicklist());
        setPullAttributes(_ai.pullAttributes());
        setDomainControlled(_ai.isDomainControlled());
        // Dynamic Filter
        m_vctAutoFlagFilter = _ai.m_vctAutoFlagFilter;
        m_bWorkflow = _ai.m_bWorkflow;
        m_bUI = _ai.m_bUI;
        m_bLikeMatching = _ai.m_bLikeMatching;
        setCheckLimit(_ai.checkLimit());
        m_bQueueSearch = _ai.m_bQueueSearch;
        m_strQueueSearch = _ai.m_strQueueSearch;
        m_strDomainAttrCode = _ai.m_strDomainAttrCode;
        m_strWGDomainAttrCode = _ai.m_strWGDomainAttrCode;  //RQ0713072645
        setSearchRequest(_ai.getSearchRequest());
        m_bUseSearchRequest = _ai.m_bUseSearchRequest;
        setSearchStringMin(_ai.getSearchStringMin());
        m_bGrabByEntityID = _ai.isGrabByEntityID();
        m_iGrabEntityID = _ai.getGrabEntityID();
        m_bParentLess = _ai.m_bParentLess;
        m_bGrabRelator = _ai.m_bGrabRelator;
        m_bUseRelator = _ai.m_bUseRelator; //RCQ216919
        m_eg1 = _ai.m_eg1;
        m_eg2 = _ai.m_eg2;
        mcog= _ai.mcog;
        m_showRelParentChild = _ai.m_showRelParentChild;
        m_filterSearch = _ai.m_filterSearch;
    }

    /**
     * This represents a Search Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public SearchActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        this(_emf, _db, _prof, _strActionItemKey, true);
    }
    /**
     * This represents a Search Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param  _emf                            Description of the Parameter
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @param  _strActionItemKey               Description of the Parameter
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @param _bUI
     */
    public SearchActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey, boolean _bUI) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        try {
        	setDomainCheck(true);
            Profile prof = getProfile();
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;

            m_sr = new SearchRequest(prof, _strActionItemKey);

            // Lets start out by assigning that was this created for UI use..?

            m_bUI = _bUI;

            // Retrieve the Action Class and the Action Description.

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
            	if (rs != null){
            		rs.close();
            		rs = null;
            	}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            //LINKTYPE:	 Attribute/Attribute
     		//LINKTYPE1: SRDPRODSTRUCT33
     		//LINKTYPE2: TYPE
     		//LINKCODE:	 GrabRelator
     		//LINKVALUE: PRODSTRUCT - not used here
            //LINKTYPE:	 Attribute/Attribute
			//LINKTYPE1: _strActionItemKey
			//LINKTYPE2: TYPE
			//LINKCODE:	 UseRelatorAttr
			//LINKVALUE: PRODSTRUCT - not used

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0); //LINKTYPE2
                String strCode = rdrs.getColumn(ii, 1); //LINKCODE
                String strValue = rdrs.getColumn(ii, 2);//LINKVALUE
                _db.debug(D.EBUG_DETAIL, "gbl7030 answer is:" + strType + "|" + strCode + "|" + strValue + "|");
                if (strType.equals("TYPE") && strCode.equals("Search")) {
                    // This pinpoints the entity that we are interested in as the displayable answer
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Picklist")) {
                    setPicklist(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Attributes")) {
                    setPullAttributes(true);
                    m_strPurpose = "Edit";
                } else if (strType.equals("TYPE") && strCode.equals("LikeEnabled")) {
                    enableLikeMatching();
                } else if (strType.equals("TYPE") && strCode.equals("DynaSearch")) {
                    //  setPicklist(true);
                    setDynaSearch(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Form")) {
                    setDynaForm(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Domain")) {
                    setDomainControlled(true);
                    setDomainAttrCode(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("QueueSearch")) {
                    setQueueSearch(true);
                    setQueueSearchName(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("ParentLess")) {
                    setParentLess(true);
                } else if (strType.equals("TYPE") && strCode.equals("RootEI")) {
                    setRootEI(true);
                    setRootEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SearchStringMin")) {
                    setSearchStringMin(Integer.parseInt(strValue));
                } else if (strType.equals("FLAGFILTER")) {
                    m_vctAutoFlagFilter.add(strCode + ":" + strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("GrabByEntityID")) {
                    m_bGrabByEntityID = true;
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
                } else if (strType.equals("TYPE") && strCode.equals("GrabRelator")) {
                    m_bGrabRelator = true;
                } else if (strType.equals("TYPE") && strCode.equalsIgnoreCase("UseRelatorAttr")) {
                	m_bUseRelator = true; //RCQ216919
                } else if (strType.equals("TYPE") && strCode.equals("ColumnOrderControl")) {
                    setMetaColumnOrderControl(true);
                } else if (strType.equals("TYPE") && strCode.equals("ShowRelParentChild")) {
                    m_showRelParentChild.put(strValue, strValue);
                } else if (strType.equals("FILTERENTITY")) {
                    m_filterSearch.put(strCode, strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
				    setDomainCheck(!strValue.equals("N")); //RQ0713072645
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }

            // pull out search target if general search
            if (!isDynaSearchEnabled()) {
                rs = _db.callGBL8010(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
                for (int ii = 0; ii < rdrs.size(); ii++) {
                    String strEntityType = rdrs.getColumn(ii, 0).trim();
                    String strETDesc = rdrs.getColumn(ii, 1).trim();
                    String strAttributeCode = rdrs.getColumn(ii, 2).trim();
                    String strAttrDesc = rdrs.getColumn(ii, 3).trim();

                    SearchTarget st = m_sr.getSearchTarget(strEntityType + strAttributeCode);

                    _db.debug(D.EBUG_SPEW, "gbl8010 answer is:" + strEntityType + ":" + strETDesc + ":" + strAttributeCode + ":" + strAttrDesc);

                    if (st == null) {
                        st = new SearchTarget(null, prof, strEntityType, strAttributeCode);
                        st.setETDescription(strETDesc);
                        st.setAttrDescription(strAttrDesc);
                    }
                    m_sr.putSearchTarget(st);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     *  Description of the Method
     *
     * @param  _bBrief  Description of the Parameter
     * @return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("SearchActionItem:" + super.dump(_bBrief));
        strbResult.append(":EGTarget:" + getTargetEntityGroup());
        strbResult.append(":DynaSearch?:" + isDynaSearchEnabled());
        strbResult.append(":DynaForm?:" + getDynaForm());
        return strbResult.toString();
    }

    /**
    *  Gets the purpose attribute of the SearchActionItem object
    *
    * @return    The purpose value
    */
    public String getPurpose() {
        return m_strPurpose;
    }

    /**
     *  Sets the targetEntityGroup attribute of the SearchActionItem object
     *
     * @param  _str  The new targetEntityGroup value
     */
    protected void setTargetEntityGroup(String _str) {
        m_strEntityGroupKey = _str;
    }

    /**
     *  Gets the targetEntityGroup attribute of the SearchActionItem object
     *
     * @return    The targetEntityGroup value
     */
    public String getTargetEntityGroup() {
        return m_strEntityGroupKey;
    }

    /**
     *  Sets the searchString attribute of the SearchActionItem object
     *
     * @param  _str                            The new searchString value
     * @exception  MiddlewareRequestException  Description of the Exception
     */
    public void setSearchString(String _str) throws MiddlewareRequestException {
        if (_str == null) {
            return;
        }
        T.est(_str.length() > 2, "Search String is too small .. needs to be at least 3 characters.");
        m_strSearch = _str;
    }

    /**
     *  Gets the searchString attribute of the SearchActionItem object
     *
     * @return    The searchString value
     */
    public String getSearchString() {
        return m_strSearch;
    }

    /*********
     * meta ui must update action
     */
    public void setActionClass(){
    	setActionClass("Search");
    }
    public void updateAction(boolean bPullAtts){
        setPullAttributes(bPullAtts);
    }
    /**
     *  Sets the pullAttributes attribute of the SearchActionItem object
     *
     * @param  _b  The new pullAttributes value
     */
    protected void setPullAttributes(boolean _b) {
        m_bPullAttributes = _b;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean pullAttributes() {
        return m_bPullAttributes;
    }

    /**
     *  Sets the dynaSearch attribute of the SearchActionItem object
     *
     * @param  _b  The new dynaSearch value
     */
    protected void setDynaSearch(boolean _b) {
        m_bDynaSearch = _b;
    }

    /**
     *  Gets the dynaSearchEnabled attribute of the SearchActionItem object
     *
     * @return    The dynaSearchEnabled value
     */
    public boolean isDynaSearchEnabled() {
        return m_bDynaSearch;
    }

    /**
     *  Sets the dynaForm attribute of the SearchActionItem object
     *
     * @param  _str  The new dynaForm value
     */
    public void setDynaForm(String _str) {
        m_strDynaForm = _str;
        m_bFormEnabled = true;
    }

    /**
     *  Gets the dynaForm attribute of the SearchActionItem object
     *
     * @return    The dynaForm value
     */
    public String getDynaForm() {
        return m_strDynaForm;
    }

    /**
     *  Gets the formEnabled attribute of the SearchActionItem object
     *
     * @return    The formEnabled value
     */
    public boolean isFormEnabled() {
        return m_bFormEnabled;
    }

    /**
     *  Sets the checkLimit attribute of the SearchActionItem object
     *
     * @param  _b  The new checkLimit value
     */
    public void setCheckLimit(boolean _b) {
        m_bCheckLimit = _b;
    }

    /**
     *  Description of the Method
     *
     * @return    Description of the Return Value
     */
    public boolean checkLimit() {
        return m_bCheckLimit;
    }

    /**
     * setUseSearchRequest
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setUseSearchRequest(boolean _b) {
        m_bUseSearchRequest = _b;
    }

    /**
     * useSearchRequest
     *
     * @return
     *  @author David Bigelow
     */
    public boolean useSearchRequest() {
        return m_bUseSearchRequest;
    }

    /**
     *  Sets the entityGroup attribute of the SearchActionItem object
     *
     * @param  _eg  The new entityGroup value
     */
    private void setEntityGroup(EntityGroup _eg) {
        m_eg = _eg;
    }

    /**
     *  Gets the entityGroup attribute of the SearchActionItem object
     *
     * @return    The entityGroup value
     */
    public EntityGroup getEntityGroup() {
        return m_eg;
    }

    /**
     * getEntityGroup1
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup1() {
        return m_eg1;
    }

    /**
     * getEntityGroup2
     *
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup2() {
        return m_eg2;
    }
    
    /**
     * if dynasearch is used and the m_eg group was built with a different nls, it must be switched
     * @param nls
     */
    public void verifyReadLanguage(NLSItem nls){
    	if(m_eg!=null){
    		// force code to pull it again after changing the nls
    		if(!m_eg.getProfile().getReadLanguage().equals(nls)){
    			getProfile().setReadLanguage(nls);
    			m_eg.dereference();
    			m_eg = null;
    		}
    	}
    }

    /**
     * getEntityGroup
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
        if (m_eg != null) {
            return m_eg;
        }
        EntityList el = new EntityList(getProfile());
        // O.K.  Lets go get it from the database
        el.setParentActionItem(this);
        setEntityGroup(new EntityGroup(el, _db, getProfile(), m_strEntityGroupKey, "Search", m_bUI));
        el.putEntityGroup(m_eg);
        if (enforceWGDomain()) {
            el.enforceWorkGroupDomain(_db, getProfile());
        }
        if (!isGrabRelator()) {
            getEntityGroup().setUsedInSearch(true);
            getEntityGroup().addRow();
        } 
        return m_eg;

    }

    /**
     * getEntityGroup
     *
     * @param _rdi
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public EntityGroup getEntityGroup(RemoteDatabaseInterface _rdi) throws RemoteException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        if (m_eg != null) {
            return m_eg;
        }
        EntityList el = new EntityList(getProfile());

        // O.K.  Lets go get it from the database
        el.setParentActionItem(this);
        setEntityGroup(new EntityGroup(el, _rdi, getProfile(), m_strEntityGroupKey, "Search"));
        el.putEntityGroup(m_eg);
        if (enforceWGDomain()) {
            el.enforceWorkGroupDomain(_rdi, getProfile());
        }
        if (!isGrabRelator()) {
            getEntityGroup().setUsedInSearch(true);
            getEntityGroup().addRow();
        }
        return m_eg;
    }

    /**
     *  Gets the picklist attribute of the SearchActionItem object
     *
     * @return    The picklist value
     */
    public boolean isPicklist() {
        return m_bPicklist;
    }

    /**
     *  Sets the picklist attribute of the SearchActionItem object
     *
     * @param  _b  The new picklist value
     */
    protected void setPicklist(boolean _b) {
        m_bPicklist = _b;
    }

    /**
     * performance to get the DynaSearchTable is significantly faster if done on the server but
     * the entitygroups are not set if done there using rmi
     * must get them now
     * @param rst
     * @param el
     */
    private void setEntityGroups(RowSelectableTable rst, EntityList el){
    	if(rst !=null && rst.getEANTableWrapper() instanceof SearchBinder){
    		SearchBinder sb = (SearchBinder)rst.getEANTableWrapper();
    		this.m_eg1 = sb.getEntityGroup1();
    		this.m_eg2 = sb.getEntityGroup2();
    		if(m_eg1!=null){
    			el.putEntityGroup(m_eg1);
    		}
    		if(m_eg2!=null){
    			el.putEntityGroup(m_eg2);
    		}


    		if(this.isUseRelatorAttr()){
    			//RCQ216919
    			//must get the relator entitygroup and replace it
    			EntityGroup sbeg = sb.getEntityGroup(); 
    			el.removeEntityGroup(m_eg);
    			m_eg = sbeg;
    			el.putEntityGroup(m_eg);
    		}

    		mcog = sb.getMetaColumnOrderGroup();
    	}
    }
    /**
     * getDynaSearchTable
     *
     * @param _db
     * @return
     *  @author David Bigelow
     */
    public RowSelectableTable getDynaSearchTable(Database _db) {
        try {
            EntityGroup eg = getEntityGroup(_db);
            EntityList el = eg.getEntityList();
            if (el == null) {
                el = new EntityList(getProfile());
                el.setParentActionItem(this);
                el.putEntityGroup(eg);
            }

            if (isGrabRelator() && eg.isRelator()) {
                SearchBinder searchBinder = null;
                m_eg1 = new EntityGroup(el, _db, getProfile(), eg.getEntity1Type(), "Search");
                m_eg1.setUsedInSearch(true);
                el.putEntityGroup(m_eg1);
                m_eg2 = new EntityGroup(el, _db, getProfile(), eg.getEntity2Type(), "Search");
                m_eg2.setUsedInSearch(true);
                el.putEntityGroup(m_eg2);
                if (enforceWGDomain()) {
                    el.enforceWorkGroupDomain(_db, getProfile());
                }
                m_eg1.addRow();
                m_eg2.addRow();

                if(this.isUseRelatorAttr()){
                	//RCQ216919
                	if(eg.getEntityItemCount()==0){
                		eg.setUsedInSearch(true);
                		eg.addRow();
                	}

                	searchBinder = new SearchBinder(eg, m_eg1, m_eg2);	
                }else{
                    searchBinder = new SearchBinder(m_eg1, m_eg2);	
                }
 
                if (isMetaColumnOrderControl()) {
                    buildActionItemColumnOrders(_db, null, searchBinder);
                }
                return new RowSelectableTable(searchBinder, getLongDescription());
            } else {
                SearchBinder searchBinder = new SearchBinder(eg);
                if (isMetaColumnOrderControl()) {
                    buildActionItemColumnOrders(_db, null, searchBinder);
                }
                return new RowSelectableTable(searchBinder, getLongDescription());
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }

    /**
     * getDynaSearchTable
     *
     * @param _rdi
     * @return
     */
    public RowSelectableTable getDynaSearchTable(RemoteDatabaseInterface _rdi) {
    	RowSelectableTable rst = null;
    	try {
    		EntityGroup eg = getEntityGroup(_rdi);
    		EntityList el = eg.getEntityList();
    		if (el == null) {
    			el = new EntityList(getProfile());
    			el.setParentActionItem(this);
    			el.putEntityGroup(eg);
    		}

    		if (isGrabRelator() && eg.isRelator()) {
    			if(m_eg1!=null && m_eg2 !=null){
    				SearchBinder searchBinder = null;
    				// reuse them   				
                    if(this.isUseRelatorAttr()){
                       	//RCQ216919
                    	searchBinder = new SearchBinder(eg, m_eg1, m_eg2);	
                    }else{
                        searchBinder = new SearchBinder(m_eg1, m_eg2);	
                    }
    				if (isMetaColumnOrderControl()) {
    					buildActionItemColumnOrders(null, _rdi, searchBinder);
    				}

    				rst = new RowSelectableTable(searchBinder, getLongDescription());
    			}else{
    				//clip for rmi
    				m_eg1=null;
    				m_eg2=null;

    				OPICMList m_showRelParentChildtmp = m_showRelParentChild;
    				m_showRelParentChild = null;
    				OPICMList m_filterSearchtmp = m_filterSearch;
    				m_filterSearch = null;
    				Vector m_vctAutoFlagFiltertmp = m_vctAutoFlagFilter;
    				m_vctAutoFlagFilter = null;
    				EANList m_eltmp = m_el;
    				m_el = null;

    				// significantly improve perf by generating this on the server
    				rst = _rdi.getDynaSearchTable(this);

    				// restore values clipped for rmi and set entitygroups that were created on the server
    				m_el = m_eltmp;
    				setEntityGroups(rst,el);
    				m_showRelParentChild = m_showRelParentChildtmp;
    				m_filterSearch = m_filterSearchtmp;
    				m_vctAutoFlagFilter = m_vctAutoFlagFiltertmp;
    			}
    		}else{
    			SearchBinder searchBinder = new SearchBinder(eg);
    			if (isMetaColumnOrderControl()) {
    				buildActionItemColumnOrders(null, _rdi, searchBinder);
    			}
    			rst = new RowSelectableTable(searchBinder, getLongDescription());
    		}
    	} catch (Exception x) {
    		x.printStackTrace();
    	}

    	return rst;
    }
    /*
    public RowSelectableTable getDynaSearchTableOrig(RemoteDatabaseInterface _rdi) {
        try {
            EntityGroup eg = getEntityGroup(_rdi);
            EntityList el = eg.getEntityList();
            if (el == null) {
                el = new EntityList(getProfile());
                el.setParentActionItem(this);
                el.putEntityGroup(eg);
            }
            if (isGrabRelator() && eg.isRelator()) {

                SearchBinder searchBinder = null;

                m_eg1 = new EntityGroup(el, _rdi, getProfile(), eg.getEntity1Type(), "Search");
                el.putEntityGroup(m_eg1);
                m_eg1.setUsedInSearch(true);
                m_eg2 = new EntityGroup(el, _rdi, getProfile(), eg.getEntity2Type(), "Search");
                el.putEntityGroup(m_eg1);
                m_eg2.setUsedInSearch(true);
                if (enforceWGDomain()) {
                    el.enforceWorkGroupDomain(_rdi, getProfile());
                }
                m_eg1.addRow();
                m_eg2.addRow();

                searchBinder = new SearchBinder(m_eg1, m_eg2);

                if (isMetaColumnOrderControl()) {
                    buildActionItemColumnOrders(null, _rdi, searchBinder);
                }
                return new RowSelectableTable(searchBinder, getLongDescription());
            } else {
                SearchBinder searchBinder = new SearchBinder(eg);
                if (isMetaColumnOrderControl()) {
                    buildActionItemColumnOrders(null, _rdi, searchBinder);
                }
                return new RowSelectableTable(searchBinder, getLongDescription());
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }*/

    private final void buildActionItemColumnOrders(Database _db, RemoteDatabaseInterface _rdi, SearchBinder _searchBinder) {
    	try {
        	if( mcog == null){
        		if (_db != null) {
        			mcog = new MetaColumnOrderGroup(_db, _searchBinder);
        		} else {
        			mcog = _rdi.getMetaColumnOrderGroup(_searchBinder);
        		}
        	}
          //  if (mcog.getMetaColumnOrderItemCount() > 0) { set it anyway - avoid another call
                D.ebug(D.EBUG_SPEW, "SearchAction.buildActionItemColumnOrders setting MetaColumnOrderGroup on SearchBinder for actionItem:" + getKey());
                _searchBinder.setMetaColumnOrderGroup(mcog);
          //  }
        } catch (Exception exc) {
            String strMessage = "SearchAction.buildActionItemColumnOrders ERROR:" + exc.toString();
            if (_db != null) {
                _db.debug(D.EBUG_ERR, strMessage);
            } else {
                System.err.println(strMessage);
            }
            exc.printStackTrace(System.err);
        }
        //System.out.println("GAB searchActionItem: searchBinder.hasMetaColumnOrderGroup()?" + _searchBinder.hasMetaColumnOrderGroup());
    }

    /**
     * updatePdhMeta
     * @param  _db                      Description of the Parameter
     * @param  _bExpire                 Description of the Parameter
     * @return                          true if successful, false if nothing to update or unsuccessful
     * @exception  MiddlewareException  Description of the Exception
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        return true;
    }

    /**
     * Here is the execute area for the remote
     *
     * @param  _rdi                                       Description of the Parameter
     * @param  _prof                                      Description of the Parameter
     * @return                                            Description of the Return Value
     * @exception  SQLException                           Description of the Exception
     * @exception  MiddlewareRequestException             Description of the Exception
     * @exception  MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  MiddlewareException                    Description of the Exception
     * @exception  RemoteException                        Description of the Exception
     */

    public EntityList rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {

        // We will need to remove these guys
        EANList elp = new EANList();

        // This guy preps the information for RDI since this is a remote call we need to strip
        for (int ii = 0; ii < m_el.size(); ii++) {
            elp.put(new EntityItem((EntityItem) m_el.getAt(ii)));
        }

        m_el = elp;

        // OK .. we now have modified EntityItems with no more linkage

        return _rdi.executeAction(_prof, this);

    }

    /*
     *  Here is the execute area for the local
     */
    /**
     *  Description of the Method
     *
     * @param  _db                             Description of the Parameter
     * @param  _prof                           Description of the Parameter
     * @return                                 Description of the Return Value
     * @exception  SQLException                Description of the Exception
     * @exception  MiddlewareRequestException  Description of the Exception
     * @exception  MiddlewareException         Description of the Exception
     */
    public EntityList executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareException {

        // Does a whole bunch of stuff
        return new EntityList(_db, _prof, this);
    }

    /**
     *  Description of the Method
     */
    public void resetParentEntityItems() {
        m_el = new EANList();
    }

    /**
     *  Sets the parentEntityItems attribute of the SearchActionItem object
     *
     * @param  _aei  The new parentEntityItems value
     */
    public void setParentEntityItems(EntityItem[] _aei) {
        EANFoundation fn = getParent();

        resetParentEntityItems();

        if (fn instanceof ActionGroup) {
            ActionGroup ag = (ActionGroup) fn;
            String strEntityType = ag.getEntityType();

            if (useRootEI()) {
                strEntityType = m_strRootEntityType;
            }
            setParentEntityType(strEntityType);

            try {
                for (int ii = 0; ii < _aei.length; ii++) {
                    if (!(_aei[ii].getEntityType().equals(strEntityType))) {
                        boolean bFound = false;
                        // Loop and find child
                        for (int ij = 0; ij < _aei[ii].getDownLinkCount(); ij++) {
                            EntityItem eid = (EntityItem) _aei[ii].getDownLink(ij);

                            if (eid == null) {
                                System.out.println("SearchActionItem.setParentEntityItems(): *** cannot find downlink for :" + _aei[ii].getKey());
                                break;
                            }

                            if (eid.getEntityType().equals(strEntityType)) {
                                m_el.put(new EntityItem(eid));
                                bFound = true;
                                break;
                            }
                        }
                        if (!bFound) {
                            System.err.println("SearchActionItem.setParentEntityItems(): WARNING *** cannot find home for:" + _aei[0].getKey() + "->DownLink:" + (_aei[0].getDownLink(0) == null ? "NONE" : _aei[0].getDownLink(0).getEntityType()));
                        }
                    } else {
                        m_el.put(new EntityItem(_aei[ii]));
                    }
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        } else {
            System.err.println("SearchActionItem.setParentEntityItems(): WARNING *** cannot find Parent Action Group.. so we are taking the EntityItem array verbatum");
        }
    }

    /**
     *  Sets the parentEntityItems attribute of the SearchActionItem object
     *
     * @param  _el  The new parentEntityItems value
     */
    protected void setParentEntityItems(EANList _el) {
        m_el = _el;
        if (_el!=null && _el.size() > 0 && _el.getAt(0) instanceof EntityItem) {
            setParentEntityType(((EntityItem) _el.getAt(0)).getEntityType());
        }
    }

    /**
     *  Gets the parentEntityItems attribute of the SearchActionItem object
     *
     * @return    The parentEntityItems value
     */
    protected EANList getParentEntityItems() {
        return m_el;

    }

    /**
     *  Gets the parentEntityType attribute of the SearchActionItem object
     *
     * @return    The parentEntityType value
     */
    protected String getParentEntityType() {
        return m_strParentEntityType;
    }

    /**
     *  Sets the parentEntityType attribute of the SearchActionItem object
     *
     * @param  _str  The new parentEntityType value
     */
    protected void setParentEntityType(String _str) {
        m_strParentEntityType = _str;
    }

    /**
     *  Gets the parentInfoPresent attribute of the SearchActionItem object
     *
     * @return    The parentInfoPresent value
     */
    protected boolean isParentInfoPresent() {
        return (getParentEntityItems().size() > 0);
    }

    /**
     *  Sets the domainControlled attribute of the SearchActionItem object
     *
     * @param  _b  The new domainControlled value
     */
    protected void setDomainControlled(boolean _b) {
        m_bDomainControlled = _b;
    }

    /**
     * setQueueSearch
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setQueueSearch(boolean _b) {
        m_bQueueSearch = _b;
    }

    /**
     * isQueueSearch
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isQueueSearch() {
        return m_bQueueSearch;
    }

    /**
     * setParentLess
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setParentLess(boolean _b) {
        m_bParentLess = _b;
    }

    /**
     * isParentLess
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isParentLess() {
        return m_bParentLess;
    }

    /**
     * getQueueSearchName
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueSearchName() {
        return m_strQueueSearch;
    }

    /**
     * setQueueSearchName
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setQueueSearchName(String _str) {
        m_strQueueSearch = _str;
    }

    /**
     * getDomainAttrCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getDomainAttrCode() {
        return m_strDomainAttrCode;
    }

    /**
     * getWGDomainAttrCode
     * RQ0713072645 "New Enabling Technology for Converged Products"
     * Enhancement 2
	 *
	 * A new (additional) version of the Search Action where I can specify an ATTRIBUTECODE available on WG
	 * to provide the list of PDHDOMAINs for a specific Search Action. This action would always function no matter
	 * what the PDHDOMAIN is for the entity that I am at.
	 *
	 * Add this to meta (only difference is the "WGFEATURE:PDHDOMAIN" instead of "PDHDOMAIN")
	 * SG	Action/Attribute	SRDAUD	TYPE	Domain	WGFEATURE:PDHDOMAIN
	 * where WGFEATURE is an Attribute on WG that has values to be used for PDHDOMAIN when searching
	 * WGFEATURE flag codes must be the same as PDHDOMAIN
     * @return String
     */
    public String getWGDomainAttrCode() {
        return m_strWGDomainAttrCode;
    }

    /**
     * setDomainAttrCode
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setDomainAttrCode(String _str) {
		if (_str.indexOf(":")==-1){
        	m_strDomainAttrCode = _str;
        	m_strWGDomainAttrCode = _str;
		}else{
			// split it
			StringTokenizer st = new StringTokenizer(_str, ":");
			m_strWGDomainAttrCode = st.nextToken();
			m_strDomainAttrCode = st.nextToken();
		}
    }

    /**
     *  Gets the domainControlled attribute of the SearchActionItem object
     *
     * @return    The domainControlled value
     */
    public boolean isDomainControlled() {
        return m_bDomainControlled;
    }

    /**
     * disableWorkflow
     *
     *  @author David Bigelow
     */
    protected void disableWorkflow() {
        m_bWorkflow = false;
    }

    /**
     * enableWorkflow
     *
     *  @author David Bigelow
     */
    protected void enableWorkflow() {
        m_bWorkflow = true;
    }

    /**
     * isWorkflowEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWorkflowEnabled() {
        return m_bWorkflow;
    }

    /**
     * disableLikeMatching
     *
     *  @author David Bigelow
     */
    protected void disableLikeMatching() {
        m_bLikeMatching = false;
    }

    /**
     * enableLikeMatching
     *
     *  @author David Bigelow
     */
    protected void enableLikeMatching() {
        m_bLikeMatching = true;
    }

    /**
     * isLikeMatchingEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isLikeMatchingEnabled() {
        return m_bLikeMatching;
    }

    /**
     * disableUIAfterCache
     *
     *  @author David Bigelow
     */
    public void disableUIAfterCache() {
        m_bUI = false;
    }

    /**
     * enableUIAfterCache
     *
     *  @author David Bigelow
     */
    protected void enableUIAfterCache() {
        m_bUI = true;
    }

    /**
     * isUIAfterCacheEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isUIAfterCacheEnabled() {
        return m_bUI;
    }

    /**
     * getSearchRequest
     *
     * @return
     *  @author David Bigelow
     */
    public SearchRequest getSearchRequest() {
        return m_sr;
    }

    /**
     * setSearchRequest
     *
     * @param _sr
     *  @author David Bigelow
     */
    protected void setSearchRequest(SearchRequest _sr) {
        m_sr = _sr;
    }

    /**
     * setSearchStringMin
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setSearchStringMin(int _i) {
        m_iSearchStringMin = _i;
    }

    /**
     * getSearchStringMin
     *
     * @return
     *  @author David Bigelow
     */
    public int getSearchStringMin() {
        return m_iSearchStringMin;
    }

    /**
     * If this is true, we really aren't doing a search; we're going to grab the Entity w/ the specified EntityID.
     *
     * @return boolean
     */
    public boolean isGrabByEntityID() {
        return m_bGrabByEntityID;
    }

    /**
     * setGrabEntityID
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setGrabEntityID(int _i) {
        m_iGrabEntityID = _i;
    }

    /**
     * getGrabEntityID
     *
     * @return
     *  @author David Bigelow
     */
    protected int getGrabEntityID() {
        return m_iGrabEntityID;
    }
    /**
     * isGrabRelator
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isGrabRelator() {
        return m_bGrabRelator;
    }

    /**
     * isUseRelatorAttr
     * RCQ216919
     * @return
     */
    public boolean isUseRelatorAttr() {
        return m_bUseRelator; 
    }
    /**
     * setGrabRelator
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setGrabRelator(boolean _b) {
        m_bGrabRelator = _b;
    }

    /**
     * enforceWGDomain
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean enforceWGDomain() {
        return m_bEnforceWGDomain;
    }

    /**
     * showRelParentChild
     *
     * @param _strRelatorType
     * @return
     *  @author David Bigelow
     */
    public boolean showRelParentChild(String _strRelatorType) {
        if (m_showRelParentChild != null && m_showRelParentChild.get(_strRelatorType) != null) {
            return true;
        }

        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isFilterByEntity(String _strEntityType) {
        if (m_filterSearch != null && m_filterSearch.get(_strEntityType) != null) {
            return true;
        }
        return false;
   }

    /**
     *
     * @return boolean
     */
    public String getFilterActionKey(String _strEntityType) {
        if (m_filterSearch != null && m_filterSearch.get(_strEntityType) != null) {
            return (String)m_filterSearch.get(_strEntityType);
        }
        return "";
   }

    /**
     *
     * @return boolean
     */
    public boolean hasFilterByEntity() {
        if (m_filterSearch != null && m_filterSearch.size() > 0) {
            return true;
        }
        return false;
   }

}
