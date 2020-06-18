//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LinkActionItem.java,v $
// Revision 1.136  2014/05/15 15:16:10  wendy
// RCQ 297831 - set link limit in profile
//
// Revision 1.135  2013/03/18 20:27:04  wendy
// Create EntityGroup once and reuse it
//
// Revision 1.134  2012/10/23 10:59:41  wendy
// restore check queuesourced for link_default
//
// Revision 1.133  2009/11/03 19:03:04  wendy
// SR11, SR15 and SR17 restrict create of relator
//
// Revision 1.132  2009/09/11 01:25:38  yang
// add link limit function
//
// Revision 1.131  2009/05/20 14:15:49  wendy
// Support dereference for memory release
//
// Revision 1.130  2009/05/11 13:54:11  wendy
// Support turning off domain check for all actions
//
// Revision 1.129  2009/03/12 14:59:14  wendy
// Add methods for metaui access
//
// Revision 1.128  2008/03/14 20:34:02  wendy
// MN34992400 - default to not enforcing domain checks
//
// Revision 1.127  2007/08/02 21:15:38  wendy
// RQ0713072645
//
// Revision 1.126  2006/01/12 23:14:19  joan
// work on CR0817052746
//
// Revision 1.125  2005/08/10 16:14:23  tony
// improved catalog viewer functionality.
//
// Revision 1.124  2005/08/03 17:09:44  tony
// added datasource logic for catalog mod
//
// Revision 1.123  2005/04/15 17:25:27  gregg
// add (ok) to VE locked dialogue
//
// Revision 1.122  2005/03/28 22:02:14  joan
// input prof NLSID for sp
//
// Revision 1.121  2005/03/25 21:10:49  dave
// more fixes
//
// Revision 1.120  2005/03/11 20:41:16  roger
// Foreign ABRs
//
// Revision 1.119  2005/03/04 18:26:23  dave
// Jtest actions for the day
//
// Revision 1.118  2005/03/03 17:56:28  gregg
// debugging rule51
//
// Revision 1.117  2005/03/02 23:17:24  gregg
// debugg
//
// Revision 1.116  2005/01/31 18:30:14  gregg
// gbl0004 now gbl0006
//
// Revision 1.115  2005/01/20 22:57:00  gregg
// cleaning up after the scourge of Rule51
//
// Revision 1.114  2005/01/20 22:44:41  gregg
// use GBL0004 to check Rule51 existence
//
// Revision 1.113  2005/01/18 22:08:39  gregg
// remove from trspartno for rule51
//
// Revision 1.112  2005/01/18 21:46:50  dave
// more parm debug cleanup
//
// Revision 1.111  2005/01/18 21:22:44  gregg
// more Rule51 link
//
// Revision 1.110  2005/01/18 19:15:57  gregg
// some debugs and et cetera
//
// Revision 1.109  2005/01/18 18:59:11  gregg
// compyle phix
//
// Revision 1.108  2005/01/18 18:53:31  gregg
// moreRule51 for link
//
// Revision 1.107  2005/01/18 01:18:36  gregg
// more Rule51 processing in executeAction
//
// Revision 1.106  2005/01/18 01:15:01  gregg
// Rule51 processing
//
// Revision 1.105  2004/06/22 21:39:11  joan
// work on CR
//
// Revision 1.104  2004/06/08 17:05:22  joan
// add methods
//
// Revision 1.103  2004/05/26 22:45:26  joan
// add debug statement
//
// Revision 1.102  2004/05/24 19:05:34  joan
// fix bugs
//
// Revision 1.101  2004/05/24 18:00:44  joan
// fix bug
//
// Revision 1.100  2004/05/21 22:55:08  joan
// fix compile
//
// Revision 1.99  2004/05/21 22:37:11  joan
// more adjustment
//
// Revision 1.98  2004/05/21 21:55:27  joan
// work on chain action
//
// Revision 1.97  2004/05/21 20:30:23  joan
// more adjustments
//
// Revision 1.96  2004/05/21 17:30:22  joan
// put more work
//
// Revision 1.95  2004/05/21 15:45:07  joan
// more adjustment
//
// Revision 1.94  2004/05/20 22:48:30  joan
// fix compile
//
// Revision 1.93  2004/05/20 22:37:41  joan
// fix compile
//
// Revision 1.92  2004/05/20 22:20:08  joan
// fix compile
//
// Revision 1.91  2004/05/20 22:09:56  joan
// work on chain action
//
// Revision 1.90  2003/12/10 21:01:03  joan
// adjust for ExcludeCopy
//
// Revision 1.89  2003/10/30 00:43:33  dave
// fixing all the profile references
//
// Revision 1.88  2003/09/11 19:31:13  joan
// fb52150
//
// Revision 1.87  2003/08/27 21:51:20  dave
// syntax fix and enacting entitylist caching
//
// Revision 1.86  2003/08/27 21:40:19  dave
// Fixing Null pointer when we implemnt isHomeEnabled
// and deferring the VE lock loading algorythem until
// the lock is executed. (used to be created during Link
// ActionItem contructor)
//
// Revision 1.85  2003/08/05 22:10:00  joan
// fb51601
//
// Revision 1.84  2003/08/05 20:45:20  joan
// put back set NAME and ID for fb51601
//
// Revision 1.83  2003/06/30 22:35:04  dave
// added some (ok) to it
//
// Revision 1.82  2003/06/30 22:01:26  dave
// tracing for link action item
//
// Revision 1.81  2003/06/28 00:26:41  dave
// final error
//
// Revision 1.80  2003/06/28 00:12:15  dave
// removed statement cannot be reached thingie
//
// Revision 1.79  2003/06/28 00:10:37  dave
// syntax
//
// Revision 1.78  2003/06/28 00:00:51  dave
// fixing syntax
//
// Revision 1.77  2003/06/27 22:56:52  dave
// new sps for translation
//
// Revision 1.76  2003/06/26 22:46:04  dave
// syntax on LinkActionItem
//
// Revision 1.75  2003/06/25 22:53:51  dave
// minor changes
//
// Revision 1.74  2003/06/06 20:03:41  dave
// fb 51198
//
// Revision 1.73  2003/06/06 00:04:19  joan
// move changes from v111
//
// Revision 1.72.2.2  2003/06/04 23:56:58  joan
// check for linkOption in linkcopy
//
// Revision 1.72.2.1  2003/06/04 21:14:47  joan
// add check entity existence before link/copy
//
// Revision 1.72  2003/05/15 17:34:24  dave
// adding copyCount to the max calcuator
//
// Revision 1.71  2003/05/06 21:39:53  dave
// bumping limit back up to 500
//
// Revision 1.70  2003/05/06 21:26:51  dave
// reduce the limit to 5 for error testing
//
// Revision 1.69  2003/05/06 19:24:24  dave
// adding (ok) to the end of an error message
//
// Revision 1.68  2003/04/29 17:05:47  dave
// clean up and removal of uneeded function
//
// Revision 1.67  2003/04/17 19:24:39  dave
// need OPENID in sp
//
// Revision 1.66  2003/04/17 19:08:30  dave
// Syntax
//
// Revision 1.65  2003/04/17 18:41:34  dave
// syntax fixes
//
// Revision 1.64  2003/04/17 17:56:15  dave
// clean up link,deactivate, tagging
//
// Revision 1.63  2003/04/14 16:43:10  dave
// speed and cleanup
//
// Revision 1.62  2003/04/08 02:59:45  dave
// commit()
//
// Revision 1.61  2003/03/17 17:46:55  dave
// Tagging I, Relator Table
//
// Revision 1.60  2003/03/17 17:25:01  dave
// Tagging Phase II - entity prep
//
// Revision 1.59  2003/03/10 17:18:00  dave
// attempting to remove GBL7030 from the abstract Action Item
//
// Revision 1.58  2003/01/21 19:02:17  joan
// check VE lock for link
//
// Revision 1.57  2003/01/21 01:12:06  joan
// fix link
//
// Revision 1.56  2003/01/20 23:00:44  joan
// add methods to get VELockOwner and the list for VELock/Entity/Relator
//
// Revision 1.55  2003/01/20 21:18:29  joan
// debug link ve
//
// Revision 1.54  2003/01/17 21:16:58  joan
// work on velock
//
// Revision 1.53  2003/01/17 19:40:28  joan
// fix commit
//
// Revision 1.52  2003/01/17 19:08:25  joan
// fix bug
//
// Revision 1.51  2003/01/17 18:33:31  joan
// fix throw exception
//
// Revision 1.50  2003/01/17 17:57:16  joan
// adjust code for checking velock
//
// Revision 1.49  2003/01/17 17:04:31  joan
// fix compile
//
// Revision 1.48  2003/01/17 16:55:24  joan
// work on link VElock
//
// Revision 1.47  2003/01/17 01:18:09  joan
// work on the VE lock
//
// Revision 1.46  2003/01/17 00:35:35  joan
// debug
//
// Revision 1.45  2003/01/16 23:50:43  joan
// fix bugs
//
// Revision 1.44  2003/01/16 23:30:53  joan
// fix bug
//
// Revision 1.43  2003/01/16 23:21:24  joan
// debug
//
// Revision 1.42  2003/01/16 22:22:57  joan
// fix error
//
// Revision 1.41  2003/01/16 21:31:37  joan
// add code to check for VELock when link
//
// Revision 1.40  2002/12/27 21:09:17  gregg
// made getTarget() method public
//
// Revision 1.39  2002/11/13 19:18:11  dave
// more trace
//
// Revision 1.38  2002/11/13 18:13:43  dave
// trace to trap null pointers
//
// Revision 1.37  2002/11/08 19:19:41  dave
// attempting to cap the link action item to 500 or less
// transactions
//
// Revision 1.36  2002/10/31 19:22:16  dave
// fix on linkchild, syntax fix for memory trap
//
// Revision 1.35  2002/10/29 19:11:32  gregg
// updatePdhMeta logic
//
// Revision 1.34  2002/10/28 22:34:33  dave
// fixing a constant
//
// Revision 1.33  2002/10/28 21:36:09  dave
// more trace
//
// Revision 1.32  2002/10/28 21:26:40  dave
// more tracing for LinkActionItem
//
// Revision 1.31  2002/10/24 20:13:54  dave
// syntax
//
// Revision 1.30  2002/10/24 20:03:44  dave
// added Java.util.Vector on import
//
// Revision 1.29  2002/10/24 19:48:49  dave
// fixes
//
// Revision 1.28  2002/10/24 19:41:53  dave
// more syntax
//
// Revision 1.27  2002/10/24 19:40:07  dave
// syntax error's and import
//
// Revision 1.26  2002/10/24 19:29:37  dave
// syntax
//
// Revision 1.25  2002/10/24 19:21:10  dave
// first attempt at rounding up link
//
// Revision 1.24  2002/10/24 17:25:05  dave
// fixing CanLink CanCopy
//
// Revision 1.23  2002/10/24 16:37:15  dave
// Adding link and link copy
//
// Revision 1.22  2002/09/04 23:29:56  gregg
// getTarget() method
//
// Revision 1.21  2002/08/23 21:59:55  gregg
// updatePdhMeta method throws MiddlewareException
//
// Revision 1.20  2002/08/23 21:29:45  gregg
// updatePdhMeta(Database,boolean) method
//
// Revision 1.19  2002/05/10 15:31:32  joan
// add commit()
//
// Revision 1.18  2002/05/09 22:43:27  joan
// fix throwing exceptions
//
// Revision 1.17  2002/05/06 23:41:24  joan
// fixing error
//
// Revision 1.16  2002/05/03 23:37:58  joan
// fix errors
//
// Revision 1.15  2002/05/03 22:51:16  joan
// thow exception
//
// Revision 1.14  2002/05/03 22:40:39  joan
// fix throwing exception
//
// Revision 1.13  2002/05/03 22:29:33  joan
// fix throw exception
//
// Revision 1.12  2002/05/03 22:10:51  joan
// work on link
//
// Revision 1.11  2002/04/23 19:44:30  dave
// import fix
//
// Revision 1.10  2002/04/23 19:35:36  dave
// Fixing a function sequence error
//
// Revision 1.9  2002/04/23 16:17:16  dave
// help tell the app dev where to get the parent entity items from
//
// Revision 1.8  2002/04/22 22:19:30  dave
// syntax
//
// Revision 1.7  2002/04/22 21:59:24  dave
// adding metalink to a link action item
//
// Revision 1.6  2002/03/08 18:46:56  dave
// first attempt at pulling data for edit (text and flags only)
//
// Revision 1.5  2002/03/01 23:23:15  dave
// column number wrong for pulling 7030 in ActionItem objects
//
// Revision 1.4  2002/03/01 20:17:05  dave
// more syntax
//
// Revision 1.3  2002/03/01 19:00:53  dave
// merged conflicts
//
// Revision 1.2  2002/02/26 21:44:00  dave
// ensuring I am setting the rs = null after a close
//
// Revision 1.1  2002/02/20 18:29:05  dave
// new function for LinkActionItem
//
//

