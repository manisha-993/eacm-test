// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;
import java.beans.*;

import com.wallstreetwise.app.jspell.domain.*;

//import com.wallstreetwise.app.jspell.domain.net.*;  // for servlet dictionary

import javax.accessibility.*;

/******************************************************************************
* This editor is used for XHTML editing.  It can be used in an applet or as
* part of an application.
*<p>
* ISSUES:
*      Setting the charset encoding works properly but characters are not
*      rendered properly unless the IBM JRE is used and the tnrmt30.ttf font is
*      in the jre\lib\fonts directory.
*      The applet requires installation of the IBM JRE as the java plugin with
*      the font file AND an addition to the java.security file to allow paste
*      from the system clipboard.  The addition adds a url pointing to the policy
*      file on the server.
*
*      Netscape will not render unicode at all, IE will if the charset is specified.
*      <meta http-equiv=content-type content="text/html; charset=utf-8">
*
*
* @author Wendy Stimpson
* @version 3.0
*
*/
/*
JEditorPane Notes:

    Using the default constructor, JEditorPane is initialized to handle plain
text documents.
    Content type = "text/plain"
    Editor kit = "DefaultEditorKit"
    Document type = "PlainDocument"

    Based on the type, it creates and installs an editor kit.  It calls
setContentType() to create and install the proper editor.  A cache and registry
hold mappings between content types and editor kits.  The registry maps content
type to the name of the class file for the editor kit.  If you create editor
kits of your own, use registerEditorKitForContentType(type, classname).
The cache maps object types to instance of editor kits.  JEditorPane only uses
the cache.. it calls getEditorKitForContentType(type) to get an EditorKit.  The
cache is checked first, if not there the registry is checked and a new EditorKit
is created and installed.  If the type is not found, the DefaultEditorKit is
used.  The EditorKit.createDefaultDocument() will generate the proper Document.
JEditorPane.setDocument() will install it.
    JTextComponent.read() is called to create the correct document type and load
document content.

Swing is not thread safe.  Worker threads can not manipulate user interface elements.
The Java application starts with a main thread.  The main thread can call constructors
and lay out components in a frame window.  It then invokes show or setVisible() and
exits after it completes, usually after displaying the window.  When the first window
is shown, a second thread, the event dispatch thread is created.  All event notifications
and paint is handled by this thread.  All code is contained in event handlers to
respond to user interface and repaint requests and runs on the dispatch thread.
Rules:
1)  If an action takes a long time, fire up a new thread to do the work.  The application
will appear dead if you don't.
2)  If an action can block on input or output, fire up a new thread.  Such as using a
network connection.
3)  If you need to wait, don't sleep in the event dispatch thread, use a timer.
4)  Any work done in worker threads cannot touch the user interface.  Read info from the
UI before launching threads, launch them, and then update the UI from the event dispatching
thread once completed.  Use SwingUtilities.invokeLater() or SwingUtilities.invokeAndWait().

Exceptions:
1)  A few swing methods are thread safe and are marked in the API doc such as:
        JTextComponent.setText()
        JTextArea.insert()
        JTextArea.append()
        JTextArea.replaceRange()
2)  JComponent.repaint() and revalidate() can be called from any thread.
3)  Event listeners can be safely added or removed in any thread.
4)  Conponents can be constructed, their properties set and added to containers as long
as none of the components have been realized.  A component has been realized when it can
receive paint or validation events.  This happens as soon as show(), setVisible() or pack()
is invoked on the component or the container it is added too.

Create javadoc for public and protected members: javadoc -d html  com.ibm.transform.oim.eacm.xml.editor
*/
// $Log: XMLEditor.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.8  2006/11/03 00:09:23  wendy
// MN29873273 added check for multiple windowlisteners causing close->cancel problems
//
// Revision 1.7  2006/10/24 16:40:14  wendy
// TIR 6UQGPT, enforce spellcheck if meta has rule
//
// Revision 1.6  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.5  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.4  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.3  2005/10/04 17:48:27  wendy
// Ignore Jspell messages during dereference
//
// Revision 1.2  2005/10/04 15:44:52  wendy
// Verify close body tag exists when setting error msg
//
// Revision 1.1.1.1  2005/09/09 20:39:18  tony
// This is the initial load of OPICM
//
//
public class XMLEditor extends JPanel implements XMLEditorGlobals, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "3.21";
    // 2.1 with <pre> for v11 2.2 with <table> for v111 and jre1.4.0
    // 2.21 changes for JavaUI and Frame usage instead of Dialog
    // 2.22 for FB50417 prevent specifying too many table items to delete
    // 2.23 for CR051603653 change max to bytes instead of chars v1.2c
    // 2.24 for FB52443 replaceAll loop, FB52437 for component version alignment
    // 2.25 for FB53138:79E506 changed 32768 max to 32000 max
    // 2.26 for FB53237:575129 prevent > 32k in single element
    // 2.27 for CR TBD from CCE handle TradeMark character entity reference
    // 3.0 new package, jtest changes
    // 3.1 changed to EACM
    // 3.2 added spellcheck requirement TIR 6UQGPT, enforce spellcheck if meta has rule
    // 3.21 added check for multiple windowlisteners causing close->cancel problems MN29873273

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 590;
    private static final int BLOCK_4K = 4096;
    private static final int MAX_STR_LEN=80;
    private static final int MONO_SIZE=12;

    private int x_width = DEF_WIDTH;
    private int x_height = DEF_HEIGHT;
    // myActions will be all shared actions created for the editor like cut, copy, paste
    // or spellcheck.. etc
    // dtdActions are specific actions supported by the current dtd
    private Hashtable<Object, Action> myActions= new Hashtable<Object, Action>();
    private Hashtable<String, HashSet<?>> dtdActions=new Hashtable<String, HashSet<?>>();
    // pulldown and popups use their own menu items but share actions, save the
    // original list to restore menus when dtd is changed
    private Hashtable<String, Vector<JMenuItem>> origPulldownMenuItems = new Hashtable<String, Vector<JMenuItem>>();
    private Hashtable<String, Vector<JMenuItem>> origPopupMenuItems = new Hashtable<String, Vector<JMenuItem>>();
    private XMLEditorPane editor=null;
    private boolean inApplet = false;
    private boolean inDialogApplet = false;
    private JPopupMenu popupMenu = new JPopupMenu();
    private JRootPane rootPane=null;

    private XMLUndoEditMgr editUndoMgr=null;

    private MyCaretListener caretListener=null;
    private MyKeyListener x_keyListener=null;

    private boolean userCanEdit = false;
    private JLabel statusBar=null;

    // to be used for find/replace dialog
    /* find/repl test*/
    private XMLFindReplaceMgr findRepMgr=null;
    private XMLPrintMgr printMgr=null;

    /**/
    // markup test
    /*private XMLMarkupMgr markupMgr;
*/

    // spell checker
    private XMLJSpellHandler spellCheckerHandler=null;
    private String eolprop=null;
    private XMLJSpellWrapper wrapper = null;

    private Component[] menuBarComponents = null;
    private Component[] popupMenuComponents = null;
    private Component[] toolBarComponents = null;

    // menu specifications
    // These must be final static because the Editor class can be shared
    // across multiple applets, any change will affect all instances
    /* markup test
    private final static XMLEditorMenuSpec[] markupSpec = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Start markup mode", SetMarkupAction,'S',
            "Start markup mode"),
        new XMLEditorMenuSpec("Accept all edits", CompleteMarkupAction,"a",'A',
            "Accept all edits"),
        new XMLEditorMenuSpec("Reject all edits", CompleteMarkupAction,"r",'R',
            "Reject all edits"),
        new XMLEditorMenuSpec("Find Next edit", FindMarkupAction,"n",'N',
            "Find Next edit"),
        new XMLEditorMenuSpec("Find Previous edit", FindMarkupAction,"p",'P',
            "Find Previous edit"),
        new XMLEditorMenuSpec("Accept current edit", PartialMarkupAction,"a",'C',
            "Accept current edit"),
        new XMLEditorMenuSpec("Reject current edit", PartialMarkupAction,"r",'E',
            "Reject current edit")
    };*/

    private final static XMLEditorMenuSpec[] EDIT_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Undo", UNDO_EDIT_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK),'U',
            "Undo previous edit action"),
        new XMLEditorMenuSpec("Redo", REDO_EDIT_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK),'R',
            "Redo last edit action"),
        new XMLEditorMenuSpec(XMLEditorMenuSpec.ADD_SEPARATOR, ""),
        new XMLEditorMenuSpec("Cut", DefaultEditorKit.cutAction,
            KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK),'T',
            "Cut selected content"),
        new XMLEditorMenuSpec("Cut With Structure", CUT_STRUCTURE_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK),'W',
            "Cut selected content and structure"),
        new XMLEditorMenuSpec("Copy", DefaultEditorKit.copyAction,
            KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK),'C',
            "Copy selected content"),
        new XMLEditorMenuSpec("Copy With Structure", COPY_STRUCTURE_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK),'Y',
            "Copy selected content and structure"),
        new XMLEditorMenuSpec("Paste", DefaultEditorKit.pasteAction,
            KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK),'P',
            "Paste contents of clipboard"),

/*find/repl test*/
        new XMLEditorMenuSpec(XMLEditorMenuSpec.ADD_SEPARATOR, ""),
        new XMLEditorMenuSpec("Find/Replace...", FIND_REPL_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK),'F',
            "Find or replace text"),
        new XMLEditorMenuSpec("Find Next", FIND_NEXT_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_MASK),'D',
            "Find next"),
        new XMLEditorMenuSpec("Replace Next", REPLACE_NEXT_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK),'L',
            "Replace next"),
/*end find/repl test*/
        new XMLEditorMenuSpec(XMLEditorMenuSpec.ADD_SEPARATOR, ""),
        new XMLEditorMenuSpec("Select All", DefaultEditorKit.selectAllAction,
            KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK),'A',
            "Select everything in the editor"),
        new XMLEditorMenuSpec("Spell Check", SPELLCHECK_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK),'S',
            "Perform spell check on the contents.  Undo history will be reset."),
/*markup test
new XMLEditorMenuSpec("Markup",markupSpec,'M',null),
*/
        new XMLEditorMenuSpec(XMLEditorMenuSpec.ADD_SEPARATOR, ""),
        new XMLEditorMenuSpec("Show Length", SHOW_LENGTH_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK),'L',
            "Show current length of complete XML source.")
    };
    private final static XMLEditorMenuSpec[] VIEW_SPEC = new XMLEditorMenuSpec[] {
        // don't use VK_W.. closes browser!!!
        new XMLEditorMenuSpec(WEB_ONLY_CONTENT, VIEW_WEBONLY_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK),'W',
            "View content in Web Only mode."),
        new XMLEditorMenuSpec(PRINT_ONLY_CONTENT, VIEW_PRINTONLY_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK),'P',
            "View content in Print Only mode."),
        new XMLEditorMenuSpec(TAG_CONTENT, VIEW_TAG_ACTION,
            KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK),'X',
            "View XML source for this document.")
    };

/* not supported at this time

    private final static XMLEditorMenuSpec[] colorSpec = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Red", FontColorAction, XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.red),convertColorToRGB(Color.red)),
        new XMLEditorMenuSpec("Green", FontColorAction, XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.green),convertColorToRGB(Color.green)),
        new XMLEditorMenuSpec("Blue", FontColorAction,XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.blue),convertColorToRGB(Color.blue)),
        new XMLEditorMenuSpec("Orange", FontColorAction,XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.orange),convertColorToRGB(Color.orange)),
        new XMLEditorMenuSpec("Yellow", FontColorAction,XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.yellow),convertColorToRGB(Color.yellow)),
        new XMLEditorMenuSpec("Magenta", FontColorAction,XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.magenta),convertColorToRGB(Color.magenta)),
        new XMLEditorMenuSpec("Black", FontColorAction,XMLEditorMenuSpec.PLAIN,
                                new ColoredBox(Color.black),convertColorToRGB(Color.black)),
        new XMLEditorMenuSpec("Custom color", CustomColorChooserAction)
    };
*/

/* not supported at this time
    // could be in a vector and combobox, but looks odd on pulldown
    private final static XMLEditorMenuSpec[] sizeSpec = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("-7", FontSizeAction),
        new XMLEditorMenuSpec("-6", FontSizeAction),
        new XMLEditorMenuSpec("-5", FontSizeAction),
        new XMLEditorMenuSpec("-4", FontSizeAction),
        new XMLEditorMenuSpec("-3", FontSizeAction),
        new XMLEditorMenuSpec("-2", FontSizeAction),
        new XMLEditorMenuSpec("-1", FontSizeAction),
        new XMLEditorMenuSpec("+1", FontSizeAction),
        new XMLEditorMenuSpec("+2", FontSizeAction),
        new XMLEditorMenuSpec("+3", FontSizeAction),
        new XMLEditorMenuSpec("+4", FontSizeAction),
        new XMLEditorMenuSpec("+5", FontSizeAction),
        new XMLEditorMenuSpec("+6", FontSizeAction),
        new XMLEditorMenuSpec("+7", FontSizeAction)
    };

    // Menu definitions for fonts
    private final static XMLEditorMenuSpec[] fontSpec = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Size", sizeSpec),
        new XMLEditorMenuSpec("Color", colorSpec)
    };
*/
    private final static XMLEditorMenuSpec[] HEADING_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("h1",H1_ACTION,'1',"Heading level 1"),
        new XMLEditorMenuSpec("h2",H2_ACTION,'2',"Heading level 2"),
        new XMLEditorMenuSpec("h3",H3_ACTION,'3',"Heading level 3"),
        new XMLEditorMenuSpec("h4",H4_ACTION,'4',"Heading level 4"),
        new XMLEditorMenuSpec("h5",H5_ACTION,'5',"Heading level 5"),
        new XMLEditorMenuSpec("h6",H6_ACTION,'6',"Heading level 6")
    };

    private final static XMLEditorMenuSpec[] TABLE_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Create...", INSERT_TABLE_ACTION,"table",'C',"New Table wizard"),
        new XMLEditorMenuSpec("Insert Row/Column...", INSERT_TR_TD_ACTION,"tr",'I',
            "Add row or column to the table"),
        new XMLEditorMenuSpec("Delete...", DELETE_TR_TD_ACTION,"tr",'D',
            "Delete a row or column from the table"),
        new XMLEditorMenuSpec("border", TOGGLE_BORDER_ACTION,//menu listener will modify text
            "table",'B',"Turn border on or off"),
        new XMLEditorMenuSpec("header", TOGGLE_TH_ACTION,//menu listener will modify text
            "table",'H',"Turn header on or off")
// NOTE: actionCmd must be in the set of supported tags returned for the DTD to be displayed
    };

    private final static XMLEditorMenuSpec[] NESTED_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Ordered List", INSERT_NESTED_OL_ACTION,"nol",'O',
            "Nested Ordered list with numbers"),
        new XMLEditorMenuSpec("Unordered List", INSERT_NESTED_UL_ACTION,"nul",'U',
            "Nested Unordered list with bullets"),
        new XMLEditorMenuSpec("Preformatted Text", INSERT_NESTED_PRE_ACTION,"npre",'E',
            "Nested Preformatted text"),
        new XMLEditorMenuSpec("Paragraph", INSERT_NESTED_P_ACTION,"pn",'P',
            "Nested Paragraph")
    };

    private final static XMLEditorMenuSpec[] TEXT_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec(B_STYLE, FONT_B_ACTION,
            XMLEditorMenuSpec.CHECKBOX,"b",'B',"Mark text as bold"),
        new XMLEditorMenuSpec(I_STYLE, FONT_I_ACTION,
            XMLEditorMenuSpec.CHECKBOX,"i",'I',"Mark text as italic"),
        new XMLEditorMenuSpec(U_STYLE, FONT_U_ACTION,
            XMLEditorMenuSpec.CHECKBOX,"u",'U',"Mark text as underlined"),
        new XMLEditorMenuSpec(STRONG_STYLE, FONT_STRONG_ACTION,
            XMLEditorMenuSpec.CHECKBOX,"strong",'S',"Mark text as strong"),
        new XMLEditorMenuSpec(EM_STYLE, FONT_EM_ACTION,
            XMLEditorMenuSpec.CHECKBOX,"em",'M',"Mark text as emphasized"),
        new XMLEditorMenuSpec(XMLEditorMenuSpec.ADD_SEPARATOR, ""),
        new XMLEditorMenuSpec("Unordered List", INSERT_UL_ACTION,"ul",'N',
            "Unordered list with bullets"),
        new XMLEditorMenuSpec("Ordered List", INSERT_OL_ACTION,"ol",'O',
            "Ordered list with numbers"),
        new XMLEditorMenuSpec("List Item", INSERT_LI_ACTION,"li",'L',
            "List item"),
        new XMLEditorMenuSpec("Parent List Item", INSERT_PARENT_LI_ACTION,"pli",'R',
            "List item in parent structure"),
        new XMLEditorMenuSpec("Preformatted Text", INSERT_PRE_ACTION,"pre",'E',
            "Preformatted text"), //v11
        new XMLEditorMenuSpec("Paragraph", INSERT_P_ACTION,"p",'P',
            "Paragraph"),
    //  new XMLEditorMenuSpec("Horizontal Rule", InsertEmptyAction,"hr",'Z'),
        new XMLEditorMenuSpec("Line Break", INSERT_BR_ACTION,"br",'K',
            "Line break within current structure"),
        new XMLEditorMenuSpec("Nested",NESTED_SPEC,'S',INSERT_NESTED_OL_ACTION),
        new XMLEditorMenuSpec("Headings",HEADING_SPEC,'D',H1_ACTION),
        new XMLEditorMenuSpec("Table", TABLE_SPEC,'T',INSERT_TABLE_ACTION),
        new XMLEditorMenuSpec("Special Characters...", INSERT_SPECCHAR_ACTION,"sc",'C',
            "Special Characters")
    };

    // HTML menu
    private final static XMLEditorMenuSpec[] HTML_SPEC = new XMLEditorMenuSpec[] {
        new XMLEditorMenuSpec("Web Only",INSERT_WEBONLY_ACTION,
            " "+PUBLISH_TAG_ATTR+"=\""+WEBONLY_TAG_ATTR+"\"",'W',
            "Web Only publish tag attribute"),
        new XMLEditorMenuSpec("Print Only",INSERT_PRINTONLY_ACTION,
            " "+PUBLISH_TAG_ATTR+"=\""+PRINTONLY_TAG_ATTR+"\"",'P',
            "Print Only publish tag attribute")
    };

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE_STR = new String(FOOL_JTEST);
    static final String NEWLINE = System.getProperty("line.separator");

