//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Color;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import java.util.EventListener;
import java.awt.event.*;

import com.ibm.eacm.preference.ColorPref;

import COM.ibm.eannounce.objects.SimpleTextAttribute;

/***************
 * 
 * Cell editor for SimpleTextAttribute 
 * - used in FilterTable for value column and MaintTable
 * @author Wendy Stimpson
 */
//$Log: SimpleTextCellEditor.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class SimpleTextCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 1L;
	protected DocumentListener docListener;

	/**
	 * constructor
	 */
	public SimpleTextCellEditor(DocumentListener dl) {
		this(new Text(),dl);
	}
	/**
	 * @param tf
	 * @param dl
	 */
	protected SimpleTextCellEditor(JTextField tf,DocumentListener dl) {
		super(tf);
		setClickCountToStart(2);
		docListener = dl;
		if(docListener != null){
			((JTextField)getComponent()).getDocument().addDocumentListener(docListener);
		}
	}

	/**
	 * set the attribute to use for this editor execution
	 * @param ean
	 */
	public void setTextAttribute(SimpleTextAttribute ean) {
		if(docListener != null){
			((JTextField)getComponent()).getDocument().removeDocumentListener(docListener);
		}
		((JTextField)getComponent()).setText(ean.toString());
		if(docListener != null){
			((JTextField)getComponent()).getDocument().addDocumentListener(docListener);
		}
	}
	
	/**
	 * release memory
	 */
	public void dereference(){
		if(docListener != null){
			((JTextField)getComponent()).getDocument().removeDocumentListener(docListener);
			docListener = null;
		}
	   	EventListener listeners[] = ((JTextField)getComponent()).getListeners(ActionListener.class);
    	if (listeners!=null){
    		for(int ii=0; ii<listeners.length; ii++) {
    			((JTextField)getComponent()).removeActionListener((ActionListener)listeners[ii]);
    			listeners[ii]=null;
    		}
    	}
     	((JTextField)getComponent()).setUI(null);
    	((JTextField)getComponent()).removeAll();
	}
	
	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
    private static class Text extends JTextField {
		private static final long serialVersionUID = 1L;

    	//must do it like this or List in renderer doesnt have it
        public Color getBackground() {
        	return ColorPref.getOkColor();  
        }    
    }
}
