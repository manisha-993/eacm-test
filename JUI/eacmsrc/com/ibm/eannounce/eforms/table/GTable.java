// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: GTable.java,v $
 * Revision 1.6  2012/04/05 17:51:16  wendy
 * jre142 and win7 changes
 *
 * Revision 1.5  2010/06/24 13:28:53  wendy
 * Fix cant edit number of relators cell in matrix
 *
 * Revision 1.4  2008/02/21 21:21:32  wendy
 * TIR7C2JUT-Freeze action is not working
 *
 * Revision 1.3  2008/02/01 15:45:26  wendy
 * Moved column size limit to user preferences
 *
 * Revision 1.2  2007/10/11 13:27:59  couto
 * MN33121008 - Preventing 100% CPU usage by defining the max amount of columns to be resized in the properties file.
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.9  2005/02/09 19:29:51  tony
 * JTest After Scout
 *
 * Revision 1.8  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.7  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.6  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.4  2004/11/19 18:01:51  tony
 * imporoved accessibilitiy and replayable log file functionatlity.
 *
 * Revision 1.3  2004/11/16 22:25:01  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.2  2004/10/22 22:14:43  tony
 * auto_sort/size
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.60  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.59  2003/12/19 18:42:19  tony
 * acl_20031219
 * updated logic to prevent error and null pointers when
 * painting or validating components.
 *
 * Revision 1.58  2003/12/12 21:01:02  tony
 * 53396
 *
 * Revision 1.57  2003/11/13 23:16:37  tony
 * comment
 *
 * Revision 1.56  2003/11/11 00:42:21  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.55  2003/11/10 15:35:01  tony
 * accessibility
 *
 * Revision 1.54  2003/11/07 19:17:32  tony
 * accessibility
 *
 * Revision 1.53  2003/11/03 16:04:11  tony
 * 52847
 *
 * Revision 1.52  2003/10/31 17:30:48  tony
 * 52783
 *
 * Revision 1.51  2003/10/29 19:10:42  tony
 * acl_20031029
 *
 * Revision 1.50  2003/10/29 00:15:06  tony
 * removed System.out. statements.
 *
 * Revision 1.49  2003/10/27 22:18:20  tony
 * updated logic to make ACCESSIBLE_ENABLED a boolean
 * that was not computed each time but instead a constant...
 * That will allow for the app to run a bit faster.
 * Parameterized accessibility on the table with the new
 * Parm.
 *
 * Revision 1.48  2003/10/21 18:28:59  tony
 * 52653
 *
 * Revision 1.47  2003/10/08 22:18:41  tony
 * updated getAccessible value to process the screen reader
 * string out of the standard queue.
 *
 * Revision 1.46  2003/10/08 19:25:01  tony
 * 52489
 *
 * Revision 1.45  2003/10/07 21:35:38  tony
 * added setlookandfeel to assist in accessibility
 * high contrast functionality.
 *
 * Revision 1.44  2003/10/06 20:06:47  tony
 * 52474
 *
 * Revision 1.43  2003/10/03 20:49:55  tony
 * updated accessibility.
 *
 * Revision 1.42  2003/10/03 16:39:11  tony
 * updated accessibility.
 *
 * Revision 1.41  2003/10/02 20:09:12  tony
 * accessibility update
 *
 * Revision 1.40  2003/10/02 18:23:26  tony
 * updated for accessibility...
 * logic for screen reader updated.
 *
 * Revision 1.39  2003/10/02 16:16:56  tony
 * updated accessibility
 *
 * Revision 1.38  2003/10/01 21:06:58  tony
 * accesssibility
 *
 * Revision 1.37  2003/10/01 20:39:15  tony
 * added accessibility logic to aid in the screen reader functionality.
 *
 * Revision 1.36  2003/09/17 16:47:01  tony
 * 52284
 *
 * Revision 1.35  2003/09/11 00:11:56  tony
 * 52152
 *
 * Revision 1.34  2003/09/10 20:55:01  tony
 * 52152
 *
 * Revision 1.33  2003/09/02 15:54:21  tony
 * 52008
 *
 * Revision 1.32  2003/08/27 16:01:17  tony
 * 51941
 *
 * Revision 1.31  2003/08/15 15:53:53  tony
 * cr_0805036452
 *
 * Revision 1.30  2003/08/15 15:38:45  tony
 * cr_0805036452
 *
 * Revision 1.29  2003/08/14 15:54:12  tony
 * 51766
 *
 * Revision 1.28  2003/08/13 21:04:18  tony
 * 51757
 *
 * Revision 1.27  2003/08/13 16:39:32  joan
 * 51725
 *
 * Revision 1.26  2003/07/11 16:59:22  tony
 * updated logic to allow for ctrl-v shortcut on table.
 *
 * Revision 1.25  2003/07/07 15:26:21  tony
 * updated logic per Wayne to only report 0 errors for
 * spell check when manually invoked.
 *
 * Revision 1.24  2003/06/20 22:34:37  tony
 * 1.2 modification.
 *
 * Revision 1.23  2003/06/16 23:28:12  tony
 * 51294
 *
 * Revision 1.22  2003/06/16 19:33:19  tony
 * 51294
 *
 * Revision 1.21  2003/06/16 19:30:31  tony
 * 51294
 *
 * Revision 1.20  2003/06/10 16:46:48  tony
 * 51260
 *
 * Revision 1.19  2003/05/30 21:09:23  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.18  2003/05/29 21:20:43  tony
 * adjusted setParm logic for single parm string.
 *
 * Revision 1.17  2003/05/09 16:51:28  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.16  2003/05/02 20:05:55  tony
 * 50504 -- adjusted messaging and this seems to have
 * resolved the problem in local test.
 *
 * Revision 1.15  2003/04/24 15:33:11  tony
 * updated logic to include preference for selection fore and
 * back ground.
 *
 * Revision 1.14  2003/04/23 20:59:09  tony
 * adjusted matrix table resizeing
 *
 * Revision 1.13  2003/04/16 17:21:15  tony
 * overrode getTransferHandler
 *
 * Revision 1.12  2003/04/15 22:01:33  tony
 * 50395 -- copy was looping.
 *
 * Revision 1.11  2003/04/14 21:38:25  tony
 * updated table Logic.
 *
 * Revision 1.10  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;