/* not supported at this time
    private static final Vector FONT_FAMILY_VCT;
    static {
        GraphicsEnvironment gEnv =
              GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        FONT_FAMILY_VCT = new Vector();
        for ( int i = 1; i < envfonts.length; i++ ) {
           FONT_FAMILY_VCT.addElement(envfonts[i]);
        }
    }*/

    //private static ResourceBundle dialogText = ResourceBundle.getBundle("xmleditor-dialog");

    /*********************************************************************
    * Use a resource to get the text for the dialog
    * This will not be used now because it slows the load of the applet down too
    * much and there is no requirement for multiple languages on the
    * controls and messages
    */
    static String getDialogText(String name)
    {
    //  try
    //  {
    //      return dialogText.getString(name);
    //  }
    //  catch(MissingResourceException mre)
    //  {
        return name;
    //  }
    }

    /*********************************************************************
    * Gets GWA compliant XML with all elements. 'publish=webonly' attribute
    * will be removed and 'publish=printonly' will be changed to use a format
    * style.
    *
    * @param text       String in UTF8 charset
    * @return String    XML
    */
    static public String getWebOnlyXML(String text)
    {
        String txt = null;
        if (text==null||text.length()==0){
            txt= "";
        }
        else {
            txt = XMLGenerator.generateXML(text,WEBONLY_VIEW_TYPE);
        }

        return txt;
    }

    /*********************************************************************
    * Gets GWA compliant XML with all elements. 'publish=printonly' attribute
    * will be removed and 'publish=webonly' will be changed to use a format
    * style.
    *
    * @param text       String in UTF8 charset
    * @return String    XML
    */
    static public String getPrintOnlyXML(String text)
    {
        String txt = null;
        if (text==null||text.length()==0){
            txt= "";
        }
        else {
            txt = XMLGenerator.generateXML(text,PRINTONLY_VIEW_TYPE);
        }
        return txt;
    }

    /*********************************************************************
    * Gets GWA compliant XML with the 'publish=xxx' tag attributes removed
    *
    * @param text       String in UTF8 charset
    * @return String    XML
    */
    static public String getNormalXML(String text)
    {
        String txt = null;
        if (text==null||text.length()==0){
            txt= "";
        }
        else {
            txt=XMLGenerator.generateXML(text,NORMAL_VIEW_TYPE);
        }

        return txt;
    }

    /*********************************************************************
    * Constructor
    *
    * Assumption: DTD will be specified with each load.
    *
    * @param rootPanex       JRootPane to add content and menu bar to
    * @param dictionary     JSpellDictionary used for spell checking
    */
    public XMLEditor(JRootPane rootPanex, JSpellDictionary dictionary)
    {
        this(rootPanex, dictionary, false);
    }
    /*********************************************************************
    * Constructor
    *
    * Assumption: DTD will be specified with each load.
    *
    * @param rootPanex       JRootPane to add content and menu bar to
    * @param dictionary     JSpellDictionary used for spell checking
    * @param isInDialogApplet  boolean true if running as dialog applet in BUI
    */
    XMLEditor(JRootPane rootPanex, JSpellDictionary dictionary, boolean isInDialogApplet)
    {
        AccessibleContext ac;
        JMenuBar theBar;
        // warn user that this will fail.. could throw an exception
        if (System.getProperty("java.version").compareTo("1.2")<=0)
        {
            System.err.println("This editor requires Java version 1.3 or greater!");
            System.exit(1);
        }

        this.rootPane = rootPanex;

        editor = new XMLEditorPane(this);

        editUndoMgr = new XMLUndoEditMgr(editor);

        // if this is running in an applet, close is not added to the menu
        inApplet = (rootPane.getTopLevelAncestor() instanceof JApplet);
        inDialogApplet = isInDialogApplet;

        if (rootPane.getTopLevelAncestor() instanceof XMLEditorListener){
            addXMLEditorListener((XMLEditorListener)rootPane.getTopLevelAncestor());
        }

        // right now setting editability is static, done at instantiation time
        // if this needs to be dynamic, like after user submits document and
        // lock is released, disable some actions.. right now.. no way to
        // switch to editable, only uneditable.. is there a need?
        editor.setEditable(false);  // prevent anything until control is loaded or new

        this.setLayout(new BorderLayout());

        add(new JScrollPane(editor),"Center");

        // Set the editor to show the popup menu.
        editor.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent evt) {
                    if (evt.isPopupTrigger()) {
                        popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
        });

        // markup test
        //markupMgr = new XMLMarkupMgr(editor, editUndoMgr);

        // create menubar and toolbar
        setupGUI();//dictionary);

        if (inApplet || getTopLevelAncestor() instanceof EATestFrame || inDialogApplet) {
            setWindowsLookAndFeel();
        }

        enableActions(false);  // start out as uneditable
        //enableDtdActions(false);

        // disable all pulldown and popup menus
        for (int i=0; i<popupMenu.getComponentCount(); i++)
        {
            Component comp = popupMenu.getComponent(i);
            // disable all menus
            ((JMenu)comp).setEnabled(false);
        }

        // disable pulldown menus
        theBar = rootPane.getJMenuBar();
        for (int i=0; i<theBar.getMenuCount(); i++)
        {
            JMenu menu = theBar.getMenu(i);
            menu.setEnabled(false);
        }

        editor.addPropertyChangeListener(this);

        if (dictionary !=null)
        {
            try
            {
                JSpellParser spellParser;
                wrapper = new XMLJSpellWrapper(editor);
                // looks like different languages will use different parsers based
                // on preliminary JSpell documentation, change code to pass in
                // a parser for instantiation instead of a dictionary.  applet will
                // have to provide the correct parser based on nls id.. java ui will
                // need to also
                /* from JSpell doc Nov 9, 2001
                The JSpellParser is responsible for interacting with the JSpellDictionary that is passed into the constructor
                or is set using the setter method. The JSpellParser maintains a copy of the text being checked internally
                and performs all of its processing using this internal representation of the text. The GUI portions of the
                JSpell library use the get and setTextString directly when working with text components. The JSpellParser
                is where much of the English language specific processing is being performed. Note: This class in the
                future will delegate language specific processing to language specific parsers.
                */
                spellParser = new JSpellParser(dictionary);
                spellCheckerHandler = new XMLJSpellHandler(spellParser,wrapper, editor,rootPane.getTopLevelAncestor());

                addPropertyChangeListener(spellCheckerHandler);
            }catch(Exception re)
            {
                // disable the action permanently, leave icon
                SpellCheckerAction act = (SpellCheckerAction)myActions.get(SPELLCHECK_ACTION);
                System.err.println("Exception with spell checker initialization "+re.getMessage());
                re.printStackTrace(System.err);
                act.disable();//setEnabled(false);
                // remove it so it will not get re-enabled
                //myActions.remove(SPELLCHECK_ACTION);
            }
        }
        else
        {
            // disable the action permanently, leave icon
            SpellCheckerAction act = (SpellCheckerAction)myActions.get(SPELLCHECK_ACTION);
            act.disable();//setEnabled(false);
            /*// remove it so it will not get re-enabled
            myActions.remove(SPELLCHECK_ACTION);
            // remove the spell check menu item
            for (int i=0; i<theBar.getMenuCount(); i++)
            {
                JMenu menu = theBar.getMenu(i);
                if (menu.getText().equals("Edit"))
                {
                    int itemCount = menu.getItemCount();
                    for (int ii=0; ii<itemCount; ii++)
                    {
                        JMenuItem item = menu.getItem(ii);
                        if (item==null) continue;  // separators are null

                        if (item.getText().equals("Spell Check"))
                        {
                            item.getUI().uninstallUI(item);
                            EventListener listeners[] = item.getListeners(ActionListener.class);
                            for(int it=0; it<listeners.length; it++)
                            {
                                item.removeActionListener((ActionListener)listeners[it]);
                                listeners[it]=null;
                            }
                            menu.remove(item);
                            break;
                        }
                    }
                    break;
                }
            }
            // remove spell check menu item
            for (int i=0; i<popupMenu.getComponentCount(); i++)
            {
                JMenu menu = (JMenu)popupMenu.getComponent(i);
                if (menu.getText().equals("Edit"))
                {
                    int itemCount = menu.getItemCount();
                    for (int ii=0; ii<itemCount; ii++)
                    {
                        JMenuItem item = menu.getItem(ii);
                        if (item==null) continue;  // separators are null

                        if (item.getText().equals("Spell Check"))
                        {
                            item.getUI().uninstallUI(item);
                            EventListener listeners[] = item.getListeners(ActionListener.class);
                            for(int it=0; it<listeners.length; it++)
                            {
                                item.removeActionListener((ActionListener)listeners[it]);
                                listeners[it]=null;
                            }
                            menu.remove(item);
                            break;
                        }
                    }
                    break;
                }
            }*/
        }

        ac = getAccessibleContext();
        ac.setAccessibleName("XML Data Attribute Editor");
    }

    /*********************************************************************
    * process property change events
    * @param evt
    */
    public void propertyChange(PropertyChangeEvent evt)
    {
//System.out.println("Editor Property change for: "+evt.getPropertyName());
        if (evt.getPropertyName().equals("document"))
        {
            if (evt.getOldValue() instanceof XMLDocument)
            {
                // release memory for old document
                ((XMLDocument)evt.getOldValue()).dereference();
            }

            if (evt.getNewValue() instanceof XMLDocument)
            {
//System.out.println("property change ol document "+evt.getOldValue()+" new "+evt.getNewValue());

                XMLDocument doc = (XMLDocument)evt.getNewValue();
//System.out.println("property change new document "+doc);

                doc.setUndoEditMgr(editUndoMgr);

                // spell checker does not get text from the document, therefore
                // there is not a 1:1 correspondence with the caret position and
                // location of the mis-spelled word.
                // force the eol character to be \n only
                // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
                // if the original text contained them or if System.getProperty("line.separator")
                // specifies it.
                // get the original property
                eolprop = (String)doc.getProperty(DefaultEditorKit.EndOfLineStringProperty);
                // set it to \n to preserve 1:1 correspondence to internal mapping
                // if not done doc.getLength() returns a shorter length than the text
                // that is returned by getText()
                doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, NEWLINE_STR);
            }
        }
    }

    /**
     * XMLEditorListener is used to submit xml to db
     * @param l
     */
    public void addXMLEditorListener(XMLEditorListener l) {
        listenerList.add(XMLEditorListener.class, l);
    }

    /**
     * remove listener
     * @param l
     */
    public void removeXMLEditorListener(XMLEditorListener l) {
        listenerList.remove(XMLEditorListener.class, l);
    }

    XMLUndoEditMgr getUndoEditMgr() { return editUndoMgr; }

    /*********
    * used by Java UI to force memory release and cleanup v1.3
    * load must not be called after this is executed
    * Cleans up all internal structures
    */
    public void dereference()
    {
    	// remove these first
        editor.removeCaretListener(caretListener);
        caretListener = null;

        editor.removeKeyListener(x_keyListener);
        x_keyListener = null;

        JMenuBar theBar;
        EventListener listeners[];
        // clear all containers
        myActions.clear();
        myActions = null;
        dtdActions.clear();
        dtdActions = null;

        // this is the original pulldown menu items that are dependent on dtd such as heading
        for (Enumeration e = origPulldownMenuItems.elements(); e.hasMoreElements() ;)
        {
            Vector menuItemVct = (Vector)e.nextElement();
            for (int i=0; i<menuItemVct.size(); i++)
            {
                JMenuItem item = (JMenuItem) menuItemVct.elementAt(i);
                if (item !=null)  // not a separator
                {
                    listeners = item.getListeners(ActionListener.class);
                    item.getUI().uninstallUI(item);
                    for(int it=0; it<listeners.length; it++)
                    {
                        item.removeActionListener((ActionListener)listeners[it]);
                        listeners[it]=null;
                    }
                }
            }
            menuItemVct.clear();
        }
        origPulldownMenuItems.clear();
        origPulldownMenuItems=null;
        // this is the original popup menu items that are dependent on dtd such as heading
        for (Enumeration e = origPopupMenuItems.elements(); e.hasMoreElements() ;)
        {
            Vector menuItemVct = (Vector)e.nextElement();
            for (int i=0; i<menuItemVct.size(); i++)
            {
                JMenuItem item = (JMenuItem) menuItemVct.elementAt(i);
                if (item !=null)  // not a separator
                {
                    listeners = item.getListeners(ActionListener.class);
                    item.getUI().uninstallUI(item);
                    for(int it=0; it<listeners.length; it++)
                    {
                        item.removeActionListener((ActionListener)listeners[it]);
                        listeners[it]=null;
                    }
                }
            }
            menuItemVct.clear();
        }
        origPopupMenuItems.clear();
        origPopupMenuItems=null;

        try{
            // dereference all menus, including menu items already released above
            for (int i=0; i<menuBarComponents.length; i++)
            {
                if (menuBarComponents[i] instanceof JMenu){
                    dereferenceMenus((JMenu)menuBarComponents[i]);}
            }
        }catch(Exception e) {
            System.out.println(e.getMessage()); // jtest req
        } // ignore it..
        menuBarComponents = null;
        theBar = rootPane.getJMenuBar();
        theBar.getUI().uninstallUI(theBar);
        theBar.removeAll();
        theBar=null;

        try{
            // dereference all menus, including menu items already released above
            for (int i=0; i<popupMenuComponents.length; i++)
            {
                if (popupMenuComponents[i] instanceof JMenu){
                    dereferenceMenus((JMenu)popupMenuComponents[i]);}
            }
        }catch(Exception e) {
            System.out.println(e.getMessage()); // jtest req
        } // ignore it..
        popupMenu.getUI().uninstallUI(popupMenu);
        popupMenu.removeAll();
        popupMenuComponents = null;

        try{
            // this array will have all buttons, not just ones used for the last dtd
            for (int i=0; i<toolBarComponents.length; i++)
            {
                if (toolBarComponents[i] instanceof JButton){
                    dereferenceButtons((JButton)toolBarComponents[i]);}
            }
        }catch(Exception e) {
            System.out.println(e.getMessage()); // jtest req
        } // ignore it..
        toolBarComponents = null;

        // null references
        eolprop = null;

		if (spellCheckerHandler!=null) {
        	removePropertyChangeListener(spellCheckerHandler);
        	spellCheckerHandler.dereference();
		}

        spellCheckerHandler = null;
        if (wrapper!=null){
            wrapper.dereference();
        }
        wrapper=null;

        editor.dereference();
        editor.removePropertyChangeListener(this);

        listeners = getListeners(XMLEditorListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeXMLEditorListener((XMLEditorListener)listeners[i]);
        }
        if (!inApplet)
        {
            listeners = ((Window)rootPane.getTopLevelAncestor()).getListeners(WindowListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                if (listeners[i] instanceof ExitListener)
                {
                    ((Window)rootPane.getTopLevelAncestor()).removeWindowListener((WindowListener)listeners[i]);
                }
                listeners[i]=null;
            }
        }

        editUndoMgr.dereference();
        editUndoMgr = null;

        /* find/repl test*/
        findRepMgr.dereference();
        findRepMgr = null;

        printMgr.dereference();
        printMgr = null;

        /*markup test*/
        //markupMgr.dereference();
        //markupMgr = null;

        dereferenceContainer(this);

        statusBar.setLabelFor(null);
        statusBar = null;

        rootPane.setJMenuBar(null);
        dereferenceContainer(rootPane.getTopLevelAncestor());

        setLayout(null);
        rootPane=null;
        popupMenu=null;
        editor = null;

        // release menuitems held in menuspec as a convenience to fill in the origXX vectors
        for (int i = 0; i<EDIT_SPEC.length; i++){
            EDIT_SPEC[i].dereference();}
        for (int i = 0; i<VIEW_SPEC.length; i++){
            VIEW_SPEC[i].dereference();}
        for (int i = 0; i<HEADING_SPEC.length; i++){
            HEADING_SPEC[i].dereference();}
        for (int i = 0; i<NESTED_SPEC.length; i++){
            NESTED_SPEC[i].dereference();}
        for (int i = 0; i<TABLE_SPEC.length; i++){
            TABLE_SPEC[i].dereference();}
        for (int i = 0; i<TEXT_SPEC.length; i++){
            TEXT_SPEC[i].dereference();}
        for (int i = 0; i<HTML_SPEC.length; i++){
            HTML_SPEC[i].dereference();}
    }

    // this clears the current menu items, and anything that is not dynamic based on dtd
    private void dereferenceMenus(JMenu menu)
    {
        EventListener listeners[];
        // remove all listeners
        for (int ii=0; ii<menu.getItemCount(); ii++)
        {
            JMenuItem item = menu.getItem(ii);

            // remove listeners
            if (item !=null)  // not a separator
            {
                listeners= item.getListeners(ActionListener.class);
                for(int i=0; i<listeners.length; i++)
                {
                    item.removeActionListener((ActionListener)listeners[i]);
                    listeners[i]=null;
                }

                if (item instanceof JMenu){
                    dereferenceMenus((JMenu)item);}
                else
                {
                    item.getUI().uninstallUI(item);

/*      item.setAction(null);
        item.setLayout(null);
        item.setAutoscrolls(false);

        item.setModel(null);
///////this doesn't seem to help!!! run again withoutsave to see if still ok
*/              }
            }
        }
        listeners = menu.getListeners(MenuListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            menu.removeMenuListener((MenuListener)listeners[i]);
            if (listeners[i] instanceof XMLEditorMenuListener)
            {
                ((XMLEditorMenuListener)listeners[i]).dereference();
            }
            listeners[i]=null;
        }

        menu.getUI().uninstallUI(menu);
        menu.removeAll();
    }

    private void dereferenceButtons(JButton button)
    {
        EventListener listeners[] = button.getListeners(ActionListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            button.removeActionListener((ActionListener)listeners[i]);
        }
        listeners = button.getListeners(MouseMotionListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            button.removeMouseMotionListener((MouseMotionListener)listeners[i]);
        }

        listeners = button.getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            button.removeFocusListener((FocusListener)listeners[i]);
            listeners[i]=null;
        }
        button.setToolTipText(null);
        button.setAction(null);
        button.setPreferredSize(button.getMinimumSize());// remove ref to Dimension
        button.setMaximumSize(button.getMinimumSize());// remove ref to Dimension
        button.getUI().uninstallUI(button);
        button.setIcon(null);
        button.setLayout(null);
        button.setAutoscrolls(false);

        button.setModel(null);

        button.removeAll();
        // force release of tooltip bug in jre 1.3.0r13a that hangs onto reference when
        // button is pressed and dialog is hidden
        try{
            ToolTipManager.sharedInstance().mouseExited(
                new MouseEvent(button,
                    0,//int id,
                    0,//long when,
                    0,//int modifiers,
                    0,//int x,
                    0,//int y,
                    1,//int clickCount,
                    false //boolean popupTrigger
            ));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());  // jtest req
        }// ignore it, bogus fix anyway
    }

    static void dereferenceContainer(Container cont)
    {
//System.out.println("deref cont: "+cont);
        // get all containers
        for (int i=0; i<cont.getComponentCount(); i++)
        {
            Component comp = cont.getComponent(i);
            if (comp instanceof Container)
            {
                dereferenceContainer((Container) comp);
                ((Container) comp).removeAll();
            }
        }
        if (cont instanceof JToolBar)
        {
            dereferenceToolBar((JToolBar)cont);
        }
        cont.removeAll();
    }
    static void dereferenceToolBar(JToolBar theBar)
    {
        theBar.setLayout(null);

        theBar.getUI().uninstallUI(theBar);
    }

    // Notify all listeners that have registered interest for
    // notification on this event type.
    private void fireCloseEvent()
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==XMLEditorListener.class) {
                ((XMLEditorListener)listeners[i+1]).editorClosing();
            }
        }
        listeners = null;
    }
    private boolean fireUpdateEvent()
    {
        boolean success=true;
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==XMLEditorListener.class) {
                success=success&&((XMLEditorListener)listeners[i+1]).updateRequested();
            }
        }
        listeners = null;

        return success;
    }

    private boolean hasEditorListeners()
    {
        boolean fnd = false;
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==XMLEditorListener.class){
                //return true;
                fnd = true;
                break;
            }
        }
        listeners = null;
        return fnd;//false;
    }

    /*********************************************************************
    * @return boolean   editable state of this control
    */
    boolean isEditable() { return userCanEdit; }

    void ignoreCaret(boolean b) { caretListener.ignoreCaret(b); }

    /*********************************************************************
    * Set listeners and restore caret.  This should be called on the event
    * dispatch thread.  Swing components are NOT thread safe.
    */
    private void loadingComplete()
    {
        // page is done loading!!

        // enable file and edit menus, they were disabled before the load
        // (in the constructor)
        // use resources to get menu text
        String fileMenuText = "File";//XMLEditorMenuSpec.getMenuText("File");
        String editMenuText = "Edit";//XMLEditorMenuSpec.getMenuText("Edit");
        String viewMenuText = "View";//XMLEditorMenuSpec.getMenuText("View");
        String helpMenuText = "Help";//XMLEditorMenuSpec.getMenuText("Help");

        // enable pulldown menus
        JMenuBar theBar = rootPane.getJMenuBar();
        for (int i=0; i<theBar.getMenuCount(); i++)
        {
            JMenu menu = theBar.getMenu(i);
            // enable File to allow close, other menus are controlled by property listeners
            // when menu actions are enabled/disabled
            if (menu.getText().equals(fileMenuText)||menu.getText().equals(editMenuText)
                ||menu.getText().equals(viewMenuText)||menu.getText().equals(helpMenuText))
            {
                menu.setEnabled(true);
            }
        }
        // enable edit on popup
        for (int i=0; i<popupMenu.getComponentCount(); i++)
        {
            JMenu comp = (JMenu)popupMenu.getComponent(i);
            // enable edit menu
            if (comp.getText().equals(editMenuText)||comp.getText().equals(viewMenuText))
            {
                comp.setEnabled(true);
            }
        }

        restoreCursors();

        if (caretListener == null){
            caretListener = new MyCaretListener();
        }

        // add caret listener, needed to enable copy even if user can't edit
        editor.addCaretListener(caretListener);

        if (x_keyListener == null){
            x_keyListener = new MyKeyListener();
        }
        editor.addKeyListener(x_keyListener);

        // page is done loading!!
        if (userCanEdit)
        {
            editor.setEditable(true);

            // at this point the document will not be changed for the editor
            editUndoMgr.start();

            enableActions(true);
            // dtd actions will be enabled by the caret listener

            // sometimes the caret is not visible.. so make sure it is
            // probably when editor is switched from not editable to
            // editable..
            editor.getCaret().setVisible(true);
        }

        caretListener.ignoreCaret(false);  //set dtd actions and make sure the listener will not ignore updates now

        // notify listeners that page is loaded
        firePropertyChange("page","empty", "new");

        // notify the user if they can not access the system clipboard
        if (inApplet || inDialogApplet)
        {
            // must be in a separate thread to allow Accessibility engine to run
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        editor.checkClipboardAccess();
                        setFocusInEditor();  // applet does not return focus to the editor
                    }
            });
        }
        else{  // JUI has intermittent problem where it attaches a listener and that listener
        	// disposes the parent frame, causing major problems if the user 'cancels' the close
        	// if more than one listener exists, remove it!! self preservation
         /*   SwingUtilities.invokeLater(new Runnable() {
                public void run() {
			        if(rootPane.getTopLevelAncestor()!=null){
						EventListener[] listeners = ((Window)rootPane.getTopLevelAncestor()).getListeners(WindowListener.class);
						if(listeners.length>1){
							System.err.println("ERROR XMLEditor.loadingComplete found too many WindowListeners: "+listeners.length);
							for(int i=0; i<listeners.length; i++) {
				                if (!(listeners[i] instanceof ExitListener)) {
									((Window)rootPane.getTopLevelAncestor()).removeWindowListener((WindowListener)listeners[i]);
									//com.elogin.FrameDialog is from JUI
									System.err.println("XMLEditor.loadingComplete REMOVING "+listeners[i]);
								}
							}
						}
					}
                }
            });*/
		}
