/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: DefaultToolbarLayout.java,v $
 * Revision 1.3  2008/08/04 14:04:29  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.2  2008/02/20 21:06:41  wendy
 * Added support for excel import - RQ3522
 *
 * Revision 1.1  2007/04/18 19:45:24  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:15  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2005/09/08 17:59:08  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.15  2005/03/09 19:54:43  tony
 * 6542 toolbar update
 *
 * Revision 1.14  2005/03/03 21:46:41  tony
 * cr_FlagUpdate
 * Added Flag Modification Capability.
 *
 * Revision 1.13  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.12  2005/01/26 22:17:58  tony
 * JTest Modifications
 *
 * Revision 1.11  2005/01/18 23:02:27  tony
 * dwb_20050118 added save to cross table.
 *
 * Revision 1.10  2005/01/18 21:38:49  tony
 * USRO-R-JSTT-68RKKP
 *
 * Revision 1.9  2005/01/17 21:11:21  tony
 * removed pivot functionality
 *
 * Revision 1.8  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.7  2005/01/05 23:47:17  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.6  2004/08/02 21:38:25  tony
 * ChangeGroup modification.
 *
 * Revision 1.5  2004/06/22 18:06:47  tony
 * cr_2115
 *
 * Revision 1.4  2004/06/16 20:23:14  tony
 * copyAction
 *
 * Revision 1.3  2004/03/29 17:36:37  tony
 * cr_209046022
 *
 * Revision 1.2  2004/03/26 23:27:22  tony
 *  cr_209046022 -- first pass
 *
 * Revision 1.1.1.1  2004/02/10 16:59:48  tony
 * This is the initial load of OPICM
 *
 * Revision 1.17  2004/01/05 22:22:40  tony
 * 53067
 *
 * Revision 1.16  2003/10/21 19:49:39  tony
 * 52666
 * 52667
 *
 * Revision 1.15  2003/09/23 23:30:59  tony
 * 52351
 *
 * Revision 1.14  2003/08/26 21:17:09  tony
 * cr_TBD_3
 * update whereused-matrix & matrix-whereused functionality.
 *
 * Revision 1.13  2003/08/22 21:33:43  tony
 * 51726
 *
 * Revision 1.12  2003/08/13 21:05:31  tony
 * fixed mistyped shortcut
 *
 * Revision 1.11  2003/07/25 17:16:10  tony
 * updated restore capability
 *
 * Revision 1.10  2003/07/25 15:42:12  tony
 * updated restore logic.
 *
 * Revision 1.9  2003/07/23 20:41:57  tony
 * added and enhanced restore logic
 *
 * Revision 1.8  2003/07/22 23:30:32  tony
 * added back in restore toolbar items
 *
 * Revision 1.7  2003/06/19 16:06:29  tony
 * toolbar functionality update 1.2h
 *
 * Revision 1.6  2003/06/12 22:23:40  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.5  2003/05/21 17:05:01  tony
 * 50827 -- separated out picklist from navigate
 *
 * Revision 1.4  2003/05/15 22:10:06  tony
 * cleaned-up code to improve persistant lock
 * functionality.
 *
 * Revision 1.3  2003/03/20 01:57:08  tony
 * bookmarking and pinning added to the system.
 *
 * Revision 1.2  2003/03/12 23:51:17  tony
 * accessibility and column order
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2002/12/02 18:42:07  tony
 * 23398
 *
 * Revision 1.12  2002/11/20 16:30:49  tony
 * acl_20021120 -- added navigate and search functions to
 * menu and toolbar
 *
 * Revision 1.11  2002/11/15 19:57:22  tony
 * acl_20021115 added add to cart and add all to cart
 * to the navigate toolbar.
 *
 * Revision 1.10  2002/11/07 16:58:36  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.toolbar;
