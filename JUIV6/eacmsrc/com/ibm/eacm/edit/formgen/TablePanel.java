//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;

import java.awt.*;

import javax.swing.*;

import com.ibm.eacm.edit.form.FormCellPanel;

/**
 * this holds all of the attribute values specified for the TABLE element
 * @author Wendy Stimpson
 */
// $Log: TablePanel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class TablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GridBagConstraints constraints = new GridBagConstraints();
	private int xcoord = 0;
	private int ycoord = 0;
	private Color backColor = null;

	/**
     * tablePanel
     */
    protected TablePanel() {
		setLayout(new GridBagLayout());
		setGridHeight(1);
		setGridWidth(1);
		setX(0);
		setY(1);
		setOpaque(false);
	}

	/**
     * set starting x coord for this table
     * @param i
     */
    protected void setHoldX(int i) {
    	xcoord = i;
	}

	/**
     * set starting y coord for this table
     * @param i
     */
    protected void setHoldY(int i) {
    	ycoord = i;
	}

	/**
     * getAdjustedX
     * @return
     */
    protected int getAdjustedX() {
		return xcoord + constraints.gridx;
	}

	/**
     * getAdjustedY
     * @return
     */
    protected int getAdjustedY() {
		return ycoord + constraints.gridy;
	}

	/**
     * set x coord specified in the form
     * @param i
     */
    protected void setX(int i) {
		constraints.gridx = i;
	}

	/**
     * set y coord specified in the form
     * @param i
     */
    protected void setY(int i) {
		constraints.gridy = i;
	}

	/**
     * set GRIDHEIGHT specified in the form
     * @param i
     */
    protected void setGridHeight(int i) {
		constraints.gridheight = i;
	}

	/**
     *  set GRIDWIDTH specified in the form
     * @param i
     */
    protected void setGridWidth(int i) {
		constraints.gridwidth = i;
	}

	/**
     * set WEIGHTX specified in the form
     * @param i
     */
    protected void setWeightX(int i) {
		constraints.weightx = i;
	}

	/**
     * set WEIGHTY specified in the form
     * @param i
     */
    protected void setWeightY(int i) {
		constraints.weighty = i;
	}

	/**
     * set ANCHOR specified in the form
     * @param i
     */
    protected void setAnchor(int i) {
		constraints.anchor = i;
	}

	/**
     *  set FILL specified in the form
     * @param i
     */
    protected void setFill(int i) {
		constraints.fill = i;
	}

	/**
     * get the Constraints specified for this table
     * @return
     */
    protected GridBagConstraints getConstraints() {
		return constraints;
	}

	/**
     * set row BackgroundColor
     * @param _c
     */
    protected void setRowBgColor(Color _c) {
		backColor =_c;
	}

	/**
     * getBackground used for this row
     * @return
     */
    protected Color getRowBgColor() {
		return backColor;
	}

    /**
     * release memory for variables used in form generation, this still is part of the form panel
     */
    protected void derefGenVars() {		
		constraints = null;	
		backColor = null;
	}							
  
    /**
     * release memory for all components
     */
    protected void dereference(){
		for (int i = 0; i < getComponentCount(); ++i) {
			Component c = getComponent(i);
			if (c instanceof FormLabel) {
				FormLabel fl = (FormLabel) c;
				fl.dereference();
			} else if(c instanceof FormCellPanel){
				FormCellPanel fl = (FormCellPanel) c;
				fl.dereference();
			}
		}
		constraints = null;
		backColor = null;
		removeAll();
		setUI(null);
    }
}

