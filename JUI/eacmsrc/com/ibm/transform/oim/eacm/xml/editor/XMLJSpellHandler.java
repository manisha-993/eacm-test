// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import com.wallstreetwise.app.jspell.gui.*;
import com.wallstreetwise.app.jspell.domain.*;
import javax.swing.event.*;
import java.beans.*;

import java.awt.*;
import java.awt.event.*;

/******************************************************************************
* This is used to recognize when the JSpell thread has ended.
* This is needed when an attribute requires spell check before saving.
* there is no way to know when jspell has completed without this class
* this class will maintain state based on if spell check is required and
* if the user ran it after making changes and before trying to save
*
* @author Wendy Stimpson
*
* Change History:
*/
// $Log: XMLJSpellHandler.java,v $
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.2  2006/10/25 13:47:57  wendy
// Added code to get around JSpell flaw with window closing  and thread notification
//
// Revision 1.1  2006/10/24 16:35:10  wendy
// Init for TIR 6UQGPT, enforce spellcheck if meta has rule
//
//
class XMLJSpellHandler extends JSpellErrorHandler implements DocumentListener, PropertyChangeListener
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";

    private boolean spellChkRequired = false;   // true if meta has spellcheck rule
    private boolean spellChkDone = false;       // true if spchk ran since last user change
    private XMLEditorPane editor = null;
	private DialogListener dialogListener;		// need to know when window is closed
	private Window jspellDialog;				// handle to correction dialog to add/remove listener
    private Thread activeThread=null;

    /******************************************
    * constructor
    * @param parser JSpellParser
    * @param textComponent com.wallstreetwise.core.gui.JSpellTextComponent
    * @param xeditor XMLEditorPane
    */
    XMLJSpellHandler(JSpellParser parser,
        com.wallstreetwise.core.gui.JSpellTextComponent textComponent,
        XMLEditorPane xeditor)
    {
        super(parser,textComponent);
        editor = xeditor;
    }

    /******************************************
    * this is set when an attribute is loaded
    * if true, attribute has the spellcheck rule set and spellchk must run before saved
    * @param b boolean
    */
    void setSpellCheckRequired(boolean b) {
        spellChkRequired = b;
        spellChkDone = false;
    }

    /******************************************
    * returns true if meta has spellcheck rule and spellcheck hasn't been done since
    * last change was made
    */
    boolean isRequired() {
        return (spellChkRequired & !spellChkDone);
    }

    /******************************************
    * release memory
    */
    void dereference()
    {
        try{
            JSpellParser parser;
            setTextComponent((com.wallstreetwise.core.gui.JSpellTextComponent)null);
            parser = getParser();
            parser.setPercentChangeListener(null);
            try{
                parser.setDictionary(null);
            }
            catch(RuntimeException re)
            {
                // this will be thrown when the dictionary is verified, but the ref is released
                String msg=re.getMessage(); // jtest req
                msg=null;
            }
            setParser(null);
            // there is still a reference to JSpellErrorHandler held by the JSpellCorrectionDialog
            // that can't be reached to release it, every time the editor is instantiated, one
            // more JSpellErrorHandler will be left behind
        }
        catch(Exception e)
        { // a null ptr exception will be thrown when the handler tries to use the null parser
            String msg=e.getMessage(); // jtest req
            msg=null;
        }
        editor.getDocument().removeDocumentListener(this);
        editor = null;

        if (jspellDialog!=null){
			jspellDialog.removeWindowListener(dialogListener);
			jspellDialog = null;
			dialogListener = null;
		}

		activeThread = null;
    }

    /******************************************
    * problem with jspell, it doesn't stop the thread when window is closed. it only
    * stops when the 'stop' button is pressed or spell check completes (no more errors)
    * get handle to error dialog and add window closing listener
    *
    */
	public void init()
	{
		Window childWindows[];
		super.init();
		// base class init() creates the correctiondialog
		childWindows = ((Window)getFrame()).getOwnedWindows();
		for (int i=0; i<childWindows.length; i++){
			Window child = childWindows[i];
			if (child instanceof JSpellCorrectionDialog){
				jspellDialog = child;
				dialogListener = new DialogListener();
				jspellDialog.addWindowListener(dialogListener);
				break;
			}
		}
		// release memory
		for (int i=0; i<childWindows.length; i++){
			childWindows[i] =null;
		}
	}

    // add listeners for document changes
    /******************************************
    * document listener
    * @param e DocumentEvent
    */
    public void changedUpdate(DocumentEvent e)
    {
        spellChkDone = false;
    }

    /******************************************
    * Gives notification that there was an insert into the document.
    * @param e DocumentEvent
    */
    public void insertUpdate(DocumentEvent e)
    {
        spellChkDone = false;
    }

    /*******************************************
    * Gives notification that a portion of the document has been removed.
    * @param e DocumentEvent
    */
    public void removeUpdate(DocumentEvent e)
    {
        spellChkDone = false;
    }

    /*******************************************
    * property change sent when editor is loaded (document is ready for listeners)
    * @param evt
    */
    public void propertyChange(PropertyChangeEvent evt)
    {
        // notified when content is completely loaded
        if (evt.getPropertyName().equals("page"))
        {
            editor.getDocument().addDocumentListener(this);
        }
    }

    /*********************************************************************
    * run spellcheck
    */
    public void run()
    {
        activeThread = Thread.currentThread();

        // remove listeners, document will be replaced
        editor.getDocument().removeDocumentListener(this);

        spellChkDone = true;
        // run spellchecker
        try{
            super.run();
        }catch(Throwable t) {
			// if window is closed, DialogListener will interrupt this thread to kill it
            System.err.println("XMLJSpellHandler::run() exception in "+activeThread+" : "+t);
            //t.printStackTrace(System.err);
        }

		activeThread=null;
		// the document was replaced by jspell updates
        editor.getDocument().addDocumentListener(this);

        // must do this on a separate thread to get it to work in an applet
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                editor.requestFocus();  // editor does not get focus in applet
            }
        });
    }

    /*********************************************************************
    * must track window closing to restore documentlistener to know of any
    * changes (to enforce spellchk before save)
    */
    private class DialogListener extends WindowAdapter
    {
        // called by closing the window
        public void windowClosing(WindowEvent evt)
        {
			/* if user closes jspell dialog with window control, the thread isn't notified
			and when run a second time, both threads try to update the editor.  so kill the
			first one now, ugly but works */
			if (activeThread!=null) {
				System.err.println("JSpell dialog closing, killing "+activeThread);
				activeThread.interrupt();
			}
	  	}
	}
}
