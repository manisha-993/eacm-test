/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentGroup extends CatItem {

	private SyncMapCollection m_smc = null;
	private WorldWideComponentCollection m_wwcc = null;

	public static final int WWCOMPONENT_REFERENCE = 0;

    private String ENTERPRISE           		= null;
    private String COUNTRYCODE          		= null;
    private String LANGUAGECODE         		= null;
    private int NLSID                		= 0;

    private int QTYLOWLIMT           		= 0;
    private int QTYUPPRLIMT          		= 0;
    private String CGTYPE               		= null;
    private String CGTYPE_FC            		= null;

    private String MULTIVALSELECTTBLFLG 		= null;
    private String PRCFLG               		= null;
    private String REQFLG               		= null;
    private String SELECTBLFLG         			= null;

    private String MULTIVALSELECTTBLFLG_FC 		= null;
    private String PRCFLG_FC               		= null;
    private String REQFLG_FC               		= null;
    private String SELECTBLFLG_FC         		= null;

    private String STATUS               		= null;
    private String STATUS_FC            		= null;
    private String VALFROM              		= null;
    private String VALTO                		= null;

    private HashMap m_AttCollection             = new HashMap();

    /**
     * ComponentGroup
     * @param _cid
     */
    public ComponentGroup(CatId _cid) {
        super(_cid);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
    }

    public void get(Catalog _cat) {
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();
		Profile prof = _cat.getCatalogProfile();
		ComponentGroupId cmpgrpid = getComponentGroupId();
		GeneralAreaMapItem gami = cmpgrpid.getGami();
		ComponentGroupCollectionId cmpgrpcid = cmpgrpid.getComponentGroupCollectionId();
		String strEnterprise = gami.getEnterprise();
		String strEntityType = cmpgrpid.getEntityType();
		int iEntityID = cmpgrpid.getEntityID();
		int iNLSID = gami.getNLSID();
		CatalogInterval cati = cmpgrpcid.getInterval();

		// O.K.. lets see what we got!

		if (cmpgrpcid.isByInterval() && cmpgrpcid.isFromPDH()) {

			// Right now .. we are gong to have to rebuild this guy.. and
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
                D.ebug(this, D.EBUG_DETAIL, "gettingAttributes:"+cmpgrpid.getEntityType()+":"+cmpgrpid.getEntityID());

				EntityItem eiRoot = Catalog.getEntityItem(_cat, cmpgrpid.getEntityType(), cmpgrpid.getEntityID());

				this.setAttributes((eiRoot));

            } catch (Exception xe)  {
			} finally {
				//TODO
			}

		} else {

			try {
				rs = db.callGBL4020(new ReturnStatus(-1), strEnterprise, strEntityType, iEntityID, iNLSID);

				if (rs.next()) {

					int i = 1;
                    setCOUNTRYCODE(rs.getString(i++));
                    setLANGUAGECODE(rs.getString(i++));
                    setQTYLOWLIMT(rs.getInt(i++));
                    setQTYUPPRLIMT(rs.getInt(i++));
                    setCGTYPE(rs.getString(i++));
                    setCGTYPE_FC(rs.getString(i++));
                    setMULTIVALSELECTTBLFLG(rs.getString(i++));
                    setPRCFLG(rs.getString(i++));
                    setREQFLG(rs.getString(i++));
                    setSELECTBLFLG(rs.getString(i++));
                    setMULTIVALSELECTTBLFLG_FC(rs.getString(i++));
                    setPRCFLG_FC(rs.getString(i++));
                    setREQFLG_FC(rs.getString(i++));
                    setSELECTBLFLG_FC(rs.getString(i++));
                    setSTATUS(rs.getString(i++));
                    setSTATUS_FC(rs.getString(i++));
                    setVALFROM(rs.getString(i++));
                    setVALTO(rs.getString(i++));
                    setActive(rs.getInt(i++) == 1);

				}

			} catch (SQLException _ex) {
				_ex.printStackTrace();

			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					db.commit();
					db.freeStatement();
					db.isPending();
				} catch (SQLException _ex) {
					_ex.printStackTrace();
				}
			}
		}
    }

    public void getReferences(Catalog _cat, int _icase) {
        // To do

		ComponentGroupCollectionId cgcid = this.getComponentGroupId().getComponentGroupCollectionId();

		switch (_icase) {

		case WWCOMPONENT_REFERENCE :
			WorldWideComponentCollectionId wwccid = new WorldWideComponentCollectionId(this.getComponentGroupId(), cgcid.getSource(), cgcid.getType(), cgcid.getInterval());
			setWorldWideComponentCollection(new WorldWideComponentCollection(wwccid));
			//
			// Lets share the SMC stuff
			//
			if (this.hasSyncMapCollection()) {
				D.ebug(this, D.EBUG_DETAIL, "getReferences(WWCOMPONENT_REFERENCE) - setting WWCOMPONENT 's SMC");
				getWorldWideComponentCollection().setSmc(this.getSmc());
			}

			D.ebug(this, D.EBUG_DETAIL, "getReferences(WWCOMPONENT_REFERENCE) - lets go stub out the WWCOMPONENT" + wwccid);
			getWorldWideComponentCollection().get(_cat);

			setDeep(true);

			break;

		default :

			break;
		}
    }

    public String toString() {
        return this.getCOMPENTITYTYPE()+":"+this.getCOMPENTITYID();
    }

    public String dump(boolean _b) {
        return "TBD";
    }

    public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
            D.ebug(D.EBUG_SPEW, this.getClass().getName() + " >>> Catalog.isDryRun() == true!!! Skipping put()! <<<");
			return;
		}
		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		ComponentGroupId cmpgrid = this.getComponentGroupId();
		GeneralAreaMapItem gami = cmpgrid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		String strCountryList = gami.getCountryList();
		int iNLSID = gami.getNLSID();
		try {
			db.callGBL8410(
				rets,
				strEnterprise,
				strCountryCode,
				strLanguageCode,
				iNLSID,
                this.getCOMPENTITYTYPE(),
				this.getCOMPENTITYID(),
			    this.getQTYLOWLIMT(),
			    this.getQTYUPPRLIMT(),
                (this.getCGTYPE() == null ? "" : this.getCGTYPE()),
                (this.getCGTYPE_FC() == null ? "" : this.getCGTYPE_FC()    ),
			    this.getMULTIVALSELECTTBLFLG(),
			    this.getPRCFLG(),
			    this.getREQFLG(),
			    this.getSELECTBLFLG(),
			    this.getMULTIVALSELECTTBLFLG_FC(),
			    this.getPRCFLG_FC(),
			    this.getREQFLG_FC(),
			    this.getSELECTBLFLG_FC(),
                (this.getSTATUS() == null ? "" : this.getSTATUS()    ),
                (this.getSTATUS_FC() == null ? "" : this.getSTATUS_FC()    ),
				(this.isActive() ? 1 : 0));

			if (_bcommit) {
				db.commit();
			}
			db.freeStatement();
			db.isPending();

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void deactivate(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
            D.ebug(D.EBUG_SPEW, this.getClass().getName() + " >>> Catalog.isDryRun() == true!!! Skipping put()! <<<");
			return;
		}
		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		ComponentGroupId cmpgrid = this.getComponentGroupId();
		GeneralAreaMapItem gami = cmpgrid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		String strCountryList = gami.getCountryList();
		int iNLSID = gami.getNLSID();
		try {
			db.callGBL8410(
				rets,
				strEnterprise,
				strCountryCode,
				strLanguageCode,
				iNLSID,
                this.getCOMPENTITYTYPE(),
				this.getCOMPENTITYID(),
			    this.getQTYLOWLIMT(),
			    this.getQTYUPPRLIMT(),
                (this.getCGTYPE() == null ? "" : this.getCGTYPE()),
                (this.getCGTYPE_FC() == null ? "" : this.getCGTYPE_FC()    ),
			    this.getMULTIVALSELECTTBLFLG(),
			    this.getPRCFLG(),
			    this.getREQFLG(),
			    this.getSELECTBLFLG(),
			    this.getMULTIVALSELECTTBLFLG_FC(),
			    this.getPRCFLG_FC(),
			    this.getREQFLG_FC(),
			    this.getSELECTBLFLG_FC(),
                (this.getSTATUS() == null ? "" : this.getSTATUS()    ),
                (this.getSTATUS_FC() == null ? "" : this.getSTATUS_FC()    ),
				0);

			if (_bcommit) {
				db.commit();
			}
			db.freeStatement();
			db.isPending();

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    public void setAttribute(String _strTag, Object _oAtt) {
        return;
    }

    public void setAttributes(EntityItem _ei) {
		String strTraceBase = "ComponentGroup setAttributes method";
		D.ebug(D.EBUG_SPEW, strTraceBase + " ei: " + _ei.dump(false));

		//QTYLOWLIMT
        EANTextAttribute tQTYLOWLIMT = (EANTextAttribute) _ei.getAttribute("QTYLOWLIMT");
        if (tQTYLOWLIMT!=null)  {
			this.setQTYLOWLIMT((Integer.parseInt(tQTYLOWLIMT.toString())));
        }

        //QTYUPPRLIMT
        EANTextAttribute tQTYUPPRLIMT = (EANTextAttribute) _ei.getAttribute("QTYUPPRLIMT");
        if (tQTYUPPRLIMT!=null)  {
			this.setQTYUPPRLIMT((Integer.parseInt(tQTYUPPRLIMT.toString())));
        }

		//, CGTYPE, CGTYPE_FC

        EANFlagAttribute faCGTYPE = (EANFlagAttribute) _ei.getAttribute("CGTYPE");
        if (faCGTYPE != null) {
			this.setCGTYPE(faCGTYPE.toString());
            this.setCGTYPE_FC(faCGTYPE.getFirstActiveFlagCode());
        }

		//MULTIVALSELECTTBLFLG,  MULTIVALSELECTTBLFLG_FC
        EANFlagAttribute faMULTIVALSELECTBL = (EANFlagAttribute) _ei.getAttribute("MULTIVALSELECTBL");
        if (faCGTYPE != null) {
			this.setMULTIVALSELECTTBLFLG(faMULTIVALSELECTBL.toString());
            this.setMULTIVALSELECTTBLFLG_FC(faMULTIVALSELECTBL.getFirstActiveFlagCode());
        }

		// PRCFLG, PRCFLG_FC
        EANFlagAttribute faPRCFLG = (EANFlagAttribute) _ei.getAttribute("PRCFLG");
        if (faPRCFLG != null) {
			this.setPRCFLG(faPRCFLG.toString());
            this.setPRCFLG_FC(faPRCFLG.getFirstActiveFlagCode());
        }

        //REQFLG, REQFLG_FC
        EANFlagAttribute faREQFLG = (EANFlagAttribute) _ei.getAttribute("REQFLG");
        if (faREQFLG != null) {
			this.setREQFLG(faREQFLG.toString());
            this.setREQFLG_FC(faREQFLG.getFirstActiveFlagCode());
        }

        // SELECTBLFLG, SELECTBLFLG_FC
        EANFlagAttribute faSELECTBLFLG = (EANFlagAttribute) _ei.getAttribute("SELECTBLFLG");
        if (faSELECTBLFLG != null) {
			this.setSELECTBLFLG(faSELECTBLFLG.toString());
            this.setSELECTBLFLG_FC(faSELECTBLFLG.getFirstActiveFlagCode());
        }

		// STATUS, STATUS_FC
        EANFlagAttribute faSTATUS = (EANFlagAttribute) _ei.getAttribute("STATUS");
        if (faSTATUS != null) {
			this.setSTATUS(faSTATUS.toString());
            this.setSTATUS_FC(faSTATUS.getFirstActiveFlagCode());
        }
    }

    public Object getAttribute(String _strTag) {
		if (m_AttCollection.containsKey(_strTag)) {
			return m_AttCollection.get(_strTag);
		} else {
			System.out.println("attribute not found for " + _strTag);
		}
		return null;
    }

	/**
	 * get attribute keys
	 * 20050808
	 * @return keys[]
	 * @author tony
	 */
    public String[] getAttributeKeys() {
		if (m_AttCollection != null) {
			Set keys = m_AttCollection.keySet();
			if (keys != null) {
				return (String[]) keys.toArray(new String[m_AttCollection.size()]);
			}
		}
		return null;
	}


    /**
     * @return
     */
    public String getCGTYPE() {
        if (CGTYPE == null) {
            return "";
        }

      return CGTYPE;
    }

    /**
     * @return
     */
    public String getCGTYPE_FC() {
        if (CGTYPE_FC == null) {
            return "";
        }

      return CGTYPE_FC;
    }

    /**
     * @return
     */
    public int getCOMPENTITYID() {
      return getComponentGroupId().getEntityID();
    }

    /**
     * @return
     */
    public String getCOMPENTITYTYPE() {
      return getComponentGroupId().getEntityType();
    }

    /**
     * @return
     */
    public String getCOUNTRYCODE() {
      return COUNTRYCODE;
    }

    /**
     * @return
     */
    public String getENTERPRISE() {
      return ENTERPRISE;
    }

    /**
     * @return
     */
    public String getLANGUAGECODE() {
      return LANGUAGECODE;
    }

  /**
   * @return
   */
  public SyncMapCollection getSmc() {
    return m_smc;
  }


    /**
     * @return
     */
    public String getMULTIVALSELECTTBLFLG() {
        if (MULTIVALSELECTTBLFLG == null) {
            return "";
        }
      return MULTIVALSELECTTBLFLG;
    }

    /**
     * @return
     */
    public String getMULTIVALSELECTTBLFLG_FC() {
        if (MULTIVALSELECTTBLFLG_FC == null) {
            return "";
        }

      return MULTIVALSELECTTBLFLG_FC;
    }

    /**
     * @return
     */
    public int getNLSID() {
      return NLSID;
    }

    /**
     * @return
     */
    public String getPRCFLG() {
        if (PRCFLG == null) {
            return "";
        }

      return PRCFLG;
    }

    /**
     * @return
     */
    public String getPRCFLG_FC() {
        if (PRCFLG_FC == null) {
            return "";
        }

      return PRCFLG_FC;
    }

    /**
     * @return
     */
    public int getQTYLOWLIMT() {
      return QTYLOWLIMT;
    }

    /**
     * @return
     */
    public int getQTYUPPRLIMT() {
      return QTYUPPRLIMT;
    }

    /**
     * @return
     */
    public String getREQFLG() {
        if (REQFLG == null) {
            return "";
        }

      return REQFLG;
    }

    /**
     * @return
     */
    public String getREQFLG_FC() {
        if (REQFLG_FC == null) {
            return "";
        }

      return REQFLG_FC;
    }

    /**
     * @return
     */
    public String getSELECTBLFLG() {
        if (SELECTBLFLG == null) {
            return "";
        }

      return SELECTBLFLG;
    }

    /**
     * @return
     */
    public String getSELECTBLFLG_FC() {
        if (SELECTBLFLG_FC == null) {
            return "";
        }

      return SELECTBLFLG_FC;
    }

    /**
     * @return
     */
    public String getSTATUS() {
        if (STATUS == null) {
            return "";
        }

      return STATUS;
    }

    /**
     * @return
     */
    public String getSTATUS_FC() {
        if (STATUS_FC == null) {
            return "";
        }

      return STATUS_FC;
    }

    /**
     * @return
     */
    public String getVALFROM() {
      return VALFROM;
    }

    /**
     * @return
     */
    public String getVALTO() {
      return VALTO;
    }

    /**
     * @param string
     */
    public void setCGTYPE(String string) {
      CGTYPE = string;
      m_AttCollection.put("CGTYPE", string);
    }

    /**
     * @param string
     */
    public void setCGTYPE_FC(String string) {
      CGTYPE_FC = string;
    }

    /**
     * @param string
     */
    public void setCOUNTRYCODE(String string) {
      COUNTRYCODE = string;
    }

    /**
     * @param string
     */
    public void setENTERPRISE(String string) {
      ENTERPRISE = string;
    }

    /**
     * @param string
     */
    public void setLANGUAGECODE(String string) {
      LANGUAGECODE = string;
    }

  /**
   * @param collection
   */
  public void setSmc(SyncMapCollection collection) {
    m_smc = collection;
  }

    /**
     * @param i
     */
    public void setMULTIVALSELECTTBLFLG(String _s) {
      MULTIVALSELECTTBLFLG = _s;
    }

    /**
     * @param i
     */
    public void setMULTIVALSELECTTBLFLG_FC(String _s) {
      MULTIVALSELECTTBLFLG_FC = _s;
    }

    /**
     * @param i
     */
    public void setNLSID(int i) {
      NLSID = i;
    }

    /**
     * @param i
     */
    public void setPRCFLG(String _s) {
      PRCFLG = _s;
      m_AttCollection.put("PRCFLG",_s);
    }

    /**
     * @param i
     */
    public void setPRCFLG_FC(String _s) {
      PRCFLG_FC = _s;
    }

    /**
     * @param i
     */
    public void setQTYLOWLIMT(int i) {
      QTYLOWLIMT = i;
      m_AttCollection.put("QTYLOWLIMT", new Integer(i));
    }

    /**
     * @param i
     */
    public void setQTYUPPRLIMT(int i) {
      QTYUPPRLIMT = i;
       m_AttCollection.put("QTYUPPRLIMT", new Integer(i));
    }

    /**
     * @param i
     */
    public void setREQFLG(String _s) {
      REQFLG = _s;
      m_AttCollection.put("REQFLG", _s);
    }

    /**
     * @param i
     */
    public void setREQFLG_FC(String _s) {
      REQFLG_FC = _s;
    }

    /**
     * @param i
     */
    public void setSELECTBLFLG_FC(String _s) {
      SELECTBLFLG_FC = _s;
      m_AttCollection.put("SELECTBLFLG", _s);
    }

    /**
     * @param i
     */
    public void setSELECTBLFLG(String _s) {
      SELECTBLFLG = _s;
    }

    /**
     * @param string
     */
    public void setSTATUS(String string) {
      STATUS = string;
       m_AttCollection.put("STATUS", string);
    }

    /**
     * @param string
     */
    public void setSTATUS_FC(String string) {
      STATUS_FC = string;
    }

    /**
     * @param string
     */
    public void setVALFROM(String string) {
      VALFROM = string;
    }

    /**
     * @param string
     */
    public void setVALTO(String string) {
      VALTO = string;
    }
	public ComponentGroupId getComponentGroupId() {
		return (ComponentGroupId) getId();
	}

	public int getComponentGroupEntityID() {
		return getComponentGroupId().getEntityID();
	}

	/**
	 * getFeatEntityType
	 *
	 * @return
	 */
	public String getComponentGroupEntityType() {
		return getComponentGroupId().getEntityType();
	}
   public void generateXMLFragment(XMLWriter _xml) {
       try {
        _xml.writeEntity("COMPONENTGROUP");
        this.xmlCOMPENTITYTYPE(_xml);
        this.xmlCOMPENTITYID(_xml);
        this.xmlQTYLOWLIMT(_xml);
        this.xmlQTYUPPRLIMT(_xml);
        this.xmlCGTYPE(_xml);
        this.xmlCGTYPE_FC(_xml);
        this.xmlMULTIVALSELECTTBLFLG(_xml);
        this.xmlPRCFLG(_xml);
        this.xmlREQFLG(_xml);
        this.xmlSELECTBLFLG(_xml);
        this.xmlMULTIVALSELECTTBLFLG_FC(_xml);
        this.xmlPRCFLG_FC(_xml);
        this.xmlREQFLG_FC(_xml);
        this.xmlSELECTBLFLG_FC(_xml);
        this.xmlSTATUS(_xml);
        this.xmlSTATUS_FC(_xml);
        this.xmlVALFROM(_xml);
        this.xmlVALTO(_xml);

        _xml.endEntity();
       } catch (Exception xe)   {
           xe.printStackTrace();
       }

   }

    public void xmlCOMPENTITYTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("COMPENTITYTYPE");
        _xml.write(getCOMPENTITYTYPE());
        _xml.endEntity();
    }
    public void xmlCOMPENTITYID(XMLWriter _xml) throws Exception {
        _xml.writeEntity("COMPENTITYID");
        _xml.write(String.valueOf(getCOMPENTITYID()));
        _xml.endEntity();
    }

    public void xmlQTYLOWLIMT(XMLWriter _xml) throws Exception {
        _xml.writeEntity("QTYLOWLIMT");
        _xml.write(String.valueOf(getQTYLOWLIMT()));
        _xml.endEntity();
    }
    public void xmlQTYUPPRLIMT(XMLWriter _xml) throws Exception {
        _xml.writeEntity("QTYUPPRLIMT");
        _xml.write(String.valueOf(getQTYUPPRLIMT()));
        _xml.endEntity();
    }
    public void xmlCGTYPE(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CGTYPE");
        _xml.write(getCGTYPE());
        _xml.endEntity();
    }
    public void xmlCGTYPE_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("CGTYPE_FC");
        _xml.write(getCGTYPE_FC());
        _xml.endEntity();
    }
    public void xmlMULTIVALSELECTTBLFLG(XMLWriter _xml) throws Exception {
        _xml.writeEntity("MULTIVALSELECTTBLFLG");
        _xml.write(String.valueOf(getMULTIVALSELECTTBLFLG()));
        _xml.endEntity();
    }
    public void xmlPRCFLG(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRCFLG");
        _xml.write(String.valueOf(getPRCFLG()));
        _xml.endEntity();
    }
    public void xmlREQFLG(XMLWriter _xml) throws Exception {
        _xml.writeEntity("REQFLG");
        _xml.write(String.valueOf(getREQFLG()));
        _xml.endEntity();
    }
    public void xmlSELECTBLFLG(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SELECTBLFLG");
        _xml.write(String.valueOf(getSELECTBLFLG()));
        _xml.endEntity();
    }
    public void xmlMULTIVALSELECTTBLFLG_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("MULTIVALSELECTTBLFLG_FC");
        _xml.write(String.valueOf(getMULTIVALSELECTTBLFLG_FC()));
        _xml.endEntity();
    }
    public void xmlPRCFLG_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("PRCFLG_FC");
        _xml.write(String.valueOf(getPRCFLG_FC()));
        _xml.endEntity();
    }
    public void xmlREQFLG_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("REQFLG_FC");
        _xml.write(String.valueOf(getREQFLG_FC()));
        _xml.endEntity();
    }
    public void xmlSELECTBLFLG_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("SELECTBLFLG");
        _xml.write(String.valueOf(getSELECTBLFLG_FC()));
        _xml.endEntity();

    }

    public void xmlSTATUS(XMLWriter _xml) throws Exception {
        _xml.writeEntity("STATUS");
        _xml.write(getSTATUS());
        _xml.endEntity();
    }
    public void xmlSTATUS_FC(XMLWriter _xml) throws Exception {
        _xml.writeEntity("STATUS_FC");
        _xml.write(getSTATUS_FC());
        _xml.endEntity();
    }
    public void xmlVALFROM(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALFROM");
        _xml.write(getVALFROM());
        _xml.endEntity();
    }
    public void xmlVALTO(XMLWriter _xml) throws Exception {
        _xml.writeEntity("VALTO");
        _xml.write(getVALTO());
        _xml.endEntity();
    }

	/**
     * setWorldWideComponentCollection
     *
     * @param _wwcc
     */
    protected void setWorldWideComponentCollection(WorldWideComponentCollection _wwcc) {
		m_wwcc = _wwcc;
	}

	/**
     * getWorldWideComponentCollection
     *
     * @return
     */
    public WorldWideComponentCollection getWorldWideComponentCollection() {
		return m_wwcc;
	}

	/**
	 * hasSyncMapCollection
	 *
	 * @return
	 */
	public final boolean hasSyncMapCollection() {
		return m_smc != null;
	}

}

