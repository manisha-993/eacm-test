// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* QSMABRInterface interface QSM* classes will implement this for each entity type
* QSMRPTABRSTATUS will launch the correct class
* From "SG FS RPT QSM Load SS 20080926.doc"
*
* A single ABR supports this functionality based on the Entity Type that queues this function utilizing
* AttributeCode QSMRPTABRSTATUS of Type A.
*
*/
// QSMABRInterface.java,v
// Revision 1.2  2009/02/04 21:26:44  wendy
// CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
// Revision 1.1  2008/09/30 12:50:12  wendy
// CQ00006066-WI LA CTO - EACM - Support GFS data Load of QSM (report)
//
//
public interface QSMABRInterface
{
	static final String STATUS_FINAL = "0020";
	static final String DEFAULT_PUBFROM = "1980-01-01";
	static final String DEFAULT_PUBTO = "9999-12-31";
	
    /**********************************
    * get the name of the VE to use
    */
    String getVeName();

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
	String getVersion();

	/***********************************************
	 *  Get the number of columns in the ss
	 *
	 *@return int
	 */
	int getColumnCount();
	/***********************************************
	 *  Get the label for the specified column index
	 *
	 *@return String
	 */
	String getColumnLabel(int i);
	/***********************************************
	 *  Get the label for the specified column index for the flatfile
	 *
	 *@return String
	 */
	String getFFColumnLabel(int i);	
	/***********************************************
	 *  Get the width for the specified column (used for flat files)  CQ00016165
	 *
	 *@return int
	 */
	int getColumnWidth(int i);

	/***********************************************
	 * Should the report be generated for this root
	 * Check for specified attributes on root
	 *@return boolean
	 */
	boolean canGenerateReport(EntityItem rootitem, QSMRPTABRSTATUS abr);
	/***********************************************
	 * Should the report be generated for this extract
	 * Check for specified structure and attributes
	 *@return boolean
	 */
	boolean canGenerateReport(EntityList list, QSMRPTABRSTATUS abr);
	/***********************************************
	 * Should the report be generated for these dates
	 *@return boolean
	 */
	boolean withinDateRange(EntityItem rootitem, String now, QSMRPTABRSTATUS abr);
	
	/***********************************************
	 *  is this column changed?
	 *
	 *@return boolean
	 */
	boolean isChanged(String rowtype, Hashtable itemTbl, int i);
	
	/***********************************************
	 *  set the value for the specified column index into the cell - ss only
	 *
	 */
	void setColumnValue(HSSFCell cell,String rowtype, Hashtable itemTbl, int i);
	
	/***********************************************
	 *  get the value for the specified column index - flat file only CQ00016165
	 *
	 */
	String getColumnValue(String rowtype, Hashtable itemTbl, int i);
			
	/***********************************************
	 *  Get the entity items to put in the ss for this geo
	 *
	 *@return Vector
	 */
	Vector getRowItems(EntityList list,Hashtable diffTbl, String geo, QSMRPTABRSTATUS abr);
	
	/***********************************************
	 *  get row 1 - flat file only CQ00016165
	 *
	 */
	String getRowOne(EntityItem rootitem);
	/***********************************************
	 *  get row 1 - flat file only CQ00016165
	 *  text		start	end width justification
	 * Date______		3	12	10	Left
	 * Time___________	14	28	15	Left
	 * 
	 * or
	 * IFCDate___		3	12	10	Left
	 * IFCTime________	14	28	15	Left
	 * 
	 */
	String getRowTwoPrefix();
}
