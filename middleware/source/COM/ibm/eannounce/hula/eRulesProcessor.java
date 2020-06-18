//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eRulesProcessor.java,v $
// Revision 1.32  2008/03/09 04:13:36  wendy
// Undo RSA change
//
// Revision 1.31  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.30  2004/12/01 22:22:16  gregg
// more fixes
//
// Revision 1.29  2004/12/01 22:05:43  gregg
// fixes
//
// Revision 1.28  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.27  2004/10/06 18:59:53  gregg
// some unpublish logic
//
// Revision 1.26  2004/09/24 02:11:49  dave
// more spew.. a wonderfull thing
//
// Revision 1.25  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.24  2004/09/09 15:27:30  gregg
// some publish filtering for rules
//
// Revision 1.23  2004/09/02 17:31:32  gregg
// sort rollup vals.
//
// Revision 1.22  2004/09/01 22:01:33  gregg
// return null productDetails if rollup atts cant be found
//
// Revision 1.21  2004/09/01 21:24:14  gregg
// rollup rules volume II
//
// Revision 1.20  2004/09/01 19:02:01  gregg
// null ptr fix -- get now time from ePtoduct parent...
//
// Revision 1.19  2004/08/31 17:39:07  gregg
// removing some debugs
//
// Revision 1.18  2004/08/31 17:05:34  gregg
// minor change
//
// Revision 1.17  2004/08/31 16:25:49  gregg
// rollup rules : test on FLAGCODE
//
// Revision 1.16  2004/08/31 16:07:29  gregg
// some debugs
//
// Revision 1.15  2004/08/30 22:31:41  gregg
// rollup rules
//
// Revision 1.14  2004/08/30 21:55:02  gregg
// working on rollup rules
//
// Revision 1.13  2004/08/30 20:17:51  gregg
// more inbound/outbound rules
//
// Revision 1.12  2004/08/27 16:38:36  gregg
// Vector to array
//
// Revision 1.11  2004/08/26 21:25:09  gregg
// fixing tokens
//
// Revision 1.10  2004/08/26 20:53:23  gregg
// some debugs
//
// Revision 1.9  2004/08/26 19:52:20  gregg
// next pass at concat enation
//
// Revision 1.8  2004/08/26 18:01:50  gregg
// remove PLUS
//
// Revision 1.7  2004/08/25 21:20:16  gregg
// some concatenation spacing
//
// Revision 1.6  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.5  2004/08/23 23:14:02  gregg
// more rules
//
// Revision 1.4  2004/08/23 22:45:33  gregg
// use pass value from eConcatRule
//
// Revision 1.3  2004/08/23 21:34:48  gregg
// some ConcatRules
//
// Revision 1.2  2004/08/23 18:06:21  gregg
// return null when both atts are missing
//
// Revision 1.1  2004/08/23 16:42:20  gregg
// load to middleware module
//
//

package COM.ibm.eannounce.hula;

import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

public class eRulesProcessor {

    static final long serialVersionUID = 20011106L;

    private static final String WILDCARD = "*";

