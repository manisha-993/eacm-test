/*
 * Created on May 19, 2003
 */
package com.ibm.rdh.chw.caller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bobc
 */
public class ClassUtil {

	public static String reg = "(-[A-Z0-9]{6}$)";
	public static Pattern pattern = Pattern.compile(reg);
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
	public static String removeUniqueId(String value){
		String result = value;
		Matcher matcher = pattern.matcher(value);
		//Matcher matcher = 
		if(matcher.find())
			result = value.substring(0,value.length()-7);
		
		return result;
	}

}
