// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.awt.datatransfer.*;

import javax.accessibility.*;
/******************************************************************************
* This class is used as the XML editor.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLEditorPane.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:07  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.5  2006/05/10 14:43:01  wendy
// Change e-announce to EACM
//
// Revision 1.4  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.3  2005/12/16 15:52:14  wendy
// Changes for DQA for td, th tags
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLEditorPane extends JEditorPane implements XMLEditorGlobals, XMLErrorListener, FocusListener
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditor editor=null;
    private static boolean warningShown=false;  // for applet security restrictions on clipboard
    private boolean insertStructure = false;  // used to paste structure
//  private static boolean ownsClipBd = false;
    private DefaultListModel errorMsgVct = new DefaultListModel();

    private int bodyTagLength=6;
    private String questionableXML=null;
    private static boolean pasteAsContent = false;

    // FB21302.. withdrawn for now.. do not implement this, much testing needed if done
/*  class XMLDefaultCaret extends DefaultCaret
    {
        public void focusLost(FocusEvent e)
        {
System.err.println("focus lost in xmldefaultcaret");
            super.focusLost(e);         // allow base functionality
            setSelectionVisible(true);  // force selection to be visible
        }
    }
*/

    /*********************************************************************
    * Constructor
    *
    */
    XMLEditorPane(XMLEditor ed)
    {
        EventListener[] mouselisteners;
        EventListener[] mouseMotionlisteners;
        setContentType("text/html");
        setEditorKit(new XMLEditorKit());
        editor = ed;

        // remove anchor listeners.. not needed and can cause problems during load
        mouselisteners = getListeners(MouseListener.class);
        for (int i=0; i<mouselisteners.length; i++)
        {
            if (mouselisteners[i] instanceof HTMLEditorKit.LinkController) {
                removeMouseListener((MouseListener)mouselisteners[i]);
            }
        }
        mouseMotionlisteners = getListeners(MouseMotionListener.class);
        for (int i=0; i<mouseMotionlisteners.length; i++)
        {
            if (mouseMotionlisteners[i] instanceof HTMLEditorKit.LinkController) {
                removeMouseMotionListener((MouseMotionListener)mouseMotionlisteners[i]);
            }
        }
        mouselisteners=null;
        addFocusListener(this);  // force visible caret so accessibility reads near cursor

        this.getAccessibleContext().setAccessibleName("xml editor pane");

        // FB21302.. withdrawn for now.. do not implement this, much testing needed if done
/*      // force selection to stay highlighted when control loses focus
        Caret caret = new XMLDefaultCaret();
        // blink rate is set by something else for the DefaultCaret, copy the value and set
        // it here
        caret.setBlinkRate(getCaret().getBlinkRate());
        setCaret(caret);  // replace the caret

        listeners = getListeners(FocusListener.class);
*/  }
    /**************************
     * Focus gained
     * @param e
     */
    public void focusGained(FocusEvent e) // Invoked when a component gains the keyboard focus
    {
        getCaret().setVisible(true);
    }
    /**************************
     * Focus lost
     * @param e
     */
    public void focusLost(FocusEvent e) //Invoked when a component loses the keyboard focus.
    {
        getCaret().setVisible(false);
    }

    void ignoreCaret(boolean b) {
        editor.ignoreCaret(b);
    }

    XMLUndoEditMgr getUndoEditMgr() {
        return editor.getUndoEditMgr();
    }

    void dereference()
    {
        EventListener listeners[];
        editor = null;
        errorMsgVct.clear();
        errorMsgVct = null;
        questionableXML = null;

        ((XMLDocument)getDocument()).dereference();
        ((XMLEditorKit)getEditorKit()).dereference();

        listeners = getListeners(MouseListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeMouseListener((MouseListener)listeners[i]);
        }
        // can't remove propertyChangeListeners like this..
        listeners = getListeners(KeyListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeKeyListener((KeyListener)listeners[i]);
        }
        listeners = getListeners(CaretListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeCaretListener((CaretListener)listeners[i]);
            listeners[i]=null;
        }

        // FB21302.. withdrawn for now.. do not implement this, much testing needed if done
/*      // new caret causes crash during dereference(), make sure it is removed as a focus listener
        listeners = getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeFocusListener((FocusListener)listeners[i]);
        }
*/
        removeFocusListener(this);

        getUI().uninstallUI(this);// this seems to add one more string ref..why??
        setEditorKit(null);
    }

    boolean tableHasBorder() //v111
    {
        boolean border=false;
        HTMLDocument doc = (HTMLDocument)getDocument();
        Element paragraph = doc.getParagraphElement(getSelectionStart());

        while(paragraph!=null && !paragraph.getName().equals(HTML.Tag.TABLE.toString()))
        {
            paragraph = paragraph.getParentElement();
        }
        if (paragraph!=null){
            if (paragraph.getAttributes().getAttribute(HTML.Attribute.BORDER)!=null)
            {
                border = (paragraph.getAttributes().getAttribute(HTML.Attribute.BORDER).toString().equals("1"));
            }
        }
        return border;//false;
    }

    boolean tableHasHeader() //v111
    {
        boolean header=false;
        HTMLDocument doc = (HTMLDocument)getDocument();
        Element paragraph = doc.getParagraphElement(getSelectionStart());

        while(paragraph!=null && !paragraph.getName().equals(HTML.Tag.TABLE.toString()))
        {
            paragraph = paragraph.getParentElement();
        }
        if (paragraph!=null){
            header= paragraph.getElement(0).getElement(0).getName().equals(HTML.Tag.TH.toString());
        }
        return header;
    }

    boolean hasXMLErrors() { return errorMsgVct.size()>0; }
    void showXMLErrors(final String msg)
    {
        if (errorMsgVct.size()>0){
            // do this in a separate thread, later
            SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        // share the model between this and the errordialog list.
                        XMLErrorDialog tmp = new XMLErrorDialog(editor, errorMsgVct, msg, questionableXML);
                        UIManager.getLookAndFeel().provideErrorFeedback(null);
                        tmp.setVisible(true);
                        tmp.dereference();
                        tmp=null;
                        errorMsgVct.clear();
                    }
            });
        }
    }

    //interface XMLErrorListener
    /***********************************************************************
    * This is called when the XML DTD parser has flagged an error.
    * @param errMsg
    * @param pos
    */
    public void handleError(String errMsg, int pos)
    {
        String position = " at position: ";
        if (errMsg.indexOf("html")==-1) { // if exists, artifact of parser, not meaningful to user
            if (errMsg.startsWith("Missing end")) {
                position = " for start tag at position: ";
            }
            // pos is usually after the error is found, try to adjust to the actual location
            pos = findErrorPosition(errMsg+position,pos);

            if(errMsg.equals("Text is not allowed")) { // position is +1
                pos--;
            }
            // if this is an initial load either from bad IDL'd data or from paste
            // save error msg for later display
            errorMsgVct.addElement(new XMLErrorObject(errMsg+position, pos));
        }
    }

    private int findErrorPosition(String errMsg, int pos)
    {
        /* jtest flags indexOf()
        Disallows using 'String.indexOf ()' with 'String.substring ()'.
        Note: If the algorithm implemented by the method is not String parsing, then
        StringTokenizer cannot be used and this rule's error message should be ignored.
        */
        boolean found = false;
        // some errors have (%) in them, look for that first
        int errId = errMsg.indexOf("(");
        String error=null;
        if (errId!=-1)
        {
//            error = errMsg.substring(errId+1,errMsg.indexOf(")")).toLowerCase();
            error = XMLGenerator.getSubString(errMsg,errId+1,errMsg.indexOf(")")).toLowerCase();
            if (error!=null)
            {
                errId = questionableXML.toLowerCase().lastIndexOf(error,pos);
                if (errId!=-1) {
                    //return errId;
                    found=true;
                }
            }
        }
        if (!found){
            // most errors have <%> in them, look for that next
            errId = errMsg.indexOf("<");
            if (errId!=-1)
            {
//                error = errMsg.substring(errId,errMsg.indexOf(">")).toLowerCase();
                error = XMLGenerator.getSubString(errMsg,errId,errMsg.indexOf(">")).toLowerCase();
                // if message is 'missing', must handle differently
                //errMsgTbl.put("start.missing","Missing start tag <%>");
                //errMsgTbl.put("end.missing","Missing end tag </%>");
                if (errMsg.startsWith("Missing start")) {
                    error=null;  // use default position
                }
                else
                if (errMsg.startsWith("Missing end"))
                {
                    // find previous start tag
//                    error = "<"+error.substring(2);
                    error = "<"+XMLGenerator.getSubString(error,2);
                }
            }
            if (error!=null)
            {
                errId = questionableXML.toLowerCase().lastIndexOf(error,pos);
                if (errId!=-1)
                {
                    // was this location already flagged, could happen with invalid nested structure
                    if (errorMsgVct.contains(new XMLErrorObject(errMsg, errId)))
                    {
                        // find previous error tag
                        int errId2 = questionableXML.toLowerCase().lastIndexOf(error,errId-1);
                        if (errId2!=-1) {
                            errId = errId2;
                        }
                    }
                    found=true;
                    //return errId;
                }
            }
            if(!found) {
                errId =pos-bodyTagLength;           // -6 for <body> if body was part of xml
            }
        }
        return errId;
    }

    /*******AccessibleText tests**********************************************************/
    /***************************
     * accessibility
     * @return AccessibleContext
     */
    public AccessibleContext getAccessibleContext() {
        return new AccessibleXMLEditorPane();
    }

    private class AccessibleXMLEditorPane extends AccessibleJEditorPane {
    	private static final long serialVersionUID = 1L;
    	public AccessibleText getAccessibleText() {
            return new JEditorPaneAccessibleTextSupport();
        }
    }
    private class JEditorPaneAccessibleTextSupport extends JEditorPaneAccessibleHypertextSupport
    {
    	private static final long serialVersionUID = 1L;
 /* public int getCaretPosition() //Returns the zero-based offset of the caret.
 {
     int c = super.getCaretPosition();
     System.out.println("caretpos "+c);
     return c;
 }
 public String getAtIndex(int part, int index)
 {
     String tmp = super.getAtIndex(part,index);
     System.out.println("getAtIndex:  Returns *"+tmp+" at a given index: "+index+
     " part: "+part);
     return tmp;
 }
 public String getBeforeIndex(int part, int index)
 {
     String tmp = super.getBeforeIndex(part,index);
     System.out.println("getBeforeIndex:  Returns *"+tmp+" before a given index: "+index+
     " part: "+part);
     return tmp;
 }
 public String getAfterIndex(int part, int index)
 {
     String tmp = super.getAfterIndex(part,index);
     System.out.println("getAfterIndex:  Returns *"+tmp+" after a given index: "+index+
     " part: "+part);
     return tmp;
 }
public AttributeSet getCharacterAttribute(int i)
 {
    AttributeSet as = super.getCharacterAttribute(i);
    System.out.println("char attr for i: "+i);
    XMLDocument.showAttributes(System.out,as);
    return as;
 }

 public String getSelectedText()
 {
     System.out.println("getSelectedText() "+super.getSelectedText());
     return super.getSelectedText();
 }
public int getSelectionEnd()
{
    System.out.println("getselectionEnd "+super.getSelectionEnd());
    return super.getSelectionEnd();
}
public int getSelectionStart()
{
    System.out.println("getselectionstart "+super.getSelectionStart());
    return super.getSelectionStart();
}


 public Rectangle getCharacterBounds(int i)
 {
     Rectangle rect = super.getCharacterBounds(i);
     System.out.println("rect for i: "+i+" rect: "+rect);
     return rect;
 }
public int getCharCount()
{
    System.out.println("getcharcount: "+super.getCharCount());
    return super.getCharCount();
}*/
 //Given a point in local coordinates, return the zero-based index of the character under that Point.
        public int getIndexAtPoint(Point p)
        {
            // this sees to come in with the wrong value, y is off, if not changed the index
            // is calculated to the same for short lines and the svk tool kit will not get the text!!!
            // ask onix if he had this behavior or not!!
            // problem with jre build with fix for caret pos bug
            Point p2 = new Point(p.x,p.y-1);
            int f = super.getIndexAtPoint(p2);
            //   System.out.println("getIndexAtPoint(Point p) "+p+" = "+super.getIndexAtPoint(p));
            //   System.out.println("getIndexAtPoint(Point p2) "+p2+" = "+f);
            return f;
        }
    }
    /*************************************************************************************/

    /*********************************************************************
    * FB53510 request to append a keystroke after load from JUI.  It will be added
    * as part of the terminating paragraph, it will be by itself.. not appended to previous text!
    * @param text  String to be appended to the editor
    */
    void appendText(String text)
    {
        // add data to end of document
        setCaretPosition(getDocument().getLength());
        replaceSelection(text);
    }

    /*********************************************************************
    * Load synchronously. Used by JSpell and initial load.
    *
    * This is implemented to remove the contents of the current document,
    * and replace them by parsing the given string using the current
    * <code>EditorKit</code>.  The base class gives the semantics of the
    * superclass by not changing out the model, while supporting the content
    * type currently set on this component.  The assumption is that the
    * previous content is relatively small, and that the previous content
    * doesn't have side effects.
    * Both of those assumptions can be violated and cause undesirable results.
    * To avoid this, create a new document,
    * <code>getEditorKit().createDefaultDocument()</code>, and replace the
    * existing <code>Document</code> with the new one. You are then assured the
    * previous <code>Document</code> won't have any lingering state.
    * <ol>
    * <li>
    * Leaving the existing model in place means that the old view will be
    * torn down, and a new view created, where replacing the document would
    * avoid the tear down of the old view.
    * <li>
    * Some formats (such as HTML) can install things into the document that
    * can influence future contents.  HTML can have style information embedded
    * that would influence the next content installed unexpectedly.
    * </ol>
    * @param text  text to be loaded into the editor
    */
    public void setText(String text)
    {
        StringReader rdr;
        // base class does not get a new document.. but JSpell will
        // reload the entire contents each time, thus causing intermittent exceptions
        // when tearing down the old view

        Document doc = getEditorKit().createDefaultDocument();
        setDocument(doc);  // must do this here instead of after read() or get a null ptr exception

        errorMsgVct.clear();

        /*
            java.lang.NullPointerException
            at javax.swing.text.ParagraphView.getLayoutViewCount(ParagraphView.java(Compiled Code))
            at javax.swing.text.html.ParagraphView.isVisible(ParagraphView.java(Compiled Code))
        */
        if (doc instanceof XMLDocument)  // catch any errors found in the xml
        {
            ((XMLDocument)doc).addXMLErrorListener(this);
            questionableXML = text;
        }

        if (text.length()>0){
            // v11 <pre> parser flags <---US as error, replace < inside <pre> with &lt;
            if (text.indexOf("<pre")!=-1) {
                text = XMLGenerator.convertPRE(text);
            }

            rdr = new StringReader("<body>"+text+"</body>");
            try{
                if (doc instanceof XMLDocument) { // catch any errors found in the xml
                    bodyTagLength=6;
                    questionableXML = text;
                }

                getEditorKit().read(rdr, doc, 0);
            } catch (Exception ioe) {
    //System.err.println("setText() exception "+ioe.getMessage());
    //ioe.printStackTrace(System.err);
            	UIManager.getLookAndFeel().provideErrorFeedback(null);
                System.out.println(ioe.getMessage()); // jtest req
            }
            finally{
                if (rdr!=null) {
                    rdr.close();
                }
            }
            rdr = null;

            // sometimes the 'getting attribute..' msg is not removed, the new text can not be seen, this seems to fix that problem
            paintImmediately(getBounds());
        }
    }

    void restoreCursors() {
        editor.restoreCursors();
    }
    void setWaitCursors() {
        editor.setWaitCursors();
    }
    void setFocusInEditor() {
        editor.setFocusInEditor();
    }