    private eRulesProcessor() {}

/**
 * Build a Derived ProductDetail object based on a Basic Rule.
 */
    public static final eProductDetail getDerivedProductDetail(Database _db, eProduct _prod, eBasicRule _basicRule) throws Exception {
        eProductDetail epd = null;
        switch(_basicRule.getType()) {
            case eBasicRule.CONCATRULE_TYPE:
                epd = getDerivedProductDetail(_db,_prod,(eConcatRule)_basicRule);
                break;
            case eBasicRule.ROLLUPRULE_TYPE:
                epd = getDerivedProductDetail(_db,_prod,(eRollupRule)_basicRule);
                break;
            default:
                epd = null;
                break;
        }
        return epd;
    }


/**
 * Build a Derived ProductDetail object based on a Concatenation Rule.
 */
    private static final eProductDetail getDerivedProductDetail(Database _db, eProduct _prod, eConcatRule _concatRule) throws Exception {
        String strExp = _concatRule.getPass();
        StringTokenizer st = new StringTokenizer(strExp," ");
        String[] saVals = new String[st.countTokens()];
        int iToks = 0;

        boolean bPublish = false;
        while(st.hasMoreTokens()) {
            String strToken = st.nextToken();

            String strVal = strToken;
            eProductDetail[] aProdDetail = getProductDetailsForToken(_prod,strToken);
            if(aProdDetail.length > 0) {
                strVal = aProdDetail[0].getStringVal(eProductDetail.ATTRIBUTEVALUE);
                if(aProdDetail[0].getStringVal(eProductDetail.PUBLISHFLAG).equals(eProductDetail.PUBLISH_VAL)) {
                    bPublish = true;
                }
            }

            saVals[iToks++] = strVal;
        }
        // now put back in spaces
        StringBuffer sbFinal = new StringBuffer();
        for(int i = 0; i < saVals.length; i++) {
            String strVal = saVals[i];
            sbFinal.append(strVal);
            if(i < (saVals.length-1)) {
                sbFinal.append(" ");
            }
        }
        return buildDerivedProductDetail(_db,_prod,_concatRule,sbFinal.toString(),bPublish);
    }

/**
 * Build a Derived ProductDetail object based on a Rollup Rule.
 */
    private static final eProductDetail getDerivedProductDetail(Database _db, eProduct _prod, eRollupRule _rollupRule) throws Exception {

                D.ebug(D.EBUG_SPEW,"TIMING: eRulesProcessor.getDerivedProductDetail START");

        String strTest = _rollupRule.getTest();
        String strPass = _rollupRule.getPass();
        //boolean bPassed = false;

        // This part will have to be looked at later....
        StringTokenizer st = new StringTokenizer(strTest," ");
        String strLeftToken = st.nextToken();
        String strOpToken = st.nextToken(); // "=="
        String strRightToken = st.nextToken();
        eProductDetail[] aProdDetailTests = getProductDetailsForToken(_prod,strLeftToken);
        eProductDetail[] aProdDetailPasses = getProductDetailsForToken(_prod,strPass);
        Vector vctRolledUpVals = new Vector();
                D.ebug(D.EBUG_SPEW,"TIMING: eRulesProcessor.getDerivedProductDetail.stats." +
                "PDTest:"+ aProdDetailTests.length +
                "PDPasses:" + aProdDetailPasses.length
                );

        boolean bPublish = false;

      //TEST_LOOP:
        for(int i = 0; i < aProdDetailTests.length; i++) {
            eProductDetail prodDetailTest = aProdDetailTests[i];
            // if test passes for this guy....
            if(prodDetailTest.getStringVal(eProductDetail.FLAGCODE).equals(strRightToken)) {
                // BRUTE FORCE METHOD...
                // find all matching 'entities' from the 'real world'
                for(int j = 0; j < aProdDetailPasses.length; j++) {
                    eProductDetail prodDetailPass = aProdDetailPasses[j];
                    if(prodDetailTest.getStringVal(eProductDetail.ENTERPRISE).equals(prodDetailPass.getStringVal(eProductDetail.ENTERPRISE)) &&
                       //prodDetailTest.getStringVal(eProductDetail.ROOTTYPE).equals(prodDetailPass.getStringVal(eProductDetail.ROOTTYPE)) &&
                       prodDetailTest.getStringVal(eProductDetail.ENTITYTYPE).equals(prodDetailPass.getStringVal(eProductDetail.ENTITYTYPE)) &&
                       //prodDetailTest.getIntVal(eProductDetail.ROOTID) == prodDetailPass.getIntVal(eProductDetail.ROOTID) &&
                       prodDetailTest.getIntVal(eProductDetail.ENTITYID) == prodDetailPass.getIntVal(eProductDetail.ENTITYID) &&
                       prodDetailTest.getIntVal(eProductDetail.NLSID) == prodDetailPass.getIntVal(eProductDetail.NLSID)) {
                        vctRolledUpVals.addElement(prodDetailPass.getStringVal(eProductDetail.ATTRIBUTEVALUE));
                        if(prodDetailPass.getStringVal(eProductDetail.PUBLISHFLAG).equals(eProductDetail.PUBLISH_VAL)) {
                            bPublish = true;
                        }
                    }
                }
            }
        }

                D.ebug(D.EBUG_SPEW,"TIMING: eRulesProcessor.getDerivedProductDetail END");
        String[] saVals = (String[])vctRolledUpVals.toArray(new String[vctRolledUpVals.size()]);
        if(saVals.length == 0) {
            return null;
        }
        String strVals = rollupVals(saVals);

        return buildDerivedProductDetail(_db,_prod,_rollupRule,strVals,bPublish);
    }

