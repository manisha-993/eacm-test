/**
 * Copyright (c) 2004-2005 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2005/01/20
 * @author Anthony C. Liberto
 *
 * $Log: EProgress.java,v $
 * Revision 1.2  2008/01/30 16:27:11  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:47:05  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:13  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.2  2005/01/26 17:18:51  tony
 * JTest Formatting modifications
 *
 * Revision 1.1  2005/01/20 23:05:42  tony
 * *** empty log message ***
 *
 *
 */
package com.ibm.eannounce.progress;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class EProgress extends JDialog {
	private static final long serialVersionUID = 1L;
	private EPanel pMain = new EPanel(new BorderLayout());
    private EPanel pTask = new EPanel(new BorderLayout());

    private ELabel lTask = new ELabel("Task being monitored");
    private JProgressBar pbTask = new JProgressBar();
    private String strLockProcess = null;

    /**
     * Constructor
     * @param _frame
     * @param _title
     * @author Anthony C. Liberto
     */
    public EProgress(JFrame _frame, String _title) {
        super(_frame, _title);
        init();
        setModal(false);
        setResizable(false);
        return;
    }

    private void init() {
        pTask.add("North", lTask);
        pTask.add("South", pbTask);

        pMain.add("North", pTask);
        getContentPane().add("Center", pMain);
        setIndeterminate(true);
        pack();
        return;
    }

    /**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    }

    /**
     * center
     * @author Anthony C. Liberto
     */
    public void center() {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((sSize.width - getWidth()) / 2), ((sSize.height - getHeight()) / 2));
        return;
    }

    /*
     task
     */
    /**
     * setMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setMessage(String _s) {
        lTask.setText(_s);
        pack();
        return;
    }

    /**
     * setMinimum
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMinimum(int _i) {
        pbTask.setMinimum(_i);
    }

    /**
     * setMaximum
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMaximum(int _i) {
        pbTask.setMaximum(_i);
    }

    /**
     * setValue
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setValue(int _i) {
        pbTask.setValue(_i);
    }

    /**
     * setIndeterminate
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setIndeterminate(boolean _b) {
        pbTask.setIndeterminate(_b);
    }

    /*
     locking
     */
    /**
     * lock
     * @param _str
     * @author Anthony C. Liberto
     */
    public void lock(String _str) {
        if (_str != null) {
            strLockProcess = new String(_str);
        }
        return;
    }

    /**
     * unlock
     * @param _str
     * @author Anthony C. Liberto
     */
    public void unlock(String _str) {
        if (_str != null) {
            if (_str.equals(strLockProcess)) {
                strLockProcess = null;
            }
        }
        return;
    }

    /**
     * isLocked
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isLocked() {
        return strLockProcess != null;
    }
}
