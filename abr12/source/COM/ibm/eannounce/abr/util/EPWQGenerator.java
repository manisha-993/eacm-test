// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.util;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.sg.*;

/**********************************************************************************
 * This class writes creates an EPWAITINGQUEUE entity based on specified criteria
 *
 */
// $Log: EPWQGenerator.java,v $
// Revision 1.3  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/11/28 22:59:17  wendy
// merged epims with wwprt in base class
//
// Revision 1.1  2007/11/16 19:27:16  nancy
// Init for GX EPIMs ABRs
//
//
public class EPWQGenerator
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2007  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.3 $";

    private EPIMSWWPRTBASE epimsAbr;
    private static final String CREATEACTION_NAME = "CREPWAITINGQUEUE";
    private static final String EPWQ_TYPE = "EPWAITINGQUEUE";

    public static final String EPFEATURECODE = "EPFEATURECODE";
    public static final String EPMACHTYPE = "EPMACHTYPE";
    public static final String EPMODELATR = "EPMODELATR";
    public static final String EPONEID = "EPONEID";
    public static final String EPONET = "EPONET";
    public static final String EPSALESORG = "EPSALESORG";
    public static final String EPSTATUS = "EPSTATUS";
    public static final String EPWAITEID = "EPWAITEID";
    public static final String EPWAITET = "EPWAITET";

    private Hashtable updateTbl = new Hashtable();
    private CreateActionItem cai = null;
    private EntityItem[] wgArray = new EntityItem[1];

/* EPWAITINGQUEUE  entity
		EPFEATURECODE	T	Feature code
		EPMACHTYPE		T	Machine Type
		EPMODELATR		T	Model
		EPONEID			T	Waiting On Entity Id
		EPONET			T	Waiting On Entity Type
		EPSALESORG		T	Sales Org
		EPSTATUS		T	ePIMs Status
		EPWAITEID		T	Waiting Entity ID
		EPWAITET		T	Waiting Entity Type
*/

    /********************************************************************************
    * Constructor
    * @param epims EPIMSWWPRTBASE object
    */
    public EPWQGenerator(EPIMSWWPRTBASE epims)
    {
        epimsAbr = epims;
    }

    /********************************************************************************
    * Add value to be used for the specified attribute
    * @param attrcode String
    * @param value String
    */
    public void addAttribute(String attrcode, String value)
    {
        updateTbl.put(attrcode, value);
    }

    /********************************************************************************
    * clear all values
    */
    public void reset()
    {
        updateTbl.clear();
    }

	private void init() throws
		java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		if (cai == null){
	        Profile profile = epimsAbr.getProfile();
			cai = new CreateActionItem(null, epimsAbr.getDB(), profile, CREATEACTION_NAME);
			wgArray[0] = new EntityItem(null, profile, "WG", profile.getWGID());
		}
	}

    /********************************************************************************
    * Create EPWAITINGQUEUE entity and set attributes
    */
    public void createEntity() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.rmi.RemoteException
    {
        Profile profile = epimsAbr.getProfile();

		init();
        // create the entity
        EntityList list = new EntityList(epimsAbr.getDB(), profile, cai,wgArray); // parent needed even though no relator

        EntityGroup eGrp = list.getEntityGroup(EPWQ_TYPE);
        if (eGrp.getEntityItemCount() == 1)
        {
			StringBuffer sb = new StringBuffer();
            // write the attributes
            EntityItem queueItem = eGrp.getEntityItem(0);

            // these are all Text attributes
			for (Enumeration e = updateTbl.keys(); e.hasMoreElements();)
			{
				String code = (String)e.nextElement();
				String value = (String)updateTbl.get(code);
				setText(queueItem,code, value);
				sb.append(code+":"+value+" ");
			}

            // must commit new entity to the PDH, this is a standalone entity, so relator not needed
            queueItem.commit(epimsAbr.getDB(), null);

            epimsAbr.addDebug("EPWQGenerator.createEntity() created Entity: "+queueItem.getKey()+
                " for "+sb);
        }
        else
        {
            epimsAbr.getDB().debug(D.EBUG_ERR,"EPWQGenerator.createEntity() ERROR: Can not create "+EPWQ_TYPE+" entity");
            epimsAbr.addError("EPWQGenerator.createEntity() ERROR: Can not create "+EPWQ_TYPE+" entity");
        }
        list.dereference();
    }

    /********************************************************************************
    * Create unique flag attribute and set it
    * @param queueItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    * @param flagCode String with value
    *
    private void setUniqueFlag(EntityItem queueItem,String attrCode, String flagCode) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EANFlagAttribute flagAttr = (EANFlagAttribute)createAttr(queueItem,attrCode);

        if (flagAttr !=null)
        {
            MetaFlag[] mfa = (MetaFlag[]) flagAttr.get();
            for (int i = 0; i < mfa.length; i++)
            {
                if (mfa[i].getFlagCode().equals(flagCode))
                {
                    mfa[i].setSelected(true);
                    flagAttr.put(mfa);
                    break;
                }
            }
        }
    }*/

    /********************************************************************************
    * Create text attribute and set it
    * @param queueItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    * @param value String with value
    */
    private void setText(EntityItem queueItem,String attrCode, String value)   throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        EANAttribute textAttr = createAttr(queueItem,attrCode);
        if (textAttr != null)
        {
            textAttr.put(value);
        }
    }

    /********************************************************************************
    * Create specified attribute
    * @param queueItem EntityItem to add attribute to
    * @param attrCode String with attribute code
    */
    private EANAttribute createAttr(EntityItem queueItem, String attrCode) throws
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        EANAttribute attr =null;
        EntityGroup eGrp = queueItem.getEntityGroup();
        EANMetaAttribute ma = eGrp.getMetaAttribute(attrCode);
        if (ma==null)
        {
            epimsAbr.addError("EPWQGenerator.createAttr() MetaAttribute cannot be found to Create "+EPWQ_TYPE+"."+attrCode);
            epimsAbr.getDB().debug(D.EBUG_ERR,"EPWQGenerator.createAttr() MetaAttribute cannot be found to Create "+EPWQ_TYPE+"."+attrCode);
        }
        else {
            switch (ma.getAttributeType().charAt(0))
            {
            case 'T':
                {
                    TextAttribute ta = new TextAttribute(queueItem, null, (MetaTextAttribute) ma);
                    queueItem.putAttribute(ta);
                    attr=ta;
                    break;
                }
            case 'U':
                {
                    SingleFlagAttribute sfa = new SingleFlagAttribute(queueItem, null, (MetaSingleFlagAttribute) ma);
                    queueItem.putAttribute(sfa);
                    attr=sfa;
                    break;
                }
            case 'B':
                {
                    BlobAttribute ba = new BlobAttribute(queueItem, null, (MetaBlobAttribute) ma);
                    queueItem.putAttribute(ba);
                    attr=ba;
                    break;
                }
            default:
                epimsAbr.addError("EPWQGenerator.createAttr() MetaAttribute Type="+ma.getAttributeType()+" is not supported yet "+EPWQ_TYPE+"."+attrCode);
                // could not get anything
                break;
            }
        }
        return attr;
    }
}
