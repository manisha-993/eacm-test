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
 * $Log: PersistantCart.java,v $
 * Revision 1.2  2008/01/30 16:26:55  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:44:49  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:05  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/01 00:27:56  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 23:42:27  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/11/07 19:18:52  tony
 * CR_903035214
 *
 * Revision 1.1  2003/11/05 17:12:03  tony
 * cr_persistant_cart
 * Change Request to make cart persistant across tabs.
 * This is the first pass at addressing the issue.  It is not
 * currently implemented, but all of the source code is in
 * place and ready for test.
 * To implement this change navigate needs to use the new
 * navCartDialog instead of the existing navCart.  There are
 * two flavors... navCartTab, is similar to the current
 * implementation and navCartProfile is a cross tab
 * implementation.
 *
 */
package com.ibm.eannounce.eforms.navigate;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Collection;
import java.util.HashMap;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PersistantCart extends HashMap {
	private static final long serialVersionUID = 1L;
	/**
     * persistantCart
     * @author Anthony C. Liberto
     */
    public PersistantCart() {
		super();
		return;
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
		clear();
		return;
	}

	/**
     * getCartList
     * @param _prof
     * @param _create
     * @return
     * @author Anthony C. Liberto
     */
    public CartList getCartList(Profile _prof, boolean _create) {
		String key = getCartKey(_prof);
		if (key != null) {
			if (containsKey(key)) {
				return (CartList)get(key);
			}
			if (_create) {
				CartList cList = new CartList(_prof);
				put(getCartKey(_prof),cList);
				return cList;
			}
		}
		return null;
	}

	/**
     * getEntityList
     * @param _prof
     * @param _create
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList getEntityList(Profile _prof, boolean _create) {
		return getCartList(_prof,_create).getEntityList();
	}

	/**
     * getCutGroup
     * @param _prof
     * @param _create
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup getCutGroup(Profile _prof,boolean _create) {
		return getCartList(_prof,_create).getCutGroup();
	}

	/**
     * add
     * @param _prof
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    public EntityGroup add(Profile _prof, EntityItem[] _ei) {
		EntityGroup eg = null;
		EntityList eList = getEntityList(_prof,true);
		if (eList != null) {
			for (int i=0; i < _ei.length; i++) {
				if (!_ei[i].isNew()) {
					EntityItem[] aei = {_ei[i]};
					eg = eaccess().dBase().addToCart(eList,aei);
				}
			}
			return eg;
		}
		return null;
	}

	/**
     * addCutItems
     * @param _prof
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void addCutItems(Profile _prof, EntityItem[] _ei) {
		CartList cList = null;
        if (_ei == null)  {
			return;
		}
		cList = getCartList(_prof,true);
		if (cList == null) {
			cList.addCutGroup(_prof,_ei);
		}
		return;
	}

	/**
     * canPaste
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste(Profile _prof) {
		EntityGroup cutGroup = getCutGroup(_prof,true);
		if (cutGroup != null) {
			return (cutGroup.getEntityItemCount() != 0);
		}
		return false;
	}

	/**
     * clear
     * @param _prof
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList clear(Profile _prof, EntityGroup _eg) {
		return clear(getEntityList(_prof,false),_eg);
	}

	private EntityList clear(EntityList _list, EntityGroup _eg) {
		if (_eg != null) {
			if (_list != null) {
				_eg = clear(_eg);
				_list.removeEntityGroup(_eg);
			}
			return _list;
		}
		return null;
	}

	/**
     * clear
     * @param _eg
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityGroup clear(EntityGroup _eg) {
		if (_eg != null) {
			while (_eg.getEntityItemCount() > 0) {
				EntityItem ei = _eg.getEntityItem(0);
				if (ei != null) {
					_eg.removeEntityItem(ei);
				}
			}
		}
		return _eg;
	}

	/**
     * clear
     * @param _prof
     * @param _ei
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList clear(Profile _prof, EntityItem[] _ei) {
		return clear(getEntityList(_prof,false),_ei);
	}

	private EntityList clear(EntityList _list, EntityItem[] _ei) {
		if (_ei != null) {
			if (_list != null) {
				int ii = _ei.length;
				for (int i=0;i<ii;++i) {
					EntityGroup eg = _ei[i].getEntityGroup();
					if (eg != null) {
						eg.removeEntityItem(_ei[i]);
						if (eg.getEntityItemCount() == 0) {
							_list = clear(_list,eg);
						}
					}
				}
			}
			return _list;
		}
		return null;
	}

	/**
     * clear
     * @param _prof
     * @return
     * @author Anthony C. Liberto
     */
    public EntityList clear(Profile _prof) {
		return clear(getEntityList(_prof,false));
	}

	private EntityList clear(EntityList _list) {
		if (_list != null) {
			while(_list.getEntityGroupCount() > 0) {
				clear(_list,_list.getEntityGroup(0));
			}
			return _list;
		}
		return null;
	}

	private void clear(CartList _list) {
		clear(_list.getEntityList());
		clear(_list.getCutGroup());
		_list.dereference();
		return;
	}

	/**
     * @see java.util.Map#clear()
     * @author Anthony C. Liberto
     */
    public void clear() {
		Collection collect = values();
		if (collect != null) {
			CartList[] list = (CartList[])collect.toArray(new CartList[collect.size()]);
			if (list != null) {
				for (int i=0;i<list.length;++i) {
					if (list[i] != null) {
						clear(list[i]);
					}
				}
			}
		}
		return;
	}

	private String getCartKey(Profile _prof) {
		if (_prof != null) {
			return _prof.getEnterprise() + ":" + _prof.getOPWGID();
		}
		return null;
	}
}
