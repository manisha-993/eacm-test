//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANBlobAttribute.java,v $
// Revision 1.41  2008/02/01 22:10:07  wendy
// Cleanup RSA warnings
//
// Revision 1.40  2005/06/17 05:28:27  dave
// found interesting bug in opicmblobvalue
//
// Revision 1.39  2005/06/17 05:10:42  dave
// ok.. we are really close
//
// Revision 1.38  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.37  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.36  2004/04/20 19:52:53  joan
// work on duplicate
//
// Revision 1.35  2004/01/19 22:22:48  gregg
// trying to setActive(true) in rollback (blame tony if bad)
//
// Revision 1.34  2004/01/14 19:47:45  joan
// fix null pointer
//
// Revision 1.33  2004/01/13 19:54:40  dave
// space Saver Phase II  m_hsh1 and m_hsh2 created
// as needed instead of always there
//
// Revision 1.32  2003/11/21 21:53:04  dave
// deferred restriction refresh
//
// Revision 1.31  2003/10/03 22:01:33  dave
// adding unique file name, adding odsNET timetable
//
// Revision 1.30  2003/06/26 23:52:33  dave
// adding the abstract stuff
//
// Revision 1.29  2003/01/30 01:44:09  dave
// removed the Display Statement in the PDHPut method
//
// Revision 1.28  2003/01/09 19:51:56  dave
// change protected to public for select methods for more robust
// api to internal developers
//
// Revision 1.27  2003/01/03 21:33:22  joan
// remove System.out
//
// Revision 1.26  2003/01/03 00:59:19  joan
// debug deactivate
//
// Revision 1.25  2002/11/12 17:32:29  dave
// Syntax
//
// Revision 1.24  2002/11/12 17:18:27  dave
// System.out.println clean up
//
// Revision 1.23  2002/11/06 00:39:32  dave
// working w/ refreshrestrictions in more places
//
// Revision 1.22  2002/09/12 22:23:43  dave
// adding to change stack when blob is deactivated
//
// Revision 1.21  2002/09/10 22:30:20  dave
// de-activating if byte array is null
//
// Revision 1.20  2002/05/03 16:42:40  joan
// add getByteArray methods
//
// Revision 1.19  2002/04/29 18:20:26  joan
// fixing getBlobExtension
//
// Revision 1.18  2002/04/29 17:49:16  joan
// debug getBlobExtension
//
// Revision 1.17  2002/04/29 17:28:17  joan
// debug
//
// Revision 1.16  2002/04/29 17:18:19  joan
// debug toString
//
// Revision 1.15  2002/04/29 16:47:55  joan
// debug
//
// Revision 1.14  2002/04/29 16:26:06  joan
// debug toString
//
// Revision 1.13  2002/04/26 23:58:52  joan
// add code in EntityList for blob attribute
//
// Revision 1.12  2002/04/26 23:17:29  joan
// debug
//
// Revision 1.11  2002/04/26 22:35:04  joan
// debug
//
// Revision 1.10  2002/04/26 22:22:27  joan
// debug
//
// Revision 1.9  2002/04/26 21:53:12  joan
// debug toString
//
// Revision 1.8  2002/04/26 21:07:54  joan
// null pointer
//
// Revision 1.7  2002/04/26 20:48:56  joan
// work on blob
//
// Revision 1.6  2002/04/26 18:17:32  joan
// working on blob
//
// Revision 1.5  2002/04/26 17:08:24  joan
// working on blob attribute
//
// Revision 1.4  2002/04/25 22:32:00  joan
// blob attribute
//
// Revision 1.3  2002/04/25 18:31:07  joan
// fixing error
//
// Revision 1.2  2002/04/25 18:21:59  joan
// fix compiling errors
//
// Revision 1.1  2002/04/25 18:02:27  joan
// initial load for blob attribute
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.transactions.OPICMBlobValue;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.RemoteDatabaseInterface;
import COM.ibm.opicmpdh.transactions.NLSItem;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;


