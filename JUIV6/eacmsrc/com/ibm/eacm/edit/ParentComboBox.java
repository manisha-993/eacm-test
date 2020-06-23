//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;

/*********************************************************************
 * This is used for selecting a new entity's parent when there may be more than one to choose from
 * only used when edit can create
 * @author Wendy Stimpson
 */
//$Log: ParentComboBox.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ParentComboBox extends JComboBox {
	private static final long serialVersionUID = 1L;
	private EditController editCtrl = null;
	private ActionListener actListener = null;

	/**
	 * constructor
	 * @param ec
	 * @param group
	 */
	protected ParentComboBox(EditController ec, EntityGroup group){
		setAutoscrolls(true);
		setMaximumRowCount(5);
		editCtrl = ec;

		if(group==null || !group.hasData()){
			//parSel.notavail = No Parent is Available
			insertItemAt(Utils.getResource("parSel.notavail"), 0);
			setSelectedIndex(0);
		}else{
			EntityItem[] parents = group.getEntityItemsAsArray();
			Arrays.sort(parents, new java.util.Comparator<Object>(){
				public int compare(Object _o1, Object _o2) {
					return _o1.toString().compareToIgnoreCase(_o2.toString());
				}
			});

		    setModel(new DefaultComboBoxModel(parents));
		    
			actListener = new ActionListener() {
				public void actionPerformed(ActionEvent e)	{
					Object o = getSelectedItem();
					if (o instanceof EntityItem) {
						editCtrl.getCurrentEditor().setParentItem((EntityItem) o);
					}
					setToolTipText(getTooltipText(o));
				}
			};
			
			
			if (parents.length > 1) {
				//parSel.select = Please Select Parent Item
				insertItemAt(Utils.getResource("parSel.select"), 0);
			}
			if (parents.length > 0) {
				setSelectedIndex(0);
			}
			setToolTipText(getTooltipText(getSelectedItem()));
			addActionListener(actListener);
		}
	}
	
	/**
	 * build tooltip based on object selected
	 * @param obj
	 * @return
	 */
	private String getTooltipText(Object obj){
		String tooltip = null;
		if (BehaviorPref.showTableTooltips()) { //show tt
			tooltip = obj.toString();
			if (obj instanceof EntityItem) {
				//parSel.parent = Parent is {0}: {1}
				tooltip=Utils.getResource("parSel.parent",((EntityItem) obj).getKey(),tooltip);
			}
		}

		return tooltip;
	}

	/**
	 * select the item in the set of comboitems
	 * called when selected item changes - display its parent
	 * @param item
	 */
	protected void setSelectedParent(EntityItem item) {
		removeActionListener(actListener);
		if (item == null || item.getEntityID()<0) {
			setSelectedIndex(0);
			setToolTipText(getTooltipText(getSelectedItem()));
		} else {
			String key = item.getKey();
			for (int i = 0; i < getItemCount(); ++i) {
				Object o = getItemAt(i);
				if (o instanceof EntityItem) {
					if (key.equals(((EntityItem) o).getKey())) {
						setSelectedItem(o);
						setToolTipText(getTooltipText(o));
						break;
					}
				}
			}
		}
		addActionListener(actListener);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComboBox#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue) {
		boolean ok = false;
		if(newValue){
			// depends on selection of a new row
			ok = editCtrl.getCurrentEditor()!=null &&
				editCtrl.getCurrentEditor().hasNewSelected();
		}
		super.setEnabled(newValue && ok);
	}
	/**
	 * release memory
	 */
	protected void dereference(){
		editCtrl = null;
		
		removeActionListener(actListener);
		actListener = null;
		
		setUI(null);
		removeAll();
	}
}
