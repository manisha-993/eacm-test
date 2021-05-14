/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MultiPopupHorz.java,v $
 * Revision 1.2  2008/01/30 16:27:05  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:59  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/05/25 20:59:57  tony
 * multiple flag enhancement
 *
 * Revision 1.3  2005/02/01 20:48:33  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/10/29 00:18:11  tony
 * removed System.out. statements.
 *
 * Revision 1.2  2003/10/22 17:12:04  tony
 * 52674
 *
 * Revision 1.1  2003/10/21 15:53:56  tony
 * 52619
 *
 * Revision 1.11  2003/10/10 20:10:24  tony
 * 52499
 *
 * Revision 1.10  2003/09/12 20:16:18  tony
 * 52194
 *
 * Revision 1.9  2003/08/29 19:44:28  tony
 * 51981
 *
 * Revision 1.8  2003/07/15 18:24:15  joan
 * 51336
 *
 * Revision 1.7  2003/05/14 19:00:36  tony
 * changed layout
 *
 * Revision 1.6  2003/04/28 19:27:14  tony
 * adjusted rendering on multi-editor so
 * component underneath is not visible.
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/04/18 15:42:41  tony
 * enhnaced multiEditor functionality.
 *
 * Revision 1.3  2003/04/17 23:13:25  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import com.ibm.eannounce.eobjects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class MultiPopupHorz extends JPopupMenu implements MultiPopup, EAccessConstants {
	private static final long serialVersionUID = 1L;
	private EFlagList list = new EFlagList() {
		private static final long serialVersionUID = 1L;
		public void updateText(String _s) {
			popupTextUpdate(_s);
		}
	};
	private EScrollPane scroll = new EScrollPane(list);
	private Dimension scrollSize = new Dimension(300,200);

	/**
     * multiPopupHorz
     * @author Anthony C. Liberto
     */
    public MultiPopupHorz() {
	    super();
	    init();
	    return;
	}

    /**
     * getKeyListener
     *
     * @return
     * @author Anthony C. Liberto
     */
    public KeyListener getKeyListener() {
		return list.getKeyListener();
	}

	/**
     * setEditor
     * @author Anthony C. Liberto
     * @param _editor
     */
    public void setEditor(MultiEditor _editor) {
		list.setEditor(_editor);
	}

    /**
     * acquireFocus
     *
     * @author Anthony C. Liberto
     */
    public void acquireFocus() {
		requestFocus();
		list.acquireFocus();
		return;
	}

	/**
     * setColumnHeader
     * @author Anthony C. Liberto
     * @param _port
     */
    public void setColumnHeader(JViewport _port) {
		scroll.setColumnHeader(_port);
		return;
	}

	private void init() {
		setFocusable(false);
		setLightWeightPopupEnabled(true);
		setLayout(new BorderLayout());
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setOpaque(false);
		add("North",scroll);
		setDoubleBuffered(true);

		setRequestFocusEnabled(false);
		scroll.setRequestFocusEnabled(false);
		scroll.getHorizontalScrollBar().setRequestFocusEnabled(false);
		scroll.getVerticalScrollBar().setRequestFocusEnabled(false);

		scroll.setSize(scrollSize);
		scroll.setPreferredSize(scrollSize);
		scroll.setScrollMode(JViewport.BLIT_SCROLL_MODE);			//52194
		return;
	}

    /**
     * getScrollSize
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Dimension getScrollSize() {
		return scrollSize;
	}

    /**
     * getFlagCount
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getFlagCount() {
		return list.getDataSize();
	}

/*
 * quick and simple utility method
 * used to refresh the flags
 */
	/**
     * refresh
     * @author Anthony C. Liberto
     * @param _att
     */
    public void refresh(EANAttribute _att) {
		refresh((MetaFlag[])_att.get());
		return;
	}

	/**
     * refresh
     * @author Anthony C. Liberto
     * @param _flag
     */
    public void refresh(MetaFlag[] _flag) {
		list.load(_flag);
		return;
	}

    /**
     * cancelChanges
     *
     * @author Anthony C. Liberto
     */
    public void cancelChanges() {
		list.resetFlags();
		return;
	}

/*
 * return the selected flags (and only the selected flags)
 * to the editor so that it can update the RowSelectableTable
 */
    /**
     * getMultipleSelection
     *
     * @return
     * @author Anthony C. Liberto
     */
    public MetaFlag[] getMultipleSelection() {
		int ii = getFlagCount();
		MetaFlag[] out = new MetaFlag[ii];
		for (int i=0;i<ii;++i) {
			out[i] = list.getFlagAt(i);
		}
		return out;
	}

/*
 * utility methods to remove
 * component completely from
 * memory
 */
    /**
     * removeAllFlags
     *
     * @author Anthony C. Liberto
     */
    public void removeAllFlags() {
		while (getFlagCount() > 0) {
			list.removeAt(0);
		}
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (scroll != null) {
			scroll.dereference();
			scroll = null;
		}
		removeAllFlags();
		list.dereference();
	}

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {  //51336
		scroll.setModalCursor(_b);
		list.setModalCursor(_b);
	}


/*
 51981
 */
	/**
     * @see java.awt.Component#setVisible(boolean)
     * @author Anthony C. Liberto
     */
    public void setVisible(boolean _b) {
		if (!_b) {
			return;
		}
		super.setVisible(_b);
		return;
	}

    /**
     * showHide
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void showHide(boolean _b) {
		super.setVisible(_b);
		return;
	}

/*
 52499
 */
    /**
     * getDisplayComponent
     *
     * @return
     * @author Anthony C. Liberto
     */
    public JComponent getDisplayComponent() {
		return list;
	}

	/**
     * addButtons
     * @author Anthony C. Liberto
     * @param _btnCancel
     * @param _btnSave
     */
    public void addButtons(EButton _btnSave, EButton _btnCancel) {}

/*
 52674
 */
	/**
     * \processPopupKey
     * @author Anthony C. Liberto
     * @param _ke
     */
    public void processPopupKey(KeyEvent _ke) {
		if (_ke != null) {
			list.scrollToCharacter(_ke.getKeyChar());
		}
		return;
	}

	/**
     * popupTextUpdate
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    public void popupTextUpdate(String _s) {}
}
