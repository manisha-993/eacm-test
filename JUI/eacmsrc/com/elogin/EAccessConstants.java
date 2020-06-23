// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: EAccessConstants.java,v $
 * Revision 1.8  2012/04/05 17:28:12  wendy
 * jre142 and win7 changes
 *
 * Revision 1.7  2009/02/27 15:01:35  wendy
 * Part of CQ00021335 - login, bp api chgs
 *
 * Revision 1.6  2008/08/04 14:01:36  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.5  2008/02/26 13:48:34  wendy
 * fix simple date format
 *
 * Revision 1.4  2008/02/01 15:44:29  wendy
 * Moved column size limit to user preferences
 *
 * Revision 1.3  2007/10/11 13:24:47  couto
 * MN33121008 - Preventing 100% CPU usage by defining the max amount of columns to be resized in the properties file.
 *
 * Revision 1.2  2007/04/30 13:47:44  wendy
 * Add arm file to override maximize
 *
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.12  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.11  2006/06/19 19:32:56  tony
 * added timing logic for refresh functionality
 *
 * Revision 1.10  2006/05/04 16:14:32  tony
 * cr103103686
 *
 * Revision 1.9  2006/05/02 17:11:08  tony
 * CR103103686
 *
 * Revision 1.8  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.7  2006/03/07 22:24:19  tony
 * CR 6299
 * update mandatory/optional
 *
 * Revision 1.6  2006/02/15 17:42:11  tony
 * updated tunneling per Steve for BUI
 *
 * Revision 1.5  2005/10/12 16:37:53  tony
 * instance logic
 *
 * Revision 1.4  2005/10/06 15:58:18  tony
 * updated reporting logic per AHE
 *
 * Revision 1.3  2005/10/05 15:17:14  tony
 * AHE Single instance of application running
 *
 * Revision 1.2  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:37  tony
 * This is the initial load of OPICM
 *
 * Revision 1.53  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.52  2005/08/18 21:07:43  tony
 * updated messagePanel added more functionality.
 *
 * Revision 1.51  2005/05/26 15:16:47  tony
 * adjusted logic
 *
 * Revision 1.50  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.49  2005/05/24 21:27:57  tony
 * silverBullet
 *
 * Revision 1.48  2005/05/19 20:41:49  tony
 * singleFlag UI Enhancement.
 *
 * Revision 1.47  2005/05/17 20:07:23  tony
 * added batch file execution component.
 *
 * Revision 1.46  2005/05/17 17:48:31  tony
 * updated logic to address update of e-announce application.
 * added madatory update functionality as well.
 * improved pref logic for mandatory updates.
 *
 * Revision 1.45  2005/03/03 22:19:04  tony
 * cr_FlagUpdate
 * improved functionality
 *
 * Revision 1.44  2005/03/03 21:46:39  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.43  2005/02/25 16:47:44  tony
 * 6542 change request wrap-up
 *
 * Revision 1.42  2005/02/17 18:55:51  tony
 * cr_6542
 *
 * Revision 1.41  2005/02/10 19:01:12  tony
 * Button Animation
 *
 * Revision 1.40  2005/02/03 21:26:12  tony
 * JTest Format Third Pass
 *
 * Revision 1.39  2005/02/03 16:22:52  tony
 * JTest Second Pass
 *
 * Revision 1.38  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.37  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.36  2005/01/18 21:38:49  tony
 * USRO-R-JSTT-68RKKP
 *
 * Revision 1.35  2005/01/14 19:45:49  tony
 * xpnd_action
 *
 * Revision 1.34  2004/11/04 14:42:00  tony
 * adjusted default file logic.
 *
 * Revision 1.33  2004/11/03 23:51:03  tony
 * USRO-R-DMKR-66CHMM
 *
 * Revision 1.32  2004/10/28 20:07:09  tony
 * added broadcast.arm file
 *
 * Revision 1.31  2004/10/26 22:36:32  tony
 * //TIR 664NNE
 *
 * Revision 1.30  2004/10/22 22:15:30  tony
 * auto_sort/size
 *
 * Revision 1.29  2004/10/21 21:29:34  tony
 * updated sametime chatting functionality.
 *
 * Revision 1.28  2004/10/13 19:31:34  tony
 * added capability for autoDetectUpdate preference and
 * corresponding logic to support the function.
 *
 * Revision 1.27  2004/10/13 17:56:17  tony
 * added on-line update functionality.
 *
 * Revision 1.26  2004/10/11 21:01:28  tony
 * updated linktype functionality to bring in line
 * with preferences functionality
 *
 * Revision 1.25  2004/10/09 17:41:17  tony
 * improved debugging functionality.
 *
 * Revision 1.24  2004/09/15 22:45:48  tony
 * updated blue pages add logic
 *
 * Revision 1.23  2004/09/09 19:42:43  tony
 * DWB 20040908
 * adjusted logic for 3.0a functionality.
 *
 * Revision 1.22  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.21  2004/08/04 17:49:13  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.20  2004/08/02 21:38:25  tony
 * ChangeGroup modification.
 *
 * Revision 1.19  2004/07/29 22:35:21  tony
 * updated default preference value.
 *
 * Revision 1.18  2004/06/25 16:28:42  tony
 * added auto_link preference.
 *
 * Revision 1.17  2004/06/22 18:02:49  tony
 * cr_2115
 *
 * Revision 1.16  2004/06/17 18:58:57  tony
 * cr_4215 cr0313024215
 *
 * Revision 1.15  2004/06/17 14:53:36  tony
 * relator_edit << CR0616045323
 *
 * Revision 1.14  2004/06/16 20:23:14  tony
 * copyAction
 *
 * Revision 1.13  2004/06/10 20:53:17  tony
 * improved location chooser functionality.
 *
 * Revision 1.12  2004/06/09 16:08:23  tony
 * hid loaction chooser functionality.
 *
 * Revision 1.11  2004/06/09 15:48:52  tony
 * location chooser added to application.  It is controlled by
 * a boolean parameter (LOCATION_CHOOSER_ENABLED)
 * in eAccessConstants.
 *
 * Revision 1.10  2004/06/03 14:50:56  tony
 * added abiltity to reset preferences with the presence of
 * a reset.pref file.
 *
 * Revision 1.9  2004/05/25 17:26:06  tony
 * buildDate computation fix for foreign builds.
 *
 * Revision 1.8  2004/05/20 17:27:48  tony
 * updated version logic.
 *
 * Revision 1.7  2004/05/20 14:46:55  tony
 * adjusted release candidate display.
 *
 * Revision 1.6  2004/05/07 16:54:47  tony
 * adjusted last update logic to improve functionality and
 * no longer require manual adjusting of last.update.
 *
 * Revision 1.5  2004/03/15 17:35:03  tony
 * 53703
 *
 * Revision 1.4  2004/03/15 16:05:40  tony
 * 53719
 *
 * Revision 1.3  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.81  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.80  2004/01/20 00:19:53  tony
 * 53562
 *
 * Revision 1.79  2004/01/12 18:42:20  tony
 * cache_20040112
 * added cache folder logic
 *
 * Revision 1.78  2003/12/31 16:57:13  tony
 * cr_3312
 *
 * Revision 1.77  2003/12/17 19:14:18  tony
 * acl_20031217
 * updated hidden logic and added comment for previous token
 *
 * Revision 1.76  2003/12/11 22:30:31  tony
 * cr_3274
 *
 * Revision 1.75  2003/12/08 21:56:49  tony
 * cr_3274
 *
 * Revision 1.74  2003/12/04 21:00:37  tony
 * literal updated
 *
 * Revision 1.73  2003/11/14 21:50:44  tony
 * cr_dtd
 *
 * Revision 1.72  2003/11/10 15:33:57  tony
 * accessibility
 *
 * Revision 1.71  2003/10/29 19:10:40  tony
 * acl_20031029
 *
 * Revision 1.70  2003/10/29 00:23:35  tony
 * 52573
 *
 * Revision 1.69  2003/10/27 22:18:19  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.68  2003/10/17 18:00:39  tony
 * 52616
 *
 * Revision 1.67  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.66  2003/10/03 20:49:54  tony
 * updated accessibility.
 *
 * Revision 1.65  2003/09/30 16:33:06  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.64  2003/09/11 22:32:49  tony
 * preference for bookmark filter
 *
 * Revision 1.63  2003/09/05 17:31:42  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.62  2003/09/05 16:03:01  tony
 * acl_20030905 added automatic garbage collection
 * preference to the application.
 *
 * Revision 1.61  2003/08/27 19:08:48  tony
 * updated generic search to improve functionality.
 *
 * Revision 1.60  2003/08/22 16:38:33  tony
 * general search
 *
 * Revision 1.59  2003/08/15 20:10:42  tony
 * cleaned-up formatting.
 *
 * Revision 1.58  2003/08/14 15:32:56  tony
 * updated default font.
 *
 * Revision 1.57  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.56  2003/07/25 19:40:51  tony
 * updated default font face to have nls supported by default.
 *
 * Revision 1.55  2003/07/15 18:52:18  tony
 * improved reporting logic to use vbscript to eliminate browser
 * elements that were deemed unnecessary.
 *
 * Revision 1.54  2003/07/14 17:40:44  tony
 * added windows version variables
 *
 * Revision 1.53  2003/07/11 17:00:15  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.52  2003/07/09 17:56:44  tony
 * updated encryption to AES and improved security by
 * basing the key on an encrypted key.  This will mean that
 * an Encrypted key must be resident on the client computer.
 * Additionally changed to 128 bit encryption.
 *
 * Revision 1.51  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.50  2003/06/27 20:07:59  tony
 * changed message and adjusted undefined
 *
 * Revision 1.49  2003/06/27 15:29:35  tony
 * *** empty log message ***
 *
 * Revision 1.48  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.47  2003/06/25 18:19:37  tony
 * 1.2h bookmark enhancements
 *
 * Revision 1.46  2003/06/25 16:19:41  tony
 * added constants for future expansion.
 *
 * Revision 1.45  2003/06/16 17:22:54  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.44  2003/06/10 16:46:46  tony
 * 51260
 *
 * Revision 1.43  2003/05/29 20:44:39  tony
 * added tunneling logic
 *
 * Revision 1.42  2003/05/29 19:05:20  tony
 * updated report launching.
 *
 * Revision 1.41  2003/05/27 21:19:21  tony
 * added tunneling data
 *
 * Revision 1.40  2003/05/21 22:36:05  tony
 * added visible menu size to eAccessConstants.
 *
 * Revision 1.39  2003/05/21 17:05:00  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.38  2003/05/21 15:38:12  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.37  2003/05/13 22:45:04  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 * Revision 1.36  2003/05/12 23:18:36  tony
 * 50614
 *
 * Revision 1.35  2003/05/09 16:50:08  tony
 * updated colors
 *
 * Revision 1.34  2003/05/06 22:06:31  tony
 * 24321
 *
 * Revision 1.33  2003/05/06 18:57:30  tony
 * 50536
 *
 * Revision 1.32  2003/05/05 22:51:15  tony
 * changed found color
 *
 * Revision 1.31  2003/05/05 18:04:38  tony
 * 50515
 *
 * Revision 1.30  2003/05/02 17:53:43  tony
 * 50494
 *
 * Revision 1.29  2003/04/30 21:43:01  tony
 * changed default required column color
 *
 * Revision 1.28  2003/04/25 19:22:53  tony
 * added image constants
 *
 * Revision 1.27  2003/04/24 15:33:13  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.26  2003/04/22 23:00:59  tony
 * added default highlight color to constants
 *
 * Revision 1.25  2003/04/22 21:04:50  tony
 * default child and parent colors were reversed
 *
 * Revision 1.24  2003/04/22 17:29:38  tony
 * updated default association color
 *
 * Revision 1.23  2003/04/22 16:37:04  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.22  2003/04/21 23:21:47  tony
 * added association renderer for used table.
 *
 * Revision 1.21  2003/04/21 22:14:47  tony
 * updated default logic to allow for parent and child
 * color defaults.  Updated the sort on the whereUsed table.
 *
 * Revision 1.20  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.19  2003/04/18 20:10:30  tony
 * added tab placement to preferences.
 *
 * Revision 1.18  2003/04/18 17:01:28  tony
 * fixed refresh (F5) after a back causing reset when you
 * perform the refresh(F5) function.
 *
 * Revision 1.17  2003/04/18 14:40:43  tony
 * enhanced record toggle logic per KC multiple create
 * did not toggle thru records properly.
 *
 * Revision 1.16  2003/04/17 23:13:26  tony
 * played around with editing Colors
 *
 * Revision 1.15  2003/04/16 17:17:56  tony
 * added static delimit Character
 *
 * Revision 1.14  2003/04/07 17:55:16  tony
 * added DEFAULT_DTD
 *
 * Revision 1.13  2003/04/03 22:28:20  tony
 * column order needed to be based on selected profile.
 * added profile selector and improved logic.
 *
 * Revision 1.12  2003/04/03 18:50:39  tony
 * added DEFAULT_ICON added.
 *
 * Revision 1.11  2003/03/28 16:59:32  tony
 * adjust ePropertyField by moving parameters
 * to eAccessConstants.
 *
 * Revision 1.10  2003/03/27 22:50:23  tony
 * adjusted synchropnize logic by adding type and replacing exist.
 *
 * Revision 1.9  2003/03/25 21:44:48  tony
 * adjusted logic to integrate in the xmlEditor.
 *
 * Revision 1.8  2003/03/20 23:59:35  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
 *
 * Revision 1.7  2003/03/18 22:39:09  tony
 * more accessibility updates.
 *
 * Revision 1.6  2003/03/12 23:51:08  tony
 * accessibility and column order
 *
 * Revision 1.5  2003/03/11 00:33:22  tony
 * accessibility changes
 *
 * Revision 1.4  2003/03/07 21:40:45  tony
 * Accessibility update
 *
 * Revision 1.3  2003/03/05 18:54:23  tony
 * accessibility updates.
 *
 * Revision 1.2  2003/03/04 22:34:49  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:39  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;
