// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import COM.ibm.eannounce.hula.*;
/**********************************************************************************
* used by periodic abr for CATNAV
*
*/
// XMLMQChanges.java,v
// Revision 1.4  2008/05/27 14:28:58  wendy
// Clean up RSA warnings
//
// Revision 1.3  2008/05/03 23:29:32  wendy
// Changed to support generation of large XML files
//
// Revision 1.2  2008/04/30 12:09:18  wendy
// Allow access to getAffectedRoots() from derived class
//
// Revision 1.1  2008/04/29 14:30:47  wendy
// Init for
//  -   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//  -   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//  -   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//  -   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//  -   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI
//
//
public abstract class XMLMQChanges extends XMLMQRoot
{
    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		String ADSTYPE = PokUtils.getAttributeFlagValue(rootEntity, "ADSTYPE");

		// look at ADSTYPE
		String etype = "";
		if (ADSTYPE != null){
			etype = (String)ADSABRSTATUS.ADSTYPES_TBL.get(ADSTYPE);
		}

		// Find all instances of the VE Structure that changed between T1 and T2, and then
		// create XML for each instance where the structure has changed or an attribute of
		// interest has changed.
		Vector rootIds = getAffectedRoots(abr,getVeName(), profileT2, etype,
			profileT1.getValOn(), profileT2.getValOn());

		if (rootIds.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND",etype);
		}else{
			// get all of this at once to check pdhdomain
			EntityItem eiArray[] = new EntityItem[rootIds.size()];
			for (int i=0; i<rootIds.size(); i++){
				eiArray[i] = new EntityItem(null, profileT2, etype,
					Integer.parseInt((String)rootIds.elementAt(i)));
			}

			// pull just roots to check pdhdomain before doing anything else
			EntityList rootlist = abr.getDB().getEntityList(profileT2,
					new ExtractActionItem(null, abr.getDB(), profileT2,"dummy"),eiArray);

			EntityGroup eg = rootlist.getParentEntityGroup();
			for (int i=0; i<eg.getEntityItemCount(); i++){
				EntityItem item = eg.getEntityItem(i);
				abr.addDebug("XMLMQChanges checking root "+item.getKey());
				if (abr.domainNeedsChecks(item)){
					super.processThis(abr, profileT1, profileT2, item);
				}else{
					abr.addXMLGenMsg("DOMAIN_NOT_LISTED",item.getKey());
				}
			}

			// release memory
			rootlist.dereference();
			for (int i=0; i<eiArray.length; i++){
				eiArray[i] = null;
			}
			eiArray = null;
			rootIds.clear();
		}
    }

    /**************************************************************************************
    * find out what has changed in this interval - used for periodic abrs
    * @param extractName String
    * @param rootType String
    * @param fromTime String with dts of time 1
    * @param toTime String with dts of time 2
    * @return Vector roots that had changes
    */
    protected Vector getAffectedRoots(ADSABRSTATUS abr,String extractName, Profile profile,
    	String rootType, String fromTime, String toTime) throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        ExtractActionItem eai = new ExtractActionItem(null, abr.getDB(), profile, extractName);
        eDoc edoc = null;
        Vector rootChgVct = new Vector();
        Vector deletedVct = new Vector();  // root and structure will have indicators

        ReturnDataResultSet rdrs = null;

        abr.addDebug("XMLMQChanges.getAffectedRoots() Running edoc with root: "+rootType+" extract: "+extractName+" fromTime "+fromTime+" toTime "+toTime);
        edoc = new eDoc(abr.getDB(),profile, eai,rootType,fromTime, toTime);

        //keep track of what changed
        rdrs = edoc.getTransactions();
        for (int r = 0; r < rdrs.getRowCount(); r++)
        {
            ReturnDataRow row = rdrs.getRow(r);
            StringBuffer sb = new StringBuffer();
            String rootChgId = null;
            String rootChgType=null;
            boolean rootOff = false;
            String childType = null;
            String childId = null;
            boolean childOff = false;
            // col 0 is the 'general'area' value.. which will always be 000000
            for (int c=1; c<row.getColumnCount(); c++) {
                sb.append("["+r+"]["+c+"] "+rdrs.getColumn(r,c)+" ");
            }
            abr.addDebug(sb.toString());

/* PRODSTRUCT 83 created and deleted in same interval
[155][1] FEATURE [155][2] 27 [155][3] OFF [155][4] MODEL [155][5] 5 [155][6] OFF [155][7] 0 [155][8] E [155][9] PRODSTRUCT [155][10] NOOP [155][11] 0
[156][1] FEATURE [156][2] 27 [156][3] OFF [156][4] PRODSTRUCT [156][5] 83 [156][6] OFF [156][7] 0 [156][8] R [156][9] PRODSTRUCT [156][10] MODEL [156][11] 5
*/
            rootChgType = rdrs.getColumn(r,1);
            if (!rootChgType.equals(rootType)){  // edoc is returning relator instead of root in col 1.. skip it
                abr.addDebug(rootChgType+rdrs.getColumn(r,2)+" was not expected root "+rootType+", skipping it");
                continue;
            }

            rootChgId = rdrs.getColumn(r,2);
            rootOff = rdrs.getColumn(r,3).equals("OFF");
           	childType = rdrs.getColumn(r,4);
           	childId = rdrs.getColumn(r,5); // may also be root, just thing that changed
           	childOff = rdrs.getColumn(r,6).equals("OFF");
           	if (deletedVct.contains(rootChgId) ||  // deleted root will be returned before the deleted relators
				((rootOff || childOff) && rootChgType.equals(rootType) && childType.equals(rootType))){
                abr.addDebug(rootChgType+rootChgId+" was deleted, skipping it");

				if (!deletedVct.contains(rootChgId)) {
					deletedVct.addElement(rootChgId);
				}
                continue;
			}
            // really only care which roots were impacted for VE, hang onto ids and pull all at current time
            if (!rootChgVct.contains(rootChgId)) {
                rootChgVct.addElement(rootChgId);
            }
        }
        abr.addDebug("XMLMQChanges.getAffectedRoots() root id size: "+rootChgVct.size()+" vct: "+rootChgVct);

		deletedVct.clear();
        return rootChgVct;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()    {
        return "1.4";
    }
}
