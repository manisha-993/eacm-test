package com.ibm.pprds.epimshw.revenue;

import org.apache.log4j.Logger;

import com.ibm.pprds.epimshw.util.LogManager;


/**
 * Base class for revenue objects that supplies common functionality.
 * 
 * @author Tim
 *
 */
public abstract class RevenueObject
{

	private static Logger _logger = LogManager.getLogManager().getPromoteLogger();

	
	/**
	 * Get the log4j logger for this class.
	 */	
	public Logger getLogger()
	{
		return _logger;
	}

}
