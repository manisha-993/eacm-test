/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2003/09/15
 * @author Anthony C. Liberto
 *
 * $Log: ETransitionGroup.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2005/09/08 17:58:55  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.6  2005/02/04 16:57:41  tony
 * JTest Formatter Third Pass
 *
 * Revision 1.5  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.4  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.3  2004/10/11 21:02:09  tony
 * added debug functionality.
 *
 * Revision 1.2  2004/09/23 20:34:27  tony
 * updated messaging
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2003/09/15 23:23:33  tony
 * created as a result of 52151
 *
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.*;
import java.util.Vector;
import java.awt.Component;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ETransitionGroup implements EAccessConstants {
	private Component comp = null;
	private Vector vct = new Vector();

	/**
     * eTransitionGroup
     * @param _comp
     * @author Anthony C. Liberto
     */
    public ETransitionGroup(Component _comp) {
		super();
		comp = _comp;
		return;
	}

	/**
     * process
     * @author Anthony C. Liberto
     */
    public void process() {
		process(vct,false);
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

	private void process(Vector _vct, boolean _report) {
		if (_vct != null && !_vct.isEmpty()) {
			boolean bSuccess = false;
			Vector tmp = new Vector();
			int ii = _vct.size();
			for (int i=0;i<ii;++i) {
				ETransitionItem eTran = (ETransitionItem)_vct.get(i);
				if (eaccess().isDebug()) {
					EAccess.appendLog("processing transaction(" + i + "): " + eTran.dump());
				}
				if (eTran.process(_report)) {
					bSuccess = true;
					eTran.clear();
					eTran = null;
				} else if (!_report) {
					tmp.add(eTran);
				}
			}
			if (_report) {
				return;
			}
			if (bSuccess) {
				if (eaccess().isDebug()) {
					EAccess.appendLog("    processed at least one bad transition");
				}
				process(tmp,false);
			} else {
				if (eaccess().isDebug()) {
					EAccess.appendLog("    did NOT process at least one bad transition");
				}
				process(tmp,true);
			}
		}
		return;
	}

	/**
     * add
     * @param _orig
     * @param _new
     * @author Anthony C. Liberto
     */
    public void add(EANAttribute _orig, EANAttribute _new) {
		ETransitionItem eItem = new ETransitionItem(_orig,_new);
		vct.add(eItem);
		return;
	}

	/**
     * clear
     * @author Anthony C. Liberto
     */
    public void clear() {
		vct.clear();
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		comp = null;
		if (vct != null) {
			vct.clear();
			vct = null;
		}
		return;
	}

	private class ETransitionItem {
		private EANAttribute origEAN = null;
		private EANAttribute newEAN = null;

		/**
         * eTransitionItem
         * @param _orig
         * @param _new
         * @author Anthony C. Liberto
         */
        protected ETransitionItem(EANAttribute _orig, EANAttribute _new) {
			origEAN = _orig;
			newEAN = _new;
			return;
		}

		/**
         * clear
         * @author Anthony C. Liberto
         */
        public void clear() {
			origEAN = null;
			newEAN = null;
			return;
		}

		/**
         * dump
         * @return
         * @author Anthony C. Liberto
         */
        public String dump() {
			if (origEAN != null && newEAN != null) {
				return "orig: " + origEAN.getKey() + " new: " + newEAN.getKey();
			}
			return "undefined";
		}

		/**
         * process
         * @param _report
         * @return
         * @author Anthony C. Liberto
         */
        public boolean process(boolean _report) {
			try {
				newEAN.put(origEAN.get());
				return true;
			} catch (StateTransitionException _ste) {
				if (_report) {
					_ste.printStackTrace();
					eaccess().showException(_ste,comp,ERROR_MESSAGE,OK);
				}
			} catch (EANBusinessRuleException _bre) {
				_bre.printStackTrace();
				eaccess().showException(_bre,comp,ERROR_MESSAGE,OK);
			}
			return false;
		}
	}

}
