//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: DuplicateBookmarkException.java,v $
// Revision 1.3  2010/02/15 17:38:41  wendy
// add new line to error msg
//
// Revision 1.2  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.1  2004/03/19 21:19:15  gregg
// initial load
//
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Profile;

/**
 * Exception class to be thrown for failed Bookmarking transactions. 
 */
public class DuplicateBookmarkException extends BookmarkException {

    /**
     * DuplicateBookmarkException
     *
     * @param _p
     * @param _eai
     * @param _strUserDescription
     *  @author David Bigelow
     */
    public DuplicateBookmarkException(Profile _p, EANActionItem _eai, String _strUserDescription) {
        super("\nBookmark:\"" + _strUserDescription + "\" already exists. (" + _p + ":" + _eai + ")");
    }

}
