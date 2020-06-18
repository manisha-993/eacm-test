/**
 * Copyright (c) 2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *@version    3.0a 2004/11/11
 *@author     Anthony C. Liberto
 *
 * $Log: rsTableComparator.java,v $
 * Revision 1.17  2009/05/21 19:32:51  wendy
 * Fix sort for implicators
 *
 * Revision 1.16  2009/04/15 20:15:39  wendy
 * Maintain a list for lookup to prevent returning the wrong EANObject
 *
 * Revision 1.15  2008/08/04 12:43:54  wendy
 * CQ00006067-WI : LA CTO - Added support for QueryAction
 *
 * Revision 1.14  2007/04/26 14:08:28  wendy
 * TIR72LUD4 fix
 *
 * Revision 1.13  2007/04/15 23:09:58  wendy
 * Sort fix was for SG: Restore function fails to restore CCINs -- RQ0410072922
 *
 * Revision 1.12  2007/04/15 23:00:52  wendy
 * Corrected default sort behavior when e2 had attr but e1 did not
 *
 * Revision 1.11  2007/02/19 22:41:50  tony
 * fixed VEEdit Sort
 *
 * Revision 1.9  2005/06/02 16:44:27  tony
 * JSTT-6CYHKV refined
 *
 * Revision 1.8  2005/06/02 16:29:28  tony
 * JSTT-6CYHKV
 *
 * Revision 1.7  2005/03/10 23:21:25  dave
 * do not throw column conversion in int/long
 *
 * Revision 1.6  2005/03/10 22:12:07  dave
 * Jtest results
 *
 * Revision 1.5  2004/11/22 16:28:29  tony
 * updated null pointer fix on sort.
 *
 * Revision 1.4  2004/11/22 16:01:03  tony
 * fixed null pointer on sort
 *
 * Revision 1.3  2004/11/18 23:46:01  tony
 * refined change
 *
 * Revision 1.2  2004/11/18 23:43:04  tony
 * fix multiple matrix compare
 *
 * Revision 1.1  2004/11/16 16:55:57  bala
 * tear out the row,column references to table in compare methods
 * change var names, beautify
 *
 */

package COM.ibm.eannounce.objects;
import java.util.Comparator;

/**
 *  Description of the Class
 *
 *@author     Balagopal
 *@created    November 16, 2004
 */
public class rsTableComparator implements Comparator {
	public static final int NO_OP_ID = 1000;
    /**
     * FIELD
     */
    protected boolean[] m_bAscending = null;
    /**
     * FIELD
     */
    protected int[] m_index = null;
    /**
     * FIELD
     */
    protected String[] m_colAttributes = null;
    /**
     * FIELD
     */
    protected RowSelectableTable m_table = null;
	private int iType = -1;
    /*
    constructors
    */
    /**
     *Constructor for the rsTableComparator object
     */
    public rsTableComparator() {
    }

    /**
     *Constructor for the rsTableComparator object
     *
     *@param  _i    Description of the Parameter
     *@param  _asc  Description of the Parameter
     */
    public rsTableComparator(int[] _i, boolean[] _asc) {
        setSort(_i, _asc);
    }

    /**
     *Constructor for the rsTableComparator object
     *
     *@param  _i       Description of the Parameter
     *@param  _asc     Description of the Parameter
     *@param  _matrix  Description of the Parameter
     */
    public rsTableComparator(int[] _i, boolean[] _asc, int _type) {
        setSort(_i, _asc);
        setType(_type);
    }

    /**
     *Constructor for the rsTableComparator object
     *
     *@param  _comp  Description of the Parameter
     */
    public rsTableComparator(rsTableComparator _comp) {
        setType(_comp.getType());
        setSort(_comp.m_index, _comp.m_bAscending);
    }

    /**
     *  Sets the sort attribute of the rsTableComparator object
     *
     *@param  _i    The new sort value
     *@param  _asc  The new sort value
     */
    public void setSort(int[] _i, boolean[] _asc) {
        setSort(_i);
        setSort(_asc);
    }

    /**
     *  Sets the sort attribute of the rsTableComparator object
     *
     *@param  _i  The new sort value
     */
    public void setSort(int[] _i) {
        m_index = _i;
    }

    /**
     *  Sets the sort attribute of the rsTableComparator object
     *
     *@param  _b  The new sort value
     */
    public void setSort(boolean[] _b) {
        m_bAscending = _b;
    }

