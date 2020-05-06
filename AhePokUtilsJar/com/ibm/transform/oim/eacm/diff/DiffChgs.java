// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.diff;

/**********************************************************************************
* The result of comparison is an "edit script": a chain of DiffChgs objects.
*    Each DiffChgs object represents one place where some rows are deleted
*     and some are inserted.
*
*     LINE0 and LINE1 are the first affected rows in the two vectors (origin 0).
*     DELETED is the number of rows deleted here from file 0.
*     INSERTED is the number of rows inserted here in file 1.
*
*     If DELETED is 0 then LINE0 is the number of the row before
*     which the insertion was done; vice versa for INSERTED and LINE1.
*/
// $Log: DiffChgs.java,v $
// Revision 1.1  2006/07/24 20:50:11  wendy
// Replacement for XML in change reports
//
//
public class DiffChgs {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";

	/** cvs revision number */
	public static final String VERSION = "$Revision: 1.1 $";

	private DiffChgs link;	// next set of changes
	private int inserted;	// # lines of file 1 changed here. (rows in vector2)
	private int deleted;	// # lines of file 0 changed here. (rows in vector1)
	private int line0;		// Line number of 1st deleted line
	private int line1;		// Line number of 1st inserted line.

	/***************************************
	* Constructor
	*@param l0 int Line number of 1st deleted line.
	*@param l1 int Line number of 1st inserted line.
	*@param deletd int # lines of file 0 changed here.
	*@param insertd int # lines of file 1 changed here.
	*@param old DiffChgs next set of changes
	*/
 	DiffChgs(int l0, int l1, int deletd, int insertd, DiffChgs old) {
		line0 = l0;
		line1 = l1;
		inserted = insertd;
		deleted = deletd;
		link = old;
	}
    /***************************************
    * release memory
    */
    public void dereference() {
		if (link!=null){
			link.dereference();
			link=null;
		}
    }
	/***************************************
	* Get  next set of changes
	*@return DiffChgs
	*/
	public DiffChgs getLink() {
		return link;
	}

	/***************************************
	* Get  # rows of vector 1 inserted here.
	*@return int
	*/
	public int getInsertedCount() {
		return inserted;
	}
	/***************************************
	* Get  # rows of vector 0 deleted here.
	*@return int
	*/
	public int getDeletedCount() {
		return deleted;
	}
	/***************************************
	* Get row number of 1st deleted row.
	*@return int
	*/
	public int getFirstDeletedRowNumber() {
		return line0;
	}
	/***************************************
	* Get row number of 1st inserted row.
	*@return int
	*/
	public int getFirstInsertedRowNumber() {
		return line1;
	}

	/********************************************************************************
	* display this for debug
	*@return String
	*/
	public String toString() {
		return "line0: "+line0+" line1:"+line1+" inserted:"+inserted+" deleted:"+deleted;
	}
}