import com.ibm.eannounce.eobjects.EObject;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class DefaultToolbarLayout extends EObject {
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
    public static final ComboItem EDIT_FORM_BAR		= new ComboItem("toolbar.edit.form","form",1);
	/**
     * LOCK_BAR
     */
    public static final ComboItem LOCK_BAR			= new ComboItem("toolbar.lock","lock",2);
	/**
     * MATRIX_BAR
     */
    public static final ComboItem MATRIX_BAR		= new ComboItem("toolbar.matrx","mtrx",3);
	/**
     * CROSSTAB_BAR
     */
    public static final ComboItem CROSSTAB_BAR		= new ComboItem("toolbar.cross","cross",4);
	/**
     * RESTORE_BAR
     */
    public static final ComboItem RESTORE_BAR		= new ComboItem("toolbar.restore","rstr",5);
	/**
     * USED_BAR_PARENT
     */
    public static final ComboItem USED_BAR_PARENT	= new ComboItem("toolbar.used.parent","usedP",5);
	/**
     * USED_BAR_CHILD
     */
    public static final ComboItem USED_BAR_CHILD	= new ComboItem("toolbar.used.child","usedC",6);
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
    public static final ComboItem NAV_BAR_DUAL		= new ComboItem("toolbar.navigate.dual","navD",9);	//cr_209046022
	/**
     * QUERY_BAR
     */
    public static final ComboItem QUERY_BAR	= new ComboItem("toolbar.query","query",10);
	/**
     * getToolbars
     * @return
     * @author Anthony C. Liberto
     */
    public static ComboItem[] getToolbars() {
		ComboItem[] out = new ComboItem[13];
		out[0] = EDIT_HORZ_BAR;
		out[1] = EDIT_VERT_BAR;
		out[2] = EDIT_FORM_BAR;
		out[3] = LOCK_BAR;
		out[4] = MATRIX_BAR;
		out[5] = CROSSTAB_BAR;
		out[6] = RESTORE_BAR;
		out[7] = USED_BAR_PARENT;
		out[8] = USED_BAR_CHILD;
		out[9] = USED_BAR_TABLE;
		out[10] = NAV_BAR;
		out[11] = NAV_BAR_DUAL;
		out[12] = QUERY_BAR;
		return out;
	}

	/**
     * getDefaultLayout
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] getDefaultLayout(ComboItem _item) {
		return getDefaultLayout(_item.getIntKey());
	}

	/**
     * getDefaultLayout
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] getDefaultLayout(int _i) {
		if (_i == EDIT_HORZ_BAR.getIntKey()) {
			return generateDefaultEditLayout();
		} else if (_i == EDIT_VERT_BAR.getIntKey()) {
			return generateDefaultEditVertLayout();
		} else if (_i == LOCK_BAR.getIntKey()) {
			return generateDefaultLockLayout();
		} else if (_i == MATRIX_BAR.getIntKey()) {
			return generateDefaultMatrixLayout();
		} else if (_i == CROSSTAB_BAR.getIntKey()) {
			return generateDefaultCrossLayout();
		} else if (_i == RESTORE_BAR.getIntKey()) {
			return generateDefaultRestoreLayout();
		} else if (_i == USED_BAR_PARENT.getIntKey()) {
			return generateDefaultUsedParentLayout();
		} else if (_i == USED_BAR_CHILD.getIntKey()) {
			return generateDefaultUsedChildLayout();
		} else if (_i == USED_BAR_TABLE.getIntKey()) {
			return generateDefaultUsedTableLayout();
		} else if (_i == NAV_BAR.getIntKey()) {
			return generateDefaultNavigateLayout();
		} else if (_i == NAV_BAR_DUAL.getIntKey()) {
			return generateDefaultDualNavigateLayout();
		}else if (_i == QUERY_BAR.getIntKey()) {
			return generateDefaultQueryLayout();
		}
		return null;
	}

	/**
     * getAvailLayout
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] getAvailLayout(ComboItem _item) {
		return getAvailLayout(_item.getIntKey());
	}

	/**
     * getAvailLayout
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] getAvailLayout(int _i) {
		if (_i == EDIT_HORZ_BAR.getIntKey()) {
			return generateAvailEditLayout();
		} else if (_i == EDIT_VERT_BAR.getIntKey()) {
			return generateAvailEditVertLayout();
		} else if (_i == LOCK_BAR.getIntKey()) {
			return generateAvailLockLayout();
		} else if (_i == MATRIX_BAR.getIntKey()) {
			return generateAvailMatrixLayout();
		} else if (_i == CROSSTAB_BAR.getIntKey()) {
			return generateAvailCrossLayout();
		} else if (_i == RESTORE_BAR.getIntKey()) {
			return generateAvailRestoreLayout();
		} else if (_i == USED_BAR_PARENT.getIntKey()) {
			return generateAvailUsedParentLayout();
		} else if (_i == USED_BAR_CHILD.getIntKey()) {
			return generateAvailUsedChildLayout();
		} else if (_i == USED_BAR_TABLE.getIntKey()) {
			return generateAvailUsedTableLayout();
		} else if (_i == NAV_BAR.getIntKey()) {
			return generateAvailNavigateLayout();
		} else if (_i == NAV_BAR_DUAL.getIntKey()) {
			return generateAvailDualNavigateLayout();
		}else if (_i == QUERY_BAR.getIntKey()) {
			return generateAvailQueryLayout();
		}
		return null;
	}

	/**
     * generateDefaultEditLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultEditLayout() {
		ToolbarItem[] out = new ToolbarItem[39];
		out[0] = new ToolbarItem("save.gif","saveR","saveR",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("defSave.gif","dsave","dsave",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("saveAll.gif","saveA","saveA",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem();
		out[4] = new ToolbarItem("print.gif","print","print",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem();
		out[6] = new ToolbarItem("frze.gif","frze","frze",ToolbarItem.BUTTON_TYPE);			//51726
		out[7] = new ToolbarItem("thaw.gif","thaw","thaw",ToolbarItem.BUTTON_TYPE);			//51726
		out[8] = new ToolbarItem();															//51726
		out[9] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[10] = new ToolbarItem("copy.gif","copy","copy",ToolbarItem.BUTTON_TYPE);
		out[11] = new ToolbarItem("paste.gif","pste","pste",ToolbarItem.BUTTON_TYPE);
		out[12] = new ToolbarItem("duplicate.gif","dup","dup",ToolbarItem.BUTTON_TYPE);
		out[13] = new ToolbarItem();
		out[14] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);		//53067
		out[15] = new ToolbarItem("cncl.gif","rstA","rstA",ToolbarItem.BUTTON_TYPE);
		out[16] = new ToolbarItem("dcncl.gif","dcncl","dcncl",ToolbarItem.BUTTON_TYPE);
		out[17] = new ToolbarItem();
		out[18] = new ToolbarItem("spell.gif","spll","spll",ToolbarItem.BUTTON_TYPE);
		out[19] = new ToolbarItem();
		out[20] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[21] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);		//52666
		out[22] = new ToolbarItem();
		out[23] = new ToolbarItem("grid.gif","horz","horz",ToolbarItem.BUTTON_TYPE);
		out[24] = new ToolbarItem("vert.gif","vert","vert",ToolbarItem.BUTTON_TYPE);
		out[25] = new ToolbarItem("form.gif","form","form",ToolbarItem.BUTTON_TYPE);
		out[26] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[27] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);	//52351
		out[28] = new ToolbarItem("maint.gif","maint","maint",ToolbarItem.BUTTON_TYPE);	//cr_FlagUpdate
		out[29] = new ToolbarItem("lang.gif","toglTreeView","toglTreeView",ToolbarItem.BUTTON_TYPE);	//cr_6542
		out[30] = new ToolbarItem();
		out[31] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
//21545		out[23] = new toolbarItem("layout.gif","layout","layout",toolbarItem.BUTTON_TYPE);
		out[32] = new ToolbarItem();
		out[33] = new ToolbarItem("lock.gif","lock","lock",ToolbarItem.BUTTON_TYPE);
		out[34] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
		out[35] = new ToolbarItem();
//22501		out[27] = new toolbarItem("new.gif","crte","add",toolbarItem.BUTTON_TYPE);
		out[36] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.BUTTON_TYPE);		//22501
		out[37] = new ToolbarItem("waste.gif","remove","remove",ToolbarItem.BUTTON_TYPE);
		out[38] = new ToolbarItem("excel.gif","xl8r","xl8r",ToolbarItem.BUTTON_TYPE); //RQ3522
		return out;
	}

	/**
     * generateAvailEditLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailEditLayout() {
		ToolbarItem[] out = new ToolbarItem[31];
		out[0] = new ToolbarItem("save.gif","saveR","saveR",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("defSave.gif","dsave","dsave",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("saveAll.gif","saveA","saveA",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("print.gif","print","print",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("copy.gif","copy","copy",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem("paste.gif","pste","pste",ToolbarItem.BUTTON_TYPE);
		out[7] = new ToolbarItem("duplicate.gif","dup","dup",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);		//53067
		out[9] = new ToolbarItem("cncl.gif","rstA","rstA",ToolbarItem.BUTTON_TYPE);
		out[10] = new ToolbarItem("dcncl.gif","dcncl","dcncl",ToolbarItem.BUTTON_TYPE);
		out[11] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);
		out[12] = new ToolbarItem("spell.gif","spll","spll",ToolbarItem.BUTTON_TYPE);
		out[13] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[14] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);		//52666
		out[15] = new ToolbarItem("grid.gif","horz","horz",ToolbarItem.BUTTON_TYPE);
		out[16] = new ToolbarItem("vert.gif","vert","vert",ToolbarItem.BUTTON_TYPE);
		out[17] = new ToolbarItem("form.gif","form","form",ToolbarItem.BUTTON_TYPE);
		out[18] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[19] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);	//52351
		out[20] = new ToolbarItem("maint.gif","maint","maint",ToolbarItem.BUTTON_TYPE);	//cr_FlagUpdate
		out[21] = new ToolbarItem("lang.gif","toglTreeView","toglTreeView",ToolbarItem.BUTTON_TYPE);	//cr_6542
		out[22] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
//21545		out[17] = new toolbarItem("layout.gif","layout","layout",toolbarItem.BUTTON_TYPE);
		out[23] = new ToolbarItem("lock.gif","lock","lock",ToolbarItem.BUTTON_TYPE);
		out[24] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
//22501		out[19] = new toolbarItem("new.gif","crte","add",toolbarItem.BUTTON_TYPE);
		out[25] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.BUTTON_TYPE);			//22501
		out[26] = new ToolbarItem("waste.gif","remove","remove",ToolbarItem.BUTTON_TYPE);
		out[27] = new ToolbarItem("excel.gif","xl8r","xl8r",ToolbarItem.BUTTON_TYPE);//RQ3522
		out[28] = new ToolbarItem("frze.gif","frze","frze",ToolbarItem.BUTTON_TYPE);		//51726
		out[29] = new ToolbarItem("thaw.gif","thaw","thaw",ToolbarItem.BUTTON_TYPE);		//51726
		out[30] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultEditVertLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultEditVertLayout() {
		ToolbarItem[] out = new ToolbarItem[37];
		out[0] = new ToolbarItem("save.gif","saveR","saveR",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("defSave.gif","dsave","dsave",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("saveAll.gif","saveA","saveA",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem();
		out[4] = new ToolbarItem("print.gif","print","print",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem();
		out[6] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[7] = new ToolbarItem("copy.gif","copy","copy",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("paste.gif","pste","pste",ToolbarItem.BUTTON_TYPE);
		out[9] = new ToolbarItem("duplicate.gif","dup","dup",ToolbarItem.BUTTON_TYPE);
		out[10] = new ToolbarItem();
		out[11] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);		//53067
		out[12] = new ToolbarItem("cncl.gif","rstA","rstA",ToolbarItem.BUTTON_TYPE);
		out[13] = new ToolbarItem("dcncl.gif","dcncl","dcncl",ToolbarItem.BUTTON_TYPE);
		out[14] = new ToolbarItem();
		out[15] = new ToolbarItem("spell.gif","spll","spll",ToolbarItem.BUTTON_TYPE);
		out[16] = new ToolbarItem();
		out[17] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[18] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);		//52666
		out[19] = new ToolbarItem();
		out[20] = new ToolbarItem("grid.gif","horz","horz",ToolbarItem.BUTTON_TYPE);
		out[21] = new ToolbarItem("vert.gif","vert","vert",ToolbarItem.BUTTON_TYPE);
		out[22] = new ToolbarItem("form.gif","form","form",ToolbarItem.BUTTON_TYPE);
		out[23] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[24] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);	//52351
		out[25] = new ToolbarItem("maint.gif","maint","maint",ToolbarItem.BUTTON_TYPE);	//cr_FlagUpdate
		out[26] = new ToolbarItem("lang.gif","toglTreeView","toglTreeView",ToolbarItem.BUTTON_TYPE);	//cr_6542
		out[27] = new ToolbarItem();
		out[28] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
//21545		out[23] = new toolbarItem("layout.gif","layout","layout",toolbarItem.BUTTON_TYPE);
		out[29] = new ToolbarItem();
		out[30] = new ToolbarItem("lock.gif","lock","lock",ToolbarItem.BUTTON_TYPE);
		out[31] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
		out[32] = new ToolbarItem();
//22501		out[27] = new toolbarItem("new.gif","crte","add",toolbarItem.BUTTON_TYPE);
		out[33] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.BUTTON_TYPE);		//22501
		out[34] = new ToolbarItem("waste.gif","remove","remove",ToolbarItem.BUTTON_TYPE);
		out[35] = new ToolbarItem("excel.gif","xl8r","xl8r",ToolbarItem.BUTTON_TYPE);//RQ3522
		out[36] = new ToolbarItem("recToggle","recToggle", ToolbarItem.COMPONENT_TYPE);
		return out;
	}

	/**
     * generateAvailEditVertLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailEditVertLayout() {
		ToolbarItem[] out = new ToolbarItem[30];
		out[0] = new ToolbarItem("save.gif","saveR","saveR",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("defSave.gif","dsave","dsave",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("saveAll.gif","saveA","saveA",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("print.gif","print","print",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("copy.gif","copy","copy",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem("paste.gif","pste","pste",ToolbarItem.BUTTON_TYPE);
		out[7] = new ToolbarItem("duplicate.gif","dup","dup",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);		//53067
		out[9] = new ToolbarItem("cncl.gif","rstA","rstA",ToolbarItem.BUTTON_TYPE);
		out[10] = new ToolbarItem("dcncl.gif","dcncl","dcncl",ToolbarItem.BUTTON_TYPE);
		out[11] = new ToolbarItem("cnclS.gif","rstS","rstS",ToolbarItem.BUTTON_TYPE);
		out[12] = new ToolbarItem("spell.gif","spll","spll",ToolbarItem.BUTTON_TYPE);
		out[13] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[14] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);		//52666
		out[15] = new ToolbarItem("grid.gif","horz","horz",ToolbarItem.BUTTON_TYPE);
		out[16] = new ToolbarItem("vert.gif","vert","vert",ToolbarItem.BUTTON_TYPE);
		out[17] = new ToolbarItem("form.gif","form","form",ToolbarItem.BUTTON_TYPE);
		out[18] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[19] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);	//52351
		out[20] = new ToolbarItem("maint.gif","maint","maint",ToolbarItem.BUTTON_TYPE);	//cr_FlagUpdate
		out[21] = new ToolbarItem("lang.gif","toglTreeView","toglTreeView",ToolbarItem.BUTTON_TYPE);	//cr_6542
		out[22] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
//21545		out[17] = new toolbarItem("layout.gif","layout","layout",toolbarItem.BUTTON_TYPE);
		out[23] = new ToolbarItem("lock.gif","lock","lock",ToolbarItem.BUTTON_TYPE);
		out[24] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
//22501		out[19] = new toolbarItem("new.gif","crte","add",toolbarItem.BUTTON_TYPE);
		out[25] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.BUTTON_TYPE);		//22501
		out[26] = new ToolbarItem("waste.gif","remove","remove",ToolbarItem.BUTTON_TYPE);
		out[27] = new ToolbarItem("excel.gif","xl8r","xl8r",ToolbarItem.BUTTON_TYPE);//RQ3522 histLoadLotus
		out[28] = new ToolbarItem("recToggle","recToggle",ToolbarItem.COMPONENT_TYPE);
		out[29] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultLockLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultLockLayout() {
		ToolbarItem[] out = new ToolbarItem[6];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem();
		out[3] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		out[5] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailLockLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailLockLayout() {
		ToolbarItem[] out = new ToolbarItem[5];
		out[0] = new ToolbarItem("find.gif","find","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("ulck.gif","ulck","ulck",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultMatrixLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultMatrixLayout() {
		ToolbarItem[] out = new ToolbarItem[9];
		out[0] = new ToolbarItem("save.gif","save","save",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem();
		out[2] = new ToolbarItem("cncl.gif","cncl","cncl",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem();
		out[4] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[5] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);			//cr_TBD_3
		out[6] = new ToolbarItem();
		out[7] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailMatrixLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailMatrixLayout() {
		ToolbarItem[] out = new ToolbarItem[7];													//52667
		out[0] = new ToolbarItem("save.gif","save","save",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("cncl.gif","cncl","cncl",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[3] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);			//cr_TBD_3
		out[4] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultCrossLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultCrossLayout() {
		ToolbarItem[] out = new ToolbarItem[12];
		out[0] = new ToolbarItem("save.gif","save","save",ToolbarItem.BUTTON_TYPE);				//dwb_20050118
		out[1] = new ToolbarItem();																//dwb_20050118
		out[2] = new ToolbarItem("addcol.gif","pick","pick",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem();
		out[4] = new ToolbarItem("0row.gif","deRow","deRow",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("0col.gif","deCol","deCol",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem();
		out[7] = new ToolbarItem("nrow.gif","aRow","aRow",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("ncol.gif","aCol","aCol",ToolbarItem.BUTTON_TYPE);
		out[9] = new ToolbarItem();
		out[10] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[11] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailCrossLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailCrossLayout() {
		ToolbarItem[] out = new ToolbarItem[9];
		out[0] = new ToolbarItem("save.gif","save","save",ToolbarItem.BUTTON_TYPE);				//dwb_20050118
		out[1] = new ToolbarItem("addcol.gif","pick","pick",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("0row.gif","deRow","deRow",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("0col.gif","deCol","deCol",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem("nrow.gif","aRow","aRow",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("ncol.gif","aCol","aCol",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[7] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultRestoreLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultRestoreLayout() {
		ToolbarItem[] out = new ToolbarItem[6];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem();
		out[3] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		out[5] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailRestoreLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailRestoreLayout() {
		ToolbarItem[] out = new ToolbarItem[5];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("refresh.gif","rfrsh","rfrsh",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultUsedParentLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultUsedParentLayout() {
		ToolbarItem[] out = new ToolbarItem[1];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailUsedParentLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailUsedParentLayout() {
		ToolbarItem[] out = new ToolbarItem[1];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateDefaultUsedChildLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultUsedChildLayout() {
		ToolbarItem[] out = new ToolbarItem[5];
		out[0] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[1] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[2] = new ToolbarItem("rlink.gif","rLink","rLink",ToolbarItem.CONTAINER_TYPE);
		out[3] = new ToolbarItem();
		out[4] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailUsedChildLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailUsedChildLayout() {
		ToolbarItem[] out = new ToolbarItem[5];
		out[0] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[1] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[2] = new ToolbarItem("rlink.gif","rLink","rLink",ToolbarItem.CONTAINER_TYPE);
		out[3] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		return out;
	}

	/**
     * generateDefaultUsedTableLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultUsedTableLayout() {
		ToolbarItem[] out = new ToolbarItem[9];
		out[0] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[1] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);		//6554
		out[2] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[3] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[4] = new ToolbarItem("rlink.gif","rLink","rLink",ToolbarItem.CONTAINER_TYPE);
		out[5] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);			//cr_TBD_3
		out[6] = new ToolbarItem();
		out[7] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		return out;
	}
 
	/**
     * generateAvailUsedTableLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailUsedTableLayout() {
		ToolbarItem[] out = new ToolbarItem[8];
		out[0] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[1] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);		//6554
		out[2] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[3] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[4] = new ToolbarItem("rlink.gif","rLink","rLink",ToolbarItem.CONTAINER_TYPE);
		out[5] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);		//cr_TBD_3
		out[6] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[7] = new ToolbarItem();
		return out;
	}
	/**
     * generateDefaultQueryLayout
     * @return
     */
    public static ToolbarItem[] generateDefaultQueryLayout() {
		ToolbarItem[] out = new ToolbarItem[2];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		return out;
	}
	/**
     * generateDefaultQueryLayout
     * @return
     */
    public static ToolbarItem[] generateAvailQueryLayout() {
		ToolbarItem[] out = new ToolbarItem[2];
		out[0] = new ToolbarItem("find.gif","f/r","f/r",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("fltr.gif","fltr","fltr",ToolbarItem.BUTTON_TYPE);
		return out;
	}
	/**
     * generateDefaultNavigateLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultNavigateLayout() {
		ToolbarItem[] out = new ToolbarItem[29];
		out[0] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem();
		out[2] = new ToolbarItem("pin.gif","pin","pin",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("bookmark.gif","bookmark","bookmark",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		out[5] = new ToolbarItem("left.gif","prev","prev",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem();
		out[7] = new ToolbarItem("wg_plus.gif","add2cart","add2cart",ToolbarItem.BUTTON_TYPE);		//23398
		out[8] = new ToolbarItem("wg_plusa.gif","add2cartA","add2cartA",ToolbarItem.BUTTON_TYPE);	//23398
		out[9] = new ToolbarItem();
		out[10] = new ToolbarItem("nav.gif","nav","nav",ToolbarItem.CONTAINER_TYPE);				//acl_20021120
		out[11] = new ToolbarItem("navPick.gif","navP","navP",ToolbarItem.CONTAINER_TYPE);				//50827
		out[12] = new ToolbarItem("search.gif","search","search",ToolbarItem.CONTAINER_TYPE);	//acl_20021120
		out[13] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[14] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[15] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[16] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[17] = new ToolbarItem("report.gif","rprt","rprt",ToolbarItem.CONTAINER_TYPE);
		out[18] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[19] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);
		out[20] = new ToolbarItem("pdg.gif","pdg","pdg",ToolbarItem.CONTAINER_TYPE);		//USRO-R-JSTT-68RKKP
		out[21] = new ToolbarItem("extract.gif", "xtract", "xtract",ToolbarItem.CONTAINER_TYPE);
		out[22] = new ToolbarItem("copy.gif", "cpyA", "cpyA",ToolbarItem.CONTAINER_TYPE);		//copyAction
		out[23] = new ToolbarItem("abrView.gif", "abrS", "abrS",ToolbarItem.CONTAINER_TYPE);		//cr_2115
		out[24] = new ToolbarItem("setGroup.gif","sGrp","sGrp",ToolbarItem.CONTAINER_TYPE);		//chgroup
		out[25] = new ToolbarItem("leaveGroup.gif","cGrp","cGrp",ToolbarItem.BUTTON_TYPE);		//chgroup
		out[26] = new ToolbarItem("lockPanel.gif","viewP","viewP",ToolbarItem.BUTTON_TYPE);
		out[27] = new ToolbarItem("lockWGPanel.gif","viewPW","viewPW",ToolbarItem.BUTTON_TYPE);
		out[28] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailNavigateLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailNavigateLayout() {
		ToolbarItem[] out = new ToolbarItem[26];
		out[0] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("pin.gif","pin","pin",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("bookmark.gif","bookmark","bookmark",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("left.gif","prev","prev",ToolbarItem.BUTTON_TYPE);
//23398		out[2] = new toolbarItem("plus.gif","add2cart","add2cart",toolbarItem.BUTTON_TYPE);	//acl_20021114
//23398		out[3] = new toolbarItem("plusa.gif","add2cartA","add2cartA",toolbarItem.BUTTON_TYPE);	//acl_20021114
		out[4] = new ToolbarItem("wg_plus.gif","add2cart","add2cart",ToolbarItem.BUTTON_TYPE);		//23398
		out[5] = new ToolbarItem("wg_plusa.gif","add2cartA","add2cartA",ToolbarItem.BUTTON_TYPE);	//23398
//		out[2] = new toolbarItem("right.gif","next","next",toolbarItem.BUTTON_TYPE);
//hist_remove		out[2] = new toolbarItem("histM.gif","histM","histM",toolbarItem.BUTTON_TYPE);
//		out[3] = new toolbarItem("prune.gif","prune","prune",toolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem("nav.gif","nav","nav",ToolbarItem.CONTAINER_TYPE);				//acl_20021120
		out[7] = new ToolbarItem("navPick.gif","navP","navP",ToolbarItem.CONTAINER_TYPE);				//50827
		out[8] = new ToolbarItem("search.gif","search","search",ToolbarItem.CONTAINER_TYPE);	//acl_20021120
		out[9] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[10] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[11] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[12] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[13] = new ToolbarItem("report.gif","rprt","rprt",ToolbarItem.CONTAINER_TYPE);
		out[14] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[15] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);
		out[16] = new ToolbarItem("pdg.gif","pdg","pdg",ToolbarItem.CONTAINER_TYPE);		//USRO-R-JSTT-68RKKP
		out[17] = new ToolbarItem("extract.gif", "xtract", "xtract",ToolbarItem.CONTAINER_TYPE);
		out[18] = new ToolbarItem("copy.gif", "cpyA", "cpyA",ToolbarItem.CONTAINER_TYPE);	//copyAction
		out[19] = new ToolbarItem("abrView.gif", "abrS", "abrS",ToolbarItem.CONTAINER_TYPE);	//copyAction
//21729		out[11] = new toolbarItem("restore.gif","rstr","rstr",toolbarItem.BUTTON_TYPE);
		out[20] = new ToolbarItem("setGroup.gif","sGrp","sGrp",ToolbarItem.CONTAINER_TYPE);		//chgroup
		out[21] = new ToolbarItem("leaveGroup.gif","cGrp","cGrp",ToolbarItem.BUTTON_TYPE);		//chgroup
		out[22] = new ToolbarItem("lockPanel.gif","viewP","viewP",ToolbarItem.BUTTON_TYPE);
		out[23] = new ToolbarItem("lockWGPanel.gif","viewPW","viewPW",ToolbarItem.BUTTON_TYPE);
		out[24] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		out[25] = new ToolbarItem();
		return out;
	}
/*
 cr_209046022
 */
	/**
     * generateDefaultDualNavigateLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateDefaultDualNavigateLayout() {
		ToolbarItem[] out = new ToolbarItem[28];
		out[0] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem();
		out[2] = new ToolbarItem("pin.gif","pin","pin",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("bookmark.gif","bookmark","bookmark",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem();
		out[5] = new ToolbarItem("left.gif","prev","prev",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem();
		out[7] = new ToolbarItem("wg_plus.gif","add2cart","add2cart",ToolbarItem.BUTTON_TYPE);
		out[8] = new ToolbarItem("wg_plusa.gif","add2cartA","add2cartA",ToolbarItem.BUTTON_TYPE);
		out[9] = new ToolbarItem();
		out[10] = new ToolbarItem("nav.gif","nav","nav",ToolbarItem.CONTAINER_TYPE);
		out[11] = new ToolbarItem("navPick.gif","navP","navP",ToolbarItem.CONTAINER_TYPE);
		out[12] = new ToolbarItem("search.gif","search","search",ToolbarItem.CONTAINER_TYPE);
		out[13] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[14] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[15] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[16] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[17] = new ToolbarItem("report.gif","rprt","rprt",ToolbarItem.CONTAINER_TYPE);
		out[18] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[19] = new ToolbarItem("linkD.gif","linkD","linkD",ToolbarItem.CONTAINER_TYPE);
		out[20] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);
		out[21] = new ToolbarItem("pdg.gif","pdg","pdg",ToolbarItem.CONTAINER_TYPE);		//USRO-R-JSTT-68RKKP
		out[22] = new ToolbarItem("extract.gif", "xtract", "xtract",ToolbarItem.CONTAINER_TYPE);
		out[23] = new ToolbarItem("setGroup.gif","sGrp","sGrp",ToolbarItem.CONTAINER_TYPE);		//chgroup
		out[24] = new ToolbarItem("leaveGroup.gif","cGrp","cGrp",ToolbarItem.BUTTON_TYPE);		//chgroup
		out[25] = new ToolbarItem("lockPanel.gif","viewP","viewP",ToolbarItem.BUTTON_TYPE);
		out[26] = new ToolbarItem("lockWGPanel.gif","viewPW","viewPW",ToolbarItem.BUTTON_TYPE);
		out[27] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		return out;
	}

	/**
     * generateAvailDualNavigateLayout
     * @return
     * @author Anthony C. Liberto
     */
    public static ToolbarItem[] generateAvailDualNavigateLayout() {
		ToolbarItem[] out = new ToolbarItem[25];
		out[0] = new ToolbarItem("cut.gif","cut","cut",ToolbarItem.BUTTON_TYPE);
		out[1] = new ToolbarItem("pin.gif","pin","pin",ToolbarItem.BUTTON_TYPE);
		out[2] = new ToolbarItem("bookmark.gif","bookmark","bookmark",ToolbarItem.BUTTON_TYPE);
		out[3] = new ToolbarItem("left.gif","prev","prev",ToolbarItem.BUTTON_TYPE);
		out[4] = new ToolbarItem("wg_plus.gif","add2cart","add2cart",ToolbarItem.BUTTON_TYPE);
		out[5] = new ToolbarItem("wg_plusa.gif","add2cartA","add2cartA",ToolbarItem.BUTTON_TYPE);
		out[6] = new ToolbarItem("nav.gif","nav","nav",ToolbarItem.CONTAINER_TYPE);
		out[7] = new ToolbarItem("navPick.gif","navP","navP",ToolbarItem.CONTAINER_TYPE);
		out[8] = new ToolbarItem("search.gif","search","search",ToolbarItem.CONTAINER_TYPE);
		out[9] = new ToolbarItem("edit.gif","edit","edit",ToolbarItem.CONTAINER_TYPE);
		out[10] = new ToolbarItem("mtrx.gif","mtrx","mtrx",ToolbarItem.CONTAINER_TYPE);
		out[11] = new ToolbarItem("used.gif","used","used",ToolbarItem.CONTAINER_TYPE);
		out[12] = new ToolbarItem("new.gif","crte","crte",ToolbarItem.CONTAINER_TYPE);
		out[13] = new ToolbarItem("report.gif","rprt","rprt",ToolbarItem.CONTAINER_TYPE);
		out[14] = new ToolbarItem("link.gif","link","link",ToolbarItem.CONTAINER_TYPE);
		out[15] = new ToolbarItem("linkD.gif","linkD","linkD",ToolbarItem.CONTAINER_TYPE);
		out[16] = new ToolbarItem("flow.gif","wFlow","wFlow",ToolbarItem.CONTAINER_TYPE);
		out[17] = new ToolbarItem("pdg.gif","pdg","pdg",ToolbarItem.CONTAINER_TYPE);		//USRO-R-JSTT-68RKKP
		out[18] = new ToolbarItem("extract.gif", "xtract", "xtract",ToolbarItem.CONTAINER_TYPE);
		out[19] = new ToolbarItem("setGroup.gif","sGrp","sGrp",ToolbarItem.CONTAINER_TYPE);		//chgroup
		out[20] = new ToolbarItem("leaveGroup.gif","cGrp","cGrp",ToolbarItem.BUTTON_TYPE);		//chgroup
		out[21] = new ToolbarItem("lockPanel.gif","viewP","viewP",ToolbarItem.BUTTON_TYPE);
		out[22] = new ToolbarItem("lockWGPanel.gif","viewPW","viewPW",ToolbarItem.BUTTON_TYPE);
		out[23] = new ToolbarItem("restore.gif","rstr","rstr",ToolbarItem.BUTTON_TYPE);
		out[24] = new ToolbarItem();
		return out;
	}
}
