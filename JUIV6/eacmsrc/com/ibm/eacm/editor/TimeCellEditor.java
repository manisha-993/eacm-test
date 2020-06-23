//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.objects.Utils;

import java.util.Calendar;
import java.util.Date;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/***************
 * 
 * Cell editor for Time Attributes
 * @author Wendy Stimpson
 */
//$Log: TimeCellEditor.java,v $
//Revision 1.3  2013/12/04 22:01:18  wendy
//use time editor value for getting bg color
//
//Revision 1.2  2013/12/04 20:18:28  wendy
//use meta rules for editor background color
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class TimeCellEditor extends AttrCellEditor 
{
	private static final long serialVersionUID = 1L;
	private TimeEditor timeEditor = null;
	private DocumentListener docListener = null;

	//===============added for using in a form
    public Color getBackground() {
    	String value = "";
    	if(timeEditor!=null){
    		value = timeEditor.getTime();
    	}
    	timeEditor.getTextField().setBackground(getBackground(value));
		return getBackground(value);
    } 
	public void setForeground(Color fg) {
		timeEditor.setForeground(fg);
	}
	public void setFont(Font font) {
		timeEditor.setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	timeEditor.setOpaque(isOpaque);
    }
    
	public void setFormCellPanel(FormCellPanel f) {timeEditor.setFormCellPanel(f);};// added for formeditor
	public FormCellPanel getFormCellPanel(){// added for formeditor
		return timeEditor.getFormCellPanel();
	}
	//===============end added for using in a form	
	
	/**
	 * constructor
	 * @param prof
	 * @param inSearch
	 */
	public TimeCellEditor(Profile prof, boolean inSearch) {
		super(inSearch);
		timeEditor = new TimeEditor(prof);
    	// set the color in the editor
		timeEditor.getTextField().setBackground(getBackground(""));
		
		//make it look like the renderer, left aligned
		((JSpinner.DefaultEditor)timeEditor.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
	}

    /**
     * perform the cut action
     */
    public void cut(){
    	((JSpinner.DefaultEditor)timeEditor.getEditor()).getTextField().cut();
    }
    /**
     * hang onto the document listener, add it after attribute is set
     * @param dl
     */
    public void addDocumentListener(DocumentListener dl){
    	docListener = dl;
    }
    
	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
	 */
	public Component getComponent(){
		return timeEditor;
	}

	/**
	 * use editor rules when pasting
	 * @param pasteObj 
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		String timeStr = pasteObj.toString();
		int colon = timeStr.indexOf(":");
		if(timeStr.length()!=5 || colon==-1){
	        //msg5021.3 = Invalid time format, it must be HH:MM.
            com.ibm.eacm.ui.UI.showFYI(timeEditor, Utils.getResource("msg5021.3"));
            return false;
		}
		setEditorValue(timeStr);
	
		// check to see if any errors were flagged
		String textAfter = timeEditor.getTime();
		if (textAfter==null){
			textAfter="";
		}
		boolean success = timeStr.equals(textAfter);
		if(!success){
		    UIManager.getLookAndFeel().provideErrorFeedback(null);
		}
		return success;
	}

	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    public void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	timeEditor.getTextField().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
    	timeEditor.getTextField().getActionMap().put(act.getActionKey(), act);		
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
    	timeEditor.getTextField().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
    	timeEditor.getTextField().getActionMap().remove(act.getActionKey());
    }
	/**
	 * return the edited value
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return timeEditor.getTime();
	}


	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof EANAttribute) {
			setAttribute((EANAttribute) value);
		}
		return timeEditor;
	} 

	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setAttribute(EANAttribute _ean) {
		super.setAttribute(_ean);

		if(docListener != null){
			((JSpinner.DefaultEditor)timeEditor.getEditor()).getTextField().getDocument().removeDocumentListener(docListener);
		}
		setEditorValue(attr.toString());
		if(docListener != null){
			((JSpinner.DefaultEditor)timeEditor.getEditor()).getTextField().getDocument().addDocumentListener(docListener);
		}
	}
	
	private void setEditorValue(String value){
		Calendar timeCalendar = Calendar.getInstance();

		if (value !=null && value.toString().length()>0){
			int hr = Integer.parseInt(value.substring(0,2));
			int min = Integer.parseInt(value.substring(3,5));
			timeCalendar.set(2010, 1, 1, hr, min); 
		}else{
			timeCalendar.set(2010, 1, 1, 23, 59); // only hrs and mins matter			
		}

		// must use a date object in this control
		timeEditor.setValue(new Date(timeCalendar.getTimeInMillis()));
	}
	/**
	 * release memory
	 */
	public void dereference(){
		super.dereference();
		if(docListener != null){
			((JSpinner.DefaultEditor)timeEditor.getEditor()).getTextField().getDocument().removeDocumentListener(docListener);
			docListener = null;
		}
		timeEditor.dereference();
		timeEditor = null;
	}

}
