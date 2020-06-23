//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.*;

import COM.ibm.opicmpdh.middleware.Profile;


import com.ibm.eacm.EACM;
import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.mw.Timer;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.tabs.TabPanel;

/**
 * this is the status bar
 * @author Wendy Stimpson
 */
// $Log: StatusToolbar.java,v $
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//
public class StatusToolbar extends JToolBar implements EACMGlobals, PropertyChangeListener
{
	private static final long serialVersionUID = 1L;
	private static final String LONG_TIME_DISPLAY = "hh:mm a 'middleware server time'";

	private InfoIconPanel infoPane = null;
    private boolean holdTime = false; // if true, date has been dialed back and time should not be updated
	private JLabel roleLabel = new JLabel();
	private JPanel rolePane = new JPanel(new BorderLayout(0,0));
	private JLabel versLabel = new JLabel();
	private JPanel versPane = new JPanel(new BorderLayout(0,0));
	private JLabel lngLabel = new JLabel();
	private JPanel lngPane = new JPanel(new BorderLayout(0,0));

	private JLabel dateLabel = new JLabel();
	private JLabel timeLabel = new JLabel();

	/**
     * constructor - instantiated when eacmframe is built
     * used when the first profile is selected
     */
    public StatusToolbar() {
		infoPane = new InfoIconPanel();

		init();
		setFloatable(false);
		setDoubleBuffered(true);
		setOrientation(JToolBar.HORIZONTAL);
		setAlignmentX(JToolBar.BOTTOM_ALIGNMENT);

		Date newdate = DateRoutines.parseDate(FORMAT_IN, RMIMgr.getRmiMgr().getTimer().getNowWithTime());
		updateDateTime(newdate); // set the initial date and time

		//look for timer notifications
		RMIMgr.getRmiMgr().getTimer().addPropertyChangeListener(Timer.TIMER_UPDATE,this);
	}

	private void init() {
		rolePane.setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
		rolePane.add(roleLabel,BorderLayout.WEST);
		versPane.setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
		versPane.add(versLabel,BorderLayout.WEST);
		lngPane.setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
		lngPane.add(lngLabel,BorderLayout.WEST);

		dateLabel.setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
		timeLabel.setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));

		add(versPane);
		add(lngPane);
		add(rolePane);
		add(infoPane);

		add(dateLabel);
		add(timeLabel);
	    // render as multilines
		versLabel.setToolTipText("<html>"+Routines.convertToHTML(Utils.getResource("copyright"))+"</html>");
		versLabel.setText(EACMProperties.getProperty("eannounce.version"));
	}
	/**
	 * release memory
	 */
	public void dereference(){
		RMIMgr.getRmiMgr().getTimer().removePropertyChangeListener(Timer.TIMER_UPDATE,this);
		
		infoPane.dereference();
		infoPane = null;

		roleLabel.removeAll();
		roleLabel.setUI(null);
		roleLabel = null;
		rolePane.removeAll();
		rolePane.setUI(null);
		rolePane = null;
		versLabel.removeAll();
		versLabel.setUI(null);
		versLabel = null;
		versPane.removeAll();
		versPane.setUI(null);
		versPane = null;
		lngLabel.removeAll();
		lngLabel.setUI(null);
		lngLabel = null;
		lngPane.removeAll();
		lngPane.setUI(null);
		lngPane = null;

		dateLabel.removeAll();
		dateLabel.setUI(null);
		dateLabel = null;
		timeLabel.removeAll();
		timeLabel.setUI(null);
		timeLabel = null;
		
		removeAll();
		setUI(null);
	}
	/* (non-Javadoc)
	 * called by rmi timer
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(Timer.TIMER_UPDATE)){
			if (!holdTime){ // date is not dialed back
				updateDateTime((Date)event.getNewValue());
			}
		}
	}

	/**
     * setFilter status
     * @param _b
     */
    public void setFilterStatus(boolean _b){
		infoPane.setFilterEnabled(_b);
	}
	/**
     * setHidden status
     * @param _b
     */
    public void setHiddenStatus(boolean _b) {
		infoPane.setHiddenEnabled(_b);
	}
	/**
     * setPast
     * time/date and icons should reflect current tab
     */
    public void setPastStatus() {
    	TabPanel tab = EACM.getEACM().getCurrentTab();
    	if(tab.getProfile()==null){
    		return;
    	}
    	String time = tab.getProfile().getValOn();
    	holdTime = tab.isDateDialedBack();
    	
    	infoPane.setPastDateEnabled(holdTime);

    	Date newdate = null;
    	if (!holdTime){
    		newdate = DateRoutines.parseDate(FORMAT_IN, RMIMgr.getRmiMgr().getTimer().getNowWithTime());
    	}else{
    		if (time!=null){
    			newdate = DateRoutines.parseDate(FORMAT_IN, time);
    		}
    	}
    	if(newdate!=null){
    		updateDateTime(newdate);
    	}
	}

    /**
     * update date and time fields
     * @param newdate
     */
    private void updateDateTime(Date newdate){
		dateLabel.setText("  " + DateRoutines.formatDate(FORMATTED_DATE,newdate) + "  ");
		dateLabel.setToolTipText(DateRoutines.formatDate(DISPLAY_DATE,newdate));

		timeLabel.setText("  " + DateRoutines.formatDate(DISPLAY_TIME,newdate) + "  ");
		timeLabel.setToolTipText(DateRoutines.formatDate(LONG_TIME_DISPLAY,newdate));

		if(isShowing()){
			repaint();
		}
    }

    /**
     * called when nls changes or active profile is changed
     * @param prof
     */
    public void updateStatus(Profile prof){
    	lngLabel.setText(prof.getReadLanguage().toString());
        // render as multilines
    	lngLabel.setToolTipText("<html>"+Routines.convertToHTML(Utils.getNLSInfo(prof.getReadLanguage()))+"</html>");

        roleLabel.setText(prof.toString());
        // render as multilines
        roleLabel.setToolTipText("<html>"+Routines.convertToHTML(Utils.getProfileInfo(prof))+"</html>");
    }
}
