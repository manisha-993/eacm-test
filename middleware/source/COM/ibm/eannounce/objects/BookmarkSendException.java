////
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BookmarkSendException.java,v $
// Revision 1.3  2005/02/10 01:22:25  dave
// JTest fixes
//
// Revision 1.2  2004/03/19 19:04:35  gregg
// add prof to list!!please.
//
// Revision 1.1  2004/03/16 17:35:44  gregg
// BookmarkSendException throwing
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Vector;

/**
 * Exception class to be thrown for failed Bookmarking "Send" transactions. 
 */
public class BookmarkSendException extends BookmarkException {
        
    private Vector m_vctFailedProfiles = null;    
    private Vector m_vctExceptions = null;
        
    /**
     * BookmarkSendException
     *
     *  @author David Bigelow
     */
    public BookmarkSendException() {
        super();    
    }
    
    /**
     * getFailedProfileCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getFailedProfileCount() {
        return m_vctFailedProfiles.size();
    }

    /**
     * getFailedProfile
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public Profile getFailedProfile(int _i) {
        return (Profile)m_vctFailedProfiles.elementAt(_i);    
    }
    
    /**
     * getFailedProfile(i)'s corresponding Exception
     *
     * @param _i
     * @return Exception
     */
    public Exception getProfileException(int _i) {
        return (Exception)m_vctExceptions.elementAt(_i);    
    }
    
    /**
     * addFailedProfile
     *
     * @param _prof
     * @param _exc
     *  @author David Bigelow
     */
    protected void addFailedProfile(Profile _prof, Exception _exc) {
        if(m_vctFailedProfiles == null) {
            m_vctFailedProfiles = new Vector();   
        }
        m_vctFailedProfiles.addElement(_prof);
        if(m_vctExceptions == null) {
            m_vctExceptions = new Vector();   
        }
        m_vctExceptions.addElement(_exc);
    }
   
}

