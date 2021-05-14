//
// Copyright (c) 2005, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MultiRowAttrGroup.java,v $
// Revision 1.2  2011/05/05 11:21:34  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:21  jingb
// no message
//
// Revision 1.1.1.1  2006/03/30 17:36:29  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.23  2006/03/21 17:43:57  gregg
// copyright,Log
//
/*
 * Created on May 31, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.util.Vector;
import java.sql.*;
import COM.ibm.opicmpdh.middleware.*;

/**
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MultiRowAttrGroup {

    private Catalog m_cat = null;
    private String m_strObjKey = null;
    private Vector m_vct = null;

    /**
     * Build ourselves an object from the database.
     */
    public MultiRowAttrGroup(Catalog _cat, String _strObjKey) {
        m_cat = _cat;
        m_strObjKey = _strObjKey;
        m_vct = new Vector();
        ResultSet rs = null;
        Database db = _cat.getCatalogDatabase();
        try {

            rs = db.callGBL4019(new ReturnStatus( -1), m_cat.getEnterprise(),
                                m_strObjKey);

            ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

            D.ebug(D.EBUG_SPEW, "GBL4019 row count:" + rdrs.getRowCount());

            for (int i = 0; i < rdrs.getRowCount(); i++) {
                String strColumnKey = rdrs.getColumn(i, 0);
                D.ebug(D.EBUG_SPEW,
                       "GBL4019 Answers[" + i + "]:" + strColumnKey);
                MultiRowAttrItem cni = new MultiRowAttrItem(this, strColumnKey);
                putItem(cni);
            }

        }
        catch (SQLException _ex) {
            _ex.printStackTrace();
        }
        catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            try {
                rs.close();
                rs = null;
                db.commit();
                db.freeStatement();
                db.isPending();
            }
            catch (SQLException _ex) {
                _ex.printStackTrace();
            }
        }
    }

    public String getObjectKey() {
        return m_strObjKey;
    }

    public Catalog getCatalog() {
        return m_cat;
    }

    public int getItemCount() {
        return m_vct.size();
    }

    public MultiRowAttrItem getItem(int _i) {
        return (MultiRowAttrItem) m_vct.elementAt(_i);
    }

    private void putItem(MultiRowAttrItem _cni) {
        m_vct.addElement(_cni);
    }

    public final MultiRowAttrItem getItem(String _strColKey) {
        // this is going to be such a short list, that we don't nede to use a hastable ..
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).getColumnKey().equals(_strColKey)) {
                return getItem(i);
            }
        }
        return null;
    }

    public boolean containsColumnKey(String _strColKey) {
        return (getItem(_strColKey) != null);
    }

    public boolean containsColumnKey_fc(String _strColKey) {
        if (_strColKey.length() >= 3) {
            _strColKey = _strColKey.substring(0, _strColKey.length() - 3);
        }
        //D.ebug(D.EBUG_SPEW,"containsColumnKey_fc a for:" + _strColKey + " is:" + (getItem(_strColKey) != null));
        return (getItem(_strColKey) != null);
    }

    public String toString() {
        return "CatNavGroup count is:" + getItemCount();
    }

    protected int getValueCombinationCount() {
        int iTotal = 1;
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).getValueCount() > 0) {
                iTotal *= getItem(i).getValueCount();
            }
        }
        return iTotal;
    }

    protected int getPopulatedItemCount() {
		return getPopulatedItems().size();
	}

    private MultiRowAttrItem getPopulatedItem(int _i) {
		if(_i > getPopulatedItems().size()-1) {
            D.ebug(D.EBUG_WARN,"MultiRowAttrGroup.getPopulatedItem WARNING! out of index!" );
			return null;
		}
		return (MultiRowAttrItem)getPopulatedItems().elementAt(_i);
	}

	private Vector getPopulatedItems() {
		Vector v = new Vector();
		for(int i = 0; i < getItemCount(); i++) {
		    if(getItem(i).getValueCount() == 0) {
				continue;
			}
			v.addElement(getItem(i));
		}
		return v;
	}

    public String getValueCombo(String _strColKey, int _i) {
        // ok... I know there's a better way, but this should do the trick...

        int iTotLen = getValueCombinationCount();
        int iCol = getPopulatedItemCount();
        int iColIdx = -1;
        boolean bFC = (_strColKey.indexOf("_FC") > -1);
        for (int i = 0; i < getPopulatedItemCount(); i++) {
            String strColKey = getPopulatedItem(i).getColumnKey();
            String strColKey_fc = getPopulatedItem(i).getColumnKey_fc();
            if (_strColKey.equals(strColKey) ||
                (bFC && _strColKey.equals(strColKey_fc))) {
                iColIdx = i;
                break;
            }
        }
        if (iColIdx == -1) {
            D.ebug(D.EBUG_SPEW,"getValueCombo couldnt locate columnkey for:" + _strColKey);
            return null;
        }
        String[][] mtxFinal = new String[iTotLen][iCol];
        int iLastCycle = 1;

        for (int i = 0; i < iTotLen; i++) {
            MultiRowAttrItem mrai = getPopulatedItem(i);

            if(mrai == null) {
				continue;
			}

            int iCycle = mrai.getValueCount() * iLastCycle;
            int iRowCount = 0;
            int iNumChunks = (iTotLen/iCycle);
            while (iRowCount < iTotLen) {
                for (int j = 0; j < iNumChunks; j++) {
                    for (int k = 0; k < mrai.getValueCount(); k++) {
                        int iCurrValCount = 0;
                        while (iCurrValCount < iLastCycle) {
                            if (bFC) {
                                mtxFinal[iRowCount++][i] = mrai.getValue_fc(k);
                            }
                            else {
                                mtxFinal[iRowCount++][i] = mrai.getValue(k);
                            }
                            iCurrValCount++;
                        }
                    }
                }
            }
            iLastCycle = iCycle;
        }

        for (int i = 0; i < mtxFinal.length; i++) {
            String[] oneRow = mtxFinal[i];
            for (int j = 0; j < oneRow.length; j++) {
                System.out.print(oneRow[j] + " ");
            }
            System.out.print("\n");
        }
        //
        String s = mtxFinal[_i][iColIdx];
        return mtxFinal[_i][iColIdx];
    }

}