//System.out.println("editor.loadingcomplete exiting");
//System.out.println("editor.hasfocus() "+editor.hasFocus());
        // this is needed to get the focus in an applet
    //  setFocusInEditor();
    }

    /*********************************************************************
    * Set all cursors to original state.  This should be called on the event
    * dispatch thread.  Swing components are NOT thread safe.
    */
    void restoreCursors()
    {
        // set on the event dispatch thread
        if (SwingUtilities.isEventDispatchThread())
        {
            rootPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            if (editor.isEditable()){
                editor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));}
            else{
                editor.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            rootPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                            if (editor.isEditable()){
                                editor.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));}
                            else{
                                editor.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }
        }
    }

    /*********************************************************************
    * Set all cursors to wait state.  This should be called on the event
    * dispatch thread.  Swing components are NOT thread safe.
    */
    void setWaitCursors()
    {
        // set on the event dispatch thread
        if (SwingUtilities.isEventDispatchThread())
        {
            rootPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            editor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            rootPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            editor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }
        }
    }

    /*********************************************************************
    * Set uneditable, needed if user can 'update' back to the database and
    * still see the document.. it will no longer have a lock and therefore
    * should no longer be editable
    */
    private void setUneditable()
    {
        if (userCanEdit){ // already not editable?
            // reset undo manager and action states
            editUndoMgr.stop();

            editor.setEditable(false);
            userCanEdit = false;

            enableActions(false);
            enableDtdActions(false);
        }
    }

    /*********************************************************************
    * Enable or disable actions.  This should be called on the event
    * dispatch thread.  Swing components are NOT thread safe.
    */
    private void enableActions(boolean editable)
    {
        String[] list = {
            SPELLCHECK_ACTION,
            VIEW_WEBONLY_ACTION,
            VIEW_PRINTONLY_ACTION,
            VIEW_TAG_ACTION,
            DefaultEditorKit.pasteAction,
            SHOW_LENGTH_ACTION,
            FIND_REPL_ACTION,
            FIND_NEXT_ACTION,
            REPLACE_NEXT_ACTION
            };

        if (!userCanEdit) { // don't override editor capability
            editable=false;}

        // disable my own actions, not static actions from 'super' classes
        if (!editable)
        {
            for (Enumeration e = myActions.keys(); e.hasMoreElements();)
            {
                Action a;
                String actionName = (String)e.nextElement();
                if (actionName.equals(DefaultEditorKit.copyAction) ||
                    actionName.equals(COPY_STRUCTURE_ACTION)||
                    actionName.equals(DefaultEditorKit.selectAllAction)||
                    actionName.equals(VIEW_WEBONLY_ACTION)||
                    actionName.equals(VIEW_PRINTONLY_ACTION)||
                    actionName.equals(VIEW_TAG_ACTION)||
                    actionName.equals(SHOW_LENGTH_ACTION)){
                    continue;
                }

                a = (Action) myActions.get(actionName);
                if (actionName.equals(FIND_REPL_ACTION)||
                    actionName.equals(FIND_NEXT_ACTION)){
//  ||actionName.equals(PRINT_PREVIEW_ACTION)||actionName.equals(PRINT_ACTION))
                    a.setEnabled(true);
                }
                else{
                    a.setEnabled(false);
                }
            }
        }
        else // enable a subset of my own actions; others are controlled by other events
        {
            for (int i=0; i<list.length; i++)
            {
                Action a = (Action)myActions.get(list[i]);
                if (a!=null){
                    a.setEnabled(editable);
                }
            }
        }

        // allow caretlistener to do this.. enableDtdActions(editable);
        list = null;
    }

    /*************************************************************************************
    * Disable all dtd actions or enable based on context (all actions on the HTML and TEXT menus)
    */
    void enableDtdActions(boolean editable)
    {
        Iterator actions;
        Set attrSet, depSet;
        XMLEditorKit editorKit;
        XMLDocument doc;
        int startPos,endPos,numNestedLevels, numLists;
        Vector tagVct;
        Action webOnlyAction,printOnlyAction;
        // set html actions to false enable later based on dtd content
        // there are 3 buckets, get actions from each
        Set basicSet = (Set)dtdActions.get(BASIC_DTD);
        if (basicSet!=null) {
            actions = basicSet.iterator();
    /*
    basic action InsertParagraph
    basic action Web Only
    basic action Print Only
    basic action InsertUnorderedList
    basic action InsertOrderedList
    attr action InsertBreak
    attr action font-italic
    attr action font-underline
    attr action font-bold
    dependant action InsertOrderedListItem
    dependant action InsertUnorderedListItem
    */
            while (actions.hasNext())
            {
                Action a = (Action) actions.next();
                a.setEnabled(false);
            }
            attrSet = (Set)dtdActions.get(ATTRIBUTE_DTD);
            actions = attrSet.iterator();
            while (actions.hasNext())
            {
                Action a = (Action) actions.next();
                a.setEnabled(false);
            }
            depSet = (Set)dtdActions.get(DEPENDENT_DTD);
            actions = depSet.iterator();
            while (actions.hasNext())
            {
                Action a = (Action) actions.next();
                a.setEnabled(false);
            }
            editorKit = (XMLEditorKit)editor.getEditorKit();
            // reset publish action text
            webOnlyAction = editorKit.getDtdAction(INSERT_WEBONLY_ACTION);
            printOnlyAction = editorKit.getDtdAction(INSERT_PRINTONLY_ACTION);
            if (webOnlyAction != null)
            {
                webOnlyAction.setEnabled(false);
                webOnlyAction.putValue(Action.NAME,INSERT_WEBONLY_ACTION);
            }
            if (printOnlyAction != null)
            {
                printOnlyAction.setEnabled(false);
                printOnlyAction.putValue(Action.NAME,INSERT_PRINTONLY_ACTION);
            }

            if (editable) { // disable dtd actions
                // all basic actions are always enabled because the structure is split to handle insertions
                actions = basicSet.iterator();
                while (actions.hasNext())
                {
                    Action a = (Action) actions.next();
                    if (a==webOnlyAction || a== printOnlyAction){// publish actions are in this set, don't enable here
                        continue;}
                    // nestedlist
                    // list actions can only be enabled if not crossing element hierarchy
                    if (a instanceof XMLReplaceListAction)
                    {
                        a.setEnabled(((XMLReplaceListAction)a).isValid(editor));
                    }
                    else{
                        a.setEnabled(true);
                    }
                }

                doc = (XMLDocument)editor.getDocument();
                // get caret position
                startPos = editor.getSelectionStart();
                // sometimes the selection seems to go beyond the length of the document
                endPos = Math.min(editor.getSelectionEnd(),doc.getLength());

                enableAttributeActions(attrSet, startPos, endPos);

                enablePublishAttributeActions(startPos, endPos, webOnlyAction,printOnlyAction);

                // get number of levels allowed, 1 means 1 level of nesting.. a LI can have a UL|OL
                numNestedLevels = ((XMLEditorKit)editor.getEditorKit()).getNumberNestedLevels();

                // enable any dependent actions such as li
                tagVct = doc.getDependentControllingTags(startPos);

                // find current depth of nested lists, 1 is toplevel list only, 2 is '1' level of nesting
                numLists = 0;
                for (int i=0; i<tagVct.size(); i++)
                {
                    String tagName = (String)tagVct.elementAt(i);
                    if (tagName.equals("ol")|| tagName.equals("ul"))
                    {
                        numLists++;
                    }
                }
        //System.err.println("XMLEditor:enableDtdActions numNestedLevels: "+numNestedLevels+" curdepth: "+numLists);

                actions = depSet.iterator();
                while (actions.hasNext())
                {
                    Action a = (Action) actions.next();
                    if (a.getValue(Action.NAME).equals(INSERT_LI_ACTION) && (tagVct.contains("ol")||tagVct.contains("ul")))
                    {
                        a.setEnabled(true);
                    }
                    else
                    if ((a.getValue(Action.NAME).equals(INSERT_NESTED_P_ACTION) ||
                            a.getValue(Action.NAME).equals(INSERT_NESTED_PRE_ACTION)) &&
                        (tagVct.contains("ul") || tagVct.contains("ol")))
                    {
                        Element elem;
                        // if current depth == numNestedLevels+1 do not enable
                        if (numLists== numNestedLevels+1)
                        {
        //                  System.err.println("Hit max depth, prevent enable of nested p/pre");
                            continue;
                        }

                        // if selection spans multiple list items, do not enable it
                        elem = doc.getParagraphElement(startPos);
                        while(!elem.getName().equals(HTML.Tag.LI.toString())){
                            elem = elem.getParentElement();}

                        if (elem.getStartOffset()<=startPos &&
                            elem.getEndOffset()>endPos){
                            a.setEnabled(true);}
                    }
                    else
                    if ((a.getValue(Action.NAME).equals(INSERT_TR_TD_ACTION) ||
                            a.getValue(Action.NAME).equals(TOGGLE_BORDER_ACTION) ||
                            a.getValue(Action.NAME).equals(TOGGLE_TH_ACTION) ||
                            a.getValue(Action.NAME).equals(DELETE_TR_TD_ACTION))&& tagVct.contains("table"))
                    {
                        a.setEnabled(true);     // enable table actions
                    }
                    else if (a.getValue(Action.NAME).equals(INSERT_PARENT_LI_ACTION))
                    {
                        // check for 2 levels of lists
                        //int numLists = 0;
                        //for (int i=0; i<tagVct.size(); i++)
                        //{
                        //  String tagName = (String)tagVct.elementAt(i);
                        //  if (tagName.equals("ol")|| tagName.equals("ul"))
                        //  {
                        //      numLists++;
                        if (numLists>1)
                        {
                            a.setEnabled(true);
                        //          break;
                        }
                        //  }
                        //}
                    }
                    else // new for nested lists..
                        if ((a.getValue(Action.NAME).equals(INSERT_NESTED_OL_ACTION) ||
                                a.getValue(Action.NAME).equals(INSERT_NESTED_UL_ACTION)) &&
                            (tagVct.contains("ol")||tagVct.contains("ul")))
                        {
                            // if current depth == numNestedLevels+1 do not enable
                            if (numLists== numNestedLevels+1)
                            {
        //                  System.err.println("Hit max depth, prevent enable of nested list");
                                continue;
                            }

                            a.setEnabled(true);
                        }
                }
                tagVct.clear();
                tagVct = null;
            }
        }
    }

    private void enableAttributeActions(Set attrSet, int startPos, int endPos)
    {
        MutableAttributeSet as;
        boolean hasBR=false;
        Action brAction;

        XMLEditorKit editorKit = (XMLEditorKit)editor.getEditorKit();
        XMLDocument doc = (XMLDocument)editor.getDocument();
        // attribute actions are context sensitive based on paragraph elements
        Element[] curTags = doc.getAttributeControllingTags(startPos, endPos);
        int tagCnt=0;

        // enable each attribute action if all tags support that action
        // bold, italic, underline are stored as attributes on an element
        Iterator actions = attrSet.iterator();
        while (actions.hasNext())
        {
            Action a = (Action) actions.next();
            String aName = (String)a.getValue(Action.NAME);
            for(int u=0; u<curTags.length; u++)
            {
                javax.swing.text.Element curElem = curTags[u];
                // for text actions, verify all elements allow this content
                // get the content that is valid for this tag
                Vector elemVct = editorKit.getContentElements(curElem.getName());

                // verify the content for this element supports this attribute action
                // that is, that bold can be a value for text in this element
                for (int ii=0; ii<elemVct.size(); ii++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ii);
                    String actionName = (String)XMLEditorKit.ACTION_NAMES_TBL.get(elem.name);
                    if (aName.equals(actionName))
                    {
                        tagCnt++;
                        break;
                    }
                }
            }

            if (tagCnt==curTags.length) { // all tags support this attribute action
                a.setEnabled(true);}

            tagCnt=0;  // reset
        }

        // LS wants to prevent bold italic text, if dtd specified this
        // we must disable the corresponding action.  At this point the style actions
        // will be enabled if the structure content allowed it

        // get list of styles controlling this location
        // curTags are all the affected elements
        // must check their attributes
