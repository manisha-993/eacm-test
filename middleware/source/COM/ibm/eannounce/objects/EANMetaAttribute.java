
//Copyright (c) 2001, International Business Machines Corp., Ltd.
//All Rights Reserved.
//Licensed for use in connection with IBM business only.

//$Log: EANMetaAttribute.java,v $
//Revision 1.206  2014/02/28 21:15:41  wendy
//RCQ242344 Translation support fallback
//
//Revision 1.205  2012/11/08 21:21:35  wendy
//rollback if error instead of commit
//
//Revision 1.204  2010/11/08 18:40:23  wendy
//Reuse string objects to reduce memory requirements
//
//Revision 1.203  2010/09/14 19:57:59  wendy
//Restore support for COMBOUNIQUE across any entitytype (linkvalue=L)
//
//Revision 1.202  2010/07/12 21:00:28  wendy
//BH SR87, SR655 - extended combounique rule
//
//Revision 1.201  2009/12/09 18:16:48  wendy
//SR11, SR15 and SR17 allow rules by entitytype for 'BH FS EA Enabling Tech Enhancements  20091209'

//Revision 1.200  2009/05/28 13:06:27  wendy
//BH SR 14 - Date warning support

//Revision 1.199  2009/05/11 15:19:16  wendy
//Support dereference for memory release

//Revision 1.198  2008/11/05 22:08:23  wendy
//MN37420204 - support multiple entitytypes on the COMBOUNIQUE rule

//Revision 1.197  2008/04/25 11:43:10  wendy
//support multiple entitytypes exists rule for same attr

//Revision 1.196  2007/12/18 15:09:45  wendy
//MN33363011 fix unique for multiple entity types

//Revision 1.195  2007/08/28 19:00:50  wendy
//Added more debug and chk rs!=null before rs.close()

//Revision 1.194  2007/08/03 11:25:44  wendy
//RQ0713072645-1 Make actions sensitive to the PROFILE's PDHDOMAINs

//Revision 1.193  2007/06/29 20:56:34  wendy
//Added support for RQ110806292 & RQ1103062348, allow EXIST by entitytype+attrcode

//Revision 1.192  2007/06/18 21:55:55  wendy
//Prevent null pointers in search request

//Revision 1.191  2006/06/03 01:07:36  bala
//Add OdsLength capability for LongText 'L' type attributes

//Revision 1.190  2006/06/03 00:49:48  bala
//collect OdsLength for all attibutes (only T,S,U,I) attrs checked earlier

//Revision 1.189  2006/05/12 15:05:10  tony
//*** empty log message ***

//Revision 1.188  2006/05/09 16:09:51  tony
//cr 0428066447

//Revision 1.187  2006/05/02 17:12:37  tony
//CR103103686 check for active as part of the check.

//Revision 1.186  2006/04/28 19:49:05  tony
//refine cr

//Revision 1.185  2006/04/27 20:42:45  tony
//testing of CR

//Revision 1.184  2006/04/26 21:43:44  tony
//CR093005678
//CR0930055856
//Added VALIDATIONRULE

//Revision 1.183  2006/03/08 00:31:53  joan
//make changes for SetAttributeType

//Revision 1.182  2006/02/20 21:39:46  joan
//clean up System.out.println

//Revision 1.181  2006/02/17 17:46:36  tony
//6LY42Z

//Revision 1.180  2006/01/12 23:14:18  joan
//work on CR0817052746

//Revision 1.179  2005/10/11 18:20:01  joan
//add new method for PDG

//Revision 1.178  2005/06/17 23:07:36  joan
//fixes

//Revision 1.177  2005/02/28 19:43:53  dave
//more Jtest fixes from over the weekend

//Revision 1.176  2005/02/15 18:42:24  dave
//More JTest cleanup

//Revision 1.175  2005/02/15 00:02:20  dave
//CR1124045246 - making a place for any
//clear Change Group function to be triggered

//Revision 1.174  2004/10/21 16:49:53  dave
//trying to share compartor

//Revision 1.173  2004/10/15 18:24:17  gregg
//getAttributeTypeInt()

//Revision 1.172  2004/10/15 17:20:03  dave
//syntax

//Revision 1.171  2004/10/15 17:13:53  dave
//o.k. some syntax

//Revision 1.170  2004/10/15 17:06:03  dave
//atttempting some speed up by bypassing instance of checks

//Revision 1.169  2004/08/03 18:24:56  gregg
//isComboUniqueOptionalRequiredAttribute

//Revision 1.168  2004/08/02 21:39:42  gregg
//compile fix

//Revision 1.167  2004/08/02 21:14:55  gregg
//reuse combo unique attribute placeholders for combo unique optional rule.
//this implies either one rule or the other.

//Revision 1.166  2004/08/02 20:49:53  gregg
//unique check optional

//Revision 1.165  2004/03/31 17:46:14  gregg
//going for derived entityid attribute

//Revision 1.164  2004/01/14 23:15:39  dave
//syntax

//Revision 1.163  2004/01/14 22:56:02  dave
//more trimming.. targeting reverse lookup function

//Revision 1.162  2004/01/13 22:51:15  dave
//more squeezing

//Revision 1.161  2004/01/13 22:41:11  dave
//more space squeezing

//Revision 1.160  2004/01/12 21:48:39  dave
//more space claiming

//Revision 1.159  2003/11/14 19:16:29  dave
//syntax

//Revision 1.158  2003/11/14 18:55:20  dave
//added dtd logic for the ls

//Revision 1.157  2003/11/13 19:01:31  dave
//adding XMLFile processing

//Revision 1.156  2003/10/30 02:48:19  dave
//trace

//Revision 1.155  2003/10/30 00:43:31  dave
//fixing all the profile references

//Revision 1.154  2003/08/14 21:22:39  dave
//Syntax

//Revision 1.153  2003/08/14 21:09:43  dave
//syntax  fixes for combo unique

//Revision 1.152  2003/08/14 20:54:55  dave
//fixing unique part number check

//Revision 1.151  2003/08/07 21:31:53  dave
//Adding ComboUniqueGrouping

//Revision 1.150  2003/07/03 16:57:30  dave
//trace

//Revision 1.149  2003/07/02 19:41:06  dave
//making changes for flag -> text combo check

//Revision 1.148  2003/05/14 16:25:16  gregg
//updatePdhMeta method: removed all updates for MLA "Navigate" record

//Revision 1.147  2003/05/06 00:23:09  dave
//trace and 31000 >>> 32000 in EANMetaAttribute

//Revision 1.146  2003/04/23 18:59:38  dave
//syntax

//Revision 1.145  2003/04/23 18:36:49  dave
//spec changes and syntax

//Revision 1.144  2003/04/23 15:57:35  dave
//Clean up.. and first add to autoselector

//Revision 1.143  2003/04/15 22:05:40  gregg
//removing references to MetaLinkAttribute's DisplayOrder

//Revision 1.142  2003/04/14 22:37:19  dave
//syntax

//Revision 1.141  2003/04/14 22:27:47  dave
//syntax

//Revision 1.140  2003/04/14 22:13:30  dave
//clean up , and checking to ensure usEnglishOnly can actually
//write in USEnglish

//Revision 1.139  2003/04/14 20:57:28  dave
//fixing the updateUSEnglishOnly meta rule

//Revision 1.138  2003/04/12 22:31:39  dave
//clean up and reformatting.
//Search Lite weight II

//Revision 1.137  2003/04/08 02:38:38  dave
//commit()

//Revision 1.136  2003/03/27 23:07:21  dave
//adding some timely commits to free up result sets

//Revision 1.135  2003/01/29 01:06:30  joan
//remove System.out

//Revision 1.134  2003/01/29 00:12:10  joan
//add getGeneralAreaList

//Revision 1.133  2003/01/28 23:03:01  gregg
//include GEOINDICATOR in updatePdhMeta method (getAttributeTests)

//Revision 1.132  2003/01/28 21:09:38  joan
//add GEOINDICATOR

//Revision 1.131  2003/01/22 22:05:29  gregg
//alpha rules in updatePdhMeta

//Revision 1.130  2003/01/22 21:57:14  gregg
//fix for ALPHAINTEGERSPECIALUPPER in constructor - setInteger(true) was setNumeric(true)

//Revision 1.129  2003/01/08 23:27:00  dave
//added get ODS Length to the EAN MetaAttribute

//Revision 1.128  2002/12/16 19:34:31  gregg
//better bChagned logic in updatePdhMeta method

//Revision 1.127  2002/12/16 18:32:24  gregg
//compile error fix

//Revision 1.126  2002/12/16 18:23:35  gregg
//return a boolean in updatePdhMeta method indicating whether any database updates were performed.

//Revision 1.125  2002/12/13 22:35:14  dave
//syntax

//Revision 1.124  2002/12/13 22:22:54  dave
//domain enabled search for dynamic search

//Revision 1.123  2002/11/25 18:56:42  gregg
//isOrphan, setOrphan

//Revision 1.122  2002/11/12 20:50:50  dave
//put default maxlen on Text,Longtext, and XML when non
//is present.

//Revision 1.121  2002/11/06 22:39:37  gregg
//removing display statements

//Revision 1.120  2002/11/06 19:12:52  gregg
//introduced deep update for updatePdhMeta method

//Revision 1.119  2002/11/05 23:48:53  gregg
//removed excess _db.freeStatement/isPending at end of constructor (finally cause is enough)

//Revision 1.118  2002/10/31 22:30:11  gregg
//in updatePdhMeta --> expire old MetaHelpValue if it has been removed, but used to exist.

//Revision 1.117  2002/10/21 22:40:20  gregg
//updatePdhMeta: Attribute/Test records for CLASSIFY,NOBLANKS,REFRESHFORM,SEARCHABLE,USENGLISH,GREATER

//Revision 1.116  2002/10/21 20:29:06  gregg
//made setRefresh(), setUSEnglishOnly() methods public (were protected)

//Revision 1.115  2002/10/16 23:31:45  gregg
//setCapability method made public

//Revision 1.114  2002/10/16 23:10:27  gregg
//updatePdhMeta: if new att -> update db cap to 'S' AND setCapability to 'S'.

//Revision 1.113  2002/10/16 00:12:57  gregg
//in updatePdhMeta : db att with which to compare must have capability set externally

//Revision 1.112  2002/10/15 21:54:46  gregg
//in detachPdhMeta - remove Navigate record from MLA also.

//Revision 1.111  2002/10/15 20:05:19  gregg
//more updatePdhMeta debugging

//Revision 1.110  2002/10/15 18:35:11  gregg
//some debug stmts in updatePdhMeta for isNavigate,isExcludeFromCopy

//Revision 1.109  2002/10/15 17:49:22  gregg
//updatePdhMeta: use getDates().getNow for most current time (was from profile)

//Revision 1.108  2002/10/15 16:52:52  gregg
//some more updatePdhMeta - Entity/Attribute records

//Revision 1.107  2002/10/15 16:34:09  gregg
//fix for display order updatePdhMeta

//Revision 1.106  2002/10/14 23:34:11  dave
//invalid method call

//Revision 1.105  2002/10/14 23:08:01  dave
//adding Greater Than Rule

//Revision 1.104  2002/10/14 21:51:04  gregg
//in updatePdhMeta method: default capability to "S" for newly created attributes

//Revision 1.103  2002/10/09 22:46:56  gregg
//removed iNLSID param from getMetaDescriptionControlBlock method

//Revision 1.102  2002/10/09 22:37:52  gregg
//getMetaDescriptionControlBlock method

//Revision 1.101  2002/10/08 20:36:23  gregg
//fix for updatePdhMeta on displayOrder

//Revision 1.100  2002/10/08 20:02:41  gregg
//some more updatePdhMeta for excludeFromCopy

//Revision 1.99  2002/10/08 16:13:04  gregg
//pdhMetaUpdates/Expires for ExcludeFromCopy (linkcode = "Copy")

//Revision 1.98  2002/10/04 16:23:09  gregg
//excludeFromCopy

//Revision 1.97  2002/10/03 22:50:18  gregg
//sort by getActualLongDescription, getActualSortDescription

//Revision 1.96  2002/09/27 21:40:03  dave
//Finishing off dyna search user stub

//Revision 1.95  2002/09/27 18:44:08  gregg
//getAttTypeMappingsArray method

//Revision 1.94  2002/09/26 19:34:29  gregg
//sort by display order added

//Revision 1.93  2002/09/26 19:17:15  dave
//disabling noedit on classification fields

//Revision 1.92  2002/09/24 20:04:08  dave
//Clearer dump statements

//Revision 1.91  2002/09/19 18:25:30  dave
//if MetaAttribute isClassified.. you cannot edit.. unless overt things happen

//Revision 1.90  2002/09/19 14:32:45  dave
//added debug statement to see 'Classification'

//Revision 1.89  2002/09/19 14:20:09  dave
//adding the classifed key to attributes

//Revision 1.88  2002/09/16 19:28:54  dave
//removed a system.out from EANMetaAttribute

//Revision 1.87  2002/09/09 19:46:03  dave
//tracing for isEditable

//Revision 1.86  2002/09/06 17:38:47  gregg
//SortFilterInfo now uses String sort/filter key constants (were ints)

//Revision 1.85  2002/08/16 22:14:40  dave
//fix to the e-announce no blanks exception thingie

//Revision 1.84  2002/08/16 22:00:04  dave
//added noblanks logic.. except on the put of text.. checking

//Revision 1.83  2002/08/08 22:17:04  gregg
//getCompareField(), setCompareField(String) methods required by EANComparable inteface

//Revision 1.82  2002/08/02 17:29:24  joan
//change ALPHAINTEGER

//Revision 1.81  2002/07/26 16:34:48  gregg
//SORT_BY_ATTTYPEMAPPING

//Revision 1.80  2002/07/24 22:25:21  gregg
//setCapability method

//Revision 1.79  2002/07/19 17:25:23  gregg
//sort/filter for meta display stuff...

//Revision 1.78  2002/07/15 21:26:32  gregg
//getActualShortDescription, getActualLongDescription methods added.

//Revision 1.77  2002/06/18 00:43:33  gregg
//getAttributeTypeMapping method

//Revision 1.76  2002/06/17 18:18:06  gregg
//EANComparable stuff

//Revision 1.75  2002/06/03 19:45:33  gregg
//null ptr fix in getNavigate method

//Revision 1.74  2002/06/03 16:15:50  gregg
//in getNavigate(db) -> no longer throw exception when parent is not an EG

//Revision 1.73  2002/05/24 21:37:37  gregg
//ALPHANUMERICSPECIAL,ALPHANUMERICSPECIALUPPER,ALPHASPECIAL,ALPHASPECIALUPPER,ALPHAUPPER added in constructor

//Revision 1.72  2002/05/06 19:14:24  dave
//mod isRefreshEnabled to include returning true on all status
//values

//Revision 1.71  2002/05/06 18:42:37  dave
//added refreshform to metaattribute.  This says
//the caller needs to refresh form values if attribute is changed
//when set to true

//Revision 1.70  2002/05/03 18:21:57  gregg
//replaced gbl6010 w/ gbl8610, gbl6011 w/ gbl8611

//Revision 1.69  2002/04/29 16:01:25  joan
//add code for blob attribute

//Revision 1.68  2002/04/17 20:17:05  dave
//new XMLAttribute and its MetaPartner

//Revision 1.67  2002/04/16 23:02:26  gregg
//fix for updatePdhMeta - Unique rule

//Revision 1.66  2002/04/15 21:17:38  gregg
//updatePdhMeta -> set parent to EntityGroup for att_db

//Revision 1.65  2002/04/12 21:46:53  dave
//typo in USENGLISH check

//Revision 1.64  2002/04/12 21:18:01  dave
//introduced english only concept in EANMetaAttribute

//Revision 1.63  2002/04/11 23:41:13  dave
//toString on entityItem is limited to navigateable attributes only

//Revision 1.62  2002/04/09 18:09:43  gregg
//in getNavigate method -> throw excep. if for some reason parent is not an EntityGroup

//Revision 1.61  2002/04/09 18:03:19  gregg
//setNavigate(b) in constructor

//Revision 1.60  2002/04/09 00:46:48  dave
//fixing up isEditable to include looking at read/write language

//Revision 1.59  2002/04/04 00:16:02  gregg
//removed setValto,valFrom...

//Revision 1.58  2002/04/02 21:38:57  gregg
//getValFrom, getValTo methods added

//Revision 1.57  2002/03/21 19:10:58  dave
//added MetaHelp into the dump

//Revision 1.56  2002/03/21 18:50:54  dave
//syntax fixes

//Revision 1.55  2002/03/21 18:38:57  dave
//added getHelp to the EANTableModel

//Revision 1.54  2002/03/19 17:51:38  gregg
//debug stmt

//Revision 1.53  2002/03/19 17:38:50  gregg
//check for getMetaHelpValue() != null on updatePdh

//Revision 1.52  2002/03/15 23:31:51  gregg
//update helpTextValues

//Revision 1.51  2002/03/14 23:00:40  gregg
//fixes to update ODSLength...

//Revision 1.50  2002/03/14 22:46:26  gregg
//displayOrder update ..

//Revision 1.49  2002/03/14 21:50:48  gregg
//update ODSLength

//Revision 1.48  2002/03/14 20:07:09  gregg
//MetaHelpValue

//Revision 1.47  2002/03/14 17:51:38  gregg
//cleanup of some update code

//Revision 1.46  2002/03/14 01:18:39  gregg
//we cannot create a new EANMetaAttribute using db for an attribute that is non-existent in the db.

//Revision 1.45  2002/03/13 22:18:48  gregg
//include odsLength in attTypes 'I' also

//Revision 1.44  2002/03/13 01:17:21  gregg
//added debug stmt for gbl7503

//Revision 1.43  2002/03/13 01:06:36  gregg
//ods length

//Revision 1.42  2002/03/12 19:31:07  dave
//adding stuff for proper column header display

//Revision 1.41  2002/03/12 01:26:28  gregg
//detachPdhMeta() method added

//Revision 1.40  2002/03/09 00:45:36  gregg
//EXIST added in getAttributeTests()

//Revision 1.39  2002/03/08 23:20:23  gregg
//flip-flopped some Long/Short desc's

//Revision 1.38  2002/03/08 18:43:26  gregg
//fix array out-of bounds exception

//Revision 1.37  2002/03/08 01:09:02  gregg
//Attribute/Test updates completes (first pass..)

//Revision 1.36  2002/03/08 00:52:39  gregg
//Attribute/Test updates/expires