    /**
     *  Sets the table attribute of the rsTableComparator object
     *
     *@param  _table  The new table value
     */
    public void setTable(RowSelectableTable _table) {
        m_table = _table;
    }

    /**
     *  Gets the ready attribute of the rsTableComparator object
     *
     *@return    The ready value
     */
    private boolean isReady() {
        return m_table != null && m_bAscending != null && m_index != null;
    }

    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     * /
    public int compare_old(Object _o1, Object _o2) {
        int out = 0;
        if (isReady() && _o1 != null && _o2 != null) {
            if (_o1 instanceof EntityItem) {
                out = compareEntityItems(_o1, _o2);
            } else if (_o1 instanceof Implicator) {
                out = compareImplicators(_o1, _o2);
            } else {
                out = compareColumnByColumn(_o1, _o2);
            }
        }
        return out;
    }*/

    /**
     *  compare Entity Items
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareEntityItems(EntityItem ei1, EntityItem ei2) {    	
    	int result = 0;

    	// find the row this object is from, then use the specified column
    	// using the name of the column is not longer valid when multiple entities are in the row
    	int rowid1 = m_table.getRowIndex(ei1.getKey());
    	int rowid2 = m_table.getRowIndex(ei2.getKey());

    	for (int i = 0; i < m_index.length && result == 0; ++i) {
    		int colIndex = m_index[i];
    		if (colIndex==NO_OP_ID){
    			continue;
    		}
    		Object colEaf1 = null;
    		Object colEaf2 = null;
    		if (isMatrix()){
    			colEaf1 = m_table.getMatrixValue(rowid1, colIndex);
    			colEaf2 = m_table.getMatrixValue(rowid2, colIndex);
    		}else{
    			colEaf1 = m_table.getEANObject(rowid1, colIndex);
    			colEaf2 = m_table.getEANObject(rowid2, colIndex);                	 
    		}

    		result = compareObjects(colEaf1, colEaf2, i); 
    	}
    	
        return result;
    }

    /*
    private int compareEntityItemsOld(Object _o1, Object _o2) {    	
        int result = 0;
        if (_o1 != null && _o2 != null) {
            int ii = m_index.length;
            EntityItem ei1 = (EntityItem) _o1;
            EntityItem ei2 = (EntityItem) _o2;
            for (int i = 0; i < ii && result == 0; ++i) {
                EANFoundation foundation = m_table.getColumn(m_index[i]);
                Object[] att = getObjects(ei1, ei2, foundation);
                result = compareObjects(att[0], att[1], i);
            }
        }
        return result;
    }*/
    /**
     *  Gets the objects attribute of the rsTableComparator object
     *
     *@param  _ei1    Description of the Parameter
     *@param  _ei2    Description of the Parameter
     *@param  _found  Description of the Parameter
     *@return         The objects value
     */
    private Object[] getObjects(EntityItem _ei1, EntityItem _ei2, EANFoundation _found) {
        String sKey = null;
        String sParent = null;
        Object[] out = new Object[2];
        if (_found instanceof EANMetaAttribute) {
            EANMetaAttribute meta = (EANMetaAttribute) _found;
            sKey = meta.getKey();
            sParent = ((EntityGroup) meta.getParent()).getEntityType();
        }
 //       System.out.println("ACL VEEdit Sort");

        if (_ei1 != null && _ei2 != null) {
	        if (_ei1.isVEEdit() && _ei2.isVEEdit()) {
//	        	System.out.println("ACL I am sorting VEEdits for: " + sParent);
	        	ExtractActionItem xtract = (ExtractActionItem)_ei1.getEntityGroup().getEntityList().getParentActionItem();
	        	VEPath[] path = xtract.getPath(sParent);
	        	if (path != null) {
	        		int ii = path.length;
	        		int iDir = 0;
	        		EntityItem eiNext1 = _ei1;
	        		EntityItem eiNext2 = _ei2;
	        		for (int i=1;i<ii;++i) {
	        			String sDir = path[iDir].getDirection();
	        			String sEnt = path[i].getType();
	        			if (sDir.equalsIgnoreCase("d")) {
	        				if (eiNext1.hasDownLinks()) {
	        					int dd = eiNext1.getDownLinkCount();
	        					for (int d=0;d<dd;++d) {
	        						EntityItem eiTmp = (EntityItem)eiNext1.getDownLink(d);
	        						if (eiTmp.getEntityType().equalsIgnoreCase(sEnt)) {
//	        							System.out.println("ACL stepping down1 to " + sEnt);
	        							eiNext1 = eiTmp;
	        							break;
	        						}
	        					}
	        				}
	        				if (eiNext2.hasDownLinks()) {
	        					int dd = eiNext2.getDownLinkCount();
	        					for (int d=0;d<dd;++d) {
	        						EntityItem eiTmp = (EntityItem)eiNext2.getDownLink(d);
	        						if (eiTmp.getEntityType().equalsIgnoreCase(sEnt)) {
//	        							System.out.println("ACL stepping down2 to " + sEnt);
	        							eiNext2 = eiTmp;
	        							break;
	        						}
	        					}
	        				}
	        			} else {
	        				if (eiNext1.hasUpLinks()) {
	        					int uu = eiNext1.getUpLinkCount();
	        					for (int u=0;u<uu;++u) {
	        						EntityItem eiTmp = (EntityItem)eiNext1.getUpLink(u);
	        						if (eiTmp.getEntityType().equalsIgnoreCase(sEnt)) {
	        							eiNext1 = eiTmp;
	        							break;
	        						}
	        					}
	        				}
	        				if (eiNext2.hasUpLinks()) {
	        					int uu = eiNext2.getUpLinkCount();
	        					for (int u=0;u<uu;++u) {
	        						EntityItem eiTmp = (EntityItem)eiNext2.getUpLink(u);
	        						if (eiTmp.getEntityType().equalsIgnoreCase(sEnt)) {
	        							eiNext2 = eiTmp;
	        							break;
	        						}
	        					}
	        				}
	        			}
	        			++iDir;
	        		}
//	        		System.out.println("ACL attKey is: " + sKey);
//	        		System.out.println("ACL eiNext1: " + eiNext1.getKey());
//	        		System.out.println("ACL eiNext2: " + eiNext2.getKey());
	            	out[0] = eiNext1.getAttribute(sKey);
//	            	System.out.println("ACL out[0]: " + out[0].toString());
	            	out[1] = eiNext2.getAttribute(sKey);
//	            	System.out.println("ACL out[1]: " + out[1].toString());
	            	return out;
	        	}
	        }
        }

        if (sKey == null) {
            return out;
        }

        //wss this was missing compares on some entity items if ei1 did not have the attr set but ei2 did
        //RQ0410072922
        /*if (_ei1.hasDownLinks()) {
            EANEntity eanDown2 = null;
            EANEntity eanDown = _ei1.getDownLink(0);
            if ((sParent == null || eanDown.getEntityType().equals(sParent)) && eanDown.containsAttribute(sKey)) {
                out[0] = eanDown.getAttribute(sKey);
                eanDown2 = _ei2.getDownLink(0);
                if (eanDown2 != null && eanDown2.containsAttribute(sKey)) {
                    out[1] = eanDown2.getAttribute(sKey);
                }
                return out;
            }
        }
        if (_ei1.hasUpLinks()) {
            EANEntity eanUp = _ei1.getUpLink(0);
            EANEntity eanUp2 = null;
            if ((sParent == null || eanUp.getEntityType().equals(sParent)) && eanUp.containsAttribute(sKey)) {
                out[0] = eanUp.getAttribute(sKey);
                eanUp2 = _ei2.getUpLink(0);
                if (eanUp2 != null && eanUp2.containsAttribute(sKey)) {
                    out[1] = eanUp2.getAttribute(sKey);
                }
                return out;
            }
        }
        */
        if (_ei1.hasDownLinks()||_ei2.hasDownLinks()) {
			if (_ei1.hasDownLinks()) {
				EANEntity eanDown = _ei1.getDownLink(0);
				if ((sParent == null || eanDown.getEntityType().equals(sParent)) && eanDown.containsAttribute(sKey)) {
					out[0] = eanDown.getAttribute(sKey);
				}
			}
			if (_ei2.hasDownLinks()) {
				EANEntity eanDown = _ei2.getDownLink(0);
				if ((sParent == null || eanDown.getEntityType().equals(sParent)) && eanDown.containsAttribute(sKey)) {
					out[1] = eanDown.getAttribute(sKey);
				}
			}
			if (out[0]!=null || out[1]!=null){ // one of the down links had a value
        	    return out;
			}
        }
        if (_ei1.hasUpLinks()||_ei2.hasUpLinks()) {
			if (_ei1.hasUpLinks()) {
				EANEntity eanUp = _ei1.getUpLink(0);
				if ((sParent == null || eanUp.getEntityType().equals(sParent)) && eanUp.containsAttribute(sKey)) {
					out[0] = eanUp.getAttribute(sKey);
				}
			}
			if (_ei2.hasUpLinks()) {
				EANEntity eanUp = _ei2.getUpLink(0);
				if ((sParent == null || eanUp.getEntityType().equals(sParent)) && eanUp.containsAttribute(sKey)) {
					out[1] = eanUp.getAttribute(sKey);
				}
			}
			if (out[0]!=null || out[1]!=null){ // one of the up links had a value
        	    return out;
			}
		}
        if (sKey != null) {
            if (_ei1 != null && _ei1.containsAttribute(sKey)) {
                out[0] = _ei1.getAttribute(sKey);
            }
            if (_ei2 != null && _ei2.containsAttribute(sKey)) {
                out[1] = _ei2.getAttribute(sKey);
            }
        }

        return out;
    }

