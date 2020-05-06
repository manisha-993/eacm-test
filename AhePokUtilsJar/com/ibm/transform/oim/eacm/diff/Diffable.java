// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.diff;

/*****************************************************************************
* This interface is used for marking objects as ADD or DEL using the diff algorithm
*
*/
// $Log: Diffable.java,v $
// Revision 1.1  2006/07/24 20:50:11  wendy
// Replacement for XML in change reports
//
//
public interface Diffable
{
    /***********************************************************************
    * Called when the object was added
    */
    void setAdded();
    /***********************************************************************
    * Called when the object was deleted
    */
    void setDeleted();
    /***********************************************************************
    * Called when the object was not changed at all - entity existed at prior and
    * current time, but attributes need to be checked.  Add the EntityItem from current
    * time to the DiffEntity (it already has the EntityItem from prior time)
    *@param currentObj
    */
    void setNoChange(Diffable currentObj);
}
