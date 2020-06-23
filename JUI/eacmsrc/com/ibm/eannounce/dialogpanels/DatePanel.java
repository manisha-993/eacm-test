/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: DatePanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:10  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/09 18:55:23  tony
 * Scout Accessibility
 *
 * Revision 1.5  2005/02/04 15:22:08  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/11/19 18:01:50  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/11/25 22:29:56  tony
 * accessibility
 *
 * Revision 1.8  2003/10/20 23:22:31  tony
 * acl_20031020
 * updated datepanel to prevent popup position error.
 *
 * Revision 1.7  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.6  2003/06/17 20:50:13  tony
 * added mnemonic and default button for 1.2h
 *
 * Revision 1.5  2003/05/30 21:09:19  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.4  2003/04/11 20:02:28  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.ETimeDate;
import java.awt.*;
import java.util.Date;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DatePanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EButton btnOK = null;
    private EButton btnCncl = null;

    private ETimeDate td = null;
    private Date d = null;

    private JPanel pSSouth = new JPanel(new GridLayout(1, 2, 10, 10));
    private JPanel pSouth = new JPanel(new BorderLayout());

    /**
     * datePanel
     * @author Anthony C. Liberto
     */
    public DatePanel() {
        super(new BorderLayout());
        td = new ETimeDate();
        td.clearDateBorder();
        init();
        add("West", td.getDateEditor());
        add("East", td.getTimeEditor());
        pSouth.add("North", td.getDatePopupPanel());
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        btnOK = new EButton(getString("ok"));
        btnCncl = new EButton(getString("cncl"));
        btnOK.setActionCommand("ok");
        btnCncl.setActionCommand("cncl");
        btnOK.addActionListener(this);
        btnCncl.addActionListener(this);
        btnOK.setMnemonic(getChar("ok-s")); //acl_20030617
        btnCncl.setMnemonic(getChar("cncl-s")); //acl_20030617
        pSSouth.add(btnOK);
        pSSouth.add(btnCncl);
        pSouth.add("South", pSSouth);
        add("South", pSouth);
        return;
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        if (_action.equals("cncl")) {
            setDate(null);
            disposeDialog();
        } else if (_action.equals("ok")) {
            setBusy(true);
            setDate(td.getProcessDate());
            disposeDialog();
        } else if (_action.equals("exit")) {
            disposeDialog();
        }
        return;
    }

    /**
     * setDate
     * @param _date
     * @author Anthony C. Liberto
     */
    public void setDate(String _date) {
        if (_date == null) {
            d = null;
            return;
        }
        d = parse(_date);
        return;
    }

    /**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
        return null;
    }

    /**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {
    }
    /**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {
    }

    /**
     * parse
     * @param _date
     * @return
     * @author Anthony C. Liberto
     */
    public Date parse(String _date) {
        return eaccess().parseDate(ISO_DATE, _date);
    }

    /**
     * setDisplayDate
     * @param _date
     * @author Anthony C. Liberto
     */
    public void setDisplayDate(String _date) {
        td.setDate(_date);
        return;
    }

    /**
     * getDate
     * @return
     * @author Anthony C. Liberto
     */
    public String getDate() {
        td.getDatePopupPanel().setVisible(!EAccess.isAccessible());
        show(this, this, true);
        return eaccess().formatDate(ISO_DATE, d);
    }

    /**
     * hideMe
     * @author Anthony C. Liberto
     */
    public void hideMe() {
        if (td != null) {
            td.hidePopup();
        }
        return;
    }

    /**
     * set
     * @param _i
     * @param _future
     * @param _past
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void set(int _i, boolean _future, boolean _past, boolean _reset) {
        if (td != null) {
            td.setDateValidatorType(_i);
            td.setFutureDate(_future, false);
            td.setPastDate(_past, false);
            if (_reset) {
                td.resetCalendar();
            }
        }
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        btnOK.removeActionListener(this);
        btnCncl.removeActionListener(this);

        pSSouth.removeAll();
        pSSouth.removeNotify();

        pSouth.removeAll();
        pSouth.removeNotify();

        btnOK.removeAll();
        btnOK.removeNotify();

        btnCncl.removeAll();
        btnCncl.removeNotify();

        d = null;
        td.dereference();
        td = null;

        super.dereference();
    }

    /**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {
    }
    /**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
        return getString("date.panel");
    }
    /*
     51296
     */
    /**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
        getRootPane().setDefaultButton(btnOK);
        td.getDateEditor().setCaretPosition(0);
        return;
    }

}
