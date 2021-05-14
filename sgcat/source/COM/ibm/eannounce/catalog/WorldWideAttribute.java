/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.StringTokenizer;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MultiFlagAttribute;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideAttribute extends CatList {

    private SyncMapCollection m_smc = null;
    public WorldWideAttribute(WorldWideAttributeId _cid) {
        super(_cid);
    }

    public void get(Catalog _cat) {

        //
        // When we are getting something out of the CatDB
        // We need to pull enterprise out of the ProductID.

        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        Database db = _cat.getCatalogDatabase();
        Profile prof = _cat.getCatalogProfile();

        WorldWideAttributeId wwaid = (WorldWideAttributeId) getId();
        WorldWideAttributeCollectionId wwacid = (WorldWideAttributeCollectionId) wwaid.getCollectionId();

        if (wwacid == null) {
            D.ebug(this, D.EBUG_ERR, "get(): ** ERROR *** wwacid is null!!");
            return;
        }

        GeneralAreaMapItem gami = wwaid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strWWEntityType = this.getWorldWideAttributeId().getWWEntityType();
        int iWWEntityID = this.getWorldWideAttributeId().getWWEntityID();
        String strAttEntityType = this.getWorldWideAttributeId().getAttEntityType();
        int iAttEntityID = this.getWorldWideAttributeId().getAttEntityID();
        int iNLSID = gami.getNLSID();

        D.ebug(
            this,
            D.EBUG_DETAIL,
            "get() - here is wwacid..."
                + ":isByInteval:"
                + wwacid.isByInterval()
                + ":isFromPDH: "
                + wwacid.isFromPDH()
                + ":isFullImages:"
                + wwacid.isFullImages());

        try {
            if (wwacid.isByInterval() && wwacid.isFromPDH()) {
                CatalogInterval cati = wwacid.getInterval();
                //
                // o.k. we have a world wide Product that made us.
                //
                if (wwacid.hasWorldWideProductId()) {

                    //	Right now .. we are gong to have to rebuild this guy.. and
                    // any other thing it may be resonsible for
                    // at this point.. we will always need to

                    if (this.getSmc() == null) {
                        System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                        return;
                    }

                    //
                    // Lets set the valon's in the profile for the Catalog to
                    // I am not sure i like setting them here.
                    //

                    prof.setEffOn(cati.getEndDate());
                    prof.setValOn(cati.getEndDate());

                    try {

                        //
                        // Not sure we need to reget Model or WWSEO here
                        // DWB its important to cache these extracts
                        // so we can simply reuse in the memory
                        //

                        EntityItem ei = Catalog.getEntityItem(_cat, wwaid.getAttEntityType(), wwaid.getAttEntityID());

                        this.setAttributes(ei, gami);

                    } finally {
                    }
                }
            } else if (wwacid.isFromCAT()) {
                try {
                    rs =
                        db.callGBL4012(
                            new ReturnStatus(-1),
                            strEnterprise,
                            strWWEntityType,
                            iWWEntityID,
                            strAttEntityType,
                            iAttEntityID,
                            iNLSID);
                    rdrs = new ReturnDataResultSet(rs);
                } finally {
                    rs.close();
                    db.commit();
                    db.freeStatement();
                    db.isPending();

                }

                //
                // Ok.. lets build them up
                //
                String strPriorAttCode = null;
                String strRunningAttValue = null;
                String strMaxValFrom = null;
                String strPriorFlagCode = null;
                String strPriorAttType = null;
                int iPriorActive = 0;

                for (int i = 0; i < rdrs.size(); i++) {

                    int c = 0;
                    String strAttCode = rdrs.getColumn(i, c++);
                    String strAttType = rdrs.getColumn(i, c++);
                    String strFlagCode = rdrs.getColumn(i, c++);
                    String strAttValue = rdrs.getColumn(i, c++);
                    String strValFrom = rdrs.getColumnDate(i, c++);
                    int iActive = rdrs.getColumnInt(i, c++);
                    D.ebug(this,D.EBUG_SPEW,"resultset:strAttCode:"+strAttCode+":strAttType:"+strAttType+":strFlagCode:"+strFlagCode+":strAttValue:"+strAttValue+":strValFrom:"+strValFrom+":iActive:"+iActive);

                    if (strPriorAttCode == null) {
                        strPriorAttCode = strAttCode;
                        strPriorAttType = strAttType;
                        strRunningAttValue = strAttValue;
                        strMaxValFrom = strValFrom;
                        strPriorFlagCode = strFlagCode;
                        iPriorActive = iActive;
                    } else if (strPriorAttCode.equals(strAttCode)) {

                        //
                        //  This is the case where we keep a max of the ValFrom
                        //  and a concatenated description going.
                        //
                        strRunningAttValue = strRunningAttValue + ";" + strAttValue;
                        if (strValFrom.compareTo(strMaxValFrom) > 0) {
                            strMaxValFrom = strValFrom;
                        }

                    } else {
                        AttributeFragmentId afid = new AttributeFragmentId(this.getWorldWideAttributeId(), strAttCode, gami);
                        AttributeFragment af = new AttributeFragment(afid);
                        if (strPriorAttType.equals("M")) {
                            af.setFlagCode("Multi");
                        } else {
                            af.setFlagCode(strPriorFlagCode);
                        }
                        af.setAttributeType(strPriorAttType);
                        af.setAttributeValue(strRunningAttValue);
                        af.setValFrom(strMaxValFrom);
                        af.setActive(iPriorActive == 1);

                        super.put(af);

                        // set current to prior...
                        strPriorAttCode = strAttCode;
                        strMaxValFrom = strValFrom;
                        strRunningAttValue = strAttValue;
                        strPriorFlagCode = strFlagCode;
                        strPriorAttType = strAttType;

                    }

                }

                //
                //  Don't forget the last one!
                //
D.ebug(this,D.EBUG_SPEW,"LAST:"+this.getWorldWideAttributeId()+":strPriorAttCode:"+strPriorAttCode+":strPriorFlagCode:"+strPriorFlagCode+":strPriorAttType:"+strPriorAttType
                                             +":strPriorAttType:"+strPriorAttType+":strRunningAttValue:"+strRunningAttValue+":strMaxValFrom:"+strMaxValFrom);
                AttributeFragmentId afid = new AttributeFragmentId(this.getWorldWideAttributeId(), strPriorAttCode, gami);
                AttributeFragment af = new AttributeFragment(afid);
                if (strPriorAttType.equals("M")) {
                    af.setFlagCode("Multi");
                } else {
                    af.setFlagCode(strPriorFlagCode);
                }
                af.setAttributeType(strPriorAttType);
                af.setAttributeValue(strRunningAttValue);
                af.setValFrom(strMaxValFrom);
                af.setActive(iPriorActive == 1);

                super.put(af);
                setHeavy(true);

            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (MiddlewareException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }

    }

    public String toString() {
        // TODO Auto-generated method stub
        return this.getWorldWideAttributeId().toString();
    }

    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer("\n");
        Iterator it = values().iterator();
        while (it.hasNext()) {
            AttributeFragment af = (AttributeFragment) it.next();
            sb.append(af.dump(_brief));
        }
        return sb.toString();
    }

    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub
        // There are no references to get in this object.
    }

    public static void main(String[] args) {
    }

    public final WorldWideAttributeId getWorldWideAttributeId() {
        return (WorldWideAttributeId) this.getId();
    }

    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			return;
		}

		//
		//  For each attribute .. we have to get each fragement and post it!
		//
		//  TODO We will need to be wiping out the rows on a refresh type
		//  transaction right here prior to processing the fragements.
		//
		//  we can also delete the ones that were never touched after this has been
		//  completed
		//

		Iterator it = this.values().iterator();

		while (it.hasNext()) {
			AttributeFragment af = (AttributeFragment)it.next();
			af.put(_cat,_bcommit);
		}

    }

    /**
     * getSmc
     * @return
     */
    public SyncMapCollection getSmc() {
        return m_smc;
    }

    /**
     * setSmc
     * @param collection
     */
    public void setSmc(SyncMapCollection collection) {
        m_smc = collection;
    }

    public final boolean hasSyncMapCollection() {
        return m_smc != null;
    }

    /**
     * this guy is  trickier than the normal setAttributes
     * but not really.. each Attribute is a new attributefragement.
     *
     * This may be the way we want to model all attribute for an item
     * in the future.
     *
     * @param _ei
     */
    public void setAttributes(EntityItem _ei, GeneralAreaMapItem _gami) {

        //
        // For each attribute in the _ei.. we make a framagne
        // mulit value flags get rolled up
        //


        for (int i = 0; i < _ei.getAttributeCount(); i++) {
            EANAttribute att = _ei.getAttribute(i);
             AttributeFragmentId afid = new AttributeFragmentId(this.getWorldWideAttributeId(),att.getAttributeCode(), _gami);
            AttributeFragment af = new AttributeFragment(afid);

            if (att instanceof EANFlagAttribute) {
                af.setAttributeType("F");
                if (att instanceof MultiFlagAttribute) {
                    af.setFlagCode("Multi");
                } else {
                    af.setFlagCode(((EANFlagAttribute) att).getFirstActiveFlagCode());
                }
            } else {
                af.setAttributeType("T");
            }
            //
            // Some fun with String Tokenizer
            //
			StringTokenizer st = new StringTokenizer(att.toString(),"\n");
			StringBuffer sb = new StringBuffer();
			while (st.hasMoreTokens()) {
				String str = st.nextToken();
				str = str.replace('*', ' ').trim();
				sb.append(str);
				sb.append(";");
			}
			if(sb.length() > 0) {
			    sb.deleteCharAt(sb.length()-1);
			}
            af.setAttributeValue(sb.toString());
            af.setValFrom(att.getValFrom());
            af.setActive(true);
            this.put(af);
        }

    }

    /**
     * This generates an XMLFragment per the XML Interface
     *
     * Over the time, we can make new getXXXXX's and pass the
     * xml object to them and they will generate their own fragements
     * @param _xml
     *
     * we will need a units of measure gluer!!!
     */
    public void generateXMLFragment(XMLWriter _xml) {

        WorldWideAttributeId wwaid = (WorldWideAttributeId) this.getId();

        //
        // O.K.  did we come from a WW Product?
        //
        try {

            Iterator it = this.values().iterator();

            while (it.hasNext()) {
                AttributeFragment af = (AttributeFragment) it.next();
                _xml.writeEntity("ATTRVALUE");
                _xml.writeEntity("PARENTID");
                _xml.write(wwaid.getWWEntityType() + wwaid.getWWEntityID());
                _xml.endEntity();
                _xml.writeEntity("ATTRTOKEN");
                _xml.write(af.getAttributeCode());
				_xml.endEntity();
				_xml.writeEntity("ATTRVAL");
				_xml.write(af.getAttributeValue());
				_xml.endEntity();
				_xml.writeEntity("DETAILSUOM");
				_xml.write(af.getUnitOfMeasure());
				_xml.endEntity();

				//
				// This should be relator ID between prodstruct and this guy to guarantee
				// uniqueness
				//
				_xml.writeEntity("ATTRVALTOK");
				_xml.write("1");
				_xml.endEntity();

				//
				// This should be relator ID between prodstruct and this guy to guarantee
				// uniqueness
				//
				_xml.writeEntity("VAMSEQ");
				_xml.write("-1");
				_xml.endEntity();
				_xml.endEntity();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