//      MutableAttributeSet as = new SimpleAttributeSet(editorKit.getInputAttributes());
        // must do it this way, instantiating one does not copy values properly
        // for the StyleConstants check
        as = (MutableAttributeSet)editorKit.getInputAttributes().copyAttributes();
        if (endPos!=startPos) { // if there is a selection, check all elements
            for(int u=0; u<curTags.length; u++)
            {
                javax.swing.text.Element curElem = curTags[u];
                // get to content
                for (int x=0; x<curElem.getElementCount(); x++)
                {
                    hasBR = hasBR || buildAttributeSet(as,curElem.getElement(x), startPos,endPos);
                }
            }
        }
        // does bold control this location, check to see if other 'attributes' are allowed
        if (StyleConstants.isBold(as))
        {
            Object obj;
            Vector nestedVct = editorKit.getContentElements("b");
            actions = attrSet.iterator();
            obj = as.getAttribute(HTML.Tag.STRONG);
            actionloop:while (actions.hasNext())
            {
                AbstractAction a = (AbstractAction) actions.next();
                String aMnemonic = (String)a.getValue(XML_CONTROL_KEY);
                // is it bold or strong
                if ((aMnemonic.equals("b")&& obj==null) ||
                    (aMnemonic.equals("strong")&& obj!=null)) { // must allow toggle to off
                    continue;}

                // check nested vector of elements
                for (int i=0;i<nestedVct.size(); i++)
                {
                    javax.swing.text.html.parser.Element nestedElem =
                        (javax.swing.text.html.parser.Element)nestedVct.elementAt(i);
                    if (nestedElem.getName().equals(aMnemonic)){
                        continue actionloop;}
                }
                a.setEnabled(false);
            }
            nestedVct.clear();
            nestedVct=null;
        }
        // does italic control this location, check to see if other 'attributes' are allowed
        if (StyleConstants.isItalic(as))
        {
            Object obj;
            Vector nestedVct = editorKit.getContentElements("i");
            actions = attrSet.iterator();
            obj = as.getAttribute(HTML.Tag.EM);
            actionloop:while (actions.hasNext())
            {
                AbstractAction a = (AbstractAction) actions.next();
                String aMnemonic = (String)a.getValue(XML_CONTROL_KEY);
                // is it italic or em
                if ((aMnemonic.equals("i")&& obj==null) ||
                    (aMnemonic.equals("em")&& obj!=null)) { // must allow toggle to off
                    continue;}

                // check nested vector of elements
                for (int i=0;i<nestedVct.size(); i++)
                {
                    javax.swing.text.html.parser.Element nestedElem =
                        (javax.swing.text.html.parser.Element)nestedVct.elementAt(i);
                    if (nestedElem.getName().equals(aMnemonic)){
                        continue actionloop;}
                }
                a.setEnabled(false);
            }
            nestedVct.clear();
            nestedVct=null;
        }
        // does underline control this location, check to see if other 'attributes' are allowed
        if (StyleConstants.isUnderline(as))
        {
            Vector nestedVct = editorKit.getContentElements("u");
            actions = attrSet.iterator();
            actionloop:while (actions.hasNext())
            {
                AbstractAction a = (AbstractAction) actions.next();
                String aMnemonic = (String)a.getValue(XML_CONTROL_KEY);
                if (aMnemonic.equals("u")) { // must allow toggle to off
                    continue;}

                // check nested vector of elements
                for (int i=0;i<nestedVct.size(); i++)
                {
                    javax.swing.text.html.parser.Element nestedElem =
                        (javax.swing.text.html.parser.Element)nestedVct.elementAt(i);
                    if (nestedElem.getName().equals(aMnemonic)){
                        continue actionloop;}
                }
                a.setEnabled(false);
            }
            nestedVct.clear();
            nestedVct=null;
        }

        // all of previous will disable br if it is not allowed in styles
        // but if no styles are set and br part of selected text, styles must be disabled
        brAction = editorKit.getDtdAction(INSERT_BR_ACTION);
        if (brAction!=null && brAction.isEnabled())
        {
            if (hasBR)
            {
                // look at each action
                // does it allow br? if not, disable it
                actions = attrSet.iterator();
                actionloop:while (actions.hasNext())
                {
                    Vector nestedVct;
                    AbstractAction a = (AbstractAction) actions.next();
                    String aMnemonic = (String)a.getValue(XML_CONTROL_KEY);
                    if (aMnemonic.equals("br")) { // skip it
                        continue;
                    }

                    nestedVct = editorKit.getContentElements(aMnemonic);
                    // check nested vector of elements
                    for (int i=0;i<nestedVct.size(); i++)
                    {
                        javax.swing.text.html.parser.Element nestedElem =
                            (javax.swing.text.html.parser.Element)nestedVct.elementAt(i);
                        if (nestedElem.getName().equals("br")){
                            continue actionloop;
                        }
                    }
                    a.setEnabled(false);
                }
            }
            // if still enabled, check if selection and surrounding content prevents br
            if (brAction.isEnabled() && startPos != endPos && startPos!=0)
            {
                javax.swing.text.Element leftElem = doc.getCharacterElement(startPos-1);
                javax.swing.text.Element rightElem = doc.getCharacterElement(endPos);
                if (leftElem!=rightElem)  // attribute differences must exist
                {
                    if (leftElem.getName().equals(HTML.Tag.CONTENT.toString()) &&
                        rightElem.getName().equals(HTML.Tag.CONTENT.toString()))
                    {
                        AttributeSet leftSet = leftElem.getAttributes();
                        AttributeSet rightSet = rightElem.getAttributes();
                        // both are content, check if they are the same style
                        // if same style, does this style allow br?
                        // if not, disable br
                        // unfortunately AttributeSet.isEqual() doesn't work so must
                        // test each style
                        String styleType=null;
                        if (StyleConstants.isBold(leftSet) && StyleConstants.isBold(rightSet))
                        {
                            styleType = "b";
                        } else
                        if (StyleConstants.isItalic(leftSet) && StyleConstants.isItalic(rightSet))
                        {
                            styleType = "i";
                        } else
                        if (StyleConstants.isUnderline(leftSet) && StyleConstants.isUnderline(rightSet))
                        {
                            styleType = "u";
                        }

                        if (styleType !=null)  // a match was found
                        {
                            Vector nestedVct = editorKit.getContentElements(styleType);
                            // check nested vector of elements
                            for (int i=0;i<nestedVct.size(); i++)
                            {
                                javax.swing.text.html.parser.Element nestedElem =
                                    (javax.swing.text.html.parser.Element)nestedVct.elementAt(i);
                                if (nestedElem.getName().equals("br")){
                                    return;
                                }
                            }
                            brAction.setEnabled(false);
                            nestedVct.clear();
                            nestedVct=null;
                        }
                    }
                }
            }
        }
    }

    private boolean buildAttributeSet(MutableAttributeSet as,
        javax.swing.text.Element curElem, int startPos, int endPos)
    {
        boolean hasBR = false;
        if (curElem.isLeaf())
        {
            AttributeSet elemAs;
            if (curElem.getEndOffset()<=startPos || // elem is before caret
                curElem.getStartOffset()>=endPos)  { // elem is after caret
                //return
                hasBR =false;
            }else {
                elemAs = curElem.getAttributes();

                if (StyleConstants.isBold(elemAs))
                {
                    // is it strong or bold
                    Object obj = elemAs.getAttribute(HTML.Tag.STRONG);
                    if (obj == null){
                        as.addAttribute(HTML.Tag.B, SimpleAttributeSet.EMPTY);
                    }
                    else{
                        as.addAttribute(HTML.Tag.STRONG, SimpleAttributeSet.EMPTY);
                    }
                    StyleConstants.setBold(as, true);
                }
                if(StyleConstants.isItalic(elemAs))
                {
                    // is it italic or emphasis?
                    Object obj = elemAs.getAttribute(HTML.Tag.EM);
                    if (obj == null){
                        as.addAttribute(HTML.Tag.B, SimpleAttributeSet.EMPTY);
                    }
                    else{
                        as.addAttribute(HTML.Tag.STRONG, SimpleAttributeSet.EMPTY);
                    }
                    StyleConstants.setItalic(as, true);
                }
                if(StyleConstants.isUnderline(elemAs)){
                    StyleConstants.setUnderline(as, true);
                }

                //return
                hasBR =(curElem.getName().equals(HTML.Tag.BR.toString()));
            }
        }else{
            // get to content
            for (int x=0; x<curElem.getElementCount(); x++)
            {
                hasBR = hasBR || buildAttributeSet(as,curElem.getElement(x),startPos, endPos);
            }
        }
        return hasBR;
    }

    private void enablePublishAttributeActions(int startPos, int endPos, Action webOnlyAction,Action printOnlyAction)
    {
        XMLEditorKit editorKit = (XMLEditorKit)editor.getEditorKit();
        XMLDocument doc = (XMLDocument)editor.getDocument();

        // publish attribute actions are context sensitive based on paragraph elements or their parents
        Element[] curTags = doc.getPublishAttributeControllingTags(startPos, endPos);
        int tagCnt=0;

        // publish attributes must be handled differently because the parent such as ul, ol, table or
        // tr may support the attribute

        String curPublishValue=null;
        for(int u=0; u<curTags.length; u++)
        {
            AttributeSet attrs =curTags[u].getAttributes();
            // is the attribute for this action part of the list
            javax.swing.text.html.parser.Element elem = editorKit.getElement(curTags[u].getName());
            // is publish part of the list
            javax.swing.text.html.parser.AttributeList attlist = elem.getAttribute(PUBLISH_TAG_ATTR);
            if (attlist!=null)
            {
                // all selected must have the same value
                String tmpVal=null;
                // is this attribute already set.. if so what is it's value..
                if (attrs.isDefined(PUBLISH_TAG_ATTR)) // only look at the current attributeset
                {
                    if (!attrs.getAttribute(PUBLISH_TAG_ATTR).equals("none")){
                        tmpVal= attrs.getAttribute(PUBLISH_TAG_ATTR).toString();
                    }
                }
                // are all values the same for tags in the selection?
                if (u==0)  // first tag in the selection
                {
                    curPublishValue=tmpVal;
                    tagCnt++;
                }
                else
                {
                    if (curPublishValue==tmpVal) { // both may be null
                        tagCnt++;
                    }
                    else
                    {
                        if (curPublishValue!=null&&curPublishValue.equals(tmpVal)){
                            tagCnt++;}
                    }
                }
                // set action text
                if (tagCnt==curTags.length)  // all match
                {
                    if (curPublishValue!=null)
                    {
                        if (curPublishValue.equals(WEBONLY_TAG_ATTR))  // remove webonly
                        {
                            webOnlyAction.putValue(Action.NAME,"Remove "+INSERT_WEBONLY_ACTION);
                        }
                        else
                        if (curPublishValue.equals(PRINTONLY_TAG_ATTR))  // remove printonly
                        {
                            printOnlyAction.putValue(Action.NAME,"Remove "+INSERT_PRINTONLY_ACTION);
                        }
                    }
                }
            }
        }

        if (tagCnt==curTags.length)  // all tags support this attribute action
        {
            webOnlyAction.setEnabled(true);
            printOnlyAction.setEnabled(true);
        }
    }
    /*********************************************************************
    * GUI creation methods - all called on the main thread, not a problem
    * for Swing components because they have not been realized (shown) yet.
    **********************************************************************/
    /*********************************************************************
    * Create the GUI menus, toolbar and popup menu
    */
    private void setupGUI()//JSpellDictionary dictionary)
    {
        Hashtable actions;
        XMLEditorMenuListener menuListener;
        rootPane.getContentPane().add(this);
        // menu listener used to set menu state like bold checked or not
        menuListener = new XMLEditorMenuListener(editor);

        /* find/repl test*/
        findRepMgr = new XMLFindReplaceMgr(editor);

        printMgr = new XMLPrintMgr();

        actions = createActions();

        //Set up the menu bar.
        rootPane.setJMenuBar(createMenuBar(menuListener, actions));
        // add the toolbar
//        add(getToolBar(dictionary, actions),"North");
        add(getToolBar(actions),"North");
        // add the popup menu
        createPopupMenu(menuListener, actions);

        // add status bar
        statusBar = new JLabel("No Tag");
        add(statusBar,"South");
        statusBar.setLabelFor(editor);

        // memory cleanup
        actions.clear();
        actions = null;
    }

    /*********************************************************************
    * Menu bar and menu creation methods
    **********************************************************************/
    /*private static String convertColorToRGB(Color color)
    {
        StringBuffer rgb = new StringBuffer("#");
        String num = Integer.toHexString(color.getRed());
        if (num.length()==1)
            num = "0"+num;
        rgb.append(num);
        num = Integer.toHexString(color.getGreen());
        if (num.length()==1)
            num = "0"+num;
        rgb.append(num);
        num = Integer.toHexString(color.getBlue());
        if (num.length()==1)
            num = "0"+num;
        rgb.append(num);
        return rgb.toString();
    }*/

    /*********************************************************************
    * Create the Menu bar
    */
    private JMenuBar createMenuBar(XMLEditorMenuListener menuListener, Hashtable actions)
    {
        JMenu menu;
        JCheckBoxMenuItem cbitem;
        Vector<JMenuItem> origmi = new Vector<JMenuItem>(VIEW_SPEC.length);

        JMenuBar theBar = new JMenuBar();
        // use resources to get menu text
        String editMenuText = XMLEditorMenuSpec.getMenuText("Edit");
        String viewMenuText = XMLEditorMenuSpec.getMenuText("View");
        String textMenuText = XMLEditorMenuSpec.getMenuText("Text");
        //String fontMenuText = XMLEditorMenuSpec.getMenuText("Font");
        //String familyMenuText = XMLEditorMenuSpec.getMenuText("Family");
        String htmlMenuText = XMLEditorMenuSpec.getMenuText("HTML");
        String showTipsMenuText = "Show XML Tool Tips";//XMLEditorMenuSpec.getMenuText("Show Tool Tips");

        // add File menu
        theBar.add(createFileMenu(actions));

        // add the Edit menu
        menu = XMLEditorMenuSpec.buildMenu(editMenuText,EDIT_SPEC,actions,'E');
        theBar.add(menu);

        // add show tool tips to edit menu (not on popup)
        cbitem = new JCheckBoxMenuItem(showTipsMenuText,null);
        cbitem.setMnemonic('X');
        cbitem.setState(editor.isShowingXMLToolTips());
        cbitem.getAccessibleContext().setAccessibleDescription("Turn XML tool tips on and off.");
        cbitem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    editor.showXMLToolTips(!editor.isShowingXMLToolTips());
                    evt=null;// jtest req
                }
        });
        menu.add(cbitem);
        cbitem = null;

        // add the View menu
        menu = XMLEditorMenuSpec.buildMenu(viewMenuText,VIEW_SPEC,actions,'V');
        theBar.add(menu);
        for (int i=0; i<VIEW_SPEC.length; i++){
            origmi.addElement(VIEW_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put(viewMenuText,origmi);

        // add the Text menu
        menu = XMLEditorMenuSpec.buildMenu(textMenuText,TEXT_SPEC,actions,'T');
        theBar.add(menu);
        menu.addMenuListener(menuListener);
        origmi = new Vector<JMenuItem>(TEXT_SPEC.length);
        for (int i=0; i<TEXT_SPEC.length; i++)
        {
            origmi.addElement(TEXT_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put(textMenuText,origmi);

        origmi = new Vector<JMenuItem>(HEADING_SPEC.length);
        for (int i=0; i<HEADING_SPEC.length; i++){
            origmi.addElement(HEADING_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put("Headings",origmi);

        origmi = new Vector<JMenuItem>(TABLE_SPEC.length);
        for (int i=0; i<TABLE_SPEC.length; i++){
            origmi.addElement(TABLE_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put("Table",origmi);

        origmi = new Vector<JMenuItem>(NESTED_SPEC.length);
        for (int i=0; i<NESTED_SPEC.length; i++){
            origmi.addElement(NESTED_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put("Nested",origmi);

        // add the Font menu
/*      menu = XMLEditorMenuSpec.buildMenu(fontMenuText,fontSpec,actions);
        JMenu fontmenu = new JMenu(familyMenuText);
        // put the fonts on the pulldown
        addFontFamilyComboBox(fontmenu);
        menu.add(fontmenu);
        theBar.add(menu);
        menu.addMenuListener(menuListener);
*/
        // Add the HTML menu
        menu = XMLEditorMenuSpec.buildMenu(htmlMenuText, HTML_SPEC, actions,'M');
        theBar.add(menu);
        origmi = new Vector<JMenuItem>(HTML_SPEC.length);
        for (int i=0; i<HTML_SPEC.length; i++){
            origmi.addElement(HTML_SPEC[i].getMenuItem());
        }
        origPulldownMenuItems.put(htmlMenuText,origmi);

        // add Help menu
        theBar.add(createHelpMenu());//actions));

        // save all components for later restorations
        menuBarComponents= theBar.getComponents();

        origmi = null;
        menu = null;

        return theBar;
    }

    // add actions to the global action table and the local action table
    private void addActions(Hashtable<Object, Action> actions, Action a)
    {
        actions.put(a.getValue(Action.NAME), a);
        myActions.put(a.getValue(Action.NAME), a);
        a.setEnabled(false);  // start out as disabled
    }
    /*********************************************************************
    * Get all actions for menubar and toolbar creation, there will be
    * static and instance actions in this collection
    */
    private Hashtable createActions()
    {
        Action selAct, a;
        // this table is used for all actions to create menu items and buttons
        Hashtable<Object, Action> actions = new Hashtable<Object, Action>();

        // get all default supported actions for this EditorKit, things like cut, copy, paste, page-down...
/*        Action[] actionsArray = editor.getActions();
        for (int i = 0; i < actionsArray.length; i++)
        {
            Action a = actionsArray[i];
            // the only action used with a menuitem or button that can be static is select-all, get it
            // can't use static select-all, it isn't dereferenced properly
            if (a.getValue(Action.NAME).equals(DefaultEditorKit.selectAllAction))
            {
                actions.put(a.getValue(Action.NAME), a);
                myActions.put(a.getValue(Action.NAME), a);
                // don't disable it.. it is static
                break;
            }
        }
*/
        // get all possible actions supported by the dtd
        Action[] actionsArray = ((XMLEditorKit)editor.getEditorKit()).getAllDTDActions();
        for (int i = 0; i < actionsArray.length; i++) {
            a = actionsArray[i];
            a.setEnabled(false);  // start out as disabled
            // do not store in the dtdAction table now.. not needed until loadDocument is done
            actions.put(a.getValue(Action.NAME), a);
        }

        // create local copies of the following actions because they
        // are static and disabling them will disable actions in ALL instances
        // of the Editor.. a problem in applets or multiple instances of the editor!
        addActions(actions,new DefaultEditorKit.CutAction());
        addActions(actions,new DefaultEditorKit.CopyAction());
        addActions(actions,new DefaultEditorKit.PasteAction());
        addActions(actions,new CopyStructureAction());
        addActions(actions,new CutStructureAction());
        // can't use static select-all, it isn't dereferenced properly, create one here
        selAct = new TextAction(DefaultEditorKit.selectAllAction) {
        	private static final long serialVersionUID = 1L;
        	public void actionPerformed(ActionEvent e)
                {
                    JTextComponent target = getTextComponent(e);
                    if (target != null)
                    {
                        Document doc = target.getDocument();
                        target.setCaretPosition(0);
                        target.moveCaretPosition(doc.getLength());
                    }
                }
            };
        actions.put(selAct.getValue(Action.NAME), selAct);
        myActions.put(selAct.getValue(Action.NAME), selAct);

        // create actions to view content, don't disable them
        a = new ViewHtmlAction(VIEW_WEBONLY_ACTION,WEBONLY_VIEW_TYPE);
        actions.put(a.getValue(Action.NAME), a);
        myActions.put(a.getValue(Action.NAME), a);
        a = new ViewHtmlAction(VIEW_PRINTONLY_ACTION,PRINTONLY_VIEW_TYPE);
        actions.put(a.getValue(Action.NAME), a);
        myActions.put(a.getValue(Action.NAME), a);
        a = new ViewHtmlAction(VIEW_TAG_ACTION,TAG_VIEW_TYPE);
        actions.put(a.getValue(Action.NAME), a);
        myActions.put(a.getValue(Action.NAME), a);
        a = new DisplayLengthAction();
        actions.put(a.getValue(Action.NAME), a);
        myActions.put(a.getValue(Action.NAME), a);

        // undo/redo
        actionsArray = editUndoMgr.getActions();
        for (int i = 0; i < actionsArray.length; i++)
        {
            addActions(actions,actionsArray[i]);
        }

        // find and replace
/*find/repl test*/
        actionsArray = findRepMgr.getActions();
        for (int i = 0; i < actionsArray.length; i++)
        {
            addActions(actions,actionsArray[i]);
        }
/*end find/repl test*/

        // spell check
        addActions(actions,new SpellCheckerAction());


        // update
        addActions(actions,new UpdateAction());

        // change fonts based on string selected in JComboBox font selection control
        //fontFamilyAction = new FontFamilyAction();
        //actions.put(FontFamilyFaceAction,fontFamilyAction);

        return actions;
    }

    /*********************************************************************
    * Create the help menu
    */
    private JMenu createHelpMenu()//Hashtable actions)
    {
        // use resources to get menu text
        String helpMenuText = XMLEditorMenuSpec.getMenuText("Help");

        JMenu menu = new JMenu(helpMenuText);

        JMenuItem item = new JMenuItem("Attribute Information",'I');
        menu.setMnemonic('H');
        item.getAccessibleContext().setAccessibleDescription("Help information about data element under edit.");

        // add the action
// using F1 as the help key in IE pops up our help and IE help!! modifications to the jsp prevent this
        item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                String msgs[];
                                String accdesc[];
                                String helpTxt = null;

                                EventListener listeners[] = getListeners(XMLEditorListener.class);
                                if (listeners.length>0)
                                {
                                    setWaitCursors();
                                    helpTxt=((XMLEditorListener)listeners[0]).attributeHelpTextRequested();

                                    restoreCursors();
                                }
                                if (helpTxt==null){
                                    helpTxt = NO_HELP_TXT;
                                }

                                // display it
    //                          JOptionPane.showMessageDialog(editor, helpfTxt,
    //                              XMLEditorGlobals.APP_NAME+" XML Editor Attribute Information", JOptionPane.INFORMATION_MESSAGE);

                                msgs = new String []{helpTxt};
    //System.err.println("helpTxt: "+helpTxt);
                                // some help strings are very long, break them up
                                if (helpTxt.length()>MAX_STR_LEN)
                                {
                                    Vector<String> vct = new Vector<String>(1);
                                    BreakIterator boundary = BreakIterator.getLineInstance();
                                    StringBuffer sb = new StringBuffer();

                                    StringTokenizer st = new StringTokenizer(helpTxt,NEWLINE_STR);
                                    while (st.hasMoreTokens())
                                    {
                                        int start,end,lineLength;
                                        String part = st.nextToken();
                                        boundary.setText(part);
                                        start = boundary.first();
                                        end = boundary.next();
                                        lineLength = 0;
                                        while (end != BreakIterator.DONE)
                                        {
                                            String word = part.substring(start,end);
                                            lineLength = lineLength + word.length();
                                            if (lineLength > MAX_STR_LEN)
                                            {
                                                lineLength = word.length();
                                                vct.addElement(sb.toString());
                                                sb.setLength(0);
                                            }
                                            sb.append(word);

                                            start = end;
                                            end = boundary.next();
                                        }
                                        if (sb.length()>0)
                                        {
                                            vct.addElement(sb.toString());
                                            sb.setLength(0);
                                        }

                                        /*int startid=0;
                                        for (int i=MAX_STR_LEN; i<part.length();i+=MAX_STR_LEN)
                                        {
                                            int blankid = part.indexOf(" ",i);
                                            if (blankid==-1)
                                                break;
                                            vct.addElement(part.substring(startid, blankid).trim());
                                            startid=blankid+1;
                                        }
                                        if (startid<part.length())
                                            vct.addElement(part.substring(startid).trim());*/
                                    }

                                    msgs = new String[vct.size()];

                                    vct.copyInto(msgs);
                                    vct.clear();
                                }

                                accdesc = new String[]{"Press OK to close dialog."};
                                showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                                    XMLEditorGlobals.APP_NAME+" XML Editor Attribute Information",
                                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_OPTION,
                                    "Display attribute help text.", msgs, accdesc);

                                msgs = null;
                                accdesc = null;

                                setFocusInEditor();  // applet does not return focus to the editor
                            }
                    });
                }
        });

        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menu.add(item);

        item = new JMenuItem("About",'A');
        item.getAccessibleContext().setAccessibleDescription("Help information about the XML editor.");

        // add the action
        item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    // display it
    //              JOptionPane.showMessageDialog(editor, array,
    ////                "XMLEditor "+VERSION+"\n"+
    ////                    "Using DTD \""+((XMLEditorKit)editor.getEditorKit()).getDTDName()+"\"\n"+
    ////                    "Running under "+System.getProperty("java.vm.vendor")+" "+
    ////                    System.getProperty("java.vm.name")+
    ////                    " Java \""+System.getProperty("java.vm.version")+"\"\n\n"+
    ////                    ABOUT_XML_EDITOR,
    //                  "About "+XMLEditorGlobals.APP_NAME+" XML Editor", JOptionPane.INFORMATION_MESSAGE);

                    // build button to get to list of component versions
                    String accdesc[] = {"Press OK to close dialog."};
                    Object msgs[];
                    ActionListener acl;
                    JPanel panel = new JPanel(false);
                    JButton button = new JButton();
                    String toolTipText = "List component versions";
                    ImageIcon icon = new ImageIcon(getClass().getResource("/images/more.gif"));

                    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                    panel.add(new JLabel("XMLEditor "+VERSION));
                    button.setToolTipText(toolTipText);
                    button.getAccessibleContext().setAccessibleName(toolTipText);
                    icon.setDescription(toolTipText);
                    button.setIcon(icon);
                    button.setRequestFocusEnabled(false);
                    button.setBorderPainted(false);

                    panel.add(button);
                    acl = new ActionListener()
                    {
/*
                        class MyKeyListener2 extends KeyAdapter
                        {
                            public void keyPressed(KeyEvent e)
                            {
                                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                                {
                                    Component comp;
                                    e.consume();
                                    comp = e.getComponent();
                                    while(comp!=null)
                                    {
                                        if (comp instanceof JOptionPane)
                                        {
                                            // change property, it will close the dialog
                                            ((JOptionPane)comp).setValue(new Integer(JOptionPane.YES_OPTION));
                                            break;
                                        }
                                        comp = comp.getParent();
                                    }
                                }
                            }
                        }
*/
                        public void actionPerformed(ActionEvent e)
                        {
                            String accdesc2[] = {"Press OK to close dialog."};
                            Object msgs2[];
                            MyKeyListener2 keylistener = new MyKeyListener2();
                            JTextArea ta = new JTextArea(
                                "XMLEditor:                 Revision: "+XMLEditor.VERSION+
                                NEWLINE_STR+"XMLDerefList:             "+XMLDerefList.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLDisplayDialog:         "+XMLDisplayDialog.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLDocument:              "+XMLDocument.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLDocumentParser:        "+XMLDocumentParser.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLDTD:                   "+XMLDTD.VERSION.replace('$',' ')+
                                //                             "\nXMLEditorDialogApplet:    "+XMLEditorDialogApplet.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLEditorKit:             "+XMLEditorKit.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLEditorMenuListener:    "+XMLEditorMenuListener.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLEditorMenuSpec:        "+XMLEditorMenuSpec.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLEditorPane:            "+XMLEditorPane.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLErrorDialog:           "+XMLErrorDialog.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLErrorObject:           "+XMLErrorObject.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLErrorHighlightPainter: "+XMLErrorHighlightPainter.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLFindDialog:            "+XMLFindDialog.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLFindReplaceMgr:        "+XMLFindReplaceMgr.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLGenerator:             "+XMLGenerator.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLJSpellHandler:         "+XMLJSpellHandler.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLJSpellWrapper:         "+XMLJSpellWrapper.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLParserDelegator:       "+XMLParserDelegator.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLPasteAction:           "+XMLPasteAction.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLPrintMgr:              "+XMLPrintMgr.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLReplaceListAction:     "+XMLReplaceListAction.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLTableCreatePanel:      "+XMLTableCreatePanel.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLTableRowColPanel:      "+XMLTableRowColPanel.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLTableWizard:           "+XMLTableWizard.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLToggleTHAction:        "+XMLToggleTHAction.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLUndoEditMgr:           "+XMLUndoEditMgr.VERSION.replace('$',' ')+
                                NEWLINE_STR+"XMLWriter:                "+XMLWriter.VERSION.replace('$',' '));

                            ta.setEditable(false);
                            ta.addKeyListener(keylistener);
                            if (!ta.getFont().getName().equalsIgnoreCase("monospaced")) {//FB52437:55133C alignment affected by font
                                ta.setFont(new Font("MonoSpaced", Font.PLAIN, MONO_SIZE));
                            }

                            msgs2 = new Object[] {ta};

                            showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                                XMLEditorGlobals.APP_NAME+" XML Editor Component Versions",
                                JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_OPTION,
                                "Display information about the editor's components.", msgs2, accdesc2);

                            ta.removeKeyListener(keylistener);
                            msgs2[0] = null;
                            msgs2=null;
                            accdesc2[0]=null;
                            accdesc2=null;
                        }
                    };
                    button.addActionListener(acl);

                    msgs= new Object[]{panel,
                        "Using DTD \""+((XMLEditorKit)editor.getEditorKit()).getDTDName()+"\"",
                        "Running under "+System.getProperty("java.vm.vendor")+" "+
                        System.getProperty("java.vm.name")+
                        " Java \""+System.getProperty("java.vm.version")+"\"",
                        " ",
                        ABOUT_XML_EDITOR1,
                        ABOUT_XML_EDITOR2,
                        " ",
                        ABOUT_XML_EDITOR3
                    };

                    showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                        "About "+XMLEditorGlobals.APP_NAME+" XML Editor",
                        JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_OPTION,
                        "Display information about the editor.", msgs, accdesc);

                    msgs = null;
                    accdesc = null;
                    button.removeActionListener(acl);

                    setFocusInEditor();  // applet does not return focus to the editor
                }
        });
        menu.add(item);

        return menu;
    }

    // used in help about window for editor
    private static class MyKeyListener2 extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                Component comp;
                e.consume();
                comp = e.getComponent();
                while(comp!=null)
                {
                    if (comp instanceof JOptionPane)
                    {
                        // change property, it will close the dialog
                        ((JOptionPane)comp).setValue(new Integer(JOptionPane.YES_OPTION));
                        break;
                    }
                    comp = comp.getParent();
                }
            }
        }
    }

    /*********************************************************************
    * Create the file menu
    */
    private JMenu createFileMenu(Hashtable actions)
    {
        Action a;
        JMenuItem item;
        Action[] actionArray;
        // use resources to get menu text
        String fileMenuText = XMLEditorMenuSpec.getMenuText("File");
        String closeMenuText = "Close";//XMLEditorMenuSpec.getMenuText("Close");

        JMenu menu = new JMenu(fileMenuText);
        menu.setMnemonic('F');

        // updating is controlled by the ancestor of this control, it must
        // call our writeDocument with an output stream or getCompletedXML()
        a = (Action)actions.get(UPDATE_DB_ACTION);
        // add the action
        item = menu.add(a);
        //item.setText(XMLEditorMenuSpec.getMenuText("Save"));
        item.setMnemonic('S');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        item.getAccessibleContext().setAccessibleDescription("Save data");

        a = null;
// diff approach.. without using menu.add(action), no change in left over JMenu ref if
// ctrl+s is used to save or menu item is used
/*
        JMenuItem item = new JMenuItem((String)a.getValue(Action.NAME),
                                     (Icon)a.getValue(Action.SMALL_ICON));
        item.setHorizontalTextPosition(JButton.RIGHT);
        item.setVerticalTextPosition(JButton.CENTER);
        item.setMnemonic('S');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        item.getAccessibleContext().setAccessibleDescription("Save data");
        item.setEnabled(a.isEnabled());
        item.setAction(a);
        //item.setText(XMLEditorMenuSpec.getMenuText("Save"));
        menu.add(item);
*/
        // add print actions
        menu.addSeparator();
        actionArray = printMgr.getActions();
        for (int i=0; i<actionArray.length; i++)
        {
            item = menu.add(actionArray[i]);
            item.setMnemonic(((Integer)(actionArray[i].getValue(Action.MNEMONIC_KEY))).intValue());
            if (actionArray[i].getValue(Action.ACCELERATOR_KEY) !=null){
                item.setAccelerator((KeyStroke)actionArray[i].getValue(Action.ACCELERATOR_KEY));
            }
            item.setText(XMLEditorMenuSpec.getMenuText((String)(actionArray[i].getValue(Action.NAME))));

//          myActions.put(actionArray[i].getValue(Action.NAME),actionArray[i]); // if need to access action
        }

// DEBUG WYSISWYG
/*      if (!inApplet)
        {
            item = new JMenuItem("Save to a File");
            menu.add(item);
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        //Writer w = new FileWriter(fileName);// + ".new");
                        FileOutputStream fos = new FileOutputStream(fileName);
                        writeCompletedXML(fos);
                        System.out.println("Content saved to " + fileName);// + ".new");
                    } catch (Throwable t) {
                        System.out.println("Error while saving: " + t);
                        t.printStackTrace();
                    }
                }
            });
            //item.setEnabled(editor.isEditable());
        }*/

        // don't add close to the applet, but will be added to dialogApplet
        if (!inApplet)
        {
            ExitListener exitListener = new ExitListener();
            item = new JMenuItem(closeMenuText);
            item.setMnemonic('C');
// this will close the browser if editor loses focus!!!!
// alt-F4 works without this.. but this would advertise it.. should it be displayed??
// but if dialog is modal it should still be ok.. but dangerous
//          item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));

            menu.addSeparator();
            menu.add(item);
            item.addActionListener(exitListener);
            item.getAccessibleContext().setAccessibleDescription("Close editor");

            ((Window)rootPane.getTopLevelAncestor()).addWindowListener(exitListener);

            // don't allow the window to close without checking for a save
            if (rootPane.getTopLevelAncestor() instanceof JDialog){
                ((JDialog)rootPane.getTopLevelAncestor()).setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
            if (rootPane.getTopLevelAncestor() instanceof JFrame){
                ((JFrame)rootPane.getTopLevelAncestor()).setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        }
        /*else
        {
            // if in applet, provide an about page to see which JRE is running
            // this is a problem because of the security manager, so must look at the plug-in
            // control panel?
            String info[] = {
                "java.home",
                "java.vm.name",
                "java.specification.version",
                "java.vendor",
                "java.vm.version",
                "java.runtime.version",
                "java.vm.info",
                "java.fullversion",
                "java.runtime.name"
            };

            for (int i=0; i<info.length; i++)
            {
                try{
                System.out.println(info[i]+" = "+System.getProperty(info[i]));
                }
                catch(java.security.AccessControlException e)
                {
                    System.out.println("exception: "+e.getMessage());
                }
            }
            /*
            exception: access denied (java.util.PropertyPermission java.home read)
            java.vm.name = Classic VM
            java.specification.version = 1.3
            java.vendor = IBM Corporation
            java.vm.version = 1.3.0
            exception: access denied (java.util.PropertyPermission java.runtime.version read)
            exception: access denied (java.util.PropertyPermission java.vm.info read)
            exception: access denied (java.util.PropertyPermission java.fullversion read)
            exception: access denied (java.util.PropertyPermission java.runtime.name read)
            */
        /*}*/
        item = null;

        return menu;
    }

    /*********************************************************************
    * Set GUI to have windows look and feel
    */
    private void setWindowsLookAndFeel()
    {
        try {
            //String currentLaf = UIManager.getLookAndFeel().getClass().getName();
            String cn = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

//          if (cn.equals(currentLaf))
//              return;  // no changes needed
// set it anyway.. if multiple applets are on a page it seems that there is an occasional race
// and the second one is not set because currentLaf already==cn.. at time of check

            UIManager.setLookAndFeel(cn);
/*     try {
         UIManager.setLookAndFeel("LowVisionMetalLookAndFeel");
     } catch (Exception ex) {
         System.out.println("Failed loading Low Vision Metal Look and Feel");
         ex.printStackTrace(System.out);
     }
*/
            SwingUtilities.updateComponentTreeUI(rootPane);
            SwingUtilities.updateComponentTreeUI(popupMenu);

            SwingUtilities.updateComponentTreeUI(findRepMgr); // find/repl
            findRepMgr.addListeners(); // reinstall listeners.. changing laf loses previous listeners
        }
        catch(Exception e)
        {
            System.err.println(e);
            e.printStackTrace(System.err);
        }
    }

    /*********************************************************************
    * Create the popup menu, mnemonic are meaningless in a popup, set them to blank
    */
    private void createPopupMenu(XMLEditorMenuListener menuListener, Hashtable actions)
    {
        Vector<JMenuItem> origmi;
        // use resources to get menu text
        String editMenuText = "Edit";//XMLEditorMenuSpec.getMenuText("Edit");
        String viewMenuText = "View";//XMLEditorMenuSpec.getMenuText("View");
        String textMenuText = "Text";//XMLEditorMenuSpec.getMenuText("Text");
        //String fontMenuText = XMLEditorMenuSpec.getMenuText("Font");
        String htmlMenuText = "HTML";//XMLEditorMenuSpec.getMenuText("HTML");
        //String familyMenuText = XMLEditorMenuSpec.getMenuText("Family");

        // add the Edit menu
        JMenu menu = XMLEditorMenuSpec.buildMenu(editMenuText,EDIT_SPEC,actions,' ');
        popupMenu.add(menu);

        // add the View menu
        menu = XMLEditorMenuSpec.buildMenu(viewMenuText,VIEW_SPEC,actions,' ');
        popupMenu.add(menu);
        origmi = new Vector<JMenuItem>(VIEW_SPEC.length);
        for (int i=0; i<VIEW_SPEC.length; i++){
            origmi.addElement(VIEW_SPEC[i].getMenuItem());}
        origPopupMenuItems.put(viewMenuText,origmi);

        // add the Text menu
        menu = XMLEditorMenuSpec.buildMenu(textMenuText,TEXT_SPEC,actions,' ');
        popupMenu.add(menu);
        menu.addMenuListener(menuListener);
        origmi = new Vector<JMenuItem>(TEXT_SPEC.length);
        for (int i=0; i<TEXT_SPEC.length; i++){
            origmi.addElement(TEXT_SPEC[i].getMenuItem());}
        origPopupMenuItems.put(textMenuText,origmi);

        origmi = new Vector<JMenuItem>(HEADING_SPEC.length);
        for (int i=0; i<HEADING_SPEC.length; i++){
            origmi.addElement(HEADING_SPEC[i].getMenuItem());}
        origPopupMenuItems.put("Headings",origmi);

        origmi = new Vector<JMenuItem>(TABLE_SPEC.length);
        for (int i=0; i<TABLE_SPEC.length; i++){
            origmi.addElement(TABLE_SPEC[i].getMenuItem());}
        origPopupMenuItems.put("Table",origmi);

        origmi = new Vector<JMenuItem>(NESTED_SPEC.length);
        for (int i=0; i<NESTED_SPEC.length; i++){
            origmi.addElement(NESTED_SPEC[i].getMenuItem());}
        origPopupMenuItems.put("Nested",origmi);

        // add the Font menu
/*      menu = XMLEditorMenuSpec.buildMenu(fontMenuText,fontSpec,actions);
        popupMenu.add(menu);
        menu.addMenuListener(menuListener);
        JMenu fontmenu = new JMenu(familyMenuText);
        // put the fonts on the popup
        addFontFamilyComboBox(fontmenu);
        menu.add(fontmenu);
*/

        // Add the HTML menu
        menu = XMLEditorMenuSpec.buildMenu(htmlMenuText, HTML_SPEC, actions,' ');
        popupMenu.add(menu);
        origmi = new Vector<JMenuItem>(HTML_SPEC.length);
        for (int i=0; i<HTML_SPEC.length; i++) {
            origmi.addElement(HTML_SPEC[i].getMenuItem());
        }
        origPopupMenuItems.put(htmlMenuText,origmi);

        // save all components for later restorations
        popupMenuComponents = popupMenu.getComponents();

        origmi = null;
        menu = null;
    }

    /*********************************************************************
    * Tool bar creation methods
    **********************************************************************/
//    private JToolBar getToolBar(JSpellDictionary dictionary, Hashtable actions)
    private JToolBar getToolBar(Hashtable actions)
    {
        Dimension forcedDim;
        Action undoAction,redoAction;
        JButton undoButton,redoButton;
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/actn123.gif"));
        String toolTipText = "Undo";//XMLEditorMenuSpec.getMenuText("Undo");

        JToolBar bar = new JToolBar();
        bar.setFloatable(false);

        addToolBarButton(bar,actions,UPDATE_DB_ACTION, "Save", "/images/save.gif",null);
        bar.addSeparator();

        //XMLUndoEditMgr.UndoAction undoAction = (XMLUndoEditMgr.UndoAction)actions.get(UNDO_EDIT_ACTION);
        undoAction = (Action)actions.get(UNDO_EDIT_ACTION);
        undoButton = bar.add(undoAction);
        undoButton.setText(""); //an icon-only button
        // set button in action
        editUndoMgr.setUndoButton(undoButton,(Action)actions.get(UPDATE_DB_ACTION));

        // use resources to get tool tip (not now)
        undoButton.setToolTipText(toolTipText);
        undoButton.getAccessibleContext().setAccessibleName(toolTipText);
        icon.setDescription(toolTipText);
        undoButton.setIcon(icon);

//        XMLUndoEditMgr.RedoAction redoAction = (XMLUndoEditMgr.RedoAction)actions.get(REDO_EDIT_ACTION);
        redoAction = (Action)actions.get(REDO_EDIT_ACTION);
        redoButton = bar.add(redoAction);
        editUndoMgr.setRedoButton(redoButton);

        redoButton.setText(""); //an icon-only button
        toolTipText = "Redo";//XMLEditorMenuSpec.getMenuText("Redo");
        redoButton.setToolTipText(toolTipText);
        redoButton.getAccessibleContext().setAccessibleName(toolTipText);
        icon = new ImageIcon(getClass().getResource("/images/actn038.gif"));
        icon.setDescription(toolTipText);
        redoButton.setIcon(icon);

//Find/repl
        addToolBarButton(bar,actions,FIND_REPL_ACTION, "Find/Replace", "/images/find.gif",null);

//      if (dictionary != null)
        addToolBarButton(bar,actions,SPELLCHECK_ACTION, "Spell check", "/images/spell.gif",null);

        bar.addSeparator();

        addToolBarButton(bar,actions,DefaultEditorKit.cutAction, "Cut", "/images/cut.gif",null);
        addToolBarButton(bar,actions,DefaultEditorKit.copyAction, "Copy", "/images/copy.gif",null);
        addToolBarButton(bar,actions,DefaultEditorKit.pasteAction, "Paste", "/images/paste.gif",null);

        bar.addSeparator();

        addToolBarButton(bar,actions,FONT_B_ACTION, "Bold", "/images/Bold.gif","b");
        addToolBarButton(bar,actions,FONT_I_ACTION, "Italic", "/images/Italic.gif","i");
        addToolBarButton(bar,actions,FONT_U_ACTION, "Underline", "/images/Underline.gif","u");
        addToolBarButton(bar,actions,INSERT_BR_ACTION, "Line Break", "/images/htmlbreak.gif","br");

        bar.addSeparator();
        addToolBarButton(bar,actions,INSERT_P_ACTION, "Paragraph", "/images/Paragraph.gif",null);
        addToolBarButton(bar,actions,INSERT_OL_ACTION, "Ordered List", "/images/NumberedList.gif","ol");
        addToolBarButton(bar,actions,INSERT_UL_ACTION, "Unordered List", "/images/BulletList.gif","ul");

        // pick one button and set all the same, add 6 for border
        forcedDim = new Dimension(undoButton.getIcon().getIconWidth()+6, undoButton.getIcon().getIconHeight()+6);
        for (int i=0; i<bar.getComponentCount(); i++)
        {
            Component comp = bar.getComponent(i);
            // everytime a button is pressed, focus will be returned to the editor
            if (comp instanceof JButton)
            {
                ((JButton)comp).setRequestFocusEnabled(false);
                // preferred sizes and max sizes are different if this control is
                // part of a dialog.. force them to be the same
                ((JButton)comp).setPreferredSize(forcedDim);
                ((JButton)comp).setMaximumSize(forcedDim);
            }
        }

        forcedDim = null;

        // put font family at the end of the toolbar for now
    //  addFontFamilyComboBox(bar);
        toolBarComponents = bar.getComponents();

//      editor.setNextFocusableComponent(bar); deprecated and wasn't correct, an attempt for accessibility
        return bar;
    }

    /*********************************************************************
    * Create the FontFamily JComboBox and add it to the component
    * put the fonts on the toolbar now, if added to the pulldown menu, the combobox
    * hangs in the air after selecting it.. looks odd but works fine
    */
/*  private void addFontFamilyComboBox(JComponent bar)
    {
        JComboBox fonts = new JComboBox(FONT_FAMILY_VCT);
        fonts.setmaximumRowCount(9);
        bar.add(fonts);
        // prevent stretching across width of remaining toolbar
        fonts.setmaximumSize(fonts.getPreferredSize());
        fonts.setEnabled(fontFamilyAction.isEnabled());
        // control enabled property with the action
        fontFamilyAction.addPropertyChangeListener(new ActionChangedListener(fonts));
        fonts.addActionListener(new ActionListener() {
            // fired every time the user selects an item, even if already selected
            // need this if they change font in different places but use the same font
            // The combo box fires an action event when the user selects an item from the combo box's menu.
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                // create a new event with the selected font family
                ActionEvent ae = new ActionEvent(editor,ActionEvent.RESERVED_ID_MAX+1,
                            (String)cb.getSelectedItem());
                // change the font
                fontFamilyAction.actionPerformed(ae);
                editor.requestFocus();
            }
        });
    }*/

    /*********************************************************************
    * Create the toolbar buttons
    */
    private void addToolBarButton(JToolBar bar, Hashtable actions,String actionStr,
        String toolTip, String iconFn, String actionCmd)
    {
        // use resources to get tool tip
        String toolTipText = XMLEditorMenuSpec.getMenuText(toolTip);
        // this is the proper way to get the file.. getResource takes the
        // file name and returns a URL by locating the image file using
        // the class loader that loaded the class file.  The package name
        // is prepended along with the local file or the codebase for the host
        ImageIcon icon = new ImageIcon(getClass().getResource(iconFn));

        Action action = (Action)(actions.get(actionStr));
        //JButton button = bar.add(action);  // this leaves the tool bar if save is pressed
        // this leaves the save button if save is pressed.. what is going on???
        // looks like the ToolTipManager is hanging onto a reference for Save button
        // if the menu or ctrl-s is used, the JMenu reference is left, even putting
        // dialog.hide() into a separate thread will not allow the reference to be released
/**/    JButton button = new JButton((String)action.getValue(Action.NAME),
            (Icon)action.getValue(Action.SMALL_ICON));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setEnabled(action.isEnabled());
        button.setAction(action);
        bar.add(button);
/**/

        button.setToolTipText(toolTipText);
        icon.setDescription(toolTip);
        button.setIcon(icon);
        button.setText("");
        button.getAccessibleContext().setAccessibleName(toolTip);
        if (actionCmd != null) {
            button.setActionCommand(actionCmd);
        }
    }

    /*********************************************************************
     * Set initial size.
     * @return Dimension
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(x_width, x_height);
    }

    /*********************************************************************
    * Set the preferred size.
    * @param d Dimension
    */
    public void setPreferredSize(Dimension d)
    {
        super.setPreferredSize(d);
        // use new settings
        x_width = d.width;
        x_height = d.height;
    }

    /*********************************************************************
    * Content input/output methods
    **********************************************************************/
    /*********************************************************************
    * FB53510 request to append a keystroke after load from JUI.  It will be added
    * as part of the terminating paragraph, it will be by itself.. not appended to previous text!
    * JUI wants to pass keystroke when user types in the JUI xml text control then have the editor append the keystroke
    * and continue on.. but editor has overhead when loading.. if user types quickly, the JUI control may reload
    * the editor and pass the new keystroke, net result is loss of previous key strokes..
    * also note this could be problematic because the JUI may get focus (editor is a frame), how are keystrokes
    * handled in the JUI xml text control when the editor is already open but doesn't have focus? if editor is not
    * 'minimized' it seems like the JUI closes the editor, so loss of focus is not a problem.. but when minimized
    * the JUI seems to just reload the editor, strange behavior
    * @param   evt <code>KeyEvent</code> with character to be appended
    * /
    public void appendKey(KeyEvent evt)
    {
        char c = evt.getKeyChar();
//System.err.println("XMLEditor::appendKey keyevent.char "+c);

        // only allow concatenation if user can edit or character is valid
        if (userCanEdit && (Character.isLetterOrDigit(c) || Character.isSpaceChar(c)))
        {
            editor.appendText(new String(new char[]{c}));
            evt.consume();  // mark this event as handled
        }
    }*/

    /* ********************************************************************
    * Load the document with a String (already in unicode).
    *
    * @param   data <code>String</code> to be loaded.
    * @param   editable boolean indicating if the user can make updates.
    * deprecated This will default to the Learning Services dtd.
    *             Instead, use the loadXML(String,boolean,String)
    *             method to specify the dtd.
    *
    public void loadXML(final String data, final boolean editable)
    {
        loadXML(data, editable, "ls.dtd");  // for now, default to the ls.dtd
    }*/

    /*********************************************************************
    * Load the document with a String (already in unicode).
    *
    * @param   data <code>String</code> to be loaded.
    * @param   editable boolean indicating if the user can make updates.
    * @param   dtdName <code>String</code> with name of DTD to be used.
    * @deprecated This will default to spellcheck not required
    *             Instead, use the loadXML(String,boolean,String, boolean)
    *             method to specify spellcheck requirement.
    */
    public void loadXML(final String data, final boolean editable, final String dtdName)
    {
        if (SwingUtilities.isEventDispatchThread()){
            loadDocumentOnEvtThrd(data,editable, dtdName,false);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            loadDocumentOnEvtThrd(data,editable, dtdName,false);
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }
        }
    }

    /*********************************************************************
    * Load the document with a String (already in unicode).
    *
    * @param   data <code>String</code> to be loaded.
    * @param   editable boolean indicating if the user can make updates.
    * @param   dtdName <code>String</code> with name of DTD to be used.
    * @param   spellChkReq boolean indicating if spellchk must be executed before save
    */
    public void loadXML(final String data, final boolean editable, final String dtdName,
    	final boolean spellChkReq)
    {
        if (SwingUtilities.isEventDispatchThread()){
            loadDocumentOnEvtThrd(data,editable, dtdName,spellChkReq);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            loadDocumentOnEvtThrd(data,editable, dtdName,spellChkReq);
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }
        }
    }

    /*********************************************************************
    * Load document from a stream (original applet used this)
    * Assumption: "UTF8" will be charset used!
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    void loadDocument(final InputStream is, final boolean editable, final String dtdName)
    {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    loadDocumentOnEvtThrd(is, CHAR_ENCODING,editable, dtdName,false);
                }
        });
    }

    /*********************************************************************
    * Load document from a file stream, debug only!!!
    *
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    * Will this be needed from SPIN applet?  if so, I will have to hang on to
    * the charset and use it for output too.. also will have to become public
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    void loadDocument(final InputStream is, final String charset, final boolean editable,
            final String dtdName)
    {
        System.out.println("debug mode loading editor charset "+charset);

        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    loadDocumentOnEvtThrd(is, charset,editable, dtdName,false);
                }
        });
    }

    /*********************************************************************
    * Load document from a file stream, debug only!!!
    *
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    * Will this be needed from SPIN applet?  if so, I will have to hang on to
    * the charset and use it for output too.. also will have to become public
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    void loadDocument(final InputStream is, final String charset, final boolean editable,
            final String dtdName, final boolean spellChkReq)
    {
        System.out.println("debug mode loading editor charset "+charset);

        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    loadDocumentOnEvtThrd(is, charset,editable, dtdName, spellChkReq);
                }
        });
    }

    /*********************************************************************
    * Load document from a string
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    */
    private synchronized void loadDocumentOnEvtThrd(String data, boolean editable, String dtdName,
    	boolean spellChkReq)
    {
        JMenuBar theBar;
        userCanEdit=editable;

        // ui wants to reuse same instance...
        // reset to init state
        editor.setEditable(false);  // prevent anything until control is loaded
        enableActions(false);  // start out as uneditable
        enableDtdActions(false);
        // disable all pulldown and popup menus
        for (int i=0; i<popupMenu.getComponentCount(); i++)
        {
            Component comp = popupMenu.getComponent(i);
            // disable all menus
            ((JMenu)comp).setEnabled(false);
        }

        // disable pulldown menus
        theBar = rootPane.getJMenuBar();
        for (int i=0; i<theBar.getMenuCount(); i++)
        {
            JMenu menu = theBar.getMenu(i);
            menu.setEnabled(false);
        }

        if (data == null) {// force starting with <p></p>
            data="";
        }

        // notify spellcheck handler if required to run before save or not
		if (spellCheckerHandler!=null) {
			spellCheckerHandler.setSpellCheckRequired(spellChkReq);
		}

        // indicate the page is loading
        setWaitCursors();

        // clear any old document information
        clearOldDocument();

        loadDocument(data, dtdName);
    }

    /*********************************************************************
    * Load document from a stream
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    */
    private synchronized void loadDocumentOnEvtThrd(InputStream is, String charset, boolean editable,
        String dtdName,boolean spellChkReq)
    {
        BufferedReader rdr = null;
        userCanEdit=editable;

        if (is == null)
        {
            createNewDocument(dtdName);
            //return;
        }else {
            try {
                StringBuffer sb = new StringBuffer();
                String s=null;
                // Note: character encoding used with inputstreams can not
                // be determined by the inputstream alone.  If the encoding is
                // not specified, it assumes the input is in the default encoding
                // of the platform.
                rdr = new BufferedReader(new InputStreamReader(is, charset));
                // append lines until done
                while((s=rdr.readLine()) !=null){
                    sb.append(s+NEWLINE_STR);  // duplicate the original string
                }

                loadDocumentOnEvtThrd(sb.toString(), editable, dtdName, spellChkReq);
            }
            catch(Exception t)
            {
                System.err.println("Error while loading document from stream: " + t);
                t.printStackTrace(System.err);
                restoreCursors();
            }
            finally{
                if (rdr!=null){
                    try{
                        rdr.close();
                    }catch(Exception x){
                        System.out.println(x.getMessage()); // jtest req
                    }
                }
            }
        }
    }

    private void resetMenuItems(JMenu menu, Hashtable origMenuItemTbl)
    {
        Vector origMenuItems = (Vector)origMenuItemTbl.get(menu.getText());
        if (origMenuItems!=null){
            // removeall and re-insert
            menu.removeAll();
            for (int ii=0; ii<origMenuItems.size(); ii++)
            {
                JMenuItem item = (JMenuItem)origMenuItems.elementAt(ii);
                if (item instanceof JMenu)
                {
                    resetMenuItems((JMenu)item, origMenuItemTbl);
                }
                if (item==null) // it was a separator
                {
                    if (menu.getItemCount()>0){
                        menu.addSeparator();
                    }
                }
                else{
                    menu.add(item);
                }
            }
        }
    }

    private void resetMenuItems()
    {
        // use resources to get menu text
        String editMenuText = "Edit";//XMLEditorMenuSpec.getMenuText("Edit");
        String fileMenuText = "File";//XMLEditorMenuSpec.getMenuText("File");
        String helpMenuText = XMLEditorMenuSpec.getMenuText("Help");

        // look at html menu and restore all possible actions
        // restore headings
        JMenuBar theBar = rootPane.getJMenuBar();
        // restore menubar
        theBar.removeAll();
        for (int i=0; i<menuBarComponents.length; i++)
        {
            theBar.add(menuBarComponents[i]);
        }
        for (int i=0; i<theBar.getMenuCount(); i++)
        {
            JMenu menu = theBar.getMenu(i);
            // skip File, Edit, Help
            if (menu.getText().equals(editMenuText) || menu.getText().equals(fileMenuText) ||
                menu.getText().equals(helpMenuText)){
                continue;
            }

            // removeall and re-insert
            resetMenuItems(menu, origPulldownMenuItems);
        }

        // restore popupMenu
        popupMenu.removeAll();
        for (int i=0; i<popupMenuComponents.length; i++){
            popupMenu.add(popupMenuComponents[i]);}
        // set popup menus to reflect current set of supported actions
        for (int i=0; i<popupMenu.getComponentCount(); i++)
        {
            Component comp = popupMenu.getComponent(i);
            // set all menus except the Edit menu
            if (comp instanceof JMenu)
            {
                JMenu menu = (JMenu)comp;
                if (menu.getText().equals(editMenuText)){
                    continue;
                }
                // removeall and re-insert
                resetMenuItems(menu, origPopupMenuItems);
            }
        }
    }
    private void setSupportedItems()
    {
        // use resources to get menu text
        String editMenuText = "Edit";//XMLEditorMenuSpec.getMenuText("Edit");
        String fileMenuText = "File";//XMLEditorMenuSpec.getMenuText("File");
        String helpMenuText = "Help";//XMLEditorMenuSpec.getMenuText("Help");
        Vector<JMenu> removedMenus = new Vector<JMenu>(1);
        String elemArray[];
        JMenuBar theBar;
        JToolBar bar = null;

        // set menu items back to original state
        resetMenuItems();

        // get the toolbar
        for (int i=0; i<getComponentCount(); i++)
        {
            Component comp = getComponent(i);
            if (comp instanceof JToolBar)
            {
                bar = (JToolBar)comp;
                bar.removeAll();
                // reset toolbar
                for (int ii=0; ii<toolBarComponents.length; ii++)
                {
                    bar.add(toolBarComponents[ii]);
                }
            }
        }

        elemArray = ((XMLEditorKit)editor.getEditorKit()).getSupportedElements();

        // remove any toolbar buttons that are not supported
        removeToolBarButtons(elemArray,bar);

        // set popup menus to reflect current set of supported actions
        for (int i=0; i<popupMenu.getComponentCount(); i++)
        {
            Component comp = popupMenu.getComponent(i);
            // set all menus except the Edit menu to allow select or copy
            if (comp instanceof JMenu)
            {
                JMenu menu = (JMenu)comp;
                if (menu.getText().equals(editMenuText)) {// || menu.getText().equals(viewMenuText))
                    continue;
                }
                removeMenuItems(elemArray, menu);
                if (menu.getItemCount()==0){
                    removedMenus.addElement(menu);
                }
            }
        }
        for (int i=0; i<removedMenus.size();i++)
        {
            popupMenu.remove((Component)removedMenus.elementAt(i));
        }

        // set pulldown menus to reflect current set of supported actions
        theBar = rootPane.getJMenuBar();
        removedMenus.clear();
        for (int i=0; i<theBar.getMenuCount(); i++)
        {
            JMenu menu = theBar.getMenu(i);
            // set all menus except the Edit menu to allow select or copy and File to close
            if (menu.getText().equals(editMenuText) || menu.getText().equals(fileMenuText) ||
                menu.getText().equals(helpMenuText)){
                continue;
            }

            removeMenuItems(elemArray, menu);
            if (menu.getItemCount()==0){
                removedMenus.addElement(menu);
            }
        }
        for (int i=0; i<removedMenus.size();i++)
        {
            theBar.remove((Component)removedMenus.elementAt(i));
        }

        removedMenus.clear();
        removedMenus=null;
    }

    // remove menu items that do not have supporting actions
    private void removeMenuItems(String elemArray[], JMenu menu)
    {
        Vector<JMenuItem> removeVct = new Vector<JMenuItem>(1);
        menuloop: for (int ii=0; ii<menu.getItemCount(); ii++)
        {
            String actCmd;
            JMenuItem item = menu.getItem(ii);
            if (item==null) {
                continue;}  // separators are null
            if (item instanceof JMenu)  // don't disable menu headings
            {
                int cnt;
                removeMenuItems(elemArray,(JMenu)item);
                cnt = ((JMenu)item).getItemCount();
                if (cnt==0)
                {
                    removeVct.addElement(item);
                }
                continue;
            }
            actCmd = item.getActionCommand();

            // always support xml source view
            if (actCmd.equals(TAG_CONTENT)){
                continue;
            }

            if (actCmd.equals("pn")) // nested paragraph
            {
                // nested paragraph must be handled separately
                // make sure that p is valid in li
                Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements("li");
                for (int ix=0; ix<elemVct.size(); ix++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ix);
                    if (elem.name.equals("p"))
                    {
                        continue menuloop;
                    }
                }
            }
            if (actCmd.equals("npre")) // nested pre
            {
                // nested pre must be handled separately
                // make sure that pre is valid in li
                Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements("li");
                for (int ix=0; ix<elemVct.size(); ix++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ix);
                    if (elem.name.equals("pre"))
                    {
                        continue menuloop;
                    }
                }
            }
            // nestedlist
            if (actCmd.equals("pli")) // parent list items
            {
                // nested list items must be handled separately
                // make sure that nested lists are valid in li
                Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements("li");
                for (int ix=0; ix<elemVct.size(); ix++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ix);
                    if (elem.name.equals("ul")||elem.name.equals("ol"))
                    {
                        continue menuloop;
                    }
                }
            }
            // nestedlist
            if (actCmd.equals("nol")) // nested ordered list
            {
                // nested lists must be handled separately
                // make sure that nested lists are valid in li
                Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements("li");
                for (int ix=0; ix<elemVct.size(); ix++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ix);
                    if (elem.name.equals("ol"))
                    {
                        continue menuloop;
                    }
                }
            }
            // nestedlist
            if (actCmd.equals("nul")) // nested unordered list
            {
                // nested lists must be handled separately
                // make sure that nested lists are valid in li
                Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements("li");
                for (int ix=0; ix<elemVct.size(); ix++)
                {
                    javax.swing.text.html.parser.Element elem =
                        (javax.swing.text.html.parser.Element)elemVct.elementAt(ix);
                    if (elem.name.equals("ul"))
                    {
                        continue menuloop;
                    }
                }
            }

            for(int ie=0; ie<elemArray.length; ie++)
            {
                // find corresponding element for menuitem
                if (actCmd.equals(elemArray[ie]))
                {
                    continue menuloop;
                }

                // publish attribute is supported
                if (elemArray[ie].equals(PUBLISH_TAG_ATTR))
                {
                    // allow publish as a tag attribute
                    if (actCmd.indexOf(PUBLISH_TAG_ATTR)!=-1) {// publish="webonly" or publish="printonly"
                        continue menuloop;
                    }
                    if (actCmd.equals(WEB_ONLY_CONTENT) || actCmd.equals(PRINT_ONLY_CONTENT)){
                        continue menuloop;
                    }
                }
            }

            // disabling menu item is not enough because the action is still enabled, it must
            // be removed
            removeVct.addElement(item);
        }
        for (int i2=0; i2<removeVct.size(); i2++)
        {
            menu.remove((JMenuItem)removeVct.elementAt(i2));
        }
        removeVct.clear();
        removeVct = null;
    }

    // remove buttons that do not have supporting actions
    private void removeToolBarButtons(String elemArray[], JToolBar bar)
    {
        Vector<Component> removeVct = new Vector<Component>(1);
        //toolBarComponents
        barloop:for (int ii=0; ii<bar.getComponentCount(); ii++)
        {
            Component comp = bar.getComponent(ii);
            if (comp==null) {
                continue;}
            if (comp instanceof JButton)
            {
                String actCmd = ((JButton)comp).getActionCommand();
                if (actCmd==null || actCmd.length()==0) {
                    continue;}

                for(int ie=0; ie<elemArray.length; ie++)
                {
                    // find corresponding element for menuitem
                    if (actCmd.equals(elemArray[ie])){
                        continue barloop;
                    }
                }

                removeVct.addElement(comp);
            }
        }
        for (int i2=0; i2<removeVct.size(); i2++)
        {
            bar.remove((Component)removeVct.elementAt(i2));
        }
        removeVct.clear();
        removeVct = null;
    }

    /*********************************************************************
    * Load the document initially.
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    private void loadDocument(String data, String dtdName)
    {
        // set the DTD to use for this document
        ((XMLEditorKit)editor.getEditorKit()).setDTDName(dtdName);

        // if requested DTD could not be loaded, disable the editor
        if (!dtdName.equals(((XMLEditorKit)editor.getEditorKit()).getDTDName())){
            userCanEdit=false;
        }

        // get all supported actions for this EditorKit and dtd and disable unsupported actions
        dtdActions.clear();
        dtdActions = ((XMLEditorKit)editor.getEditorKit()).getDTDActions();

        // get the supported elements and disable unsupported menu items
        setSupportedItems();

        // convert all <br /> to <br> because the parser doesn't handle them properly
        // xmlwriter will save any <br> as <br />
        data = XMLGenerator.replaceEmptyXML(data);

//System.out.println("setting editor to *"+data+"*");
        // display the text.. the document will be recreated
        editor.setText(data);
        editor.setCaretPosition(0);  // move cursor back to start of text

        // clear cursors, enable actions, etc
        loadingComplete();

        // if errors were flagged, allow update because the attribute text was modified
        if (editor.hasXMLErrors() && userCanEdit){
            editUndoMgr.changesMade();
        }

        // display any errors
        editor.showXMLErrors("Unexpected or unsupported tags and attributes were discarded.");
    }

    /*********************************************************************
    * Create an empty document in the event dispatch thread.
    * Assumption: DTD will be specified with each load.. the alternative
    * is that the DTD would be specified at instantiation time.
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    private void createNewDocument(String dtdName)
    {
        // indicate the page is loading
        setWaitCursors();

        // clear any old document information
        clearOldDocument();

        // load an empty string to set up all the properties, etc
        loadDocument("", dtdName);
    }

    /*********************************************************************
    * Clear undo/redo, remove listeners, remove document content
    * This should be called on the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    private void clearOldDocument()
    {
        // clear any pending undo/redo
        editUndoMgr.stop();

        // remove any listeners
        if (caretListener!=null)
        {
            editor.removeCaretListener(caretListener);
        }
        if (x_keyListener!=null)
        {
            editor.removeKeyListener(x_keyListener);
        }
    }

    /*********************************************************************
    * This is used to display errors in the applet
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    void setErrorMsg(final String txt)
    {
        // set listeners now, and change caret on the event dispatch thread
        if (SwingUtilities.isEventDispatchThread()){
            setErrorMsgOnEventThread(txt);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            setErrorMsgOnEventThread(txt);
                        }
                });
            }catch(Exception e){
                System.out.println("XMLEditor.setErrorMsg exception: "+e.getMessage()); // jtest req
            }
        }
    }

    /*********************************************************************
    * This is used to display errors in the applet using the event dispatch thread.
    * This will use the event dispatch thread.  Swing components
    * are NOT thread safe.
    */
    private void setErrorMsgOnEventThread(String txt)
    {
        Action a;
        int bodyId;
        // make sure control is clear
        clearOldDocument();

        // indicate the page is loading
        setWaitCursors();
        // text will not be editable.. this is a display of errors only!
        setUneditable();

        // make sure the text does not already contain <html> and <body> tags
        /* jtest flags indexOf() but it is ok can't use StringTokenizer because it uses each
        character as a separate delimiter, need to find the complete String
        Disallows using 'String.indexOf ()' with 'String.substring ()'.
        Note: If the algorithm implemented by the method is not String parsing, then
        StringTokenizer cannot be used and this rule's error message should be ignored.
        */
        bodyId = txt.indexOf("<body");
        if (bodyId!=-1)
        {
            int endId = txt.indexOf(">",bodyId);
            int endBody = txt.indexOf("</body");
            if (endBody==-1){
                endBody=txt.length();
            }
//            txt = txt.substring(endId+1,endBody);
            txt = XMLGenerator.getSubString(txt,endId+1,endBody);
            editor.setText(txt);
        }
        else{
            editor.setText("<p>"+txt+"</p>");
        }

        // disable view html actions and print view action
        a = (Action)myActions.get(VIEW_WEBONLY_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(VIEW_PRINTONLY_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(VIEW_TAG_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(SHOW_LENGTH_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(FIND_REPL_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(FIND_NEXT_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(REPLACE_NEXT_ACTION);
        a.setEnabled(false);

        // set listeners now, and change caret
        loadingComplete();
    }

    /*********************************************************************
     * Used to display temporary text, like while getting xml from the db.
     * editor.editable will not be modified!!
     * Cursor will be changed to wait cursor
     * This will use the event dispatch thread.  Swing components
     * are NOT thread safe.
     *
     * @param txt
     */
    public void setTemporaryMsg(final String txt)
    {
        // set listeners now, and change caret on the event dispatch thread
        if (SwingUtilities.isEventDispatchThread())
        {
            setTmpMsgOnEventThread(txt);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            setTmpMsgOnEventThread(txt);
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }
        }
    }
    private void setTmpMsgOnEventThread(String txt)
    {
        Action a;
        // make sure control is clear
        clearOldDocument();

        // indicate the page is loading
        setWaitCursors();

        setUneditable();

        editor.setText(txt);
        // for some reason the terminating newline for an uneditable view does not
        // render properly and the cursor is drawn in the middle of the editor, force
        // the caret back one position
        editor.setCaretPosition(editor.getDocument().getLength()-1);

        // caret listener not yet added, so call this directly
        setStatusBarMsg();

        a = (Action)myActions.get(FIND_REPL_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(FIND_NEXT_ACTION);
        a.setEnabled(false);
        a = (Action)myActions.get(REPLACE_NEXT_ACTION);
        a.setEnabled(false);
    }

    private void showSpellChkReqDialog()
    {
        String msgs[] = {
            "Spell check must be done before saving this attribute."
            };

        String accdesc[] = {"Press OK to close dialog."};
        UIManager.getLookAndFeel().provideErrorFeedback(null);

        showAccessibleDialog(this,//editor, jre 1.4.0 positions dialog incorrectly
            XMLEditorGlobals.APP_NAME+" XML Editor SpellCheck Required",
            JOptionPane.WARNING_MESSAGE, JOptionPane.YES_OPTION,
            "Spell check is required.", msgs, accdesc);

        msgs = null;
        accdesc = null;

        setFocusInEditor();  // applet does not return focus to the editor
    }


    private void showLengthExceededDialog(int totalLen)
    {
//      JOptionPane.showMessageDialog(this,
////        "The maximum length of "+
////                        MAX_LENGTH+" has been exceeded.\nThis data cannot be saved.\n"+
////                        "The current length of content and structure is "+totalLen+
////                        ".\n\nPlease reduce the size of the document.",
//                      XMLEditorGlobals.APP_NAME+" XML Editor Length Exceeded", JOptionPane.ERROR_MESSAGE);

        String msgs[] = {
            "The maximum length of "+MAX_LENGTH+" bytes has been exceeded.",
            "This data cannot be saved.",
            "The current length of content and structure is "+totalLen+" bytes ",
            "("+getCurrentNumChars()+" characters).",  // CR051603653
            " ",
            "Please reduce the size of the document."
            };

        String accdesc[] = {"Press OK to close dialog."};
        UIManager.getLookAndFeel().provideErrorFeedback(null);

        showAccessibleDialog(this,//editor, jre 1.4.0 positions dialog incorrectly
            XMLEditorGlobals.APP_NAME+" XML Editor Length Exceeded",
            JOptionPane.ERROR_MESSAGE, JOptionPane.YES_OPTION,
            "Document has exceeded the maximum allowed length.", msgs, accdesc);

        msgs = null;
        accdesc = null;

        setFocusInEditor();  // applet does not return focus to the editor
    }

    /*****************************************************************
    * CR051603653:
    * Changes will be made to the XML editor checking that will increase the limit from the current 10K in V111
    * to a 32K byte limit in V12C.  This will triple to size available to the user in V111 to V12C.
    * new test is 1) if > 32K bytes - stop them - no save
    *             2) if < 32K bytes but > 10K characters, warn them - but, allow save
    */
    private int showCharLimitDialog(int totalChars)
    {
        String msgs[] = {
            "The current number of characters is "+totalChars+".",
            "This text may not be translatable into all languages since it exceeds ",
            CHAR_WARNING_LIMIT+" characters.",
            " ",
            "Continue with save?"
            };

        String accdesc[] = {
            "Press Yes save data and close.",
            "Press No to prevent save."
            };
        int result = showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                XMLEditorGlobals.APP_NAME+" XML Editor Warning",
                JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION,
                "Verify save.  Number of characters exceeds limit.", msgs, accdesc);

        msgs = null;
        accdesc = null;

        return result;
    }

    /*********************************************************************
    * Write the document.  Called by the applet.
    * Assumption is that this will be called before submission to the
    * database.
    * This should be called on the event dispatch thread.  Swing components
    * are NOT thread safe.
    *
    * @param os OutputStream to write to
    * @throws IOException
    */
    public void writeCompletedXML(OutputStream os) throws IOException
    {
        Writer wOut=null;
        Document doc;
        char[] textAsChar;
        int nleft;
        int offset = 0;

        // make sure there are no html errors, if the 'update' button
        // was pressed first this was already done, but if a caller invokes
        // this directly the text must be verified
        // make sure complete document does not exceed max length
        int totalLen = getTotalLength();
        if (totalLen>MAX_LENGTH)
        {
            showLengthExceededDialog(totalLen);
            throw new IOException("Data exceeds maximum length of "+MAX_LENGTH+" bytes");
        }

        try{
            // OutputStreamWriter will convert from characters to bytes using
            // the specified character encoding or the platform default if none
            // is specified.  Output as unicode
            wOut = new OutputStreamWriter(os, CHAR_ENCODING);

            // because of the spell checker, the eol property must be changed, restore it now
            // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
            // if the original text contained them or if System.getProperty("line.separator")
            // specifies it.
            doc = editor.getDocument();
            // restore original
            doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, eolprop);

            // write to caller's stream
            textAsChar = editor.getText().toCharArray();
            nleft = textAsChar.length;
            // Just write out text in 4k blocks
            while (nleft > 0)
            {
                int n = Math.min(nleft, BLOCK_4K);
                wOut.write(textAsChar, offset, n);
                offset += n;
                nleft -= n;
            }

            wOut.flush();

            if (!inApplet) {// (applet) must add info if stream is used, close it there
                wOut.close();
            }
        }catch(IOException ioe){
            throw ioe;
        }
        finally{
            if (wOut!=null){
                try{
                    wOut.close();
                }catch(Exception e){
                    System.out.println(e.getMessage()); //jtest req
                }
            }
        }
    }

    private int getTotalLength()
    {
        String allText;
        int len;
        // because of the spell checker, the eol property must be changed, restore it now
        // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
        // if the original text contained them or if System.getProperty("line.separator")
        // specifies it.
        Document doc = editor.getDocument();
        // restore original
        doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, eolprop);

        allText = editor.getText();
        len = allText.length();
        try // CR051603653 convert from testing #chars to #bytes
        {
            byte bytes[] = allText.getBytes(CHAR_ENCODING);
            len = bytes.length;
        } catch(java.io.UnsupportedEncodingException ue) {
            System.out.println(ue.getMessage()); // jtest req
        }

        // set it to \n to preserve 1:1 correspondence to internal mapping
        // if not done doc.getLength() returns a shorter length than the text
        // that is returned by getText()
        doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, NEWLINE_STR);

        return len;
    }

    private int getCurrentNumChars() // CR051603653
    {
        int len;
        // because of the spell checker, the eol property must be changed, restore it now
        // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
        // if the original text contained them or if System.getProperty("line.separator")
        // specifies it.
        Document doc = editor.getDocument();
        // restore original
        doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, eolprop);

        len = editor.getText().length();
        // set it to \n to preserve 1:1 correspondence to internal mapping
        // if not done doc.getLength() returns a shorter length than the text
        // that is returned by getText()
        doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, NEWLINE_STR);

        return len;
    }

    /*********************************************************************
    * Get the document.
    * Assumption is that this will be called before submission to the
    * database.
    * This should be called on the event dispatch thread.  Swing components
    * are NOT thread safe.
    * @return String
    * @throws IOException
    */
    public String getCompletedXML() throws IOException
    {
        Document doc;
        // make sure there are no html errors, if the 'update' button
        // was pressed first this was already done, but if a caller invokes
        // this directly the text must be verified
        // make sure complete document does not exceed max length
        int totalLen = getTotalLength();
        if (totalLen>MAX_LENGTH)
        {
            showLengthExceededDialog(totalLen);
            throw new IOException("Data exceeds maximum length of "+MAX_LENGTH+" bytes.");
        }

        // because of the spell checker, the eol property must be changed, restore it now
        // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
        // if the original text contained them or if System.getProperty("line.separator")
        // specifies it.
        doc = editor.getDocument();
        // restore original
        doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, eolprop);

        return editor.getText();
    }

    /*********************************************************************
    * Inner classes
    **********************************************************************/

    /*********************************************************************
    * This listens for changes in action properties.  It controls the enablement
    * of the JComboBox (used for font family changes)
    */
