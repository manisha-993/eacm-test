//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

/**
 * Shared universal constants
 */
// $Log: EACMGlobals.java,v $
// Revision 1.5  2014/02/24 15:11:19  wendy
// RCQ285768 - view cached XML in JUI
//
// Revision 1.4  2014/01/22 20:41:05  wendy
// RCQ 288700 Apache OpenDocument support
//
// Revision 1.3  2013/11/07 18:09:39  wendy
// Add FillCopyEntity action
//
// Revision 1.2  2013/09/17 13:18:58  wendy
// add abr queue
//
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public interface EACMGlobals {
	int MIN_BLOB_WIDTH = 220;
	int MIN_COL_WIDTH = 20;

	String APP_PKG_NAME = com.ibm.eacm.EACM.class.getPackage().getName();
	String EDIT_PKG_NAME = com.ibm.eacm.edit.EditController.class.getPackage().getName();
	String NAV_PKG_NAME = com.ibm.eacm.nav.Navigate.class.getPackage().getName();
	String WU_PKG_NAME = com.ibm.eacm.wused.WUsedActionTab.class.getPackage().getName();
	String MTRX_PKG_NAME = com.ibm.eacm.mtrx.MatrixActionBase.class.getPackage().getName();
	String MW_PKG_NAME =com.ibm.eacm.mw.RMIMgr.class.getPackage().getName();
	String STDOUT_LEVEL = "STDOUT";
	String STDERR_LEVEL = "STDERR";
	String ALWAYS_LEVEL = "Always";
	String TIMING_LOGGER = "com.ibm.eacm.timing";

	/**
	 * eacm version literal
	 */
	String EACM_TOKEN = "POTPALASDEENALAB";
	//FIXME yellowzone
	//String EACM_TOKEN = "yellowzone";

    /*
     Generic Constants
    */
	String DATACHANGE_PROPERTY="DataChanged";
	String DATALOCKED_PROPERTY="DataLocked";

	String TCE_PROPERTY="tableCellEditor"; // from JTable.setCellEditor

	String EXIT_ACTION = "exit";
	String LOGOFF_ACTION = "logOff";
	String CLOSEALL_ACTION = "clsA";
	String CLOSETAB_ACTION = "clsT";
	String ABOUT_ACTION = "about";
	String FMI_ACTION = "fmi";
	String CONTENTS_ACTION = "contents";
	String ATTRHELP_ACTION = "attHelp";
	String MEMORY_ACTION = "memory";
	String PREF_ACTION = "pref";
	String SELECTALL_ACTION = "selA";
	String SELECTINV_ACTION = "iSel";
	String GOTO_ACTION = "goto";
	String ENTITYDATA_ACTION ="eData";
	String WINDOW_ACTION = "wins";
    String CHKUP_ACTION = "checkUp";

    String PAGESETUP_ACTION = "pageSetup";
	String SAVE_ACTION ="save";
	String FINDREP_ACTION ="f/r";
	String EXITDIALOG_ACTION = "exitDialog";
	String LOADBM_ACTION = "loadbm";
	String SAVEBM_ACTION ="savebm";
	String SEND_ACTION = "send";
	String REMOVE_ACTION = "remove";
	String RENAMEBM_ACTION = "renameMark";
	String REFRESH_ACTION = "rfrsh";
	String EXPORT_ACTION = "xprt";
	String SORT_ACTION ="srt";
	String FILTER_ACTION = "fltr";
	String VIEWXML_ACTION ="viewxml";
	String VIEWXMLOK_ACTION = "viewxmlok";

	String BREAK_ACTION = "break";
	String SRTOK_ACTION = "srtok";
	String SRTCLEAR_ACTION = "srtclr";
	String CLEAR_ACTION = "clr";
	String CANCEL_ACTION = "cancel";
	String CLOSE_ACTION = "clse";
	String UNDOFILTER_ACTION = "rstg";
	String RUNFILTER_ACTION = "runf";
	String ADD_ACTION="addF";
	String REMOVESEL_ACTION = "rmvS";
	String REMOVEALL_ACTION = "rmvA";
	String FIND_ACTION ="fnd";
	String REPLACE_ACTION="rpl";
	String REPLACEALL_ACTION="rplAll";
	String REPLACENEXT_ACTION="rplNxt";
	String RESETCOLOR_ACTION = "rstc";
	String FINDNEXT_ACTION = "fndNxt";
	String RESETDATE_ACTION = "resetDate";
	String RESETDATEPROF = "resetDateProf";
	String OK_ACTION = "ok";
	String ADD2CART_BUTTON="add2cartButton";
	String ADD2CART_ACTION="add2cart";
	String ADDALL2CART_ACTION="add2cartA";
	String REMOVECART_BUTTON="rmvButtonS";
	String REMOVESELCART_ACTION="rmvS";
	//String REMOVEALLCART_ACTION="rmvA";
	String RESETCART_ACTION = "resetCart";
	String REMOVETAB_ACTION="rmvT";
	String LINKALL_ACTION="lnkA";
	String PASTE_ACTION="pste";
	String CUTPASTE_ACTION="cutpste";
	String COPY_ACTION = "copy";
	String CUT_ACTION = "cut";

	//nav actions
	String BOOKMARK_ACTION = "bookmark";
	String PIN_ACTION = "pin";
	String RESET_ACTION = "reset";
	String SHOWHIST_ACTION = "histI";
	String SHOWATTRHIST_ACTION = "histA";
	String SHOWRELHIST_ACTION = "histR";
	String SHOWCART_ACTION = "showCart";
	String PREV_ACTION = "prev";
	String CGRP_ACTION = "cGrp";
	String VIEWP_ACTION = "viewP";
	String VIEWPW_ACTION = "viewPW";
	String ABRQS_ACTION = "abrqs";

	String RESTORE_ACTION = "rstr";

	String NAVCUT_ACTION = "navcut";
	String NAV_ACTIONSET = "nav";
	String NAVPICK_ACTIONSET = "navP";
	String NAVSRCH_ACTIONSET = "search";

	String NAVEDIT_ACTIONSET="edit";
	String NAVLINK_ACTIONSET="link";
	String NAVLINKD_ACTIONSET="linkD";
	String NAVMTRX_ACTIONSET="mtrx";
	String NAVWU_ACTIONSET="used";
	String NAVCRT_ACTIONSET="crte";
	String NAVRPT_ACTIONSET="rprt";
	String NAVWF_ACTIONSET="wFlow";
	String NAVPDG_ACTIONSET="pdg";
	String NAVXTRACT_ACTIONSET = "xtract";
	String NAVCOPY_ACTIONSET = "cpyA";
	String NAVABRVIEW_ACTIONSET = "abrS";
	String NAVSETCGGRP_ACTIONSET = "sGrp";
	String NAVLOCKP_ACTIONSET = "lckP";
	String NAVDEACT_ACTIONSET = "deAct";
	String NAVQUERY_ACTIONSET = "query";
	String NAVLANG_ACTIONSET = "sel3";

	//wu actions
	String WUCRT_ACTIONSET="crte";
	String WUEDIT_ACTIONSET="edit";
	String WU_ACTIONSET="used";
	String WULINK_ACTIONSET="link";
	String WURLINK_ACTIONSET="rLink";
	String WUMTRX_ACTIONSET="mtrx";

	//edit actions
	String SAVERECORD_ACTION ="saveR";
	String SAVEALL_ACTION ="saveA";
	String SAVEDEFAULT_ACTION ="dsave";
	String CANCELDEFAULT_ACTION ="dcncl";
	String FILLCOPY_ACTION ="fcopy";
	String FILLCOPYENTITY_ACTION ="fcopyr";
	String FILLAPPEND_ACTION ="fapnd";
	String FILLPASTE_ACTION ="fpste";

	String EDITMTRX_ACTIONSET="mtrx";
	String EDITWF_ACTIONSET="wFlow";
	String GRIDEDITOR_ACTION = "horz";
	String VERTEDITOR_ACTION = "vert";
	String FORMEDITOR_ACTION = "form";
	String FREEZE_ACTION = "frze";
	String THAW_ACTION = "thaw";
	String LOCK_ACTION = "lock";
	String PREVEDIT_ACTION = "prev";
	String NEXTEDIT_ACTION = "next";
	String REMOVENEW_ACTION = "remove";
	String REFRESHFORM_ACTION = "rfrshForm";
	String FLAGMAINT_ACTION="maint";
	String IMPORTXLS_ACTION = "xl8r";
	String IMPORTODS_ACTION = "ods";
	String SPELLCHK_ACTION = "spll";
	String TOGGLE_ACTION = "toglAct";
	String TOGGLENLSTREE_ACTION = "toglTreeView";

	//matrix actions
	String MTRXEDIT_ACTIONSET="edit";
	String MTRXWU_ACTIONSET="used";
	String MTRX_ADJUSTROW="aRow";
	String MTRX_ADJUSTCOL="aCol";
	String MTRX_ADDCOL="pick";
	String MTRX_DELETEROW="deRow";
	String MTRX_DELETECOL="deCol";
	String MTRX_PIVOT="pivot";
	String MTRX_UNPIVOT="unpivot";
	String MTRX_CANCEL="mtrxcncl";
	String MTRX_RESETSEL="rstSel";
	String MTRX_SAVE=SAVE_ACTION;
	String MTRX_SAVEORDER="saveO";
	String MTRX_RESTOREORDER="rvrtO";
	String MTRX_TOGLACT="toglAct";
	String MTRX_TOGLCRS="toglCrs";

	String HIDECOL_ACTION="hide";
	String HIDEROW_ACTION="hide.row";

	String UNHIDECOL_ACTION="unhide";
	String UNHIDEROW_ACTION="unhide.row";

	String MOVECOL_LEFT_ACTION="left";
	String MOVECOL_RIGHT_ACTION="right";

	String UNLOCK_ACTION="ulck";
	String RESETALLATTR_ACTION="rstA";
	String RESETONEATTR_ACTION="rstS";
	String RESETRECORD_ACTION="rstR";
	String CREATE_ACTION="crte";
	String PRINT_ACTION="print";
	String DUPLICATE_ACTION="dup";
	String DEACTIVATEATTR_ACTION = "dActAtt";
	String EDIT_ACTION="edit";
	String DELETE_ACTION="del";

	String ADDCOL_ACTION="addC";
	String LINK_ACTION="link";
	String COPYLINK_ACTION="copylink";
	String VIEW_ACTION="view";
	
	//tree actions
	String EXPANDALL_ACTION = "xpndAll";
	String COLLAPSEALL_ACTION = "clpseAll";

	char NO_MNEMONIC = '\0'; //specify no mnemonic

    /**
     * file encode
     */
    String EACM_FILE_ENCODE = "Cp850";

    /**
     * return
     */
    String RETURN = System.getProperty("line.separator");
    String NEWLINE = "\n";

    /**
     * default icon
     */
    String DEFAULT_ICON = "icon.gif";

    /**
     * delimit char
     */
    String DELIMIT_CHAR = "|";

    /**
     * flag delimit
     */
    String FLAG_DELIMIT = ";"; //xl8r
    /**
     * array delimit
     */
    String ARRAY_DELIMIT = ",";

    /**
     * ok error indicator
     */
    String OK_ERROR = "(ok)"; //24321
    /**
     * log file extension
     */
    String LOG_EXTENSION = ".log";
    String LCK_EXTENSION = ".lck";

    /**
     * vb script extension
     */
    String VB_SCRIPT_EXTENSION = ".vbs";


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
     * validate form
     */
    String FORM_VALIDATE_ARM_FILE = "formVal" + ARM_EXTENSION;
    /**
     * location chooser
     */
    String LOCATION_CHOOSER_ARM_FILE = "locChoose" + ARM_EXTENSION;

    /**
     * verbose rmi
     */
    String RMI_VERBOSE_ARM_FILE = "rmiVerbose" + ARM_EXTENSION;

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
     * trace calls
     */
    String TRACE_ARM_FILE = "trace" + ARM_EXTENSION;

    /**
     * verbose
     */
    String VERBOSE_ARM_FILE = "verbose" + ARM_EXTENSION;

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
	 * default extension
	 */
    String DEF_EXTENSION = ".def";


    /*
     OS Constants
     */
    String WINDOWS_2000 = "Windows 2000";
    String WINDOWS_XP = "Windows XP";
    String WINDOWS = "Windows";
    int OS_OTHER = -4;
    int OS_WINDOWS = 0;
    int OS_WINDOWS_2000 = 1;
    int OS_WINDOWS_XP = 2;
    int OS_MINIMUM_CLIENT = OS_WINDOWS_2000;

    int FUDGE_FACTOR = 10;


    /*
     Folder Constants
    */
    String FILE_SEP = System.getProperty("file.separator");
    String HOME = System.getProperty("user.dir");
    String HOME_DIRECTORY = HOME + FILE_SEP;
    String RESOURCE_FOLDER = HOME_DIRECTORY + "Resource";
    String RESOURCE_DIRECTORY = RESOURCE_FOLDER + FILE_SEP;
    String FUNCTION_FOLDER = HOME_DIRECTORY + "Function";
    String FUNCTION_DIRECTORY = FUNCTION_FOLDER + FILE_SEP;
    String LOGS_FOLDER = HOME_DIRECTORY + "Logs";
    String LOGS_DIRECTORY = LOGS_FOLDER + FILE_SEP;
    String TEMP_FOLDER = HOME_DIRECTORY + "Temp";
    String TEMP_DIRECTORY = TEMP_FOLDER + FILE_SEP;
    String UPDATE_FOLDER = HOME_DIRECTORY + "Updates";
    String UPDATE_DIRECTORY = UPDATE_FOLDER + FILE_SEP;
    String SAVE_FOLDER = HOME_DIRECTORY + "Saved";
    String SAVE_DIRECTORY = SAVE_FOLDER + FILE_SEP;

	/**
	 * jar dir
	 */
    String JAR_DIRECTORY = HOME_DIRECTORY + "Jars" + FILE_SEP;

	/**
	 * cache folder
	 */
    String CACHE_FOLDER = HOME_DIRECTORY + "Cache"; //cache_20040112
	/**
	 * cache dir
	 */
    String CACHE_DIRECTORY = CACHE_FOLDER + FILE_SEP; //cache_20040112


	/**
	 * update to install
	 */
    String UPDATE_TO_INSTALL = "eannounce.update.file";

	String MNEMONIC_EXT="-s";
	String TOOLTIP_EXT="-t";

    /*
     Message Constants
    */
    int ABORT_BUTTON = javax.swing.JOptionPane.YES_OPTION;
    int YES_BUTTON = javax.swing.JOptionPane.YES_OPTION;
    int OK_BUTTON = javax.swing.JOptionPane.YES_OPTION;
    int NOW_BUTTON = javax.swing.JOptionPane.YES_OPTION;

    int NO_BUTTON =javax.swing.JOptionPane.NO_OPTION;
    int NONE_BUTTON = javax.swing.JOptionPane.NO_OPTION;
    int RETRY = javax.swing.JOptionPane.NO_OPTION;

    int ALL_BUTTON = 4;
    int CHOOSE_BUTTON = 3;
    int CLOSED = javax.swing.JOptionPane.CLOSED_OPTION;

    int CANCEL_BUTTON =javax.swing.JOptionPane.CANCEL_OPTION;
    int IGNORE =  javax.swing.JOptionPane.CANCEL_OPTION;

    /*
     Action Constants
    */

    String ACTION_PURPOSE_CREATE = "Create";
    String ACTION_PURPOSE_DELETE = "Delete";
    String ACTION_PURPOSE_EDIT = "Edit";
    String ACTION_PURPOSE_EXTRACT = "Extract";
    String[] ACTION_PURPOSE_EDIT_EXTRACT = {ACTION_PURPOSE_EDIT,ACTION_PURPOSE_EXTRACT};
    String ACTION_PURPOSE_LINK = "Link";
    String ACTION_PURPOSE_LOCK = "Lock";
    String ACTION_PURPOSE_MATRIX = "Matrix";
    String ACTION_PURPOSE_NAVIGATE = "Navigate";
    String ACTION_PURPOSE_REPORT = "Report";
    String ACTION_PURPOSE_SEARCH = "Search";
    String ACTION_PURPOSE_WHERE_USED = "WhereUsed";
    String ACTION_PURPOSE_WORK_FLOW = "Workflow";
    String ACTION_PURPOSE_PDG = "PDGActionItem"; //USRO-R-JSTT-68RKKP
    String ACTION_PURPOSE_COPY = "Copy";
    String ACTION_PURPOSE_CHANGE_GROUP = "CG"; //chgroup
    String ACTION_PURPOSE_ABRSTATUS = "ABRStatus"; //cr_2115
    String ACTION_PURPOSE_QUERY = "Query";

	/**
	 * simple date format constant
	 */
    String ISO_DATE = "yyyy-MM-dd-HH.mm.ss.SSSSSS";
    String FORMATTED_DATE = "yyyy-MM-dd";
    String DATE_TIME_ONLY = "yyyyMMddHHmmss";
    String FORMAT_IN = "yyyy-MM-dd-HH.mm.ss";
	String LOGFORMAT_DATE = "yyyy-MM-dd-HH.mm.ss.SSS";


	/**
	 * simple date format constant
	 */
    String FOREVER = "9999-12-31-00.00.00.000000";
    /**
     * end of time
     */
    String END_OF_TIME = "9999-12-31-23.59.59.99";// why not 999999
    /**
	 * simple date format constant
	 */
    String DISPLAY_TIME = "HH:mm";

	/**
	 * simple date format constant
	 */
    String DISPLAY_DATE = "EEEEE, MMMMM dd, yyyy";

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

	/**
	 * panel type constants
	 */
    String TYPE_BOOKMARK = "eNavForm" + DELIMIT_CHAR + "bookmark";

    /*
     menu constants
    */
    String EDIT_MENU = "edit";
    String TABLE_MENU = "tbl";
	String ACTIONS_MENU ="act";
	String HISTORY_MENU = "hist";
    String HELP_MENU = "help";
    String FILE_MENU = "file";
    String SYSTEM_MENU = "system";
    String VIEW_MENU = "view";
    String WORKGROUP_MENU = "workgroup";
    String WINDOW_MENU = "wins";
    String UPDATE_MENU="updt";
    String FILL_MENU="fill";


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
    int ACCESSIBLE_VALUE=5;
    /**
     * max
     */
    int ACCESSIBLE_MAX = 6;


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
	 * optional update
	 */
	String OPTIONAL = "optional";

	// UI keys
	String UNDERLINE_BORDER_KEY = "eannounce.underlineBorder";
	String EMPTY_BORDER_KEY = "eannounce.emptyBorder";
	String ETCHED_BORDER_KEY = "eannounce.etchedBorder";
	String RAISED_BORDER_KEY = "eannounce.raisedBorder";
	String LOWERED_BORDER_KEY = "eannounce.loweredBorder";
	String SELECTED_BORDER_KEY = "eannounce.selectedBorder";
	String NOFOCUS_BORDER_KEY = "eannounce.noFocusBorder";
	String FOCUS_BORDER_KEY = "eannounce.focusBorder";
	String WUFOCUS_BORDER_KEY = "eannounce.wufocusBorder";
	String FOUND_FOCUS_BORDER_KEY = "eacm.foundFocusBorder";
	String FOUND_BORDER_KEY = "eacm.foundBorder";

	String PREF_COLOR_ASSOC = "preferred.association.color";
	String PREF_COLOR_CHILD = "preferred.child.color";
	String PREF_COLOR_PARENT = "preferred.parent.color";
	String PREF_COLOR_LOCK = "preferred.lock.color";
	String PREF_COLOR_FOUND = "preferred.found.color";
	String PREF_COLOR_WU_BG = "preferred.wu.bg.color";
	String ROW_REND_COLOR = "row.renderer.color";

	String WU_SELECTION_BACKGROUND = "eacm.wu.selection.color";
	String TOOLBAR_BUTTON_DIM ="eacm.buttondim";

	String MULTILINETOOLTIP_UI = "multiline.tooltip.ui";

	String MAXAGE_PREF="maxage";

	String EXPORTPREFS_ACTION = "exportprefs";
	String IMPORTPREFS_ACTION = "importprefs";
	String RESETPREFS_ACTION = "resetprefs";
}