/**
 * EANBlobAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EANBlobAttribute extends EANAttribute {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: EANBlobAttribute.java,v 1.41 2008/02/01 22:10:07 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Manages EANBlobAttributes in the e-announce data model
     *
     * @param _edf
     * @param _prof
     * @param _mta
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public EANBlobAttribute(EANDataFoundation _edf, Profile _prof, EANMetaBlobAttribute _mta) throws MiddlewareRequestException {
        super(_edf, _prof, _mta);
    }

    /*
    * Puts the PDHData into the PDH storage structure of this object
    */
    /**
     * putPDHData
     *
     * @param _iNLS
     * @param _blobValue
     *  @author David Bigelow
     */
    public void putPDHData(int _iNLS, OPICMBlobValue _blobValue) {
        if (m_hsh1 == null) {
            m_hsh1 = new Hashtable();
        }
        m_hsh1.put("" + _iNLS, _blobValue);
    }

    /**
     * Puts Local Data back to this attribute, NLSID is always infered from attached or derived
     * profile.  Here is where all the rules get fired...
     *
     * @param _blobValue
     * @throws COM.ibm.eannounce.objects.EANBusinessRuleException 
     */
    public void put(Object _blobValue) throws EANBusinessRuleException {

        EANMetaAttribute ma = getMetaAttribute();
        EntityItem ei = (EntityItem) getParent();

        byte[] bMessage = {(byte) 255, (byte) 255 };
        OPICMBlobValue opv = null;
        int iNLSID = 0;
        
        if (_blobValue == null) {
            
            iNLSID = (ma.isUSEnglishOnly() ? 1 : getNLSID());
            
            setActive(false);
            if (m_hsh2 == null) {
                m_hsh2 = new Hashtable();
            }
            m_hsh2.put("" + iNLSID, new OPICMBlobValue(iNLSID, "Deactivated", bMessage));
            if (ei != null) {
                ei.addToStack(this);
            }
            return;
        }

        if (!(_blobValue instanceof OPICMBlobValue)) {
            return;
        }

        opv = (OPICMBlobValue) _blobValue;
        iNLSID = opv.getNLSID();

        setActive(true);
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        m_hsh2.put("" + iNLSID, opv);

        if (ei != null) {
            ei.addToStack(this);
        }
        refreshDefaults();
        if (ei != null) {
            ei.refreshRequired();
            ei.refreshRestrictions(false);
            ei.refreshResets();
        }
    }

    /*
    *
    */
    /**
     * (non-Javadoc)
     * toString
     *
     * @see COM.ibm.eannounce.objects.EANAddressable#toString()
     */
    public String toString() {

        String strAnswer = getBlobExtension();
        if (!isActive()) {
            return "";
        }
        if (strAnswer == null) {
            return "";
        }
        return strAnswer;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        return "EANBlobAttribute:" + getKey() + ":" + getMetaAttribute().toString() + ":" + toString();
    }

    /*
    * If there is no value.. and there is a default.. we need to apply it here..
    */
    /**
     * (non-Javadoc)
     * get
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#get()
     */
    public Object get() {
        refreshDefaults();
        return toString();
    }

    /*
    * no default values for blob attribute
    */
    /**
     * (non-Javadoc)
     * refreshDefaults
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#refreshDefaults()
     */
    public void refreshDefaults() {
    }

    /**
     * (non-Javadoc)
     * rollback
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#rollback()
     */
    public void rollback() {

        EntityItem ei = (EntityItem) getParent();
        if (!isActive()) {
            setActive(true);
        }

        resetHash2();
        // Remove from any stack
        if (ei != null) {
            ei.removeFromStack(this);
        }
        refreshDefaults();
        ei.refreshRestrictions();
        ei.refreshRequired();

    }

    /**
     * (non-Javadoc)
     * checkBusinessRules
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#checkBusinessRules()
     */
    protected boolean checkBusinessRules() {
        return true;
    }

    /**
     * (non-Javadoc)
     * generateUpdateAttribute
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#generateUpdateAttribute()
     */
    protected Vector generateUpdateAttribute() {

        EntityItem ei = (EntityItem) getParent();

        Vector vctReturn = null;
        ControlBlock cbOn = null;
        ControlBlock cbOff = null;

        // Only package a vector if the business rules have been met
        if (!checkBusinessRules()) {
            return null;
        }
        
        vctReturn = new Vector();
        
        cbOn = new ControlBlock(getEffFrom(), getEffTo(), getEffFrom(), getEffTo(), getProfile().getOPWGID());
        cbOff = new ControlBlock(getEffFrom(), getEffFrom(), getEffFrom(), getEffFrom(), getProfile().getOPWGID());

        // We do not need control block yet
        getMetaAttribute();

        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strBlobKey = (String) it.next();
                OPICMBlobValue blobValue = (OPICMBlobValue) m_hsh2.get(strBlobKey);
                if (this instanceof BlobAttribute) {
                    Blob blob1 = new Blob(getProfile().getEnterprise(), ei.getEntityType(), ei.getEntityID(), getAttributeCode(), blobValue.getBlobValue(), blobValue.getBlobExtension(), blobValue.getNLSID(), (isActive() ? cbOn : cbOff));
                    vctReturn.addElement(blob1);
                }
            }
        }

        return vctReturn;

    }

    /**
     * (non-Javadoc)
     * commitLocal
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#commitLocal()
     */
    public void commitLocal() {

        if (!isActive()) {
            resetHash1();
            resetHash2();
            return;
        }

        // Loop through and move things up from local to PDH structure
        if (m_hsh2 != null) {
            Iterator it = m_hsh2.keySet().iterator();
            while (it.hasNext()) {
                String strBlobKey = (String) it.next();
                OPICMBlobValue baAttrValue = (OPICMBlobValue) m_hsh2.get(strBlobKey);
                int iNLSID = Integer.valueOf(strBlobKey).intValue();
                putPDHData(iNLSID, baAttrValue);
            }
        }

        resetHash2();
    }

    /**
     * put
     *
     * @param _s1
     * @param _ab1
     *  @author David Bigelow
     */
    public void put(String _s1, byte[] _ab1) {

        // If  the byte array is null.. then
        // they must have deactivated it
        EntityItem ei = (EntityItem) getParent();
        String strBlobExtension = null;
        //int x = 0;
        int iNLSID = getNLSID();
 
        if (_ab1 == null) {
            setActive(false);
            refreshDefaults();
            if (ei != null) {
                ei.addToStack(this);
            }
            return;
        }

        if (_ab1 != null) {
            //  System.out.println ("LENGHTH:" + _ab1.length);
        }

        setActive(true);

        if (_s1 != null) {
            strBlobExtension = _s1;
        }

        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }

		if (m_hsh2.get("" + iNLSID) == null) {
			m_hsh2.put("" + iNLSID, new OPICMBlobValue(iNLSID, strBlobExtension, _ab1));
		} else {
			((OPICMBlobValue) m_hsh2.get("" + iNLSID)).setBlobValue(_ab1);
			((OPICMBlobValue) m_hsh2.get("" + iNLSID)).setBlobExtension(strBlobExtension);
		}

        if (ei != null) {
            ei.addToStack(this);
        }
    }


	/**
		* put
		*
		* @param _s1
		* @param _ab1
		*  @author David Bigelow
		*/
	   public void put(String _s1, byte[] _ab1, int _iNLSID) {

		   // If  the byte array is null.. then
		   // they must have deactivated it
		   EntityItem ei = (EntityItem) getParent();
		   String strBlobExtension = null;
		   //int x = 0;
		   int iNLSID = _iNLSID;
 
		   if (_ab1 == null) {
			   setActive(false);
			   refreshDefaults();
			   if (ei != null) {
				   ei.addToStack(this);
			   }
			   return;
		   }

		   if (_ab1 != null) {
			   //  System.out.println ("LENGHTH:" + _ab1.length);
		   }

		   setActive(true);

		   if (_s1 != null) {
			   strBlobExtension = _s1;
		   }

		   if (m_hsh2 == null) {
			   m_hsh2 = new Hashtable();
		   }

		   if (m_hsh2.get("" + iNLSID) == null) {
			   m_hsh2.put("" + iNLSID, new OPICMBlobValue(iNLSID, strBlobExtension, _ab1));
		   } else {
			   ((OPICMBlobValue) m_hsh2.get("" + iNLSID)).setBlobValue(_ab1);
			   ((OPICMBlobValue) m_hsh2.get("" + iNLSID)).setBlobExtension(strBlobExtension);
		   }
		   if (ei != null) {
			   ei.addToStack(this);
		   }
	   }
    /**
     * getOPICMBlobValue
     *
     * @return
     *  @author David Bigelow
     */
    public OPICMBlobValue getOPICMBlobValue() {

        OPICMBlobValue obvReturn = null;
        int iNLS = getNLSID();
        // get local value
        if (m_hsh2 != null) {
            obvReturn = (OPICMBlobValue) m_hsh2.get("" + iNLS);
            if (obvReturn == null) {
                obvReturn = (OPICMBlobValue) m_hsh2.get("" + 1);
            }
        }

        // get pdh value
        if (m_hsh1 != null) {
            if (obvReturn == null) {
                obvReturn = (OPICMBlobValue) m_hsh1.get("" + iNLS);
            }

            if (obvReturn == null) {
                obvReturn = (OPICMBlobValue) m_hsh1.get("" + 1);
            }
        }

        return obvReturn;
    }

    /**
     * getBlobExtension
     *
     * @return
     *  @author David Bigelow
     */
    public String getBlobExtension() {

        String strAnswer = null;
        int iNLS = getNLSID();
        // get local value
        OPICMBlobValue obv = null;

        if (m_hsh2 != null) {
            obv = (OPICMBlobValue) m_hsh2.get("" + iNLS);
            if (obv != null) {
                strAnswer = obv.getBlobExtension();
            }

            obv = (OPICMBlobValue) m_hsh2.get("" + 1);
            if (strAnswer == null && obv != null) {
                strAnswer = obv.getBlobExtension();
            }
        }

        if (m_hsh1 != null) {
            obv = (OPICMBlobValue) m_hsh1.get("" + iNLS);
            if (strAnswer == null && obv != null) {
                strAnswer = obv.getBlobExtension();
            }

            obv = (OPICMBlobValue) m_hsh1.get("" + 1);
            if (strAnswer == null && obv != null) {
                strAnswer = obv.getBlobExtension();
            }

        }

        if (strAnswer == null) {
            strAnswer = "";
        }

        return strAnswer;
    }

    /**
     * getBlobFileName
     *
     * @return
     *  @author David Bigelow
     */
    public String getBlobFileName() {

        // now.. lets get everything to the right of the last dot.

        EntityItem ei = (EntityItem) getParent();
        Profile prof = getProfile();

        String strEntityType = ei.getEntityType();
        int iEntityID = ei.getEntityID();
        int iNLSID = prof.getReadLanguage().getNLSID();

        String strExtension = getBlobExtension(); // should be one here
        int i = strExtension.lastIndexOf(".");
        strExtension = (i == -1 ? ".unk" : strExtension.substring(i));

        return strEntityType + "_" + iEntityID + "_" + getAttributeCode() + "_" + iNLSID + strExtension;

    }

    /**
     * getBlobValue
     *
     * @return
     *  @author David Bigelow
     */
    public byte[] getBlobValue() {

        byte[] baAnswer = null;
        int iNLS = getNLSID();
        // get local value
        OPICMBlobValue obv = null;

        if (m_hsh2 != null) {
            obv = (OPICMBlobValue) m_hsh2.get("" + iNLS);
            if (obv != null) {
                baAnswer = obv.getBlobValue();
            }

            obv = (OPICMBlobValue) m_hsh2.get("" + 1);
            if (baAnswer == null && obv != null) {
                baAnswer = obv.getBlobValue();
            }

        }

        if (m_hsh1 != null) {
            obv = (OPICMBlobValue) m_hsh1.get("" + iNLS);
            if (baAnswer == null && obv != null) {
                baAnswer = obv.getBlobValue();
            }

            obv = (OPICMBlobValue) m_hsh1.get("" + 1);
            if (baAnswer == null && obv != null) {
                baAnswer = obv.getBlobValue();
            }
        }

        return baAnswer;

    }

    /**
     * (non-Javadoc)
     * hasData
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#hasData()
     */
    protected boolean hasData() {
        if (!isActive()) {
            return false;
        }
        if (getBlobExtension() == null) {
            return false;
        }
        return true;
    }

    /**
     * getBlobValue
     *
     * @param _rdi
     * @param _db
     * @return
     *  @author David Bigelow
     */
    public byte[] getBlobValue(RemoteDatabaseInterface _rdi, Database _db) {
        byte[] baAnswer = null;

        Blob blobValue = null;
        Profile prof = getProfile();
        NLSItem nls = prof.getReadLanguage();
        EntityItem ei = (EntityItem) getParent();

        try {
            if (_rdi == null) {
                blobValue = _db.getBlob(prof, ei.getEntityType(), ei.getEntityID(), getAttributeCode());
            } else {
                blobValue = _rdi.getBlob(prof, ei.getEntityType(), ei.getEntityID(), getAttributeCode());
            }
        } catch (Exception x) {
            System.out.println("EANBlobAttribute getBlobValue exception: " + x);
        }

        if (nls != Profile.ENGLISH_LANGUAGE && (blobValue == null || blobValue.size() == 0)) {
            prof.setReadLanguage(Profile.ENGLISH_LANGUAGE);

            try {
                if (_rdi == null) {
                    blobValue = _db.getBlob(prof, ei.getEntityType(), ei.getEntityID(), getAttributeCode());
                } else {
                    blobValue = _rdi.getBlob(prof, ei.getEntityType(), ei.getEntityID(), getAttributeCode());
                }
            } catch (Exception x) {
                System.out.println("EANBlobAttribute getBlobValue exception: " + x);
            }
            prof.setReadLanguage(nls);
        }

        if (blobValue != null) {
            baAnswer = blobValue.getBAAttributeValue();
        }

        return baAnswer;
    }

    /**
     * (non-Javadoc)
     * duplicate
     *
     * @see COM.ibm.eannounce.objects.EANAttribute#duplicate(java.lang.Object)
     */
    protected void duplicate(Object _blobValue) {

        EntityItem ei = (EntityItem) getParent();

        OPICMBlobValue opv = null;
        int iNLSID = 0;

        if (_blobValue == null) {
            return;
        }

        if (!(_blobValue instanceof OPICMBlobValue)) {
            return;
        }

        opv = (OPICMBlobValue) _blobValue;
        iNLSID = opv.getNLSID();

        setActive(true);
        if (m_hsh2 == null) {
            m_hsh2 = new Hashtable();
        }
        m_hsh2.put("" + iNLSID, opv);
        if (ei != null) {
            ei.addToStack(this);
        }
    }

}
