//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MatrixItem.java,v $
// Revision 1.34  2010/09/15 17:44:51  wendy
// Create a unique new entity for each matrix item, was losing meta info for bg color in UI when id=-1 was reused
//
// Revision 1.33  2009/05/19 12:23:17  wendy
// Support dereference for memory release
//
// Revision 1.32  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.31  2005/08/17 19:54:38  joan
// fixes
//
// Revision 1.30  2005/03/04 21:35:51  dave
// Jtest work
//
// Revision 1.29  2005/01/18 23:23:28  gregg
// settin Rule51 on rrk
//
// Revision 1.28  2004/12/17 21:42:54  joan
// fix on matrix
//
// Revision 1.27  2004/11/12 01:16:23  joan
// work on search picklist
//
// Revision 1.26  2004/06/30 16:32:00  joan
// debug
//
// Revision 1.25  2004/06/29 22:56:42  joan
// work on feature matrix
//
// Revision 1.24  2004/06/22 16:12:06  joan
// work on matrix
//
// Revision 1.23  2004/06/11 20:57:24  joan
// work on feature matrix
//
// Revision 1.22  2004/06/10 21:27:12  joan
// more work on special matrix
//
// Revision 1.21  2004/06/08 21:04:42  joan
// fix compile
//
// Revision 1.20  2004/06/08 20:52:13  joan
// work on special matrix
//
// Revision 1.19  2003/09/19 22:35:32  dave
// unraveling matrix lock commit, etc
//
// Revision 1.18  2003/09/19 21:42:28  dave
// O.K. lets see what we broken for this one
//
// Revision 1.17  2003/04/22 16:32:23  dave
// need to catch a throw
//
// Revision 1.16  2003/04/22 16:00:49  dave
// cleaning up Matrix.. only columns in the Nav Action Item
// need be shown.. and we are deriving the MetaLink
//
// Revision 1.15  2003/02/05 21:10:05  joan
// check link limit
//
// Revision 1.14  2003/01/21 19:38:42  joan
// fix bugs
//
// Revision 1.13  2003/01/21 19:02:18  joan
// check VE lock for link
//
// Revision 1.12  2003/01/14 23:27:33  joan
// adjust exception
//
// Revision 1.11  2003/01/14 23:08:09  joan
// work on orphan
//
// Revision 1.10  2002/06/18 22:50:55  joan
// work on hasChanges
//
// Revision 1.9  2002/06/18 22:16:22  joan
// work on rollback and hasChanges
//
// Revision 1.8  2002/06/17 18:08:52  joan
// work on matrix
//
// Revision 1.7  2002/06/07 22:30:30  joan
// working on business rule
//
// Revision 1.6  2002/06/07 15:32:04  joan
// working on commit
//
// Revision 1.5  2002/06/06 21:32:51  joan
// fixing errors
//
// Revision 1.4  2002/06/06 20:54:02  joan
// working on link
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;
import COM.ibm.opicmpdh.transactions.OPICMList;



import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author     davidbig
 *@created    April 22, 2003
 */
public class MatrixItem extends EANDataFoundation {
    private EntityItem m_parentEI = null;
    private EntityItem m_childEI = null;
    private int m_iOldCount = 0;
    private int m_iNewCount = 0;
    private int newRelatorId = 0; // allow each matrix item to have a unique 'new' relator id
    private EANList m_relatorList = null;

    /**
     *@serial
     */
    final static long serialVersionUID = 1L;
    
    protected void dereference(){
    	m_parentEI = null;
        m_childEI = null;
        if (m_relatorList!=null){
        	m_relatorList.clear();
        	m_relatorList = null;
        }

    	super.dereference();
    }

    /**
     *  Gets the version attribute of the MatrixItem object
     *
     *@return    The version value
     */
    public String getVersion() {
        return "$Id: MatrixItem.java,v 1.34 2010/09/15 17:44:51 wendy Exp $";
    }

