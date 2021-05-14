/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: EAccess.java,v $
 * Revision 1.24  2012/04/05 17:27:34  wendy
 * jre142 and win7 changes
 *
 * Revision 1.23  2010/08/24 20:40:11  wendy
 * Avoid report.deref releasing shared objects
 *
 * Revision 1.22  2009/12/18 12:18:34  wendy
 * make sure rpt url has a closing slash
 *
 * Revision 1.21  2009/09/01 17:22:25  wendy
 * removed useless code and cleanup
 *
 * Revision 1.20  2009/05/28 14:02:11  wendy
 * Performance cleanup
 *
 * Revision 1.19  2009/04/15 19:52:27  wendy
 * move filekey to where it is used
 *
 * Revision 1.18  2009/04/10 11:31:32  wendy
 * chg timestamp
 *
 * Revision 1.17  2009/03/06 22:25:28  wendy
 * Allow for failed login and release file lock
 *
 * Revision 1.16  2009/03/06 20:05:15  wendy
 * Allow for failed login
 *
 * Revision 1.15  2009/02/27 15:05:24  wendy
 * Part of CQ00021335 - login, bp api chgs
 *
 * Revision 1.14  2008/02/29 22:05:38  wendy
 * Added support for timestamp interval
 *
 * Revision 1.13  2008/02/26 14:18:37  wendy
 * add duration to timestamp msg
 *
 * Revision 1.12  2008/02/21 19:18:52  wendy
 * Add access to change history for relators
 *
 * Revision 1.11  2008/02/20 15:43:15  wendy
 * Show error instead of info for import errors
 *
 * Revision 1.10  2008/02/19 16:32:07  wendy
 * Add support for importing an XLS file
 *
 * Revision 1.9  2008/02/04 14:22:47  wendy
 * Cleanup more RSA warnings
 *
 * Revision 1.8  2008/02/01 18:11:30  wendy
 * restore autoupdate chk chgd by RSA
 *
 * Revision 1.7  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.6  2008/01/23 22:36:37  wendy
 * Improve performance by hanging onto profile entityitem
 *
 * Revision 1.5  2007/08/02 12:22:55  wendy
 * Display pdhdomain information with profile
 *
 * Revision 1.4  2007/08/01 18:36:30  wendy
 * RQ0713072645 - use parent for null component to showerror
 *
 * Revision 1.3  2007/06/27 13:54:37  wendy
 * Added more error reporting
 *
 * Revision 1.2  2007/06/26 19:22:31  wendy
 * Improve error handling for reportaction
 *
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.15  2006/11/09 19:04:56  tony
 * update monitor again
 *
 * Revision 1.14  2006/11/09 15:51:06  tony
 * more monitor logic
 *
 * Revision 1.13  2006/11/09 01:29:33  tony
 * monitor
 *
 * Revision 1.12  2006/11/09 00:31:43  tony
 * added monitor functionality
 *
 * Revision 1.11  2006/06/19 19:32:56  tony
 * added timing logic for refresh functionality
 *
 * Revision 1.10  2006/05/09 17:48:37  tony
 * cr 103103686 complete
 *
 * Revision 1.9  2006/05/04 16:14:32  tony
 * cr103103686
 *
 * Revision 1.8  2006/05/02 17:11:08  tony
 * CR103103686
 *
 * Revision 1.7  2006/03/23 20:32:44  tony
 * adjusted profile dump to show read-only
 *
 * Revision 1.6  2006/03/16 22:01:28  tony
 * Capture logging
 *
 * Revision 1.5  2006/03/07 22:24:19  tony
 * CR 6299
 * update mandatory/optional
 *
 * Revision 1.4  2006/02/15 17:42:11  tony
 * updated tunneling per Steve for BUI
 *
 * Revision 1.3  2005/12/15 19:08:58  tony
 * reworked logic for testing purposes
 *
 * Revision 1.2  2005/09/20 16:01:59  tony
 * CR092005410
 * Ability to add middleware location on the fly.
 *
 * Revision 1.1.1.1  2005/09/09 20:37:36  tony
 * This is the initial load of OPICM
 *
 * Revision 1.106  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.105  2005/06/21 17:58:01  tony
 * 24423680 -- updating non-existant blobs.
 *
 * Revision 1.104  2005/05/26 15:37:26  tony
 * update
 *
 * Revision 1.103  2005/05/26 15:16:46  tony
 * adjusted logic
 *
 * Revision 1.102  2005/05/25 20:59:28  tony
 * improved logic
 *
 * Revision 1.101  2005/05/25 18:15:42  tony
 * silverBulletReload
 *
 * Revision 1.100  2005/05/24 22:25:43  tony
 * adjusted system statements
 *
 * Revision 1.99  2005/05/24 21:49:17  tony
 * wooden stake
 *
 * Revision 1.98  2005/05/24 21:27:57  tony
 * silverBullet
 *
 * Revision 1.97  2005/05/17 20:07:23  tony
 * added batch file execution component.
 *
 * Revision 1.96  2005/05/17 17:48:31  tony
 * updated logic to address update of e-announce application.
 * added madatory update functionality as well.
 * improved pref logic for mandatory updates.
 *
 * Revision 1.95  2005/05/11 14:52:57  tony
 * Adjuste ObjectPool Clear logic.
 *
 * Revision 1.94  2005/05/09 19:00:38  tony
 * adjusted logging
 *
 * Revision 1.93  2005/05/09 18:23:35  tony
 * improved version logging.
 *
 * Revision 1.92  2005/03/31 17:42:44  tony
 * adjusted versioning logic
 *
 * Revision 1.91  2005/03/15 20:01:23  tony
 * changed and adjusted method name
 *
 * Revision 1.90  2005/03/15 17:03:31  tony
 * updated versioning logic
 *
 * Revision 1.89  2005/03/11 23:30:39  tony
 * adjusted middleware versioning logic
 *
 * Revision 1.88  2005/03/10 18:59:26  tony
 * added middleware versioning dump.
 * This will assist in troubleshooting incompatibility issues.
 *
 * Revision 1.87  2005/03/08 20:33:54  tony
 * added updateFlagCodes logic
 *
 * Revision 1.86  2005/03/03 21:46:39  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.85  2005/02/23 17:50:35  tony
 * 7342
 *
 * Revision 1.84  2005/02/23 17:30:30  tony
 * Change Request 7342
 *
 * Revision 1.83  2005/02/21 17:16:49  tony
 * adjusted versioning logic by separating out
 * versioning logic to improve update functionality.
 *
 * Revision 1.82  2005/02/15 20:25:37  tony
 * CR_1124045346
 *
 * Revision 1.81  2005/02/09 19:29:50  tony
 * JTest After Scout
 *
 * Revision 1.80  2005/02/09 18:55:22  tony
 * Scout Accessibility
 *
 * Revision 1.79  2005/02/08 16:33:58  tony
 * JTest Formatting Fourth pass
 *
 * Revision 1.78  2005/02/07 16:04:35  tony
 * Fixed JTest Formatting on Report launch.
 *
 * Revision 1.77  2005/02/04 16:57:40  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.76  2005/02/03 19:42:21  tony
 * JTest Third pass
 *
 * Revision 1.75  2005/02/03 16:38:51  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.74  2005/02/03 16:22:52  tony
 * JTest Second Pass
 *
 * Revision 1.73  2005/01/31 20:47:45  tony
 * JTest Second Pass
 *
 * Revision 1.72  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.71  2005/01/20 23:03:44  tony
 * added progress indicator for download per dwb
 *
 * Revision 1.70  2005/01/19 20:52:18  tony
 * altered automatic update logic to remove
 * the ability of the user to bypass the update.
 *
 * Revision 1.69  2005/01/17 21:12:08  tony
 * improved update logic
 *
 * Revision 1.68  2005/01/14 21:25:30  tony
 * added error reporting for bad token update.
 *
 * Revision 1.67  2005/01/14 19:48:25  tony
 * xpnd_action
 * improved upgrade logic
 *
 * Revision 1.66  2005/01/13 20:52:47  tony
 * removed invalid reference
 *
 * Revision 1.65  2004/12/14 23:32:42  tony
 * TIR USRO-R-JSTT-67HTKR
 *
 * Revision 1.64  2004/12/09 22:07:34  tony
 * improved functionality
 *
 * Revision 1.63  2004/11/19 18:01:49  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.62  2004/11/18 18:03:23  tony
 * adjusted key logic based on middleware modifications.
 *
 * Revision 1.61  2004/11/16 17:28:59  tony
 * USRO-R-DWES-66MV6T
 *
 * Revision 1.60  2004/11/15 22:59:40  tony
 * *** empty log message ***
 *
 * Revision 1.59  2004/11/11 23:43:07  tony
 * sort
 *
 * Revision 1.58  2004/11/03 23:51:25  tony
 * USRO-R-DMKR-66CHMM
 * Bala sort
 *
 * Revision 1.57  2004/11/02 20:26:16  tony
 * added admin message logic for sametime client.
 *
 * Revision 1.56  2004/10/28 20:08:20  tony
 * adjusted sametime place creation.
 *
 * Revision 1.55  2004/10/27 20:15:36  tony
 * improved updater by adding the ability to automatically
 * restart the e-announce application that was running.
 *
 * Revision 1.54  2004/10/26 22:37:49  tony
 * improved logging
 *
 * Revision 1.53  2004/10/22 22:33:16  tony
 * removed debug statements
 *
 * Revision 1.52  2004/10/22 22:15:30  tony
 * auto_sort/size
 *
 * Revision 1.51  2004/10/21 21:29:34  tony
 * updated sametime chatting functionality.
 *
 * Revision 1.50  2004/10/18 20:23:02  tony
 * adjusted logic to always update with current jar version.
 *
 * Revision 1.49  2004/10/14 21:27:06  tony
 * added ability to copy backups of existing updated items.
 *
 * Revision 1.48  2004/10/14 17:37:38  tony
 * improved software update put and get logic.
 *
 * Revision 1.47  2004/10/13 21:40:53  tony
 * update wrap-up
 *
 * Revision 1.46  2004/10/13 19:31:34  tony
 * added capability for autoDetectUpdate preference and
 * corresponding logic to support the function.
 *
 * Revision 1.45  2004/10/13 17:56:17  tony
 * added on-line update functionality.
 *
 * Revision 1.44  2004/10/12 18:16:44  tony
 * improved update functionality
 *
 * Revision 1.43  2004/10/11 21:01:28  tony
 * updated linktype functionality to bring in line
 * with preferences functionality
 *
 * Revision 1.42  2004/10/09 17:41:17  tony
 * improved debugging functionality.
 *
 * Revision 1.41  2004/09/09 19:42:43  tony
 * DWB 20040908
 * adjusted logic for 3.0a functionality.
 *
 * Revision 1.40  2004/08/26 16:26:35  tony
 * accessibility enhancement.  Adjusted menu and toolbar
 * appearance for the accessibility impaired.  Improved
 * accessibility performance by enabling preference toggling.
 *
 * Revision 1.39  2004/08/25 16:24:39  tony
 * updated location chooser logic to enhance functionality.
 *
 * Revision 1.38  2004/08/23 21:38:08  tony
 * TIR USRO-R-RLON-645P76
 *
 * Revision 1.37  2004/08/19 15:12:05  tony
 * xl8r
 *
 * Revision 1.36  2004/08/04 17:49:13  tony
 * adjusted logic to break function parameterization into
 * arm files and a new function directory.  This way
 * we will eliminate the possibility of translation to
 * accidentally change functionality.
 *
 * Revision 1.35  2004/08/03 17:58:22  tony
 * changeGroup enhancement
 *
 * Revision 1.34  2004/08/02 21:39:24  tony
 * Change Group modification.
 * update of logic for sametime client.
 * adjusted existing chat logic.
 *
 * Revision 1.33  2004/07/15 17:39:33  tony
 * USRO-R-JTOY-62WKFV
 *
 * Revision 1.32  2004/07/12 21:16:31  tony
 * MN_20005489
 *
 * Revision 1.31  2004/06/24 18:29:18  tony
 * parentless search
 *
 * Revision 1.30  2004/06/24 17:55:27  tony
 * parentless search capability added.
 *
 * Revision 1.29  2004/06/17 18:58:57  tony
 * cr_4215 cr0313024215
 *
 * Revision 1.28  2004/06/10 20:53:17  tony
 * improved location chooser functionality.
 *
 * Revision 1.27  2004/06/09 15:48:51  tony
 * location chooser added to application.  It is controlled by
 * a boolean parameter (LOCATION_CHOOSER_ENABLED)
 * in eAccessConstants.
 *
 * Revision 1.26  2004/05/27 16:57:16  tony
 * simplified version logic.
 *
 * Revision 1.25  2004/05/25 22:48:17  tony
 * 5ZBTCQ
 *
 * Revision 1.24  2004/05/25 21:36:19  tony
 * imporved logic
 *
 * Revision 1.23  2004/05/25 17:26:06  tony
 * buildDate computation fix for foreign builds.
 *
 * Revision 1.22  2004/05/24 21:48:54  tony
 * cr_ActChain
 *
 * Revision 1.21  2004/05/20 14:46:55  tony
 * adjusted release candidate display.
 *
 * Revision 1.20  2004/05/19 15:12:40  tony
 * updated showInput logic.
 *
 * Revision 1.19  2004/05/18 17:36:41  tony
 *  acl_20040518
 *  improved showInput functionality.
 *
 * Revision 1.18  2004/05/18 14:40:14  tony
 * updated versioning logic.
 *
 * Revision 1.17  2004/04/28 15:04:14  tony
 * MN_18928920
 *
 * Revision 1.16  2004/04/22 18:03:02  tony
 * updated remoter server/chat functionality.
 * improved capability added autoDetect binding
 * to determine if the client is behind a firewall.
 * It is not currently implemented however.
 *
 * Revision 1.15  2004/04/07 21:11:08  tony
 * enhanced shellControl functionality.
 *
 * Revision 1.14  2004/03/31 20:40:45  tony
 * 53771:600C35
 *
 * Revision 1.13  2004/03/24 20:17:11  tony
 * accessibility
 *
 * Revision 1.12  2004/03/24 20:09:39  tony
 * accessibility
 *
 * Revision 1.11  2004/03/12 18:58:42  tony
 * added invalid token update.
 *
 * Revision 1.10  2004/03/04 16:58:57  tony
 * added icon support
 *
 * Revision 1.9  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.8  2004/03/02 21:01:12  tony
 * updated getNow logic.  should now call only once.
 *
 * Revision 1.7  2004/02/27 18:57:11  tony
 * added reconnectDatabase functionality
 *
 * Revision 1.6  2004/02/24 18:01:31  tony
 * e-announce13 send tab
 *
 * Revision 1.5  2004/02/23 21:30:52  tony
 * e-announce13
 *
 * Revision 1.4  2004/02/20 22:17:58  tony
 * chatAction
 *
 * Revision 1.3  2004/02/20 20:15:34  tony
 * e-announce1.3
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 16:59:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.153  2004/01/20 19:25:51  tony
 * added password logic to the log file.
 *
 * Revision 1.152  2004/01/19 22:40:10  tony
 * dictionary.name update.
 *
 * Revision 1.151  2004/01/16 23:25:22  tony
 * 53562
 *
 * Revision 1.150  2004/01/09 00:42:43  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.149  2003/12/22 19:17:35  tony
 * 53437
 *
 * Revision 1.148  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.147  2003/12/11 22:30:31  tony
 * cr_3274
 *
 * Revision 1.146  2003/12/11 16:45:02  tony
 * acl_20031211
 *
 * Revision 1.145  2003/12/09 20:01:50  tony
 * reporting update
 *
 * Revision 1.144  2003/12/08 21:56:49  tony
 * cr_3274
 *
 * Revision 1.143  2003/12/01 17:46:08  tony
 * accessibility
 *
 * Revision 1.142  2003/11/25 22:05:07  tony
 * accessibility enhancement.
 *
 * Revision 1.141  2003/11/20 19:59:06  tony
 * added testing logic.
 *
 * Revision 1.140  2003/11/05 17:12:03  tony
 * cr_persistant_cart
 * Change Request to make cart persistant across tabs.
 * This is the first pass at addressing the issue.  It is not
 * currently implemented, but all of the source code is in
 * place and ready for test.
 * To implement this change navigate needs to use the new
 * navCartDialog instead of the existing navCart.  There are
 * two flavors... navCartTab, is similar to the current
 * implementation and navCartProfile is a cross tab
 * implementation.
 *
 * Revision 1.139  2003/10/31 17:31:30  tony
 * fixed variable definitions
 *
 * Revision 1.138  2003/10/30 19:04:51  tony
 * 52796
 * 52797
 *
 * Revision 1.137  2003/10/29 19:10:40  tony
 * acl_20031029
 *
 * Revision 1.136  2003/10/29 00:22:47  tony
 * removed System.out. statements.
 *
 * Revision 1.135  2003/10/23 22:27:02  tony
 * 52682
 *
 * Revision 1.134  2003/10/20 23:21:41  tony
 * 52531
 *
 * Revision 1.133  2003/10/15 18:10:04  tony
 * 52488
 *
 * Revision 1.132  2003/10/14 17:25:49  tony
 * 52548
 *
 * Revision 1.131  2003/10/08 22:19:47  tony
 * 52493
 * created updated get message to prevent overwriting of code.
 *
 * Revision 1.130  2003/10/08 20:08:44  tony
 * 52476
 *
 * Revision 1.129  2003/10/07 21:37:50  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.128  2003/10/06 23:44:52  tony
 * vb.script update, changed logic from a date based
 * file to a random file naming convention.
 *
 * Revision 1.127  2003/10/06 19:54:10  tony
 * sb_20031005
 * pass in the entire profileSet instead of a new Profile set
 * with only a single profile.
 *
 * Revision 1.126  2003/10/03 20:49:54  tony
 * updated accessibility.
 *
 * Revision 1.125  2003/09/30 16:32:22  tony
 * kehrli_20030929 --
 * logic enhancement to improve modification
 * ability of tab icon, title, tip, and menu items for
 * future modifications.
 *
 * Revision 1.124  2003/09/29 17:20:01  tony
 * 52439
 *
 * Revision 1.123  2003/09/23 16:26:41  tony
 * 52350
 *
 * Revision 1.122  2003/09/16 18:19:53  tony
 * 52275
 *
 * Revision 1.121  2003/09/15 20:51:31  tony
 * 52256
 *
 * Revision 1.120  2003/09/11 22:32:49  tony
 * preference for bookmark filter
 *
 * Revision 1.119  2003/09/11 21:59:44  tony
 * bookmark_filter
 *
 * Revision 1.118  2003/09/05 22:08:01  tony
 * 51965
 *
 * Revision 1.117  2003/09/05 16:03:01  tony
 * acl_20030905 added automatic garbage collection
 * preference to the application.
 *
 * Revision 1.116  2003/09/04 17:11:21  tony
 * 52051
 *
 * Revision 1.115  2003/09/03 20:13:19  tony
 * 52013
 *
 * Revision 1.114  2003/09/02 15:52:45  tony
 * 52013
 *
 * Revision 1.113  2003/08/28 22:55:51  tony
 * memory enhancements
 *
 * Revision 1.112  2003/08/28 18:36:01  tony
 * 51975
 *
 * Revision 1.111  2003/08/27 19:53:01  tony
 * 51967
 *
 * Revision 1.110  2003/08/25 21:27:11  tony
 * 51922
 *
 * Revision 1.109  2003/08/25 14:36:20  tony
 * 51815
 *
 * Revision 1.108  2003/08/21 22:19:03  tony
 * dwb_20030821
 *
 * Revision 1.107  2003/08/21 19:43:09  tony
 * 51391
 *
 * Revision 1.106  2003/08/21 18:12:50  tony
 * cleaned-up logic
 *
 * Revision 1.105  2003/08/21 15:56:35  tony
 * fixed dialog reuse issue.
 *
 * Revision 1.104  2003/08/20 19:09:25  tony
 * update peer_create to allow for standalone create to
 * match peer_create.
 *
 * Revision 1.103  2003/07/29 21:34:32  tony
 * updated form functionality.
 *
 * Revision 1.102  2003/07/29 16:58:29  tony
 * 51555
 *
 * Revision 1.101  2003/07/23 20:41:56  tony
 * added and enhanced restore logic
 *
 * Revision 1.100  2003/07/22 23:28:38  tony
 * repaired null pointer
 *
 * Revision 1.99  2003/07/17 22:37:21  tony
 * updated logic fo peer create.
 *
 * Revision 1.98  2003/07/16 21:06:48  tony
 * report url update
 *
 * Revision 1.97  2003/07/15 19:38:14  tony
 * updated logfile replay.
 *
 * Revision 1.96  2003/07/15 18:52:17  tony
 * improved reporting logic to use vbscript to eliminate browser
 * elements that were deemed unnecessary.
 *
 * Revision 1.95  2003/07/11 17:00:52  tony
 * added verbose method to display memory usage.
 *
 * Revision 1.94  2003/07/07 15:26:20  tony
 * updated logic per Wayne to only report 0 errors for
 * spell check when manually invoked.
 *
 * Revision 1.93  2003/07/03 16:38:03  tony
 * improved scripting logic.
 *
 * Revision 1.92  2003/07/03 00:41:40  tony
 * updated event logging so log parser can directly parse
 * the log file.
 *
 * Revision 1.91  2003/07/02 21:42:29  tony
 * updated logging to allow parsing of log file to
 * auto navigate development to the proper location
 * via the proper path.
 *
 * Revision 1.90  2003/07/02 16:43:23  tony
 * updated logging capabilities to allow for play back of
 * log files.
 *
 * Revision 1.89  2003/06/30 21:21:45  tony
 * improved logic and functionality.
 *
 * Revision 1.88  2003/06/30 18:07:21  tony
 * improved functionality for testing by adding functionality
 * to MWObject that includes the default of the userName and
 * overwrite.properties file to use.  This will give the test
 * team more flexibility in defining properties for the application.
 *
 * Revision 1.87  2003/06/27 20:07:24  tony
 * 51334
 *
 * Revision 1.86  2003/06/27 15:35:19  tony
 * 51334
 *
 * Revision 1.85  2003/06/26 16:46:04  tony
 * updated cipher logic.
 *
 * Revision 1.84  2003/06/25 23:49:13  tony
 * added eCipher which will encrypt Strings.  This will allow
 * for safe replaying of passwords.
 *
 * Revision 1.83  2003/06/25 16:19:18  tony
 * 51325
 *
 * Revision 1.82  2003/06/20 22:36:14  tony
 * 51325
 *
 * Revision 1.81  2003/06/19 20:00:07  joan
 * work on PDGInfo panel
 *
 * Revision 1.80  2003/06/19 18:31:32  tony
 * 51298
 *
 * Revision 1.79  2003/06/19 16:09:42  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.78  2003/06/16 17:22:53  tony
 * updated logic for 1.2h to allow for the application to
 * automatically select the visible navigate as current when
 * the navigate splitpane is adjusted to its minimum or
 * its maximum.
 *
 * Revision 1.77  2003/06/10 16:46:46  tony
 * 51260
 *
 * Revision 1.76  2003/06/06 19:14:37  tony
 * trap exception
 *
 * Revision 1.75  2003/06/05 20:15:22  tony
 * 51169
 *
 * Revision 1.74  2003/06/04 16:09:36  tony
 * 51038
 *
 * Revision 1.73  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.72  2003/05/29 22:27:44  tony
 * adjusted tunnel based on middleware update.
 *
 * Revision 1.71  2003/05/29 21:30:57  tony
 * updated errorMessaging on report launch.
 *
 * Revision 1.70  2003/05/29 21:20:45  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.69  2003/05/29 20:44:39  tony
 * added tunneling logic
 *
 * Revision 1.68  2003/05/29 19:05:20  tony
 * updated report launching.
 *
 * Revision 1.67  2003/05/28 17:04:33  tony
 * updated eSwingworker.
 *
 * Revision 1.66  2003/05/28 14:42:03  tony
 * updated date logic for testing purposes
 *
 * Revision 1.65  2003/05/27 21:19:21  tony
 * added tunneling data
 *
 * Revision 1.64  2003/05/22 16:23:12  tony
 * 50874 -- filter, find, and sort adjust the object they
 * function on dynamically.
 *
 * Revision 1.63  2003/05/21 15:54:55  tony
 * 24209
 *
 * Revision 1.62  2003/05/21 15:52:17  tony
 * 24209
 *
 * Revision 1.61  2003/05/21 15:47:23  tony
 * 24209
 *
 * Revision 1.60  2003/05/20 23:47:16  tony
 * stubbed out 24209
 *
 * Revision 1.59  2003/05/19 22:05:34  tony
 * upgraded to latest release of jspell
 *
 * Revision 1.58  2003/05/15 23:13:26  tony
 * updated functionality improved logic for next and
 * previous tab.
 *
 * Revision 1.57  2003/05/15 16:26:33  joan
 * work on pop up dialog when editing PDG
 *
 * Revision 1.56  2003/05/13 16:05:40  tony
 * 50621
 *
 * Revision 1.55  2003/05/09 14:57:15  tony
 * 50525
 *
 * Revision 1.54  2003/05/08 19:17:53  tony
 * 50445
 *
 * Revision 1.53  2003/05/06 18:59:22  joan
 * 50530
 *
 * Revision 1.52  2003/05/02 20:05:53  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.51  2003/05/02 17:53:43  tony
 * 50494
 *
 * Revision 1.50  2003/05/01 22:41:34  tony
 * added static borders to address border rendering
 * issues on found.
 *
 * Revision 1.49  2003/04/29 17:20:46  tony
 * added XMLEditor and panel pass thrus
 *
 * Revision 1.48  2003/04/25 20:37:32  tony
 * adjusted orderTable capabilities to allow for
 * update of column order before login is complete.
 *
 * Revision 1.47  2003/04/22 16:37:04  tony
 * created MWChooser to update handling of default
 * middlewareLocation.
 *
 * Revision 1.46  2003/04/21 22:14:47  tony
 * updated default logic to allow for parent and child
 * color defaults.  Updated the sort on the whereUsed table.
 *
 * Revision 1.45  2003/04/21 17:30:17  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.44  2003/04/18 20:10:30  tony
 * added tab placement to preferences.
 *
 * Revision 1.43  2003/04/16 22:42:16  tony
 * adjusted copy logic.
 *
 * Revision 1.42  2003/04/16 17:22:41  tony
 * added eTransferHandleLogic
 *
 * Revision 1.41  2003/04/14 21:55:32  joan
 * 50362
 *
 * Revision 1.40  2003/04/11 17:31:34  tony
 * updated logic so that eResource will
 * compile with the rest of the application.
 *
 * Revision 1.39  2003/04/10 20:06:23  tony
 * updated logic to allow for dialogs to properly
 * eminiate from the dialogs parent.
 *
 * Revision 1.38  2003/04/10 19:50:32  joan
 * fix feedback
 *
 * Revision 1.37  2003/04/09 22:50:04  tony
 * adjsuted logic on ColumnOrder so that the preference
 * will always appear.
 *
 * Revision 1.36  2003/04/09 22:16:38  tony
 * added getObject method.
 *
 * Revision 1.35  2003/04/03 23:48:12  tony
 * updated default profile logic.
 *
 * Revision 1.34  2003/04/03 22:28:20  tony
 * column order needed to be based on selected profile.
 * added profile selector and improved logic.
 *
 * Revision 1.33  2003/04/03 18:50:00  tony
 * adjusted logic to display individualized icon
 * for each frameDialog.
 *
 * Revision 1.32  2003/04/03 16:19:06  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.31  2003/04/02 20:49:39  tony
 * updated profile handling to improve performance and
 * implement tagging.
 *
 * Revision 1.30  2003/04/02 19:53:37  tony
 * adjusted logic.  Everytime a new tab is launched the
 * system must grab a new instance of the profile.
 * This will aid in session tagging.
 *
 * Revision 1.29  2003/04/02 17:58:17  tony
 * 50331
 *
 * Revision 1.28  2003/03/28 18:53:01  tony
 * updated logic for tagging, when user elects to run
 * multiple tabs for a single profile we have to generate
 * a new instance of the profile.
 *
 * Revision 1.27  2003/03/28 16:58:38  tony
 * Switched the XMLEditor display Component
 * from a Dialog to a frame.
 *
 * Revision 1.26  2003/03/28 00:37:23  tony
 * trapped jspell local dictionary null pointer.
 * If a local dictionary does not exist it is still for
 * some reason set to ready.
 *
 * Revision 1.25  2003/03/27 23:15:44  tony
 * added eannounce.trace
 *
 * Revision 1.24  2003/03/27 22:49:20  tony
 * improved tracking logic.
 *
 * Revision 1.23  2003/03/27 21:02:53  tony
 * improved functionality and tracking.
 *
 * Revision 1.22  2003/03/27 18:34:06  tony
 * improved performance tracking.
 *
 * Revision 1.21  2003/03/27 16:21:32  tony
 * added profile.getNewInstance, and moved dictioary to resource
 *
 * Revision 1.20  2003/03/24 23:46:25  tony
 * workgroup reset.
 * adjusted logic on eComboBoxUI
 * accessibility panel enhancements.
 *
 * Revision 1.19  2003/03/24 21:52:22  tony
 * added modalCursor logic.
 *
 * Revision 1.18  2003/03/21 18:12:20  tony
 * refined preferences logic.
 *
 * Revision 1.17  2003/03/20 23:59:35  tony
 * column order moved to preferences.
 * preferences refined.
 * Change History updated.
 * Default Column Order Stubs added
 *
 * Revision 1.16  2003/03/20 18:12:31  tony
 * stickpin and bookmarking.
 *
 * Revision 1.15  2003/03/20 01:57:07  tony
 * bookmarking and pinning added to the system.
 *
 * Revision 1.14  2003/03/20 01:03:51  joan
 * add PDG Action item
 *
 * Revision 1.13  2003/03/19 20:35:02  tony
 * addition of bookmarking logic
 *
 * Revision 1.12  2003/03/18 22:39:09  tony
 * more accessibility updates.
 *
 * Revision 1.11  2003/03/17 23:44:36  tony
 * adjust eStatus.component placement logic.
 *
 * Revision 1.10  2003/03/13 21:16:31  tony
 * adjusted disposeDialog and enhance modalDialog.
 *
 * Revision 1.9  2003/03/13 18:38:42  tony
 * accessibility and column Order.
 *
 * Revision 1.8  2003/03/12 23:51:08  tony
 * accessibility and column order
 *
 * Revision 1.7  2003/03/11 00:33:22  tony
 * accessibility changes
 *
 * Revision 1.6  2003/03/07 21:40:45  tony
 * Accessibility update
 *
 * Revision 1.5  2003/03/05 19:16:48  tony
 * AttributeChangeHistoryGroup addition.
 *
 * Revision 1.4  2003/03/05 18:54:23  tony
 * accessibility updates.
 *
 * Revision 1.3  2003/03/04 22:34:48  tony
 * *** empty log message ***
 *
 * Revision 1.2  2003/03/04 16:54:04  tony
 * adjusted logic for Wizard and picklist update to allow
 * multiple link action selection. per cc
 *
 * Revision 1.1.1.1  2003/03/03 18:03:39  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;

