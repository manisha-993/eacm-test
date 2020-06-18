//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: GeneralAreaItem.java,v $
// Revision 1.6  2005/03/03 21:12:25  dave
// Jtest cleanup
//
// Revision 1.5  2003/01/31 21:59:48  joan
// add methods.
//
// Revision 1.4  2003/01/29 18:49:19  joan
// add copyright
//

package COM.ibm.eannounce.objects;


import COM.ibm.opicmpdh.middleware.Profile;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/**
 * GeneralAreaItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GeneralAreaItem extends EANMetaEntity {
    private EntityItem m_eiGenArea = null;

    /**
     * GeneralAreaItem
     *
     * @param _prof
     * @param _ei
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public GeneralAreaItem(Profile _prof, EntityItem _ei) throws MiddlewareRequestException {
        super(null, _prof, _ei.getKey());
        m_eiGenArea = _ei;
    }

    /*
    * returns General Area Name
    */
    /**
     * getName
     *
     * @return
     *  @author David Bigelow
     */
    public String getName() {
        EANAttribute att = m_eiGenArea.getAttribute("GENAREANAME");
        if (att != null) {
            MetaFlag[] amf = (MetaFlag[]) att.get();
            for (int f = 0; f < amf.length; f++) {
                if (amf[f].isSelected()) {
                    return amf[f].getLongDescription();
                }
            }
        }
        return "";
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("GeneralAreaItem:" + ":" + m_eiGenArea.getKey() + ":" + m_eiGenArea.toString());
        return new String(strbResult);
    }
}
