package com.ibm.rdh.chw.entity;


import java.sql.ResultSet;
import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
//import com.ibm.pprds.epimshw.revenue.TypeModelRev;
//import com.ibm.pprds.epimshw.seovalidation.SeoAbnormalValidationException;
import java.util.Enumeration;
import java.util.Vector;


/**
 * Insert the type's description here. Creation date: (4/19/2001 11:36:43 AM)
 * 
 * @author: Lizsolette Williams
 */
public class TypeModel 
{
    private String type;
    private String model;
    private String eanUPCCode;
    private String description;

    private java.util.Date gaDate;

    //private CHWPlant plant;

    private String annDocNo;
    private boolean customerSetup = false;

    private String modificationReason;
    private String functionClass;
    private String rentalPlan;
    private String EMEABrandCode;
    private boolean meterReading = false;
    private String systemAssurance;
    private String prodIdentification;
    private boolean maintEligible = false;
    private String ICRCategory;
    private boolean licenseCode = false;
    private boolean noChargeRental = false;
    private boolean noChargePurchase = false;
    private boolean netPriceMES = false;
    private boolean superComputer = false;

    private boolean ea = false;
    private boolean colour = false;
    private boolean language = false;
    private boolean volumeDiscountElig = false;
    private boolean AASOrderable = false;
    private boolean machineLevelCtrl = false;

    private boolean voltage = false;
    private boolean admin = false;
    private boolean hvStock = false;
    private boolean integratedModel = false;
    private boolean lowEnd = false;
    private boolean midRange = false;
    private boolean IBMCredit = false;
    private boolean itemReturn = false;
    private boolean volumeMaint = false;
    private boolean extendedMaint = false;
    private boolean CPU = false;
    private String productHierarchy;
    private boolean Changed = false;
    private boolean Deleted = false;
    private boolean SharedProduct = false;
    private String SharedProductMaterialType;
    private boolean SharedProductIn0147 = false;



    // CHW4.1 added by Laxmi
    //private TypeModelRev TmRev;

    private java.lang.String warrantyPer;
    private boolean containsMTC = false;
    private Vector Flfil = new Vector();
    private java.util.Date announcementDateForFirstTM;
    private boolean hasTypeModelPromoted = false;
    private boolean hasTypeModelPromotedAnyAnn = false;
    private boolean hasTypeModelDeleted = false;
    private boolean hasTypeModelDeletedAnyAnn = false;
    private String loadingGroup;
    private String systemType;
    // SAP Ledger
    private String profitCenter;
    private String vendorID;

    private String acctAsgnGrp;//RQ0411077139 Changes
    private String div;//RQ0724066720 Changes
    private String segmentAcronym;//RQ0724066720 Changes
   //gx1 code drop A
    private String sapAssortMod;
    private String lastSapAssortMod;
    private String matrlGrp1;
    private String matrlGrp3;
    private String systemDesc;

    /**
     * Returns the loadingGroup from all type model geos with precedence US
     * first and then first non-blank geo
     * 
     * @return
     */
    public String getLoadingGroup()
    {
        return loadingGroup;
    }

    /**
     * @param loadingGroup
     *            The loadingGroup to set.
     */
    public void setLoadingGroup(String loadingGroup)
    {
        this.loadingGroup = loadingGroup;
    }

    /**
     * @return Returns the systemType.
     */
    public String getSystemType()
    {
        return systemType;
    }

    /**
     * @param systemType
     *            The systemType to set.
     */
    public void setSystemType(String systemType)
    {
        this.systemType = systemType;
    }

    /**
     * TypeModel constructor comment.
     * 
     * @throws HWPIMSAbnormalException
     */
    public TypeModel() throws HWPIMSAbnormalException
    {
        super();

    }

    /**
     * Insert the method's description here. Creation date: (4/30/2001 4:22:50
     * PM)
     * 
     * @param type
     *            java.lang.String
     * @param model
     *            java.lang.String
     */
    public TypeModel(String go, String typ, String modl, String anndocno,
            boolean mmlcPromoteSelect) throws HWPIMSAbnormalException
    {

        annDocNo = anndocno;
  
    }

    /**
     * Create a Type Model from a ResultSet row
     */
    public TypeModel(ResultSet rs, boolean retrieveTmgCollection)
            throws HWPIMSAbnormalException
    {
        this();
       // populate(rs, retrieveTmgCollection);
    }