import com.ibm.eannounce.dialogpanels.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
//import com.ibm.eannounce.editor.*;
import com.ibm.transform.oim.eacm.xml.editor.*;
import com.ibm.eannounce.eforms.navigate.*;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.epanels.ENavForm;
//import com.ibm.eannounce.eChat.*;
import com.ibm.eannounce.eserver.*;
import com.ibm.eannounce.progress.EProgress;
import com.ibm.eannounce.sametime.*;
import com.ibm.eannounce.version.Version;
import com.wallstreetwise.app.jspell.domain.*;
import com.wallstreetwise.app.jspell.gui.*;
import com.wallstreetwise.app.jspell.domain.net.*;
import com.wallstreetwise.core.util.SynchronizedLock;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.ImageIcon;
import java.net.*;
import java.util.zip.*;
import com.ibm.eannounce.eforms.edit.*;
/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EAccess implements EAccessConstants, ActionListener {
    private String errorCode = null;
    private String title = null;
    private String msg = null;
    private String[] parm = null;

    private ResourceBundle packagedBundle = null;
    //	private ResourceBundle overwriteBundle = null;
    private PropertyResourceBundle overwriteBundle = null;

    private EGraphics eGraph = new EGraphics();
    private SerialPref sPref = SerialPref.readFile();
    private MWParser mwParse = null;

    //	private modalDialog xmlfDialog = null;
    private FrameDialog xmlfDialog = null;

    private Vector md = null;
    private Vector fd = null;

    private static EAccess eaccess = null;
    private static Hashtable uiInstanceTbl = new Hashtable();
    
    /************************************
     * Enforce a single instance of the JUI unless 'multipleUI.arm' exists
     * @param _c
     * @param sUserName
     * @param location
     * @return
     */
    public static boolean isAlreadyRunning(Component _c,String sUserName, String location){
    	boolean isrunning = false;
    	if (EAccess.isArmed(MULTIPLE_UI_ARM_FILE)) {
			// allow multiple instances of UI to run
    		appendLog("EAccess.isAlreadyRunning bypassing UI instance checks");
    	}else{
    		String uiInstance = UILock.getLockFileName(sUserName, location);
    		UILock ua = new UILock(uiInstance);
    		isrunning = ua.isAppActive();
    		if (isrunning) {
    			eaccess().showError(_c,"msg5026.0");
    		}else{
    			uiInstanceTbl.put(uiInstance, ua);
    		}
		}
       
    	return isrunning;
    }
    /************************************
     * Release the lock, needed if login fails
     * @param sUserName
     * @param location
     */
    public static void freeUILock(String sUserName, String location){
    	String uiInstance = UILock.getLockFileName(sUserName, location);
		UILock ua = (UILock)uiInstanceTbl.get(uiInstance);
		if (ua!=null){
			ua.forceRelease();
		}
    }

    /**
     * tBase
     */
    private ThinBase tBase = null;
    public ThinBase getTBase() { return tBase;}
    /**
     * dBase
     */
    private DataBase dBase = null;

    private boolean bModalBusy = false;
    private boolean bBusy = false;
//    private boolean bPast = false;

    private int iBlob = -1;

    private DateAdjust dAdjust = null;
    /**
     * gIO
     */
    public Gio gio = null;
    private HTMLGrabber hGrabber = new HTMLGrabber();

    private ELogin parent = null;

    private ShellControl sControl = null;
//    private EannounceSort eaSort = null;

    //	private Date dateNow = null;
    private Timer tTimer = new Timer();

    private int iError = 0;
    private boolean bStop = false;
    private JSpellDictionary dictionary = null;
    private JSpellParser thrdParser = null;
    private JSpellErrorHandler thrdHandler = null;
    private JSpellParser saParser = null;
    private SynchronizedLock synchLock = null;

    //private SimpleDateFormat dateFormat = null;
    private ProfileSet profSet = null;
    //private int fileKey = 0;
    private ESwingWorker worker = null;
    private LockList lockList = null;
    private Vector vWorker = new Vector();

    /**
     * ePane
     */
    public ETabbedMenuPane ePane = null;
    private PrintUtilities print = null;
    private BookmarkPanel bMarkPnl = null;
    private DatePanel datePnl = null;
    private FilterPanel filterPnl = null;
    private MaintenancePanel maintPnl = null;		//cr_FlagUpdate
    private FindPanel findPnl = null;
    private LinkPanel linkPnl = null;
    private MessagePanel messagePnl = null;
    private MWChooser mwChoose = null;
    private NumberPanel numberPnl = null;
    private PrefPanel prefPnl = null;
    private RelatorPanel relatorPnl = null;
    private ScrollPanel scrollPnl = null;
    private SearchPanel searchPnl = null;
    private SortPanel sortPnl = null;
    private TablePanel tablePnl = null;
    private WizardPanel wizardPnl = null;
    private XMLPanel xmlPnl = null;
    private JFileChooser chooser = null;
    private RemoteControl remote = null;
    private EProgress progressMonitor = null;

	private Hashtable lockownerTbl = new Hashtable(); // vastly improve performance


    //deprecated	private eChatLocal locChat = null;
    private AbstractClientUI stClient = null;
    private static EditPDGPanel editPDGPnl = null;
    private static NavPDGPanel navPDGPnl = null;
    private static PDGInfoPanel infoPDGPnl = null; //infoPDG
    private EColorChooser colorChoose = null;
    private ETransHandler transHand = null;

    /**
     * FOUND_FOCUS_BORDER
     */
    public static ELineBorder FOUND_FOCUS_BORDER = new ELineBorder(PREF_COLOR_FOUND_FOCUS, DEFAULT_COLOR_FOUND_FOCUS, 1);
    /**
     * FOUND_BORDER
     */
    public static ELineBorder FOUND_BORDER = new ELineBorder(PREF_COLOR_FOUND, DEFAULT_COLOR_FOUND, 1);

    private boolean bOffline = true;
    private Profile actProf = null;

    private boolean m_bPDGOn = false; //50530
    private boolean bAutoGC = true; //mem

    //	public  boolean ACCESSIBLE_ENABLED		= false;		//acl_20031029
    //	public  boolean REPLAYABLE_LOGFILE		= false;		//acl_20031029
    //	public  boolean VERT_FLAG_FRAME			= false;		//acl_20031029

    private PersistantCart cart = null; //cr_persistant_cart
    private Cursor CHANGE_GROUP_CURSOR = null;
    //private Cursor WORKER_CURSOR = null;

    private boolean bUpdateable = true;
    private boolean bCheckForUpdate = true;
    private EAccess() {
        getPackagedBundle("eAnnounce");
        getOverwriteBundle("default_local.properties");
        //		setRedirect(getString("resource.class"));
        setRedirect(new com.ibm.eannounce.eresource.EResource().getClass());
        loadCursors();
    }

    private void loadCursors() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        try {
            CHANGE_GROUP_CURSOR = createCustomCursor(kit, "change.cursor");
            //WORKER_CURSOR = createCustomCursor(kit, "worker.cursor");
        } catch (IndexOutOfBoundsException _iob) {
            _iob.printStackTrace();
        }
    }

    private Cursor createCustomCursor(Toolkit _kit, String _key) throws IndexOutOfBoundsException {
        return _kit.createCustomCursor(getImage(getString(_key)), new Point(getInt(_key + ".x"), getInt(_key + ".y")), _key);
    }

    private void getPackagedBundle(String _code) {
        if (isUndefined(_code)) {
            return;
        }
        try {
            packagedBundle = ResourceBundle.getBundle(_code);
        } catch (MissingResourceException _mre) {
            _mre.printStackTrace();
        }
    }

    /**
     * getOverwriteBundle
     *
     * @author Anthony C. Liberto
     * @param _code
     */
    private void getOverwriteBundle(String _code) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(RESOURCE_DIRECTORY + _code);
            overwriteBundle = new PropertyResourceBundle(fin);
            //			overwriteBundle = ResourceBundle.getBundle(_code);
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
    }

    /**
     * setFileKey
     *
     * @author Anthony C. Liberto
     * @param _i
     * /
    public void setFileKey(int _i) {
        fileKey = _i;
        return;
    }

    /**
     * getFileKey
     *
     * @author Anthony C. Liberto
     * @return
     * /
    public int getFileKey() {
        return fileKey;
    }

    /**
     * incrementFileKey
     *
     * @author Anthony C. Liberto
     * /
    public void incrementFileKey() {
        ++fileKey;
        return;
    }*/

    /**
     * setRedirect
     *
     * @author Anthony C. Liberto
     * @param _class
     * /
    public void setRedirect(String _class) {
        eGraph.setRedirect(_class);
        hGrabber.setRedirect(_class);
    }*/

    /**
     * setRedirect
     *
     * @author Anthony C. Liberto
     * @param _class
     */
    private void setRedirect(Class _class) {
        eGraph.setRedirect(_class);
        hGrabber.setRedirect(_class);
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     * @param _parent
     */
    protected void init(ELogin _parent) {
        parent = _parent;
        setAutoGC(getPrefBoolean(PREF_AUTO_GC, DEFAULT_AUTO_GC));
        print = new PrintUtilities(true);
        fd = new Vector();
        md = new Vector();
        xmlfDialog = new FrameDialog(_parent);
        //		xmlfDialog = modalDialog.createDialog(_parent);
        messagePnl = new MessagePanel();
        loadDictionary();
        dAdjust = new DateAdjust();
        sControl = new ShellControl();
        //dateFormat = new SimpleDateFormat();
        datePnl = new DatePanel();
        filterPnl = new FilterPanel();
        maintPnl = new MaintenancePanel();		//cr_FlagUpdate
        findPnl = new FindPanel();
        linkPnl = new LinkPanel();
        numberPnl = new NumberPanel();
        relatorPnl = new RelatorPanel();
        scrollPnl = new ScrollPanel();
        searchPnl = new SearchPanel();
        prefPnl = new PrefPanel();
        searchPnl.init();
        sortPnl = new SortPanel();
        tablePnl = new TablePanel();
        wizardPnl = new WizardPanel();
        xmlPnl = new XMLPanel(xmlfDialog.getRootPane(), dictionary);
        ePane = new ETabbedMenuPane();
        editPDGPnl = new EditPDGPanel();
        editPDGPnl.init();
        navPDGPnl = new NavPDGPanel();
        navPDGPnl.init();
        infoPDGPnl = new PDGInfoPanel(); //infopdg
        FOUND_FOCUS_BORDER = new ELineBorder(PREF_COLOR_FOUND_FOCUS, DEFAULT_COLOR_FOUND_FOCUS, 1);
        FOUND_BORDER = new ELineBorder(PREF_COLOR_FOUND, DEFAULT_COLOR_FOUND, 1);
    }

    /**
     * print
     *
     * @author Anthony C. Liberto
     * @param _c
     */
    public void print(Component _c) {
        print.print(_c, getPrefDouble(PREF_PRINT_RATIO, PRINT_DEFAULT_RATIO), getPrintScaleType(), getPrefInt(PREF_PRINT_ORIENTATION, PRINT_DEFAULT_ORIENTATION));
    }

    private int getPrintScaleType() {
        boolean bX = getPrefBoolean(PREF_PRINT_SCALE_X, false);
        boolean bY = getPrefBoolean(PREF_PRINT_SCALE_Y, false);
        if (bX && bY) {
            return SCALE_ALL;
        } else if (bX) {
            return SCALE_X;
        } else if (bY) {
            return SCALE_Y;
        }
        return SCALE_CUSTOM;
    }

    /**
     * pageSetup
     *
     * @author Anthony C. Liberto
     */
    public void pageSetup() {
        print.adjustPageFormat(getPrefInt(PREF_PRINT_ORIENTATION, PRINT_DEFAULT_ORIENTATION));
    }

    /**
     * printReset
     *
     * @author Anthony C. Liberto
     */
    public void printReset() { //50331
        print.reset(); //50331
    } //50331

    /**
     * launchURL
     *
     * @author Anthony C. Liberto
     * @param _url
     */
    public void launchURL(String _url) {
        sControl.launchURL(_url);
    }

    /**
     * launchReport
     *
     * @author Anthony C. Liberto
     * @param _rai
     * @param _ei
     */
	public void launchReport(ReportActionItem _rai, EntityItem[] _ei) {
		Profile prof = getActiveProfile();
		ReportActionItem rai = null;
		EntityItem[] ei = null;
		appendLog("EAccess.launchReport running report: " + _rai.getURL());
		try {
			rai = new ReportActionItem(null,_rai);
		} catch (MiddlewareException _me) {
			_me.printStackTrace();
		}

		if (rai == null) {
			appendLog("EAccess.launchReport ERROR null rai");
			reportError(_rai);
			return;
		}

		ei = EntityList.clipShift(_rai,_ei);
		if (ei != null) {
			appendLog("EAccess.launchReport "+dBase().getEntityItems("reportEntityItems",ei)+"\"");
			String tranid = processReportInput(prof,rai,ei);
			if (tranid!=null){
				processReport(tranid,prof,rai);
			}else{
				reportError(_rai);
			}
			//rai.dereference(); action.deref was releasing shared objs, avoid it here for now
		}else{
			appendLog("EAccess.launchReport ERROR null EntityItem array");
			reportError(_rai);
		}
	}

	/**
	* Store profile, report action and entityitem info as a blob, getting a trans id to use later to execute rpt
	*/
	private String processReportInput(Profile _prof,ReportActionItem _rai, EntityItem[] _ei) {
		JuiReportActionObject report = new JuiReportActionObject(setActiveProfile(profSet,_prof),_rai,_ei);
		URL url = null;
		URLConnection connection = null;
		OutputStream os = null;
		DeflaterOutputStream zos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		String strTranid = null;
		try {
			String rptUrl = System.getProperty(REPORT_PREFIX);
			if (!rptUrl.endsWith("/")){
				rptUrl+="/";
				System.setProperty(REPORT_PREFIX, rptUrl);
			}
			String strReportURL = rptUrl + TUNNEL_SET_SERVLET;
			System.out.println("EAccess.processReportInput report connection: " + strReportURL);
			url = new URL(strReportURL);
			connection = url.openConnection();
			connection.setRequestProperty("Content-Type","application/x-java-serialized-object");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			os = connection.getOutputStream();
			zos = new DeflaterOutputStream(os);
			bos = new BufferedOutputStream(zos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(report);
			oos.reset();
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
		} finally {
			try {
				if (oos !=  null) {
					oos.flush();
					oos.close();
				}

				if (connection != null) {
					strTranid = processReportOutput(connection);
					connection = null;
				}else{
					appendLog("EAccess.processReportInput ERROR null connection");
				}

				url = null;
				if (bos != null) {
					bos.flush();
					bos.close();
				}
				if (zos != null) {
					zos.flush();
					zos.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}

		return strTranid;
	}

	private String processReportOutput(URLConnection _connection) {
		InputStream is = null;
		InflaterInputStream zis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		String sTranid = null;
		try {
			_connection.connect();
			is = _connection.getInputStream();
			zis = new InflaterInputStream(is);
			bis = new BufferedInputStream(zis);
			ois = new ObjectInputStream(bis);
			try {
				sTranid = (String)ois.readObject();
			} catch (ClassNotFoundException _cnf) {
				_cnf.printStackTrace();
			}
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (zis != null) {
					zis.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
		return sTranid;
	}

	private void processReport(String _tranid, Profile _prof, ReportActionItem _rai) {
		if (_tranid != null) {
			try {
				String[] parms = new String[2];
				parms[0] = URLEncoder.encode(_tranid,ENCODE_TYPE);
				parms[1] = _prof.getEnterprise();
				String sTunnel = replaceParms(TUNNEL_RUN_SERVLET,parms);
				System.out.println("EAccess.processReport : " + System.getProperty(REPORT_PREFIX) + sTunnel);
				launchURL(System.getProperty(REPORT_PREFIX) + sTunnel);
//				launchURL(System.getProperty(REPORT_PREFIX) + TUNNEL_RUN_SERVLET + URLEncoder.encode(_tranid,ENCODE_TYPE));
			} catch (UnsupportedEncodingException _uee) {
				_uee.printStackTrace();
				reportError(_rai);
			}
		} else {
			reportError(_rai);
		}
	}

	private void reportError(ReportActionItem _rai) {
		setCode("msg12005.0");
		setParmCount(2);
		setParm(0,System.getProperty(REPORT_PREFIX));
		setParm(1,_rai.getURL());
		showError(parent);
	}

    /**
     * launch
     *
     * @author Anthony C. Liberto
     * @param _command
     */
    public void launch(String _command) {
        if (sControl != null) {
	        sControl.launch(_command);
		}
    }

    /**
     * command
     *
     * @author Anthony C. Liberto
     * @param _command
     * /
    public void command(String _command) {
        if (sControl != null) {
	        sControl.command(_command);
		}
    }*/

    /**
     * shellCommand
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    protected void shellCommand(String _s) {
        if (sControl != null) {
	        sControl.shellCommand(_s, true);
		}
    }

    /*
     timer insertion
     */
    /**
     * getNow
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getNow() {
        return tTimer.getNow();
    }

    /**
     * getNow
     *
     * @author Anthony C. Liberto
     * @param _format
     * @return
     */
    public String getNow(String _format) {
        return tTimer.formatDate(_format);
    }

    /**
     * getNow
     *
     * @author Anthony C. Liberto
     * @param _format
     * @param _refreshTime
     * @return
     */
    public String getNow(String _format, boolean _refreshTime) {
        return getNow(_format);
    }

    /**
     * parseDate
     *
     * @author Anthony C. Liberto
     * @param _pattern
     * @param _date
     * @return
     */
    public Date parseDate(final String _pattern, final String _date) {
        return tTimer.parseDate(_pattern, _date);
    }

    /**
     * formatDate
     *
     * @author Anthony C. Liberto
     * @param _pattern
     * @param _date
     * @return
     */
    public String formatDate(String _pattern, Date _date) {
        return tTimer.formatDate(_pattern, _date);
    }
 
    /**
     * getLogin
     *
     * @author Anthony C. Liberto
     * @return
     */
    public ELogin getLogin() {
        return parent;
    }

    /**
     * getLinkType
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getLinkType() {
        return parent.getLinkType();
    }

    /**
     * connect
     *
     * @author Anthony C. Liberto
     * @param _c
     */
    protected void connect(Component _c) {
        String sOverwrite = null;
        tBase = new ThinBase(getMWParser());
        while (!tBase.isRunning()) {
            try {
                tBase.databaseConnect(_c);
            } catch (RemoteException _re) {
                report(_re,false);
            }
        }
        sOverwrite = System.getProperty(MW_PROPERTIES);
        if (Routines.have(sOverwrite)) {
            getOverwriteBundle(sOverwrite);
        }
        tTimer.setBase(tBase);
        verbose();
    }

    /**
     * getMWParser
     *
     * @author Anthony C. Liberto
     * @return
     */
    public MWParser getMWParser() {
        if (mwParse == null) {
            mwParse = new MWParser();
        }
        return mwParse;
    }

    /**
     * connect
     *
     * @author Anthony C. Liberto
     * @param _dBase
     */
    private void connect(DataBase _dBase) {
        if (dBase == null) {
            dBase = _dBase;
        }
    }

    /**
     * dBase
     *
     * @author Anthony C. Liberto
     * @return
     */
    public DataBase dBase() {
        if (dBase == null) {
            connect(new DataBase(tBase)); //DataBase
        }
        //		verbose();
        if (isAutoGC()) { //mem
            gc(); //mem   
        } //mem
        
        return dBase;
    }

    /**
     * getMetaEntityList
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @return
     */
    public MetaEntityList getMetaEntityList(Profile _prof) {
        if (tBase != null) {
            return tBase.getMetaEntityList(_prof, parent);
        }
        return null;
    }

    /**
     * getChangeHistory - will go downlink
     *
     * @author Anthony C. Liberto
     * @param _ei
     */
    public void getChangeHistory(EntityItem _ei) {
        EntityChangeHistoryGroup changeGroup = dBase().getChangeHistory(_ei, parent);
        if (changeGroup != null) {
            tablePnl.setTable(changeGroup, changeGroup.getChangeHistoryGroupTable());
            show(null, tablePnl, false);
        }
    }
    /**
     * getThisChangeHistory - this will get relator information, wont try to go downlink
     *
     * @param _ei
     */
    public void getThisChangeHistory(EntityItem _ei) {
        EntityChangeHistoryGroup changeGroup = dBase().getThisChangeHistory(_ei, parent);
        if (changeGroup != null) {
            tablePnl.setTable(changeGroup, changeGroup.getChangeHistoryGroupTable());
            show(null, tablePnl, false);
        }
    }
    /**
     * getChangeHistory
     *
     * @author Anthony C. Liberto
     * @param _att
     */
    public void getChangeHistory(EANAttribute _att) {
        AttributeChangeHistoryGroup changeGroup = dBase().getChangeHistory(_att, parent);
        if (changeGroup != null) {
            tablePnl.setTable(changeGroup, changeGroup.getChangeHistoryGroupTable());
            show(null, tablePnl, false);
        }
    }

    /**
     * gio
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Gio gio() {
        if (gio == null) {
            gio = new Gio();
        }
        return gio;
    }

    /**
     * cipher
     *
     * @author Anthony C. Liberto
     * @return
     * /
    public ECipher cipher() {
        return ECipher.cipher();
    }

    //cr_1210035324	public void showBookmark(EANActionItem _item) {
    /**
     * showBookmark
     *
     * @author Anthony C. Liberto
     * @param _item
     * @param _items
     */
    public void showBookmark(EANActionItem _item, EANActionItem[] _items) {
        if (bMarkPnl == null) {
            bMarkPnl = new BookmarkPanel();
        }
        bMarkPnl.setActionItem(_item);
        bMarkPnl.setActionItems(_items); //cr_1210035324
        showFrame(null, bMarkPnl);
    }

    /**
     * getEntityList
     *
     * @author Anthony C. Liberto
     * @param _ean
     * @param _ei
     * @param _c
     * @return
     */
    private EntityList getEntityList(EANActionItem _ean, EntityItem[] _ei, Component _c) {
        return dBase.getEntityList(_ean, _ei, _c);
    }

    /**
     * setProfileSet
     *
     * @author Anthony C. Liberto
     * @param _pSet
     */
    protected void setProfileSet(ProfileSet _pSet) {
        profSet = _pSet;
        prefPnl.init();
    }

    /**
     * getProfileSet
     *
     * @author Anthony C. Liberto
     * @return
     */
    public ProfileSet getProfileSet() {
        return profSet;
    }

    /**
     * setActiveProfile
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setActiveProfile(Profile _prof) {
        if (_prof != null && !_prof.equals(actProf)) {
            appendLog(getProfileInfo(_prof));
        }

        if (actProf != null && _prof != null) {			//dwb_20050510
        	String ent1 = actProf.getEnterprise();		//dwb_20050510
        	String ent2 = _prof.getEnterprise();		//dwb_20050510
        	if (ent1 != null && ent2 != null) {			//dwb_20050510
	        	if (!ent1.equals(ent2)) {				//dwb_20050510
					ObjectPool.getInstance().clear();	//dwb_20050510
				}										//dwb_20050510
			}											//dwb_20050510
		}												//dwb_20050510

        actProf = _prof;
        parent.setStatus(_prof);
    }

    /**
     * getDefaultProfile
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Profile getDefaultProfile() {
        Profile[] prof = profSet.toArray();
        if (prof != null) {
            int ii = prof.length;
            if (ii == 1 && prof[0] != null) {
                return prof[0];
            }
            if (ii > 0) {
                //51965				Object o  = getPrefObject(prof[0].getOPID() + DEFAULT_PROFILE);
                Object o = getPrefObject(prof[0].getOPName() + DEFAULT_PROFILE); //51965
                if (o != null && o instanceof Profile) {
                    Profile defaultProfile = (Profile) o;
                    for (int i = 0; i < ii; ++i) {
                        if (defaultProfile.getOPWGID() == prof[i].getOPWGID()) {
                            if (defaultProfile.getEnterprise().equals(prof[i].getEnterprise())) {
                                return prof[i];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * setDefaultProfile
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setDefaultProfile(Profile _prof) {
        if (_prof != null) { //50525
            //51965			setPrefObject(_prof.getOPID() + DEFAULT_PROFILE, _prof);
            setPrefObject(_prof.getOPName() + DEFAULT_PROFILE, _prof);
        } //50525
    }

    //52439	public void resetDefaultProfile(int _opwg) {
    //52439		if (_opwg > 0) {
    //52439			clearPref(_opwg + DEFAULT_PROFILE, true);
    /**
     * resetDefaultProfile
     *
     * @author Anthony C. Liberto
     * @param _user
     */
    public void resetDefaultProfile(String _user) { //52439
        clearPref(_user + DEFAULT_PROFILE, true); //52439
    }

    /**
     * getNewProfileInstance
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Profile getNewProfileInstance() {
        return getNewProfileInstance(getActiveProfile());
    }

    /**
     * getNewProfileInstance
     *
     * @author Anthony C. Liberto
     * @param _active
     * @return
     */
    public Profile getNewProfileInstance(Profile _active) {
        Profile prof = null;
        if (_active != null) {
            try {
                prof = _active.getNewInstance(getRemoteDatabaseInterface());
                if (prof != null) {
                    setActiveProfile(prof);
                }
            } catch (java.rmi.RemoteException _re) {
                _re.printStackTrace();
				showException(_re,null,ERROR_MESSAGE,OK); // notify user
            } catch (MiddlewareRequestException _mre) {
                _mre.printStackTrace();
				showException(_mre,null,ERROR_MESSAGE,OK);// notify user
            } catch (MiddlewareException _me) {
                _me.printStackTrace();
				showException(_me,null,ERROR_MESSAGE,OK);// notify user
            } catch (MiddlewareShutdownInProgressException _shut) {
                _shut.printStackTrace();
				showException(_shut,null,ERROR_MESSAGE,OK);// notify user
            }
        }
        return prof;
    }

    /**
     * getRemoteDatabaseInterface
     * @return RemoteDatabaseInterface
     */
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
        verbose();
        return tBase.getRemoteDatabaseInterface();
    }

    /**
     * getOPWGID
     *
     * @author Anthony C. Liberto
     * @return
     */
    public int getOPWGID() {
        Profile prof = getActiveProfile();
        if (prof != null) {
            return prof.getOPWGID();
        }
        return -1;
    }

    /**
     * getSessionID
     *
     * @author Anthony C. Liberto
     * @return
     * /
    public int getSessionID() {
        Profile prof = getActiveProfile();
        if (prof != null) {
            return prof.getSessionID();
        }
        return -1;
    }*/

    /**
     * getActiveProfile
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Profile getActiveProfile() {
        return actProf;
    }

    /**
     * getActiveNLSItem
     *
     * @author Anthony C. Liberto
     * @return
     */
    public NLSItem getActiveNLSItem() {
        Profile prof = getActiveProfile();
        if (prof != null) {
            return prof.getReadLanguage();
        }
        return null;
    }

    /**
     * getDateRoutines
     *
     * @author Anthony C. Liberto
     * @return
     */
    public DateRoutines getDateRoutines() {
        return dAdjust.getDateRoutines();
    }

    /**
     * fileExist
     *
     * @author Anthony C. Liberto
     * @param _file
     * @return
     */
    protected boolean fileExist(String _file) {
        return gio.exists(_file);
    }

    /**
     * getDateString
     *
     * @author Anthony C. Liberto
     * @param _dif
     * @return
     */
    protected String getDateString(int _dif) {
        return dAdjust.getDateString(_dif);
    }

    /**
     * clearPref
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _write
     */
    protected void clearPref(String _code, boolean _write) {
        sPref.removePref(_code, _write);
    }

    /**
     * writePref
     *
     * @author Anthony C. Liberto
     */
    public void writePref() {
        sPref.writeFile();
    }

    /**
     * readStringFromFile
     *
     * @author Anthony C. Liberto
     * @param _fileName
     * @return
     * /
    public String readStringFromFile(String _fileName) {
        return gio.readString(_fileName);
    }*/

    /**
     * getPrefInt
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     * /
    public Integer getPrefInt(String _code) {
        return sPref.getPrefInteger(_code);
    }*/

    /**
     * getPrefInt
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    public int getPrefInt(String _code, int _def) {
        return sPref.getPrefInt(_code, _def);
    }

    /**
     * getPrefString
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    public String getPrefString(String _code, String _def) {
        return sPref.getPrefString(_code, _def);
    }

    /**
     * setPrefString
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _val
     */
    protected void setPrefString(String _code, String _val) {
        sPref.putPrefString(_code, _val);
    }

    /**
     * setPrefInt
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _i
     */
    protected void setPrefInt(String _code, int _i) {
        sPref.putPrefInteger(_code, _i);
    }

    /**
     * setPrefColor
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _color
     */
    public void setPrefColor(String _code, Color _color) {
        sPref.putPrefColor(_code, _color);
    }

    /**
     * getPrefColor
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    public Color getPrefColor(String _code, Color _def) {
        return sPref.getPrefColor(_code, _def);
    }

    /**
     * setPrefFont
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _fnt
     */
    public void setPrefFont(String _code, Font _fnt) {
        sPref.putPrefFont(_code, _fnt);
    }

    /**
     * getPrefFont
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    private Font getPrefFont(String _code, Font _def) {
        return sPref.getPrefFont(_code, _def);
    }

    /**
     * setPrefDouble
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _dbl
     */
    public void setPrefDouble(String _code, double _dbl) {
        sPref.putPrefDouble(_code, _dbl);
    }

    /**
     * getPrefDouble
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    public double getPrefDouble(String _code, double _def) {
        return sPref.getPrefDouble(_code, _def);
    }

    /**
     * setPrefBoolean
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _b
     */
    public void setPrefBoolean(String _code, boolean _b) {
        sPref.putPrefBoolean(_code, _b);
    }

    /**
     * getPrefBoolean
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _def
     * @return
     */
    public boolean getPrefBoolean(String _code, boolean _def) {
        return sPref.getPrefBoolean(_code, _def);
    }

    /**
     * getPrefPoint
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    protected Point getPrefPoint(String _code) {
        return sPref.getPrefPoint(_code);
    }

    /**
     * setPrefPoint
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _pt
     * /
    public void setPrefPoint(String _code, Point _pt) {
        sPref.putPrefPoint(_code, _pt);
    }*/

    /**
     * getPrefDimension
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    protected Dimension getPrefDimension(String _code) {
        return sPref.getPrefDimension(_code);
    }

    /**
     * setPrefDimension
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _d
     * /
    public void setPrefDimension(String _code, Dimension _d) {
        sPref.putPrefDimension(_code, _d);
    }*/

    /**
     * setPrefObject
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _o
     */
    public void setPrefObject(String _code, Object _o) {
        sPref.put(_code, _o);
    }

    /**
     * getPrefObject
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public Object getPrefObject(String _code) {
        return sPref.get(_code);
    }

    /**
     * containsPref
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     * /
    public boolean containsPref(String _code) {
        return sPref.containsKey(_code);
    }*/

    /**
     * eaccess
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static EAccess eaccess() {
        if (eaccess == null) {
            eaccess = new EAccess();
        }
        return eaccess;
    }
    /*
     * performance tracking
     */
    /**
     * write
     *
     * @author Anthony C. Liberto
     * @param _o
     */
    protected void write(Object _o) {
        if (isArmed(DATABASE_WRITE_ARM_FILE)) {
            save(_o);
        }
    }

    /**
     * dump
     *
     * @author Anthony C. Liberto
     * @param _o
     */
    protected void dump(Object _o) {
        String[] s = null;
        if (isArmed(DATABASE_DUMP_ARM_FILE)) {
            if (_o instanceof EntityList) {
                appendLog("EntityList.dump()...");
                appendLog(((EntityList) _o).dump(false));
            } else if (_o instanceof EntityChangeHistoryGroup) {
                appendLog("EntityChangeHistoryGroup.dump()...");
                appendLog(((EntityChangeHistoryGroup) _o).dump(false));
            } else if (_o instanceof AttributeChangeHistoryGroup) {
                appendLog("AttributeChangeHistoryGroup.dump()...");
                appendLog(((AttributeChangeHistoryGroup) _o).dump(false));
            } else if (_o instanceof MetaColumnOrderGroup) {
                appendLog("MetaColumnOrderGroup.dump()...");
                appendLog(((MetaColumnOrderGroup) _o).dump(false));
            } else if (_o instanceof MetaEntityList) {
                appendLog("MetaEntityList.dump()...");
                appendLog(((MetaEntityList) _o).dump(false));
            } else if (_o instanceof BookmarkGroup) {
                appendLog("BookmarkGroup.dump()...");
                appendLog(((BookmarkGroup) _o).dump(false));
            } else if (_o instanceof WhereUsedList) {
                appendLog("WhereUsedList.dump()...");
                appendLog(((WhereUsedList) _o).dump(false));
            } else if (_o instanceof MatrixList) {
                appendLog("MatrixList.dump()...");
                appendLog(((MatrixList) _o).dump(false));
            } else if (_o instanceof HWUpgradeList) {
                appendLog("HWUpgradeList.dump()...");
                appendLog(((HWUpgradeList) _o).dump(false));
            } else if (_o instanceof String) {
                appendLog("String.dump()...");
                appendLog((String) _o);
            } else if (_o instanceof String[]) {
                appendLog("String[].dump()...");
                s = (String[]) _o;
                for (int i = 0; i < s.length; ++i) {
                    appendLog(s[i]);
                }
            } else {
            }
        }
    }

    /**
     * timestamp
     * @param _s
     */
    public Long timestamp(String _s) {
    	return timestamp(_s,null);
    }
    public Long timestamp(String _s, Long startLong) {
        if (isArmed(TIMESTAMP_ARM_FILE)) {
        	long curtime = System.currentTimeMillis();
        	// attempt to get duration in msg, it will be the time since the last call to this method
        	String duration = "";
        	if (startLong !=null){
        		// use the passed in value as the starttime to calc diff
        		duration = Stopwatch.format(curtime-startLong.longValue());
        	}
    		System.out.println("EAccess.timestamp " + _s + " "+duration+" ("+ 
    				formatDate(TIMESTAMP_DATE,new Date())+")");
    		
            return new Long(curtime);
        }
        return null;
    }

    /**
     * trace
     *
     * @author Anthony C. Liberto
     * /
    public void trace() {
        if (isArmed(TRACE_ARM_FILE)) {
            java.lang.Thread.dumpStack();
        }
    }*/

    /**
     * verbose
     *
     * @author Anthony C. Liberto
     */
    private void verbose() {
        if (isArmed(VERBOSE_ARM_FILE)) {
            parent.gc();
            parent.memory(false);
        }
    }

    /**
     * save
     *
     * @author Anthony C. Liberto
     * @param _o
     */
    public void save(Object _o) {
    	if (_o !=null){
    		String fileName = null;
    		repaintImmediately();
    		fileName = gio().getFileName(FileDialog.SAVE);
    		if (fileName != null) {
    			gio.write(fileName, _o);
    		}
    	}
    }

    /**
     * load
     *
     * @author Anthony C. Liberto
     */
    public void load() {
        String fileName = gio().getFileName(FileDialog.LOAD);
        if (fileName != null) {
            repaintImmediately();
            //51975			parent.load(gio.read(fileName),null);
            parent.load(null, gio.read(fileName), null, null); //51975
        }
    }

    /*
    spelling
    */
    /**
     * loadDictionary
     *
     * @author Anthony C. Liberto
     */
    private void loadDictionary() {
        if (isDictionaryReady()) {
            showError(parent, "msg11025.1"); //dictionary already defined
            return;
        }
        if (equals("dictionary.type", "local")) {
            String sLocation = RESOURCE_DIRECTORY + getString("dictionary.name");
            dictionary = new JSpellDictionaryLocal(sLocation);
            ((JSpellDictionaryLocal) dictionary).setDictionaryFileName(sLocation);
        } else if (equals("dictionary.type", "servlet")) {
            dictionary = new JSpellDictionaryServlet();
            ((JSpellDictionaryServlet) dictionary).setURL(getString("dictionary.url"));
        } else if (equals("dictionary.type", "socket")) {
            dictionary = new JSpellDictionarySocket();
            ((JSpellDictionarySocket) dictionary).setHost(getString("dictionary.host"));
            ((JSpellDictionarySocket) dictionary).setPort(getInt("dictionary.port", 1557));
        }
        if (dictionary != null) {
            try {
                dictionary.open();
            } catch (JSpellNetworkException _net) {
                _net.printStackTrace();
                showError(parent, "msg11025.2"); //dictionary open failed
                return;
            } catch (JSpellException _jse) {
                _jse.printStackTrace();
                showError(parent, "msg11025.2"); //dictionary open failed
                return;
            }

            initDictionary();

            thrdParser = new JSpellParser(dictionary);
            thrdHandler = new JSpellErrorHandler();
            thrdHandler.setParser(thrdParser);
            thrdHandler.setFrame(parent);
            saParser = new JSpellParser(dictionary);
            synchLock = new SynchronizedLock();
        }
    }

    private void initDictionary() {
        /* should the dictionary learn words */
        dictionary.setLearnWords(false);

        /* are words with numbers errors */
        dictionary.setIgnoreWordsWithNumbers(true);

        /* should the first word of a sentence be checked for proper capitalization */
        dictionary.setIgnoreFirstCaps(false);

        /* Ignore upper case words */
        dictionary.setIgnoreUpper(false);

        /* check text and return suggestions using ALL upper case */
        dictionary.setForceUpperCase(false);

        /* special case scenerio */
        dictionary.setIgnoreIrregularCaps(false);

        /* are double words errors? */
        dictionary.setIgnoreDoubleWords(false);
    }

    /**
     * closeDictionary
     *
     * @author Anthony C. Liberto
     */
    protected void closeDictionary() {
        if (isDictionaryReady()) {
            try {
                dictionary.close();
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }
    }

    /**
     * getDictionary
     *
     * @author Anthony C. Liberto
     * @return
     * /
    public JSpellDictionary getDictionary() {
        return dictionary;
    }*/

    /**
     * isDictionaryReady
     *
     * @author Anthony C. Liberto
     * @return
     */
    private boolean isDictionaryReady() {
        if (dictionary != null) {
            return dictionary.getDictionaryReady();
        }
        return false;
    }

    /**
     * initSpellCheck
     *
     * @author Anthony C. Liberto
     */
    public void initSpellCheck() {
        iError = 0;
        bStop = false;
    }

    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * @param _s
     * @param _tc
     * @return
     */
    public String spellCheck(String _s, JTextComponent _tc) {
        return spellCheck(parent, _s, _tc); //51815
    } //51815

    /**
     * spellCheck
     *
     * @author Anthony C. Liberto
     * @param _frm
     * @param _s
     * @param _tc
     * @return
     */
    public String spellCheck(Frame _frm, String _s, JTextComponent _tc) { //51815
        JSpellErrorInfo jError = null;
        JSpellCorrectionDialog jSpellDialog = null;

        String str = null;
        if (_frm == null) { //51815
            _frm = parent; //51815
        } //51815

        if (_tc != null) {
            saParser.setTextString(_tc.getText());
        } else if (_s != null) {
            saParser.setTextString(_s);
        }

        jError = getError(saParser);
        //51815		JSpellCorrectionDialog jSpellDialog = new JSpellCorrectionDialog(parent,thrdHandler,synchLock);
        jSpellDialog = new JSpellCorrectionDialog(_frm, thrdHandler, synchLock); //51815

        while (jError != null && !bStop) {
            ++iError;
            jSpellDialog.handleError(jError);
            str = jSpellDialog.getWordFieldText();
            switch (jSpellDialog.getStatus()) {
            case JSpellCorrectionDialog.ACTION_REPLACE :
                saParser.replaceSingle(jError, str);
                if (_tc != null) {
                    _tc.setText(saParser.getTextStringBuffer().toString());
                }
                break;
            case JSpellCorrectionDialog.ACTION_REPLACEALL :
                saParser.replaceAll(jError, str);
                if (_tc != null) {
                    _tc.setText(saParser.getTextStringBuffer().toString());
                }
                break;
            case JSpellCorrectionDialog.ACTION_LEARN :
                saParser.learnWord(jError);
                break;
            case JSpellCorrectionDialog.ACTION_IGNOREALL :
                saParser.ignoreWord(jError.getWord());
                break;
            case JSpellCorrectionDialog.ACTION_STOP :
                bStop = true;
                break;
            default:
                break;
            }
            jError = getError(saParser);
        }
        return saParser.getTextStringBuffer().toString();
    }

    /**
     * completeSpellCheck
     *
     * @author Anthony C. Liberto
     * @param _showZero
     */
    public void completeSpellCheck(boolean _showZero) {
        if (iError == 0) {
            if (_showZero) {
                setCode("msg11025.3");
                setParm(toString(iError));
                showFYI(parent);
            }
        } else {
            setCode("msg11025.3");
            setParm(toString(iError));
            showFYI(parent);
        }
        dictionary.resetAll(); //24209
    }

    private JSpellErrorInfo getError(JSpellParser _parse) {
        try {
            return _parse.getError();
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
        return null;
    }

    /**
     * containsSpellError
     *
     * @author Anthony C. Liberto
     * @param _s
     * @param _tc
     * @return
     */
    public boolean containsSpellError(String _s, JTextComponent _tc) {
        JSpellErrorInfo jError = null;
        if (!isDictionaryReady()) {
            showError(parent, "msg11025.0"); //dictioary not defined
            return false;
        }
        if (_tc != null) {
            saParser.setTextString(_tc.getText());
        } else if (_s != null) {
            saParser.setTextString(_s);
        } else {
            return false;
        }
        jError = getError(saParser);
        if (jError != null) {
            saParser.reset();
            return true;
        }
        saParser.reset();
        return false;
    }

    /**
     * spawnSpellCheck
     *
     * @author Anthony C. Liberto
     * @param _tc
     * /
    public void spawnSpellCheck(JTextComponent _tc) {
        if (!isDictionaryReady()) {
            showError(parent, "msg11025.0"); //dictionary not defined
            return;
        }
        if (_tc == null) {
            return;
        }
        thrdHandler.setTextComponent(new JSpellSwingTextComponentWrapper(_tc));
        thrdHandler.init();
        thrdHandler.check();
    }*/

    /**
     * spawnSpellCheck
     *
     * @author Anthony C. Liberto
     * @param _tc
     * /
    public void spawnSpellCheck(JTextComponent[] _tc) {
        int ii = -1;
        if (!isDictionaryReady()) {
            showError(parent, "msg11025.0"); //dictionary not defined
            return;
        }
        if (_tc == null) {
            return;
        }
        ii = _tc.length;
        thrdHandler.init();
        for (int i = 0; i < ii; ++i) {
            if (_tc[i] != null) {
                thrdHandler.addComponent(_tc[i]);
            }
        }
        thrdHandler.check();
    }*/

    /**
     * toString
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    private static String toString(int _i) {
        return Routines.toString(_i);
    }

    /**
     * toString
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    private static String toString(boolean _b) {
        return Routines.toString(_b);
    }

    /**
     * sets the code for the message to
     * be displayed.
     *
     * @parm String _code => messageCode
     * @param _code
     */
    public void setCode(String _code) {
        errorCode = _code;
    }

    /**
     * retrieves the code that is being
     * used.
     *
     * @return String => message code
     */
    private String getCode() {
        return errorCode;
    }

    /**
     * clearCode
     *
     * @author Anthony C. Liberto
     * /
    public void clearCode() {
        errorCode = null;
    }*/

    /**
     * sets the title variable for the dialog
     *
     * @parm String _s => displayed title
     * @param _s
     */
    public void setTitle(String _s) {
        title = _s;
    }

    /**
     * returns the title variable for the dialog
     *
     * @return String title => the dialog title
     */
    public String getTitleRSAfix() { // this was commented out, added RSAfix to remove title not read warning
    	if (title == null){
    		return getString("name");
    	}
    	return title;
    }

    /**
     * sets the Parms for the messaging System.
     * This call is used if only one variable exists
     * on the message
     *
     * @parm String _s => the variable used to replace the {parm0} code
     * @param _s
     */
    public void setParm(String _s) {
        parm = new String[1];
        parm[0] = _s;
    }

    /**
     * sets all the parms for the messaging system.
     * This call is used for multiple variable situations
     *
     * @parm String[] _s => array of variable equal in size to the
     * number of greates {parmn} variable
     * @param _s
     */
    public void setParms(String[] _s) {
        int ii = _s.length;
        parm = new String[ii];
        for (int i = 0; i < ii; ++i) {
            if (_s[i] != null) {
                parm[i] =_s[i];
            } else {
                appendLog("***   Missing Parm   ***");
                java.lang.Thread.dumpStack();
                parm[i] = "missing parm: " + i;
            }
        }
    }

    /**
     * sets all the parms for the messaging system.
     * This call is used for multiple variable situations
     *
     * @parm String _s => delimited string variable equal in size to the
     * number of greates {parmn} variable
     * @parm String _delim => the delimiting character
     * @param _delim
     * @param _s
     */
    public void setParms(String _s, String _delim) {
        parm = Routines.getStringArray(_s, _delim);
    }

    /**
     * getProfileInfo
     *
     * @author Anthony C. Liberto
     * @param _p
     * @return
     */
    protected String getProfileInfo(Profile _p) {
        String out = null;
        if (_p == null) {
            return getString("nia");
        }
        setCode("profInfo");
        setParmCount(19);
        setParm(0, _p.getEnterprise());
        setParm(1, _p.getOPName());
        setParm(2, toString(_p.getOPID()));
        setParm(3, toString(_p.getOPWGID()));
        setParm(4, _p.getRoleCode());
        setParm(5, _p.getRoleDescription());
        setParm(6, _p.getWGName());
        setParm(7, toString(_p.isReadOnly()));
        setParm(8, toString(_p.getWGID()));
        setParm(9, toString(_p.getSessionID()));
        setParm(10, toString(_p.getTranID()));
        setParm(11, toString(_p.getDefaultIndex()));
        setParm(12, _p.getEmailAddress());
        setParm(13, _p.getEndOfDay());
        setParm(14, _p.getLoginTime());
        setParm(15, _p.getOPWGName());
        setParm(16, _p.getValOn());
        setParm(17,_p.dumpPDHDomain(true));
        setParm(18, Profile.getVersion());
        out = getMessage();
        clear();
        return out;
    }

    /**
     * getNLSInfo
     *
     * @author Anthony C. Liberto
     * @param _nls
     * @return
     */
    protected String getNLSInfo(NLSItem _nls) {
        String out = null;
        if (_nls == null) {
            return getString("nia");
        }
        setCode("nlsInfo");
        setParmCount(3);
        setParm(0, _nls.toString());
        setParm(1, _nls.getNLSDescription());
        setParm(2, toString(_nls.getNLSID()));
        out = new String(getMessage());
        clear();
        return out;
    }

    /**
     * sets the number of {parmn} variables
     * contained in the message
     *
     * @parm int _i => the greatest {parmn} clause.
     * @param _i
     */
    public void setParmCount(int _i) {
        if (_i >= 0) {
            clearParms();
            parm = new String[_i];
        } else {
            clearParms();
        }
    }

    /**
     * used to set individual {parmn} variables on the message.
     * must be preceeded by a call to setParmCount(int _i).
     *
     * @parm int _i => the {parmn} that is to be set
     * @parm String _s => The String representation of the {parmn}
     * @param _i
     * @param _s
     */
    public void setParm(int _i, String _s) {
        if (_s != null && _i >= 0 && parm != null && _i < parm.length) {
            parm[_i] = new String(_s);
        }
    }

    /**
     * clear all the parameters
     */
    public void clearParms() {
        if (parm != null) {
            for (int i = 0; i < parm.length; ++i) {
                parm[i] = null;
            }
            parm = null;
        }
    }

    /**
     * get the array of parameters
     *
     * @return String[] => the array of parameters
     */
    public String[] getParms() {
        return parm;
    }

    /**
     * returns the message based on the code and the
     * parameters that have been handed in.
     * The {parm0}, {parm1}...{parmn} clauses are
     * replaced with the appropriate String from the
     * array of parameters
     *
     * @return String message => The message
     */
    public String getMessage() {
        String out = null;
        if (msg != null) {
            return msg;
        }
        out = getString(getCode());
        if (parm != null) {
            int ii = parm.length;
            for (int i = 0; i < ii; ++i) {
                out = Routines.replace(out, "{parm" + i + "}", (parm[i] == null) ? "null" : parm[i]);
            }
        }
        return out;
    }

    /**
     * getMessage
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _parms
     * @return
     */
    public String getMessage(String _code, String[] _parms) {
        String out = getString(_code);
        if (_parms != null) {
            int ii = _parms.length;
            for (int i = 0; i < ii; ++i) {
                out = Routines.replace(out, "{parm" + i + "}", _parms[i]);
            }
        }
        return out;
    }

    /**
     * replace parms
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _parms
     * @return
     */
    private static String replaceParms(String _code, String[] _parms) {
        String out = new String(_code);
        if (_parms != null) {
            int ii = _parms.length;
            for (int i = 0; i < ii; ++i) {
                out = Routines.replace(out, "{parm" + i + "}", _parms[i]);
            }
        }
        return out;
    }

    /**
     * setMessage
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setMessage(String _s) {
        msg = null;
        if (_s != null) {
            msg = _s;
        }
    }

    private void clear(Vector _v) {
        while (!_v.isEmpty()) {
            Object o = _v.remove(0);
            if (o != null && o instanceof InterfaceDialog) {
                ((InterfaceDialog) o).dereference();
            }
        }
        _v.clear();
    }

    private void showXMLFrame(Window _win, DisplayableComponent _dc) {
        _dc.setParentDialog(xmlfDialog);
        xmlfDialog.setTitle(_dc.getTitle());
        xmlfDialog.setOwner(_win);
        xmlfDialog.show(_dc);
    }

    /**
     * hide the dialog if one is showing.
     *
     * @param _id
     */
    public void hide(InterfaceDialog _id) {
        if (_id != null) {
            _id.hide();
        }
    }

    /**
     * dispose the dialog that is being displayed
     *
     * @param _id
     */
    public void dispose(InterfaceDialog _id) {
        if (_id != null) {
            _id.dispose();
        }
    }

    /**
     * dereferenceDialog
     *
     * @author Anthony C. Liberto
     * @param _id
     */
    public void dereferenceDialog(InterfaceDialog _id) {
        if (!md.contains(_id) && !fd.contains(_id)) {
            _id.dereferenceFull();
        }
        validate(); //53437
    }

    /**
     * pack the dialog that is being shown.
     *
     * @param _id
     */
    public void pack(InterfaceDialog _id) {
        if (_id != null) {
            _id.pack();
        }
    }

    /**
     * validate
     *
     * @author Anthony C. Liberto
     * @param _id
     */
    public void validate(InterfaceDialog _id) {
        if (_id != null) {
            _id.validate();
        }
    }
    /*
     	Retrieve information from resource bundles.
     */
    /**
     * is
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _lookup
     * @return
     */
    public boolean is(String _code, boolean _lookup) {
        if (_lookup) {
            _code = getString(_code).trim();
        }
        return Routines.is(_code);
    }

    /**
     * not
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _lookup
     * @return
     */
    public boolean not(String _code, boolean _lookup) {
        if (_lookup) {
            _code = getString(_code).trim();
        }
        return Routines.not(_code);
    }

    /**
     * getString
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getString(String _code) {
        return getOverwrite(_code);
    }

    private String getPackaged(String _code) {
        try {
            if (_code.startsWith("msg")) {
                return packagedBundle.getString(_code) + " (" + _code + ")";
            } else {
                return packagedBundle.getString(_code);
            }
        } catch (MissingResourceException _mre) {
            report(_mre,false);
            if (isTestMode()) {
                return UNDEFINED + _code;
            }
            return _code;
        }
    }

    private String getOverwrite(String _code) {
        if (overwriteBundle == null) {
            return getPackaged(_code);
        }
        try {
            if (_code.startsWith("msg")) {
                return overwriteBundle.getString(_code) + " (" + _code + ")";
            } else {
                return overwriteBundle.getString(_code);
            }
        } catch (MissingResourceException _mre) {
            report(_mre,false);
        }
        return getPackaged(_code);
    }

    /**
     * getStringArray
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public String[] getStringArray(String _code) {
        String s = getString(_code);
        return Routines.getStringArray(s, ARRAY_DELIMIT);
    }

    /**
     * getObject
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public Object getObject(String _code) {
        return getOverwriteObject(_code);
    }

    private Object getPackagedObject(String _code) {
        try {
            return packagedBundle.getObject(_code);
        } catch (MissingResourceException _mre) {
            report(_mre,false);
            appendLog("getObject.undefined key: " + _code);
            return null;
        }
    }

    private Object getOverwriteObject(String _code) {
        try {
            return overwriteBundle.getObject(_code);
        } catch (MissingResourceException _mre) {
            report(_mre,false);
        }
        return getPackagedObject(_code);
    }

    /**
     * getCharacter
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public Character getCharacter(String _code) {
        String s = getString(_code);
        char c = '?';
        //51334		if (s == null) return null;
        if (s == null || s.equals(_code)) { //51334
            return null; //51334
        } //51334
        c = s.trim().charAt(0);
        if (Character.isDefined(c)) {
            return new Character(c);
        }
        return null;
    }

    /**
     * getChar
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public char getChar(String _code) {
        Character c = getCharacter(_code);
        if (c != null) {
            return c.charValue();
        }
        return Character.MAX_VALUE;
    }

    /**
     * getInt
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public int getInt(String _code) {
        return getInt(_code, 0);
    }

    /**
     * getInt
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _default
     * @return
     */
    public int getInt(String _code, int _default) {
        String sNumb = getString(_code);
        try {
            return Integer.valueOf(sNumb).intValue();
        } catch (NumberFormatException _nfe) {
            _nfe.printStackTrace();
        }
        return _default;
    }

    /**
     * equals
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _value
     * @return
     */
    public boolean equals(String _code, String _value) {
        String str = getString(_code);
        return str.equals(_value);
    }

    /**
     * equalsIgnoreCase
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _value
     * @return
     */
    public boolean equalsIgnoreCase(String _code, String _value) {
        String str = getString(_code);
        return str.equalsIgnoreCase(_value);
    }

    /**
     * appendLog
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public static void appendLog(String _s) {
        D.ebug(_s);
    }

    /**
     * clear
     *
     * @author Anthony C. Liberto
     */
    public void clear() {
        errorCode = null;
        title = null;
        msg = null;
        clearParms();
    }

    /*
     	dialog Panels
    */
    /**
     * getXMLPanel
     *
     * @author Anthony C. Liberto
     * @return
     */
    public XMLPanel getXMLPanel() {
        return xmlPnl;
    }

    /**
     * getXMLEditor
     *
     * @author Anthony C. Liberto
     * @return
     */
    public XMLEditor getXMLEditor() {
        if (xmlPnl != null) {
            return xmlPnl.getEditor();
        }
        return null;
    }

    /*
    	images & html
    */
    /**
     * getImage
     *
     * @author Anthony C. Liberto
     * @return Image
     * @param _img
     * @concurrency $none
     */
    public synchronized Image getImage(String _img) {
        if (gio().exists(RESOURCE_DIRECTORY + _img)) {
            return Toolkit.getDefaultToolkit().createImage(RESOURCE_DIRECTORY + _img);
        }
        if (eGraph != null) {
            return eGraph.getImage(_img);
        }
        return null;
    }

    /**
     * getImageIcon
     *
     * @author Anthony C. Liberto
     * @return ImageIcon
     * @param _img
     * @concurrency $none
     */
    public synchronized ImageIcon getImageIcon(String _img) {
        if (gio().exists(RESOURCE_DIRECTORY + _img)) {
            return new ImageIcon(RESOURCE_DIRECTORY + _img);
        }
        if (eGraph != null) {
            return eGraph.getImageIcon(_img);
        }
        return null;
    }

    /**
     * getHTML
     *
     * @author Anthony C. Liberto
     * @param _code
     * @return
     */
    public String getHTML(String _code) {
        if (gio().exists(RESOURCE_DIRECTORY + _code)) {
            return gio().readString(RESOURCE_DIRECTORY + _code);
        }
        return hGrabber.getHTML(_code);
    }

    /**
     * appendMiddlewareLocation
     * CR092005410
     * @param _code
     * @param _s
     * @author tony
     */
    public void appendMiddlewareLocation(String _code, String _s) {
		if (gio().exists(RESOURCE_DIRECTORY + _code)) {
			gio().insertString(RESOURCE_DIRECTORY + _code,_s,-1);
		}
	}

    /**
     * XMLExists
     *
     * @author Anthony C. Liberto
     * @param _name
     * @return
     */
    public boolean xmlExists(String _name) {
        return hGrabber.xmlExists(_name);
    }

    /*
    	cursor
    */

    /**
     * getCursor
     * @author Anthony C. Liberto
     * @return Cursor
     */
    public Cursor getCursor() {
        if (isBusy()) {
            return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        } else if (hasChangeGroup() && CHANGE_GROUP_CURSOR != null) {
            return CHANGE_GROUP_CURSOR;
        }
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * getModalCursor
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Cursor getModalCursor() {
        if (isModalBusy()) {
            return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        }
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * setBusy
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setBusy(boolean _b) {
        if (!_b && worker != null) {
            return;
        }
        bBusy = _b;
        parent.updateCursorImmediately();
    }

    /**
     * isBusy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isBusy() {
        return bModalBusy || bBusy;
    }

    /**
     * setModalBusy
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setModalBusy(boolean _b) {
        if (!_b && worker != null) {
            return;
        }
        bModalBusy = _b;
        parent.updateCursorImmediately();
    }

    /**
     * isModalBusy
     * @author Anthony C. Liberto
     * @return boolean
     */
    public boolean isModalBusy() {
        return bModalBusy;
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        clear();
        eGraph = null;
        eaccess = null;
        packagedBundle = null;
        overwriteBundle = null;

        clear(md);
        md = null;

        clear(fd);
        fd = null;

        xmlfDialog.dereference();
        xmlfDialog = null;

        messagePnl.dereference();
        messagePnl = null;

		lockownerTbl.clear();
    }

    /**
     * exit
     *
     * @author Anthony C. Liberto
     */
    public void exit(String _s) {
		if (isMonitor()) {
			monitor(_s,new Date());
		}
        parent.exit();
    }

    /*
     * copy and paste
     */
    /**
     * getClipboard
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Clipboard getClipboard() {
        return Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    /**
     * getTransferHandler
     *
     * @author Anthony C. Liberto
     * @return
     */
    public ETransHandler getTransferHandler() {
        if (transHand == null) {
            transHand = new ETransHandler();
        }
        return transHand;
    }

    /**
     * setClipboardContents
     *
     * @author Anthony C. Liberto
     * @param _value
     * @param _type
     * @param _copied
     * @return
     */
    public TransferObject setClipboardContents(String _value, String _type, Object _copied) {
        int opwg = getOPWGID();
        TransferObject trans = new TransferObject(_value, _type, _copied, opwg);
        if (setClipboardContents(trans) == null) {
            showError(parent, "msg11004");
        }
        return trans;
    }

    /**
     * setClipboardContents
     *
     * @author Anthony C. Liberto
     * @param _value
     * @param _type
     * @param _cols
     * @param _copied
     * @return
     */
    public TransferObject setClipboardContents(String[] _value, String _type, int _cols, Object _copied) {
        int opwg = getOPWGID();
        TransferObject trans = new TransferObject(Routines.toString(_value), _value, _type, _copied, _cols, opwg);
        if (setClipboardContents(trans) == null) {
            showError(parent, "msg11004");
        }
        return trans;
    }

    /**
     * setClipboardContents
     *
     * @author Anthony C. Liberto
     * @param _to
     * @return
     */
    public TransferObject setClipboardContents(TransferObject _to) {
        Clipboard clip = getClipboard();
        if (clip != null) {
            clip.setContents(_to, null);
            return _to;
        }
        return null;
    }

    /**
     * getClipboardContents
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Transferable getClipboardContents() {
        Clipboard clip = getClipboard();
        if (clip != null) {
            return clip.getContents(this);
        }
        return null;
    }

    /**
     * canPaste
     *
     * @author Anthony C. Liberto
     * @param _internal
     * @return
     */
    public boolean canPaste(boolean _internal) {
        try {
            Transferable trans = getClipboardContents();
            if (trans != null) {
                return true;
            }
        } catch (Exception _x) {
            report(_x,false);
        }
        return false;
    }

    /**
     * setFilter
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setFilter(boolean _b) {
        parent.setFilter(_b);
    }

    /**
     * getColor
     *
     * @author Anthony C. Liberto
     * @param _c
     * @return
     */
    public Color getColor(Color _c) {
        if (colorChoose == null) {
            colorChoose = new EColorChooser(prefPnl, getString("chooseColor"));
        }
        return colorChoose.getColor(_c);
    }

    /**
     * getBackground
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Color getBackground() {
        return getPrefColor(PREF_COLOR_BACKGROUND, DEFAULT_COLOR_ENABLED_BACKGROUND);
    }

    /**
     * getDisabledBackground
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Color getDisabledBackground() {
        return getBackground();
    }

    /**
     * getForeground
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Color getForeground() {
        return getPrefColor(PREF_COLOR_FOREGROUND, DEFAULT_COLOR_ENABLED_FOREGROUND);
    }

    /**
     * getDisabledForeground
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Color getDisabledForeground() {
        //		return getForeground();
        //		Color c = getForeground().brighter();
        //		return c;
        return getPrefColor(PREF_COLOR_DISABLED_FOREGROUND, DEFAULT_COLOR_DISABLED_FOREGROUND);
    }

    /**
     * getFont
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Font getFont() {
        return getPrefFont(PREF_FONT, DEFAULT_FONT);
    }

    /**
     * repaintImmediately
     *
     * @author Anthony C. Liberto
     */
    public void repaintImmediately() {
        parent.repaintImmediately();
    }

    /**
     * getFocusOwner
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Component getFocusOwner() {
        return parent.getFocusOwner();
    }

    /**
     * gc
     */
    public static void gc() {
//    	put in own thread to improve performance
        Runnable writelater = new Runnable() {
            public void run() {
                System.out.flush();
                System.gc();
            }
        };

        Thread t = new Thread(writelater);
        t.start();            
    }

    /**
     * nlsChanged
     *
     * @author Anthony C. Liberto
     * @param _nls
     */
    public void nlsChanged(NLSItem _nls) {
        setNLS(_nls);
        refresh(true);
    }

    /**
     * setNLS
     *
     * @author Anthony C. Liberto
     * @param _nls
     */
    public void setNLS(NLSItem _nls) {
        Profile prof = getActiveProfile();
        if (prof != null) {
            prof.setReadLanguage(_nls);
        }
        if (parent != null) { //51285
            parent.setStatus(prof); //51285
        } //51285
    }

    /**
     * setNLSData
     *
     * @author Anthony C. Liberto
     * @param _prof
     */
    public void setNLSData(Profile _prof) {
//        NLSItem[] ra = getNLSItemArray(_prof);
        NLSItem nls = _prof.getReadLanguage();
        setNLS(nls);
    }

    /**
     * getNLSItemArray
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @return
     */
    protected NLSItem[] getNLSItemArray(Profile _prof) {
        Vector v = _prof.getReadLanguages();
        int ii = v.size();
        NLSItem[] out = new NLSItem[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = ((NLSItem) v.elementAt(i));
        }
        Arrays.sort(out, new EComparator(true));
        return out;
    }

    /*
     USRO-R-DWES-66MV6T
    	public String getTabTitle(String _code, Profile _prof) {
    		setCode(_code);
    		setParm(_prof.toString());
    		String out = new String(getMessage());
    		clear();
    		return out;
    	}
    */
    /**
     * getTabbedPane
     *
     * @author Anthony C. Liberto
     * @return
     */
    public ETabbedMenuPane getTabbedPane() {
        return ePane;
    }

    /**
     * getTab
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public ETabable getTab(int _i) {
        if (ePane != null) {
            return ePane.getTabable(_i);
        }
        return null;
    }

    /**
     * getCurrentTab
     *
     * @author Anthony C. Liberto
     * @return
     */
    public ETabable getCurrentTab() {
        if (ePane != null) {
            return ePane.getSelectedTab();
        }
        return null;
    }

    /**
     * getNavigateIndex
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @return
     */
    public int getNavigateIndex(Profile _prof) {
        if (ePane != null) {
            return ePane.getNavigateIndex(_prof);
        }
        return -1;
    }

    /**
     * getETabable
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @param _panelType
     * @return
     */
    public ETabable getETabable(Profile _prof, String _panelType) {
        if (ePane != null) {
            return ePane.getETabable(_prof, _panelType);
        }
        return null;
    }

    /**
     * setSelectedIndex
     *
     * @author Anthony C. Liberto
     * @param _i
     */
    public void setSelectedIndex(int _i) {
        if (ePane != null) {
            ePane.setSelectedIndex(_i);
        }
    }

    /**
     * getTabCount
     *
     * @author Anthony C. Liberto
     * @return
     */
    public int getTabCount() {
        if (ePane != null) {
            return ePane.getTabCount();
        }
        return 0;
    }

    /**
     * getSelectedIndex
     *
     * @author Anthony C. Liberto
     * @return
     */
    public int getSelectedIndex() {
        if (ePane != null) {
            return ePane.getSelectedIndex();
        }
        return -1;
    }

    /**
     * adjustPrevNext
     *
     * @author Anthony C. Liberto
     * @param _indx
     * @param _max
     */
    public void adjustPrevNext(int _indx, int _max) {
        if (parent != null) {
            parent.adjustPrevNext(_indx, _max);
        }
    }

    /**
     * getTabMenu
     *
     * @author Anthony C. Liberto
     * @return
     */
    public EMenu getTabMenu() {
        if (ePane != null) {
            return ePane.getMenu();
        }
        return null;
    }

    /**
     * close
     *
     * @author Anthony C. Liberto
     * @param _tab
     */
    public void close(ETabable _tab) {
        parent.close(ePane, _tab);
    }

    /**
     * closeAll
     *
     * @author Anthony C. Liberto
     */
    public void closeAll() {
        parent.closeAll(ePane);
    }

    /**
     * logOff
     *
     * @author Anthony C. Liberto
     */
    public void logOff() {
        parent.logOff(ePane);
    }

    /**
     * setLockList
     *
     * @author Anthony C. Liberto
     * @param _ll
     */
    public void setLockList(LockList _ll) {
        lockList = _ll;
    }

    /**
     * getLockList
     *
     * @author Anthony C. Liberto
     * @return
     */
    public LockList getLockList() {
        return lockList;
    }

    /**
     * setEMenuBar
     *
     * @author Anthony C. Liberto
     * @param _bar
     */
    public void setEMenuBar(EMenubar _bar) {
        parent.setEMenuBar(_bar);
    }

    /**
     * setWorker
     *
     * @author Anthony C. Liberto
     * @param _worker
     */
    public void setWorker(ESwingWorker _worker) {
        if (_worker == null) {
            resetWorker();
            return;
        }
        if (worker != null) {
            pushWorker(_worker);
            return;
        }

        worker = _worker;
        worker.start();
    }

    private void resetWorker() {
        worker = null;
        popWorker();
        if (worker == null) {
            setModalBusy(false);
        }
    }

    private void pushWorker(ESwingWorker _worker) {
        vWorker.add(_worker);
    }

    private void popWorker() {
        if (!vWorker.isEmpty()) {
            Object o = vWorker.remove(0);
            if (o != null && o instanceof ESwingWorker) {
                setWorker((ESwingWorker) o);
            }
        }
    }

    /**
     * clearWorker
     *
     * @author Anthony C. Liberto
     */
    public void clearWorker() {
        vWorker.clear();
    }

    /**
     * interrupt
     *
     * @author Anthony C. Liberto
     */
    public void interrupt() {
        if (worker != null) {
            if (worker.isInterruptable()) {
                worker.interrupt();
                setBusy(false);
            } else {
                return;
            }
        } else if (isTestMode()) {
            setBusy(false);
        }
        resetWorker();
    }

    /**
     * setProcessTime
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @param _time
     */
    public void setProcessTime(Profile _prof, String _time) {
        if (_prof != null) {
            _prof.setValOnEffOn(_time, _time);
            //1.2h		bPast = !_time.equals(getNow(END_OF_DAY));
            //52548			parent.setPast(isPast());
            parent.setPast(isPast(), _time); //52548
        }
    }

    /**
     * prune
     *
     * @author Anthony C. Liberto
     * @param _o
     *
    public void prune(Object _o) {
    }

    /**
     * showScrollDialog
     *
     * @author Anthony C. Liberto
     * @param _message
     */
    public void showScrollDialog(String _message) {
        showScrollDialog(null, _message);
    }

    /**
     * showModalScrollDialog
     *
     * @author Anthony C. Liberto
     * @param _win
     * @param _message
     */
    public void showModalScrollDialog(Window _win, String _message) {
        scrollPnl.setResizable(true); //50362
        scrollPnl.setText(_message);
        show(_win, scrollPnl, true);
    }

    /**
     * showScrollDialog
     *
     * @author Anthony C. Liberto
     * @param _win
     * @param _message
     */
    public void showScrollDialog(Window _win, String _message) {
        scrollPnl.setText(_message);
        showFrame(_win, scrollPnl);
    }

    /*
     	get number of copies with a variable message
     */
    /**
     * getNumber
     *
     * @author Anthony C. Liberto
     * @param _message
     * @return
     */
    public int getNumber(String _message) {
        return getNumber(null, _message);
    }

    /**
     * getNumber
     *
     * @author Anthony C. Liberto
     * @param _win
     * @param _message
     * @return
     */
    public int getNumber(Window _win, String _message) {
        numberPnl.reset();
        numberPnl.setText(getString(_message));
        showDialog(_win, numberPnl);
        return numberPnl.getSelected();
    }

    /**
     * show
     *
     * @author Anthony C. Liberto
     * @param _panel
     * @param _modal
     */
    public void show(int _panel, boolean _modal) {
        show(null, _panel, _modal);
    }

    /**
     * show
     *
     * @author Anthony C. Liberto
     * @param _win
     * @param _panel
     * @param _modal
     */
    public void show(Window _win, int _panel, boolean _modal) {
        show(_win, getPanel(_panel), _modal);
    }

    /**
     * isUndefined
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     */
    public boolean isUndefined(String _s) {
        return _s.startsWith(UNDEFINED);
    }

    /**
     * getPanel
     *
     * @author Anthony C. Liberto
     * @param _panel
     * @return
     */
    public AccessibleDialogPanel getPanel(int _panel) {
        switch (_panel) {
        case DATE_PANEL :
            return datePnl;
        case FIND_PANEL :
            return findPnl;
        case FILTER_PANEL :
            return filterPnl;
        case PREFERENCE_PANEL :
            return prefPnl;
        case SORT_PANEL :
            return sortPnl;
        case WIZARD_PANEL :
            return wizardPnl;
        case XML_PANEL :
            return xmlPnl;
        case MAINTENANCE_PANEL:			//cr_FlagUpdate
        	return maintPnl;			//cr_FlagUpdate
        default :
            return messagePnl;
        }
    }

    /**
     * getRelator
     *
     * @author Anthony C. Liberto
     * @param _meta
     * @return
     */
    public MetaLink getRelator(MetaLink[] _meta) {
        return relatorPnl.showDialog(_meta);
    }

    /**
     * showLinkDialog
     *
     * @author Anthony C. Liberto
     * @param _title
     * @param _message
     */
    public void showLinkDialog(String _title, String _message) {
        showLinkDialog(null, _title, _message);
    }

    /**
     * showLinkDialog
     *
     * @author Anthony C. Liberto
     * @param _win
     * @param _title
     * @param _message
     */
    public void showLinkDialog(Window _win, String _title, String _message) {
        String[] parms = { getLinkDefinition(), _message }; //link_type
        linkPnl.setText(_title);

        linkPnl.setMessage(getMessage("eannounce.link.success", parms)); //link_type

        //link_type		linkPnl.setMessage(_message);
        showDialog(_win, linkPnl); //50455
        //50455		showFrame(_win,linkPnl);
    }

    private String getLinkDefinition() { //link_type
        String[] sDef = getStringArray("eannounce.link.type"); //link_type

        return getString(sDef[getPrefInt(PREF_LINK_TYPE, DEFAULT_LINK_TYPE)]); //link_type
    } //link_type

    /**
     * getDate
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getDate() {
        datePnl.set(2, false, true, true);
        datePnl.setDisplayDate(getNow(FORMATTED_DATE, false));
        setBusy(false);
        return datePnl.getDate();
    }

    /**
     * shouldRefresh
     *
     * @author Anthony C. Liberto
     * @param _tab
     * @return
     */
    public boolean shouldRefresh(ETabable _tab) {
        return _tab.shouldRefresh();
    }

    /**
     * isShowing
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public boolean isShowing(int _i) {
        EPanel pnl = getPanel(_i);
        if (pnl != null) {
            return pnl.isShowing();
        }
        return false;
    }

    /**
     * showSearch
     *
     * @author Anthony C. Liberto
     * @param _sai
     * @param _navType
     * @param _o
     */
    public void showSearch(SearchActionItem _sai, int _navType, Object _o) {
        searchPnl.showSearch(_sai, _navType, _o);
    }

    /**
     * editContains
     *
     * @author Anthony C. Liberto
     * @param _ei
     * @param _eai
     * @return
     */
    public boolean editContains(EntityItem[] _ei, EANActionItem _eai) {
        int ii = ePane.getTabCount();
        Profile prof = getActiveProfile();
        int id = -1;
        if (prof == null) {
            return false;
        }
        id = prof.getOPWGID();
        for (int i = 0; i < ii; ++i) {
            ETabable t = ePane.getTabable(i);
            if (t != null && !(t.getPanelType().equals(TYPE_ENAVFORM))) {
                if (t.getOPWGID() == id) {
                    if (t.contains(_ei, _eai)) {
                        ePane.setSelectedIndex(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * getBlobCount
     *
     * @author Anthony C. Liberto
     * @return
     */
    public int getBlobCount() {
        ++iBlob;
        return iBlob;
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        if (action.equals("break")) {
            interrupt();
        } else if (action.equals("exit")) {
            exit("exit eaccessListener");
        } else if (action.equals("load")) {
            load();
        }
    }

    /**
     * refresh
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void refresh(boolean _b) {
        ETabable eTab = ePane.getSelectedTab();
        if (eTab != null) {
            eTab.refresh(_b);
        }
    }

    /**
     * setOffline
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setOffline(boolean _b) {
        bOffline = _b;
    }

    /**
     * isOffline
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isOffline() {
        return bOffline;
    }

    /**
     * test
     *
     * @author Anthony C. Liberto
     */
    public void test() {
        final ESwingWorker myWorker = new ESwingWorker() {
            //boolean bLockList = false;
            public Object construct() {
                return null;
            }

            public void finished() {
                setWorker(null);
                setBusy(false);
                return;
            }
        };
        setWorker(myWorker);
    }

    /**
     * getEditPDGPnl
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static EditPDGPanel getEditPDGPnl() { //pdg
        return editPDGPnl;
    }

    /**
     * getNavPDGPnl
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static NavPDGPanel getNavPDGPnl() { //pdg
        return navPDGPnl;
    }

    /**
     * getPDGInfoPnl
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static PDGInfoPanel getPDGInfoPnl() { //infopdg
        return infoPDGPnl;
    }

    /**
     * refreshAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        if (ePane != null) {
            ePane.refreshAppearance();
        }
        if (parent != null) {
            parent.refreshAppearance();
        }
    }

    /**
     * refreshDialogAppearance
     *
     * @author Anthony C. Liberto
     */
    public void refreshDialogAppearance() {
        if (!md.isEmpty()) {
            Object[] o = md.toArray();
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                ((ModalDialog) o[i]).refreshAppearance();
            }
        }
        if (!fd.isEmpty()) {
            Object[] o = fd.toArray();
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                ((FrameDialog) o[i]).refreshAppearance();
            }
        }
        getMiddlewareChooser(null, true).refreshAppearance();
    }

    /**
     * getMiddlewareChooser
     *
     * @author Anthony C. Liberto
     * @param _id
     * @param _standAlone
     * @return
     */
    public MWChooser getMiddlewareChooser(InterfaceDialog _id, boolean _standAlone) {
        if (mwChoose == null) {
            mwChoose = new MWChooser();
        }
        mwChoose.setType(_standAlone);
        mwChoose.setParentDialog(_id);
        return mwChoose;
    }

    /**
     * getMWObject
     *
     * @author Anthony C. Liberto
     * @return
     */
    public MWObject getMWObject() {
        show(null, getMiddlewareChooser(null, true), true);
        return mwChoose.getMiddleware();
    }

    /**
     * getMiddlewareSettings
     *
     * @author Anthony C. Liberto
     * @return
     */
    public MWObject getMiddlewareSettings() {
        return getMWParser().getCurrent();
    }

    /**
     * getCurrentMiddlewareObject
     *
     * @author Anthony C. Liberto
     * @return
     */
    public MWObject getCurrentMiddlewareObject() { //TIR USRO-R-RLON-645P76
        return tBase.getCurrentMiddlewareObject(); //TIR USRO-R-RLON-645P76
    } //TIR USRO-R-RLON-645P76

    /*
    50494
    */
    /**
     * setMenuEnabled
     *
     * @author Anthony C. Liberto
     * @param _menu
     * @param _item
     * @param _b
     */
    public void setMenuEnabled(String _menu, String _item, boolean _b) {
        parent.setMenuEnabled(_menu, _item, _b);
    }

    /**
     * setPDGOn
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setPDGOn(boolean _b) { //50530
        m_bPDGOn = _b;
    }

    /**
     * isPDGOn
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isPDGOn() { //50530
        return m_bPDGOn;
    }
    /*
     50874
    */
    /**
     * getSearchObject
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Object getSearchObject() {
        ETabable tab = getCurrentTab();
        if (tab != null) {
            return tab.getSearchableObject();
        }
        return null;
    }

    /*
     pass thru methods.
     */
    /**
     * getActiveWindow
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Window getActiveWindow() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
    }

    /**
     * getFocusComponent
     *
     * @author Anthony C. Liberto
     * @return
     */
    public Component getFocusComponent() {
        return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    }

    /**
     * getParentWindow
     *
     * @author Anthony C. Liberto
     * @param _c
     * @return
     */
    public Window getParentWindow(Component _c) {
        if (_c != null) {
            if (_c instanceof Window) {
                return (Window) _c;
            }
            return getWindow(_c.getParent());
        }
        return null;
    }

    /**
     * getWindow
     *
     * @author Anthony C. Liberto
     * @param _cont
     * @return
     */
    public Window getWindow(Container _cont) {
        while (_cont != null) {
            if (_cont instanceof Window) {
                return (Window) _cont;
            } else if (_cont instanceof DisplayableComponent) {
                return (Window) ((DisplayableComponent) _cont).getParentDialog();
            }
            return getWindow(_cont.getParent());
        }
        return null;
    }
    /*
     messaging
    */
    /**
     * show a modal FYI message
     * should be preceeded by the standard message manipulation
     * of setCode(String) and if applicable one of the setParms()
     *
     * @param _c
     */
    public void showFYI(Component _c) {
        if (!messagePnl.isShowing()) { //51038
            messagePnl.construct(MESSAGE_TYPE, FYI_MESSAGE, OK, getMessage());
            show(_c, messagePnl, true);
            clear();
        } //51038
    }

    /**
     * showFYI
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _code
     */
    public void showFYI(Component _c, String _code) {
        if (!messagePnl.isShowing()) { //51038
            setTitle(_code);
            setCode(_code);
            messagePnl.construct(MESSAGE_TYPE, FYI_MESSAGE, OK, getMessage());
            show(_c, messagePnl, true);
            clear();
        } //51038
    }
    /**
     * showFYI
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _parms
     * @param _c
     * @return String
     */
    public String showFYI(String _code, String[] _parms, Component _c) {
        String out = null;
        if (!messagePnl.isShowing()) {
            messagePnl.construct(MESSAGE_TYPE, FYI_MESSAGE, OK, getMessage(_code, _parms));
            show(_c, messagePnl, true);
            out = messagePnl.getText();
            clear();
        }
        return out;
    }

    /**
     * showAdminMsg
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void showAdminMsg(String _s) {
        if (!messagePnl.isShowing()) {
            messagePnl.construct(MESSAGE_TYPE, FYI_MESSAGE, OK, _s);
            show(getLogin(), messagePnl, true);
            clear();
        }
    }

    /**
     * show a modal error message
     * should be preceeded by the standard message manipulation
     * of setCode(String) and if applicable one of the setParms()
     *
     * @param _c
     */
    public void showError(Component _c) {
        if (!messagePnl.isShowing()) { //51038
            messagePnl.construct(MESSAGE_TYPE, ERROR_MESSAGE, OK, getMessage());
			if (_c == null){ // RQ0713072645
				_c = parent;
			}
            show(_c, messagePnl, true);
            clear();
        } //51038
    }

    /**
     * showError
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _code
     */
    public void showError(Component _c, String _code) {
        if (!messagePnl.isShowing()) { //51038
            setTitle(_code);
            setCode(_code);
            messagePnl.construct(MESSAGE_TYPE, ERROR_MESSAGE, OK, getMessage());
            show(_c, messagePnl, true);
            clear();
        } //51038
    }

    /**
     * showError
     * 24423680
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _code
     * @param _parms
     */
    public void showError(Component _c, String _code, String[] _parms) {
        if (!messagePnl.isShowing()) {
            setTitle(_code);
            messagePnl.construct(MESSAGE_TYPE, ERROR_MESSAGE, OK, getMessage(_code,_parms));
            show(_c, messagePnl, true);
            clear();
        }
    }

    /**
     * show a modal confirmation message
     * should be preceeded by the standard message manipulation
     * of setCode(String) and if applicable one of the setParms()
     *
     * @parms int _buttons => the buttons to be displayed on the dialog
     * @return int
     * @param _buttons
     * @param _c
     * @param _clear
     */
    public int showConfirm(Component _c, int _buttons, boolean _clear) {
        int out = -1;
        if (!messagePnl.isShowing()) { //51038
            messagePnl.construct(CONFIRM_TYPE, QUESTION_MESSAGE, _buttons, getMessage());
            show(_c, messagePnl, true);
            out = messagePnl.getResponse();
            if (_clear) {
                clear();
            }
        } //51038
        return out;
    }

    /**
     * showConfirm
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _buttons
     * @param _code
     * @param _clear
     * @return
     */
    public int showConfirm(Component _c, int _buttons, String _code, boolean _clear) {
        int out = -1;
        if (!messagePnl.isShowing()) { //51038
            setTitle(_code);
            setCode(_code);
            messagePnl.construct(CONFIRM_TYPE, QUESTION_MESSAGE, _buttons, getMessage());
            show(_c, messagePnl, true);
            out = messagePnl.getResponse();
            if (_clear) {
                clear();
            }
        } //51038
        return out;
    }

    /**
     * show a modal input message
     * should be preceeded by the standard message manipulation
     * of setCode(String) and if applicable one of the setParms()
     *
     * @return String
     * @param _c
     */
    public String showInput(Component _c) {
        String out = null;
        if (!messagePnl.isShowing()) { //51038
            messagePnl.construct(INPUT_TYPE, QUESTION_MESSAGE, OK_CANCEL, getMessage());
            show(_c, messagePnl, true);
            out = messagePnl.getText();
            clear();
        } //51038
        return out;
    }

    /**
     * the generic message dialog, constructs a message panel based on the passed in data
     *
     * @parm _dialogType => the type of dialog
     * @parm _msgType => the icon that should be displayed on the dialog
     * @parm _buttons => the buttons that should be displayed on the dialog
     * @parm _reset => should the message be reset
     * @param _buttons
     * @param _c
     * @param _dialogType
     * @param _msgType
     * @param _reset
     */
    public void show(Component _c, int _dialogType, int _msgType, int _buttons, boolean _reset) {
        if (!messagePnl.isShowing()) { //51038
            messagePnl.construct(_dialogType, _msgType, _buttons, getMessage());
            show(_c, messagePnl, true);
            if (_reset) {
                clear();
            }
        } //51038
    }

    /**
     * show
     * @author Anthony C. Liberto
     * @param _c
     * @param _dc
     * @param _modal
     */
    public void show(Component _c, DisplayableComponent _dc, boolean _modal) {
        if (_dc.isShowing()) { //51169
            _dc.toFront(); //51669
        } else { //51169
            Window win = getParentWindow(_c);
            if (_modal) {
                showDialog(win, _dc);
            } else {
                if (_dc == xmlPnl) {
                    showXMLFrame(win, _dc);
                } else {
                    showFrame(win, _dc);
                }
            }
        } //51169
    }

    private void showDialog(Window _win, DisplayableComponent _dc) {
        ModalDialog mDialog = getModalDialog(_win, _dc);
        _dc.setParentDialog(mDialog);
        mDialog.setTitle(_dc.getTitle());
        mDialog.setOwner(_win);
        if (mDialog.isShowing()) {
            mDialog.toFront();
        } else {
            mDialog.show(_dc);
        }
        mDialog.getAccessibleContext().setAccessibleDescription(_dc.getAccessibleDescription());
    }

    private ModalDialog getModalDialog(Window _win, DisplayableComponent _dc) {
        //50621		if (!fd.isEmpty()) {
        ModalDialog mDialog = null;
        if ((_win == null || _win == parent) && !fd.isEmpty()) { //50621
            Object[] o = md.toArray();
            if (o != null) {
                int ii = o.length;
                for (int i = 0; i < ii; ++i) {
                    if (!((ModalDialog) o[i]).isShowing(_dc)) {
                        return (ModalDialog) o[i];
                    }
                }
            }
        }
        if (_win != null && _win instanceof FrameDialog) {
            return ModalDialog.createDialog((FrameDialog) _win);
        }
        mDialog = ModalDialog.createDialog(parent);
        md.add(mDialog);
        return mDialog;
    }

    private void showFrame(Window _win, DisplayableComponent _dc) {
        FrameDialog fDialog = getFrameDialog(_win, _dc);
        _dc.setParentDialog(fDialog);
        fDialog.setTitle(_dc.getTitle());
        fDialog.setIconImage(getImage(_dc.getIconName()));
        fDialog.setOwner(_win);
        if (fDialog.isShowing()) {
            fDialog.toFront();
        } else {
            fDialog.show(_dc);
            fDialog.toFront(); //51869
        }
        fDialog.getAccessibleContext().setAccessibleDescription(_dc.getAccessibleDescription());
    }

    private FrameDialog getFrameDialog(Window _win, DisplayableComponent _dc) {
        //50621		if (!fd.isEmpty()) {
        FrameDialog fDialog = null;
        if ((_win == null || _win == parent) && !fd.isEmpty()) { //50621
            Object[] o = fd.toArray();
            int ii = o.length;
            for (int i = 0; i < ii; ++i) {
                if (!((FrameDialog) o[i]).isShowing(_dc)) {
                    return (FrameDialog) o[i];
                }
            }
        }
        if (_win != null && _win instanceof FrameDialog) {
            return new FrameDialog((FrameDialog) _win);
        }
        fDialog = new FrameDialog(parent);
        fd.add(fDialog);
        return fDialog;
    }
    /*
     51260
     */
    /**
     * showException
     *
     * @author Anthony C. Liberto
     * @param _x
     * @param _c
     * @param _icon
     * @param _buttons
     * @return
     */
    public int showException(Exception _x, Component _c, int _icon, int _buttons) {
        return showException(_x, _c, _icon, _buttons, false);
    }

    private int showException(Exception _x, Component _c, int _icon, int _buttons, boolean _simple) {
        int out = -1;
        String strMsg = null;
        clear();
        strMsg = (_simple) ? simplify(_x.toString()) : _x.toString();
        if (isOKMessage(strMsg)) {
            //TIR USRO-R-JSTT-67HTKR			messagePnl.construct(CONFIRM_TYPE,_icon,OK,getMessage());
            messagePnl.constructException(CONFIRM_TYPE, _icon, OK, getMessage());
            show(_c, messagePnl, true);
            out = IGNORE;
        } else {
            //TIR USRO-R-JSTT-67HTKR			messagePnl.construct(CONFIRM_TYPE,_icon,_buttons,getMessage());
            messagePnl.constructException(CONFIRM_TYPE, _icon, _buttons, getMessage());
            show(_c, messagePnl, true);
            out = messagePnl.getResponse();
        }
        clear();
        _x.printStackTrace();
        logError(_x.toString(), out);
        return out;
    }

    private boolean isOKMessage(String _msg) {
        //51967		if (_msg.endsWith(OK_ERROR)) {
        //51967			setMessage(_msg.substring(0,_msg.length() - 4));
        //51967			return true;
        //51967		}
        int index = _msg.lastIndexOf(OK_ERROR); //51967
        if (index > 0) { //51967
            //52350			setMessage(_msg.substring(0,index));		//51967
            setMessage(Routines.replace(_msg, OK_ERROR, "")); //52350
            return true; //51967
        } //51967
        setMessage(_msg);
        return false;
    }

    private String simplify(String _msg) {
        int index_colon = _msg.indexOf(EXCEPTION_SEARCH);
        if (index_colon > 0) {
            int adjust = EXCEPTION_SEARCH.length();
            return _msg.substring((index_colon + adjust), _msg.length()).trim();
        }
        return _msg;
    }

    private void logError(String _s, int _i) {
        appendLog("Error --> " + _s.trim() + " {User selected (" + _i + ")}");
    }

    /*
     51298
    */
    //rst_update 	public EANActionItem[] getExecutableActionItems(EntityGroup _eg) {
    /**
     * getExecutableActionItems
     *
     * @author Anthony C. Liberto
     * @param _eg
     * @param _table
     * @return
     */
    public EANActionItem[] getExecutableActionItems(EntityGroup _eg, RowSelectableTable _table) { //rst_update
        if (_table != null) {
			if (isMonitor()) {
				monitor("action(s) on " + _eg.getEntityType(),_table.toString());
			}
            Vector v = new Vector();
            int ii = _table.getRowCount();
            boolean bTmpPast = isPast();
            boolean bHasData = _eg.getEntityItemCount() > 0; //peer_create
			if (isMonitor()) {
				monitor("    rows", "" + ii);
				monitor("    past", "" + bTmpPast);
				monitor("    data", "" + bHasData);
			}
            for (int i = 0; i < ii; ++i) {
				Object o = _table.getRow(i);
                if (o instanceof EANActionItem) {
					if (isMonitor()) {
						monitor("        processing action " + i + " of " + ii,((EANActionItem)o).getKey());
					}
					if (isArmed(SHOW_ALL_ARM_FILE)) {
                        v.add((EANActionItem) o);
                    } else if (!bTmpPast && !bHasData) { //peer_create
                        if (o instanceof CreateActionItem && (((CreateActionItem) o).isPeerCreate() || ((CreateActionItem) o).isStandAlone())) { //peer_create_2
                            v.add((EANActionItem) o); //peer_create
                        } else if (o instanceof NavActionItem) { //DWB_20040908
                            NavActionItem nav = (NavActionItem) o; //DWB_20040908
                            if (nav.isPicklist() && nav.useRootEI()) { //DWB_20040908
                                v.add((NavActionItem) o); //DWB_20040908
                            } //DWB_20040908
                        } else if (o instanceof SearchActionItem) { //3.0a
                            if (((SearchActionItem) o).isParentLess()) { //3.0a
                                v.add((EANActionItem) o); //3.0a
                            } //3.0a
                        } else if (o instanceof LinkActionItem) { //3.0a
                            if (((LinkActionItem) o).isOppSelect()) { //3.0a
                                v.add((EANActionItem) o); //3.0a
                            } //3.0a
                        } //peer_create
                    } else if (isExecutableAction((EANActionItem) o, bTmpPast)) {
                        v.add((EANActionItem) o);
                    }
                }
            }
            if (!v.isEmpty()) {
                return (EANActionItem[]) v.toArray(new EANActionItem[v.size()]);
            }
        } else {
			if (isMonitor()) {
				monitor("actionLoad","table null");
			}
		}
        return null;
    }

    /**
     * isExecutableAction
     *
     * @author Anthony C. Liberto
     * @param _ean
     * @param _bPast
     * @return
     */
    public boolean isExecutableAction(EANActionItem _ean, boolean _bPast) {
        if (_bPast) {
            if (_ean instanceof LinkActionItem) {
                return false;
            } else if (_ean instanceof CreateActionItem) {
                return false;
            } else if (_ean instanceof DeleteActionItem) {
                return false;
            } else if (_ean instanceof LockActionItem) {
                return false;
            } else if (_ean instanceof NavActionItem) {
                if (((NavActionItem) _ean).isPicklist()) {
                    return false;
                }
            }
        }
        return true;
    }

    //rst_update	public String getActionTitle(EntityGroup _eg) {
    /**
     * getActionTitle
     *
     * @author Anthony C. Liberto
     * @param _eg
     * @param _table
     * @return
     */
    public String getActionTitle(EntityGroup _eg, RowSelectableTable _table) { //rst_update
        if (_eg != null && _table != null) {
            if (isTestMode()) {
                ActionGroup ag = _eg.getActionGroup();
                return _table.getTableTitle() + ((ag == null) ? "" : (" (" + ag.getKey() + ")"));
            }
            return _table.getTableTitle();
        }
        return null;
    }

    /**
     * isEditable
     *
     * @author Anthony C. Liberto
     * @param _table
     * @return
     */
    public boolean isEditable(RowSelectableTable _table) {
        if (isPast()) {
            return false;
        }
        if (_table != null) {
            return _table.canEdit();
        }
        return false;
    }

    /**
     * isTestMode
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static boolean isTestMode() {
        return isArmed(TEST_MODE_ARM_FILE);
    }

    /**
     * isCaptureMode
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static boolean isCaptureMode() {
        return isArmed(CAPTURE_MODE_ARM_FILE);
    }

	/**
	 capture the passed in string
	 @param _s
	 @author tony
	 */
    public void capture(String _s) {
		if (_s != null) {
			String fileName = gio().getFileName(FileDialog.SAVE);
			if (fileName != null) {
				gio().write(fileName, _s);
			}
		}
	}

    /**
     * isAccessible
     *
     * @author Anthony C. Liberto
     * @return
     */
    public static boolean isAccessible() {
		return isArmed(ACCESSIBLE_ARM_FILE);
	}

    /**
     * isDebug
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isDebug() {
        return isArmed(DEBUG_ARM_FILE);
    }
    /*
     51325
    */
    /**
     * updateBookmarkAction
     *
     * @author Anthony C. Liberto
     * @param _item
     */
    public void updateBookmarkAction(EANActionItem _item) {
        if (bMarkPnl != null && bMarkPnl.isShowing() && _item instanceof NavActionItem) {
            bMarkPnl.setActionItem(_item);
        }
    }
    /*
     crypt
     */

    /**
     * encryptString
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     *
    public byte[] encryptString(String _s) {
        return cipher().encryptString(_s);
    }

    /**
     * decryptString
     *
     * @author Anthony C. Liberto
     * @param _b
     * @return
     *
    public String decryptString(byte[] _b) {
        return cipher().decryptString(_b);
    }

    /**
     * encryptToString
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     *
    public String encryptToString(String _s) {
        return cipher().encryptToString(_s);
    }

    /**
     * decryptString
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     *
    public String decryptString(String _s) {
        return cipher().decryptString(_s);
    }

    /*
     log parse
     */
    /**
     * selectRole
     *
     * @author Anthony C. Liberto
     * @param _enterprise
     * @param _roleCode
     * @param _opwg
     */
    public void selectRole(String _enterprise, String _roleCode, int _opwg) {
        Profile[] prof = profSet.toArray();
        int ii = prof.length;
        int index = -1;
        appendLog("*. " + _enterprise + ", " + _roleCode + ", " + _opwg);
        for (int i = 0; i < ii; ++i) {
            appendLog(i + ". " + prof[i].getEnterprise() + ", " + prof[i].getRoleCode() + ", " + prof[i].getOPWGID());
            if (prof[i].getEnterprise().equals(_enterprise)) {
                if (prof[i].getRoleCode().equals(_roleCode)) {
                    if (prof[i].getOPWGID() == _opwg) {
                        parent.profileSelected(prof[i], false);
                        appendLog("   Matching profile found.");
                        return;
                    }
                }
            }
        }
        while (index < 0 || index >= ii) {
            index = getNumber("msg12006.0");
        }
        parent.profileSelected(prof[index], false);
    }

    /**
     * selectTab
     *
     * @author Anthony C. Liberto
     * @param _tabType
     * @param _key
     * @param _opwg
     * @return
     */
    public ETabable selectTab(String _tabType, String _key, int _opwg) {
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            ETabable tab = getTab(i);
            if (tab != null) {
                if (tab.getPanelType().equals(_tabType)) {
                    if (tab.getParentKey().equals(_key)) {
                        if (tab.getOPWGID() == _opwg) {
                            setSelectedIndex(i);
                            return tab;
                        }
                    }
                }
            }
        }
        return null;
    }

    /*
     acl_20030715
     */
    /**
     * generateScript
     *
     * @author Anthony C. Liberto
     * @param _url
     * @return
     */
    public String generateScript(String _url) {
        String strFileName = null;
        String strMsg = null;
        clear();
        setCode("vb.script");
        setParm(_url);
        //acl_20031006		String strFileName = TEMP_DIRECTORY + getNow(DATE_TIME_ONLY) + VB_SCRIPT_EXTENSION;
        strFileName = TEMP_DIRECTORY + Routines.getRandomString() + VB_SCRIPT_EXTENSION; //acl_20031006
        strMsg = getMessage();
        appendLog("EAccess.generateScript script fn:" + strFileName + " msg:" + strMsg);
        gio().writeString(strFileName, strMsg);
        gio().deleteOnExit(strFileName);
        return strFileName;
    }

    /**
     * getOS
     *
     * @author Anthony C. Liberto
     * @return
     */
    public int getOS() {
        String os = System.getProperty("os.name");
        if (os != null) {
            if (os.equals(WINDOWS_2000)) {
                return OS_WINDOWS_2000;
            } else if (os.equals(WINDOWS_XP)) {
                return OS_WINDOWS_XP;
            } else if (os.startsWith(WINDOWS)) {
                return OS_WINDOWS;
            }
        }
        return OS_OTHER;
    }

    /**
     * isStandardClient
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isStandardClient() {
        int ios = getOS();
        return ios >= OS_MINIMUM_CLIENT;
    }

    /**
     * isWindows
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isWindows() {
        return  getOS() >= OS_WINDOWS;
        //windows LNF does not render on win7
    }
    /*
     51555
     */
    /**
     * refresh
     *
     * @author Anthony C. Liberto
     * @param _inAct
     * @param _prof
     * @param _code
     */
    public void refresh(InactiveItem[] _inAct, Profile _prof, int _code) {
        int ii = getTabCount();
        if (_inAct != null) { //53771:600C35
            int xx = _inAct.length;
            for (int i = 0; i < ii; ++i) {
                ETabable tab = getTab(i);
                if (tab != null && tab.getPanelType().equals(TYPE_ENAVFORM)) {
                    for (int x = 0; x < xx; ++x) {
                        tab.setShouldRefresh(_inAct[x].getEntityType(), _prof.getOPWGID(), getCode(_inAct[x]));
                    }
                }
            }
        } //53771:600C35
    }

    private int getCode(InactiveItem _inAct) {
        if (_inAct.getEntityClass().equals("Relator")) {
            return RELATOR_TYPE;
        }
        return CHILD_TYPE;
    }
    /*
     dwb_20030821
     */
    /**
     * showException
     *
     * @author Anthony C. Liberto
     * @param _x
     * @param _c
     * @param _icon
     * @param _buttons
     * @return
     */
    public int showException(String _x, Component _c, int _icon, int _buttons) {
        return showException(_x, _c, _icon, _buttons, false);
    }

    private int showException(String _x, Component _c, int _icon, int _buttons, boolean _simple) {
        int out = -1;
        String strMsg = null;
        clear();
        strMsg = (_simple) ? simplify(_x) : _x;
        if (isOKMessage(strMsg)) {
            messagePnl.construct(CONFIRM_TYPE, _icon, OK, getMessage());
            show(_c, messagePnl, true);
            out = IGNORE;
        } else {
            messagePnl.construct(CONFIRM_TYPE, _icon, _buttons, getMessage());
            show(_c, messagePnl, true);
            out = messagePnl.getResponse();
        }
        clear();
        logError(_x, out);
        return out;
    }

    /**
     * load
     *
     * @author Anthony C. Liberto
     * @param _parTab
     * @param _history
     * @param _entityList
     * @param _navType
     * @param _gif
     * @param _prof
     * @return
     */
    public ENavForm load(ETabable _parTab, Object _history, Object _entityList, int _navType, String _gif, Profile _prof) {
        if (_history != null && _entityList != null) {
            return parent.load(_parTab, _history, _entityList, _navType, _gif, _prof);
        }
        return null;
    }

    //cr_1210035324	public void load(eTabable _parTab, Object _o, String _gif) {
    /**
     * load
     *
     * @author Anthony C. Liberto
     * @param _parTab
     * @param _o
     * @param _book
     * @param _gif
     */
    public void load(ETabable _parTab, Object _o, BookmarkItem _book, String _gif) { //cr_1210035324
        if (_o != null) {
            parent.load(_parTab, _o, _book, _gif); //cr_1210035324
            //cr_1210035324			parent.load(_parTab, _o, _gif);
        }
    }

    /*
     52051
    */
    /**
     * getClipboardString
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getClipboardString() {
        Transferable trans = getClipboardContents();
        StringBuffer out = null;
        if (trans == null) {
            return null;
        }
        out = new StringBuffer();
        try {
            Reader read = DataFlavor.stringFlavor.getReaderForText(trans);
            BufferedReader inStream = new BufferedReader(read);
            try {
                String temp = null;
                while ((temp = inStream.readLine()) != null) {
                    out.append(temp);
                }
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            } finally {
                inStream.close();
                read.close();
            }
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } catch (UnsupportedFlavorException _ufe) {
            _ufe.printStackTrace();
        }
        return out.toString();
    }
    /*
     mem
     */
    /**
     * setAutoGC
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setAutoGC(boolean _b) {
        bAutoGC = _b;
    }

    /**
     * isAutoGC
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isAutoGC() {
        return bAutoGC;
    }

    /*
     bookmark_filter
     */
    /**
     * isBookmarkFilterGroup
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isBookmarkFilterGroup() {
        return getPrefBoolean(PREF_BOOKMARK_FILTER, DEFAULT_BOOKMARK_FILTER);
    }

    /**
     * getLockOwner
     *
     * @author Anthony C. Liberto
     * @return
     */
    public EntityItem getLockOwner() {
        EntityItem lockOwnerEI = null;
        try {
			lockOwnerEI = (EntityItem)lockownerTbl.get(""+getActiveProfile().getOPWGID());
			if (lockOwnerEI==null){
				lockOwnerEI = new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
				lockownerTbl.put(""+getActiveProfile().getOPWGID(),lockOwnerEI);
			}
        } catch (Exception _x) {
            report(_x,false);
        }
        return lockOwnerEI;
    }

    /*
     kehrli_20030929
     */
    /**
     * addTab
     *
     * @author Anthony C. Liberto
     * @param _parTab
     * @param _tabComponent
     */
    public void addTab(ETabable _parTab, ETabable _tabComponent) {
        ePane.addTab(_parTab, _tabComponent);
    }

    /**
     * addTab
     *
     * @author Anthony C. Liberto
     * @param _parTab
     * @param _tabComponent
     * @param _icon
     */
    public void addTab(ETabable _parTab, ETabable _tabComponent, String _icon) {
        ePane.addTab(_parTab, _tabComponent, (_icon == null) ? null : getImageIcon(_icon));
    }

    /**
     * addTab
     *
     * @author Anthony C. Liberto
     * @param _parTab
     * @param _tabComponent
     * @param _icon
     * @param _title
     */
    public void addTab(ETabable _parTab, ETabable _tabComponent, String _icon, String _title) {
        ePane.addTab(_parTab, _tabComponent, (_icon == null) ? null : getImageIcon(_icon), _title);
    }

    /**
     * setIconAt
     *
     * @author Anthony C. Liberto
     * @param _tab
     * @param _icon
     */
    public void setIconAt(ETabable _tab, String _icon) {
        if (ePane != null) {
            ePane.setIconAt(_tab, (_icon == null) ? null : getImageIcon(_icon));
            ePane.revalidate();
        }
    }

    /**
     * setIconAt
     *
     * @author Anthony C. Liberto
     * @param _tab
     * @param _icon
     */
    public void setIconAt(ETabable _tab, Icon _icon) {
        if (ePane != null) {
            ePane.setIconAt(_tab, _icon);
            ePane.revalidate();
        }
    }

    /*
     acl_20031007
     */
    /**
     * canOverrideColor
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean canOverrideColor() {
        //		return false;
        return getPrefBoolean(PREF_COLOR_OVERRIDE, DEFAULT_COLOR_OVERRIDE);
    }

    /*
     52476
     */
    /**
     * setHidden
     *
     * @author Anthony C. Liberto
     * @param _b
     */
    public void setHidden(boolean _b) {
        if (parent != null) {
            parent.setHidden(_b);
        }
    }

    /*
     52531
     */
    /**
     * setActiveProfile
     *
     * @author Anthony C. Liberto
     * @param _pSet
     * @param _prof
     * @return
     */
    public ProfileSet setActiveProfile(ProfileSet _pSet, Profile _prof) {
        NLSItem langRead = null;
        NLSItem langWrite = null;
        if (_pSet != null && _prof != null) {
            int ii = _pSet.size();
            for (int i = 0; i < ii; ++i) {
                Profile prof = _pSet.elementAt(i);
                if (prof != null) {
                    if (prof.getEnterprise().equals(_prof.getEnterprise())) {
                        if (prof.getOPWGID() == _prof.getOPWGID()) {
                            _pSet.setActiveProfile(prof);
                            langRead = _prof.getReadLanguage(); //USRO-R-JTOY-62WKFV
                            if (langRead != null) { //USRO-R-JTOY-62WKFV
                                prof.setReadLanguage(langRead); //USRO-R-JTOY-62WKFV
                            } //USRO-R-JTOY-62WKFV
                            langWrite = _prof.getWriteLanguage(); //USRO-R-JTOY-62WKFV
                            if (langWrite != null) { //USRO-R-JTOY-62WKFV
                                prof.setWriteLanguage(langWrite); //USRO-R-JTOY-62WKFV
                            } //USRO-R-JTOY-62WKFV
                            //USRO-R-JTOY-62WKFV							prof.setReadLanguage(_prof.getReadLanguage());			//53701
                            //USRO-R-JTOY-62WKFV							prof.setWriteLanguage(_prof.getWriteLanguage());		//53701
                            return _pSet;
                        }
                    }
                }
            }
        }
        return _pSet;
    }

    /*
     52682
     */
    /**
     * isReadOnly
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @return
     */
    public boolean isReadOnly(Profile _prof) {
        if (_prof != null) {
            return _prof.isReadOnly();
        }
        return false;
    }

    /**
     * isVertFlagFrame
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isVertFlagFrame() {
        return getPrefBoolean(PREF_VERT_FLAG_FRAME, isArmed(FLOATABLE_FLAG_DEFAULT));
    }

    /*
     52796
     */
    /**
     * validate
     *
     * @author Anthony C. Liberto
     */
    public void validate() {
        if (parent != null) {
            parent.validate();
        }
    }

    /*
     cr_persistant_cart
     */
    /**
     * getCartList
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @param _create
     * @return
     */
    public CartList getCartList(Profile _prof, boolean _create) {
        if (cart == null) {
            cart = new PersistantCart();
        }
        return cart.getCartList(_prof, _create);
    }

    /**
     * getPersistantCart
     *
     * @author Anthony C. Liberto
     * @return
     */
    public PersistantCart getPersistantCart() {
        if (cart == null) {
            cart = new PersistantCart();
        }
        return cart;
    }

    /*
     memory
     */
    /*
     write an object to the file system
     */
    /**
     * writeObject
     *
     * @author Anthony C. Liberto
     * @param _fileName
     * @param _o
     */
    public void writeObject(String _fileName, Object _o) {
        File f = null;
        FileOutputStream fout = null;
        ObjectOutputStream outStream = null;
        try {
            f = new File(TEMP_DIRECTORY + _fileName);
            f.deleteOnExit();
            fout = new FileOutputStream(f);
            outStream = new ObjectOutputStream(fout);
            outStream.writeObject(_o);
            outStream.reset();			//JTest
            outStream.flush();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (outStream != null) {
		            outStream.close();
				}
				if (fout != null) {
		            fout.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
    }

    /*
     reads an object from the file system
     */
    /**
     * readObject
     *
     * @author Anthony C. Liberto
     * @param _fileName
     * @return
     */
    public Object readObject(String _fileName) {
		File f = null;
		FileInputStream fin = null;
		ObjectInputStream inStream = null;
        Object out = null;
        try {
            f = new File(TEMP_DIRECTORY + _fileName);
            fin = new FileInputStream(f);
            inStream = new ObjectInputStream(fin);
            out = inStream.readObject();
        } catch (ClassNotFoundException _cnf) {
            _cnf.printStackTrace();
        } catch (FileNotFoundException _fnf) {
            _fnf.printStackTrace();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
				if (inStream != null) {
					inStream.close();
				}
				if (fin != null) {
					fin.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return out;
    }

    /*
     reads a specific variable from targeted object
     */
    /**
     * readVariable
     *
     * @author Anthony C. Liberto
     * @param _fileName
     * @param _objName
     * @return
     */
    public Object readVariable(String _fileName, String _objName) {
		FileInputStream fin = null;
		ObjectInputStream inStream = null;
        Object out = null;
        try {
            fin = new FileInputStream(TEMP_DIRECTORY + _fileName);
            inStream = new ObjectInputStream(fin);
            out = inStream.readFields().get(_objName, (Object) null);
        } catch (ClassNotFoundException _cnf) {
            _cnf.printStackTrace();
        } catch (FileNotFoundException _fnf) {
            _fnf.printStackTrace();
        } catch (IOException _ioe) {
            _ioe.printStackTrace();
        } finally {
			try {
            	if (inStream != null) {
            		inStream.close();
				}
				if (fin != null) {
		            fin.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
        return out;
    }
    /*
     cr3274
     */

    /**
     * alwaysRefresh
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean alwaysRefresh() {
        int iPref = getPrefInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE);
        int iTest = getInt("refType.always");
        return iPref == iTest;
    }

    /**
     * neverRefresh
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean neverRefresh() {
        int iPref = getPrefInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE);
        int iTest = getInt("refType.never");
        return iPref == iTest;
    }

    /**
     * promptRefresh
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean promptRefresh() {
        int iPref = getPrefInt(PREF_REFRESH_TYPE, DEFAULT_REFRESH_TYPE);
        int iTest = getInt("refType.prompt");
        return iPref == iTest;
    }

    /*
     acl_20031211
     */
    /**
     * reset
     *
     * @author Anthony C. Liberto
     */
    public void reset() {
        if (bMarkPnl != null) {
            bMarkPnl.reset();
        }
        if (stClient != null) {
            if (stClient.isShowing()) {
                stClient.hide();
            }
            stClient.stop();
        }
    }

    /*
     53562
     */
    /**
     * setTitleAt
     *
     * @author Anthony C. Liberto
     * @param _tab
     * @param _title
     */
    public void setTitleAt(ETabable _tab, String _title) {
        if (ePane != null && _tab != null) {
            ePane.setTitleAt(_tab, _title);
            ePane.revalidate();
        }
    }

    /*
     1.3 Chat
     */
    /**
     * getUserName
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getUserName() {
        return EServerProperties.getUserName();
    }

    /**
     * setUserName
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setUserName(String _s) {
        EServerProperties.setUserName(_s);
        EServerProperties.setBehindFirewall(getPrefBoolean(PREF_BEHIND_FIREWALL, DEFAULT_BEHIND_FIREWALL));
    }

    /**
     * loginSametime
     *
     * @author Anthony C. Liberto
     * @param _frame
     * @param _user
     * @param _pass
     */
    public void loginSametime(JFrame _frame, String _user, String _pass) {
        String[] strParms = { System.getProperty("eannounce.middleware.ip")};
        System.out.println("EAccess.loginSametime logging into sametime");

        if (stClient == null) {
            if (_frame == null) {
                stClient = new SametimeClientUI(parent, "Sametime Client", isArmed(SAMETIME_ADMIN_ARM_FILE)) {
                	private static final long serialVersionUID = 1L;
                	public void sametimeStarted() {
                        parent.validateChat();
                    }
                    public void showAdminMessage(String _s) {
                        showAdminMsg(_s);
                    }
                    public String getAdminMsg() {
                        String[] parms = null;
                        return showInput("eannounce.admin.message", parms, getLogin().getComponent());
                    }
                };
            } else {
                stClient = new SametimeClientUI(_frame, "Sametime Client", isArmed(SAMETIME_ADMIN_ARM_FILE)) {
                	private static final long serialVersionUID = 1L;
                	public void sametimeStarted() {
                        parent.validateChat();
                    }
                    public void showAdminMessage(String _s) {
                        showAdminMsg(_s);
                    }
                    public String getAdminMsg() {
                        String[] parms = null;
                        return showInput("eannounce.admin.message", parms, getLogin().getComponent());
                    }
                };
            }
        }
        if (!stClient.loggedIn()) {
            //			stClient.start(getString("eannounce.messaging.host"),_user,_pass,getString("eannounce.messaging.place"));
            stClient.start(getString("eannounce.messaging.host"), _user, _pass, getMessage("eannounce.messaging.place", strParms));
        }
    }

    /**
     * validateChat
     *
     * @author Anthony C. Liberto
     */
    public void validateChat() {
        System.out.println("EAccess.validateChat ");
        if (parent != null) {
            parent.validateChat();
        }
    }

    /**
     * showRemoteChat
     *
     * @author Anthony C. Liberto
     */
    public void showRemoteChat() {
        if (stClient != null) {
            if (stClient.isShowing()) {
                stClient.toFront();
            } else {
                stClient.show();
            }
        }
    }

    /*
     called only if you have an invalid token.
     will log you in to the chat server and
     trigger the check for update.
     */
    /**
     * invalidTokenUpdate
     *
     * @author Anthony C. Liberto
     */
    protected void invalidTokenUpdate() {
        String strValOn = getNow(END_OF_DAY);
        String strEffOn = strValOn;
        Blob bUpdate = null;
        if (strValOn != null && strEffOn != null) {
            EProgress prog = getProgress(); //dwb_20050119
            prog.setMessage(getString("download")); //dwb_20050119
            prog.show(); //dwb_20050119
            prog.center(); //dwb_20050119
            bUpdate = tBase.getSoftwareImage(null, strValOn, strEffOn, parent);
            prog.hide(); //dwb_20050119
            if (bUpdate != null) {
                getSoftwareImage(bUpdate, true);
                return;
            }
        }
        showError(parent, "msg1004.1");
    }

    /**
     * hasChat
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean hasChat() {
        if (stClient != null) {
            return stClient.loggedIn();
        }
        return false;
    }

    /**
     * disconnectChat
     *
     * @author Anthony C. Liberto
     */
    public void disconnectChat() {
        if (stClient != null) {
            stClient.stop();
        }
    }

    /**
     * perform
     *
     * @author Anthony C. Liberto
     * @param _action
     */
    public void perform(ChatAction _action) {
        System.out.println("EAccess.perform received chatAction....");
        if (_action != null) {
            EANActionItem action = null;
            EntityItem[] ei = null;
            if (_action.hasAction()) {
                Object o = _action.getAction();
                if (o != null && o instanceof EANActionItem) {
                    action = (EANActionItem) o;
                }
            }
            if (_action.hasSelection()) {
                Object o = _action.getSelectedItems();
                if (o != null && o instanceof EntityItem[]) {
                    ei = (EntityItem[]) o;
                }
            }
            if (action != null && ei != null) {
                perform(action, ei);
            } else {
                System.out.println("EAccess.perform action is null: " + (action == null));
                System.out.println("EAccess.perform ei is null: " + (ei == null));
            }
        }
    }

    private void perform(EANActionItem _action, EntityItem[] _ei) {
        System.out.println("EAccess.perform loading chatAction....");
        if (_action instanceof WhereUsedActionItem) {
            WhereUsedList list = dBase().rexec((WhereUsedActionItem) _action, _ei, parent);
            if (list != null) {
                System.out.println("EAccess.perform loading WhereUsed");
                parent.load(list, _ei, "chat.gif");
            }
        } else if (_action instanceof MatrixActionItem) {
            MatrixList list = dBase().rexec((MatrixActionItem) _action, _ei, parent);
            if (list != null) {
                System.out.println("EAccess.perform loading Matrix");
                parent.load(list, _ei, "chat.gif");
            }
        } else {
            EntityList list = getEntityList(_action, _ei, parent);
            if (list != null) {
                if (_action instanceof CreateActionItem) {
                    parent.load(list, _ei, "chat.gif");
                } else if (_action instanceof EditActionItem) {
                    parent.load(list, _ei, "chat.gif");
                } else {
                    parent.load(list, "chat.gif");
                }
            }
        }
    }

    /*
     accessibility
     */
    /**
     * showToolTipText
     *
     * @author Anthony C. Liberto
     * @param _comp
     * @param _str
     */
    public void showToolTipText(JComponent _comp, String _str) {
        Point pt = null;
        String oldTip = null;
        if (_comp == null) {
            _comp = getLogin().getComponent();
        }
        pt = _comp.getLocation();
        oldTip = _comp.getToolTipText();
        if (_str != null) {
            _comp.setToolTipText(_str);
        }
        ToolTipManager.sharedInstance().mouseMoved(new java.awt.event.MouseEvent(_comp, 0, 0L, 0, pt.x, pt.y, 0, false));
        if (_str != null) {
            _comp.setToolTipText(oldTip);
        }
    }

    /*
     MN_18928920
     */
    /**
     * isPast
     *
     * @author Anthony C. Liberto
     * @return
     */
    public boolean isPast() {
        return isPast(getActiveProfile(), getNow(END_OF_DAY, false));
    }

    /**
     * isPast
     *
     * @author Anthony C. Liberto
     * @param _prof
     * @param _now
     * @return
     */
    public boolean isPast(Profile _prof, String _now) {
        if (_prof != null) {
            String sTime = _prof.getValOn();
            if (sTime != null) {
                return !sTime.equals(_now);
            }
        }
        return false;
    }

    /**
     * updateProcessTime
     *
     * @author Anthony C. Liberto
     * @param _date
     */
    public void updateProcessTime(String _date) {
        String sNow = getNow(END_OF_DAY, false);
        int ii = getTabCount();
        for (int i = 0; i < ii; ++i) {
            ETabable tab = getTab(i);
            if (tab != null) {
                Profile prof = tab.getProfile();
                if (prof != null) {
                    if (!isPast(prof, sNow)) {
                        prof.setValOnEffOn(_date, _date);
                    }
                }
            }
        }
    }

    /**
     * getVersionParms
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String[] getVersionParms() {
        Date bldDate = parseDate(BUILD_DATE, Version.getDate());
        String[] parms = { formatDate(RELEASE_DATE, bldDate), formatDate(FORMATTED_DATE, bldDate), formatDate(BUILD_DATE, bldDate), formatDate(DATE_TIME_ONLY, bldDate)};
        return parms;
    }

    /**
     * getJarVersion
     *
     * @author Anthony C. Liberto
     * @return
     */
    public String getJarVersion() {
        return getJarVersion(UPDATED_DATE);
    }

    /**
     * getJarVersion
     *
     * @author Anthony C. Liberto
     * @param _s
     * @return
     */
    public String getJarVersion(String _s) {
        if (_s != null) {
	        Date bldDate = parseDate(BUILD_DATE, Version.getDate());
	        return formatDate(_s, bldDate);
		}
		return _s;
    }

    /*
     acl_20040518
     improved showInput functionality.
     */
    /**
     * showInput
     *
     * @author Anthony C. Liberto
     * @param _code
     * @param _parms
     * @param _c
     * @return
     */
    public String showInput(String _code, String[] _parms, Component _c) {
        String out = null;
        if (!messagePnl.isShowing()) {
            messagePnl.construct(INPUT_TYPE, QUESTION_MESSAGE, OK_CANCEL, getMessage(_code, _parms));
            show(_c, messagePnl, true);
            out = messagePnl.getText();
            clear();
        }
        return out;
    }

    /*
     cr_ActChain
     */
    /**
     * load
     *
     * @author Anthony C. Liberto
     * @param _o
     * @param _gif
     */
    public void load(Object _o, String _gif) {
        load(getCurrentTab(), _o, null, _gif);
    }

    /**
     * load
     *
     * @author Anthony C. Liberto
     * @param _tab
     * @param _o
     * @param _gif
     */
    public void load(ETabable _tab, Object _o, String _gif) {
        if (_tab != null && _o != null && parent != null) {
            parent.load(_tab, _o, null, _gif);
        }
    }
    /*
     5ZBTCQ
     */
    /**
     * show
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _panel
     * @param _modal
     */
    public void show(Component _c, int _panel, boolean _modal) {
        show(_c, getPanel(_panel), _modal);
    }
    /*
     loc_choose
     */
    /**
     * connectMiddleware
     *
     * @author Anthony C. Liberto
     * @param _mw
     * @return
     */
    public boolean connectMiddleware(MWObject _mw) {
        if (parent != null) {
            return parent.connectMiddleware(_mw);
        }
        return false;
    }

    /**
     * connect
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _mw
     * @return
     */
    public boolean connect(Component _c, MWObject _mw) {
        String sOverwrite = null;
        tBase = new ThinBase(getMWParser());
        if (isArmed(LOCATION_CHOOSER_ARM_FILE)) { //loc_chooser
            if (!_mw.isValid()) { //loc_chooser
                setTitle(getString("mw_connect"));
                setMessage(getString("msg13004.0"));
                showError(_c);
                return false; //loc_chooser
            } //loc_chooser
        } //loc_chooser
        while (!tBase.isRunning()) {
            try {
                tBase.databaseConnect(_mw, _c);
            } catch (RemoteException _re) {
                report(_re,false);
                return false;
            }
        }
        sOverwrite = System.getProperty(MW_PROPERTIES);
        if (Routines.have(sOverwrite)) {
            getOverwriteBundle(sOverwrite);
        }
        tTimer.setBase(tBase);
        verbose();
        return true;
    }

    /**
     * isArmed
     *
     * @author Anthony C. Liberto
     * @param _sFileName
     * @return
     */
    public static boolean isArmed(String _sFileName) {
        return isArmed(FUNCTION_DIRECTORY, _sFileName);
    }

    /**
     * isArmed
     *
     * @author Anthony C. Liberto
     * @param _sDirectory
     * @param _sFileName
     * @return
     */
    public static boolean isArmed(String _sDirectory, String _sFileName) {
        boolean out = false;
        if (_sDirectory != null && _sFileName != null) {
            File armFile = new File(_sDirectory + _sFileName);
            if (armFile != null) {
                out = armFile.exists();
            }
        }
        return out;
    }

    /**
     * arm
     *
     * @author Anthony C. Liberto
     * @param _sFileName
     * @return
     */
    public boolean arm(String _sFileName) {
        return arm(FUNCTION_DIRECTORY, _sFileName);
    }

    /**
     * arm
     *
     * @author Anthony C. Liberto
     * @param _sDirectory
     * @param _sFileName
     * @return
     */
    public boolean arm(String _sDirectory, String _sFileName) {
        if (_sDirectory != null && _sFileName != null) {
            File armFile = new File(_sDirectory + _sFileName);
            if (armFile != null) {
                try {
                    armFile.createNewFile();
                } catch (IOException _ioe) {
                    report(_ioe,false);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * disarm
     *
     * @param _sFileName
     * @return
     * @author Anthony C. Liberto
     */
    public boolean disarm(String _sFileName) {
        return disarm(FUNCTION_DIRECTORY, _sFileName);
    }

    /**
     * disarm
     *
     * @param _sDirectory
     * @param _sFileName
     * @return
     * @author Anthony C. Liberto
     */
    public boolean disarm(String _sDirectory, String _sFileName) {
        boolean out = false;
        if (_sDirectory != null && _sFileName != null) {
            File armFile = new File(_sDirectory + _sFileName);
            if (armFile != null) {
                out = armFile.delete();
                if (!out) {
                    armFile.deleteOnExit();
                }
            }
        }
        return out;
    }

    /*
     cr_4215
     */
    /**
     * always
     *
     * @param _s
     * @param _pref
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public boolean always(String _s, String _pref, int _def) {
        int iPref = getPrefInt(_pref, _def); //MN20005489
        //MN20005489		int iPref = getPrefInt(_pref,DEFAULT_REFRESH_TYPE);
        int iTest = getInt(_s + ".always");
        return iPref == iTest;
    }

    /**
     * never
     *
     * @param _s
     * @param _pref
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public boolean never(String _s, String _pref, int _def) {
        int iPref = getPrefInt(_pref, _def);
        int iTest = getInt(_s + ".never");
        return iPref == iTest;
    }

    /**
     * prompt
     *
     * @param _s
     * @param _pref
     * @param _def
     * @return
     * @author Anthony C. Liberto
     */
    public boolean prompt(String _s, String _pref, int _def) {
        int iPref = getPrefInt(_pref, _def);
        int iTest = getInt(_s + ".prompt");
        return iPref == iTest;
    }

    /**
     * showConfirm
     *
     * @param _c
     * @param _buttons
     * @param _code
     * @param _pref
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(Component _c, int _buttons, String _code, String _pref, int[] _i) {
        int out = -1;
        if (!messagePnl.isShowing()) {
            messagePnl.construct(CONFIRM_TYPE, QUESTION_MESSAGE, _buttons, getString(_code));
            show(_c, messagePnl, true);
            out = messagePnl.getResponse(_pref, _i);
            clear();
        }
        return out;
    }
    /*
     chgroup
     */
    /**
     * getActiveProfiles
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Profile[] getActiveProfiles() {
        int ii = getTabCount();
        Profile[] out = new Profile[ii];
        for (int i = 0; i < ii; ++i) {
            ETabable tab = getTab(i);
            if (tab != null) {
                out[i] = tab.getProfile();
            }
        }
        return out;
    }

    /**
     * hasChangeGroup
     *
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChangeGroup(Profile _prof) {
        if (_prof != null) {
            return _prof.getTranID() != 0;
        }
        return false;
    }

    /**
     * hasChangeGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChangeGroup() {
        return hasChangeGroup(getActiveProfile());
    }
    /*
     xl8r
     */
    /**
     * getFileChooser
     *
     * @return
     * @author Anthony C. Liberto
     */
    public JFileChooser getFileChooser() {
        if (chooser == null) {
            chooser = new JFileChooser();
        }
        return chooser;
    }

    /**
     * getImportString
     *
     * @param _c
     * @return
     * @author Anthony C. Liberto
     * /
    public String[] getImportString(Component _c) {
        String[] out = null;
        int reply = -1;
        getFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        reply = chooser.showOpenDialog(_c);
        if (reply == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
                out = gio.readStringArray(file.getAbsolutePath());
            }
        }
        return out;
    }*/

    /**
     * importFromFile
     *
     * @param _c
     */
    public void importFromFile(Importable _c) {
        int reply = -1;
        Component comp = null;
        if (_c instanceof Component){
        	comp = (Component)_c;
        }
        getFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // use old import method if arm file exists
        if (!EAccess.isArmed(XL8R_ARM_FILE)) {
        	String userdir = System.getProperty("user.dir");
        	//userdir = "C:\\dev\\eanc30\\export4.xls"; //testing only remove this
        	chooser.setAcceptAllFileFilterUsed(false);
        	if (userdir!=null){
        		chooser.setCurrentDirectory(new File(userdir)); 
        	}
        	chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
        		private String getExtension(File f) {
        			String ext ="";
        			if(f != null) {
        				String filename = f.getName();
        				int i = filename.lastIndexOf('.');
        				if(i>0 && i<filename.length()-1) {
        					ext= filename.substring(i).toLowerCase();
        				}
        			}
        			return ext;
        		}
        		public boolean accept(File f) {
        			if (f.isDirectory()){
        				return true;
        			}
        			String extension = getExtension(f);
   	        		if (extension.equals(".xls")) {
   	        			return true;
   	        		} else {
   	        			return false;
   	        		}
        		}

        		//The description of this filter
        		public String getDescription() {
        			return "Microsoft Excel Workbook (*.xls)";
        		}
        	});
        }// end arm file does not exist
 
        reply = chooser.showOpenDialog(comp);
        if (reply == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
            	try	{
            		// if user selected xls file then use new import code
                    if (file.getName().toLowerCase().endsWith(".xls")) {            		
                    	XLSImport xlsImport = new XLSImport(file.getAbsolutePath());
                    	xlsImport.process(_c);
                    	String warning = xlsImport.getWarnings();
                    	// display warnings
                    	if (warning.length()>0){
                    		setMessage(warning);
                    		showError(null);
                    	}                                         	
                    	xlsImport.dereference();
                    }else{
                    	_c.importString(gio.readStringArray(file.getAbsolutePath()));
                    }
        		}
        		catch (IOException e){
        			showException(e, comp,ERROR_MESSAGE,OK);
        		}      
            }
        }          
    }    
    /*
     update
     */

    /**
     * putSoftwareImage
     *
     * @author Anthony C. Liberto
     */
    public void putSoftwareImage() {
        final Worker myWorker = new Worker() {
            public void process() {
                String[] sFileName = gio().getFileNames(FileDialog.LOAD, UPDATE_EXTENSION);
                String strAttCode = "IMAGE_UPDATE";
                String _valChain = null;
                if (_valChain != null) {
                    strAttCode = _valChain;
                }
                if (sFileName != null) {
                    String sNow = getNow(ISO_DATE);
                    ControlBlock cBlock = new ControlBlock(sNow, FOREVER, sNow, FOREVER, 1);
                    int reply = showConfirm(parent, OK_CANCEL, "msg11026.2", true);
                    if (reply == OK) {
                        try {
                            Blob blob = new Blob("UP", "UPDATE", 1, strAttCode, sFileName[0] + sFileName[1], getJarVersion(), 1, cBlock);
                            tBase.putSoftwareImage("UP", blob, parent);
                        } catch (IOException _ioe) {
                            _ioe.printStackTrace();
                        }
                    }
                }
            }
            public void wrapup() {
                setBusy(false);
            }
            public String getMethodName() {
                return "putSoftwareImage";
            }

        };
        myWorker.start();
    }

    /**
     * autoCheckForUpdate
     *
     * @author Anthony C. Liberto
     */
    protected void autoCheckForUpdate() {
        final Worker myWorker = new Worker() {
            public void process() {
                if (isArmed(AUTO_UPDATE_FILE) && bCheckForUpdate) {
                    String strUpdate = getPrefString(UPDATE_TO_INSTALL, null);
                    bCheckForUpdate = false;
                    if (strUpdate == null) {
						String strVer = getJarVersion();											//6299
						String strExt = getUpdateExtension(strVer);									//6299
						if (isUpdateAvailable(strExt,strVer,false)) {								//6299
//6299                        if (isUpdateAvailable(false)) {
							if (MANDATORY_UPDATE) {
								getSoftwareImage(availableSoftwareUpdate(getValueChain()),true);
							} else if (isMandatory(strExt)) {										//6299
								getSoftwareImage(availableSoftwareUpdate(getValueChain()),true);	//6299
							} else {
	                            int reply = showConfirm(parent, YES_NO, "msg11026.5", true);
	                            if (reply == YES) {
	                                getSoftwareImage(availableSoftwareUpdate(getValueChain()), true);
	                            } else {
									appendLog(getMiddlewareVersions());
								}
							}
                        }
                    }
                }
            }

            public void wrapup() {
                setBusy(false);
            }

            public String getMethodName() {
                return "getSoftwareImage";
            }

        };
        myWorker.start();
    }

    /**
     * isUpdateAvailable
     * still called from elogin action, but that action may be deprecated
     * @param _msg
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isUpdateAvailable(boolean _msg) {
		String strCurVersion = getJarVersion();
        Blob b = tBase.getSoftwareImageVersion(getValueChain(), strCurVersion, parent);
        if (b != null) {
            String strBExtension = b.getBlobExtension();		//CR_6299
            appendLog("    APPLICATION_VERSION_CLIENT  : " + strCurVersion);
            appendLog("    APPLICATION_VERSION_DATABASE: " + strBExtension);
            if (strBExtension.compareTo(strCurVersion) > 0) {
                return true;
            }
        }
        if (_msg) {
            showFYI(parent, "up2date");
        }
        /*
         only do this when there is no update.
         */
        appendLog(getMiddlewareVersions());
        return false;
    }

    /**
     * based on the existing version get the blob extension
     * 6299
     * @param _sVer
     * @return String
     * @author tony
     */
    private String getUpdateExtension(String _sVer) {
		String sOut = null;
        Blob b = tBase.getSoftwareImageVersion(getValueChain(), _sVer, parent);
        if (b != null) {
			sOut = b.getBlobExtension();
		}
		return sOut;
	}

	/**
	 * based on the extension and the version determine if this is an update
	 * 6299
	 *
	 * @param extension
	 * @param version
	 * @param show message
	 * @return update exist
	 * @author tony
	 */
	private boolean isUpdateAvailable(String _sExt, String _sVer, boolean _msg) {
		if (_msg) {
			showFYI(parent,"up2date");
		}
		//<date>.mandatory
		StringTokenizer st = new StringTokenizer(_sExt,".");
		String sExt = null;
		if (st.hasMoreTokens()) {
			sExt = st.nextToken();
		} else {
			sExt = _sExt;
		}
		if (sExt.compareTo(_sVer) > 0) {
			return true;
		}
		appendLog(getMiddlewareVersions());
		return false;
	}

	/**
	 * based on extension determine if this is mandatory
	 * 6299
	 * @param extension
	 * @return mandatory
	 * @author tony
	 */
	public boolean isMandatory(String _sExt) {
		StringTokenizer st = new StringTokenizer(_sExt,".");
		//String sExt = null;
		String sSuf = null;
		if (st.hasMoreTokens()) {
		//	sExt =
			st.nextToken();
		}
		if (st.hasMoreTokens()) {
			sSuf = st.nextToken();
		}
		if (sSuf != null) {
			if (sSuf.equalsIgnoreCase(OPTIONAL)) {
				return false;
			}
		}
		return true;
	}

    /**
     * availableSoftwareUpdate
     *
     * @param _valChain
     * @return
     * @author Anthony C. Liberto
     */
    public Blob availableSoftwareUpdate(String _valChain) {
        Blob out = null; //dwb_20050119
        EProgress prog = getProgress(); //dwb_20050119
        prog.setMessage(getString("download")); //dwb_20050119
        prog.show(); //dwb_20050119
        prog.center(); //dwb_20050119
        out = tBase.getSoftwareImage(_valChain, parent);
        prog.hide(); //dwb_20050119
        return out;
    }

    /**
     * getSoftwareImage
     *
     * @author Anthony C. Liberto
     */
    protected void getSoftwareImage() {
        final Worker myWorker = new Worker() {
            public void process() {
                if (isUpdateAvailable(true)) {
                    int reply = showConfirm(parent, YES_NO, "msg11026.4", true);
                    if (reply == YES) {
                        getSoftwareImage(availableSoftwareUpdate(getValueChain()), false);
                    } else {
					    appendLog(getMiddlewareVersions());
					}
                }
            }
            public void wrapup() {
                setBusy(false);
            }

            public String getMethodName() {
                return "getSoftwareImage";
            }

        };
        myWorker.start();
    }

    /**
     * getSoftwareImage
     *
     * @param _b
     * @param _auto
     * @author Anthony C. Liberto
     */
    public void getSoftwareImage(Blob _b, boolean _auto) {
        if (_b != null) {
            String fileName = HOME_DIRECTORY + _b.getBlobExtension();
            try {
                System.out.println("EAccess.getSoftwareImage downloading update");
                _b.saveToFile(fileName);
                update(fileName, _auto);
                return;
            } catch (IOException _ioe) {
                _ioe.printStackTrace();
            }
        }
        return;
    }

    private void update(String _file, boolean _auto) {
        int reply = -1;
        String[] parms = null;
        if (_auto) {
            reply = NOW;
        } else {
            reply = showConfirm(parent, NOW_LATER, "msg11026.3", true);
        }
        if (reply == NOW) {
            if (remote == null) {
                remote = new RemoteControl();
            }
            parms = getUpdateParms(_file);
            if (parms != null) {
                remote.command(getMessage("update.launch.command", parms));
                Routines.pause(5000);
                exit("exit update");
            }
        } else {
            setPrefString(UPDATE_TO_INSTALL, _file);
            bUpdateable = false;
            parent.setMenuEnabled(SYSTEM_MENU, "checkUp", isUpdateable());
        }
    }

    /**
     * checkForExistingUpdate
     * @author Anthony C. Liberto
     */
    public void checkForExistingUpdate() {
        String strUpdate = getPrefString(UPDATE_TO_INSTALL, null);
        if (strUpdate != null) {
            File fileUpdate = new File(strUpdate);
            if (fileUpdate.exists()) {
                clearPref(UPDATE_TO_INSTALL, true);
                update(strUpdate, false);
            } else {
                clearPref(UPDATE_TO_INSTALL, true);
            }
        }
    }

    private String[] getUpdateParms(String _file) {
        try {
            String[] parms = { System.getProperty("java.home"), //location of jre
                UPDATE_FOLDER, //location of ea-server.jar
                URLEncoder.encode(_file, "utf8"), //location of updated files (in zip)
                URLEncoder.encode(HOME_DIRECTORY, "utf8"), //directory location of Home
                URLEncoder.encode(System.getProperty("lax.application.name"), "utf8") //restart the application located here
            };
            return parms;
        } catch (java.io.UnsupportedEncodingException _uee) {
            _uee.printStackTrace();
        }
        return null;
    }

    /**
     * isUpdateable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isUpdateable() {
        return bUpdateable;
    }

    /**
     * getValueChain
     * @return
     * @author Anthony C. Liberto
     */
    public String getValueChain() {
        return null;
    }

    /*
     auto_sort/size
     */
    /**
     * canSort
     * @param _size
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canSort(int _size) {
        boolean b = getPrefBoolean(PREF_AUTO_SORT, DEFAULT_AUTO_SORT);
        boolean out = b;
        int iMax = getMaxSort();
        if (b) {
            if (iMax > 0) {
                out = iMax > _size;
            }
        }
       // appendLog("canSort(" + b + (iMax > 0?" and " + iMax + " > " + _size:"") + "): " + out);
        return out;
    }

    /**
     * canSize
     *
     * @param _size
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canSize(int _size) {
        boolean b = getPrefBoolean(PREF_AUTO_SIZE, DEFAULT_AUTO_SIZE);
        boolean out = b;
        int iMax = getMaxSize();
        if (b) {
            if (iMax > 0) {
                out = iMax > _size;
            }
        }
      //  appendLog("canSize(" + b + (iMax > 0?" and " + iMax + " > " + _size:"") + "): " + out);
        return out;
    }

    private int getMaxSort() {
        return Routines.toInt(getPrefString(PREF_AUTO_SORT_COUNT, DEFAULT_SORT_SIZE_COUNT));
    }

    private int getMaxSize() {
        return Routines.toInt(getPrefString(PREF_AUTO_SIZE_COUNT, DEFAULT_SORT_SIZE_COUNT));
    }

    /*
     bala sort

     */
    /**
     * sort
     *
     * @param _o
     * @param _com
     * @author Anthony C. Liberto
     */
    public void sort(Object[] _o, Comparator _com) {
        Date start = new Date();
        Date stop = null;
        Arrays.sort(_o, _com); //3 - 59
        stop = new Date();
        System.out.println("EAccess.sort took " + compareDate(start, stop) + " seconds.");
    }

    private long timeDifference(Date _d1, Date _d2) {
        return _d2.getTime() - _d1.getTime();
    }

    private long secondDifference(long _sec) {
        return _sec / 1000;
    }

    /**
     * compareDate
     *
     * @param _d1
     * @param _d2
     * @return
     * @author Anthony C. Liberto
     */
    public long compareDate(Date _d1, Date _d2) {
        return secondDifference(timeDifference(_d1, _d2));
    }

    /*
     USRO-R-DWES-66MV6T
     */
    /**
     * getTabTitle
     *
     * @param _code
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public String getTabTitle(String _code, Profile _prof) {
        String[] parms = getProfileParms(_prof);
        String out = new String(getMessage(_code, parms));
        return out;
    }

    private String[] getProfileParms(Profile _prof) {
        String[] out = { _prof.toString(), _prof.getEnterprise(), _prof.getOPName(), toString(_prof.getOPID()), toString(_prof.getOPWGID()), _prof.getRoleCode(), _prof.getRoleDescription(), _prof.getWGName(), _prof.getOPWGName()};
        return out;
    }

    /*
     dwb_20041117
     */
    /**
     * getKey
     * @param _ei
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public static String getKey(EntityItem _ei, String _code) {
        if (isRelator(_ei)) {
            return _ei.getEntityType() + ":" + _code + ":R";
        } else {
            return _ei.getEntityType() + ":" + _code + ":C";
        }
    }

    /**
     * isRelator
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    public static boolean isRelator(EntityItem _ei) {
        if (_ei != null) {
            return isRelator(_ei.getEntityGroup());
        }
        return false;
    }

    /**
     * isRelator
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    private static boolean isRelator(EntityGroup _eg) {
        if (_eg != null) {
            return _eg.isRelator();
        }
        return false;
    }

    /*
     xpnd_action
     */
    /**
     * isExpandAction
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isExpandAction() {
        return getPrefBoolean(PREF_ACTION_EXPANDED, DEFAULT_PREF_ACTION_EXPANDED);
    }

    /*
     dwb_20050120
     */
    private EProgress getProgress() {
        if (progressMonitor == null) {
            progressMonitor = new EProgress(parent, "Process Status");
        }
        return progressMonitor;
    }

    /**
     * report
     *
     * @author Anthony C. Liberto
     * @param _x
     * @param _b
     */
    public static void report(Exception _x,boolean _b) {
        if (_b) {
            _x.printStackTrace();
        }
    }

/*
 CR_7342
 */
	/**
     * getParentInformationAtLevel
     *
     * @author Anthony C. Liberto
     * @return
     * /
    public Object[] getParentInformationAtLevel() {
        return getParentInformationAtLevel(getNumber("msg3019.0"));
	}*/

	/**
     * getParentInformationAtLevel
     *
     * @author Anthony C. Liberto
     * @param _i
     * @return
     */
    public Object[] getParentInformationAtLevel(int _i) {
        ETabable eTab = getCurrentTab();
        if (eTab instanceof ENavForm) {
			return ((ENavForm)eTab).getParentInformationAtLevel(_i);
		}
		return null;
	}

	/**
     * getParentInformationAtLevel
     *
     * @author Anthony C. Liberto
     * @param _iTab
     * @param _i
     * @return
     * /
    public Object[] getParentInformationAtLevel(int _iTab, int _i) {
        ETabable eTab = getTab(_iTab);
        if (eTab instanceof ENavForm) {
			return ((ENavForm)eTab).getParentInformationAtLevel(_i);
		}
		return null;
	}*/
    /**
     * show MaintenanceList
     *
     * cr_FlagUpdate
     *
     * @author Anthony C. Liberto
     * @param _c
     * @param _list
     * @param _maintList
     */
	public void show(Component _c, EntityList _list, MetaFlagMaintList _maintList) {
		if (_maintList != null && maintPnl != null) {
			maintPnl.refresh(_maintList);
			maintPnl.setParentEntityList(_list);
			show(maintPnl, maintPnl, false);
		}
	}

	/**
	 * getMiddlewareVersions
	 *
	 * @author Anthony C. Liberto
	 * @return String
	 */
	private String getMiddlewareVersions() {
		Date tmp = parseDate(BUILD_DATE, Version.BUILD_DATE.substring(4));
		String[] in = new String[5];
		String[] parms = new String[10];
		String[] vers = null;
		boolean bError = false;

		in[0] = Routines.trim(tBase.getLocalVersion(),22);
		in[1] = Routines.trim(tBase.getRemoteVersion(),22);
		in[2] = Routines.trim(tBase.getCompiledVersion(),22);
		in[3] = Routines.trim(getJarVersion(ISO_DATE),22);
		in[4] = Routines.trim(formatDate(ISO_DATE, tmp),22);

		parms[0] = in[0];
		parms[2] = in[1];
		parms[4] = in[2];
		parms[6] = in[3];
		parms[8] = in[4];

		parms[1] = "n/a";
		parms[3] = "n/a";
		parms[5] = "n/a";
		parms[7] = "n/a";
		parms[9] = "n/a";

		vers = dBase().getMiddlewareVersions(in,END_OF_TIME,parent);

		if (vers != null) {
			for (int i=0;i<5 && !bError; ++ i) {
				if (!vers[i].equals(END_OF_TIME)) {
					if (!vers[i].equals(in[i])) {
						bError = true;
					}
				}
			}
			parms[1] = vers[0];
			parms[3] = vers[1];
			parms[5] = vers[2];
			parms[7] = vers[3];
			parms[9] = vers[4];

		}

		if (bError) {
			String strOut = getMessage("mw.error.version",parms);
			if (!isTestMode()) {
				showMessage(getString("name"),WARN_MESSAGE,OK,strOut,parent);
			}
			return strOut;
		}
		return getMessage("mw.version",parms);
	}

    /**
     * show Message
     *
     * @author Anthony C. Liberto
     * @param _title
     * @param _type
     * @param _button
     * @param _msg
     * @param _c
     */
    private void showMessage(String _title, int _type, int _button, String _msg, Component _c) {
        if (!messagePnl.isShowing()) {
            setTitle(_title);
            messagePnl.construct(MESSAGE_TYPE, _type, _button, _msg);
            messagePnl.setUseTrueTypeFont(true);
            show(_c, messagePnl, true);
            clear();
        }
    }

/*
CR103103686
*/
	public int getRecordLimit() {
		return getRecordLimit(getActiveProfile());
	}

	private int getRecordLimit(Profile _prof) {
		if (_prof != null) {
//			System.out.println("profile record limit: " + _prof.getRecordLimit());
			return Math.max(_prof.getRecordLimit(),MAXIMUM_EDIT_ROW);
		}
		return MAXIMUM_EDIT_ROW;
	}

/*
 log locking
 */
	public static boolean isMonitor() {
		return isArmed(MONITOR_ARM_FILE);
	}

	public static void monitor(String _s, Object _o) {
		if (_o instanceof Date) {
			writeLog(MONITOR_ARM_FILE,_s + " " + _o.toString());
		} else if (_o instanceof InactiveItem[]) {
			writeLog(MONITOR_ARM_FILE,_s + " " +  new Date());
			InactiveItem[] inAct = (InactiveItem[])_o;
			int ii = inAct.length;
			for (int i=0;i<ii;++i) {
				writeLog(MONITOR_ARM_FILE,"    " + _s + " " + inAct[i].getEntityType() + ":" + inAct[i].getEntityID());
			}
		} else if (_o instanceof LockList) {
			LockList l = (LockList)_o;
			int ii = l.getLockGroupCount();
			writeLog(MONITOR_ARM_FILE,_s + " " + new Date());
			for (int i=0;i<ii;++i) {
				LockGroup lg = l.getLockGroup(i);
				if (lg != null) {
					int xx = lg.getLockItemCount();
					for (int x=0;x<xx;++x) {
						LockItem li = lg.getLockItem(x);
						if (li != null) {
							writeLog(MONITOR_ARM_FILE,"    currentlyLocked " + li.getEntityType() + ":" + li.getEntityID());
						}
					}
				}
			}
			writeLog(MONITOR_ARM_FILE,"end " + _s);
		} else {
			writeLog(MONITOR_ARM_FILE,_s + " " + new Date() + " " + _o.toString());
		}
	}

    private static void writeLog(String _file, String _txt) {
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;
        try {
            fos = new FileOutputStream(FUNCTION_DIRECTORY + _file,true);
            osw = new OutputStreamWriter(fos, EANNOUNCE_FILE_ENCODE);
            out = new BufferedWriter(osw);
            out.write(_txt + RETURN);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
				if (osw != null) {
					osw.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException _ioe) {
				_ioe.printStackTrace();
			}
		}
	}
}
