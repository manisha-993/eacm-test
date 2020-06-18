//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneralAreaGroup.java,v $
// Revision 1.7  2005/03/03 21:12:25  dave
// Jtest cleanup
//
// Revision 1.6  2003/05/11 02:04:41  dave
// making EANlists bigger
//
// Revision 1.5  2003/01/31 21:59:48  joan
// add methods.
//
// Revision 1.4  2003/01/29 18:49:19  joan
// add copyright
//

package COM.ibm.eannounce.objects;


/**
 * GeneralAreaGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GeneralAreaGroup {
    
    private EANList m_gaiList = new EANList(EANMetaEntity.LIST_SIZE);

    /**
     * GeneralAreaGroup
     *
     *  @author David Bigelow
     */
    public GeneralAreaGroup() {
        m_gaiList = new EANList(EANMetaEntity.LIST_SIZE);
    }

    /**
     * returns number of GeneralAreaItem
     *
     * @return int
     */
    public int getGeneralAreaItemCount() {
        return m_gaiList.size();
    }

    /**
     * putGeneralAreaItem
     *
     * @param _gai
     *  @author David Bigelow
     */
    protected void putGeneralAreaItem(GeneralAreaItem _gai) {
        m_gaiList.put(_gai);
    }

    /**
     * getGeneralAreaItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public GeneralAreaItem getGeneralAreaItem(int _i) {
        if (_i < 0 || _i >= m_gaiList.size()) {
            return null;
        }

        return (GeneralAreaItem) m_gaiList.getAt(_i);
    }

    /**
     * removeGeneralAreaItem
     *
     * @param _gai
     *  @author David Bigelow
     */
    public void removeGeneralAreaItem(GeneralAreaItem _gai) {
        m_gaiList.remove(_gai);
    }

    /**
     * getGeneralAreaItem
     *
     * @param     _s	key of GeneralAreaItem
     * @return GeneralAreaItem
     */
    public GeneralAreaItem getGeneralAreaItem(String _s) {
        Object o = m_gaiList.get(_s);
        if (o == null) {
            return null;
        } else {
            return (GeneralAreaItem) o;
        }
    }

    /**
     * getGeneralAreaItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getGeneralAreaItem() {
        return m_gaiList;
    }

    /**
     * dump
     *
     * @param _bBrief
     * @return
     *  @author David Bigelow
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("GeneralAreaGroup");
        for (int i = 0; i < getGeneralAreaItemCount(); i++) {
            GeneralAreaItem gai = getGeneralAreaItem(i);
            strbResult.append("\ni: " + i + ":" + gai.dump(_bBrief));
        }
        return new String(strbResult);
    }
}
