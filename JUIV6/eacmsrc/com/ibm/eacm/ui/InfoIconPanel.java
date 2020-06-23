//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;

/**
 * display status of filter, hidden columns and past date as icons in the status bar
 * @author Wendy Stimpson
 */
public class InfoIconPanel extends JPanel implements EACMGlobals {
	private static final long serialVersionUID = 1L;

	private JLabel filter = new JLabel(Utils.getImageIcon("fltr.gif"));
	private JLabel wayBack = new JLabel(Utils.getImageIcon("wayBack.gif")); 			
	private JLabel lblHidden = new JLabel(Utils.getImageIcon("hideCol.gif"));			

	/**
     * panel to hold information icons	
     */
    public InfoIconPanel() {
		super(new GridLayout(1,3,3,3));

		setFilterEnabled(false);
		setPastDateEnabled(false);										
		setHiddenEnabled(false);
																	
		add(filter);
		add(lblHidden);																
		add(wayBack);

		setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
	}
    
    /**
     * release memory
     */
    protected void dereference(){
    	filter.removeAll();
    	filter = null;
    	
		lblHidden.removeAll();
		lblHidden = null;															
		wayBack.removeAll();
		wayBack = null;	
		
		removeAll();
		setUI(null);
    }
    /**
     * enable filter icon and set tooltip
     * @param b
     */
    public void setFilterEnabled(boolean b){
    	filter.setEnabled(b);
    
    	String tip = null;
    	if (b){
    		tip=Utils.getResource("fltrdOn");
    	}else{
    		tip=Utils.getResource("fltrdOff");
    	}
		filter.setToolTipText(tip);
    }
    /**
     * enable hidden icon and set tooltip
     * @param b
     */
    public void setHiddenEnabled(boolean b){
    	lblHidden.setEnabled(b);
    	
     	String tip = null;
    	if (b){
    		tip=Utils.getResource("hiddenExists");
    	}else{
    		tip=Utils.getResource("hiddenNo");
    	}																			

    	lblHidden.setToolTipText(tip);
    }
    /**
     * enable pastdate icon and set tooltip
     * @param b
     */
    public void setPastDateEnabled(boolean b){
    	wayBack.setEnabled(b);
    	String tip = null;
    	if (b){
    		tip=Utils.getResource("wayBPast");
    	}else{
    		tip=Utils.getResource("wayBcur");
    	}

    	wayBack.setToolTipText(tip);			
    }
}
