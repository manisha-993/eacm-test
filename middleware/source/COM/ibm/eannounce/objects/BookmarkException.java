//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BookmarkException.java,v $
// Revision 1.4  2005/02/09 23:46:46  dave
// more Jtest Cleanup
//
// Revision 1.3  2004/03/16 17:23:07  gregg
// empty Constructor added
//
// Revision 1.2  2003/04/15 18:26:50  gregg
// Message for max limit of Items
//
// Revision 1.1  2003/04/15 17:51:39  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

/**
 * Exception class to be thrown for failed Bookmarking transactions. 
 */
public class BookmarkException extends Exception {

    /**
     * FIELD
     */
    public static final String MAX_LIMIT_MSG = "Maximum Number of Bookmarks Exceeded (" + BookmarkGroup.MAX_BOOKMARKITEMS + ").";

    /**
     * BookmarkException
     *
     * @param _strMessage
     *  @author David Bigelow
     */
    public BookmarkException(String _strMessage) {
        super(_strMessage);
    }

    /**
     * BookmarkException
     *
     *  @author David Bigelow
     */
    public BookmarkException() {
        super();
    }

}
