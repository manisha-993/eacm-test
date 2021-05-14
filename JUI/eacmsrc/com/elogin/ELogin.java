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
 * $Log: ELogin.java,v $
 * Revision 1.11  2012/04/05 17:30:26  wendy
 * jre142 and win7 changes
 *
 * Revision 1.10  2010/08/05 17:00:24  wendy
 * don't delete all temp files immediately, let them age 24 hrs
 *
 * Revision 1.9  2009/09/01 17:22:26  wendy
 * removed useless code and cleanup
 *
 * Revision 1.8  2009/08/07 15:43:14  wendy
 * Make sure Temp dir is clean at startup
 *
 * Revision 1.7  2009/05/28 14:01:29  wendy
 * Performance cleanup
 *
 * Revision 1.6  2009/04/15 19:44:19  wendy
 * Prevent stack overflow caused by load calling itself
 *
 * Revision 1.5  2009/02/27 15:06:44  wendy
 * Part of CQ00021335 - login, bp api chgs
 *
 * Revision 1.4  2008/01/30 16:27:00  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.3  2007/05/31 19:44:55  wendy
 * Allow exit if exception in exit() occurs
 *
 * Revision 1.2  2007/04/30 13:47:44  wendy
 * Add arm file to override maximize
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.14  2007/04/06 12:06:11  wendy
 * TIR6YZSXM allow setting of D.ebug level
 *
 * Revision 1.13  2007/03/27 14:20:45  wendy
 * MN31311135 track exit for debug
 *
 * Revision 1.12  2006/11/09 18:55:50  tony
 * more monitor stuff.
 *
 * Revision 1.11  2006/11/09 01:29:33  tony
 * monitor
 *
 * Revision 1.10  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.9  2006/05/23 21:50:49  tony
 * Fix Nancy title mod
 *
 * Revision 1.8  2006/05/23 21:19:59  tony
 * cleaned-up code
 *
 * Revision 1.7  2006/05/16 21:44:43  tony
 * update title logic.
 *
 * Revision 1.6  2006/05/15 17:44:13  tony
 * nb 20060515
 * updated logic to display middleware connection
 * information to eliminate confustion relating to the
 * server that is being connected to.
 *
 * Revision 1.5  2005/12/15 19:09:33  tony
 * added https support
 *
 * Revision 1.4  2005/10/12 16:37:53  tony
 * instance logic
 *
 * Revision 1.3  2005/10/05 15:17:14  tony
 * AHE Single instance of application running
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:38  tony
 * This is the initial load of OPICM
 *
 * Revision 1.57  2005/09/08 17:58:53  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.56  2005/05/27 15:56:40  tony
 * removed print
 *
 * Revision 1.55  2005/05/24 21:27:57  tony
 * silverBullet
 *
 * Revision 1.54  2005/05/17 20:07:23  tony
 * added batch file execution component.
 *
 * Revision 1.53  2005/05/17 18:08:42  tony
 * enhance .delete file logic.
 *
 * Revision 1.52  2005/05/17 17:48:31  tony
 * updated logic to address update of e-announce application.
 * added madatory update functionality as well.
 * improved pref logic for mandatory updates.
 *
 * Revision 1.51  2005/05/11 14:52:57  tony
 * Adjuste ObjectPool Clear logic.
 *
 * Revision 1.50  2005/03/28 17:56:37  tony
 * MN_button_disappear
 * adjust to preven java bug 4664818
 *
 * Revision 1.49  2005/03/15 17:03:31  tony
 * updated versioning logic
 *
 * Revision 1.48  2005/03/10 23:37:18  tony
 * adjust logging
 *
 * Revision 1.47  2005/03/10 18:59:26  tony
 * added middleware versioning dump.
 * This will assist in troubleshooting incompatibility issues.
 *
 * Revision 1.46  2005/02/23 17:50:35  tony
 * 7342
 *
 * Revision 1.45  2005/02/21 17:16:49  tony
 * adjusted versioning logic by separating out
 * versioning logic to improve update functionality.
 *
 * Revision 1.44  2005/02/09 18:55:22  tony
 * Scout Accessibility
 *
 * Revision 1.43  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.42  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.41  2005/02/02 21:30:08  tony
 * JTest Second Pass
 *
 * Revision 1.40  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.39  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.38  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.37  2004/11/03 23:50:43  tony
 * USRO-R-JSTT-66DMB7
 *
 * Revision 1.36  2004/10/28 20:08:01  tony
 * improved chat menu logic.
 *
 * Revision 1.35  2004/10/21 21:28:41  tony
 * deprecated current funciton and
 * updates sametime arming with an arm file
 *
 * Revision 1.34  2004/10/13 19:31:35  tony
 * added capability for autoDetectUpdate preference and
 * corresponding logic to support the function.
 *
 * Revision 1.33  2004/10/13 17:56:18  tony
 * added on-line update functionality.
 *
 * Revision 1.32  2004/10/12 18:16:44  tony
 * improved update functionality
 *
 * Revision 1.31  2004/10/11 21:01:28  tony
 * updated linktype functionality to bring in line
 * with preferences functionality
 *
 * Revision 1.30  2004/09/23 16:24:20  tony
 * improved logic to acquire focus to textbox on
 * message panel input functionality.
 *
 * Revision 1.29  2004/09/15 22:45:48  tony
 * updated blue pages add logic
 *
 * Revision 1.28  2004/08/25 16:24:39  tony
 * updated location chooser logic to enhance functionality.
 *
 * Revision 1.27  2004/08/23 21:38:09  tony
 * TIR USRO-R-RLON-645P76
 *
 * Revision 1.26  2004/08/09 21:22:12  tony
 * improved logging
 *
 * Revision 1.25  2004/08/05 16:10:40  tony
 * added function folder
 *
 * Revision 1.24  2004/08/04 17:49:13  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.23  2004/08/02 21:39:51  tony
 * adjusted update logic.
 *
 * Revision 1.22  2004/06/10 20:53:17  tony
 * improved location chooser functionality.
 *
 * Revision 1.21  2004/06/09 15:48:52  tony
 * location chooser added to application.  It is controlled by
 * a boolean parameter (LOCATION_CHOOSER_ENABLED)
 * in eAccessConstants.
 *
 * Revision 1.20  2004/05/19 15:12:09  tony
 * updated messaging logic.
 *
 * Revision 1.19  2004/05/18 14:40:14  tony
 * updated versioning logic.
 *
 * Revision 1.18  2004/04/22 18:03:02  tony
 * updated remoter server/chat functionality.
 * improved capability added autoDetect binding
 * to determine if the client is behind a firewall.
 * It is not currently implemented however.
 *
 * Revision 1.17  2004/04/20 14:29:12  tony
 * bidirectional server login support added.
 *
 * Revision 1.16  2004/04/08 19:53:47  tony
 * adjusted guest logic
 *
 * Revision 1.15  2004/04/07 21:11:08  tony
 * enhanced shellControl functionality.
 *
 * Revision 1.14  2004/04/07 17:25:51  tony
 * updated logic
 *
 * Revision 1.13  2004/03/24 20:17:11  tony
 * accessibility
 *
 * Revision 1.12  2004/03/24 16:15:38  tony
 * accessibility.
 *
 * Revision 1.11  2004/03/12 18:57:16  tony
 * added appendlog version.
 *
 * Revision 1.10  2004/03/12 00:06:20  tony
 * added version data
 *
 * Revision 1.9  2004/03/01 21:04:52  tony
 * updated append log statement.
 *
 * Revision 1.8  2004/03/01 19:38:05  tony
 * usability enhancements.
 *
 * Revision 1.7  2004/02/27 23:22:08  tony
 * dwb_20040227
 *
 * Revision 1.6  2004/02/27 18:55:42  tony
 * fixed matrix sendtab function
 *
 * Revision 1.5  2004/02/24 18:01:31  tony
 * e-announce13 send tab
 *
 * Revision 1.4  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.3  2004/02/20 20:15:34  tony
 * e-announce1.3
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.101  2004/01/16 18:40:10  tony
 * 53516
 * updated tab logic to alter tab color when necessary.
 *
 * Revision 1.100  2004/01/14 21:17:20  tony
 * improved removelog logic.
 *
 * Revision 1.99  2004/01/14 20:06:59  tony
 * acl_20040114
 * updated logic to improve speed of splash screen rendering.
 *
 * Revision 1.98  2004/01/14 16:39:08  tony
 * added setCachePath
 *
 * Revision 1.97  2004/01/12 21:35:37  tony
 * cache_20040112
 * caching update
 *
 * Revision 1.96  2004/01/12 18:42:20  tony
 * cache_20040112
 * added cache folder logic
 *
 * Revision 1.95  2004/01/09 00:42:43  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.94  2004/01/08 17:40:56  tony
 * 53516
 *
 * Revision 1.93  2003/12/23 17:48:35  tony
 * added classpath envirionment variable display
 *
 * Revision 1.92  2003/12/23 17:44:33  tony
 * acl_20031223
 * added displays for properties and environment variables.
 *
 * Revision 1.91  2003/12/23 00:07:59  tony
 * acl_20031222
 * added logic to detect the os.patch.level and report
 * to the log file.
 *
 * Revision 1.90  2003/12/19 18:42:19  tony
 * acl_20031219
 * updated logic to prevent error and null pointers when
 * painting or validating components.
 *
 * Revision 1.89  2003/12/18 22:20:07  tony
 * added exception printStackTrace.
 *
 * Revision 1.88  2003/12/11 16:45:20  tony
 * acl_20031211
 *
 * Revision 1.87  2003/11/20 19:59:06  tony
 * added testing logic.
 *
 * Revision 1.86  2003/11/14 17:23:39  tony
 * accessibility
 *
 * Revision 1.85  2003/11/06 20:06:42  tony
 * 52838
 *
 * Revision 1.84  2003/10/31 19:32:53  tony
 * added appendlog statement.
 *
 * Revision 1.83  2003/10/31 17:31:52  tony
 * replay log
 *
 * Revision 1.82  2003/10/29 20:05:51  tony
 * fixed guest() logic.
 *
 * Revision 1.81  2003/10/29 19:10:41  tony
 * acl_20031029
 *
 * Revision 1.80  2003/10/29 00:25:09  tony
 * removed System.out. statements.
 * REPLAYABLE_LOGFILE
 *
 * Revision 1.79  2003/10/22 21:04:45  tony
 * 52685
 *
 * Revision 1.78  2003/10/17 18:00:56  tony
 * 52614
 *
 * Revision 1.77  2003/10/14 17:25:49  tony
 * 52548
 *
 * Revision 1.76  2003/10/14 15:48:37  tony
 * 52549
 *
 * Revision 1.75  2003/10/13 19:46:21  tony
 * 52534
 *
 * Revision 1.74  2003/10/08 20:09:19  tony
 * 52476
 *
 * Revision 1.73  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.72  2003/10/01 20:38:06  tony
 * updated logic
 *
 * Revision 1.71  2003/09/30 16:33:23  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.70  2003/09/17 16:12:58  tony
 * cleaned-up code.
 *
 * Revision 1.69  2003/08/28 18:36:01  tony
 * 51975
 *
 * Revision 1.68  2003/08/25 21:27:11  tony
 * 51922
 *
 * Revision 1.67  2003/08/15 15:36:38  tony
 * cr_0805036452
 *
 * Revision 1.66  2003/07/25 17:16:52  tony
 * improved logic.
 *
 * Revision 1.65  2003/07/22 15:41:59  tony
 * formatted file.
 *
 * Revision 1.64  2003/07/18 18:59:07  tony
 * acl_20030718 added selectKeys logic as guest
 * Entity  = current entity
 * Entity1 = parent Entity
 * Entity2 = Child Entity
 * then ',' then keys separated by ','.
 *
 * Revision 1.63  2003/07/15 18:51:21  tony
 * 51448 usability hold
 *
 * Revision 1.62  2003/07/14 17:40:29  tony
 * added testBrowse guest
 *
 * Revision 1.61  2003/07/11 17:00:15  tony
 * updated logic to perform default functions for
 * the preference panel.
 *
 * Revision 1.60  2003/07/09 17:56:45  tony
 * updated encryption to AES and improved security by
 * basing the key on an encrypted key.  This will mean that
 * an Encrypted key must be resident on the client computer.
 * Additionally changed to 128 bit encryption.
 *
 * Revision 1.59  2003/07/03 19:04:50  tony
 * updated logic so Log file would be properly formatted
 * XML
 *
 * Revision 1.58  2003/07/03 00:41:40  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.57  2003/07/02 21:42:29  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.56  2003/07/02 16:43:23  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.55  2003/06/26 17:46:06  tony
 * updated cipher added functionality to encrypt and decrypt
 * files
 *
 * Revision 1.54  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.53  2003/06/19 16:09:42  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.52  2003/06/17 20:50:51  tony
 * 1.2h adjusted profile handling
 *
 * Revision 1.51  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.50  2003/06/05 19:19:45  tony
 * added empty border
 *
 * Revision 1.49  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.48  2003/05/29 21:20:45  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.47  2003/05/29 19:05:20  tony
 * updated report launching.
 *
 * Revision 1.46  2003/05/27 21:19:55  tony
 * updated launching url logic.
 *
 * Revision 1.45  2003/05/23 17:08:37  tony
 * updated reporting logic for middleware connection.
 *
 * Revision 1.44  2003/05/22 14:30:31  tony
 * 50870
 *
 * Revision 1.43  2003/05/20 22:51:49  tony
 * 24286 -- no menu short-cut repitition.
 *
 * Revision 1.42  2003/05/15 23:40:37  tony
 * updated nextTab and prevTab logic.
 *
 * Revision 1.41  2003/05/15 23:13:26  tony
 * updated functionality improved logic for next and
 * previous tab.
 *
 * Revision 1.40  2003/05/15 20:07:31  tony
 * cleaned-up code
 *
 * Revision 1.39  2003/05/12 23:38:51  tony
 * 50614
 *
 * Revision 1.38  2003/05/12 23:18:35  tony
 * 50614
 *
 * Revision 1.37  2003/05/02 20:05:54  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.36  2003/05/02 17:53:43  tony
 * 50494
 *
 * Revision 1.35  2003/05/01 22:41:35  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.34  2003/05/01 18:02:17  tony
 * added whoHasFocus call
 *
 * Revision 1.33  2003/05/01 17:15:00  tony
 * added guest functionality.
 *
 * Revision 1.32  2003/04/24 21:49:37  tony
 * adjusted borders for table renderers.
 *
 * Revision 1.31  2003/04/24 15:33:13  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.30  2003/04/22 23:00:59  tony
 * added default highlight color to constants
 *
 * Revision 1.29  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.28  2003/04/17 15:08:48  tony
 * changed method name and propertyname to
 * better reflect what it does.
 *
 * Revision 1.27  2003/04/11 20:49:42  tony
 * improved windows logic.
 *
 * Revision 1.26  2003/04/11 20:47:17  tony
 * improved Windows logic.
 *
 * Revision 1.25  2003/04/11 20:02:25  tony
 * added copyright statements.
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.eannounce.eforms.edit.EditController;
import com.ibm.eannounce.eforms.action.ActionController;
import com.ibm.eannounce.epanels.ENavForm;
import com.ibm.eannounce.version.Version;
import java.awt.*;
import java.awt.event.*;
import java.awt.peer.ComponentPeer;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.*;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ELogin extends JFrame implements Accessible, EAccessConstants, ActionListener {
    private static final long serialVersionUID = 1L;
    private static ELogin elogin = null;
    private static LoginPanel pLogin = null;

    private EPanel pMain = null;

    private EMLabel lblInstruct = null;
    //private EMLabel lblLastLoginDate = null; // CQ14860
    
    private EMenubar mBar = null;
    private EMenu mnuFile = null;
    private EMenu mnuSystem = null;
    private EMenu mnuHelp = null;
    private EMenu mnuWorkgroup = null;

    private float maxMem = 0F;
    private float memPct = 0F;

    private FileOutputStream fileOut = null;
    private PrintStream pStream = null;

    private EStatusbar eStatus = null;

//    private String strLinkType = DEFAULT_LINK;

    /**
     * bModalCursor
     */
    private boolean bModalCursor = false;
    /**
     * bUseBack
     */
    private boolean bUseBack = true;
    /**
     * bUseFont
     */
    private boolean bUseFont = true;
    /**
     * bUseFore
     */
    private boolean bUseFore = true;

    /**
     * eLogin
     * @author Anthony C. Liberto
     */
    private ELogin() {
        UIManager.put("frame.default.background", getBackground()); //52534
        UIManager.put("frame.default.foreground", getForeground()); //52534
        setProperties();
        initOutput();
        init();
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     * /
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }*/

    /**
     * getSystemMenu
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenu getSystemMenu() {
        return mnuSystem;
    }*/

    /**
     * getHelpMenu
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenu getHelpMenu() {
        return mnuHelp;
    }*/

    /**
     * getWorkgroupMenu
     * @return
     * @author Anthony C. Liberto
     * /
    public EMenu getWorkgroupMenu() {
        return mnuWorkgroup;
    }*/

    private void initLogin() {
        setIconImage(EAccess.eaccess().getImage(DEFAULT_ICON));
        EAccess.eaccess().init(this);
        if (EAccess.isArmed(BATCH_EXECUTE_ARM_FILE)) {
            executeBatchFiles(BATCH_DIRECTORY,BATCH_EXTENSION);
            deleteFiles(BATCH_DIRECTORY,DELETE_EXTENSION);
        }
//nb_20060515        setTitle(getString("name"));
        setApplicationTitle();                          //nb_20060515
        if (EAccess.isArmed(LOCATION_CHOOSER_ARM_FILE)) { //loc_chooser
            connectMiddleware(); //loc_chooser
        } else { //loc_chooser
            connect();
        } //loc_chooser
        pMain = new EPanel(new BorderLayout(5, 5));
        getContentPane().add("Center", pMain);
        pLogin = new LoginPanel();
        pLogin.init();
        pMain.add("Center", pLogin);
        if (EAccess.eaccess().isWindows()) {
            setResizable(false);
        }
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent _we) {
                    EAccess.appendLog("window exit requested");  // MN31311135 track exit
                    exit();
                }
                public void windowActivated(WindowEvent _we) { //52838
                    validate(); //52838
                    refreshToolbar();                           //MN_button_disappear
                    repaint();
                } //52838
        });
        pack();
        center();
        System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");           //https_20051212
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());                    //https_20051212
    }

    private void init() {
        lblInstruct = new EMLabel(EAccess.eaccess().getString("intro1"));
        lblInstruct.setLabelFor(pMain);
        lblInstruct.setHorizontalAlignment(SwingConstants.CENTER);
        
        // CQ14860
        //lblLastLoginDate = new EMLabel("Last Login: ");
        //lblLastLoginDate.setHorizontalAlignment(SwingConstants.LEFT);
        //lblLastLoginDate.setBorder(new TitledBorder(""));
        // end CQ14860
        
        initMenu();
        maxMem = Float.valueOf(EAccess.eaccess().getString("maxMemory")).floatValue();
        memPct = Float.valueOf(EAccess.eaccess().getString("memPercent")).floatValue();

        int debuglvl = D.EBUG_DETAIL;
        String debugLvlStr = EAccess.eaccess().getString("mw.debugLevel"); // if not in file, returns a '?uDefKey:debugLevel' str
        if (debugLvlStr!=null && debugLvlStr.length()>0){
            char c = debugLvlStr.charAt(0); // should only be one digit
            if (Character.isDigit(c)){
                debuglvl = Integer.parseInt(new String( new char [] {c}));
            }
        }

 /*
from D.java, it defaults to EBUG_DETAIL
public static final int EBUG_ERR = 0;
public static final int EBUG_WARN = 1;
public static final int EBUG_INFO = 2;
public static final int EBUG_DETAIL = 3;
public static final int EBUG_SPEW = 4;
 */

        D.ebugLevel(debuglvl); // mw debug info was getting lost TIR6YZSXM, allow override
    }

    private void showInstruct() {
        pMain.add("Center", lblInstruct);
        //pMain.add("South",lblLastLoginDate); // CQ14860
        pMain.getAccessibleContext().setAccessibleName(EAccess.eaccess().getString("accessible.intro"));
        pMain.requestFocus();
        //      getAccessibleContext().setAccessibleName(lblInstruct.getText());
        //      getAccessibleContext().setAccessibleDescription(lblInstruct.getText());
    }

    private void hideInstruct() {
        pMain.remove(lblInstruct);
        //pMain.remove(lblLastLoginDate); //CQ14860
        pMain.getAccessibleContext().setAccessibleName(null);
        //      getAccessibleContext().setAccessibleDescription(null);
    }

    private void initOutput() {
        if (System.getProperty("lax.version") != null) {
            System.setProperty("eannounce.batch.mode", "false");
        } else {
            System.setProperty("eannounce.batch.mode", "true");
        }
    }

    private void center() {
        Dimension sSize = null;
        if (false) {
            Point lastLoc = EAccess.eaccess().getPrefPoint("LAST_LOCATION");
            Dimension lastSize = EAccess.eaccess().getPrefDimension("LAST_SIZE");
            if (lastLoc != null && lastSize != null) {
                setLocation(lastLoc);
                setSize(lastSize);
                return;
            }
        }
        sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((sSize.width - getWidth()) / 2), ((sSize.height - getHeight()) / 2));
    }

   /* private void remove(JPanel _pnl) {
        pMain.remove(_pnl);
    }*/

    /*
     * set properties for e-announce
     */
    private void setProperties() {
        File temp = null;
        File update = null;
        File saved = null;
        File log = null;
        File jars = null;
        File resource = null;
        File function = null;
        File cache = null;
        File batch = null;
        String sVersion = null;

        if (EAccess.isArmed(RMI_VERBOSE_ARM_FILE)) { //dwb_20040227
            System.setProperty("sun.rmi.client.logCalls", "true"); //dwb_20040227
            //          System.setProperty("sun.rmi.loader.logLevel","VERBOSE");                                        //dwb_20040227
            System.setProperty("sun.rmi.server.logLevel", "VERBOSE"); //dwb_20040227
            System.setProperty("sun.rmi.transport.logLevel", "VERBOSE"); //dwb_20040227
            System.setProperty("sun.rmi.transport.tcp.logLevel", "VERBOSE"); //dwb_20040227
        } //dwb_20040227
        System.setProperty("sun.rmi.transport.connectionTimeout", EAccess.eaccess().getString("rmi.connect.timeout")); //dwb_20040227
        if (EAccess.eaccess().isWindows() && EAccess.eaccess().canOverrideColor()) {
            UIManager.put("Button.background", Color.decode("#CCCCCC"));
            UIManager.put("TabbedPane.background", Color.gray);
            //53516         UIManager.put("TabbedPane.selected", Color.lightGray.brighter());
            UIManager.put("controlDkShadow", Color.black);
            UIManager.put("ComboBox.arrowButtonBackground", Color.lightGray);
            UIManager.put("Table.selectionBackground", DEFAULT_COLOR_HIGHLIGHT);
            UIManager.put("MenuItem.acceleratorForeground", Color.black);
            UIManager.put("RadioButtonMenuItem.acceleratorForeground", Color.black);
            UIManager.put("CheckBoxMenuItem.acceleratorForeground", Color.black);
            UIManager.put("ComboBox.buttonDarkShadow", Color.black);
        }

        UIManager.put("eannounce.focusBorder", new LineBorder(Color.black, 1));
        //print     UIManager.put("eannounce.printBorder", new LineBorder(Color.black,2));

        UIManager.put("eannounce.noFocusBorder", new EmptyBorder(1, 1, 1, 1));
        //      UIManager.put("eannounce.foundBorder", new LineBorder(getPrefColor(PREF_COLOR_FOUND,DEFAULT_COLOR_FOUND), 1));
        //      UIManager.put("eannounce.foundFocusBorder", new LineBorder(getPrefColor(PREF_COLOR_FOUND_FOCUS,DEFAULT_COLOR_FOUND_FOCUS), 1));

        //      UIManager.put("eannounce.selectedBorder", UIManager.getBorder("Table.focusCellHighlightBorder"));
        UIManager.put("eannounce.selectedBorder", new UnderlineBorder(Color.gray));
        UIManager.put("eannounce.loweredBorder", new BevelBorder(BevelBorder.LOWERED));
        UIManager.put("eannounce.raisedBorder", new BevelBorder(BevelBorder.RAISED));
        UIManager.put("eannounce.etchedBorder", new EtchedBorder(EtchedBorder.LOWERED));
        UIManager.put("eannounce.emptyBorder", new EmptyBorder(2, 0, 2, 0));
        //      UIManager.put("eannounce.emptyBorder", new LineBorder(Color.gray));
        UIManager.put("eannounce.underlineBorder", new UnderlineBorder(Color.gray));
        UIManager.put("eannounce.minimum", new Dimension(0, 0));
        UIManager.put("eannounce.buttonsize", new Dimension(20, 20));

        UIManager.put("eannounce.selectedIcon", EAccess.eaccess().getImageIcon("dot.gif"));
        UIManager.put("eannounce.unselectedIcon", EAccess.eaccess().getImageIcon("dot_opaque.gif"));
        UIManager.put("RadioButtonMenuItem.checkIcon", EAccess.eaccess().getImageIcon("empty.gif"));

        UIManager.put("eannounce.tab.selectedBorder", new LineBorder(Color.blue, 2));
        UIManager.put("eannounce.tab.nonSelectedBorder", new EmptyBorder(2, 2, 2, 2));

        temp = new File(TEMP_FOLDER);
        if (!temp.exists()) {
            temp.mkdirs();
        }

        update = new File(UPDATE_FOLDER);
        if (update.exists()) {
            File tmpjar = new File(UPDATE_DIRECTORY + "eaServer.tmp");
            if (tmpjar.exists()) {
                File curjar = new File(UPDATE_DIRECTORY + "eaServer.jar");
                if (curjar.delete()) {
                    tmpjar.renameTo(curjar);
                }
            }
            deleteFiles(UPDATE_DIRECTORY,DELETE_EXTENSION);         //acl_20050516
        } else {                        //acl_20050516
            update.mkdirs();            //acl_20050516
        }                               //acl_20050516

        saved = new File(SAVE_FOLDER);
        if (!saved.exists()) {
            saved.mkdirs();
        }

        log = new File(LOGS_FOLDER);
        if (!log.exists()) {
            log.mkdirs();
        }

        jars = new File(JAR_FOLDER);
        if (!jars.exists()) {
            jars.mkdirs();
        } else {                                                    //acl_20050516
            deleteFiles(JAR_DIRECTORY,DELETE_EXTENSION);            //acl_20050516
        }

        resource = new File(RESOURCE_FOLDER); //cache_20040112
        if (!resource.exists()) { //cache_20040112
            resource.mkdirs(); //cache_20040112
        } else {                                                    //acl_20050516
            deleteFiles(RESOURCE_DIRECTORY,DELETE_EXTENSION);       //acl_20050516
        } //cache_20040112

        function = new File(FUNCTION_FOLDER); //function
        if (!function.exists()) { //function
            function.mkdirs(); //function
        } else {                                                    //acl_20050516
            deleteFiles(FUNCTION_DIRECTORY, DELETE_EXTENSION);      //acl_20050516
        } //function

        batch = new File(BATCH_FOLDER);
        if (!batch.exists()) {
            batch.mkdirs();
        }

        cache = new File(CACHE_FOLDER); //cache_20040112
        if (!cache.exists()) { //cache_20040112
            cache.mkdirs(); //cache_20040112
        } else {                                                    //acl_20050516
            deleteFiles(CACHE_DIRECTORY,DELETE_EXTENSION);          //acl_20050516
        } //cache_20040112
        //      setCachePath(CACHE_DIRECTORY);                              //cache_20040112

        System.setProperty("eannounce.lib.ext", System.getProperty("java.home") + FILE_SEP + "lib" + FILE_SEP + "ext" + FILE_SEP);

        sVersion = getMessage("eannounce.version", EAccess.eaccess().getVersionParms());
        System.setProperty("eannounce.version", " " + EAccess.eaccess().getString("name") + " " + sVersion + " " + 
                EAccess.eaccess().getString("codeName"));
        System.setProperty("eannounce.jar.version", EAccess.eaccess().getJarVersion());
    }

    /**
     * reportProperties
     * @author Anthony C. Liberto
     */
    protected void reportProperties() {
        Properties p = System.getProperties();
//      Routines.dumpProperties(p);
        Toolkit toolKit = null;
        Dimension s = null;

        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            EAccess.appendLog("<!--");
        } //replay_log
        EAccess.appendLog("LOGS_DIRECTORY: " + LOGS_DIRECTORY);
        EAccess.appendLog("HOME_DIRECTORY: " + HOME_DIRECTORY);
        EAccess.appendLog("TEMP_DIRECTORY: " + TEMP_DIRECTORY);
        EAccess.appendLog("JAR_DIRECTORY: " + JAR_DIRECTORY);
        EAccess.appendLog("RESOURCE_DIRECTORY: " + RESOURCE_DIRECTORY); //cache_20040112
        EAccess.appendLog("FUNCTION_DIRECTORY: " + FUNCTION_DIRECTORY); //cache_20040112
        EAccess.appendLog("CACHE_DIRECTORY: " + CACHE_DIRECTORY); //cache_20040112
        EAccess.appendLog(displayProperty(p, "eannounce.mode.batch"));
        EAccess.appendLog(displayProperty(p, "eannounce.lib.ext"));
        EAccess.appendLog(displayProperty(p, MW_DESC));
        EAccess.appendLog(displayProperty(p, MW_OBJECT));
        EAccess.appendLog(displayProperty(p, MW_IP));
        EAccess.appendLog(displayProperty(p, MW_PORT));
        EAccess.appendLog(displayProperty(p, MW_REPORT));
        EAccess.appendLog(displayProperty(p, MW_VERSION));
        EAccess.appendLog(displayProperty(p, "file.encoding"));
        EAccess.appendLog(displayProperty(p, "user.home"));
        EAccess.appendLog(displayProperty(p, "user.name"));
        EAccess.appendLog(displayProperty(p, "user.language"));
        EAccess.appendLog(displayProperty(p, "user.region"));
        EAccess.appendLog(displayProperty(p, "user.country"));                                                  //acl_20031223
        EAccess.appendLog(displayProperty(p, "user.timezone"));
        EAccess.appendLog(displayProperty(p, "user.dir"));
        EAccess.appendLog(displayProperty(p, "os.arch"));
        EAccess.appendLog(displayProperty(p, "os.name"));
        EAccess.appendLog(displayProperty(p, "os.version"));
        EAccess.appendLog(displayProperty(p, "sun.os.patch.level"));                                            //acl_20031222
        EAccess.appendLog(displayProperty(p, "sun.cpu.isalist"));                                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "java.class.version"));                                            //acl_20031223
        EAccess.appendLog(displayProperty(p, "java.rmi.server.hostname"));                                      //acl_20031223
        EAccess.appendLog(displayProperty(p, "sun.arch.data.model"));                                           //acl_20031223
        EAccess.appendLog(displayProperty(p, "java.compiler"));                                                 //acl_20031223
        EAccess.appendLog(displayProperty(p, "java.library.path"));
        EAccess.appendLog(displayProperty(p, "java.vm.name"));
        EAccess.appendLog(displayProperty(p, "java.specification.version"));
        EAccess.appendLog(displayProperty(p, "java.vendor"));
        EAccess.appendLog(displayProperty(p, "java.vm.version"));
        EAccess.appendLog(displayProperty(p, "java.runtime.version"));
        EAccess.appendLog(displayProperty(p, "java.vm.info"));
        EAccess.appendLog(displayProperty(p, "java.fullversion"));
        EAccess.appendLog(displayProperty(p, "java.runtime.name"));
        EAccess.appendLog(displayProperty(p, "java.class.path"));
        EAccess.appendLog(displayProperty(p, "java.home"));
        EAccess.appendLog(displayProperty(p, "sun.boot.library.path"));
        EAccess.appendLog(displayProperty(p, "sun.io.unicode.encoding"));
        EAccess.appendLog(displayProperty(p, "lax.nl.current.vm"));
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.java.heap.size.initial"));
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.java.heap.size.max"));
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.stack.size.initial"));
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.stack.size.max"));
        EAccess.appendLog(displayProperty(p, "lax.root.install.dir"));
        EAccess.appendLog(displayProperty(p, "lax.application.name"));
        EAccess.appendLog(displayProperty(p, "lax.generated.launcher.name"));
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.additional"));
        EAccess.appendLog(displayProperty(p, "lax.version"));
        EAccess.appendLog(displayProperty(p, "lax.class.path"));
        EAccess.appendLog(displayProperty(p, "lax.main.class"));
        EAccess.appendLog(displayProperty(p, "lax.main.method"));                                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.command.line.args"));                                         //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.resource.dir"));                                              //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.compiler"));                                          //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.win32.java.compiler"));                                    //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.user.dir"));                                                  //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.launcher.main.method"));                              //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.launcher.main.class"));                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.verbose"));                                    //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.garbage.collection.extent"));                  //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.garbage.collection.background.thread"));       //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.java.option.debugging"));                                  //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.path"));                                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.java.compiler"));                                             //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.stdout.redirect"));                                           //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.stderr.redirect"));                                           //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.CLASSPATH"));                                          //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.PROCESSOR_ARCHITECTURE"));                             //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.PROCESSOR_IDENTIFIER"));                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.PROCESSOR_LEVEL"));                                    //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.PROCESSOR_REVISION"));                                 //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.NUMBER_OF_PROCESSORS"));                               //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.OS"));                                                 //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.COMPUTERNAME"));                                       //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.LOGONSERVER"));                                        //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.USERDOMAIN"));                                         //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.USERPROFILE"));                                        //acl_20031223
        EAccess.appendLog(displayProperty(p, "lax.nl.env.windir"));                                             //acl_20031223
        try {
            InetAddress add = InetAddress.getLocalHost();
            if (add != null) {
                InetAddress[] addra = InetAddress.getAllByName(add.getHostName());
                if (addra != null) {
                    int ii = addra.length;
                    for (int i = 0; i < ii; ++i) {
                        EAccess.appendLog("Local Internet Address[" + i + "]: " + addra[i].getHostAddress());
                    }
                }
            }
        } catch (Exception _x) {
            EAccess.report(_x,false);
        }

        EAccess.appendLog("** properties complete **");
        EAccess.appendLog("");
        toolKit = Toolkit.getDefaultToolkit();
        EAccess.appendLog("Screen Res :  " + toolKit.getScreenResolution());
        s = toolKit.getScreenSize();
        EAccess.appendLog("Screen Size:  " + s.width + " x " + s.height);
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            EAccess.appendLog("-->");
        } //replay_log
        setApplicationTitle();          //nb_20060515
    }

    private String displayProperty(Properties _prop, String _key) {
        return "System.Property: " + _key + " = " + _prop.getProperty(_key);

    }

    /*
     * connect to the database
     */
    private void connect() {
        EAccess.eaccess().connect(this);
        if (!EAccess.eaccess().isWindows() || EAccess.eaccess().not(System.getProperty("eannounce.batch.mode"), false)) {
            String sDate = EAccess.eaccess().getNow(DATE_TIME_ONLY);
            String fileName = LOGS_DIRECTORY + sDate + LOG_EXTENSION;
            try {
                fileOut = new FileOutputStream(fileName, true);
                pStream = new PrintStream(fileOut, true);
                System.setOut(pStream);
                System.setErr(pStream);
                if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
                    EAccess.appendLog("<e-announce13>");
                } //replay_log
            } catch (FileNotFoundException _fnfe) {
                _fnfe.printStackTrace();
            }
            removeOldLogFiles(); //50614
        } else {
            if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
                EAccess.appendLog("<e-announce13>");
            } //replay_log
        }
        reportProperties();
        cleanTempDir();
    }

    /**
     * Nothing should be left in the Temp dir.  The files are deleted if the JVM exits normally
     * They are left behind on error and can corrupt future usage.
     */
    private void cleanTempDir(){
        File f = new File(TEMP_DIRECTORY);
        if (f.exists()) {
            String[] files = f.list();
            if (files != null){
                for (int i = 0; i < files.length; ++i) {
                    File file = new File(TEMP_DIRECTORY, files[i]);
                    if (file.lastModified()
                    		< System.currentTimeMillis()-86400000){ // aged 24 hrs, just in case same UI instance is used for 2 ids
                    	EAccess.appendLog("cleanTempDir: Deleting "+file.getName());
                    	file.delete();
                    }
                }
            }
        }       
    }
    /**
     * main
     * @param _args
     * @author Anthony C. Liberto
     */
    public static void main(String[] _args) {
    	//acl_20040114      elogin = new eLogin();
    	Splash splash = null;
    	try{
    		splash = new Splash("splash.image");
    		elogin = new ELogin(); //acl_20040114
    		elogin.initLogin();
    		elogin.show();
    		if (splash.isShowing()) {
    			splash.remove();
    			splash = null; //acl_20040114
    		}
    		pLogin.requestFocus();
    	}catch(Throwable t){
    		t.printStackTrace(System.err);
    	}finally{
    		if (splash!=null && splash.isShowing()) {
    			splash.remove();
    			splash = null; 
    		}
    	}
    }

    /**
     * process
     * @param _pSet
     * @author Anthony C. Liberto
     */
    protected void process(ProfileSet _pSet) {
        Profile defProf = null;
        //MWObject mw = null;
        EAccess.eaccess().checkForExistingUpdate();
        EAccess.eaccess().setOffline(false);
        createWorkgroupMenu(false);
        EAccess.eaccess().setProfileSet(_pSet);
        createWorkgroupMenu(true);
        pMain.remove(pLogin);

        defProf = EAccess.eaccess().getDefaultProfile();
        if (defProf != null) {
            EAccess.eaccess().setProcessTime(defProf, EAccess.eaccess().getNow(ISO_DATE)); //update fix
            profileSelected(defProf, true);
        } else {
            showInstruct();
            //lblLastLoginDate.setText("Last Login: "+_pSet.getLastLoginTime());  // CQ14860
            
            pMain.revalidate();
            setMenubar();
            pack();
        }
        //accessibility     repaint();
        validate(); //accessibility
        EAccess.eaccess().setModalBusy(false);
    }
 
    /**
     * validateChat
     * @author Anthony C. Liberto
     */
    protected void validateChat() {
        System.out.println("eLogin.validateChat()");
        if (EAccess.eaccess().hasChat()) {
            boolean b = EAccess.isArmed(SAMETIME_ARM_FILE);
            setMenuEnabled(SYSTEM_MENU, "chat", b);
            setMenuVisible(SYSTEM_MENU, "chat", b);
        }
    }

    /*
     52685
        public void repaintImmediately() {
            update(getGraphics());
            return;
        }
    */
    private void initMenu() {
        mBar = new EMenubar();
        mnuHelp = new EMenu(HELP_MENU);
        createHelpMenu();
        mnuFile = new EMenu(FILE_MENU);
        createFileMenu();
        mnuSystem = new EMenu(SYSTEM_MENU);
        createSystemMenu();
        mnuWorkgroup = new EMenu(WORKGROUP_MENU);
    }

    private void setMenubar() {
        mBar.addMenu(mnuFile);
        mBar.addMenu(mnuWorkgroup);
        mBar.addMenu(mnuSystem);
        mBar.addMenu(mnuHelp);
        setJMenuBar(mBar);
    }

    /**
     * setEMenuBar note: JRE will do removeNotify() on the old menubar
     * @param _bar
     * @author Anthony C. Liberto
     */
    protected void setEMenuBar(EMenubar _bar) {
        if (_bar != null) {
            _bar.addMenu(mnuWorkgroup);
            _bar.addMenu(mnuSystem);
            _bar.addMenu(EAccess.eaccess().getTabMenu());
            _bar.addMenu(mnuHelp);
        }
        setJMenuBar(_bar);
    }

    private void createHelpMenu() {
        mnuHelp.addMenuItem("contents", this, 0, 0, true);
        mnuHelp.addMenuItem("attHelp", this, KeyEvent.VK_F1, 0, true);
        mnuHelp.addSeparator();
        mnuHelp.addMenuItem("fmi", this, 0, 0, true);
        mnuHelp.addMenuItem("about", this, 0, 0, true);
    }

    private void createSystemMenu() {
        mnuSystem.addMenuItem("memory", this, KeyEvent.VK_M, Event.CTRL_MASK, true);
        mnuSystem.addMenuItem("gc", this, KeyEvent.VK_G, Event.CTRL_MASK + Event.SHIFT_MASK, true);
        mnuSystem.addMenuItem("break", EAccess.eaccess(), KeyEvent.VK_CANCEL, Event.CTRL_MASK, true);
        mnuSystem.addSeparator();
        mnuSystem.addMenuItem("pref", this, 0, 0, true);
        mnuSystem.addSeparator();
        if (EAccess.isArmed(SAMETIME_ARM_FILE)) {
            mnuSystem.addMenuItem("chat", this, 0, 0, false);
            mnuSystem.addSeparator();
        }
        if (EAccess.isArmed(UPDATE_ARM_FILE)) {
            mnuSystem.addMenuItem("putUp", this, 0, 0, false);
        }
        mnuSystem.addMenuItem("checkUp", this, 0, 0, false);
        mnuSystem.addSeparator();
        mnuSystem.addMenuItem("nextTab", this, KeyEvent.VK_F3, 0, false);
        mnuSystem.addMenuItem("prevTab", this, KeyEvent.VK_F3, Event.SHIFT_MASK, false);
        mnuSystem.addSeparator();
        if (EAccess.isTestMode()) { //50870
            mnuSystem.addMenuItem("parseLog", this, 0, 0, true);
            mnuSystem.addSeparator();
        } //50870
        mnuSystem.addMenuItem("repaint", this, KeyEvent.VK_F9, Event.SHIFT_MASK, true); //cr_0805036452
        mnuSystem.addSeparator();
        mnuSystem.addMenuItem("resetDate", this, 0, 0, true);
        if (EAccess.isTestMode()) {
            mnuSystem.addSeparator();
            mnuSystem.addMenuItem("guest", this, KeyEvent.VK_COMMA, Event.CTRL_MASK, true);
        }
    }

    private void createFileMenu() {
        mnuFile.addMenuItem("logF", this, KeyEvent.VK_Q, Event.CTRL_MASK, true);
        //wait_for_UCD      mnuFile.addMenuItem("lgf", this, KeyEvent.VK_Q, Event.CTRL_MASK,true);          //51448
        mnuFile.addSeparator();
        mnuFile.addMenuItem("exit", EAccess.eaccess(), KeyEvent.VK_F4, Event.ALT_MASK, true);
    }

    private void createWorkgroupMenu(boolean _create) {
        ProfileSet ps = null;
        Vector vWG = null;
        Object[] WGArray = null;
        if (EAccess.isAccessible()) {
            mnuWorkgroup.getAccessibleContext().setAccessibleDescription(mnuWorkgroup.getText());
        } else {
            mnuWorkgroup.setMnemonic('G');
        }
        ps = EAccess.eaccess().getProfileSet();

        if (ps == null) {
            return;
        }

        // to get a list of WG, and sort them
        vWG = new Vector();
        for (int i = 0; i < ps.size(); i++) {
            Profile pi = ps.elementAt(i);
            Vector v = new Vector();
            v.addElement(pi.getWGName());
            v.addElement(new Integer(pi.getWGID()));
            vWG.addElement(v);
        }
        WGArray = vWG.toArray();
        Arrays.sort(WGArray, new EComparator(true));
        // add to Workgroup menu
        for (int i = 0; i < WGArray.length; i++) {
            Vector v = (Vector) WGArray[i];
            String WGName = (String) v.elementAt(0);
            Vector pVector = new Vector();
            int l = -1;
            Profile[] pArray = null;
            int WGID = ((Integer) v.elementAt(1)).intValue();
            if (_create) {
                mnuWorkgroup.addWorkgroupMenu(WGName, WGID, this, true);
            } else {
                mnuWorkgroup.removeWorkgroupMenu(WGName, WGID, this, true);
                continue;
            }
            // get a list of profile for each workgroup
            for (int j = 0; j < ps.size(); j++) {
                Profile pj = ps.elementAt(j);
                if (pj.getWGName().equals(WGName) && (pj.getWGID() == WGID)) {
                    pVector.add(pj);
                }
            }
            l = pVector.size();
            pArray = new Profile[l];
            for (int k = 0; k < l; k++) {
                Object o = pVector.elementAt(k);
                if (o instanceof Profile) {
                    pArray[k] = (Profile) o;
                }
            }
            Arrays.sort(pArray, new EComparator(true));
            if (_create) {
                mnuWorkgroup.adjustSubWorkgroup(WGName, WGID, pArray);
            }
        }
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        EAccess.appendLog("eLogin.actionPerformed: " + action);
        if (action.equals("break")) {
            EAccess.appendLog("    I am breaking");
            EAccess.eaccess().interrupt();
            return;
        }
        if (EAccess.eaccess().isBusy()) {
            EAccess.appendLog("    I am busy");
            return;
        }
        EAccess.eaccess().setBusy(true);
        if (action.equals("logF")) {
            //wait_for_UCD      if (action.equals("lgf")) {             //51448
            logOff(EAccess.eaccess().getTabbedPane());
        } else if (action.equals("memory")) {
            memory(true);
        } else if (action.equals("about")) {
            about();
        } else if (action.equals("fmi")) {
            EAccess.eaccess().launchURL(EAccess.eaccess().getString("fmiURL"));
        } else if (action.equals("contents")) {
            EAccess.eaccess().launchURL(EAccess.eaccess().getString("contentsURL"));
        } else if (action.equals("gc")) {
            gc();
        } else if (action.equals("parseLog")) {
            LogParser.parseLog();
        } else if (action.equals("repaint")) {
            repaintImmediately();
        } else if (action.equals("guest")) {
            guest();
            /*
            link_type
                    } else if (action.equals("noDup")) {
                        setLinkType("NODUPES");
                    } else if (action.equals("adtnl")) {
                        setLinkType("");
                    } else if (action.equals("rplAll")) {
                        setLinkType("REPLACEALL");
            */
        } else if (action.equals("resetDate")) {
            resetDate();
            return;
        } else if (action.equals("pref")) {
            EAccess.eaccess().show(PREFERENCE_PANEL, false);
            return;
        } else if (action.equals("chat")) {
            //          EAccess.eaccess().showChat();
            EAccess.eaccess().showRemoteChat();
        } else if (action.equals("putUp")) {
            EAccess.eaccess().putSoftwareImage();
            return;
        } else if (action.equals("checkUp")) {
            EAccess.eaccess().getSoftwareImage();
            return;
            //          EAccess.eaccess().checkForUpdates();
            //          EAccess.eaccess().checkForRemoteUpdates(false);
        } else if (action.equals("attHelp")) {
            attributeHelp();
        } else if (action.equals("nextTab")) {
            selectTab(1);
        } else if (action.equals("prevTab")) {
            selectTab(-1);
        } else if (action.equals("SUBWORKGROUPMENUITEM")) {
            Object o = _ae.getSource();
            if (o instanceof SubWorkgroupMenuItem) {
                SubWorkgroupMenuItem swmi = (SubWorkgroupMenuItem) o;
                Profile p = swmi.getProfile();
                profileSelected(p, true);
                return;
            }
        }
        //      } else {
        //          EAccess.eaccess().actionPerformed(_ae);
        //      }
        EAccess.eaccess().setBusy(false);
    }

    private void selectTab(int _i) {
        int newIndex = EAccess.eaccess().getSelectedIndex() + _i;
        EAccess.eaccess().setSelectedIndex(newIndex);
    }

    /**
     * adjustPrevNext
     * @param _indx
     * @param _max
     * @author Anthony C. Liberto
     */
    protected void adjustPrevNext(int _indx, int _max) {
        if (mnuSystem != null) {
            mnuSystem.setEnabled("prevTab", (_indx > 0 && _max != 0));
            mnuSystem.setEnabled("nextTab", (_indx < _max && _max != 0));
        }
    }

    /**
     * guest
     * @author Anthony C. Liberto
     */
    public void guest() {
        EAccess.appendLog(Routines.whoHasFocus());
        findKeys();
        EAccess.appendLog("linkType is: " + getLinkType());
    }

    /**
     * readerTest
     * @author Anthony C. Liberto
     * /
    protected void readerTest() {
        Object o = null;
        EAccess.eaccess().writeObject("profile.tst", EAccess.eaccess().getActiveProfile());
        o = EAccess.eaccess().readVariable("profile.tst", "m_strRoleDescription");
        if (o != null) {
            System.out.println("o.class(): " + o.getClass().getName());
            System.out.println("o.string(): " + o.toString());
        }
    }*/

    /*private void whoHasFocus() {
        EAccess.appendLog(Routines.whoHasFocus());
    }*/

    /**
     * testCipher
     * @author Anthony C. Liberto
     *
    protected void testCipher() {
        EAccess.eaccess().cipher().test("I am TeStInG cipher");
        return;
    }

    /**
     * fileEncrypt
     * @author Anthony C. Liberto
     *
    protected void fileEncrypt() {
        ECipher.cipher().testFileEncrypt(this);
    }

    /**
     * fileDecrypt
     * @author Anthony C. Liberto
     *
    protected void fileDecrypt() {
        ECipher.cipher().testFileDecrypt(this);
    }

    /**
     * generateSecretKey
     * @author Anthony C. Liberto
     *
    protected void generateSecretKey() {
        ECipher.cipher().generateSecretKey();
    }*/

    /**
     * testBrowse
     * @author Anthony C. Liberto
     * /
    protected void testBrowse() {
        JEditorPane jep = new JEditorPane();
        jep.setEditable(false);
        try {
            com.ibm.eannounce.dialogpanels.ComponentPanel cPnl = null;
            jep.setPage("http://www.ibm.com");
            cPnl = new com.ibm.eannounce.dialogpanels.ComponentPanel(jep);
            show(null, cPnl, false);
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        }
    }*/
    /*
     link_type
        private void setLinkType(String _s) {
            if (_s != null) {
                strLinkType = new String(_s);
            } else {
                strLinkType = new String();
            }
            return;
        }
    */
    /**
     * getLinkType
     * @return
     * @author Anthony C. Liberto
     */
    protected String getLinkType() {
        int i = EAccess.eaccess().getPrefInt(PREF_LINK_TYPE, DEFAULT_LINK_TYPE); //link_type
        return EAccess.eaccess().getString("eannounce.link.code" + i); //link_type
        //link_type     return strLinkType;
    }

    /**
     * gc
     * @author Anthony C. Liberto
     */
    protected void gc() {
        EAccess.gc();
    }

    private void attributeHelp() {
        String str = null;
        ETabable eTab = EAccess.eaccess().getCurrentTab();
        if (eTab != null) {
            str = eTab.getHelpText();
            //50491     } else {
            //50491         str = getString("nia");
        }
        //50491     setMessage(str);
        EAccess.eaccess().setMessage((str != null) ? str : EAccess.eaccess().getString("nia")); //50491
        EAccess.eaccess().showFYI(this);
    }

    private void resetDate() {
        boolean bPast = EAccess.eaccess().isPast();
        Profile prof = EAccess.eaccess().getActiveProfile();
        int r = -1;
        ETabable eTab = null;
        if (bPast) {
            EAccess.eaccess().setCode("msg23042");
            r = EAccess.eaccess().showConfirm(this, YES_NO_CANCEL, true);
            if (r == YES) {
                EAccess.eaccess().setProcessTime(prof, EAccess.eaccess().getNow(END_OF_DAY));
            } else if (r == NO) {
                String sDate = EAccess.eaccess().getDate();
                if (sDate == null) {
                    return;
                }
                EAccess.eaccess().setProcessTime(prof, sDate);
            } else {
                EAccess.eaccess().setBusy(false); //52549
                return;
            }
        } else {
            String sDate = EAccess.eaccess().getDate();
            if (sDate == null) {
                return;
            }
            EAccess.eaccess().setProcessTime(prof, sDate);
            if (sDate.equals(EAccess.eaccess().getNow(END_OF_DAY, false))) {
                EAccess.eaccess().setBusy(false);
                return;
            }
        }
        //1.2h      EAccess.eaccess().refresh(true);
        eTab = EAccess.eaccess().getCurrentTab(); //1.2h
        if (eTab instanceof ENavForm) { //1.2h
            ((ENavForm) eTab).refreshBothSides(); //1.2h
        } //1.2h
    }

    /*private String getProcessDate() {
        return EAccess.eaccess().getDate();
    }*/

    /**
     * launchURL
     * @param _url
     * @author Anthony C. Liberto
     * /
    private void launchURL(String _url) {
        EAccess.eaccess().launchURL(_url);
    }*/

    private void about() {
        //      setMessage(System.getProperty("eannounce.version") + "\n" + getString("copyright"));
        EAccess.eaccess().setMessage(System.getProperty("eannounce.version") + RETURN + EAccess.eaccess().getString("copyright") + RETURN + Version.getVersion());
        EAccess.eaccess().showFYI(this);
    }

    /**
     * memory
     * @param _bShow
     * @author Anthony C. Liberto
     */
    protected void memory(boolean _bShow) {
        Runtime r = null;
        long lFree = 0L;
        long lTotal = 0L;
        float consMem = 0f;
        float pctUsed = 0f;
        float pctAvail = 0f;
        long curAvail = 0L;

        System.gc();
        r = Runtime.getRuntime();
        lFree = r.freeMemory();
        lTotal = r.totalMemory();
        consMem = (lTotal - lFree);
        pctUsed = ((consMem / maxMem) * 100);
        pctAvail = (100 - pctUsed);
        curAvail = ((lFree * 100) / lTotal);
        EAccess.eaccess().setTitle(EAccess.eaccess().getString("memory"));
        EAccess.eaccess().setCode("memoryDisplay");
        EAccess.eaccess().setParmCount(9);
        EAccess.eaccess().setParm(0, D.etermineMemory());
        EAccess.eaccess().setParm(1, Float.toString(maxMem));
        EAccess.eaccess().setParm(2, Long.toString(lFree));
        EAccess.eaccess().setParm(3, Long.toString(lTotal));
        EAccess.eaccess().setParm(4, Float.toString(consMem));
        EAccess.eaccess().setParm(5, Float.toString(pctUsed));
        EAccess.eaccess().setParm(6, Float.toString(pctAvail));
        EAccess.eaccess().setParm(7, Long.toString(curAvail));
        EAccess.eaccess().setParm(8, Float.toString(memPct));
        EAccess.appendLog(EAccess.eaccess().getMessage());
        if (_bShow) {
            EAccess.eaccess().showFYI(this);
        }
        EAccess.eaccess().clearParms();
    }

    /**
     * showPreferences
     * @author Anthony C. Liberto
     * /
    private void showPreferences() {
        EAccess.eaccess().show(PREFERENCE_PANEL, false);
    }*/

    /**
     * profileSelected
     * @param _p
     * @param _sepThread
     * @author Anthony C. Liberto
     */
    protected void profileSelected(Profile _p, boolean _sepThread) {
        mnuSystem.setEnabled("putUp", true);
        mnuSystem.setEnabled("checkUp", EAccess.eaccess().isUpdateable());
        if (_p == null) {
            return;
        }
        Long t1 = EAccess.eaccess().timestamp("eLogin.profileSelected():00 at: ");
        if (eStatus == null) {
            eStatus = new EStatusbar();
            eStatus.setVersionTip(EAccess.eaccess().getString("copyright"));
            eStatus.setVersion(System.getProperty("eannounce.version"));
            EAccess.eaccess().timestamp("eLogin.profileSelected():06 at: ",t1);
        }
        EAccess.eaccess().setActiveProfile(_p);
        EAccess.eaccess().timestamp("eLogin.profileSelected():08 at: ",t1);
        launchRemote(_p, _sepThread);
        showTabPane();
        EAccess.eaccess().timestamp("eLogin.profileSelected():10 at: ",t1);
        EAccess.eaccess().autoCheckForUpdate();

        if (EAccess.isMonitor()) {
            EAccess.monitor("profileSelected " + _p.getOPWGID(),new Date());
        }
    }

    private void showTabPane() {
        if (!EAccess.eaccess().ePane.isShowing()) {
            pMain.add("Center", EAccess.eaccess().ePane);
            if (lblInstruct != null && lblInstruct.isShowing()) {
                hideInstruct();
            }
            pMain.revalidate();
//nb_20060515            setTitle(getString("name"));
        }
    }


    private void launchRemote(Profile _p, boolean _sepThread) {
        ENavForm.launch(_p, _sepThread);
        wrapupLaunch();
        repaint();
        if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
            EAccess.appendLog("<roleSelect enterprise=\"" + _p.getEnterprise() + "\" role=\"" + _p.getRoleCode() + "\" opwgid=\"" + _p.getOPWGID() + "\"/>");
        } //replay_log
    }

    /**
     * wrapupLaunch
     * @author Anthony C. Liberto
     */
    private void wrapupLaunch() {
        if (!eStatus.isShowing()) {
            if (EAccess.eaccess().isWindows()) {
                setResizable(true);
            }
            if (EAccess.isArmed(NO_MAX_ARM_FILE)) {
                setSize(new Dimension(900,600));
                validate();
            }else{
                setExtendedState(MAXIMIZED_BOTH);
            }
            pMain.add("South", eStatus);
        }
    }

    /**
     * setStatus
     * @param _p
     * @author Anthony C. Liberto
     */
    protected void setStatus(Profile _p) {
        if (_p != null) {
            eStatus.setLanguage(_p.getReadLanguage().toString());
            eStatus.setLanguageTip(EAccess.eaccess().getNLSInfo(_p.getReadLanguage()));
            eStatus.setRole(_p.toString());
            eStatus.setRoleTip(EAccess.eaccess().getProfileInfo(_p));
            setPast(EAccess.eaccess().isPast(), null); //51298
        }
    }

    //51975 private void loadEdit(EntityList _list, String _str, String _gif) {
    private void loadEdit(ETabable _parTab, EntityList _list, String _str, String _gif) { //51975
        ETabable eTab = EditController.loadFromFile(_list);
        EAccess.eaccess().addTab(_parTab, eTab); //kehrli_20030929
    }

 
    /**
     * load
     * @param _parTab
     * @param _list
     * @param _gif
     * @author Anthony C. Liberto
     */
    private void load(ETabable _parTab, MatrixList _list, String _gif) { //51975
        ETabable eTab = ActionController.loadFromFile(_list);
        EAccess.eaccess().addTab(_parTab, eTab); //kehrli_20030929
    }

    /**
     * load
     * @param _parTab
     * @param _list
     * @author Anthony C. Liberto
     */
    private void load(ETabable _parTab, WhereUsedList _list) { //51975
        ETabable eTab = ActionController.loadFromFile(_list);
        EAccess.eaccess().addTab(_parTab, eTab); //kehrli_20030929
    }

    /**
     * load
     * @param _parTab
     * @param _list
     * @author Anthony C. Liberto
     */
    private void load(ETabable _parTab, LockList _list) { //51975
        ETabable eTab = ActionController.loadFromFile(_list);
        EAccess.eaccess().addTab(_parTab, eTab); //kehrli_20030929
    }

    /**
     * exit
     * @author Anthony C. Liberto
     */
    protected void exit() {
        try{
            EAccess.eaccess().closeDictionary();
            EAccess.eaccess().disconnectChat();
            if (closeAll(EAccess.eaccess().getTabbedPane())) {
                if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) { //replay_log
                    EAccess.appendLog("</e-announce13>");
                } //replay_log
                if (EAccess.isMonitor()) {
                    EAccess.monitor("exit",new Date());
                }
                System.exit(0);
            }
        }catch(Throwable t){ // allow jui to exit when something goes really wrong
            System.err.println("Exception trying to exit, force it!");
            t.printStackTrace(System.err);
            System.exit(0);
        }
    }

    /*
    function
    */
    /**
     * close
     * @param _tabs
     * @param _tab
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean close(ETabbedMenuPane _tabs, ETabable _tab) {
        return close(_tabs, _tab, _tabs.getSelectedIndex(), false); //52614
    }

    //52614 public boolean close(eTabbedMenuPane _tabs, eTabable _tab, int _indx) {
    /**
     * close
     * @param _tabs
     * @param _tab
     * @param _indx
     * @param _bCloseAll
     * @return
     * @author Anthony C. Liberto
     */
    private boolean close(ETabbedMenuPane _tabs, ETabable _tab, int _indx, boolean _bCloseAll) { //52614
        if (_tab.close()) {
            _tabs.removeTabAt(_indx);
            _tabs.remove((Component) _tab);
            validate();
            if (_tabs.isEmpty()) {
                resetApplication(_tabs);
            } else {
                _tabs.selectParent(_tab);
            }
            _tab.dereference();
            _tab = null;
            repaint();
            return true;
        } else {
            _tabs.setSelectedComponent((Component) _tab);
        }
        return false;
    }

    /**
     * closeAll
     * @param _tabs
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean closeAll(ETabbedMenuPane _tabs) {
        if (_tabs != null) {
            while (_tabs.getTabCount() > 0) {
                Component c = _tabs.getComponentAt(0);
                if (c instanceof ETabable) {
                    //52614                 if (!close(_tabs,(eTabable)c,0)) {
                    if (!close(_tabs, (ETabable) c, 0, true)) { //52614
                        return false;
                    }
                } else {
                    _tabs.removeTabAt(0);
                }
            }
            _tabs.removeAll();
            resetApplication(_tabs);
        }
        return true;
    }

    private void resetApplication(ETabbedMenuPane _tabs) {
        if (lblInstruct == null) {
            return;
        }
        if (pMain != null && pMain.isShowing()) {
            pMain.remove(_tabs);
            showInstruct();
            mnuSystem.setEnabled("putUp", false);
            mnuSystem.setEnabled("checkUp", false);
            EAccess.eaccess().setActiveProfile(null);
            pMain.revalidate();
            setMenubar();
            if (eStatus != null && eStatus.isShowing()) {
                pMain.remove(eStatus);
            }
        }
    }

    /**
     * logOff
     * @param _tabs
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean logOff(ETabbedMenuPane _tabs) {
        if (closeAll(_tabs)) {
            hideInstruct();
            if (eStatus != null) {
                pMain.remove(eStatus);
            }
            pLogin.reset();
            pMain.add("Center", pLogin);
            setJMenuBar(null);
            pack();
            center();
            if (EAccess.eaccess().isWindows()) {
                setResizable(false);
            }
            EAccess.eaccess().reset(); //acl_20031211
            pLogin.requestFocus();
//nb_20060515            setTitle(getString("name"));
            setApplicationTitle();              //nb_20060515
            repaint();
            EAccess.eaccess().setOffline(true);
            if (EAccess.isMonitor()) {
                EAccess.monitor("logOff", new Date());
            }
            return true;
        }
        return false;
    }

    //52548 public void setPast(boolean _bPast) {
    /**
     * setPast
     * @param _bPast
     * @param _time
     * @author Anthony C. Liberto
     */
    protected void setPast(boolean _bPast, String _time) { //52548
        if (eStatus != null) {
            eStatus.setPast(_bPast, _time); //52548
            //52548         eStatus.setPast(_bPast);
        }
    }

    /**
     * appendLog
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setHidden(boolean _b) { //52476
        if (eStatus != null) { //52476
            eStatus.setHidden(_b); //52476
        } //52476
    } //52476

    /*
    eInterface
    */
    /**
     * appendLog
     * @param _log
     * @author Anthony C. Liberto
     * /
    private void appendLog(String _log) {
        EAccess.appendLog(_log);
    }*/

    /**
     * getPrefColor
     * @param _code
     * @param _def
     * @return
     * @author Anthony C. Liberto
     * /
    public Color getPrefColor(String _code, Color _def) {
        return EAccess.eaccess().getPrefColor(_code, _def);
    }*/

    /**
     * setUseDefined
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setUseDefined(boolean _b) {
        setUseBack(_b);
        setUseFore(_b);
        setUseFont(_b);
    }*/

    /**
     * setUseBack
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setUseBack(boolean _b) {
        bUseBack = _b;
    }*/

    /**
     * setUseFore
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setUseFore(boolean _b) {
        bUseFore = _b;
    }*/

    /**
     * setUseFont
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setUseFont(boolean _b) {
        bUseFont = _b;
    }*/

    /**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
        if (EAccess.eaccess().canOverrideColor() && bUseBack) {
            if (isEnabled()) {
                return EAccess.eaccess().getBackground();
            } else {
                return EAccess.eaccess().getDisabledBackground();
            }
        }
        return super.getBackground();
    }

    /**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
        if (EAccess.eaccess().canOverrideColor() && bUseFore) {
            if (isEnabled()) {
                return EAccess.eaccess().getForeground();
            } else {
                return EAccess.eaccess().getDisabledForeground();
            }
        }
        return super.getForeground();
    }

    /**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
        if (EANNOUNCE_UPDATE_FONT && bUseFont) {
            return EAccess.eaccess().getFont();
        }
        return super.getFont();
    }

    /**
     * interface into eAccess getString()
     *
     * @return String
     * @param _code
     * /
    private String getString(String _code) {
        return EAccess.eaccess().getString(_code);
    }*/

    /**
     * isArmed
     * @param _code
     * @return
     * @author Anthony C. Liberto
     * /
    private boolean isArmed(String _code) {
        return EAccess.isArmed(_code);
    }*/

    /**
     * isTestMode
     * @return
     * @author Anthony C. Liberto
     * /
    private boolean isTestMode() {
        return EAccess.isTestMode();
    }*/

    /**
     * interface into eAccess showFYI
     *
     * @param _c
     * /
    private void showFYI(Component _c) {
        EAccess.eaccess().showFYI(_c);
    }*/

    /**
     * interface into eAccess showError
     *
     * @param _c
     * /
    public void showError(Component _c) {
        EAccess.eaccess().showError(_c);
    }*/

    /**
     * interface into eAccess showConfirm
     *
     * @return int
     * @param _buttons
     * @param _c
     * @param _clear
     * /
    public int showConfirm(Component _c, int _buttons, boolean _clear) {
        return EAccess.eaccess().showConfirm(_c, _buttons, _clear);
    }*/

    /**
     * interface into eAccess showInput
     *
     * @return String
     * @param _c
     * /
    private String showInput(Component _c) {
        return EAccess.eaccess().showInput(_c);
    }*/

    /**
     * interface into eAccess show
     *
     * @param _buttons
     * @param _c
     * @param _dialogType
     * @param _msgType
     * @param _reset
     * /
    public void show(Component _c, int _dialogType, int _msgType, int _buttons, boolean _reset) {
        EAccess.eaccess().show(_c, _dialogType, _msgType, _buttons, _reset);
    }*/

    /**
     * interface into eAccess show
     *
     * @param _c
     * @param _dc
     * @param _modal
     * /
    public void show(Component _c, DisplayableComponent _dc, boolean _modal) {
        EAccess.eaccess().show(_c, _dc, _modal);
    }*/

    /**
     * show
     * @param _iPanel
     * @param _modal
     * @author Anthony C. Liberto
     * /
    private void show(int _iPanel, boolean _modal) {
        EAccess.eaccess().show(_iPanel, _modal);
    }*/

    /**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     * /
    private void setCode(String _code) {
        EAccess.eaccess().setCode(_code);
    }*/

    /**
     * setMessageTitle
     * @param _title
     * @author Anthony C. Liberto
     * /
    private void setMessageTitle(String _title) {
        EAccess.eaccess().setTitle(_title);
    }*/

    /**
     * setMessage
     * @param _message
     * @author Anthony C. Liberto
     * /
    private void setMessage(String _message) {
        EAccess.eaccess().setMessage(_message);
    }*/

    /**
     * interface into eAccess setParms
     *
     * @param _s
     * /
    private void setParm(String _s) {
        EAccess.eaccess().setParm(_s);
    }*/

    /**
     * interface into eAccess setParms
     *
     * @param _s
     * /
    public void setParms(String[] _s) {
        EAccess.eaccess().setParms(_s);
    }*/

    /**
     * interface into eAccess setParms
     *
     * @param _delim
     * @param _s
     * /
    public void setParms(String _s, String _delim) {
        EAccess.eaccess().setParms(_s, _delim);
    }*/

    /**
     * interface into eAccess setParmCount
     *
     * @param _i
     * /
    private void setParmCount(int _i) {
        EAccess.eaccess().setParmCount(_i);
    }*/

    /**
     * interface into eAccess setParm
     *
     * @param _i
     * @param _s
     * /
    private void setParm(int _i, String _s) {
        EAccess.eaccess().setParm(_i, _s);
    }*/

    /**
     * interface into eAccess clearParms
     * /
    private void clearParms() {
        EAccess.eaccess().clearParms();
    }*/

    /**
     * interface into eAccess getMessage
     *
     * @return String
     * /
    private String getMessage() {
        return EAccess.eaccess().getMessage();
    }*/

    /**
     * getMessage
     * @param _code
     * @param _parm
     * @return
     * @author Anthony C. Liberto
     * /
    public String getMessage(String _code, String _parm) {
        String out = null;
        EAccess.eaccess().setCode(_code);
        EAccess.eaccess().setParm(_parm);
        out = EAccess.eaccess().getMessage();
        EAccess.eaccess().clear();
        return out;
    }*/

    /**
     * getMessage
     * @param _code
     * @param _parm
     * @param _delim
     * @return
     * @author Anthony C. Liberto
     * /
    public String getMessage(String _code, String _parm, String _delim) {
        String out = null;
        EAccess.eaccess().setCode(_code);
        EAccess.eaccess().setParms(_parm, _delim);
        out = EAccess.eaccess().getMessage();
        EAccess.eaccess().clear();
        return out;
    }*/

    /**
     * getMessage
     * @param _code
     * @param _parm
     * @return
     * @author Anthony C. Liberto
     */
    private String getMessage(String _code, String[] _parm) {
        String out = null;
        EAccess.eaccess().setCode(_code);
        EAccess.eaccess().setParms(_parm);
        out = EAccess.eaccess().getMessage();
        EAccess.eaccess().clear();
        return out;
    }

    /**
     * getImage
     * @author Anthony C. Liberto
     * @return Image
     * @param _img
     * /
    private Image getImage(String _img) {
        return EAccess.eaccess().getImage(_img);
    }*/

    /**
     * getImageIcon
     * @author Anthony C. Liberto
     * @return ImageIcon
     * @param _img
     * /
    private ImageIcon getImageIcon(String _img) {
        return EAccess.eaccess().getImageIcon(_img);
    }*/

    /**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        if (bModalCursor) {
            return EAccess.eaccess().getModalCursor();
        } else {
            EDisplayable disp = getDisplayable();
            if (disp != null) {
                return disp.getCursor();
            }
        }
        return EAccess.eaccess().getCursor();
    }

    /**
     * getDisplayable
     * @return
     * @author Anthony C. Liberto
     */
    private EDisplayable getDisplayable() {
        Container par = getParent();
        if (par instanceof EDisplayable) {
            return (EDisplayable) par;
        }
        return null;
    }

    /**
     * setModalCursor
     * @param _b
     * @author Anthony C. Liberto
     * /
    public void setModalCursor(boolean _b) {
        bModalCursor = _b;
    }*/

    /**
     * isModalCursor
     * @return
     * @author Anthony C. Liberto
     * /
    private boolean isModalCursor() {
        return bModalCursor;
    }*/

    /**
     * setBusy
     *
     * @author Anthony C. Liberto
     * @param _b
     * /
    private void setBusy(boolean _b) {
        EAccess.eaccess().setBusy(_b);
    }*/

    /**
     * isBusy
     *
     * @author Anthony C. Liberto
     * @return boolean
     * /
    private boolean isBusy() {
        return EAccess.eaccess().isBusy();
    }*/

    /**
     * setModalBusy
     *
     * @author Anthony C. Liberto
     * @param _b
     * /
    public void setModalBusy(boolean _b) {
        EAccess.eaccess().setModalBusy(_b);
    }*/

    /**
     * isModalBusy
     *
     * @author Anthony C. Liberto
     * @return boolean
     * /
    public boolean isModalBusy() {
        return EAccess.eaccess().isModalBusy();
    }*/

    /**
     * setFilter
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setFilter(boolean _b) {
        if (eStatus != null) {
            eStatus.setFilter(_b);
        }
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    protected void refreshAppearance() {
        validate();
        getJMenuBar().revalidate();
        if (eStatus != null) {
            eStatus.refreshAppearance();
        }
    }

    /*
     the methods in use are deprecated but in there infinite
     wisdom they didn't leave me any other way to update the
     cursor.  They took the GlobalCursorManager away!!!!
     The good news is it works, only issue is a compilation
     warning.
    */
    /**
     * @see java.awt.Component#updateCursorImmediately()
     * @author Anthony C. Liberto
     */
    public void updateCursorImmediately() {
        ComponentPeer peer = getPeer();
        if (peer != null) {
            peer.updateCursorImmediately();
        }
    }

    /*
    50494
    */
    /**
     * setMenuEnabled
     * @param _menu
     * @param _item
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setMenuEnabled(String _menu, String _item, boolean _b) {
        if (_menu.equalsIgnoreCase(HELP_MENU)) {
            if (mnuHelp != null) {
                mnuHelp.setEnabled(_item, _b);
            }
        } else if (_menu.equalsIgnoreCase(FILE_MENU)) {
            if (mnuFile != null) {
                mnuFile.setEnabled(_item, _b);
            }
        } else if (_menu.equalsIgnoreCase(SYSTEM_MENU)) {
            if (mnuSystem != null) {
                mnuSystem.setEnabled(_item, _b);
            }
        }
    }

    /**
     * setMenuVisible
     * @param _menu
     * @param _item
     * @param _b
     * @author Anthony C. Liberto
     */
    private void setMenuVisible(String _menu, String _item, boolean _b) {
        if (_menu.equalsIgnoreCase(HELP_MENU)) {
            if (mnuHelp != null) {
                mnuHelp.setVisible(_item, _b);
            }
        } else if (_menu.equalsIgnoreCase(FILE_MENU)) {
            if (mnuFile != null) {
                mnuFile.setVisible(_item, _b);
            }
        } else if (_menu.equalsIgnoreCase(SYSTEM_MENU)) {
            if (mnuSystem != null) {
                mnuSystem.setVisible(_item, _b);
            }
        }
    }
    /*
     50614
    */
    /**
     * removeOldLogFiles
     * @author Anthony C. Liberto
     */
    private void removeOldLogFiles() {
        String[] files = EAccess.eaccess().gio().list(LOGS_DIRECTORY, LOG_EXTENSION);
        String dateString = EAccess.eaccess().getDateString(-7);
        if (files != null) {
            int ii = files.length;
            for (int i = 0; i < ii; ++i) {
                //acl_20040114              int x = files[i].compareTo(dateString);
                if (Routines.isNumeric(files[i])) {
                    int x = files[i].compareTo(dateString); //acl_20040114
                    if (x < 0) {
                        File file = new File(LOGS_DIRECTORY, files[i]);
                        file.delete();
                    }
                }
            }
        }
    }
    /*
     acl_20030718
     */
    private void findKeys() {
        ETabable eTab = EAccess.eaccess().getCurrentTab();
        String sKey = null;
        String[] ra = null;
        if (eTab != null) {
            EAccess.eaccess().setCode("msg12007.0");
            EAccess.eaccess().setParm(ARRAY_DELIMIT);
            sKey = EAccess.eaccess().showInput(this);
            ra = Routines.getStringArray(sKey, ARRAY_DELIMIT);
            if (ra != null) {
                eTab.selectKeys(ra);
            }
        }
    }

    /*
     51975
     */
    //cr_1210035324 public void load(eTabable _parTab,Object _o, String _gif) {
    /**
     * load
     * @param _parTab
     * @param _o
     * @param _book
     * @param _gif
     * @author Anthony C. Liberto
     */
    protected void load(ETabable _parTab, Object _o, BookmarkItem _book, String _gif) {
        if (_o != null) {
            if (_o instanceof EntityList) {
                //cr_1210035324             load(_parTab,(EntityList)_o, _gif);
                load(_parTab, (EntityList) _o, _book, _gif); //cr_1210035324
            } else if (_o instanceof MatrixList) {
                //load(_parTab, (MatrixList) _o, null, _gif);
                load(_parTab, (MatrixList) _o, _gif);
            } else if (_o instanceof WhereUsedList) {
                //load(_parTab, (WhereUsedList) _o, null, _gif);
                load(_parTab, (WhereUsedList) _o);
            } else if (_o instanceof LockList) {
                //load(_parTab, (LockList) _o, null, _gif);
                load(_parTab, (LockList) _o);
            }
        }
    }

    //cr_1210035324 public void load(eTabable _parTab,EntityList _list, String _gif) {
    /**
     * load
     * @param _parTab
     * @param _list
     * @param _book
     * @param _gif
     * @author Anthony C. Liberto
     */
    private void load(ETabable _parTab, EntityList _list, BookmarkItem _book, String _gif) { //cr_1210035324
        EANActionItem action = null;
        showTabPane();
        action = _list.getParentActionItem();
        if (action instanceof EditActionItem) {
            loadEdit(_parTab, _list, EAccess.eaccess().getString("edit") + " -- ", "edit.gif");
        } else if (action instanceof CreateActionItem) {
            loadEdit(_parTab, _list, EAccess.eaccess().getString("crte") + " -- ", "crte.gif");
        } else if (action instanceof NavActionItem) {
            loadNav(_parTab, _list, _book, _gif); //cr_1210035324
            //cr_1210035324         loadNav(_parTab,_list, _gif);
        } else if (action instanceof SearchActionItem) {
            loadNav(_parTab, _list, _book, _gif); //cr_1210035324
            //cr_1210035324         loadNav(_parTab,_list, _gif);
        }
    }

    /**
     * load
     * @param _list
     * @param _ei
     * @param _gif
     * @author Anthony C. Liberto
     */
    protected void load(WhereUsedList _list, EntityItem[] _ei, String _gif) {
        ActionController ac = new ActionController(_list, _list.getKey());
        ac.setEntityItemArray(_ei);
        EAccess.eaccess().addTab(null, ac, _gif);
        ac.requestFocus(-1);
    }

    /**
     * load
     * @param _eList
     * @param _ei
     * @param _gif
     * @author Anthony C. Liberto
     */
    protected void load(EntityList _eList, EntityItem[] _ei, String _gif) {
        EditController ec = new EditController(_eList, null);
        EAccess.eaccess().addTab(null, ec, _gif);
    }

    /**
     * load
     * @param _list
     * @param _ei
     * @param _gif
     * @author Anthony C. Liberto
     */
    protected void load(MatrixList _list, EntityItem[] _ei, String _gif) {
        ActionController ac = new ActionController(_list, _list.getKey());
        ac.setEntityItemArray(_ei);
        EAccess.eaccess().addTab(null, ac, _gif);
        ac.requestFocus(-1);
    }

    /**
     * load
     * @param _list
     * @param _gif
     * @author Anthony C. Liberto
     */
    protected void load(EntityList _list, String _gif) {
        ENavForm.loadFromFile(_list, _gif);
    }

    /**
     * load
     * @param _parTab
     * @param _hist
     * @param _entityList
     * @param _navType
     * @param _gif
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    protected ENavForm load(ETabable _parTab, Object _hist, Object _entityList, int _navType, String _gif, Profile _prof) {
        if (_entityList != null && _entityList instanceof EntityList) {
            return loadNav(_parTab, _hist, (EntityList) _entityList, _navType, _gif, _prof);
        }
        return null;
    }

    private ENavForm loadNav(ETabable _parTab, Object _history, EntityList _list, int _navType, String _gif, Profile _prof) {
        return ENavForm.loadFromFile(_parTab, _history, _list, _gif, _navType, _prof);
    }

    //cr_1210035324 private void loadNav(eTabable _parTab, EntityList _list, String _gif) {
    //cr_1210035324     eNavForm.loadFromFile(_parTab,_list,_gif);
    private void loadNav(ETabable _parTab, EntityList _list, BookmarkItem _book, String _gif) { //cr_1210035324
        ENavForm.loadFromFile(_parTab, _list, _book, _gif); //cr_1210035324
    }

    /*
     52534
     */
    /**
     * replayDefaultColor
     * @author Anthony C. Liberto
     */
    public void replayDefaultColor() {
        setBackground(UIManager.getColor("frame.default.background"));
        setForeground(UIManager.getColor("frame.default.foreground"));
    }
    /*
     52685
     */
    /**
     * repaintImmediately
     * @author Anthony C. Liberto
     */
    protected void repaintImmediately() {
        try {
            repaintImmediately2();
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

    private void repaintImmediately2() throws Exception {
        Color color = getForeground();
        Font font = null;
        if (color == null) {
            setForeground(DEFAULT_COLOR_ENABLED_FOREGROUND);
        }
        color = getBackground();
        if (color == null) {
            setBackground(DEFAULT_COLOR_ENABLED_BACKGROUND);
        }
        font = getFont();
        if (font == null) {
            setFont(DEFAULT_FONT);
        }
        update(getGraphics());
    }

    /*
     acl_20031219
     */
    /**
     * @see java.awt.Component#validate()
     * @author Anthony C. Liberto
     */
    public void validate() {
        try {
            super.validate();
        } catch (Exception _x) {
            _x.printStackTrace();
        }
    }

    /*
     accessibility
     */
    /**
     * getComponent
     * @return
     * @author Anthony C. Liberto
     */
    protected JComponent getComponent() {
        return pMain;
    }
    /*
     loc_choose
     */
    private void connectMiddleware() {
        if (!EAccess.eaccess().isWindows() || EAccess.eaccess().not(System.getProperty("eannounce.batch.mode"), false)) {
            Calendar cal = Calendar.getInstance();
            String sDate = EAccess.eaccess().formatDate(DATE_TIME_ONLY, cal.getTime());
            String fileName = LOGS_DIRECTORY + sDate + LOG_EXTENSION;
            try {
                fileOut = new FileOutputStream(fileName, true);
                pStream = new PrintStream(fileOut, true);
                System.setOut(pStream);
                System.setErr(pStream);
                if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) {
                    EAccess.appendLog("<e-announce13>");
                }
            } catch (FileNotFoundException _fnfe) {
                _fnfe.printStackTrace();
            }
            removeOldLogFiles(cal);
        } else {
            if (EAccess.isArmed(REPLAYABLE_ARM_FILE)) {
                EAccess.appendLog("<e-announce13>");
            }
        }
        cleanTempDir();
    }

    /**
     * connectMiddleware
     * @param _mw
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean connectMiddleware(MWObject _mw) {
        boolean out = EAccess.eaccess().connect(this, _mw);
        if (out) {
            reportProperties();
        }
        return out;
    }

    private void removeOldLogFiles(Calendar _cal) {
        String[] files = EAccess.eaccess().gio().list(LOGS_DIRECTORY, LOG_EXTENSION);
        String dateString = null;
        //USRO-R-JSTT-66DMB7        _cal.roll(Calendar.DATE,-7);
        _cal.add(Calendar.DATE, -7); //USRO-R-JSTT-66DMB7
        dateString = EAccess.eaccess().formatDate(DATE_TIME_ONLY, _cal.getTime());
        System.out.println("delete as of: " + dateString);
        if (files != null) {
            int ii = files.length;
            for (int i = 0; i < ii; ++i) {
                if (Routines.isNumeric(files[i])) {
                    int x = files[i].compareTo(dateString);
                    if (x < 0) {
                        File file = new File(LOGS_DIRECTORY, files[i]);
                        file.delete();
                    }
                }
            }
        }
    }

    /**
     * refresh toolbar
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    private void refreshToolbar() {
        ETabable eTab = EAccess.eaccess().getCurrentTab();
        if (eTab != null) {
            eTab.refreshToolbar();
        }
    }


    /**
     * batch files
     *
     * acl_20050516
     * @author Anthony C. Liberto
     */
    private void executeBatchFiles(String _dir, String _ext) {
        String[] files = EAccess.eaccess().gio().list(_dir, _ext);
        if (files != null) {
            int ii = files.length;
            for (int i = 0; i < ii; ++i) {
                EAccess.eaccess().shellCommand(_dir+files[i]);
            }
        }
    }

    /**
     * delete files
     *
     * acl_20050516
     * @author Anthony C. Liberto
     */
    private void deleteFiles(String _dir, String _ext) {
        String[] files = EAccess.eaccess().gio().list(_dir, _ext);
        int iLen = 0;
        File file2 = null;
        if (files != null) {
            int ii = files.length;
            for (int i = 0; i < ii; ++i) {
                File file = new File(_dir, files[i]);
                if (!file.delete()) {
                    file.deleteOnExit();
                }
                iLen = files[i].length() - _ext.length();
                file2 = new File(_dir,files[i].substring(0,iLen));
                if (file2.exists()) {
                    if (!file2.delete()) {
                        file2.deleteOnExit();
                    }
                }
            }
        }
    }

    /**
     * nb_20060515
     * testers appear to be working in the wrong instance while testing.
     * this will provide better feedback on the middleware service they
     * are connected to.
     */
    private void setApplicationTitle() {
        String str = EAccess.eaccess().getString("name");
        String[] parms = {str,                                  //{parm0}
                          System.getProperty(MW_DESC),          //{parm1}
                          System.getProperty(MW_OBJECT),        //{parm2}
                          System.getProperty(MW_IP),            //{parm3}
                          System.getProperty(MW_PORT),          //{parm4}
                          System.getProperty(MW_REPORT),        //{parm5}
                          System.getProperty(MW_VERSION)};      //{parm6}
        if (parms[1] != null) {
            str = EAccess.eaccess().getMessage("app.title",parms);
        }
        setTitle(str);
    }
}