    /*
    compare Matrix Items
    */
    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareMatrixEntityItems(Object _o1, Object _o2) {
        int result = 0;
        if (_o1 != null && _o2 != null) {
            for (int i = 0; i < m_index.length && result == 0; ++i) {
            	int colIndex = m_index[i];
        		if (colIndex==NO_OP_ID){
        			continue;
        		}
                if (colIndex < 0) {
                    result = compareObjects(getHeader((EntityItem) _o1), getHeader((EntityItem) _o1), i);
                } else {
                    if (_o1 instanceof MatrixItem && _o2 instanceof MatrixItem) {
                        String str = m_table.getColumnKey(colIndex);
                        result = compareObjects(getMatrixObjectValue(_o1, str), getMatrixObjectValue(_o2, str), i);
                    } else if (_o1 instanceof EntityItem && _o2 instanceof EntityItem) {
                        EANFoundation foundation = m_table.getColumn(colIndex);
                        Object[] att = getObjects((EntityItem) _o1, (EntityItem) _o2, foundation);
                        result = compareObjects(att[0], att[1], i);
                    }
                }
            }
        }
        return result;
    }

    /**
     *  Gets the matrixObjectValue attribute of the rsTableComparator object
     *
     *@param  _ei            Description of the Parameter
     *@param  _strAttribute  Description of the Parameter
     *@return                The matrixObjectValue value
     */
    private Object getMatrixObjectValue(Object _ei, String _strAttribute) {
        Object o1 = null;
        if (_ei != null && _strAttribute != null) {
            //out = m_table.getMatrixValue(_ei.getKey(), _strAttribute);
            MatrixItem mItem = (MatrixItem) _ei;
            EntityItem eiTemp = mItem.getChildEntity();
            if (eiTemp != null) {
                o1 = eiTemp.getAttribute(_strAttribute);
            }
            if (o1 == null) {
                eiTemp = mItem.getParentEntity();
                if (eiTemp != null) {
                    o1 = eiTemp.getAttribute(_strAttribute);
                }
            }
        }
        return (o1 != null) ? o1.toString() : null;
    }

