/*
 * Created on Mar 21, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import COM.ibm.eannounce.objects.EANDataFoundation;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.T;

public class FeatureDetailCollection extends CatList {
    private static final String FOREVER = "9999-12-31-00.00.00.000000";
    private static final String EPOCH = "1980-01-01-00.00.00.000000";
	private FeatureDetailCollectionId m_fdcid = null;
	private SyncMapCollection m_smc = null;

    public static Hashtable c_hsh1 = new Hashtable();
    public static boolean haveItemHit(String _strItemEntityType) {
        return c_hsh1.containsKey(_strItemEntityType);
    }

    static {

        //
        // These represent all the feature/item combonations we need to
        // look for
        //

        c_hsh1.put("PORT", "");
        c_hsh1.put("CCIN", "");

        c_hsh1.put("AUD", "");
        c_hsh1.put("CABLE", "");
        c_hsh1.put("CNTRYPACK", "");
        c_hsh1.put("ENVIRNMTLINFO", "");
        c_hsh1.put("FAXMODM", "");
        c_hsh1.put("GRPHADAPTER", "");
        c_hsh1.put("HDC", "");
        c_hsh1.put("HDD", "");
        c_hsh1.put("INPUTDVC", "");
        c_hsh1.put("KEYBD", "");
        c_hsh1.put("LANGPACK", "");

        c_hsh1.put("MECHPKG", "");
        c_hsh1.put("MECHRACKCAB", "");
        c_hsh1.put("MEMORY", "");
        c_hsh1.put("MEMORYCARD", "");
        c_hsh1.put("MONITOR", "");
        c_hsh1.put("NIC", "");
        c_hsh1.put("OPTCALDVC", "");

        c_hsh1.put("PLANAR", "");
        c_hsh1.put("PRN", "");
        c_hsh1.put("PROC", "");
        c_hsh1.put("PWRSUPPLY", "");

        c_hsh1.put("REMOVBLSTOR", "");
        c_hsh1.put("SPEAKER", "");

        c_hsh1.put("SYSMGADAPTR", "");
        c_hsh1.put("TAPEDRIVE", "");
        c_hsh1.put("TECHINFO", "");
        c_hsh1.put("TECHINFOFEAT", "");
        c_hsh1.put("WLESSNC", "");
        c_hsh1.put("SYSMGADPTR", "");
    }

    public FeatureDetailCollection(FeatureDetailCollectionId _fdcid) {
		super(_fdcid);
		m_fdcid = _fdcid;
    }

    public FeatureDetailCollection(Catalog _cat, FeatureDetailCollectionId _fdcid) {
		super(_fdcid);
		m_fdcid = _fdcid;
		get(_cat);
    }

    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append(m_fdcid.toString());

	    Iterator it = values().iterator();

		while (it.hasNext()) {
		    FeatureDetail fd = (FeatureDetail)it.next();
			sb.append(fd.dump(_brief));
        }


        return sb.toString();
    }

    public String toString() {
        return "";
    }

    public boolean hasItemEntityType(String _strEntityType) {
	    Iterator it = values().iterator();
		while (it.hasNext()) {
		    FeatureDetail fd = (FeatureDetail)it.next();
		    System.out.println("hasItemEntityType check for:" + fd.getItemEntityType());
		    if(fd.getItemEntityType().equals(_strEntityType)) {
				return true;
			}
        }
        return false;
	}

	public void getReferences(Catalog _cat, int _icase) {
		// TODO Auto-generated method stub
	}
	public void get(Catalog _cat) {
        ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();

	    try {
			if (m_fdcid.isFromCAT()) {
				ReturnStatus returnStatus = new ReturnStatus(-1);
				ReturnDataResultSet rdrs;
				GeneralAreaMapItem gami= getId().getGami();

				int iNLSID = gami.getNLSID();
				String strEnterprise = gami.getEnterprise();
				FeatureId fid = m_fdcid.getFeatureId();
				String strFeatEntityType = fid.getFeatEntityType();
				int iFeatEntityID = fid.getFeatEntityID();
				//String strItemEntityType = fid.getItemEntityType();
				//int iItemEntityID = fid.getItemEntityID();
				FeatureDetailCollectionId fdcid = (FeatureDetailCollectionId) getId();

				//rs = db.callGBL9303(returnStatus, strEnterprise,iNLSID, strFeatEntityType, iFeatEntityID, strItemEntityType, iItemEntityID);
				rs = db.callGBL9303(returnStatus, strEnterprise,iNLSID, strFeatEntityType, iFeatEntityID);

				rdrs = new ReturnDataResultSet(rs);

				rs.close();

				db.commit();
				db.freeStatement();
				db.isPending();

				for (int ii = 0; ii < rdrs.size(); ii++) {
					String strItemEntityType = rdrs.getColumn(ii, 2).trim();
					int iItemEntityID = rdrs.getColumnInt(ii, 3);
					String strAttCode = rdrs.getColumn(ii, 4).trim();
					db.debug(D.EBUG_SPEW, "gbl9303 answers is:" + strFeatEntityType + ":" + iFeatEntityID + ":" + strItemEntityType + ":" + iItemEntityID + ":" + strAttCode);
					FeatureDetailId fdid = new FeatureDetailId(strFeatEntityType, iFeatEntityID, strItemEntityType, iItemEntityID, getId().getGami(), fdcid);
					put(new FeatureDetail(fdid));
				}
			} else if (m_fdcid.isByInterval() && m_fdcid.isFromPDH()) {
                this.processSyncMap(_cat);
            }
		} catch (SQLException _ex) {
			_ex.printStackTrace();
		} catch (MiddlewareException e) {
		 	e.printStackTrace();

		} finally {
			try {
				db.commit();
				db.freeStatement();
				db.isPending();
			} catch (SQLException _ex) {
				_ex.printStackTrace();
			}
		}
	}


    public static void main(String[] args) {
    }

	public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			return;
		}
	}


	public void merge(CatItem _ci) {
		// TODO Auto-generated method stub

	}

    /**
      * This sig says my collection is being driven by another SyncMapCollection..
      * I am not the root.  So I will not have a SyncMap of myown
      * The passed CatId is the controlling parent here.  This eventually can be a
      * private thing.. called by a constructor!
      *
      * road
      * @param _cat
      */
    public void processSyncMap(Catalog _cat) {
        D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - In FeatureDetailCollection");

        FeatureDetailCollectionId fdcid = (FeatureDetailCollectionId) this.getId();
        GeneralAreaMapItem gami = fdcid.getGami();
        SyncMapCollection smc = this.getSmc();

		FeatureId fid = fdcid.getFeatureId();
		String strFeatEntityType = fid.getFeatEntityType();
		int iFeatEntityID = fid.getFeatEntityID();

        for (int x = 0; x < smc.getCount(); x++) {
            SyncMapItem smi = smc.get(x);
            /* RQK - 08092007 - I am adding a check for childtran ON before processing this SMI.
             * This solves a problem when there are multiple feature - same item records in the SMC, 
             * because the same element has been linked, unlinked and relinked to the feature. 
             * It is not necessary to process SMIs which have childtran off since all featuredetail records
             * have already been deactivated.
             */

            if (haveItemHit(smi.getChildType()) && smi.getChildLevel() == 0 && smi.getChildTran().equals("ON")) {
                D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - smi is: " + smi.toString());

                FeatureDetailId fdid = new FeatureDetailId(strFeatEntityType, iFeatEntityID, smi.getChildType(), smi.getChildID(), gami, fdcid);
                FeatureDetail fd = (FeatureDetail) this.get(fdid);
                if (fd == null) {
                    fd = new FeatureDetail(fdid);
                    D.ebug(this, D.EBUG_DETAIL, "processSyncMap() - making new FeatureDetail");
                    fd.setSmc(new SyncMapCollection());
                    this.put(fd);
                 }
				fd.setActive(smi.getChildTran().equals("ON"));
				fd.getSmc().add(smi);
            }
        }
    }

	/**
     * getSmc
     *
     * @return
     */
    public SyncMapCollection getSmc() {
		return m_smc;
	}

	/**
	 * setSmc
	 *
	 * @param collection
	 */
	public void setSmc(SyncMapCollection collection) {
		m_smc = collection;
	}

	public FeatureDetailCollectionId getFeatureDetailCollectionId() {
		return (FeatureDetailCollectionId) getId();
	}
    /**
       * First attempt at an XML writer usage - This sends the output to
       * Standard out for now.
       */
    public void generateXML() {

        Iterator it = null;

        XMLWriter xml = new XMLWriter();
        FeatureDetailCollectionId fcid = this.getFeatureDetailCollectionId();
        CatalogInterval cati = fcid.getInterval();
        String strFromTimestamp = (fcid.isByInterval() ? cati.getStartDate() : EPOCH);
        String strToTimestamp = (fcid.isByInterval() ? cati.getEndDate() : FOREVER);

        int iNumberOfElements = this.size();

        try {

            xml.writeEntity("FeatureDetailFile");

            xml.writeEntity("FromTimestamp");
            xml.write(strFromTimestamp);
            xml.endEntity();

            xml.writeEntity("ToTimestamp");
            xml.write(strToTimestamp);
            xml.endEntity();

            xml.writeEntity("NumberOfElements");
            xml.write(iNumberOfElements + "");
            xml.endEntity();

            /*
             * For now .. we just have the update command..
             */
            xml.writeEntity("Command");
            xml.write("update");
            xml.endEntity();

            xml.writeEntity("Source");
            xml.write("STG");
            xml.endEntity();

            it = values().iterator();

            while (it.hasNext()) {
                FeatureDetail fd = (FeatureDetail) it.next();
                fd.generateXMLFragment(xml);
            }

            xml.endEntity();

            xml.finishEntity();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
