/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: ETimeLabel.java,v $
 * Revision 1.2  2008/01/30 16:27:01  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:09  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.4  2005/02/03 16:38:52  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:16  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/10/14 17:25:49  tony
 * 52548
 *
 * Revision 1.4  2003/09/05 17:31:42  tony
 * 2003-09-05 memory enhancements
 *
 * Revision 1.3  2003/04/11 20:02:26  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.GridLayout;
//import java.util.Calendar;
import java.util.Date;
import javax.swing.UIManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETimeLabel extends EPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private EDatePanel date = null;

    private Date d = null;
    //private Calendar c = null;
    private Thread m_Time = null;

    private ELabel lblBack = new ELabel(eaccess().getImageIcon("wayBack.gif"));
    private ELabel lbl = new ELabel();
    private boolean bHoldTime = false; //52548

    /**
     * eTimeLabel
     *
     * @author Anthony C. Liberto
     * @param _d
     */
    public ETimeLabel(EDatePanel _d) {
        super(new GridLayout(1, 1));
        //c = Calendar.getInstance();
        date = _d;
        date.setTimeLabel(this);

        lblBack.setToolTipText(eaccess().getString("wayB"));
        lblBack.setEnabled(false);
        setBorder(UIManager.getBorder("eannounce.etchedBorder"));
        add(lbl);
        startThis();
        return;
    }

    /**
     * getLabel
     * @return
     * @author Anthony C. Liberto
     */
    public ELabel getLabel() {
        return lblBack;
    }

    private void setTime() {
        try { //22254
            String now = eaccess().getNow();
            if (now != null) { //22254
                setTime(now);
            }
        } catch (Exception _ex) { //22254
            EAccess.report(_ex,false);
        } //22254
        repaint();
        return;
    }

    /**
     * setTime
     * @param now
     * @author Anthony C. Liberto
     */
    public void setTime(String now) {
        try {
            Date dt = eaccess().parseDate(FORMAT_IN, now);
            setTime(dt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    private void setTime(Date dt) {
        d = dt;
        date.setDate(d);
        lbl.setText("  " + toTime() + "  ");
        lbl.setToolTipText(toFullTime()); //acl_20021115
        return;
    }

    /**
     * incrementTime
     * @author Anthony C. Liberto
     */
    public void incrementTime() {
        if (!bHoldTime) { //52548
            setTime(); //012639
        } //52548
        return;
    }

    /**
     * toTime
     * @return
     * @author Anthony C. Liberto
     */
    public String toTime() {
        if (d != null) {
            return eaccess().formatDate(DISPLAY_TIME, d);
        }
        //		return sdf.format(d);
        return ("No Time is set");
    }

    /**
     * toFullTime
     * @return
     * @author Anthony C. Liberto
     */
    public String toFullTime() { //acl_20021115
        if (d != null) { //acl_20021115
            return eaccess().formatDate(LONG_TIME_DISPLAY, d);
        } //acl_20021115
        return null; //acl_20021115
    } //acl_20021115

    /**
     * toDate
     * @return
     * @author Anthony C. Liberto
     */
    public String toDate() {
        if (d != null) {
            return eaccess().formatDate(FORMAT_IN, d);
        }
        return "No Date is set";
    }

    /**
     * StartThis
     * @author Anthony C. Liberto
     */
    public void startThis() {
        if (m_Time == null) {
            m_Time = new Thread(this);
            m_Time.start();
        }
        return;
    }

    /**
     * StopThis
     * @author Anthony C. Liberto
     */
    public void stopThis() {
        if (m_Time != null) {
            m_Time = null;
        }
        return;
    }

    /**
     * setRunning
     * @param _present
     * @author Anthony C. Liberto
     */
    public void setRunning(boolean _present) {
        if (_present && m_Time == null) {
            startThis();

        } else if (!_present && m_Time != null) {
            stopThis();
        }
        return;
    }

    /**
     * @see java.lang.Runnable#run()
     * @author Anthony C. Liberto
     */
    public void run() {
        Thread thisThread = Thread.currentThread();
        int r = -1;
        while (m_Time == thisThread) {
            int s = (60 * 1000); //sleep for one minute
            if (d == null) {
                setTime(); //goToServer
                r = Integer.valueOf(eaccess().formatDate(LONG_SECOND, d)).intValue(); //remaining seconds
                s = ((60 - r) * 1000); //sleep for remainder of current minute
            } else {
                incrementTime(); //generateOnOwn
                r = Integer.valueOf(eaccess().formatDate(LONG_SECOND, d)).intValue(); //remaining seconds
                s = ((60 - r) * 1000); //sleep for remainder of current minute
            }
            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;
    }
    /*
    private String getProcessingTime() {
        return "PROCESSING_TIME";
    }

    private String getCurrentTime() {
        incrementTime();
        return toDate();
    }

    private String getNewProcessingTime() {
        return "NEW_PROCESSING_TIME";
    }
*/
    /**
     * isCurrentProcessDate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCurrentProcessDate() {
        return !lblBack.isEnabled();
    }

    /**
     * getPanelType
     * @author Anthony C. Liberto
     * @return String
     */
    public String getPanelType() {
        return TYPE_ETIMELABEL;
    }
    /*
     52548
     */
    /**
     * setHoldTime
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setHoldTime(boolean _b) {
        bHoldTime = _b;
        return;
    }
}
