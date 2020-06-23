/**
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * Added to allow for single cart implementation across tabs.
 * This will prevent the one cart for one navigate tab
 * anomaly that was not liked by HVEC(PCD(PSG)).
 *
 *  -------------
 * | cr903035214 |
 *  -------------
 *
 * @version 1.2  2003/11/03
 * @author Anthony C. Liberto
 *
 * $Log: CartList.java,v $
 * Revision 1.2  2009/05/28 14:18:09  wendy
 * Performance cleanup
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:00  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/01 00:27:55  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/26 23:42:26  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/11/08 19:01:40  tony
 * Improved sort logic to no longer sort multiple times on a
 * navigation reload.
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:43  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/11/07 19:18:52  tony
 * CR_903035214
 *
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Vector;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class CartList {
	private EntityList list = null;
	private EntityGroup cutGroup = null;
	//private Profile prof = null;
	private Vector vData = null;

	/**
     * cartList
     * @param _prof
     * @author Anthony C. Liberto
     */
    protected CartList(Profile _prof) {
		//setProfile(_prof);
		try {
			EntityList eList = new EntityList(_prof);
			setEntityList(eList);
		} catch (MiddlewareRequestException _mre) {
			_mre.printStackTrace();
		}
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		list = null;
		cutGroup = null;
		//prof = null;
		if (vData != null) {
			vData.clear();
			vData = null;
		}
	}

	/**
     * getEntityList
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityList getEntityList() {
		return list;
	}

	/**
     * setEntityList
     * @param _list
     * @author Anthony C. Liberto
     */
    protected void setEntityList(EntityList _list) {
		list = _list;
	}

	/**
     * getProfile
     * @return
     * @author Anthony C. Liberto
     * /
    public Profile getProfile() {
		return prof;
	}*/

	/**
     * setProfile
     * @param _prof
     * @author Anthony C. Liberto
     */
    //private void setProfile(Profile _prof) {
		//prof = _prof;
	//}

	/**
     * getCutGroup
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityGroup getCutGroup() {
		return cutGroup;
	}

	/**
     * setCutGroup
     * @param _cut
     * @author Anthony C. Liberto
     */
    protected void setCutGroup(EntityGroup _cut) {
		cutGroup = _cut;
	}

	/**
     * addCutGroup
     * @param _prof
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityGroup addCutGroup(Profile _prof, EntityItem[] _ei) {
		if (cutGroup != null) {
			eaccess().getPersistantCart().clear(_prof,cutGroup);
		}
		cutGroup = eaccess().dBase().cloneEntityItem(list,_ei);
		if (cutGroup != null) {
			cutGroup.putLongDescription("Move");
		}
		return cutGroup;
	}

	/**
     * removeTab
     * @param _data
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void removeTab(NavData _data, String _s) {
		if (_data != null && _s != null) {
			_data.removeTab(_s);
			processData(_data,null,_s);
		}
	}

	/**
     * refreshTab
     * @param _data
     * @param _eg
     * @author Anthony C. Liberto
     * /
    protected void refreshTab(NavData _data, EntityGroup _eg) {
		if (_data != null && _eg != null) {
			_data.refreshTab(_eg,false);
			processData(_data,_eg,null);
		}
		return;
	}*/

	private void processData(NavData _data, EntityGroup _eg, String _s) {
		int ii = vData.size();
		for (int i=0;i<ii;++i) {
			NavData data = getData(i);
			if (data != null && data != _data) {
				if (_eg != null) {
					data.refreshTab(_eg,false);
				}
				if (_s != null) {
					data.removeTab(_s);
				}
			}
		}
	}

	private NavData getData(int _i) {
		if (vData != null && _i >= 0 && _i < vData.size()) {
			return (NavData)vData.get(_i);
		}
		return null;
	}

	/**
     * addSynchListener
     * @param _data
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean addSynchListener(NavData _data) {
		if (vData == null) {
			vData = new Vector();
		}
		if (!vData.contains(_data)) {
			return vData.add(_data);
		}
		return false;
	}

	/**
     * removeSynchListener
     * @param _data
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean removeSynchListener(NavData _data) {
		if (vData != null) {
			return vData.remove(_data);
		}
		return false;
	}
}
