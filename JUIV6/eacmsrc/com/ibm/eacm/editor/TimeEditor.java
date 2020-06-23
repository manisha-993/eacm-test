//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;


import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.mw.Timer;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

import java.awt.*;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.accessibility.AccessibleContext;

/**
 * time editor for hours and minutes
 * used for dialing back profile or for restoring data or for editing TIME attributes
 * @author Wendy Stimpson
 */
//$Log: TimeEditor.java,v $
//Revision 1.3  2013/12/04 22:01:59  wendy
//get bg color from editor textfield
//
//Revision 1.2  2012/10/26 21:46:31  wendy
//update comments
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class TimeEditor extends JSpinner implements EditorComponent, EACMGlobals {
	private static final long serialVersionUID = 1L;

	private boolean displayOnly = false;
	private FormCellPanel fcp = null; // added for formeditor

	void setFormCellPanel(FormCellPanel f) {fcp=f;}
	public FormCellPanel getFormCellPanel() { return fcp;}// added for formeditor
	/**
	 * constructor
	 */
	public TimeEditor(Profile prof) {
		super(new SpinnerDateModel());
		JSpinner.DateEditor timeed = new JSpinner.DateEditor(this,"HH:mm");
		timeed.getFormat().setLenient(false);
		this.setEditor(timeed);

		//prevent invalid typed chars and push them into the spinnermodel immediately
		DefaultFormatter defForm = (DefaultFormatter)((JSpinner.DefaultEditor)this.getEditor()).getTextField().getFormatter();
		defForm.setAllowsInvalid(false);
		defForm.setCommitsOnValidEdit(true);
		defForm.setOverwriteMode(true);

		initTime(prof);

        initAccessibility("accessible.datetimeEditor");

    	// allow esc to get to the container
        ((JSpinner.DefaultEditor)this.getEditor()).getTextField().getInputMap().put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "none");
    }
	/**
	 * provide access to textfield so keylistener can be added
	 * @return
	 */
	public JFormattedTextField getTextField(){
		return ((JSpinner.DefaultEditor)this.getEditor()).getTextField();
	}

    private void initTime(Profile prof){
	    Calendar timeCalendar = Calendar.getInstance();

		if (Utils.isPast(prof)) { // profile already dialed back in time
			String valon = prof.getValOn();
			// "yyyy-MM-dd-HH.mm.ss.SSSSSS"
			int year = Integer.parseInt(valon.substring(0,4));
			int mon = Integer.parseInt(valon.substring(5,7));
			int day = Integer.parseInt(valon.substring(8,10));
			int hr = Integer.parseInt(valon.substring(11,13));
			int min = Integer.parseInt(valon.substring(14,16));
			timeCalendar.set(year, mon, day, hr, min);
		}else{
			timeCalendar.set(2010, 1, 1, 23, 59); // only hrs and mins matter
		}

		// must use a date object in this control
	    setValue(new Date(timeCalendar.getTimeInMillis()));
    }

    /**
     * get the hours and minutes from the editor for using with PDH timestamps
     * @return
     */
    public String getFullTime() {
		Date date = (Date)getValue();
	    Calendar timeCalendar = Calendar.getInstance();
	    timeCalendar.setTime(date);

		return String.format(Locale.ENGLISH,"%1$TH.%1$TM", timeCalendar)+Timer.EOD_SECONDS;
    }

    /**
     * get the hours and minutes from the editor for using with cell editor
     * @return
     */
    public String getTime() {
		Date date = (Date)getValue();
	    Calendar timeCalendar = Calendar.getInstance();
	    timeCalendar.setTime(date);

		return String.format(Locale.ENGLISH,"%1$TH:%1$TM", timeCalendar);
    }
    public Color getBackground() {
		if(this.getTextField()==null){
			return super.getBackground();
		}

		return this.getTextField().getBackground();
    }
    public Color getForeground() {
    	if (!isDisplayOnly()){
    		return Color.black;
    	}
    	return super.getForeground();
    }

    /**
     * setDisplayOnly
     *
     * @param _b
     */
    public void setDisplayOnly(boolean _b) {
        displayOnly = _b;
        if (displayOnly) {
            setEnabled(false);
            setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));
        } else {
        	setEnabled(true);
        	setBorder(UIManager.getBorder("TextField.border"));
        }
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
     */
    public void dereference() {
    	fcp = null;
        initAccessibility(null);
        removeAll();
        setUI(null);
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

}
