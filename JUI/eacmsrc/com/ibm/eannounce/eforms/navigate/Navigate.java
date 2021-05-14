// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: Navigate.java,v $
 * Revision 1.12  2010/08/05 21:13:03  wendy
 * prevent hang if exception in finish() for navigation
 *
 * Revision 1.11  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.10  2009/04/16 17:54:42  wendy
 * Cleanup history
 *
 * Revision 1.9  2008/08/08 21:52:12  wendy
 * CQ00006067-WI : LA CTO - More support for QueryAction
 *
 * Revision 1.8  2008/08/04 14:07:47  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.7  2008/04/29 19:18:21  wendy
 * MN35270066 VEEdit rewrite
 *
 * Revision 1.6  2008/02/21 19:18:52  wendy
 * Add access to change history for relators
 *
 * Revision 1.5  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.4  2007/08/10 11:51:40  wendy
 * MN32759321 PDGaction was not handling assoc
 *
 * Revision 1.3  2007/08/03 11:30:34  wendy
 * RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs
 *
 * Revision 1.2  2007/06/19 18:18:38  wendy
 * Attempt to avoid hang condition when action fails
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.10  2006/11/09 15:51:07  tony
 * more monitor logic
 *
 * Revision 1.9  2006/06/19 19:32:57  tony
 * added timing logic for refresh functionality
 *
 * Revision 1.8  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.7  2006/03/07 22:23:21  tony
 * JT_20060307 removed unnecessary middleware classes that
 * are obsolete.
 *
 * Revision 1.6  2006/02/23 18:59:53  tony
 * added comment
 *
 * Revision 1.5  2005/10/19 17:20:19  tony
 * added dump to save logic
 *
 * Revision 1.4  2005/10/13 20:11:22  joan
 * adjust for PDG
 *
 * Revision 1.3  2005/09/20 17:48:04  tony
 * TIR USRO-R-PKUR-6GEJEY
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.64  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.63  2005/09/01 20:35:12  tony
 * *** empty log message ***
 *
 * Revision 1.62  2005/08/24 14:01:54  tony
 * MN25076829
 *
 * Revision 1.61  2005/08/18 21:09:43  tony
 * stubbed out ve edit
 *
 * Revision 1.60  2005/08/11 20:35:31  tony
 * catalog changes
 *
 * Revision 1.59  2005/08/10 17:08:46  tony
 * fixed catalog logic
 *
 * Revision 1.58  2005/05/16 18:54:55  tony
 * PKUR-6CFDY6
 *
 * Revision 1.57  2005/05/16 18:22:35  tony
 * PKUR-6CCBXQ
 *
 * Revision 1.56  2005/05/16 17:53:24  tony
 * PKUR-6CCBXQ
 *
 * Revision 1.55  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.54  2005/03/07 17:54:21  tony
 * MN22319406
 *
 * Revision 1.53  2005/02/23 17:50:36  tony
 * 7342
 *
 * Revision 1.52  2005/02/23 17:30:30  tony
 * Change Request 7342
 *
 * Revision 1.51  2005/02/22 23:01:45  tony
 * 22927316
 *
 * Revision 1.50  2005/02/15 20:25:36  tony
 * CR_1124045346
 *
 * Revision 1.49  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.48  2005/02/03 18:03:45  tony
 * 696Q65
 *
 * Revision 1.47  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.46  2005/02/01 15:35:05  tony
 * added ChangeGroup Name Indicator Logic
 *
 * Revision 1.45  2005/02/01 00:27:56  tony
 * JTest Second Pass
 *
 * Revision 1.44  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.43  2005/01/20 17:09:09  tony
 * USRO-R-JSTT-68RKKP activate menu items
 *
 * Revision 1.42  2005/01/18 21:38:50  tony
 * USRO-R-JSTT-68RKKP
 *
 * Revision 1.41  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.40  2004/11/02 20:27:15  tony
 * added code stub for USRO-R-DMKR-668L6T,
 * will require a CR for investigation and completion.
 *
 * Revision 1.39  2004/10/11 20:58:16  tony
 * improved debug functionality.
 *
 * Revision 1.38  2004/10/09 17:41:18  tony
 * improved debugging functionality.
 *
 * Revision 1.37  2004/09/30 17:35:34  tony
 * USRO-R-OGAA-652Q3N
 *
 * Revision 1.36  2004/09/27 16:31:28  tony
 * TIR USRO-R-OGAA-652Q3N
 * create action was not being initialized properly for reuse.
 *
 * Revision 1.35  2004/09/23 16:24:21  tony
 * improved logic to acquire focus to textbox on
 * message panel input functionality.
 *
 * Revision 1.34  2004/09/21 21:03:09  tony
 * TIR USRO-R-OGAA-652Q3N
 *
 * Revision 1.33  2004/09/15 22:45:49  tony
 * updated blue pages add logic
 *
 * Revision 1.32  2004/09/09 19:42:42  tony
 * DWB 20040908
 * adjusted logic for 3.0a functionality.
 *
 * Revision 1.31  2004/08/26 16:26:36  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.30  2004/08/04 17:49:15  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.29  2004/08/03 17:58:22  tony
 * changeGroup enhancement
 *
 * Revision 1.28  2004/08/02 21:38:25  tony
 * ChangeGroup modification.
 *
 * Revision 1.27  2004/07/15 17:41:11  tony
 * added timing comments
 * CR_062204695
 *
 * Revision 1.26  2004/07/02 14:37:37  tony
 * added inactive code for parentSearch.  Will need to be
 * commented in when code is approved.
 *
 * Revision 1.25  2004/06/25 17:31:19  tony
 * bluePageCreate
 *
 * Revision 1.24  2004/06/24 18:29:19  tony
 * parentless search
 *
 * Revision 1.23  2004/06/24 17:55:27  tony
 * parentless search capability added.
 *
 * Revision 1.22  2004/06/22 18:05:35  tony
 * cr_2115
 *
 * Revision 1.21  2004/06/16 20:23:14  tony
 * copyAction
 *
 * Revision 1.20  2004/05/21 20:53:31  tony
 * sg13 update for standalone removed peercreate2.
 *
 * Revision 1.19  2004/05/19 15:13:09  tony
 * 53802
 *
 * Revision 1.18  2004/05/18 17:38:13  tony
 * 53803 spec out
 *
 * Revision 1.17  2004/05/18 14:41:19  tony
 * 53782
 *
 * Revision 1.16  2004/05/07 19:40:51  tony
 * updated logic for BUI compatibility.
 *
 * Revision 1.15  2004/04/30 17:24:55  tony
 * 53774
 *
 * Revision 1.14  2004/04/26 21:28:55  tony
 * 53823
 *
 * Revision 1.13  2004/04/22 18:01:18  tony
 * repaired grab entityid replay logic.
 *
 * Revision 1.12  2004/04/20 14:30:16  tony
 * cr_1129034004
 * updated logic to improve cancel functionality.
 *
 * Revision 1.11  2004/04/06 18:56:04  tony
 * adjusted logic so that update menu will only be called once
 * tagged with test_20040406.
 *
 * Revision 1.10  2004/04/01 17:12:18  tony
 * cr_1129034004
 *
 * Revision 1.9  2004/03/30 16:30:27  tony
 * 6022 refined
 *
 * Revision 1.8  2004/03/29 17:36:37  tony
 * cr_209046022
 *
 * Revision 1.7  2004/03/26 23:27:22  tony
 *  cr_209046022 -- first pass
 *
 * Revision 1.6  2004/03/26 20:46:28  tony
 * cr_812022711
 *
 * Revision 1.5  2004/03/25 23:37:19  tony
 * cr_216041310
 *
 * Revision 1.4  2004/03/04 22:26:09  tony
 * 53583
 *
 * Revision 1.3  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.2  2004/02/20 22:17:59  tony
 * chatAction
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.128  2004/01/09 18:26:51  tony
 * cr_1210035324
 *
 * Revision 1.127  2004/01/09 00:42:44  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.126  2004/01/06 20:03:24  tony
 * 53502
 *
 * Revision 1.125  2004/01/06 19:18:15  tony
 * 53500
 *
 * Revision 1.124  2004/01/05 22:23:01  tony
 * 53496
 *
 * Revision 1.123  2003/12/22 21:38:17  tony
 * 53451
 *
 * Revision 1.122  2003/12/19 19:23:00  tony
 * acl_20031219
 * fixed null pointer when adding to cart with whereused
 * from matrix.
 *
 * Revision 1.121  2003/12/10 16:32:20  tony
 * 53367
 *
 * Revision 1.120  2003/12/02 19:15:11  joan
 * 53263
 *
 * Revision 1.119  2003/12/01 22:31:31  tony
 * pcd form fix.
 *
 * Revision 1.118  2003/11/24 21:26:04  tony
 * 53171
 *
 * Revision 1.117  2003/11/12 21:32:34  tony
 * 52993
 *
 * Revision 1.116  2003/11/04 17:35:25  tony
 * 52775
 *
 * Revision 1.115  2003/10/30 23:21:24  tony
 * 52821
 *
 * Revision 1.114  2003/10/30 19:04:32  tony
 * 52796
 *
 * Revision 1.113  2003/10/29 18:17:57  tony
 * 52727
 *
 * Revision 1.112  2003/10/29 00:27:45  tony
 * 52573
 * removed System.out. statements.
 *
 * Revision 1.111  2003/10/27 22:16:35  tony
 * 52732
 *
 * Revision 1.110  2003/10/24 21:29:22  tony
 * 52715
 *
 * Revision 1.109  2003/10/17 20:27:23  tony
 * 52610
 *
 * Revision 1.108  2003/10/17 18:02:44  tony
 * 52614
 *
 * Revision 1.107  2003/10/16 16:13:46  tony
 * 52523
 *
 * Revision 1.106  2003/10/13 21:01:08  tony
 * 52527
 *
 * Revision 1.105  2003/09/30 16:53:55  tony
 * kehrli_20030929
 *
 * Revision 1.104  2003/09/30 16:35:48  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.103  2003/09/12 16:13:48  tony
 * 52189
 *
 * Revision 1.102  2003/09/11 17:24:29  tony
 * 52158
 *
 * Revision 1.101  2003/08/29 16:16:05  tony
 * 51994
 *
 * Revision 1.100  2003/08/28 22:53:42  tony
 * memory update.
 *
 * Revision 1.99  2003/08/28 18:33:51  tony
 * 51975
 *
 * Revision 1.98  2003/08/28 17:50:22  tony
 * added homeEnabled logic.
 *
 * Revision 1.97  2003/08/27 20:42:03  tony
 * 51949
 *
 * Revision 1.96  2003/08/25 21:27:11  tony
 * 51922
 *
 * Revision 1.95  2003/08/25 16:52:29  tony
 * CR0805036452
 *
 * Revision 1.94  2003/08/20 19:09:26  tony
 * update peer_create to allow for standalone create to
 * match peer_create.
 *
 * Revision 1.93  2003/08/15 15:38:08  tony
 * cr_0805036452
 *
 * Revision 1.92  2003/08/13 21:02:37  tony
 * 51715
 *
 * Revision 1.91  2003/08/13 16:39:09  joan
 * 51714
 *
 * Revision 1.90  2003/08/06 21:23:38  joan
 * 51563
 *
 * Revision 1.89  2003/08/06 19:47:00  joan
 * 51449
 *
 * Revision 1.88  2003/08/01 17:39:11  joan
 * 51609
 *
 * Revision 1.87  2003/07/30 16:07:14  tony
 * 51563 -- spec'd out fix for fb
 *
 * Revision 1.86  2003/07/29 18:25:44  tony
 * updated refresh logic repaired for peer create.
 *
 * Revision 1.85  2003/07/29 17:14:21  tony
 * 51555
 *
 * Revision 1.84  2003/07/29 16:58:28  tony
 * 51555
 *
 * Revision 1.83  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.82  2003/07/22 15:44:31  tony
 * 51497
 *
 * Revision 1.81  2003/07/18 18:59:10  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.80  2003/07/17 22:37:59  tony
 * updated logic fo peer create
 *
 * Revision 1.79  2003/07/10 13:59:08  tony
 * cleaned-up code
 *
 * Revision 1.78  2003/07/09 15:09:00  tony
 * 51417 -- (did not tag) updated menu creation logic
 * to use the properties file instead of constant values.
 *
 * Revision 1.77  2003/07/03 16:55:24  tony
 * adjusteed whereused logic to support scripting.
 *
 * Revision 1.76  2003/07/03 16:38:04  tony
 * improved scripting logic.
 *
 * Revision 1.75  2003/07/03 00:41:39  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.74  2003/07/02 21:42:31  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.73  2003/07/01 17:12:02  tony
 * 51392
 * 51395
 *
 * Revision 1.72  2003/06/26 15:47:04  tony
 * updated messaging to clarify blank/empty picklists
 *
 * Revision 1.71  2003/06/25 16:17:22  tony
 * 51325
 *
 * Revision 1.70  2003/06/19 18:29:25  tony
 * 51298
 * 51318
 *
 * Revision 1.69  2003/06/19 16:09:41  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.68  2003/06/17 19:47:43  joan
 * work on SPDGActionItem
 *
 * Revision 1.67  2003/06/16 17:22:54  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.66  2003/06/13 16:08:30  tony
 * 1.2h update, singleCart and various fixes.
 *
 * Revision 1.65  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.64  2003/06/10 19:53:50  tony
 * 51199
 *
 * Revision 1.63  2003/06/10 16:46:48  tony
 * 51260
 *
 * Revision 1.62  2003/06/09 21:12:49  tony
 * 51199
 *
 * Revision 1.61  2003/06/09 17:34:37  tony
 * 51199
 *
 * Revision 1.60  2003/06/06 23:26:51  tony
 * updated logic so that temp files
 * are removed when application closes.
 * they were hanging out.
 *
 * Revision 1.59  2003/06/05 22:12:12  tony
 * 51160
 *
 * Revision 1.58  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.57  2003/06/03 20:28:41  tony
 * 51109
 *
 * Revision 1.56  2003/06/02 19:45:40  tony
 * 51052
 *
 * Revision 1.55  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.54  2003/05/30 21:09:23  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.53  2003/05/29 21:31:12  joan
 * fix fb
 *
 * Revision 1.52  2003/05/29 21:20:45  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.51  2003/05/29 19:05:19  tony
 * updated report launching.
 *
 * Revision 1.50  2003/05/28 17:04:03  tony
 * 50942
 *
 * Revision 1.49  2003/05/27 21:22:16  tony
 * updated url logic.
 *
 * Revision 1.48  2003/05/23 17:07:39  tony
 * 50892
 *
 * Revision 1.47  2003/05/22 21:28:12  tony
 * updated reporting logic.
 *
 * Revision 1.46  2003/05/22 16:23:13  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.45  2003/05/21 17:05:01  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.44  2003/05/21 15:36:31  tony
 * updated view persistant lock to allow for ability
 * to view multiple lock tabs with multiple users.
 *
 * Revision 1.43  2003/05/20 22:51:49  tony
 * 24286 -- no menu short-cut repitition.
 *
 * Revision 1.42  2003/05/16 19:54:16  tony
 * 50715
 *
 * Revision 1.41  2003/05/15 22:10:05  tony
 * cleaned-up code to improve persistant lock
 * functionality.
 *
 * Revision 1.40  2003/05/15 18:49:50  tony
 * updated persistant lock functionality.
 *
 * Revision 1.39  2003/05/15 16:23:38  tony
 * updated messaging logic on search.
 *
 * Revision 1.38  2003/05/14 19:01:34  tony
 * added trace statements
 *
 * Revision 1.37  2003/05/09 16:51:28  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.36  2003/05/07 20:10:04  tony
 * 50568 clean-up to compile
 *
 * Revision 1.35  2003/05/07 20:07:05  tony
 * 50568
 *
 * Revision 1.34  2003/05/06 20:40:18  joan
 * 50530
 *
 * Revision 1.33  2003/05/06 18:59:23  joan
 * 50530
 *
 * Revision 1.32  2003/05/05 22:54:21  tony
 * 50515
 *
 * Revision 1.31  2003/05/02 20:05:55  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.30  2003/05/02 17:54:19  tony
 * 50496
 *
 * Revision 1.29  2003/05/01 20:13:59  tony
 * 50485
 *
 * Revision 1.28  2003/04/29 20:03:58  tony
 * SBRActionItem remove implemented per a middleware
 * incompatibility issue.
 *
 * Revision 1.27  2003/04/28 16:27:20  tony
 * updated logic to adjust action enablement based
 * on if the current location contains data or not.
 *
 * Revision 1.26  2003/04/23 21:00:06  tony
 * added functionality to prevent a duplicate whereused from
 * being displayed.
 *
 * Revision 1.25  2003/04/18 20:10:28  tony
 * added tab placement to preferences.
 *
 * Revision 1.24  2003/04/18 17:01:28  tony
 * fixed refresh (F5) after a back causing reset when you
 * perform the refresh(F5) function.
 *
 * Revision 1.23  2003/04/17 15:06:37  tony
 * enhanced f1 help logic on navigate.
 *
 * Revision 1.22  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import com.ibm.eannounce.eserver.ChatAction;
