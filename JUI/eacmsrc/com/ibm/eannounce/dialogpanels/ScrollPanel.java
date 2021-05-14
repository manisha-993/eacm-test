/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: ScrollPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:43:25  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:11  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/11/13 23:16:16  tony
 * accessibility
 *
 * Revision 1.7  2003/10/29 00:21:15  tony
 * removed System.out. statements.
 *
 * Revision 1.6  2003/04/30 21:41:45  tony
 * 50460 changed JTextArea to eTextArea
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/04/14 21:55:32  joan
 * 50362
 *
 * Revision 1.3  2003/03/14 00:07:27  tony
 * added Mnemonic
 *
 * Revision 1.2  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:44  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import java.awt.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ScrollPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EButton btnOK = new EButton(getString("ok"));

    private EPanel pButton = new EPanel(new GridLayout(1, 1));
    private ETextArea jta = new ETextArea();
    private EScrollPane jsp = new EScrollPane(jta);
//    private Rectangle defRect = new Rectangle(0, 0, 1, 1);

    /**
     * scrollPanel
     * @author Anthony C. Liberto
     */
    public ScrollPanel() {
        super(new BorderLayout());
        jsp.setFocusable(false);
        jta.setEditable(false);
        init();
        return;
    }

    /**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
        Dimension d = new Dimension(400, 250);
        //50460		jta.setFocusable(false);
        pButton.add(btnOK);
        btnOK.addActionListener(this);
        btnOK.setMnemonic(getChar("ok-s"));
        add("South", pButton);
        add("Center", jsp);
        jta.setBackground(getBackground());
        jta.setBorder(null);
        jsp.setPreferredSize(d);
        jsp.setSize(d);
        accessibleize(); //accessible
        return;
    }

    /**
     * initShow
     * @author Anthony C. Liberto
     */
    public void initShow() {
        return;
    }

    /**
     * setText
     * @param _txt
     * @author Anthony C. Liberto
     */
    public void setText(String _txt) {
        jta.setText(_txt);
        jta.getAccessibleContext().setAccessibleDescription(_txt); //accessible
        jta.setCaretPosition(0);
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (jsp != null) {
            jsp.dereference();
            jsp = null;
        }
        super.dereference();
        return;
    }

    /**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
        disposeDialog();
        return;
    }

    /*
     accessible
     */

    /**
     * activateMe
     * @author Anthony C. Liberto
     */
    public void activateMe() {
        jta.requestFocus();
        return;
    }

    private void accessibleize() {
        btnOK.getAccessibleContext().setAccessibleDescription(getString("ok"));
        return;
    }
}
