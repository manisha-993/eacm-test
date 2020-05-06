// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//[]--------------------------------------------------------------------------[]
//|    Application Name: e-announce                                            |
//|           File Name: SWEntityItem_OIM.java                                 |
//|----------------------------------------------------------------------------|
//|          Programmer: Anhtuan Nguyen                                        |
//|        Date written: September 18, 2004                                    |
//|         Environment: Operating System: Windows 2000                        |
//|                              Compiler: IBM JDK 1.4                         |
//|----------------------------------------------------------------------------|
//|  Module Description: SW Extract for Q352 and Q523                          |
//|----------------------------------------------------------------------------|
//|        Restrictions: None                                                  |
//|        Dependencies: None                                                  |
//|  NLS Considerations: None                                                  |
//|----------------------------------------------------------------------------|
//|      Change History:                                                       |
//|      Date            Programmer      Description/Comments                  |
//[]--------------------------------------------------------------------------[]
// $Log: SWEntityItem_OIM.java,v $
// Revision 1.2  2006/01/26 15:36:26  anhtuan
// AHE copyright.
//
// Revision 1.1  2005/09/14 03:55:11  anhtuan
// Init OIM3.0b
//
// Revision 1.1  2004/09/30 14:22:56  anhtuan
// Initial version.
//
//


package com.ibm.transform.oim.eacm.util;

import COM.ibm.eannounce.objects.*;

/**********************************************************************************
* SWEntityItem_OIM class
*
*
*/
public class SWEntityItem_OIM
{
    private EntityItem modelSWAppBase;
    private EntityItem modelSWOptFeaBase;
    private EntityItem swProdStruct;
    private EntityItem fromSWProdStruct;

    /***********************************************
    * Constructor
    *
    * @param modelSWAppBaseEI EntityItem
    * @param modelSWOptFeaBaseEI EntityItem
    * @param swProdStructEI EntityItem
    * @param fromSWProdStructEI EntityItem
    */
    public SWEntityItem_OIM(EntityItem modelSWAppBaseEI, EntityItem modelSWOptFeaBaseEI, EntityItem swProdStructEI, EntityItem fromSWProdStructEI)
    {
        modelSWAppBase = modelSWAppBaseEI;
        modelSWOptFeaBase = modelSWOptFeaBaseEI;
        swProdStruct = swProdStructEI;
        fromSWProdStruct = fromSWProdStructEI;
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this modelSWAppBase
    *
    * @param ei EntityItem
    * @return boolean
    */
    public boolean containModelBase(EntityItem ei)
    {
        String key = modelSWAppBase.getKey();

        return key.equals(ei.getKey());
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this modelSWAppBase
    *
    * @param aKey String
    * @return boolean
    */
    public boolean containModelBase(String aKey)
    {
        String key = modelSWAppBase.getKey();

        return key.equals(aKey);
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this modelSWOptFeaBase
    *
    * @param ei EntityItem
    * @return boolean
    */
    public boolean containModelOptFeaBase(EntityItem ei)
    {
        String key = modelSWOptFeaBase.getKey();

        return key.equals(ei.getKey());
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this modelSWOptFeaBase
    *
    * @param aKey String
    * @return boolean
    */
    public boolean containModelOptFeaBase(String aKey)
    {
        String key = modelSWOptFeaBase.getKey();

        return key.equals(aKey);
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this swProdStruct
    *
    * @param ei EntityItem
    * @return boolean
    */
    public boolean containSWProdStruct(EntityItem ei)
    {
        String key = swProdStruct.getKey();

        return key.equals(ei.getKey());
    }

    /***********************************************
    * Check if SWEtityItem_OIM class has this fromSWProdStruct
    *
    * @param ei EntityItem
    * @return boolean
    */
    public boolean containFromSWProdStruct(EntityItem ei)
    {
        String key = fromSWProdStruct.getKey();

        return key.equals(ei.getKey());
    }

    /***********************************************
    * Return swProdstruct
    *
    * @return EntityItem
    */
    public EntityItem getSWProdStruct()
    {
        return swProdStruct;
    }

    /***********************************************
    * Return modelSWOptFeaBase
    *
    * @return EntityItem
    */
    public EntityItem getOptFeaBaseModel()
    {
        return modelSWOptFeaBase;
    }

    /***********************************************
    * return fromSWProdStruct
    *
    * @return EntityItem
    */
    public EntityItem getFromSWProdStruct()
    {
        return fromSWProdStruct;
    }
}
