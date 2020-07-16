/*
 * Created on Jun 8, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import COM.ibm.eannounce.objects.EANBlobAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

/**
 * Image
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Image extends Collateral {

    public static final String IMG_MKTGCOLORIMG = "MKTGCOLORIMG";
    public static final String IMG_IMGDESC = "IMGDESC";
    public static final String IMG_MKTGIMGFILENAM = "MKTGIMGFILENAM";

    private byte[] MKTGCOLORIMG = null;
    private String MKTGIMGFILENAM = null;
    private String IMGDESC = null;
    private HashMap m_AttCollection = new HashMap();

    /**
     * Image
     * @param _cid
     */
    public Image(CatId _cid) {
        super(_cid);
        // TODO Auto-generated constructor stub
    }

    	/**
     *  (non-Javadoc)
     *
     * @param _cat
     * @param _bcommit
     */
    public void put(Catalog _cat, boolean _bcommit) {

        if (Catalog.isDryRun()) {
            return;
        }

        Database db = _cat.getCatalogDatabase();

        CollateralId colid = (CollateralId) this.getId();
        GeneralAreaMapItem gami = colid.getGami();

        String strEnterprise = gami.getEnterprise();
        String strCountryList = gami.getCountryList();
        String strColEntityType = colid.getCollEntityType();
        int iColEntityID = colid.getCollEntityID();

        super.putCommon(_cat, _bcommit);

        D.ebug(
            this,
            D.EBUG_DETAIL,
            "put() - puting an actual image...A"
                + this.isActive()
                + ":"
                + (this.getMKTGCOLORIMG() == null ? "NO IMAGE!" : "" + this.getMKTGIMGFILENAM().length()));

        if (this.isActive() && this.getMKTGCOLORIMG() != null) {

            String strUpdate =
                "update gbli.marketinfo set image = ? where "
                    + " enterprise = '"
                    + strEnterprise
                    + "' and "
                    + " collentitytype = '"
                    + strColEntityType
                    + "' and "
                    + " collentityid = "
                    + iColEntityID
                    + " and "
                    + " countrylist = '"
                    + strCountryList + "'";

            PreparedStatement stmtHandle = null;

            try {
                Connection con = db.getPDHConnection();
                stmtHandle = con.prepareStatement(strUpdate);

                D.ebug(this, D.EBUG_DETAIL, "put() - puting an actual image...B");

                Blobo myBlob = new Blobo(this.toString(), this.getMKTGCOLORIMG());
                InputStream ins = myBlob.openBinaryStream();
                stmtHandle.setBinaryStream(1, ins, myBlob.size());
                stmtHandle.execute();
                stmtHandle.close();
                stmtHandle = null;
                ins.close();
                db.freeStatement();
                db.isPending();

                D.ebug(this, D.EBUG_DETAIL, "put() - puting an actual image...C");

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (MiddlewareException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (_bcommit) {
            try {
                db.commit();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     *  (non-Javadoc)
     *
     * @param _cat
     */
    public void get(Catalog _cat) {

        super.getCommon(_cat);

        Profile prof = _cat.getCatalogProfile();
        CollateralId colid = (CollateralId) this.getId();
        colid.getGami();
        CollateralCollectionId colcid = colid.getCollateralCollectionId();
        CatalogInterval cati = colcid.getInterval();

        try {
            if (colcid.isFromCAT()) {
            } else if (colcid.isFromPDH() && colcid.isByInterval() && colcid.isFullImages()) {

                if (this.getSmc() == null) {
                    System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
                    return;
                }
                prof.setEffOn(cati.getEndDate());
                prof.setValOn(cati.getEndDate());

                EntityItem eiIMG = Catalog.getEntityItem(_cat, colid.getCollEntityType(), colid.getCollEntityID());
                this.setAttributes(_cat, eiIMG);
                //
                // ok.. get it out of the list...
                //
                eiIMG.getEntityGroup().resetEntityItem();
            }

        } finally {
            //TODO
        }

    }

    /**
     *  (non-Javadoc)
     *
     * @param _ci
     */
    public void merge(CatItem _ci) {
        // TODO Auto-generated method stub

    }

    /**
     *  (non-Javadoc)
     *
     * @param _strTag
     * @param _oAtt
     */
    public void setAttribute(String _strTag, Object _oAtt) {
        // TODO Auto-generated method stub

    }

    /**
     *  (non-Javadoc)
     *
     * @return Object
     * @param _strTag
     */
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
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatObject#toString()
     */
    public String toString() {
        return this.getId().toString();
    }

    /**
     *  (non-Javadoc)
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * setAttributes
     *
     * @param _cat
     * @param _ei
     */
    public final void setAttributes(Catalog _cat, EntityItem _ei) {

        D.ebug(D.EBUG_DETAIL, this.getClass().getName() + " .getAttributes(): in IMG");

        EANBlobAttribute bMKTGCOLORIMG = (EANBlobAttribute) _ei.getAttribute(Image.IMG_MKTGCOLORIMG);
        EANTextAttribute tIMG_IMGDESC = (EANTextAttribute) _ei.getAttribute(Image.IMG_IMGDESC);
        EANTextAttribute tIMG_MKTGIMGFILENAM = (EANTextAttribute) _ei.getAttribute(Image.IMG_MKTGIMGFILENAM);

        if (bMKTGCOLORIMG != null) {
            if (bMKTGCOLORIMG.getBlobValue() == null) {
            	//
            	// always store a 1 for now
                bMKTGCOLORIMG.put("MYBLOB", bMKTGCOLORIMG.getBlobValue(null, _cat.getPDHDatabase()),1);
            }
            this.setMKTGCOLORIMG(bMKTGCOLORIMG.getBlobValue());
            this.setIMGDESC((bMKTGCOLORIMG.getBlobFileName()));
        }

     //   if (tIMG_IMGDESC != null) {
     //       this.setIMGDESC(tIMG_IMGDESC.toString());
     //   }
        if (tIMG_MKTGIMGFILENAM != null) {
            this.setMKTGIMGFILENAM(tIMG_MKTGIMGFILENAM.toString());
        }

    }

    /**
     * getIMGDESC
     *
     * @return
     */
    public String getIMGDESC() {
        return IMGDESC;
    }

    /**
     * getMKTGCOLORIMG
     *
     * @return
     */
    public byte[] getMKTGCOLORIMG() {
        return MKTGCOLORIMG;
    }

    /**
     * getMKTGIMGFILENAM
     *
     * @return
     */
    public String getMKTGIMGFILENAM() {
        return MKTGIMGFILENAM;
    }

    /**
     * setIMGDESC
     *
     * @param string
     */
    public void setIMGDESC(String string) {
        IMGDESC = string;
        m_AttCollection.put(Image.IMG_IMGDESC,string);
    }

    /**
     * setMKTGCOLORIMG
     *
     * @param bs
     */
    public void setMKTGCOLORIMG(byte[] bs) {
        MKTGCOLORIMG = bs;
         m_AttCollection.put(Image.IMG_MKTGCOLORIMG,bs);
    }

    /**
     * setMKTGIMGFILENAM
     *
     * @param string
     */
    public void setMKTGIMGFILENAM(String string) {
        MKTGIMGFILENAM = string;
        m_AttCollection.put(Image.IMG_MKTGIMGFILENAM,string);
    }

}

/*
 * $Log: Image.java,v $
 * Revision 1.2  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:21  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:29  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.32  2005/11/11 20:39:50  dave
 * fixing blob sql
 *
 * Revision 1.31  2005/09/20 04:26:56  dave
 * more cleanup
 *
 * Revision 1.30  2005/08/16 16:52:58  tony
 * CatalogViewer
 *
 * Revision 1.29  2005/08/08 20:47:16  tony
 * Added getAttribute logic
 *
 * Revision 1.28  2005/08/08 18:54:24  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.27  2005/06/22 19:28:20  dave
 * ok.. trying to add countryList to the mix for my tables
 *
 * Revision 1.26  2005/06/17 05:37:10  dave
 * using derived file name for the image
 *
 * Revision 1.25  2005/06/17 05:10:42  dave
 * ok.. we are really close
 *
 * Revision 1.24  2005/06/17 05:01:44  dave
 * more minor adjustments
 *
 * Revision 1.23  2005/06/17 04:45:16  dave
 * closing in on IMG problem
 *
 * Revision 1.22  2005/06/17 04:37:36  dave
 * found the Image / Blob Issue
 *
 * Revision 1.21  2005/06/17 04:15:44  dave
 * lets try this out
 *
 * Revision 1.20  2005/06/17 04:06:26  dave
 * more trace
 *
 * Revision 1.19  2005/06/17 03:49:58  dave
 * ok.. had a hardcoded 13
 *
 * Revision 1.18  2005/06/17 03:46:47  dave
 * getting close
 *
 * Revision 1.17  2005/06/17 03:19:03  dave
 * more null pointer bug fixes
 *
 * Revision 1.16  2005/06/17 02:49:20  dave
 * going for blob
 *
 * Revision 1.15  2005/06/16 17:50:53  dave
 * some fixing for Collateral
 *
 * Revision 1.14  2005/06/13 04:35:33  dave
 * ! needs to be not !
 *
 * Revision 1.13  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.12  2005/06/11 02:12:13  dave
 * finalizing marketing info pass I
 *
 * Revision 1.11  2005/06/08 23:40:16  dave
 * ok honor a final clause
 *
 * Revision 1.10  2005/06/08 23:28:07  dave
 * ok,  more collateral
 *
 * Revision 1.9  2005/06/08 22:27:01  dave
 * ok.. lets keep forging ahead
 *
 * Revision 1.8  2005/06/08 20:31:08  dave
 * more changes to start picking up attributes for collateral
 *
 * Revision 1.7  2005/06/08 13:21:21  dave
 * testing waters to see if structures make sense outside of WWProductId
 *
 * Revision 1.6  2005/05/13 20:39:50  roger
 * Turn on logging in source
 *
 */
