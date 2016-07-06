package com.ibm.pprds.epimshw.util;

/**
 * Static methods for conversions
 * 
 * @author Tim
 *
 */
public class ConversionUtility
{
	
	private ConversionUtility() {}
	
			
	public static String convertNull(String input)
	{
		if (input == null)
		{
			return "";
		}
		return input;
	}

}