    /**
     *  Gets the header attribute of the rsTableComparator object
     *
     *@param  _ei  Description of the Parameter
     *@return      The header value
     */
    private String getHeader(EntityItem _ei) {
        String out = null;
        if (_ei != null) {
            out = _ei.getLongDescription();
            if (out == null || out.equals("TBD")) {
                out = _ei.getShortDescription();
            }
        }
        return out;
    }

    /*
    compare where used items
    */
    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareWhereUsedItems(Object _o1, Object _o2) {
        int result = 0;
        if (_o1 != null && _o2 != null) {
            for (int i = 0; i < m_index.length && result == 0; ++i) {
            	int colIndex = m_index[i];
        		if (colIndex==NO_OP_ID){
        			continue;
        		}
                Object[] obj = getObjects((WhereUsedItem) _o1, (WhereUsedItem) _o2, colIndex);
                result = compareObjects(obj[0], obj[1], i);
            }
        }
        return result;
    }

    /**
     *  Gets the objects attribute of the rsTableComparator object
     *
     *@param  _wui1  Description of the Parameter
     *@param  _wui2  Description of the Parameter
     *@param  _i     Description of the Parameter
     *@return        The objects value
     */
    private Object[] getObjects(WhereUsedItem _wui1, WhereUsedItem _wui2, int _i) {
        Object[] out = new Object[2];
        String sKey = Integer.toString(_i).trim();
        out[0] = _wui1.get(sKey, false);
        out[1] = _wui2.get(sKey, false);
        return out;
    }