import com.ibm.eannounce.*;
import com.ibm.eannounce.dialogpanels.*;
import com.ibm.eannounce.eforms.*;
import com.ibm.eannounce.eforms.edit.*;
import com.ibm.eannounce.eforms.action.*;
import com.ibm.eannounce.eforms.table.*;
import com.ibm.eannounce.eforms.toolbar.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.exception.*;
import com.ibm.eannounce.epanels.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;
import java.awt.event.*;
import java.awt.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.Border;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class Navigate extends EObject implements ChangeListener, PropertyChangeListener, EAccessConstants {
    /**
     * MAXIMUM_NAVIGATE_HISTORY
     */
    public static final int MAXIMUM_NAVIGATE_HISTORY = 50;

    /**
     * DEFAULT
     */
    public static final int DEFAULT = -1;
    /**
     * ACTION_TREE
     */
    public static final int ACTION_TREE = 0;
    /**
     * ACTION_BOX
     */
    public static final int ACTION_BOX = 1;
    /**
     * ACTION_LIST
     */
    public static final int ACTION_LIST = 2;
    /**
     * HISTORY_BOX
     */
    public static final int HISTORY_BOX = 0;
    /**
     * HISTORY_LIST
     */
    public static final int HISTORY_LIST = 1;
    /**
     * DATA_SINGLE
     */
    public static final int DATA_SINGLE = 0;
    /**
     * DATA_DOUBLE
     */
    public static final int DATA_DOUBLE = 1;
    /**
     * DATA_FORM
     */
    public static final int DATA_FORM = 2;
    /**
     * DATA_TREE
     */
    public static final int DATA_TREE = 3;
    /**
     * SINGLE_CLICK
     */
    public static final int SINGLE_CLICK = 1;
    /**
     * DOUBLE_CLICK
     */
    public static final int DOUBLE_CLICK = 2;

    private NavHist history = null;
    private NavData data = null;
    private NavAction action = null;
    private NavCartDialog cart = null;
    private NavPick pick = null;
    private EntityList navList = null;
    private SerialObjectController nsoController = null;
    //private NavSerialObject nso = null;
    private static UIPreferences oPref = null;
    private EForm nf = null;
    private ENavForm parent = null;

    //private Font navFont = null;
    private boolean bypass = true;

    //private EComparator oComp = new EComparator(true); //20011102

    private EMenubar menu = null;
    private EannounceToolbar tBar = null;
    private EPopupMenu popup = null;

    private int opwgID = -1;
    private int sessID = -1;

    private String dir = TEMP_DIRECTORY;
    private int iSide = -1;

    private FocusListener fListener = null;
    private boolean bRefresh = false;

    private ChooserPanel choose = null;

    /**
     * navigate
     * @param _parent
     * @param _iSide
     * @author Anthony C. Liberto
     */
    public Navigate(ENavForm _parent, int _iSide) {
        Profile prof = null;
        parent = _parent;
        prof = _parent.getProfile(); //51392
        nsoController = new SerialObjectController(prof);
        opwgID = prof.getOPWGID();
        sessID = prof.getSessionID();
        iSide = _iSide;
        oPref = UIPreferences.readDatabaseObject();
    }

    /**
     * navigate
     * @param _nfp
     * @param _iSide
     * @param _nf
     * @author Anthony C. Liberto
     */
    public Navigate(ENavForm _nfp, int _iSide, EForm _nf) {
        this(_nfp, _iSide);
        setForm(_nf);
        build(HISTORY_BOX, DATA_SINGLE, ACTION_LIST, DOUBLE_CLICK, true);
    }

    /**
     * navigate
     * @param _nfp
     * @param _iSide
     * @param _nf
     * @param _histType
     * @param _dataType
     * @param _actionType
     * @author Anthony C. Liberto
     */
    public Navigate(ENavForm _nfp, int _iSide, EForm _nf, int _histType, int _dataType, int _actionType) {
        this(_nfp, _iSide, _nf, _histType, _dataType, _actionType, DOUBLE_CLICK, true);
    }

    /**
     * navigate
     * @param _nfp
     * @param _iSide
     * @param _nf
     * @param _histType
     * @param _dataType
     * @param _actionType
     * @param _actionClicks
     * @author Anthony C. Liberto
     */
    public Navigate(ENavForm _nfp, int _iSide, EForm _nf, int _histType, int _dataType, int _actionType, int _actionClicks) {
        this(_nfp, _iSide, _nf, _histType, _dataType, _actionType, _actionClicks, true);
    }

    /**
     * navigate
     * @param _nfp
     * @param _iSide
     * @param _nf
     * @param _histType
     * @param _dataType
     * @param _actionType
     * @param _actionClicks
     * @param _load
     * @author Anthony C. Liberto
     */
    public Navigate(ENavForm _nfp, int _iSide, EForm _nf, int _histType, int _dataType, int _actionType, int _actionClicks, boolean _load) {
        this(_nfp, _iSide);
        setForm(_nf);
        build(_histType, _dataType, _actionType, _actionClicks, _load);
    }

    /**
     * updateTabPlacement
     * @param _revalidate
     * @author Anthony C. Liberto
     */
    public void updateTabPlacement(boolean _revalidate) {
        if (data != null) {
            data.updateTabPlacement(_revalidate);
        }
    }

    /**
     * getTagDisplay
     * @return
     * @author Anthony C. Liberto
     */
    public String getTagDisplay() {
        if (navList != null) {
            return navList.getTagDisplay();
        }
        return null;
    }

    /**
     * getSide
     * @return
     * @author Anthony C. Liberto
     */
    public int getSide() {
        return iSide;
    }

    /**
     * getENavForm
     * @return
     * @author Anthony C. Liberto
     */
    public ENavForm getENavForm() { //22695
        return parent; //22695
    } //22695

    /**
     * getCurrentEntityList
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList getCurrentEntityList() {
        return navList;
    }

    /**
     * getParentEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getParentEntityGroup() { //20059
        if (navList != null) {
            return navList.getParentEntityGroup(); //20059
        }
        return null;
    } //20059

    /**
     * isPreference
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPreference(String _key) {
        if (oPref != null) {
            return oPref.isPreference(_key);
        }
        return false;
    }

    /**
     * setPreference
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPreference(String _key, boolean _b) {
        if (oPref != null) {
            oPref.setPreference(_key, _b);
        }
    }

    /**
     * getNavSerialObjectController
     * @return
     * @author Anthony C. Liberto
     */
    public SerialObjectController getNavSerialObjectController() {
        return nsoController;
    }

    /**
     * getPreferences
     * @return
     * @author Anthony C. Liberto
     */
    public static UIPreferences getPreferences() {
        return oPref;
    }

    /**
     * getProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getProfile() {
        if (navList == null) {
            return null;
        }
        return navList.getProfile();
    }

    /**
     * requestFocus
     * @author Anthony C. Liberto
     */
    public void requestFocus() {
        if (data instanceof NavDataDouble) {					//PKUR-6CCBXQ
			DataTab dt = ((NavDataDouble)data).getDataTab();	//PKUR-6CCBXQ
			if (dt != null) {									//PKUR-6CCBXQ
				dt.requestFocus();								//PKUR-6CCBXQ
			}													//PKUR-6CCBXQ
		} else {												//PKUR-6CCBXQ
	        NavTable nt = data.getTable();
	        if (nt != null) {
	            nt.requestFocus();
			}
		}														//PKUR-6CCBXQ
    }

    /**
     * getLinkType
     * @return
     * @author Anthony C. Liberto
     */
    public String getLinkType() {
        if (cart == null) {
            return "NODUPES";
        }
        return cart.getLinkType();
    }

    /**
     * setForm
     * @param _form
     * @author Anthony C. Liberto
     */
    public void setForm(EForm _form) {
        nf = _form;
        //		setFont(_form.getFormFont());
    }

    /**
     * build
     * @author Anthony C. Liberto
     */
    public void build() {
        build(DEFAULT, DEFAULT, DEFAULT, SINGLE_CLICK);
    }

    /**
     * build
     * @param _histType
     * @param _dataType
     * @param _actionType
     * @param _actionClicks
     * @author Anthony C. Liberto
     */
    public void build(int _histType, int _dataType, int _actionType, int _actionClicks) {
        build(_histType, _dataType, _actionType, _actionClicks, true);
    }

    private void build(int _histType, int _dataType, int _actionType, int _actionClicks, boolean _load) {
        Object o = null;
        history = createHistory(_histType, _actionClicks);
        history.setName("Navigate History"); //access
        history.addFocusListener(getFocusListener());
        popup = nf.getPopupMenu(); //21893
        data = createData(_dataType, _actionClicks, _actionType);
        data.getAccessibleContext().setAccessibleDescription(getString("accessible.navData"));
        data.setName("Navigate Data"); //access
        data.addFocusListener(getFocusListener());
        data.addMouseListener(popup); //21893

        if (_dataType != DATA_DOUBLE) {
            action = createAction(_actionType, _actionClicks);
            action.setName("Navigate Action"); //access
            action.addFocusListener(getFocusListener());
        }

        if (iSide == 0) {
            cart = new NavCartDialog(this);
            cart.setName("Navigate Cart"); //access
        } else {
            cart = parent.getCart(0);
        }
        pick = new NavPick(this, NavPick.NAVIGATE);
        menu = nf.getMenu();
        //cr_209046022		tBar = nf.getEToolbar();
        if (!EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
            tBar = generateToolbar(); //cr_209046022
            parent.addToolbar(tBar); //cr_209046022
        }
        o = nf.getSeed();
        if (o != null && o instanceof EntityList) {
            init(false);
            finishAction((EntityList) o, null, NAVIGATE_INIT_LOAD);
            nf.setSeed(null); //52573
        } else {
            init(_load);
        }
    }

    /*
    	public void setFont(Font _f) {
    		navFont = _f;
    		if (menu != null) menu.setFont(_f);
    		if (popup != null) popup.setFont(_f);
    		return;
    	}

    	public Font getFont() {
    		return navFont;
    	}
    */
    /**
     * getEToolbar
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceToolbar getEToolbar() {
        return tBar;
    }

    /**
     * createPopupMenu
     * @author Anthony C. Liberto
     */
    protected void createPopupMenu() {
        addPopupMenu("srt", "srt", parent);
        popup.addSeparator();
        addPopupMenu("f/r", "f/r", parent);
        addPopupMenu("fltr", "fltr", parent);
        popup.addSeparator();
        addPopupMenu("selA", "selA", parent);
        addPopupMenu("iSel", "iSel", parent);
        popup.addSeparator();
        addPopupMenu("cut", "cut", parent);
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
        createFileMenu();
        createEditMenu();
        createViewMenu();
        createHistoryMenu();
        createLaunchMenu();
        createTableMenu();
    }

    /**
     * createFileMenu
     * @author Anthony C. Liberto
     */
    protected void createFileMenu() {
        String strKey = getString("file");
        addMenu(strKey, "clsT", parent, KeyEvent.VK_W, Event.CTRL_MASK, true);
        addMenu(strKey, "clsA", parent, KeyEvent.VK_W, Event.CTRL_MASK + Event.SHIFT_MASK, true); //22868
        menu.addSeparator(strKey);
        addMenu(strKey, "xprt", parent, 0, 0, true);
        addMenu(strKey, "print", parent, KeyEvent.VK_P, Event.CTRL_MASK, true);
        addMenu(strKey, "pageSetup", parent, 0, 0, true);
        menu.addSeparator(strKey);
        if (isTestMode()) {
            addMenu(strKey, "saveT", parent, 0, 0, true);
            addMenu(strKey, "load", parent, KeyEvent.VK_L, Event.CTRL_MASK, true);
            menu.addSeparator(strKey);
        } //51497

        addMenu(strKey, "lgf", parent, KeyEvent.VK_Q, Event.CTRL_MASK, true);
        addMenu(strKey, "exit", parent, KeyEvent.VK_F4, Event.ALT_MASK, true);
        menu.setMenuMnemonic(strKey, getChar("file-s"));
    }

    /**
     * createEditMenu
     * @author Anthony C. Liberto
     */
    protected void createEditMenu() {
        String strKey = getString("edit");
        addMenu(strKey, "cut", parent, KeyEvent.VK_X, Event.CTRL_MASK, true);
        menu.addSeparator(strKey);
        addMenu(strKey, "selA", parent, KeyEvent.VK_A, Event.CTRL_MASK, true);
        addMenu(strKey, "iSel", parent, KeyEvent.VK_I, Event.CTRL_MASK, true);
        menu.addSeparator(strKey); //20020710
        addMenu(strKey, "f/r", parent, KeyEvent.VK_F, Event.CTRL_MASK, true); //20020710
        menu.addSeparator(strKey); //CR0805036452
        addMenu(strKey, "add2cart", parent, KeyEvent.VK_A, Event.CTRL_MASK + Event.SHIFT_MASK, true); //CR0805036452
        menu.setMenuMnemonic(strKey, getChar("edit-s"));
    }

    /**
     * createTableMenu
     * @author Anthony C. Liberto
     */
    protected void createTableMenu() {
        String strKey = getString("tbl");
        addMenu(strKey, "srt", parent, 0, 0, true); //MSort
        menu.addSeparator(strKey);
        //cr_0805036452		menu.addMenu(strKey,"fltr",parent, KeyEvent.VK_F8, Event.CTRL_MASK, true);
        menu.addMenu(strKey, "fltr", parent, KeyEvent.VK_F8, 0, true); //cr_0805036452
        menu.addSeparator(strKey);
        addMenu(strKey, "pin", parent, KeyEvent.VK_P, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        addMenu(strKey, "bookmark", parent, KeyEvent.VK_B, Event.CTRL_MASK, true);
        menu.addSeparator(strKey);
        addMenu(strKey, "histI", parent, KeyEvent.VK_F11, Event.CTRL_MASK, true);
        addMenu(strKey, "histR", parent, KeyEvent.VK_F11, Event.SHIFT_MASK, true);
        addMenu(strKey, "eData", parent, KeyEvent.VK_F12, Event.CTRL_MASK, true);
        menu.addSeparator(strKey);
        addMenu(strKey, "rfrsh", parent, KeyEvent.VK_F5, 0, true);
        if (isCaptureMode()) {
			addMenu(strKey, "capture", parent, KeyEvent.VK_F5, Event.CTRL_MASK + Event.SHIFT_MASK, true);
		}
        menu.setMenuMnemonic(strKey, getChar("tbl-s"));
    }

    /**
     * createHistoryMenu
     * @author Anthony C. Liberto
     */
    protected void createHistoryMenu() {
        String strKey = getString("hist");
        //cr_0805036452		addMenu(strKey,"prev", parent, KeyEvent.VK_LEFT, Event.CTRL_MASK, true);
        addMenu(strKey, "prev", parent, KeyEvent.VK_LEFT, Event.SHIFT_MASK, true); //cr_0805036452
        menu.addSeparator(strKey);
        addMenu(strKey, "reset", parent, 0, 0, true);
        addMenu(strKey, "resetCart", parent, 0, 0, true); //21781
        menu.setMenuMnemonic(strKey, getChar("hist-s"));
    }

    /**
     * setSelected
     * @param _key
     * @param _checked
     * @author Anthony C. Liberto
     */
    public void setSelected(String _key, boolean _checked) {
        menu.setSelected(_key, _checked);
    }

    /**
     * isDisconnected
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDisconnected() {
        return menu.isSelected("disCon");
    }

    private void setPin(){
    	if (parent != null) {
    		if (parent.isPin()) {
    			//kehrli_20030929				eaccess().setIconAt(parent,parent.getGif());
    			eaccess().setIconAt(parent, getTabIcon()); //kehrli_20030929
    			parent.setPin(false);
    		} else {
    			eaccess().setIconAt(parent, "pin.gif");
    			parent.setPin(true);
    		}    
    	}
    }
    /**
     * isPin
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPin() {
        if (parent != null) { //52821
            return parent.isPin();
        } //52821
        return false; //52821
    }

    /**
     * performAction
     * @param _ae
     * @author Anthony C. Liberto
     */
    public void performAction(ActionEvent _ae) {
        String strAction = null;
        if (_ae == null) { //52715
            return; //52715
        } //52715
        strAction = _ae.getActionCommand();

        if (strAction == null) { //52715
            return; //52715
        } //52715
        appendLog("navigate.action: " + strAction);

        if (strAction.equals("nav")) { //acl_20021120
            loadAction(_ae.getSource(), NAVIGATE_LOAD); //acl_20021120
            return; //acl_20021120
        } else if (strAction.equals("navP")) { //50827
            loadAction(_ae.getSource(), NAVIGATE_LOAD); //50827
            return; //50827
        } else if (strAction.equals("search")) { //acl_20021120
            //51395			loadAction(_ae.getSource(), NAVIGATE_OTHER);			//acl_20021120
            loadAction(_ae.getSource(), NAVIGATE_LOAD); //51395
            return; //acl_20021120
        } else if (strAction.equals("edit")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("link")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("cpyA")) { //copyAction
            loadAction(_ae.getSource(), NAVIGATE_OTHER); //copyAction
            return; //copyAction
        } else if (strAction.equals("sGrp")) { //chgroup
            loadAction(_ae.getSource(), NAVIGATE_OTHER); //chgroup
            return; //chgroup
        } else if (strAction.equals("cGrp")) { //chgroup
            resetChangeGroup(); //chgroup
        } else if (strAction.equals("abrS")) { //cr_2115
            loadAction(_ae.getSource(), NAVIGATE_LOAD); //cr)
            return; //cr_2115
        } else if (strAction.equals("linkD")) { //cr_209046022
            loadAction(_ae.getSource(), NAVIGATE_OTHER); //cr_209046022
            return; //cr_209046022
        } else if (strAction.equals("wFlow")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("pdg")) { //USRO-R-JSTT-68RKKP
            loadAction(_ae.getSource(), NAVIGATE_OTHER); //USRO-R-JSTT-68RKKP
            return; //USRO-R-JSTT-68RKKP
        } else if (strAction.equals("rprt")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("xtract")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("deAct")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("lckP")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("mtrx")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("used")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("query")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        }else if (strAction.equals("crte")) {
            loadAction(_ae.getSource(), NAVIGATE_OTHER);
            return;
        } else if (strAction.equals("viewP")) {
            loadPersistantLock(false);
            return;
        } else if (strAction.equals("viewPW")) {
            loadPersistantLock(true);
            return;
        } else if (strAction.equals("rstr")) {
            loadRestore(TYPE_RESTOREACTION);
            return;
        } else if (strAction.equals("lgf")) {
            eaccess().logOff();
            return;
        } else if (strAction.equals("exit")) {
            NavTable nt = data.getTable();
            if (nt != null) {
                nt.removePropertyChangeListener(this);
            }
            eaccess().exit("exit navigate");
            return;
        }

        if (isBusy()) {
            return;
        }
        try{
        	setBusy(true);

        	if (strAction.equals("f/r")) {
        		data.getTable().showFind();
        	} else if (strAction.equals("clsT")) {
        		eaccess().close(parent);
        	} else if (strAction.equals("clsA")) { //20062
        		eaccess().closeAll(); //20062
        	} else if (strAction.equals("xprt")) {
        		export();
        	} else if (strAction.equals("load")) {
        		eaccess().load();
        	} else if (strAction.equals("saveT")) {
        		eaccess().save(navList);
        	} else if (strAction.equals("pin")) {
        		setPin();
        	} else if (strAction.equals("bookmark")) {
        		//cr_1210035324			showBookmark(navList.getParentActionItem());
        		EANActionItem ean = null; //53583
        		NavTable nt = data.getTable(); //53583
        		if (nt != null) { //53583
        			ean = nt.getParentAction(); //53583
        		} //53583
        		if (ean == null) { //53583
        			ean = navList.getParentActionItem(); //53583
        		} //53583
        		showBookmark(ean, getNavigationHistory()); //53583
        		//53583			showBookmark(navList.getParentActionItem(), getNavigationHistory());		//cr_1210035324
        		//			eaccess().pin(navList);
        	} else if (strAction.equals("print")) {
        		print();
        	} else if (strAction.equals("pageSetup")) {
        		eaccess().pageSetup();
        	} else if (strAction.equals("goto")) {
        		gotoTab();
        	} else if (strAction.equals("guest")) {
        		//op.openNavigateForm(this,"nav_test.html",true);
        		ENavForm form = new ENavForm();
        		form.init(this, null, "nav_test.html");
        		//51975			addTab("test","test",null,form,"test");
        		//kehrli_20030929			addTab(null,"test","test",null,form,"test");		//51975
        		addTab(null, form); //kehrli_20030929
        	} else if (strAction.equals("fltr")) {
        		data.getTable().showFilter();
        	} else if (strAction.equals("cut")) {
        		cut();
        	} else if (strAction.equals("selA")) {
        		data.getTable().selectAll();
        	} else if (strAction.equals("iSel")) {
        		data.getTable().invertSelection();
        	} else if (strAction.equals("histI")) {
        		// this will always go down a relator to the child entity
        		eaccess().getChangeHistory(getCurrentEntityItem(false)); // will get it based on row
        	} else if (strAction.equals("histR")) {
        		EntityItem curitem = getCurrentEntityItem(); // based on row and column
        		if (curitem.getEntityGroup().isRelator() || curitem.getEntityGroup().isAssoc()){
        			eaccess().getThisChangeHistory(curitem);
        		}else{
        			if (curitem.hasUpLinks()){
        				// get parent relator to show history
        				curitem = (EntityItem)curitem.getUpLink(0);
        				eaccess().getThisChangeHistory(curitem);
        			}else{
        				eaccess().showAdminMsg("No Parent Relator found for selected entity, "+
        					curitem.getKey()+", in this navigation.");
        			}
        		}
        	} else if (strAction.equals("eData")) {
        		data.getTable().showInformation();
        	} else if (strAction.equals("reset")) {
        		reset();
        	} else if (strAction.equals("resetCart")) { //21781
        		resetCart(); //21781
        	} else if (strAction.equals("showCart")) {
        		popupCart();
        	} else if (strAction.equals("rfrsh")) {
        		refresh();
        		return;
        	} else if (strAction.equals("capture")) {
        		if (navList != null) {
        			eaccess().capture(navList.dump(false));
        		}
        	} else if (strAction.equals("srchRQ")) {
        		loadSearch();
        	} else if (strAction.equals("prev")) {
        		history.backup();
        	} else if (strAction.equals("lckP")) {
        	} else if (strAction.equals("docSel")) {
        		showTabSelector();
        	} else if (strAction.equals("disCon")) {
        		setPreference("cachedHistory", isDisconnected());
        	} else if (strAction.equals("srt")) { //MSort
        		data.getTable().showSort(); //MSort
        	} else if (strAction.equals("LANGUAGEMENUITEM")) {
        		Object o = _ae.getSource();
        		if (o instanceof LanguageMenuItem) {
        			LanguageMenuItem lmi = (LanguageMenuItem) o;
        			nlsChanged(lmi.getNLSItem());
        		}
        	} else if (strAction.equals("add2cart")) { //acl_20021115
        		if (cart != null) { //acl_20021115
        			cart.addToCart(this);
        		} //acl_20021115
        	} else if (strAction.equals("add2cartA")) { //acl_20021115
        		if (cart != null) { //acl_20021115
        			cart.addToCartAll(this);
        		} //acl_20021115
        	} //acl_20021115
        }catch(Exception exc){
        	eaccess().showException(exc, null,ERROR_MESSAGE,OK);
        }
        setBusy(false);
    }

/*
    private void prune() {
        prune(history);
        return;
    }
*/
    /**
     * addPopupMenu
     * @param _name
     * @param _s
     * @param _al
     * @author Anthony C. Liberto
     */
    public void addPopupMenu(String _name, String _s, ActionListener _al) {
        popup.addPopupMenu(_name, _s, _al);
    }

    /**
     * addMenu
     * @param _menu
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @param _enabled
     * @author Anthony C. Liberto
     */
    public void addMenu(String _menu, String _s, ActionListener _al, int _key, int _mod, boolean _enabled) {
        menu.addMenu(_menu, _s, _al, _key, _mod, _enabled);
    }

    /**
     * addSubMenu
     * @param _menu
     * @param _s
     * @param _al
     * @param _key
     * @param _mod
     * @author Anthony C. Liberto
     */
    public void addSubMenu(String _menu, String _s, ActionListener _al, int _key, int _mod) {
        menu.addSubMenu(_menu, _s, _al, _key, _mod);
    }

    /**
     * getMenu
     * @return
     * @author Anthony C. Liberto
     */
    public EMenubar getMenu() {
        return menu;
    }

    /**
     * setCurrentNavigate
     * @author Anthony C. Liberto
     */
    public void setCurrentNavigate() {
        if (parent.setCurrentNavigate(this)) {
            updateMenuActions();
        }
    }

    /**
     * getFocusListener
     * @return
     * @author Anthony C. Liberto
     */
    public FocusListener getFocusListener() {
        if (fListener == null) {
            fListener = new FocusListener() {
                public void focusGained(FocusEvent _fe) {
                    if (!isBusy()) { //53500
                        setCurrentNavigate();
                    } //53500
                    return;
                }
                public void focusLost(FocusEvent _fe) {
                }
            };
        }
        return fListener;
    }

    /**
     * getPopupMenu
     * @return
     * @author Anthony C. Liberto
     */
    public EPopupMenu getPopupMenu() {
        return popup;
    }

    private NavAction createAction(int _type, int _clicks) {
        switch (_type) {
        case ACTION_BOX :
            return new NavActionBox(this);
        case ACTION_LIST :
            return new NavActionList(this, _clicks);
        default :
            return new NavActionTree(this, _clicks);
        }
    }

    private NavHist createHistory(int _type, int _clicks) {
        switch (_type) {
        case HISTORY_LIST :
            return new NavHistList(this, _clicks);
        default :
            return new NavHistBox(this);
        }
    }

    private NavData createData(int _type, int _clicks, int _actionType) {
        switch (_type) {
        case DATA_DOUBLE :
            return new NavDataDouble(this, _clicks, _actionType);
        case DATA_FORM :
            return new NavDataForm(this);
        case DATA_TREE :
            return new NavDataTree(this);
        default :
            return new NavDataSingle(this);
        }
    }

    private void closeLocalMenus() { //acl_Mem_20020204
        if (iSide != 0) {
            menu = null;
            popup = null;
            return;
        }
        popup.removeMenu("srt", parent);
        popup.removeMenu("f/r", parent); //acl_Mem_20020204
        popup.removeMenu("fltr", parent); //acl_Mem_20020204
        popup.removeMenu("selA", parent); //acl_Mem_20020204
        popup.removeMenu("iSel", parent); //acl_Mem_20020204
        popup.removeMenu("cut", parent); //acl_Mem_20020204
        popup.dereference(); //acl_Mem_20020204
        popup = null; //acl_Mem_20020204

        menu.removeMenuItem("clsT", parent); //acl_Mem_20020204
        menu.removeMenuItem("clsA", parent); //20062
        menu.removeMenuItem("xprt", parent); //acl_Mem_20020204
        if (isTestMode()) {
            menu.removeMenuItem("load", parent);
            menu.removeMenuItem("saveT", parent);
        } //51497
        menu.removeMenuItem("print", parent); //acl_Mem_20020204
        menu.removeMenuItem("pageSetup", parent);
        menu.removeMenuItem("goto", parent); //acl_Mem_20020204
        menu.removeMenuItem("guest", parent); //acl_Mem_20020204
        menu.removeMenuItem("lgf", parent); //acl_Mem_20020204
        menu.removeMenuItem("exit", parent); //acl_Mem_20020204
        menu.removeMenu("File"); //acl_Mem_20020204
        menu.removeMenuItem("cut", parent); //acl_Mem_20020204
        menu.removeMenuItem("lckP", parent); //acl_Mem_20020204
        menu.removeMenuItem("deAct", parent); //acl_Mem_20020204
        menu.removeMenuItem("selA", parent); //acl_Mem_20020204
        menu.removeMenuItem("iSel", parent); //acl_Mem_20020204
        menu.removeMenu("Edit"); //acl_Mem_20020204
        menu.removeMenuItem("nav", parent); //acl_20021120
        menu.removeMenuItem("navP", parent); //50827
        menu.removeMenuItem("search", parent); //acl_20021120
        menu.removeMenuItem("edit", parent); //acl_Mem_20020204
        menu.removeMenuItem("mtrx", parent); //acl_Mem_20020204
        menu.removeMenuItem("used", parent); //acl_Mem_20020204
        menu.removeMenuItem("query", parent); 
        menu.removeMenuItem("crte", parent); //acl_Mem_20020204
        menu.removeMenuItem("rprt", parent);
        menu.removeMenuItem("link", parent);
        menu.removeMenuItem("cpyA", parent); //copyAction
        menu.removeMenuItem("linkD", parent); //cr_209046022
        menu.removeMenuItem("wFlow", parent);
        menu.removeMenuItem("xtract", parent);
        menu.removeMenuItem("sGrp", parent); //chgroup
        menu.removeMenuItem("cGrp", parent); //chgroup
        menu.removeMenuItem("viewP", parent); //acl_Mem_20020204
        menu.removeMenuItem("viewPW", parent);
        menu.removeMenuItem("rstr", parent);
        menu.removeMenu("Action"); //acl_Mem_20020204
        menu.removeMenuItem("srt", parent);
        menu.removeMenuItem("f/r", parent); //acl_Mem_20020204
        menu.removeMenuItem("fltr", parent); //acl_Mem_20020204
        menu.removeMenuItem("srchRQ", parent); //acl_Mem_20020204
        menu.removeMenuItem("histI", parent);
        menu.removeMenuItem("histR", parent);
        menu.removeMenuItem("eData", parent); //acl_Mem_20020204
        menu.removeMenuItem("rfrsh", parent); //acl_Mem_20020204
        menu.removeMenuItem("capture",parent);
        menu.removeMenuItem("pin", parent);
        menu.removeMenuItem("bookmark", parent);

        menu.removeMenu("Table"); //acl_Mem_20020204
        menu.removeMenuItem("prev", parent); //acl_Mem_20020204
        menu.removeMenuItem("docSel", parent);
        menu.removeMenuItem("reset", parent); //acl_Mem_20020204
        menu.removeMenuItem("resetCart", parent); //21781
        menu.removeCheckMenu("disCon", parent); //acl_Mem_20020204
        menu.removeMenu("History"); //acl_Mem_20020204
        menu.removeMenu("Cart"); //acl_Mem_20020204
        menu.dereference(); //acl_Mem_20020204
        menu = null; //acl_Mem_20020204
    } //acl_Mem_20020204

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (oPref != null) {
            oPref.writeDatabaseObject();
            oPref.clear();
            oPref = null;
        }
        if (data != null) {
            data.removeMouseListener(popup); //21893
            data.removeFocusListener(getFocusListener());
            data.dereference();
            data = null;
        }
        closeLocalMenus(); //acl_Mem_20020204
        if (history != null) {
            history.removeFocusListener(getFocusListener());
            history.dereference();
            history = null;
        }
        if (action != null) {
            action.removeFocusListener(getFocusListener());
            action.dereference();
            action = null;
        }
        if (iSide == 0 && cart != null) {
            cart.removeFocusListener(getFocusListener());
            cart.dereference();
        }
        cart = null;

        if (pick != null) {
            pick.removeFocusListener(getFocusListener());
            pick.dereference();
            pick = null;
        }

        deleteHistoryFiles(null);
        fListener = null;
        navList = null;
        //nso = null;
        nf = null;
        //navFont = null;
        //oComp = null;
        if (parent != null) {
            parent = null;
        }
    }

    /**
     * init
     * @param _load
     * @author Anthony C. Liberto
     */
    public void init(boolean _load) {
        if (iSide == 0) {
            createPopupMenu();
            createMenu();
        }

        if (_load) {
            performAction(false, true, NAVIGATE_INIT_LOAD); //hist_remove
        }

        bypass = false;
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        int reply = -1;
        //String cartName = null;
        setCode("msg0008"); //21781
        reply = showConfirm(getComponent(), OK_CANCEL, true);
        if (reply == OK) { //21781
        	if (isPin()){
        		// remove the pin so history can be cleared
        		setPin();
        	}
            resetHistory();
            //cartName = getCartFileName(); //21781
            //deleteHistoryFiles(cartName);
            //eaccess().setFileKey(getMaxKey(dir, getFilter(), false, false));
            performAction(false, true, NAVIGATE_RESET);
        } //21781
    }

    /**
     * deleteHistoryFiles
     * @param _cart
     * @author Anthony C. Liberto
     */
    public void deleteHistoryFiles(String _cart) {
        File[] files = gio().listFiles(dir, getFilter());

        if (files != null) {
            int ii = files.length;
            for (int i = 0; i < ii; ++i) {
                if (_cart != null) {
                    if (!files[i].getName().equals(_cart)) {
//MN25076829                        files[i].delete();
						files[i].deleteOnExit();		//MN25076829
                    }
                } else {
//MN25076829                    files[i].delete();
					files[i].deleteOnExit();		//MN25076829
                }
            }
            //if (_cart == null) {
            //  gio().delete(getCartFileName());
            //}
        }
    }

    /**
     * resetHistory
     * @author Anthony C. Liberto
     */
    public void resetHistory() {
        if (history != null) {
            history.reset();
        }
    }

    /**
     * resetCart
     * @author Anthony C. Liberto
     */
    public void resetCart() { //21781
        int reply = -1;
        //File file = null;
        setCode("msg24016");
        reply = showConfirm(getComponent(), OK_CANCEL, true); //21781
        if (reply == OK) { //21781
            cart.clearAll(true); //21781
            //file = new File(getCartFileName()); //21781
            //if (file.exists()) { //21781
            //    file.delete();
            //} //21781
        } //21781
    } //21781

    /**
     * getFilePrefix
     * @return
     * @author Anthony C. Liberto
     */
    public String getFilePrefix() {
        return getSerialPrefix() + iSide;
    }

    /**
     * getSerialPrefix
     * @return
     * @author Anthony C. Liberto
     */
    public String getSerialPrefix() {
        return "*" + opwgID + "_" + sessID + "_"; //acl_20030506
    }

    private boolean loadHist(int _navType) {
        if (_navType < NAVIGATE_RENAVIGATE) {
            return true;
        }
        return false;
    }

    /**
     * loadHistory
     * @param _o
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void loadHistory(Object _o, int _navType) {
        if (_o != null && _o instanceof NavHist) {
            NavHist in = (NavHist) _o;
            if (history != null) {
                history.load(in, _navType);
            }
        }
    }

    /**
     * load - called after loading a bookmark or when finishing a navigate action (search too)
     * @param _eList
     * @param _navType
     */
    private void load(EntityList _eList, int _navType) {
        EANActionItem eai = null;
        boolean bPick = false;
        if (_eList == null) {
            return;
        }

        bRefresh = false;
        //cr_2115		boolean bPick = _eList.isPicklist();
        bPick = _eList.isPicklist() || _eList.isABRStatus(); //cr_2115

        if (isPin() && !bPick) {
            ENavForm eForm = eaccess().load(parent, history, _eList, _navType, null, getProfile()); //52158
            Navigate nav = eForm.getNavigate(); //52158
            nav.adjustPrevNext(); //52158
            //appendLog("load completed(0): " + new Date());
            return;
        }
        parent.setSessionTagText(_eList.getTagDisplay());
        eai = _eList.getParentActionItem();
        //cr_2115		if (eai instanceof NavActionItem || eai instanceof SearchActionItem) {
        if (eai instanceof NavActionItem || eai instanceof SearchActionItem || eai instanceof ABRStatusActionItem || eai instanceof ExtractActionItem) { //cr_2115
            if (!bPick) {
                navList = _eList;
                updateBookmarkAction(eai); //51325
            }
            /*if (loadHist(_navType)) {
                if (!bPick) {
                	// moved because loading history sometimes beat the load of data causing nullptrs
                	NavSerialObject nso = nsoController.generateHist(navList, iSide);
                    history.load(nso, navList, true);
                }
            } else*/ 
            if (!loadHist(_navType) && _navType != NAVIGATE_OTHER) {
                history.removeHistory(history.getSelectedIndex(), true);
            }

            if (bPick) {
                if (!hasData(_eList)) { //50568
                    showFYI(getComponent(), "msg23007.0"); //50568
                    //appendLog("load completed(1): " + new Date());
                    return; //50568
                } //50568
                pick.setEntityList(_eList);
                show(pick, pick, false);
                //appendLog("load completed(2): " + new Date());
                return;
            } else {
            	
    //Long t1 = EAccess.eaccess().timestamp("        Navigate.subload started");            	
                EntityGroup eg = data.load(_eList, false);
    //EAccess.eaccess().timestamp("        Navigate.subload1 done",t1);  
                if (loadHist(_navType)) {
            		NavSerialObject nso = nsoController.generateHist(navList, iSide);
            		history.load(nso, navList, true);
                }
                
                RowSelectableTable rst = null; //rst_update
                NavAction nAction = getAction(); //53496
                if (eg == null) {
                    if (nAction != null) {
                        nAction.clear(); //22862
                    }
                    updateMenuActions(null); //22862
                    adjustPrevNext(); //22862
                    //appendLog("load completed(3): " + new Date());
System.out.println("Navigate.load entityGroup is null for elist "+_eList.getKey()+" "+data.getClass().getName());
					return;
                }
                _eList.setActiveEntityGroup(eg);
                if (nAction != null) {
                    //51298					action.load(eg);
                    rst = eg.getActionGroupTable(); //rst_update
                    nAction.load(eaccess().getExecutableActionItems(eg, rst), eaccess().getActionTitle(eg, rst)); //rst_update
 //                   EAccess.eaccess().timestamp("        Navigate.subload2 done",t1);
				}
                stateChanged(eg, rst); 
 //               EAccess.eaccess().timestamp("        Navigate.subload ended",t1); 
            }
            adjustPrevNext();
            setNLSData(navList.getProfile());
        }
        setEnabled("goto", data.getEntityGroupCount() > 1);
        setFilter(false); //23515
        //appendLog("load completed(4): " + new Date());
    }

    /**
     * reload
     * @param _nso
     * @param _navType
     * @author Anthony C. Liberto
     * /
    public void reload(NavSerialObject _nso, int _navType) {
        NavSerialObject oldNso = reload0(_nso, _navType);
        EntityList eList = reload0(_nso);
        if (eList != null) {
            reload(eList, _nso, oldNso, _navType);
        }
    } 

    private NavSerialObject reload0(NavSerialObject _nso, int _navType) {
        NavSerialObject oldNso = null;
        if (nsoController.getCurrentNso() != _nso) {
            oldNso = nsoController.getCurrentNso();
        }
        return oldNso;
    }

    private EntityList reload0(NavSerialObject _nso) {
        return _nso.replay();
    }*/

    /**
     * reload
     * @param _navList
     * @param _nso
     * @param _oldNso
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void reload(EntityList _navList, NavSerialObject _nso, NavSerialObject _oldNso, int _navType) { //23316
        boolean bPick = _navList.isPicklist() || _navList.isABRStatus(); //cr_2115
        EntityGroup eg = null;
        NavAction nAction = null;
        bRefresh = false;
        if (NAVIGATE_RENAVIGATE == _navType && isPin() && !bPick) { //51715
            ENavForm eForm = eaccess().load(parent, history, _navList, _navType, null, getProfile()); //51975
            Navigate nav = eForm.getNavigate(); //51922
            nav.getData().setSelected(_nso.getKey(), _nso.getKeys()); //51922
            nav.adjustPrevNext(); //51949
            //appendLog("reload completed(0): " + new Date());
            nav.nsoController.setCurrentNso(_nso,isPin());
            return; //51715
        } //51715
        parent.setSessionTagText(_navList.getTagDisplay());
        navList = _navList; //23316
        if (!bPick) { //51325
            updateBookmarkAction(navList.getParentActionItem()); //51325
        } //51325
        if (_navType == NAVIGATE_INIT_LOAD) {
            if (!bPick) {
            	nsoController.setCurrentNso(_nso,isPin());
                history.load(_nso, navList, false);
            }
        } else if (_navType == NAVIGATE_LOAD) {
            if (!bPick) {
            	nsoController.setCurrentNso(_nso,isPin());
                history.load(_nso, navList, true);
            }
        } else if (_navType != NAVIGATE_OTHER) {
            history.removeHistory(history.getSelectedIndex(), true);
        }
        if (_navType == NAVIGATE_RENAVIGATE) {
        	nsoController.setCurrentNso(_nso,isPin());
            //nso = _nso;
        }
        if (bPick) {
            if (!hasData(_navList)) { //50568
                showFYI(getComponent(), "msg23007.0"); //50568
                //appendLog("reload completed(1): " + new Date());
                return; //50568
            } //50568
            pick.setEntityList(navList);
            show(pick, pick, false);
            //appendLog("reload completed(2): " + new Date());
            return;
        } else {
            FilterGroup fg = null; //53451
            if (_navType == NAVIGATE_REFRESH) { //53451
                fg = data.getFilterGroup(); //53451
            } //53451
            eg = data.load(navList, true);
            if (fg != null) { //53451
                //System.out.println("setting group");
                data.setFilterGroup(fg); //53451
            } //53451
            data.sort();
            data.setSelected(_nso.getKey(), _nso.getKeys());
            //_nso.setDisplay(navList); // why is this called here?
            nAction = getAction(); //53496
            //USRO-R-DMKR-668L6T
            if (eg != null) {
                navList.setActiveEntityGroup(eg);
                if (nAction != null) {
                    RowSelectableTable rst = eg.getActionGroupTable(); //rst_update
                    EANActionItem[] aai = eaccess().getExecutableActionItems(eg, rst); //51609
                    nAction.load(aai, eaccess().getActionTitle(eg, rst)); //51298
				 	updateMenuActions(nAction); //53502
                }
            } else { //53367
                if (nAction != null) {
                    nAction.load(null,  null); //53367
 					updateMenuActions(nAction); //53502
                } else {
 					updateMenuActions(null); //53502
                }
            }
            stateChanged();
        }
        adjustPrevNext();
        setNLSData(navList.getProfile());
        setEnabled("goto", data.getEntityGroupCount() > 1);
        setFilter(false);
        //appendLog("reload completed(3): " + new Date());
    }

    /**
     * adjustPrevNext
     * @author Anthony C. Liberto
     */
    private void adjustPrevNext() {
        int loc = history.getSelectedIndex();
//        int max = history.getHistoryCount();
        boolean bPrev = true;
        if (loc == 0) {
            bPrev = false;
        }
        setEnabled("prev", bPrev);
    }

    /**
     * hasData
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasData() {
        NavTable nt = data.getTable();
        if (nt != null) {
            return nt.hasRows() && nt.hasColumns();
        }
        return false;
    }

    /**
     * updateMenuActions
     * @author Anthony C. Liberto
     */
    public void updateMenuActions() { //53774
        if (action != null) {
            updateMenuActions(action);
        } else if (data instanceof NavDataDouble) {
            updateMenuActions(((NavDataDouble) data).getNavAction());
        }
    }
    
    /**
     * getSelectedEntityItems
     *
     * @param _new
     * @param _bEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem[] getSelectedEntityItems(boolean _new, boolean _bEx) throws OutOfRangeException {
        return data.getSelectedEntityItems(_new, _bEx);
    }

    /**
     * getCurrentEntityItem - based on row only
     * @param _new
     * @return
     * @author Anthony C. Liberto
     */
    public EntityItem getCurrentEntityItem(boolean _new) {
        return data.getCurrentEntityItem(_new);
    }
    /**
     * getCurrentEntityItem - based on row and col
     */
    private EntityItem getCurrentEntityItem() {
        return data.getCurrentEntityItem();
    }
    /**
     * getSelectedActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getSelectedActionItem() {
        if (action != null) {
            return action.getSelectedActionItem();
        } else {
            return null;
        }
    }

    /**
     * getEntityGroup
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup(String _key) { //form_enable
        return navList.getEntityGroup(_key); //form_enable
    } //form_enable

    /**
     * getNavTable
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    public NavTable getNavTable(EntityGroup _eg) { //form_enable
        return data.getTable(_eg.getKey()); //form_enable
    } //form_enable

    /**
     * getNavTable
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public NavTable getNavTable(String _key) { //form_enable
        return data.getTable(_key); //form_enable
    } //form_enable

    /**
     * getNavScroll
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public EScrollPane getNavScroll(String _key) { //form_enable
        NavTable nt = getNavTable(_key); //form_enable
        EScrollPane scroll = null;
        Dimension d = null;
        if (nt == null) {
            return new EScrollPane();
        } //form_enable
        scroll = new EScrollPane(nt, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        d = nt.getPreferredSize(); //form_enable
        scroll.setPreferredSize(d); //form_enable
        scroll.setSize(d); //form_enable
        scroll.getViewport().setSize(d); //form_enable
        scroll.getViewport().setPreferredSize(d); //form_enable
        return scroll; //form_enable
    } //form_enable

    /**
     * tEntityGroup
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getEntityGroup() {
        if (data != null) {
            return data.getEntityGroup();
        }
        return null;
    }

    /**
     * getMatrixNavActionItem
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem getMatrixNavActionItem() {
        return null;
    }

    /**
     * getHistory
     * @return
     * @author Anthony C. Liberto
     */
    public NavHist getHistory() {
        return history;
    }

    /**
     * getData
     * @return
     * @author Anthony C. Liberto
     */
    public NavData getData() {
        return data;
    }

    /**
     * getAction
     * @return
     * @author Anthony C. Liberto
     */
    public NavAction getAction() {
        if (data != null && data instanceof NavDataDouble) { //53496
            return ((NavDataDouble) data).getNavAction(); //53496
        } //53496
        return action;
    }

    /**
     * getCart
     * @return
     * @author Anthony C. Liberto
     */
    public NavCartDialog getCart() {
        return cart;
    }

    /**
     * getSelector
     * @return
     * @author Anthony C. Liberto
     */
    public NavDataSelector getSelector() {
        return data.getTabSelector();
    }

    /**
     * showTabSelector
     * @author Anthony C. Liberto
     */
    public void showTabSelector() {
        showTabSelector(false, true);
    }

    /**
     * showTabSelector
     * @param _modal
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void showTabSelector(boolean _modal, boolean _resize) {
        show(getSelector(), getSelector(), false);
    }

    /**
     * getByteArray
     * @param _delete
     * @param _setCurrent
     * @return
     * @author Anthony C. Liberto
     *
    public byte[] getByteArray(boolean _delete, boolean _setCurrent) {
        String file = new String("hist_tmp.zip");
        if (_setCurrent) {
            nsoController.setCurrent(nso);
            nsoController.write(nso);
        }
        return getByteArray(dir, file, _delete);
    }

    private synchronized byte[] getByteArray(String _dir, String _file, boolean _delete) {
        byte[] bArray = null;
        compressAll(_dir, _file, _delete);
        bArray = gio().readByteArray(_dir + _file);
        gio().delete(_dir, _file);
        return bArray;
    }
    private void compressAll(String _dir, String _file, boolean _delete) {
        gio().compress(_dir, _file, getSaveFilter(), _delete);
    }

    private String getSaveFilter() {
        return getSerialPrefix() + "*.oof";
    }    
    */

    /**
     * writeCurrentHistory
     * @author Anthony C. Liberto
     *
    public void writeCurrentHistory() {
        if (nso != null) {
            nsoController.setCurrent(nso);
            nsoController.write(nso);
        }
    }*/



    private String getFilter() {
        return getFilePrefix() + "#*.oof";
    }


    /**
     * getCartFileName
     * @return
     * @author Anthony C. Liberto
     */
   // public String getCartFileName() { //21781
   //     return dir + "hist" + nsoController.getFilePrefix(true, 1) + "_serialCart.oof"; //acl_20030506
        //		return dir + "hist" + getOPWGID() + "_serialCart.oof";				//21781
   // } //21781

    /*private int getMaxKey(String _dir, String _file, boolean _populate, boolean _populateCart) {
        String[] file = gio().list(_dir, _file);
        int max = 0;
        int ii = file.length;
        NavSerialObject tmpNso = null;
        Arrays.sort(file, oComp); //20011102
        for (int i = 0; i < ii; ++i) {
            Object o = nsoController.readNavSerialObject(_dir + file[i], false);
            if (o != null) {
                tmpNso = (NavSerialObject) o;
                max = Math.max(max, tmpNso.getNextFileKey()); //21689
                
            }
        }

        return max;
    }

    private void populate(NavSerialObject _nso, boolean _populateCart) {
        if (_nso.isPicklist()) {
            history.load(_nso, _nso.replay(), false); //20020304
        } else if (_nso.isCurrent()) {
            renavigate(_nso, NAVIGATE_INIT_LOAD);
        } else {
            history.load(_nso, null, false); //20011102
        } //20011102
    }*/

/*
    private void expandAll(String _dir, String _file, boolean _delete) {
        gio().uncompress(_dir, _file, _delete);
        return;
    }
*/
    /**
     * setEnabled
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(boolean _b) {
        NavAction nAction = getAction(); //53496
        if (nAction != null) {
            nAction.setEnabled(_b);
        }
        data.setEnabled(_b);
        history.setEnabled(_b);
    }

    /**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setVisible(boolean _b) {
        NavAction nAction = getAction(); //53496
        if (nAction != null) {
            nAction.setVisible(_b);
        }
        data.setVisible(_b);
        history.setVisible(_b);
    }

    /**
     * popupCart
     * @author Anthony C. Liberto
     */
    public void popupCart() {
        popupCart(false, true);
    }

    /**
     * popupCart
     * @param _modal
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void popupCart(boolean _modal, boolean _resize) {
        cart.refresh();						//22927316
        show(getCart(), getCart(), _modal);
    }

    /**
     * popupHistoryDialog
     * @author Anthony C. Liberto
     */
    public void popupHistoryDialog() {
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void stateChanged(ChangeEvent e) {
        stateChanged();
    }

    /**
     * stateChanged
     * @author Anthony C. Liberto
     */
    public void stateChanged() {
        EntityGroup eg = null;
        if (bypass) {
            return;
        }
        if (data == null) {
            return;
        }
        eg = data.getEntityGroup();
        if (eg != null) {
            stateChanged(eg);
        }
    }

    /**
     * stateChanged
     * @param _eg
     * @author Anthony C. Liberto
     */
    public void stateChanged(EntityGroup _eg) {
//696Q65        if (action != null) {
		NavAction nAction = getAction(); 			//696Q65
		if (nAction != null) {						//696Q65
            //51298			action.load(_eg);
            RowSelectableTable rst = _eg.getActionGroupTable(); //rst_update
            stateChanged(_eg, rst);
        }
    }

    /**
     * stateChanged
     * @param _eg
     * @param _rst
     * @author Anthony C. Liberto
     */
    public void stateChanged(EntityGroup _eg, RowSelectableTable _rst) { //rst_update
        NavAction nAction = getAction(); //53496
        if (nAction != null) {
            nAction.load(eaccess().getExecutableActionItems(_eg, _rst), eaccess().getActionTitle(_eg, _rst)); //51298
            updateMenuActions(action);
        }
        adjustMenus(_eg);
        data.requestFocus(_eg);
    } //rst_update

    /**
     * canPaste
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste() {
        return false; //51109
        //51109		return eaccess().canPaste(true);
    }

    /**
     * setCartOnForm
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setCartOnForm(boolean _b) {
        if (cart != null) {
            cart.setOnForm(_b);
            /*
            			if (_b)
            				menu.addMenu(cart.getCartMenu());
            */
        }
        setEnabled("showCart", !_b);
    }

    /**
     * setTabSelectorOnForm
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setTabSelectorOnForm(boolean _b) {
        if (data != null) {
            data.setSelectorOnForm(_b);
        }
    }

    private void adjustMenus(EntityGroup _eg) {
//        boolean createable = _eg.isCreatable();
//        boolean matrixable = false;
//        boolean editable = _eg.isEditable();
        boolean hasRows = hasRows(_eg);
//        boolean canPaste = canPaste();

        boolean cartVis = !cart.isOnForm();
        boolean tabSelectVis = !data.isSelectorOnForm();
        boolean isPast = isPast(); //20058
        if (_eg == null) {
            return;
        }

        setEnabled("docSel", tabSelectVis);
        setEnabled("showCart", cartVis);

        setEnabled("srt", hasRows);
        setEnabled("f/r", hasRows);
        setEnabled("fltr", hasRows);
        //50485		setEnabled("selA",hasRows);
        //50485		setEnabled("iSel",hasRows);
        setEnabled("selA", hasRows && !isSingleSelect(_eg)); //50485
        setEnabled("iSel", hasRows && !isSingleSelect(_eg)); //50485
        setEnabled("rLink", hasRows);
        setEnabled("xprt", hasRows);
        setEnabled("histI", hasRows);
        setEnabled("histR", hasRows);
        setEnabled("eData", hasRows);

        if (isPast) { //20186
            setEnabled("crte", false);
        } //20058
        setEnabled("cut", !isPast); //20058
    }

    /**
     * isSingleSelect
     * @param _group
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSingleSelect(EntityGroup _group) { //50485
        if (_group != null) { //50485
            EntityList eList = _group.getEntityList(); //50485
            if (eList != null) { //50485
                EANActionItem ean = eList.getParentActionItem(); //50485
                if (ean != null && ean instanceof NavActionItem) { //50485
                    return ((NavActionItem) ean).isSingleSelect(); //50485
                } //50485
            } //50485
        } //50485
        return false; //50485
    } //50485

    /**
     * setEnabled
     * @param _name
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setEnabled(String _name, boolean _b) {
        if (menu != null) {
            menu.setEnabled(_name, _b);
        }
        if (tBar != null) {
            tBar.setEnabled(_name, _b);
        }
        if (popup != null) {
            popup.setEnabled(_name, _b);
        }
    }

    private boolean hasRows(EntityGroup _eg) {
        if (_eg.getEntityItemCount() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     * @author Anthony C. Liberto
     */
    public void propertyChange(PropertyChangeEvent _pce) {
        String property = _pce.getPropertyName();
        if (property.equalsIgnoreCase("REMOVE_ROW")) {
            data.refreshTitle((EntityGroup) _pce.getNewValue());
        }
    }

    /**
     * export
     * @author Anthony C. Liberto
     */
    public void export() {
        String strDir = null;
        String file = null;
        FileDialog fd = null;
        String export = data.export();
        if (export == null) {
            showError(getComponent(), "msg11001");
            return;
        }
        fd = new FileDialog(getLogin(), getString("saveTo"), FileDialog.SAVE);
        fd.setModal(true);
        fd.setResizable(false);
        fd.show();
        strDir = fd.getDirectory();
        file = fd.getFile();
        if (strDir != null && file != null) {
            if (!Routines.endsWithIgnoreCase(file, ".csv")) {
                file = file + ".csv";
            }
            gio().writeString(strDir + file, export);
        }
    }

    /**
     * print
     * @author Anthony C. Liberto
     */
    public void print() {
        data.getTable().print();
        setBusy(false);
    }

    /**
     * loadSearch
     * @author Anthony C. Liberto
     */
    public void loadSearch() {
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        if (navList != null) {
            return navList.getParentActionItem().getKey();
        }
        return null;
    }

    /**
     * getOPWGID
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
        return opwgID;
    }

    /**
     * getSessionID
     * @return
     * @author Anthony C. Liberto
     */
    public int getSessionID() {
        return sessID;
    }

    /**
     * loadRestore
     * @author Anthony C. Liberto
     *
    public void loadRestore() {
    }*/

    /**
     * loadAction
     * @param _o
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void loadAction(Object _o, int _navType) {
        if (_o instanceof SubAction) {
            if (action != null) {
                performAction(action.getActionItem(((SubAction) _o).getKey()), _navType);
            } else if (data instanceof NavDataDouble) {
                NavAction dataAction = ((NavDataDouble) data).getNavAction();
                performAction(dataAction.getActionItem(((SubAction) _o).getKey()), _navType);
            } else {
                appendLog("navigate.loadAction() _o.class " + _o.getClass().getName());
            }
        }
    }

    /**
     * loadMatrix
     * @author Anthony C. Liberto
     *
    public void loadMatrix() {
    }*/

    /**
     * loadPersistantLock
     * @param _all
     * @author Anthony C. Liberto
     */
    public void loadPersistantLock(final boolean _all) {
        final ESwingWorker myWorker = new ESwingWorker() {
            //boolean bLockList = false;
            public Object construct() {
				try{
					if (!isBusy()) {
						setBusy(true);
						if (viewLockExist(_all)) { //51160
							return null; //51160
						} //51160
						getNewProfileInstance();
						if (_all) {
							return dBase().getAllSoftLocksForWGID(getComponent());
						}
						return dBase().getLockList(true, getComponent());
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in Navigate.loadPersistantLock.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setBusy(false);
				}

                return null;
            }

            public void finished() {
                Object o = getValue();
                if (o instanceof LockList) {
                    createPersistantLock((LockList) o, _all); //50836
                    //50836					setLockList((LockList)o);
                    //50836					createPersistantLock(_all);
                }
                setWorker(null); //acl
                setBusy(false);
                return;
            }
        };
        setWorker(myWorker);
    }

    private void createPersistantLock(LockList _ll, boolean _all) { 
        ActionController ac = new ActionController(_ll, getKey());
        ac.setLockListAll(_all); //50892
        ac.setParentProfile(getProfile());
        addTab(parent, ac); 
    }

    /**
     * loadUsed
     * @author Anthony C. Liberto
     */
   // public void loadUsed() {
   // }

    /**
     * isCreatable
     * @return
     * @author Anthony C. Liberto
     *
    public boolean isCreatable() {
        EntityGroup eg = data.getEntityGroup();
        if (eg != null) {
            return eg.isCreatable();
        }
        return false;
    }*/

    /**
     * deactivateEntity
     * @author Anthony C. Liberto
     *
    public void deactivateEntity() {
    }*/

    private EntityItem[] getDeactivateEntityItems(DeleteActionItem _dai, EntityItem[] _ei) { //23448
        Vector v = null;
        int ii = -1;
        int reply = -1;
        int xx = -1;
        EntityItem[] out = null;
        if (_ei == null) {
            return null;
        }
        v = new Vector();
        ii = _ei.length;
        setCode("msg23032.1"); //23448
        setParmCount(2); //23448
        setParm(0, _dai.toString()); //23448
        for (int i = 0; i < ii; ++i) {
            if (reply != 2) {
                String s = _ei[i].toString() + " (" + _ei[i].getEntityType() + ":" + _ei[i].getEntityID() + ")";
                setParm(1, s); //23448
                if (ii == 1) {
                    reply = showConfirm(getComponent(), OK_CANCEL, false);
                } else {
                    reply = showConfirm(getComponent(), YES_NO_ALL_CANCEL, false);
                }
            }
            if (reply == 0 || (reply == 2 && ii > 1)) {
                v.add(_ei[i]);
            } else if (reply == -1) {
                break;
            }
        }
        eaccess().clear();
        if (reply == -1) {
            return null;
        }
        if (v.isEmpty()) {
            return null;
        }
        xx = v.size();
        out = new EntityItem[xx];
        for (int x = 0; x < xx; ++x) {
            out[x] = (EntityItem) v.get(x);
        }
        return out;
    }

    private void gotoTab() {
        String s = null;
        setCode("msg11002");
        s = showInput(getComponent());
        if (Routines.have(s)) {
            data.gotoTab(s);
        }
    }

    /**
     * deactivateLink
     * @author Anthony C. Liberto
     *
    public void deactivateLink() {
    }*/

    private void refresh() {
        refresh(true);
    }

    /**
     * refresh
     * @param _breakable
     * @author Anthony C. Liberto
     */
    public void refresh(boolean _breakable) {
        EntityItem[] ei = null;
        try {
            ei = getSelectedEntityItems(false, true);
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
        }

        NavSerialObject nso = this.nsoController.getCurrentNso();
        nso.resetKeys(data.getEntityGroupKey(), ei); 
        spawnNavigationThread(nso.getAction(), nso.getEntityItems(), nso.getKey(), NAVIGATE_REFRESH, _breakable); //52527
    }

    /**
     * cut
     * @author Anthony C. Liberto
     */
    public void cut() {
        EntityItem[] ei = null;
        try {
            ei = data.getSelectedEntityItems(false, true);
            cart.addCutItems(ei);
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            showException(_range, getComponent(), FYI_MESSAGE, OK);
        }
    }

    /**
     * copy
     * @author Anthony C. Liberto
     *
    public void copy() {
    }*/

    /**
     * remove
     * @param _file
     * @author Anthony C. Liberto
     * /
    public void remove(String _file) {
        gio().delete(dir, _file);
    }*/

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFiltered() {
        if (data != null) {
            NavTable nt = data.getTable();
            if (nt != null) {
                return nt.isFiltered();
            }
        }
        return false;
    }

    /**
     * renavigate - called from history
     * @param _nso
     * @param _navType
     */
    public void renavigate(final String _fn, final int _navType) {
        if (isPin()) {
            getNewProfileInstance();
        }
        if(nsoController.isRefresh(_fn)){
        //if (nso != null && nso == _nso) { //23014
            refresh(false); //23014
        } else { //23014
            final ESwingWorker myWorker = new ESwingWorker() {
                private NavSerialObject nso = null;
                public Object construct() {
					try{
						nso = nsoController.readNavSerialObject(_fn); // this may end up in a new navigate
						if (nso!=null){
							//Long t1 = EAccess.eaccess().timestamp("       renavigate started");
							Object o = nso.replay();
							//EAccess.eaccess().timestamp("       renavigate ended",t1);
							return o;
						}
					}catch(Exception ex){ // prevent hang
						appendLog("Exception in Navigate.renavigate.ESwingWorker.construct() "+ex);
						ex.printStackTrace();
						setBusy(false);
					}
					return null;
                }

                public void finished() {
                    Object o = getValue();
                    if (o instanceof EntityList) {
                    	Long t1 = EAccess.eaccess().timestamp("Navigate renavigate.redisplay started");
                        reload((EntityList) o, nso, null, _navType);
                        EAccess.eaccess().timestamp("Navigate renavigate.redisplay complete",t1);
                    } else if (history != null) {								//PKUR-6CFDY6
						history.reselectIndex(history.getHistoryCount()-1);		//PKUR-6CFDY6
					}															//PKUR-6CFDY6
                    setWorker(null);
                    setBusy(false);
                }
            };
            setWorker(myWorker);
        }
    }

    /**
     * renavigate
     * @param _date
     * @author Anthony C. Liberto
     *
    public void renavigate(String _date) {
        reload(nsoController.getCurrentNso(), NAVIGATE_RENAVIGATE);
    }*/

    /**
     * performAction
     * @param _nai
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void performAction(EANActionItem _nai, int _navType) {
        EntityItem[] ei = null;
        if (_nai != null) {
			System.out.println("****    performAction(" + _nai.getKey() + " on " + _nai.getDataSourceAsString() + ")    ****");
			if (_nai.hasAdditional()) {
				System.out.println("****    performAdditional(" + _nai.getAdditional() + ")    ****");
			}
		}

        try {
            //cr_1310			ei = getSelectedEntityItems(false);		//acl_20020219
            ei = getSelectedEntityItems(false, !(_nai instanceof WorkflowActionItem)); //cr_1310
        } catch (OutOfRangeException _range) {
            _range.printStackTrace();
            //51260			setMessage(_range.toString());
            //51260			showError(getComponent());
            showException(_range, getComponent(), FYI_MESSAGE, OK);
            return;
        }

		if(ei!=null &&
			(_nai instanceof ReportActionItem || _nai instanceof EditActionItem ||
			_nai instanceof SearchActionItem)){ // RQ0713072645
			// SearchActionItem.setParentEntityItems() removes EntityGroup so cant check for nav attribute in SearchAction
			// check the domains here
			// check edit to output warning msg, stop report here too
			try{
				EntityList.checkDomain(getActiveProfile(),_nai,ei); // RQ0713072645
			}catch(DomainException de){
				if (!(_nai instanceof EditActionItem)){ // just do it for edit here to get msg, it will be enforced in mw
		            showException(de, getComponent(), ERROR_MESSAGE, OK);
					return;
				}else{
					setMessage(de.getMessage());
					showFYI(getComponent());
		            de.dereference();
				}
			}
		}

        if (_nai instanceof ReportActionItem) { //53782
            if (ei != null && ei.length > 100) { //53782
                showError(getComponent(), "msg12011.0"); //53782
                return; //53782
            } //53782
        } //53782

        if (_nai != null && _nai.isSingleInput()) { //CR_0622046959
            if (ei == null || ei.length != 1) { //CR_0622046959
                showError(getComponent(), "msg30a1"); //CR_0622046959
                return; //CR_0622046959
            } //CR_0622046959
        } //CR_0622046959

        if (_nai instanceof CreateActionItem && ((CreateActionItem) _nai).isPeerCreate()) { //peer_create
            //		if (_nai instanceof CreateActionItem && (((CreateActionItem)_nai).isPeerCreate() ||((CreateActionItem)_nai).isStandAlone())) {			//peer_create2
            performAction(true, _nai, getParentEntityItems(), data.getEntityGroupKey(), _navType); //peer_create
            return; //peer_create
        } //peer_create

        if (ei == null) {
            if (isParentlessSearch(_nai)) { //3.0a
            } else if (!(_nai instanceof NavActionItem)) {
                showError(getComponent(), "msg11006");
                return;
            } else if (_navType != NAVIGATE_INIT_LOAD) {
                NavActionItem nav = (NavActionItem) _nai;
                if (!nav.isHomeEnabled() && !(nav.isPicklist() && nav.useRootEI())) { //dwb_20030827
                    showError(getComponent(), "msg11006");
                    return;
                } //dwb_20030827
            }
        }

        if (_nai instanceof NavActionItem) {
            NavActionItem nav = (NavActionItem) _nai;
            if (nav.isPicklist() && nav.useRootEI()) { //DWB_20040908
            } else if (!nav.isHomeEnabled() && !validEntityItems(ei)) { //dwb_20030827
                showError(getComponent(), "msg11006"); //dwb_20030827
                return; //dwb_20030827
            } //dwb_20030827
        } else if (isParentlessSearch(_nai)) { //3.0a
        } else if (!validEntityItems(ei)) { //22088
            showError(getComponent(), "msg11006"); //19997
            return; //19997
        } //19997

        if (_nai instanceof EditActionItem) { //20059
            if (editContains(ei, _nai)) { //20059
                return; //20059
            } //20059
        } else if (_nai instanceof CreateActionItem) { //20059
            if (editContains(ei, _nai)) { //20059
                return; //20059
            } //20059
        } else if (_nai instanceof WhereUsedActionItem) {
            if (editContains(ei, _nai)) {
                return;
            }
        } else if (_nai instanceof MatrixActionItem) {
            if (editContains(ei, _nai)) {
                return;
            }
        } //20059

        if (!processableAction(_nai, ei)) { //chgroup
            return; //chgroup
        } //chgroup

        if (_nai.useRootEI()) { //root_search
            performAction(true, _nai, getParentEntityItems(), data.getEntityGroupKey(), _navType); //root_search
        } else { //root_search
            performAction(true, _nai, ei, data.getEntityGroupKey(), _navType); //breakable
        }
    }

    /**
     * performAction
     * @param _sepThread
     * @param _ai
     * @param _ei
     * @param _key
     * @param _navType
     * @author Anthony C. Liberto
     */
    private void performAction(boolean _sepThread, EANActionItem _ai, EntityItem[] _ei, String _key, int _navType) {
        if (eaccess().isDebug()) {
            if (_ei != null && _ai != null) {
                int ii = _ei.length;
                appendLog("performing action (" + _ai.getKey() + ")on...");
                appendLog(" using root: " + _ai.useRootEI());
                for (int i = 0; i < ii; ++i) {
                    if (_ei[i] != null) {
                        appendLog("    _ei[" + i + "]: " + _ei[i].getKey());
                        if (_ei[i].hasUpLinks()) {
                            int xx = _ei[i].getUpLinkCount();
                            for (int x = 0; x < xx; ++x) {
                                appendLog("        uplink(" + x + " of " + xx + "): " + _ei[i].getUpLink(x).getKey());
                            }
                        }
                        if (_ei[i].hasDownLinks()) {
                            int xx = _ei[i].getDownLinkCount();
                            for (int x = 0; x < xx; ++x) {
                                appendLog("        downlink(" + x + " of " + xx + "): " + _ei[i].getDownLink(x).getKey());
                            }
                        }
                    }
                }
            }
        }
        if (isBusy()) {
            return;
        }
        setBusy(true);
        if (_sepThread && !(_ai instanceof SearchActionItem || _ai instanceof PDGActionItem || _ai instanceof SPDGActionItem)) { //22541
            //52527			spawnNavigationThread(_ai, _ei, _key, _navType);
            spawnNavigationThread(_ai, _ei, _key, _navType, true); //52527
        } else {
			try{
				if (_ai instanceof NavActionItem) {
					if (isPin()) {
						getNewProfileInstance();
					} 
					if (((NavActionItem) _ai).isHomeEnabled()) { //dwb_20030827
						//finishAction(nso.navigate((NavActionItem) _ai, null, _key), _ei, _navType); //dwb_20030827
						finishAction(nsoController.navigate((NavActionItem) _ai, null, _key), _ei, _navType);
					} else if (validEntityItems(_ei) && nsoController.getCurrentNso() != null) {
						//finishAction(nso.navigate((NavActionItem) _ai, _ei, _key), _ei, _navType);
						finishAction(nsoController.navigate((NavActionItem) _ai, _ei, _key), _ei, _navType);
					} else {
						finishAction(dBase().getEntityList(null, null, getComponent()), _ei,_navType);	
					}
				} else if (_ai instanceof ABRStatusActionItem) { //cr_2115
					finishAction(dBase().getEntityList((ABRStatusActionItem) _ai, _ei, getComponent()), _ei, _navType); //cr_2115
				} else if (_ai instanceof CreateActionItem) {
					if (!isPast()) { //20058
						CreateActionItem create = (CreateActionItem) _ai; //bluePageCreate
						if (bluePageCreate(create)) { //bluePageCreate
							finishAction(dBase().getEntityList(create, _ei, getComponent()),_ei, _navType); //bluePageCreate
						} //bluePageCreate
						//bluePageCreate					finishAction(dBase().getEntityList(_ai, _ei,getComponent()),_navType);
					}
				} else if (_ai instanceof SearchActionItem) {
					SearchActionItem search = (SearchActionItem) _ai;
					if (isPin()) {
						getNewProfileInstance();
					}
					if (search.isGrabByEntityID()) { //cr_1129034004
						int iID = getSearchID(); //cr_1129034004
						if (iID >= 0) { //cr_1129034004
							search.setGrabEntityID(iID); //cr_1129034004
							finishAction(dBase().getEntityList(search, null, getComponent()), _ei, _navType); //cr_1129034004
						} else { //cr_1129034004
							setBusy(false); //cr_1129034004
						} //cr_1129034004
					} else { //cr_1129034004
						if (_ei == null && search.isParentLess()) { //3.0a
						} else { //3.0a
							search.setParentEntityItems(_ei);
						} //3.0a
						showSearch(search, _navType, this); //22541
					} //cr_1129034004
				} else if (_ai instanceof MatrixActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction MatrixActionItem started");
					MatrixActionItem mai = (MatrixActionItem) _ai;
					finishAction(_ei, dBase().rexec(mai, _ei, getComponent()), _navType);
					EAccess.eaccess().timestamp("Navigate performAction MatrixActionItem ended",t11);
				} else if (_ai instanceof EditActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction EditActionItem started");
					finishAction(dBase().getEntityList(_ai, _ei, getComponent()), _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction EditActionItem ended",t11);
				} else if (_ai instanceof LinkActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction LinkActionItem started");
					finishAction(_ai, _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction LinkActionItem ended",t11);
				} else if (_ai instanceof CopyActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction CopyActionItem started");
					finishAction(_ai, _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction CopyActionItem ended",t11);
				} else if (_ai instanceof ReportActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction ReportActionItem started");
					finishAction(_ai, _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction ReportActionItem ended",t11);
				} else if (_ai instanceof WorkflowActionItem) {
					finishAction(_ai, _ei, _navType);
				} else if (_ai instanceof DeleteActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction DeleteActionItem started");
					finishAction(_ai, _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction DeleteActionItem ended",t11);
				} else if (_ai instanceof LockActionItem) {
					finishAction(_ai, _ei, _navType);
				} else if (_ai instanceof ExtractActionItem) {
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction ExtractActionItem started");
					finishAction(_ai, _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction ExtractActionItem ended",t11);
				} else if (_ai instanceof PDGActionItem) {
					processPDGActionItem((PDGActionItem) _ai, _ei);
				} else if (_ai instanceof SPDGActionItem) {
					processSPDGActionItem((SPDGActionItem) _ai, _ei);
				} else if (_ai instanceof WhereUsedActionItem) {
					WhereUsedActionItem wai = (WhereUsedActionItem) _ai;
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction WhereUsedActionItem started");
					finishAction(dBase().rexec(wai, _ei, getComponent()), _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction WhereUsedActionItem ended",t11);
				} else if (_ai instanceof QueryActionItem) {
					QueryActionItem qai = (QueryActionItem) _ai;
					Long t11 = EAccess.eaccess().timestamp("Navigate performAction QueryActionItem started");
					finishAction(dBase().rexec(qai, _ei, getComponent()), _ei, _navType);
					EAccess.eaccess().timestamp("Navigate performAction QueryActionItem ended",t11);
				} else {
					setMessage("unsupported ActionItem encountered: " + _ai.getClass().getName());
					showError(getComponent());
				}
			}catch(Exception exc){
				setMessage("Exception in "+_ai+": " + exc);
				exc.printStackTrace(System.err);
				showError(getComponent());
				setBusy(false);
			}
        }
    }

    private boolean validEntityItems(EntityItem[] _ei) {
        if (_ei == null) {
            return false;
        } else if (_ei.length == 0) {
            return false;
        } else if (hasNull(_ei)) { //22088
            return false; //22088
        }
        return true;
    }

    private boolean hasNull(EntityItem[] _ei) { //22088
        int ii = _ei.length; //22088
        for (int i = 0; i < ii; ++i) { //22088
            if (_ei[i] == null) { //22088
                return true; //22088
            } //22088
        } //22088
        return false; //22088
    } //22088

    /**
     * performAction
     * @param _sepThread
     * @param _new
     * @param _navType
     * @author Anthony C. Liberto
     */
    private void performAction(boolean _sepThread, boolean _new, int _navType) { //entityList
        if (_sepThread) {
            if (_new || navList == null) {
                //52527				spawnNavigationThread(null,null,null,_navType);
                spawnNavigationThread(null, null, null, _navType, true); //52527
            }
        } else {
            if (_new || navList == null) {
                finishAction(dBase().getEntityList(null, null, getComponent()), null, _navType); //entityList
            }
        }
    } //entityList

    //52527	private void spawnNavigationThread(final EANActionItem _ai, final EntityItem[] _ei, final String _key, final int _navType) {
    private void spawnNavigationThread(final EANActionItem _ai, final EntityItem[] _ei, final String _key, final int _navType, final boolean _break) { //52527
        final ESwingWorker myWorker = new ESwingWorker() {
            public Object construct() {
                Object o = null;
                try{
					if (_ai == null && _ei == null) {
						o = dBase().getEntityList(null, null, getComponent()); //entityList
					} else if (_ai.hasAdditional()) {
						if (_ai.isCatalogDataSource()) {
							Object[] tmp = new Object[3];
							tmp[0] = getActiveProfile();
							tmp[1] = _ei[0].getEntityType();
							tmp[2] = new Integer(_ei[0].getEntityID());
							o = dBase().rexecAdditional(_ai,_ei,tmp,getComponent());
							if (o == null) {
								System.out.println("NO Additional Information Retrieved");
							} else {
								System.out.println("retrieved additional information!!!");
								System.out.println("    class: " + o.getClass().getName());
							}
						}
					} else {
						if (_ai instanceof NavActionItem) {
							if (isPin()) {
								getNewProfileInstance();
							}
							if (((NavActionItem) _ai).isHomeEnabled()) { //dwb_20030827
								if (_navType == NAVIGATE_REFRESH) { //dwb_20030827
									//o = nso.replay(); //dwb_20030827
									o = nsoController.replay();
								} else { //dwb_20030827
									//o = nso.navigate((NavActionItem) _ai, null, _key); //dwb_20030827
									o = nsoController.navigate((NavActionItem) _ai, null, _key);
								} //dwb_20030827
							} else if (validEntityItems(_ei)) {
								if (_navType == NAVIGATE_REFRESH) { //23316
									//Long t1 = eaccess().timestamp("database replay Started");
									//o = nso.replay(); //23316
									o = nsoController.replay();
									//eaccess().timestamp("database replay Complete",t1);
								} else { //23316
									//Long t1 =eaccess().timestamp("database navigate Started");
									//o = nso.navigate((NavActionItem) _ai, _ei, _key);
									o = nsoController.navigate((NavActionItem) _ai, _ei, _key);
									//eaccess().timestamp("database navigate Complete",t1);
								} //23316
							} else {
								o = dBase().getEntityList(null, null, getComponent());
							}
						} else if (_ai instanceof CopyActionItem) { //copyAction
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist CopyActionItem started");
							CopyActionItem cai = (CopyActionItem) _ai; //copyAction
							int iCopies = eaccess().getNumber("msg3013"); //copyAction
							if (iCopies > 0) { //copyAction
								cai.setNumOfCopy(iCopies); //copyAction
								o = dBase().getEntityList(cai, _ei, getComponent()); //copyAction
							} //copyAction
							 EAccess.eaccess().timestamp("Navigate getlist CopyActionItem ended",t11);
						} else if (_ai instanceof ABRStatusActionItem) { //cr_2115
							o = dBase().getEntityList(_ai, _ei, getComponent()); //cr_2115
						} else if (_ai instanceof CreateActionItem) {
							if (!isPast()) { //20058
								Long t11 = EAccess.eaccess().timestamp("Navigate getlist CreateActionItem started");
								CreateActionItem create = (CreateActionItem) _ai; //bluePageCreate
								if (bluePageCreate(create)) { //bluePageCreate
									o = dBase().getEntityList(create, _ei, getComponent()); //bluePageCreate
								} //bluePageCreate
								 EAccess.eaccess().timestamp("Navigate getlist CreateActionItem ended",t11);
								//bluePageCreate							o = dBase().getEntityList(_ai, _ei,getComponent());
							}
						} else if (_ai instanceof SearchActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist SearchActionItem started");
							SearchActionItem search = null;
							if (isPin()) {
								getNewProfileInstance();
							}
							search = (SearchActionItem) _ai;
							if (search.isGrabByEntityID()) { //cr_1129034004
								o = dBase().getEntityList(search, null, getComponent()); //cr_1129034004
								//							}																//cr_1129034004
							} else { //cr_1129034004
								o = dBase().getEntityList(search, null, getComponent()); //22541
							} //cr_1129034004
							EAccess.eaccess().timestamp("Navigate getlist SearchActionItem ended",t11);
						} else if (_ai instanceof MatrixActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist MatrixActionItem started");
							MatrixActionItem mai = (MatrixActionItem) _ai;
							o = dBase().rexec(mai, _ei, getComponent());
							EAccess.eaccess().timestamp("Navigate getlist MatrixActionItem ended",t11);
						} else if (_ai instanceof WhereUsedActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist WhereUsedActionItem started");
							WhereUsedActionItem wai = (WhereUsedActionItem) _ai;
							o = dBase().rexec(wai, _ei, getComponent());
							EAccess.eaccess().timestamp("Navigate getlist WhereUsedActionItem ended",t11);
						} else if (_ai instanceof QueryActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist QueryActionItem started");
							QueryActionItem qai = (QueryActionItem) _ai;
							o = dBase().rexec(qai, _ei, getComponent());
							EAccess.eaccess().timestamp("Navigate getlist QueryActionItem ended",t11);
						}else if (_ai instanceof EditActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate getlist EditActionItem started");
							o = dBase().getEntityList(_ai, _ei, getComponent());
							EAccess.eaccess().timestamp("Navigate getlist EditActionItem ended",t11);
						} else if (_ai instanceof LinkActionItem) {
							Long t11 = EAccess.eaccess().timestamp("Navigate thread LinkActionItem started");
							setInterruptable(false); //50942
							finishAction(_ai, _ei, _navType);
							EAccess.eaccess().timestamp("Navigate thread LinkActionItem ended",t11);
						} else if (_ai instanceof ReportActionItem) {
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof WorkflowActionItem) {
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof DeleteActionItem) {
							setInterruptable(false); //50942
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof LockActionItem) {
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof ExtractActionItem) {
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof PDGActionItem) {
							finishAction(_ai, _ei, _navType);
						} else if (_ai instanceof SPDGActionItem) {
							finishAction(_ai, _ei, _navType);
						} else {
							appendLog("ActionItem: " + _ai.getClass().getName() + " is not supported.");
							o = null;
						}
					}
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in Navigate.spawnNavigationThread.ESwingWorker.construct() "+ex);
	 				setMessage("Exception in "+_ai+": " + ex);
    				ex.printStackTrace(System.err);
    				showError(getComponent());
					setBusy(false);
				}
                return o;
            }

           /* public boolean isBreakable() { //52527
                return _break; //52527
            } //52527
            */
            public void finished() {
            	try{
            		Object o = getValue();
            		if (o instanceof EntityList) {
            			if (_navType == NAVIGATE_REFRESH && !isDereferenced()) { //52732
            				Long t1 =eaccess().timestamp("Navigate redisplay Started");
            				reload((EntityList) o, nsoController.getCurrentNso(), null, NAVIGATE_REFRESH); //23316
            				eaccess().timestamp("Navigate redisplay Complete",t1);
            			} else { //23316
            				Long t1 =eaccess().timestamp("Navigate display Started");
            				finishAction((EntityList) o, _ei, _navType);
            				eaccess().timestamp("Navigate display Complete",t1);
            			} //23316
            		} else if (o instanceof MatrixList) {
            			Long t1 =eaccess().timestamp("Navigate MatrixList display Started");
            			finishAction(_ei, (MatrixList) o, _navType);
            			eaccess().timestamp("Navigate MatrixList display Complete",t1);
            		} else if (o instanceof WhereUsedList) {
            			Long t1 =eaccess().timestamp("Navigate WhereUsedList display Started");
            			finishAction((WhereUsedList) o, _ei, _navType);
            			eaccess().timestamp("Navigate WhereUsedList display Complete",t1);
            		}else if (o instanceof QueryList) {
            			Long t1 =eaccess().timestamp("Navigate QueryList display Started");
            			finishAction((QueryList) o, _ei, _navType);
            			eaccess().timestamp("Navigate QueryList display Complete",t1);
            		}
            	}catch(Exception exc){
            		setMessage("Exception in "+_ai+": " + exc);
            		exc.printStackTrace(System.err);
            		showError(getComponent());
            	}
            	setWorker(null); //acl
            	setBusy(false); //23316
            	eaccess().validate(); //52796
            }
        };
        setWorker(myWorker);
    }

    /*
     * should always becalled to complete processing of entityList actions
     */
    private void finishAction(EntityList _eList, EntityItem[] _ei, int _navType) {
        EANActionItem parentAction = null;
        EntityGroup eg = null;														//CR_1124045246
        if (_eList == null) {
            return;
        }
        parentAction = _eList.getParentActionItem();
        if (parentAction instanceof NavActionItem) {
            processNavigateAction(_eList, _navType);
        } else if (parentAction instanceof CopyActionItem) { //copyAction
            processEditAction(_eList, _navType); //copyAction
        } else if (parentAction instanceof ABRStatusActionItem) { //cr_2115
            processNavigateAction(_eList, _navType); //cr_2115
        } else if (parentAction instanceof CreateActionItem) {
            processCreateAction(_eList, _navType);
        } else if (parentAction instanceof SearchActionItem) {
            processSearchAction(null, _eList, _navType);
        } else if (parentAction instanceof EditActionItem) {
            processEditAction(_eList, _navType);
        }
        eg = _eList.getParentEntityGroup();											//CR_1124045246
        if (eg != null) {															//CR_1124045246
	        clearAction(parentAction,eg.getEntityItemsAsArray());					//CR_1124045246
		}																			//CR_1124045246
        setBusy(false);
    }

    private void finishAction(EntityItem[] _ei, MatrixList _mList, int _navType) {
        if (_mList == null) {
            return;
        }
        processMatrixAction(_ei, _mList, _navType);
        clearAction(_mList.getParentActionItem(),_ei);							//CR_1124045246
        setBusy(false);
    }

    private void finishAction(WhereUsedList _wList, EntityItem[] _ei, int _navType) {
        if (_wList == null) {
            return;
        }
        processWhereUsedAction(_wList, _navType);
        clearAction(_wList.getParentActionItem(),_ei);							//CR_1124045246
        setBusy(false);
    }
    
    private void finishAction(QueryList _qList, EntityItem[] _ei, int _navType) {
    	if (_qList != null) {
    		processQueryAction(_qList, _navType);
    		clearAction(_qList.getParentActionItem(),_ei);	
    		setBusy(false);
    	}
    }    

    /*
     * entityList actions
     */
    private void processNavigateAction(EntityList _eList, int _navType) {
        //53171		if (_eList != null) {						//52993
        if (_eList != null && !isDereferenced()) { //53171
            load(_eList, _navType);
            if (data != null) { //52993
                NavTable nt = data.getTable(0);
                if (nt != null) {
                    nt.requestFocus();
                }
            } //52993
        } //52993
    }

    private void processCreateAction(EntityList _eList, int _navType) {
        EditController ec = new EditController(_eList, this);
        ec.setParentProfile(getProfile());
        ec.setSelectorEnabled(true);
        addTab(parent, ec); 
    }

    /**
     * processSearchAction
     * @param _id
     * @param _eList
     * @param _navType
     * @return
     * @author Anthony C. Liberto
     */
    public boolean processSearchAction(InterfaceDialog _id, EntityList _eList, int _navType) { //22541
        if (_eList.getTable().getRowCount() == 0) {
            //acl			showFYI("msg24005");
            showFYI((Component) _id, "msg24005");
            return false;
        }
        processNavigateAction(_eList, _navType);
        return true;
    }

    private void processEditAction(EntityList _eList, int _navType) {
        Long t1 = eaccess().timestamp("navigate.processEditAction():00 at: ");
        EditController ec = new EditController(_eList, this);
        eaccess().timestamp("navigate.processEditAction():01 at: ",t1);
        ec.setParentProfile(getProfile());
        addTab(parent, ec); //kehrli_20030929
        eaccess().timestamp("navigate.processEditAction() done: ",t1);
    }

    private void processMatrixAction(EntityItem[] _ei, MatrixList _mList, int _navType) {
        ActionController ac = new ActionController(_mList, getKey());
        ac.setParentProfile(getProfile());
        ac.setOPWGID(getOPWGID());
        ac.setEntityItemArray(_ei);
        ac.setCart(cart); //acl_20031219
        addTab(parent, ac); //kehrli_20030929
        ac.requestFocus(-1);
    }

    private void processWhereUsedAction(WhereUsedList _wList, int _navType) {
        ActionController ac = null;
        if (_wList == null) {
            return;
        }
        ac = new ActionController(_wList, getKey());
        ac.setParentProfile(getProfile());
        ac.setOPWGID(getOPWGID());
        ac.setCart(cart);

        addTab(parent, ac); 
        ac.requestFocus(-1);
    }
    
    private void processQueryAction(QueryList _qList, int _navType) {
        ActionController ac = null;
        if (_qList == null) {
            return;
        }
        ac = new ActionController(_qList, getKey());
        ac.setParentProfile(getProfile());
        ac.setOPWGID(getOPWGID());
        ac.setCart(cart);
        addTab(parent, ac); 
        ac.requestFocus(-1);
    }    
    /*
     * non-entityList actions
     */
    /*
     * should always becalled to complete processing of non-entityList actions
     */
    private void finishAction(EANActionItem _ai, EntityItem[] _ei, int _navType) {
        if (_ai == null) {
            return;
        }
        if (_ai instanceof LinkActionItem) {
            processLinkAction((LinkActionItem) _ai, _ei);
        } else if (_ai instanceof ReportActionItem) {
            processReportAction((ReportActionItem) _ai, _ei);
        } else if (_ai instanceof WorkflowActionItem) {
            processWorkflowAction((WorkflowActionItem) _ai, _ei);
        } else if (_ai instanceof DeleteActionItem) {
            processDeleteAction((DeleteActionItem) _ai, _ei);
        } else if (_ai instanceof LockActionItem) {
            processLockAction((LockActionItem) _ai, _ei);
        } else if (_ai instanceof ExtractActionItem) {
            processExtractAction((ExtractActionItem) _ai, _ei,_navType);
        } else if (_ai instanceof PDGActionItem) {
            processPDGActionItem((PDGActionItem) _ai, _ei);
        } else if (_ai instanceof SPDGActionItem) { //SPDG
            processSPDGActionItem((SPDGActionItem) _ai, _ei);
        } else if (_ai instanceof CopyActionItem) { //copyAction
            processCopyAction((CopyActionItem) _ai, _ei); //copyAction
        }
        clearAction(_ai,_ei);							//CR_1124045246
        setBusy(false);
    }

    private void processLinkAction(LinkActionItem _link, EntityItem[] _ei) {
        WizardPanel wizard = (WizardPanel) eaccess().getPanel(WIZARD_PANEL);
        wizard.process(_link, _ei, this);
    }

    private void processCopyAction(CopyActionItem _copy, EntityItem[] _ei) { //copyAction
        spawnNavigationThread(_copy, _ei, nsoController.getSelectionKey(), NAVIGATE_REFRESH, false); //copyAction
    } //copyAction

    private void processReportAction(ReportActionItem _report, EntityItem[] _ei) {
        EAccess.eaccess().launchReport(_report, _ei);
    }

    private void processWorkflowAction(WorkflowActionItem _ai, EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            showError(getComponent(), "msg2009");
            return;
        }
        if (dBase().rexec(_ai, _ei, getComponent())) {
            synchronize();
        }
    }

    private void processDeleteAction(DeleteActionItem _ai, EntityItem[] _ei) {
        EntityItem[] aei = null;
        if (_ei == null || _ei.length == 0) {
            showError(getComponent(), "msg2009");
            return;
        }
        aei = getDeactivateEntityItems(_ai, _ei); //23448
        if (aei != null && aei.length > 0) { //22403
            if (dBase().rexec(_ai, aei, getComponent())) {
                //52527				synchronize();
                refresh(true); //52527
                resort(); //51052
            } else {
                return;
            }
            setCode("msg11020.0"); //22041
            setParm(_ai.toString()); //23448
            showFYI(getComponent()); //22041
        } //22041
    }

    private void processLockAction(LockActionItem _ai, EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            showError(getComponent(), "msg2009");
            return;
        }
        dBase().rexec(_ai, _ei, getComponent());
    }

    private void processExtractAction(ExtractActionItem _ai, EntityItem[] _ei, int _navType) {
        if (validEntityItems(_ei)) {
            EntityList eList = dBase().getEntityList(_ai, _ei, getComponent());
            if (eList != null){
				/*if (isTestMode()) {
					int reply = -1;
					reply = eaccess().showConfirm(getComponent(), A_B_C_D, "msg5024.0(test)",true);
					if (reply == 0) {
						processEditAction(eList,_navType);
						return;
					} else if (reply == 1) {
						processNavigateAction(eList,_navType);
						return;
					} else if (reply == 2) {
						String fileName = gio().getFileName(FileDialog.SAVE);
						if (fileName != null) {
							String strDump = eList.dump(false);
							gio().write(fileName, strDump);
							System.out.println("EntityList Dump...");
							System.out.println(strDump);
						}
					}
				}*/
				if (_ai.isVEEdit()) {
					processEditAction(eList,_navType);
				} else {
					String fileName = gio().getFileName(FileDialog.SAVE);
					if (fileName != null) {
						gio().write(fileName, eList.dump(false));
					}
				}
			}
        } else {
            showError(getComponent(), "msg2009");
        }
    }

    /*
     * this is a stop gap for the time being
     */
    private void synchronize() {
        refresh(false);
    }

    /*
     * Tabable
     */
    /**
     * getEntityType
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getEntityType(int _i) {
        EntityGroup eg = getEntityGroup();
        if (eg != null) {
            if (_i == 0) { //52727
                return eg.getEntityType(); //52727
            } else if (_i == 1) {
                return eg.getEntity1Type();
            } else if (_i == 2) {
                return eg.getEntity2Type();
            }
        }
        return null;
    }

    /**
     * createViewMenu
     * @author Anthony C. Liberto
     */
    protected void createViewMenu() {
        Profile prof = getActiveProfile();
        NLSItem nls = getActiveNLSItem();
        addSubMenu("View", "sel3", parent, 0, 0);
        if (prof != null) {
            NLSItem[] array = getNLSItemArray(prof);
            updateLanguageMenu(array, nls);
        }

        menu.addSeparator("View");
        addMenu("View", "docSel", parent, 0, 0, true);
        addMenu("View", "showCart", parent, KeyEvent.VK_W, Event.CTRL_MASK + Event.ALT_MASK, true);
        addMenu("View", "goto", parent, KeyEvent.VK_G, Event.CTRL_MASK, true);
        menu.setMenuMnemonic("View", 'v');
    }

    private NLSItem[] getNLSItemArray(Profile _prof) {
        Vector NLSVector = _prof.getReadLanguages();
        int ii = NLSVector.size();
        NLSItem[] out = new NLSItem[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = ((NLSItem) NLSVector.elementAt(i));
        }
        Arrays.sort(out, new EComparator(true));
        return out;
    }

    /**
     * updateLanguageMenu
     * @param _nlsArray
     * @param nls
     * @author Anthony C. Liberto
     */
    public void updateLanguageMenu(NLSItem[] _nlsArray, NLSItem nls) {
        if (menu != null) {
            menu.adjustLanguageMenu("sel3", _nlsArray, nls);
        }
    }

    /**
     * getSearchableObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSearchableObject() { //22377
        return data.getTable(); //22377
    } //22377

    /**
     * getHelpText
     * @return
     * @author Anthony C. Liberto
     */
    public String getHelpText() {
        if (data != null) {
            EntityGroup eg = data.getEntityGroup();
            if (eg != null) {
                String s = eg.getHelp(eg.getEntityType());
                if (Routines.have(s)) {
                    return s;
                }
            }
        }
        return getString("nia");
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        NavAction nAction = null;
        if (history != null) {
            history.refreshAppearance();
        }
        if (data != null) {
            data.refreshAppearance();
        }
        nAction = getAction(); //53496
        if (nAction != null) {
            nAction.refreshAppearance();
        }
        if (cart != null) {
            cart.refreshAppearance();
        }
        if (pick != null) {
            pick.refreshAppearance();
        }

        if (tBar != null) {
            tBar.refreshAppearance();
        }
        return;
    }

    /**
     * setShouldRefresh
     * @param _eType
     * @param _opwg
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setShouldRefresh(String _eType, int _opwg, int _code) { //acl_20021022
        if (parent != null) { //acl_20021022
            parent.setShouldRefresh(_eType, _opwg, _code); //acl_20021022
        } //acl_20021022
        return; //acl_20021022
    } //acl_20021022

    private void processPDGActionItem(PDGActionItem _pdgai, EntityItem[] _ei) {
        NavActionItem nai = _pdgai.getPDGNavAction();
//        EntityList el = null;
        if (_ei == null || _ei.length == 0) {
            return;
        }

        for (int i = 0; i < _ei.length; i++) { //53263
            EntityItem ei = _ei[i]; //53263
            EntityGroup eg = ei.getEntityGroup(); //53263
            if (eg.isRelator()||eg.isAssoc()) { //MN32759321 must handle assoc too
                _ei[i] = (EntityItem) ei.getDownLink(0); //53263
            }
        }

        if (!isPDGOn()) { //50530
            nai = _pdgai.getPDGNavAction();
            if (nai != null) {
                NavPDGPanel navPnl = getNavPDGPnl();
                navPnl.setSPDG(false);
                navPnl.showNavPDG(_pdgai, dBase().getEntityList(nai, _ei, getComponent()));
            } else {
                EditActionItem eai = _pdgai.getPDGEditAction();
                if (eai != null) {
                    EditPDGPanel editPnl = getEditPDGPnl();
                    editPnl.showEditPDG(_pdgai, dBase().getEntityList(eai, _ei, getComponent()));
                }
            }
        } else { //50530
            setMessage("Open only one PDG dialog at a time."); //50530
            showError(getComponent()); //50530
            setBusy(false); //50530
        }
        return;
    }

    private void processSPDGActionItem(SPDGActionItem _spdgai, EntityItem[] _ei) {
        NavActionItem nai = _spdgai.getPDGNavAction();
//        EntityList el = null;
        if (_ei == null || _ei.length == 0) {
            return;
        }
        if (!isPDGOn()) { //50530
            nai = _spdgai.getPDGNavAction();
            if (nai != null) {
                NavPDGPanel navPnl = getNavPDGPnl();
                navPnl.setSPDG(true);
                navPnl.showNavSPDG(_spdgai, dBase().getEntityList(nai, _ei, getComponent()));
            } else {
                //				EditActionItem eai = _spdgai.getPDGEditAction();
                //				if (eai != null) {
                //					EditPDGPanel editPnl = getEditPDGPnl();
                //					editPnl.showEditPDG(_spdgai, dBase().getEntityList(eai, _ei, getComponent()));
                //				}
            }
        } else { //50530
            setMessage("Open only one PDG dialog at a time."); //50530
            showError(getComponent()); //50530
            setBusy(false); //50530
        }
        return;
    }

    /*
     50568
    */
    private boolean hasData(EntityList _eList) {
        if (_eList != null) {
            int ii = _eList.getEntityGroupCount();
            for (int i = 0; i < ii; ++i) {
                EntityGroup eg = _eList.getEntityGroup(i);
                if (eg != null) {
                    //System.out.println(eg.getEntityItemCount() + " record(s) displayable: " + eg.isDisplayable() + " for key: " + eg.getKey());
                    if (eg.isDisplayable() && eg.getEntityItemCount() > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     50827
    */
    /**
     * updateMenuActions
     * @param _action
     * @author Anthony C. Liberto
     */
    public void updateMenuActions(NavAction _action) {
        boolean bHasData = hasData();
        EANActionItem[] eai = null;
        Profile prof = null;
        EANActionItem[] acts = null;
        Vector v = null;

        if (tBar == null && menu == null) {
            return;
        }

//TIR USRO-R-PKUR-6GEJEY        if (_action != null) {
		eai = getActionItemArray(_action,ACTION_PURPOSE_NAVIGATE);
		if (menu != null) {
			menu.adjustSubAction("nav", eai, bHasData, ACTION_NAVIGATE);
		}
		if (tBar != null) {
			tBar.adjustSubAction("nav", eai, bHasData, ACTION_NAVIGATE);
		}

		if (menu != null) {
			menu.adjustSubAction("navP", eai, bHasData, ACTION_PICK_LIST);
		}
		if (tBar != null) {
			tBar.adjustSubAction("navP", eai, bHasData, ACTION_PICK_LIST);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_SEARCH);
		//3.0a			if (menu != null) menu.adjustSubAction("search",eai,bHasData,ACTION_ALL);
		//3.0a			if (tBar != null) tBar.adjustSubAction("search",eai,bHasData,ACTION_ALL);
		if (menu != null) {
			menu.adjustSubAction("search", eai, true, ACTION_ALL);
		} //3.0a
		if (tBar != null) {
			tBar.adjustSubAction("search", eai, true, ACTION_ALL);
		} //3.0a

		eai = getActionItemArray(_action,ACTION_PURPOSE_CREATE);
		//peer_create			if (menu != null) menu.adjustSubAction("crte",eai,bHasData,ACTION_ALL);
		//peer_create			if (tBar != null) tBar.adjustSubAction("crte",eai,bHasData,ACTION_ALL);
		if (menu != null) {
			menu.adjustSubAction("crte", eai, true, ACTION_ALL);
		} //peer_create
		if (tBar != null) {
			tBar.adjustSubAction("crte", eai, true, ACTION_ALL);
		} //peer_create

//            eai = _action.getActionItemArray(ACTION_PURPOSE_EDIT);
		eai = getActionItemArray(_action,ACTION_PURPOSE_EDIT_EXTRACT);				//editVE
		if (eai != null) {															//editVE
			if (v == null) {														//editVE
				v = new Vector();													//editVE
			} else {																//editVE
				v.clear();															//editVE
			}																		//editVE
			for (int i=0;i<eai.length;++i) {										//editVE
				if (eai[i] instanceof ExtractActionItem) {							//editVE
					if (eai[i].isVEEdit()) {										//editVE
						v.add(eai[i]);												//editVE
					}																//editVE
				} else {															//editVE
					v.add(eai[i]);													//editVE
				}																	//editVE
			}																		//editVE
			if (v.isEmpty()) {														//editVE
				eai = null;															//editVE
			} else {																//editVE
				eai = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);		//editVE
			}																		//editVE
		}																			//editVE
		if (menu != null) {
			menu.adjustSubAction("edit", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("edit", eai, bHasData, ACTION_ALL);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_MATRIX);
		if (menu != null) {
			menu.adjustSubAction("mtrx", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("mtrx", eai, bHasData, ACTION_ALL);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_WHERE_USED);
		if (menu != null) {
			menu.adjustSubAction("used", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("used", eai, bHasData, ACTION_ALL);
		}
		
		eai = getActionItemArray(_action,ACTION_PURPOSE_QUERY);
		if (menu != null) {
			menu.adjustSubAction("query", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("query", eai, bHasData, ACTION_ALL);
		}		

		eai = getActionItemArray(_action,ACTION_PURPOSE_REPORT);
		if (menu != null) {
			menu.adjustSubAction("rprt", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("rprt", eai, bHasData, ACTION_ALL);
		}

		if (bHasData) { //3.0a
			eai = getActionItemArray(_action,ACTION_PURPOSE_LINK);
			if (menu != null) {
				menu.adjustSubAction("link", eai, bHasData, ACTION_ALL);
			}
			if (tBar != null) {
				tBar.adjustSubAction("link", eai, bHasData, ACTION_ALL);
			}
			if (data instanceof NavDataDouble) { //cr_209046022
				processLink(eai); //cr_209046022
			} //cr_209046022
		} else { //3.0a
			eai = getActionItemArray(_action,ACTION_PURPOSE_LINK); //3.0a
			acts = limitActions(0, eai); //3.0a
			if (menu != null) {
				menu.adjustSubAction("link", acts, true, ACTION_ALL);
			} //3.0a
			if (tBar != null) {
				tBar.adjustSubAction("link", acts, true, ACTION_ALL);
			} //3.0a
			if (data instanceof NavDataDouble) { //3.0a
				processLink(acts); //3.0a
			} //3.0a
		} //3.0a
		eai = getActionItemArray(_action,ACTION_PURPOSE_WORK_FLOW);
		if (menu != null) {
			menu.adjustSubAction("wFlow", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("wFlow", eai, bHasData, ACTION_ALL);
		}

//		System.out.println("checking for PDG");
		eai = getActionItemArray(_action,ACTION_PURPOSE_PDG); //USRO-R-JSTT-68RKKP
		if (menu != null) {
			menu.adjustSubAction("pdg", eai, true, ACTION_ALL);
		} //USRO-R-JSTT-68RKKP
		if (tBar != null) {
			tBar.adjustSubAction("pdg", eai, true, ACTION_ALL);
		} //USRO-R-JSTT-68RKKP

		eai = getActionItemArray(_action,ACTION_PURPOSE_EXTRACT);
		if (eai != null) {															//editVE
			if (v == null) {														//editVE
				v = new Vector();													//editVE
			} else {																//editVE
				v.clear();															//editVE
			}																		//editVE
			for (int i=0;i<eai.length;++i) {										//editVE
				if (!eai[i].isVEEdit()) {											//editVE
					v.add(eai[i]);													//editVE
				}																	//editVE
			}																		//editVE
			if (v.isEmpty()) {														//editVE
				eai = null;															//editVE
			} else {																//editVE
				eai = (EANActionItem[])v.toArray(new EANActionItem[v.size()]);		//editVE
			}																		//editVE
		}																			//editVE
		if (menu != null) {
			menu.adjustSubAction("xtract", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("xtract", eai, bHasData, ACTION_ALL);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_DELETE);
		if (menu != null) {
			menu.adjustSubAction("deAct", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("deAct", eai, bHasData, ACTION_ALL);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_CHANGE_GROUP); //chgroup
		if (menu != null) {
			menu.adjustSubAction("sGrp", eai, bHasData, ACTION_ALL);
		} //chgroup
		if (tBar != null) {
			tBar.adjustSubAction("sGrp", eai, bHasData, ACTION_ALL);
		} //chgroup

		eai = getActionItemArray(_action,ACTION_PURPOSE_LOCK);
		if (menu != null) {
			menu.adjustSubAction("lckP", eai, bHasData, ACTION_ALL);
		}
		if (tBar != null) {
			tBar.adjustSubAction("lckP", eai, bHasData, ACTION_ALL);
		}

		eai = getActionItemArray(_action,ACTION_PURPOSE_COPY); //copyAction
		if (menu != null) {
			menu.adjustSubAction("cpyA", eai, bHasData, ACTION_ALL);
		} //copyAction
		if (tBar != null) {
			tBar.adjustSubAction("cpyA", eai, bHasData, ACTION_ALL);
		} //copyAction

		eai = getActionItemArray(_action,ACTION_PURPOSE_ABRSTATUS); //cr_2115
		if (menu != null) {
			menu.adjustSubAction("abrS", eai, bHasData, ACTION_ALL);
		} //cr_2115
		if (tBar != null) {
			tBar.adjustSubAction("abrS", eai, bHasData, ACTION_ALL);
		} //cr_2115
//TIR USRO-R-PKUR-6GEJEY		}
        prof = getActiveProfile();
        if (prof != null && tBar != null) {
            tBar.setEnabled("viewP", !prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)); //51199
            tBar.setVisible("viewP", !prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)); //51199
            tBar.setEnabled("viewPW", prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS));
            tBar.setVisible("viewPW", prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS));
        }

        setEnabled("showCart", !isPast()); //51298
        setEnabled("cut", !isPast()); //51298
        setEnabled("cGrp", eaccess().hasChangeGroup()); //chgroup

        if (isMultipleNavigate()) {
            adjustPrevNext();
        }
    }

	/*
 	 TIR USRO-R-PKUR-6GEJEY
 	 added to properly clear out the action menu
	 */
	private EANActionItem [] getActionItemArray(NavAction _action,String _key) {
		if (_action != null) {
			return _action.getActionItemArray(_key);
		}
		return null;
	}

	/*
 	 TIR USRO-R-PKUR-6GEJEY
 	 added to properly clear out the action menu
	 */
	private EANActionItem [] getActionItemArray(NavAction _action,String[] _key) {
		if (_action != null) {
			return _action.getActionItemArray(_key);
		}
		return null;
	}

    private Component getComponent() {
        //53823		return null;
        if (parent != null) { //bluePageCreate
            return parent; //bluePageCreate
        } //bluePageCreate
        return eaccess().getLogin(); //53823
    }
    /*
     51052
     */
    private void resort() {
        NavTable table = data.getTable();
        if (table != null) {
            table.resort();
        }
        return;
    }
    /*
     51160
     */
    private boolean viewLockExist(boolean _all) {
        int ii = eaccess().getTabCount();
        for (int i = 0; i < ii; ++i) {
            ETabable etab = eaccess().getTab(i);
            if (etab instanceof ActionController) {
                if (((ActionController) etab).viewLockExist(_all)) {
                    eaccess().setSelectedIndex(i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * isMultipleNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMultipleNavigate() {
        if (parent != null) {
            return parent.isMultipleNavigate();
        }
        return false;
    }

    /**
     * getOpposingNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getOpposingNavigate() {
        if (parent != null) {
            return parent.getOpposingNavigate(this);
        }
        return null;
    }

    /**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isShowing() {
        if (data != null) {
            return data.isShowing();
        }
        return false;
    }

    /**
     * getSelectedBorder
     * @return
     * @author Anthony C. Liberto
     */
    public Border getSelectedBorder() {
        if (isMultipleNavigate()) {
            if (parent != null) {
                return parent.getSelectedBorder(this);
            }
        }
        return null;
    }

    /**
     * getActiveNavigate
     * @return
     * @author Anthony C. Liberto
     */
    public Navigate getActiveNavigate() {
        if (parent != null) {
            return parent.getNavigate();
        }
        return this;
    }
    /*
     log parse
     */
    /**
     * process
     * @param _method
     * @param _action
     * @param _parent
     * @param _child
     * @author Anthony C. Liberto
     */
    public void process(String _method, String _action, String[] _parent, String[] _child) {
        EANActionItem ean = null;
        NavTable nt = null;
        if (!Routines.have(_action)) {
            return;
        }
        if (data instanceof NavDataDouble) {
            NavAction dataAction = ((NavDataDouble) data).getNavAction();
            ean = dataAction.getActionItem(_action);
        } else if (action != null) {
            ean = action.getActionItem(_action);
        }

        if (ean != null) {
            int type = NAVIGATE_OTHER;
            if (ean instanceof NavActionItem) {
                type = NAVIGATE_LOAD;
            } else if (ean instanceof SearchActionItem) {
                type = NAVIGATE_LOAD;
            } else if (ean instanceof LinkActionItem) {
                WizardPanel wizard = (WizardPanel) eaccess().getPanel(WIZARD_PANEL);
                wizard.process((LinkActionItem) ean, _parent, _child, this);
                return;
            }

            nt = data.getTable();
            if (nt != null) {
                nt.highlight(_parent);
                try {
                    EntityItem[] ei = getSelectedEntityItems(false, true);
                    performAction(false, ean, ei, data.getEntityGroupKey(), type);
                } catch (OutOfRangeException _range) {
                    _range.printStackTrace();
                }
            }
        }
        return;
    }
    /*
     peer_create
     */
    private EntityItem[] getParentEntityItems() {
        EntityGroup tmpParent = getParentEntityGroup();
        if (tmpParent != null) {
            return tmpParent.getEntityItemsAsArray();
        }
        return null;
    }

    /*
     acl_20030718
     */
    /**
     * selectKeys
     * @param _keys
     * @author Anthony C. Liberto
     */
    public void selectKeys(String[] _keys) {
        NavTable nt = data.getTable();
        if (nt != null) {
            nt.selectKeys(_keys);
        }
        return;
    }

    /*
     restore action
     */
    private void loadRestore(String _restore) {
        ActionController ac = null;
        if (viewRestoreExist()) {
            return;
        }
        ac = new ActionController(_restore, getKey());
        ac.setParentProfile(getProfile());
        //kehrli_20030929		setCode("tabTitle");
        //kehrli_20030929		setParmCount(2);
        //kehrli_20030929		setParm(0,ac.getTableTitle());
        //kehrli_20030929		setParm(1,getActiveProfile().toString());
        //kehrli_20030929		String name = getMessage();
        //kehrli_20030929		String title = getString("restore.title");
        //kehrli_20030929		eaccess().clear();
        //51975		addTab(title + name, name,"restore.gif",ac, title + name);
        //kehrli_20030929		addTab(parent,title + name, name,"restore.gif",ac, title + name);		//51975
        addTab(parent, ac); //kehrli_20030929
        return;
    }

    private boolean viewRestoreExist() {
        int ii = eaccess().getTabCount();
        for (int i = 0; i < ii; ++i) {
            ETabable etab = eaccess().getTab(i);
            if (etab instanceof ActionController) {
                if (((ActionController) etab).viewRestoreExist()) {
                    eaccess().setSelectedIndex(i);
                    return true;
                }
            }
        }
        return false;
    }

    /*
     51555
     */
    /**
     * isType
     * @param _str
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isType(String _str, int _code) {
        EntityGroup eg = null;
        String str = null;
        if (bRefresh) {
            return true;
        }
        eg = getEntityGroup();
        switch (_code) {
        case KEY_TYPE :
            str = getKey();
            break;
        case RELATOR_TYPE :
            if (eg != null) {
                str = eg.getEntityType();
            }
            break;
        case PARENT_TYPE :
            if (eg != null) {
                str = eg.getEntity1Type();
            }
            break;
        case CHILD_TYPE :
            if (eg != null) {
                str = eg.getEntity2Type();
            }
            break;
        default:
            break;
        }
        if (str != null) {
            bRefresh = str.equals(_str);
        }
        return bRefresh;
    }

    /**
     * shouldRefresh
     * @return
     * @author Anthony C. Liberto
     */
    public boolean shouldRefresh() {
        return bRefresh;
    }

    /**
     * setShouldRefresh
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setShouldRefresh(boolean _b) {
        bRefresh = _b;
    }

    /*
     52189
     */
    /**
     * select
     * @author Anthony C. Liberto
     */
    public void select() {
    }
    /**
     * deselect
     * @author Anthony C. Liberto
     */
    public void deselect() {
    }

    /*
     kehrli_20030929
     */
    /**
     * getTabToolTipText
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabToolTipText() {
        if (history != null) {
            return history.getToolTipText();
        }
        return null;
    }

    /**
     * getTabIcon
     * @return
     * @author Anthony C. Liberto
     */
    public Icon getTabIcon() {
        if (isBookmark()) {
            return getImageIcon(getString("bookmark.icon"));
        }
        return getImageIcon(getString("navigate.icon"));
    }

    /**
     * getTabMenuTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabMenuTitle() {
        return getTabTitle();
    }

    /**
     * getTabTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabTitle() {
        String name = null;
        setCode("tab.title");
        setParmCount(2);
        if (isBookmark()) {
            setParm(0, getString("bookmark.title"));
        } else {
            setParm(0, getString("navigate.title"));
        }
        setParm(1, getProfile().toString());
        name = getMessage();
        eaccess().clear();
        return name;
    }

    /**
     * isBookmark
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBookmark() {
        return parent.isBookmark();
    }

    /*
     52732
    */
    /**
     * isDereferenced
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDereferenced() {
        if (history == null) {
            return true;
        } else if (data == null) {
            return true;
        } else if (action == null && !(data instanceof NavDataDouble)) {
            return true;
        } else if (pick == null) {
            return true;
        }
        return false;
    }

    /*
     cr_1210035324
     */
    /**
     * getNavigationHistory
     * @return
     * @author Anthony C. Liberto
     */
    public EANActionItem[] getNavigationHistory() {
        if (history != null) {
            return history.getNavigationHistory();
        }
        return null;
    }

    /**
     * load
     * @param _eList
     * @param _book
     * @param _navType
     * @author Anthony C. Liberto
     */
    public void load(EntityList _eList, BookmarkItem _book, int _navType) {
        load(_eList, _navType);
        if (history != null) {
            history.loadBookmarkHistory(_book);
            adjustPrevNext();
        }
    }

    /**
     * loadBookmarkHistory
     * @param _book
     * @author Anthony C. Liberto
     */
    public void loadBookmarkHistory(BookmarkItem _book) {
        if (history != null) {
            history.loadBookmarkHistory( _book);
            adjustPrevNext();
        }
    }

    /**
     * getChatAction
     * @return
     * @author Anthony C. Liberto
     */
    public ChatAction getChatAction() {
        if (navList != null) {
            ChatAction chat = new ChatAction();
            EANActionItem item = navList.getParentActionItem();
            EntityGroup group = null;
            if (item != null) {
                chat.setAction(item);
            }
            group = navList.getParentEntityGroup();
            if (group != null) {
                EntityItem[] tmp = group.getEntityItemsAsArray();
                if (tmp != null) {
                    chat.setSelectedItems(tmp);
                }
            }
            return chat;
        }
        return null;
    }

    /*
     cr_812022711
     */
    /**
     * createLaunchMenu
     * @author Anthony C. Liberto
     */
    protected void createLaunchMenu() {
        String strKey = getString("act");
        Profile prof = null;
        addSubMenu(strKey, "nav", parent, KeyEvent.VK_0, Event.CTRL_MASK);
        addSubMenu(strKey, "navP", parent, KeyEvent.VK_1, Event.CTRL_MASK);
        addSubMenu(strKey, "search", parent, KeyEvent.VK_2, Event.CTRL_MASK);
        addSubMenu(strKey, "edit", parent, KeyEvent.VK_3, Event.CTRL_MASK);
        addSubMenu(strKey, "mtrx", parent, KeyEvent.VK_4, Event.CTRL_MASK);
        addSubMenu(strKey, "used", parent, KeyEvent.VK_5, Event.CTRL_MASK);
        addSubMenu(strKey, "crte", parent, KeyEvent.VK_6, Event.CTRL_MASK);
        addSubMenu(strKey, "rprt", parent, KeyEvent.VK_7, Event.CTRL_MASK);
        addSubMenu(strKey, "link", parent, KeyEvent.VK_8, Event.CTRL_MASK);
        if (data instanceof NavDataDouble) { //cr_209046022
            addSubMenu(strKey, "linkD", parent, KeyEvent.VK_L, Event.CTRL_MASK); //cr_209046022
        } //cr_209046022
        addSubMenu(strKey, "wFlow", parent, KeyEvent.VK_9, Event.CTRL_MASK);
        addSubMenu(strKey, "pdg", parent, KeyEvent.VK_0, Event.ALT_MASK); //USRO-R-JSTT-68RKKP
        addSubMenu(strKey, "xtract", parent, KeyEvent.VK_1, Event.ALT_MASK);
        addSubMenu(strKey, "cpyA", parent, KeyEvent.VK_2, Event.ALT_MASK); //copyAction
        addSubMenu(strKey, "abrS", parent, KeyEvent.VK_3, Event.ALT_MASK); //cr_2115
        addSubMenu(strKey, "sGrp", parent, KeyEvent.VK_4, Event.ALT_MASK); //chgroup
        addMenu(strKey, "cGrp", parent, KeyEvent.VK_5, Event.ALT_MASK, false); //chgroup

        addSubMenu(strKey, "lckP", parent, KeyEvent.VK_6, Event.ALT_MASK);
        addSubMenu(strKey, "deAct", parent, KeyEvent.VK_7, Event.ALT_MASK);
        prof = getActiveProfile();
        if (prof != null) {
            if (!prof.hasRoleFunction(Profile.ROLE_FUNCTION_READONLY)) { //51199
                addMenu(strKey, "viewP", parent, KeyEvent.VK_8, Event.CTRL_MASK + Event.ALT_MASK, true); //51199
            } //51199
            if (prof.hasRoleFunction(Profile.ROLE_FUNCTION_WGLOCKS)) {
                addMenu(strKey, "viewPW", parent, KeyEvent.VK_9, Event.CTRL_MASK + Event.ALT_MASK, true);
            }
        }
        addMenu(strKey, "rstr", parent, KeyEvent.VK_9, Event.ALT_MASK, true); //restore
        addSubMenu(strKey, "query", parent, KeyEvent.VK_0, Event.CTRL_MASK + Event.ALT_MASK); // view action
        menu.setMenuMnemonic(strKey, getChar("act-s"));
    }
    /*
     cr_209046022
     */
    /**
     * generateToolbar
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceToolbar generateToolbar() {
        if (data instanceof NavDataDouble) {
            return generateToolbar(DefaultToolbarLayout.NAV_BAR_DUAL);
        } else {
            return generateToolbar(DefaultToolbarLayout.NAV_BAR);
        }
    }

    /**
     * generateToolbar
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public EannounceToolbar generateToolbar(ComboItem _item) {
        return nf.generateToolbar(_item);
    }

    /**
     * processLink
     * @param _ean
     * @author Anthony C. Liberto
     */
    public void processLink(EANActionItem[] _ean) {
        Navigate opNav = getOpposingNavigate();
        LinkActionItem[] links = null;
        boolean bHasData = hasData();
        if (opNav != null) {
            Vector v = new Vector();
            String e2Type = opNav.getEntityType(2);
            if (_ean != null && e2Type != null) {
                int ii = _ean.length;
                for (int i = 0; i < ii; ++i) {
                    if (_ean[i] != null && _ean[i] instanceof LinkActionItem) {
                        LinkActionItem link = (LinkActionItem) _ean[i];
                        MetaLink ml = link.getMetaLink();
                        if (ml != null) {
                            if (e2Type.equals(ml.getEntity2Type())) {
                                v.add(link);
                            }
                        }
                    }
                }
            }
            if (!v.isEmpty()) {
                links = (LinkActionItem[]) v.toArray(new LinkActionItem[v.size()]);
            }
        }
        if (menu != null) {
            menu.adjustSubAction("linkD", links, bHasData, ACTION_ALL);
        }
        if (tBar != null) {
            tBar.adjustSubAction("linkD", links, bHasData, ACTION_ALL);
        }
        return;
    }
    /*
     cr_1129034004
     */
    private int getSearchID() {
        //53802		String tmp = JOptionPane.showInputDialog(getComponent(),
        //53802												  getString("searchID"),
        //53802												  getString("search.panel"),
        //53802												  JOptionPane.QUESTION_MESSAGE);
        String tmp = eaccess().showInput("searchID", null, getComponent()); //53802
        if (tmp != null) {
            int iLen = tmp.length();
            if (iLen > 0) {
                if (iLen > 15) {
                    showError(getComponent(), "msg13001.0");
                    return -1;
                }
                if (!Routines.isInt(tmp)) {
                    showError(getComponent(), "msg13002.0");
                    return -1;

                }
                return Routines.toInt(tmp);
            }
        }
        return -1;
    }

    /*
     3.0a
     */
    private EANActionItem[] limitActions(int _i, EANActionItem[] _eai) {
        if (_i == 0 && _eai != null) {
            int ii = _eai.length;
            Vector v = new Vector();
            for (int i = 0; i < ii; ++i) {
                EANActionItem eai = _eai[i];
                if (eai != null && eai instanceof LinkActionItem) {
                    if (((LinkActionItem) eai).isOppSelect()) {
                        v.add(eai);
                    }
                }
            }
            if (v.isEmpty()) {
                return null;
            } else {
                return (EANActionItem[]) v.toArray(new EANActionItem[v.size()]);
            }
        }
        return _eai;
    }

    private boolean isParentlessSearch(EANActionItem _act) {
        if (_act != null && _act instanceof SearchActionItem) {
            return ((SearchActionItem) _act).isParentLess();
        }
        return false;
    }
    /*
     bluePageCreate
     */
    private boolean bluePageCreate(CreateActionItem _create) {
        if (_create != null) {
            if (_create.isBluePage()) {
                return processBluePageCreate(_create);
            }
        }
        return true;
    }

    /*
     TIR USRO-R-OGAA-652Q3N
     */
    private boolean processBluePageCreate(CreateActionItem _create) {
        String[] name = null;
        String sUser = null;
        resetCreate(_create); //TIR USRO-R-OGAA-652Q3N
        sUser = eaccess().showInput("blueID", null, getComponent());
        if (sUser != null) {
            System.out.println("searching for: " + sUser);
            if (!Routines.have(sUser)) {
                return false;
            } else if (sUser.length() < 2) {
                showError(getComponent(), "msg5020");
                return false;
            } else if (isUserID(sUser)) {
                System.out.println("user search");
                return processBluePageCreate(_create, sUser);
            } else if (isUserName(sUser)) {
                System.out.println("name search");
                name = Routines.getStringArray(sUser, ",");
                if (name != null) {
                    if (name.length == 1) {
                        return processBluePageCreate(_create, "", name[0]);
                    } else if (name.length >= 2) {
                        return processBluePageCreate(_create, name[1], name[0]);
                    }
                }
            }
        }
        showError(getComponent(), "msg5019");
        return false;
    }

    private void resetCreate(CreateActionItem _create) { //TIR USRO-R-OGAA-652Q3N
        _create.setEmailAddress(null); //TIR USRO-R-OGAA-652Q3N
        _create.setSelectedBluePageEntries(null); //TIR USRO-R-OGAA-652Q3N
        return; //TIR USRO-R-OGAA-652Q3N
    } //TIR USRO-R-OGAA-652Q3N

    private boolean isUserID(String _s) {
        if (Routines.have(_s)) {
            _s.toLowerCase().trim();
            return (_s.endsWith(".ibm.com") && _s.indexOf("@") > 0);
        }
        return false;
    }

    private boolean isUserName(String _s) {
        return true;
    }

    private boolean processBluePageCreate(CreateActionItem _create, String _first, String _last) {
        BluePageEntry[] bpe = dBase().getBluePageEntries(_create, (_first == null) ? "" : _first.trim(), _last.trim());
        if (bpe != null) {
            System.out.println(bpe.length + " entries found.");
            if (bpe.length == 1) {
                _create.setSelectedBluePageEntries(bpe);
                return true;
            } else {
                BluePageEntry[] sel = selectBluePageEntries(bpe);
                if (sel != null) {
                    _create.setSelectedBluePageEntries(sel);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            showError(getComponent(), "msg5018");
        }
        return false;
    }

    private boolean processBluePageCreate(CreateActionItem _create, String _user) {
        if (Routines.have(_user)) {
            _create.setEmailAddress(_user.trim());
            return true;
        } else {
            return false;
        }
    }

    private BluePageEntry[] selectBluePageEntries(BluePageEntry[] _bpe) {
        Object[] out = null;
        if (choose == null) {
            choose = new ChooserPanel() {
            	private static final long serialVersionUID = 1L;
            	public void ok() {
                    setSelectedValues();
                    return;
                }
            };
            choose.setMessage(getString("bluepage.create"));
        } else {
            choose.clearSelected();
        }
        System.out.println(_bpe.length + " blue page entries found.");
        choose.load(_bpe);
        eaccess().show(getComponent(), choose, true);
        out = choose.getSelectedValues();
        if (out != null) {
            Vector vct = new Vector();
            int ii = out.length;
            for (int i = 0; i < ii; ++i) {
                if (out[i] != null && out[i] instanceof BluePageEntry) {
                    vct.add((BluePageEntry) out[i]);
                }
            }
            if (!vct.isEmpty()) {
                return (BluePageEntry[]) vct.toArray(new BluePageEntry[vct.size()]);
            }
        }
        return null;
    }

    /*
     chgroup
     */
    private boolean processableAction(EANActionItem _nai, EntityItem[] _ei) {
        if (_nai instanceof CGActionItem) {
            if (_ei == null || _ei.length != 1) {
                showError(getComponent(), "msg30a1");
                return false;
            }
            setChangeGroup((CGActionItem) _nai, _ei);
            return false;
        }
        return true;
    }

    private void setChangeGroup(CGActionItem _nai, EntityItem[] _ei) {
        Profile[] pArray = getProfileArray();
        Profile pActive = eaccess().getActiveProfile();
        _nai.setChangeGroup(_ei[0], pArray, pActive);
        eaccess().setActiveProfile(pActive);
        setEnabled("cGrp", true);
        if (_ei[0] != null) {									//cgLabel
	        parent.setChangeGroupTagText(_ei[0].toString());	//cgLabel
		}														//cgLabel
        return;
    }

    private void resetChangeGroup() {
        Profile[] pArray = getProfileArray();
        Profile pActive = eaccess().getActiveProfile();
        CGActionItem.clearChangeGroup(pArray, pActive);
        eaccess().setActiveProfile(pActive);
        setEnabled("cGrp", false);
        parent.setChangeGroupTagText(null);					//cgLabel
        return;
    }

    private Profile[] getProfileArray() {
        return eaccess().getActiveProfiles();
    }

/*
 CR_1124045246
 */
    private void clearAction(EANActionItem _ean, EntityItem[] _ei) {
		String[] parm = null;
		if (_ean != null && _ean.isClearTargetChangeGroupEnabled()) {
			if (_ei != null) {
				if (CGActionItem.clearTargetChangeGroupForAll(getProfileArray(),_ei)) {
					parm = new String[1];
					parm[0] = parent.getChangeGroupTagText();
					resetChangeGroup();
					eaccess().showFYI("msg3018.0",parm,getComponent());
				}
			}
		}
		return;
	}

    /*
    CR_7342
    */
    /**
     * getParentInformationAtLevel
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     *    public Object[] getHistory(int _i){
    	Object obj[] = new Object[3];
    	obj[0]= getItemAt(_i); // this is the value seen (NavSerialObj.toString())
    	obj[1] = hashFilename.get(obj[0]);  // this is the filename needed to read in the NavSerialObj
    	obj[2] = hashAction.get(obj[0]); // the action
    	return obj;
    }
     */
    public Object[] getParentInformationAtLevel(int _i) {
		Object[] out = null;
		if (history != null) {
			int histCount = history.getHistoryCount();
			int iLevel = histCount - _i;
			if (iLevel >= 0 && iLevel < histCount) {
				Object histObj[] = history.getHistory(iLevel);
				EntityItem[] ei = nsoController.getEntityItems(histObj.toString());
				if (ei != null) {
					out = getParentLevelInformation(ei);
				}
				
				/*NavSerialObject tmpNso = history.getNavSerialObject(iLevel);
				if (tmpNso != null) {
					EntityItem[] ei = tmpNso.getEntityItems();
					if (ei != null) {
						out = getParentLevelInformation(ei);
					} else {
						NavSerialObject nso2 = nsoController.readNavSerialObject(tmpNso.getFileName(),false);
						if (nso2 != null) {
							EntityItem[] ei2 = nso2.getEntityItems();
							if (ei2 != null) {
								out =  getParentLevelInformation(ei2);
							}
							nso2.clearMemory();
						}
					}
				}*/
			}
		}
		return out;
	}

	/**
     * getParentLevelInformation
     *
     * @author Anthony C. Liberto
     * @param _ei
     * @return
     */
    public Object[] getParentLevelInformation(EntityItem[] _ei) {
		String strEntityType = null;
		int[] iEntityID = null;
		Object[] out = new Object[2];
		if (_ei != null) {
			int ii = _ei.length;
			iEntityID = new int[ii];
			for (int i=0;i<ii;++i) {
				if (i == 0) {
					strEntityType = _ei[i].getEntityType();
//					System.out.println("entityType: " + strEntityType);
				}
				iEntityID[i] = _ei[i].getEntityID();
//				System.out.println("    entityid: " + iEntityID[i]);
			}
			out[0] = strEntityType;
			out[1] = iEntityID;
		}
		return out;
	}

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshToolbar() {
		if (tBar != null) {
			tBar.refreshToolbar();
			updateMenuActions();
		}
	}

}
