/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RSTableColumn.java,v $
 * Revision 1.3  2009/09/01 17:09:31  wendy
 * removed useless code
 *
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/10/29 16:47:40  tony
 * 52728
 *
 * Revision 1.6  2003/10/14 16:40:33  tony
 * 52087
 *
 * Revision 1.5  2003/09/25 22:48:34  tony
 * 51832
 *
 * Revision 1.4  2003/08/22 16:39:41  tony
 * general search
 *
 * Revision 1.3  2003/06/16 23:28:31  tony
 * 51294
 *
 * Revision 1.2  2003/06/16 19:04:03  tony
 * 51294
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2002/11/07 16:58:34  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import javax.swing.table.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class RSTableColumn extends TableColumn {
	private static final long serialVersionUID = 1L;
	/**
     * HIDDEN_WIDTH
     */
    public static final int HIDDEN_WIDTH = 0;
    /**
     * MIN_WIDTH
     */
    public static final int MIN_WIDTH = 20;
    /**
     * MIN_DATE_WIDTH
     */
    public static final int MIN_DATE_WIDTH = 90;
    /**
     * MIN_TIME_WIDTH
     */
    public static final int MIN_TIME_WIDTH = 40;
    //51294	public static final int MIN_BLOB_WIDTH = 60;
    /**
     * MIN_BLOB_WIDTH
     */
    public static final int MIN_BLOB_WIDTH = 220; //51294

    /**
     * MIN_WIDTH_ADJUSTMENT
     */
    public static final int MIN_WIDTH_ADJUSTMENT = 10;
    /**
     * FLAG_WIDTH_ADJUSTMENT
     */
    public static final int FLAG_WIDTH_ADJUSTMENT = 30;

    private EANMetaAttribute meta = null;
    private int myminWidth = -1;

    private String name = null;
    private String sKey = null;
    private boolean bHidden = false;
    private boolean bFilter = false;
    private boolean bResize = true;
    private int viewIndex = -1;

    //dep	private transient booleanTableAttribute bta = null;

    /**
     * rsTableColumn
     * @author Anthony C. Liberto
     */
    public RSTableColumn() {
        super();
        return;
    }

    /**
     * rsTableColumn
     * @param _indx
     * @author Anthony C. Liberto
     */
    public RSTableColumn(int _indx) {
        super(_indx);
        setModelIndex(_indx);
        setViewIndex(_indx);
        return;
    }

    /**
     * rsTableColumn
     * @param _model
     * @param _view
     * @author Anthony C. Liberto
     */
    public RSTableColumn(int _model, int _view) {
        super(_model);
        setModelIndex(_model);
        setViewIndex(_view);
        return;
    }

    /**
     * rsTableColumn
     * @param _indx
     * @param _width
     * @param _rend
     * @param _edit
     * @author Anthony C. Liberto
     */
    public RSTableColumn(int _indx, int _width, TableCellRenderer _rend, TableCellEditor _edit) {
        super(_indx, _width, _rend, _edit);
        return;
    }

    /*
     dep
    	public booleanTableAttribute getBooleanTableAttribute() {
    		if (bta == null) {
    			bta = new booleanTableAttribute(sKey,name,bHidden,!bFilter);
    		}
    		return bta;
    	}
    */
    /**
     * isFilter
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFilter() {
        return bFilter;
    }

    /**
     * setFilter
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFilter(boolean _b) {
        bFilter = _b;
        return;
    }

    /**
     * isHidden
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden() {
        return bHidden;
    }

    /**
     * setHidden
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setHidden(boolean _b) {
        bHidden = _b;
        return;
    }

    /**
     * getViewIndex
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getViewIndex() {
        return viewIndex;
    }

    /**
     * setViewIndex
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setViewIndex(int _i) {
        viewIndex = _i;
        return;
    }

    /**
     * setName
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setName(String _s) {
        name = new String(_s);
    }

    /**
     * setMinimumWidth
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setMinimumWidth(int _i) {
        myminWidth = _i;
        return;
    }

    /**
     * getMinimumWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinimumWidth() {
        return myminWidth;
    }

    /**
     * isDate
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDate() {
        if (meta != null) {
            return meta.isDate();
        }
        return false;
    }

    /**
     * isTime
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isTime() {
        if (meta != null) {
            return meta.isTime();
        }
        return false;
    }

    /**
     * isFlag
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFlag() {
        if (meta != null) {
            return (meta.getAttributeType().equalsIgnoreCase("F") || meta.getAttributeType().equalsIgnoreCase("U") || meta.getAttributeType().equalsIgnoreCase("S"));
        }
        return false;
    }

    /**
     * isLong
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isLong() {
        if (meta != null) {
            return meta.getAttributeType().equalsIgnoreCase("L");
        }
        return false;
    }

    /**
     * isBlob
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isBlob() {
        if (meta != null) {
            return meta.getAttributeType().equalsIgnoreCase("B");
        }
        return false;
    }

    /**
     * setKey
     *
     * @author Anthony C. Liberto
     * @param _s
     */
    public void setKey(String _s) {
        if (_s != null) {
            sKey = new String(_s);
            setIdentifier(sKey);
        }
        return;
    }

    /**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey() {
        return sKey;
    }

    /**
     * setMeta
     * @param _ean
     * @author Anthony C. Liberto
     */
    public void setMeta(EANFoundation _ean) {
        if (_ean instanceof EANAttribute) {
            meta = ((EANAttribute) _ean).getMetaAttribute();
        }
        return;
    }

    /**
     * getPreferredWidthAdjustment
     * @return
     * @author Anthony C. Liberto
     */
    public int getPreferredWidthAdjustment() {
        if (meta != null) {
            String attType = meta.getAttributeType();
            if (attType.equalsIgnoreCase("U")) {
                return FLAG_WIDTH_ADJUSTMENT;

            } else if (attType.equalsIgnoreCase("S")) {
                return FLAG_WIDTH_ADJUSTMENT;
            }
        }
        return MIN_WIDTH_ADJUSTMENT;
    }

    /*
     52728
    	public int getMinimumPreferredWidth() {
    		if (minWidth > 0)
    			return minWidth;
    		else if (isDate())
    			return MIN_DATE_WIDTH;
    		else if (isTime())
    			return MIN_TIME_WIDTH;
    		else if (isBlob())
    			return MIN_BLOB_WIDTH;
    		return MIN_WIDTH;
    	}
    */
    /*
     can be used to over-write rsTable calls of the same name
     not sure which way is faster probably this way.  Could give us
     some additional speed
    	public TableCellEditor getCellEditor() {}
    	public TableCellRenderer getCellRenderer() {}
    */

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        meta = null;
        name = null;
        return;
    }

    /**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
        return name;
    }

    /**
     * @see javax.swing.table.TableColumn#getWidth()
     * @author Anthony C. Liberto
     */
    public int getWidth() {
        return getSize();
    }

    /*
     * hidden logic
     */
    /**
     * isResizable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isResizable() {
        if (!isVisible()) {
            return false;
        }
        return bResize;
    }

    /**
     * isVisible
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isVisible() {
        return !(isHidden() || isFilter());
    }

    /**
     * @see javax.swing.table.TableColumn#setResizable(boolean)
     * @author Anthony C. Liberto
     */
    public void setResizable(boolean _b) {
        bResize = _b;
    }

    /**
     * setSize
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setSize(int _i) {
        setWidth(_i);
    }

    /**
     * getSize
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int getSize() {
        return getSize(true);
    }

    /**
     * getSize
     * @param _checkHidden
     * @return
     * @author Anthony C. Liberto
     */
    public int getSize(boolean _checkHidden) {
        if (_checkHidden && !isVisible()) {
            return 0;
        }
        return super.getWidth();
    }

    /**
     * key
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String key() {
        return getIdentifier().toString();
    }

    /*
     51832
     */
    /**
     * @see javax.swing.table.TableColumn#getPreferredWidth()
     * @author Anthony C. Liberto
     */
    public int getPreferredWidth() {
        if (isHidden() || isFilter()) {
            return 0;
        }
        return super.getPreferredWidth();
    }
    /*
     52728
     */
    /**
     * getMinimumPreferredWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinimumPreferredWidth() {
        if (myminWidth > 0) {
            return myminWidth;

        } else if (isDate()) {
            return MIN_DATE_WIDTH;

        } else if (isTime()) {
            return MIN_TIME_WIDTH;

        } else if (isBlob()) {
            return MIN_BLOB_WIDTH;
        }
        return MIN_WIDTH;
    }

    /**
     * getMinimumAllowableWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getMinimumAllowableWidth() {
        return MIN_WIDTH;
    }
}