    /*
    compare Implicators
    */
    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareImplicators(Object _o1, Object _o2) {
        int out = 0;
        if (_o1 != null && _o2 != null) {
            Object obj1 = ((Implicator) _o1).getParent();
            Object obj2 = ((Implicator) _o2).getParent();
            if (obj1 != null && obj2 != null) {
                if (obj1 instanceof EntityItem) {
                    out = compareMatrixEntityItems(obj1, obj2);
                } else if (obj1 instanceof WhereUsedItem) {
                    out = compareWhereUsedItems(obj1, obj2);
                } else {
                    out = compareColumnByColumn(_o1, _o2);
                }
            } else {
                out = compareColumnByColumn(_o1, _o2);
            }
        } else {
            out = compareColumnByColumn(_o1, _o2);
        }
        return out;
    }

    /*
    compare column by column
    */
    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareColumnByColumn(Object _o1, Object _o2) {
        int result = 0;
        Object o1 = null;
        Object o2 = null;

        for (int i = 0; i < m_index.length && result == 0; i++) {
        	int colIndex = m_index[i];
    		if (colIndex==NO_OP_ID){
    			continue;
    		}
            if (colIndex >= 0) {
                if (isMatrix()) {
                    MatrixItem m1 = (MatrixItem) o1;
                    MatrixItem m2 = (MatrixItem) o2;
                    EANFoundation colFoundation = m_table.getColumn(colIndex);
                    EntityItem eiTemp1 = m1.getChildEntity();
                    EntityItem eiTemp2 = m1.getChildEntity();
                    String strAttrKey = colFoundation.getKey();
                    if (eiTemp1 != null) {
                        o1 = eiTemp1.getAttribute(strAttrKey);
                    }
                    if (o1 == null) {
                        eiTemp1 = m1.getParentEntity();
                        if (eiTemp1 != null) {
                            o1 = eiTemp1.getAttribute(strAttrKey);
                        }
                    }

                    if (eiTemp2 != null) {
                        o2 = eiTemp2.getAttribute(strAttrKey);
                    }
                    if (o2 == null) {
                        eiTemp2 = m2.getParentEntity();
                        if (eiTemp2 != null) {
                            o2 = eiTemp2.getAttribute(strAttrKey);
                        }
                    }
                } else {
                	if (_o1 instanceof COM.ibm.eannounce.objects.QueryItem){
                      	EANFoundation colFoundation = m_table.getColumn(colIndex);
                      	String strAttrKey = colFoundation.getKey();
                      	QueryItem q1 = (QueryItem)_o1;
                      	QueryItem q2 = (QueryItem)_o2;
                        o1 = q1.get(strAttrKey, true); 
                        o2 = q2.get(strAttrKey, true); 
                        if (o1==null){
                        	o1="";
                        }
                        if (o2==null){
                        	o2="";
                        }
                	}else{
                		if (_o1 instanceof Implicator){
                			Object obj1 = ((Implicator) _o1).getParent();
                			Object obj2 = ((Implicator) _o2).getParent();

                			if (obj1 instanceof COM.ibm.eannounce.objects.EANAddressable){
                				EANFoundation colFoundation = m_table.getColumn(colIndex);
                				String strAttrKey = colFoundation.getKey();
                				EANAddressable q1 = (EANAddressable)obj1;
                				EANAddressable q2 = (EANAddressable)obj2;
                				o1 = q1.get(strAttrKey, true); 
                				o2 = q2.get(strAttrKey, true); 
                				if (o1==null){
                					o1="";
                				}
                				if (o2==null){
                					o2="";
                				}
                			}else{
                				o1 = _o1; //Hopefully, we will never need to come here!
                				o2 = _o2;
                			}
                		}else{
                			o1 = _o1; //Hopefully, we will never need to come here!
                			o2 = _o2;
                		}
                	}
                }
                result = compareString(o1.toString(), o2.toString(), i);
            }
        }
        return result;
    }

