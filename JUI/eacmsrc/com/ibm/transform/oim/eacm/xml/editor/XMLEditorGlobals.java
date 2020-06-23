// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

/*****************************************************************************
* This interface contains global values for XMLEditor.  It is public because
* the servlets used to access the data from a browser use it too.
* They are not part of the package at this time.
*
* @author Wendy Stimpson
* @version 1.1
*
* Change History:
*/
// $Log: XMLEditorGlobals.java,v $
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.3  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.2  2006/01/10 15:00:45  wendy
// Changes for table accessibility DQA requirements
//
// Revision 1.1.1.1  2005/09/09 20:39:18  tony
// This is the initial load of OPICM
//
//
public interface XMLEditorGlobals
{
    // constant, MenuItem text display values
    /** bold */
    String B_STYLE                  = "Bold";
    /** italic */
    String I_STYLE                  = "Italic";
    /** underline */
    String U_STYLE                  = "Underline";
    /** strike */
    String S_STYLE                  = "StrikeThrough";
    /** emphasis */
    String EM_STYLE                 = "Emphasis";
    /** strong */
    String STRONG_STYLE             = "Strong";
    /** webonly */
    String WEBONLY_TAG_ATTR         = "webonly";
    /** print */
    String PRINTONLY_TAG_ATTR       = "printonly";
    /** publish */
    String PUBLISH_TAG_ATTR         = "publish";

    // The default editor kit actions have static strings defined
    // for the action names.  Styled actions do not.  Define some of them here
    /** font em */
    String FONT_EM_ACTION               = "font-em";
    /** font strong */
    String FONT_STRONG_ACTION           = "font-strong";
    /** font bold */
    String FONT_B_ACTION                = "font-bold";
    /** font italic */
    String FONT_I_ACTION                = "font-italic";
    /** fontunderline */
    String FONT_U_ACTION                = "font-underline";
    /** spellchk */
    String SPELLCHECK_ACTION            = "spell-check";

//  static final String FontFamilyFaceAction        = "font-family";
//  static final String FontSizeAction              = "font-size";
//  static final String FontColorAction             = "font-color";
//  static final String CustomColorChooserAction    = "custom-color";
    /** h1 */
    String H1_ACTION                    = "heading-1";
    /** h2 */
    String H2_ACTION                    = "heading-2";
    /** h3 */
    String H3_ACTION                    = "heading-3";
    /** h4 */
    String H4_ACTION                    = "heading-4";
    /** h5 */
    String H5_ACTION                    = "heading-5";
    /** h6 */
    String H6_ACTION                    = "heading-6";

    /** webonly */
    String INSERT_WEBONLY_ACTION        = "Web Only";
    /** print only */
    String INSERT_PRINTONLY_ACTION      = "Print Only";
    /** insert table */
    String INSERT_TABLE_ACTION          = "InsertTable";
    /** row */
    String INSERT_TR_ACTION             = "InsertTableRow";
    /** cell */
    String INSERT_TD_ACTION             = "InsertTableCol";
    /** delete table */
    String DELETE_TABLE_ACTION          = "DeleteTable";
    /** delete row */
    String DELETE_TR_ACTION             = "DeleteTableRow";
    /** delete cell */
    String DELETE_TD_ACTION             = "DeleteTableCol";
    /** border */
    String TOGGLE_BORDER_ACTION         = "ToggleTableBorder";
    /** th */
    String TOGGLE_TH_ACTION             = "ToggleTableHeader";
    /** tr td*/
    String INSERT_TR_TD_ACTION          = "InsertTableRowCol";
    /** del trtd */
    String DELETE_TR_TD_ACTION          = "DeleteTableRowCol";
    /** insert ul */
    String INSERT_UL_ACTION             = "InsertUnorderedList";
    /** ol */
    String INSERT_OL_ACTION             = "InsertOrderedList";
    /** p */
    String INSERT_P_ACTION              = "InsertParagraph";
    /** nested p */
    String INSERT_NESTED_P_ACTION       = "InsertNestedParagraph";
    /** pre */
    String INSERT_NESTED_PRE_ACTION     = "InsertNestedPre";
    /** br */
    String INSERT_BR_ACTION             = "InsertBreak";
    /** insert pre */
    String INSERT_PRE_ACTION            = "InsertPre"; //v11

// for nested lists
    /** nested li */
    String INSERT_LI_ACTION             = "InsertListItem";
    /** li */
    String INSERT_PARENT_LI_ACTION      = "InsertParentListItem";
    /** ol */
    String INSERT_NESTED_OL_ACTION  = "InsertNestedOrderedList";
    /** ul */
    String INSERT_NESTED_UL_ACTION  = "InsertNestedUnorderedList";
// end nested lists

