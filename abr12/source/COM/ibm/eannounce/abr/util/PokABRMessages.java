// Licensed Materials -- Property of IBM

//

// (c) Copyright International Business Machines Corporation, 2001

// All Rights Reserved.

//

// $Log4



//package COM.ibm.eannounce.abr.engine;

package COM.ibm.eannounce.abr.util;



public interface PokABRMessages {



  public static final String CLASS_BRAND = new String("$Id: PokABRMessages.java,v 1.2 2004/05/13 19:03:40 chris Exp $");



	// general informational msgs

	public static final String MSG_IAB0001I=

		"<b>IAB0001I: ABRName:</b> %1# <b>Version:</b> %2# <b>Enterprise:</b> %3#";

	public static final String MSG_IAB0002I=

		"IAB0002I: Valid Date: %1# Effective Date: %2#";

	public static final String MSG_IAB0003I=

		"IAB0003I: Setting Entity %1# Attribute Code: %2# to new value: %3#.";



	// general error msgs

	public static final String ERR_IAB1001E=

		"IAB1001E: The %1 is missing the data for the Attribute: %2.";

	public static final String ERR_IAB1002E=

		"IAB1002E: Relators not defined: The %1# %2 must have one and only one %3 relator defined.";

	public static final String ERR_IAB1003E=

		"IAB1003E: Too many relators defined. The %1# %2 has more than one %3 relator defined.";

	public static final String ERR_IAB1004E=

		"IAB1004E: The %1# %2 must have one relator to: %3.";

	public static final String ERR_IAB1005E=

		"IAB1005E: The %1#:%2# entity is locked by %3.";

	public static final String ERR_IAB1006E=

		"IAB1006E: Could not get soft lock.  Execution will continue.";

	public static final String ERR_IAB1007E=

		"IAB1007E: Could not get soft lock.  Rule execution is terminated.";



	// LS specific error msgs

	public static final String ERR_IAB2001E=

		"IAB2001E: The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";

	public static final String ERR_IAB2003E=

		"IAB2003E: %1 status is not Available. The %2 is linked to %3 that has a Status of %4.";

	public static final String ERR_IAB2004E=

		"IAB2004E: The %1 Audience type of %2 %3# is missing the following price field: %4.";

	public static final String ERR_IAB2005E=

		"IAB2005E: The %1 Audience type of %2 %3# does not require the %4 Price value.";

	public static final String ERR_IAB2006E=

		"IAB2006E: The %1 Audience type of %2 %3# does not require the %4 New Price value.";

	public static final String ERR_IAB2007E=

		"IAB2007E: Valid expiration dates for courses linked to %1 are from %2 to %3.";

	public static final String ERR_IAB2008E=

		"IAB2008E: Metadata may have changed for %1! No pricing data found for audience %2.";



	// LS success

	public static final String MSG_IAB2009I =

		"IAB2009I: %1# is now available for use.";

	public static final String MSG_IAB2010I =

		"IAB2010I: %1# is now Retired.";





	// these messages are used if all tested values need to be displayed (showAll=true)

	public static final String MSG_IAB2011I =

		"IAB2011I: The required attribute %1 was populated.";

	public static final String MSG_IAB2012I=

		"IAB2012I: The required relator %1 was found for %2.";



	// LS specific

	public static final String MSG_IAB2013I=

		"IAB2013I: %1 pricing data found for audience %2.";

	public static final String MSG_IAB2014I=

		"IAB2014I: The expiration date for %1 of %2 is within the expiration date for %3 of %4.";

	public static final String MSG_IAB2015I=

		"IAB2015I: %1 has a current Life Cycle status value of %2.";

	public static final String MSG_IAB2016I =

		"IAB2016I: %1# has %2#.";

	public static final String MSG_IAB2017I =

		"IAB2017I: %1# was not updated for %2 because the ABR was %3#.";

	public static final String MSG_IAB2018I =

		"IAB2018I: %1 would be %2# if updates were not suppressed.";



	public static final String ERR_IAB2019E =

		"IAB2019E: %1 %2 require a %3 assignment.";



  public static final String MSG_IAB2020I =

		"IAB2020I: The %1 (%2 %3) %4 was set to null because the Course %5 is %6.";



	public static final String ERR_IAB2021E =

	  "IAB2021E: %1# has not been finialized for %2# (%3# %4#)";

	public static final String ERR_IAB2022E =

	  "IAB2022E: The %1# (%2# %3#) Has deviated from the %4# template for attribute %5#.";

	public static final String ERR_IAB2025E =

	  "IAB2025E: Can't find template: The %1# (%2# %3#) doesn't have a matching template.";

	public static final String ERR_IAB2026E =

	  "IAB2026E: Valid expiration dates for courses attached to %1# (%2# %3#) are from %4# to %5#.";

	public static final String MSG_IAB2023I =

	  "IAB2023I: The %1# (%2# %3#) %4# was set to %5# because it should have a value when %6# is %7#.";

	public static final String MSG_IAB2024I =

	  "IAB2024I: Template check is suspended when %1# is %2#.";

	public static final String MSG_IAB2025I =

		"IAB2025I: %1# template (%2# %3#) associated with %4# (%5# %6#) has an unnecessary %7# relator.";

  public static final String ERR_IAB2027E =

    "IAB2027E: The %1# (%2# %3#) must have an %4# selected.";

  public static final String ERR_IAB2028E =

    "IAB2028E: The %1# (%2# %3#) %4# has invalid data.";

	public static final String ERR_IAB2029E =

		"IAB2029E: ABR cannot pass because the Catalog Editing Status is %1.";

	public static final String ERR_IAB2030E =

		"IAB2030E: ABR cannot pass because the Pricing Status is %1.";

	public static final String ERR_IAB2031E =

		"IAB2031E: The %1# (%2# %3#) must have an %4# selected.";

	public static final String ERR_IAB2032E=

		"IAB2032E: The %1# %2# %3# is linked to %4# %5# %6# with a %7# that is not %8.";

}