package COM.ibm.eannounce.objects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;


// Temp made this non abstract because we need to compile in general before we whip up
// how actions are suppose to differentiate

/**
 * LinkActionItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LinkActionItem extends EANActionItem {

    static final long serialVersionUID = 20011106L;
    /**
     * FIELD
     */
    public static final int TYPE_MOVE = EANUtility.LINK_MOVE;//0;
    /**
     * FIELD
     */
    public static final int TYPE_COPY = EANUtility.LINK_COPY;//1;
    /**
     * FIELD
     */
    public static final int TYPE_DEFAULT = EANUtility.LINK_DEFAULT;//2;
    /**
     * FIELD
     */
    public static final int OPT_REPLACEALL = 0;
    /**
     * FIELD
     */
    public static final int OPT_DEFAULT = 1;
    /**
     * FIELD
     */
    public static final int OPT_NODUPES = 2;
    /**
     * FIELD
     */
    public static final int LINKLIMIT = 500;
    /**
     * FIELD
     */
    private String m_strRelatorType = null;
    /**
     * FIELD
     */
    protected MetaLink m_ml = null;
    /**
     * FIELD
     */
    protected boolean m_bWizard = false;
    /**
     * FIELD
     */
    protected boolean m_bUseParent = false;
    /**
     * FIELD
     */
    protected boolean m_bUseCart = true;
    /**
     * FIELD
     */
    protected boolean m_bLink = false;
    
    protected String m_linklimit = "-1";
    
    protected boolean m_bDomainCheck = false;// default to false MN34992400 true;  // RQ0713072645
    /**
     * FIELD
     */
    protected boolean m_bLinkCopy = false;
    /**
     * FIELD
     */
    protected EANList m_elp = new EANList();
    /**
     * FIELD
     */
    protected EANList m_elc = new EANList();
    /**
     * FIELD
     */
    protected int m_iType = TYPE_DEFAULT;
    /**
     * FIELD
     */
    protected int m_iOption = OPT_DEFAULT;

    /**
     * FIELD
     */
    protected int m_iCopyCount = 1;
    /**
     * FIELD
     */
    protected VELockERList m_pList = null;
    /**
     * FIELD
     */
    protected VELockERList m_cList = null;
    /**
     * FIELD
     */
    protected boolean m_bCheckExistence = false;
    /**
     * FIELD
     */
    protected boolean m_bQueueSource = false;
    /**
     * FIELD
     */
    protected String m_strQueueSource = "";
    /**
     * FIELD
     */
    protected boolean m_bOppSelect = false;
    
    private Hashtable sizeContainer = null; 

    // save output for chain action
    private OPICMList m_olResult = new OPICMList();
    
    public void dereference(){
    	super.dereference();
    	m_strRelatorType = null;
    	m_strQueueSource = null;
    	if (m_ml != null){
    		m_ml.dereference();
    		m_ml = null;
    	}
    	
    	if (m_elp !=null){
    		/*for (int i=0; i<m_elp.size(); i++){
    			EntityItem mt = (EntityItem) m_elp.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}*/
    		m_elp.clear();
    		m_elp = null;
    	}
       	if (m_elc !=null){
    		/*for (int i=0; i<m_elc.size(); i++){
    			EntityItem mt = (EntityItem) m_elc.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}*/
    		m_elc.clear();
    		m_elc = null;
    	}
       	
       	if (m_pList != null){
       		m_pList.dereference();
       		m_pList = null;
       	}
    	if (m_cList != null){
    		m_cList.dereference();
    		m_cList = null;
       	}
  
    	if (m_olResult != null){
    		for(int i=0; i<m_olResult.size(); i++){
    			ReturnRelatorKey rrk = (ReturnRelatorKey)m_olResult.getAt(i);
    			rrk.dereference();
    		}
    		m_olResult.clear();
    		m_olResult = null;
    	}
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: LinkActionItem.java,v 1.136 2014/05/15 15:16:10 wendy Exp $";
    }

    /**
     * LinkActionItem
     *
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public LinkActionItem(LinkActionItem _ai) throws MiddlewareRequestException {
        super(_ai);
        setRelatorType(_ai.getRelatorType());
        m_ml = _ai.m_ml;
        m_bWizard = _ai.m_bWizard;
        m_bUseParent = _ai.m_bUseParent;
        m_bUseCart = _ai.m_bUseCart;
        m_bLink = _ai.m_bLink;
        m_linklimit = _ai.m_linklimit;
        m_bLinkCopy = _ai.m_bLinkCopy;
        m_elp = _ai.m_elp;
        m_elc = _ai.m_elc;
        m_iType = _ai.m_iType;
        m_iOption = _ai.m_iOption;
        m_iCopyCount = _ai.m_iCopyCount;
        m_pList = _ai.m_pList;
        m_cList = _ai.m_cList;
        m_bCheckExistence = _ai.m_bCheckExistence;
        m_bQueueSource = _ai.m_bQueueSource;
        m_strQueueSource = _ai.m_strQueueSource;
        m_bOppSelect = _ai.m_bOppSelect;
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
    public LinkActionItem(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _strActionItemKey);

        try {
            ReturnStatus returnStatus = new ReturnStatus(-1);
            ResultSet rs = null;
            ReturnDataResultSet rdrs;
            Profile prof = getProfile();

            // Retrieve the Action Class and the Action Description.

            try {
                rs = _db.callGBL7030(returnStatus, prof.getEnterprise(), _strActionItemKey, prof.getValOn(), prof.getEffOn());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
				if (rs !=null){
	                rs.close();
	                rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            // Set the class and description...
            for (int ii = 0; ii < rdrs.size(); ii++) {

                String strType = rdrs.getColumn(ii, 0);
                String strCode = rdrs.getColumn(ii, 1);
                String strValue = rdrs.getColumn(ii, 2);

                _db.debug(D.EBUG_SPEW, "gbl7030 answer is:" + strType + ":" + strCode + ":" + strValue + ":");
                if (strType.equals("TYPE") && strCode.equals("Link")) {
                    setMetaLink(new MetaLink(this, _db, _prof, strValue));

                } else if (strType.equals("TYPE") && strCode.equals("Target")) {
                    setTarget(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("CanCopy")) {
                    setLinkCopy(strValue.equals("Y"));
                } else if (strType.equals("TYPE") && strCode.equals("CanLink")) {
                    setLink(strValue.equals("Y"));
                } else if (strType.equals("TYPE") && strCode.equals("DomainCheck")) {
				    setDomainCheck(strValue.equals("Y")); //RQ0713072645 - must turn it on
                } else if (strType.equals("TYPE") && strCode.equals("OppSelect")) {
                    setOppSelect(strValue.equals("Y"));
                } else if (strType.equals("TYPE") && strCode.equals("QueueSource")) {
                    setQueueSource(true);
                    setQueueSourceName(strValue);
                    _db.debug(D.EBUG_ERR, "TRACE Queue Thing" + strType + ":" + strCode + ":" + strValue);
                } else if (strType.equals("ENTITYTYPE") && strCode.equals("Link")) {
                    super.setAssociatedEntityType(strValue);
				} else if (strType.equals("TYPE") && strCode.equals("DataSource")) {	//catalog enhancement
					setDataSource(strValue);											//catalog enhancement
				} else if (strType.equals("TYPE") && strCode.equals("DataSourceParms")) {
					setAdditionalParms(strValue);
                } else if (strType.equals("TYPE") && strCode.equals("LinkLimit")) {
                	setLinkLimit(strValue);
                }else {
                    _db.debug(D.EBUG_ERR, "*** ACTION ITEM ATTRIBUTE *** No home for this Action/Attribute" + strType + ":" + strCode + ":" + strValue);
                }
            }

        } catch (Exception x) {
            System.out.println("LinkActionItem exception: " + x);
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * setCheckExistence
     *
     * @param _b
     *  @author David Bigelow
     */
    public void setCheckExistence(boolean _b) {
        m_bCheckExistence = _b;
    }

    /**
     * isCheckExistence
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isCheckExistence() {
        return m_bCheckExistence;
    }

    /**
     * resetParentEntityItems
     *
     *  @author David Bigelow
     */
    public void resetParentEntityItems() {
        m_elp = new EANList();
    }

    /**
     * resetChildEntityItems
     *
     *  @author David Bigelow
     */
    public void resetChildEntityItems() {
        m_elc = new EANList();
    }

    /**
     * setParentEntityItems
     *
     * @param _aei
     *  @author David Bigelow
     */
    public void setParentEntityItems(EntityItem[] _aei) {

        String strEntity1Type = null;

        if (_aei == null) {
            System.out.println("LinkActionItem.setParentEntityItems(): *** passed _aei  is null");
            return;
        }

        if (m_ml == null) {
            System.out.println("LinkActionItem.setParentEntityItems(): *** m_ml is null");
            return;
        }

        resetParentEntityItems();

        strEntity1Type = m_ml.getEntity1Type();
        // loop through and place the entityItem in the EANList
        for (int ii = 0; ii < _aei.length; ii++) {

            EntityItem processedEI = null;
            boolean bMatch = false;

            EntityItem ei = _aei[ii];
            if (ei == null) {
                System.out.println("LinkActionItem.setParentEntityItems(): *** ei is null inside of _aei, skipping it");
                continue;
            }

            if (!ei.getEntityType().equals(strEntity1Type)) {
                EntityGroup eg = (EntityGroup) ei.getParent();

                // It cannot be added to the list
                if (eg == null) {
                    continue;
                }

                if (eg.isRelator() || eg.isAssoc()) {
                    //check the child entity item
                    EntityItem eic = (EntityItem) ei.getDownLink(0);
                    if (eic == null) {
                        System.out.println("LinkActionItem.setParentEntityItems(): *** eiChild is null inside of _aei, skipping it");
                        continue;
                    }
                    if (!eic.getEntityType().equals(strEntity1Type)) {
                        //check the parent entity item
                        EntityItem eip = (EntityItem) ei.getUpLink(0);
                        if (eip == null) {
                            System.out.println("LinkActionItem.setParentEntityItems(): *** eiParent is null inside of _aei, skipping it");
                            continue;
                        }
                        if (!eip.getEntityType().equals(strEntity1Type)) {
                            bMatch = false;
                        } else {
                            processedEI = eip;
                            bMatch = true;
                        }
                    } else {
                        processedEI = eic;
                        bMatch = true;
                    }
                }
            } else {
                processedEI = ei;
                bMatch = true;
            }

            // If you found a match on entitytype.. please put it on the list
            if (bMatch) {
                m_elp.put(processedEI);
            } else {
                System.out.println("LinkActionItem.setParentEntityItems(): No match for entityItem and EntityType in action:" + strEntity1Type + ":" + ei.getKey());
            }
        }
    }

    /**
     * setChildEntityItems
     *
     * @param _aei
     *  @author David Bigelow
     */
    public void setChildEntityItems(EntityItem[] _aei) {

        String strEntity2Type = m_ml.getEntity2Type();

        resetChildEntityItems();

        if (useCart()) {
            // loop through and place the entityItem in the EANList
            for (int ii = 0; ii < _aei.length; ii++) {
                EntityItem ei = _aei[ii];
                EntityItem processedEI = null;
                boolean bMatch = false;
                if (!ei.getEntityType().equals(strEntity2Type)) {
                    bMatch = false;
                } else {
                    processedEI = ei;
                    bMatch = true;
                }

                // If you found a match on entitytype.. please put it on the list
                if (bMatch) {
                    m_elc.put(processedEI);
                } else {
                    System.out.println("LinkActionItem.setChildEntityItems(): No match for entityItem and EntityType in action:" + strEntity2Type + ":" + ei.getKey());
                }
            }
        } else if (useParents()) {
            for (int ii = 0; ii < _aei.length; ii++) {
                EntityItem ei = _aei[ii];
                EntityItem processedEI = null;
                boolean bMatch = false;
                if (!ei.getEntityType().equals(strEntity2Type)) {
                    EntityGroup eg = (EntityGroup) ei.getParent();

                    // It cannot be added to the list
                    if (eg == null) {
                        continue;
                    }

                    if (eg.isRelator() || eg.isAssoc()) {
                        //check the Child entity item
                        EntityItem eic = (EntityItem) ei.getDownLink(0);
                        if (!eic.getEntityType().equals(strEntity2Type)) {
                            bMatch = false;
                        } else {
                            processedEI = eic;
                            bMatch = true;
                        }
                    }
                } else {
                    processedEI = ei;
                    bMatch = true;
                }

                // If you found a match on entitytype.. please put it on the list
                if (bMatch) {
                    m_elc.put(processedEI);
                } else {
                    System.out.println("LinkActionItem.setChildEntityItems(): No match for entityItem and EntityType in action:" + strEntity2Type + ":" + ei.getKey());
                }
            }

        }
    }

    /**
     * exec
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @return
     *  @author David Bigelow
     */
    public Object exec(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareRequestException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException {
        Object o = _db.executeAction(_prof, this);
        resetParentEntityItems();
        resetChildEntityItems();
        return o;
    }

    /**
     * rexec
     *
     * @param _rdi
     * @param _prof
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.rmi.RemoteException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @return
     *  @author David Bigelow
     */
    public Object rexec(RemoteDatabaseInterface _rdi, Profile _prof) throws MiddlewareRequestException, MiddlewareShutdownInProgressException, MiddlewareException, RemoteException, LockException, EANBusinessRuleException, WorkflowException {

		EntityList.checkDomain(_prof,this,m_elp); //RQ0713072645
		EntityList.checkDomain(_prof,this,m_elc); //RQ0713072645

        Object o = null;
        EANList elp = new EANList();
        EANList elc = new EANList();

        // This guy preps the information for RDI since this is a remote call we need to strip

        for (int ii = 0; ii < m_elp.size(); ii++) {
            elp.put(new EntityItem((EntityItem) m_elp.getAt(ii)));
        }
        m_elp = elp;

        for (int ii = 0; ii < m_elc.size(); ii++) {
            elc.put(new EntityItem((EntityItem) m_elc.getAt(ii)));
        }
        m_elc = elc;

        // OK .. we now have modified EntityItems with no more linkage
        o = _rdi.executeAction(_prof, new LinkActionItem(this));

        resetParentEntityItems();
        resetChildEntityItems();
        return o;
    }

    private boolean isLinkable(VELockERList _list, String[] _aLockOwner, String _strEntityType, String _strRelatorType) {
        for (int i = 0; i < _aLockOwner.length; i++) {
            String strLockOwner = _aLockOwner[i];
            if (_list.test(strLockOwner, _strEntityType, _strRelatorType)) {
                return true;
            }
        }

        return false;
    }

    private boolean isExist(Database _db, Profile _prof, String _strEntityType, int _iEntityID) throws SQLException, MiddlewareException {

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;

        ReturnStatus returnStatus = new ReturnStatus(-1);
        String strEnterprise = _prof.getEnterprise();

        try {
            rs = _db.callGBL2920(returnStatus, strEnterprise, _strEntityType, _iEntityID);
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

        for (int j = 0; j < rdrs.size(); j++) {
            String strET = rdrs.getColumn(j, 1).trim();
            int iID = rdrs.getColumnInt(j, 2);
            if (strET.equals(_strEntityType) && iID == _iEntityID) {
                return true;
            }
        }

        return false;
    }

    /**
     * executeAction
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @return
     *  @author David Bigelow
     */
    public Object executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {

		EntityList.checkDomain(_prof,this,m_elp); //RQ0713072645
		EntityList.checkDomain(_prof,this,m_elc); //RQ0713072645
		

        String strTraceBase = " LinkActionItem executeAction method";
        EntityItemException eie = new EntityItemException();
        Object oReturn = null;
 
        try {
            ReturnDataResultSet rdrs = null;
            ResultSet rs = null;
            ReturnRelatorKey rrk = null;
          //  ReturnID idNew = new ReturnID(0);
            ReturnStatus returnStatus = new ReturnStatus(-1);

            // Placeholders for dates
            String strNow = null;
            String strForever = null;
            String strEndOfDay = null;

            // Pull some profile info...
            //int iOpenID = _prof.getOPWGID();
            //int iTranID = _prof.getTranID();
            //int iSessionID = _prof.getSessionID();

            String strEnterprise = _prof.getEnterprise();
            //String strRoleCode = _prof.getRoleCode();
			//int iNLSID = _prof.getReadLanguage().getNLSID();
            // Some basic EntityType, EntityID objects
            //int iEntityID = 0;
            //int iEntity1ID = 0;
            int iEntity2ID = 0;

            // Some new place holders
            //int iEntity2IDNew = 0;
            //int iEntityIDNew = 0;

            Vector vctReturnRelatorKeys = new Vector();

            DatePackage dpNow = _db.getDates();

            String strEntityType = m_ml.getEntityType();
            String strEntity1Type = m_ml.getEntity1Type();
            String strEntity2Type = m_ml.getEntity2Type();

            m_olResult = new OPICMList();

            _db.debug(D.EBUG_DETAIL, strTraceBase);
            _db.test(m_elp.size() > 0, "104035 No Items are in the Parent EntityItems.");
            _db.test(m_elc.size() > 0, "104035 No Items are in the Child EntityItems.");

            // Lets Set some Dates, make sure we operate at the end of the day here..
            // What happens when we used the ValOn.. EffOn
            strNow = dpNow.getNow();
            strForever = dpNow.getForever();
            strEndOfDay = dpNow.getEndOfDay();

            // let get the VELock/Entity/Relator info
            m_pList = new VELockERList(_db, _prof, strEntity1Type);
            m_cList = new VELockERList(_db, _prof, strEntity2Type);
            
            //add by bing get the linklimit vaule of this linkaction
            _db.debug(D.EBUG_DETAIL, strEntityType + " linkaction  is " + getActionItemKey());
            _db.debug(D.EBUG_DETAIL, strEntityType + " LinkLimit  is " + m_linklimit); 
            long linklimit = Integer.parseInt(getLinkLimit());
            
            EntityGroup egR51Parent = null; // build only once
            EntityGroup egR51Child = null;
                                      
            // Lets build the Parent Child RelationShip
            for (int ii = 0; ii < m_elp.size(); ii++) {

            	String[] pVEArray = null;
            	EntityItem eiP = (EntityItem) m_elp.getAt(ii);
            	long amount = 0;
            	int amount2 = 0;
            	if (linklimit!=-1){
            		//add by bing here will loop check child entities and decide whether to do linkAction 
            		_db.debug(D.EBUG_DETAIL, "Root entity " + eiP.getEntityType() + " has linklimit!");
            		try {
            			rs = _db.callGBL5555A(returnStatus, strEnterprise, eiP.getEntityType(), eiP.getEntityID());
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
            		if(sizeContainer==null){
            			loadSizes(_db,_prof,eiP.getEntityType());
            		}
            		for (int iii = 0; iii < rdrs.size(); iii++) {
            			String relatedEntity = rdrs.getColumn(iii, 0).trim();
            			int count = rdrs.getColumnInt(iii, 1);
            			_db.debug(D.EBUG_DETAIL, "The gbl5555 return values are: " + relatedEntity + count);
            			amount += getEntitySize(_db, _prof,eiP.getEntityType(),relatedEntity)*count;
            		} 
            		_db.debug(D.EBUG_DETAIL, "amount value is" + amount);
            	} // end has link limit
            	
            	// We may need to look down the chain to get the right parent match
                if (!eiP.getEntityType().equals(strEntity1Type)) {
                    for (int ij = 0; ij < eiP.getDownLinkCount(); ij++) {
                        EntityItem eiPDown = (EntityItem) eiP.getDownLink(ij);
                        if (eiPDown.getEntityType().equals(strEntity1Type)) {
                            eiP = eiPDown;
                        }
                    }
                }

                // check first entity's VELock
                pVEArray = EANUtility.getVELockOwners(_db, _prof, eiP);
                if (pVEArray.length > 0 && (!isLinkable(m_pList, pVEArray, strEntity1Type, strEntityType))) {
                    EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, eiP.getEntityType(), eiP.getEntityID()), 
                    		"Unable to link because the entity is VE locked (ok)", eie); 
                    continue;
                }

                for (int iy = 0; iy < m_elc.size(); iy++) {
                    String[] cVEArray = null;
                    EntityItem eiC = (EntityItem) m_elc.getAt(iy);
                    if (getLinkType() == TYPE_MOVE) {
                        // for MOVE, we need to look at the relator
                        if (eiC.getEntityType().equals(strEntityType)) {
                            boolean b51 = false;

                            cVEArray = EANUtility.getVELockOwners(_db, _prof, eiC);
                            // if the relator is VE locked, we can't remove the link
                            if (cVEArray.length > 0) {
                                EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, eiC.getEntityType(), eiC.getEntityID()), 
                                		"Unable to link because the entity is VE locked (ok)", eie); 
                                continue;
                            } 
                            // check child entity's VE lock
                            EntityItem eiCDown = (EntityItem) eiC.getDownLink(0);
                            if (eiCDown != null && eiCDown.getEntityType().equals(strEntity2Type)) {
                            	cVEArray = EANUtility.getVELockOwners(_db, _prof, eiCDown);
                            	if (cVEArray.length > 0 && (!isLinkable(m_cList, cVEArray, strEntity2Type, strEntityType))) {
                                    EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, eiCDown.getEntityType(), eiCDown.getEntityID()),
                            				"Unable to link because the entity is VE locked (ok)", eie); 
                            		continue;
                            	}
                            }

                            rrk = new ReturnRelatorKey(strEntityType, m_ml.getNextRID(), strEntity1Type, eiP.getEntityID(), eiC.getEntityType(), eiC.getEntityID(), true);
                            try {
                            	//Rule51:is this a rule 51 entitytype?
                                rs = _db.callGBL0006(returnStatus, strEnterprise, rrk.getEntity1Type());
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
                            if (rdrs.size() > 0) {
                                b51 = true;
                            }

                            if (b51) {
                            	if(egR51Parent==null){
                            		egR51Parent = new EntityGroup(this, _db, _prof, strEntity1Type, "Edit");
                            	}
                            	if(egR51Child==null){
                            		egR51Child = new EntityGroup(this, _db, _prof, strEntity2Type, "Edit");
                            	}
                                if (egR51Parent.hasRule51Group()) {
                                    // _db.debug(D.EBUG_DETAIL,"Rule51 found for MetaLink:" + getKey());
                                    try {
                                        Rule51Group r51g = egR51Parent.getRule51Group().getCopy();
                                        EntityItem ei51Parent = new EntityItem(egR51Parent, _prof, _db, strEntity1Type, eiP.getEntityID());
                                        EntityItem ei51Child = new EntityItem(egR51Child, _prof, _db, strEntity2Type, eiC.getEntityID());
                                        EANAttribute att51 = ei51Parent.getAttribute(r51g.getAttributeCode());
                                        if (att51 != null) {
                                            r51g.setAttributeValue(att51.toString());
                                            r51g.setParentEntityID(eiP.getEntityID());
                                            r51g.addDomainEntities(ei51Child);
                                            rrk.setRule51Group(r51g);
                                        } else {
                                            _db.debug(D.EBUG_DETAIL, "error A in LinkActionItem.executeAction for Rule51: attribute is null!");
                                        }
                                    } catch (Exception exc) {
                                    	_db.debug(D.EBUG_DETAIL, "error B in LinkActionItem.executeAction for Rule51:" + exc.toString());
                                    	exc.printStackTrace(System.out);
                                    }
                                }
                            } // end rule51 group
                            vctReturnRelatorKeys.addElement(rrk);
                        } // end child matches relatortype
                    } else if (getLinkType() == TYPE_DEFAULT || getLinkType() == TYPE_COPY) {
                    	boolean bGo = false;

                    	// look down the child entity to get the right match
                    	if (!eiC.getEntityType().equals(strEntity2Type)) {
                    		for (int iz = 0; iz < eiC.getDownLinkCount(); iz++) {
                    			EntityItem eiCDown = (EntityItem) eiC.getDownLink(iz);
                    			if (eiCDown.getEntityType().equals(strEntity2Type)) {
                    				eiC = eiCDown;
                    			}
                    		}
                    	}

                    	// check VE Lock
                    	if (getLinkType() == TYPE_DEFAULT) {
                    		cVEArray = EANUtility.getVELockOwners(_db, _prof, eiC);
                    		if (cVEArray.length > 0 && (!isLinkable(m_cList, cVEArray, strEntity2Type, strEntityType))) {
                    			EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, eiC.getEntityType(), eiC.getEntityID()), 
                    					"Unable to link because the entity is VE locked (ok)", eie); 
                    			bGo = false;
                    		} else {
                    			bGo = true;
                    		}
                    	} else if (getLinkType() == TYPE_COPY) {
                    		// only need to check parent, because this type of link only copy child's attribute values.
                    		bGo = true;
                    	}

                    	if (bGo) {
                    		_db.debug(D.EBUG_DETAIL, "NOW bGo!");
                    		// check for RULE51
                    		if(egR51Parent==null){
                    			egR51Parent = new EntityGroup(this, _db, _prof, strEntity1Type, "Edit");
                    		}
                    		if(egR51Child==null){
                    			egR51Child = new EntityGroup(this, _db, _prof, strEntity2Type, "Edit");
                    		}

                    		rrk = new ReturnRelatorKey(strEntityType, m_ml.getNextRID(), strEntity1Type, eiP.getEntityID(), strEntity2Type, eiC.getEntityID(), true);

                    		if (egR51Parent.hasRule51Group()) {
                    			_db.debug(D.EBUG_DETAIL, "Here has Rule51Group!");
                    			try {
                    				Rule51Group r51g = egR51Parent.getRule51Group().getCopy();
                    				EntityItem ei51Parent = new EntityItem(egR51Parent, _prof, _db, strEntity1Type, eiP.getEntityID());
                    				EntityItem ei51Child = new EntityItem(egR51Child, _prof, _db, strEntity2Type, eiC.getEntityID());
                    				EANAttribute att51 = ei51Parent.getAttribute(r51g.getAttributeCode());
                    				_db.debug(D.EBUG_DETAIL, "Here has Rule51Group!" + ei51Parent.getDownLink().size() +  ei51Parent.getDownLink().toString() );
                    				if (att51 != null) {
                    					r51g.setAttributeValue(att51.toString());
                    					r51g.setParentEntityID(eiP.getEntityID());
                    					r51g.addDomainEntities(ei51Parent);
                    					r51g.addDomainEntity(ei51Child);
                    					rrk.setRule51Group(r51g);
                    				} else {
                    					_db.debug(D.EBUG_DETAIL, "error A in LinkActionItem.executeAction for Rule51: attribute is null!");
                    				}
                    			} catch (Exception exc) {
                    				_db.debug(D.EBUG_DETAIL, "error B in LinkActionItem.executeAction for Rule51:" + exc.toString());
                    				exc.printStackTrace(System.out);
                    			}
                    		}
                    		//add by bing here accumulate weights of the Child-entity. if amount bigger than linklimit, then it throws exception.                
                    		if(linklimit!= -1){
                    			if(sizeContainer==null){
                    				loadSizes(_db,_prof,eiP.getEntityType());
                    			}
                    			amount2 = amount2 + getEntitySize(_db, _prof,eiP.getEntityType(),strEntity2Type);
                    			if (amount + amount2 > linklimit&&getEntitySize(_db, _prof,eiP.getEntityType(),strEntity2Type)!=0){

                    				_db.debug(D.EBUG_DETAIL, "Root entity " + strEntity1Type + " relate to " + strEntity2Type + getEntitySize(_db, _prof,eiP.getEntityType(),strEntity2Type) + " has exceed the linklimit");	
                    				LockException le =  new LockException("Nothing added to the Translation Package because the request would exceed the maximum size. You may only add up to " + (linklimit-amount)/getEntitySize(_db, _prof,eiP.getEntityType(),strEntity2Type) + " more " + strEntity2Type + "(s) total to the Translation Package." );
                    				throw le;
                    			}
                    		}
                    		vctReturnRelatorKeys.addElement(rrk);
                        } // end all ok to here
                    } // end linktype=default or copy
                }// end child loop
            } // end parent loop
            if(egR51Parent != null){
            	egR51Parent.dereference();
            	egR51Parent=null;
            }       
            if(egR51Child != null){
            	egR51Child.dereference();
            	egR51Child=null;
            }
                 
            // Lets test the size of the link here... the (ok) at the end tells tony to not do an about/retry
            T.est(
                //(vctReturnRelatorKeys.size() * getCopyCount()) <= LINKLIMIT,  //RCQ297831
            	// "This link request generates over " + (vctReturnRelatorKeys.size() * getCopyCount()) + " Relationships.  You are only allowed to generate " + LINKLIMIT + " per transaction. Please reduce your selection and try again. (ok)");
                  (vctReturnRelatorKeys.size() * getCopyCount()) <= _prof.getLinkLimit(),
                "This link request generates over " + (vctReturnRelatorKeys.size() * getCopyCount()) + " Relationships.  You are only allowed to generate " + _prof.getLinkLimit() + " per transaction. Please reduce your selection and try again. (ok)");

            // check for the existence of entity2 before linking/copying
            if (m_bCheckExistence) {
                if (getLinkType() == TYPE_COPY || getLinkType() == TYPE_DEFAULT) {
                    for (int i = 0; i < vctReturnRelatorKeys.size(); i++) {
                        rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(i);
                        if (!isExist(_db, _prof, rrk.getEntity2Type(), rrk.getEntity2ID())) {
                        	EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, rrk.getEntity2Type(), rrk.getEntity2ID()),
                                	" The entity doesn't exist "+rrk.getEntity2Type()+rrk.getEntity2ID(), eie); 
                        }
                    }
                }

                if (eie.getErrorCount() > 0) {
                    throw eie;
                }
            }

            // OK.. here we go on the actual updates... and all the cases!!!
            if (getLinkType() == TYPE_MOVE) {
            	EANUtility.linkMove(_db, _prof, vctReturnRelatorKeys, m_olResult, strNow,eie);
            } else if (getLinkType() == TYPE_COPY) {
                _db.debug(D.EBUG_DETAIL, " TYPE_COPY ");
                Vector vctNetReturnRelatorKeys = new Vector();

                // do this here, EANUtility doesnt do it
                for (int ii = 0; ii < vctReturnRelatorKeys.size(); ii++) {
                	rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(ii);
                	_db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                	_db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

                	strEntity2Type = rrk.getEntity2Type();
                	iEntity2ID = rrk.getEntity2ID();

                	if (isQueueSourced()) {
                		if (!isEntityInGroupQueue(_db, strEnterprise, getQueueSourceName(), strEntity2Type, iEntity2ID, 0)) {
                			// Throw some kind of exception.. and skip it
                           	EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, strEntity2Type, iEntity2ID), 
                					"Unable to link because this child entity is no longer on the " + getQueueSourceName() + " Queue. (ok)", eie); 
                			continue;
                		} else {
                			setGroupQueueStatus(_db, strEnterprise, getQueueSourceName(), strEntity2Type, iEntity2ID, 0, 1);
                		}
                	}
                	vctNetReturnRelatorKeys.add(rrk);
                }
              
                if(vctNetReturnRelatorKeys.size()>0){
                	EANUtility.linkCopy(_db, _prof, vctNetReturnRelatorKeys, m_olResult, 
                			getOptionString(), getCopyCount(), strNow, strForever, strEndOfDay, eie);
                	vctNetReturnRelatorKeys.clear();
                }
            } else if (getLinkType() == TYPE_DEFAULT) {
                Vector vctNetReturnRelatorKeys = new Vector();

                // do this here, EANUtility doesnt do it
                for (int ii = 0; ii < vctReturnRelatorKeys.size(); ii++) {
                	rrk = (ReturnRelatorKey) vctReturnRelatorKeys.elementAt(ii);
                	_db.test(rrk.getEntity2Type() != null, "entity2Type is null");
                	_db.test(rrk.getEntity2ID() > 0, "entity2ID <= 0");

                	strEntity2Type = rrk.getEntity2Type();
                	iEntity2ID = rrk.getEntity2ID();

                	if (isQueueSourced()) {
                		if (!isEntityInGroupQueue(_db, strEnterprise, getQueueSourceName(), strEntity2Type, iEntity2ID, 0)) {
                			// Throw some kind of exception.. and skip it
                           	EANUtility.buildErrorMsg(_db, _prof, new EntityItem(null, _prof, strEntity2Type, iEntity2ID), 
                					"Unable to link because this child entity is no longer on the " + getQueueSourceName() + " Queue. (ok)", eie); 
                			continue;
                		} else {
                			setGroupQueueStatus(_db, strEnterprise, getQueueSourceName(), strEntity2Type, iEntity2ID, 0, 1);
                		}
                	}
                	vctNetReturnRelatorKeys.add(rrk);
                }
                
                if(vctNetReturnRelatorKeys.size()>0){
                    EANUtility.linkDefault(_db,_prof, vctNetReturnRelatorKeys, m_olResult, 
                    		getOptionString(), strNow, strForever, false,	true, eie);
                	vctNetReturnRelatorKeys.clear();
                }
            }

            oReturn = new Boolean(true);
            if (hasChainAction()) {
                if (!requireInput()) {
                    oReturn = executeChainAction(_db, _prof);
                }
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
            _db.commit();
        }

        if (eie.getErrorCount() > 0) {
            throw eie;
        }
        return oReturn;
    }

    public int getEntitySize(Database _db, Profile _prof,String rootEntitytype,String entitytype){
		if (sizeContainer==null){
			loadSizes(_db,_prof,rootEntitytype);
		}
    	Object value = null;        
    	value = sizeContainer.get(entitytype.trim());
    	if (value != null){
    		return Integer.parseInt(value.toString()); 
    	}else
    		return 0;			
    }
    
    public void loadSizes(Database _db, Profile _prof,String rootEntitytype){
    	Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
    	StringBuffer strb = new StringBuffer();
    	strb.append("select entitytype,size from opicm.linklimit where ");		
    	strb.append("enterprise = " + "'" + _prof.getEnterprise() + "'");
    	strb.append(" and root = " + "'" + rootEntitytype + "'");
    	
    	if(sizeContainer==null){
    		sizeContainer = new Hashtable();
    	}
    	try {

			try {
				//dont overwrite current connection _db.connect();
				con = _db.getPDHConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(strb.toString());
				while(rs.next()) {
					sizeContainer.put( rs.getString(1).trim(), new Integer(rs.getInt(2)));
				}
			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
	        	if (rs != null){
	        		rs.close();
	        		rs = null;
	        	}
	            
	            _db.commit();
	            _db.freeStatement();
	            _db.isPending();
	        }
		} catch (SQLException se) {
			_db.debug(D.EBUG_DETAIL, "Cannot run get entity size from opicm.linklimit number had an SQL error... ");
			se.printStackTrace();
			//System.exit(-1); do not bring mw down
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
        strbResult.append("LinkActionItem:" + super.dump(_bBrief));
        strbResult.append(NEW_LINE + ":MetaLink:");
        if (getMetaLink() == null) {
            strbResult.append(":**null**:");
        } else {
            strbResult.append(getMetaLink().getKey());
        }
        return strbResult.toString();
    }

    //
    // ACCESSORS
    //

    /**
     * (non-Javadoc)
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "Link";
    }

    /**
     * isWizardCapable
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isWizardCapable() {
        return m_bWizard;
    }

    /**
     * setMetaLink
     *
     * @param _ml
     *  @author David Bigelow
     */
    protected void setMetaLink(MetaLink _ml) {
        m_ml = _ml;
        m_ml.setParent(this);
    }
    /*********
     * meta ui must update action
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws MiddlewareRequestException 
     */
    public void updateMetaLink(EANMetaFoundation _emf, Database _db, Profile _prof, String _strEntityType) 
    throws MiddlewareRequestException, SQLException, MiddlewareException
    {
    	setMetaLink(new MetaLink(_emf, _db, _prof, _strEntityType));
    }
    public void setActionClass(){
    	setActionClass("Link");
    }
    public void updateAction(String strTarget, boolean canlink, boolean cancopy){
        setTarget(strTarget);
		setLink(canlink);
		setLinkCopy(cancopy);
    } 
    
    /**
     * setTarget
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setTarget(String _str) {
        m_bUseCart = (_str.equals("CART"));
        m_bUseParent = (_str.equals("PARENT"));
    }

    /**
     * getTarget
     *
     * @return
     *  @author David Bigelow
     */
    public String getTarget() {
        return (m_bUseCart ? "CART" : "PARENT");
    }

    /**
     * setLinkCopy
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setLinkCopy(boolean _b) {
        m_bLinkCopy = _b;
    }

    /**
     * setLink
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setLink(boolean _b) {
        m_bLink = _b;
    }
    protected void setLinkLimit(String linklimit){
   	
   	m_linklimit = linklimit;
    }
   public String getLinkLimit() {
      return m_linklimit;
   }
   
    /**
     * canLinkCopy
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canLinkCopy() {
        return m_bLinkCopy;
    }

    /**
     * canLink
     *
     * @return
     *  @author David Bigelow
     */
    public boolean canLink() {
        return m_bLink;
    }

    /**
     * setOppSelect
     *
     * @param _b
     *  @author David Bigelow
     */
    protected void setOppSelect(boolean _b) {
        m_bOppSelect = _b;
    }

    /**
     * isOppSelect
     *
     * @return
     *  @author David Bigelow
     */
    public boolean isOppSelect() {
        return m_bOppSelect;
    }

    /**
     * getLinkType
     *
     * @return
     *  @author David Bigelow
     */
    public int getLinkType() {
        return m_iType;
    }

    /**
     * setLinkType
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setLinkType(int _i) {
        m_iType = _i;
    }

    /**
     * getOption
     *
     * @return
     *  @author David Bigelow
     */
    public int getOption() {
        return m_iOption;
    }

    /**
     * setOption
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setOption(int _i) {
        m_iOption = _i;
    }

    private String getOptionString(){
        String m_strOption=EANUtility.OPT_DEFAULT_STR;
//      tie into EANUtility definition
        switch(m_iOption){
        case OPT_REPLACEALL:
        	m_strOption = EANUtility.OPT_REPLACEALL_STR;
        	break;
        case OPT_NODUPES:
        	m_strOption = EANUtility.OPT_NODUPES_STR;
        	break;
        default:
        	m_strOption = EANUtility.OPT_DEFAULT_STR;
        break;		
        }
   
        return m_strOption;
    }
    /**
     * getCopyCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getCopyCount() {
        return m_iCopyCount;
    }

    /**
     * setCopyCount
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setCopyCount(int _i) {
        m_iCopyCount = _i;
    }

    /*
    * Retrieve the set of Parents out of the Cart Object when
    * Attempting to link (if true)
    */
    /**
     * useCart
     *
     * @return
     *  @author David Bigelow
     */
    public boolean useCart() {
        return m_bUseCart;
    }

    /*
    * Retrieve the set of Parents out of the ParentEntityGroup when
    * Attempting to link (if true)
    */
    /**
     * useParents
     *
     * @return
     *  @author David Bigelow
     */
    public boolean useParents() {
        return m_bUseParent;
    }

    /*
    * Retrieve the MetaLink object associated with this action
    */
    /**
     * getMetaLink
     *
     * @return
     *  @author David Bigelow
     */
    public MetaLink getMetaLink() {
        return m_ml;
    }

    protected boolean updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        try {
            //lets get a fresh object from the database
            LinkActionItem lnk_db = new LinkActionItem(null, _db, getProfile(), getActionItemKey());
            boolean bNewAction = false;
            //check for new
            if (lnk_db.getActionClass() == null) {
                bNewAction = true;
            }

            //EXPIRES
            if (_bExpire && !bNewAction) {
                //Link
                if (lnk_db.getMetaLink() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Link", lnk_db.getMetaLink().getEntityType());

                } else {
                    _db.debug(D.EBUG_ERR, "LinkActionItem.updatePdhMeta:MetaLink is null for \"" + lnk_db.getActionItemKey() + "\"");
                }
                //Target
                if (lnk_db.getTarget() != null) {
                    updateActionAttribute(_db, true, "TYPE", "Target", lnk_db.getTarget());
                }
                //canLink
                updateActionAttribute(_db, true, "TYPE", "CanLink", (lnk_db.canLink() ? "Y" : "N"));
//              canLink
                updateActionAttribute(_db, true, "TYPE", "LinkLimit", lnk_db.getLinkLimit());
                //canCopy
                updateActionAttribute(_db, true, "TYPE", "CanCopy", (lnk_db.canLinkCopy() ? "Y" : "N"));
            } else {
                //Link
                if (lnk_db.getMetaLink() != null && this.getMetaLink() != null) {
                    String strLink_db = lnk_db.getMetaLink().getEntityType();
                    String strLink_this = this.getMetaLink().getEntityType();
                    //only need to insert since this will vary only by linkvalue
                    if (bNewAction || !strLink_db.equals(strLink_this)) {
                        updateActionAttribute(_db, false, "TYPE", "Link", strLink_this);
                    }
                } else if (lnk_db.getMetaLink() == null && this.getMetaLink() != null) {
                    updateActionAttribute(_db, false, "TYPE", "Link", getMetaLink().getEntityType());
                } else {
                    _db.debug(D.EBUG_ERR, "LinkActionItem.updatePdhMeta:MetaLink is null for \"" + this.getActionItemKey() + "\"");
                }

                //Target - shouldnt ever be null
                if (bNewAction || lnk_db == null || (!this.getTarget().equals(lnk_db.getTarget()))) {
                    //only need to insert since this will vary only by linkvalue
                    updateActionAttribute(_db, false, "TYPE", "Target", this.getTarget());
                }

                //CanLink
                if (bNewAction || lnk_db.canLink() != this.canLink()) {
                    updateActionAttribute(_db, false, "TYPE", "CanLink", (this.canLink() ? "Y" : "N"));
                }

                //CanCopy
                if (bNewAction || lnk_db.canLinkCopy() != this.canLinkCopy()) {
                    updateActionAttribute(_db, false, "TYPE", "CanCopy", (this.canLinkCopy() ? "Y" : "N"));
                }
            }

        } catch (SQLException sqlExc) {
            _db.debug(D.EBUG_ERR, "LinkActionItem 361 " + sqlExc.toString());
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

    private boolean isEntityInGroupQueue(Database _db, String _strEnterprise, String _strQueue, String _strEntityType, int _iEntityID, int _iCurrentStatus) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {

        ResultSet rs = null;
        try {
            int ii = 0;
            try {
                rs = _db.callGBL7423(new ReturnStatus(-1), _strEnterprise, _strQueue, _strEntityType, _iEntityID, _iCurrentStatus);

                if (rs.next()) {
                    ii = rs.getInt(1);
                }
            } finally {
				if (rs !=null){
	                rs.close();
	                rs = null;
				}
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

            return (ii > 0);

        } finally {
            _db.freeStatement();
            _db.isPending();
        }

    }
    private void setGroupQueueStatus(Database _db, String _strEnterprise, String _strQueue, String _strEntityType, int _iEntityID, int _iCurrentStatus, int _iNextStatus) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        try {
            _db.debug(D.EBUG_DETAIL, "calling GBL7422(" + _strEnterprise + ":" + _strQueue + ":" + _strEntityType + ":" + _iEntityID + ":" + _iCurrentStatus + ":" + _iNextStatus);
            _db.callGBL7422(new ReturnStatus(-1), _strEnterprise, _strQueue, _strEntityType, _iEntityID, _iCurrentStatus, _iNextStatus);
        } finally {
            _db.commit();
            _db.freeStatement();
            _db.isPending();
        }
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
     * setQueueSourceName
     *
     * @param _str
     *  @author David Bigelow
     */
    protected void setQueueSourceName(String _str) {
        m_strQueueSource = _str;
    }

    /**
     * getQueueSourceName
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueSourceName() {
        return m_strQueueSource;
    }

    private EntityItem[] getEntityItemsForChainAction(Profile _prof, OPICMList _ol) throws MiddlewareRequestException {
        Vector v = new Vector();
        EntityItem[] aei = null;
        for (int i = 0; i < _ol.size(); i++) {
            ReturnRelatorKey rrk = (ReturnRelatorKey) _ol.getAt(i);
            EntityItem ei = new EntityItem(null, _prof, rrk.getEntity2Type(), rrk.getEntity2ID());
            v.addElement(ei);
        }
        aei = new EntityItem[v.size()];
        v.toArray(aei);
        return aei;

    }

    private Object executeChainAction(Database _db, Profile _prof) throws SQLException, MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
        String strTraceBase = "LinkActionItem executeChainAction";
        _db.debug(D.EBUG_DETAIL, strTraceBase + ":" + getKey());

        try {
            EntityItem[] aei = getEntityItemsForChainAction(_prof, m_olResult);

            if (aei == null || aei.length <= 0) {
                System.out.println("LinkActionItem executeChainAction aei is null: ");
                return null;
            }

            if (isChainEditAction()) {
                EditActionItem eai = (EditActionItem) getChainActionItem(_db, null, _prof);
                return EntityList.getEntityList(_db, _prof, eai, aei);
            } else if (isChainNavAction()) {
                NavActionItem nai = (NavActionItem) getChainActionItem(_db, null, _prof);
                return EntityList.getEntityList(_db, _prof, nai, aei);
            } else if (isChainWhereUsedAction()) {
                WhereUsedActionItem wai = (WhereUsedActionItem) getChainActionItem(_db, null, _prof);
                wai.setEntityItems(aei);
                return wai.exec(_db, _prof);
            } else if (isChainMatrixAction()) {
                MatrixActionItem mai = (MatrixActionItem) getChainActionItem(_db, null, _prof);
                mai.setEntityItems(aei);
                return mai.exec(_db, _prof);
            } else if (isChainExtractAction()) {
                ExtractActionItem xai = (ExtractActionItem) getChainActionItem(_db, null, _prof);
                return EntityList.getEntityList(_db, _prof, xai, aei);
            } else if (isChainLockAction()) {
                LockActionItem lai = (LockActionItem) getChainActionItem(_db, null, _prof);
                lai.setEntityItems(aei);
                lai.exec(_db, _prof);
                return new Boolean(true);
            } else if (isChainWorkflowAction()) {
                WorkflowActionItem wfai = (WorkflowActionItem) getChainActionItem(_db, null, _prof);
                wfai.setEntityItems(aei);
                wfai.exec(_db, _prof);
                return new Boolean(true);
            }
        } catch (RemoteException re) {
            re.printStackTrace();
        }
        return null;
    }

    /**
     * executeLink
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException
     * @throws COM.ibm.opicmpdh.middleware.LockException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     * @return
     *  @author David Bigelow
     */
    public Vector executeLink(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException, LockException, WorkflowException {
        Vector vReturn = new Vector();
        Object o = executeAction(_db, _prof);
        vReturn.addElement(o);
        vReturn.addElement(m_olResult);
        return vReturn;
    }

    protected void setRelatorType(String strRelatorType) {
        m_strRelatorType = strRelatorType;
    }

    protected String getRelatorType() {
        return m_strRelatorType;
    }
}