//Revision 1.35  2002/03/07 00:49:24  gregg
//more debug statements for update...

//Revision 1.34  2002/03/06 23:46:19  gregg
//debugs added

//Revision 1.33  2002/03/06 19:01:29  gregg
//more MetaLinkAttr update rows (Entity/Attribute, Attribute/Test)

//Revision 1.32  2002/03/06 00:18:49  gregg
//added linkCode as a parameter to gbl7507

//Revision 1.31  2002/03/05 22:48:06  gregg
//syntax

//Revision 1.30  2002/03/05 22:46:06  gregg
//getMetaLinkAttrRowsForUpdate() method expanded

//Revision 1.29  2002/03/05 04:17:43  dave
//trace and debug fixes

//Revision 1.28  2002/03/02 01:01:06  gregg
//first pass at update stuff...

//Revision 1.27  2002/02/28 19:09:22  dave
//adding 7502 and adding navflag to denote nav attributes

//Revision 1.26  2002/02/26 21:43:59  dave
//ensuring I am setting the rs = null after a close

//Revision 1.25  2002/02/18 18:47:15  dave
//more fixes

//Revision 1.24  2002/02/12 18:21:35  dave
//more dump rationale

//Revision 1.23  2002/02/12 18:10:22  dave
//more changes

//Revision 1.22  2002/02/06 00:02:56  dave
//more syntax fixes

//Revision 1.21  2002/02/05 18:17:48  dave
//more fixes

//Revision 1.20  2002/02/02 21:39:19  dave
//more  fixes to tighen up import

//Revision 1.19  2002/02/02 20:56:54  dave
//tightening up code

//Revision 1.18  2002/02/01 00:42:32  dave
//more foundation fixes for passing _prof

//Revision 1.17  2002/01/02 17:50:05  dave
//sync between 1.0 and 1.1

//Revision 1.16  2001/11/26 18:42:47  dave
//carry forward for metaflag fix

//Revision 1.15  2001/10/12 00:27:05  dave
//fixes

//Revision 1.14  2001/10/12 00:18:18  dave
//test for MetaFlagAttributeList

//Revision 1.13  2001/10/04 23:32:23  dave
//fixes

//Revision 1.12  2001/10/04 23:28:34  dave
//fixes

//Revision 1.11  2001/10/04 23:05:46  dave
//more fixes

//Revision 1.10  2001/10/04 22:38:26  dave
//more fixes

//Revision 1.9  2001/10/04 22:21:14  dave
//fixes

//Revision 1.8  2001/10/04 22:09:28  dave
//more constructor cool stuff

//Revision 1.7  2001/10/04 21:04:43  dave
//more fixes

//Revision 1.6  2001/10/04 20:58:12  dave
//more massive changes to eannounce objects

//Revision 1.5  2001/08/02 19:13:59  dave
//display fixes

//Revision 1.4  2001/08/02 19:08:13  dave
//adjustments for compile

//Revision 1.3  2001/08/02 18:37:12  dave
//final syntax

//Revision 1.2  2001/08/02 18:16:32  dave
//syntax

//Revision 1.1  2001/08/02 18:10:32  dave
//more conversion.. getting close



package COM.ibm.eannounce.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import COM.ibm.opicmpdh.objects.ControlBlock;

/**
 * EANMetaAttribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */ 
public abstract class EANMetaAttribute extends EANMetaFoundation implements EANComparable {

	public static final int TEXT_MAX_LEN = 254; // T attrs
	public static final int LONGTEXT_MAX_LEN = 32000; // L and X attrs

	// Instance variables
	/**
	 *@serial
	 */
	final static long serialVersionUID = 1L;
	private static final String DEFAULT_TYPE="Value";  //LINKCODE:	Value | n(entitytype)

	private Hashtable m_hshOverrides = null; //SR11, SR15 and SR17 allow rules by entitytype

	private String m_strAttributeType = null;
	private String m_strCapability = null;

	protected boolean m_balpha = false; // ALPHA from ALPHAINTEGERSPECIALUPPER,ALPHAINTEGERSPECIAL,ALPHASPECIALUPPER,ALPHASPECIAL
	protected boolean m_bnumeric = false; //NUMERIC
	protected boolean m_bspecial = false;// from ALPHAINTEGERSPECIALUPPER,ALPHAINTEGERSPECIAL,ALPHASPECIALUPPER,ALPHASPECIAL
	protected boolean m_bupper = false; //UPPER
	protected boolean m_bdate = false; // DATE, FUTUREDATE, PASTDATE
	protected boolean m_btime = false; //TIME
	protected boolean m_bfuture = false; //FUTUREDATE
	private boolean m_bwarningDate = false; //BH SR 14
	protected boolean m_bpast = false; //PASTDATE
	protected boolean m_binteger = false; //INTEGER
	protected boolean m_bdecimal = false; //DECIMAL
	protected boolean m_breq = false; //REQUIRED
	protected String m_strDV = null; //DEFAULT
	private boolean m_bexpireOtherNls = false; //RCQ242344 Translation support fallback
	private String m_strExpireOtherNLSAttr = null;

	/**
	 * FIELD
	 */
	protected boolean m_bUnique = false;
	/**
	 * FIELD
	 */
	protected boolean m_bPDGINFO = false;

	/**
	 * FIELD
	 */
	protected boolean m_bCmbU = false;
	/**
	 * FIELD
	 */
	protected boolean m_bCmbUOpt = false;
	protected boolean m_bSpChk = false; //SPELLCHECK
	/**
	 * FIELD
	 */
	protected String m_strUnTp = null;
	/**
	 * FIELD
	 */
	protected String m_strUnCl = null;
	/**
	 * FIELD
	 */
	private Vector m_vctCmbUnAC = null;
	private Hashtable m_tblCmboUn = null; //MN37420204 - must allow for attribute on different entity types
	private String m_strCmbUnGr = null; // allow for 'L' on COMBOUNIQUE
	/**
	 * FIELD
	 */
	protected Vector m_vctCmbUnOptAC = null;
	/**
	 * FIELD
	 */
	protected String m_strCmbUnOptGr = null;
	/**
	 * FIELD
	 */
	protected boolean m_bCmbUOptReqAtt = false;
	protected int m_imax = 0; //MAX
	protected int m_imin = 0; //MIN
	protected int m_iequals = 0; //EQU
	protected int m_iDcPlc = 0; //DECIMAL, number of decimal places
	private int m_iOrder = 999;
	private boolean m_bNavigate = false;
	/**
	 * FIELD
	 */
	protected int m_iOdsLength = -1;
	/**
	 * FIELD
	 */
	protected MetaHelpValue m_oMhv = null;

	private boolean m_bUSEnglishOnly = false; //USENGLISH
	private boolean m_bRefresh = false;
	private boolean m_bSelectHelper = false;
	protected boolean m_bNoBlanks = false; //NOBLANKS
	/**
	 * FIELD
	 */
	protected boolean m_bClassified = false;
	/**
	 * FIELD
	 */
	protected boolean m_bSearchable = false;
	/**
	 * FIELD
	 */
	protected boolean m_bGeoIn = false;
	private boolean m_bExCpy = false;
	private boolean m_bGreater = false;//GREATER
	protected int m_iGreater = 0; //GREATER than value
	/**
	 * FIELD
	 */
	protected boolean m_bOrphan = false;
	/**
	 * FIELD
	 */
	protected boolean m_bDmnCtr = false;
	/**
	 * FIELD
	 */
	protected boolean m_bRevLkUp = false;

	/**
	 * FIELD
	 */
	protected String m_strXMLDTD = null;

	/**
	 * FIELD
	 */
	protected boolean m_bDerived = false;

	/**
	 * FIELD
	 */
	public final static String SORT_BY_LONGDESC = "LD";
	/**
	 * FIELD
	 */
	public final static String SORT_BY_SHORTDESC = "SD";
	/**
	 * FIELD
	 */
	public final static String SORT_BY_ATTCODE = "AC";
	/**
	 * FIELD
	 */
	public final static String SORT_BY_ATTTYPE = "AT";
	/**
	 * FIELD
	 */
	public final static String SORT_BY_ATTTYPEMAPPING = "ATM";
	/**
	 * FIELD
	 */
	public final static String SORT_BY_IS_ORPHAN = "O";

	private String m_strSortType = SORT_BY_LONGDESC;
	private boolean m_bDisplayableForFilter = true;

	// this just keeps track of whether this EG ~should~ be displayed - its up to calling party to display this EG or not

	//some attribute type mapping constants
	private final static String TEXT_TYPE = "Text";
	private final static String ID_TYPE = "Id";
	private final static String LONGTEXT_TYPE = "Long Text";
	private final static String MULTIPLEFLAG_TYPE = "Multiple Flag";
	private final static String UNIQUEFLAG_TYPE = "Unique Flag";
	private final static String STATUS_TYPE = "Status";
	private final static String ABR_TYPE = "ABR";
	private final static String BLOB_TYPE = "Blob";
	private final static String XML_TYPE = "XML";
	private final static String[] TYPE_MAPPINGS = { TEXT_TYPE, ID_TYPE, LONGTEXT_TYPE, MULTIPLEFLAG_TYPE, UNIQUEFLAG_TYPE, STATUS_TYPE, ABR_TYPE, BLOB_TYPE, XML_TYPE };

	//some attribute type mapping constants
	//used instead of instance of .. should be faster in some cases
	/**
	 * FIELD
	 */
	public final static int IS_TEXT = 0;
	/**
	 * FIELD
	 */
	public final static int IS_ID = 1;
	/**
	 * FIELD
	 */
	public final static int IS_LONGTEXT = 2;
	/**
	 * FIELD
	 */
	public final static int IS_MULTI = 3;
	/**
	 * FIELD
	 */
	public final static int IS_SINGLE = 4;
	/**
	 * FIELD
	 */
	public final static int IS_STATUS = 5;
	/**
	 * FIELD
	 */
	public final static int IS_ABR = 6;
	/**
	 * FIELD
	 */
	public final static int IS_BLOB = 7;
	/**
	 * FIELD
	 */
	public final static int IS_XML = 8;

	/**
	 * FIELD
	 */
	protected int m_iAttributeType = -1;

	/**
	 * FIELD
	 */
	protected String[] m_aWGDefaults = null;
	protected boolean m_bWGDEFAULT = false;

	/**
	 * CR093005678
	 * CR0930055856
	 */
	protected boolean m_bValidRule = false;
	/**
	 * CR093005678
	 * CR0930055856
	 */
	protected Hashtable m_hshValidRule = null;

	/**
	 *cr 0428066447
	 */
	protected Hashtable m_hshSearchHidden = null;

	private Hashtable m_hshUnique = null;
	
	/**
	 *  Gets the version attribute of the EANMetaAttribute object
	 *
	 *@return    The version value
	 */
	public String getVersion() {
		return "$Id: EANMetaAttribute.java,v 1.206 2014/02/28 21:15:41 wendy Exp $";
	}

	/**
	 *  Main method which performs a simple test of this class
	 *
	 *@param  arg  Description of the Parameter
	 */
	public static void main(String arg[]) {
	}

	/**
	 *  Creates the PDHMetaAttribute with the Default NLSID and Value
	 *
	 *@param  _emf                            Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@param  _s1                             Description of the Parameter
	 *@param  _s2                             Description of the Parameter
	 *@param  _s3                             Description of the Parameter
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EANMetaAttribute(EANMetaFoundation _emf, Profile _prof, String _s1, String _s2, String _s3) throws MiddlewareRequestException {
		super(_emf, _prof, _s1);
		m_strAttributeType = _s2;
		m_strCapability = _s3;
	}

	/**
	 *  Creates the PDHMetaAttribute with the Default NLSID and Value
	 *
	 *@param  _s1                             attributcode
	 *@param  _emf                            Description of the Parameter
	 *@param  _db                             Description of the Parameter
	 *@param  _prof                           Description of the Parameter
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public EANMetaAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1) throws SQLException, MiddlewareException, MiddlewareRequestException {
		this(_emf,_db,_prof, _s1,null);
	}
	public EANMetaAttribute(EANMetaFoundation _emf, Database _db, Profile _prof, String _s1, Hashtable memTbl) throws SQLException, MiddlewareException, MiddlewareRequestException {

		super(_emf, _prof, _s1);

		try {
	        //attempt to use less memory, hang onto strings already used
	        Hashtable mymemTbl = null;
	        if(memTbl==null){ // may not be passed in, so create it here
	        	mymemTbl = new Hashtable();
	        	memTbl = mymemTbl;
	        }

			ReturnStatus returnStatus = new ReturnStatus(-1);
			ReturnDataResultSet rdrs = null;
			ResultSet rs = null;

			try {

				Profile prof = getProfile();
				boolean success = false;

				try {
					boolean found = false;
					rs = _db.callGBL8610(returnStatus, prof.getEnterprise().trim(), prof.getRoleCode().trim(), _s1, prof.getReadLanguage().getNLSID(), prof.getValOn(), prof.getEffOn());

					while (rs.next()) {
						found = true;
						String s1 = EANUtility.reuseObjects(memTbl,rs.getString(1).trim());
						String s2 = EANUtility.reuseObjects(memTbl,rs.getString(2).trim());
						int i3 = rs.getInt(3);
						String s4 = EANUtility.reuseObjects(memTbl,rs.getString(4).trim());
						String s5 = EANUtility.reuseObjects(memTbl,rs.getString(5).trim());
						String s6 = rs.getString(6).trim();
						//ValFrom
						String s7 = rs.getString(7).trim();
						//ValTo

						_db.debug(D.EBUG_SPEW, "gbl8610 answer:" + s1 + ":" + s2 + ":" + i3 + ":" + s4 + ":" + s5 + ":" + s6 + ":" + s7);

						setAttributeType(s1);
						m_strCapability = s2;
						putShortDescription(i3, s4);
						putLongDescription(i3, s5);

					}
					success = true;
					if (!found){
						// cant continue, no attribute type set or anything else
						String msg = "WARNING gbl8610 had NO answer for :" + prof.getEnterprise() + ":" +
						prof.getRoleCode() + ":" + _s1 + ":" + prof.getReadLanguage().getNLSID() + ":" +
						prof.getValOn() + ":" + prof.getEffOn();
						_db.debug(D.EBUG_ERR, msg);
						throw new MiddlewareException(msg);
					}
				} finally {
					if (rs!=null){
						rs.close();
						rs = null;
					}
					if(success){
						_db.commit();
					}else{
						_db.rollback();
					}
					_db.freeStatement();
					_db.isPending();
				}

				try {
					success = false;
					// Get the description
					rs = _db.callGBL8611(returnStatus, prof.getEnterprise().trim(), _s1, prof.getValOn(), prof.getEffOn());
					rdrs = new ReturnDataResultSet(rs);
					success = true;
				} finally {
					if (rs!=null){
						rs.close();
						rs = null;
					}
					if(success){
						_db.commit();
					}else{
						_db.rollback();
					}
					_db.freeStatement();
					_db.isPending();
				}


				//LINKTYPE:	 Attribute/Test
				//LINKTYPE1: n(attributecode)
				//LINKTYPE2: MAX (or any others that set values)
				//LINKCODE:	 Value | n(entitytype)
				//LINKVALUE: length

				//LINKTYPE:	 Attribute/Test
				//LINKTYPE1: n(attributecode)
				//LINKTYPE2: INTEGER  (or any of the other booleans)
				//LINKCODE:	 Value | n(entitytype)
				//LINKVALUE: 0 |false  - (existence of this row assumes true, use 'false' to turn this off)

				//LINKTYPE:	 Attribute/Test
				//LINKTYPE1: attributecode
				//LINKTYPE2: COMBOUNIQUE - valid for flag and text, BH SR87, SR655 text and text or flag and flag combinations
				//LINKCODE:	 other attributecodes delimited by ':' or multiple rows for linktype1
				//LINKVALUE: entitytype or 'L'

				//LINKTYPE:  Attribute/Test
				//LINKTYPE1: attributecode    - like MKTGNAME
				//LINKTYPE2: ExpireOtherNLS
				//LINKCODE:  Value | entitytype - if controlled by entitytype - MODEL
				//LINKVALUE: TRANSLATIONWATCH:TWON - attribute to update:attribute value to use for update

				for (int ii = 0; ii < rdrs.size(); ii++) {

					String s1 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 0)); //linktype2
					String s2 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 1)); //linkcode
					String s3 = EANUtility.reuseObjects(memTbl,rdrs.getColumn(ii, 2)); //linkvalue

					_db.debug(D.EBUG_SPEW, "gbl8611:answer:" + s1 + ":" + s2 + ":" + s3);

					// Get all the rules....
					if (s1.equals("MAX")) {
						setMaxLen(Integer.parseInt(s3),s2);
					} else if (s1.equals("MIN")) {
						setMinLen(Integer.parseInt(s3),s2);
					} else if (s1.equals("GREATER")) {
						setGreater(Integer.parseInt(s3));
					} else if (s1.equals("EQU")) {
						setEqualsLen(Integer.parseInt(s3),s2);
					} else if (s1.equals("ALPHA")) { //linkvalue is 0,and was always turned on
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("UPPER")) {
						//setUpper(true);
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("NUMERIC")) {
						//setNumeric(true);
						setNumeric(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equalsIgnoreCase("ExpireOtherNLS")) { //RCQ242344
						boolean turnon = !s3.equalsIgnoreCase("false");
						if (turnon && s3.indexOf(':') != -1){
							m_strExpireOtherNLSAttr = s3; // parse it later, use less storage
						}
						setExpireOtherNLS(turnon,s2);//linkvalue=false will support override turning this off
					}else if (s1.equals("UPPERNUMERIC")) {
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						setNumeric(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("INTEGER")) {//linkvalue is 0,and was always turned on
						//setInteger(true);
						setInteger(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("DECIMAL")) { //was always turned on, linkvalue is decimal places, set=false to turnoff
						//setDecimal(true);
						boolean isTurnedOn = !s3.equalsIgnoreCase("false");
						setDecimal(isTurnedOn,s2);
						if (isTurnedOn){ // if not turned off for an entitytype
							if (s3.equals("Y")) {
								//setDecimalPlaces(2);
								setDecimalPlaces(2,s2);
							} else {
								//setDecimalPlaces(Integer.valueOf(s3).intValue());
								setDecimalPlaces(Integer.valueOf(s3).intValue(),s2);
							}
						}else{
							setDecimalPlaces(0,s2); // was turned off for this type
						}
					} else if (s1.equals("EXIST")) {
						//setRequired(true);
						setRequired(s2,s3); //RQ110806292
					} else if (s1.equals("DATE")) {
						setDate(true);
					} else if (s1.equals("DEFAULT")) {
						//setDefaultValue(s3);
						setDefaultValue(s3,s2);
					} else if (s1.equals("ALPHAINTEGER")) {
						//setInteger(true);
						setInteger(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHAINTEGERUPPER")) {
						//setInteger(true);
						setInteger(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setUpper(true);
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHAINTEGERSPECIAL")) {
						//setInteger(true);
						setInteger(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setSpecial(true);
						setSpecial(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHAINTEGERSPECIALUPPER")) {
						//setInteger(true);
						setInteger(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setUpper(true);
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setSpecial(true);
						setSpecial(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHASPECIAL")) {
						//setSpecial(true);
						setSpecial(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHASPECIALUPPER")) {
						//setSpecial(true);
						setSpecial(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setUpper(true);
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("ALPHAUPPER")) {
						//setAlpha(true);
						setAlpha(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
						//setUpper(true);
						setUpper(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("PASTDATE")) {
						//SG	Attribute/Test	ANNDATEMACTUL	PASTDATE	Warning	0
						setDate(true);
						setPastDate(true);
						setWarningDate(s2.equalsIgnoreCase("Warning")); //BH SR 14
					} else if (s1.equals("FUTUREDATE")) {
						setDate(true);
						setFutureDate(true);
						setWarningDate(s2.equalsIgnoreCase("Warning")); //BH SR 14
					} else if (s1.equals("UNIQUE")) {
						setUnique(true);
						setUniqueClassType(s2,s3);
						//setUniqueClass(s2); MN33363011
						//setUniqueType(s3); MN33363011
					} else if (s1.equals("COMBOUNIQUE")) {
						setComboUnique(true);
						setComboUniqueAttributeCode(s2,s3);// parent group type may not be known when constructed
						//setComboUniqueGrouping(s3);MN37420204 - must allow for attribute on different entity types
					} else if (s1.equals("COMBOUNIQUEOPTIONAL")) {
						setComboUniqueOptional(true);
						setComboUniqueAttributeCode(s2);
						setComboUniqueOptionalRequiredAttribute((s3.trim().equalsIgnoreCase("Required")));
					} else if (s1.equals("SPELLCHECK")) {
						//setSpellCheckable(true);
						setSpellCheckable(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("TIME")) {
						setTime(true);
					} else if (s1.equals("USENGLISH")) {
						setUSEnglishOnly(true);
						// removed from spec because of ODS usage setUSEnglishOnly(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("REVERSELOOKUP")) {
						setReverseLookup(true);
					} else if (s1.equals("REFRESHFORM")) {
						setRefresh(true);
					} else if (s1.equals("NOBLANKS")) {
						//setNoBlanks(true);
						setNoBlanks(!s3.equalsIgnoreCase("false"),s2);//linkvalue=false will support override turning this off
					} else if (s1.equals("CLASSIFY")) {
						setClassified(true);
					} else if (s1.equals("SEARCHABLE")) {
						setSearchable(true);
					} else if (s1.equals("GEOINDICATOR")) {
						setGeoIndicator(true);
					} else if (s1.equals("SELECTHELPER")) {
						setSelectHelper(true);
					} else if (s1.equals("XMLDTD")) {
						setXMLDTD(s3);
					} else if (s1.equals("PDGINFO")) {
						setPDGINFO(true);
					} else if (s1.equals("WGDEFAULT")) {
						setWGDEFAULT(true);
					} else if (s1.equals("VALIDATIONRULE")) {	//CR093005678 CR0930055856
						setValidationRule(true);				//CR093005678 CR0930055856
						addValidationRule(s2,s3);				//CR093005678 CR0930055856
					} else if (s1.equals("SEARCHHIDDENPREFILL")) {	//cr0428066447
						setSearchHidden(s2,s3);						//cr0428066447
					}
				}

				//only set ODSLength if attType in ('T','S','U')
				// it is up to subclasses to provide their own access to this variable
				// o.k. we are going to combine this w/
				if (getAttributeType().equals("T") || getAttributeType().equals("S") || getAttributeType().equals("U") || getAttributeType().equals("I") || getAttributeType().equals("L")) {
					try {
						success = false;
						rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "ODSLength", "ODS", getAttributeCode(), "Length", getProfile().getValOn(), getProfile().getEffOn());
						rdrs = new ReturnDataResultSet(rs);
						success = true;
					} finally {
						if (rs !=null){
							rs.close();
							rs = null;
						}
						if(success){
							_db.commit();
						}else{
							_db.rollback();
						}
						_db.freeStatement();
						_db.isPending();
					}
					if (rdrs!=null && rdrs.getRowCount() > 0) {
						String strOdsLen = rdrs.getColumn(0, 0);
						try {
							m_iOdsLength = Integer.parseInt(strOdsLen);
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						}
					}
				}
				//get the helpValue associated w/ this attribute
				setMetaHelpValue(new MetaHelpValue(this, _db, getProfile()));
				setNavigate(getNavigate(_db));
			} finally {
				_db.freeStatement();
				_db.isPending();
			    if(mymemTbl!=null){ // may not be passed in, so release it here
				    mymemTbl.clear();
			    }
			}

		} finally {
			_db.freeStatement();
			_db.isPending();
		}

	}

	/**
	 *  set the MetaHelpValue associatied w/ this attribute
	 *
	 *@param  _oMhv  The new metaHelpValue value
	 */
	protected void setMetaHelpValue(MetaHelpValue _oMhv) {
		m_oMhv = _oMhv;
		return;
	}

