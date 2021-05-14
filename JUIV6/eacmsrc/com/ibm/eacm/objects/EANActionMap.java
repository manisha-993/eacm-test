//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.objects;

import java.util.HashMap;
import java.util.Vector;



import COM.ibm.eannounce.objects.CreateActionItem;
import COM.ibm.eannounce.objects.DeleteActionItem;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EditActionItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.LinkActionItem;
import COM.ibm.eannounce.objects.LockActionItem;
import COM.ibm.eannounce.objects.MatrixActionItem;
//import COM.ibm.eannounce.objects.MetaMaintActionItem;
import COM.ibm.eannounce.objects.NavActionItem;
import COM.ibm.eannounce.objects.PDGActionItem;
import COM.ibm.eannounce.objects.QueryActionItem;
import COM.ibm.eannounce.objects.ReportActionItem;
import COM.ibm.eannounce.objects.SearchActionItem;
import COM.ibm.eannounce.objects.WhereUsedActionItem;
import COM.ibm.eannounce.objects.WorkflowActionItem;

/**
 * This class holds EANActionItems allowing reference by actionitemkey or set of items with the same purpose
 * @author Wendy Stimpson
 */
//$Log: EANActionMap.java,v $
//Revision 1.2  2013/07/18 18:23:22  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class EANActionMap implements EACMGlobals {
	private HashMap<String, EANActionItem> map = new HashMap<String, EANActionItem>();
	private HashMap<String, Vector<EANActionItem>> mapAction = new HashMap<String, Vector<EANActionItem>>();
	
	/**
	 * reset the contents
	 */
	public void clear() {
		map.clear();
		mapAction.clear();
	}
	
	/**
	 * release memory
	 */
	public void dereference() {
		clear();
		mapAction = null;
		map = null;
	}
	
	/**
	 * add set of actions based on their purpose
	 * @param _ean
	 */
	public void addActionItem(EANActionItem[] _ean) {
		if (_ean != null){
			for (int i=0;i<_ean.length;++i) {
				addActionItem(_ean[i]);
			}
		}
	}
	
	/**
	 * add action based on its purpose
	 * @param _ean
	 */
	public void addActionItem(EANActionItem _ean) {
		String key = getPurpose(_ean); //get key based on class type, group same types of actions
		map.put(_ean.getKey(),_ean);

		Vector<EANActionItem> v = mapAction.get(key);
		if (v==null){
			v = new Vector<EANActionItem>();
			mapAction.put(key,v);	
		}
		v.add(_ean);
	}
	
    /**
     * get ActionItem with specified actionkey
     * @param _key
     * @return
     */
    public EANActionItem getActionItem(String _key) {
		return map.get(_key);
	}
    
    /**
     * do any actions exist for this 'purpose'
     * @param purpose
     * @return
     */
    public boolean actionExists(String purpose) {
		return mapAction.containsKey(purpose);
	}
    
    /** 
     * get all actions for this 'purpose'
     * @param purpose
     * @return
     */
    public EANActionItem[] getActionItemArray(String purpose) {
		return getActionItemArray(new String[]{purpose});
	}
    
    /**
     * get all actions for this array of 'purpose'
     * @param purpose
     * @return
     */
    public EANActionItem[] getActionItemArray(String[] purpose) {
    	EANActionItem[] out = null;
		Vector<EANActionItem> vctAll = new Vector<EANActionItem>();
		if (purpose != null) {
			for (int i=0;i<purpose.length;++i) {
				if (mapAction.containsKey(purpose[i])) {
					vctAll.addAll(mapAction.get(purpose[i]));
				}
			}
			if (!vctAll.isEmpty()) {
				out = new EANActionItem[vctAll.size()];
				vctAll.copyInto(out);
				vctAll.clear();
			}
		}
		return out;
	}
    
	/**
	 * get the purpose or category for this action
	 * @param _ean
	 * @return
	 */
	public static String getPurpose(EANActionItem _ean) {
		if (_ean instanceof CreateActionItem) {
			return ACTION_PURPOSE_CREATE;
		} else if (_ean instanceof DeleteActionItem) {
			return ACTION_PURPOSE_DELETE;
		} else if (_ean instanceof EditActionItem) {
			return ACTION_PURPOSE_EDIT;
		} else if (_ean instanceof ExtractActionItem) {
			return ACTION_PURPOSE_EXTRACT;
		} else if (_ean instanceof LinkActionItem) {
			return ACTION_PURPOSE_LINK;
		} else if (_ean instanceof LockActionItem) {
			return ACTION_PURPOSE_LOCK;
		} else if (_ean instanceof MatrixActionItem) {
			return ACTION_PURPOSE_MATRIX;
		} else if (_ean instanceof NavActionItem) {
			return ACTION_PURPOSE_NAVIGATE;
		} else if (_ean instanceof ReportActionItem) {
			return ACTION_PURPOSE_REPORT;
		} else if (_ean instanceof SearchActionItem) {
			return ACTION_PURPOSE_SEARCH;
		} else if (_ean instanceof WhereUsedActionItem) {
			return ACTION_PURPOSE_WHERE_USED;
		} else if (_ean instanceof WorkflowActionItem) {
			return ACTION_PURPOSE_WORK_FLOW;
		} else if (_ean instanceof PDGActionItem) { 
			return ACTION_PURPOSE_PDG;
		} else if (_ean instanceof QueryActionItem) {
			return ACTION_PURPOSE_QUERY;
		}

		return _ean.getActionClass();	
	}
}
