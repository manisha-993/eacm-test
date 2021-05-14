//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.util.logging.Level;

/**
 * Class defining 2 new Logging levels, one for STDOUT, one for STDERR,
 * used when multiplexing STDOUT and STDERR into the same rolling log file
 * via the Java Logging APIs.
 * @author Wendy Stimpson
 */
//$Log: AlwaysLevel.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class AlwaysLevel extends Level  implements EACMGlobals {
	private static final long serialVersionUID = 1L;
	/**
	 * Level for always logging activity.
	 */
	public static Level ALWAYS = new AlwaysLevel(ALWAYS_LEVEL, Level.SEVERE.intValue()+10);
	/**
	 * Private constructor
	 */
	private AlwaysLevel(String name, int value) {
		super(name, value);
	}

	/**
	 * Method to avoid creating duplicate instances when deserializing the object.
	 * @return the singleton instance of this <code>Level</code> value in this classloader
	 * @throws ObjectStreamException If unable to deserialize
	 */
	protected Object readResolve() throws ObjectStreamException {
		if (this.intValue() == ALWAYS.intValue())
			return ALWAYS;;
		throw new InvalidObjectException("Unknown instance :" + this);
	}        
}
