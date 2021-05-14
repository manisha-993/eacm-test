//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.ui;

import javax.swing.*;
import javax.swing.text.*;

import com.ibm.eacm.objects.Utils;

/**
 *  create a textbox that only allows for the entry of integers
 *  @author Wendy Stimpson
 */
// $Log: IntField.java,v $
// Revision 1.2  2014/01/31 20:11:41  wendy
// correct max value check
//
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public class IntField extends JTextField
{
	private static final int MAX_INT_LEN = 10;
	private static final long serialVersionUID = 1L;
	private String errTitle = "integer.err-title";
	private static final String MAX_VAL_STR = Integer.toString(Integer.MAX_VALUE);

    /**
     * create a new intfield with max number of columns
     *Integer.MAX_VALUE= 2147483647
     * @param col 
     */
    public IntField() {
        this(MAX_INT_LEN);
    }
    public IntField(int col) {
        super(Math.min(col,MAX_INT_LEN)); //Integer cannot exceed 10 digits
        
        ((AbstractDocument)getDocument()).setDocumentFilter(new IntDocumentFilter()); 
    }

    public void setErrTitle(String s){errTitle = s;}
    
    
    private class IntDocumentFilter extends DocumentFilter {       
    	public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) 
    	throws BadLocationException { 
            if ((fb.getDocument().getLength() + text.length()) <= getColumns()) {
            	if (charValidation(text)) {
            		if(validateInt(fb.getDocument().getLength(),offset,text)){
            			fb.insertString(offset, text, attr); 
            		}
            	}
            } else {
                UIManager.getLookAndFeel().provideErrorFeedback(null);
                lengthExceeded();
            }
    	} 

    	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,	String text, AttributeSet attrs) 
    	throws BadLocationException { 
    		int curLen = fb.getDocument().getLength();
    		if(text!=null){
    			curLen += (text.length()-length);
    		}

    		if(curLen<= getColumns()) {
    			if (charValidation(text)) {
    				if(validateInt(fb.getDocument().getLength(),offset,length,text)){
    					fb.replace(offset, length, text, attrs); 
    				}
    			}
    		}else {
    		    UIManager.getLookAndFeel().provideErrorFeedback(null);
    			lengthExceeded();
    		}
    	} 
        /**
         * validate the replaced characters are not > integer.max
         */
        private boolean validateInt(int doclen, int offs,int sellen, String text) {	
        	try {
        		int newlen = doclen;
        		if(text!=null){
        			newlen += (text.length()-sellen);

        			if (newlen== MAX_INT_LEN){
        				StringBuffer sb = new StringBuffer(getText(0,doclen));
        				sb.replace(offs, offs+sellen, text);

        				if(MAX_VAL_STR.compareTo(sb.toString())<0) {// 10 digits can exceed integer.max
        			//	if(Integer.MAX_VALUE<Double.parseDouble(sb.toString())){ //this gives scientific notation and fails
        				    UIManager.getLookAndFeel().provideErrorFeedback(null);
        					nonInteger();
        					return false;
        				}
        			}
        		}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}

            return true;
        }
        /**
         * validate the entered character to guarantee that it is an int
         */
        private boolean charValidation(String in) {
            char[] chr = in.toCharArray();
            int x = chr.length;
            for (int i = 0; i < x; ++i) {
                if (!Character.isDigit(chr[i])) {
                    UIManager.getLookAndFeel().provideErrorFeedback(null);
                    nonDigit();
                    return false;
                }
            }
      
            return true;
        }
        /**
         * validate the inserted characters are not > integer.max
         */
        private boolean validateInt(int doclen, int offs,String str) {	
        	try {
        		int newlen = (doclen + str.length());
        		if (newlen== MAX_INT_LEN){
           			StringBuffer sb = new StringBuffer(getText(0,doclen));
        			if (offs<doclen){
        				sb.insert(offs, str);
        			}else{
        				sb.append(str);
        			}
        			
    				if(MAX_VAL_STR.compareTo(sb.toString())<0) {// 10 digits can exceed integer.max
        			//if(Integer.MAX_VALUE<Double.parseDouble(sb.toString())){ // 10 digits can exceed integer.max
        			    UIManager.getLookAndFeel().provideErrorFeedback(null);
        				nonInteger();
        				return false;
        			}
        		}

			} catch (BadLocationException e) {
				e.printStackTrace();
			}

            return true;
        }
    } 
 
    /**
     * dereference
     */
    public void dereference() {
    	errTitle = null;
        removeAll();
        setUI(null);
    }

    private void nonInteger() {
    	com.ibm.eacm.ui.UI.showMessage(this,errTitle, JOptionPane.ERROR_MESSAGE, "information-acc", 
    			Utils.getResource("msg5021.2"));
    }
    /**
     * nonDigit
     */
    private void nonDigit() {
    	com.ibm.eacm.ui.UI.showMessage(this,errTitle, JOptionPane.ERROR_MESSAGE, "information-acc", 
    			Utils.getResource("msg5021.0"));
    }

    /**
     * lengthExceeded
     */
    private void lengthExceeded() {
    	com.ibm.eacm.ui.UI.showMessage(this,errTitle, JOptionPane.ERROR_MESSAGE, "information-acc", 
    			Utils.getResource("msg5021.1"));
    }

}
