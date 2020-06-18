//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: NavActionItem.java,v $
// Revision 1.84  2009/05/18 12:09:43  wendy
// Support dereference for memory release
//
// Revision 1.83  2009/05/18 11:58:44  wendy
// Support dereference for memory release
//
// Revision 1.82  2009/05/11 13:57:00  wendy
// Support turning off domain check for all actions
//
// Revision 1.81  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.80  2007/08/15 14:36:37  wendy
// RQ0713072645- Enhancement 3
//
// Revision 1.79  2007/06/18 19:07:17  wendy
// RQ0927066214
// XCC GX SR001.5.001 EACM Enabling Technology - Bread Crumbs
//
// Revision 1.78  2006/05/10 15:37:03  tony
// cr 0428066810
//
// Revision 1.77  2005/08/22 19:46:33  tony
// added targetType calls
//
// Revision 1.76  2005/08/22 19:21:49  tony
// Added getTargetType Logic to improve keying of the
// records
//
// Revision 1.75  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.74  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.73  2005/03/08 23:15:47  dave
// Jtest checkins from today and last ngith
//
// Revision 1.72  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.71  2004/11/18 23:02:46  dave
// new ForceDisplay
//
// Revision 1.70  2004/11/18 22:18:58  dave
// ok.. looking for forcedisplay
//
// Revision 1.69  2004/11/18 20:26:24  dave
// syntax
//
// Revision 1.68  2004/11/18 20:19:24  dave
// making all tabs show in the event of a isShowParent Action Item
//
// Revision 1.67  2004/11/09 21:00:05  joan
// work on showing both parent and child in navigate
//
// Revision 1.66  2004/11/03 23:12:44  gregg
// MetaColumnOrderGroup for NavActionItems
// MetaLinkAttr switch on ActionItem ColumnOrderControl
// Apply ColumnOrderControl for EntityItems
// Cleanup Debugs
//
// Revision 1.65  2004/09/20 15:28:55  joan
// fix null pointer
//
// Revision 1.64  2004/09/15 23:34:47  joan
// fixes
//
// Revision 1.63  2004/09/15 21:20:17  joan
// work on get no down link when add to cart
//
// Revision 1.62  2004/09/08 21:39:53  dave
// using setRootID on NavActionItem
//
// Revision 1.61  2004/07/15 16:58:18  joan
// add SingleInput to accept only one input
//
// Revision 1.60  2004/06/18 21:39:41  joan
// work on show parent for nav action
//
// Revision 1.59  2004/04/14 21:53:15  joan
// initial load
//
// Revision 1.58  2004/01/26 21:01:22  gregg
// check for isHomeEnabled() in executeAction (fix for FB53586)
//
// Revision 1.57  2004/01/08 22:49:02  dave
// more clean up for ActionTree I
//
// Revision 1.56  2003/10/30 00:43:34  dave
// fixing all the profile references
//
// Revision 1.55  2003/10/09 16:25:03  dave
// upping the nav limit to 50,000
//
// Revision 1.54  2003/08/22 19:37:43  dave
// small indexes
//
// Revision 1.53  2003/08/22 19:28:54  dave
// rmi trickery II
//
// Revision 1.52  2003/08/22 18:32:56  dave
// Lets try remote procedure calls in contructors
//
// Revision 1.51  2003/06/07 19:16:08  dave
// bumped 1000 to 2000
//
// Revision 1.50  2003/06/06 20:21:45  joan
// move changes from v111
//
// Revision 1.49  2003/06/06 00:04:19  joan
// move changes from v111
//
// Revision 1.48  2003/05/28 17:57:22  dave
// Trace Cleanup
//
// Revision 1.47  2003/04/01 23:21:49  dave
// fixing resetTagging
//
// Revision 1.46  2003/04/01 22:39:22  dave
// setting the parent to WG right now to see if the
// passthru entities will render in navigate
//
// Revision 1.45  2003/04/01 17:52:20  dave
// missing paren
//
// Revision 1.44  2003/04/01 17:43:30  dave
// small sintax change
//
// Revision 1.43  2003/04/01 17:18:36  dave
// implementing NavActionItem tagging II
//
// Revision 1.42  2003/03/31 23:51:53  dave
// tagging and passthru logic I
//
// Revision 1.41  2003/03/27 23:07:23  dave
// adding some timely commits to free up result sets
//
// Revision 1.40  2003/03/19 20:34:59  gregg
// null ptr fix for EntryPoint in executeAction method
//
// Revision 1.39  2003/03/19 01:13:37  gregg
// setEntityItems in copy Constructor
//
// Revision 1.38  2003/03/19 00:53:50  gregg
// made getEntityItems public
//
// Revision 1.37  2003/03/18 01:01:46  dave
// single select fix
//
// Revision 1.36  2003/03/18 00:52:13  dave
// adding singleselect to options of nav Action Item
//
// Revision 1.35  2003/03/17 22:50:16  gregg
// null ptr fix for setEntityItems
//
// Revision 1.34  2003/03/17 20:31:00  gregg
// executeAction on NavActionItem
//
// Revision 1.33  2003/03/10 17:18:00  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.32  2002/10/31 19:54:30  dave
// more memory trapping
//
// Revision 1.31  2002/10/31 19:22:17  dave
// fix on linkchild, syntax fix for memory trap
//
// Revision 1.30  2002/10/31 19:06:01  dave
// more memory watching
//
// Revision 1.29  2002/09/05 23:19:43  gregg
// catchException message in updatePdhMeta
//
// Revision 1.28  2002/09/05 22:13:42  gregg
// first pass at updatePdhMeta
//
// Revision 1.27  2002/08/26 19:31:20  dave
// _ai to _nai
//
// Revision 1.26  2002/08/26 19:24:59  dave
// syntax fix for NavActionitem to have TargetEntityType
//
// Revision 1.25  2002/08/26 19:01:41  dave
// first attempt at getting to the dg work queue
//
// Revision 1.24  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.23  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.22  2002/04/25 21:39:52  dave
// remove a line of code
//
// Revision 1.21  2002/04/25 21:18:43  dave
// attempting to implement the go home function in navigate
//
// Revision 1.20  2002/04/25 17:54:50  dave
// Fixing Syntax
//
// Revision 1.19  2002/04/25 17:40:10  dave
// syntax
//
// Revision 1.18  2002/04/05 20:16:55  dave
// syntax fixes
//
// Revision 1.17  2002/04/05 18:43:38  dave
// first attempt at the extract action item
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
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSetGroup;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.transactions.OPICMList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.StringTokenizer;

// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate
/**
 * NavActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NavActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;
    private boolean m_bPicklist = false;
    private boolean m_bPath = false;
    private String m_strEntityGroupKey = null;
    private String m_strEntityType = "";
    private boolean m_bReturnToHome = false;
    private boolean m_bPassThru = false;
    private boolean m_bQueueSource = false;
    private EntityItem[] m_arrayEntityItems = null;
    private boolean m_bSingleSelect = false;
    private Vector m_breadCrumbsVct = null; //RQ0927066214
    // Tagging information
    private boolean m_bSetTag = false;
    private boolean m_bResetTag = false;
    /**
     * FIELD
     */
    protected Hashtable m_hshForceDisplay = null;
    /**
     * FIELD
     */
    public static final int NAVLIMIT = 50000;
    /**
     * FIELD
     */
    protected boolean m_bCheckLimit = true;
    /**
     * FIELD
     */
    protected boolean m_bFlagResearch = false;
    /**
     * FIELD
     */
    protected boolean m_bShowParent = false;
    /**
     * FIELD
     */
    protected boolean m_bShowParentChild = false;
    /**
     * FIELD
     */
    protected OPICMList m_noDownLinkList = new OPICMList();
    /**
     * FIELD
     */
    protected OPICMList m_showRelParentChild = new OPICMList();

    private String m_strAttributeCode = null;
    private String m_strAttributeValue = null;

    /**
     * cr0428066810
     */
    protected int m_iDisplayLimit = 0;

    private EANActionItem parentActionItem = null; //RQ0713072645-3 whereused must be able to run this
    
    public void dereference(){
    	super.dereference();
    	m_strEntityGroupKey = null;
        m_strEntityType = null;
        if (m_arrayEntityItems != null){
        	for (int i = 0; i < m_arrayEntityItems.length; i++) {
                m_arrayEntityItems[i].dereference();
                m_arrayEntityItems[i] = null;
            }
        }
        if (m_breadCrumbsVct != null){
        	for (int i=0; i<m_breadCrumbsVct.size(); i++){
				BreadCrumbs bc = (BreadCrumbs)m_breadCrumbsVct.elementAt(i);
				bc.dereference();
        	}
        	m_breadCrumbsVct.clear();
        	m_breadCrumbsVct = null;
        }
 
        if(m_hshForceDisplay != null){
        	m_hshForceDisplay.clear();
        	m_hshForceDisplay = null;
        }
        if (m_noDownLinkList!=null){
        	m_noDownLinkList.clear();
        	m_noDownLinkList = null;
        }
        if (m_showRelParentChild!=null){
        	m_showRelParentChild.clear();
        	m_showRelParentChild = null;
        }

        m_strAttributeCode = null;
        m_strAttributeValue = null;
        parentActionItem = null;  
    }

    public void setParentActionItem(EANActionItem p) {parentActionItem=p;} //RQ0713072645-3 whereused must be able to run this
    public EANActionItem getParentActionItem() { return parentActionItem;} //RQ0713072645-3 whereused must be able to run this

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
        return "$Id: NavActionItem.java,v 1.84 2009/05/18 12:09:43 wendy Exp $";
    }

    /*
    * Instantiate a new ActionItem based upon a dereferenced version of the Existing One
    */
    /**
     * NavActionItem
     *
     * @param _nai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public NavActionItem(NavActionItem _nai) throws MiddlewareRequestException {
        super(_nai);
        setPicklist(_nai.isPicklist());
        setTargetEntityGroup(_nai.getTargetEntityGroup());
        setTargetEntityType(_nai.getTargetEntityType());
        setQueueSource(_nai.isQueueSourced());
        setPath(_nai.isPath());
        setHomeEnabled(_nai.isHomeEnabled());
        setPassThru(_nai.isPassThru());
        setSingleSelect(_nai.isSingleSelect());
        setEntityItems(_nai.getEntityItems());
        setTagging(_nai.shouldSetTag());
        setResetTagging(_nai.shouldResetTag());
        setCheckLimit(_nai.checkLimit());
        setFlagResearch(_nai.isFlagResearch());
        setShowParent(_nai.isShowParent());

        setAttributeCode(_nai.getAttributeCode());
        setAttributeValue(_nai.getAttributeValue());
        setDisplayLimit(_nai.getDisplayLimit());		//cr0428066810
        m_noDownLinkList = _nai.m_noDownLinkList;
        m_showRelParentChild = _nai.m_showRelParentChild;
        m_hshForceDisplay = _nai.m_hshForceDisplay;
        m_breadCrumbsVct = _nai.m_breadCrumbsVct; //RQ0927066214
        setParentActionItem(_nai.getParentActionItem());//RQ0713072645-3
    }

    /**
     * NavActionItem
     *
     * @param _mf
     * @param _nai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public NavActionItem(EANMetaFoundation _mf, NavActionItem _nai) throws MiddlewareRequestException {
        super(_mf, _nai);
        setPicklist(_nai.isPicklist());
        setTargetEntityGroup(_nai.getTargetEntityGroup());
        setTargetEntityType(_nai.getTargetEntityType());
        setQueueSource(_nai.isQueueSourced());
        setPath(_nai.isPath());
        setHomeEnabled(_nai.isHomeEnabled());
        setPassThru(_nai.isPassThru());
        setSingleSelect(_nai.isSingleSelect());
        setEntityItems(_nai.getEntityItems());
        setTagging(_nai.shouldSetTag());
        setResetTagging(_nai.shouldResetTag());
        setCheckLimit(_nai.checkLimit());
        setFlagResearch(_nai.isFlagResearch());
        setShowParent(_nai.isShowParent());
        setAttributeCode(_nai.getAttributeCode());
        setAttributeValue(_nai.getAttributeValue());
        setDisplayLimit(_nai.getDisplayLimit());		//cr0428066810
        m_noDownLinkList = _nai.m_noDownLinkList;
        m_showRelParentChild = _nai.m_showRelParentChild;
        m_hshForceDisplay = _nai.m_hshForceDisplay;
        m_breadCrumbsVct = _nai.m_breadCrumbsVct; //RQ0927066214
        setParentActionItem(_nai.getParentActionItem());//RQ0713072645-3
    }

    /*
    * Used for EntityPoint
    */
    /**
     * NavActionItem
     *
     * @param _emf
     * @param _prof
     * @param _strActionItemKey
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public NavActionItem(EANMetaFoundation _emf, Profile _prof, String _strActionItemKey) throws MiddlewareRequestException {
        super(_emf, _prof, _strActionItemKey);
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strActionItemKey
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public NavActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_emf, _db, _prof, _strActionItemKey);

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;

            Profile prof = getProfile();

            // If we are a nav Action Item that says EntryPoint
            // We need to reset Tagging

            if (_strActionItemKey.equals("EntryPoint")) {
                setResetTagging(true);
            }

            // Retrieve the Action Class and the Action Description.
            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
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

            // Set the class and description...
            setClassAndDescriptions(_db,rdrs);
            /*for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);
                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
                if (strType.equals("TYPE") && strCode.equals("Path")) {
                    setPath(true);
                    setTargetEntityGroup(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Picklist")) {
                    setPicklist(true);
                    setTargetEntityGroup(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleSelect")) {
                    setSingleSelect(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("GoHome")) {
                    setHomeEnabled(true);
                } else if (strType.equals("TYPE") && strCode.equals("PassThru")) {
                    setPassThru(true);
                } else if (strType.equals("TYPE") && strCode.equals("setTag")) {
                    setTagging(true);
                } else if (strType.equals("TYPE") && strCode.equals("resetTag")) {
                    setResetTagging(true);
                } else if (strType.equals("TYPE") && strCode.equals("QUEUESOURCE")) {
                    setQueueSource((strValue.equals("Y") ? true : false));
                } else if (strType.equals("TYPE") && strCode.equals("Target")) {
                    setTargetEntityType(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("FlagResearch")) {
                    setFlagResearch(true);
                } else if (strType.equals("TYPE") && strCode.equals("RootEI")) {
                    setRootEI(true);
                    setRootEntityType(strValue);
                    setTargetType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("ShowParent")) {
                    setShowParent(true);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                    setSingleInput(true);
                } else if (strType.equals("TYPE") && strCode.equals("NoDownLink")) {
                    m_noDownLinkList.put(strValue, strValue);
                } else if (strType.equals("TYPE") && strCode.equals("ColumnOrderControl")) {
                    setMetaColumnOrderControl(true);
                } else if (strType.equals("TYPE") && strCode.equals("ShowRelParentChild")) {
                    m_showRelParentChild.put(strValue, strValue);
                } else if (strType.equals("ForceDisplay") && strCode.equals("Y")) {
                    putForceDisplay(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DisplayLimit")) {	//cr0428066810
					setDisplayLimit(strValue);											//cr0428066810
                } else if (strType.equals("TYPE") && strCode.equals("BreadCrumbs")) {	//RQ0927066214
					setBreadCrumbs(strValue);											//RQ0927066214
                } else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }*/

        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * This represents an Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key*
     *
     * @param _emf
     * @param _ro
     * @param _prof
     * @param _strActionItemKey
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public NavActionItem(EANMetaFoundation _emf, RemoteDatabaseInterface _ro, Profile _prof, String _strActionItemKey) throws RemoteException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _ro, _prof, _strActionItemKey);
        try {

            ReturnDataResultSetGroup rdrsg = null;
            ReturnDataResultSet rdrs = null;

            Profile prof = getProfile();

            // If we are a nav Action Item that says EntryPoint
            // We need to reset Tagging

            if (_strActionItemKey.equals("EntryPoint")) {
                setResetTagging(true);
            }

            rdrsg = _ro.remoteGBL7030(prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
            rdrs = rdrsg.getResultSet(0);
            setClassAndDescriptions(null,rdrs);
            // Set the class and description...
            /*for (int ii = 0; ii < rdrs.size(); ii++) {
                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);
                D.ebug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
                if (strType.equals("TYPE") && strCode.equals("Path")) {
                    setPath(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("Picklist")) {
                    setPicklist(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("SingleSelect")) {
                    setSingleSelect(true);
                    setTargetEntityGroup(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("GoHome")) {
                    setHomeEnabled(true);
                } else if (strType.equals("TYPE") && strCode.equals("PassThru")) {
                    setPassThru(true);
                } else if (strType.equals("TYPE") && strCode.equals("setTag")) {
                    setTagging(true);
                } else if (strType.equals("TYPE") && strCode.equals("resetTag")) {
                    setResetTagging(true);
                } else if (strType.equals("TYPE") && strCode.equals("QUEUESOURCE")) {
                    setQueueSource((strValue.equals("Y") ? true : false));
                } else if (strType.equals("TYPE") && strCode.equals("Target")) {
                    setTargetEntityType(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("FlagResearch")) {
                    setFlagResearch(true);
                } else if (strType.equals("TYPE") && strCode.equals("ShowParent")) {
                    setShowParent(true);
                } else if (strType.equals("TYPE") && strCode.equals("ShowRelParentChild")) {
                    m_showRelParentChild.put(strValue, strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
                } else if (strType.equals("ForceDisplay") && strCode.equals("Y")) {
                    putForceDisplay(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("BreadCrumbs")) {	//RQ0927066214
					setBreadCrumbs(strValue);											//RQ0927066214
                } else {
                    D.ebug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }*/

        } finally {
            // Not alot to do here
        }
    }
    private void setClassAndDescriptions(Database _db, ReturnDataResultSet rdrs){
    	setDomainCheck(true);
    	for (int ii = 0; ii < rdrs.size(); ii++) {
            String strType = rdrs.getColumn(ii, 0);
            String strCode = rdrs.getColumn(ii, 1);
            String strValue = rdrs.getColumn(ii, 2);
            if (_db !=null){
            	_db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
            }else{
            	D.ebug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
            }
            if (strType.equals("TYPE") && strCode.equals("Path")) {
                setPath(true);
                setTargetEntityGroup(strValue);
                setTargetType(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("Picklist")) {
                setPicklist(true);
                setTargetEntityGroup(strValue);
                setTargetType(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("SingleSelect")) {
                setSingleSelect(true);
                setTargetEntityGroup(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("GoHome")) {
                setHomeEnabled(true);
            } else if (strType.equals("TYPE") && strCode.equals("PassThru")) {
                setPassThru(true);
            } else if (strType.equals("TYPE") && strCode.equals("setTag")) {
                setTagging(true);
            } else if (strType.equals("TYPE") && strCode.equals("resetTag")) {
                setResetTagging(true);
            } else if (strType.equals("TYPE") && strCode.equals("QUEUESOURCE")) {
                setQueueSource((strValue.equals("Y") ? true : false));
            } else if (strType.equals("TYPE") && strCode.equals("Target")) {
                setTargetEntityType(strValue);
                setTargetType(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("FlagResearch")) {
                setFlagResearch(true);
            } else if (strType.equals("TYPE") && strCode.equals("RootEI")) {
                setRootEI(true);
                setRootEntityType(strValue);
                setTargetType(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("ShowParent")) {
                setShowParent(true);
            } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                super.setAssociatedEntityType(strValue);
            } else if (strType.equals("TYPE") && strCode.equals("SingleInput")) {
                setSingleInput(true);
            } else if (strType.equals("TYPE") && strCode.equals("NoDownLink")) {
                m_noDownLinkList.put(strValue, strValue);
            } else if (strType.equals("TYPE") && strCode.equals("ColumnOrderControl")) {
                setMetaColumnOrderControl(true);
            } else if (strType.equals("TYPE") && strCode.equals("ShowRelParentChild")) {
                m_showRelParentChild.put(strValue, strValue);
            } else if (strType.equals("ForceDisplay") && strCode.equals("Y")) {
                putForceDisplay(strValue);
			} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
				setDataSource(strValue);											//catalog enhancement
			} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
				setAdditionalParms(strValue);
			} else if (strType.equals("TYPE") && strCode.equals("DisplayLimit")) {	//cr0428066810
				setDisplayLimit(strValue);											//cr0428066810
            } else if (strType.equals("TYPE") && strCode.equals("BreadCrumbs")) {	//RQ0927066214
				setBreadCrumbs(strValue);											//RQ0927066214
            } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) { // default is on, allow off
			    setDomainCheck(!strValue.equals("N")); //RQ0713072645
            } else {
            	if (_db !=null){
                	_db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
            	}else{
            		D.ebug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
            	}
            }
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
        strbResult.append("NavActionItem:" + super.dump(_bBrief));
        strbResult.append(":isPicklist:" + isPicklist());
        strbResult.append(":isPath:" + isPath());
        strbResult.append(":EGTarget:" + getTargetEntityGroup());
        return strbResult.toString();

    }

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Navigate";
    }

    /*
    * is This NavActionItem a Picklist
    */
    /**
     * isPicklist
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPicklist() {
        return m_bPicklist;
    }

    /*
    * is This NavActionItem a Path
    */
    /**
     * isPath
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPath() {
        return m_bPath;
    }

    /**
     * isSingleSelect
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isSingleSelect() {
        return m_bSingleSelect;
    }

    /**
     * setSingleSelect
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setSingleSelect(boolean _b) {
        m_bSingleSelect = _b;
    }

    /**
     * setPicklist
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setPicklist(boolean _b) {
        m_bPicklist = _b;
    }

    /**
     * setPath
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setPath(boolean _b) {
        m_bPath = _b;
    }

    /**
     * setTargetEntityGroup
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setTargetEntityGroup(String _str) {
        m_strEntityGroupKey = _str;
    }

    /***
     * metaui must be able to update the action
     * getNav().setQueueSource(bQueueType);
			if(isQueueType()) {
			    getNav().setTargetEntityType(strTarget);
				//reset these
				getNav().setPath(false);
				getNav().setPicklist(false);
				getNav().setTargetEntityGroup(null);
			} else {
				getNav().setPath(bPathType);
				getNav().setPicklist(bPicklistType);
                if(!bPathType && !bPicklistType)
				    getNav().setTargetEntityGroup(null);
				else
				    getNav().setTargetEntityGroup(strTargetEG);
				//reset these 
				getNav().setTargetEntityType(null);
			}
			getNav().setActionClass("Navigate");
     */
    public void updateAction(boolean queueSrc,boolean path, boolean picklist, String targetEg,
    		String actClass){
    	setQueueSource(queueSrc);
    	setPath(path);
    	setPicklist(picklist);
    	setTargetEntityGroup(targetEg);
    	setActionClass(actClass);
    }
    /**
     * getTargetEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    public String getTargetEntityGroup() {
        return m_strEntityGroupKey;
    }

    /**
     * setHomeEnabled
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setHomeEnabled(boolean _b) {
        m_bReturnToHome = _b;
    }

    /**
     * isHomeEnabled
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isHomeEnabled() {
        return m_bReturnToHome;
    }

    /**
     * setPassThru
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setPassThru(boolean _b) {
        m_bPassThru = _b;
    }

    /**
     * isPassThru
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isPassThru() {
        return m_bPassThru;
    }

    /**
     * setTagging
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setTagging(boolean _b) {
        m_bSetTag = _b;
    }

    /**
     * setResetTagging
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setResetTagging(boolean _b) {
        m_bResetTag = _b;
    }

    /*
    * Should the session tags be reset?
    */
    /**
     * shouldResetTag
     *
     * @return
     *  @author David Bigelow
     */
    public boolean shouldResetTag() {
        return m_bResetTag;
    }

    /*
    * Should the session tags be set
    */
    /**
     * shouldSetTag
     *
     * @return
     *  @author David Bigelow
     */
    public boolean shouldSetTag() {
        return m_bSetTag;
    }

    /**
     * setQueueSource
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setQueueSource(boolean _b) {
        m_bQueueSource = _b;
    }

    /**
     * isQueueSourced
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isQueueSourced() {
        return m_bQueueSource;
    }

    /**
     * getTargetEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getTargetEntityType() {
        return m_strEntityType;
    }

    /**
     * setTargetEntityType
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setTargetEntityType(String _str) {
        m_strEntityType = _str;
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
     * setFlagResearch
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setFlagResearch(boolean _b) {
        m_bFlagResearch = _b;
    }

    /**
     * isFlagResearch
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isFlagResearch() {
        return m_bFlagResearch;
    }

    /**
     * setShowParent
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setShowParent(boolean _b) {
        m_bShowParent = _b;
    }

    /**
     * isShowParent
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isShowParent() {
        return m_bShowParent;
    }

    /**
     * setShowParentChild
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setShowParentChild(boolean _b) {
        m_bShowParentChild = _b;
    }

    /**
     * isShowParentChild
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isShowParentChild() {
        return m_bShowParentChild;
    }

    /**
     * getAttributeCode
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeCode() {
        return (m_strAttributeCode == null ? "" : m_strAttributeCode);
    }

    /**
     * setAttributeCode
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setAttributeCode(String _str) {
        m_strAttributeCode = _str;
    }

    /**
     * getAttributeValue
     *
     * @return
     *  @author David Bigelow
     */
    public String getAttributeValue() {
        return (m_strAttributeValue == null ? "" : m_strAttributeValue);
    }

    /**
     * setAttributeValue
     *
     * @param _str
     *  @author David Bigelow
     */
    public void setAttributeValue(String _str) {
        m_strAttributeValue = _str;
    }

    /**
     * Array of EntityItems we are navigating ~from~.
     *
     * @param _aei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    public void setEntityItems(EntityItem[] _aei) throws MiddlewareRequestException {
        m_arrayEntityItems = new EntityItem[(_aei != null ? _aei.length : 0)];
        // We only need stub info
        for (int i = 0; i < m_arrayEntityItems.length; i++) {
            m_arrayEntityItems[i] = new EntityItem(null, getProfile(), _aei[i].getEntityType(), _aei[i].getEntityID());
        }
    }

    /**
     * Array of EntityItems we are navigating ~from~.
     *
     * @return EntityItem[]
     */
    public EntityItem[] getEntityItems() {
        return m_arrayEntityItems;
    }

    /**
     * Execute this NavActionItem
     *
     * @return EntityList
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     */
    public EntityList executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        String strEntityType = null;
        if (getEntityItems() == null || getEntityItems().length == 0) {
            if (isHomeEnabled()) {
                strEntityType = "WG";
            } else {
                return new EntityList(_db, _prof);
            }
        } else { // this is our 'standard' case
            // try to get the EntityType for the ActionItem's parent ActionGroup (these are being defined??);
            // - else -> grab the ET of 1st EntityItem
            strEntityType = (getAssociatedEntityType() != null ? getAssociatedEntityType() : getEntityItems()[0].getEntityType());
        }
        // DWB Candidate for actionTREE
        return new EntityList(_db, _prof, this, this.getEntityItems(), strEntityType, true);
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @return
     *  @author David Bigelow
     */
    public EntityList exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, MiddlewareShutdownInProgressException {
        return _db.executeAction(_prof, this);
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @return
     *  @author David Bigelow
     */
    public EntityList rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws MiddlewareShutdownInProgressException, MiddlewareException, RemoteException {
        // careful not to serialize parents!
        EntityList elReturn = null;
        EANFoundation ef = getParent();
        setTransientParent();
        setParent(null);
        elReturn = _rdi.executeAction(_prof, this);
        // now set back
        setParent(ef);
        resetTransientParent();
        return elReturn;
    }

    /**
     * updatePdhMeta
     *
     * @return true if successful, false if nothing to update or unsuccessful
     * currently only updates:
     *   - isQueueSourced()
     *   - isPath()
     *   - isPicklist()
     *   - getTargetEntityType()
     *   - getTargetEntityGroup()
     * @param _db
     * @param _bExpire
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     */
    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            NavActionItem nav_db = new NavActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if (nav_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if (_bExpire && !bNewAction) {
                //Target Entity Type
                if (nav_db.getTargetEntityType() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Target", nav_db.getTargetEntityType());
                }
                //Queue Source
                updateActionAttribute(_db, true, "TYPE", "QUEUESOURCE", (nav_db.isQueueSourced() ? "Y" : "N"));
                //isPath
                if (nav_db.isPath()) {
                    updateActionAttribute(_db, true, "TYPE", "Path", nav_db.getTargetEntityGroup());
                }
                //isPath
                if (nav_db.isPicklist()) {
                    updateActionAttribute(_db, true, "TYPE", "Picklist", nav_db.getTargetEntityGroup());
                }

            } else {

                // 1) Target Entity Type
                String s1_db = nav_db.getTargetEntityType();
                String s1 = this.getTargetEntityType();

                if (s1_db == null) {
                    s1_db = "";
                }
                if (s1 == null) {
                    s1 = "";
                }
                //used to exist -> now doesnt (is this field required anyways??) --> expire
                if (!s1_db.equals("") && s1.equals("")) {
                    updateActionAttribute(_db, true, "TYPE", "Target", s1_db);
                //new add or this changed --> update

                } else if ((bNewAction && !s1.equals("")) || (!s1.equals(s1_db))) {
                    updateActionAttribute(_db, false, "TYPE", "Target", s1);
                }

                // 2) Queue Source
                if (bNewAction || nav_db.isQueueSourced() != this.isQueueSourced()) {
                    updateActionAttribute(_db, false, "TYPE", "QUEUESOURCE", (this.isQueueSourced() ? "Y" : "N"));
                }

                //3) isPath/isPicklist -- careful, these are mutually exclusive!!
                //   a) add new
                if (bNewAction) {
                    if (isPath()) {
                        updateActionAttribute(_db, false, "TYPE", "Path", getTargetEntityGroup());

                    } else if (isPath()) {
                        updateActionAttribute(_db, false, "TYPE", "Picklist", getTargetEntityGroup());
                    }
                }
                //   b)update/expire 3 cases
                //     - path, picklist, neither
                else {
                    if (isPath()) {
                        //update path
                        if (!nav_db.isPath()) {
                            updateActionAttribute(_db, false, "TYPE", "Path", getTargetEntityGroup());
                        }
                        //expire db picklist
                        if (nav_db.isPicklist()) {
                            updateActionAttribute(_db, true, "TYPE", "Picklist", nav_db.getTargetEntityGroup());
                        }
                        //entitygroup ONLY changed
                        if ((nav_db.isPath() == this.isPath() == true) && !nav_db.getTargetEntityGroup().equals(this.getTargetEntityGroup())) {
                            //update should do it, since only linkValue changed
                            updateActionAttribute(_db, false, "TYPE", "Path", this.getTargetEntityGroup());
                        }
                    } else if (isPicklist()) {
                        //update picklist
                        if (!nav_db.isPicklist()) {
                            updateActionAttribute(_db, false, "TYPE", "Picklist", getTargetEntityGroup());
                        }
                        //expire db path
                        if (nav_db.isPath()) {
                            updateActionAttribute(_db, true, "TYPE", "Path", nav_db.getTargetEntityGroup());
                        }
                        //entitygroup ONLY changed
                        if ((nav_db.isPicklist() == this.isPicklist() == true) && !nav_db.getTargetEntityGroup().equals(this.getTargetEntityGroup())) {
                            //update should do it, since only linkValue changed
                            updateActionAttribute(_db, false, "TYPE", "Picklist", this.getTargetEntityGroup());
                        }
                    } else {
                        if (nav_db.isPath()) {
                            updateActionAttribute(_db, true, "TYPE", "Path", nav_db.getTargetEntityGroup());

                        } else if (nav_db.isPicklist()) {
                            updateActionAttribute(_db, true, "TYPE", "Picklist", nav_db.getTargetEntityGroup());
                        }
                    }
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "NavActionItem 406 " + sqlExc.toString());
            return false;
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
        return true;
    }

    /**
     * to simplify things a bit
     */
    private void updateActionAttribute(Database _db, boolean _bExpire, String _strLinkType2, String _strLinkCode, String _strLinkValue) throws MiddlewareException {
        String strValFrom = _db.getDates().getNow();
        String strValTo = (_bExpire ? strValFrom : _db.getDates().getForever());
        new MetaLinkAttrRow(getProfile(), "Action/Attribute", getActionItemKey(), _strLinkType2, _strLinkCode, _strLinkValue, strValFrom, strValTo, strValFrom, strValTo, 2).updatePdh(_db);
    }

	/** RQ0927066214
	*  set bread crumbs for UI
	D:MODEL.MACHTYPEATR.MODELATR,U:FEATURE.FEATURECODE
	*@param strValue String
	*/
	private void setBreadCrumbs(String strValue){
		m_breadCrumbsVct = new Vector();
		// each set of info is delimited by ','
        StringTokenizer st1 = new StringTokenizer(strValue, ",");
        while (st1.hasMoreTokens()) {
			// direction is delimited by ':'
			// each piece is delimited by '.'
            StringTokenizer st2 = new StringTokenizer(st1.nextToken(), ":");
            String dir = st2.nextToken();
            StringTokenizer st3 = new StringTokenizer(st2.nextToken(), ".");
			BreadCrumbs bc = new BreadCrumbs(dir,st3.nextToken());
			m_breadCrumbsVct.addElement(bc);
            while (st3.hasMoreTokens()) {
				bc.addAttribute(st3.nextToken());
			}
		}
	}

	/** RQ0927066214
	*  get bread crumbs for UI
	*@param list EntityList
	*@param delim  String delimiter for one parent
	*@param delim2  String delimiter between parents
	*@return    String
	*/
    public String getBreadCrumbs(EntityList list, String delim, String delim2)
    {
		EntityGroup pgrp = list.getParentEntityGroup();
        StringBuffer strbResult = new StringBuffer();
        // loop thru each parent item
        if (m_breadCrumbsVct !=null){
			for (int e=0; e<pgrp.getEntityItemCount(); e++){
				EntityItem parent = pgrp.getEntityItem(e);
				StringBuffer sb = new StringBuffer();
				for (int i=0; i<m_breadCrumbsVct.size(); i++){
					BreadCrumbs bc = (BreadCrumbs)m_breadCrumbsVct.elementAt(i);
					if (bc.direction.equals("P")){ // parent is source
						for (int a=0; a<bc.attrVct.size(); a++){
		                    EANAttribute att = parent.getAttribute(bc.attrVct.elementAt(a).toString());
							if (a>0){
								sb.append(delim);
							}
		                    sb.append(att == null ? "" : att.toString());
						}// end attr list
					}else{
						Vector linkVct = null;
						if (bc.direction.equals("U")){ // get value from uplink
							linkVct = parent.getUpLink();
						}else{ // get value from downlink
							linkVct = parent.getDownLink();
						}
						if (linkVct !=null){
							for (int l=0; l<linkVct.size(); l++){
								EntityItem item = (EntityItem)linkVct.elementAt(l);
								if (item.getEntityType().equals(bc.etype)){
									for (int a=0; a<bc.attrVct.size(); a++){
										EANAttribute att = item.getAttribute(bc.attrVct.elementAt(a).toString());
										if (a>0){
											sb.append(delim);
										}
										sb.append(att == null ? "" : att.toString());
									}// end attr list
								}
							}// end links
						}
					} // end U or D links
					if (sb.length()>0 && (i+1)<m_breadCrumbsVct.size()){ // separate each bread crumb component
						sb.append(delim);
					}
				}// end breadcrumbs
				if (strbResult.length()>0){ // separate each parent item
					strbResult.append(delim2);
				}
				strbResult.append(sb.toString());
			} // end parent items

		}// end breadcrumbs exist

        return strbResult.toString();
    }


	/** RQ0927066214
	*  Hang onto breadcrumb meta info
	*/
    private static class BreadCrumbs implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		String direction;
		String etype;
		Vector attrVct = new Vector();
		void dereference(){
			direction = null;
			etype=null;
			attrVct.clear();
			attrVct = null;
		}
		BreadCrumbs(String dir, String type){
			direction=dir;
			etype=type;
		}
		void addAttribute(String attr){
			attrVct.addElement(attr);
		}
	}

    /*
    * Here we will check to see if tagging needs to be set.. if so.. we set it using the passed entityItem
    */
    /**
     * checkTagging
     *
     * @param _db
     * @param _prof
     * @param _aei
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public void checkTagging(Database _db, Profile _prof, EntityItem[] _aei) throws SQLException, MiddlewareException, MiddlewareRequestException {

        EntityItem ei = null;
        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = _prof.getEnterprise();
        int iSessionID = _prof.getSessionID();
        int iOPWGID = _prof.getOPWGID();

        String strEntityType = null;
        int iEntityID = 0;
        int iFlag = 0;

        if (shouldSetTag()) {
            iFlag = 1;

            _db.debug(D.EBUG_DETAIL, "NavActionItem.checkTagging:SetTag");

            if (_aei != null && _aei.length > 0) {
                ei = _aei[0];
                strEntityType = ei.getEntityType();
                iEntityID = ei.getEntityID();
            } else {
                _db.debug(D.EBUG_ERR, "NavActionItem.checkTagging: *** ERROR ***  Empty or null _aei");
                return;
            }

            _db.callGBL7547(returnStatus, strEnterprise, iSessionID, iOPWGID, strEntityType, iEntityID, iFlag);
            _db.commit();
            _db.freeStatement();
            _db.isPending();

        } else if (shouldResetTag()) {

            _db.debug(D.EBUG_DETAIL, "NavActionItem.checkTagging:ResetTag");

            strEntityType = "NOOP";
            iEntityID = 0;
            iFlag = 0;

            _db.callGBL7547(returnStatus, strEnterprise, iSessionID, iOPWGID, strEntityType, iEntityID, iFlag);
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * isNoDownLink
     *
     * @param _strEntityType
     * @return
     *  @author David Bigelow
     */
    public boolean isNoDownLink(String _strEntityType) {
        if (m_noDownLinkList != null && m_noDownLinkList.get(_strEntityType) != null) {
            return true;
        }

        return false;
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
     * putForceDisplay
     *
     * @param _strEntityType
     *  @author David Bigelow
     */
    protected void putForceDisplay(String _strEntityType) {
        if (m_hshForceDisplay == null) {
            m_hshForceDisplay = new Hashtable();
        }
        m_hshForceDisplay.put(_strEntityType.trim(), "Y");
    }

    /**
     * isForceDisplay
     *
     * @param _eg
     * @return
     *  @author David Bigelow
     */
    protected boolean isForceDisplay(EntityGroup _eg) {
        if (_eg == null || m_hshForceDisplay == null) {
            return false;
        }
        return m_hshForceDisplay.containsKey(_eg.getEntityType().trim());
    }

/*
 cr0428066810
 */
 	protected int getDisplayLimit() {
		return m_iDisplayLimit;
	}

	protected void setDisplayLimit(String _s) {
		try {
			setDisplayLimit(Integer.parseInt(_s));
		} catch (NumberFormatException _nfe) {
		}
		return;
	}

	protected void setDisplayLimit(int _i) {
		m_iDisplayLimit = _i;
		return;
	}
}
