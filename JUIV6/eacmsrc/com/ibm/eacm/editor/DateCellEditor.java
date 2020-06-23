//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.DocumentListener;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.edit.form.FormTable;
import com.ibm.eacm.objects.Utils;

import java.awt.event.*;
import COM.ibm.eannounce.objects.*;

/***************
 * 
 * Cell editor for Date Attributes
 * @author Wendy Stimpson
 */
//$Log: DateCellEditor.java,v $
//Revision 1.2  2013/08/14 16:52:11  wendy
//restore after paste failure
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class DateCellEditor extends AttrCellEditor implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private DateEditor dateEditor = new DateText();
	private DocumentListener docListener = null;
	
	//===============added for using in a form
	public void setForeground(Color fg) {
		dateEditor.setForeground(fg);
	}
	public void setFont(Font font) {
		dateEditor.setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	dateEditor.setOpaque(isOpaque);
    }
	public void setFormCellPanel(FormCellPanel f) {
		((DateText)dateEditor).setFormCellPanel(f);
	}
	public FormCellPanel getFormCellPanel(){// added for formeditor
		return ((DateText)dateEditor).getFormCellPanel();
	}
	
	//====================================
    /**
	 * constructor
	 */
	public DateCellEditor(boolean inSearch) {
		super(inSearch);
		dateEditor.addActionListener(this);
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
	 */
	public Component getComponent(){
		return dateEditor;
	}
    /**
     * perform the cut action
     */
    public void cut(){
    	dateEditor.cut();
    }
	/**
	 * use editor rules when pasting
	 * @param pasteObj 
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		StringBuilder sb = new StringBuilder();
		String data = pasteObj.toString().trim();
		int selstart = 0;		
		String textBefore = dateEditor.getText();
		if (textBefore==null){
			textBefore="";
		}
		
		if(editOpen){
			// is the entire string selected?
			selstart = dateEditor.getSelectionStart();
			int selend = dateEditor.getSelectionEnd();

			// build a new string representing the text after paste is done
			if(!DateEditor.NO_VALUE.equals(textBefore) && selstart!=0){
				sb.append(textBefore.substring(0, selstart));
			}
			sb.append(data);
			if(!DateEditor.NO_VALUE.equals(textBefore) && selend!=textBefore.length()){
				sb.append(textBefore.substring(selend,textBefore.length()));
			}
		}else{
			// if editor was not open, replace the entire contents
			sb.append(data);
		}
		
		dateEditor.setText(sb.toString());
		// check to see if any errors were flagged when inserting into the doc
		String textAfter = dateEditor.getText();
		if (textAfter==null){
			textAfter="";
		}

		//boolean success = sb.toString().equals(textAfter);
		boolean success = textAfter.indexOf(sb.toString()) != -1; // allow for partial date pasted
		if(!success){
			if(DateEditor.NO_VALUE.equals(textBefore)){
				dateEditor.setText(null); // cant set the format string - just ignores it and beeps
			}else {
				dateEditor.setText(textBefore);
			}
		    UIManager.getLookAndFeel().provideErrorFeedback(dateEditor);
			//msg50000 = Invalid date specified. {0}
			com.ibm.eacm.ui.UI.showErrorMessage(dateEditor, Utils.getResource("msg50000",sb.toString()));
		}else{
			success = dateEditor.isValidDateSyntax();
			if(!success){
				if(DateEditor.NO_VALUE.equals(textBefore)){
					dateEditor.setText(null); // cant set the format string - just ignores it and beeps
				}else {
					dateEditor.setText(textBefore);
				}
			}else{
				selstart+=data.length();
				if(selstart<=sb.toString().length()){
					dateEditor.setCaretPosition(selstart);
				}
			}
		}
		
		dateEditor.requestFocusInWindow();
		
		return success;
	}
	
	/**
	 * get the item selected - used in vert and grid edit for copy
	 * @return
	 */
	public Object getSelection() { 
		String selection = dateEditor.getSelectedText(); 
		String attrval = getAttribute().toString();
		
		//if user has not hit 'ok' the contents are not in the attribute, so copy will not have the value to paste
		if(attrval.equals(selection)){
			return getAttribute();
		}
		
		return selection;
	}
	
	/**
	 * is this a valid date
	 * @param datetxt
	 * @return
	 */
	public boolean isValid(String datetxt){
		dateEditor.setText(datetxt);
		// check to see if any errors were flagged when inserting into the doc
		
		return canStopCellEditing();
	}
	
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.AttrCellEditor#registerEACMAction(com.ibm.eacm.actions.EACMAction, javax.swing.KeyStroke)
     */
    public void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	// must get to WHEN_FOCUSED input map too for textfields 
    	dateEditor.getInputMap().put(keystroke, act.getActionKey());
    	dateEditor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
    	dateEditor.getActionMap().put(act.getActionKey(), act);		
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
    	dateEditor.getInputMap().remove(keystroke);
    	dateEditor.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
    	dateEditor.getActionMap().remove(act.getActionKey());
    }
    /**
     * hang onto the document listener, add it after attribute is set
     * @param dl
     */
    public void addDocumentListener(DocumentListener dl){
    	docListener = dl;
    }
    
    /**
     * called if the user cancels the edit, should hide the dialog here
     * @see javax.swing.AbstractCellEditor#cancelCellEditing()
     */
    public void cancelCellEditing() { 
    	dateEditor.setInputVerifier(null);
    	super.cancelCellEditing(); 
    }
    
    /**
     * called if the user clicks on a different cell, should hide the dialog here
     * returning true tells the table to use the partially edited value, that it is valid
     * @see javax.swing.AbstractCellEditor#stopCellEditing()
     */
    public boolean stopCellEditing() { 
    	if(dateEditor.isValidDate()){ //this may popup a warning dialog!!!! 
    		dateEditor.setInputVerifier(null);
    		return super.stopCellEditing();
    	}
    	
    	return false; // this prevents user from leaving the cell
    }
    /**
     * get around focus issue with losing focus to longcellpanel
     * dont put up the error dialog until after focus is transferred
     * @return
     */
    public boolean canStopCellEditing(){
    	return dateEditor.isValidDate(false);
    }
    /**
     * return the edited value
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
    	// check to see if a date was actually entered, user may have opened the editor and closed it before selecting
    	// a date, only the template would be in the textfield
    	String date = dateEditor.getDate();
    	if (DateEditor.NO_VALUE.equals(date)){
    		date=null;
    	}
    	
    	return date;
    }


	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (value instanceof EANAttribute) {
			setAttribute((EANAttribute) value);
		}
		return dateEditor;
	} 
    
    /**
     * prevent null ptr when editor is removed 
     * @see javax.swing.AbstractCellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
     */
    public void removeCellEditorListener(CellEditorListener l){
    	dateEditor.setInputVerifier(null); // prevent null ptr in verifier
    	super.removeCellEditorListener(l);
    }
    
    /**
     * allow enter in textfield to stop edit
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	stopCellEditing();
    }
    
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setAttribute(EANAttribute _ean) {
		super.setAttribute(_ean);
		if (!inSearch && metaAttr!=null){ // search doesnt check future or past
			if (metaAttr.isFutureDate()){
				if (metaAttr.isWarningDate()){
					dateEditor.setDateType(DateEditor.DateType.FUTURE_WARNING_DATE);
				}else{
					dateEditor.setDateType(DateEditor.DateType.FUTURE_DATE);
				}
			}else if (metaAttr.isPastDate()){
				if (metaAttr.isWarningDate()){
					dateEditor.setDateType(DateEditor.DateType.PAST_WARNING_DATE);
				}else{
					dateEditor.setDateType(DateEditor.DateType.PAST_DATE);
				}
			}else{ // mainly a reset
				dateEditor.setDateType(DateEditor.DateType.ANY_DATE);
			}
		}else{ // mainly a reset
			dateEditor.setDateType(DateEditor.DateType.ANY_DATE);
		}

		if(docListener != null){
			dateEditor.getDocument().removeDocumentListener(docListener);
		}
		int caret = dateEditor.getCaretPosition();
		dateEditor.setDisplay(attr.toString());
		if(docListener != null){
			dateEditor.getDocument().addDocumentListener(docListener);
		}
		if(caret>attr.toString().length()){ // dont go past end
			caret = 0;
		}
		dateEditor.setCaretPosition(caret);
	}
	/**
	 * release memory
	 */
	public void dereference(){
		super.dereference();
		if(docListener != null){
			dateEditor.getDocument().removeDocumentListener(docListener);
			docListener = null;
		}
		
	    dateEditor.removeActionListener(this);
    	dateEditor.dereference();
    	dateEditor = null;
	}

	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
	private class DateText extends DateEditor implements ActionListener, KeyListener, EditorComponent {
		private static final long serialVersionUID = 1L;
		private Dimension dButton = new Dimension(16, 16);
		private FormCellPanel fcp = null; // added for formeditor
		void setFormCellPanel(FormCellPanel f) {fcp=f;}
		public FormCellPanel getFormCellPanel() { return fcp;}// added for formeditor
		
		private JButton button = new JButton(Utils.getImageIcon("cal.gif")) {
			private static final long serialVersionUID = 1L;
			public Cursor getCursor() {
				return DateText.this.getCursor(); 
			}
			public void requestFocus(){
				DateText.this.giveUpFocus(); // dont verify the field when popup button is pressed
				super.requestFocus();
			}
		};
		private DatePopup popup = null;

	  	//must do it like this or List in renderer doesnt have it
        public Color getBackground() {
        	if(this.getDocument()==null){
        		return super.getBackground(); 
        	}
        	return DateCellEditor.this.getBackground(getDate());
        } 
		
		DateText(){
			popup = new DatePopup(this);
			button.setFocusable(false);
			add(button);
			button.addActionListener(this);
			button.setOpaque(false);
			popup.getDateSelector().addPropertyChangeListener(this);
			
			// can i get tab?
			addKeyListener(this);
		}
	    /* (non-Javadoc)
	     * must disable/enable table/editors
	     * @see javax.swing.JComponent#setEnabled(boolean)
	     */
	    public void setEnabled(boolean enabled) {
	    	super.setEnabled(enabled);
	    	button.setEnabled(enabled);
	    }
	    
		/* (non-Javadoc)
		 * @see com.ibm.eacm.editor.DateEditor#dereference()
		 */
		public void dereference(){
			super.dereference();
			fcp = null; 
			button.removeActionListener(this);
			popup.getDateSelector().removePropertyChangeListener(this);
			popup.setVisible(false);
			popup.dereference();
			popup = null;
		}

		/**
		 * user has selected a day on the calendar, hide it
		 * @see com.ibm.eacm.editor.DateEditor#setSelectedDate(java.lang.String)
		 */
		protected void setSelectedDate(String _date) {
			popup.setVisible(false);
			super.setSelectedDate(_date);
		}
		
		//put button on  right side of editor
		// only used when laying out a jtable cell or formtablecell
		public void setBounds(Rectangle _rec) {
			button.setBounds(_rec.width - dButton.width, (_rec.height - dButton.height) / 2, dButton.width, dButton.height);
			super.setBounds(_rec);
		}
		
		/**
		 * toggle the popup when button is pressed
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent _ae) {
			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				final FormTable ft = getFormCellPanel().getFormTable();
				if(!ft.canStopEditing()){
					// put the error dialog in another thread
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
		    			public void run() {
		    				//display problem to user
		    				ft.stopCellEditing();
		    			}
		    		});
					return;
				}
			}
			if (popup.isShowing()) {
				popup.setVisible(false);
			} else {
				Dimension popupSize = popup.getPreferredSize(); 
			    int x = this.getWidth() -popupSize.width;	
				popup.show(this, x, computeY());
			}
		}

		/***
		 * tell the selector what the datetype is, it will need to layout the calendar panel again
		 * @see com.ibm.eacm.editor.DateEditor#setDateType(com.ibm.eacm.editor.DateEditor.DateType)
		 */
		public void setDateType(DateType dt){
			super.setDateType(dt);
			if (popup!=null){
				popup.getDateSelector().setDateType(dt);
			}
		}

		/**
		 * prevent popup from covering editor 
		 * @return
		 */
		private int computeY() { 
			Point editorPt = this.getLocationOnScreen(); 
		
			// Get toolkit
		    Toolkit toolkit = Toolkit.getDefaultToolkit();

		    // Get screen size
		    Dimension screenDim = toolkit.getScreenSize();
		    
			Dimension pSize = popup.getPreferredSize(); 
			int popupHeight = pSize.height; 
			if (popupHeight + editorPt.y+this.getHeight()>screenDim.height) { 
				return -(popupHeight); // put popup over editor
			} 

			return getHeight();  // put popup under editor
		}

		/* (non-Javadoc)
		 * needed to grab enter when date button has focus, popupmenu doesnt work the same as a dialog with
		 * the dateselector panel
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()== KeyEvent.VK_TAB){
				e.consume();
				button.doClick();//keeps focus 
				popup.getDateSelector().requestFocusInWindow();
			}
	    	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	    		if(e.getSource() instanceof JButton){
	    			((JButton)e.getSource()).doClick();
	    		}
	    	} 
		}

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}