// cut, copy and paste to handle tags as well as content
    String getStructureAndText(int pos, int len)
    {
        String txt;
        try {
            StringWriter buf = new StringWriter();
            // if pos is at terminating newline of previous structure do not pull
            // the previous structure
            HTMLDocument doc = (HTMLDocument)getDocument();
            javax.swing.text.Element paragraph = doc.getParagraphElement(pos);
            if (paragraph.getEndOffset()-1 == pos)
            {
                pos++;
                len--;
            }

            // get selection with content and structure!!
            getEditorKit().write(buf, getDocument(), pos, len);
            txt = buf.toString();
        } catch (Exception ioe) {
            txt = "";
            System.out.println(ioe.getMessage()); // jtest req
        }

        return txt;
    }

    // display a message if user will not be able to access the system clipboard.
    // this is required for paste from external sources and to cut,copy and paste structure
    void checkClipboardAccess()
    {
        if (!warningShown) {
            try {
                Clipboard clipboard = getToolkit().getSystemClipboard();
                clipboard=null;
            }
            catch(java.security.AccessControlException ace)
            {
                System.out.println(ace.getMessage());  // jtest req
                if (!warningShown)
                {
                    String msgs[] = {
                        "The editor cannot access the system clipboard.",
                        " ",
                        "Paste from external sources will not be supported.",
                        " ",
                        "Please refer to the "+XMLEditorGlobals.APP_NAME+" installation guide",
                        "to allow access to the system clipboard."
                        };

                    String accdesc[] = {"Press OK to close dialog."};

                    UIManager.getLookAndFeel().provideErrorFeedback(null);

    //              JOptionPane.showMessageDialog(this,
    ////                            "The editor cannot access the system clipboard.\n\n"+
    ////                            "Paste from external sources or cut, copy and paste\n"+
    ////                            "of structure will not be supported.\n\n"+
    ////                            "Please refer to the "+XMLEditorGlobals.APP_NAME+" installation guide\n"+
    ////                            "to allow access to the system clipboard.\n",
    //                          XMLEditorGlobals.APP_NAME+" XML Editor Access Error", JOptionPane.WARNING_MESSAGE);

                    // default behavior in 1.4.0 is to copy structure, not just text
                    // so if user can't access the system clipboard, structure will always be copied

                    XMLEditor.showAccessibleDialog(this.getParent(),//this, jre 1.4.0 positions dialog incorrectly
                        XMLEditorGlobals.APP_NAME+" XML Editor Access Error",
                        JOptionPane.WARNING_MESSAGE, JOptionPane.YES_OPTION,
                        "Editor cannot access the system clipboard.", msgs, accdesc);

                    msgs = null;
                    accdesc = null;

                    warningShown = true;
                }
            }
        }
    }

    /**  paste() calls this... handle tags here
     * Replaces the currently selected content with new content
     * represented by the given string.  If there is no selection
     * this amounts to an insert of the given text.  If there
     * is no replacement text this amounts to a removal of the
     * current selection.
     * <p>
     * This is the method that is used by the default implementation
     * of the action for inserting content that gets bound to the
     * keymap actions.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param content  the content to replace the selection with
     */
     // From JEditorPane
    public void replaceSelection(String content)
    {
        EditorKit kit;
//System.err.println("XMLEditorPane:replaceSelection SwingUtilities.isEventDispatchThread()? "+SwingUtilities.isEventDispatchThread());
        if (! isEditable()) {
        	UIManager.getLookAndFeel().provideErrorFeedback(null);
            //return;
        }else {
            kit = getEditorKit();
            if(kit instanceof StyledEditorKit) {
                boolean wasReplaceEdit = getUndoEditMgr().isReplaceEdit(); //v11
                Caret caret = getCaret();
                int p0 = Math.min(caret.getDot(), caret.getMark());
                int p1 = Math.max(caret.getDot(), caret.getMark());
                try {
                    Document doc = getDocument();

                    // maintain the style of the replaced text, a copy is required
                    AttributeSet attrSet = new SimpleAttributeSet(((StyledEditorKit)kit).getInputAttributes());
                    if (p0 != p1) {
//                        System.err.println("Before remove elem "+((HTMLDocument)doc).getParagraphElement(p0));

                        if (!wasReplaceEdit) {// find/repl
                            getUndoEditMgr().setStartReplaceEdit("replacement");
                        }
                        doc.remove(p0, p1 - p0);
                        // deletion of table cell contents leaves term new lines selected
                        if (getSelectionEnd()!=getSelectionStart()) {//v111
                            setCaretPosition(getSelectionStart());
                        }
                    }
                    if (content != null && content.length() > 0)
                    {
                        Element elem = ((HTMLDocument)doc).getParagraphElement(p0);
                        // does the content contain structure?
                        if (!insertStructure)  // no structure
                        {
                            int elemLen;
                            // pasting new lines into a list item will not be wysiwyg.. replace new lines with spaces
                            if (elem.getName().equals(HTML.Tag.IMPLIED.toString())){
                                elem = elem.getParentElement();
                            }

                            if (elem.getName().equals(HTML.Tag.LI.toString()) ||
                                elem.getName().equals(HTML.Tag.TD.toString()) || //v111
                                elem.getName().equals(HTML.Tag.TH.toString())) //v111
                            {
                                content = content.replace('\n',' ');
                            }

                            // do not allow tabs unless in <pre> v11
                            if (!elem.getName().equals(HTML.Tag.PRE.toString())){
                                content = content.replace('\t',' ');
                            }

                            // FB 53237:575129 JRE can't layout a single structure of more than 32k chars
                            // number varies based on where text can flow.
                            // JRE 1.4.0 gives
    /*
    javax.swing.text.StateInvariantError: infinite loop in formatting
            at javax.swing.text.FlowView$FlowStrategy.layout(FlowView.java(Compiled Code))
            at javax.swing.text.FlowView.layout(FlowView.java:208)
            at javax.swing.text.BoxView.setSize(BoxView.java(Compiled Code))
            at javax.swing.text.BoxView.modelToView(BoxView.java:482)
            at javax.swing.text.CompositeView.modelToView(CompositeView.java:274)
            at javax.swing.text.BoxView.modelToView(BoxView.java:484)
            at javax.swing.text.CompositeView.modelToView(CompositeView.java:274)
            at javax.swing.text.BoxView.modelToView(BoxView.java:484)
            at javax.swing.plaf.basic.BasicTextUI$RootView.modelToView(BasicTextUI.java:1353)
            at javax.swing.plaf.basic.BasicTextUI.modelToView(BasicTextUI.java:895)
            at javax.swing.text.DefaultCaret.repaintNewCaret(DefaultCaret.java:1070)
            at javax.swing.text.DefaultCaret$1.run(DefaultCaret.java:1049)
            at java.awt.event.InvocationEvent.dispatch(InvocationEvent.java(Compiled Code))
            at java.awt.EventQueue.dispatchEvent(EventQueue.java(Compiled Code))
            at java.awt.EventDispatchThread.pumpOneEventForHierarchy(EventDispatchThread.java(Compiled Code))
            at java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java(Compiled Code))
            at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:165)
            at java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:157)
            at java.awt.EventDispatchThread.run(EventDispatchThread.java:125)
    */
                            /* jtest flags indexOf()
                            Disallows using 'String.indexOf ()' with 'String.substring ()'.
                            Note: If the algorithm implemented by the method is not String parsing, then
                            StringTokenizer cannot be used and this rule's error message should be ignored.
                            */
                            elemLen = elem.getEndOffset()-elem.getStartOffset();
                            if (content.indexOf(XMLEditor.NEWLINE_STR)== -1 &&  // allow new lines to go thru, a new structure will be created
                                elemLen+content.length()>MAX_LENGTH)
                            {
                                //String msgs[] = null;
                                final Vector<String> vct = new Vector<String>(1);
                                int exceedsCnt;
                                System.err.println("XMLEditor::Element <"+elem.getName()+"> length: "+elemLen+
                                    " plus content length: "+content.length()+" exceeds "+MAX_LENGTH);


                                vct.addElement("Text to be inserted exceeds the maximum number ");
                                vct.addElement("of "+MAX_LENGTH+" characters in a single structural element.");

                                exceedsCnt = (elemLen+content.length())-MAX_LENGTH;
                                if (content.length()>exceedsCnt) // insert some of the string
                                {
                                    System.err.println("XMLEditor::Truncating content to "+(content.length()-exceedsCnt)+" characters");
//                                    content = content.substring(0,content.length()-exceedsCnt);
                                    content = XMLGenerator.getSubString(content,0,content.length()-exceedsCnt);
                                    vct.addElement("Text has been truncated.");
                                }
                                else
                                {
                                    content = null; // can't insert any
                                }

                                SwingUtilities.invokeLater(new Runnable()
                                    {
                                        public void run() {
                                            String accdesc[] = {
                                                "Press OK to close dialog."
                                                };
                                            String msgs[]= new String[vct.size()];
                                            vct.copyInto(msgs);
                                            vct.clear();

                                            UIManager.getLookAndFeel().provideErrorFeedback(null);

                                            XMLEditor.showAccessibleDialog(XMLEditorPane.this.getParent(),//this, jre 1.4.0 positions dialog incorrectly
                                                XMLEditorGlobals.APP_NAME+" XML Editor Error",
                                                JOptionPane.WARNING_MESSAGE, JOptionPane.YES_OPTION,
                                                "Maximum length exceeded.", msgs, accdesc);

                                            msgs = null;
                                            accdesc = null;
                                        }
                                    });
                            } // end FB 53237:575129

                            if (content!=null) {
                                // this will only handle content.. structure is ignored
                                doc.insertString(p0, content, attrSet);//((StyledEditorKit)kit).getInputAttributes());
                            }
                        }
                        else  // content has structure
                        {
                            XMLPasteAction pasteXML;
                            content = XMLGenerator.replaceEmptyXML(content);
                            // insert structure
                            pasteXML = new XMLPasteAction(content, this);
                            pasteXML.actionPerformed(null);
                        }
     // JRE 1.4.0 GM PMR 40100,001,866
                        // jre1.4.0 does not repaint if content added to any cell in a table.. force it here
                        while(elem!=null && !elem.getName().equals(HTML.Tag.TR.toString())) {
                            elem = elem.getParentElement();
                        }
                        if (elem!=null) {
                            repaint();
                        }
                        // end jre1.4.0 does not repaint
                    }
                } catch (BadLocationException e) {
                	UIManager.getLookAndFeel().provideErrorFeedback(null);
                    System.out.println(e.getMessage());  // jtest req
                }
                insertStructure = false;  // reset flag

                if (p0 != p1 && !wasReplaceEdit) {// find/repl
                    getUndoEditMgr().setEndReplaceEdit();
                }
            }
            else {
                super.replaceSelection(content);
            }

            kit=null;
        }
    }

    /**
     * Transfers the currently selected range in the associated
     * text model to the system clipboard, removing the contents
     * from the model.  The current selection is reset.  Does nothing
     * for null selections.
     */
    public void cut()
    {
        Clipboard clipboard;
        if (warningShown)  // if warning was shown, user can not access clipboard so don't try
        {
            super.cut();
            pasteAsContent=true;  // make sure any typed <p> is not rendered as structure
            //return;
        }else{
            clipboard= getToolkit().getSystemClipboard();
            if (isEditable() && isEnabled() && clipboard  != null) {
                try {
                    int p0 = Math.min(getCaret().getDot(), getCaret().getMark());
                    int p1 = Math.max(getCaret().getDot(), getCaret().getMark());
                    if (p0 != p1) {
                        Document doc = getDocument();
                        String srcData = doc.getText(p0, p1 - p0);
                        StringSelection contents = new StringSelection(srcData);
                    // we need to know when we no longer own the clipboard
                        clipboard.setContents(contents, defaultClipboardOwner);
                        doc.remove(p0, p1 - p0);

                        // deletion of table cell contents leaves term new lines selected
                        if (getSelectionEnd()!=getSelectionStart()){//v111
                            setCaretPosition(getSelectionStart());
                        }

                    }
                } catch (BadLocationException e) {
                    System.out.println(e.getMessage()); // jtest req
                }
            } else {
            	UIManager.getLookAndFeel().provideErrorFeedback(null);
            }
            clipboard = null;
            pasteAsContent=true;  // make sure any typed <p> is not rendered as structure
        }
    }

    /**
     * Transfers the currently selected range in the associated
     * text model with associated structure to the system clipboard, removing the contents
     * from the model.  The current selection is reset.  Does nothing
     * for null selections.
     */
    public void cutStructure()
    {
        Clipboard clipboard = getToolkit().getSystemClipboard();

        if (isEditable() && isEnabled() && clipboard != null)
        {
            try {
                int p0 = Math.min(getCaret().getDot(), getCaret().getMark());
                int p1 = Math.max(getCaret().getDot(), getCaret().getMark());
                if (p0 != p1)
                {
                    Document doc = getDocument();
                    // get tags with content
                    String srcData = getStructureAndText(p0, p1 - p0);
                    StringSelection contents = new StringSelection(srcData);
                    clipboard.setContents(contents, defaultClipboardOwner);
//                  ownsClipBd = true;
                    pasteAsContent = false;
                    doc.remove(p0, p1 - p0);

                    // deletion of table cell contents leaves term new lines selected
                    if (getSelectionEnd()!=getSelectionStart()) {//v111
                        setCaretPosition(getSelectionStart());
                    }
                }
            } catch (BadLocationException e) {
                System.out.println(e.getMessage()); //jtest req
            }
        } else {
        	UIManager.getLookAndFeel().provideErrorFeedback(null);
        }
        clipboard=null;
    }

    /**
     * Transfers the currently selected range in the associated
     * text model to the system clipboard, leaving the contents
     * in the text model.  The current selection is remains intact.
     * Does nothing for null selections.
     */
    public void copy()
    {
        Clipboard clipboard;
        if (warningShown)  // if warning was shown, user can not access clipboard so don't try
        {
            super.copy();
            pasteAsContent=true;  // make sure any typed <p> is not rendered as structure
            //return;
        }else{
            clipboard= getToolkit().getSystemClipboard();

            if (clipboard != null) {
                try {
                    int p0 = Math.min(getCaret().getDot(), getCaret().getMark());
                    int p1 = Math.max(getCaret().getDot(), getCaret().getMark());
                    if (p0 != p1) {
                        Document doc = getDocument();
                        String srcData = doc.getText(p0, p1 - p0);
                        StringSelection contents = new StringSelection(srcData);
                        // we need to know when we no longer own the clipboard
                        clipboard.setContents(contents, defaultClipboardOwner);
                    }
                } catch (BadLocationException e) {
                    System.out.println(e.getMessage());  // jtest req
                }
            }
            pasteAsContent=true;  // make sure any typed <p> is not rendered as structure
            clipboard=null;
        }
    }

    /**
     * Transfers the currently selected range in the associated
     * text model with associated structure to the system clipboard, leaving the contents
     * in the text model.  The current selection is remains intact.
     * Does nothing for null selections.
     */
    public void copyStructure()
    {
        Clipboard clipboard= getToolkit().getSystemClipboard();

        if (clipboard  != null)
        {
            int p0 = Math.min(getCaret().getDot(), getCaret().getMark());
            int p1 = Math.max(getCaret().getDot(), getCaret().getMark());
            if (p0 != p1)
            {
                //Document doc = getDocument();
                // get tags with content
                String srcData = getStructureAndText(p0, p1 - p0);
                StringSelection contents = new StringSelection(srcData);
                clipboard.setContents(contents, defaultClipboardOwner);
//              ownsClipBd = true;
                pasteAsContent = false;
            }
        }
        clipboard=null;
    }

    /**
     * Transfers the contents of the system clipboard into the
     * associated text model.  If there is a selection in the
     * associated view, it is replaced with the contents of the
     * clipboard.  If there is no selection, the clipboard contents
     * are inserted in front of the current insert position in
     * the associated view.  If the clipboard is empty, does nothing.
     *
     */
    public void paste()
    {
        if (warningShown)  // if warning was shown, user can not access clipboard so don't try
        {
            super.paste();
            //return;
        }else{
            try{
                Clipboard clipboard= getToolkit().getSystemClipboard();

                /* jtest flags indexOf()
                Disallows using 'String.indexOf ()' with 'String.substring ()'.
                Note: If the algorithm implemented by the method is not String parsing, then
                StringTokenizer cannot be used and this rule's error message should be ignored.
                */

                if (isEditable() && isEnabled() && clipboard != null)
                {
                    Transferable content = clipboard.getContents(this);
                    if (content != null) {
                        try {
                            boolean isok=true;
                            Document doc;
                            String dstData = (String)(content.getTransferData(DataFlavor.stringFlavor));
                            // can't do this determination in replaceSelection() because keystrokes use it too
                            // html can be typed but should not be input as structure
                            // mark this as paste of structure
                            // this is a minimal check.. for start tag and end tag.. it can be easily
                            // fooled with bad xml.  is there any need to enhance this and only allow
                            // paste of properly formed xml?  the parser will flag any errors it finds
                            // but it may not be properly rendered.
                            if (!pasteAsContent){  // make sure any typed <p> is not rendered as structure
                                insertStructure = (dstData.indexOf("<")!=-1)&&
                                ((dstData.indexOf("</")!=-1)||(dstData.indexOf("/>")!=-1))&&
                                (dstData.indexOf(">")!=-1);  // <br /> must be inside other structure
                            }

                            // make sure any new xml is not surrounded by <body> tag
                            if (insertStructure)
                            {
                                // <body> can not be part of xml
                                int bodyId = dstData.toLowerCase().indexOf("<body");
                                if(bodyId!=-1)
                                {
                                    int endTag = dstData.indexOf(">",bodyId);
//                                    dstData = dstData.substring(endTag+1);
                                    dstData = XMLGenerator.getSubString(dstData,endTag+1);
                                    bodyId = dstData.toLowerCase().indexOf("</body>");
                                    if (bodyId!=-1){
//                                        dstData = dstData.substring(0,bodyId);
                                        dstData = XMLGenerator.getSubString(dstData,0,bodyId);
                                    }
                                }
                            }

                            errorMsgVct.clear();
                            doc = getDocument();
                            // catch any errors found in the xml, this must be done for internal
                            // pastes too, like from one editor to another because they may be
                            // using different DTDs
                            if (insertStructure && doc instanceof XMLDocument)
                            {
                                XMLPasteAction pasteXML;
                                bodyTagLength = 0;
                                questionableXML = dstData;

                                // must parse the data to be inserted first because the
                                // parser inaccurately creates structure for some errors
                                // like <ol>xyz</ol> will create list but incorrectly

                                // check structure
                                pasteXML = new XMLPasteAction(XMLGenerator.replaceEmptyXML(dstData), this);
                                pasteXML.verifyXML();

                                // if it has errors, display them and return, do not insert
                                if (hasXMLErrors())
                                {
                                    showXMLErrors("Clipboard content cannot be inserted.");
                                    insertStructure=false;
                                    //return;
                                    isok=false;
                                }
                            }

                            if(isok){
                                replaceSelection(dstData);
                            }
                            dstData=null;
                        } catch (Exception e) {
                        	UIManager.getLookAndFeel().provideErrorFeedback(null);
                            System.out.println(e.getMessage());  // jtest req
                        }

                        content=null;
                    } else {
                    	UIManager.getLookAndFeel().provideErrorFeedback(null);
                    }
                }
                clipboard=null;
            }
            catch(java.security.AccessControlException ace)
            {
                super.paste();
                System.out.println(ace.getMessage()); // jtest req
            }
        }
    }

