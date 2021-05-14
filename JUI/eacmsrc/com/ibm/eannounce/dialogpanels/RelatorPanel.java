/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: RelatorPanel.java,v $
 * Revision 1.2  2008/01/30 16:26:54  wendy
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
 * Revision 1.3  2005/02/02 17:30:20  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:29  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2003/08/13 23:05:18  tony
 * *** empty log message ***
 *
 * Revision 1.4  2003/08/13 21:01:55  tony
 * 51697
 *
 * Revision 1.3  2003/05/30 21:09:20  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
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
import COM.ibm.eannounce.objects.MetaLink;
import java.awt.*;
import javax.swing.JMenuBar;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RelatorPanel extends AccessibleDialogPanel {
	private static final long serialVersionUID = 1L;
	private ELabel lbl = new ELabel(getString("msg24009"));
	private EFlagBox ufe = new EFlagBox();
	//private String relator = null;

	private EPanel pnlSouth = new EPanel(new GridLayout(1,2,5,5));
	private EButton btnOK = new EButton(getString("ok"));
	private EButton btnCancel = new EButton(getString("cncl"));

	/**
     * relatorPanel
     * @author Anthony C. Liberto
     */
    public RelatorPanel() {
		super(new BorderLayout());
		init();															//51697
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		pnlSouth.add(btnOK);
		btnOK.setActionCommand("ok");
		pnlSouth.add(btnCancel);
		btnCancel.setActionCommand("exit");
		add("North", lbl);
		add("Center", ufe);
		add("South", pnlSouth);
		btnOK.addActionListener(this);
		btnCancel.addActionListener(this);
		return;
	}

	/**
     * showDialog
     * @param _ml
     * @return
     * @author Anthony C. Liberto
     */
    public MetaLink showDialog(MetaLink[] _ml) {
		ufe.reload(_ml);
		ufe.setSelectedItem(null);
		showDialog(this,this);
		return (MetaLink)ufe.getSelectedItem();
	}

	/**
     * actionPerformed
     * @author Anthony C. Liberto
     * @param _action
     */
    public void actionPerformed(String _action) {
		if (_action.equals("ok")) {
			//MetaLink tmp = ufe.getSelectedMetaLinkItem();
			//if (tmp != null) {
				//relator = tmp.getKey();
			//}
			disposeDialog();
		} else if (_action.equals("exit")) {
			//relator = null;
			ufe.setSelectedItem(null);
			disposeDialog();
		}
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		btnOK.removeActionListener(this);
		btnOK = null;
		btnOK.removeAll();
		btnCancel.removeActionListener(this);
		btnCancel.removeAll();
		btnCancel = null;
		lbl.removeAll();
		lbl = null;
		ufe.removeAll();
		ufe = null;
		//relator = null;
		pnlSouth.removeAll();
		pnlSouth.removeNotify();
		pnlSouth = null;
		removeAll();
		removeNotify();
		return;
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
		return getString("relator.panel");
	}

/*
 51697
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