    /**
     *  Main method which performs a simple test of this class
     *
     *@param  arg  Description of the Parameter
     */
    public static void main(String arg[]) {
    }

    /**
     *  Constructor for the MatrixItem object
     *
     *@param  _mi                             Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixItem(MatrixItem _mi) throws MiddlewareRequestException {
        super(null, _mi.getProfile(), _mi.getKey());
        setParentEntity(_mi.getParentEntity());
        setChildEntity(_mi.getChildEntity());
        setOldCount(_mi.getOldCount());
        setNewCount(_mi.getNewCount());
        setRelatorList(_mi.getRelatorList());
        newRelatorId = _mi.newRelatorId;
    }

    /**
     *  Constructor for the MatrixItem object
     *
     *@param  _f                              Description of the Parameter
     *@param  _prof                           Description of the Parameter
     *@param  _eip                            Description of the Parameter
     *@param  _eic                            Description of the Parameter
     *@exception  MiddlewareRequestException  Description of the Exception
     */
    public MatrixItem(EANFoundation _f, Profile _prof, EntityItem _eip, EntityItem _eic) throws MiddlewareRequestException {
        super(_f, _prof, _eip.getKey() + _eic.getKey());
        setParentEntity(_eip);
        setChildEntity(_eic);

        // Do some initialization
        m_relatorList = new EANList();
    }
    
    /**
     * give each matrix item a unique id for a new relator so it isnt shared in the table for the JUI matrix action
     * @param uid
     */
    protected void setNewRelatorId(int uid){
        newRelatorId = -1*uid;
    }

    /**
     *  Gets the matrixGroup attribute of the MatrixItem object
     *
     *@return    The matrixGroup value
     */
    public MatrixGroup getMatrixGroup() {
        return (MatrixGroup) getParent();
    }

    /**
     *  Description of the Method
     *
     *@param  _bBrief  Description of the Parameter
     *@return          Description of the Return Value
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append(NEW_LINE + "MatrixItem:" + getKey() + ":");
        if (m_parentEI != null) {
            strbResult.append(NEW_LINE + "Parent EntityItem:" + m_parentEI.getKey() + ":");
        }
        if (m_childEI != null) {
            strbResult.append(NEW_LINE + "Child EntityItem:" + m_childEI.getKey() + ":");
        }
        strbResult.append(NEW_LINE + "Number of relators:" + getNewCount() + ":");
        for (int i = 0; i < m_relatorList.size(); i++) {
            EntityItem ei = (EntityItem) m_relatorList.getAt(i);
            strbResult.append("#" + i + " relator: " + ei.getKey());
        }
        return strbResult.toString();
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public String toString() {
       // StringBuffer strbResult = new StringBuffer();
        //strbResult.append(getKey());
        return getKey();//new String(strbResult);
    }

    /**
     *  Sets the parentEntity attribute of the MatrixItem object
     *
     *@param  _ei  The new parentEntity value
     */
    protected void setParentEntity(EntityItem _ei) {
        m_parentEI = _ei;
    }

    /**
     *  Gets the parentEntity attribute of the MatrixItem object
     *
     *@return    The parentEntity value
     */
    public EntityItem getParentEntity() {
        return m_parentEI;
    }

    /**
     *  Sets the childEntity attribute of the MatrixItem object
     *
     *@param  _ei  The new childEntity value
     */
    protected void setChildEntity(EntityItem _ei) {
        m_childEI = _ei;
    }

    /**
     *  Gets the childEntity attribute of the MatrixItem object
     *
     *@return    The childEntity value
     */
    public EntityItem getChildEntity() {
        return m_childEI;
    }

    /**
     *  Sets the oldCount attribute of the MatrixItem object
     *
     *@param  _i  The new oldCount value
     */
    protected void setOldCount(int _i) {
        m_iOldCount = _i;
    }

    /**
     *  Gets the oldCount attribute of the MatrixItem object
     *
     *@return    The oldCount value
     */
    public int getOldCount() {
        return m_iOldCount;
    }

