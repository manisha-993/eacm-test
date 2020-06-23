//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

/*********************************************************************
 * This is used for find/replace requests
 * @author Wendy Stimpson
 */
//$Log: Findable.java,v $
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public interface Findable {
	/**
	 * reset any cells marked as found
	 */
	void resetFound();
	
    /**
     * select multi-column search checkbox based on this value
     * @return
     */
    boolean isMultiColumn();

    /**
     * enable replace function if this is true
     * @return
     */
    boolean isReplaceable();
    /**
     * enable reset color button
     * @return
     */
    boolean hasFound(); 
    
    /**
     * do the find
     * @param findValue
     * @param isMulti
     * @param useCase
     * @param isDown
     * @param isWrap
     * @return true if found
     */
    boolean findValue(String findValue, boolean isMulti, boolean useCase, boolean isDown, 
    		boolean isWrap);
    
    //Replace return values
    int REPLACED=0;
    int NOT_FOUND=1;
    int CELL_LOCKED=2;
    int CELL_UNEDITABLE=3;
    int ATTR_NOTREPLACEABLE=4;
    
    String getLockInformation();

    int replaceValue(String findValue, String replace, boolean useCase);

    /**
     * @param findValue
     * @param replace
     * @param isMulti
     * @param useCase
     * @param isDown
     * @param isWrap
     * @return
     */
    int replaceNextValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap);
    /**
     * replaceAllValue
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    void replaceAllValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown);
}
