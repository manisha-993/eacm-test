// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

/******************************************************************************
* This is used to manage undo/redo and compound edits
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLUndoEditMgr.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:21  tony
// This is the initial load of OPICM
//
//
class XMLUndoEditMgr extends KeyAdapter implements UndoableEditListener, DocumentListener, XMLEditorGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditorPane editor=null;
    private int prevLen = 0, netDiff=0;
    private int keyCode = KeyEvent.CHAR_UNDEFINED;
    private boolean isReplaceEdit=false, hadChanges=false;

    private UndoAction undoAction=null;
    private RedoAction redoAction=null;
    private UndoManager undoMgr = new UndoManager();

    private CompoundTextEdit compoundTextEdit=null;
//    private CompoundEdit compoundReplaceEdit=null;
    private CompoundReplaceEdit compoundReplaceEdit=null;
    private char prevChar='X';
    private char curChar='X';
    private String replaceType=null;

    void dereference()
    {
        editor.getDocument().removeUndoableEditListener(this);
        editor.getDocument().removeDocumentListener(this);

        editor = null;
        undoAction.dereference();
        undoAction = null;
        redoAction = null;
        undoMgr = null;
        compoundTextEdit=null;
        compoundReplaceEdit=null;
        replaceType = null;
    }

    /*********************************************************************
    * Constructor
    */
    XMLUndoEditMgr(XMLEditorPane xeditor)
    {
        this.editor = xeditor;
        undoAction = new UndoAction();
        redoAction = new RedoAction();
    }

    Action[] getActions()
    {
        Action[] acts = new Action[2];
        acts[0] = undoAction;
        acts[1] = redoAction;

        return acts;
    }

    // undo action is private, so must go thru this to set button
    void setUndoButton(JButton b, Action update)
    {
        undoAction.setButton(b, update);
    }
    // redo action is private, so must go thru this to set button
    void setRedoButton(JButton b)
    {
        redoAction.setButton(b);
    }

    boolean isChanged()
    {
        return (undoMgr.canUndo() ||
            (compoundTextEdit!=null && compoundTextEdit.isInProgress())||hadChanges);
    }

    void start()
    {
        hadChanges = false;
        restart();
    }

    void restart()  // start listening again
    {
        prevLen = editor.getDocument().getLength();
        netDiff=0;
        // at this point the document will not be changed for the editor
        editor.getDocument().addUndoableEditListener(this);
        editor.getDocument().addDocumentListener(this);

        editor.addKeyListener(this);
        undoAction.updateUndoState();
    }

    void endCompoundTextEdit()
    {
        if (compoundTextEdit!=null)
        {
            compoundTextEdit.end();
            compoundTextEdit = null;
        }
    }

    // the document must be changed when spell check makes a correction
    // so to allow update to be enabled, set the changed flag
    void changesMade()
    {
        hadChanges=true;
        undoAction.updateUndoState();
    }

    // can not maintain undo state.. styles are lost somehow
    void suspend()  // stop listening for a while
    {
        hadChanges=hadChanges||(undoMgr.canUndo()||
                (compoundTextEdit!=null&&compoundTextEdit.isInProgress()));
        clearEdits();
    }

    void stop()
    {
        hadChanges=false;
        // reset undo manager and action states
        clearEdits();
    }

    private void clearEdits()
    {
        // clear any pending undo/redo
        undoMgr.discardAllEdits();
        endCompoundTextEdit();

        undoAction.updateUndoState();
        redoAction.updateRedoState();

        // remove listeners
        editor.getDocument().removeUndoableEditListener(this);
        editor.getDocument().removeDocumentListener(this);

        editor.removeKeyListener(this);
    }

    /******************************************
     * document listener
     * @param e DocumentEvent
     */
    public void changedUpdate(DocumentEvent e)
    {
        // prevent any possibility of concatenating style changes
        endCompoundTextEdit();
        netDiff=0;
    }

    /******************************************
     * Gives notification that there was an insert into the document.
     * @param e DocumentEvent
     */
    public void insertUpdate(DocumentEvent e)
    {
        // keep track of how much the doc changed
        netDiff = e.getDocument().getLength()-prevLen;
        prevLen = e.getDocument().getLength();
    }

    /*******************************************
     * Gives notification that a portion of the document has been removed.
     * @param e DocumentEvent
     */
    public void removeUpdate(DocumentEvent e)
    {
        // keep track of how much the doc changed
        netDiff = e.getDocument().getLength()-prevLen;
        prevLen = e.getDocument().getLength();
    }

    boolean isReplaceEdit() { return isReplaceEdit;} //v11

    void setStartReplaceEdit(String type)
    {
//System.err.println("editmgr:setStartReplaceEdit entered "+type);
        if (compoundTextEdit !=null)
        {
            compoundTextEdit.end();  // end this edit
            compoundTextEdit=null;
        }
        isReplaceEdit=true;
        replaceType = type;
    }
    void setEndReplaceEdit()
    {
//System.err.println("editmgr:setEndReplaceEdit entered ");
        isReplaceEdit=false;
        if (compoundReplaceEdit != null)
        {
            compoundReplaceEdit.end();
            compoundReplaceEdit = null;
        }
        undoAction.updateUndoState();
        redoAction.updateRedoState();
    }

    /**
     * keyadapter needed to keep track of how deletion is done
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e)
    {
        keyCode = e.getKeyCode();
        try{
            javax.swing.text.Segment s = new javax.swing.text.Segment();
            // Get the previous character and current character
            int offset = editor.getSelectionStart();
            prevChar='X';
            curChar='X';
            if (offset > 0) {
                editor.getDocument().getText(offset - 1, 1, s);
                prevChar= s.array[s.offset];
            }
            editor.getDocument().getText(offset, 1, s);
            curChar= s.array[s.offset];
            s = null;
        }catch(javax.swing.text.BadLocationException ble) {
            System.out.println(ble.getMessage()); // jtest req
        }
    }
    /***********************************************
     * @see javax.swing.event.UndoableEditListener#undoableEditHappened(javax.swing.event.UndoableEditEvent)
     */
    public void undoableEditHappened(UndoableEditEvent e)
    {
        //Remember the edit and update the menus.
        checkSequential(e.getEdit());
        undoAction.updateUndoState();
        redoAction.updateRedoState();
        keyCode = KeyEvent.CHAR_UNDEFINED;  // reset key action
    }

    private void checkMode(int editMode, UndoableEdit edit)
    {
        int curPos = editor.getSelectionStart();
        if (compoundTextEdit == null ||
            compoundTextEdit.getMode() != editMode)
        {
            if (compoundTextEdit !=null) {
                compoundTextEdit.end();  // end this edit
            }
            compoundTextEdit = new CompoundTextEdit(editMode);
            undoMgr.addEdit(compoundTextEdit);
            compoundTextEdit.addEdit(edit,curPos);
        }
        else  // mode exists
        {
            if (!compoundTextEdit.isSequential(curPos)) // cursor moved
            {
                compoundTextEdit.end();  // end previous
                compoundTextEdit = new CompoundTextEdit(editMode);
                undoMgr.addEdit(compoundTextEdit);
            }
            compoundTextEdit.addEdit(edit,curPos);
        }
    }

    // Edits generated by Swing text components do not support merging
    // Any merging must be done in our own CompoundEdit object
    // Consecutive inserts or deletions will be merged together
    private void checkSequential(UndoableEdit edit)
    {
        if (isReplaceEdit)
        {
            if (compoundReplaceEdit==null)
            {
                compoundReplaceEdit = new CompoundReplaceEdit(replaceType);
                undoMgr.addEdit(compoundReplaceEdit);
            }
            compoundReplaceEdit.addEdit(edit);
            //return;
        }else{
            //group sequential inserts and deletions
            if (netDiff != 0)  // document was changed
            {
                // check for single insertion but do not group enter keys
                if (netDiff ==1 && (keyCode!=KeyEvent.VK_ENTER))  // typing chars
                {
                    checkMode(CompoundTextEdit.INSERT, edit);
                }
                else if (netDiff==-1)  // deleting chars
                {
                    // if backspace and prevchar was newline do not compound edit
                    // if delete and curchar was newline do not compound edit
                    if (keyCode==KeyEvent.VK_DELETE && curChar!='\n') {// was delete key
                        checkMode(CompoundTextEdit.DELETE,edit);
                    }
                    else if (keyCode==KeyEvent.VK_BACK_SPACE && prevChar!='\n') {// was backspace
                        checkMode(CompoundTextEdit.BKSPACE,edit);
                    }
                    else // was replacement
                    {
                        endCompoundTextEdit();
                        undoMgr.addEdit(edit);
                    }
                }
                else // not single insert or delete
                {
                    endCompoundTextEdit();
                    undoMgr.addEdit(edit);
                }
            }
            else // must be a style change
            {
                endCompoundTextEdit();
                undoMgr.addEdit(edit);
            }
        }
    }


    /*********************************************************************
    * This collects sequential actions and allows them to be undone/redone
    * as a single unit.
    */
    private class CompoundTextEdit extends CompoundEdit
    {
    	private static final long serialVersionUID = 1L;
    	private int mode=0;
        private int lastPos=0;
        static final int INSERT=0;
        static final int DELETE=1;
        static final int BKSPACE=2;
        CompoundTextEdit(int d) { mode=d;}
        int getMode() { return mode;}
        public boolean addEdit(UndoableEdit anEdit, int curPos)
        {
            lastPos = curPos;  // save last position in the sequence
            return super.addEdit(anEdit);
        }
        boolean isSequential(int curPos)
        {
            return (!((mode == INSERT && lastPos+1 != curPos)||
                    (mode == DELETE && lastPos != curPos)||
                    (mode == BKSPACE && lastPos-1 != curPos)));
        }
    }

    /*********************************************************************
    * This is used to group sets of edits for delete and insert
    */
    private class CompoundReplaceEdit extends CompoundEdit
    {
    	private static final long serialVersionUID = 1L;
    	private String type=null;
        CompoundReplaceEdit(String t) { type=t.toLowerCase();}
        public String getPresentationName() {return type;}
        public String getUndoPresentationName() { return "Undo "+type; }
        public String getRedoPresentationName() { return "Redo "+type; }
    }

    /*********************************************************************
    * This controls undo and the state of the action.
    */
    private class UndoAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private JButton undoButton=null;
        private Action updateAction=null;
        void dereference()
        {
            undoButton=null;
            updateAction = null;
        }
        UndoAction() {
            super(UNDO_EDIT_ACTION);
            setEnabled(false);
        }
        void setButton(JButton b, Action update)
        {
            undoButton = b;
            updateAction = update;
        }

        /**
         * override AbstractAction
         *
         * @param e ActionEvent
         * @concurrency $none
         */
        public void actionPerformed(ActionEvent e) {
/* got this when press undo rapidly
java.lang.ArrayIndexOutOfBoundsException
        at java.lang.System.arraycopy(Native Method)
        at javax.swing.text.AbstractDocument$BranchElement.replace(AbstractDocument.java:2007)
        at javax.swing.text.AbstractDocument$ElementEdit.undo(AbstractDocument.java:2728)
        at javax.swing.undo.CompoundEdit.undo(CompoundEdit.java:54)
        at javax.swing.text.AbstractDocument$DefaultDocumentEvent.undo(AbstractDocument.java:2506)
        at javax.swing.undo.CompoundEdit.undo(CompoundEdit.java:54)
        at javax.swing.undo.UndoManager.undoTo(UndoManager.java:212)
        at javax.swing.undo.UndoManager.undo(UndoManager.java:277)
        at COM.ibm.eannounce.editor.XMLUndoEditMgr$UndoAction.actionPerformed(XMLUndoEditMgr.java:389)
*/
            synchronized(XMLUndoEditMgr.this) // attempt to avoid undo problems if user presses undo rapidly
            {
                try {
                    int startPos = 0;
                    // disable caret until undo or redo is complete to avoid excessive
                    // parsing and activation/deactivation of actions through caret listener
                    editor.ignoreCaret(true);

                    if (compoundTextEdit != null)
                    {
                        compoundTextEdit.end();
                        compoundTextEdit=null;
                    }
    //              String selText = editor.getSelectedText();
                    // get len of currently selected text
    //              int selLen = (selText==null)?0:selText.length();
    //              int oldLen = editor.getDocument().getLength();
                    startPos = editor.getSelectionStart();

                    // indicate the page is loading
                    editor.setWaitCursors();
                    undoMgr.undo();

                    // reset the caret location to avoid inaccurately drawn <br>
                    // if user adds <br> and then types, then undoes, the typing is
                    // removed but the caret is placed after the space in the model
                    // the <br> uses.. wrong!!
                    editor.setSelectionStart(editor.getSelectionStart());

                    // in applet occasionally leaves blank spots when restoring tagged area
    /*              if (oldLen<editor.getDocument().getLength())  // text was added
                    {
                        int diffLen = editor.getDocument().getLength() - oldLen;
                        if (selLen==0) diffLen=0;
                        editor.setSelectionStart(startPos);
                        editor.setSelectionEnd(startPos+selLen+diffLen);
                    }
                    else
                    {
                        editor.setSelectionStart(editor.getSelectionStart());
                        editor.setSelectionEnd(editor.getSelectionStart()+selLen);
                    }
    */
                    // make sure the actions reflect the latest changes
                    editor.ignoreCaret(false);

                    // input attributes are not getting reset when undo runs, force it
                    ((XMLEditorKit)editor.getEditorKit()).restoreInputAttributeSet();
                } catch (CannotUndoException ex) {
                    // there is a bug in jdk1.3.9 intermittent undo exc
                    // clear any pending undo/redo
                    undoMgr.discardAllEdits();

                    // make sure the actions reflect the latest changes
                    editor.ignoreCaret(false);

                    System.err.println("Unable to undo: " + ex);
                    ex.printStackTrace();
                }
                finally{
                    editor.restoreCursors();
                }
                updateUndoState();
                redoAction.updateRedoState();
            } // end synch on this
        }

        void updateUndoState()
        {
            setEnabled(undoMgr.canUndo()|| (compoundTextEdit!=null&&compoundTextEdit.isInProgress()));
            // update save action state
            if (updateAction !=null) {
                updateAction.setEnabled(this.isEnabled()||hadChanges);
            }

            putValue(Action.NAME, undoMgr.getUndoPresentationName());
            undoButton.setText(""); //an icon-only button
            undoButton.setToolTipText(undoMgr.getUndoPresentationName());
            undoButton.getAccessibleContext().setAccessibleName(undoMgr.getUndoPresentationName());

            /* markup test
            // check if the cursor is in marked text
            a = (Action)actions.get(PartialMarkupAction);
            if (a !=null)
                a.setEnabled(markupMgr.isMarkupText());
            */
        }
    }

    /*********************************************************************
    * This controls redo and the state of the action.
    */
    private class RedoAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	private JButton redoButton=null;
        RedoAction()
        {
            super(REDO_EDIT_ACTION);
            setEnabled(false);
        }

        void setButton(JButton b)
        {
            redoButton = b;
        }
        /**
         * override AbstractAction
         *
         * @param e ActionEvent
         * @concurrency $none
         */
        public void actionPerformed(ActionEvent e)
        {
            synchronized(XMLUndoEditMgr.this) // attempt to avoid undo problems if user presses undo rapidly
            {
                try {
                    final int startPos = editor.getSelectionStart();
                    // disable caret until undo or redo is complete to avoid excessive
                    // parsing and activation/deactivation of actions through caret listener
                    editor.ignoreCaret(true);

                    // indicate the page is loading
                    editor.setWaitCursors();
                    editor.getCaret().setVisible(false);  // avoid flash

                    undoMgr.redo();

                    // redo of <br> insertion does not put caret in proper place
                    // it puts it after the blank inserted into the content instead of
                    // after the newline used by the view, by running this after this action
                    // completes the cursor is placed in the correct spot
                    if (undoMgr!=null) { // fake out jtest
                        final int curPos = editor.getSelectionStart();
                        final int endPos = editor.getSelectionEnd();
                        SwingUtilities.invokeLater(new Runnable()
                            {
                                public void run()
                                {
                                    javax.swing.text.Element elem = null;
                                    editor.setCaretPosition(Math.min(curPos,editor.getDocument().getLength()));
                                    editor.moveCaretPosition(Math.min(endPos,editor.getDocument().getLength()));
                                    editor.getCaret().setVisible(true);  // avoid flash
                                    // make sure the actions reflect the latest changes
                                    editor.ignoreCaret(false);

                                    // if restoring a table, jre1.4.0 does not repaint properly
                                    elem = ((javax.swing.text.StyledDocument)editor.getDocument()).getParagraphElement(startPos);
             // JRE 1.4.0 GM PMR 40100,001,866
                                    // jre1.4.0 does not repaint if content added to any cell in a table.. force it here
                                    while(elem!=null && !elem.getName().equals("tr")) {
                                        elem = elem.getParentElement();
                                    }
                                    if (elem!=null) {
                                        editor.repaint();
                                    }
                                    // end jre1.4.0 does not repaint
                                }
                            });
                    }
                } catch (CannotRedoException ex) {
                    // there is a bug in jdk1.3.9 intermitten undo exc
                    // clear any pending undo/redo
                    undoMgr.discardAllEdits();
                    endCompoundTextEdit();
                    // make sure the actions reflect the latest changes
                    editor.ignoreCaret(false);

                    System.err.println("Unable to redo: " + ex);
                    ex.printStackTrace();
                }
                finally{
                    editor.restoreCursors();
                }
                updateRedoState();
                undoAction.updateUndoState();
            }
        }

        void updateRedoState()
        {
            setEnabled(undoMgr.canRedo());
            putValue(Action.NAME, undoMgr.getRedoPresentationName());
            redoButton.setText(""); //an icon-only button
            redoButton.setToolTipText(undoMgr.getRedoPresentationName());
            redoButton.getAccessibleContext().setAccessibleName(undoMgr.getRedoPresentationName());
        }
    }
}

