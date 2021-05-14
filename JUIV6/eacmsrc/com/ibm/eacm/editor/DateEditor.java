//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.text.*;
import javax.swing.text.MaskFormatter;
import java.util.*;
import javax.accessibility.AccessibleContext;

/**
 * date editor textfield - use it in a DateCellEditor and do the popup and button there
 * @author Wendy Stimpson
 */
//$Log: DateEditor.java,v $
//Revision 1.5  2013/10/08 21:12:47  wendy
//make sure cursor is visible if launched with a keystroke
//
//Revision 1.4  2013/08/14 16:58:05  wendy
//paste has cell listener to support type after paste
//
//Revision 1.3  2013/07/30 17:38:03  wendy
//dont overwrite char when editor is opened with keystroke
//
//Revision 1.2  2013/07/26 15:34:33  wendy
//move caret to position 0 if no date exists
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class DateEditor extends JFormattedTextField implements PropertyChangeListener, EACMGlobals, ChangeListener
{
	public static final String NO_VALUE="____-__-__";
	
	public static enum DateType {
		FUTURE_DATE,
		PAST_DATE,
		FUTURE_WARNING_DATE,
		PAST_WARNING_DATE,
		ANY_DATE
	};

	private static final long serialVersionUID = 1L;
	private DateType datetype = DateType.ANY_DATE;

	private boolean giveupfocus = false;
	private boolean displayOnly = false;

	private SimpleDateFormat sdf = null;
	private DateVerifier objDateVerifier;
	private MaskFormatter objMask = null;
	private String initvalue=DateEditor.NO_VALUE;  // hang onto any initial value, if it would cause a warning, avoid it
	private boolean showErrorMsg = true;
	private boolean forceCaret0 = false;

	/**
	 * constructor
	 */
	public DateEditor() {
		this(DateType.ANY_DATE,null);
	}
	
	/**
	 * constructor
	 * @param dt
	 * @param prof
	 */
	public DateEditor(DateType dt, Profile prof) {
		setMaskFormatter();

		setDateType(dt);
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);

		setFocusLostBehavior(PERSIST);
		objDateVerifier = new DateVerifier();
		setInputVerifier(objDateVerifier);

		if(prof!=null){
			setText(prof.getValOn());
		}else{
			setText(DateRoutines.getEOD());
		}

		initAccessibility("accessible.datetimeEditor");

		// allow esc to get to the container
		getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none"); 
		
		//must be able to move caret to pos 0 if editor is opened with mouseclick and has no date yet
		this.getCaret().addChangeListener(this);
	}
	
	/**
	 * @return
	 */
	public DateType getDateType() {return datetype;}
	
	/**
	 * @param dt
	 */
	public void setDateType(DateType dt){
		datetype = dt;
		if(isFuture()){
			setToolTipText(Utils.getToolTip("dateeditor.future"));
		}else if(isPast()){
			setToolTipText(Utils.getToolTip("dateeditor.past"));
		}else{
			setToolTipText(Utils.getToolTip("dateeditor"));
		}
	}

	/**
	 * set a numeric mask date format
	 */
	private void setMaskFormatter() {
		try {
			objMask = new MaskFormatter();
			objMask.setMask("####-##-##");
			objMask.setPlaceholderCharacter('_');
			objMask.install(this);
		} catch (ParseException pe) {
			pe.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals(DateSelector.NEWDATE_PROPERTY))	{
			setSelectedDate((String)event.getNewValue());
		}
	}
	
	/**
	 * verify this is a valid date syntax - used from paste - past and prev warnings will be generated when editor is
	 * closed
	 * @return
	 */
	public boolean isValidDateSyntax(){
		boolean valid = objDateVerifier.isDateSyntaxValid(getText());
		if(!valid && objDateVerifier.errMsgCode!=null){
			String msg = Utils.getResource(objDateVerifier.errMsgCode);
			if(objDateVerifier.errMsgCode.equals("msg50000")){
				msg = Utils.getResource(objDateVerifier.errMsgCode,getDate());
			}
			UIManager.getLookAndFeel().provideErrorFeedback(this);
			com.ibm.eacm.ui.UI.showErrorMessage(this, msg);
	
			objDateVerifier.errMsgCode = null;
		}
		return valid;
	}
	
	/**
	 * verify this is a valid date 
	 * @return
	 */
	public boolean isValidDate(){
		return isValidDate(true);
	}

	/**
	 * verify this is a valid date
	 * @param showMsg
	 * @return
	 */
	public boolean isValidDate(boolean showMsg){
		showErrorMsg = showMsg;
		setInputVerifier(null); // must turn off verifer when focus is lost because verify may put up a dialog
		boolean b = objDateVerifier.verify(this); 
		setInputVerifier(objDateVerifier);
		showErrorMsg = true;
		return b;
	}

	/**
	 * getBackground
	 *
	 * @return
	 */
	public Color getBackground() {
		if (!isDisplayOnly()){
			return Color.white;
		}
		return super.getBackground();
	}

	/**
	 * setSelectedDate - this is only called by the calendar control
	 * @param _date
	 */
	protected void setSelectedDate(String _date) {
		setText(_date); 
		requestFocusInWindow();
	}

	/**
	 * @return
	 */
	public String getDate(){
		return getText();
	}

	/**
	 * setDisplay
	 * @param _s
	 */
	public void setDisplay(String t) {
		super.setText(t);
		initvalue = t;
		//if editor is opened with a mouse click, caret is positioned there.. so move it back
		// to pos 0 if no date exists
		forceCaret0 = t.length()==0;
	}


	/**
	 * setDisplayOnly
	 *
	 * @param _b
	 */
	public void setDisplayOnly(boolean _b) {
		displayOnly = _b;
		setBorder(getCurrentBorder());
		setEditable(!displayOnly);
	}

	/**
	 * isDisplayOnly
	 *
	 * @return
	 */
	public boolean isDisplayOnly() {
		return displayOnly;
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		getCaret().removeChangeListener(this);
		
		initAccessibility(null);

		removeAll();
		setUI(null);
		objMask.install(null);
		objMask = null;
		
		datetype = DateType.ANY_DATE;

		sdf = null;
		objDateVerifier = null;
		initvalue = null;
	}

	private Border getCurrentBorder() {
		if (isDisplayOnly()) {
			return UIManager.getBorder(EMPTY_BORDER_KEY);
		} 

		return UIManager.getBorder("TextField.border");
	}

	/**
	 * initAccessibility
	 * @param _s
	 */
	private void initAccessibility(String _s) {
		AccessibleContext ac = getAccessibleContext();
		if (ac != null) {
			if (_s == null) {
				ac.setAccessibleName(null);
				ac.setAccessibleDescription(null);
			} else {
				String strAccessible = Utils.getResource(_s);
				ac.setAccessibleName(strAccessible);
				ac.setAccessibleDescription(strAccessible);
			}
		}
	}

	private boolean isWarning(){
		return datetype== DateEditor.DateType.FUTURE_WARNING_DATE || datetype==DateEditor.DateType.PAST_WARNING_DATE;
	}
	private boolean isFuture(){
		return datetype== DateEditor.DateType.FUTURE_WARNING_DATE || datetype==DateEditor.DateType.FUTURE_DATE;
	}
	private boolean isPast(){
		return datetype== DateEditor.DateType.PAST_WARNING_DATE || datetype==DateEditor.DateType.PAST_DATE;
	}
	
	/**
	 * must support bypass of loss of focus checks
	 */
	public void giveUpFocus(){
		giveupfocus = true;
	}

	class DateVerifier extends InputVerifier {
		private Calendar tmpCal = Calendar.getInstance();
		private String errMsgCode = null;
		private String warnMsg = null;
		
		/**
		 * only check date syntax, not past or future
		 * @param p_Date
		 * @return
		 */
		private boolean isDateSyntaxValid(String p_Date) {
			errMsgCode = null;
			try {
				int placeholderid = p_Date.indexOf(objMask.getPlaceholderCharacter());
				if (placeholderid!=-1){
					//msg50000 = Invalid date specified.
					errMsgCode = "msg50000";
					return false;
				}
				// this validates it is a valid date - not 0000-00-00
				// but fails if p_Date ends in '_'
				Date date = sdf.parse(p_Date);
				tmpCal.setTime(date);

				if (tmpCal.get(Calendar.YEAR)<1980){ // must be at least 1980
					//msg50005 = Date must be at least 1980-01-01
					errMsgCode = "msg50005";
					return false;
				}
				return true;
			} catch (ParseException dfe) {
				//msg50000 = Invalid date specified.
				errMsgCode = "msg50000";
				return false;
			}
		}

		/**
		 * check date syntax as well as any past or future date warnings
		 * @param p_Date
		 * @return
		 */
		private boolean isDateValid(String p_Date) {
			try {
				int placeholderid = p_Date.indexOf(objMask.getPlaceholderCharacter());
				if (placeholderid!=-1){
					//msg50000 = Invalid date specified.
					errMsgCode = "msg50000";
					return false;
				}
				// this validates it is a valid date - not 0000-00-00
				// but fails if p_Date ends in '_'
				Date date = sdf.parse(p_Date);
				tmpCal.setTime(date);

				if (tmpCal.get(Calendar.YEAR)<1980){ // must be at least 1980
					//msg50005 = Date must be at least 1980-01-01
					errMsgCode = "msg50005";
					return false;
				}

				if(isFuture()&& DateRoutines.isFutureDate(p_Date, DateRoutines.getToday())){
					//msg50001 = Date must be in the future.
					if(!isWarning()){
						errMsgCode = "msg50001";
					}else{
						//msg50004 = Warning: {0} is not in the future.  Do you want to continue?
						warnMsg = Utils.getResource("msg50004",p_Date);
					}
					return false;
				}
				if(isPast()&& DateRoutines.isPastDate(p_Date, DateRoutines.getToday())){
					//msg50002 = Date must be in the past.
					if(!isWarning()){
						errMsgCode = "msg50002";
					}else{
						//msg50003 =Warning: {0} is not in the past.  Do you want to continue?
						warnMsg = Utils.getResource("msg50003",p_Date);
					}
					return false;
				}

				return true;
			} catch (ParseException dfe) {
				//msg50000 = Invalid date specified.
				errMsgCode = "msg50000";
				return false;
			}
		}

		public boolean shouldYieldFocus(JComponent input) {
			if (giveupfocus){ // dont go into verify code, used for day button presses or popup button in dialog
				giveupfocus = false;
				return true;
			}
			return verify(input);
		}
		  
		@Override
		public boolean verify(JComponent input) {
			try {
				if (input instanceof JFormattedTextField) {
					JFormattedTextField jtf = (JFormattedTextField) input;
					if (initvalue.equals(jtf.getText()) ||
							NO_VALUE.equals(jtf.getText())){
						jtf.commitEdit();
						return true;
					}else if (isDateValid(jtf.getText())) {
						try {
							jtf.commitEdit();
							return true;
						} catch (ParseException e) {
							jtf.selectAll();
							return false;
						}
					} else {
						if(warnMsg!=null){
							jtf.setInputVerifier(null);
							Component comp = input; // use DateCellEditor if it is the parent
							if(input.getParent() instanceof JPanel){
								comp = input.getParent(); // use ResetDateDialog or RestoreActionTab
							}
							int r = com.ibm.eacm.ui.UI.showConfirmYesNo(comp,warnMsg);
							
							warnMsg = null;
							if (r==YES_BUTTON){ 
								jtf.commitEdit();
								jtf.setInputVerifier(this);
								return true; 
							}
							
							jtf.setText(initvalue);
						}

						if(errMsgCode!=null){
							Component comp = input; // use DateCellEditor if it is the parent
							if(input.getParent() instanceof JPanel){
								comp = input.getParent(); // use ResetDateDialog or RestoreActionTab
							}
							String msg = Utils.getResource(errMsgCode);
							if(errMsgCode.equals("msg50000")){
								msg = Utils.getResource(errMsgCode,getDate());
							}
							if(showErrorMsg){
								com.ibm.eacm.ui.UI.showErrorMessage(comp, msg);
							}
							errMsgCode = null;
						}
						jtf.selectAll();
						jtf.setInputVerifier(this);
						return false;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(forceCaret0){
			forceCaret0 = false;
			requestFocusInWindow(); // cursor not visible if launched with a keystroke
			if(getCaret().getDot() ==0){ // already at position0, so user started with a keystroke
				return;
			}

			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					setCaretPosition(0);
				}
			});
		}
	}
}
