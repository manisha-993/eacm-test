//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;

import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.PDGEditTable;

/**************
 * 
 * this is used for displaying a pdg editor 
 * @author Wendy Stimpson
 */
public class PDGEditor extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private PDGEditTable table = null;
	private JScrollPane scroll = null;

	/**
	 * PDGEditor
	 * @param prof
	 * @param rst
	 */
	public PDGEditor(Profile prof, EntityItem ei) { 
		super(new BorderLayout());

		table = new PDGEditTable(prof, ei);

		scroll = new JScrollPane(table);

		Dimension scrollDim = new Dimension(500,500);

		scroll.setSize(scrollDim);
		scroll.setPreferredSize(scrollDim);
		scroll.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
		add(scroll,BorderLayout.CENTER);
	} 

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		table.setEnabled(enabled);
	}

	/**
	 * get table used by the pdg editor, needed to add listeners to it, etc
	 * @return
	 */
	public PDGEditTable getTable(){
		return table;
	}
	/**
	 * @param bre
	 */
	protected void moveToError(EANBusinessRuleException bre) { 
		Object o = bre.getObject(0);
		EntityItem ei = null;
		String attrcode = null;
		String columnKey = null;
		if (o instanceof EANAttribute) {
			EANAttribute att = (EANAttribute) o;
			ei = (EntityItem) att.getParent();
			attrcode = att.getAttributeCode();
			columnKey = Utils.getAttributeKey(ei, attrcode);
		} else if (o instanceof EntityItem) {
			ei = (EntityItem) o;
		}

		if(ei!=null){
			if(columnKey!=null){
				table.setSelection(columnKey);
			}
		}
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		table.dereference();
		table = null;

		scroll.removeAll();
		scroll.setUI(null);
		scroll = null;

		removeAll();
		setUI(null);
	}

}