import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.NLSItem;
import java.util.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;												//013390
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;
import javax.accessibility.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract class GTable extends JTable implements EAccessConstants, EDisplayable, EAccessibleTable {
	/**
     * minWidth
     */
    protected int minWidth = 10;

	/**
     * findPt
     */
    protected Point findPt = new Point(-1,-1);
	private boolean editable = true;				//ep
	private boolean bModalCursor = false;
	/**
     * bUseBack
     */
    private boolean bUseBack = false;
	/**
     * bUseFont
     */
    private boolean bUseFont = true;
	/**
     * bUseFore
     */
    private boolean bUseFore = true;

	/**
     * GTable
     * @author Anthony C. Liberto
     */
    public GTable() {
		this(null, null, null);
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param dm
     * @author Anthony C. Liberto
     */
    public GTable(TableModel dm) {
		this(dm, null, null);
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param dm
     * @param cm
     * @author Anthony C. Liberto
     */
    public GTable(TableModel dm, TableColumnModel cm) {
		this(dm, cm, null);
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param dm
     * @param cm
     * @param sm
     * @author Anthony C. Liberto
     */
    public GTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
		super(dm,cm,sm);
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param numRows
     * @param numColumns
     * @author Anthony C. Liberto
     */
    public GTable(int numRows, int numColumns) {
		this(new DefaultTableModel(numRows, numColumns));
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param rowData
     * @param columnNames
     * @author Anthony C. Liberto
     */
    public GTable(final Vector rowData, final Vector columnNames) {
		super(rowData, columnNames);
		setRowHeight(getFont());
		return;
	}

	/**
     * GTable
     * @param rowData
     * @param columnNames
     * @author Anthony C. Liberto
     */
    public GTable(final Object[][] rowData, final Object[] columnNames) {
		super(rowData, columnNames);
		setRowHeight(getFont());
		return;
	}

	/**
     * @see javax.swing.JComponent#getTransferHandler()
     * @author Anthony C. Liberto
     */
    public TransferHandler getTransferHandler() {
		return eaccess().getTransferHandler();
	}

    /**
     * @see javax.swing.JTable#createDefaultTableHeader()
     * @author Anthony C. Liberto
     */
    protected JTableHeader createDefaultTableHeader() {
        return new RSTableHeader(columnModel);
    }

    /**
     * getDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public EDisplayable getDisplayable() {
		Container par = getParent();
		if (par instanceof EDisplayable) {
			return (EDisplayable)par;
		}
		return null;
	}

    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
		setUseBack(_b);
		setUseFore(_b);
		setUseFont(_b);
		return;
	}

    /**
     * setUseBack
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseBack(boolean _b) {
		bUseBack = _b;
		return;
	}

    /**
     * setUseFore
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFore(boolean _b) {
		bUseFore = _b;
		return;
	}

    /**
     * setUseFont
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseFont(boolean _b) {
		bUseFont = _b;
		return;
	}

	/**
     * @see javax.swing.JTable#getSelectionBackground()
     * @author Anthony C. Liberto
     */
    public Color getSelectionBackground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_BACKGROUND,DEFAULT_COLOR_SELECTION_BACKGROUND);
	}

	/**
     * @see javax.swing.JTable#getSelectionForeground()
     * @author Anthony C. Liberto
     */
    public Color getSelectionForeground() {
		return eaccess().getPrefColor(PREF_COLOR_SELECTION_FOREGROUND,DEFAULT_COLOR_SELECTION_FOREGROUND);
	}

	/**
     * @see java.awt.Component#getBackground()
     * @author Anthony C. Liberto
     */
    public Color getBackground() {
		if (eaccess().canOverrideColor() && bUseBack) {
			if (isEnabled()) {
				return eaccess().getBackground();
			} else {
				return eaccess().getDisabledBackground();
			}
		}
		return super.getBackground();
	}

	/**
     * @see java.awt.Component#getForeground()
     * @author Anthony C. Liberto
     */
    public Color getForeground() {
		if (eaccess().canOverrideColor() && bUseFore) {
			if (isEnabled()) {
				return eaccess().getForeground();
			} else {
				return eaccess().getDisabledForeground();
			}
		}
		return super.getForeground();
	}

	/**
     * @see java.awt.MenuContainer#getFont()
     * @author Anthony C. Liberto
     */
    public Font getFont() {
		if (EANNOUNCE_UPDATE_FONT && bUseFont) {
			return eaccess().getFont();
		}
		return super.getFont();
	}

	/**
     * isHorizontalTableFormat
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isHorizontalTableFormat() {
		return false;
	}

	/**
     * isMatrixTableFormat
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isMatrixTableFormat() {
		return false;
	}

	/**
     * isVerticalTableFormat
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isVerticalTableFormat() {
		return false;
	}

	/**
     * isHidden
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden(int _r) {
		return false;
	}

	/**
     * isMultiLineClass
     * @param _r
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isMultiLineClass(int _r) {
		return false;
	}

	/**
     * resizeCells
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
		int rr = getRowCount();
        int cc = getColumnCount();
        int[] width = new int[cc];
		//MN 33121008 - if we have too much columns, then resizing cells will produce a 100% CPU usage for a long time
        //int maxColumns = Integer.parseInt(eaccess().getString(EAccessConstants.MAX_COLUMNS));
        int maxColumns = Routines.toInt(eaccess().getPrefString(PREF_AUTO_SIZE_COLS_COUNT, DEFAULT_COL_SIZE_COUNT));
        
		if (!eaccess().canSize(rr)) {
			if (cc > maxColumns) {
				initRowHeight(); //TIR7C2JUT
			}else{ // num cols is ok so adjust width
				for (int r=0;r<rr;++r) {
					boolean bMultiLine = isMultiLineClass(r);								//21805
					for (int c=0;c<cc;++c) {
						Object o = getDirectValueAt(r,c);											//tableresize
						String str = "";													//tableresize
						if (o != null) {													//tableresize
							str = o.toString();												//tableresize
						}																	//tableresize
						if (bMultiLine) {													//21805
							if (isMatrixTableFormat()) {
								width[c] = Math.max(width[c],getMatrixLineWidth(str));
							} else {
								width[c] = Math.max(width[c],getMultiLineWidth(str));
							}
						} else {															//21805
							width[c] = Math.max(width[c],getWidth(str));
						}																	//21805
					}
				}
				processWidth(width);
				resizeAndRepaint();
		    }
			
			appendLog("GTable.resizeCells: Cells cannot be resized. "+rr+" rows exceeds the limit.");
			return;													//auto_sort/size
		}															//auto_sort/size

        int[] height = new int[rr];

        if (cc > maxColumns) {
        	//initRowHeight();//TIR7C2JUT
        	for (int r=0;r<rr;++r) { // not too many rows, so adjust heights
    			boolean bMultiLine = isMultiLineClass(r);								//21805
    			for (int c=0;c<cc;++c) {
    				Object o = getDirectValueAt(r,c);											//tableresize
    				String str = "";													//tableresize
    				if (o != null) {													//tableresize
    					str = o.toString();												//tableresize
    				}																	//tableresize
    				if (isHidden(r)) {			//19950
    					height[r] = -1;			//19950
    				} else if (bMultiLine) {											//21805
    					if (isVerticalTableFormat()) {									//21805
    						height[r] = Math.max(height[r], getMultiLineHeight(str));	//21805
    					} else if (isMatrixTableFormat()) {
    						height[r] = Math.max(height[r], getMatrixLineHeight(str));
    					} else if (isHorizontalTableFormat()) {							//21805
    						height[r] = Math.max(height[r], 1);							//21805
    					}																//21805
    				} else {					//19950
    					if (isHorizontalTableFormat()) {								//22424
    						height[r] = 1;
    					} else {														//22424
    						height[r] = Math.max(height[r],getHeight(str));
    					}																//22424
    				}							//19950
    			}
    		}
        	processHeight(height);														//22210
    		resizeAndRepaint();
        	appendLog("GTable.resizeCells: Cells cannot be resized. "+cc+" columns exceeds the limit of "+maxColumns);
        	return;
        }

		for (int r=0;r<rr;++r) {
			boolean bMultiLine = isMultiLineClass(r);								//21805
			for (int c=0;c<cc;++c) {
				Object o = getDirectValueAt(r,c);											//tableresize
				String str = "";													//tableresize
				if (o != null) {													//tableresize
					str = o.toString();												//tableresize
				}																	//tableresize
				if (isHidden(r)) {			//19950
					height[r] = -1;			//19950
				} else if (bMultiLine) {											//21805
					if (isVerticalTableFormat()) {									//21805
						height[r] = Math.max(height[r], getMultiLineHeight(str));	//21805
					} else if (isMatrixTableFormat()) {
						height[r] = Math.max(height[r], getMatrixLineHeight(str));
					} else if (isHorizontalTableFormat()) {							//21805
						height[r] = Math.max(height[r], 1);							//21805
					}																//21805
				} else {					//19950
					if (isHorizontalTableFormat()) {								//22424
						height[r] = 1;
					} else {														//22424
						height[r] = Math.max(height[r],getHeight(str));
					}																//22424
				}							//19950
				if (bMultiLine) {													//21805
					if (isMatrixTableFormat()) {
						width[c] = Math.max(width[c],getMatrixLineWidth(str));
					} else {
						width[c] = Math.max(width[c],getMultiLineWidth(str));
					}
				} else {															//21805
					width[c] = Math.max(width[c],getWidth(str));
				}																	//21805
			}
		}
		processWidth(width);
		processHeight(height);														//22210
		resizeAndRepaint();
	}

	/**
     * resizeEditColumn
     * @param _col
     * @author Anthony C. Liberto
     */
    public void resizeEditColumn(int _col) {										//23239
		int rr = getRowCount();														//23239
		int width = 0;
        RSTableColumn tc = null;														//23239
		for (int r=0;r<rr;++r) {													//23239
			boolean bMultiLine = isMultiLineClass(r);								//23239
			String str = getString(r,_col,false);									//23239
			if (bMultiLine) {														//23239
				if (isMatrixTableFormat()) {
					width = Math.max(width,getMatrixLineWidth(str));
				} else {
					width = Math.max(width,getMultiLineWidth(str));
				}
			} else {																//23239
				width = Math.max(width,getWidth(str));								//23239
			}																		//23239
		}																			//23239

		tc = (RSTableColumn)((RSTableColumnModel)getColumnModel()).getColumn(_col);		//23420
		if (tc != null && tc.isResizable()) {										//23420
			int w = Math.max(getHeaderWidth(tc), width);							//23239
			w += tc.getPreferredWidthAdjustment();									//23239
			tc.setWidth(w);															//23239
			tc.setPreferredWidth(w);												//23239
		}																			//23239
		return;																		//23239
	}																				//23239

	/**
     * refreshEditRow
     * @param _row
     * @author Anthony C. Liberto
     */
    public void resizeEditRow(int _row) {											//23317
		int cc = getColumnCount();													//23317
		int height = 0;																//23317
		FontMetrics fm = null;
        int h = -1;
        int iHeight = -1;
        for (int c=0;c<cc;++c) {													//23317
			String str = getString(_row,c,false);									//23317
			if (isVerticalTableFormat()) {											//50379
				height = Math.max(height, getMultiLineHeight(str));					//50379
			} else if (isMatrixTableFormat()) {
				height = Math.max(height, getMatrixLineHeight(str));
			} else {																//50379
				height = Math.max(height, getHeight(str));							//23317
			}																		//50379
		}																			//23317
		fm = getFontMetrics(getFont());
		h = fm.getHeight();														//23317
//51757		int h = fm.getHeight() + 5;													//51294

//51757		int iHeight = (Math.max(1,height) * h);										//23317
		iHeight = (Math.max(1,height) * h) + 10;								//51757
		setRowHeight(_row, iHeight);												//23317
		return;																		//23317
	}																				//23317

    /**
     * Rows need some initial height for freeze, if num rows or cols exceeds the limit for resizecells
     * do this
     * TIR7C2JUT-Freeze action is not working
     */
    private void initRowHeight() {
		int height = getRowHeight();
		for (int r=0;r<getRowCount();++r) {
			setRowHeight(r, height);
		}
	}
    
	/**
     * processHeight
     * @param _height
     * @return
     * @author Anthony C. Liberto
     */
    protected int processHeight(int[] _height) {
		int rr = _height.length;
		int out = 0;
		int h = getRowHeight();
		for (int r=0;r<rr;++r) {
			if (isRowFiltered(r) || _height[r] < 0)	{	//19950
				setRowHeight(r,0);
			} else {
				if (_height[r] == 1) {												//52008
					int height = (Math.max(1,_height[r])) * h;
					setRowHeight(r, height);
					out += height;
				} else {															//52008
					FontMetrics fm = getFontMetrics(getFont());						//52008
					int height = ((Math.max(1,_height[r])) * fm.getHeight());		//52008
					setRowHeight(r, height);										//52008
					out += height;													//52008
				}																	//52008
			}
		}
		return out;
	}

	/**
     * processWidth
     * @param _width
     * @return
     * @author Anthony C. Liberto
     */
    protected int processWidth(int[] _width) {
		int out = 0;
		int cc = _width.length;
		for (int c=0;c<cc;++c) {
			RSTableColumn tc = (RSTableColumn)getColumnModel().getColumn(c);
			int w = Math.max(getHeaderWidth(tc), _width[c]);
			w += tc.getPreferredWidthAdjustment();
			out += w;
			tc.setWidth(w);
			tc.setPreferredWidth(w);
		}
		return out;
	}

	/**
     * resizeColumn
     * @param _col
     * @author Anthony C. Liberto
     */
    public void resizeColumn(int _col) {
		RSTableColumn tc = (RSTableColumn)getColumnModel().getColumn(_col);
		int rr = getRowCount();
        int width = 0;
        int w = -1;
        if (!tc.getResizable()) {
            return;
		}
		for (int r=0;r<rr;++r) {
			String str = getString(r,_col,false);
			width = Math.max(width,getWidth(str));
		}
		w = Math.max(getHeaderWidth(tc),width);		//acl_20021209
		tc.setWidth(w);									//acl_20021209
		tc.setPreferredWidth(w);						//acl_20021209
		resizeAndRepaint();
		return;
	}

	/**
     * resizeRow
     * @param _r
     * @author Anthony C. Liberto
     */
    public void resizeRow(int _r) {
        int cc = getColumnCount();
        int height = 1;
        if (isRowFiltered(_r)) {
            return;
		}
		for (int c=0;c<cc;++c) {
			String str = getString(_r,c,false);
			height = Math.max(height, getHeight(str));
		}
		setRowHeight(_r, height * getRowHeight());
		resizeAndRepaint();
		return;
	}

	/**
     * getHeight
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    public int getHeight(String _str) {
		return Routines.getCharacterCount(_str,RETURN);
	}

	/**
     * getMultiLineHeight
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    public int getMultiLineHeight(String _str) {									//21805
        String str = Routines.splitString(_str,80);									//21805
		return getHeight(str);														//21805
	}																				//21805

	/**
     * getMultiLineWidth
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    protected int getMultiLineWidth(String _str) {									//21805
		String str = Routines.splitString(_str,80);									//21805
		return getWidth(str);														//21805
	}																				//21805

	/**
     * getMatrixLineHeight
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    public int getMatrixLineHeight(String _str) {									//21805
		return Math.max(1,getHeight(_str));											//52284
//52284		return getHeight(_str);														//21805
	}																				//21805

	/**
     * getMatrixLineWidth
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    protected int getMatrixLineWidth(String _str) {									//21805
		return getWidth(_str);														//21805
	}																				//21805

	/**
     * getWidth
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    protected int getWidth(String _str) {
		FontMetrics fm = null;
        if (_str != null) {															//52847
			if (_str.indexOf('\n') >= 0) {
				if (isVerticalTableFormat() || isMatrixTableFormat()) {				//21805
					return getWidth(Routines.getStringArray(_str,RETURN));
				} else {															//21805
				    return getWidth(Routines.getStringArray(Routines.truncate(_str,164),RETURN),0);				//21805
				}																	//21805
			}
			fm = getFontMetrics(getFont());
			return fm.stringWidth(Routines.truncate(_str,164));						//21805
		}
		return 0;																	//52847
	}

	private int getWidth(String[] _str) {
		if (_str != null) {															//53396
			FontMetrics fm = getFontMetrics(getFont());
			if (fm != null) {														//53396
				int ii = _str.length;
				int w = 0;
				for (int i=0;i<ii;++i) {
					w = Math.max(w,fm.stringWidth(_str[i]));
				}
				return w;
			}																		//53396
		}																			//53396
		return 30;																	//53396
	}

	private int getWidth(String[] _str, int _i) {									//21805
		FontMetrics fm = getFontMetrics(getFont());									//21805
		return fm.stringWidth(_str[_i]);											//21805
	}																				//21805

	/**
     * getHeaderWidth
     * @param _tc
     * @return
     * @author Anthony C. Liberto
     */
    protected int getHeaderWidth(RSTableColumn _tc) {
		Object o = _tc.getHeaderValue();
		String str = "";
		if (o != null) {
			str = o.toString();
		}
		return Math.max(_tc.getMinimumPreferredWidth(),getWidth(str));
	}

	/**
     * setRowHeight
     * @param _font
     * @author Anthony C. Liberto
     */
    public void setRowHeight(Font _font) {
		if (_font != null) {
			FontMetrics fm = getFontMetrics(_font);
			setRowHeight(fm.getHeight() + 4);
		}
		return;
	}


	/**
     * isEditable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable() {					//ep
        return editable;							//ep
	}												//ep

	/**
     * setEditable
     * @param b
     * @author Anthony C. Liberto
     */
    public void setEditable(boolean b) {			//ep
		editable = b;								//ep
		return;										//ep
	}												//ep

	/**
     * finishEditing
     * @author Anthony C. Liberto
     */
    public void finishEditing() {
		if (isEditing()) {
			TableCellEditor tce = getCellEditor();
			tce.stopCellEditing();
		}
		return;
	}

	/**
     * isColumnVisible
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isColumnVisible(int _col) {
		TableColumn tc = null;
        if (_col < 0 || _col >= getColumnCount()) {
            return true;
		}
		tc = getColumnModel().getColumn(_col);
		if (tc.getWidth() == 0) {
            return false;
		}
		return true;
	}

	/**
     * @see javax.swing.JTable#getSelectedRows()
     * @author Anthony C. Liberto
     */
    public int[] getSelectedRows() {										//20010423
		int rr[] = super.getSelectedRows();									//20010423
		LinkedList l = new LinkedList();									//20010423
		int ii = -1;
        int[] out = null;
        for (int r=0;r<rr.length;++r) {										//20010423
			if (getRowHeight(rr[r]) > 0) {									//20010423
				l.add(new Integer(rr[r]));
			}									//20010423
		}																	//20010423
	    ii = l.size();													//20010423
		if (rr.length == ii) {												//20010423
			return rr;
		}														//20010423
		out = new int[ii];											//20010423
		for (int i=0;i<ii;++i) {												//20010423
			out[i] = ((Integer)l.get(i)).intValue();
		}						//20010423
		return out;															//20010423
	}																		//20010423

	/**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public void changeSelection(int _r, int _c, boolean _toggle, boolean _xtnd) {
		if (isEmpty()) {
			return;
		}
		if (isEditing()) {		//acl_20021015
			return;				//acl_20021015
		}
		if (isBusy() && !isModalCursor()) {
			return;
		}
		if (isModalBusy()) {
			return;
		}
		super.changeSelection(_r,_c,_toggle,_xtnd);
		return;
	}

	/**
     * getNextColumn
     * @param c
     * @param m
     * @return
     * @author Anthony C. Liberto
     */
    public int getNextColumn(int c, int m) {
		int col = c + m;
		int cc = getColumnCount();
		if (col >= cc || col < 0) {
			return c;
		}
		while (!isColumnVisible(col)) {
			col = col + m;
			if (col >= cc || col < 0) {
				return c;
			}
		}
		return col;
	}

	/**
     * isValidCell
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidCell() {
		return isValidCell(getSelectedRow(), getSelectedColumn());
	}

	/**
     * isValidCell
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidCell(int r, int c) {
		if (r < 0 || r >= getRowCount()) {
			return false;

		} else if (c < 0 || c >= getColumnCount()) {
			return false;
		}
		return true;
	}

	/**
     * resetSelection
     * @param r
     * @param c
     * @author Anthony C. Liberto
     */
    public void resetSelection(int r, int c) {										//012874
		clearSelection();															//012874
		if (isValidCell(r,c)) {														//012874
			setRowSelectionInterval(r,r);											//012874
			setColumnSelectionInterval(c,c);										//012874
		}																			//012874
		return;																		//012874
	}																				//012874

    /*****************************************
     *          OverRidden methods          **
     *
     * @return int
     * @param point
     *****************************************/

	public int rowAtPoint(Point point) {
		int y = point.y;
        int rowSpacing = getIntercellSpacing().height;
        int rowCount = getRowCount();
        int rowHeight = 0;
        if(y < 0) {
            return -1;
		}
		for(int i = 0; i < rowCount; i++) {
			rowHeight += getRowHeight(i) + rowSpacing;
			if(y < rowHeight) {
				return i;
			}
		}
		return -1;
	}

    /**
     * determine if the selected cell is on the screen or not.
     *
     * @return boolean
     * @param c
     * @param r
     */
	public boolean isCellVisible(int r, int c) {
		Rectangle cell = getCellRect(r,c,true);
		Rectangle vis = getVisibleRect();
		if (vis.contains(cell)) {
			return true;
		}
		return false;
	}


	/**
     * @see javax.swing.JTable#getCellRect(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle cellFrame = new Rectangle();
		TableColumnModel tcm = getColumnModel();				//52152
		TableColumn tc = null;									//52152
        cellFrame.height = getRowHeight(row);
		if (tcm != null) {										//52152
			if (column < 0 || column >= tcm.getColumnCount()) {	//52489
				return cellFrame;								//52489
			}													//52489
			tc = tcm.getColumn(column);							//52152
			if (tc != null) {									//52152
				cellFrame.width = tc.getWidth();				//52152
			}													//52152
		}														//52152
//52789		for (int i=0;i<row;i++) {
		for(int i=0;i<row && i<getRowCount();++i) {					//52789
			cellFrame.y += getRowHeight(i);
		}

		if (tcm != null) {										//52152
			for (int i=0;i<column && i<tcm.getColumnCount();i++) {
				tc = tcm.getColumn(i);							//52152
				if (tc != null) {								//52152
					cellFrame.x += tc.getWidth();				//52152
				}												//52152
			}													//52152
		}
		if (!includeSpacing) {
			Dimension spacing = getIntercellSpacing();
			cellFrame.setBounds(cellFrame.x + spacing.width/2, cellFrame.y + spacing.height/2, cellFrame.width - spacing.width, cellFrame.height - spacing.height);
		}
		return cellFrame;
	}

	/**
     * @see javax.swing.event.TableColumnModelListener#columnMarginChanged(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void columnMarginChanged(ChangeEvent e) {							//012870
		if (isEditing()) {														//012870
			if (editingCanStop()) {												//012870
				super.columnMarginChanged(e);									//012870

			} else {																//012870
				return;
			}															//012870
		}																		//012870
		super.columnMarginChanged(e);											//012870
		return;																	//012870
	}																			//012870

	/**
     * editingCanStop
     * @return
     * @author Anthony C. Liberto
     */
    public boolean editingCanStop() {											//012870
		return editingCanStop(getCellEditor());									//013544
	}																			//012870

	/**
     * editingCanStop
     * @param tce
     * @return
     * @author Anthony C. Liberto
     */
    public boolean editingCanStop(TableCellEditor tce) {						//013544
		if (tce != null) {
			return tce.stopCellEditing();
		}											//013544
		return true;
	}																			//013544

    /**
     * Find logic
     *
     * @return String
     * @param _forReplace
     * @param caseSens
     * @param findValue
     * @param inc
     * @param multiCol
     * @param selCol
     * @param selRow
     */
	public String getNextValue(String findValue, boolean multiCol, boolean caseSens, int inc, int selRow, int selCol, boolean _forReplace) {
		int rr = getRowCount();
		int cc = getColumnCount();
		String o = null;
        if (selCol < 0 || selCol >= cc) {
			selCol = 0;
		}
		if (selRow < 0 || selRow >= rr) {
			selRow = 0;
		}

		if (selRow != findPt.x || selCol != findPt.y) {													//013293
			o = searchThisLocation(findValue,caseSens,selRow,selCol,_forReplace);
		}									//013194
		if (o != null) {																					//013194
			return o;
		}																					//013194

		o =	searchThisColFromSelection(findValue,caseSens,inc,selRow,selCol,rr,_forReplace);						//013194
		if (o != null) {
			return o;
		}
		if (multiCol) {
			o = searchToEnd(findValue, caseSens, inc, selRow, selCol,rr,cc,_forReplace);
			if (o != null) {
				return o;
			}
//			if (showConfirm(OK_CANCEL,"msg3016",true) != FIRST_BUTTON)																			//012807
//				return null;																									//012807
			if (eaccess().showConfirm(eaccess().getPanel(FIND_PANEL),OK_CANCEL,"msg3016",true) != FIRST_BUTTON) {																			//012807
				return null;
			}
			o = searchFromBeg(findValue, caseSens, inc, selRow, selCol,rr,cc,_forReplace);
			if (o != null) {
				return o;
			}
		}

		o = searchThisColToSelection(findValue, caseSens, inc, selRow, selCol, rr,_forReplace);
		if (o != null) {
			return o;
		}
		setCode("msg3003");
		setTitle("msg3003");
		setParm(findValue);
//		showError();
		eaccess().showError(eaccess().getPanel(FIND_PANEL));
		return null;
	}

	/**
     * searchThisLocation
     * @param findValue
     * @param caseSens
     * @param r
     * @param c
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    public String searchThisLocation(String findValue, boolean caseSens, int r, int c, boolean _forReplace) {				//013194
		if (!isRowFiltered(r)) {																			//013194
			if (isColumnVisible(c)) {																	//013194
				String str = getString(r,c,_forReplace);															//013194
				if (isFound(str,findValue,caseSens)) {													//013194
					setFound(r,c);																		//013194
					repaint();																			//013194
					return str;																			//013194
				}																						//013194
			}																							//013194
		}																								//013194
		return null;																					//013194
	}																									//013194

	/**
     * searchFromBeg
     * @param findValue
     * @param caseSens
     * @param inc
     * @param selRow
     * @param selCol
     * @param rows
     * @param cols
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    public String searchFromBeg(String findValue, boolean caseSens, int inc, int selRow, int selCol, int rows, int cols, boolean _forReplace) {
		if (inc < 0) {
			for (int c=cols-1;c>selCol;--c) {										//20075
				if (isColumnVisible(c)) {											//20075
					for (int r=rows-1;r>=0;--r) {									//20075
						if (!isRowFiltered(r)) {									//20075
							String str = getString(r,c,_forReplace);				//20075
							if (isFound(str,findValue,caseSens)) {					//20075
								setFound(r,c);										//20075
								repaint();											//20075
								return str;											//20075
							}													    //20075
						}															//20075
					}																//20075
				}																	//20075
			}
		} else if (inc > 0) {
			for (int c=0;c<selCol;++c) {										   //20075
				if (isColumnVisible(c)) {										   //20075
					for (int r=0;r<rows;++r) {									   //20075
						if (!isRowFiltered(r)) {								   //20075
							String str = getString(r,c,_forReplace);			   //20075
							if (isFound(str,findValue,caseSens)) {				   //20075
								setFound(r,c);									   //20075
								repaint();											//20075
								return str;										   //20075
							}													   //20075
						}														   //20075
					}															   //20075
				}																	//20075
			}
		}
		return null;
	}

	/**
     * searchToEnd
     * @param findValue
     * @param caseSens
     * @param inc
     * @param selRow
     * @param selCol
     * @param rows
     * @param cols
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    public String searchToEnd(String findValue, boolean caseSens, int inc, int selRow, int selCol, int rows, int cols, boolean _forReplace) {
		if (inc < 0) {
			for (int c=selCol-1;c>=0;--c) {									//20075
				if (isColumnVisible(c)) {									//20075
					for (int r=rows-1; r>=0; --r) {							//20075
						if (!isRowFiltered(r)) {							//20075
							String str = getString(r,c,_forReplace);					//20075
							if (isFound(str,findValue,caseSens)) {			//20075
								setFound(r,c);								//20075
								repaint();									//20075
								return str;									//20075
							}												//20075
						}													//20075
					}														//20075
				}															//20075
			}
		} else if (inc > 0) {
			for (int c=selCol+1;c<cols;++c) {								//20075
				if (isColumnVisible(c)) {									//20075
					for (int r=0;r<rows;++r) {								//20075
						if (!isRowFiltered(r)) {							//20075
							String str = getString(r,c,_forReplace);					//20075
							if (isFound(str,findValue,caseSens)) {			//20075
								setFound(r,c);								//20075
								repaint();									//20075
								return str;									//20075
							}												//20075
						}													//20075
					}														//20075
				}															//20075
			}																//20075

		}
		return null;
	}

	/**
     * searchThisColToSelection
     * @param findValue
     * @param caseSens
     * @param inc
     * @param selRow
     * @param selCol
     * @param rows
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    public String searchThisColToSelection(String findValue, boolean caseSens, int inc, int selRow, int selCol, int rows, boolean _forReplace) {
		if (!isColumnVisible(selCol)) {
			return null;
		}
		if (inc < 0) {
			for (int r=rows -1;r>=selRow;--r) {
				if (!isRowFiltered(r)) {								//013136
					String str = getString(r,selCol,_forReplace);
					if (isFound(str,findValue,caseSens)) {
						setFound(r,selCol);
						repaint();									//013293
						return str;
					}
				}													//013136
			}
		} else if (inc > 0) {
			for (int r=0;r<=selRow;++r) {
				if (!isRowFiltered(r)) {								//013136
					String str = getString(r,selCol,_forReplace);
					if (isFound(str,findValue,caseSens)) {
						setFound(r,selCol);
						repaint();									//013293
						return str;
					}
				}													//013136
			}
		}
		return null;
	}

	/**
     * searchThisColFromSelection
     * @param findValue
     * @param caseSens
     * @param inc
     * @param selRow
     * @param selCol
     * @param rows
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    public String searchThisColFromSelection(String findValue, boolean caseSens, int inc, int selRow, int selCol, int rows, boolean _forReplace) {
		int start = -1;
        if (!isColumnVisible(selCol)) {
			return null;
		}
		start = selRow + inc;
		if (start < 0 || start >= rows) {
			return null;
		}
		if (inc < 0) {
			for (int r=start;r>=0;--r) {
				if (!isRowFiltered(r)) {								//013136
					String str = getString(r,selCol,_forReplace);
					if (isFound(str,findValue,caseSens)) {
						setFound(r,selCol);
						repaint();									//013293
						return str;
					}
				}													//013136
			}
		} else if (inc > 0) {
			for (int r=start;r<rows;++r) {
				if (!isRowFiltered(r)) {								//013136
					String str = getString(r,selCol,_forReplace);
					if (isFound(str,findValue,caseSens)) {
						setFound(r,selCol);
						repaint();									//013293
						return str;
					}
				}													//013136
			}
		}
		return null;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		removeAll();
		removeNotify();
		return;
	}

	/**
     * getString
     * @param r
     * @param c
     * @param _forReplace
     * @return
     * @author Anthony C. Liberto
     */
    protected abstract String getString(int r, int c,boolean _forReplace);
	/**
     * setFound
     * @param row
     * @param c
     * @author Anthony C. Liberto
     */
    protected abstract void setFound(int row, int c);
	/**
     * isFound
     * @param row
     * @param col
     * @return
     * @author Anthony C. Liberto
     */
    protected abstract boolean isFound(int row, int col);
	/**
     * isFound
     * @param str
     * @param find
     * @param caseSensitive
     * @return
     * @author Anthony C. Liberto
     */
    protected abstract boolean isFound(String str, String find, boolean caseSensitive);
	/**
     * isRowFiltered
     * @param r
     * @return
     * @author Anthony C. Liberto
     */
    protected abstract boolean isRowFiltered(int r);
	/**
     * getHeaderValueAt
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public String getHeaderValueAt(int _r, int _c) {
		return getValueAt(_r,_c).toString();
	}

	/**
     * @see java.awt.Component#isFocusable()
     * @author Anthony C. Liberto
     */
    public boolean isFocusable() {					//22738
		return hasRows()  && hasColumns();			//22738
	}												//22738

	/**
     * hasColumns
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasColumns() {
		return getColumnCount() > 0;
	}

	/**
     * hasRows
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasRows() {
		return getRowCount() > 0;
	}

	/**
     * isEmpty
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEmpty() {
		return !(hasRows() && hasColumns());
	}

	/**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getActiveProfile() {
		return eaccess().getActiveProfile();
	}

	/**
     * @see java.awt.Component#getCursor()
     * @author Anthony C. Liberto
     */
    public Cursor getCursor() {
		if (isModalCursor()) {
			return eaccess().getModalCursor();
		} else {
			EDisplayable disp = getDisplayable();
			if (disp != null) {
				return disp.getCursor();
			}
		}
		return eaccess().getCursor();
	}

    /**
     * setModalCursor
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalCursor(boolean _b) {
		bModalCursor = _b;
		return;
	}

    /**
     * isModalCursor
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalCursor() {
		return bModalCursor;
	}
/*
eaccess pass thru
start
*/
	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * getString
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public String getString(String _code) {
		return eaccess().getString(_code);
	}

	/**
     * getChar
     * @param _code
     * @return
     * @author Anthony C. Liberto
     */
    public char getChar(String _code) {
		return eaccess().getChar(_code);
	}

	/**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void appendLog(String _message) {
		EAccess.appendLog(_message);
		return;
	}

	/**
     * getNow
     * @param _format
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format) {
		return eaccess().getNow(_format);
	}

	/**
     * getNow
     * @param _format
     * @param _refreshTime
     * @return
     * @author Anthony C. Liberto
     */
    public String getNow(String _format, boolean _refreshTime) {
		return eaccess().getNow(_format,_refreshTime);
	}

	/**
     * setCode
     * @param _code
     * @author Anthony C. Liberto
     */
    public void setCode(String _code) {
		eaccess().setCode(_code);
		return;
	}

	/**
     * setTitle
     * @param _title
     * @author Anthony C. Liberto
     */
    public void setTitle(String _title) {
		eaccess().setTitle(_title);
		return;
	}

	/**
     * setParms
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(String _s) {
		eaccess().setParm(_s);
		return;
	}

	/**
     * setParms
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParms(String[] _s) {
		eaccess().setParms(_s);
		return;
	}

	/**
     * setParms
     * @param _s
     * @param _delim
     * @author Anthony C. Liberto
     */
    public void setParms(String _s, String _delim) {
		eaccess().setParms(_s,_delim);
		return;
	}
	/**
     * setParmCount
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setParmCount(int _i) {
		eaccess().setParmCount(_i);
		return;
	}

	/**
     * setParm
     * @param _i
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setParm(int _i, String _s) {
		eaccess().setParm(_i,_s);
		return;
	}

	/**
     * clearParms
     * @author Anthony C. Liberto
     */
    public void clearParms() {
		eaccess().clearParms();
		return;
	}

	/**
     * getParms
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getParms() {
		return eaccess().getParms();
	}

	/**
     * getMessage
     * @return
     * @author Anthony C. Liberto
     */
    public String getMessage() {
		return eaccess().getMessage();
	}

	/**
     * setMessage
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setMessage(String _s) {
		eaccess().setMessage(_s);
		return;
	}

	/**
     * showFYI
     * @author Anthony C. Liberto
     */
    public void showFYI() {
		eaccess().showFYI(this);
		return;
	}

	/**
     * showFYI
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showFYI(String _code) {
		eaccess().showFYI(this,_code);
		return;
	}

	/**
     * showError
     * @author Anthony C. Liberto
     */
    public void showError() {
		eaccess().showError(this);
		return;
	}

	/**
     * showError
     * @param _code
     * @author Anthony C. Liberto
     */
    public void showError(String _code) {
		eaccess().showError(this,_code);
		return;
	}

	/**
     * showConfirm
     * @param _buttons
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, boolean _clear) {
		return eaccess().showConfirm(this,_buttons, _clear);
	}

	/**
     * showInput
     * @return
     * @author Anthony C. Liberto
     */
    public String showInput() {
		return eaccess().showInput(this);
	}

	/**
     * show
     * @param _dialogType
     * @param _msgType
     * @param _buttons
     * @param _reset
     * @author Anthony C. Liberto
     */
    public void show(int _dialogType, int _msgType, int _buttons, boolean _reset) {
		eaccess().show(this,_dialogType,_msgType,_buttons,_reset);
		return;
	}

	/**
     * show
     * @param _c
     * @param _adp
     * @param _modal
     * @author Anthony C. Liberto
     */
    public void show(Component _c, AccessibleDialogPanel _adp, boolean _modal) {
		eaccess().show(_c, _adp,_modal);
		return;
	}

	/**
     * getImage
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public Image getImage(String _img) {
		return eaccess().getImage(_img);
	}

	/**
     * getImageIcon
     * @param _img
     * @return
     * @author Anthony C. Liberto
     */
    public ImageIcon getImageIcon(String _img) {
		return eaccess().getImageIcon(_img);
	}

	/**
     * setBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setBusy(boolean _b) {
		eaccess().setBusy(_b);
		return;
	}

	/**
     * isBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBusy() {
		return eaccess().isBusy();
	}

	/**
     * setModalBusy
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setModalBusy(boolean _b) {
		eaccess().setModalBusy(_b);
		return;
	}

	/**
     * isModalBusy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isModalBusy() {
		return eaccess().isModalBusy();
	}

	/**
     * isWindows
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean isWindows() {
		return eaccess().isWindows();
	}

	/**
     * setClipboardContents
     * @param _value
     * @param _type
     * @param _copied
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject setClipboardContents(String _value, String _type, Object _copied) {
		return eaccess().setClipboardContents(_value,_type,_copied);				//50395
	}

	/**
     * setClipboardContents
     * @param _value
     * @param _type
     * @param _cols
     * @param _copied
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject setClipboardContents(String[] _value, String _type, int _cols, Object _copied) {
		return eaccess().setClipboardContents(_value,_type,_cols,_copied);			//50395
	}

	/**
     * getClipboardContents
     * @return
     * @author Anthony C. Liberto
     */
    public Transferable getClipboardContents() {
		return eaccess().getClipboardContents();
	}

	/**
     * canPaste
     * @param _internal
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste(boolean _internal) {
		return eaccess().canPaste(_internal);
	}

	/**
     * getRemoteDatabaseInterface
     * @return
     * @author Anthony C. Liberto
     */
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
		return eaccess().getRemoteDatabaseInterface();
	}

	/**
     * setFilter
     * @param _filter
     * @author Anthony C. Liberto
     */
    public void setFilter(boolean _filter) {
		eaccess().setFilter(_filter);
		return;
	}

	/**
     * getLockList
     * @return
     * @author Anthony C. Liberto
     */
    public LockList getLockList() {
		return eaccess().getLockList();
	}

	/**
     * spellCheck
     * @param _s
     * @param _tc
     * @return
     * @author Anthony C. Liberto
     */
    public String spellCheck(String _s, JTextComponent _tc) {
		return eaccess().spellCheck(_s, _tc);
	}

	/**
     * initSpellCheck
     * @author Anthony C. Liberto
     */
    public void initSpellCheck() {
		eaccess().initSpellCheck();
		return;
	}

	/**
     * completeSpellCheck
     * @author Anthony C. Liberto
     */
    public void completeSpellCheck() {
		eaccess().completeSpellCheck(true);
		return;
	}

	/**
     * containsSpellError
     * @param _s
     * @param _tc
     * @return
     * @author Anthony C. Liberto
     */
    public boolean containsSpellError(String _s, JTextComponent _tc) {
		return eaccess().containsSpellError(_s,_tc);
	}

	/**
     * showScrollDialog
     * @param _message
     * @author Anthony C. Liberto
     */
    public void showScrollDialog(String _message) {
		eaccess().showScrollDialog(_message);
		return;
	}

	/**
     * getActiveNLSItem
     * @return
     * @author Anthony C. Liberto
     */
    public NLSItem getActiveNLSItem() {
		return eaccess().getActiveNLSItem();
	}

	/**
     * dBase
     * @return
     * @author Anthony C. Liberto
     */
    public DataBase dBase() {
		return eaccess().dBase();
	}

	/**
     * print
     * @param _c
     * @author Anthony C. Liberto
     */
    public void print(Component _c) {
		eaccess().print(_c);
		return;
	}

	/**
     * showConfirm
     * @param _buttons
     * @param _code
     * @param _clear
     * @return
     * @author Anthony C. Liberto
     */
    public int showConfirm(int _buttons, String _code, boolean _clear) {
		return eaccess().showConfirm(this,_buttons,_code,_clear);
	}

/*
eaccess pass thru
complete
*/
/*
 51260
 */
    /**
     * showException
     * @param _x
     * @param _icon
     * @param _buttons
     * @return
     * @author Anthony C. Liberto
     */
    public int showException(Exception _x, int _icon, int _buttons) {
    	return eaccess().showException(_x,this,_icon,_buttons);
    }

/*
 acl_20030710
 */
	/**
     * @see javax.swing.JComponent#processKeyBinding(javax.swing.KeyStroke, java.awt.event.KeyEvent, int, boolean)
     * @author Anthony C. Liberto
     */
    protected boolean processKeyBinding(KeyStroke _ks, KeyEvent _ke,int _cond, boolean _press) {
		if (_ke != null) {
			if (_ke.isControlDown()) {
				int iKeyCode = _ke.getKeyCode();
				if (iKeyCode == KeyEvent.VK_V) {
					return false;
				} else if (iKeyCode == KeyEvent.VK_C) {		//51766
					return false;							//51766
				} else if (iKeyCode == KeyEvent.VK_X) {		//51766
					return false;							//51766
				}
			} else if (_ke.isShiftDown()) {					//52474
				int iKeyCode = _ke.getKeyCode();			//52474
				if (iKeyCode == KeyEvent.VK_LEFT) {			//52474
					return false;							//52474
				}											//52474
			}
		}
		return super.processKeyBinding(_ks,_ke,_cond,_press);
	}

/*
 51941
 */
	/**
     * @see javax.swing.JTable#getRowHeight()
     * @author Anthony C. Liberto
     */
    public int getRowHeight() {
		FontMetrics fm = getFontMetrics(getFont());
		if (fm != null) {
			return fm.getHeight() + 7;			//52653
//52653			return fm.getHeight() + 10;
		}
		return super.getRowHeight();
	}

/*
 accessiblity
 the screen reader gets the accessibility from the renderer....
 for full integration we will need to update the renderers as well.
 */
	/**
     * updateContext
     *
     * @author Anthony C. Liberto
     * @return AccessibleContext
     * @param _ac
     * @param _col
     * @param _row
     */
    public AccessibleContext updateContext(AccessibleContext _ac, int _row, int _col) {
		if (EAccess.isArmed(ACCESSIBLE_ARM_FILE)) {
			if (_row < 0 || _col < 0) {
				return _ac;
			}
			if (_ac != null) {
				String sAccessibleContext = getAccessibleCell(_row,_col);
				if (sAccessibleContext != null) {
//					routines.test("=*=*=*=*=*     screenReader.output for (" + _row + ", " + _col + "):  " + sAccessibleContext + "     =*=*=*=*=*");
					_ac.setAccessibleName(sAccessibleContext);
				}
			}
		}
		return _ac;
	}

//	 ""		 --> default
//	 ".ctab" --> cross table
//	 ".horz" --> horizontal
//	 ".mtrx" --> matrix
//	 ".vert" --> vertical
	/**
     * getContext
     * @return
     * @author Anthony C. Liberto
     */
    protected String getContext() {
		return "";
	}

    /**
     * isAccessibleCellEditable
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAccessibleCellEditable(int _row, int _col) {
		return false;
	}

    /**
     * isAccessibleCellLocked
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isAccessibleCellLocked(int _row, int _col) {
		return false;
	}

    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAccessibleValueAt(int _row, int _col) {
		return getValueAt(_row,_col);
	}

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
		return getColumnName(_col);
	}

    /**
     * getAccessibleRowNameAt
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleRowNameAt(int _row) {
		return " " + _row;
	}

    /**
     * convertAccessibleColumn
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public int convertAccessibleColumn(int _col) {
		return convertColumnIndexToModel(_col);
	}

    /**
     * convertAccessibleRow
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public int convertAccessibleRow(int _row) {
		return _row;
	}

    /**
     * isCellFocused
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellFocused(int _row, int _col) {
		if (isFocusOwner()) {
			return  isRowAnchor(_row) && isColumnAnchor(_col);
		}
		return false;
	}

    /**
     * getAccessibleCell
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleCell(int _row, int _col) {
		boolean bEdit = false;
        boolean bLock = false;
        String[] parms = null;
        convertAccessibleColumn(_col);
		convertAccessibleRow(_row);
		bEdit = isAccessibleCellEditable(_row,_col);
		bLock = isAccessibleCellLocked(_row,_col);
		parms = new String[ACCESSIBLE_MAX];
		parms[ACCESSIBLE_EDIT_STATUS] = getAccessibleEditStatus(bEdit);
		parms[ACCESSIBLE_LOCK_STATUS] = bEdit ? getAccessibleLockStatus(bLock) : "";
		parms[ACCESSIBLE_ATTRIBUTE_TYPE] = getAccessibleAttributeType(_row,_col);
		parms[ACCESSIBLE_COLUMN_NAME] = getAccessibleColumnName(_col);
		parms[ACCESSIBLE_ROW_NAME] = getAccessibleRowName(_row);
		parms[ACCESSIBLE_VALUE] = getAccessibleValue(_row,_col);
		return eaccess().getMessage(getAccessibleCellCode(),parms);
	}

	/**
     * getAccessibleCellCode
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleCellCode() {
		return "accessible.cell" + getContext();
	}

	/**
     * getAccessibleColumnName
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleColumnName() {
		return "accessible.column.name" + getContext();
	}

	/**
     * getAccessibleRowName
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleRowName() {
		return "accessible.row.name" + getContext();
	}

	/**
     * getAccessibleValueName
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleValueName() {
		return "accessible.value.name" + getContext();
	}

	/**
     * getAccessibleEditStatus
     * @param _bEdit
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleEditStatus(boolean _bEdit) {
		if (_bEdit) {
			return getString("accessible.editable");
		}
		return getString("accessible.uneditable");
	}

	/**
     * getAccessibleLockStatus
     * @param _bLock
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleLockStatus(boolean _bLock) {
		if (_bLock) {
			return getString("accessible.locked");
		}
		return	getString("accessible.unlocked");
	}

	/**
     * getAccessibleColumnName
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleColumnName(int _col) {
		return getString(getAccessibleColumnName()) + " " + getAccessibleColumnNameAt(_col);
	}

	/**
     * getAccessibleRowName
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleRowName(int _row) {
		return getString(getAccessibleRowName()) + " " + getAccessibleRowNameAt(_row);
	}

	/**
     * getAccessibleValue
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleValue(int _row, int _col) {
		Object o = getAccessibleValueAt(_row,_col);
		if (Routines.have(o)) {
			return getString(getAccessibleValueName()) + " " + o.toString();
		}
		return getString("accessible.value.null");
	}

	/**
     * getAccessibleAttributeType
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected String getAccessibleAttributeType(int _row, int _col) {
		String sConstant = "accessible.editor.type.";
		EANMetaAttribute meta = getAccessibleMetaAttribute(_row,_col);
		if (meta != null) {
			String attType = meta.getAttributeType();
			if (attType != null) {
				if (attType.equalsIgnoreCase("T")) {
					if (meta.isDate()) {
						return getString(sConstant+"DATE");
					} else if (meta.isTime()) {
						return getString(sConstant+"TIME");
					} else {
						return getString(sConstant+attType);
					}
				}
				return getString(sConstant+attType);
			}
		}
		return "";
	}

	/**
     * getAccessibleMetaAttribute
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected EANMetaAttribute getAccessibleMetaAttribute(int _row, int _col) {
		return null;
	}

	/**
     * isRowAnchor
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isRowAnchor(int _row) {
		return (selectionModel.getAnchorSelectionIndex() == _row);
	}

	/**
     * isColumnAnchor
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isColumnAnchor(int _col) {
		return (columnModel.getSelectionModel().getAnchorSelectionIndex() == _col);
	}

/*
 52783
 */
    /**
    * hasData
    * @return
    * @author Anthony C. Liberto
    */
    public boolean hasData() {
		return !isEmpty();
	}

 /*
  accessibility
  */
    /**
    * getAccessibleTableDescription
    * @param _code
    * @return
    * @author Anthony C. Liberto
    */
    public String getAccessibleTableDescription(String _code) {
        return "";
    }

/*
 acl_20031219
 */
	/**
     * @see java.awt.Component#validate()
     * @author Anthony C. Liberto
     */
    public void validate() {
		try {
			super.validate();
		} catch (Exception _x) {
			_x.printStackTrace();
		}
		return;
	}

    /**
     * @see javax.swing.JComponent#paintImmediately(int, int, int, int)
     * @author Anthony C. Liberto
     */
    public void paintImmediately(int _x, int _y, int _w, int _h) {
		if (!isDereferenced()) {						//acl_20040104
			try {
				paintImmediately2(_x,_y,_w,_h);
			} catch (Exception _e) {
				_e.printStackTrace();
			}
		}												//acl_20040104
		return;
	}

	private void paintImmediately2(int _x, int _y, int _w, int _h) throws Exception {
		super.paintImmediately(_x,_y,_w,_h);
		return;
	}

/*
 acl_20040104
 */
	/**
     * isDereferenced
     * @return
     * @author Anthony C. Liberto
     */
    public abstract boolean isDereferenced();

/*
 tableresize
 */
	/**
     * getDirectValueAt
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getDirectValueAt(int _r, int _c) {
		return getValueAt(_r,_c);
	}

/*
 accessibility
 */
	/**
     * initAccessibility
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void initAccessibility(String _s) {
		if (EAccess.isAccessible()) {
			AccessibleContext ac = getAccessibleContext();
			String strAccessible = null;
			if (ac != null) {
				if (_s == null) {
					ac.setAccessibleName(null);
					ac.setAccessibleDescription(null);
				} else {
					strAccessible = getString(_s);
					ac.setAccessibleName(strAccessible);
					ac.setAccessibleDescription(strAccessible);
				}
			}
		}
		return;
	}
}
