/**
 * Copyright (c) 2002 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 */

//
//$Log: JuiActionObject.java,v $
//Revision 1.3  2008/02/01 22:10:06  wendy
//Cleanup RSA warnings
//
//Revision 1.2  2005/03/03 23:21:52  dave
//fixing small int problem and jtest
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
 * JuiActionObject
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class JuiActionObject implements Serializable {
    /** Automatically generated javadoc for: serialVersionUID */
    private static final long serialVersionUID = 5302124670135632541L;

    /**
     * FIELD
     */
    public ProfileSet m_oProfileSet = null;
    /**
     * FIELD
     */
    public EANActionItem m_oEANActionItem = null;

    /**
     * JuiActionObject
     *
     * @param _oProfileSet
     * @param _oEANActionItem
     *  @author David Bigelow
     */
    public JuiActionObject(ProfileSet _oProfileSet, EANActionItem _oEANActionItem) {
        this.m_oProfileSet = _oProfileSet;
        this.m_oEANActionItem = _oEANActionItem;
    }

    /**
     * getProfileSet
     *
     * @return
     *  @author David Bigelow
     */
    public ProfileSet getProfileSet() {
        return m_oProfileSet;
    }

    /**
     * getEANActionItem
     *
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getEANActionItem() {
        return m_oEANActionItem;
    }

    /**
     * getVersion
     *
     * @return
     *  @author David Bigelow
     */
    public static String getVersion() {
        return "$Id: JuiActionObject.java,v 1.3 2008/02/01 22:10:06 wendy Exp $";
    }

}
