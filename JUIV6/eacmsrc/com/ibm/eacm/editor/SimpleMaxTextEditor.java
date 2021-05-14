//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.editor;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.preference.ColorPref;

/** RQ110306297
 * This is needed to support max length when a user is creating flags
 *@author Wendy Stimpson
 *
 */
//$Log: SimpleMaxTextEditor.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class SimpleMaxTextEditor extends SimpleTextCellEditor implements EACMGlobals {
	private static final long serialVersionUID = 1L;

	/**
	 * simpleTextEditor with a maximum length
	 */
	public SimpleMaxTextEditor(DocumentListener dl) {
		super(new MaxText(),dl);
	}
	/**
	 * set max length
	 * @param len int with a maximum length
	 */
	public void setMaxLen(int len){
		((MaxText)getComponent()).setMaxLen(len);
	}

	/**
	 * set attribute code, used for error msgs
	 * @param s EANMetaAttribute
	 */
	public void setMetaAttr(EANMetaAttribute s){
		((MaxText)getComponent()).setMetaAttr(s);
	}
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setTextAttribute(SimpleTextAttribute _ean) {
		if(docListener != null){
			((JTextField)getComponent()).getDocument().removeDocumentListener(docListener);
		}
		((MaxText)getComponent()).setDisplay(_ean.toString());
		if(docListener != null){
			((JTextField)getComponent()).getDocument().addDocumentListener(docListener);
		}
	}
	/***************
	 * 
	 * used by the cell editor to display the values
	 *
	 */
	private static class MaxText extends JTextField {
		private static final long serialVersionUID = 1L;
		private int maxLen=0;
		private boolean byPassValidation = false;
		private EANMetaAttribute metaAttr = null;
		/**
		 * set attribute code, used for error msgs
		 * @param s EANMetaAttribute
		 */
		void setMetaAttr(EANMetaAttribute s){
			metaAttr = s;
		}
		/**
		 * set max length
		 * @param len int with a maximum length
		 */
		void setMaxLen(int len){
			maxLen = len;
		}

		void setDisplay(String txt){
			byPassValidation = true;
			setText(txt);
			byPassValidation = false;
		}
		//must do it like this or List in renderer doesnt have it
		public Color getBackground() {
			return ColorPref.getOkColor();  
		}    

		/**
		 * called by jre, max len validation
		 *
		 * @return Document
		 */
		protected Document createDefaultModel() {
			return new MaxDoc();
		}

		private class MaxDoc extends PlainDocument {
			private static final long serialVersionUID = 1L;
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null) {
					return;
				}
				if (byPassValidation) {
					super.insertString(offs, str, a);
				} else if (checkLength(MaxText.this.getText()+str)) {
					super.insertString(offs, str, a);
				}
				repaint();
			}
			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
				if (!byPassValidation) {
					if (len>0){ // something was removed
						checkLength(MaxText.this.getText());
					}
				}
				repaint();
			}
		}

		private boolean checkLength(String str){
			boolean ok = true;
			if (maxLen>0 && str.length()>maxLen){
				ok=false;
				String msg = "";
				if (metaAttr!=null){
					msg = "Attribute " + metaAttr.getAttributeCode() + " failed the following rules..." + NEWLINE;
				}
				msg = msg + "Maximum Length of " + maxLen+ " Exceeded";
				com.ibm.eacm.ui.UI.showErrorMessage(this,msg);
			}

			return ok;
		}
	}
}
