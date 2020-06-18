//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PackageInfo.java,v $
// Revision 1.21  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.20  2005/01/27 04:02:37  dave
// removing automated readObject from Jtest
//
// Revision 1.19  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.18  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.17  2003/07/08 22:04:37  dave
// must throw SQLError
//
// Revision 1.16  2003/07/08 21:00:43  dave
// driving to the net
//
// Revision 1.15  2003/07/08 20:36:15  dave
// syntax
//
// Revision 1.14  2003/07/08 20:32:00  dave
// syntax fixes
//
// Revision 1.13  2003/07/08 20:14:00  dave
// translation package changes II
//
//


package COM.ibm.opicmpdh.translation;


import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import java.io.Serializable;







import java.sql.SQLException;




/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Dave
 */
public final class PackageInfo implements Serializable {

    static final long serialVersionUID = 1L;

    /**
     * m_pkID
     */
    protected PackageID m_pkID = null;
    /**
     * m_pks
     *
     */
    protected PackageStatus m_pks = null;
    /**
     * m_ei
     *
     */
    protected EntityItem m_ei = null;
    /**
     * m_attComment
     *
     */
    protected EANAttribute m_attComment = null;
    /**
     * m_attContact
     */
    protected EANAttribute m_attContact = null;
    /**
     * m_faStatus
     */
    protected EANFlagAttribute m_faStatus = null;

    /**
     * main
     * @param _args
     * @author Dave
     */
    public static void main(String _args[]) {
    }

    /**
     * create a New Package Info Object
     * @param _pkID
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     */
    protected PackageInfo(PackageID _pkID, Database _db, Profile _prof)
        throws SQLException, MiddlewareException {
        EntityGroup eg = null;
        m_pkID = _pkID;
        eg =
            new EntityGroup(null, _db, _prof, m_pkID.getEntityType(), "Edit");
        m_ei =
            new EntityItem(
                eg,
                _prof,
                _db,
                m_pkID.getEntityType(),
                m_pkID.getEntityID());
        m_attComment = m_ei.getAttribute("XLCOMMENT");
        m_attContact = m_ei.getAttribute("XLCONTACT");

        m_faStatus =
            (EANFlagAttribute) m_ei.getAttribute(
                "XLSTATUS" + m_pkID.getNLSID());
        if (m_faStatus != null) {
            String strFlagCode = m_faStatus.getFirstActiveFlagCode();
            String strDescription =
                m_faStatus.getFlagLongDescription(strFlagCode);
            m_pks = new PackageStatus(strFlagCode, strDescription);
        } else {
            m_pks = new PackageStatus("0000", "UNKNOWN");
        }
    }

    /**
     * getPackageID
     *
     * @return
     * @author Dave
     */
    public PackageID getPackageID() {
        return m_pkID;
    }

    /**
     * getPackageStatus
     * @return
     * @author Dave
     */
    public PackageStatus getPackageStatus() {
        return m_pks;
    }

    /**
     * getNLSID
     * @return
     * @author Dave
     */
    public int getNLSID() {
        return m_pkID.m_iNLSID;
    }

    /**
     * getLanguage
     * @return
     * @author Dave
     */
    public String getLanguage() {
        return m_pkID.getLanguage();
    }

    /**
     * getTransDate
     * @return
     * @author Dave
     */
    public String getTransDate() {
        return m_pkID.getTransDate();
    }

    /**
     * getComment
     *
     * @return
     * @author Dave
     */
    public String getComment() {
        return (m_attComment == null ? "NOT FOUND" : m_attComment.toString());
    }

    /**
     * getConact
     * @return
     * @author Dave
     */
    public String getContact() {
        return (m_attContact == null ? "NOT FOUND" : m_attContact.toString());
    }

    /**
     * @see java.lang.Object#toString()
     * @author Dave
     */
    public String toString() {
        return "PackageID: "
        + getPackageID()
        + " Status: "
        + getPackageStatus()
        + " Comment: "
        + getComment()
        + " Contact: "
        + getContact();
    }

    /**
     * getVersion
     * @return
     * @author Dave
     */
    public final static String getVersion() {
        return "$Id: PackageInfo.java,v 1.21 2005/02/08 21:56:51 dave Exp $";
    }
}