/*  private class ActionChangedListener implements PropertyChangeListener
    {
        JComboBox box;

        ActionChangedListener(JComboBox box) {
            super();
            this.box = box;
        }
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                box.setEnabled(enabledState.booleanValue());
            }
        }
    }

    /*********************************************************************
    * This listens for exit requests, then checks if a save is needed or
    * not and displays a dialog. Use
    * setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE) to specify
    * that your window listener takes care of all window-closing duties.
    */
    private class ExitListener extends WindowAdapter implements ActionListener
    {
        // called by selecting 'close' menu item
        public void actionPerformed(ActionEvent evt)
        {
            // if there is nothing to undo, assumption is that all has been saved
            // or restored
            if (!editUndoMgr.isChanged() || !hasEditorListeners())
            {
                ((Window)rootPane.getTopLevelAncestor()).setVisible(false);

                // notify parent that editor is closing
                fireCloseEvent();

                // test only
                if (rootPane.getTopLevelAncestor() instanceof EATestFrame)
                {
                    ((Window)rootPane.getTopLevelAncestor()).dispose();
                    System.exit(0);
                }

                //return;
            }else {
                checkForSave();
            }
        }

        public void windowActivated(WindowEvent evt)
        {
            editor.requestFocus(); //Note: Do this if focus
                                   //      has never been set before in your window.
                                   //      Swing remembers the last focus set.
        }

        private void closeMenus(JMenu menu)
        {
            // check for any submenus left open
            int itemCount = menu.getItemCount();
            for (int ii=0; ii<itemCount; ii++)
            {
                JMenuItem item = menu.getItem(ii);
                if (item instanceof JMenu)
                {
                    closeMenus((JMenu)item);
                }
            }
            if (menu.isPopupMenuVisible()){
                menu.setPopupMenuVisible(false);
            }
        }
        // called by closing the window
        public void windowClosing(WindowEvent evt)
        {
            // was a menu left in the pulled down mode?
            // bug in JRE, next time editor is opened, the menu is stuck down
            JMenuBar theBar = rootPane.getJMenuBar();
            for (int i=0; i<theBar.getMenuCount(); i++)
            {
                JMenu menu = theBar.getMenu(i);
                closeMenus(menu);
            }
            if (popupMenu.isVisible())
            {
                for (int i=0; i<popupMenu.getComponentCount(); i++)
                {
                    Component comp = popupMenu.getComponent(i);
                    if (comp instanceof JMenu)
                    {
                        JMenu menu = (JMenu)comp;
                        closeMenus(menu);
                    }
                }
                popupMenu.setVisible(false);
            }
            // if there is nothing to undo, assumption is that all has been saved
            // or restored
            if (!editUndoMgr.isChanged() || !hasEditorListeners())
            {
                // the close operation was overridden, hide here
                // if already hidden, a null ptr is hit in COM.eLogin.modalDialog.hide()
                // but this is a problem because the editor must control hide()!!
                if(rootPane.getTopLevelAncestor()!=null){
					if (((Window)rootPane.getTopLevelAncestor()).isShowing()){
						((Window)rootPane.getTopLevelAncestor()).setVisible(false);
					}

					// notify parent that editor is closing
					fireCloseEvent();

					// test only
					if (rootPane.getTopLevelAncestor() instanceof EATestFrame)
					{
						((Window)rootPane.getTopLevelAncestor()).dispose();
						System.exit(0);
					}
				}else{
					// this happens when another window listener has already closed the editor's frame
					// there shouldn't be any other listeners, the editor must control this
					// to allow for 'cancel' of the close event
					System.err.println("ERROR XMLEditor.ExitListener window closing event has a null TopLevelAncestor!");
				}

                //return;
            }else {
                checkForSave();
            }
        }

        /***************************************************************************
        * This is used when the editor is closing.  It will notify the user if changes
        * were made and not updated.
        * This should be called on the event dispatch thread.  Swing components
        * are NOT thread safe.
        */
        private void checkForSave()
        {
            // use resources for string
            //String saveQuestion = "Save changes?";//getDialogText("SaveQuestion");

            // something was modified
            // never called by an applet so this is ok to invoke directly
            //int result = JOptionPane.showConfirmDialog(XMLEditor.this, saveQuestion,
            //  XMLEditorGlobals.APP_NAME+" XML Editor Verify Close", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

            String msgs[] = {
                "The data has been modified.  Save changes?"
                };

            String accdesc[] = {
                "Press Yes save data and close.",
                "Press No to close without saving.",
                "Press Cancel to prevent closing."
                };

            int result;
            boolean fireclose = true;
            UIManager.getLookAndFeel().provideErrorFeedback(null);

            result = showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                    XMLEditorGlobals.APP_NAME+" XML Editor Verify Close",
                    JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION,
                    "Verify closing the editor.  Changes have been made.", msgs, accdesc);

            msgs = null;
            accdesc = null;

            switch(result)
            {
            case JOptionPane.YES_OPTION:
                {
                    // make sure it is not too long
                    int totalLen = getTotalLength();
                    if (totalLen>MAX_LENGTH)
                    {
                        showLengthExceededDialog(totalLen);
                        //return;  // don't allow exit if they said yes
                        fireclose = false;
                    }
                    else
                    {
                        // check number of characters for the warning msg  CR051603653
                        int totalChars = getCurrentNumChars();
                        if (totalChars>CHAR_WARNING_LIMIT)
                        {
                            result = showCharLimitDialog(totalChars);
                        }

                        if (result==JOptionPane.YES_OPTION)  // continue with save
                        {
                            // save the document
                            boolean success = fireUpdateEvent();
                            if (success){
                                setUneditable();
                            }
                            else
                            {
                                setFocusInEditor();  // applet does not return focus to the editor
                            //  return;
                            }
                        }
                        else{
                            setFocusInEditor();  // applet does not return focus to the editor
                        }

                        //return; // don't fire close if save was requested
                        fireclose = false;
                    }
                }
                break;
            case JOptionPane.NO_OPTION:
                if (!inApplet)
                {
                    // if already hidden, a null ptr is hit in COM.eLogin.modalDialog.hide()
                    // but this is a problem because the editor must control hide()!!
                    if (((Window)rootPane.getTopLevelAncestor()).isShowing()){
                        ((Window)rootPane.getTopLevelAncestor()).setVisible(false);
                    }

                    // if in a dialog don't exit.. test only
                    if (rootPane.getTopLevelAncestor() instanceof EATestFrame)
                    {
                        // notify parent that editor is closing
                        fireCloseEvent();
                        ((Window)rootPane.getTopLevelAncestor()).dispose();
                        System.exit(0);
                        //return;
                        fireclose = false;
                    }
                }
                break;
            case JOptionPane.CANCEL_OPTION:
            case JOptionPane.CLOSED_OPTION:
                //return;
                fireclose = false;
                break;
            default:
                break;
            }

            // notify parent that editor is closing
            if (fireclose) {
                fireCloseEvent();
            }
        }
    }

    /*********************************************************************
    * This listens for enter key presses and changes to the editor document
    */
    private class MyKeyListener extends KeyAdapter
    {
        private void handleArrowDown(KeyEvent e,XMLDocument doc, int caretPos, int endPos)
        {
            boolean done = false;
            Element table;
            int nextPos;
            //System.err.println("\nDOWN start "+startPos+" end: "+endPos+" caret: "+editor.getCaretPosition());
            Element elem = doc.getParagraphElement(caretPos);
            int id = elem.getElementIndex(caretPos);
            // start at this element and look for a break in this paragraph
            // element, if found return and allow base code to handle it
            for (int i=id; i<elem.getElementCount();i++)
            {
                Element charElem = elem.getElement(i);
                if (charElem.getName().equals(HTML.Tag.BR.toString())){
                    //return;
                    done = true;
                    break;
                }
            }

            if (!done){
                //System.err.print("DOWN charelem: "+charElem);
                //System.err.print("DOWN charelem after: "+doc.getCharacterElement(charElem.getEndOffset()));
                // check to see if caret is currently in a table
                table = elem;
                while(table!=null&&!table.getName().equals(HTML.Tag.TABLE.toString()))
                {
                    table = table.getParentElement();
                }
                nextPos = caretPos;

                if (table==null) // caret is not in a TD
                {
                    // does a table follow this element
                    if (elem.getEndOffset()>=doc.getLength()) {// at end of doc
                        //return;
                        done = true;
                    }else {
                        table = doc.getParagraphElement(elem.getEndOffset());
                        while(table!=null&&!table.getName().equals(HTML.Tag.TABLE.toString()))
                        {
                            table = table.getParentElement();
                        }
                        if (table==null){
                            //return;
                            done = true;
                        }else {
                            // will be in a table so move caret to start of first row
                            nextPos = table.getElement(0).getElement(0).getStartOffset();
                            if (nextPos<endPos &&
                                endPos <table.getElement(0).getEndOffset()){
                                nextPos=endPos;  // don't move after end of prev selection
                            }
                        }
                    }
                }
                else
                {
                    int numRows = table.getElementCount();
                    int rowId = table.getElementIndex(caretPos); // get row id where selection ends
                    Element tr = table.getElement(rowId); // get row where selection ends
                    int colId = tr.getElementIndex(caretPos);  // get col id where selection ends

                    // move to cell below this one
                    if (rowId==(numRows-1)){ // already at bottom
                        nextPos = Math.min(doc.getLength(),table.getEndOffset());
                    }
                    else
                    {
                        nextPos = table.getElement(rowId+1).getElement(colId).getStartOffset();
                        if (nextPos<endPos &&
                            endPos <table.getElement(rowId+1).getElement(colId).getEndOffset()){
                            nextPos=endPos;  // don't move after end of prev selection
                        }
                    }
                }

                //System.err.println("next: "+nextPos);

                // if calc does not advance cursor, allow base code to move it
                if (!done && nextPos!=caretPos){
                    if (!e.isShiftDown()){
                        editor.setCaretPosition(nextPos);
                    }
                    else{  // create selection
                        editor.moveCaretPosition(nextPos);
                    }

                    e.consume();
                }
            }
        }
        private void handleArrowUp(KeyEvent e,XMLDocument doc, int caretPos, int startPos)
        {
            boolean done = false;
            Element table;
            int nextPos;
//System.err.println("\nUP start "+startPos+" end: "+endPos+" caret: "+editor.getCaretPosition());
            Element elem = doc.getParagraphElement(caretPos);
            int id = elem.getElementIndex(caretPos);
            // start at previous element and look for a break in this paragraph
            // element, if found return and allow base code to handle it
            for (int i=id-1; i>=0;i--)
            {
                Element charElem = elem.getElement(i);
                if (charElem.getName().equals(HTML.Tag.BR.toString())){
                    //return;
                    done = true;
                    break;
                }
            }

            if (!done){
//System.err.print("UP caretPos: paragraph elem "+elem);
                // look to see if this element is in a table
                table = elem;
                while(table!=null&&!table.getName().equals(HTML.Tag.TABLE.toString()))
                {
                    table = table.getParentElement();
                }
                nextPos = caretPos;

                if (table==null) // caret is not in a TD
                {
                    // does a table precede this element
                    if (elem.getStartOffset()-1<=0){ // at start of doc
                        //return;
                        done = true;
                    }else{
                        table = doc.getParagraphElement(elem.getStartOffset()-1);
                        while(table!=null&&!table.getName().equals(HTML.Tag.TABLE.toString()))
                        {
                            table = table.getParentElement();
                        }
                        if (table==null){
                            //return;
                            done = true;
                        }else{
                            // will be in a table so move caret to start of last row
                            nextPos = table.getElement(table.getElementCount()-1).getElement(0).getStartOffset();
                        }
                    }
                }
                else
                {
                    //int numRows = table.getElementCount();
    //System.err.print("caretpos: "+caretPos+" startpos: "+startPos+" end: "+endPos+" tr "+tr);
                    int rowId = table.getElementIndex(caretPos); // get row id where selection ends
                    Element tr = table.getElement(rowId); // get row where selection ends
                    int colId = tr.getElementIndex(caretPos);  // get col id where selection ends
    //System.err.println("colid: "+colId+" numrows "+numRows+" rowid: "+rowId);

                    Element prevElem=null;
                    // move to cell above this one
                    if (rowId==0) // already at top
                    {
    //System.err.println("AT TOP of TABLE");
                        if (table.getStartOffset()>0)
                        {
                            Element prevTable;
                            // move to start of previous element
                            prevElem = doc.getParagraphElement(table.getStartOffset()-1);
                            // but is prevElem another table?
                            prevTable = prevElem;
                            while(prevTable!=null&&!prevTable.getName().equals(HTML.Tag.TABLE.toString()))
                            {
                                prevTable = prevTable.getParentElement();
                            }
                            if (prevTable!=null) {// will be in a table so move caret to start of last row
                                prevElem = prevTable.getElement(prevTable.getElementCount()-1).getElement(0);
                            }
                        }
                    }
                    else
                    {
                        // get previous row
                        prevElem = table.getElement(rowId-1).getElement(colId);
    //System.err.println("move to start of prev row");
                    }
                    // can't move to start of elem if breaks are in it, must move to last break
                    // are there any breaks between end and start of elem.. if so go to last one
                    if (prevElem!=null)
                    {
                        int i=prevElem.getEndOffset()-1;
    //                        for (int i=prevElem.getEndOffset()-1; i>=prevElem.getStartOffset();)
                        while(i>=prevElem.getStartOffset())
                        {
                            Element charElem = doc.getCharacterElement(i);
                            if (charElem.getName().equals(HTML.Tag.BR.toString()))
                            {
                                nextPos=charElem.getStartOffset()+1;
                                break;
                            }
                            i = charElem.getStartOffset()-1;
    //  System.err.print("charElem in loop "+charElem);
                        }

                        if (nextPos==caretPos) {// not set in loop
                            nextPos = prevElem.getStartOffset();
                        }

                        if (caretPos>startPos && nextPos<startPos){
                            nextPos=startPos;  // don't move before beginning of prev selection
                        }
                    }
                }

                if (done || (caretPos==nextPos &&        // allow base class to move arrow
                    table.getStartOffset()>0)){   // not at the top already
                    //return;
                }else {
        //System.err.println("start: "+startPos+" next: "+nextPos+" endPos: "+endPos+" caret: "+caretPos);

                    if (!e.isShiftDown()){
                        editor.setCaretPosition(nextPos);
                    }
                    else{  // create selection
                        editor.moveCaretPosition(nextPos);
                    }

                    e.consume();
                }
            }
        }

        private void handleTab(KeyEvent e,XMLDocument doc, int startPos, int endPos)
        {
            boolean done = false;
            // v11 <pre> support
            Element[] elems = doc.getAttributeControllingTags(startPos,endPos);
            // only allow tab if first elem is pre.. rest will be merged into pre
            if (elems.length>0)
            {
                // only allow tab if first elem is pre.. rest will be merged into pre
                if (elems[0].getName().equals(HTML.Tag.PRE.toString())){
                    //return;
                    done = true;
                }else{
                    //v111 <table> support
                    // wordpro tabs from cell to cell, tab at end appends a new row
                    // shift-tab at start prepends a new row
                    if (elems[0].getName().equals(HTML.Tag.TD.toString()) ||
                        elems[0].getName().equals(HTML.Tag.TH.toString()))
                    {
                        Element tr = elems[0].getParentElement();
                        int numCols = tr.getElementCount();
                        int colId = tr.getElementIndex(elems[0].getStartOffset());
                        Element table = tr.getParentElement();
                        int numRows = table.getElementCount();
                        int rowId = table.getElementIndex(tr.getStartOffset());
                        if (e.isShiftDown()) // tab backwards
                        {
                            if (rowId==0 && colId==0) // prepend a new row
                            {
                                XMLTableRowColPanel rowMgr = new XMLTableRowColPanel(editor);
                                rowMgr.insertRow(true);
                                rowMgr.dereference();
                                rowMgr = null;
                            }
                            else // move the cursor to the previous cell
                            {
                                if (colId>=1) // move the cursor to the prev cell
                                {
                                    editor.setCaretPosition(tr.getElement(colId-1).getStartOffset());
                                }
                                else // move to end of prev row
                                {
                                    editor.setCaretPosition(table.getElement(rowId-1).getElement(numCols-1).getStartOffset());
                                }
                            }
                        }
                        else // tab forwards
                        {
                            if (rowId==(numRows-1) && colId==(numCols-1))
                            {
                                //append a new row
                                XMLTableRowColPanel rowMgr = new XMLTableRowColPanel(editor);
                                rowMgr.insertRow(false);
                                rowMgr.dereference();
                                rowMgr = null;
                            }
                            else // move the cursor to the next cell
                            {
                                if (colId+1<numCols) // move the cursor to the next cell
                                {
                                    editor.setCaretPosition(tr.getElement(colId+1).getStartOffset());
                                }
                                else // move to next row
                                {
                                    editor.setCaretPosition(table.getElement(rowId+1).getStartOffset());
                                }
                            }
                        }

                        e.consume();
                        //return;
                        done = true;
                    }
                }
            }
            // end v11 <pre> support
            if (!done){
                e.consume();
                UIManager.getLookAndFeel().provideErrorFeedback(null);
                // for accessibility
    /*              for (int i=0; i<XMLEditor.this.getComponentCount(); i++)
                {
                    Component comp = XMLEditor.this.getComponent(i);
                    if (comp instanceof JToolBar)
                    {
                        JToolBar bar = (JToolBar)comp;
                        bar.requestFocus();
                        break;
                    }
                }*/
            }
        }

        // used to allow multiple additions of list items or table cells
        public void keyPressed(KeyEvent e)
        {
            int startPos, endPos,caretPos;
            XMLDocument doc;
            // if not in applet, allow esc to close editor for accessibility
            if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
                if (!inApplet)
                {
                    EventListener[] listeners = ((Window)rootPane.getTopLevelAncestor()).getListeners(WindowListener.class);
                    e.consume();
                    for(int i=0; i<listeners.length; i++)
                    {
                        if (listeners[i] instanceof ExitListener)
                        {
                            ((ActionListener)listeners[i]).actionPerformed(null);
                            break;
                        }
                    }
                }
                return;
            }else {
                if (userCanEdit){
                    startPos = editor.getSelectionStart();
                    endPos = Math.min(editor.getSelectionEnd(),editor.getDocument().getLength());
                    caretPos = editor. getCaretPosition(); //v111

                    doc = (XMLDocument)editor.getDocument();

                    // default arrow movement in table is incorrect, must move from cell to cell
                    if (e.getKeyCode()==KeyEvent.VK_DOWN)//v111 <table> support
                    {
                        handleArrowDown(e, doc, caretPos, endPos);
                        //return;
                    }

                    else if (e.getKeyCode()==KeyEvent.VK_UP)//v111 <table> support
                    {
                        handleArrowUp(e, doc, caretPos, startPos);
                        //return;
                    }

                    // don't allow tabs to get through.. they have no meaning to html
                    else if (e.getKeyCode()==KeyEvent.VK_TAB)
                    {
                        handleTab(e,doc, startPos, endPos);
                        //return;
                    }

                    else if (e.getKeyCode() == KeyEvent.VK_DELETE ||
                        e.getKeyCode() == KeyEvent.VK_BACK_SPACE)  // v111
                    {
                        SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    // deletion of table cell contents leaves term new lines selected
                                    if (editor.getSelectionEnd()!=editor.getSelectionStart()){ //v111
                                        editor.setCaretPosition(editor.getSelectionStart());
                                    }

                                    // caret doesn't move so reset actions
                                    // enable the next set of actions for this tag
                                    enableDtdActions(userCanEdit);
                                    setStatusBarMsg();
                                }
                        });
                        //return;
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        Element[] elems;
                        // force end of compound text edit.. undo as separate units
                        editUndoMgr.endCompoundTextEdit();

                        elems = doc.getAttributeControllingTags(startPos,endPos);
                        if (elems.length>0)
                        {
                            Element elem = elems[0];
                            // can't allow enter in a table cell.. not re-rendered properly
                            // only allow new rows and cells through dialog
                            if(elem.getName().equals(HTML.Tag.TD.toString())||
                                elem.getName().equals(HTML.Tag.TH.toString()))
                            {
                                e.consume();
                                UIManager.getLookAndFeel().provideErrorFeedback(null);//getToolkit().beep();
                            }
                            else
                            if(elem.getName().equals("li"))
                            {
                                Action a;
                                elem = elem.getParentElement();
                                a = ((XMLEditorKit)editor.getEditorKit()).getDtdAction(INSERT_LI_ACTION);
                                try{
                                    // if anything is selected, remove it first?
                                    if (startPos!=endPos){
                                        doc.remove(startPos, endPos - startPos);
                                    }
                                }catch(BadLocationException ble) {
                                    System.out.println(ble.getMessage()); //jtest req
                                }
        //                          a.actionPerformed(new ActionEvent(editor, 0, ""));
                                a.actionPerformed(null);
                                e.consume();
                            }
                        }
                    }
                }
            }
        }
    }

    // called by errordialog when closed to force focus here
    // should be called by event thread
    void setFocusInEditor()
    {
        // THIS IS REQUIRED or the error dialog will not change focus!!!
        // but causes loss of keybd focus in applet if used to set focus in applet!!!!
        // sev 1 bug reported, need release ?? to get the fix
//System.out.println("XMLEditor:setfocusineditor() isevtthrd "+
//      javax.swing.SwingUtilities.isEventDispatchThread());

        // set focus in editor
        rootPane.getTopLevelAncestor().requestFocus();
        editor.requestFocus();  // editor does not get focus in applet
        rootPane.getTopLevelAncestor().repaint();  // jre1.4.0 seems to leave dialog box images occasionally
    }

