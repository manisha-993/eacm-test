//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.edit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.Utils;

import com.wallstreetwise.app.jspell.domain.JSpellParser;
import com.wallstreetwise.app.jspell.gui.JSpellCorrectionDialog;
import com.wallstreetwise.app.jspell.gui.JSpellErrorHandler;

/*********************************************************************
 * This is used to run and clean up spell check
 * @author Wendy Stimpson
 */
//$Log: SpellCheckHandler.java,v $
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class SpellCheckHandler extends JSpellErrorHandler implements DocumentListener
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
	/** cvs version */
	public static final String VERSION = "$Revision: 1.1 $";

	private DialogListener dialogListener;		// need to know when window is closed
	private Thread activeThread=null;
	private boolean showZero = false;
	private boolean spellChkRequired = false;   // true if meta has spellcheck rule
	private boolean spellChkDone = false;       // true if spchk ran since last user change
	private SpellCheckWrapper wrapper = null;
	private JSpellCorrectionDialog jspellDialog = null;
	private int errCount=0;

	/******************************************
	 * constructor
	 * @param parser
	 * @param scw
	 * @param showZeroError
	 */
	public SpellCheckHandler(JSpellParser parser,SpellCheckWrapper scw)
	{
		super(parser,scw);

		wrapper = scw;
		// add a listener to the text document
		wrapper.addDocumentListener(this);

		setFrame(EACM.getEACM());
		init();

		jspellDialog = getCorrectionDialog();

		dialogListener = new DialogListener();

		/*
		 * problem with jspell, it doesn't stop the thread when window is closed. it only
		 * stops when the 'stop' button is pressed or spell check completes (no more errors)
		 * get handle to error dialog and add window closing listener
		 */
		jspellDialog.addWindowListener(dialogListener);
	}

	/******************************************
	 * this is set when an attribute is loaded
	 * if true, attribute has the spellcheck rule set and spellchk must run before saved
	 * @param b boolean
	 */
	public void setSpellCheckRequired(boolean b) {
		spellChkRequired = b;
		spellChkDone = false;
	}

	/******************************************
	 * returns true if meta has spellcheck rule and spellcheck hasn't been done since
	 * last change was made
	 */
	public boolean isRequired() {
		return (spellChkRequired & !spellChkDone);
	}
	/******************************************
	 * returns true if meta has spellcheck rule
	 * spellcheck action needs to turn it off and then back on
	 */
	public boolean spellCheckRequired() {
		return spellChkRequired;
	}
	/**
	 * spellcheck action needs to restore this
	 * @param b
	 */
	public void restoreSpellCheckRequired(boolean b) {
		spellChkRequired =b;;
	}

	/* (non-Javadoc)
	 * use this to count errors
	 * @see com.wallstreetwise.app.jspell.gui.JSpellErrorHandler#processResults()
	 */
	public void processResults(){
		super.processResults();
		errCount++;
	}
    // add listeners for document changes
    /******************************************
    * document listener
    * @param e DocumentEvent
    */
    public void changedUpdate(DocumentEvent e) {
        spellChkDone = false;
    }

    /******************************************
    * Gives notification that there was an insert into the document.
    * @param e DocumentEvent
    */
    public void insertUpdate(DocumentEvent e) {
        spellChkDone = false;
    }

    /*******************************************
    * Gives notification that a portion of the document has been removed.
    * @param e DocumentEvent
    */
    public void removeUpdate(DocumentEvent e) {
        spellChkDone = false;
    }

	/**
	 * run spell check
	 * @param showZeroError if true, show zero error count msg
	 */
	public void check(boolean showZeroError) {
		showZero = showZeroError;
		errCount=0;
		super.check();
	}

	/*********************************************************************
	 * run spellcheck
	 */
	public void run(){
		wrapper.spellCheckStarted();
		activeThread = Thread.currentThread();
	    spellChkDone = true;
		// run spellchecker
		try{
			super.run();
		}catch(Throwable t) {
			// if window is closed, DialogListener will interrupt this thread to kill it
			//t.printStackTrace(System.err);
		}

		activeThread=null;

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (errCount == 0) {
					if (showZero) {
						com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11025.3",""+errCount));
					}
				} else {
					com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg11025.3",""+errCount));
				}
			}
		});

		wrapper.spellCheckEnded();
	}
	/******************************************
	 * release memory
	 */
	public void dereference(){
    	if(activeThread!=null){
    		cancel(); // make sure everything is shutdown
    	}

		wrapper.dereference();
		wrapper = null;

		try{
			JSpellParser parser = getParser();
			setTextComponent((com.wallstreetwise.core.gui.JSpellTextComponent)null);
			parser.setPercentChangeListener(null);
			try{
				parser.setDictionary(null);
			}
			catch(RuntimeException re) {
				// this will be thrown when the dictionary is verified, but the ref is released
			}
			setParser(null);
			// there is still a reference to JSpellErrorHandler held by the JSpellCorrectionDialog
			// that can't be reached to release it, every time the editor is instantiated, one
			// more JSpellErrorHandler will be left behind
		}
		catch(Exception e)	{
			// a null ptr exception will be thrown when the handler tries to use the null parser
		}

		jspellDialog.removeWindowListener(dialogListener);
		jspellDialog.dispose();
		jspellDialog = null;

		dialogListener = null;
		activeThread = null;
	}

	/*********************************************************************
	 * must track window closing
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
				errCount++; // there was at least one error
				activeThread.interrupt();
			}
		}
	}

}