	/**
	 *  Get the MetaHelpValue associated w/ this attribute
	 *
	 *@return    The metaHelpValue value
	 */
	public MetaHelpValue getMetaHelpValue() {
		return m_oMhv;
	}

	/**
	 *  Gets the helpValueText attribute of the EANMetaAttribute object
	 *
	 *@return    The helpValueText value
	 */
	public String getHelpValueText() {
		if (m_oMhv != null) {
			return m_oMhv.getHelpValueText();
		} else {
			return "No help is available for this item.";
		}
	}

	/**
	 *  Sets the spellCheckable attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new spellCheckable value
	 * /
    public void setSpellCheckable(boolean _b) {
        m_bSpChk = _b;
    }

    /**
	 *  Gets the spellCheckable attribute of the EANMetaAttribute object
	 *
	 *@return    The spellCheckable value
	 * /
    public boolean isSpellCheckable() {
        return m_bSpChk;
    }*/

	/**
	 * SR11, SR15 and SR17 allow SPELLCHECK by entitytype
	 *  Sets the spellCheckable attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new spellCheckable value
	 */
	public void setSpellCheckable(boolean _b) {
		setSpellCheckable(_b,DEFAULT_TYPE);
	}
	public void setSpellCheckable(boolean _b,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bSpChk = _b;
		}else{
			// specific entitytype was specified
			setValueByType("SPELLCHECK",etype, new Boolean(_b)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow SPELLCHECK by entitytype
	 *  Gets the spellCheckable attribute of the EANMetaAttribute object
	 *
	 *@return    The spellCheckable value
	 */
	public boolean isSpellCheckable() {
		return isValueSetByType("SPELLCHECK", m_bSpChk);
	}
	/**
	 *  Returns the AttributeCode
	 *
	 *@return    the AttributeCode
	 */
	public String getAttributeCode() {
		return getKey();
	}

	/**
	 *  Returns the AttributeType
	 *
	 *@return    the AttributeType
	 */
	public String getAttributeType() {
		return m_strAttributeType;
	}

	/**
	 *  Get the short description without any '*' indicating required field. Use
	 *  this for MetaUI.
	 *
	 *@return    super.getShortDescription()
	 */
	public String getActualShortDescription() {
		return super.getShortDescription();
	}

	/**
	 *  Get the long description without any '*' indicating required field. Use
	 *  this for MetaUI.
	 *
	 *@return    super.getLongDescription()
	 */
	public String getActualLongDescription() {
		return super.getLongDescription();
	}

	/**
	 *  Returns the full word for the AttributeType
	 *
	 *@return    the AttributeType
	 */
	public String getAttributeTypeMapping() {

		if (getAttributeType() == null) {
			return null;
		} else if (getAttributeType().equals("T")) {
			return TEXT_TYPE;
		} else if (getAttributeType().equals("I")) {
			return ID_TYPE;
		} else if (getAttributeType().equals("L")) {
			return LONGTEXT_TYPE;
		} else if (getAttributeType().equals("X")) {
			return XML_TYPE;
		} else if (getAttributeType().equals("F")) {
			return MULTIPLEFLAG_TYPE;
		} else if (getAttributeType().equals("U")) {
			return UNIQUEFLAG_TYPE;
		} else if (getAttributeType().equals("S")) {
			return STATUS_TYPE;
		} else if (getAttributeType().equals("A")) {
			return ABR_TYPE;
		} else if (getAttributeType().equals("B")) {
			return BLOB_TYPE;
		}
		return getAttributeType();
	}

	/**
	 *  Gets the attTypeMappingsArray attribute of the EANMetaAttribute class
	 *
	 *@return    The attTypeMappingsArray value
	 */
	public static String[] getAttTypeMappingsArray() {
		return TYPE_MAPPINGS;
	}

	/**
	 *  Returns the Capability
	 *
	 *@return    the Capability
	 */
	public String getCapability() {
		return m_strCapability;
	}

	/**
	 *  Set the capability of this MetaAttribute
	 *
	 *@param  _s  The new capability value
	 */
	public void setCapability(String _s) {
		m_strCapability = _s;
	}

	/**
	 *  Returns true if this MetaAttribute is editable
	 *
	 *@return    true if this MetaAttribute is editable
	 */
	public boolean isEditable() {

		// Is the read language contained in the Write Language?
		Profile prof = getProfile();							//6LY42Z
		if (prof != null && !prof.isLanguageUpdatable()) {		//6LY42Z
			return false;
		}

		if (isUSEnglishOnly() && prof != null && !prof.isUSEnglishUpdatable()) {	//6LY42Z
			return false;
		}

		return (m_strCapability.equals("W") || m_strCapability.equals("S"));
	}

	/**
	 *  Returns true if this MetaAttribute is super editable
	 *
	 *@return    true if this MetaAttribute is super editable
	 */
	public boolean isSuperEditable() {
		return m_strCapability.equals("S");
	}

	/**
	 *  sets the Alpha rule for this object
	 *
	 *@param  _b1  The new alpha value
	 * /
    public void setAlpha(boolean _b1) {
        m_balpha = _b1;
    }
    /**
	 *  Returns true if the Alpha rule is enabled otherwise, false.
	 *
	 *@return    is alpha rule enabled or not
	 * /
    public boolean isAlpha() {
        return m_balpha;
    }*/
	/**
	 * SR11, SR15 and SR17 allow ALPHA by entitytype
	 *  sets the Alpha rule for this object
	 *
	 *@param  _b1  The new alpha value
	 */
	public void setAlpha(boolean _b1) {
		setAlpha(_b1,DEFAULT_TYPE);
	}
	public void setAlpha(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_balpha = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("ALPHA",etype, new Boolean(_b1)); // save new values by type
		}
	}

	/**
	 * SR11, SR15 and SR17 allow ALPHA by entitytype
	 *  Returns true if the Alpha rule is enabled otherwise, false.
	 *
	 *@return    is alpha rule enabled or not
	 */
	public boolean isAlpha() {
		return isValueSetByType("ALPHA", m_balpha);
	}

	/**
	 *  sets the Classisfication flag for this object
	 *
	 *@param  _b  The new classified value
	 */
	public void setClassified(boolean _b) {
		m_bClassified = _b;
	}

	/**
	 *  Returns true if this is a Classification Attribute
	 *
	 *@return    is alpha rule enabled or not
	 */
	public boolean isClassified() {
		return m_bClassified;
	}

	/**
	 *  sets the Searchable flag for this object
	 *
	 *@param  _b  The new searchable value
	 */
	public void setSearchable(boolean _b) {
		m_bSearchable = _b;
	}

	/**
	 *  Returns true if this is a Classification Attribute
	 *
	 *@return    is alpha rule enabled or not
	 */
	public boolean isSearchable() {
		return m_bSearchable;
	}

	/**
	 *  sets the GeoIndicator flag for this object
	 *
	 *@param  _b  The new geoIndicator value
	 */
	public void setGeoIndicator(boolean _b) {
		m_bGeoIn = _b;
	}

	/**
	 *  Returns true if this is a GeoIndicator Attribute
	 *
	 *@return    The geoIndicator value
	 */
	public boolean isGeoIndicator() {
		return m_bGeoIn;
	}

	/**
	 * @param key - rule like MAX
	 * @param etype
	 * @param value
	 */
	private void setValueByType(String key,String etype, Object value){
		if(m_hshOverrides==null){
			m_hshOverrides = new Hashtable();
		}
		Hashtable valueTbl = (Hashtable)m_hshOverrides.get(key);
		if (valueTbl==null){
			valueTbl = new Hashtable();
			m_hshOverrides.put(key, valueTbl);
		}

		valueTbl.put(etype, value); // save new values by type
	}
	private boolean isValueSetByType(String key, boolean defaultValue) {
		boolean b = defaultValue;

		// check to see if this entitytype had its own value
		if(m_hshOverrides != null && getParent() instanceof EntityGroup){
			Hashtable valueTbl = (Hashtable)m_hshOverrides.get(key);
			if (valueTbl!=null){
				Boolean bInt = (Boolean)valueTbl.get(((EntityGroup)getParent()).getEntityType());
				if (bInt!=null){
					b = bInt.booleanValue();
				}
			}
		}

		return b;
	}
	private int getIntValueSetByType(String key, int defaultValue) {
		int b = defaultValue;

		// check to see if this entitytype had its own value
		if(m_hshOverrides != null && getParent() instanceof EntityGroup){
			Hashtable valueTbl = (Hashtable)m_hshOverrides.get(key);
			if (valueTbl!=null){
				String val = (String)valueTbl.get(((EntityGroup)getParent()).getEntityType());
				if (val!=null){
					b = Integer.parseInt(val);
					if (this instanceof MetaTextAttribute) {
						b= Math.min(b,TEXT_MAX_LEN); // dont exceed db max value
					} else if (this instanceof MetaLongTextAttribute || this instanceof MetaXMLAttribute) {
						b= Math.min(b,LONGTEXT_MAX_LEN);
					}
				}
			}
		}

		return b;
	}
	private String getValueSetByType(String key, String defaultValue) {
		String b = defaultValue;

		// check to see if this entitytype had its own value
		if(m_hshOverrides != null && getParent() instanceof EntityGroup){
			Hashtable valueTbl = (Hashtable)m_hshOverrides.get(key);
			if (valueTbl!=null){
				String val = (String)valueTbl.get(((EntityGroup)getParent()).getEntityType());
				if (val!=null){
					b = val;
				}
			}
		}

		return b;
	}
	/**
	 *  set the Numeric rule for this object
	 *
	 *@param  _b1  The new numeric value
	 * /
    public void setNumeric(boolean _b1) {
        m_bnumeric = _b1;
    }

    /**
	 *  Returns true if the numeric rule is enabled otherwise, false.
	 *
	 *@return    is numeric rule enabled or not
	 * /
    public boolean isNumeric() {
        return m_bnumeric;
    }*/
	/**
	 * SR11, SR15 and SR17 allow NUMERIC by entitytype
	 *  set the Numeric rule for this object
	 *
	 *@param  _b1  The new numeric value
	 */
	public void setNumeric(boolean _b1) {
		setNumeric(_b1,DEFAULT_TYPE);
	}
	public void setNumeric(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bnumeric = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("NUMERIC",etype, new Boolean(_b1)); // save new values by type
		}
	}
	
