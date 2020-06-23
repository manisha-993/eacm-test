//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.toolbar;

import com.ibm.eacm.objects.EACMGlobals;

import java.util.*;
/**
 * this defines the toolbar for a specific 'action'
 * 
 * @author Wendy Stimpson
 */
// $Log: DefaultToolbarLayout.java,v $
// Revision 1.4  2014/01/22 20:40:30  wendy
// RCQ 288700 Apache OpenDocument support
//
// Revision 1.3  2013/09/19 15:09:49  wendy
// add abr queue icon to dual nav
//
// Revision 1.2  2013/09/17 13:26:36  wendy
// add abr queue
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class DefaultToolbarLayout implements EACMGlobals {
	/**
     * EDIT_HORZ_BAR
     */
    public static final ComboItem EDIT_HORZ_BAR		= new ComboItem("toolbar.edit.horz","horz",0);
	/**
     * EDIT_VERT_BAR
     */
    public static final ComboItem EDIT_VERT_BAR		= new ComboItem("toolbar.edit.vert","vert",1);
	/**
     * EDIT_FORM_BAR
     */
    public static final ComboItem EDIT_FORM_BAR		= new ComboItem("toolbar.edit.form","form",2);
	/**
     * LOCK_BAR
     */
    public static final ComboItem LOCK_BAR			= new ComboItem("toolbar.lock","lock",3);
	/**
     * MATRIX_BAR
     */
    public static final ComboItem MATRIX_BAR		= new ComboItem("toolbar.matrx","mtrx",4);
	/**
     * CROSSTAB_BAR
     */
    public static final ComboItem CROSSTAB_BAR		= new ComboItem("toolbar.cross","cross",5);
	/**
     * RESTORE_BAR
     */
    public static final ComboItem RESTORE_BAR		= new ComboItem("toolbar.restore","rstr",6);
	/**
     * USED_BAR_TABLE
     */
    public static final ComboItem USED_BAR_TABLE	= new ComboItem("toolbar.used.table","usedT",7);
	/**
     * NAV_BAR
     */
    public static final ComboItem NAV_BAR			= new ComboItem("toolbar.navigate","nav",8);
	/**
     * NAV_BAR_DUAL
     */
    public static final ComboItem NAV_BAR_DUAL	= new ComboItem("toolbar.navigate.dual","navD",9);	
	/**
     * QUERY_BAR
     */
    public static final ComboItem QUERY_BAR	= new ComboItem("toolbar.query","query",10);
	/**
     * RELATTRCROSSTAB_BAR
     */
    public static final ComboItem RELATTRCROSSTAB_BAR	= new ComboItem("toolbar.relattrcross","relattrcross",11);
	/**
     * ABRQUEUE_BAR
     */
    public static final ComboItem ABRQS_BAR			= new ComboItem("toolbar.abrqs","abrqs",12);

    public static final ComboItem[] TOOLBARS = {
    	EDIT_HORZ_BAR,
		EDIT_VERT_BAR,
		EDIT_FORM_BAR,
		LOCK_BAR,
		MATRIX_BAR,
		CROSSTAB_BAR,
		RESTORE_BAR,
		USED_BAR_TABLE,
		NAV_BAR,
		NAV_BAR_DUAL,
		QUERY_BAR,
		RELATTRCROSSTAB_BAR,
		ABRQS_BAR
    };

	/**
     * getDefaultLayout
     * @param _item
     * @return
     */
    public static ToolbarItem[] getDefaultLayout(ComboItem _item) {
    	int _i = _item.getIntKey();
		if (_i == EDIT_HORZ_BAR.getIntKey()) {
			return DEFAULT_EDIT_LAYOUT;
		} else if (_i == EDIT_VERT_BAR.getIntKey()) {
			return DEFAULT_VERTEDIT_LAYOUT;
		} else if (_i == EDIT_FORM_BAR.getIntKey()) {
			return DEFAULT_VERTEDIT_LAYOUT;
		} else if (_i == LOCK_BAR.getIntKey()) {
			return DEFAULT_LOCK_LAYOUT;
		} else if (_i == MATRIX_BAR.getIntKey()) {
			return DEFAULT_MATRIX_LAYOUT;
		} else if (_i == CROSSTAB_BAR.getIntKey()) {
			return DEFAULT_CROSS_LAYOUT;
		} else if (_i == RESTORE_BAR.getIntKey()) {
			return DEFAULT_RESTORE_LAYOUT;
		} else if (_i == USED_BAR_TABLE.getIntKey()) {
			return DEFAULT_WUSED_LAYOUT;
		} else if (_i == NAV_BAR.getIntKey()) {
			return DEFAULT_NAVIGATE_LAYOUT;
		} else if (_i == NAV_BAR_DUAL.getIntKey()) {
			return DEFAULT_DUALNAVIGATE_LAYOUT;
		}else if (_i == QUERY_BAR.getIntKey()) {
			return DEFAULT_QUERY_LAYOUT;
		}else if (_i == RELATTRCROSSTAB_BAR.getIntKey()) {
			return DEFAULT_RELATTRCROSS_LAYOUT;
		}else if (_i == ABRQS_BAR.getIntKey()) {
			return DEFAULT_ABRQS_LAYOUT;
		}
		
		return null;
	}

	/**
     * getAvailLayout - used in toolbar preferences
     * @param _item
     * @return
     */
    public static ToolbarItem[] getAvailLayout(ComboItem _item) {
    	int _i = _item.getIntKey();
		if (_i == EDIT_HORZ_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_EDIT_LAYOUT);
		} else if (_i == EDIT_VERT_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_VERTEDIT_LAYOUT);
		} else if (_i == EDIT_FORM_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_VERTEDIT_LAYOUT);
		} else if (_i == LOCK_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_LOCK_LAYOUT);
		} else if (_i == MATRIX_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_MATRIX_LAYOUT);
		} else if (_i == CROSSTAB_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_CROSS_LAYOUT);
		} else if (_i == RESTORE_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_RESTORE_LAYOUT);
		} else if (_i == USED_BAR_TABLE.getIntKey()) {
			return generateAvailLayout(DEFAULT_WUSED_LAYOUT);
		} else if (_i == NAV_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_NAVIGATE_LAYOUT);
		} else if (_i == NAV_BAR_DUAL.getIntKey()) {
			return generateAvailLayout(DEFAULT_DUALNAVIGATE_LAYOUT);
		}else if (_i == QUERY_BAR.getIntKey()) {
			return DEFAULT_QUERY_LAYOUT;
		}else if (_i == RELATTRCROSSTAB_BAR.getIntKey()) {
			return generateAvailLayout(DEFAULT_RELATTRCROSS_LAYOUT);
		}else if (_i == ABRQS_BAR.getIntKey()) {
			return DEFAULT_ABRQS_LAYOUT;
		}
		return null;
	}

    /**
     * get all available actions without the separators, add a separator at the end
     * @param out
     * @return
     */
    private static ToolbarItem[] generateAvailLayout(ToolbarItem[] out ) {
    	Vector<ToolbarItem> vct = new Vector<ToolbarItem>();
    	for (int i=0; i<out.length; i++){
    		if (out[i].equals(ToolbarItem.SEPARATOR)){
    			continue;
    		}
    		vct.add(out[i]);
    	}
    	vct.add(ToolbarItem.SEPARATOR);
    	out = new ToolbarItem[vct.size()];
    	vct.copyInto(out);
    	return out;
	}
    
    private static final ToolbarItem[] DEFAULT_EDIT_LAYOUT = {
    	new ToolbarItem("save.gif",SAVERECORD_ACTION),
		new ToolbarItem("defSave.gif",SAVEDEFAULT_ACTION),
		new ToolbarItem("saveAll.gif",SAVEALL_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("print.gif",PRINT_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("frze.gif",FREEZE_ACTION),
		new ToolbarItem("thaw.gif",THAW_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("cut.gif",CUT_ACTION),
		new ToolbarItem("copy.gif",COPY_ACTION),
		new ToolbarItem("paste.gif",PASTE_ACTION),
		new ToolbarItem("duplicate.gif",DUPLICATE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("cnclS.gif",RESETONEATTR_ACTION),
		new ToolbarItem("cncl.gif",RESETALLATTR_ACTION),
		new ToolbarItem("dcncl.gif",CANCELDEFAULT_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("spell.gif",SPELLCHK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("grid.gif",GRIDEDITOR_ACTION),
		new ToolbarItem("vert.gif",VERTEDITOR_ACTION),
		new ToolbarItem("form.gif",FORMEDITOR_ACTION),
		new ToolbarItem("mtrx.gif",EDITMTRX_ACTIONSET),
		new ToolbarItem("flow.gif",EDITWF_ACTIONSET),
		new ToolbarItem("maint.gif",FLAGMAINT_ACTION),
		new ToolbarItem("lang.gif",TOGGLENLSTREE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("refresh.gif",REFRESHFORM_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("lock.gif",LOCK_ACTION),
		new ToolbarItem("ulck.gif",UNLOCK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("new.gif",CREATE_ACTION),
		new ToolbarItem("waste.gif",REMOVENEW_ACTION),
		new ToolbarItem("excel.gif",IMPORTXLS_ACTION),
		new ToolbarItem("ods.png",IMPORTODS_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_VERTEDIT_LAYOUT = {
		new ToolbarItem("save.gif",SAVERECORD_ACTION),
		new ToolbarItem("defSave.gif",SAVEDEFAULT_ACTION),
		new ToolbarItem("saveAll.gif",SAVEALL_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("print.gif",PRINT_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("cut.gif",CUT_ACTION),
		new ToolbarItem("copy.gif",COPY_ACTION),
		new ToolbarItem("paste.gif",PASTE_ACTION),
		new ToolbarItem("duplicate.gif",DUPLICATE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("cnclS.gif",RESETONEATTR_ACTION),
		new ToolbarItem("cncl.gif",RESETALLATTR_ACTION),
		new ToolbarItem("dcncl.gif",CANCELDEFAULT_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("spell.gif",SPELLCHK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("grid.gif",GRIDEDITOR_ACTION),
		new ToolbarItem("vert.gif",VERTEDITOR_ACTION),
		new ToolbarItem("form.gif",FORMEDITOR_ACTION),
		new ToolbarItem("mtrx.gif",EDITMTRX_ACTIONSET),
		new ToolbarItem("flow.gif",EDITWF_ACTIONSET),
		new ToolbarItem("maint.gif",FLAGMAINT_ACTION),
		new ToolbarItem("lang.gif",TOGGLENLSTREE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("refresh.gif",REFRESHFORM_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("lock.gif",LOCK_ACTION),
		new ToolbarItem("ulck.gif",UNLOCK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("new.gif",CREATE_ACTION),
		new ToolbarItem("waste.gif",REMOVENEW_ACTION),
		new ToolbarItem("excel.gif",IMPORTXLS_ACTION),
		new ToolbarItem("ods.png",IMPORTODS_ACTION),
		new ToolbarItem("recToggle", ToolbarItem.COMPONENT_TYPE)
    };
 
    private static final ToolbarItem[] DEFAULT_LOCK_LAYOUT = {
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("ulck.gif",UNLOCK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("refresh.gif",REFRESH_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_ABRQS_LAYOUT = {
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("refresh.gif",REFRESH_ACTION)
    };
    private static final ToolbarItem[] DEFAULT_MATRIX_LAYOUT = {
    	new ToolbarItem("save.gif",MTRX_SAVE),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("cncl.gif",MTRX_CANCEL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("edit.gif",MTRXEDIT_ACTIONSET),
		new ToolbarItem("used.gif",MTRXWU_ACTIONSET),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_CROSS_LAYOUT = {
		new ToolbarItem("copy.gif",COPY_ACTION),
		new ToolbarItem("paste.gif",PASTE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("addcol.gif",MTRX_ADDCOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("0row.gif",MTRX_DELETEROW),
		new ToolbarItem("0col.gif",MTRX_DELETECOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("nrow.gif",MTRX_ADJUSTROW),
		new ToolbarItem("ncol.gif",MTRX_ADJUSTCOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_RELATTRCROSS_LAYOUT = {
		new ToolbarItem("save.gif",MTRX_SAVE),
		new ToolbarItem("cncl.gif",MTRX_CANCEL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("copy.gif",COPY_ACTION),
		new ToolbarItem("paste.gif",PASTE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("addcol.gif",MTRX_ADDCOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("0row.gif",MTRX_DELETEROW),
		new ToolbarItem("0col.gif",MTRX_DELETECOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("nrow.gif",MTRX_ADJUSTROW),
		new ToolbarItem("ncol.gif",MTRX_ADJUSTCOL),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION)
    };
    
    private static final ToolbarItem[] DEFAULT_RESTORE_LAYOUT = {
    	new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("restore.gif",RESTORE_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("refresh.gif",REFRESH_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_WUSED_LAYOUT = {
		new ToolbarItem("new.gif",WUCRT_ACTIONSET),
		new ToolbarItem("edit.gif",WUEDIT_ACTIONSET),
		new ToolbarItem("wg_plus.gif",ADD2CART_ACTION),
		new ToolbarItem("used.gif",WU_ACTIONSET),
		new ToolbarItem("link.gif",WULINK_ACTIONSET),
		new ToolbarItem("rlink.gif",WURLINK_ACTIONSET),
		new ToolbarItem("mtrx.gif",WUMTRX_ACTIONSET),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_QUERY_LAYOUT = {
    	new ToolbarItem("find.gif",FINDREP_ACTION),
    	new ToolbarItem("fltr.gif",FILTER_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_NAVIGATE_LAYOUT = {
    	new ToolbarItem("cut.gif",NAVCUT_ACTION),
    	new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
    	new ToolbarItem("print.gif",PRINT_ACTION),
    	ToolbarItem.SEPARATOR,
		new ToolbarItem("pin.gif",PIN_ACTION),
		new ToolbarItem("bookmark.gif",BOOKMARK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("left.gif",PREV_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("wg_plus.gif",ADD2CART_ACTION),
		new ToolbarItem("wg_plusa.gif",ADDALL2CART_ACTION),
		ToolbarItem.SEPARATOR,	
		new ToolbarItem("nav.gif",NAV_ACTIONSET), 	
		new ToolbarItem("navPick.gif",NAVPICK_ACTIONSET), 
		new ToolbarItem("search.gif",NAVSRCH_ACTIONSET),
		new ToolbarItem("edit.gif",NAVEDIT_ACTIONSET),
		new ToolbarItem("mtrx.gif",NAVMTRX_ACTIONSET),
		new ToolbarItem("used.gif",NAVWU_ACTIONSET),
		new ToolbarItem("new.gif",NAVCRT_ACTIONSET),
		new ToolbarItem("report.gif",NAVRPT_ACTIONSET),
		new ToolbarItem("link.gif",NAVLINK_ACTIONSET),
		new ToolbarItem("flow.gif",NAVWF_ACTIONSET),
		new ToolbarItem("pdg.gif",NAVPDG_ACTIONSET),
		new ToolbarItem("extract.gif",NAVXTRACT_ACTIONSET),
		new ToolbarItem("copy.gif", NAVCOPY_ACTIONSET),
		//new ToolbarItem("abrView.gif", NAVABRVIEW_ACTIONSET),
		new ToolbarItem("abrView.gif", ABRQS_ACTION),
		new ToolbarItem("setGroup.gif",NAVSETCGGRP_ACTIONSET),
		new ToolbarItem("leaveGroup.gif",CGRP_ACTION),
		new ToolbarItem("lockPanel.gif",VIEWP_ACTION),
		new ToolbarItem("lockWGPanel.gif",VIEWPW_ACTION),
		new ToolbarItem("restore.gif",RESTORE_ACTION)
    };

    private static final ToolbarItem[] DEFAULT_DUALNAVIGATE_LAYOUT = {
    	new ToolbarItem("cut.gif",NAVCUT_ACTION),
    	new ToolbarItem("find.gif",FINDREP_ACTION),
		new ToolbarItem("fltr.gif",FILTER_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("pin.gif",PIN_ACTION),
		new ToolbarItem("bookmark.gif",BOOKMARK_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("left.gif",PREV_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("wg_plus.gif",ADD2CART_ACTION),
		new ToolbarItem("wg_plusa.gif",ADDALL2CART_ACTION),
		ToolbarItem.SEPARATOR,
		new ToolbarItem("nav.gif",NAV_ACTIONSET), 	
		new ToolbarItem("navPick.gif",NAVPICK_ACTIONSET), 
		new ToolbarItem("search.gif",NAVSRCH_ACTIONSET),
		new ToolbarItem("edit.gif",NAVEDIT_ACTIONSET),
		new ToolbarItem("mtrx.gif",NAVMTRX_ACTIONSET),
		new ToolbarItem("used.gif",NAVWU_ACTIONSET),
		new ToolbarItem("new.gif",NAVCRT_ACTIONSET),
		new ToolbarItem("report.gif",NAVRPT_ACTIONSET),
		new ToolbarItem("link.gif",NAVLINK_ACTIONSET),
		new ToolbarItem("linkD.gif",NAVLINKD_ACTIONSET),
		new ToolbarItem("flow.gif",NAVWF_ACTIONSET),
		new ToolbarItem("pdg.gif",NAVPDG_ACTIONSET),
		new ToolbarItem("extract.gif", NAVXTRACT_ACTIONSET),
		new ToolbarItem("abrView.gif", ABRQS_ACTION),
		new ToolbarItem("setGroup.gif",NAVSETCGGRP_ACTIONSET),
		new ToolbarItem("leaveGroup.gif",CGRP_ACTION),
		new ToolbarItem("lockPanel.gif",VIEWP_ACTION),
		new ToolbarItem("lockWGPanel.gif",VIEWPW_ACTION),
		new ToolbarItem("restore.gif",RESTORE_ACTION)
    };
}
