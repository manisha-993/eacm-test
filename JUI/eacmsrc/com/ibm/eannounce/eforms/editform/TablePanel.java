/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: TablePanel.java,v $
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
 * Revision 1.2  2003/03/04 22:34:51  tony
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
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TablePanel extends EPanel {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints = new GridBagConstraints();
	private Point hold = new Point();
	private GridBagLayout layout = new GridBagLayout();
	private Color backColor = null;

	/**
     * tablePanel
     * @author Anthony C. Liberto
     */
    public TablePanel() {
		super();
		setLayout(layout);
		setGridHeight(1);
		setGridWidth(1);
		setX(0);
		setY(1);
		setTransparent(true);
		return;
	}

	/**
     * setConstraints
     * @param c
     * @param gbc
     * @author Anthony C. Liberto
     */
    public void setConstraints(Component c, GridBagConstraints gbc) {
		layout.setConstraints(c,gbc);
		return;
	}

	/**
     * setHoldX
     * @param i
     * @author Anthony C. Liberto
     */
    public void setHoldX(int i) {
		hold.x = i;
	}

	/**
     * setHoldY
     * @param i
     * @author Anthony C. Liberto
     */
    public void setHoldY(int i) {
		hold.y = i;
	}

	/**
     * getHoldX
     * @return
     * @author Anthony C. Liberto
     */
    public int getHoldX() {
		return hold.x;
	}

	/**
     * getHoldY
     * @return
     * @author Anthony C. Liberto
     */
    public int getHoldY() {
		return hold.y;
	}

	/**
     * getAdjustedX
     * @return
     * @author Anthony C. Liberto
     */
    public int getAdjustedX() {
		return hold.x + constraints.gridx;
	}

	/**
     * getAdjustedY
     * @return
     * @author Anthony C. Liberto
     */
    public int getAdjustedY() {
		return hold.y + constraints.gridy;
	}

	/**
     * setX
     * @param i
     * @author Anthony C. Liberto
     */
    public void setX(int i) {
		constraints.gridx = i;
		return;
	}

	/**
     * setY
     * @param i
     * @author Anthony C. Liberto
     */
    public void setY(int i) {
		constraints.gridy = i;
		return;
	}

	/**
     * incrementX
     * @param i
     * @author Anthony C. Liberto
     */
    public void incrementX(int i) {
		constraints.gridx += i;
	}

	/**
     * incrementY
     * @param i
     * @author Anthony C. Liberto
     */
    public void incrementY(int i) {
		constraints.gridy += i;
	}

	/**
     * setGridHeight
     * @param i
     * @author Anthony C. Liberto
     */
    public void setGridHeight(int i) {
		constraints.gridheight = i;
		return;
	}

	/**
     * setGridWidth
     * @param i
     * @author Anthony C. Liberto
     */
    public void setGridWidth(int i) {
		constraints.gridwidth = i;
		return;
	}

	/**
     * setWeightX
     * @param i
     * @author Anthony C. Liberto
     */
    public void setWeightX(int i) {
		constraints.weightx = i;
		return;
	}

	/**
     * setWeightY
     * @param i
     * @author Anthony C. Liberto
     */
    public void setWeightY(int i) {
		constraints.weighty = i;
		return;
	}

	/**
     * setAnchor
     * @param i
     * @author Anthony C. Liberto
     */
    public void setAnchor(int i) {
		constraints.anchor = i;
		return;
	}

	/**
     * setFill
     * @param i
     * @author Anthony C. Liberto
     */
    public void setFill(int i) {
		constraints.fill = i;
		return;
	}

    /**
     * setComponentsVisible
     *
     * @param b
     * @param _locked
     * @author Anthony C. Liberto
     */
    public void setComponentsVisible(boolean b, boolean _locked) {
		int ii = getComponentCount();
		for (int i=0;i<ii;++i) {
			Component c = getComponent(i);
//			if (c instanceof FormEditor) {
//				FormEditor fe = (FormEditor)c;
//				if (!b)
//					fe.setAllVisible(false);
//				else
//					fe.displayEditableComponent(locked);
//			} else if (c instanceof JScrollPane) {
			if (c instanceof JScrollPane) {
				((JScrollPane)c).setVisible(b);
			}
		}
		return;
	}

	/**
     * getConstraints
     * @return
     * @author Anthony C. Liberto
     */
    public GridBagConstraints getConstraints() {
		return constraints;
	}

	/**
     * setBackgroundColor
     * @param _c
     * @author Anthony C. Liberto
     */
    public void setBackgroundColor(Color _c) {
		backColor =_c;
	}

	/**
     * getBackgroundColor
     * @return
     * @author Anthony C. Liberto
     */
    public Color getBackgroundColor() {
		return backColor;
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {		//acl_Mem_20020131
		constraints = null;		//acl_Mem_20020131
		hold = null;			//acl_Mem_20020131
		layout = null;			//acl_Mem_20020131
	}							//acl_Mem_20020131

    /**
     * getPanelType
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getPanelType() {
		return TYPE_TABLEPANEL;
	}
}