// The following is needed because getClipboard() can't be accessed in JTextComponent.. it is
// copied from JTextComponent due to private or package access restrictions
    private static ClipboardOwner defaultClipboardOwner = new ClipboardObserver();
    private static class ClipboardObserver implements ClipboardOwner {
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
//          ownsClipBd = false;
            pasteAsContent = false;
//System.err.println("******ClipboardOwner lostOwnership!!");
        }
    }

    // tool tips to provide information about html settings
    private int lastPos=-1;
    private boolean showTips=false;

    void showXMLToolTips(boolean b)
    {
        showTips=b;
        if (!showTips){
            setToolTipText(null);  // clear it
        }
    }

    boolean isShowingXMLToolTips() { return showTips; }
    /*********************************
     * test
     * @param x
     * @param y
     * @return boolean
     */
    public boolean contains(int x, int y)
    {
        boolean bContains=super.contains(x,y);
        if (bContains && showTips)
        {
            Point pt = new Point(x, y);
            Position.Bias[] biasRet = new Position.Bias[1];
            int pos = getUI().viewToModel(this, pt, biasRet);
            if (lastPos!=pos)
            {
                Document doc = getDocument();
                lastPos=pos;
                if (doc instanceof XMLDocument)
                {
                    int endPos = Math.min(getSelectionEnd(),doc.getLength());
                    int startPos = getSelectionStart();
                    // input attribute set is needed because it holds things like bold for next character
                    Element e = ((XMLDocument)doc).getCharacterElement(lastPos);
                    AttributeSet attr = e.getAttributes();

                    // output selection information if the mouse is inside a selected area
                    if (lastPos<startPos || lastPos>endPos)
                    {
                        startPos=lastPos;
                        endPos=lastPos;
                    }
                    // split so DQA won't flag this font, this will never make it outside of the editor
                    setToolTipText("<html><center><"+"f"+"ont size=+1>" +
                        ((XMLDocument)doc).getControlTagInfo(startPos,endPos,attr,"<"+"b"+"r>") +
                        "</"+"f"+"ont></center></html>");
                }
            }
            biasRet=null;
        }
        return bContains;
    }
}