    private static final String rollupVals(String[] _saVals) {
        EANComparator ec = new EANComparator(true);
        Arrays.sort(_saVals, ec);
        StringBuffer sbFinal = new StringBuffer();
        String strSeperator = ",";
        for(int i = 0; i < _saVals.length; i++) {
            sbFinal.append(_saVals[i]);
            if(i < (_saVals.length-1)) {
                sbFinal.append(strSeperator);
            }
        }
        return sbFinal.toString();
    }

/**
 * Construct our Derived Attribute
 */
    private static final eProductDetail buildDerivedProductDetail(Database _db, eProduct _prod, eBasicRule _basicRule, String _strAttVal, boolean _bPublish) throws Exception {
        String strHeritage = _basicRule.getHeritage();
        String strChildType = _basicRule.getEntityType();
        String strAttCode = _basicRule.getAttributeCode();
        int iChildID = _prod.getIntVal(eProduct.ENTITYID);
        String strPublish = (_bPublish?eProductDetail.PUBLISH_VAL:eProductDetail.UNPUBLISH_VAL);
        String strAttType = (_basicRule.isInBoundTrigger()?eProductDetail.INBOUND_DERIVED_ATTRIBUTETYPE:eProductDetail.OUTBOUND_DERIVED_ATTRIBUTETYPE);

        // publish if at least one att is published
        //String strPublish = ((strLeftPublish.equals(eProductDetail.PUBLISH_VAL) || strRightPublish.equals(eProductDetail.PUBLISH_VAL))?eProductDetail.PUBLISH_VAL:eProductDetail.UNPUBLISH_VAL);

        eProductDetail prodDetail = new eProductDetail(_prod
                                                      ,_prod.getProfile()
                                                      ,strChildType
                                                      ,iChildID
                                                      ,-1
                                                      ,-1
                                                      ,-1
                                                      ,-1
                                                      ,strHeritage
                                                      ,strAttCode
                                                      ,strAttType
                                                      ,eProductDetail.NO_COLUMN_VAL
                                                      ,_strAttVal
                                                      ,strPublish
                                                      ,_prod.getStringVal(eProduct.VALFROM)
                                                      ,-99);
        prodDetail.updateSequences(_db);

        return prodDetail;
    }

/**
 * REMEMBER WE CAN SPEED THIS STUFF UP A BIT.....
 */
    //private static final String getValueFromToken(eProduct _prod, String _strToken, String _strColKey) {
    //    eProductDetail[] aProdDetail = getProductDetailsForToken(_prod,_strToken);
    //    if(aProdDetail.length == 0) {
    //        return _strToken;
    //    }
    //    return aProdDetail[0].getStringVal(_strColKey);
    //}

/**
 * Token is in the form:
 *   HERITAGE.ENTITYTYPE.ATTRIBUTECODE
 *   We want to return a eProductDetail[] b/c wildcards could yield multiple Product Detail Values.
 */
    private static final eProductDetail[] getProductDetailsForToken(eProduct _prod, String _strToken) {
        //eProductUpdater.debug("getValueFromToken for " + _prod.toString() + ", \"" + _strToken + "\"");
        Vector vctVals = new Vector();
        //String strVal = null;
        try {
            StringTokenizer st = new StringTokenizer(_strToken,".");
            String strHeritage = st.nextToken();
            boolean bHeritageWildcard = strHeritage.equals(WILDCARD);
            String strEType = st.nextToken();
            boolean bETypeWildcard = strEType.equals(WILDCARD);
            String strAttCode = st.nextToken();
            boolean bAttCodeWildcard = strAttCode.equals(WILDCARD);
          ATT_LOOP:
            for(int i = 0; i < _prod.getProductDetailCount(); i++) {
                eProductDetail prodDetail = _prod.getProductDetail(i);
                if(!prodDetail.isPublished()) {
                    // we only care about published attributes!!!
                    continue ATT_LOOP;
                }
                String strCheckHeritage = prodDetail.getStringVal(eProductDetail.HERITAGE);
                String strCheckEType = prodDetail.getStringVal(eProductDetail.ENTITYTYPE);
                String strCheckAttCode = prodDetail.getStringVal(eProductDetail.ATTRIBUTECODE);
                if((strHeritage.equals(strCheckHeritage) || bHeritageWildcard) &&
                   (strEType.equals(strCheckEType) || bETypeWildcard) &&
                   (strAttCode.equals(strCheckAttCode) || bAttCodeWildcard)) {
                    // we can tack this on to the end later if need be....
                    // we want: if at least one attribute is published --> the derived is published.
                    //strPublish = (prodDetail.getStringVal(eProductDetail.PUBLISHFLAG).equals(eProductDetail.UNPUBLISH_VAL) || (prodDetail.isQueuedForUnpublish() && prodDetail.isActive())?eProductDetail.UNPUBLISH_VAL:eProductDetail.PUBLISH_VAL);
                    vctVals.addElement(prodDetail);
                    //eProductUpdater.debug("     ***getValueFromToken for " + _prod.toString() + ", \"" + _strToken + "\" FOUND:" + strVal);
                    //break ATT_LOOP;
                }
            }
        } catch(NoSuchElementException x) {}
        if(vctVals.size() == 0) {
            //eProductUpdater.debug("     getValueFromToken for " + _prod.toString() + ", \"" + _strToken + " NOT FOUND....");
        }
        return (eProductDetail[])vctVals.toArray(new eProductDetail[vctVals.size()]);
    }

/*
 * Version info
 */
    public String getVersion() {
        return new String("$Id: eRulesProcessor.java,v 1.32 2008/03/09 04:13:36 wendy Exp $");
    }

}
