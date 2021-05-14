/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: HeaderPanel.java,v $
 * Revision 1.2  2008/01/30 16:27:09  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:19  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:12  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:55  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:02  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/04 15:22:09  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/02/03 16:38:53  tony
 * JTest Second Pass Wrapup
 *
 * Revision 1.2  2005/01/27 19:15:16  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/10/29 00:19:20  tony
 * removed System.out. statements.
 *
 * Revision 1.3  2003/03/21 22:38:33  tony
 * form accessibilty update.
 *
 * Revision 1.2  2003/03/04 22:34:50  tony
 * *** empty log message ***
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/07 16:58:27  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editform;
import com.elogin.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HeaderPanel extends EPanel {
	private static final long serialVersionUID = 1L;
	private boolean canCollapse = true;
	private boolean isCollapsed = false;
	private NLSLabel title = new NLSLabel();
	private BasicArrowButton btnCollapsed = new BasicArrowButton(JButton.EAST);
	private BasicArrowButton btnExpanded = new BasicArrowButton(JButton.SOUTH);

	/**
     * headerPanel
     * @author Anthony C. Liberto
     */
    public HeaderPanel() {
		super(new BorderLayout(5,5));
		init();
		setTransparent(true);
		return;
	}

	/**
     * addActionListener
     * @param _al
     * @author Anthony C. Liberto
     */
    public void addActionListener(ActionListener _al) {
		btnCollapsed.addActionListener(_al);
		btnExpanded.addActionListener(_al);
		return;
	}

	/**
     * removeActionListener
     * @param _al
     * @author Anthony C. Liberto
     */
    public void removeActionListener(ActionListener _al) {
		btnCollapsed.removeActionListener(_al);
		btnExpanded.removeActionListener(_al);
		return;
	}

	private void init() {
		adjustButton();
		btnCollapsed.setActionCommand("collapse");
		btnExpanded.setActionCommand("expand");
		add("Center", title);
		return;
	}

	/**
     * adjustHideShow
     * @param b
     * @author Anthony C. Liberto
     */
    public void adjustHideShow(boolean b) {
		btnCollapsed.setVisible(b);
		btnExpanded.setVisible(b);
		title.setVisible(b);
		return;
	}

	/**
     * getPreferredHeight
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredHeight() {
		int h = title.getPreferredSize().height;
		return Math.max(16,h);
	}

/*
	public void setButtonDimension() {
		dimButton = btnExpanded.getPreferredSize();
		dimButton.width += 5;
		return;
	}

	public void setTitleDimension(String s) {
		FontMetrics fm = getFontMetrics(getFont());
		dimTitle =  new Dimension(fm.stringWidth(s), fm.getHeight());
//		dimTitle = title.getPreferredSize();
		return;
	}

	public Dimension getPreferredSize() {
		if (dimButton != null && dimTitle != null) {
			int height = Math.max(dimButton.height, dimTitle.height);
			int width = dimButton.width + dimTitle.width;
			return new Dimension(width,height);
		} else if (dimButton != null && dimTitle == null)
			return dimButton;
		else if (dimButton == null && dimTitle != null)
			return dimTitle;
		return super.getPreferredSize();
	}
*/

//	public void setTitle(String s) {
//		title.setText(s);
//		return;
//	}
//
//	public String getTitle() {
//		return title.getText();
//	}

	/**
     * setTitle
     * @param s
     * @param nls
     * @author Anthony C. Liberto
     */
    public void setTitle(String s, int nls) {
		title.setTitle(s,nls);
		return;
	}

	/**
     * getTitle
     * @param _nls
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle(int _nls) {
		return title.getTitle(_nls);
	}

	/**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
		return title.toString();
	}

	/**
     * setNLS
     * @param _nls
     * @author Anthony C. Liberto
     */
    public void setNLS(int _nls) {
		title.setNLS(_nls);
	}

	/**
     * setTitleColor
     * @param c
     * @author Anthony C. Liberto
     */
    public void setTitleColor(Color c) {
		title.setForeground(c);
		return;
	}

	/**
     * getTitleColor
     * @return
     * @author Anthony C. Liberto
     */
    public Color getTitleColor() {
		return title.getForeground();
	}

	/**
     * setCollapsible
     * @param b
     * @author Anthony C. Liberto
     */
    public void setCollapsible(boolean b) {
		canCollapse = b;
		btnCollapsed.setEnabled(b);
		btnExpanded.setEnabled(b);
		return;
	}

	/**
     * isCollapsible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCollapsible() {
		return canCollapse;
	}


	/**
     * setCollapsed
     * @param b
     * @author Anthony C. Liberto
     */
    public void setCollapsed(boolean b) {
		if (isCollapsible()) {
			isCollapsed = b;
			adjustButton();
			validate();										//16312
			repaint();
		}
		return;
	}

	/**
     * isCollapsed
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCollapsed() {
		return isCollapsed;
	}

	/**
     * adjustButton
     * @author Anthony C. Liberto
     */
    public void adjustButton() {
		if (isCollapsed()) {
			remove(btnExpanded);
			add("West", btnCollapsed);
		} else {
			remove(btnCollapsed);
			add("West", btnExpanded);
        }
		if (isShowing()) {
			repaint();
		}
		return;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		title.removeAll();
		title = null;
		btnCollapsed.removeAll();
		btnCollapsed.removeNotify();
		btnCollapsed = null;
		btnExpanded.removeAll();
		btnExpanded.removeNotify();
		btnExpanded = null;
	}

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_HEADERPANEL;
	}
}