    /**
     *  Sets the newCount attribute of the MatrixItem object
     *
     *@param  _i  The new newCount value
     */
    protected void setNewCount(int _i) {
        m_iNewCount = _i;
    }

    /**
     *  Gets the newCount attribute of the MatrixItem object
     *
     *@return    The newCount value
     */
    public int getNewCount() {
        return m_iNewCount;
    }

    /**
     *  Sets the relatorList attribute of the MatrixItem object
     *
     *@param  _el  The new relatorList value
     */
    protected void setRelatorList(EANList _el) {
        m_relatorList = _el;
    }

    /**
     *  Gets the relatorList attribute of the MatrixItem object
     *
     *@return    The relatorList value
     */
    public EANList getRelatorList() {
        return m_relatorList;
    }

    /**
     *  Description of the Method
     *
     *@param  _ei  Description of the Parameter
     */
    public void putRelator(EntityItem _ei) {
        EntityGroup eg = _ei.getEntityGroup();
        if (eg.isRelator() || eg.isAssoc()) {
            EntityItem eip = (EntityItem) _ei.getUpLink(0);
            EntityItem eic = (EntityItem) _ei.getDownLink(0);
            if (eip != null && eic != null && eip.getKey().equals(m_parentEI.getKey()) && eic.getKey().equals(m_childEI.getKey())) {
                m_relatorList.put(_ei);
            }
        }
    }

    /**
     *  Description of the Method
     */
    private void resetNewCount() {
        m_iNewCount = m_iOldCount;
    }