import java.awt.*;
import java.awt.print.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface EAccessConstants {
    /*
     Generic Constants
    */
    /**
     * e-announce token
     */
    String EANNOUNCE_TOKEN = "POTPALASDEENALAB";
    /**
     * mandatory update if true updates are required
     * acl_20050516
     */
    boolean MANDATORY_UPDATE = false;
    /**
     * preference token
     */
    String PREFERENCE_TOKEN = "Test"; //53562 will cause eannc.pref to be reset when changed.
    /**
     * encode type
     */
    String ENCODE_TYPE = "utf8";
    /**
     * encode algorithm
     */
    String ENCODE_ALGORITHM = "AES";
    /**
     * encode key size
     */
    int ENCODE_KEY_SIZE = 128;
    /**
     * saved password
     */
    String EANNOUNCE_SAVED_PASSWORD = "eannounce.password.";
    /**
     * saved user name
     */
    String EANNOUNCE_SAVED_USERNAME = "eannounce.user.name";
    /**
     * file encode
     */
    String EANNOUNCE_FILE_ENCODE = "Cp850";
    /**
     * default link
     */
    String DEFAULT_LINK = "NODUPES";
    /**
     * middleware profile key
     */
    String MIDDLEWARE_PROFILE_KEY = "mw.profile.key";
    /**
     * dash
     */
    String DASH = "-";
    /**
     * return
     */
    String RETURN = System.getProperty("line.separator");
    /**
     * update font
     */
    boolean EANNOUNCE_UPDATE_FONT = true;
    //acl_20031007	boolean	EANNOUNCE_UPDATE_FOREGROUND 		= true;
    //acl_20031007	boolean	EANNOUNCE_UPDATE_BACKGROUND 		= true;
    /**
     * quote
     */
    String QUOTE = "\"";
    /**
     * default icon
     */
    String DEFAULT_ICON = "icon.gif";
    /**
     * colon
     */
    String COLON = ":  ";
    //cr_dtd	String	DEFAULT_DTD							= "eannounce.dtd";
    /**
     * delimit char
     */
    String DELIMIT_CHAR = "|";
    /**
     * tab
     */
    String TAB_DELIMIT = "\t"; //xl8r
    /**
     * flag delimit
     */
    String FLAG_DELIMIT = ";"; //xl8r
    /**
     * array delimit
     */
    String ARRAY_DELIMIT = ",";
    /**
     * record toggle
     */
    String RECORD_TOGGLE = "recTog";
    /**
     * report prefix
     */
    String REPORT_PREFIX = "reportprefix";
    /**
     * ok error indicator
     */
    String OK_ERROR = "(ok)"; //24321
    /**
     * log file extension
     */
    String LOG_EXTENSION = ".log"; //50614
    /**
     * delete file extension
     * acl_20050516
     */
    String DELETE_EXTENSION = ".delete";
    /**
     * vb script extension
     */
    String VB_SCRIPT_EXTENSION = ".vbs";
    /**
     * update extension
     */
    String UPDATE_EXTENSION = ".zip";
    /**
     * exception search
     */
    String EXCEPTION_SEARCH = "xception:"; //51260
    /**
     * do nothing
     */
    int DO_NOTHING = -99;
    /**
     * max vivible menu items for scroll
     */
    int MAX_VISIBLE_MENU_ITEMS = 10;
    /**
     * max edit rows
     */
	int MAXIMUM_EDIT_ROW = 250;
    /**
     * english
     */
    int NLSID_ENGLISH = 1; //53703
    /**
     * parent
     */
    String INDICATE_PARENT = "(+) "; //relator_edit
    /**
     * child
     */
    String INDICATE_CHILD = "(-) "; //relator_edit

    /*
     functionality
     */
    /**
     * the arm file extension
     */
    String ARM_EXTENSION = ".arm";
    /**
     * accessibility
     */
    String ACCESSIBLE_ARM_FILE = "accessible" + ARM_EXTENSION;
    /**
     * sametime administrator
     */
    String SAMETIME_ADMIN_ARM_FILE = "sametimeAdmin" + ARM_EXTENSION;
    /**
     * auto login
     */
    String AUTO_LOGIN_ARM_FILE = "autoLog" + ARM_EXTENSION;
    /**
     * bidirection communication
     * @deprecated
     */
    String BIDIRECTIONAL_ARM_FILE = "bidirectional" + ARM_EXTENSION;
    /**
     * chat
     */
    String CHAT_ARM_FILE = "chat" + ARM_EXTENSION;
    /**
     * font rendering in font
     */
    String COOL_ARM_FILE = "coolRend" + ARM_EXTENSION;
    /**
     * dump dbase
     */
    String DATABASE_DUMP_ARM_FILE = "dBaseDump" + ARM_EXTENSION;
    /**
     * write dbase files
     */
    String DATABASE_WRITE_ARM_FILE = "dBaseWrite" + ARM_EXTENSION;
    /**
     * validate form
     */
    String FORM_VALIDATE_ARM_FILE = "formVal" + ARM_EXTENSION;
    /**
     * location chooser
     */
    String LOCATION_CHOOSER_ARM_FILE = "locChoose" + ARM_EXTENSION;
    /**
     * if this exists, user can open multiple instances of the UI
     */
    String MULTIPLE_UI_ARM_FILE = "multipleUI"+ARM_EXTENSION; 
    /**
     * replayable log
     */
    String REPLAYABLE_ARM_FILE = "replay" + ARM_EXTENSION;
    /**
     * verbose rmi
     */
    String RMI_VERBOSE_ARM_FILE = "rmiVerbose" + ARM_EXTENSION;
    /**
     * sametime connectivity
     */
    String SAMETIME_ARM_FILE = "sametime" + ARM_EXTENSION;
    /**
     * broadcast message
     */
    String BROADCAST_ARM_FILE = "broadcast" + ARM_EXTENSION;
    /**
     * test
     */
    String TEST_MODE_ARM_FILE = "test" + ARM_EXTENSION;
    /**
     * capture
     */
    String CAPTURE_MODE_ARM_FILE = "capture" + ARM_EXTENSION;
    /**
     * batch execute arm file
     */
    String BATCH_EXECUTE_ARM_FILE = "batch" + ARM_EXTENSION;
    /**
     * timestamp callse
     */
    String TIMESTAMP_ARM_FILE = "timeStamp" + ARM_EXTENSION;
    /**
     * trace calls
     */
    String TRACE_ARM_FILE = "trace" + ARM_EXTENSION;
    /**
     * update
     */
    String UPDATE_ARM_FILE = "update" + ARM_EXTENSION;
    /**
     * verbose
     */
    String VERBOSE_ARM_FILE = "verbose" + ARM_EXTENSION;
    /**
     * accelerator
     */
    String XL8R_ARM_FILE = "xl8r" + ARM_EXTENSION;
    /**
     * show all actions
     */
    String SHOW_ALL_ARM_FILE = "showall" + ARM_EXTENSION;
    /**
     * autolink
     */
    String AUTOLINK_ARM_FILE = "autolink" + ARM_EXTENSION; //MN20805300
    /**
     * debug
     */
    String DEBUG_ARM_FILE = "debug" + ARM_EXTENSION;
    /**
     * monitor
     */
    String MONITOR_ARM_FILE = "monitor" + ARM_EXTENSION;
    /**
     * update automatically
     */
    String AUTO_UPDATE_FILE = "autoUpdate" + ARM_EXTENSION;
    /**
     * enhanced flag editor
     */
    String ENHANCED_FLAG_EDIT = "enhanceFlag" + ARM_EXTENSION;
    /**
     * enhanced flag hide popup
     */
	String ENHANCED_FLAG_HIDE_POPUP = "hidePopup" + ARM_EXTENSION;
    /**
     * no maximize
     */
	String NO_MAX_ARM_FILE = "noMaximize" + ARM_EXTENSION;
    /*
     default behavior
     */
	/**
	 * default extension
	 */
    String DEF_EXTENSION = ".def";
	/**
	 * autolink by default
	 */
    String AUTOLINK_DEFAULT = "autolink" + DEF_EXTENSION; //TIR 664NNE
	/**
	 * floatable flag by default
	 */
    String FLOATABLE_FLAG_DEFAULT = "floatFlag" + DEF_EXTENSION; //USRO-R-DMKR-66CHMM

    /*
     Middleware
     */
	/**
	 * obj name
	 */
    String MW_OBJECT = "eannounce.middleware.object";
	/**
	 * desc
	 */
    String MW_DESC = "eannounce.middleware.description";
	/**
	 * ip
	 */
    String MW_IP = "eannounce.middleware.ip";
	/**
	 * port
	 */
    String MW_PORT = "eannounce.middleware.port";
	/**
	 * report
	 */
    String MW_REPORT = "eannounce.middleware.report";
	/**
	 * user
	 */
    String MW_USER = "eannounce.middleware.username";
	/**
	 * pass
	 */
    String MW_PASS = "eannounce.middleware.password";
	/**
	 * version
	 */
    String MW_VERSION = "eannounce.middleware.version";
	/**
	 * redirect
	 */
    String MW_PROPERTIES = "eannounce.redirect.properties";

    /*
     Report Constants
    */
	/**
	 * tunnel run
	 */
//  String TUNNEL_RUN_SERVLET = "JuiExecAction?tranID=";
//    String TUNNEL_RUN_SERVLET = "JuiExecAction.wss?tranID=";
	String TUNNEL_RUN_SERVLET = "JuiExecAction.wss?tranID={parm0}&enterprise={parm1}";
	/**
	 * tunnel set
	 */
//  String TUNNEL_SET_SERVLET = "JuiSetAction";
    String TUNNEL_SET_SERVLET = "JuiSetAction.wss";

    /*
     OS Constants
     */
	/**
	 * 2000
	 */
    String WINDOWS_2000 = "Windows 2000";
	/**
	 * xp
	 */
    String WINDOWS_XP = "Windows XP";
	/**
	 * windows
	 */
    String WINDOWS = "Windows";
	/**
	 * other
	 */
    int OS_OTHER = -4;
	/**
	 * linux
	 */
    int OS_LINUX = -3;
	/**
	 * apple
	 */
    int OS_APPLE = -2;
	/**
	 * apple osx
	 */
    int OS_X = -1;
	/**
	 * windows
	 */
    int OS_WINDOWS = 0;
	/**
	 * win2k
	 */
    int OS_WINDOWS_2000 = 1;
	/**
	 * win xp
	 */
    int OS_WINDOWS_XP = 2;
	/**
	 * min client
	 */
    int OS_MINIMUM_CLIENT = OS_WINDOWS;// win7 is after XP but os.name is 'Windows NT (unknown)' OS_WINDOWS_2000;

    /*
     Navigate Constants
    */
	/**
	 * nav init load
	 */
    int NAVIGATE_INIT_LOAD = 0; //always load
	/**
	 * nav load
	 */
    int NAVIGATE_LOAD = 1; //always load
	/**
	 * nav reset
	 */
    int NAVIGATE_RESET = 2; //always load
	/**
	 * renav
	 */
    int NAVIGATE_RENAVIGATE = 3; //never load
	/**
	 * refresh
	 */
    int NAVIGATE_REFRESH = 4; //never load
	/**
	 * other
	 */
    int NAVIGATE_OTHER = 5; //never load
	/**
	 * div min
	 */
    int NAVIGATE_DIVIDER_MINIMIZED = 1; //right nav
	/**
	 * div max
	 */
    int NAVIGATE_DIVIDER_MAXIMIZED = 0; //left nav
	/**
	 * div other
	 */
    int NAVIGATE_DIVIDER_OTHER = 2; //do nothing

    /*
     busy states

     process improvement acl_2004-10-22
     */
	/**
	 * available
	 */
    int AVAILABLE = 0;
	/**
	 * background busy
	 */
    int BACKGROUND_BUSY = 1;
	/**
	 * process busy
	 */
    int PROCESS_BUSY = 2;
	/**
	 * worker busy
	 */
    int WORKER_BUSY = 3;
	/**
	 * modal busy
	 */
    int MODAL_BUSY = 4;

    /*
     Folder Constants
    */
	/**
	 * file sep
	 */
    String FILE_SEP = System.getProperty("file.separator");
	/**
	 * home
	 */
    String HOME = System.getProperty("user.dir");
    /**
     * OS_TEMP_DIR
     */
    String OS_TMP_DIR = System.getProperty("java.io.tmpdir");
	/**
	 * WIN_DIR ...
	 * lax.nl.env.windir=C:\WINDOWS
	 */
	String WIN_DIR = System.getProperty("lax.nl.env.windir");
	/**
	 * ROOT_DIR
	 * C:\
	 */
	String ROOT_DIR = System.getProperty("lax.nl.env.HOMEDRIVE") + FILE_SEP;
	/**
	 * home dir
	 */
    String HOME_DIRECTORY = HOME + FILE_SEP;
	/**
	 * resource
	 */
    String RESOURCE = "Resource";
	/**
	 * resource folder
	 */
    String RESOURCE_FOLDER = HOME_DIRECTORY + RESOURCE;
	/**
	 * resource dir
	 */
    String RESOURCE_DIRECTORY = RESOURCE_FOLDER + FILE_SEP;
	/**
	 * function
	 */
    String FUNCTION = "Function";
	/**
	 * function folder
	 */
    String FUNCTION_FOLDER = HOME_DIRECTORY + FUNCTION;
	/**
	 * function directory
	 */
    String FUNCTION_DIRECTORY = FUNCTION_FOLDER + FILE_SEP;
	/**
	 * preference
	 */
    String PREFERENCE_FILE = RESOURCE_DIRECTORY + "eannc.pref";
	/**
	 * reset pref
	 */
    String PREFERENCE_RESET = RESOURCE_DIRECTORY + "reset.pref";
	/**
	 * pref key
	 */
    String PREFERENCE_KEY = "eannounce.preference.key"; //53562
	/**
	 * logs
	 */
    String LOGS = "Logs";
	/**
	 * logs folder
	 */
    String LOGS_FOLDER = HOME_DIRECTORY + LOGS;
	/**
	 * logs directory
	 */
    String LOGS_DIRECTORY = LOGS_FOLDER + FILE_SEP;
	/**
	 * temp
	 */
    String TEMP = "Temp";
	/**
	 * temp folder
	 */
    String TEMP_FOLDER = HOME_DIRECTORY + TEMP;
	/**
	 * temp directory
	 */
    String TEMP_DIRECTORY = TEMP_FOLDER + FILE_SEP;
	/**
	 * update
	 */
    String UPDATE = "Updates";
	/**
	 * update folder
	 */
    String UPDATE_FOLDER = HOME_DIRECTORY + UPDATE;
	/**
	 * update dire
	 */
    String UPDATE_DIRECTORY = UPDATE_FOLDER + FILE_SEP;
	/**
	 * save
	 */
    String SAVE = "Saved";
	/**
	 * save folder
	 */
    String SAVE_FOLDER = HOME_DIRECTORY + SAVE;
	/**
	 * save dir
	 */
    String SAVE_DIRECTORY = SAVE_FOLDER + FILE_SEP;
	/**
	 * jar
	 */
    String JAR = "Jars";
	/**
	 * jar folder
	 */
    String JAR_FOLDER = HOME_DIRECTORY + JAR;
	/**
	 * jar dir
	 */
    String JAR_DIRECTORY = JAR_FOLDER + FILE_SEP;
	/**
	 * cache
	 */
    String CACHE = "Cache"; //cache_20040112
	/**
	 * cache folder
	 */
    String CACHE_FOLDER = HOME_DIRECTORY + CACHE; //cache_20040112
	/**
	 * cache dir
	 */
    String CACHE_DIRECTORY = CACHE_FOLDER + FILE_SEP; //cache_20040112
	/**
	 * cache ext
	 */
    String CACHE_EXTENSION = ".cache"; //cache_20040112
	/**
	 * batch
	 * acl_20050516
	 */
    String BATCH = "Batch";
	/**
	 * batch folder
	 * acl_20050516
	 */
    String BATCH_FOLDER = HOME_DIRECTORY + BATCH;
	/**
	 * batch dir
	 * acl_20050516
	 */
    String BATCH_DIRECTORY = BATCH_FOLDER + FILE_SEP;
	/**
	 * batch ext
	 * acl_20050516
	 */
    String BATCH_EXTENSION = ".bat";
    /*
     Font constants
    */
	/**
	 * def font size
	 */
    int DEFAULT_FONT_SIZE = 12;
	/**
	 * def font style
	 */
    int DEFAULT_FONT_STYLE = Font.PLAIN;
    //nls	String DEFAULT_FONT_FACE				= "dialog";
    //nls	String DEFAULT_FONT_FACE					= "Times New Roman MT 30";		//nls
	/**
	 * def font face
	 */
    String DEFAULT_FONT_FACE = "sansserif"; //nls
	/**
	 * def font
	 */
    Font DEFAULT_FONT = new Font(DEFAULT_FONT_FACE, DEFAULT_FONT_STYLE, DEFAULT_FONT_SIZE);

    /*
     Process Constants
    */
	/**
	 * process exist
	 */
    int PROCESS_EXIST = 0;
	/**
	 * process not exist
	 */
    int PROCESS_NOT_EXIST = 1;
	/**
	 * process all
	 */
    int PROCESS_ALL = 2;
	/**
	 * process none
	 */
    int PROCESS_NONE = 3;

    /*
     Property Constants
    */
	/**
	 * min
	 */
    int MINIMUM_TYPE = 0;
	/**
	 * max
	 */
    int MAXIMUM_TYPE = 1;
	/**
	 * min =
	 */
    int MINIMUM_EQUAL_TYPE = 2;
	/**
	 * max =
	 */
    int MAXIMUM_EQUAL_TYPE = 3;
	/**
	 * activate
	 */
    String ACTIVATE = "ACTIVATE";
	/**
	 * deactivate
	 */
    String DEACTIVATE = "DEACTIVATE";

    /*
     Status Constants
    */
	/**
	 * vers
	 */
    int VERSION_STATUS = 0;
	/**
	 * lang
	 */
    int LANGUAGE_STATUS = 1;
	/**
	 * role
	 */
    int ROLE_STATUS = 2;

    /*
     Border Constants
    */
	/**
	 * norm
	 */
    int NORMAL_BORDER = 0;
	/**
	 * raised
	 */
    int RAISED_BORDER = 1;
	/**
	 * lower
	 */
    int LOWERED_BORDER = 2;
	/**
	 * etched
	 */
    int ETCHED_BORDER = 3;

    /*
     eImagePanel Constants
    */
	/**
	 * tiled
	 */
    int IMAGE_TILED = 0; //image repeated
	/**
	 * center
	 */
    int IMAGE_CENTERED = 1; //displayed centered
	/**
	 * scaled to fit
	 */
    int IMAGE_SCALED_TO_FIT = 2; //scaled to fit
	/**
	 * normal
	 */
    int IMAGE_NORMAL = 3; //image displayed at point (0,0)
	/**
	 * scaled
	 */
    int IMAGE_SCALED = 4; //scaled and centered

    /*
     Preference Constants
    */
	/**
	 * metal
	 */
    String METAL_LNF = "javax.swing.plaf.metal.MetalLookAndFeel";
	/**
	 * motif
	 */
    String MOTIF_LNF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	/**
	 * windows
	 */
    String WINDOWS_LNF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	/**
	 * lnf
	 */
    String LOOK_AND_FEEL = "nav.look.feel";
	/**
	 * default profile
	 */
    String DEFAULT_PROFILE = ".default.profile";
	/**
	 * print ratio
	 */
    String PREF_PRINT_RATIO = "print.scale.ratio";
	/**
	 * print scale x
	 */
    String PREF_PRINT_SCALE_X = "print.scale.x";
	/**
	 * print scale y
	 */
    String PREF_PRINT_SCALE_Y = "print.scale.y";
	/**
	 * print orient
	 */
    String PREF_PRINT_ORIENTATION = "print.orientation";
	/**
	 * font face
	 */
    String PREF_FONT_FACE = "font.face";
	/**
	 * font size
	 */
    String PREF_FONT_SIZE = "font.size";
	/**
	 * font stylye
	 */
    String PREF_FONT_STYLE = "font.style";
	/**
	 * preferred font
	 */
    String PREF_FONT = "default.eannounce.font";
	/**
	 * tab layout
	 */
    String PREF_TAB_LAYOUT = "tab.layout";
	/**
	 * load bookmark
	 */
    String PREF_LOAD_BOOKMARK = "load.bookmark";
	/**
	 * default load bookmark
	 */
    boolean DEFAULT_LOAD_BOOKMARK = true;
	/**
	 * auto garbage collect
	 */
    String PREF_AUTO_GC = "auto.gc";
	/**
	 * default auto garbage collect
	 */
    boolean DEFAULT_AUTO_GC = true;
	/**
	 * load bookmark filter
	 */
    String PREF_BOOKMARK_FILTER = "load.bookmark.filter";
	/**
	 * default bookmark filter
	 */
    boolean DEFAULT_BOOKMARK_FILTER = true;
	/**
	 * refresh type
	 */
    int DEFAULT_REFRESH_TYPE = 1; //cr_3274
	/**
	 * pref refresh type
	 */
    String PREF_REFRESH_TYPE = "eannounce.refresh.type"; //cr_3274
	/**
	 * work folder clear type
	 */
    int DEFAULT_WF_CLEAR_TYPE = 0; //cr_4215
	/**
	 * pref wf clear
	 */
    String PREF_WF_CLEAR_TYPE = "eannounce.clearWF.type"; //cr_4215
	/**
	 * pref tree xpnd
	 */
    String PREF_TREE_EXPANDED = "eannounce.tree.expanded"; //cr_3312
	/**
	 * def tree xpnd
	 */
    boolean DEFAULT_PREF_TREE_EXPANDED = false; //cr_3312
	/**
	 * pref link type
	 */
    String PREF_LINK_TYPE = "eannounce.link.type"; //link_type
	/**
	 * def link type
	 */
    int DEFAULT_LINK_TYPE = 0; //link_type
	/**
	 * pref flag frame edit
	 */
    String PREF_VERT_FLAG_FRAME = "vertical.use.frame.flag"; //acl_20031029
    //USRO-R-DMKR-66CHMM	boolean	DEFAULT_VERTICAL_USE_FRAME_FLAG		= false;						//acl_20031029
	/**
	 * behind fire wall
	 */
    String PREF_BEHIND_FIREWALL = "behind.firewall";
	/**
	 * def behind fire
	 */
    boolean DEFAULT_BEHIND_FIREWALL = true;
	/**
	 * auto link
	 */
    String PREF_AUTO_LINK = "auto.link"; //auto_link
    //MN20805300	boolean	DEFAULT_AUTO_LINK					= true;							//auto_link
	/**
	 * auto size
	 */
    boolean DEFAULT_AUTO_SIZE = true; //auto_sort/size
	/**
	 * pref auto size
	 */
    String PREF_AUTO_SIZE = "auto.size"; //auto_sort/size
	/**
	 * pref auto size count for rows
	 */
    String PREF_AUTO_SIZE_COUNT = "auto.size.count"; //auto_sort/size
	/**
	 * pref auto size count for columns
	 */
    String PREF_AUTO_SIZE_COLS_COUNT = "auto.size.cols.count"; //auto_sort/size
	/**
	 * sort size count
	 */
    String DEFAULT_SORT_SIZE_COUNT = "0"; //auto_sort/size
	/**
	 * column size count
	 */
    String DEFAULT_COL_SIZE_COUNT = "20";
    /**
	 * def auto sort
	 */
    boolean DEFAULT_AUTO_SORT = true; //auto_sort/size
	/**
	 * pref auto sort
	 */
    String PREF_AUTO_SORT = "auto.sort"; //auto_sort/size
	/**
	 * pref auto sort count
	 */
    String PREF_AUTO_SORT_COUNT = "auto.sort.count"; //auto_sort/size
	/**
	 * pref profile
	 */
    String PREFERENCE_PROFILE = "prof.pref";
	/**
	 * pref lnf
	 */
    String PREFERENCE_LOOK_AND_FEEL = "lnf.pref";
	/**
	 * pref tbar
	 */
    String PREFERENCE_TOOLBAR = "tbar.pref";
	/**
	 * pref mw loc
	 */
    String PREFERENCE_MIDDLEWARE_LOCATION = "mwLoc.pref";
	/**
	 * pref print
	 */
    String PREFERENCE_PRINT = "print.pref";
	/**
	 * pref font
	 */
    String PREFERENCE_FONT = "font.pref";
	/**
	 * pref appear
	 */
    String PREFERENCE_APPEARANCE = "appearance.pref";
	/**
	 * pref cold ord
	 */
    String PREFERENCE_COLUMN_ORDER = "colOrd.pref";
	/**
	 * pref def col ord
	 */
    String PREFERENCE_DEFAULT_COLUMN_ORDER = "colOrd.def.pref";
	/**
	 * pref behavior
	 */
    String PREFERENCE_BEHAVIOR = "bhvur.pref";
	/**
	 * update to install
	 */
    String UPDATE_TO_INSTALL = "eannounce.update.file";
	/**
	 * accessible def
	 */
    boolean PREF_ACCESSIBLE_DEFAULT = false;
	/**
	 * update default
	 */
    boolean PREF_UPDATE_DEFAULT = false;
	/**
	 * action xpnd
	 */
    String PREF_ACTION_EXPANDED = "eannounce.action.expanded"; //xpnd_action
	/**
	 * def action xpnd
	 */
    boolean DEFAULT_PREF_ACTION_EXPANDED = false; //xpnd_action
	/**
	 * pref animate button
	 */
	String PREF_ANIMATE_BUTTON = "eannounce.animate.button";
	/**
	 * def animate button
	 */
	boolean DEFAULT_ANIMATE_BUTTON = true;

	/*
	 * Properties Constants
	 */

	/**
	 * MN 33121008 - Max number of colums for resizing the Tables
	 */
	String MAX_COLUMNS = "table.maxcolumns";

    /*
     Boolean Constants
    */
	/**
	 * on
	 */
    boolean ON = true;
	/**
	 * off
	 */
    boolean OFF = false;
	/**
	 * pass
	 */
    boolean PASS = true;
	/**
	 * fail
	 */
    boolean FAIL = false;

    /*
     Message Constants
    */
	/**
	 * cancel
	 */
    int CANCEL_CONSTANT = -99;
	/**
	 * message
	 */
    int MESSAGE_TYPE = 0;
	/**
	 * input
	 */
    int INPUT_TYPE = 1;
	/**
	 * confirm
	 */
    int CONFIRM_TYPE = 2;

	/**
	 * ok
	 */
    int OK = 0;
	/**
	 * ok cancel
	 */
    int OK_CANCEL = 1;
	/**
	 * new existing
	 */
    int NEW_EXISTING = 2;
	/**
	 * choose exit
	 */
    int CHOOSE_EXIT = 3;
	/**
	 * yes no
	 */
    int YES_NO = 4;
	/**
	 * now later
	 */
    int NOW_LATER = 5;
	/**
	 * choose A B
	 */
	int A_B = 6;
	/**
	 * yes no show
	 */
    int YES_NO_SHOW = 7; //cr_4215
	/**
	 * yes no cancel
	 */
    int YES_NO_CANCEL = 8;
	/**
	 * abort retry ignore
	 */
    int ABORT_RETRY_IGNORE = 9;
	/**
	 * choose A B C
	 */
    int A_B_C = 10;
	/**
	 * yes no all cancel
	 */
    int YES_NO_ALL_CANCEL = 11;
	/**
	 * all none choose cancel
	 */
    int ALL_NONE_CHOOSE_CANCEL = 12;
	/**
	 * choose A B C D
	 */
    int A_B_C_D = 13;
	/**
	 * error
	 */
    int ERROR_MESSAGE = 0;
	/**
	 * fyi
	 */
    int FYI_MESSAGE = 1;
	/**
	 * warn
	 */
    int WARN_MESSAGE = 2;
	/**
	 * question
	 */
    int QUESTION_MESSAGE = 3;
	/**
	 * first
	 */
    int FIRST_BUTTON = 0;
	/**
	 * abort
	 */
    int ABORT = 0;
	/**
	 * all
	 */
    int ALL = 0;
	/**
	 * yes
	 */
    int YES = 0;
	/**
	 * now
	 */
    int NOW = 0;
	/**
	 * second
	 */
    int SECOND_BUTTON = 1;
	/**
	 * no
	 */
    int NO = 1;
	/**
	 * none
	 */
    int NONE = 1;
	/**
	 * retry
	 */
    int RETRY = 1;
	/**
	 * later
	 */
    int LATER = 1;
	/**
	 * third
	 */
    int THIRD_BUTTON = 2;
	/**
	 * choose
	 */
    int CHOOSE = 2;
	/**
	 * ignore
	 */
    int IGNORE = 2;
	/**
	 * fourth
	 */
    int FOURTH_BUTTON = 3;

    /*
     Printing Constants
    */
	/**
	 * symmetric
	 */
    boolean SYMMETRIC_SCALING = true;
	/**
	 * asymmetric
	 */
    boolean ASYMMETRIC_SCALING = false;
	/**
	 * no scale
	 */
    int NO_SCALE = 0;
	/**
	 * custom scale
	 */
    int SCALE_CUSTOM = 1;
	/**
	 * all scale
	 */
    int SCALE_ALL = 2;
	/**
	 * scale x
	 */
    int SCALE_X = 3;
	/**
	 * scale y
	 */
    int SCALE_Y = 4;
	/**
	 * def orientation
	 */
    int PRINT_DEFAULT_ORIENTATION = PageFormat.PORTRAIT;
	/**
	 * default ratio
	 */
    double PRINT_DEFAULT_RATIO = .5d;
	/**
	 * default scale
	 */
    int PRINT_DEFAULT_SCALE = SCALE_CUSTOM;

    /*
     Action Constants
    */
	/**
	 * create
	 */
    String ACTION_PURPOSE_CREATE = "Create";
	/**
	 * delete
	 */
    String ACTION_PURPOSE_DELETE = "Delete";
	/**
	 * edit
	 */
    String ACTION_PURPOSE_EDIT = "Edit";
    /**
	 * xtract
	 */
    String ACTION_PURPOSE_EXTRACT = "Extract";
    /**
     * edit and extract
     */
    String[] ACTION_PURPOSE_EDIT_EXTRACT = {ACTION_PURPOSE_EDIT,ACTION_PURPOSE_EXTRACT};
	/**
	 * link
	 */
    String ACTION_PURPOSE_LINK = "Link";
	/**
	 * lock
	 */
    String ACTION_PURPOSE_LOCK = "Lock";
	/**
	 * mtrx
	 */
    String ACTION_PURPOSE_MATRIX = "Matrix";
	/**
	 * nav
	 */
    String ACTION_PURPOSE_NAVIGATE = "Navigate";
	/**
	 * report
	 */
    String ACTION_PURPOSE_REPORT = "Report";
	/**
	 * search
	 */
    String ACTION_PURPOSE_SEARCH = "Search";
	/**
	 * where used
	 */
    String ACTION_PURPOSE_WHERE_USED = "WhereUsed";
	/**
	 * workflow
	 */
    String ACTION_PURPOSE_WORK_FLOW = "Workflow";
	/**
	 * pdg
	 */
    String ACTION_PURPOSE_PDG = "PDGActionItem"; //USRO-R-JSTT-68RKKP
	/**
	 * copy
	 */
    String ACTION_PURPOSE_COPY = "Copy";
	/**
	 * changegroup
	 */
    String ACTION_PURPOSE_CHANGE_GROUP = "CG"; //chgroup
	/**
	 * abr
	 */
    String ACTION_PURPOSE_ABRSTATUS = "ABRStatus"; //cr_2115
	/**
	 * query action
	 */
    String ACTION_PURPOSE_QUERY = "Query";    
	/**
	 * lnf
	 */
    String LNF = "nav.look.feel";
	/**
	 * undefined
	 */
    String UNDEFINED = "?uDefKey:";

	/**
	 * all
	 */
    int ACTION_ALL = 0;
	/**
	 * nav
	 */
    int ACTION_NAVIGATE = 1;
	/**
	 * picklist
	 */
    int ACTION_PICK_LIST = 2;

    /*
     Dialog Constants
    */
	/**
	 * date
	 */
    int DATE_PANEL = 0;
	/**
	 * find
	 */
    int FIND_PANEL = 1;
	/**
	 * filter
	 */
    int FILTER_PANEL = 2;
	/**
	 * hw perm
	 */
    int HW_PERMUTATION_PANEL = 3;
	/**
	 * hw up
	 */
    int HW_UPGRADE_PANEL = 4;
	/**
	 * link
	 */
    int LINK_PANEL = 5;
	/**
	 * message
	 */
    int MESSAGE_PANEL = 6;
	/**
	 * number
	 */
    int NUMBER_PANEL = 7;
	/**
	 * pref
	 */
    int PREFERENCE_PANEL = 8;
	/**
	 * print
	 */
    int PRINT_PANEL = 9;
	/**
	 * scroll
	 */
    int SCROLL_PANEL = 10;
	/**
	 * sort
	 */
    int SORT_PANEL = 11;
	/**
	 * wiz of oz
	 */
    int WIZARD_PANEL = 12;
	/**
	 * xml
	 */
    int XML_PANEL = 13;
    /**
     * maintenance panel
     * cr_FlagUpdate
     */
    int MAINTENANCE_PANEL = 14;

    /*
     Time and Date Constants
    */
	/**
	 * simple date format constant
	 */
    String YEAR_ONLY = "yyyy";
	/**
	 * simple date format constant
	 */
    String SHORT_MONTH = "M";
	/**
	 * simple date format constant
	 */
    String LONG_MONTH = "MM";
	/**
	 * simple date format constant
	 */
    String SHORT_MONTH_STRING = "MMM";
	/**
	 * simple date format constant
	 */
    String LONG_MONTH_STRING = "MMMM";
	/**
	 * simple date format constant
	 */
    String SHORT_DAY = "d";
	/**
	 * simple date format constant
	 */
    String LONG_DAY = "dd";
	/**
	 * simple date format constant
	 */
    String SHORT_DAY_STRING = "EEE";
	/**
	 * simple date format constant
	 */
    String LONG_DAY_STRING = "EEEEEE";
	/**
	 * simple date format constant
	 */
    String HOUR_24 = "kk";
	/**
	 * simple date format constant
	 */
    String SHORT_HOUR = "H";
	/**
	 * simple date format constant
	 */
    String LONG_HOUR = "HH";
	/**
	 * simple date format constant
	 */
    String SHORT_MIN = "m";
	/**
	 * simple date format constant
	 */
    String LONG_MIN = "mm";
	/**
	 * simple date format constant
	 */
    String SHORT_SECOND = "s";
	/**
	 * simple date format constant
	 */
    String LONG_SECOND = "ss";
	/**
	 * simple date format constant
	 */
    String SHORT_TIME_ZONE = "zz";
	/**
	 * simple date format constant
	 */
    String LONG_TIME_ZONE = "zzzz";
	/**
	 * simple date format constant
	 */
    String MILLIS_ONLY = "SSSSSS";
	/**
	 * simple date format constant
	 */
    String AM_PM = "a";
	/**
	 * simple date format constant
	 */
    String FORMAT_IN = "yyyy-MM-dd-HH.mm.ss";
	/**
	 * simple date format constant
	 */
    String DATE_ONLY = "yyyyMMdd";
	/**
	 * simple date format constant
	 */
    String TIME_ONLY = "HHmmss";
	/**
	 * simple date format constant
	 */
    String DATE_TIME_ONLY = "yyyyMMddHHmmss";
	/**
	 * simple date format constant
	 */
    String FORMATTED_DATE = "yyyy-MM-dd";
	/**
	 * simple date format constant
	 */
    String FORMATTED_TIME = "HH:mm";
	/**
	 * simple date format constant
	 */
    String FORMATTED_FULL_TIME = "HH:mm:ss";
	/**
	 * simple date format constant
	 */
    String START_OF_DAY = "yyyy-MM-dd-00.00.00.000000";
	/**
	 * simple date format constant
	 */
    String END_OF_DAY = "yyyy-MM-dd-23.59.59.000000";
	/**
	 * simple date format constant
	 */
    String ISO_DATE = "yyyy-MM-dd-HH.mm.ss.SSSSSS";
	/**
	 * simple date format constant
	 */
    String FOREVER = "9999-12-31-00.00.00.000000";
    /**
     * end of time
     */
    String END_OF_TIME = "9999-12-31-23.59.59.99";
    /**
	 * simple date format constant
	 */
    String DISPLAY_TIME = "HH:mm";
	/**
	 * simple date format constant
	 */
    String LONG_TIME_DISPLAY = "hh:mm a 'middleware server time'";
	/**
	 * simple date format constant
	 */
    String DISPLAY_DATE = "EEEEE, MMMMM dd, yyyyy";
	/**
	 * simple date format constant
	 */
    String BEGINING = "1980-01-01-00.00.00.000000";
	/**
	 * simple date format constant
	 */
    int DATE_TYPE = 0;
	/**
	 * simple date format constant
	 */
    int TIME_TYPE = 1;
	/**
	 * simple date format constant
	 */
    String UPDATED_DATE = "yyyyMMddHHmm";
	/**
	 * simple date format constant
	 */
    String BUILD_DATE = "MM/dd/yyyyHH:mm:ss.SS";
	/**
	 * simple date format constant
	 */
    String RELEASE_DATE = "MMdd";

	/**
	 * simple date format constant
	 */
    String TIMESTAMP_DATE = "yyyy-MM-dd HH:mm:ss.SS";

    /*
     panel Types
    */
	/**
	 * panel type constants
	 */
    String TYPE_BOOKMARK = "eNavForm" + DELIMIT_CHAR + "bookmark"; //kehrli_20030929
	/**
	 * panel type constants
	 */
    String TYPE_ENAVFORM = "eNavForm";
	/**
	 * panel type constants
	 */
    String TYPE_ETABBEDPANEL = "eTabbedPanel";
	/**
	 * panel type constants
	 */
    String TYPE_EPARSEPANEL = "eParsePanel";
	/**
	 * panel type constants
	 */
    String TYPE_EDITCONTROLLER = "editController";
	/**
	 * panel type constants
	 */
    String TYPE_ACTIONCONTROLLER = "actionController";
	/**
	 * panel type constants
	 */
    String TYPE_EIMAGEPANEL = "eImagePanel";
	/**
	 * panel type constants
	 */
    String TYPE_TOOLBARCONTROL = "toolbarControl";
	/**
	 * panel type constants
	 */
    String TYPE_NAVPICK = "navPick";
	/**
	 * panel type constants
	 */
    String TYPE_NAVCART = "navCart";
	/**
	 * panel type constants
	 */
    String TYPE_MULTICOLHEADER = "multiColHeader";
	/**
	 * panel type constants
	 */
    String TYPE_EIPANEL = "eiPanel";
	/**
	 * panel type constants
	 */
    String TYPE_DATEPANEL = "datePanel";
	/**
	 * panel type constants
	 */
    String TYPE_DATEEDITOR = "dateEditor";
	/**
	 * panel type constants
	 */
    String TYPE_CROSSTABEDITOR = "crossTabEditor";
	/**
	 * panel type constants
	 */
    String TYPE_BLOBEDITOR = "blobEditor";
	/**
	 * panel type constants
	 */
    String TYPE_TABLEPANEL = "tablePanel";
	/**
	 * panel type constants
	 */
    String TYPE_HEADERPANEL = "headerPanel";
	/**
	 * panel type constants
	 */
    String TYPE_VERTEDITOR = "vertEditor";
	/**
	 * panel type constants
	 */
    String TYPE_HORZEDITOR = "horzEditor";
	/**
	 * panel type constants
	 */
    String TYPE_FORMEDITOR = "formEditor";
	/**
	 * panel type constants
	 */
    String TYPE_USEDACTION = "usedAction";
	/**
	 * panel type constants
	 */
    String TYPE_QUERYACTION = "queryAction";
	/**
	 * panel type constants
	 */
    String TYPE_RESTOREACTION = "restoreAction";
	/**
	 * panel type constants
	 */
    String TYPE_MATRIXACTION = "matrixAction";
	/**
	 * panel type constants
	 */
    String TYPE_LOCKACTION = "lockAction";
	/**
	 * panel type constants
	 */
    String TYPE_FORMPANEL = "formPanel";
	/**
	 * panel type constants
	 */
    String TYPE_LOGINPANEL = "loginPanel";
	/**
	 * panel type constants
	 */
    String TYPE_ETIMELABEL = "eTimeLabel";
	/**
	 * panel type constants
	 */
    String TYPE_EREMOTEPANEL = "eRemotePanel";
	/**
	 * panel type constants
	 */
    String TYPE_EPANEL = "ePanel";
	/**
	 * panel type constants
	 */
    String TYPE_EINFORMATIONPANEL = "eInformationPanel";
	/**
	 * panel type constants
	 */
    String TYPE_ACCESSIBLEDIALOGPANEL = "accessibleDialogPanel";
	/**
	 * panel type constants
	 */
    String TYPE_XMLPANEL = "XMLPanel";
	/**
	 * panel type constants
	 */
    String TYPE_NAVDATAPICK = "navDataPick";
	/**
	 * panel type constants
	 */
    String TYPE_NAVDATASELECTOR = "navDataSelector";

    /*
     Color Constants
    */
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_FOREGROUND = "foreground.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_DISABLED_FOREGROUND = "disabled.foreground.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_BACKGROUND = "background.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_DISABLED_BACKGROUND = "disabled.background.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_REQUIRED = "preferred.required.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_LOW_REQUIRED = "preferred.low.required.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_LOW = "preferred.low.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_OK = "preferred.ok.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_LOCK = "preferred.lock.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_FOUND = "preferred.found.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_FOUND_FOCUS = "preferred.found.focus.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_ASSOC = "preferred.association.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_CHILD = "preferred.child.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_PARENT = "preferred.parent.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_SELECTION_FOREGROUND = "foreground.selection.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_SELECTION_BACKGROUND = "background.selection.color";
	/**
	 * preferred color parameters preference keys
	 */
    String PREF_COLOR_OVERRIDE = "override.system.color"; //acl_20031007

	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_ENABLED_FOREGROUND = Color.black;
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_ENABLED_BACKGROUND = Color.decode("#CCCCCC");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_SELECTION_FOREGROUND = Color.black;
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_SELECTION_BACKGROUND = Color.decode("#9999CC");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_DISABLED_FOREGROUND = Color.darkGray;
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_DISABLED_BACKGROUND = Color.decode("#CCCCCC");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_REQUIRED = Color.decode("#FFCCCC"); //red
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_LOW_REQUIRED = Color.decode("#FFCE9C"); //orange
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_LOW = Color.decode("#FFFF66"); //yellow
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_OK = Color.decode("#CCFF99"); //green
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_LOCK = Color.decode("#C6EFF7"); //cyan
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_FOUND = Color.blue;
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_FOUND_FOCUS = Color.decode("#DE5AAd"); //magenta
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_ASSOC = Color.decode("#EA6F75");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_CHILD = Color.decode("#484473");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_PARENT = Color.decode("#A21A02");
	/**
	 * default colors
	 */
    Color DEFAULT_COLOR_HIGHLIGHT = Color.decode("#000080");
	/**
	 * default color override
	 */
    boolean DEFAULT_COLOR_OVERRIDE = false; //acl_20031007
    /*
     menu constants -- 50494
    */
	/**
	 * help
	 */
    String HELP_MENU = "help";
	/**
	 * file
	 */
    String FILE_MENU = "file";
	/**
	 * system
	 */
    String SYSTEM_MENU = "system";
	/**
	 * workgroup
	 */
    String WORKGROUP_MENU = "workgroup";

    /*
     table types
    */
	/**
	 * table type
	 */
    int TABLE_DEFAULT = 0;
	/**
	 * table type
	 */
    int TABLE_NAVIGATE = 1;
	/**
	 * table type
	 */
    int TABLE_HORIZONTAL = 2;
	/**
	 * table type
	 */
    int TABLE_VERTICAL = 3;
	/**
	 * table type
	 */
    int TABLE_MATRIX = 4; //it looks weird to have two 4s but it is correct
	/**
	 * table type
	 */
    int TABLE_CROSS = 4; //cross and matrix should have the same int id.
	/**
	 * table type
	 */
    int TABLE_LOCK = 5;
	/**
	 * table type
	 */
    int TABLE_BOOLEAN = 6; //gen_search
	/**
	 * table type
	 */
    int TABLE_RESTORE = 7; //53719
    /**
     * table type
     */
    int TABLE_MAINTENANCE = 8;
    /*
     51555
     */
    /**
     * key type
     */
    int KEY_TYPE = 1;
    /**
     * rel type
     */
    int RELATOR_TYPE = 2;
    /**
     * parent type
     */
    int PARENT_TYPE = 3;
    /**
     * child
     */
    int CHILD_TYPE = 4;

    /*
     accessibility
     */
    /**
     * edit status
     */
    int ACCESSIBLE_EDIT_STATUS = 0;
    /**
     * lock status
     */
    int ACCESSIBLE_LOCK_STATUS = 1;
    /**
     * att type
     */
    int ACCESSIBLE_ATTRIBUTE_TYPE = 2;
    /**
     * column name
     */
    int ACCESSIBLE_COLUMN_NAME = 3;
    /**
     * row name
     */
    int ACCESSIBLE_ROW_NAME = 4;
    /**
     * value
     */
    int ACCESSIBLE_VALUE = 5;
    /**
     * max
     */
    int ACCESSIBLE_MAX = 6;

    /*
     will eventually become parameterized in
     eAccess

    	boolean ACCESSIBLE_ENABLED					= false;
    	boolean REPLAYABLE_LOGFILE					= false;				//52573
    */
    /**
     * light keyboard nav
     */
    String LIGHTWEIGHT_KEYBOARD_NAVIGATION = "JComboBox.lightweightKeyboardNavigation"; //$NON-NLS-1$
    /**
     * lightweight
     */
    String LIGHTWEIGHT_KEYBOARD_NAVIGATION_ON = "Lightweight"; //$NON-NLS-1$
    /**
     * heavyweight
     */
    String LIGHTWEIGHT_KEYBOARD_NAVIGATION_OFF = "Heavyweight"; //$NON-NLS-1$

/*
 CR 6542
 */
    /**
     * nls read only
     */
    int NLS_READ_ONLY = 2;
    /**
     * nls write
     */
    int NLS_WRITE = 0;
    /**
     * nls create
     */
    int NLS_CREATE = 1;
    /**
     * nls already processed
     */
    int NLS_ALREADY_PROCESSED = -1;

/*
 6299
 */
    /**
     * mandatory update
     */
    String MANDATORY = "mandatory";
	/**
	 * optional update
	 */
	String OPTIONAL = "optional";

}
