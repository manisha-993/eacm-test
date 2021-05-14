/**
 * Copyright (c) 2002-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * cr_6303
 *
 * @version 1.2  2004/03/12
 * @author Anthony C. Liberto
 *
 * $Log: ChooserPanel.java,v $
 * Revision 1.3  2008/01/30 16:26:53  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.2  2007/08/01 18:42:29  wendy
 * prevent hang
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
 * Revision 1.9  2005/09/08 17:59:00  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.8  2005/02/02 17:30:21  tony
 * JTest Second Pass
 *
 * Revision 1.7  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.6  2004/09/21 21:03:09  tony
 * TIR USRO-R-OGAA-652Q3N
 *
 * Revision 1.5  2004/09/15 22:45:49  tony
 * updated blue pages add logic
 *
 * Revision 1.4  2004/03/15 18:17:34  tony
 *  cr_6303 take two too many bookmarks
 *
 * Revision 1.3  2004/03/13 00:16:23  tony
 * updated to multiple selection list.
 *
 * Revision 1.2  2004/03/13 00:06:12  tony
 * *** empty log message ***
 *
 * Revision 1.1  2004/03/12 23:07:47  tony
 * cr_6303
 * send bookmark to a friend.
 *
 *
 */

package com.ibm.eannounce.dialogpanels;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.BookmarkItem;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuBar;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ChooserPanel extends AccessibleDialogPanel implements ActionListener, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private EPanel pnlButton = new EPanel(new GridLayout(1,2,5,5));
	private EButton btnOK = new EButton(getString("ok"));
	private EButton btnCancel = new EButton(getString("cncl"));
	private ELabel lbl = new ELabel(getString("bookmark.send.to"));

	private EList list = new EList();
	private EScrollPane scroll = new EScrollPane(list);
	private BookmarkItem book = null;

	private Object[] objArray = null;

	/**
     * chooserPanel
     * @author Anthony C. Liberto
     */
    public ChooserPanel() {
		super(new BorderLayout());
		init();
		return;
	}

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		pnlButton.add(btnOK);
		btnOK.setMnemonic(getChar("ok-s"));
		btnOK.setToolTipText(getString("ok-t"));
		btnOK.setActionCommand("ok");
		btnOK.addActionListener(this);
		pnlButton.add(btnCancel);
		btnCancel.setMnemonic(getChar("cncl-s"));
		btnCancel.setToolTipText(getString("cncl-t"));
		btnCancel.setActionCommand("cancel");
		btnCancel.addActionListener(this);
		add("North",lbl);
		lbl.setLabelFor(list);
		add("Center",scroll);
		add("South",pnlButton);
		setModalCursor(true);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		pnlButton.removeAll();
		pnlButton.removeNotify();
		if (btnOK != null) {
			btnOK.removeActionListener(this);
			btnOK.removeAll();
			btnOK.removeNotify();
			btnOK = null;
		}
		if (btnCancel != null) {
			btnCancel.removeActionListener(this);
			btnCancel.removeAll();
			btnCancel.removeNotify();
			btnCancel = null;
		}
		if (scroll != null) {
			scroll.dereference();
			scroll = null;
		}
		book = null;
		super.dereference();
		return;
	}

	/**
     * load
     * @param _ps
     * @author Anthony C. Liberto
     */
    public void load(ProfileSet _ps) {
		list.load(_ps);
		return;
	}

	/**
     * load
     * @param _o
     * @author Anthony C. Liberto
     */
    public void load(Object[] _o) {
		list.load(_o);
		return;
	}

	/**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {
		String action = _ae.getActionCommand();
		if (action.equals("ok")) {
			ok();
		} else if (action.equals("cancel")) {
			cancel();
		}
		disposeDialog();
		return;
	}

	/**
     * windowClosing
     * @param _we
     * @author Anthony C. Liberto
     */
    public void windowClosing(WindowEvent _we) {
		cancel();
		disposeDialog();
		return;
	}

	private void cancel() {
		return;
	}

	/**
     * ok
     * @author Anthony C. Liberto
     */
    public void ok() {
		if (!list.isSelectionEmpty()) {
			Object[] o = list.getSelectedValues();
			if (o != null) {
				int ii = o.length;
				Profile[] prof = new Profile[ii];
				for (int i=0;i<ii;++i) {
					prof[i] = ((ProfileDisplay)o[i]).getProfile();
				}
				if (prof != null) {
					send(book,prof);
					return;
				}
		    }
		}
		return;
	}

	/**
     * getSelectedValues
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getSelectedValues() {
		return objArray;
	}

	/**
     * getParentComponent
     * @return
     * @author Anthony C. Liberto
     */
    public Component getParentComponent() {
		return null;
	}

	/**
     * send
     * @param _book
     * @param _prof
     * @author Anthony C. Liberto
     */
    public void send(final BookmarkItem _book, final Profile[] _prof) {
		final ESwingWorker myWorker = new ESwingWorker() {
			public Object construct() {
				try{
					setModalBusy(true);
					dBase().sendBookmark(_book,_prof,getParentComponent());
				}catch(Exception ex){ // prevent hang
					appendLog("Exception in ChooserPanel.send.ESwingWorker.construct() "+ex);
					ex.printStackTrace();
					setBusy(false);
					setModalBusy(false);
				}

				return null;
			}

			public void finished() {
				setWorker(null);
				setModalBusy(false);
				setBusy(false);
				return;
			}
		};
		setWorker(myWorker);
		return;
	}

/*
 blue create
 */
	/**
     * setSelectedValues
     * @author Anthony C. Liberto
     */
    public void setSelectedValues() {
		objArray = list.getSelectedValues();
		return;
	}

	/**
     * setBookmarkItem
     * @param _book
     * @author Anthony C. Liberto
     */
    public void setBookmarkItem(BookmarkItem _book) {
		book = _book;
		return;
	}

	/**
     * clearSelected
     * @author Anthony C. Liberto
     */
    public void clearSelected() {
		objArray = null;
	}

/*
 TIR USRO-R-OGAA-652Q3N

 */
	/**
     * setMessage
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setMessage(String _s) {
		lbl.setText(_s);
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
    protected void createMenu() {}
	/**
     * removeMenu
     * @author Anthony C. Liberto
     */
    protected void removeMenu() {}

}