/*
debugging
    public AttributeSet getSelectionAttributeSet()
    {
        // find settings
        HTMLDocument doc = (HTMLDocument)editor.getDocument();
        Element ele = doc.getCharacterElement(editor.getSelectionStart());
        AttributeSet as = null;

        // this only works if the attributes are in the element like
        // <B>xxx</b>, it does not work if the attributes are controlled
        // by the view, as in <H1>xxx</H1> or in <p class="italicBold">xxx</p>

        // to accurately find the attributes used to render the view
        // the view's attributes must be checked.  Using the element's
        // attributes is not sufficient
        View v = getViewForElement(ele);
        if (v == null)
        {
            System.err.println("View was null for this element!!! "+ele);
            as = ele.getAttributes();
        }
        else
        {
System.out.println("view found: "+v);
        // element attributes are used as resolve parents, so they will be
        // taken into account also
            as = v.getAttributes();
        }

        // get any input attributes
        StyledEditorKit kit = (StyledEditorKit)editor.getEditorKit();
        MutableAttributeSet attr = kit.getInputAttributes();

        // output the attributes for debug
        AttributeSet as2 = attr;
        System.out.print("\nAttributes for element: "+ele);
        int x=1;
        while (as2 != null)
        {
            showAttributes(as2);
            as2 = as2.getResolveParent();
            if (as2!=null)
                System.out.println("Attributes for resolve parent: "+x);
            x++;
        }

        return attr;
    }

    // there must be a better way to get the corresponding view
    // for an element or cursor position
    private View getViewForElement(Element ele)
    {
System.out.print("getview for ele "+ele);
        View view = editor.getUI().getRootView(editor);
        return findView(view,ele);
    }
    private View findView(View view, Element ele)
    {
        int startEle = ele.getStartOffset();
        int endEle = ele.getEndOffset();
        int startV = view.getStartOffset();
        int endV = view.getEndOffset();
    System.out.print("element:  offsets [" + startEle + ", " + endEle + "]");
    System.out.println("  view:  offsets [" + startV + ", " + endV + "]");

// bug when using Shift-JIS or other encodings?? views do not match elements
// one element covers a string of encoded characters, but many views are created
if (startEle==startV) return view;

        // check if element is in this view's range
        if (startEle < startV || endEle > endV ||
            startV > startEle || endV <startEle)
            return null;

System.out.println("findview: viewcnt: "+view.getViewCount());
        for (int i=0; i<view.getViewCount(); i++)
        {
            View v = view.getView(i);
System.out.println("view["+i+"] "+v);
System.out.print("view["+i+"] ele "+v.getElement());
            if (v.getElement() == ele)
                return v;
            v = findView(v,ele);
            if (v != null)
                return v;
        }
System.out.println("NO MATCHING VIEW!!!");
        return null;
    }*/

    /*********************************************************************
    * This listens for and reports caret movements.  It enables/disables
    * the cut and copy actions based on selection.
    */
    private class MyCaretListener implements CaretListener
    {
        private boolean ignoreUpdates = false;
        void ignoreCaret(boolean b) // used while editor is modified to prevent excess action enable/disable
        {
            ignoreUpdates = b;
            if (!ignoreUpdates) { // make sure actions reflect caret position
                caretUpdate(null);
            }
        }

        public void caretUpdate(CaretEvent e)
        {
            int startPos, endPos;
            Document doc;
            boolean hasSelection;
            Action copyAction;

//System.err.println("caret pos "+editor.getSelectionStart());
//if (editor.getDocument() instanceof HTMLDocument)
//getSelectionAttributeSet();

            // disabled during html edit actions or undo/redo actions
            if (!ignoreUpdates){
                // get caret position (called with CaretEvent=null sometimes so can't use e.getDot/e.getMark())
                startPos = editor.getSelectionStart();
                doc = editor.getDocument();
                // sometimes the selection seems to go beyond the length of the document
                endPos = Math.min(editor.getSelectionEnd(),doc.getLength());

                hasSelection = (startPos != endPos);
                if (userCanEdit)
                {
                    Action cutAction = (Action)myActions.get(DefaultEditorKit.cutAction);
                    cutAction.setEnabled(hasSelection);
                    cutAction = (Action)myActions.get(CUT_STRUCTURE_ACTION);
                    cutAction.setEnabled(hasSelection);

                    // input attributes are not getting reset when caret moves within a 'run'
                    // place cursor in italic text, press italic.. italic is turned off but if you
                    // move the cursor around on same line, the italic remains off..
                    // if this is a problem uncomment this code
                    //if (e!=null)  // real caret movement, not just our usage for action update
                    //  ((XMLEditorKit)editor.getEditorKit()).restoreInputAttributeSet();
                }
                copyAction = (Action)myActions.get(DefaultEditorKit.copyAction);
                copyAction.setEnabled(hasSelection);
                copyAction = (Action)myActions.get(COPY_STRUCTURE_ACTION);
                copyAction.setEnabled(hasSelection);

                SwingUtilities.invokeLater(new Runnable() {
                	public void run()
                	{
                		// input attributes are not set until after the caret listener completes
                		// all notifications, invoke this later
                		setStatusBarMsg();
                	}
                });

                // if the user did not have edit capability or the current view is not normal do not
                // enable actions
                if (userCanEdit){
                	// actions are based on caret and inputattribute set, but input attrs are
                	// not calculated until after this completes!
                	// set actions based on context
                	SwingUtilities.invokeLater(new Runnable() {
                		public void run()
                		{
                			/*markup test
                        // check if the cursor is in marked text
                        Action act = (Action)myActions.get(PartialMarkupAction);
                        if (act !=null)
                            act.setEnabled(markupMgr.isMarkupText());
            end markup test*/
                			enableDtdActions(userCanEdit);
                		}
                	});
                }
            }
        }
    }

    // used by caret listener and formatting actions to display element information in status bar
    void setStatusBarMsg()
    {
        int startPos = editor.getSelectionStart();
//try{
//if (startPos!=-1)
//throw new IOException("traceit");
//}
//catch(IOException e)
//{
//  e.printStackTrace();
//}
        XMLDocument doc = (XMLDocument)editor.getDocument();
        int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
        // input attribute set is needed because it holds things like bold for next character
        MutableAttributeSet attr = ((StyledEditorKit)editor.getEditorKit()).getInputAttributes();
        statusBar.setText(doc.getControlTagInfo(startPos,endPos,attr, null));

        // for accessibility, use tag info for description, but does it work? svk doesn't output it
        getAccessibleContext().setAccessibleDescription(doc.getControlTagInfo(startPos,endPos,attr,null));
    }

    /*********************************************************************
    * This controls the spell checker
    */
    private class SpellCheckerAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private boolean allowEnable=true;
        SpellCheckerAction() {
            super(SPELLCHECK_ACTION);
        }

        void disable()
        {
            setEnabled(false);
            allowEnable = false;
        }
        void spellCheckAndUpdate() {
			actionPerformed(null);
		}
        public void setEnabled(boolean newValue)
        {
            super.setEnabled(newValue&&allowEnable);
        }

        public void actionPerformed(ActionEvent evt)
        {
            if (!spellCheckerHandler.getBusy())
            {
                // the document stores \r and \r\n as \n.  getText() converts \n to \r\n
                // if the original text contained them or if System.getProperty("line.separator")
                // specifies it.
                Document doc = editor.getDocument();
                // get the property
                String cureolprop = (String)doc.getProperty(DefaultEditorKit.EndOfLineStringProperty);
                if (!cureolprop.equals(eolprop))
                {
                    // set it to \n to preserve 1:1 correspondence to internal mapping
                    // if not done doc.getLength() returns a shorter length than the text
                    // that is returned by getText()
                    doc.putProperty(DefaultEditorKit.EndOfLineStringProperty, NEWLINE_STR);
                }

                // run the spell checker
                spellCheckerHandler.check();
            }
        }
    }

    /*********************************************************************
    * This displays the current length of the document (content and structure)
    */
    private class DisplayLengthAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private static final String EXCEEDS_STR =
            "The current length exceeds the maximum.  It cannot be saved.";
        DisplayLengthAction(){
            super(SHOW_LENGTH_ACTION);
        }

        public void actionPerformed(ActionEvent evt)
        {
            String msgs[];
            String accdesc[] = {
                "Press OK to close dialog."
                };

            int totalLen = getTotalLength();
            // it must be a text field for accessibility to work
//          String msg = "The current length of content and structure is "+totalLen+
//                      ".\nThe maximum length is "+MAX_LENGTH+".\n\n"+
//                      (totalLen>MAX_LENGTH? EXCEEDS_STR :
//                          "There are "+(MAX_LENGTH-totalLen)+" characters remaining.");
    //          JOptionPane.showMessageDialog(XMLEditor.this, msg,
    //                  XMLEditorGlobals.APP_NAME+" XML Editor Current Length", JOptionPane.PLAIN_MESSAGE);

            int totalChars = getCurrentNumChars();
            Vector<String> msgVct = new Vector<String>();
            msgVct.addElement("The current length of content and structure is "+totalLen+" bytes,");
            msgVct.addElement("("+totalChars+" characters).");// CR051603653
            msgVct.addElement("The maximum length is "+MAX_LENGTH+" bytes.");
            msgVct.addElement(" ");
            if (totalLen>MAX_LENGTH)
            {
                msgVct.addElement(EXCEEDS_STR);
            }
            else
            {
                msgVct.addElement("There are "+(MAX_LENGTH-totalLen)+" bytes remaining.");
                if (totalChars>CHAR_WARNING_LIMIT)
                {
                    msgVct.addElement("This text may not be translatable into all languages since it ");
                    msgVct.addElement("exceeds "+CHAR_WARNING_LIMIT+" characters.");
                }
            }

            msgs= new String[msgVct.size()];
            msgVct.copyInto(msgs);
            msgVct.clear();

            showAccessibleDialog(XMLEditor.this,//editor, jre 1.4.0 positions dialog incorrectly
                    XMLEditorGlobals.APP_NAME+" XML Editor Current Length",
                    JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_OPTION,
                    "Display current length of the XML document.", msgs, accdesc);

            msgs = null;
            accdesc = null;

            setFocusInEditor();  // applet does not return focus to the editor
        }
    }

    /*********************************************************************
    * This is used with JOptionPane and text messages.  This will allow
    * Accessibility to read the information.  It will look like a label.  The
    * user can tab from line to line and hear the text.  They can then tab to
    * the buttons.
    */
    private static class AccessibleDialogMsg extends JTextField
    {
    	private static final long serialVersionUID = 1L;
    	AccessibleDialogMsg(String msg)
        {
            super(msg);
            setBackground(UIManager.getColor("Label.background"));
            setEditable(false);
            setBorder(null);
            getAccessibleContext().setAccessibleDescription("message line");
            getAccessibleContext().setAccessibleName("message");
        }
        void dereference()
        {
            getAccessibleContext().setAccessibleDescription(null);
            getAccessibleContext().setAccessibleName(null);
            accessibleContext = null;
        }
    }

    /*********************************************************************
    * This is used with JOptionPane Dialogs.  JOptionPane static showXXX are
    * not Accessibile.  Buttons do not have mnemonics or descriptions.
    */
    static int showAccessibleDialog(Component parentComponent, String title,
        int messageType, int optionType, String accDialogDesc, Object []msgs,
        String[] accButtonDesc)
    {
        int result=0;
        Object[] options;
        // build message object for option pane
        // this may not be the way to do this, if done in a textarea, the user must
        // arrow up and down for each line of information, this way allows the
        // user to tab from text field to textfield
        Object[] msgArray = new Object[msgs.length];
        for (int i=0; i<msgs.length; i++)
        {
            // allow blank labels to provide spaces between lines
            if (msgs[i] instanceof String && ((String)msgs[i]).trim().length()>0){
                msgArray[i] = new AccessibleDialogMsg((String)msgs[i]);
            }
            else{
                msgArray[i] = msgs[i];
            }
        }

        options = new Object[accButtonDesc.length];

        // build array of options, first button is yes, next is no, third is cancel
        for (int i=0; i<accButtonDesc.length; i++)
        {
            JButton btn = null;
            switch(i)
            {
            case 0:
                if (accButtonDesc.length==1){
                    btn= new JButton("OK");
                }
                else{
                    btn= new JButton("Yes");
                }
                break;
            case 1: btn= new JButton("No"); break;
            case 2: btn= new JButton("Cancel"); break;
            default:
                break;
            }
            btn.setMnemonic(btn.getText().charAt(0));
            btn.getAccessibleContext().setAccessibleDescription(accButtonDesc[i]);
            options[i] = btn;
        }

//JOptionPane(Object message, int messageType, int optionType, Icon icon,
 //Object[] options, Object initialValue)
        if (options !=null)
        {  // jtest won't allow variable defs in middle of code
            JDialog dialog;
            Object selectedValue;

            final JOptionPane optionPane = new JOptionPane(msgArray,
                messageType,
                optionType,
                null,
                options,
                options[0]);

            for (int i=0; i<options.length; i++)
            {
                JButton btn = (JButton)options[i];
                switch(i)
                {
                case 0:
                    btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                optionPane.setValue(new Integer(JOptionPane.YES_OPTION));
                            }
                    });
                    break;
                case 1:
                    btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                optionPane.setValue(new Integer(JOptionPane.NO_OPTION));
                            }
                    });
                    break;
                case 2:
                    btn.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                optionPane.setValue(new Integer(JOptionPane.CANCEL_OPTION));
                            }
                    });
                    break;
                default:
                    break;
                }
            }

            dialog = optionPane.createDialog(parentComponent, title);
            dialog.getAccessibleContext().setAccessibleDescription(accDialogDesc);
            ((JButton)options[0]).requestFocus(); // make sure button has focus

            dialog.setVisible(true);
            // cleanup
            dialog.dispose();
            optionPane.getUI().uninstallUI(optionPane);
            optionPane.setLayout(null);

            dialog = null;
            for (int i=0; i<msgArray.length; i++)
            {
                if (msgArray[i] instanceof AccessibleDialogMsg){
                    ((AccessibleDialogMsg)msgArray[i]).dereference();
                }
            }
            msgArray = null;

            for (int i=0; i<options.length; i++)
            {
                JButton btn = (JButton)options[i];
                EventListener listeners[] = btn.getListeners(ActionListener.class);
                for(int ii=0; ii<listeners.length; ii++)
                {
                    btn.removeActionListener((ActionListener)listeners[ii]);
                    listeners[ii]=null;
                }
                options[i]=null;
            }
            options = null;

            // get return value
            selectedValue = optionPane.getValue();
            result = JOptionPane.CLOSED_OPTION;
            if(selectedValue != null)
            {
                if(selectedValue instanceof Integer){
                    result = ((Integer)selectedValue).intValue();
                }
            }
        }
        return result;
    }

    /*********************************************************************
    * This copies structure and content to the system clipboard
    */
    private static class CopyStructureAction extends TextAction
    {
    	private static final long serialVersionUID = 1L;
    	private boolean canAccessClipboard=false;
        CopyStructureAction() {
            super(COPY_STRUCTURE_ACTION);
            // don't allow enablement if can't access systemclipboard
            try {
                java.awt.datatransfer.Clipboard clipboard =
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
                canAccessClipboard=true;
                clipboard=null;
            }
            catch(java.security.AccessControlException ace) {
                System.out.println(ace.getMessage()); //jtest req
            }
        }

        /**
         * Enables or disables the action.
         *
         * @param newValue  true to enable the action, false to disable it
         * @see Action#setEnabled
         */
        public void setEnabled(boolean newValue)
        {
            super.setEnabled(newValue&&canAccessClipboard);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getTextComponent(e);
            if (target != null && target instanceof XMLEditorPane) {
                ((XMLEditorPane)target).copyStructure();
            }
        }
    }

    /*********************************************************************
    * This copies structure and content to the system clipboard
    */
    private static class CutStructureAction extends TextAction
    {
    	private static final long serialVersionUID = 1L;
    	private boolean canAccessClipboard=false;
        CutStructureAction() {
            super(CUT_STRUCTURE_ACTION);
            // don't allow enablement if can't access systemclipboard
            try {
                java.awt.datatransfer.Clipboard clipboard =
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
                canAccessClipboard=true;
                clipboard=null;
            }
            catch(java.security.AccessControlException ace) {
                System.out.println(ace.getMessage()); // jtest req
            }
        }

        /**
         * Enables or disables the action.
         *
         * @param newValue  true to enable the action, false to disable it
         * @see Action#setEnabled
         */
        public void setEnabled(boolean newValue)
        {
            super.setEnabled(newValue&&canAccessClipboard);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getTextComponent(e);
            if (target != null && target instanceof XMLEditorPane) {
                ((XMLEditorPane)target).cutStructure();
            }
        }
    }

    /*********************************************************************
    * This controls viewing the html
    */
    private class ViewHtmlAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private int viewType = NORMAL_VIEW_TYPE;
        ViewHtmlAction(String name, int type) {
            super(name);
            viewType = type;
        }

        public void actionPerformed(ActionEvent evt)
        {
            XMLDisplayDialog dd;
            // this causes this to be executed on the event dispatch thread
            // after actionPerformed returns but with jre1.4.0 the tooltip for find pops up
            // and displaydialog gets moved back in the browser even though it is modal
//          SwingUtilities.invokeLater(new Runnable() {
//              public void run() {
//                  try {
            setWaitCursors();
            dd= new XMLDisplayDialog(XMLEditor.this,
                editor.getText(), viewType, rootPane.getSize(),
                ((XMLEditorKit)editor.getEditorKit()).getDTDName());/*,
                (Action)myActions.get(PRINT_PREVIEW_ACTION));*/

            dd.setVisible(true);
            // dialog will be disposed in dialog listener code
            dd.dereference();
            dd=null;

            // try to correct residual menu image by requesting a repaint
            // FB 21264 and duplicate 20688
            rootPane.repaint();
            //                  } catch (Throwable t) {
            //                      getToolkit().beep();
            //                      t.printStackTrace(System.err);
            //                  }
            //                  finally{
            restoreCursors();
//                  }
//              }
//          });
        }
    }

    /*********************************************************************
    * This controls update to the db
    */
    private class UpdateAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	UpdateAction() {
            super(UPDATE_DB_ACTION);
            setEnabled(false);
        }

        /**
         * Enables or disables the action.
         *
         * @param newValue  true to enable the action, false to disable it
         * @see Action#setEnabled
         */
        public void setEnabled(boolean newValue)
        {
            super.setEnabled(newValue && hasEditorListeners());
            if(newValue && !hasEditorListeners()){ // trying to enable but nothing will pick it up
                System.err.println("UpdateAction::WARNING Save can not be enabled.  No XMLEditorListener found.");
            }
        }

        public void actionPerformed(ActionEvent evt)
        {
            SpellCheckerAction act = (SpellCheckerAction)myActions.get(SPELLCHECK_ACTION);
			// force spell check if required
			if (spellCheckerHandler!=null && act.isEnabled() &&
				spellCheckerHandler.isRequired()) {
				// tell user spellcheck is required
				showSpellChkReqDialog();

				// run spellchk
				act.actionPerformed(null);
			}else{
				// make sure complete document does not exceed max length
				int totalLen = getTotalLength();
				if (totalLen>MAX_LENGTH)
				{
					showLengthExceededDialog(totalLen);
				}
				else
				{
					// check number of characters for the warning msg  CR051603653
					int result=JOptionPane.YES_OPTION;
					int totalChars = getCurrentNumChars();
					if (totalChars>CHAR_WARNING_LIMIT)
					{
						result = showCharLimitDialog(totalChars);
					}

					if (result==JOptionPane.YES_OPTION)  // continue with save
					{
						// do the update
						boolean success = fireUpdateEvent();
						if (success){
							setUneditable();
						}
					}
				}
			}
        }
    }
