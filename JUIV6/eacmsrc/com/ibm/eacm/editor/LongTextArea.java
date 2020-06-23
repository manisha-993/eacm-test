//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.preference.ColorPref;

/**
 * this class is used to display a long text editor in a dialog or form
 * @author Wendy Stimpson
 */
//$Log: LongTextArea.java,v $
//Revision 1.2  2013/08/14 16:47:11  wendy
//check max length when text is entered
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class LongTextArea extends JTextArea implements EditorComponent {	
	private static final long serialVersionUID = 1L;
	private LongEditor longEditor = null;
	private boolean byPassValidation = false;
	
	//added for formeditor
	private FormCellPanel fcp = null; 
	public FormCellPanel getFormCellPanel() { return fcp;}
	public void setFormCellPanel(FormCellPanel f) {fcp=f;}
	//end added for formeditor
	
	/**
	 * constructor
	 * @param le
	 */
	protected LongTextArea(LongEditor le){
		super(0,80);
		longEditor = le;
		setLineWrap(true); 
		setWrapStyleWord(true);
		//textArea.setFont(new Font("Courier New", 0, 11)); use preferences

		// allow esc to get to the container
		getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none"); 
	}
	/**
	 * release memory
	 */
	protected void dereference(){
		fcp=null;
		longEditor = null;
		removeAll();
		setUI(null);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		if(longEditor!=null){
			return longEditor.getBackground();
		}
		return ColorPref.getOkColor();
	}
	
	void setDisplay(String s) {
		byPassValidation = true;
		setText(s);
		byPassValidation = false;
	}
	
    /**
     * character validation
     *
     * @return Document
     */
    protected Document createDefaultModel() {
        return new MetaDoc();
    }
   
    private class MetaDoc extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    		if (str != null) {
    			if (byPassValidation) {
    				super.insertString(offs, str, a);
    			} else if (longEditor.charValidation(str)) {	
    				super.insertString(offs, str, a);
    			}
    			repaint();
    		}
        }
    }

}