/*

 * $Log: ComponentGroup.java,v $
 * Revision 1.2  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:12  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.30  2005/11/29 19:19:48  dave
 * formatting
 *
 * Revision 1.29  2005/11/23 19:26:04  joan
 * fixes
 *
 * Revision 1.28  2005/11/22 16:57:36  joan
 * work on idl
 *
 * Revision 1.27  2005/11/21 23:46:13  joan
 * fixes
 *
 * Revision 1.26  2005/10/04 23:24:16  joan
 * work on component
 *
 * Revision 1.25  2005/10/04 20:35:30  joan
 * fixes
 *
 * Revision 1.24  2005/10/03 22:08:33  joan
 * work on component
 *
 * Revision 1.23  2005/10/03 17:51:10  joan
 * fixes
 *
 * Revision 1.22  2005/10/03 17:40:18  joan
 * fixes
 *
 * Revision 1.21  2005/08/30 22:04:26  dave
 * ok .. we try this
 *
 * Revision 1.20  2005/08/30 21:11:37  joan
 * remove comments from cvs
 *
 * Revision 1.19  2005/08/30 21:00:54  joan
 * remove comments made by cvs to compile
 *
 * Revision 1.18  2005/08/30 17:39:13  dave
 * new cat comments
 *
 * Revision 1.14  2005/07/14 17:34:22  bala
 * fixes
 *
 * Revision 1.13  2005/07/13 20:18:44  bala
 * xml plug in
 *
 * Revision 1.12  2005/07/12 21:35:20  bala
 * sync up
 *
 * $Log: ComponentGroup.java,v $
 * Revision 1.2  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:12  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.30  2005/11/29 19:19:48  dave
 * formatting
 *
 * Revision 1.29  2005/11/23 19:26:04  joan
 * fixes
 *
 * Revision 1.28  2005/11/22 16:57:36  joan
 * work on idl
 *
 * Revision 1.27  2005/11/21 23:46:13  joan
 * fixes
 *
 * Revision 1.26  2005/10/04 23:24:16  joan
 * work on component
 *
 * Revision 1.25  2005/10/04 20:35:30  joan
 * fixes
 *
 * Revision 1.24  2005/10/03 22:08:33  joan
 * work on component
 *
 * Revision 1.23  2005/10/03 17:51:10  joan
 * fixes
 *
 * Revision 1.22  2005/10/03 17:40:18  joan
 * fixes
 *
 * Revision 1.21  2005/08/30 22:04:26  dave
 * ok .. we try this
 *
 * Revision 1.20  2005/08/30 21:11:37  joan
 * remove comments from cvs
 *
 * Revision 1.19  2005/08/30 21:00:54  joan
 * remove comments made by cvs to compile
 *
 * Revision 1.18  2005/08/30 17:39:13  dave
 * new cat comments
 *
 * Revision 1.17  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.16  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.15  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.14  2005/07/14 17:34:22  bala
 * fixes
 *
 * Revision 1.13  2005/07/13 20:18:44  bala
 * xml plug in
 *
 * Revision 1.12  2005/07/12 21:35:20  bala
 * sync up
 *
>>>>>>> 1.17
 */
