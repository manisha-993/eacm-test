//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.mw;

/*********************************************************************
 * This is used for heavy weight operations that should not be cancelled
 * one of these can run at a time, no other tasks
 * actions that modify the pdh need to use this worker
 * @author Wendy Stimpson
 */
//$Log: HeavyWorker.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public abstract class HeavyWorker<T, V>  extends DBSwingWorker<T, V>  {

}
