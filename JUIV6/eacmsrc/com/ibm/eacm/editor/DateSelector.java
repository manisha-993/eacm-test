//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.event.KeyListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.ibm.eacm.editor.DateEditor.DateType;
import com.ibm.eacm.objects.DateRoutines;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;
/**
 * panel used in calendar popup
 * @author Wendy Stimpson
 */
//$Log: DateSelector.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class DateSelector extends JPanel implements FocusListener, EACMGlobals {
	private static final long serialVersionUID = 1L;
	public static final String NEWDATE_PROPERTY="DateSelector";

	private static final String PREVYEAR = "PREV_YEAR";
	private static final String NEXTYEAR = "NEXT_YEAR";
	private static final String NEXTMONTH = "NEXT_MONTH";
	private static final String PREVMONTH = "PREV_MONTH";

	private static final Border NORMAL =  new EmptyBorder(2,2,2,2);
	private static final Border LOWERED = UIManager.getBorder(LOWERED_BORDER_KEY);

	private static final int MONTH_LABEL = 0;
	private static final int YEAR_LABEL = 1;

	private ButtonListener dateButtonListener = new ButtonListener();

	private String[] shortDays = null;
	private String[] shortMonths = null;

	private DateEditor.DateType datetype = DateEditor.DateType.ANY_DATE;
	/*
    public static enum DateEditor.DateType {
    	FUTURE_DATE,
    	PAST_DATE,
    	FUTURE_WARNING_DATE,
    	PAST_WARNING_DATE,
    	ANY_DATE
    };
	 */

	private DateEditor dateEditor = null;
	private int currentYear = 0;
	private int currentMonth = 0;
	private int currentDay = 0;

	private int displayMonth = 0;
	private int displayYear = 0;

	private JLabel[] lbl = new JLabel[9]; // days of the week with 2 empty labels

	private DateButton[] btnUtil = new DateButton[4];
	private DateButton[] btnDays = new DateButton[42];

	private Calendar tmpCal = Calendar.getInstance();
	private KeyListener keyListener = null;


	/**
	 * construct a date panel with the current date
	 * used in resetdate dialog
	 * @param dt
	 */
	public DateSelector(DateEditor.DateType dt) {
		this(dt,null);
	}
	/**
	 * used in popups
	 * @param dt
	 * @param kl
	 */
	public DateSelector(DateEditor.DateType dt,KeyListener kl) {
		super(new GridLayout(8, 7, 0, 0));

		keyListener = kl;
		setBorder(UIManager.getBorder(FOCUS_BORDER_KEY));
		init();

		setDateType(dt);

		addFocusListener(this);
	}

	/**
	 * @param dt
	 */
	protected void setDateType(DateType dt){
		datetype = dt;
		resetCalendar();
	}

	/* (non-Javadoc)
	 * keep tabbing within this panel
	 * @see java.awt.Container#isFocusCycleRoot()
	 */
	public boolean isFocusCycleRoot() {
		return true;
	}

	/**
	 * build panel with month, year and days of week
	 */
	private void init() {
		DateFormatSymbols dfs = new DateFormatSymbols();
		shortDays = dfs.getShortWeekdays();
		shortMonths = dfs.getShortMonths();
		dfs = null;

		initializeLabels();
		initializeButtons();
		//row1
		add(btnUtil[0]);
		add(lbl[MONTH_LABEL]);
		add(btnUtil[1]);
		add(new JLabel());
		add(btnUtil[2]);
		add(lbl[YEAR_LABEL]);
		add(btnUtil[3]);
		//row2 - days of the week labels
		for (int i=2; i<lbl.length; i++){
			add(lbl[i]);
		}
		//row3-8
		for (int i=0; i<btnDays.length; i++){
			add(btnDays[i]);
		}
	}

	private void initializeButtons() {
		for (int i = 0; i < btnUtil.length; ++i) {
			switch (i) {
			case 0 :
				btnUtil[i] = new DateButton("<<");
				btnUtil[i].setActionCommand(PREVMONTH);
				break;
			case 1 :
				btnUtil[i] = new DateButton(">>");
				btnUtil[i].setActionCommand(NEXTMONTH);
				break;
			case 2 :
				btnUtil[i] = new DateButton("<<");
				btnUtil[i].setActionCommand(PREVYEAR);
				break;
			case 3 :
				btnUtil[i] = new DateButton(">>");
				btnUtil[i].setActionCommand(NEXTYEAR);
				break;
			default:
				break;
			}
		}

		for (int x = 0; x < btnDays.length; ++x) {
			btnDays[x] = new DateButton();
		}
	}

	private void initializeLabels() {
		for (int i = 0; i < lbl.length; ++i) {
			switch (i) {
			case MONTH_LABEL :
			case YEAR_LABEL :
				lbl[i] = new JLabel();
				break;
			default :
				lbl[i] = new JLabel(shortDays[i - 1]);
			break;
			}
			lbl[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	private boolean isToday(int _day) {
		return (currentYear == displayYear && currentMonth == displayMonth && currentDay == _day);
	}

	/**
	 * reset days to changed year or month
	 */
	private void resetDays() {
		setDays(displayYear, displayMonth);
	}

	private boolean isFuture(int _day) {
		if (isFuture()) {
			return DateRoutines.isFutureDate(getSelectedDate(_day), DateRoutines.getToday());
		}
		return false;
	}

	private boolean isPast(int _day) {
		if (isPast()){
			return DateRoutines.isPastDate(getSelectedDate(_day), DateRoutines.getToday());
		}
		return false;
	}

	private void setDays(int _year, int _month) {
		int startDay = -1;
		int maxDays = -1;
		int currentDate = 1;

		lbl[MONTH_LABEL].setText(shortMonths[_month]);
		lbl[YEAR_LABEL].setText(Integer.toString(_year));

		tmpCal.set(_year, _month, 1); // set it to the first day of the month
		startDay = tmpCal.get(Calendar.DAY_OF_WEEK) - 1;
		maxDays = tmpCal.getActualMaximum(Calendar.DATE);

		btnUtil[0].setEnabled(true);
		btnUtil[1].setEnabled(true);
		btnUtil[2].setEnabled(true);
		btnUtil[3].setEnabled(true);

		if (datetype == DateEditor.DateType.FUTURE_DATE){
			//disable year
			if(currentYear >= displayYear){
				btnUtil[2].setEnabled(false);
				//disable month
				if(currentMonth >= displayMonth){
					btnUtil[0].setEnabled(false);
				}
			}
		}

		if (datetype == DateEditor.DateType.PAST_DATE){
			//disable year
			if(currentYear <= displayYear){
				btnUtil[3].setEnabled(false);
				//disable month
				if(currentMonth <= displayMonth){
					btnUtil[1].setEnabled(false);
				}
			}
		}

		for (int i = 0; i < btnDays.length; ++i) {
			if (i < startDay) {
				btnDays[i].clear();
			} else if (currentDate <= maxDays) {
				String day = Integer.toString(currentDate);
				btnDays[i].setText(day);
				btnDays[i].setActionCommand(day);
				if (isToday(currentDate)) {
					btnDays[i].setCurrent(true);
					btnDays[i].setEnabled(true);
				} else {
					btnDays[i].setCurrent(false);
					if (isFuture(currentDate) || isPast(currentDate)) {
						if(isWarning()){
							btnDays[i].setWarning();
							btnDays[i].setEnabled(true);
						}else{
							btnDays[i].setEnabled(false);
						}
					} else {
						btnDays[i].setEnabled(true);
					}
				}
				++currentDate;
			} else {
				btnDays[i].clear();
			}
		}
		revalidate();
		repaint();
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
	 * update the date panel with the current date
	 */
	private void resetCalendar() {
		Calendar today = Calendar.getInstance();
		displayYear = currentYear = today.get(Calendar.YEAR);
		displayMonth = currentMonth = today.get(Calendar.MONTH);
		currentDay = today.get(Calendar.DATE);
		setDays(currentYear, currentMonth);
	}

	/**
	 * change year by specified increment
	 * @param _increment
	 */
	private void adjustYear(int _increment) {
		displayYear += _increment;
		if (displayYear < 1980) { // wrapped
			displayYear = 9999;
		} else if (displayYear > 9999) { // wrapped
			displayYear = 1980;
		}
	}

	/**
	 * change month by specified increment
	 * @param _increment
	 */
	private void adjustMonth(int _increment) {
		displayMonth += _increment;
		if (displayMonth > 11) { // wrapped
			displayMonth = 0;
			adjustYear(_increment);
		} else if (displayMonth < 0) { // wrapped
			displayMonth = 11;
			adjustYear(_increment);
		}
	}

	/**
	 * get date that corresponds to this day on the calendar
	 * @param _day
	 * @return
	 */
	private String getSelectedDate(int _day) {
		tmpCal.set(displayYear, displayMonth, _day);
		return String.format(Locale.ENGLISH,"%1$TY-%1$Tm-%1Td", tmpCal);
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		for (int i = 0; i < btnUtil.length; ++i) {
			btnUtil[i].dereference();
			btnUtil[i] = null;
		}
		btnUtil = null;
		for (int i = 0; i < btnDays.length; ++i) {
			btnDays[i].dereference();
			btnDays[i] = null;
		}
		btnDays = null;
		for (int i = 0; i < lbl.length; ++i) {
			lbl[i].removeAll();
			lbl[i] = null;
		}
		lbl = null;

		removeAll();
		setUI(null);
		if (dateEditor !=null){
			removePropertyChangeListener(dateEditor);
			dateEditor = null;
		}

		keyListener = null;

		removeFocusListener(this);

		dateButtonListener = null;

		shortDays = null;
		shortMonths = null;

		datetype = null;
		tmpCal = null;
	}

	/**
	 * set the editor to use with this date selector
	 * it controls the type of calendar built
	 * @param dateEdit
	 */
	public void setEditor(DateEditor dateEdit){
		dateEditor = dateEdit;
		addPropertyChangeListener(dateEditor);
		setDateType(dateEditor.getDateType());
	}

	/**
	 * get the current date from the selector panel
	 * used when user hits enter on popup
	 * @return
	 */
	public String getCurrentDate(){
		String date = null;
		for (int i=0; i<btnDays.length; i++){
			if (btnDays[i].blnCurrent){
				String action = btnDays[i].getActionCommand();
				date = getSelectedDate(Integer.parseInt(action));
				break;
			}
		}
		return date;
	}
	/**
	 *
	 */
	private class DateButton extends JButton {//needs to be button for keybd traversal
		private static final long serialVersionUID = 1L;
		private boolean blnCurrent = false;

		private DateButton() {
			this("");
		}
		private DateButton(String _string) {
			super(_string);
			setForeground(Color.black);
			setBorder(NORMAL);
			addActionListener(dateButtonListener);
			if(keyListener!=null){
				addKeyListener(keyListener);//needed to capture enter key press and activate the button
			}
		}

		private void clear(){
			setText("");
			setActionCommand("");
			setEnabled(false);
			setCurrent(false);
		}
		public void setEnabled(boolean b) {
			setFocusable(b); // prevent button that had focus, keeping it when disabled
			super.setEnabled(b);
		}

		/***
		 * must tell the date editor to allow focus to move to the button if the
		 * date editor has an invalid date in it
		 * @see javax.swing.JComponent#requestFocus()
		 */
		public void requestFocus(){
			if (dateEditor!=null && (!getActionCommand().equals(PREVYEAR) &&
					!getActionCommand().equals(NEXTYEAR) && !getActionCommand().equals(NEXTMONTH)
					&& !getActionCommand().equals(PREVMONTH))){
				dateEditor.giveUpFocus();
			}
			super.requestFocus();
		}
		/**
		 * setCurrent
		 * @param _b
		 */
		private void setCurrent(boolean _b) {
			blnCurrent = _b;
			if (blnCurrent) {
				setForeground(Color.blue);
				setBorder(LOWERED);
			} else {
				setForeground(Color.black);
				setBorder(NORMAL);
			}
		}
		/**
		 * BH SR-14
		 * Draw warning days in red
		 */
		private void setWarning() {
			setForeground(Color.red);
			setBorder(NORMAL);
		}

		/**
		 * release memory
		 */
		private void dereference() {
			removeActionListener(dateButtonListener);
			if(keyListener!=null){
				removeKeyListener(keyListener);
			}

			removeAll();
			setUI(null);
		}

		/**
		 * getCursor
		 *
		 * @return
		 */
		public Cursor getCursor() {
			return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}
	}

	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equals(PREVYEAR)) {
				adjustYear(-1);
				resetDays();
			} else if (action.equals(NEXTYEAR)) {
				adjustYear(+1);
				resetDays();
			} else if (action.equals(PREVMONTH)) {
				adjustMonth(-1);
				resetDays();
			} else if (action.equals(NEXTMONTH)) {
				adjustMonth(+1);
				resetDays();
			} else if (Routines.have(action)) {
				firePropertyChange(NEWDATE_PROPERTY,null,
						getSelectedDate(Integer.parseInt(action)));
			}
		}
	}

	public void focusGained(FocusEvent e) {
		for (int i=0; i<btnDays.length; i++){
			if (btnDays[i].blnCurrent){
				btnDays[i].requestFocusInWindow();
				break;
			}
		}
	}
	public void focusLost(FocusEvent e) {
	};
}
