/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce chat functionality
 *
 * @version 1.3  2004/02/13
 * @author Anthony C. Liberto
 *
 * $Log: UpdateStatus.java,v $
 * Revision 1.2  2008/01/30 16:27:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.12  2005/02/03 21:26:15  tony
 * JTest Format Third Pass
 *
 * Revision 1.11  2005/01/31 20:47:49  tony
 * JTest Second Pass
 *
 * Revision 1.10  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.9  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.8  2004/10/27 20:15:36  tony
 * improved updater by adding the ability to automatically
 * restart the e-announce application that was running.
 *
 * Revision 1.7  2004/10/14 21:27:07  tony
 * added ability to copy backups of existing updated items.
 *
 * Revision 1.6  2004/03/04 18:38:30  tony
 * updater enhancement
 *
 * Revision 1.5  2004/03/03 00:01:42  tony
 * added to functionality, moved firewall to preference.
 *
 * Revision 1.4  2004/03/01 23:48:24  tony
 * usability update.
 *
 * Revision 1.3  2004/03/01 21:05:58  tony
 * update usability.
 *
 * Revision 1.2  2004/02/24 17:08:29  tony
 * firewall enhancement
 *
 * Revision 1.1  2004/02/19 21:36:58  tony
 * e-announce1.3
 *
 *
 */
package com.ibm.eannounce.eserver;
import com.elogin.EAccessConstants;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class UpdateStatus extends JFrame implements ActionListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private JPanel pnlMain = new JPanel(new BorderLayout());
    private JPanel pnlButton = new JPanel(new BorderLayout());
    private JTextArea jtaDisplay = new JTextArea();
    private JScrollPane scroll = new JScrollPane(jtaDisplay);
    private JProgressBar progress = new JProgressBar();
    private JButton button = new JButton("OK");
//    private int iMax = 0;
    private int iCount = 0;
    private Dimension dMin = new Dimension(300, 150);

    /**
     * Constructor
     * @author Anthony C. Liberto
     */
    public UpdateStatus() {
        super("e-announce update");
        progress.setStringPainted(true);
        init();
        jtaDisplay.setEditable(false);
        jtaDisplay.setLineWrap(false);
        jtaDisplay.setWrapStyleWord(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        return;
    }

    private void init() {
        scroll.setSize(dMin);
        scroll.setPreferredSize(dMin);
        scroll.setMinimumSize(dMin);

        button.addActionListener(this);
        button.setActionCommand("ok");
        button.setMnemonic('o');
        setComplete(false);
        //		pnlButton.add("North",progress);
        pnlButton.add("South", button);
        pnlMain.add("North", scroll);
        pnlMain.add("South", pnlButton);
        getContentPane().add("Center", pnlMain);
        pack();
        jtaDisplay.append("Update Status..." + RETURN);
        center();
        return;
    }

    private void center() {
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(((sSize.width - getWidth()) / 2), ((sSize.height - getHeight()) / 2));
        return;
    }

    /**
     * setComplete
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setComplete(boolean _b) {
        button.setEnabled(_b);
        return;
    }

    /**
     * isComplete
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isComplete() {
        return button.isEnabled();
    }

    /**
     * setMessage
     * @param _s
     * @param _return
     * @author Anthony C. Liberto
     */
    protected void setMessage(String _s, boolean _return) {
        if (!isShowing()) {
            show();
        }
        jtaDisplay.append(_s);
        if (_return) {
            jtaDisplay.append(RETURN);
        }
        scrollToEnd();
        progress.setValue(iCount);
        ++iCount;
        return;
    }

    /**
     * getMessage
     * @return
     * @author Anthony C. Liberto
     */
    protected String getMessage() {
        return jtaDisplay.getText();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
        String action = _ae.getActionCommand();
        if (action.equals("ok")) {
            processOK();
        }
        return;
    }

    /**
     * processOK
     * @author Anthony C. Liberto
     */
    public void processOK() {
        hide();
        System.exit(0);
        return;
    }

    /**
     * setMax
     * @param _max
     * @author Anthony C. Liberto
     */
    protected void setMax(int _max) {
        progress.setMaximum(_max);
        progress.setMinimum(0);
        iCount = 0;
        return;
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        setComplete(false);
        if (!isShowing()) {
            show();
        }
        return;
    }

    /**
     * scrollToEnd
     * @author Anthony C. Liberto
     */
    public void scrollToEnd() {
        JViewport view = scroll.getViewport();
        if (view != null) {
            Dimension d = jtaDisplay.getSize();
            if (d != null) {
                view.setViewPosition(new Point(0, d.height));
            }
        }
        jtaDisplay.revalidate();
        return;
    }
}
