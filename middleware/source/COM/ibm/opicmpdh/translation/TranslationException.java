//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: TranslationException.java,v $
// Revision 1.8  2008/01/31 21:32:03  wendy
// Cleanup RSA warnings
//
// Revision 1.7  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.6  2001/08/22 16:54:02  roger
// Removed author RM
//
// Revision 1.5  2001/03/26 16:35:47  roger
// Misc formatting clean up
//
// Revision 1.4  2001/03/21 00:01:17  roger
// Implement java class file branding in getVersion method
//
// Revision 1.3  2001/03/16 15:52:28  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.translation;




/**
 * The basic exception used by translation
 * @version @date
 */
public class TranslationException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a <code>TranslationException</code> with no specified detail message
     */
    public TranslationException() {
        this("no detail message");
    }

    /**
     * Constructs a <code>TranslationException</code> with the specified detail message
     *
     * @param s 
     */
    public TranslationException(String s) {
        super(s);
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public String getVersion() {
        return "$Id: TranslationException.java,v 1.8 2008/01/31 21:32:03 wendy Exp $";
    }
}
