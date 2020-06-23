/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: LinkPanel.java,v $
 * Revision 1.3  2009/05/26 12:29:25  wendy
 * Performance cleanup
 *
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
 * Revision 1.4  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2003/06/09 17:33:29  tony
 * 51213
 *
 * Revision 1.5  2003/05/08 19:18:30  tony
 * 50445
 *
 * Revision 1.4  2003/04/21 17:30:18  tony
 * updated Color Logic by adding edit and found color
 * preferences to appearance.
 *
 * Revision 1.3  2003/03/13 18:38:44  tony
 * accessibility and column Order.
 *
 * Revision 1.2  2003/03/07 21:40:47  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:43  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class LinkPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private EButton btnOK = new EButton(getString("ok"));
	private EButton btnDetails = new EButton(getString("data"));
	private ELabel lbl1 = new ELabel();
	private ELabel lbl2 = new ELabel(getString("3011.1"));
	private boolean bDetails = false;


	private EPanel button = new EPanel();
	private GridLayout ga = new GridLayout(2,1,5,5);

	private EPanel pDialog = new EPanel();
	private GridLayout gc = new GridLayout(2,1,0,0);

	private EPanel direction = new EPanel();
	private BorderLayout gb = new BorderLayout();

//	private Dimension panelSize = new Dimension();

	private JTextArea jta = new JTextArea();
	private JScrollPane jsp = new JScrollPane(jta);

	/**
     * linkPanel
     * @author Anthony C. Liberto
     */
    public LinkPanel() {
		super (new BorderLayout());
		init();
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		btnOK.setMnemonic(getChar("ok-s"));
		btnDetails.setMnemonic(getChar("data-s"));
		btnOK.addActionListener(this);
		btnOK.setActionCommand("exit");
		btnDetails.addActionListener(this);
		btnDetails.setActionCommand("details");

		button.setLayout(ga);
		button.add(btnOK);
		button.add(btnDetails);

		pDialog.setLayout(gc);

		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pDialog.add(lbl1);
		pDialog.add(lbl2);

		direction.setLayout(gb);
		direction.add("West", pDialog);
		direction.add("East", button);
		direction.add("South", new JSeparator());

		jta.setBackground(Color.lightGray);
		jta.setEditable(false);

		add("North", direction);
		add("South", jsp);

		setBackground(Color.lightGray);
		setForeground(eaccess().getPrefColor(PREF_COLOR_FOREGROUND,Color.black));
		setModalCursor(true);					//50445
		button.setModalCursor(true);			//50445
		pDialog.setModalCursor(true);			//50445
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		appendLog("linkPanel.actionPerformed(" + _action + ")");
		if (_action.equals("exit")) {
			hideDialog();
		} else if (_action.equals("details")) {
			toggleDetails();
		}

	}

	private void toggleDetails() {
		if (bDetails) {
			bDetails = false;
		} else {
			bDetails = true;
		}
		showDetailView(bDetails);
		validate();
	}

	/**
     * sizePanel
     * @param _b
     * @author Anthony C. Liberto
     */
    private void sizePanel(boolean _b) {
		showDetailView(_b);
		packDialog();
	}

	/**
     * setText
     * @param _text
     * @author Anthony C. Liberto
     */
    public void setText(String _text) {
		lbl1.setText(_text);
	}

	/**
     * setMessage
     * @author Anthony C. Liberto
     * @param _message
     */
    public void setMessage(String _message) {
		jta.setText(_message);
	}

	private void showDetailView(boolean b) {
		Dimension d = null;
		if (b) {
			d = new Dimension(100,200);
		} else {
			d = UIManager.getDimension("eannounce.minimum");
		}
		jsp.setPreferredSize(d);
		jsp.setSize(d);
		packDialog();
	}

	/**
     * showMe
     * @author Anthony C. Liberto
     */
    public void showMe() {
		bDetails = false;			//51213
		sizePanel(false);
	}

	/**
     * setTitle
     * @author Anthony C. Liberto
     * @param _title
     */
    public void setTitle(String _title) {}
	/**
     * getTitle
     * @author Anthony C. Liberto
     * @return String
     */
    public String getTitle() {
		return getString("link.panel");
	}
/*
 50445
*/
	/**
     * createMenu
     * @author Anthony C. Liberto
     */
    protected void createMenu() {}
	/**
     * getMenuBar
     * @author Anthony C. Liberto
     * @return JMenuBar
     */
    public JMenuBar getMenuBar() {
		return null;
	}
	/**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {}

}