    /*
    supporting compare methods
    */
    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@param  _i   Description of the Parameter
     *@return      Description of the Return Value
     */
    private int compareObjects(Object _o1, Object _o2, int _i) {
        if (_o1 != null && _o2 != null) {
            return compareString(_o1.toString(), _o2.toString(), _i);
        } else if (_o1 != null) {
            return compareString(_o1.toString(), "", _i);
        } else if (_o2 != null) {
            return compareString("", _o2.toString(), _i);
        }
        return 0;
    }

    /**
     *  Description of the Method
     *
     *@param  s1  Description of the Parameter
     *@param  s2  Description of the Parameter
     *@param  _i  Description of the Parameter
     *@return     Description of the Return Value
     */
    private int compareString(String s1, String s2, int _i) {

        Long n1 = null;

        if (s1 == null || s2 == null) {
            return -1;
        }
        n1 = toLong(null, s1);
        if (n1 != null) {
            Long n2 = toLong(null, s2);
            if (n2 != null) {
                return compareNumber(n1, n2, _i);
            }
        }
        return getResult(s1.compareToIgnoreCase(s2), _i);
    }


    /**
     *  Description of the Method
     *
     *@param  l1  Description of the Parameter
     *@param  l2  Description of the Parameter
     *@param  _i  Description of the Parameter
     *@return     Description of the Return Value
     */
    private int compareNumber(Long l1, Long l2, int _i) {
        return getResult(l1.compareTo(l2), _i);
    }

    /*
    numeric conversion
    */
    /**
     *  Description of the Method
     *
     *@param  _long  Description of the Parameter
     *@param  _s     Description of the Parameter
     *@return        Description of the Return Value
     */
    private Long toLong(Long _long, String _s) {
        try {
            return Long.valueOf(_s.trim());
        } catch (NumberFormatException nfe) {
            // Really do not dump the stack
        }
        return _long;
    }


    /*
    adjust for ascending descending order.
    */
    /**
     *  Gets the result attribute of the rsTableComparator object
     *
     *@param  result  Description of the Parameter
     *@param  _i      Description of the Parameter
     *@return         The result value
     */
    private int getResult(int result, int _i) {
        boolean ascending = m_bAscending[_i];
        if (!ascending) {
            return -result;
        }
        return result;
    }

/*
 JSTT-6CYHKV
 */
    /**
     *  Gets the vertical attribute of the rsTableComparator object
     *
     *@return    The vertical value
     */
	public boolean isVertical() {
		return iType == 3;
	}

    /**
     *  Gets the matrix attribute of the rsTableComparator object
     *
     *@return    The matrix value
     */
    public boolean isMatrix() {
        return iType == 4;
    }

	/**
	 * set the type
	 * JSTT-6CYHKV
	 * @param int
	 * @author tony
	 */
    public void setType(int _i) {
		iType = _i;
	}
	/**
	 * get the type
	 * JSTT-6CYHKV
	 * @return int
	 * @author tony
	 */
	public int getType() {
		return iType;
	}

    /**
     *  Description of the Method
     *
     *@param  _o1  Description of the Parameter
     *@param  _o2  Description of the Parameter
     *@return      Description of the Return Value
     */
    public int compare(Object _o1, Object _o2) {
        int out = 0;
        if (isReady() && _o1 != null && _o2 != null) {
            if (isVertical()) {
				out = compareVertical(_o1,_o2);
			} else if (_o1 instanceof EntityItem) {
                out = compareEntityItems((EntityItem)_o1, (EntityItem)_o2);
            } else if (_o1 instanceof Implicator) {
                out = compareImplicators(_o1, _o2);
            } else {
                out = compareColumnByColumn(_o1, _o2);
            }
        }
        return out;
    }

    private int compareVertical(Object _o1, Object _o2) {
        int result = 0;
        if (_o1 != null && _o2 != null) {
            for (int i = 0; i < m_index.length && result == 0; ++i) {
               	int colIndex = m_index[i];
        		if (colIndex==NO_OP_ID){
        			continue;
        		}
                if (_o1 instanceof Implicator) {
					if (colIndex == 0) {
						result = compareImplicators(_o1,_o2);
					} else {
						Object obj1 = ((Implicator) _o1).getParent();
						Object obj2 = ((Implicator) _o2).getParent();
						result = compareString(obj1.toString(), obj2.toString(), i);
					}
				} else {
                    result = compareString(_o1.toString(), _o2.toString(), i);
				}
            }
        }
        return result;
	}

}