    /** unod */
    String UNDO_EDIT_ACTION             = "Undo";
    /** redo */
    String REDO_EDIT_ACTION             = "Redo";
    /** copy struct */
    String COPY_STRUCTURE_ACTION        = "CopyStructure";
    /** cut struct */
    String CUT_STRUCTURE_ACTION         = "CutStructure";

    /** find */
    String FIND_REPL_ACTION             = "FindReplace";
    /** next */
    String FIND_NEXT_ACTION             = "FindNext";
    /** rep */
    String REPLACE_NEXT_ACTION          = "ReplaceNext";
    /** save */
    String UPDATE_DB_ACTION             = "Save";
    /** setup */
    String PAGE_SETUP_ACTION            = "Page Setup...";
    /** print */
    String PRINT_ACTION             = "Print...";
    /** preview */
    String PRINT_PREVIEW_ACTION     = "Print Preview";

/* markup test
    String SetMarkupAction              = "SetMarkup";
    String CompleteMarkupAction     = "CompleteMarkup";
    String PartialMarkupAction          = "PartialMarkup";
    String FindMarkupAction         = "FindMarkup";
    String DeletedStyle             = "DeletedStyle";
    String InsertedStyle                = "InsertedStyle";
    String MarkupMode                   = "Markup";
*/

    /** view web */
    String VIEW_WEBONLY_ACTION      = "ViewWebOnlyHtml";
    /** view print */
    String VIEW_PRINTONLY_ACTION        = "ViewPrintOnlyHtml";
    /** view tag */
    String VIEW_TAG_ACTION          = "ViewTagHtml";
    /** view normal */
    String VIEW_NORMAL_ACTION           = "ViewNormalHtml";
    /** len */
    String SHOW_LENGTH_ACTION           = "ShowLength";
    /** spec char */
    String INSERT_SPECCHAR_ACTION       = "InsertSpecialChar";

    /** web */
    int WEBONLY_VIEW_TYPE               = 1;
    /** print */
    int PRINTONLY_VIEW_TYPE             = 2;
    /** normal */
    int NORMAL_VIEW_TYPE                = 3;
    /** tab */
    int TAG_VIEW_TYPE                   = 4;

    /** basic */
    String BASIC_DTD                    = "Basic";
    /** attr */
    String ATTRIBUTE_DTD                = "Attribute";
    /** dependent */
    String DEPENDENT_DTD                = "Dependent";
    /** default */
    String DEFAULT_DTDNAME              = "default.dtd";

    /** web */
    String WEB_ONLY_CONTENT             = "Web Only Content";
    /** print */
    String PRINT_ONLY_CONTENT           = "Print Only Content";
    /** tag */
    String TAG_CONTENT                  = "XML Source";
    /** about 1 */
    String ABOUT_XML_EDITOR1            =
        "Copyright (c) 2001-2005, International Business Machines Corp., Ltd.";
    /** about 2 */
    String ABOUT_XML_EDITOR2            =
        "All Rights Reserved.";
    /** about 3 */
    String ABOUT_XML_EDITOR3            =
        "Licensed for use in connection with IBM business only.";

    //static final int MAX_LENGTH = 32768;          // maximum number of bytes CR051603653
    // FB 53138:79E506 mw only allows 32000, spec will be updated from 32K to 32000
    /** max */
    int MAX_LENGTH = 32000;             // maximum number of bytes CR051603653
    /** limit */
    int CHAR_WARNING_LIMIT = 10000;     // warning limit for number of chars CR051603653

    /** no help */
    String NO_HELP_TXT          = "No Information Available.";
    /** utf8 */
    String CHAR_ENCODING        = "UTF8";
    /** err */
    String SERVLET_XML_ATTR_ERR = "XMLAttrError";

    // Action.MNEMONIC_KEY must be an integer in JRE 1.4.0, so create our own key
    /** control */
    String XML_CONTROL_KEY              = "XMLControlKey";

    /* AHE Accessibility requirements */
    /** table tag summary attribute */
    String TABLE_SUMMARY = "summary";
    /** table tag summary attribute must = 'layout' if th is not in the table */
    String TABLE_LAYOUT = "layout";
    /** th tag id attribute must be set if table is not a layout table */
    String TH_ID = "id";
    /** td tag headers attribute must be set if table is not a layout table */
    String TD_HEADERS = "headers";

    /** application name */
    String APP_NAME = "EACM";
}