	/**
	 * SR11, SR15 and SR17 allow NUMERIC by entitytype
	 *  Returns true if the numeric rule is enabled otherwise, false.
	 *
	 *@return    is numeric rule enabled or not
	 */
	public boolean isNumeric() {
		return isValueSetByType("NUMERIC", m_bnumeric);
	}
	/*
	 * LINKTYPE:  Attribute/Test
LINKTYPE1: attributecode    - like MKTGNAME
LINKTYPE2: ExpireOtherNLS
LINKCODE:  Value | entitytype - if controlled by entitytype - MODEL
LINKVALUE: TRANSLATIONWATCH:TWON - attribute to update:attribute value to use for update
RCQ242344 Translation support fallback
	 */
	protected void setExpireOtherNLS(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bexpireOtherNls = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("EXPIREOTHERNLS",etype, new Boolean(_b1)); // save new values by type
		}
	}
	/**
	 * RCQ242344 Translation support fallback
	 *  Returns true if the ExpireOtherNLS rule is enabled otherwise, false.
	 *
	 *@return    is ExpireOtherNLS rule set
	 */
	public boolean isExpireOtherNLS() {
		return isValueSetByType("EXPIREOTHERNLS", m_bexpireOtherNls);
	}
	/**
	 * RCQ242344 Translation support fallback
	 * MUST be a flag attribute and flagcode
	 * 
	 * @return
	 */
	public String getExpireOtherNLSAttr(){
		return m_strExpireOtherNLSAttr;
	}
	/**
	 *  sets the Special characters allowed rule for this object
	 *
	 *@param  _b1  The new special value
	 * /
    public void setSpecial(boolean _b1) {
        m_bspecial = _b1;
    }
    /**
	 *  Returns if the special characters rule is enabled
	 *
	 *@return    is the special character rule enabled
	 * /
    public boolean isSpecial() {
        return m_bspecial;
    }*/
	/**
	 * SR11, SR15 and SR17 allow ALPHAINTEGERSPECIALUPPER,ALPHAINTEGERSPECIAL,ALPHASPECIALUPPER,ALPHASPECIAL by entitytype
	 *  sets the Special characters allowed rule for this object
	 *
	 *@param  _b1  The new special value
	 */
	public void setSpecial(boolean _b1) {
		setSpecial(_b1,DEFAULT_TYPE);
	}
	public void setSpecial(boolean _b1,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bspecial = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("SPECIAL",etype, new Boolean(_b1)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow ALPHAINTEGERSPECIALUPPER,ALPHAINTEGERSPECIAL,ALPHASPECIALUPPER,ALPHASPECIAL by entitytype
	 *  Returns if the special characters rule is enabled
	 *
	 *@return    is the special character rule enabled
	 */
	public boolean isSpecial() {
		return isValueSetByType("SPECIAL", m_bspecial);
	}

	/**
	 *  sets the upper case rule to the passed variable
	 *
	 *@param  _b1  The new upper value
	 * /
    public void setUpper(boolean _b1) {
        m_bupper = _b1;
    }
    /**
	 *  Returns is the uppercase rule enabled
	 *
	 *@return    is the uppercase rule enabled
	 * /
    public boolean isUpper() {
        return m_bupper;
    }*/

	/**
	 * SR11, SR15 and SR17 allow UPPER by entitytype
	 *  sets the upper case rule to the passed variable
	 *
	 *@param  _b1  The new upper value
	 */
	public void setUpper(boolean _b1) {
		setUpper(_b1,DEFAULT_TYPE);
	}
	public void setUpper(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bupper = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("UPPER",etype, new Boolean(_b1)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow UPPER by entitytype
	 *  Returns is the uppercase rule enabled
	 *
	 *@return    is the uppercase rule enabled
	 */
	public boolean isUpper() {
		return isValueSetByType("UPPER", m_bupper);
	}
	/**
	 *  sets the required rule on/off
	 *
	 *@param  _b1  The new required value
	 */
	public void setRequired(boolean _b1) {
		m_breq = _b1;
	}

	/** RQ110806292
	 * original form must be supported
	 * LINKTYPE			LINKTYPE1	LINKTYPE2	LINKCODE	LINKVALUE
	 * Attribute/Test	SPECBID		EXIST		Value		0
	 *
	 *  sets the required rule on/off based on attributecode+entitytype in linkvalue
	 * LINKTYPE			LINKTYPE1	LINKTYPE2	LINKCODE	LINKVALUE
	 * Attribute/Test	SPECBID		EXIST		LEVEL2		WWSEO
	gbl8611:answer:EXIST:LEVEL2:ANNOUNCEMENT
	gbl8611:answer:EXIST:LEVEL2:FCTRANSACTION
	gbl8611:answer:EXIST:LEVEL2:MODELCONVERT
	gbl8611:answer:DATE:Value:0
	gbl8611:answer:SEARCHABLE:Value:0
	gbl8611:answer:USENGLISH:Value:0
	 *@param  linkcode String
	 *@param  linkvalue String
	 */
	private void setRequired(String linkcode, String linkvalue) {
		EANFoundation parent = getParent();
		if (linkcode.equals(DEFAULT_TYPE) || parent==null){
			m_breq = true;
		}else{
			//m_breq = false; dont reset because this.entitygroup.entitytype may have already been read
			if (parent instanceof EntityGroup){
				if (linkcode.equals("LEVEL2") && linkvalue.equals(((EntityGroup)parent).getEntityType())){
					m_breq = true;
				}
			}
		}
	}

	/**
	 *  Returns is the required rule enabled
	 *
	 *@return    is the required rule enabled
	 */
	public boolean isRequired() {
		return m_breq;
	}

	/*
	 *  Returns true if the caller needs to refresh form information based upon
	 *  a change to this attribute
	 */
	/**
	 *  Gets the refreshEnabled attribute of the EANMetaAttribute object
	 *
	 *@return    The refreshEnabled value
	 */
	public boolean isRefreshEnabled() {
		return (getAttributeType().equals("S") || m_bRefresh);
	}

	/**
	 *  sets the Integer rule on/off
	 *
	 *@param  _b1  The new integer value
	 * /
    public void setInteger(boolean _b1) {
        m_binteger = _b1;
    }
    /**
	 *  Returns is the integer rule enabled
	 *
	 *@return    is the integer rule enabled
	 * /
    public boolean isInteger() {
        return m_binteger;
    }*/

	/**
	 * SR11, SR15 and SR17 allow INTEGER by entitytype
	 *  sets the Integer rule on/off
	 *
	 *@param  _b1  The new integer value
	 *@param etype
	 */
	public void setInteger(boolean _b1){
		setInteger(_b1,DEFAULT_TYPE);
	}
	public void setInteger(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_binteger = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("INTEGER",etype, new Boolean(_b1)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow INTEGER by entitytype
	 *  Returns is the integer rule enabled
	 *
	 *@return
	 */
	public boolean isInteger() {
		return isValueSetByType("INTEGER", m_binteger);
	}

	/**
	 *  sets the Decimal rule on/off
	 *
	 *@param  _b1  The new decimal value
	 * /
    public void setDecimal(boolean _b1) {
        m_bdecimal = _b1;
    }
    /**
	 *  Sets the decimalPlaces attribute of the EANMetaAttribute object
	 *
	 *@param  _i  The new decimalPlaces value
	 * /
    public void setDecimalPlaces(int _i) {
        m_iDcPlc = _i;
    }
    /**
	 *  Gets the decimalPlaces attribute of the EANMetaAttribute object
	 *
	 *@return    The decimalPlaces value
	 * /
    public int getDecimalPlaces() {
        return m_iDcPlc;
    }
    /**
	 *  Returns is the Decimal rule enabled
	 *
	 *@return    is the Decimal rule enabled
	 * /
    public boolean isDecimal() {
        return m_bdecimal;
    }*/

	/**
	 * SR11, SR15 and SR17 allow DECIMAL by entitytype
	 *  sets the Decimal rule on/off
	 *
	 *@param  _b1  The new decimal value
	 */
	public void setDecimal(boolean _b1) {
		setDecimal(_b1,DEFAULT_TYPE);
	}
	public void setDecimal(boolean _b1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bdecimal = _b1;
		}else{
			// specific entitytype was specified
			setValueByType("DECIMAL",etype, new Boolean(_b1)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow DECIMAL by entitytype
	 *  Returns is the Decimal rule enabled
	 *
	 *@return    is the Decimal rule enabled
	 */
	public boolean isDecimal() {
		return isValueSetByType("DECIMAL", m_bdecimal);
	}
	/**
	 *  SR11, SR15 and SR17 allow DECIMAL by entitytype
	 *  Sets the decimalPlaces attribute of the EANMetaAttribute object
	 *
	 *@param  _i  The new decimalPlaces value
	 */
	public void setDecimalPlaces(int _i) {
		setDecimalPlaces(_i,DEFAULT_TYPE);
	}
	public void setDecimalPlaces(int _i, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_iDcPlc = _i;
		}else{
			// specific entitytype was specified
			setValueByType("DECIMALPLACE",etype, ""+_i); // save new values by type
		}
	}
	/**
	 *  SR11, SR15 and SR17 allow DECIMAL by entitytype
	 *  Gets the decimalPlaces attribute of the EANMetaAttribute object
	 *
	 *@return    The decimalPlaces value
	 */
	public int getDecimalPlaces() {
		return getIntValueSetByType("DECIMALPLACE", m_iDcPlc);
	}

	/**
	 *  sets the Date rule on/off
	 *
	 *@param  _b1  The new date value
	 */
	public void setDate(boolean _b1) {
		m_bdate = _b1;
	}

	/**
	 *  Sets the time attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new time value
	 */
	public void setTime(boolean _b) {
		m_btime = _b;
	}

	/**
	 *  Returns is the date rule enabled
	 *
	 *@return    is the date rule enabled
	 */
	public boolean isDate() {
		return m_bdate;
	}

	/**
	 *  Gets the time attribute of the EANMetaAttribute object
	 *
	 *@return    The time value
	 */
	public boolean isTime() {
		return m_btime;
	}

	/**
	 *  sets the futute date rule on/off
	 *
	 *@param  _b1  The new futureDate value
	 */
	public void setFutureDate(boolean _b1) {
		m_bfuture = _b1;
	}
	protected void setWarningDate(boolean _b1) { //BH SR 14
		m_bwarningDate = _b1;
	}
	/**
	 *  Returns is the past date or future date warning rule enabled
	 *
	 *@return boolean
	 */
	public boolean isWarningDate() { //BH SR 14
		return m_bwarningDate;
	}
	/**
	 *  Returns is the future date rule enabled
	 *
	 *@return    is the future date rule enabled
	 */
	public boolean isFutureDate() {
		return m_bfuture;
	}

	/**
	 *  sets the Past Date rule on/off
	 *
	 *@param  _b1  The new pastDate value
	 */
	public void setPastDate(boolean _b1) {
		m_bpast = _b1;
	}

	/**
	 *  Returns is the past date rule enabled
	 *
	 *@return    is the past date rule enabled
	 */
	public boolean isPastDate() {
		return m_bpast;
	}

	/**
	 *  Gets the uSEnglishOnly attribute of the EANMetaAttribute object
	 *
	 *@return    The uSEnglishOnly value
	 * /
    public boolean isUSEnglishOnly() {
        return m_bUSEnglishOnly;
    }

    /**
	 *  Sets the uSEnglishOnly attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new uSEnglishOnly value
	 * /
    public void setUSEnglishOnly(boolean _b) {
        m_bUSEnglishOnly = _b;
    }*/

	/**
	 * SR11, SR15 and SR17 allow USENGLISH by entitytype
	 *  Gets the uSEnglishOnly attribute of the EANMetaAttribute object
	 *
	 *@return    The uSEnglishOnly value
	 */
	public boolean isUSEnglishOnly() {
		return isValueSetByType("USENGLISH", m_bUSEnglishOnly);
	}

	/**
	 * SR11, SR15 and SR17 allow USENGLISH by entitytype
	 *  Sets the uSEnglishOnly attribute of the EANMetaAttribute object
	 *  ODS code sets this too!!!
	 *@param  _b  The new uSEnglishOnly value
	 */
	public void setUSEnglishOnly(boolean _b) {
		setUSEnglishOnly(_b,DEFAULT_TYPE);
	}
	//public void setUSEnglishOnly(boolean _b, String etype) { spec chg for now..
	private void setUSEnglishOnly(boolean _b, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bUSEnglishOnly = _b;
		}else{
			// specific entitytype was specified
			setValueByType("USENGLISH",etype, new Boolean(_b)); // save new values by type
		}
	}
	/**
	 *  sets the Default Value for this Attribute for flags.. it is the flag code
	 *  for text.. it is some
	 *
	 *@param  _s1  The new defaultValue value
	 * /
    public void setDefaultValue(String _s1) {
        m_strDV = _s1;
    }

    /**
	 *  Returns the Default Value for this Attribute for flags.. it is the flag
	 *  code for text.. it is some
	 *
	 *@return     The defaultValue value
	 *@returns    a default value
	 * /
    public String getDefaultValue() {
        return m_strDV;
    }*/
	/**
	 * SR11, SR15 and SR17 allow DEFAULT by entitytype
	 *  sets the Default Value for this Attribute for flags.. it is the flag code
	 *  for text.. it is some
	 *
	 *@param  _s1  The new defaultValue value
	 */
	public void setDefaultValue(String _s1) {
		setDefaultValue(_s1,DEFAULT_TYPE);
	}
	public void setDefaultValue(String _s1, String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_strDV = _s1;
		}else{
			// specific entitytype was specified
			setValueByType("DEFAULT",etype, _s1); // save new values by type
		}
	}

	/**
	 * SR11, SR15 and SR17 allow DEFAULT by entitytype
	 *  Returns the Default Value for this Attribute for flags.. it is the flag
	 *  code for text.. it is some
	 *
	 *@return     The defaultValue value
	 *@returns    a default value
	 */
	public String getDefaultValue() {
		return getValueSetByType("DEFAULT", m_strDV);
	}

	/**
	 *  returns true if a default value exists
	 *
	 *@return     Description of the Return Value
	 *@returns    true if a defaul value exists
	 */
	public boolean hasDefaultValue() {
		if (m_strDV == null) {
			return false;
		}
		return true;
	}

	/**
	 *  sets the Default Value for this Attribute for flags.. it is the flag code
	 *  for text.. it is some
	 *
	 *@param  _s1  The new defaultValue value
	 */
	public void setWGDefaultValues(String[] _as1) {
		StringBuffer sb = new StringBuffer();
		if (_as1 !=null){
			for (int i=0; i<_as1.length; i++){
				sb.append(_as1[i]+" ");
			}
		}else {
			sb.append("null");
		}

		D.ebug(D.EBUG_SPEW,"EANMetaAttribute setWGDefaultValues for "+getKey()+" to "+sb);
		m_aWGDefaults = _as1;
	}

	/**
	 *  Returns the Default Value for this Attribute for flags.. it is the flag
	 *  code for text.. it is some
	 *
	 *@return     The defaultValue value
	 *@returns    a default value
	 */
	public String[] getWGDefaultValues() {
		D.ebug(D.EBUG_SPEW,"EANMetaAttribute getWGDefaultValues for "+getKey());
		return m_aWGDefaults;
	}

	/**
	 *  returns true if a default value exists
	 *
	 *@return     Description of the Return Value
	 *@returns    true if a defaul value exists
	 */
	public boolean isWGDEFAULT() {
		return m_bWGDEFAULT;
	}

	public void setWGDEFAULT(boolean _b) {
		m_bWGDEFAULT = _b;
	}

	/**
	 *  Returns the Maximum length of the attribute
	 *
	 *@return    is the past date rule enabled
	 * /
    public int getMaxLen() {
        if (m_imax == 0) {
            if (this instanceof MetaTextAttribute) {
                return 254;
            } else if (this instanceof MetaLongTextAttribute || this instanceof MetaXMLAttribute) {
                return 32000;
            }
        }

        return m_imax;
    }*/
	/**
	 *  sets the Maximum Len of this attribute For MetaText <= 254 and >= 1 For
	 *  MetaLongText <= 30000 >= 254
	 *
	 *@param  _i1  The new maxLen value
	 * /
     public void setMaxLen(int _i1) {
         m_imax = _i1;
     }*/

	/**
	 * SR11, SR15 and SR17 allow MAX by entitytype
	 * @param _i1
	 * @param etype
	 */
	public void setMaxLen(int _i1) {
		setMaxLen(_i1,DEFAULT_TYPE);
	}
	public void setMaxLen(int _i1,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_imax = _i1;
		}else{
			// specific entitytype was specified
			setValueByType("MAX",etype, ""+_i1); // save new values by type
		}
	}

	/**
	 * SR11, SR15 and SR17 allow MAX by entitytype
	 * @return
	 */
	public int getMaxLen() {
		// check to see if this entitytype had its own value
		int max = getIntValueSetByType("MAX", m_imax);

		if (max == 0) {
			if (this instanceof MetaTextAttribute) {
				max= TEXT_MAX_LEN;
			} else if (this instanceof MetaLongTextAttribute || this instanceof MetaXMLAttribute) {
				max= LONGTEXT_MAX_LEN;
			}
		}

		return max;
	}

	/**
	 *  sets the Minimum Len of this attribute.
	 *
	 *@param  _i1  The new minLen value
	 * /
    public void setMinLen(int _i1) {
        m_imin = _i1;
    }*/
	/**
	 *  Returns the Minimum length of the attribute
	 *
	 *@return
	 * /
     public int getMinLen() {
         return m_imin;
     }*/
	/**
	 * SR11, SR15 and SR17 allow MIN by entitytype
	 *  sets the Minimum Len of this attribute.
	 *
	 *@param  _i1  The new minLen value
	 */
	public void setMinLen(int _i1){
		setMinLen(_i1,DEFAULT_TYPE);
	}
	public void setMinLen(int _i1,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_imin = _i1;
		}else{
			// specific entitytype was specified
			setValueByType("MIN",etype, ""+_i1); // save new values by type
		}
	}
	/**
	 *  SR11, SR15 and SR17 allow MIN by entitytype
	 *  Returns the Minimum length of the attribute
	 *
	 *@return
	 */
	public int getMinLen() {
		// check to see if this entitytype had its own value
		return getIntValueSetByType("MIN", m_imin);
	}

	/**
	 *  Sets the greater attribute of the EANMetaAttribute object
	 *
	 *@param  _i  The new greater value
	 */
	public void setGreater(int _i) {
		m_iGreater = _i;
		m_bGreater = true;
	}

	/**
	 *  Gets the greater attribute of the EANMetaAttribute object
	 *
	 *@return    The greater value
	 */
	public boolean isGreater() {
		return m_bGreater;
	}

	/**
	 *  Gets the greater attribute of the EANMetaAttribute object
	 *
	 *@return    The greater value
	 */
	public int getGreater() {
		return m_iGreater;
	}

	/**
	 *  sets the EqualsLen of this attribute.
	 *
	 *@param  _i1  The new equalsLen value
	 * /
    public void setEqualsLen(int _i1) {
        m_iequals = _i1;
    }

    /**
	 *  Returns the Equals length of the attribute
	 *
	 *@return    is the past date rule enabled
	 * /
    public int getEqualsLen() {
        return m_iequals;
    }*/
	/**
	 *  sets the EqualsLen of this attribute.
	 * SR11, SR15 and SR17 allow EQU by entitytype
	 *@param  _i1  The new equalsLen value
	 */
	public void setEqualsLen(int _i1) {
		setEqualsLen(_i1,DEFAULT_TYPE);
	}
	public void setEqualsLen(int _i1,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_iequals = _i1;
		}else{
			// specific entitytype was specified
			setValueByType("EQU",etype, ""+_i1); // save new values by type
		}
	}

	/**
	 * SR11, SR15 and SR17 allow EQU by entitytype
	 *  Returns the Equals length of the attribute
	 *
	 *@return
	 */
	public int getEqualsLen() {
		return getIntValueSetByType("EQU", m_iequals);
	}
	/**
	 * isUnique
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isUnique() {
		return m_bUnique;
	}

	/**
	 * isPDGINFO
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isPDGINFO() {
		return m_bPDGINFO;
	}

	/**
	 * isComboUnique
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isComboUnique() {
		return m_bCmbU;
	}

	/**
	 * setUnique
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setUnique(boolean _b) {
		m_bUnique = _b;
	}

	/**
	 * setPDGINFO
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setPDGINFO(boolean _b) {
		m_bPDGINFO = _b;
	}

	/**
	 * setComboUnique
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setComboUnique(boolean _b) {
		m_bCmbU = _b;
	}

	/**
	 * setComboUniqueAttributeCode
	 * this is used for optional and required pairs, they are not based on entitytype
	 *
	 * SG  Attribute/Test  CCINMOD COMBOUNIQUEOPTIONAL CCINATR Optional
	 * SG  Attribute/Test  CCINATR COMBOUNIQUEOPTIONAL CCINMOD Required
	 *
	 * this is used for unique combinations ignoring the entitytype
	 * Attribute/Test	MODELATR	COMBOUNIQUE	MACHTYPEATR	L
	 * @param _str
	 *  @author David Bigelow
	 */
	public void setComboUniqueAttributeCode(String _str) {
		if (m_vctCmbUnAC == null) {
			m_vctCmbUnAC = new Vector();
		}
		m_vctCmbUnAC.addElement(_str);
	}
	/**
	 * setComboUniqueAttributeCode
	 * MN37420204 - must allow for attribute on different entity types
	 * this is used for unique combinations based on entitytype between flag and text
	 * SG  Attribute/Test  INVENTORYGROUP  COMBOUNIQUE FEATURECODE     FEATURE
	 * SG  Attribute/Test  INVENTORYGROUP  COMBOUNIQUE FEATURECODE     SVCFEATURE
	 * SG  Attribute/Test  FEATURECODE 	   COMBOUNIQUE INVENTORYGROUP  FEATURE
	 * SG  Attribute/Test  FEATURECODE     COMBOUNIQUE INVENTORYGROUP  SVCFEATURE
	 *
	 * extended support for combounique BH SR87, SR655
	 * this is used for unique combinations based on entitytype between text and text
	 * SG  Attribute/Test  SMACHTYPEATR    COMBOUNIQUE MODELTYPEATR  SVCMOD
	 * SG  Attribute/Test  MODELTYPEATR    COMBOUNIQUE SMACHTYPEATR  SVCMOD
	 *
	 * this is used for unique combinations based on entitytype across multiple attr
	 * SG  Attribute/Test  SMACHTYPEATR    COMBOUNIQUE MODELTYPEATR:INVENTORYGROUP  SVCMOD
	 *
	 * this is used for unique combinations ignoring the entitytype
	 * Attribute/Test	MODELATR	COMBOUNIQUE	MACHTYPEATR	L
	 * Attribute/Test	MACHTYPEATR	COMBOUNIQUE	MODELATR	L
	 *
	 * @param _attr
	 * @param _type
	 */
	private void setComboUniqueAttributeCode(String _attr, String _type) {
		if (_type.equalsIgnoreCase("L")){ // dont use entitytype here
			m_strCmbUnGr = _type;
			setComboUniqueAttributeCode(_attr);
		}else{
			if (m_tblCmboUn == null) {
				m_tblCmboUn = new Hashtable();
			}
			Vector tmp = (Vector)m_tblCmboUn.get(_type);
			if (tmp==null){
				tmp = new Vector(1);
				m_tblCmboUn.put(_type, tmp);
			}

			// allow for a list of attr
			if (_attr.indexOf(':')!=-1){
				// extended support for combounique BH SR87, SR655
				StringTokenizer stok = new StringTokenizer(_attr, ":");
				while (stok.hasMoreTokens()) {
					String attrcode = stok.nextToken().trim();
					tmp.addElement(attrcode);
				}
			}else{
				tmp.addElement(_attr);
			}
		}
	}
	/**
	 * setComboUniqueGrouping
	 *
	 * @param _str
	 *  @author David Bigelow
	 *
    public void setComboUniqueGrouping(String _str) {
        m_strCmbUnGr = _str;
    }
    /**
	 * getComboUniqueAttributeCode
	 *
	 * @param _i
	 * @return
	 *  @author David Bigelow
	 *
    public String getComboUniqueAttributeCode(int _i) {
        if (m_vctCmbUnAC == null) {
            return null;
        }
        return (String) m_vctCmbUnAC.elementAt(_i);
    }
	 */

	/**
	 * getComboUniqueAttributeCode
	 *
	 * @param _i
	 * @return String
	 */
	public String getComboUniqueAttributeCode(int _i) {
		Vector tmp = getComboUniqueAttributeCode();
		if (tmp.size()>_i){
			return (String) tmp.elementAt(_i);
		}
		return null;
	}

	/**
	 * getComboUniqueAttributeCode
	 *
	 * @return Vector
	 */
	public Vector getComboUniqueAttributeCode() {
		if(isComboUnique()){
			if(m_strCmbUnGr !=null){ // 'L' was specified use its attribute list
				return m_vctCmbUnAC;
			}
			if (m_tblCmboUn != null) {	//MN37420204 - must allow for attribute on different entity types
				// if parent entitytype is in the table, the rule was set
				if (getParent() instanceof EntityGroup) {
					EntityGroup eg = (EntityGroup) getParent();
					if (m_tblCmboUn.containsKey(eg.getEntityType())){
						Vector tmp = (Vector)m_tblCmboUn.get(eg.getEntityType());
						return tmp;
					}
				}else{
					System.err.println("EANMetaAttr.getComboUniqueAttributeCode "+getAttributeCode()+" returning first combo value for parent "+getParent());
					Thread.dumpStack();
					// not sure how this can happen, but return first value
					for (java.util.Enumeration e = m_tblCmboUn.elements(); e.hasMoreElements();)
					{
						Vector tmp =  (Vector)e.nextElement();
						return tmp;
					}
				}

				return new Vector();
			}else{
				return new Vector();
			}
		}else{
			if (m_vctCmbUnAC == null) {
				m_vctCmbUnAC = new Vector();
			}
			return m_vctCmbUnAC;
		}
	}

	/**
	 * getComboUniqueGrouping
	 *
	 * @return
	 *  @author David Bigelow
	 *
    public String getComboUniqueGrouping() {
        if (m_strCmbUnGr == null) {
            return "";
        }
        return m_strCmbUnGr;
    }*/
	/**
	 * MN37420204 - must allow for attribute on different entity types
	 * getComboUniqueGrouping
	 *
	 */
	public String getComboUniqueGrouping() {
		String grping = m_strCmbUnGr;
		if (m_tblCmboUn != null) {
			// if parent entitytype is in the table, the rule was set
			if (getParent() instanceof EntityGroup) {
				EntityGroup eg = (EntityGroup) getParent();
				if (m_tblCmboUn.containsKey(eg.getEntityType())){
					grping = eg.getEntityType();
				}
			}else{
				System.err.println("EANMetaAttr.getComboUniqueGrouping "+getAttributeCode()+" returning first combo key for parent "+getParent());
				Thread.dumpStack();
				// not sure how this can happen, but return first key
				for (java.util.Enumeration e = m_tblCmboUn.keys(); e.hasMoreElements();)
				{
					grping = (String)e.nextElement();
					break;
				}
			}
		}

		if (grping==null){
			grping = "";
		}
		return grping;
	}
	/**
	 * setComboUniqueOptional
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setComboUniqueOptional(boolean _b) {
		m_bCmbUOpt = _b;
	}

	/**
	 * Flag whether or not this is the required half of the COMBOUNIQUEOPTIONAL rule.
	 *
	 * @param _b
	 */
	public void setComboUniqueOptionalRequiredAttribute(boolean _b) {
		m_bCmbUOptReqAtt = _b;
	}

	/**
	 * isComboUniqueOptionalRequiredAttribute
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isComboUniqueOptionalRequiredAttribute() {
		return m_bCmbUOptReqAtt;
	}

	/**
	 * isComboUniqueOptional
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isComboUniqueOptional() {
		return m_bCmbUOpt;
	}

	/**
	 * setNoBlanks
	 *
	 * @param _b
	 *  @author David Bigelow
	 * /
    public void setNoBlanks(boolean _b) {
        m_bNoBlanks = _b;
    }
    /**
	 * isNoBlanks
	 *
	 * @return
	 *  @author David Bigelow
	 * /
    public boolean isNoBlanks() {
        return m_bNoBlanks;
    }*/
	/**
	 * SR11, SR15 and SR17 allow NOBLANKS by entitytype
	 * setNoBlanks
	 *
	 * @param _b
	 */
	public void setNoBlanks(boolean _b) {
		setNoBlanks(_b,DEFAULT_TYPE);
	}
	public void setNoBlanks(boolean _b,String etype) {
		if (etype.equals(DEFAULT_TYPE)){
			m_bNoBlanks = _b;
		}else{
			// specific entitytype was specified
			setValueByType("NOBLANKS",etype, new Boolean(_b)); // save new values by type
		}
	}
	/**
	 * SR11, SR15 and SR17 allow NOBLANKS by entitytype
	 * isNoBlanks
	 *
	 * @return
	 */
	public boolean isNoBlanks() {
		return isValueSetByType("NOBLANKS", m_bNoBlanks);
	}
	/**
	 * setRefresh
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setRefresh(boolean _b) {
		m_bRefresh = _b;
	}

	/**
	 * setUniqueClassType
     level1 - across pdh
     level2 - within entitytype
     level3 - within list of entitytypes and within entitytype
     need to support the following:
	SG	Attribute/Test	SEOID	UNIQUE	LEVEL2	LSEO
	SG	Attribute/Test	SEOID	UNIQUE	LEVEL2	LSEOBUNDLE
	SG	Attribute/Test	SEOID	UNIQUE	LEVEL2	WWSEO
	or
	SG	Attribute/Test	SEOID	UNIQUE	LEVEL3	LSEO:LSEOBUNDLE
	SG	Attribute/Test	SEOID	UNIQUE	LEVEL2	WWSEO
	can only support one kind of class per entitytype
	 *
	 * @param _s
	 */
	public void setUniqueClassType(String _s, String _t) throws MiddlewareException
	{
		if (m_strUnCl == null) { // first UNIQUE value for this Attribute
			m_strUnCl = _s;
			m_strUnTp = _t;
		}else{ // must account for other levels or types MN33363011
			// multiple types may have this attribute and different level checks could apply
			if (m_strUnCl.equals("LEVEL1")){ // across PDH, no other levels are allowed by entitytype
				throw new MiddlewareException(getAttributeCode()+
						" already has LEVEL1 specified, nothing else can be supported "+_s);
			}
			if (m_hshUnique==null){
				m_hshUnique = new Hashtable();
				m_hshUnique.put(m_strUnTp, m_strUnCl); // save original values level2, level3 saved by type
			}

			StringTokenizer st = new StringTokenizer(_t,":");
			while(st.hasMoreTokens()) {
				String level =(String)m_hshUnique.get(st.nextToken());
				if (level!=null){ // only support one level per entity type
					throw new MiddlewareException(getAttributeCode()+" "+_t+
							" already has "+level+" specified, nothing else can be supported "+_s);
				}
			}
			m_hshUnique.put(_t, _s); // save new values by type
		}
	}

	/**
	 * getUniqueClass based on parent entitytype
	 *
	 * @return
	 */
	public String getUniqueClass() {
		if (m_strUnCl == null) {
			return "";
		}
		if (isUnique()) {
			if (m_hshUnique==null){ // LEVEL1 or other single class values
				return m_strUnCl;
			}else{
				// get level class by entity type MN33363011
				if (getParent() instanceof EntityGroup) {
					EntityGroup eg = (EntityGroup) getParent();
					String ptype = eg.getEntityType();
					for (java.util.Enumeration e = m_hshUnique.keys(); e.hasMoreElements();)
					{
						String etypes = (String)e.nextElement();
						String lvl = (String)m_hshUnique.get(etypes);
						if (etypes.equals(ptype)){ //LEVEL2
							return lvl;
						}
						// check to see if the key is a combination of types like LSEO:LSEOBUNDLE
						if (etypes.indexOf(":")!=-1){
							StringTokenizer st = new StringTokenizer(etypes,":");
							while(st.hasMoreTokens()) {
								String etype =st.nextToken();
								if (etype.equals(ptype)){ //LEVEL3
									return lvl;
								}
							}
						}
					}
				}
			}
			return m_strUnCl; // should never get here, be leave original
		}
		return "";
	}

	/** MN33363011
	 * setUniqueClassType
	 * @param _s
	 * /
    public void setUniqueClass(String _s) {
		// this just overwrites, last one in wins
        m_strUnCl = _s;
    }

    /**
	 * setUniqueType
	 *
	 * @param _s
	 *  @author David Bigelow
	 * /
    public void setUniqueType(String _s) {
		// this just overwrites, last one in wins
        m_strUnTp = _s;
    }*/

	/**
	 * getUniqueType based on parent entitytype
	 *
	 * @return
	 */
	public String getUniqueType() {
		if (m_strUnTp == null) {
			return "";
		}

		if (isUnique()) {
			if (m_hshUnique==null){ // LEVEL1 or other single class values
				return m_strUnTp;
			}else{
				// if parent entitytype is in the table, the level was set MN33363011
				if (getParent() instanceof EntityGroup) {
					EntityGroup eg = (EntityGroup) getParent();
					String ptype = eg.getEntityType();
					for (java.util.Enumeration e = m_hshUnique.keys(); e.hasMoreElements();)
					{
						String etypes = (String)e.nextElement();
						if (etypes.equals(ptype)){ //LEVEL2
							return etypes;
						}
						// check to see if the key is a combination of types like LSEO:LSEOBUNDLE
						if (etypes.indexOf(":")!=-1){
							StringTokenizer st = new StringTokenizer(etypes,":");
							while(st.hasMoreTokens()) {
								String etype =st.nextToken();
								if (etype.equals(ptype)){ //LEVEL3
									return etypes; // return entire string
								}
							}
						}
					}
				}
			}

			return m_strUnTp;  // should never get here, be leave original
		}
		return "";
	}

	/**
	 * setOrder
	 *
	 * @param _i
	 *  @author David Bigelow
	 */
	public void setOrder(int _i) {
		m_iOrder = _i;
	}

	/**
	 * getOrder
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public int getOrder() {
		return m_iOrder;
	}

	/**
	 * setNavigate
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	public void setNavigate(boolean _b) {
		m_bNavigate = _b;
	}

	/**
	 * isNavigate
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isNavigate() {
		return m_bNavigate;
	}

	/**
	 * isDomainControlled
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public boolean isDomainControlled() {
		return m_bDmnCtr;
	}

	/**
	 *  Sets the domainControlled attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new domainControlled value
	 */
	protected void setDomainControlled(boolean _b) {
		m_bDmnCtr = _b;
	}

	/**
	 *  Sets the domainControlled attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new domainControlled value
	 */
	protected void setSelectHelper(boolean _b) {
		m_bSelectHelper = _b;
	}

	/**
	 *  Gets the selectHelper attribute of the EANMetaAttribute object
	 *
	 *@return    The selectHelper value
	 */
	public boolean isSelectHelper() {
		return m_bSelectHelper;
	}

	/**
	 *  Description of the Method
	 *
	 *@param  _bBrief  Description of the Parameter
	 *@return          Description of the Return Value
	 */
	public String dump(boolean _bBrief) {

		StringBuffer strbResult = new StringBuffer();

		if (_bBrief) {
			strbResult.append(getKey());
		} else {
			strbResult.append(":MetaAttribute: " + getKey());
			strbResult.append(":ShortDesc:" + getShortDescription());
			strbResult.append(":LongDesc:" + getLongDescription());
			strbResult.append(":AttributeCode:" + getAttributeCode());
			strbResult.append(":AttributeType:" + getAttributeType());
			strbResult.append(":Classification:" + isClassified());
			strbResult.append(":DomainControlled:" + isDomainControlled());
			if (getMetaHelpValue() != null) {
				strbResult.append(":MetaHelpText:" + getMetaHelpValue().dump(_bBrief));
			}
		}

		return strbResult.toString();
	}

	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Return Value
	 */
	public String toString() {
		return getLongDescription();
	}

	/*
	 *  override getLongDescription to include * for reference
	 */
	/**
	 *  Gets the longDescription attribute of the EANMetaAttribute object
	 *
	 *@return    The longDescription value
	 */
	public String getLongDescription() {
		if (isRequired()) {
			return "*" + super.getLongDescription();
		}
		return super.getLongDescription();
	}

	/*
	 *  override getShortDescription to include * for reference
	 */
	/**
	 *  Gets the shortDescription attribute of the EANMetaAttribute object
	 *
	 *@return    The shortDescription value
	 */
	public String getShortDescription() {
		if (isRequired()) {
			return "*" + super.getShortDescription();
		}
		return super.getShortDescription();
	}

	/**
	 *  Description of the Method
	 */
	protected void dereference() {
		m_strAttributeType = null;
		m_strCapability = null;
		m_strDV = null;
		m_strUnTp = null;
		m_strExpireOtherNLSAttr = null;
		if(m_hshOverrides != null){
			for (Enumeration e = m_hshOverrides.elements(); e.hasMoreElements();) {
				Hashtable tbl = (Hashtable)e.nextElement();
				tbl.clear();
			}
			m_hshOverrides.clear();
			m_hshOverrides = null;
		}

		m_strUnCl = null;
		if (m_vctCmbUnAC!=null){
			m_vctCmbUnAC.clear();
			m_vctCmbUnAC = null;
		}

		if (m_tblCmboUn!= null){
			Enumeration e = m_tblCmboUn.elements();
			while (e.hasMoreElements()) {
				Vector uag = (Vector) e.nextElement();
				uag.clear();
			}
			m_tblCmboUn.clear();
			m_tblCmboUn = null;
		}
		m_strCmbUnGr = null;

		if (m_vctCmbUnOptAC!=null){
			m_vctCmbUnOptAC.clear();
			m_vctCmbUnOptAC = null;
		}
		m_strCmbUnOptGr = null;
		if (m_oMhv!=null){
			m_oMhv.dereference();
			m_oMhv = null;
		}
		m_strXMLDTD = null;
		m_strSortType = null;
		m_aWGDefaults = null;
		if (m_hshValidRule!=null){
			m_hshValidRule.clear();
			m_hshValidRule = null;
		}
		if (m_hshSearchHidden!=null){
			m_hshSearchHidden.clear();
			m_hshSearchHidden = null;
		}
		if (m_hshUnique!=null){
			m_hshUnique.clear();
			m_hshUnique = null;
		}

		super.dereference();
	}

	/**
	 *  Get Navigate from database
	 *
	 *@param  _db                      Description of the Parameter
	 *@return                          The navigate value
	 *@exception  MiddlewareException  Description of the Exception
	 *@exception  SQLException         Description of the Exception
	 */
	private boolean getNavigate(Database _db) throws MiddlewareException, SQLException {

		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		String strLinkType = "Entity/Attribute";
		String strAttCode = getAttributeCode();
		String strLinkCode = "Navigate";
		String strValOn = _db.getDates().getNow();
		String strEffOn = strValOn;
		String strParentEntType = null;
		boolean bNavigate = false;
		EntityGroup oEgParent = null;

		String strEnterprise = getProfile().getEnterprise();

		if (!(getParent() instanceof EntityGroup)) {
			if (getParent() != null) {
				_db.debug(D.EBUG_DETAIL, "EANMetaAttribute.getNavigate()->parent is not an EntityGroup! [" + getParent().getClass().getName() + "]");
			} else {
				_db.debug(D.EBUG_DETAIL, "EANMetaAttribute.getNavigate()->parent is null");
			}
			return false;
		}

		oEgParent = (EntityGroup) getParent();
		if (oEgParent != null) {
			strParentEntType = oEgParent.getEntityType();
			try {
				rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				if (rs !=null){
					rs.close();
					rs = null;
				}
				_db.freeStatement();
				_db.isPending();
			}
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				String strLinkType1 = rdrs.getColumn(row, 0);
				String s2 = rdrs.getColumn(row, 1);
				_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + s2);
				//if we find the parent, set bNewParentEntity = false
				if (strLinkType1.equals(strParentEntType)) {
					bNavigate = true;
					break;
				}
			}
		}
		_db.debug(D.EBUG_SPEW, "EANMetaAttribute.getNavigate(): for " + strAttCode + "->" + bNavigate);
		return bNavigate;
	}

	///////////
	// SORT
	///////////

	/**
	 *  get the sort key
	 *
	 *@return    Description of the Return Value
	 */
	public String toCompareString() {
		if (getSortType().equals(SORT_BY_LONGDESC)) {
			return getActualLongDescription();
		}
		if (getSortType().equals(SORT_BY_SHORTDESC)) {
			return getActualShortDescription();
		}
		if (getSortType().equals(SORT_BY_ATTCODE)) {
			return getAttributeCode();
		}
		if (getSortType().equals(SORT_BY_ATTTYPE)) {
			return getAttributeType();
		}
		if (getSortType().equals(SORT_BY_ATTTYPEMAPPING)) {
			return getAttributeTypeMapping();
		}
		if (getSortType().equals(SORT_BY_IS_ORPHAN)) {
			return (isOrphan() ? "Yes" : "No");
		}

		//this shouldn't occur
		return toString();
	}

	/**
	 * setSortType
	 *
	 * @param _s
	 *  @author David Bigelow
	 */
	public void setSortType(String _s) {
		m_strSortType = _s;
		return;
	}

	/**
	 * getSortType
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public String getSortType() {
		return m_strSortType;
	}

	/**
	 *  Sets the compareField attribute of the EANMetaAttribute object
	 *
	 *@param  _s  The new compareField value
	 */
	public void setCompareField(String _s) {
		m_strSortType = _s;
	}

	//}}}

	//{{{ getCompareFiled() method
	//get the String representing the field to compare by
	/**
	 *  Gets the compareField attribute of the EANMetaAttribute object
	 *
	 *@return    The compareField value
	 */
	public String getCompareField() {
		return m_strSortType;
	}

	//}}}

	/**
	 *  Sets the displayableForFilter attribute of the EANMetaAttribute object
	 *
	 *@param  _b  The new displayableForFilter value
	 */
	public void setDisplayableForFilter(boolean _b) {
		m_bDisplayableForFilter = _b;
	}

	/**
	 *  Gets the displayableForFilter attribute of the EANMetaAttribute object
	 *
	 *@return    The displayableForFilter value
	 */
	public boolean isDisplayableForFilter() {
		return m_bDisplayableForFilter;
	}

	/**
	 *  Exclude this attribute from copy at the Entity level. This means nothing if
	 *  not attached to an EntityGroup.
	 *
	 *@param  _b  The new excludeFromCopy value
	 */
	public void setExcludeFromCopy(boolean _b) {
		m_bExCpy = _b;
	}

	/**
	 *  Exclude from copy at the Entity level.
	 *
	 *@return    The excludeFromCopy value
	 */
	public boolean isExcludeFromCopy() {
		return m_bExCpy;
	}

	/**
	 *  Get the ValFrom, ValTo, EffFrom, EffTo, openID, tranID for the row of the
	 *  MetaDescriptionTable that defines this EANMetaAttribute
	 *
	 *@param  _db                      Description of the Parameter
	 *@return                          The metaDescriptionControlBlock value
	 *@exception  MiddlewareException  Description of the Exception
	 *@exception  SQLException         Description of the Exception
	 */
	public ControlBlock getMetaDescriptionControlBlock(Database _db) throws MiddlewareException, SQLException {
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		Profile prof = getProfile();
		int iNLSID = prof.getReadLanguage().getNLSID();
		String strEnterprise = prof.getEnterprise();
		String strAttCode = getAttributeCode();
		String strAttType = getAttributeType();

		ControlBlock controlBlock = new ControlBlock();

		try {
			rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, strAttCode, strAttType);
			rdrs = new ReturnDataResultSet(rs);
		} finally {
			if (rs !=null){
				rs.close();
				rs = null;
			}
			_db.commit();
			_db.freeStatement();
			_db.isPending();
		}
		for (int row = 0; row < rdrs.getRowCount(); row++) {
			int iNLSID_rdrs = rdrs.getColumnInt(row, 0);
			if (iNLSID_rdrs == iNLSID) {
				String strValFrom = rdrs.getColumn(row, 3);
				String strValTo = rdrs.getColumn(row, 4);
				String strEffFrom = rdrs.getColumn(row, 5);
				String strEffTo = rdrs.getColumn(row, 6);
				int iOpenID = rdrs.getColumnInt(row, 7);
				int iTranID = rdrs.getColumnInt(row, 8);
				controlBlock.setValFrom(strValFrom);
				controlBlock.setValTo(strValTo);
				controlBlock.setEffFrom(strEffFrom);
				controlBlock.setEffTo(strEffTo);
				controlBlock.setOPENID(iOpenID);
				controlBlock.setTranID(iTranID);
				//break out
				break;
			}
		}
		return controlBlock;
	}

	/**
	 *  does this attribute have a parent?
	 *
	 *@param  _b  The new orphan value
	 */
	protected void setOrphan(boolean _b) {
		m_bOrphan = _b;
	}

	/**
	 *  does this attribute have a parent?
	 *
	 *@return    The orphan value
	 */
	protected boolean isOrphan() {
		return m_bOrphan;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////// META UPDATE ROUTINES //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 *  Detach this attribute from its parent EntityGroup and update the PDH
	 *  accordingly.
	 *
	 *@param  _db                             Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public boolean detachPdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
		boolean bChanged = false;
		String strAttCode = this.getAttributeCode();
		String strLinkType = "Entity/Attribute";
		String strLinkCode = null;
		String strLinkType1 = null;
		String strLinkValue = null;
		String strNow = _db.getDates().getNow();
		String strEnterprise = getProfile().getEnterprise();
		String strValOn = strNow;
		String strEffOn = strNow;
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		EntityGroup oEg = null;
		String strParentEntType = null;
		String[] sa = new String[3];

		oEg = (EntityGroup) this.getParent();
		if (oEg != null) {
			oEg.removeMetaAttribute(this);
		} else {
			throw new MiddlewareException("EANMetaAttribute.detachPdhMeta(): You cannot detach an attribute from a null parent!.");
		}
		strParentEntType = oEg.getEntityType();

		sa[0] = "EntityAttribute";
		sa[1] = "Translate";
		sa[2] = "Copy";

		for (int i = 0; i < sa.length; i++) {
			strLinkCode = sa[i];
			try {
				rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				if (rs !=null){
					rs.close();
					rs = null;
				}
				_db.commit();
				_db.freeStatement();
				_db.isPending();
			}
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				strLinkType1 = rdrs.getColumn(row, 0);
				strLinkValue = rdrs.getColumn(row, 1);
				_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
				//if we find the parent, expire this record...
				if (strLinkType1.equals(strParentEntType)) {
					MetaLinkAttrRow oMlaRow = new MetaLinkAttrRow(getProfile(), strLinkType, strParentEntType, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2);
					oMlaRow.updatePdh(_db);
					bChanged = true;
				}
			}
		}
		return bChanged;
	}

	/**
	 *  Update the changes to EANMetaAttribute to the PDH
	 *
	 *@param  _db                             Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public boolean updatePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
		return updatePdhMeta(_db, true);
	}

	/**
	 *  Update the changes to EANMetaAttribute to the PDH
	 *
	 *@param  _bDeepUpdate                    if true go all the way down to
	 *      MetaFlags (same as updatEPdhMeta(db))-- else stop at MetaAttribute
	 *      level
	 *@param  _db                             Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public boolean updatePdhMeta(Database _db, boolean _bDeepUpdate) throws SQLException, MiddlewareException, MiddlewareRequestException {
		boolean bChanged = updatePdhMeta(_db, false, _bDeepUpdate);
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;

		if (getMetaHelpValue() != null) {
			bChanged = (getMetaHelpValue().updatePdh(_db) ? true : bChanged);
		} else if (getMetaHelpValue() == null || getMetaHelpValue().getHelpValueText().equals("")) {
			//if this used to exist -> expire it!
			try {
				try {
					rs = _db.callGBL7513(new ReturnStatus(-1), getProfile().getEnterprise(), getAttributeCode());
					rdrs = new ReturnDataResultSet(rs);
				} finally {
					if (rs !=null){
						rs.close();
						rs = null;
					}
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
				for (int row = 0; row < rdrs.getRowCount(); row++) {
					int iNLSID = rdrs.getColumnInt(row, 0);
					String strHelpVal = rdrs.getColumn(row, 1);
					_db.debug(D.EBUG_SPEW, "gbl7513 answers:" + iNLSID + ":" + strHelpVal);
					if (iNLSID == getProfile().getReadLanguage().getNLSID()) {
						if (!strHelpVal.equals("")) {
							//then an old value existed --> expire!!
							MetaHelpValue mhv_old = new MetaHelpValue(this, getProfile());
							mhv_old.setHelpValueText(strHelpVal);
							mhv_old.expirePdh(_db);
							this.setMetaHelpValue(null);
							bChanged = true;
							break;
						}
					}
				}
			} catch (Exception exc) {
				_db.debug(D.EBUG_ERR, "EANMetaAttribute.updatePdhMeta:error obtaining/expiring old help values:" + exc.toString());
			} finally {
				_db.freeStatement();
				_db.isPending();
			}
		}
		return bChanged;
	}

	/**
	 *  Expire the EANMetaAttribute in the PDH
	 *
	 *@param  _db                             Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	public boolean expirePdhMeta(Database _db) throws SQLException, MiddlewareException, MiddlewareRequestException {
		boolean bChanged = updatePdhMeta(_db, true, true);
		//now make sure this attribute is removed from its parent
		EntityGroup oEg = (EntityGroup) this.getParent();
		if (oEg != null) {
			oEg.removeMetaAttribute(this);
		}
		//now help value
		if (getMetaHelpValue() != null) {
			bChanged = getMetaHelpValue().expirePdh(_db);
		}
		return bChanged;
	}

	/**
	 *  toggle expire
	 *
	 *@param  _db                             Description of the Parameter
	 *@param  _bIsExpire                      Description of the Parameter
	 *@param  _bDeepUpdate                    Description of the Parameter
	 *@return                                 Description of the Return Value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	private boolean updatePdhMeta(Database _db, boolean _bIsExpire, boolean _bDeepUpdate) throws SQLException, MiddlewareException, MiddlewareRequestException {

		boolean bChanged = false;
		EANMetaAttribute oEma_db = null;
		EANList oEl = null;
		String strAttType = getAttributeType();
		String strAttCode = getAttributeCode();
		String strNow = _db.getDates().getNow();
		String strEntTypeParent = "";
		String strValOn = strNow;
		String strEffOn = strNow;

		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;

		boolean bIsNewAttCode = MetaEntityList.isNewAttributeCode(_db, getProfile(), strAttCode);


		//
		// Lets pretend to use this variable
		//
		//if (_bDeepUpdate) {
		//    _bDeepUpdate = _bDeepUpdate;
		// }

		getProfile().setValOn(strNow);
		getProfile().setEffOn(strNow);

		if (getParent() != null) {
			EntityGroup eg = (EntityGroup) getParent();
			strEntTypeParent = eg.getEntityType();
		}

		//we must check for this, because null ptr. gets thrown if we try to create a new MetaAttribute below for an
		//attributeCode that does not exist!
		if (!bIsNewAttCode) {
			switch (strAttType.charAt(0)) {

			case 'T' :
			case 'I' :

				oEma_db = new MetaTextAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'L' :

				oEma_db = new MetaLongTextAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'X' :

				oEma_db = new MetaXMLAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'F' :

				oEma_db = new MetaMultiFlagAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'U' :

				oEma_db = new MetaSingleFlagAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'S' :

				oEma_db = new MetaStatusAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'A' :

				oEma_db = new MetaTaskAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			case 'B' :

				oEma_db = new MetaBlobAttribute((EntityGroup) getParent(), _db, getProfile(), strAttCode);

				break;
			default :
				break;
			}
			//set db exclude from copy
			if (oEma_db != null) {
				strNow = _db.getDates().getNow();
				strValOn = strNow;
				strEffOn = strNow;
				//set exclude from copy here b/c it usually is set in EntityGroup...
				try {
					rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "Entity/Attribute", strEntTypeParent, getAttributeCode(), "Copy", strValOn, strEffOn);
					rdrs = new ReturnDataResultSet(rs);
				} finally {
					if (rs !=null){
						rs.close();
						rs = null;
					}
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
				for (int row = 0; row < rdrs.getRowCount(); row++) {
					String strLinkValue = rdrs.getColumn(row, 0);
					//this should be 'N'
					_db.debug(D.EBUG_SPEW, "gbl7503 answers:" + strLinkValue);
					if (strLinkValue.equals("N")) {
						oEma_db.setExcludeFromCopy(true);
					}
				}
			}
			//set db Capability
			if (oEma_db != null) {
				strNow = _db.getDates().getNow();
				strValOn = strNow;
				strEffOn = strNow;
				//set dispaly order here b/c it usually is set in EntityGroup...
				try {
					rs = _db.callGBL7503(new ReturnStatus(-1), getProfile().getEnterprise(), "Role/Attribute", getProfile().getRoleCode(), getAttributeCode(), "Capability", strValOn, strEffOn);
					rdrs = new ReturnDataResultSet(rs);
				} finally {
					if (rs !=null){
						rs.close();
						rs = null;
					}
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
				for (int row = 0; row < rdrs.getRowCount(); row++) {
					String strLinkValue = rdrs.getColumn(row, 0);
					//this should be 'N'
					_db.debug(D.EBUG_SPEW, "gbl7503 answers:" + strLinkValue);
					oEma_db.setCapability(strLinkValue);
				}
			}
		}

		//1) MetaEntity table
		oEl = getMetaEntityRowsForUpdate(_db, oEma_db, _bIsExpire);
		for (int i = 0; i < oEl.size(); i++) {
			MetaEntityRow oMeRow = (MetaEntityRow) oEl.getAt(i);
			oMeRow.updatePdh(_db);
			bChanged = true;
		}
		//2) MetaDescription table
		oEl = getMetaDescriptionRowsForUpdate(_db, oEma_db, _bIsExpire);
		for (int i = 0; i < oEl.size(); i++) {
			MetaDescriptionRow oMdRow = (MetaDescriptionRow) oEl.getAt(i);
			oMdRow.updatePdh(_db);
			bChanged = true;
		}
		//3) MetaLinkAttr table
		oEl = getMetaLinkAttrRowsForUpdate(_db, oEma_db, _bIsExpire);
		_db.debug(D.EBUG_SPEW, "MetaLinkAttrRow EANList size: " + oEl.size());
		for (int i = 0; i < oEl.size(); i++) {
			MetaLinkAttrRow oMlaRow = (MetaLinkAttrRow) oEl.getAt(i);
			oMlaRow.updatePdh(_db);
			bChanged = true;
		}

		//RE: if this is a MetaFlagAttribute -> update MetaFlags here...

		return bChanged;
	}

	/**
	 *  Get the MetaLinkAttrRows for any changes relevent to MetaLinkAttr table 1)
	 *  if new Att -> get one MLA row 2) else if cap has changed -> get one changed
	 *  row RE: Update Entity/Attribute records!!
	 *
	 *@param  _db                             Description of the Parameter
	 *@param  _oEma_db                        Description of the Parameter
	 *@param  _bIsExpire                      Description of the Parameter
	 *@return                                 The metaLinkAttrRowsForUpdate value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	private EANList getMetaLinkAttrRowsForUpdate(Database _db, EANMetaAttribute _oEma_db, boolean _bIsExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
		EANList eList = new EANList();
		String strAttCode = this.getAttributeCode();
		String strLinkType = null;
		String strRoleCode = getProfile().getRoleCode();
		String strLinkCode = null;
		String strLinkType1 = null;
		String strLinkValue = null;
		String strNow = _db.getDates().getNow();
		String strForever = _db.getDates().getForever();
		String strEnterprise = getProfile().getEnterprise();
		String strValOn = strNow;
		String strEffOn = strNow;
		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		String strCap = this.getCapability();
		String strCap_db = "";
		// String strAttType_db = "";

		//does this attribute exist in the MetaEntity table??
		boolean bIsNewAtt = MetaEntityList.isNewAttributeCode(_db, getProfile(), strAttCode);

		if (!bIsNewAtt) {
			strCap_db = _oEma_db.getCapability();
			//   strAttType_db = _oEma_db.getAttributeType();
		}

		//UPDATE
		if (!_bIsExpire) {

			EntityGroup oEgParent = (EntityGroup) getParent();
			String strParentEntType = null;
			boolean bNewParentEntity = true;

			//0 lets determine if this is a new attribute in relation to this entity (i.e. new link) or not yet
			strLinkType = "Entity/Attribute";
			// A) EntityAttribute
			strLinkCode = "EntityAttribute";
			strLinkValue = "L";

			if (oEgParent != null) {
				strParentEntType = oEgParent.getEntityType();
				try {
					rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
					rdrs = new ReturnDataResultSet(rs);
				} finally {
					if (rs !=null){
						rs.close();
						rs = null;
					}
					_db.commit();
					_db.freeStatement();
					_db.isPending();
				}
				for (int row = 0; row < rdrs.getRowCount(); row++) {
					strLinkType1 = rdrs.getColumn(row, 0);
					_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1);
					//if we find the parent, set bNewParentEntity = false
					if (strLinkType1.equals(strParentEntType)) {
						bNewParentEntity = false;
						break;
					}
				}
			}
			//

			// 1) Role/Attribute
			strLinkType = "Role/Attribute";
			strLinkCode = "Capability";
			//default cap for new attributes to "S"
			if (bIsNewAtt || bNewParentEntity) {
				strCap = "S";
				setCapability("S");
			}
			if (bIsNewAtt || !strCap.equals(strCap_db)) {
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strRoleCode, strAttCode, strLinkCode, strCap, strNow, strForever, strNow, strForever, 2));
				//we must add for UPDATEAL...
				if (!strRoleCode.equals("UPDATEAL")) {
					eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, "UPDATEAL", strAttCode, strLinkCode, "S", strNow, strForever, strNow, strForever, 2));
				}
			}
			// 2) Entity/Attribute records
			strLinkType = "Entity/Attribute";
			// A) EntityAttribute
			strLinkCode = "EntityAttribute";
			strLinkValue = "L";

			//ASSUME THAT LINKVALUE WILL ALWAYS BE 'L' FOR NOW - ALTHOUGH IT COULD BE 'A' FOR ASSOCIATION ATTS....
			//(e.g. only add this record for new atts or for atts that are not on this entity yet..)
			//ALSO: this attribute must be attached to an entity
			if ((bIsNewAtt || bNewParentEntity) && strParentEntType != null) {
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strParentEntType, strAttCode, strLinkCode, strLinkValue, strNow, strForever, strNow, strForever, 2));
			}

			// D) Exclude From Copy
			strLinkCode = "Copy";
			if (strParentEntType != null) {
				//new Att: put only if excludeFromCopy == true
				if ((bIsNewAtt || bNewParentEntity) && this.isExcludeFromCopy()) {
					eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strParentEntType, strAttCode, strLinkCode, "N", strNow, strForever, strNow, strForever, 2));
				}
				//if nav has changed
				else if (!bIsNewAtt && this.isExcludeFromCopy() != _oEma_db.isExcludeFromCopy()) {

					//if navigate has been added put record
					if (this.isExcludeFromCopy()) {
						eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strParentEntType, strAttCode, strLinkCode, "N", strNow, strForever, strNow, strForever, 2));
					} else {
						//expire if removed
						eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strParentEntType, strAttCode, strLinkCode, "N", strNow, strNow, strNow, strNow, 2));
					}
				}
			}

			// 3) Attribute/Test records
			strLinkType = "Attribute/Test";
			//first if this is a new att...
			if (bIsNewAtt) {
				Vector v = getAttributeTests(this);
				for (int i = 0; i < v.size(); i++) {
					String[] sa = (String[]) v.elementAt(i);
					String s0 = sa[0];
					//linkType2
					String s1 = sa[1];
					//linkCode
					String s2 = sa[2];
					//linkValue
					eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strAttCode, s0, s1, s2, strNow, strForever, strNow, strForever, 2));
				}
			}
			//else->compare new to old
			else if (!bIsNewAtt) {
				Vector v1 = getExpireAttributeTests(_oEma_db, this);
				Vector v2 = null;
				for (int i = 0; i < v1.size(); i++) {
					String[] sa = (String[]) v1.elementAt(i);
					String s0 = sa[0];
					//linkType2
					String s1 = sa[1];
					//linkCode
					String s2 = sa[2];
					//linkValue
					eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strAttCode, s0, s1, s2, strNow, strNow, strNow, strNow, 2));
				}
				v2 = getUpdateAttributeTests(_oEma_db, this);
				for (int i = 0; i < v2.size(); i++) {
					String[] sa = (String[]) v2.elementAt(i);
					String s0 = sa[0];
					//linkType2
					String s1 = sa[1];
					//linkCode
					String s2 = sa[2];
					//linkValue
					eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strAttCode, s0, s1, s2, strNow, strForever, strNow, strForever, 2));
				}
			}
			//4) ODSLength - only for 'S','U','I','T' types
			if (getAttributeType().equals("T") || getAttributeType().equals("S") || getAttributeType().equals("U") || getAttributeType().equals("I") || getAttributeType().equals("L")) {
				int iOdsLen_db = -1;
				String strLinkType2 = getAttributeCode();

				strLinkType = "ODSLength";
				strLinkType1 = "ODS";
				strLinkCode = "Length";
				//first get db data if it exists
				if (!bIsNewAtt) {
					//access this guy directly (easier than recasting, etc...)
					iOdsLen_db = _oEma_db.m_iOdsLength;
					//if old in db and it changed -> expire old
					if (iOdsLen_db != m_iOdsLength) {
						String strLinkValue_old = String.valueOf(iOdsLen_db);
						strLinkValue = String.valueOf(m_iOdsLength);
						if (iOdsLen_db != -1) {
							eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue_old, strNow, strNow, strNow, strNow, 2));
						}
						eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue, strNow, strForever, strNow, strForever, 2));
					}
				} else {
					//update if ODSLength exists
					if (m_iOdsLength != -1) {
						strLinkValue = String.valueOf(m_iOdsLength);
						eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue, strNow, strForever, strNow, strForever, 2));
					}
				}

			}
		}
		//EXPIRE
		else if (!bIsNewAtt) {
			// 1) Role/Attribute records
			//get ALL roleCodes that maintain this attribute (including the current profile's roleCode)
			strLinkType = "Role/Attribute";
			strLinkCode = "Capability";
			_db.debug(D.EBUG_SPEW, "calling gbl7507: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strLinkCode + "," + strValOn + "," + strEffOn);
			rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				strLinkType1 = rdrs.getColumn(row, 0);
				strLinkValue = rdrs.getColumn(row, 1);
				_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
				//_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strLinkType1 + "," + strAttCode + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			}
			// 2) Entity/Attribute records - Careful: we must be sure to expire ALL records (so do this for all entities...)
			// A) linkCode = EntityAttribute
			strLinkType = "Entity/Attribute";
			strLinkCode = "EntityAttribute";
			_db.debug(D.EBUG_SPEW, "calling gbl7507: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strLinkCode + "," + strValOn + "," + strEffOn);
			rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				strLinkType1 = rdrs.getColumn(row, 0);
				strLinkValue = rdrs.getColumn(row, 1);
				_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
				//_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strLinkType1 + "," + strAttCode + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			}
			// B) linkCode = DisplayOrder
			/*
			 *  strLinkCode = "DisplayOrder";
			 *  _db.debug(D.EBUG_SPEW, "calling gbl7507: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strLinkCode + "," + strValOn + "," + strEffOn);
			 *  rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
			 *  rdrs = new ReturnDataResultSet(rs);
			 *  rs.close();
			 *  rs = null;
			 *  _db.freeStatement();
			 *  _db.isPending();
			 *  for (int row = 0; row < rdrs.getRowCount(); row++) {
			 *  strLinkType1 = rdrs.getColumn(row, 0);
			 *  strLinkValue = rdrs.getColumn(row, 1);
			 *  _db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
			 *  /_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strLinkType1 + "," + strAttCode + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
			 *  eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			 *  }
			 */

			// GAB 5/14/03 - removed Navigate update
			/*
            // C) linkCode = Navigate
            strLinkCode = "Navigate";
            _db.debug(D.EBUG_SPEW, "calling gbl7507: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strLinkCode + "," + strValOn + "," + strEffOn);
            rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
            rdrs = new ReturnDataResultSet(rs);
            rs.close();
            rs = null;
            _db.freeStatement();
            _db.isPending();
            for (int row = 0; row < rdrs.getRowCount(); row++) {
            strLinkType1 = rdrs.getColumn(row, 0);
            strLinkValue = rdrs.getColumn(row, 1);
            _db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
            //_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strLinkType1 + "," + strAttCode + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
            eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
            }
			 */

			// D) Exclude From Copy
			strLinkCode = "Copy";
			_db.debug(D.EBUG_SPEW, "calling gbl7507: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strLinkCode + "," + strValOn + "," + strEffOn);
			rs = _db.callGBL7507(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strLinkCode, strValOn, strEffOn);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				strLinkType1 = rdrs.getColumn(row, 0);
				strLinkValue = rdrs.getColumn(row, 1);
				_db.debug(D.EBUG_SPEW, "gbl7507 answers: " + strLinkType1 + ":" + strLinkValue);
				//_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strLinkType1 + "," + strAttCode + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strAttCode, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			}

			// 3) Attribute/Test records
			strLinkType = "Attribute/Test";
			_db.debug(D.EBUG_SPEW, "calling gbl7509: " + strEnterprise + "," + strLinkType + "," + strAttCode + "," + strValOn + "," + strEffOn);
			rs = _db.callGBL7509(new ReturnStatus(-1), strEnterprise, strLinkType, strAttCode, strValOn, strEffOn);
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				strLinkType1 = rdrs.getColumn(row, 0);
				strLinkCode = rdrs.getColumn(row, 1);
				strLinkValue = rdrs.getColumn(row, 2);
				_db.debug(D.EBUG_SPEW, "gbl7509 answers: " + strLinkType1 + ":" + strLinkCode + ":" + strLinkValue);
				//_db.debug(D.EBUG_SPEW,"MetaLinkAttrRow: [" + strLinkType + "," + strAttCode + "," + strLinkType1 + "," + strLinkCode + "," + strLinkValue + "," + strNow + "(*4),2]");
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strAttCode, strLinkType1, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			}
			//4) ODSLength - only for 'S','U','I','T',L types
			if (m_iOdsLength != -1 && (getAttributeType().equals("T") || getAttributeType().equals("S") || getAttributeType().equals("U") || getAttributeType().equals("I") || getAttributeType().equals("L"))) {
				String strLinkType2 = getAttributeCode();
				strLinkType = "ODSLength";
				strLinkType1 = "ODS";
				strLinkCode = "Length";
				// we must access this guy directly
				strLinkValue = String.valueOf(m_iOdsLength);
				eList.put(new MetaLinkAttrRow(getProfile(), strLinkType, strLinkType1, strLinkType2, strLinkCode, strLinkValue, strNow, strNow, strNow, strNow, 2));
			}
		}
		return eList;
	}

	/**
	 *  Get the MetaDescriptionRows for any changes relavent to MetaDescription
	 *  table. 1) if new attribute -> get one row for update. 2) if descriptions
	 *  have changed -> get row w/ new descriptions (gbl2909 will take care of the
	 *  rest)
	 *
	 *@param  _db                             Description of the Parameter
	 *@param  _oEma_db                        Description of the Parameter
	 *@param  _bIsExpire                      Description of the Parameter
	 *@return                                 The metaDescriptionRowsForUpdate
	 *      value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	private EANList getMetaDescriptionRowsForUpdate(Database _db, EANMetaAttribute _oEma_db, boolean _bIsExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
		EANList eList = new EANList();
		String strAttCode = this.getAttributeCode();
		String strShortDesc = this.getShortDescription();
		String strLongDesc = this.getLongDescription();
		String strNow = _db.getDates().getNow();
		String strForever = _db.getDates().getForever();
		String strEnterprise = getProfile().getEnterprise();

		ResultSet rs = null;
		ReturnDataResultSet rdrs = null;
		String strAttType = this.getAttributeType();

		String strShortDesc_db = "";
		String strLongDesc_db = "";
		String strAttType_db = "";

		//does this attribute exist in the MetaEntity table??
		boolean bIsNewAtt = MetaEntityList.isNewAttributeCode(_db, getProfile(), strAttCode);

		if (!bIsNewAtt) {
			strShortDesc_db = _oEma_db.getShortDescription();
			strLongDesc_db = _oEma_db.getLongDescription();
			strAttType_db = _oEma_db.getAttributeType();
		}

		//UPDATE
		if (!_bIsExpire) {
			//1) new Entity
			if (bIsNewAtt) {
				MetaDescriptionRow oMdRow = new MetaDescriptionRow(getProfile(), strAttCode, strAttType, strShortDesc, strLongDesc, getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2);
				eList.put(oMdRow);
			} else {
				// update the description and/or entityClass if required...

				//2) update descriptions if necessary
				if (!strShortDesc.equals(strShortDesc_db) || !strLongDesc.equals(strLongDesc_db)) {
					eList.put(new MetaDescriptionRow(getProfile(), strAttCode, strAttType, strShortDesc, strLongDesc, getProfile().getReadLanguage().getNLSID(), strNow, strForever, strNow, strForever, 2));
				}
			}
		}
		//EXPIRE
		else if (!bIsNewAtt) {
			//if it isnt in database -> we dont need to remove it from database (duh..)
			//we must expire ALL nlsId's!
			try {
				rs = _db.callGBL7511(new ReturnStatus(-1), strEnterprise, strAttCode, strAttType_db);
				rdrs = new ReturnDataResultSet(rs);
			} finally {
				if (rs !=null){
					rs.close();
					rs = null;
				}
				_db.freeStatement();
				_db.isPending();
			}
			for (int row = 0; row < rdrs.getRowCount(); row++) {
				int iNLSID = rdrs.getColumnInt(row, 0);
				String strShortDesc_nls = rdrs.getColumn(row, 1);
				String strLongDesc_nls = rdrs.getColumn(row, 2);
				_db.debug(D.EBUG_SPEW, "gbl7511 answers: " + iNLSID + ":" + strShortDesc_nls + ":" + strLongDesc_nls);
				//_db.debug(D.EBUG_SPEW,"MetaDescriptionRow: [" + strAttCode + "," + strAttType_db + "," + strShortDesc_nls + "," + strLongDesc_nls + "," + iNLSID + "," + strNow + "(*4),2]");
				eList.put(new MetaDescriptionRow(getProfile(), strAttCode, strAttType_db, strShortDesc_nls, strLongDesc_nls, iNLSID, strNow, strNow, strNow, strNow, 2));
			}
		}
		return eList;
	}

	/**
	 *  Get the MetaEntityRows for any changes relavent to MetaEntity table. 1) if
	 *  new attribute -> get one row for update. 2) else if NOT new entity AND
	 *  attType has changed -> get old row to expire, new row to update.
	 *
	 *@param  _db                             Description of the Parameter
	 *@param  _oEma_db                        Description of the Parameter
	 *@param  _bIsExpire                      Description of the Parameter
	 *@return                                 The metaEntityRowsForUpdate value
	 *@exception  SQLException                Description of the Exception
	 *@exception  MiddlewareException         Description of the Exception
	 *@exception  MiddlewareRequestException  Description of the Exception
	 */
	private EANList getMetaEntityRowsForUpdate(Database _db, EANMetaAttribute _oEma_db, boolean _bIsExpire) throws SQLException, MiddlewareException, MiddlewareRequestException {
		EANList eList = new EANList();
		String strAttCode = this.getAttributeCode();
		String strAttType = this.getAttributeType();
		String strNow = _db.getDates().getNow();
		String strForever = _db.getDates().getForever();

		String strAttType_db = "";

		boolean bIsNewAtt = MetaEntityList.isNewAttributeCode(_db, getProfile(), strAttCode);

		if (!bIsNewAtt) {
			strAttType_db = _oEma_db.getAttributeType();
		}

		//UPDATE
		if (!_bIsExpire) {
			////// 1) New Entity case
			if (bIsNewAtt) {
				eList.put(new MetaEntityRow(getProfile(), strAttCode, strAttType, strNow, strForever, strNow, strForever, 2));
			}
			////// 2) existing attribute case
			else {
				// it is an existing one needing update
				// keep in mind that user COULD change this attribute's attribute type...
			}
		}
		//EXPIRE
		else if (!bIsNewAtt) {
			//if it isnt in database -> we dont need to remove it from database (duh..)
			eList.put(new MetaEntityRow(getProfile(), strAttCode, strAttType_db, strNow, strNow, strNow, strNow, 2));
		}
		return eList;
	}

	/**
	 *  get a Vector of LinkType2, LinkCode, LinkValue String[]'s which pertain to
	 *  a particular Attribute' Tests used to simplify the process of grabbing
	 *  MetaLinkAttrRows
	 *
	 *@param  _oEma  Description of the Parameter
	 *@return        The attributeTests value
	 */
	private Vector getAttributeTests(EANMetaAttribute _oEma) {
		Vector v = new Vector();
		String[] sa = new String[3];
		boolean b1 = _oEma.isAlpha();
		boolean b2 = _oEma.isInteger();
		boolean b3 = _oEma.isSpecial();
		boolean b4 = _oEma.isUpper();
		String strAlphaRule = null;

		//first go through the alpha,numeric,special,upper 's
		sa[1] = "Value";
		sa[2] = "0";

		if (b1 && !b2 && !b3 && !b4) {
			strAlphaRule = "ALPHA";
		}
		if (b1 && b2 && !b3 && !b4) {
			strAlphaRule = "ALPHAINTEGER";
		}
		if (b1 && b2 && b3 && !b4) {
			strAlphaRule = "ALPHAINTEGERSPECIAL";
		}
		if (b1 && b2 && b3 && b4) {
			strAlphaRule = "ALPHAINTEGERSPECIALUPPER";
		}
		if (b1 && b2 && !b3 && b4) {
			strAlphaRule = "ALPHAINTEGERUPPER";
		}
		if (b1 && !b2 && b3 && !b4) {
			strAlphaRule = "ALPHASPECIAL";
		}
		if (b1 && !b2 && b3 && b4) {
			strAlphaRule = "ALPHASPECIALUPPER";
		}
		if (b1 && !b2 && !b3 && b4) {
			strAlphaRule = "ALPHAUPPER";
		}
		if (!b1 && b2 && !b3 && !b4) {
			strAlphaRule = "INTEGER";
		}
		if (!b1 && !b2 && !b3 && b4) {
			strAlphaRule = "UPPER";
		}
		sa[0] = strAlphaRule;
		if (sa[0] != null) {
			v.addElement(sa);
		}
		//
		if (_oEma.isNoBlanks()) {
			sa = new String[3];
			sa[0] = "NOBLANKS";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isClassified()) {
			sa = new String[3];
			sa[0] = "CLASSIFY";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isSearchable()) {
			sa = new String[3];
			sa[0] = "SEARCHABLE";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isRefreshEnabled()) {
			sa = new String[3];
			sa[0] = "REFRESHFORM";
			sa[1] = "Value";
			sa[2] = "Y";
			v.addElement(sa);
		}
		if (_oEma.isUSEnglishOnly()) {
			sa = new String[3];
			sa[0] = "USENGLISH";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isDate()) {
			sa = new String[3];
			sa[0] = "DATE";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isDecimal()) {
			sa = new String[3];
			sa[0] = "DECIMAL";
			sa[1] = "Value";
			sa[2] = String.valueOf(_oEma.getDecimalPlaces());
			v.addElement(sa);
		}
		if (_oEma.hasDefaultValue()) {
			sa = new String[3];
			sa[0] = "DEFAULT";
			sa[1] = "Value";
			sa[2] = _oEma.getDefaultValue();
			v.addElement(sa);
		}
		if (_oEma.getEqualsLen() > 0) {
			sa = new String[3];
			sa[0] = "EQU";
			sa[1] = "Value";
			sa[2] = String.valueOf(_oEma.getEqualsLen());
			v.addElement(sa);
		}
		if (_oEma.getGreater() > 0) {
			sa = new String[3];
			sa[0] = "GREATER";
			sa[1] = "Value";
			sa[2] = String.valueOf(_oEma.getGreater());
			v.addElement(sa);
		}
		if (_oEma.isFutureDate()) {
			sa = new String[3];
			sa[0] = "FUTUREDATE";
			sa[1] = (_oEma.isWarningDate()?"Warning":"Value");
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isNumeric()) {
			sa = new String[3];
			sa[0] = "NUMERIC";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.getMaxLen() > 0) {
			sa = new String[3];
			sa[0] = "MAX";
			sa[1] = "Value";
			sa[2] = String.valueOf(_oEma.getMaxLen());
			v.addElement(sa);
		}
		if (_oEma.getMinLen() > 0) {
			sa = new String[3];
			sa[0] = "MIN";
			sa[1] = "Value";
			sa[2] = String.valueOf(_oEma.getMinLen());
			v.addElement(sa);
		}
		if (_oEma.isPastDate()) {
			sa = new String[3];
			sa[0] = "PASTDATE";
			sa[1] = (_oEma.isWarningDate()?"Warning":"Value");
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isSpellCheckable()) {
			sa = new String[3];
			sa[0] = "SPELLCHECK";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isTime()) {
			sa = new String[3];
			sa[0] = "TIME";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isUnique()) {
			sa = new String[3];
			sa[0] = "UNIQUE";
			sa[1] = _oEma.getUniqueClass();
			sa[2] = _oEma.getUniqueType();
			//only if valid values
			if (sa[1] != null && sa[2] != null && !sa[1].equals("") && !sa[2].equals("")) {
				v.addElement(sa);
			}
		}
		if (_oEma.isRequired()) {
			sa = new String[3];
			sa[0] = "EXIST";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		if (_oEma.isGeoIndicator()) {
			sa = new String[3];
			sa[0] = "GEOINDICATOR";
			sa[1] = "Value";
			sa[2] = "0";
			v.addElement(sa);
		}
		return v;
	}

	/**
	 *  pull out the Attribute/Test records that were in the old Att -> ! in new
	 *  Att to Expire
	 *
	 *@param  _db    Description of the Parameter
	 *@param  _this  Description of the Parameter
	 *@return        The expireAttributeTests value
	 */
	private Vector getExpireAttributeTests(EANMetaAttribute _db, EANMetaAttribute _this) {
		Vector v = new Vector();
		Vector v1 = getAttributeTests(_db);
		Vector v2 = getAttributeTests(_this);

		for (int i = 0; i < v1.size(); i++) {
			String[] sa1 = (String[]) v1.elementAt(i);
			String s10 = sa1[0];
			String s11 = sa1[1];
			String s12 = sa1[2];
			boolean bFoundInNew = false;
			for (int j = 0; j < v2.size(); j++) {
				String[] sa2 = (String[]) v2.elementAt(j);
				String s20 = sa2[0];
				String s21 = sa2[1];
				String s22 = sa2[2];
				if (s10.equals(s20) && s11.equals(s21) && s12.equals(s22)) {
					bFoundInNew = true;
					j = v2.size();
				}
			}
			//so add this OLD row that is ! in the new att (to expire)
			if (!bFoundInNew) {
				v.addElement(sa1);
			}
		}
		return v;
	}

	/**
	 *  pull out the Attribute/Test records that were !in the old Att -> in new Att
	 *  to Update
	 *
	 *@param  _db    Description of the Parameter
	 *@param  _this  Description of the Parameter
	 *@return        The updateAttributeTests value
	 */
	private Vector getUpdateAttributeTests(EANMetaAttribute _db, EANMetaAttribute _this) {
		Vector v = new Vector();
		Vector v1 = getAttributeTests(_this);
		Vector v2 = getAttributeTests(_db);

		for (int i = 0; i < v1.size(); i++) {
			String[] sa1 = (String[]) v1.elementAt(i);
			String s10 = sa1[0];
			String s11 = sa1[1];
			String s12 = sa1[2];
			boolean bFoundInOld = false;
			for (int j = 0; j < v2.size(); j++) {
				String[] sa2 = (String[]) v2.elementAt(j);
				String s20 = sa2[0];
				String s21 = sa2[1];
				String s22 = sa2[2];
				if (s10.equals(s20) && s11.equals(s21) && s12.equals(s22)) {
					bFoundInOld = true;
					j = v2.size();
				}
			}
			//so add this NEW row that is ! in the old att (to update)
			if (!bFoundInOld) {
				v.addElement(sa1);
			}
		}
		return v;
	}

	/**
	 *  accessor - m_iOdsLength set in EANMetaAttribute super class
	 *
	 *@return    The odsLength value
	 */
	public int getOdsLength() {
		return m_iOdsLength;
	}

	/**
	 *  mutator - set m_iOdsLength defined in EANMetaAttribute super class
	 *
	 *@param  _i  The new odsLength value
	 */
	public void setOdsLength(int _i) {
		m_iOdsLength = _i;
	}

	/**
	 * setXMLDTD
	 *
	 * @param _str
	 *  @author David Bigelow
	 */
	public final void setXMLDTD(String _str) {
		m_strXMLDTD = _str;
	}

	/**
	 * getXMLDTD
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public String getXMLDTD() {
		if (m_strXMLDTD == null) {
			return "eannounce.dtd";
		}
		return m_strXMLDTD;
	}
	private final void setReverseLookup(boolean _b) {
		m_bRevLkUp = _b;
	}

	/**
	 * isReverseLookupEnabled
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isReverseLookupEnabled() {
		return m_bRevLkUp;
	}

	/**
	 * Indicates a 'derived' attribute; that is, we generate the value and store it in this object (! from db).
	 *
	 * @return boolean
	 */
	public final boolean isDerived() {
		return m_bDerived;
	}

	/**
	 * setDerived
	 *
	 * @param _b
	 *  @author David Bigelow
	 */
	protected final void setDerived(boolean _b) {
		m_bDerived = _b;
	}

	/**
	 * setAttributeType
	 *
	 * @param _str
	 *  @author David Bigelow
	 */
	protected final void setAttributeType(String _str) {
		m_strAttributeType = _str;
	}

	/**
	 * setAttributeType
	 *
	 * @param _i
	 *  @author David Bigelow
	 */
	protected final void setAttributeType(int _i) {
		m_iAttributeType = _i;
	}

	/**
	 * isText
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isText() {
		return m_iAttributeType == IS_TEXT || m_iAttributeType == IS_ID;
	}

	/**
	 * isSingle
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isSingle() {
		return m_iAttributeType == IS_SINGLE;
	}

	/**
	 * isMulti
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isMulti() {
		return m_iAttributeType == IS_MULTI;
	}

	/**
	 * isStatus
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isStatus() {
		return m_iAttributeType == IS_STATUS;
	}

	/**
	 * isABR
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isABR() {
		return m_iAttributeType == IS_ABR;
	}

	/**
	 * isLongText
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isLongText() {
		return m_iAttributeType == IS_LONGTEXT;
	}

	/**
	 * isBlob
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isBlob() {
		return m_iAttributeType == IS_BLOB;
	}

	/**
	 * isXML
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final boolean isXML() {
		return m_iAttributeType == IS_XML;
	}

	/**
	 * getAttributeTypeInt
	 *
	 * @return
	 *  @author David Bigelow
	 */
	public final int getAttributeTypeInt() {
		return m_iAttributeType;
	}

	/*
 CR093005678
 CR0930055856
	 */
	public final boolean hasValidationRule() {
		return m_bValidRule;
	}

	public void setValidationRule(boolean _b) {
		m_bValidRule = _b;
		return;
	}

	public String checkValid(EntityItem _ei) {
		if (hasValidationRule()) {
			String sError = evaluateValidationRule(_ei);
			if (sError != null) {
				return sError;
			}
		}
		return null;
	}

	public void addValidationRule(String _sKey, String _sRule) {
		if (_sKey != null && _sRule != null) {
			if (m_hshValidRule == null) {
				m_hshValidRule = new Hashtable();
			}
			m_hshValidRule.put(_sKey,_sRule);
		}
		return;
	}

	public String[] getValidationRuleKeys() {
		if (m_hshValidRule != null && !m_hshValidRule.isEmpty()) {
			Set s = m_hshValidRule.keySet();
			return (String[])s.toArray(new String[s.size()]);
		}
		return null;
	}

	public String getValidationRule(String _s) {
//		System.out.println("getValidationRule(" + _s + ")");
		if (m_hshValidRule != null && m_hshValidRule.containsKey(_s)) {
			return (String)m_hshValidRule.get(_s);
		}
		return null;
	}

	private String evaluateValidationRule(EntityItem _ei) {
		boolean bError = true;
		String out = null;
		String[] keys = getValidationRuleKeys();
		if (keys != null) {
			int ii = keys.length;
			for (int i=0;i<ii && out == null;++i) {
//				System.out.println("processing key " + i + " of " + ii + ": " + keys[i]);
				String[] rules = getValidationRules(getValidationRule(keys[i]));
				if (rules != null) {
					int xx = rules.length;
//					System.out.println("processing rule 0: " + rules[0]);
					bError = processValidationRule(bError,rules[0],_ei);	//the inner most rule must be true;
//					System.out.println("rule is active: " + bError);
					for (int x=1;x<xx && bError;++x) {
//						System.out.println("processing rule " + x + " of " + xx + ": " + rules[x]);
						bError = processValidationRule(bError,rules[x],_ei);
						if (!bError) {
							out = keys[i];
						}
					}
				}
			}
		}
//		System.out.println("rules evaluated to " + out);
		return out;
	}

	private String[] getValidationRules(String _s) {
//		System.out.println("  getValidationRules(" + _s + ")");
		int iIndex = _s.lastIndexOf("(");
		if (iIndex > 0) {
			Vector v = new Vector();
			String s = new String(_s);
			while(iIndex > 0) {
				StringBuffer sb = new StringBuffer(s);
				int iEnd = s.indexOf(")",iIndex);
				v.add(s.substring(iIndex+1,iEnd));
				sb = sb.delete(iIndex,iEnd+1);
				s = sb.toString();
//				System.out.println("new String: " + s);
				iIndex = s.lastIndexOf("(");
			}
			v.add(s);
			if (!v.isEmpty()) {
				return (String[])v.toArray(new String[v.size()]);
			}
		}
		String[] out = new String[1];
		out[0] = _s;
		return out;
	}

	private boolean processValidationRule(boolean _b, String _s, EntityItem _ei) {
//		System.out.println("processValidationRule(" + _s + ")");
		boolean out = true;
		//int iType = -1;
		String s = new String(_s);
		int iIndex = Math.max(s.lastIndexOf("&&"),s.lastIndexOf("||"));
		boolean bAnd = false;
		if (iIndex > 0) {
			bAnd = (s.charAt(iIndex) == '&');
		}
		if (iIndex <= 0) {
			return isValidationRuleMet(s,_ei);
		} else if (s.endsWith("&&") || s.endsWith("||")) {
			return isValidationRuleMet(s.substring(0,iIndex),_ei);
		}
		boolean b1 = isValidationRuleMet(s.substring(0,iIndex),_ei);

//		System.out.println("b1 is true");
		boolean b2 = processValidationRule(_b,s.substring(iIndex+2),_ei);
		if (bAnd) {
			out = and(b1, b2);
		} else {
			out = or(b1, b2);
		}
		return out;
	}

	private boolean and(boolean _b0, boolean _b1) {
//		System.out.println("and(" + _b0 + ", " + _b1 + ")");
		return _b0 && _b1;
	}

	private boolean or(boolean _b0, boolean _b1) {
//		System.out.println("or(" + _b0 + ", " + _b1 + ")");
		return _b0 || _b1;
	}

	private boolean isValidationRuleMet(String _s,EntityItem _ei) {
//		System.out.println("isValidationRuleMet(" + _s + ")");
		boolean out = false;
		if (_s != null) {
			int iIndex = 0;
			int iType = -1;
			String sAtt = null;
			String sVal = null;
			if ((iIndex = _s.indexOf("<=")) > 0) {
				iType = 0;
			} else if ((iIndex = _s.indexOf(">=")) > 0) {
				iType = 1;
			} else if ((iIndex = _s.indexOf("==")) > 0) {
				iType = 2;
			} else if ((iIndex = _s.indexOf("!=")) >0) {
				iType = 3;
			} else if ((iIndex = _s.indexOf("<")) > 0) {
				iType = 4;
			} else if ((iIndex = _s.indexOf(">")) > 0) {
				iType = 5;
			}

			sAtt = _s.substring(0,iIndex);
			if (iType <= 3) {
				iIndex +=2;
			} else {
				iIndex +=1;
			}
			sVal = _s.substring(iIndex);

			if (sAtt != null && sVal != null) {
				EANAttribute att = getAttribute(_ei,sAtt);
				if (att != null && att.isActive()) {
					if (iType == 0) {
//						System.out.println("    <=    ");
						out = (compareTo(att,sVal) < 0 || equals(att,sVal));
					} else if (iType == 1) {
//						System.out.println("    >=    ");
						out = (compareTo(att,sVal) > 0 || equals(att,sVal));
					} else if (iType == 2) {
//						System.out.println("    ==    ");
						out = equals(att,sVal);
					} else if (iType == 3) {
//						System.out.println("    !=    ");
						out = !equals(att,sVal);
					} else if (iType == 4) {
//						System.out.println("    <     ");
						out = (compareTo(att,sVal) < 0);
					} else if (iType == 5) {
//						System.out.println("    >     ");
						out = (compareTo(att,sVal) > 0);
					}
//					} else {
//					System.out.println("attribute " + sAtt + " was not found.");
				}
			}
		}
//		System.out.println("    vrfy: " + out);
		return out;
	}

	private int compareTo(EANAttribute _att, String _val) {
		String sVal = null;
		if (_att != null) {
//			System.out.println("   attribute found.");
			if (_att instanceof EANFlagAttribute) {
				if (((EANFlagAttribute)_att).isSelected(_val)) {
					sVal = _val;
				} else {
					sVal = "";
				}
			} else {
				sVal =  _att.toString();
			}
		}
//		System.out.println("    act: " + sVal);
//		System.out.println("    chk: " + _val);
		return compareTo(sVal,_val);
	}

	private int compareTo(String _s, String _ss) {
		try {
			Integer i = Integer.valueOf(_s);
			Integer ii = Integer.valueOf(_ss);
			return i.compareTo(ii);
		} catch(NumberFormatException _nfe) {
		}
		return _s.compareToIgnoreCase(_ss);
	}

	private boolean equals(EANAttribute _att, String _val) {
//		System.out.println("    act: " + _att.toString());
//		System.out.println("    chk: " + _val);
		String sVal = null;
		if (_att != null) {
//			System.out.println("   attribute found.");
			if (_att instanceof EANFlagAttribute) {
				if (((EANFlagAttribute)_att).isSelected(_val)) {
					sVal = _val;
				} else {
					sVal = "";
				}
			} else {
				sVal = _att.toString();
			}
		}
		return sVal.equalsIgnoreCase(_val);
	}

	/*
         grab the value for the attribute in question
	 */
	private EANAttribute getAttribute(EntityItem _ei, String _sAttCode) {
//		System.out.println("getAttribute " + _sAttCode + " from " + _ei.getEntityType());
		EANAttribute out = null;
		if (_ei != null) {
			if (_sAttCode.indexOf(".") >= 0) {
				out = getAttribute(_ei,new StringTokenizer(_sAttCode,"."));
			} else {
				out = _ei.getAttribute(_sAttCode);
			}
		}
//		if (out == null) {
//		System.out.println("    attribute " + _sAttCode + " not found.");
//		} else {
//		System.out.println("    returning attribute: " + out.getKey());
//		}
		return out;
	}

	private EANAttribute getAttribute(EntityItem _ei, StringTokenizer _st) {
		String sEntity = null;
		String sAtt = null;
		if (_st.hasMoreTokens()) {
			sEntity = _st.nextToken();
//			System.out.println("Entity: " + sEntity);
		}
		if (_st.hasMoreTokens()) {
			sAtt = _st.nextToken();
//			System.out.println("Att: " + sAtt);
		}
		if (sEntity != null && sAtt != null) {
			EANAttribute att = _ei.getAttribute(sAtt);
			if (att != null) {
//				System.out.println("the attribute is on the original entity");
				return att;
			}
			EntityItem eiTarget = null;
			if (_ei.hasUpLinks()) {
			//	System.out.println("checking up links");
				int ii = _ei.getUpLinkCount();
				for (int i=0;i<ii && eiTarget == null;++i) {
					EntityItem test = (EntityItem)_ei.getUpLink(i);
					if (test != null && test.getEntityType().equals(sEntity)) {
						eiTarget = test;
					}
				}
			}
			if (_ei.hasDownLinks() && eiTarget == null) {
//				System.out.println("checking down links");
				int ii = _ei.getDownLinkCount();
				for (int i=0;i<ii && eiTarget == null;++i) {
					EntityItem test = (EntityItem)_ei.getDownLink(i);
					if (test != null && test.getEntityType().equals(sEntity)) {
						eiTarget = test;
					}
				}
			}
			if (eiTarget != null) {
//				System.out.println("found target Entity " + eiTarget.getKey());
				return getAttribute(eiTarget,sAtt);
			}
		}
//		System.out.println("unable to find attribute");
		return null;
	}

	/*
 cr0428066447
	 */
	protected boolean isSearchHidden(String _sAction) {
		if (m_hshSearchHidden != null) {
			return m_hshSearchHidden.containsKey(_sAction);
		}
		return false;
	}

	protected void setSearchHidden(String _sAction, String _sPrefill) {
		if (_sAction != null && _sPrefill != null) {
			if (m_hshSearchHidden == null) {
				m_hshSearchHidden = new Hashtable();
			}
			m_hshSearchHidden.put(_sAction,_sPrefill);
		}
		return;
	}

	protected String getSearchPrefill(String _sAction) {
		if (m_hshSearchHidden != null) {
			if (m_hshSearchHidden.containsKey(_sAction)) {
				return (String)m_hshSearchHidden.get(_sAction);
			}
		}
		return null;
	}
}
