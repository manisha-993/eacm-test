/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ERolloverContainer.java,v $
 * Revision 1.2  2008/01/30 16:26:56  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:47  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:17  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:17  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:09  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/06/02 16:46:56  tony
 * PKUR-6CWGY6 commented out
 *
 * Revision 1.6  2005/04/13 15:45:29  tony
 * TIR USRO-R-PKUR-6BDDV6
 *
 * Revision 1.5  2005/02/10 19:01:12  tony
 * Button Animation
 *
 * Revision 1.4  2005/01/28 19:36:59  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/26 18:20:08  tony
 * JTest Formatting
 *
 * Revision 1.2  2005/01/14 19:54:33  tony
 * adjusted button logic to troubleshoot
 * Button disappearing issue.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:49  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2003/06/05 17:07:38  tony
 * added refreshAppearance logic to the toolbar for the container.
 * This will flow thru to the scrollable popup.
 * Adjusted the sizing logic on the scrollable popup.
 *
 * Revision 1.3  2003/05/21 20:43:07  tony
 * 50842
 *
 * Revision 1.2  2003/05/21 17:05:02  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.1.1.1  2003/03/03 18:03:52  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2002/11/07 16:58:31  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eobjects;
import com.elogin.*;
import com.ibm.eannounce.eforms.toolbar.EannounceButton;
import COM.ibm.eannounce.objects.*;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.Icon;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ERolloverContainer extends ERolloverButton implements SubActionContainer, SubAction, EannounceButton {
	private static final long serialVersionUID = 1L;
	private ActionListener al = null;
//	JPopupMenu popup = new JPopupMenu();
	private EScrollPopup popup = new EScrollPopup();
	private String key = null;

	/**
     * eRolloverContainer
     * @author Anthony C. Liberto
     */
    public ERolloverContainer() {
		super();
		popup.setLightWeightPopupEnabled(false);				//TIR USRO-R-PKUR-6BDDV6
		return;
	}

//MN21699161	public eRolloverContainer(Icon _icon) {
//MN21699161		super(_icon);
	/**
     * eRolloverContainer
     * @param _icon
     * @param _name
     * @author Anthony C. Liberto
     */
    public ERolloverContainer(Icon _icon, String _name) {		//MN21699161
		super(_icon,_name);										//MN21699161
		popup.setLightWeightPopupEnabled(false);				//TIR USRO-R-PKUR-6BDDV6
		return;
	}

	/**
     * eRolloverContainer
     * @param _string
     * @author Anthony C. Liberto
     */
    public ERolloverContainer(String _string) {
		super(_string);
		popup.setLightWeightPopupEnabled(false);				//TIR USRO-R-PKUR-6BDDV6
		return;
	}

	/**
     * setActionListener
     * @author Anthony C. Liberto
     * @param _al
     */
    public void setActionListener(ActionListener _al) {
		al = _al;
		return;
	}

	/**
     * setKey
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setKey(String _s) {
		key = new String(_s);
	}

    /**
     * getKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
		return key;
	}

	/**
     * setActionItem
     * @author Anthony C. Liberto
     * @param _eai
     */
    public void setActionItem(EANActionItem _eai) {
		setKey(_eai.getKey());
	}

//50827	public void adjustSubItems(EANActionItem[] _eai, boolean _bHasData) {
	/**
     * adjustSubItems
     * @author Anthony C. Liberto
     * @param _bHasData
     * @param _eai
     * @param _type
     */
    public void adjustSubItems(EANActionItem[] _eai, boolean _bHasData, int _type) {		//50827
		int ii = -1;
        clearSubItems();
		if (_eai == null) {
			setEnabled(false);
			return;
		}
		ii = _eai.length;
		if (ii == 0) {
			setEnabled(false);
		} else if (ii == 1) {
			if (_type == ACTION_PICK_LIST) {												//50827
				if (_eai[0] instanceof NavActionItem) {										//50827
					if (((NavActionItem)_eai[0]).isPicklist()) {							//50827
						addSubItem(_eai[0]);												//50827
						setEnabled(_bHasData);												//50827
					} else {																//50827
						setEnabled(false);													//50827
					}																		//50827
				} else {																	//50827
					setEnabled(false);														//50827
				}																			//50827
			} else if (_type == ACTION_NAVIGATE) {											//50827
				if (_eai[0] instanceof NavActionItem) {										//50827
					if (!((NavActionItem)_eai[0]).isPicklist()) {							//50827
						addSubItem(_eai[0]);												//50827
						setEnabled(_bHasData);												//50827
					} else {																//50827
						setEnabled(false);													//50827
					}																		//50827
				} else {																	//50827
					setEnabled(false);														//50827
				}																			//50827
			} else {																		//50827
				setActionItem(_eai[0]);
				addActionListener(al);
				setEnabled(_bHasData);
			}																				//50827
		} else if (_type == ACTION_PICK_LIST) {												//50827
			setEnabled(false);																//50827
			for (int i=0;i<ii;++i) {														//50827
				if (_eai[i] instanceof NavActionItem) {										//50827
					if (((NavActionItem)_eai[i]).isPicklist()) {							//50827
						addSubItem(_eai[i]);												//50827
						setEnabled(_bHasData);												//50827
					}																		//50827
				}																			//50827
			}																				//50827
		} else if (_type == ACTION_NAVIGATE) {												//50827
			setEnabled(false);																//50827
			for (int i=0;i<ii;++i) {														//50827
				if (_eai[i] instanceof NavActionItem) {										//50827
					if (!((NavActionItem)_eai[i]).isPicklist()) {							//50827
						addSubItem(_eai[i]);												//50827
						setEnabled(_bHasData);												//50827
					}																		//50827
				}																			//50827
			}																				//50827
		} else {
			for (int i=0;i<ii;++i) {
				addSubItem(_eai[i]);
			}
			setEnabled(_bHasData);
		}
	}

	/**
     * addSubItem
     * @author Anthony C. Liberto
     * @param _item
     */
    public void addSubItem(EANActionItem _item) {
		popup.add(new SubActionMenuItem(_item,getActionCommand(),al));
		return;
	}

	/**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseReleased(MouseEvent _me) {
		if (!isEnabled()) {
			return;
		}
		if (!isAnimate()) {
			super.mouseReleased(_me);
		}

		if (popup.getComponentCount() > 1 && contains(_me.getPoint())) {
			popup.show(_me.getComponent(), _me.getX(),_me.getY());
		}
		return;
	}

	/**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseEntered(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
		if (!popup.isShowing()) {
			raiseBorder();
		}
		return;
	}

	/**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseMoved(MouseEvent _me) {
		if (!isAnimate()) {
			return;
		}
		if (!popup.isShowing()) {
			raiseBorder();
		}
		return;
	}

    /**
     * clearSubItems
     *
     * @author Anthony C. Liberto
     */
    public void clearSubItems() {
		if (key != null) {
			key = null;
			removeActionListener(al);
			return;
		}
		while (popup.getMenuItemCount() > 0) {
			Component c = popup.getMenuItem(0);
			if (c instanceof SubActionMenuItem) {
				SubActionMenuItem item = (SubActionMenuItem)c;
				item.removeActionListener(al);
				item.removeAll();
			}
			popup.removeMenuItem(c);
		}
	}

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
		clearSubItems();
		popup.dereference();
		popup = null;
		al = null;
		removeAll();
	}

/*
 acl_20030505
*/
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
		if (popup != null) {
			popup.refreshAppearance();
		}
		return;
	}

    /**
     * refresh toolbar
     * TIR USRO-R-PKUR-6BDCVV
     * MN_button_disappear
     * @author Anthony C. Liberto
     */
    public void refreshButton() {
		if (popup != null && popup.isShowing()) {
			popup.setVisible(false);
		}
		super.refreshButton();
		return;
	}

	/**
	 * hidePopup
	 * PKUR-6CWGY6
	 * @author Tony
	 */
//	public void hidePopup() {
//		if (popup != null) {
//			popup.setVisible(false);
//		}
//		return;
//	}

	/**
	 * is popup showing
	 * PKUR-6CWGY6
	 * @return boolean
	 * @author tony
	 */
//	public boolean isPopupShowing() {
//		if (popup != null) {
//			return popup.isVisible();
//		}
//		return false;
//	}
}