/*
static void displayAC(String indent, AccessibleContext ac)
{
System.out.println(indent+"AccessibleContext "+ac);

 // Gets the AccessibleAction associated with this object that supports one or more actions.
 AccessibleAction aact = ac.getAccessibleAction();
System.out.println(indent+"AccessibleAction "+aact);

// Gets the AccessibleComponent associated with this object that has a graphical representation.
 AccessibleComponent aacomp = ac.getAccessibleComponent();
System.out.println(indent+"AccessibleComponent "+aacomp);

// Gets the accessibleDescription property of this object.
String adesc = ac.getAccessibleDescription();
System.out.println(indent+"AccessibleDesc "+adesc);

// Gets the AccessibleIcons associated with an object that has one or more associated icons
AccessibleIcon[] aicons = ac.getAccessibleIcon();
System.out.println(indent+"AccessibleIcon "+aicons);

// Gets the 0-based index of this object in its accessible parent.
int aid = ac.getAccessibleIndexInParent();
System.out.println(indent+"AccessibleIndexInParent "+aid);

// Gets the accessibleName property of this object.
String aname = ac.getAccessibleName();
System.out.println(indent+"AccessibleName "+aact);

// Gets the Accessible parent of this object.
Accessible aparent = ac.getAccessibleParent();
System.out.println(indent+"AccessibleParent "+aparent);

// Gets the AccessibleRelationSet associated with an object
AccessibleRelationSet ars = ac.getAccessibleRelationSet();
System.out.println(indent+"AccessibleRelationSet "+ars);

// Gets the role of this object.
AccessibleRole arole = ac.getAccessibleRole();
System.out.println(indent+"AccessibleRole "+arole);

// Gets the AccessibleSelection associated with this object which allows its Accessible children to be selected.
AccessibleSelection asel = ac.getAccessibleSelection();
System.out.println(indent+"AccessibleSelection "+asel);

// Gets the state set of this object.
AccessibleStateSet aset = ac.getAccessibleStateSet();
System.out.println(indent+"AccessibleStateSet "+aset);

// Gets the AccessibleTable associated with an object
//AccessibleTable atable = ac.getAccessibleTable();
//System.out.println(indent+"AccessibleTable "+atable);

// Gets the AccessibleText associated with this object presently editable text on the display.
AccessibleText atext = ac.getAccessibleText();
System.out.println(indent+"AccessibleText "+atext);

// Gets the AccessibleValue associated with this object that supports a Numerical value.
AccessibleValue aval = ac.getAccessibleValue();
System.out.println(indent+"AccessibleValue "+aval);

//Returns the number of accessible children of the object.
 int count = ac.getAccessibleChildrenCount();
System.out.println(indent+"AccessibleChildrenCount "+count);
for (int x=0; x<count; x++)
{
   Accessible child =  ac.getAccessibleChild(x); // Returns the specified Accessible child of the object.
   System.out.println("\n"+indent+"Accessible child["+x+"] "+child);
   displayAC("  "+indent,child.getAccessibleContext());
}
}
*/
    /*********************************************************************
    * This is used to add color icons to the color menu
    */
    /*static class ColoredBox implements Icon
    {
        private Color color;
        public ColoredBox(Color c) { color = c; }
        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            g.setColor(color);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
        public int getIconWidth()  { return 10; }
        public int getIconHeight() { return 10; }
    }*/

    private static class EATestFrame extends JFrame implements XMLEditorListener
    {
    	private static final long serialVersionUID = 1L;
    	private XMLEditor oed=null;
        EATestFrame(String title,String[] args)
        {
            super(title);
            init(args);
        }
        private void init(String[] args)
        {
            String value;
            InputStream is = null;
            FileInputStream fis = null;
            JSpellDictionaryLocal dictionary;
            String userDir = System.getProperty("user.dir");
            getAccessibleContext().setAccessibleDescription("xml editor");
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            // set up spell check using local version
/**/        dictionary = new JSpellDictionaryLocal();
            dictionary.setDictionaryFileName(userDir+System.getProperty("file.separator")+"SNDSPELL.JDX");
/**/

        // set up the spell checker using the servlet
/*      JSpellDictionaryServlet dictionary = new JSpellDictionaryServlet();
        // build the url string to the spell checker servlet
        String spURLStr = "http://opicmaix9.pok.ibm.com/eannounce/JSpell";
        dictionary.setURL(spURLStr);
        dictionary.setBatchSize(200);
*/
        // set up the spell checker using sockets
/*      JSpellDictionarySocket dictionary= new JSpellDictionarySocket(); //Constructor sets the batch size to 100.
            //The default host is "localhost" and the default port is 5317.
//      dictionary.setHost("opicmaix8.pok.ibm.com");
        dictionary.setHost("pokxopi8.pok.ibm.com");
            // use port parameter if specified, otherwise use the default of 5317
        //  int port = 5317;
//          ((JSpellDictionarySocket)dictionary).setPort(port);
*/

            try {
                dictionary.open();
                dictionary.setLearnWords(false);
            }catch(JSpellException jse)
            {
                jse.printStackTrace(System.err);
                dictionary=null;
                //return;
            }

            oed = new XMLEditor(getRootPane(),dictionary);
            this.setSize(this.getPreferredSize());
//          addWindowListener(new WindowAdapter() {
//              public void windowClosing(WindowEvent evt) {
//                  System.exit(0);
//              }
//          });
            setVisible(true);
            try{
                if (args.length==1)
                {
                    String fn = args[0];
                    try{
                        // if just a file name is specified, look in current directory
                        if (fn.indexOf(System.getProperty("file.separator"))==-1){
                            fn = userDir+System.getProperty("file.separator")+fn;}

                        if (fn.endsWith(".html")||fn.endsWith(".htm")){
                            fis = new FileInputStream(fn);}
                        else{
                            fis = new FileInputStream(userDir+"/Editor23Test.html");
                        }
                        is = new BufferedInputStream(fis);
                    }
                    catch(FileNotFoundException fe)
                    {
                        System.err.println(fe.getMessage());
                        dispose();
                        System.exit(0);
                    }
                }

    /*oed.addXMLEditorListener(new XMLEditorListener()
    {
            public boolean updateRequested()
            {
    System.err.println("listener updatereq");
    return true;
            }
            public void editorClosing()
            {
                System.err.println("editor closing notification received");
            }
    public String attributeHelpTextRequested() { return null;}
    });
    */
                value = "<p>This a sample dialog using the XML editor:</p>"+
                "<p><b>Update</b> will <i>not</i> be enabled unless the text is modified.</p>"+
                    "<p>The editor will become uneditable after <b>Update</b> is pressed "+
                    "if the updateRequested() method returns true.</p>"+
                    "<p>The quck brwn fx jmps ovr the lzy dogz.</p>";
    //          oed.loadDocument(null,true,"html32b.dtd");//"ls.dtd");
    //          oed.loadDocument(is,"SJIS",true,"html32b.dtd");//"ls.dtd");
    //          oed.loadDocument(null,"SJIS",true,"ls.dtd");
    //          oed.loadDocument(is,"SJIS",true,"lstest.dtd"); // nested
                oed.loadDocument(is,"SJIS",true,"eannounce.dtd"); // tables and pre
    //          oed.loadDocument(is,"SJIS",true,"eannounce2.dtd"); // nested, tables and pre

    //          oed.loadDocument(is,"SJIS",true,"default.dtd"); // nested, tables and pre
    /*      String data =   "<OL><LI>Do this<UL><LI>inner1<LI>inner2</LI></UL>"+
                "<OL><LI>inner3 <LI>inner4</LI></OL><LI publish=\"webonly\">And do that"+
                "<LI>And do something else </LI></OL><UL><LI>do this<LI>do that"+
                "<LI>more</LI></UL>";
    oed.loadXML(XMLEditorDialogAppletFB.adjustData(data),true,"lstest.dtd");//,"lstest.dtd");
    */
    //          oed.loadDocument(is,"SJIS",true,"ls.dtd");
    //          oed.loadDocument(is,"SJIS",false,"ls.dtd");
            }catch(Exception e){
                System.out.println(e.getMessage()); //jtest req
            }finally{
                is=null;
                fis=null;
                if (is!=null){
                    try{
                        is.close(); //jtest req this.. but it breaks editor because loaddocument is asynchronous
                    }catch(Exception x){
                        System.out.println(x.getMessage()); //jtest req
                    }
                }
                if (fis!=null){
                    try{
                        fis.close(); //jtest req this.. but it breaks editor because loaddocument is asynchronous
                    }catch(Exception x){
                        System.out.println(x.getMessage()); //jtest req
                    }
                }
            }

        }
        public boolean updateRequested()
        {
            // testing write to file
//            String fileName ="C:/work/opicm/editor23/EditorWriteTest.html";
//          String fileName ="C:/work/opicm/editor23/japan2.html";

            try {
            //  FileOutputStream fos = new FileOutputStream(fileName);
            //  oed.writeCompletedXML(fos);


//  System.out.println("Web only:\n"+oed.getWebOnlyXML(oed.getCompletedXML()));
//  System.out.println("\n\nPrint only:\n"+oed.getPrintOnlyXML(oed.getCompletedXML()));
//  System.out.println("\n\nNormal:\n"+oed.getNormalXML(oed.getCompletedXML()));
                System.out.println("Download:*"+oed.getCompletedXML()+"*");
                System.out.println("Document contents*"+oed.editor.getDocument().getText(0,
                        oed.editor.getDocument().getLength())+"*");
                ((DefaultStyledDocument)oed.editor.getDocument()).dump(System.out);
            }
            catch(Exception ioe) {
                System.err.println("got exception during update "+ioe.getMessage());
                ioe.printStackTrace(System.err);
            }

            return true;
        }
        public void editorClosing()
        {
            System.out.println("editor closing notification received");
        }
        public String attributeHelpTextRequested() { return "Testing only";}
    }

    /*********************************************************************
    * Run the editor from the command line
    * @param args
    *
    */
    public static void main(String[] args)
    {
        try {
            JFrame f = new EATestFrame(XMLEditorGlobals.APP_NAME+" XML Editor",args);
            f=null;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            System.exit(0);
        }
    }
}