    /**
     *  Description of the Method
     *
     *@param  _i  Description of the Parameter
     */
    public void put(int _i) {
        m_iNewCount = _i;
        getMatrixGroup().addToStack(this);
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public String get() {
        if (getMatrixGroup().showRelAttr()) {
            int iCount = 0;
            for (int i = 0; i < m_relatorList.size(); i++) {
                EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                EANAttribute att = ei.getAttribute(getMatrixGroup().getRelAttrCode());
                if (att != null && att.isActive()) {
                    iCount++;
                }
            }
            return iCount + "";
        }
        return "" + m_iNewCount;
    }

    /**
     * getRelAttr
     *
     * @param _strAttrCode
     * @return
     *  @author David Bigelow
     */
    public Object getRelAttr(String _strAttrCode) {
        //String strTraceBase = "MatrixItem getRelAttr ";

        EANAttribute attr = null;

        if (m_relatorList.size() > 0) {
            EntityItem eiR = (EntityItem) m_relatorList.getAt(0);
            attr = eiR.getAttribute(_strAttrCode);
            if (attr == null) {
                MatrixGroup mg = getMatrixGroup();
                attr = (EANAttribute) eiR.getEANObject(eiR.getEntityType() + ":" + mg.getRelAttrCode());
                attr.setActive(false);
            }
            return attr; 
        } else {
            try {
                MatrixGroup mg = getMatrixGroup();
                EntityGroup egR = mg.getEntityGroup();
                EntityItem eiR = egR.getEntityItem(egR.getEntityType()+newRelatorId);
                if (eiR==null){
                	eiR = new EntityItem(egR, null, egR.getEntityType(), newRelatorId);//-1);
                	egR.putEntityItem(eiR);
                }
                attr = eiR.getAttribute(_strAttrCode);
                if (attr == null) {
                	attr = (EANAttribute) eiR.getEANObject(eiR.getEntityType() + ":" + mg.getRelAttrCode());
                	attr.setActive(false);
                }
                return attr;
            } catch (MiddlewareRequestException mre) {
                mre.printStackTrace();
            }
        }
        return null;
    }

    /**
     * putRelAttr
     *
     * @param _strAttrCode
     * @param _o
     *  @author David Bigelow
     */
    public void putRelAttr(String _strAttrCode, Object _o) {
        //System.out.println(strTraceBase + _o.toString());
        try {
            if (m_relatorList.size() > 0) {
                EntityItem eiR = (EntityItem) m_relatorList.getAt(0);
                eiR.put(eiR.getEntityType() + ":" + _strAttrCode, _o);
            } else {
                MatrixGroup mg = getMatrixGroup();
                EntityGroup egR = mg.getEntityGroup();
               // EntityItem eiR = new EntityItem(egR, null, egR.getEntityType(), -1);
               // egR.putEntityItem(eiR);
                EntityItem eiR = egR.getEntityItem(egR.getEntityType()+newRelatorId);
                if (eiR==null){
                	eiR = new EntityItem(egR, null, egR.getEntityType(), newRelatorId);//-1);
                	egR.putEntityItem(eiR);
                }
                
                // Now hook them all up
                eiR.putUpLink(m_parentEI);
                eiR.putDownLink(m_childEI);
                eiR.put(eiR.getEntityType() + ":" + _strAttrCode, _o);
                m_relatorList.put(eiR);
            }
        } catch (EANBusinessRuleException bre) {
            bre.printStackTrace();
        } catch (MiddlewareRequestException mre) {
            mre.printStackTrace();
        }
        getMatrixGroup().addToStack(this);
    }

    /**
     *  Description of the Method
     */
    public void commitLocal() {
        if (getMatrixGroup().showRelAttr()) {
            for (int i = 0; i < m_relatorList.size(); i++) {
                EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                if (ei.hasChanges()) {
                    ei.commitLocal();
                }
            }
        } else {
            m_iOldCount = m_iNewCount;
        }
        getMatrixGroup().removeFromStack(this);
    }

    /**
     * getNewRelatorCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getNewRelatorCount() {
        MatrixGroup mg = getMatrixGroup();
        int iCount = 0;
        if (mg.showRelAttr()) {
            for (int i = 0; i < m_relatorList.size(); i++) {
                EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                if (ei.hasChanges()) {
                    iCount++;
                }
            }
        } else {
            iCount = Math.abs(getNewCount() - getOldCount());
        }
        return iCount;
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    public Vector generateLinkRelators() {
        //System.out.println(strTraceBase + getKey());
        Vector vctReturnRelatorKeys = new Vector();
        MetaLink ml = getMatrixGroup().getMetaLink();
        String strEntity1Type = m_parentEI.getEntityType();
        int iEntity1ID = m_parentEI.getEntityID();
        String strEntity2Type = m_childEI.getEntityType();
        int iEntity2ID = m_childEI.getEntityID();

        MatrixGroup mg = getMatrixGroup();
        if (mg.showRelAttr()) {
            try {
                for (int i = 0; i < m_relatorList.size(); i++) {
                    EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                    if (ei.hasChanges()) {
                        EANAttribute attr = ei.getAttribute(getMatrixGroup().getRelAttrCode());
                        if (attr != null && attr.isActive()) {
                            // update the attribute
                            //System.out.println(strTraceBase + " update the attribute");

                            vctReturnRelatorKeys.addElement(ei.generateUpdateEntity(false));
                            if (ei.getEntityID() < 0) {
                                // add relator
                                //System.out.println(strTraceBase + " add relator");
                                ReturnRelatorKey rrk = new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, true);
                                vctReturnRelatorKeys.addElement(rrk);
                                if (ml.hasRule51Group()) {
                                    rrk.setRule51Group(ml.getRule51Group());
                                }
                                ei.setActive(true);
                            }
                        } else {
                            //remove relator
                            vctReturnRelatorKeys.addElement(new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, false));
                            ei.setActive(false);
                        }
                    }
                }
            } catch (EANBusinessRuleException bre) {
                bre.printStackTrace();
            }
        } else {
            if (m_iNewCount == m_iOldCount) {
                for (int i = 0; i < m_relatorList.size(); i++) {
                    EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                    ei.setActive(true);
                }
            } else if (m_iNewCount > m_iOldCount) {
                // add relators
                ReturnRelatorKey rrk = null;
                int diff = m_iOldCount - m_iNewCount;
                while (diff < 0) {
                    try {
                        EntityItem ei = new EntityItem(null, getProfile(), ml.getKey(), diff++);
                        ei.setActive(true);
                        m_relatorList.put(ei);
                        rrk = new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, true);
                        if (ml.hasRule51Group()) {
                            rrk.setRule51Group(ml.getRule51Group());
                        }
                        vctReturnRelatorKeys.addElement(rrk);
                    } catch (MiddlewareRequestException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (m_iNewCount < m_iOldCount) {
                // remove relators
                int diff = m_iOldCount - m_iNewCount;
                for (int i = 0; i < diff; i++) {
                    EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                    ei.setActive(false);
                    vctReturnRelatorKeys.addElement(new ReturnRelatorKey(ei.getEntityType(), ei.getEntityID(), strEntity1Type, iEntity1ID, strEntity2Type, iEntity2ID, false));
                }
            }
        }

        return vctReturnRelatorKeys;
    }

    /**
     *  Description of the Method
     *
     *@param  _ol                           Description of the Parameter
     *@exception  EANBusinessRuleException  Description of the Exception
     */
    public void updateRelList(OPICMList _ol) throws EANBusinessRuleException {
        EntityItemException eie = new EntityItemException();
        try {
            for (int i = 0; i < _ol.size(); i++) {
                if (_ol.getAt(i) instanceof EntityItemException) {
                    eie = (EntityItemException) _ol.getAt(i);
                } else {
                    if (_ol.getAt(i) instanceof ReturnRelatorKey) {

                        ReturnRelatorKey rrk = (ReturnRelatorKey) _ol.getAt(i);

                        boolean bposted = rrk.isPosted();
                        boolean bactive = rrk.isActive();

                        EntityItem ei = (EntityItem) m_relatorList.get(rrk.hashkey());
                        if (ei != null) {
                            if (bposted && bactive) {
                                try {
                                    ei.setEntityID(rrk.getReturnID());
                                } catch (Exception x) {
                                    x.printStackTrace();
                                }
                            } else if (bposted && !bactive) {
                                m_relatorList.remove(ei);
                            } else if (!bposted) {
                                eie.add(new EntityItem(null, getProfile(), ei.getEntityType(), ei.getEntityID()), " Unable to remove this relationship because it creates an orphan");
                                m_iNewCount++;
                            }
                        } else {
                            // new relator, add in the list
                            if (bposted && bactive) {
                                for (int j = 0; j < m_relatorList.size(); j++) {
                                    EntityItem eiR = (EntityItem) m_relatorList.getAt(j);
                                    if (eiR.getEntityID() < 0) {
                                        String strOldKey = eiR.getKey();
                                        eiR.setEntityID(rrk.getReturnID());
                                        m_relatorList.resetKey(strOldKey);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //clear out relator with entityid < 0
            for (int j = 0; j < m_relatorList.size(); j++) {
                EntityItem ei = (EntityItem) m_relatorList.getAt(j);
                if (ei.getEntityID() < 0) {
                    m_relatorList.remove(ei);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (eie.getErrorCount() > 0) {
            throw eie;
        }

        commitLocal();

    }

    /**
     *  Description of the Method
     */
    public void rollback() {
        if (getMatrixGroup().showRelAttr()) {
            try {
                for (int i = 0; i < m_relatorList.size(); i++) {
                    EntityItem ei = (EntityItem) m_relatorList.getAt(i);
                    if (ei.getEntityID() > 0) {
                        ei.rollback(ei.getEntityType() + ":" + getMatrixGroup().getRelAttrCode());
                    } else {
                        ei.put(ei.getEntityType() + ":" + getMatrixGroup().getRelAttrCode(), null);
                    }
                    ei.setActive(true);
                }
            } catch (EANBusinessRuleException bre) {
                bre.printStackTrace();
            }
        } else {
            resetNewCount();
        }
        getMatrixGroup().removeFromStack(this);
    }
}
