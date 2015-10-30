/*
 * Created on May 19, 2003
 */
package com.ibm.rdh.chw.caller;

/**
 * @author bobc
 */
public class ClassUtil {

	public static String getSimpleClassName( Object obj )  {
		String ans ; 
		String fullName = getFullClassName( obj ) ;
		int idx = fullName.lastIndexOf( "." ) ; 
		ans = fullName.substring( idx + 1 ) ; 
		return ans ; 
	}
	
	public static String getFullClassName( Object obj ) {
		return obj.getClass().getName() ; 
	}


}
