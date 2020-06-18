//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANMeta.java,v $
// Revision 1.5  2005/02/14 17:57:47  dave
// more Jtest
//
// Revision 1.4  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.3  2003/04/23 15:57:35  dave
// Clean up.. and first add to autoselector
//
// Revision 1.2  2002/01/31 19:01:49  dave
// clean up and syntax fix
//
// Revision 1.1  2002/01/31 17:58:54  dave
// Rearranging Abrstract levels for more consistiency
//

package COM.ibm.eannounce.objects;

/**
*  Ensures that a getKey method is used for the eannounce objects
*
*@author     davidbig
*@created    April 23, 2003
*/
public interface EANMeta {

    /**
    *  Description of the Field
    */
    String CLASS_BRAND = "$Id: EANMeta.java,v 1.5 2005/02/14 17:57:47 dave Exp $";

    /**
    *  This is used In general to manage Meta Objects in Eannounce
    *
    *@param  _s  Description of the Parameter
    */
    void putLongDescription(String _s);

    /**
    *  Description of the Method
    *
    *@param  _i  Description of the Parameter
    *@param  _s  Description of the Parameter
    */
    void putLongDescription(int _i, String _s);

    /**
    *  Gets the longDescription attribute of the EANMeta object
    *
    *@return    The longDescription value
    */
    String getLongDescription();

    /**
    *  Description of the Method
    *
    *@param  _s  Description of the Parameter
    */
    void putShortDescription(String _s);

    /**
    *  Description of the Method
    *
    *@param  _i  Description of the Parameter
    *@param  _s  Description of the Parameter
    */
    void putShortDescription(int _i, String _s);

    /**
    *  Gets the shortDescription attribute of the EANMeta object
    *
    *@return    The shortDescription value
    */
    String getShortDescription();

    /**
    *  Sets the targetClass attribute of the EANMeta object
    *
    *@param  _cl  The new targetClass value
    */
    void setTargetClass(Class _cl);

    /**
    *  Gets the targetClass attribute of the EANMeta object
    *
    *@return    The targetClass value
    */
    Class getTargetClass();

}
