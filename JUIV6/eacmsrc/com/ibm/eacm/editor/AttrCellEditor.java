//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.ColorPref;
import com.ibm.eacm.table.RSTTable;
import com.ibm.eacm.table.RSTTableModel;

import COM.ibm.eannounce.objects.*;

/**
 * base class for all attribute cell editors
 * @author Wendy Stimpson
 */
//$Log: AttrCellEditor.java,v $
//Revision 1.7  2013/10/17 17:06:14  wendy
//prevent scroll back to top after delete
//
//Revision 1.6  2013/09/23 19:28:36  wendy
//check for special rule
//
//Revision 1.5  2013/08/16 19:08:47  wendy
//recognize keypad chars to launch editor
//
//Revision 1.4  2013/08/14 16:44:34  wendy
//Dont look at greater rule in max length check
//
//Revision 1.3  2013/07/26 16:36:18  wendy
//fix greater validation
//
//Revision 1.2  2013/05/22 11:47:02  wendy
//limit leading 0 for numeric only attrs
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public abstract class AttrCellEditor extends AbstractCellEditor implements TableCellEditor, PasteEditor, EACMGlobals
{
	private static final long serialVersionUID = 1L;
    private static final String SPEC_CHAR = "!@#$%^&*()_-+=|]}[{';:?/>.<,\""; //copied from AlphaNumberRuleException because it is not public

	protected EANAttribute attr=null;
	protected EANMetaAttribute metaAttr = null;
	private static final int clickCountToStart =2;
	protected int viewRow=-1; 
	protected int viewColumn=-1;
	protected boolean inSearch = false;
	protected RSTTable rstTable = null;
	
	public abstract FormCellPanel getFormCellPanel();// added for formeditor
	/**
	 * spell check is starting, remove the doc listeners
	 */
	public void spellCheckStarted(){}
	/**
	 *  spell check is ending, restore the doc listeners
	 */
	public void spellCheckEnded(){}

	/**
	 * @param search
	 */
	public AttrCellEditor(boolean search){ // used when table is in search dialog
		inSearch = search;
	}
	
	/**
	 * get the component used for the edit control
	 * @return
	 */
	public abstract Component getComponent();

	/**
	 * get the item selected - used in vert and grid edit for copy
	 * @return
	 */
	public Object getSelection() { 
		return getAttribute();
	}
	
	/**
	 * used for overriding keybindings and use EACM copy and paste actions
	 * @param act
	 * @param keystroke
	 */
	public void registerEACMAction(EACMAction act, KeyStroke keystroke){}
	   /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){}
    /**
     * perform the cut action
     */
    public void cut(){}
    
	/**
	 * can this event launch an editor?
	 * table calls it to determine if it should get the cell lock
	 * celleditors call it to verify editability
	 * @param anEvent
	 * @return
	 */
	public static boolean canLaunchEditorForEvent(EventObject anEvent)
	{
		boolean isok=false;
		if (anEvent instanceof MouseEvent) { 
			isok= ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
		}else if (anEvent instanceof KeyEvent) { 
			int keyCode = ((KeyEvent)anEvent).getKeyCode();
			if(((KeyEvent)anEvent).getModifiers() !=0){
				// ctrl-enter  opens the editor
				if(((KeyEvent)anEvent).isControlDown() && KeyEvent.VK_ENTER==keyCode){
					isok=true;
				}else if(((KeyEvent)anEvent).isShiftDown()){
					isok=true;
				}
			}else if(keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || 
					keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z ||
					keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9){
				isok=true;
			}else if(keyCode==KeyEvent.VK_SPACE){ // allow space to launch editor
				isok=true;
			}

		}
		return isok;
	}
	/**
	 * called by JTable class to see if it should initiate the editing process
	 * if false, the editor component will not be installed
	 * @see javax.swing.AbstractCellEditor#isCellEditable(java.util.EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		boolean isDelete = false;
		boolean isok = true;
		if(anEvent!=null){ // if null this was called from a paste or delete action operation
			isok = canLaunchEditorForEvent(anEvent);
		}else{
			// this is a paste or delete operation, get the lock if not in search
			if(!inSearch && rstTable!=null){
				isok = rstTable.getCellLock(viewRow, viewColumn);
				if(isok){
					// getting the celllock reclassifies the entity, check if attribute is still editable
					isok = rstTable.isCellEditable(viewRow, viewColumn) && 
						rstTable.getModel().isCellEditable(rstTable.convertRowIndexToModel(viewRow), 
							rstTable.convertColumnIndexToModel(viewColumn));
				}
			}
		}

		if (isok && !inSearch && rstTable!=null){
			// editCellAt() got the lock if possible.. was it successful?
			isok = isCellLocked();
		}
		
		if (anEvent instanceof KeyEvent) { 
			int code = ((KeyEvent)anEvent).getKeyCode();
			// also support delete
			isDelete =(KeyEvent.VK_DELETE==code);
			if(isok){
				preloadKeyEvent((KeyEvent)anEvent);
			}
		}

		if(inSearch && isDelete){
			// deactivate the attribute
			isok = false; // make sure the editor isnt opened
			rstTable.setValueAt(null, viewRow, viewColumn);
			//rstTable.updateTable(false); // refresh any classifications - this scrolls back to top
			((RSTTableModel)rstTable.getModel()).fireTableRowsUpdated(rstTable.convertRowIndexToModel(viewRow),rstTable.convertColumnIndexToModel(viewColumn));
			rstTable.resizeCells(); // must account for any flags	
		}
		
		return isok;
	}
	
	/**
	 * does this cell have the lock - need to override lock check for editors that can be view only
	 * @return
	 */
	protected boolean isCellLocked(){
		// editCellAt() got the lock if possible.. was it successful?
		return rstTable.isCellLocked(viewRow, viewColumn);
	}

    /**
     * derived classed can preload a key event
     * @param anEvent
     */
    protected void preloadKeyEvent(KeyEvent anEvent){}
    
	/**
	 * editor may be running in view only mode
	 * @return
	 */
	protected boolean isCellEditable(){
		boolean ok = false;
		if(rstTable!=null){
			ok = rstTable.isCellLocked(viewRow, viewColumn) &&
				rstTable.getModel().isCellEditable(
					rstTable.convertRowIndexToModel(viewRow), 
					rstTable.convertColumnIndexToModel(viewColumn));
		}else{
			FormCellPanel fcp = getFormCellPanel();
			if(fcp!=null){
				ok = !fcp.isDisplayOnly();
			}
		}
		return ok;
	}
	/**
	 * provide access to the table so locks can be checked
	 * @param rsttable
	 * @param row
	 * @param column
	 */
	public void setTable(RSTTable rsttable,int r, int col){
		rstTable = rsttable;
		viewRow = r;
		viewColumn=col;
	}
	/**
	 * when spellcheck runs in a jtable, there are no cell listeners after the edit ends, so
	 * force a load now
	 */
	public void updateSpellCheckedCell(){
		if(rstTable!=null){
	        rstTable.setValueAt(getCellEditorValue(), viewRow, viewColumn);
		}
	}
	/**
	 * set the attribute to use for this editor execution
	 * @param ean
	 */
	public void setAttribute(EANAttribute ean) {
		attr = ean;
		metaAttr = null; // reset this
		if (attr != null) {
			metaAttr =attr.getMetaAttribute();
		}
	}

	/**
	 * were any changes just made in this editor
	 * @return
	 */
	protected boolean hasNewChanges(){
		Object obj = getCellEditorValue();
		Object orig = getAttribute().get();
		String curvalue="";
		String origvalue="";
		if(obj !=null){
			if(obj instanceof MetaFlag[]){
				MetaFlag[] mf = (MetaFlag[])obj;
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<mf.length; i++){
					if(mf[i].isSelected()){
						sb.append(mf[i].getFlagCode());
					}
				}
				curvalue = sb.toString();
				if(orig !=null){
					//get original value
					mf = (MetaFlag[])orig;
					sb = new StringBuilder();
					for (int i=0; i<mf.length; i++){
						if(mf[i].isSelected()){
							sb.append(mf[i].getFlagCode());
						}
					}
					origvalue = sb.toString();
				}
			}else{
				curvalue = obj.toString();
				origvalue=orig.toString();
			}
		}

		return !origvalue.equals(curvalue);
	}
	//===============added for using in a form
	public EANAttribute getAttribute(){
		return attr;
	}
	//====================================
	/**
	 * release memory
	 */
	public void dereference(){
		attr=null;
		metaAttr=null;
		rstTable = null;
	}

    /**
     * determine background color based on meta requirements
     * @param s
     * @return
     */
    protected Color getBackground(String s) {
    	Color bgcolor = ColorPref.getOkColor();
        boolean bRequired = isRequired();
        boolean bLessThanMin = isLessThanMin(s);
        if (bRequired && bLessThanMin) {
        	bgcolor = ColorPref.getLowRequiredColor();
        } else if (bRequired) {
        	bgcolor = ColorPref.getRequiredColor();
        } else if (bLessThanMin) {
        	bgcolor =  ColorPref.getLowColor();
        } 
  
        return bgcolor;
    }
	private boolean isLessThanMin(String s) {
		boolean b = false;
		int minLen = getMinLen();
		if (minLen > 0 && (s == null || s.length() < minLen)) {
			b= true;
		}
		return b;
	}
	
	/**
	 * verify any length requirements - called when editor is done
	 * @param str
	 * @param showMsg
	 * @return
	 */
	protected boolean checkLength(String str,boolean showMsg){
		boolean ok = true;
		if(str==null){
			str="";
		}
    	int len = str.length();
		StringBuffer msgSb = new StringBuffer();
    	if(isRequired() && len==0){
    		// error must have a value
    		//msg23030 = A value is required.
			//com.ibm.eacm.ui.UI.showErrorMessage(getComponent(),Utils.getResource("msg23030",attr.getAttributeCode()));
			msgSb.append(Utils.getResource("msg23030"));
			ok = false;
    	}
    	if(ok){
    		if(getMinLen()>len){
    			// error not long enough
    			ok = false;
    			//msg23030.min = Minimum Length of {0} not met.
    			msgSb.append(Utils.getResource("msg23030.min",getMinLen()));
    			//msgSb.append("Minimum Length of " + getMinLen() + " not met.");
    		}
    		if (getEqualsLen()!=0 && getEqualsLen()!=len){
    			// error must match length
    			ok = false;
    			if(msgSb.length()>0){
    				msgSb.append(NEWLINE);
    			}
    			//msg23030.exact = Exact Length of {0} not met.
    			msgSb.append(Utils.getResource("msg23030.exact",getEqualsLen()));
    			//msgSb.append("Exact Length of " + getEqualsLen() + " not met.");
    		}
    		if (getMaxLen()!=0 && getMaxLen()<len){
    			// error too long
    			ok = false;
      			if(msgSb.length()>0){
    				msgSb.append(NEWLINE);
    			}
      			
      			//msgSb.append("Maximum Length of " + getMaxLen() + " was exceeded.");	
      			//msg23030.max = Maximum Length of {0} was exceeded.
      			msgSb.append(Utils.getResource("msg23030.max",getMaxLen()));
    		}

    		if(hasGreater()){
    			// assumption is that this is a numeric only attribute
    			int greaterValue = getGreaterValue();
    			try { 
    				int curVal = Integer.valueOf(str).intValue(); 
    				if (greaterValue>=curVal) { 
    					if(msgSb.length()>0){
    						msgSb.append(NEWLINE);
    					}
    					//msg23030.greater = Greater than Validation (value must be > {0}).
    					msgSb.append(Utils.getResource("msg23030.greater",greaterValue));
    					ok = false;
    				} 
    			} catch (NumberFormatException nfe) { 
    				nfe.printStackTrace(); 
    				if(msgSb.length()>0){
    					msgSb.append(NEWLINE);
    				}
    				ok = false;
    				//msg23030.greater = Greater than Validation (value must be > {0}).
					msgSb.append(Utils.getResource("msg23030.greater",greaterValue));
    				//msgSb.append("Greater than Validation (value must be > " + greaterValue + ").");
    			}
    		}
    	}
		if(!ok && showMsg){
			//metaRuleErr = Attribute {0} failed the following rules:
			String errheader = Utils.getResource("metaRuleErr",attr.getAttributeCode())+ NEWLINE;
			com.ibm.eacm.ui.UI.showErrorMessage(getComponent(),errheader+msgSb.toString());  
		}
    	return ok;
    }
	 
	/**
	 * validate characters as user types them in
	 * 
	 * @param curStr
	 * @param addedStr
	 * @return
	 */
	protected boolean charValidation(String curStr, String addedStr){
		StringBuffer msgSb = new StringBuffer();
		// check for blanks in the newly added string
		boolean ok = checkBlanks(addedStr);
		if (ok){
			// make sure max length has not been exceeded
			ok = checkMaxLength(curStr+addedStr, msgSb);
			
			//verify numeric or decimal format, must look at entire strings for multiple '.'
			ok &= checkNumericOrDecimal(curStr+addedStr, msgSb);
			
			// look at added string for integer
			ok &= checkIntAlpha(addedStr, msgSb);
			if (!ok){
    			//metaRuleErr = Attribute {0} failed the following rules:
    			String errheader = Utils.getResource("metaRuleErr",attr.getAttributeCode())+ NEWLINE;
	 			//String errheader = "Attribute " + attr.getAttributeCode() + " failed the following rules:" + NEWLINE;
    			com.ibm.eacm.ui.UI.showErrorMessage(getComponent(),errheader+msgSb.toString());  
			}
		}
		return ok;
	}
	/**
	 * verify numeric or decimal format 
	 * @param str this is the complete string, must look for duplicate '.'
	 * @param msgSb
	 * @return
	 */
	private boolean checkNumericOrDecimal(String str,StringBuffer msgSb){
		boolean ok = true;
        if(isDecimal() || isNumeric()){
            StringCharacterIterator sci = new StringCharacterIterator(str);
            char ch = sci.first();
            boolean dotFnd = false;
            while(ch != CharacterIterator.DONE) {
                switch(ch)
                {
                case '.':
                    if(dotFnd){ // can only have one
                    	ok = false;
                    	updateErrMsg(msgSb,(isDecimal()?"Decimal":"Numeric")+
            					" Validation: cannot have multiple decimal points");
                    }
                    dotFnd = true;
                    break;
                case '+':
                case '-':
                	if (isDecimal() || sci.getIndex()!=0){ // cant have + or - any place else
                    	ok = false;
                    	updateErrMsg(msgSb,(isDecimal()?"Decimal":"Numeric")+
            					" Validation: invalid character");
                	}
                    break;
                default:
                	if(!Character.isDigit(ch)){
                    	ok = false;
                    	updateErrMsg(msgSb,(isDecimal()?"Decimal":"Numeric")+
            					" Validation: cannot have characters");
                	}
                }
                ch = sci.next();
            }
        }
        return ok;
	}
	private void updateErrMsg(StringBuffer msgSb, String errMsg){
		if(msgSb.indexOf(errMsg) ==-1){ // only output the errmsg once
			if(msgSb.length()>0){
				msgSb.append(NEWLINE);
			}
			msgSb.append(errMsg);
		}
	}
	/**
	 * look for blanks if NOBLANKS is in the meta
	 * @param str this is the added string
	 * @return
	 */
	private boolean checkBlanks(String str){
		boolean ok = true;
		if (metaAttr != null && metaAttr.isNoBlanks()) {
			StringCharacterIterator sci = new StringCharacterIterator(str);
			char ch = sci.first();

			while(ch != CharacterIterator.DONE) {
				if (Character.isSpaceChar(ch)) {
					//msg24017 = No Blanks are allowed in this attribute.
					com.ibm.eacm.ui.UI.showErrorMessage(getComponent(),Utils.getResource("msg24017"));
					ok = false;
					break;
				}
				ch = sci.next();
			}
		} 

		return ok;
	}
	
	/**
	 * verify INTEGER and ALPHA in the meta
	 * @param str this is the added string
	 * @param msgSb
	 * @return
	 */
	private boolean checkIntAlpha(String str, StringBuffer msgSb){
		boolean ok = true;
		if (isInteger() || isUpper() || isAlpha()) {
			StringCharacterIterator sci = new StringCharacterIterator(str);
			char ch = sci.first();

			while(ch != CharacterIterator.DONE) {
				if(isSpecial()){
					if (SPEC_CHAR.indexOf(ch + "") != -1) { 
						ch = sci.next();
						continue;
					}
				}
				if(isInteger()){ // integer is allowed
					if(Character.isDigit(ch)) { // character is an integer
						ch = sci.next();
						continue;
					}
					if (!isAlpha()){ // and alpha is not allowed
						ok = false;
						updateErrMsg(msgSb,"Integer Validation: cannot have characters");
					}	
				}
				
				if (isUpper()) {
					if (!(Character.isLetter(ch) || Character.isSpaceChar(ch))) { 
						ok = false;
						if(isSpecial() && !Character.isLetter(ch)){
							updateErrMsg(msgSb,"Alpha Validation: (Special): '"+ch+"'");
						}else{
							updateErrMsg(msgSb,"Alpha Validation: (Upper): '"+ch+"'");
						}
					}
				} else if (isAlpha()) {
					if (!(Character.isLetter(ch) || Character.isSpaceChar(ch))) {
						ok = false;
						updateErrMsg(msgSb,"Alpha Validation: (Alpha): '"+ch+"'");
					}
				}
	            
				ch = sci.next();
			}
		} 

		return ok;
	}
    protected boolean isAlpha() {
    	boolean isAlpha = false;
		if (metaAttr != null) {
			isAlpha = metaAttr.isAlpha();
		}
        return isAlpha;
    }
    
	private boolean isSpecial() {
		boolean isSpec = false;
		if (metaAttr != null) {
			isSpec = metaAttr.isSpecial();
		}
		return isSpec;
	}
    
    private boolean isUpper() {
    	boolean isUpper = false;
		if (metaAttr != null) {
			isUpper = metaAttr.isUpper();
		}
        return isUpper;
    }
	protected boolean isInteger() {
		boolean isInt = false;
		if (metaAttr != null) {
			isInt = metaAttr.isInteger();
		}
		return isInt;
	}
	
	private boolean isDecimal() {
		boolean isDec = false;
		if (metaAttr != null) {
			isDec = metaAttr.isDecimal();
		}
		return isDec;
	}
	protected boolean isNumeric() {
		boolean isNum = false;
		if (metaAttr != null) {
			isNum=metaAttr.isNumeric();
		}
		return isNum; 
	}
	/**
	 * verify max length requirements - called when user is keying in text
	 * @param str
	 * @return
	 */
	private boolean checkMaxLength(String str,StringBuffer msgSb){
		boolean ok = true;
		int len = str.length();
		if (getMaxLen()!=0 && getMaxLen()<len){
			// error too long
			ok = false;
			if(msgSb.length()>0){
				msgSb.append(NEWLINE);
			}

			//msgSb.append("Maximum Length of " + getMaxLen() + " was exceeded.");	
  			//msg23030.max = Maximum Length of {0} was exceeded.
  			msgSb.append(Utils.getResource("msg23030.max",getMaxLen()));
		}

		return ok;
	}	
	/**
	 * Attribute must have a value
	 * meta has EXIST rule
	 * @return
	 */
	private boolean isRequired() {
		boolean req = false;
		if (!inSearch && attr != null) {
			req =  attr.isRequired();
		}
		return req;
	}
	
	/**
	 * Attribute has a minimum length
	 * meta has MIN rule or EQU rule
	 * @return
	 */
	private int getMinLen() {
		int eqLen = 0;
		if(!inSearch){
			eqLen = getEqualsLen();
			if (eqLen == 0 && metaAttr!=null) {
				eqLen = metaAttr.getMinLen();
			}
		}
		return eqLen;
    }
    /**
	 * Attribute has a maximum length
	 * meta has MAX rule
     * @return
     */
    private int getMaxLen() {
        int eqLen = getEqualsLen();
        if (eqLen == 0 && metaAttr!=null) {
            eqLen = metaAttr.getMaxLen();
        }
        
        return eqLen;
    }
	/**
	 * Attribute has a equal length
	 * meta has EQU rule
	 * @return
	 */
    private int getEqualsLen() {
		int len = 0;
		if (!inSearch && metaAttr!=null){
			len = metaAttr.getEqualsLen();
		} 
		return len;
    }
	/**
	 * Attribute has a greater value
	 * meta has GREATER rule, the value must be greater than this
	 * @return
	 */
    private boolean hasGreater() { 
    	boolean greater = false;
    	if (metaAttr != null) {
    		greater= metaAttr.isGreater();
    	} 
    	return greater;
    } 
    private int getGreaterValue() { 
    	int greater = -1;
        if (metaAttr != null) {
        	greater = metaAttr.getGreater(); 
        } 
        return greater;
    } 
}
