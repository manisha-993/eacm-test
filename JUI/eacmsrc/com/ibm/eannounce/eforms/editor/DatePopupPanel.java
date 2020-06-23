/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DatePopupPanel.java,v $
 * Revision 1.4  2009/06/09 19:42:56  wendy
 * BH SR-14 date warnings
 *
 * Revision 1.3  2009/05/22 14:20:39  wendy
 * Performance cleanup
 *
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:03  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.4  2005/02/01 22:06:31  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/08/21 19:43:10  tony
 * 51391
 *
 * Revision 1.3  2003/04/15 17:31:35  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.2  2003/03/04 22:34:51  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Calendar;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DatePopupPanel extends EPanel {
	private static final long serialVersionUID = 1L;
	private DateFormatSymbols dfs = new DateFormatSymbols();

    private String[] shortDays = dfs.getShortWeekdays();
    private String[] shortMonths = dfs.getShortMonths();

    private boolean past = false;
    private boolean future = false;
    private boolean warningDate = false; // BH SR-14

    private static final int MONTH_LABEL = 0;
    private static final int YEAR_LABEL = 1;

    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;

    private int displayMonth = 0;
    private int displayYear = 0;

    /**
     * lbl
     */
    private ELabel[] lbl = new ELabel[9];
    /**
     * btnUtil
     */
    private EDateLabel[] btnUtil = new EDateLabel[4];
    /**
     * btnDays
     */
    private EDateLabel[] btnDays = new EDateLabel[42];

    private static final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar curCal = Calendar.getInstance();
    private Calendar tmpCal = Calendar.getInstance();
    private MouseListener ml = null;

    /**
     * datePopupPanel
     * @param _ml
     * @author Anthony C. Liberto
     */
    private DatePopupPanel(MouseListener _ml) {
        super(new GridLayout(8, 7, 0, 0));
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        ml = _ml;
        init();
    }

    /**
     * datePopupPanel
     * @param _ml
     * @param _past
     * @param _future
     * @param _resetCalendar
     * @author Anthony C. Liberto
     */
    protected DatePopupPanel(MouseListener _ml, boolean _past, boolean _future, boolean _resetCalendar) {
        this(_ml);
        setPast(_past);
        setFuture(_future);
        if (_resetCalendar) {
            resetCalendar();
        }
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    private void init() {
        initializeLabels();
        initializeButtons();
        //row1
        add(btnUtil[0]);
        add(lbl[0]);
        add(btnUtil[1]);
        add(new JLabel());
        add(btnUtil[2]);
        add(lbl[1]);
        add(btnUtil[3]);
        //row2
        add(lbl[2]);
        add(lbl[3]);
        add(lbl[4]);
        add(lbl[5]);
        add(lbl[6]);
        add(lbl[7]);
        add(lbl[8]);
        //row3
        add(btnDays[0]);
        add(btnDays[1]);
        add(btnDays[2]);
        add(btnDays[3]);
        add(btnDays[4]);
        add(btnDays[5]);
        add(btnDays[6]);
        //row4
        add(btnDays[7]);
        add(btnDays[8]);
        add(btnDays[9]);
        add(btnDays[10]);
        add(btnDays[11]);
        add(btnDays[12]);
        add(btnDays[13]);
        //row5
        add(btnDays[14]);
        add(btnDays[15]);
        add(btnDays[16]);
        add(btnDays[17]);
        add(btnDays[18]);
        add(btnDays[19]);
        add(btnDays[20]);
        //row6
        add(btnDays[21]);
        add(btnDays[22]);
        add(btnDays[23]);
        add(btnDays[24]);
        add(btnDays[25]);
        add(btnDays[26]);
        add(btnDays[27]);
        //row7
        add(btnDays[28]);
        add(btnDays[29]);
        add(btnDays[30]);
        add(btnDays[31]);
        add(btnDays[32]);
        add(btnDays[33]);
        add(btnDays[34]);
        //row8
        add(btnDays[35]);
        add(btnDays[36]);
        add(btnDays[37]);
        add(btnDays[38]);
        add(btnDays[39]);
        add(btnDays[40]);
        add(btnDays[41]);
    }

    private void initializeButtons() {
        int ii = btnUtil.length;
        int xx = -1;
        for (int i = 0; i < ii; ++i) {
            switch (i) {
            case 0 :
                btnUtil[i] = new EDateLabel("<<");
                btnUtil[i].setActionCommand("PREV_MONTH");
                break;
            case 1 :
                btnUtil[i] = new EDateLabel(">>");
                btnUtil[i].setActionCommand("NEXT_MONTH");
                break;
            case 2 :
                btnUtil[i] = new EDateLabel("<<");
                btnUtil[i].setActionCommand("PREV_YEAR");
                break;
            case 3 :
                btnUtil[i] = new EDateLabel(">>");
                btnUtil[i].setActionCommand("NEXT_YEAR");
                break;
			default:
				break;
            }
            btnUtil[i].addMouseListener(ml);
        }
        xx = btnDays.length;
        for (int x = 0; x < xx; ++x) {
            btnDays[x] = new EDateLabel();
            btnDays[x].addMouseListener(ml);
        }
    }

    private void initializeLabels() {
        int ii = lbl.length;
        for (int i = 0; i < ii; ++i) {
            switch (i) {
            case 0 :
                lbl[i] = new ELabel();
                break;
            case 1 :
                lbl[i] = new ELabel();
                break;
            default :
                lbl[i] = new ELabel(shortDays[i - 1]);
                break;
            }
            lbl[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private boolean isToday(int _day) {
        if (currentYear == displayYear) {
            if (currentMonth == displayMonth) {
                if (currentDay == _day) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * setDays
     * @author Anthony C. Liberto
     */
    protected void setDays() {
        setDays(displayYear, displayMonth, -1);
    }

    /**
     * setFuture
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setFuture(boolean _b) {
        future = _b;
    }

    /**
     * isFuture
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isFuture() {
        return future;
    }

    /**
     * setPast
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setPast(boolean _b) {
        past = _b;
    }
    /**
     * BH SR-14
     * @param _b
     */
    protected void setWarning(boolean _b) {
        warningDate = _b;
    }
    /**
     * isPast
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPast() {
        return past;
    }
    /**
     * BH SR-14
     * @return
     */
    public boolean isWarning() {
        return warningDate;
    }

    private boolean isFuture(int _day) {
        if (isFuture()) {
            return eaccess().getDateRoutines().isFutureDate(getSelectedDate(_day), System.getProperty("mw.now.date"));
        }
        return false;
    }

    private boolean isPast(int _day) {
        if (isPast()) {
            return eaccess().getDateRoutines().isPastDate(getSelectedDate(_day), System.getProperty("mw.now.date"));
        }
        return false;
    }

    private void setDays(int _year, int _month, int _today) {
        int startDay = -1;
        int maxDays = -1;
        int currentDate = 1;
     
        lbl[MONTH_LABEL].setText(shortMonths[_month]);
        lbl[YEAR_LABEL].setText(Integer.toString(_year));

        curCal.set(_year, _month, 1);
        startDay = curCal.get(Calendar.DAY_OF_WEEK) - 1;
        maxDays = curCal.getActualMaximum(Calendar.DATE);

        for (int i = 0; i < btnDays.length; ++i) {
            if (i < startDay) {
                btnDays[i].setText("");
                btnDays[i].setActionCommand("");
                btnDays[i].setEnabled(false);
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
                    	if(isWarning()){ // BH SR-14
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
                btnDays[i].setText("");
                btnDays[i].setActionCommand("");
                btnDays[i].setEnabled(false);
                if (btnDays[i].isCurrent()) {
                    btnDays[i].setCurrent(false);
                }
            }
        }
        revalidate();
        repaint();
    }

    /**
     * resetCalendar
     * @author Anthony C. Liberto
     */
    protected void resetCalendar() {
        Calendar today = Calendar.getInstance();
        currentYear = today.get(Calendar.YEAR);
        displayYear = currentYear;
        currentMonth = today.get(Calendar.MONTH);
        displayMonth = currentMonth;
        currentDay = today.get(Calendar.DATE);
        setDays(currentYear, currentMonth, currentDay);
    }

    /**
     * adjustYear
     * @param _increment
     * @author Anthony C. Liberto
     */
    protected void adjustYear(int _increment) {
        displayYear += _increment;
        if (displayYear < 1980) {
            displayYear = 9999;
        } else if (displayYear > 9999) {
            displayYear = 1980;
        }
    }

    /**
     * adjustMonth
     * @param _increment
     * @author Anthony C. Liberto
     */
    protected void adjustMonth(int _increment) {
        displayMonth += _increment;
        if (displayMonth > 11) {
            displayMonth = 0;
            adjustYear(_increment);
        } else if (displayMonth < 0) {
            displayMonth = 11;
            adjustYear(_increment);
        }
    }

    /**
     * getSelectedDate
     * @param _day
     * @return
     * @author Anthony C. Liberto
     */
    protected String getSelectedDate(String _day) {
        return getSelectedDate(Integer.valueOf(_day).intValue());
    }

    /**
     * getSelectedDate
     * @param _day
     * @return
     * @author Anthony C. Liberto
     */
    private String getSelectedDate(int _day) {
        tmpCal.set(displayYear, displayMonth, _day);
        return yyyymmdd.format(tmpCal.getTime());
    }

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        for (int i = 0; i < 4; ++i) {
            btnUtil[i].removeMouseListener(ml);
            btnUtil[i].removeAll();
            remove(btnUtil[i]);
            btnUtil[i] = null;
        }
        for (int i = 0; i < 42; ++i) {
            btnDays[i].removeMouseListener(ml);
            btnDays[i].removeAll();
            remove(btnDays[i]);
            btnDays[i] = null;
        }
        for (int i = 0; i < 9; ++i) {
            lbl[i].removeAll();
        }
        ml = null;
        removeAll();
    }

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
        return TYPE_DATEPANEL;
    }
}
