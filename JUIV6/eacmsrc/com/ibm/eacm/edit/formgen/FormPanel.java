//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;


import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import com.ibm.eacm.edit.form.FormCellPanel;

/**
 * this is used in the formtable
 * @author Wendy Stimpson
 */
// $Log: FormPanel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class FormPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private HeaderPanel header = null;
    private GridBagConstraints constraints = new GridBagConstraints();

    /**
     * formPanel
     */
    protected FormPanel() {
    	setLayout(new GridBagLayout());
    	header = new HeaderPanel(this);
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.NONE;
        add(header,constraints);
    	setOpaque(false);
    }

    /**
     * actionPerformed
     *
     * @parm ae
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("collapse")) {
            setCollapsed(false);
        } else if (action.equals("expand")) {
            setCollapsed(true);
        }
    }

    /**
     * get the Constraints specified for this panel
     * @return
     */
    protected GridBagConstraints getConstraints() {
        return constraints;
    }

    /**
     * setTitleColor
     * @param _c
     */
    protected void setTitleColor(Color _c) {
        header.setTitleColor(_c);
    }

    /**
     * setTitle
     * @param s
     * @param i
     */
    protected void addTitle(String s, int i) {
        header.addTitle(s, i);
    }

    /**
     * setNLS
     * @param _nls
     */
    protected void setNLSTitle(int _nls) {
        header.setNLSTitle(_nls);
    }

    /**
     * setCollapsible
     * @param b
     */
    protected void setCollapsible(boolean b) {
        header.setCollapsible(b);
    }

    /**
     * setCollapsed
     * @param b
     */
    protected void setCollapsed(boolean b) {
        for (int i = 0; i < getComponentCount(); ++i) {
            Component c = getComponent(i);
            if (!(c instanceof HeaderPanel)) {
                c.setVisible(!b);
            }
        }
        header.setCollapsed(b);
        revalidate();
    }

    /**
     * @see javax.swing.JComponent#revalidate()
     */
    public void revalidate() {
        super.revalidate();
        if(header!=null){ // called when base class is initializing
        	header.revalidate();
        }
    }
    
    /**
     * release memory used to build form
     */
    protected void derefGenVars(){
       	constraints = null; 
    }
    
    /**
     * release memory for all components
     */
    protected void dereference(){
    	header.dereference(this); 
    	header = null; 
 
		for (int i = 0; i < getComponentCount(); ++i) {
			Component c = getComponent(i);
			if (c instanceof FormLabel) {
				FormLabel fl = (FormLabel) c;
				fl.dereference();
			} else if (c instanceof TablePanel) {
				((TablePanel) c).dereference();
			}else if(c instanceof FormCellPanel){
				FormCellPanel fl = (FormCellPanel) c;
				fl.dereference();
			}
		}
		
		constraints = null;
		
		removeAll();
		setUI(null);
    }
}
