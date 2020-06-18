//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaStatusAttribute.java,v $
// Revision 1.14  2010/11/08 18:48:10  wendy
// Reuse string objects to reduce memory requirements
//
// Revision 1.13  2009/05/11 15:46:53  wendy
// Support dereference for memory release
//
// Revision 1.12  2005/03/30 00:51:54  dave
// adding taxonomy object
//
// Revision 1.11  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.10  2004/10/15 17:06:04  dave
// atttempting some speed up by bypassing instance of checks
//
// Revision 1.9  2002/05/02 17:34:46  gregg
// for extract, build EANMetaFlagAttributes w/ all NLS flag values
//
// Revision 1.8  2002/03/13 01:15:28  gregg
// syntax
//
// Revision 1.7  2002/03/13 01:06:37  gregg
// ods length
//
// Revision 1.6  2002/03/05 04:58:02  dave
// more dump display cleanup
//
// Revision 1.5  2002/02/28 00:50:53  dave
// syntax
//
// Revision 1.4  2002/02/28 00:19:51  dave
// added trace and debug for StateTranstion inside of the MetaStatusAttribute
//
// Revision 1.3  2002/02/27 21:09:24  dave
// more syntax
//
// Revision 1.2  2002/02/27 20:37:16  dave
// adding state machines and the such to entityGroup
//
// Revision 1.1  2002/02/05 21:09:11  dave
// more abstract file jockeying
//
// Revision 1.1  2002/02/04 20:51:40  dave
// new classes
//
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.Hashtable;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * MetaStatusAttribute
*/
public class MetaStatusAttribute extends EANMetaFlagAttribute {

    // Instance variables
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    private EANList m_elTrans = new EANList();
    
    protected void dereference() {
    	super.dereference();
    	if (m_elTrans !=null){
    		for (int i=0; i<m_elTrans.size(); i++){
    			StateTransition mt = (StateTransition) m_elTrans.getAt(i);
    			if (mt != null){
    				mt.dereference();
    			}
    		}
    		m_elTrans.clear();
    	}
    }

    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: MetaStatusAttribute.java,v 1.14 2010/11/08 18:48:10 wendy Exp $";
    }

    /**
     * Main method which performs a simple test of this class
     *
     * @param arg 
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the MetaStatusAttribute
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param s1 is the AttributeCode
     * @param s2 is the AttributeType
     * @param s3 is the Capability
     * This is where the caller is responsible for building the object
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _emf
     * @param _prof
     * @param _s1
     * @param _s2
     * @param _s3 
     */
    public MetaStatusAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
        super(_emf, _prof, _s1, _s2, _s3);
        setAttributeType(EANMetaAttribute.IS_STATUS);
    }

    /**
     * Creates the MetaStatusAttribute with the Default US English LongDescription
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param _db is the database
     * @param _prof is the Profile
     * @param _str is the AttributeCode
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _emf 
     */
    public MetaStatusAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str);
        setAttributeType(EANMetaAttribute.IS_STATUS);
    }

    /**
     * Creates the MetaStatusAttribute with the Default US English LongDescription - all NLS flag values
     *
     * @param emf is the Assosicated Creator tied to this Object
     * @param _db is the database
     * @param _prof is the Profile
     * @param _str is the AttributeCode
     * @param _bAllNls is whether we will retreive all NLS flag values
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @param _emf 
     */
    public MetaStatusAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str, boolean _bAllNls) throws SQLException, MiddlewareException, MiddlewareRequestException {
    	this(_emf, _db, _prof, _str, _bAllNls,null);
    }
    public MetaStatusAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _str, boolean _bAllNls,Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, _str, _bAllNls,memTbl);
        setAttributeType(EANMetaAttribute.IS_STATUS);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("MetaStatusAttribute: " + getKey());
        if (!_bBrief) {
            strbResult.append(":" + super.dump(_bBrief));
            for (int ii = 0; ii < getStateTransitionCount(); ii++) {
                StateTransition st = getStateTransition(ii);
                strbResult.append(NEW_LINE + "StateTransition:" + ii + ":" + st.dump(_bBrief));
            }
        }
        return strbResult.toString();
    }

    //
    // State Machine methods.. you can only get from this abstract layer
    // The only thing that can
    //

    /**
     * getStateTransition
     *
     * @param _strFlag1
     * @param _strFlag2
     * @return
     *  @author David Bigelow
     */
    protected StateTransition getStateTransition(String _strFlag1, String _strFlag2) {
        return (StateTransition) m_elTrans.get(getKey() + _strFlag1 + _strFlag2);
    }

    /**
     * getStateTransition
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected StateTransition getStateTransition(int _i) {
        return (StateTransition) m_elTrans.getAt(_i);
    }

    /**
     * getStateTransitionCount
     *
     * @return
     *  @author David Bigelow
     */
    protected int getStateTransitionCount() {
        return m_elTrans.size();
    }

    /**
     * hasStateMachine
     *
     * @return
     *  @author David Bigelow
     */
    protected boolean hasStateMachine() {
        if (m_elTrans == null) {
            return false;
        }
        return m_elTrans.size() > 0;
    }

    /**
     * putStateTransition
     *
     * @param _st
     *  @author David Bigelow
     */
    protected void putStateTransition(StateTransition _st) {
        m_elTrans.put(_st);
    }

    /**
     * accessor - m_iOdsLength set in EANMetaAttribute super class
     *
     * @return int
     */
    public int getOdsLength() {
        return super.m_iOdsLength;
    }

    /**
     *  mutator - set m_iOdsLength defined in EANMetaAttribute super class
     *
     * @param _i 
     */
    public void setOdsLength(int _i) {
        super.m_iOdsLength = _i;
    }
}
