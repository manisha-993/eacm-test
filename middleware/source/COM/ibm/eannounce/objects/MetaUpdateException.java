//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: MetaUpdateException.java,v $
// Revision 1.3  2005/03/08 23:15:46  dave
// Jtest checkins from today and last ngith
//
// Revision 1.2  2003/01/13 22:47:54  gregg
// semantix
//
// Revision 1.1  2003/01/13 19:31:49  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

/**
 * Provide a facility for reporting logical errors in PDH Meta Updates (such as duplicate unique keys ... )
 */
public class MetaUpdateException extends Exception {

    // Types
    private static final int DEFAULT_TYPE = 0;
    private static final int DUPLICATE_KEY_TYPE = 1;
    // end Types
    private int m_iType = DEFAULT_TYPE; // hold exception type
    private String m_strDupKey = null;

    /**
    * Constructs a <code>MetaUpdateException</code> with no specified detail message
    */
    public MetaUpdateException() {
        super();
    }

    /**
     * Constructs a <code>MetaException</code> with the specified detail message
     *
     * @param _strGenericMessage 
     */
    public MetaUpdateException(String _strGenericMessage) {
        super(_strGenericMessage);
    }

    /**
     * This Exception represents a duplicate unique key in the PDH
     * @param _strKey the unique key being violated (i.e. an Entity Type or Actin Item Key)
     */
    protected void setDuplicateKey(String _strKey) {
        setType(DUPLICATE_KEY_TYPE);
        m_strDupKey = _strKey;
    }

    private void setType(int _i) {
        m_iType = _i;
    }

    private int getType() {
        return m_iType;
    }

    private String getDuplicateKey() {
        return m_strDupKey;
    }

    // override Throwable's getMessage()
    /**
     * (non-Javadoc)
     * getMessage
     *
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        if (getType() == DUPLICATE_KEY_TYPE) {
            return getDuplicateKey() + " already exists in the database. Please select a different key.";
        }
        return super.getMessage();

    }

    /**
    * Return the date/time this class was generated
    * $Id: MetaUpdateException.java,v 1.3 2005/03/08 23:15:46 dave Exp $
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: MetaUpdateException.java,v 1.3 2005/03/08 23:15:46 dave Exp $";
    }

}