    /*
     * public TypeModel(ResultSet rs, boolean retrieveTmsorgCollection) throws
     * HWPIMSAbnormalException//Code Drop A { this(); populate(rs,
     * retrieveTmsorgCollection); }
     */

//    /**
//     * Create a Type Model from data retrieved from the data base.
//     */
//    public static TypeModel getObject(String type, String model,
//            boolean retrieveTmgCollection) throws HWPIMSAbnormalException
//    /*
//     * public static TypeModel getObject(String type, String model, boolean
//     * retrieveTmsorgCollection) throws HWPIMSAbnormalException //Code Drop A
//     */
//    {
//        TypeModel obj = null;
//        String schema = ConfigManager.getConfigManager().getString(
//                PropertyKeys.KEY_SCHEMA);
//        Connection db2con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        String sql = "SELECT * FROM " + schema + ".TYPE_MODEL"
//                + " WHERE Type = '" + type + "'" + " and Model ='" + model
//                + "'";
//
//        try
//        {
//            db2con = DBConnectionManager.getInstance().getConnection();
//            stmt = db2con.createStatement();
//            rs = stmt.executeQuery(sql);
//            if (rs.next())
//            {
//                obj = new TypeModel(rs, retrieveTmgCollection);
//                //obj = new TypeModel(rs, retrieveTmsorgCollection);//Code Drop
//                // A
//            }
//        }
//        catch (SQLException e)
//        {
//            String msg = "Unable to create Type Model from Db entry via SQL: "
//                    + sql;
//            HWPIMSLog
//                    .Write(msg + "\n" + ExceptionUtility.getStackTrace(e), "E");
//            throw new HWPIMSAbnormalException(msg, e);
//        }
//        finally
//        {
//            DBConnectionManager.closeRSAndStatement(rs, stmt);
//            DBConnectionManager.closeConnection(db2con);
//        }
//        return obj;
//    }


    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:58:50
     * PM)
     * 
     * @return int
     */
    public boolean getAASOrderable()
    {
        return AASOrderable;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:00:14
     * PM)
     * 
     * @return int
     */
    public boolean getAdmin()
    {
        return admin;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:45:17
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getAnnDocNo()
    {
        return annDocNo;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:31
     * PM)
     * 
     * @return int
     */
    public boolean getColour()
    {
        return colour;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:03:36
     * PM)
     * 
     * @return int
     */
    public boolean getCPU()
    {
        return CPU;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:45:46
     * PM)
     * 
     * @return int
     */
    public boolean getCustomerSetup()
    {
        return customerSetup;
    }

    public boolean getChanged()
    {
        return Changed;
    }

    /*
     * public boolean getSalesOrgChanged() { return SalesOrgChanged; }
     */
    public boolean getDeleted()
    {
        return Deleted;
    }

    public boolean getSharedProduct()
    {
        return SharedProduct;
    }

    public boolean getSharedProductIn0147()
    {
        return SharedProductIn0147;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:38:16
     * AM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getDescription()
    {
        return description;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:10
     * PM)
     * 
     * @return int
     */
    public boolean getEa()
    {
        return ea;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:56
     * AM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getEanUPCCode()
    {
        return eanUPCCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:50:44
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getEMEABrandCode()
    {
        return EMEABrandCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:03:16
     * PM)
     * 
     * @return int
     */
    public boolean getExtendedMaint()
    {
        return extendedMaint;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:47:58
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getFunctionClass()
    {
        return functionClass;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:39:37
     * AM)
     * 
     * @return java.util.Date
     */
    public java.util.Date getGaDate()
    {
        return gaDate;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:39:09
     * AM)
     * 
     * @return java.lang.String
     */
    public boolean getHvStock()
    {
        return hvStock;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:11
     * PM)
     * 
     * @return int
     */
    public boolean getIBMCredit()
    {
        return IBMCredit;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:52:43
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getICRCategory()
    {
        return ICRCategory;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:00:58
     * PM)
     * 
     * @return int
     */
    public boolean getIntegratedModel()
    {
        return integratedModel;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:33
     * PM)
     * 
     * @return int
     */
    public boolean getItemReturn()
    {
        return itemReturn;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:49
     * PM)
     * 
     * @return int
     */
    public boolean getLanguage()
    {
        return language;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:06
     * PM)
     * 
     * @return int
     */
    public boolean getLicenseCode()
    {
        return licenseCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:01:17
     * PM)
     * 
     * @return int
     */
    public boolean getLowEnd()
    {
        return lowEnd;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:59:17
     * PM)
     * 
     * @return int
     */
    public boolean getMachineLevelCtrl()
    {
        return machineLevelCtrl;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:52:14
     * PM)
     * 
     * @return int
     */
    public boolean getMaintEligible()
    {
        return maintEligible;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:02
     * PM)
     * 
     * @return int
     */
    public boolean getMeterReading()
    {
        return meterReading;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:01:37
     * PM)
     * 
     * @return int
     */
    public boolean getMidRange()
    {
        return midRange;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:30
     * AM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getModel()
    {
        return model;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:46:26
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getModificationReason()
    {
        return modificationReason;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:54:37
     * PM)
     * 
     * @return int
     */
    public boolean getNetPriceMES()
    {
        return netPriceMES;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:53
     * PM)
     * 
     * @return int
     */
    public boolean getNoChargePurchase()
    {
        return noChargePurchase;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:31
     * PM)
     * 
     * @return int
     */
    public boolean getNoChargeRental()
    {
        return noChargeRental;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:51
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getProdIdentification()
    {
        return prodIdentification;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:04:06
     * PM)
     * 
     * @return int
     */
    public java.lang.String getProductHierarchy()
    {
        return productHierarchy;
    }

    // SAP Ledger
    public java.lang.String getProfitCenter()
    {
        return profitCenter;
    }

    public java.lang.String getVendorID()
    {
        return vendorID;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:50:19
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getRentalPlan()
    {
        return rentalPlan;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:54:59
     * PM)
     * 
     * @return int
     */
    public boolean getSuperComputer()
    {
        return superComputer;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:25
     * PM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getSystemAssurance()
    {
        return systemAssurance;
    }


    //CHW4.1
//    public TypeModelRev getTmRev()
//    {
//        return TmRev;
//    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:08
     * AM)
     * 
     * @return java.lang.String
     */

    public java.lang.String getType()
    {
        return type;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:59:59
     * PM)
     * 
     * @return int
     */
    public boolean getVoltage()
    {
        return voltage;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:58:26
     * PM)
     * 
     * @return int
     */
    public boolean getVolumeDiscountElig()
    {
        return volumeDiscountElig;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:54
     * PM)
     * 
     * @return int
     */
    public boolean getVolumeMaint()
    {
        return volumeMaint;
    }

    /**
     * Insert the method's description here. Creation date: (5/30/2001 10:08:52
     * AM)
     * 
     * @return java.lang.String
     */
    public java.lang.String getWarrantyPer()
    {
        return warrantyPer;
    }

    public java.lang.String getDiv()
    { //RQ0724066720 Changes
        return div;
    }

    public java.lang.String getSegmentAcronym()
    { //RQ0724066720 Changes
        return segmentAcronym;
    }

    public java.lang.String getAcctAsgnGrp()
    { //RQ0411077139 Changes
        return acctAsgnGrp;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:58:50
     * PM)
     * 
     * @param newAASOrderable
     *            int
     */
    public void setAASOrderable(boolean newAASOrderable)
    {
        AASOrderable = newAASOrderable;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:00:14
     * PM)
     * 
     * @param newAdmin
     *            int
     */
    public void setAdmin(boolean newAdmin)
    {
        admin = newAdmin;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:45:17
     * PM)
     * 
     * @param newAnnDocNo
     *            java.lang.String
     */
    public void setAnnDocNo(java.lang.String newAnnDocNo)
    {
        annDocNo = newAnnDocNo;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:31
     * PM)
     * 
     * @param newColour
     *            int
     */
    public void setColour(boolean newColour)
    {
        colour = newColour;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:03:36
     * PM)
     * 
     * @param newCPU
     *            int
     */
    public void setCPU(boolean newCPU)
    {
        CPU = newCPU;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:45:46
     * PM)
     * 
     * @param newCustomerSetup
     *            int
     */
    public void setCustomerSetup(boolean newCustomerSetup)
    {
        customerSetup = newCustomerSetup;
    }

    public void setChanged(boolean newChanged)
    {
        Changed = newChanged;
    }

    public void setDeleted(boolean newDeleted)
    {
        Deleted = newDeleted;
    }

    public void setSharedProduct(boolean newSharedProduct)
    {
        SharedProduct = newSharedProduct;
    }

    public void setSharedProductMaterailType(
            java.lang.String newSharedProductMaterialType)
    {
        SharedProductMaterialType = newSharedProductMaterialType;
    }

    public void setSharedProductIn0147(boolean newSharedProductIn0147)
    {
        SharedProductIn0147 = newSharedProductIn0147;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:38:16
     * AM)
     * 
     * @param newDescription
     *            java.lang.String
     */
    public void setDescription(java.lang.String newDescription)
    {
        description = newDescription;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:10
     * PM)
     * 
     * @param newEa
     *            int
     */
    public void setEa(boolean newEa)
    {
        ea = newEa;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:56
     * AM)
     * 
     * @param newEanUPCCode
     *            java.lang.String
     */
    public void setEanUPCCode(java.lang.String newEanUPCCode)
    {
        eanUPCCode = newEanUPCCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:50:44
     * PM)
     * 
     * @param newEMEABrandCode
     *            java.lang.String
     */
    public void setEMEABrandCode(java.lang.String newEMEABrandCode)
    {
        EMEABrandCode = newEMEABrandCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:03:16
     * PM)
     * 
     * @param newExtendedMaint
     *            int
     */
    public void setExtendedMaint(boolean newExtendedMaint)
    {
        extendedMaint = newExtendedMaint;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:47:58
     * PM)
     * 
     * @param newFunctionClass
     *            java.lang.String
     */
    public void setFunctionClass(java.lang.String newFunctionClass)
    {
        functionClass = newFunctionClass;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:39:37
     * AM)
     * 
     * @param newGaDate
     *            java.util.Date
     */
    public void setGaDate(java.util.Date newGaDate)
    {
        gaDate = newGaDate;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:39:09
     * AM)
     * 
     * @param newHvStock
     *            java.lang.String
     */
    public void setHvStock(boolean newHvStock)
    {
        hvStock = newHvStock;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:11
     * PM)
     * 
     * @param newIBMCredit
     *            int
     */
    public void setIBMCredit(boolean newIBMCredit)
    {
        IBMCredit = newIBMCredit;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:52:43
     * PM)
     * 
     * @param newICRCategory
     *            java.lang.String
     */
    public void setICRCategory(java.lang.String newICRCategory)
    {
        ICRCategory = newICRCategory;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:00:58
     * PM)
     * 
     * @param newIntegratedModel
     *            int
     */
    public void setIntegratedModel(boolean newIntegratedModel)
    {
        integratedModel = newIntegratedModel;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:33
     * PM)
     * 
     * @param newItemReturn
     *            int
     */
    public void setItemReturn(boolean newItemReturn)
    {
        itemReturn = newItemReturn;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:57:49
     * PM)
     * 
     * @param newLanguage
     *            int
     */
    public void setLanguage(boolean newLanguage)
    {
        language = newLanguage;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:06
     * PM)
     * 
     * @param newLisenceCode
     *            int
     */
    public void setLicenseCode(boolean newLicenseCode)
    {
        licenseCode = newLicenseCode;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:01:17
     * PM)
     * 
     * @param newLowEnd
     *            int
     */
    public void setLowEnd(boolean newLowEnd)
    {
        lowEnd = newLowEnd;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:59:17
     * PM)
     * 
     * @param newMachineLvelCtrl
     *            int
     */
    public void setMachineLevelCtrl(boolean newMachineLevelCtrl)
    {
        machineLevelCtrl = newMachineLevelCtrl;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:52:14
     * PM)
     * 
     * @param newMaintEligible
     *            int
     */
    public void setMaintEligible(boolean newMaintEligible)
    {
        maintEligible = newMaintEligible;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:02
     * PM)
     * 
     * @param newMeterReading
     *            int
     */
    public void setMeterReading(boolean newMeterReading)
    {
        meterReading = newMeterReading;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:01:37
     * PM)
     * 
     * @param newMidRange
     *            int
     */
    public void setMidRange(boolean newMidRange)
    {
        midRange = newMidRange;
    }

    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:30
     * AM)
     * 
     * @param newModel
     *            java.lang.String
     */
    public void setModel(java.lang.String newModel)
    {
        model = newModel;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:46:26
     * PM)
     * 
     * @param newModificationReason
     *            java.lang.String
     */
    public void setModificationReason(java.lang.String newModificationReason)
    {
        modificationReason = newModificationReason;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:54:37
     * PM)
     * 
     * @param newNetPriceMES
     *            int
     */
    public void setNetPriceMES(boolean newNetPriceMES)
    {
        netPriceMES = newNetPriceMES;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:53
     * PM)
     * 
     * @param newNoChargePurchase
     *            int
     */
    public void setNoChargePurchase(boolean newNoChargePurchase)
    {
        noChargePurchase = newNoChargePurchase;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:53:31
     * PM)
     * 
     * @param newNoChargeRental
     *            int
     */
    public void setNoChargeRental(boolean newNoChargeRental)
    {
        noChargeRental = newNoChargeRental;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:51
     * PM)
     * 
     * @param newProdIdentification
     *            java.lang.String
     */
    public void setProdIdentification(java.lang.String newProdIdentification)
    {
        prodIdentification = newProdIdentification;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:04:06
     * PM)
     * 
     * @param newProductHierarchy
     *            int
     */
    public void setProductHierarchy(java.lang.String newProductHierarchy)
    {
        productHierarchy = newProductHierarchy;
    }

    // SAP Ledger
    public void setProfitCenter(java.lang.String newProfitCenter)
    {
        profitCenter = newProfitCenter;
    }

    public void setVendorID(java.lang.String newVendorID)
    {
        vendorID = newVendorID;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:50:19
     * PM)
     * 
     * @param newRentalPlan
     *            java.lang.String
     */
    public void setRentalPlan(java.lang.String newRentalPlan)
    {
        rentalPlan = newRentalPlan;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:54:59
     * PM)
     * 
     * @param newSuperComputer
     *            int
     */
    public void setSuperComputer(boolean newSuperComputer)
    {
        superComputer = newSuperComputer;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:51:25
     * PM)
     * 
     * @param newSystemAssurance
     *            java.lang.String
     */
    public void setSystemAssurance(java.lang.String newSystemAssurance)
    {
        systemAssurance = newSystemAssurance;
    }

  
    /**
     * Insert the method's description here. Creation date: (4/19/2001 11:37:08
     * AM)
     * 
     * @param newType
     *            java.lang.String
     */
    public void setType(java.lang.String newType)
    {
        type = newType;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:59:59
     * PM)
     * 
     * @param newVoltage
     *            int
     */
    public void setVoltage(boolean newVoltage)
    {
        voltage = newVoltage;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 3:58:26
     * PM)
     * 
     * @param newVolumeDiscountElig
     *            int
     */
    public void setVolumeDiscountElig(boolean newVolumeDiscountElig)
    {
        volumeDiscountElig = newVolumeDiscountElig;
    }

    /**
     * Insert the method's description here. Creation date: (4/26/2001 4:02:54
     * PM)
     * 
     * @param newVolumeMaint
     *            int
     */
    public void setVolumeMaint(boolean newVolumeMaint)
    {
        volumeMaint = newVolumeMaint;
    }

    /**
     * Insert the method's description here. Creation date: (5/30/2001 10:08:52
     * AM)
     * 
     * @param newWarrantyCat
     *            java.lang.String
     */
    public void setWarrantyPer(java.lang.String newWarrantyPer)
    {
        warrantyPer = newWarrantyPer;
    }

    public void setAcctAsgnGrp(java.lang.String acctAsgnGrp)
    { //RQ0411077139 Changes
        this.acctAsgnGrp = acctAsgnGrp;
    }

    //RQ0724066720 Changes start
    public void setDiv(java.lang.String div)
    {
        this.div = div;
    }

    public void setSegmentAcronym(java.lang.String segmentAcronym)
    {
        this.segmentAcronym = segmentAcronym;
    }

    //RQ0724066720 Changes end
    /**
     * @return Returns the lastSapAssortMod.
     */
    public String getLastSapAssortMod()
    {
        return lastSapAssortMod;
    }

    /**
     * @param lastSapAssortMod
     *            The lastSapAssortMod to set.
     */
    public void setLastSapAssortMod(String lastSapAssortMod)
    {
        this.lastSapAssortMod = lastSapAssortMod;
    }

    /**
     * @return Returns the matrlGrp1.
     */
    public String getMatrlGrp1()
    {
        return matrlGrp1;
    }

    /**
     * @param matrlGrp1
     *            The matrlGrp1 to set.
     */
    public void setMatrlGrp1(String matrlGrp1)
    {
        this.matrlGrp1 = matrlGrp1;
    }

    /**
     * @return Returns the matrlGrp2.
     */
    public String getMatrlGrp3()
    {
        return matrlGrp3;
    }

    /**
     * @param matrlGrp2 The matrlGrp2 to set.
     */
    public void setMatrlGrp3(String matrlGrp3)
    {
        this.matrlGrp3 = matrlGrp3;
    }

    /**
     * @return Returns the sapAssortMod.
     */
    public String getSapAssortMod()
    {
        return sapAssortMod;
    }

    /**
     * @param sapAssortMod
     *            The sapAssortMod to set.
     */
    public void setSapAssortMod(String sapAssortMod)
    {
        this.sapAssortMod = sapAssortMod;
    }

    /**
     * @return Returns the systemDesc.
     */
    public String getSystemDesc()
    {
        return systemDesc;
    }

    /**
     * @param systemDesc
     *            The systemDesc to set.
     */
    public void setSystemDesc(String systemDesc)
    {
        this.systemDesc = systemDesc;
    }

    /**
     * Returns a String that represents the value of this object.
     * 
     * @return a string representation of the receiver
     */

    public String toString()
    {

        StringBuffer s = new StringBuffer(1000);

        s.append("type >> " + type + "\n");
        s.append("model >> " + model + "\n");
        s.append("eanUPCCode >> " + eanUPCCode + "\n");
        s.append("description >> " + description + "\n");
        s.append("gaDate >> " + gaDate + "\n");
        s.append("annDocNo >> " + annDocNo + "\n");
        s.append("customerSetup >> " + customerSetup + "\n");

        s.append("modificationReason >> " + modificationReason + "\n");
        s.append("functionClass >> " + functionClass + "\n");
        s.append("rentalPlan >> " + rentalPlan + "\n");
        s.append("EMEABrandCode >> " + EMEABrandCode + "\n");
        s.append("meterReading >> " + meterReading + "\n");
        s.append("systemAssurance >> " + systemAssurance + "\n");
        s.append("prodIdentification >> " + prodIdentification + "\n");
        s.append("maintEligible >> " + maintEligible + "\n");
        s.append("ICRCategory >> " + ICRCategory + "\n");
        s.append("licenseCode >> " + licenseCode + "\n");
        s.append("noChargeRental >> " + noChargeRental + "\n");
        s.append("noChargePurchase >> " + noChargePurchase + "\n");
        s.append("netPriceMES >> " + netPriceMES + "\n");
        s.append("superComputer >> " + superComputer + "\n");

        s.append("ea >> " + ea + "\n");
        s.append("language >> " + language + "\n");
        s.append("volumeDiscountElig >> " + volumeDiscountElig + "\n");
        s.append("AASOrderable >> " + AASOrderable + "\n");
        s.append("machineLevelCtrl >> " + machineLevelCtrl + "\n");

        s.append("voltage >> " + voltage + "\n");
        s.append("admin >> " + admin + "\n");
        s.append("hvStock >> " + hvStock + "\n");
        s.append("integratedModel >> " + integratedModel + "\n");
        s.append("lowEnd >> " + lowEnd + "\n");
        s.append("IBMCredit >> " + IBMCredit + "\n");
        s.append("itemReturn >> " + itemReturn + "\n");
        s.append("volumeMaint >> " + volumeMaint + "\n");
        s.append("extendedMaint >> " + extendedMaint + "\n");
        s.append("CPU >> " + CPU + "\n");
        s.append("productHierarchy >> " + productHierarchy + "\n");
        //s.append("descrChanged >> "+ descrChanged+"\n");
        s.append("Changed >> " + Changed + "\n");
        s.append("Deleted >> " + Deleted + "\n");
        s.append("warrantyPeriod >> " + warrantyPer + "\n");
        s.append("AcctAsgnGrp >> " + acctAsgnGrp + "\n");//RQ0411077139 Changes
        // RQ0724066720 Changes
        s.append("div >> " + div + "\n");
        s.append("segmentAcronym >> " + segmentAcronym + "\n");
        //code drop A
        s.append("sapAssortMod >> "+sapAssortMod+"\n");
        s.append("lastSapAssortMod >> "+lastSapAssortMod+"\n");
        s.append("matrlgrp1 >>"+matrlGrp1+"\n");
        s.append("matrlgrp3 >>"+matrlGrp3+"\n");
        s.append("systemDesc >>"+systemDesc+"\n");

        //CHW4.1
        //s.append(tmRev.toString());

        return s.toString();
    }

    /**
     * Gets the containsMTC
     * 
     * @return Returns a boolean
     */
    public boolean getContainsMTC()
    {
        return containsMTC;
    }

    /**
     * Sets the containsMTC
     * 
     * @param containsMTC
     *            The containsMTC to set
     */
    public void setContainsMTC(boolean containsMTC)
    {
        this.containsMTC = containsMTC;
    }

    public boolean gethasTMPromoted()
    {
        return hasTypeModelPromoted;
    }

    /**
     * Sets the hasTypeModelPromoted
     * 
     * @param hasPromoted
     *            The hasTypeModelPromoted to set
     */
    public void sethasTMPromoted(boolean hasPromoted)
    {
        this.hasTypeModelPromoted = hasPromoted;
    }

    public boolean gethasTMPromotedAnyotherAnn()
    {
        return hasTypeModelPromotedAnyAnn;
    }

    /**
     * Sets the hasTypeModelPromotedAnyAnn
     * 
     * @param hasTYpeModelPromotedAnyAnn
     *            The hasTypeModelPromoted to set
     */
    public void sethasTMPromotedAnyotherAnn(boolean hasPromotedAny)
    {
        this.hasTypeModelPromotedAnyAnn = hasPromotedAny;
    }

    public boolean gethasTMDeleted()
    {
        return hasTypeModelDeleted;
    }

    /**
     * Sets the hasTypeModelPromoted
     * 
     * @param hasPromoted
     *            The hasTypeModelPromoted to set
     */
    public void sethasTMDeleted(boolean hasDeleted)
    {
        this.hasTypeModelDeleted = hasDeleted;
    }

    public boolean gethasTMDeletedAnyotherAnn()
    {
        return hasTypeModelDeletedAnyAnn;
    }

    /**
     * Sets the hasTypeModelPromotedAnyAnn
     * 
     * @param hasTYpeModelPromotedAnyAnn
     *            The hasTypeModelPromoted to set
     */
    public void sethasTMDeletedAnyotherAnn(boolean hasDeletedAny)
    {
        this.hasTypeModelDeletedAnyAnn = hasDeletedAny;
    }

    /**
     * Gets the contains Vector containing the flfilpipe cd's
     * 
     * @return Returns a vector
     */
    public Vector getFlfilCol()
    {
        return Flfil;
    }

    /**
     * Sets the containsMTC
     * 
     * @param Vector
     *            containing the flfilpipe cd's to set
     */
    public void setFlfilCol(Vector vecFifl)
    {
        this.Flfil = vecFifl;
    }

  

    public String getFlfilCd(Vector flfilcol) throws HWPIMSAbnormalException
    {
        String flfilcd;
        if ((flfilcol.contains("XCC")) && (flfilcol.contains("ZIP")))
        {
            flfilcd = "BTH";
        }
        else if (flfilcol.contains("XCC"))
        {
            flfilcd = "XCC";
        }
        else if (flfilcol.contains("ZIP"))
        {
            flfilcd = "ZIP";
        }
        else
        {
            flfilcd = "EMPTY";
        }
        return flfilcd;
    }

  
//
//    public boolean getAnyGeoChanged(Vector tmGeos)
//            throws HWPIMSAbnormalException
//    {
//        boolean isChanged = false;
//        int tmgSize = tmGeos.size();
//        for (int j = 0; j < tmgSize; j++)
//        {
//            TypeModelGeo tmGeoObj = (TypeModelGeo) tmGeos.elementAt(j);
//            if (tmGeoObj.getChanged())
//            {
//                isChanged = true;
//                break;
//            }
//            else
//            {
//                isChanged = false;
//            }
//        }
//        return isChanged;
//    }

   
 
    /*
     * following function is added to check for ffindicator
     */

    public boolean hasXcc()
    {
        boolean ans = false;

        Enumeration e;
        String fpipe;

        e = getFlfilCol().elements();

        while (e.hasMoreElements())
        {
            fpipe = (String) e.nextElement();
            if ("XCC".equalsIgnoreCase(fpipe))
            {
                ans = true;
                break;
            }
        }

        return ans;
    }

    /*
     * hasXcc END
     */
    public String getFlfilCd(Vector flfilcol, String salesOrg)
            throws HWPIMSAbnormalException
    {
        String flfilcd;
        if (salesOrg.equalsIgnoreCase("0026"))
        {
            flfilcd = "XCC";
        }
        else
        {
            if ((flfilcol.contains("XCC")) && (flfilcol.contains("ZIP")))
            {
                flfilcd = "BTH";
            }
            else if (flfilcol.contains("XCC"))
            {
                flfilcd = "XCC";
            }
            else if (flfilcol.contains("ZIP"))
            {
                flfilcd = "ZIP";
            }
            else
            {
                flfilcd = "EMPTY";
            }
        }
        return flfilcd;
    }

  

  

//    public boolean isFirstModelforFlfilPipeCdAndGeo(Hashtable ptHT,
//            String type, String geo, Vector vecFlfilcds)
//            throws HWPIMSAbnormalException
//    {
//        boolean ans = false;
//        //Vector vecFlfilcds = this.getFlfilCol();
//        Enumeration eFifl = vecFlfilcds.elements();
//        while (eFifl.hasMoreElements())
//        {
//            String ffInd = (String) eFifl.nextElement();
//            if (isTypePromotedForGeoAndFlfilPipeCd(ptHT, type, geo))
//            {
//                ans = true;
//                break;
//            }
//
//        }
//        return ans;
//    }
//
//    public boolean isTypePromotedForGeoAndFlfilPipeCd(Hashtable promoteTypeHT,
//            String type, String geo) throws HWPIMSAbnormalException
//    {
//        Vector typeV;
//        boolean result = false;
//        if (promoteTypeHT.containsKey(type))
//        {
//            typeV = (Vector) promoteTypeHT.get(type);
//            result = searchFor(typeV, geo);
//        }
//
//        return result;
//    }

//    private boolean searchFor(Vector typeV, String geo)
//    {
//        boolean result = false;
//        Enumeration e;
//        PromoteTypeInfo pti;
//
//        e = typeV.elements();
//        while (e.hasMoreElements())
//        {
//            pti = (PromoteTypeInfo) e.nextElement();
//            if (pti.getGeo().equalsIgnoreCase(geo))
//            {
//                result = true;
//                break;
//            }
//        }
//        return result;
//    }
//
//    public boolean isTypeNotPromotedFirstFFCd(Hashtable promoteTypeHT,
//            String type, String geo, Vector vecFlfilcds)
//            throws HWPIMSAbnormalException
//    {
//        Vector typeV;
//        boolean result = false;
//        //Vector vecFlfilcds = this.getFlfilCol();
//        Enumeration eFifl = vecFlfilcds.elements();
//        while (eFifl.hasMoreElements())
//        {
//            String ffCD = (String) eFifl.nextElement();
//            if (isTypePromotedForGeoAndFlfilPipeCdNotfirstFF(promoteTypeHT,
//                    type, geo, ffCD))
//            {
//                result = true;
//            }
//
//        }
//        return result;
//    }
//
//    public boolean isTypePromotedForGeoAndFlfilPipeCdNotfirstFF(
//            Hashtable promoteTypeHT, String type, String geo, String ffCD)
//            throws HWPIMSAbnormalException
//    {
//        Vector typeV;
//        boolean result = false;
//        if (promoteTypeHT.containsKey(type))
//        {
//            typeV = (Vector) promoteTypeHT.get(type);
//            result = searchForFF(typeV, geo, ffCD);
//        }
//
//        return result;
//    }

//    private boolean searchForFF(Vector typeV, String geo, String flfilPipeCd)
//    {
//        boolean result = false;
//        Enumeration e;
//        PromoteTypeInfo pti;
//
//        e = typeV.elements();
//        while (e.hasMoreElements())
//        {
//            pti = (PromoteTypeInfo) e.nextElement();
//            if (pti.getGeo().equalsIgnoreCase(geo)
//                    && pti.getFifl().equalsIgnoreCase(flfilPipeCd))
//            {
//                result = true;
//                break;
//            }
//        }
//        return result;
//    }
    // GsaLegal changes start
  

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((annDocNo == null) ? 0 : annDocNo.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TypeModel other = (TypeModel) obj;
		if (annDocNo == null) {
			if (other.annDocNo != null)
				return false;
		} else if (!annDocNo.equals(other.annDocNo))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
    
    
	//GsaLegal changes end

}

