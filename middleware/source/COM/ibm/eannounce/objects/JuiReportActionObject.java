/**
 * Copyright (c) 2002 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 */

//
//$Log: JuiReportActionObject.java,v $
//Revision 1.3  2008/02/01 22:10:08  wendy
//Cleanup RSA warnings
//
//Revision 1.2  2005/03/04 18:26:23  dave
//Jtest actions for the day
//
//Revision 1.1  2003/05/29 22:13:12  gregg
//inital load into this package
//
//Revision 1.4  2003/05/29 20:16:07  steve
//added serializable
//
//Revision 1.3  2003/05/29 18:15:08  steve
//*** empty log message ***
//
//Revision 1.2  2003/05/29 17:56:26  steve
//changed member var Profile to a ProfileSet
//
//Revision 1.1  2003/05/29 17:51:31  steve
//new file
//
//
//

package COM.ibm.eannounce.objects;

import java.io.Serializable;
import COM.ibm.opicmpdh.middleware.ProfileSet;

/**
 * JuiReportActionObject
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JuiReportActionObject extends JuiActionObject implements Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = -5457712403667463859L;

    private EntityItem[] m_aEntityItem = null;

    /**
     * JuiReportActionObject
     *
     * @param _oProfileSet
     * @param _oReportActionItem
     * @param _aEntityItem
     *  @author David Bigelow
     */
    public JuiReportActionObject(ProfileSet _oProfileSet, ReportActionItem _oReportActionItem, EntityItem[] _aEntityItem) {

        super(_oProfileSet, _oReportActionItem);
        this.m_aEntityItem = _aEntityItem;
    }

    /**
     * getEntityItemArray
     *
     * @return
     *  @author David Bigelow
     */
    public EntityItem[] getEntityItemArray() {
        return m_aEntityItem;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public static String getVersion() {
        return "$Id: JuiReportActionObject.java,v 1.3 2008/02/01 22:10:08 wendy Exp $";
    }
   
}
