//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: UniqueAttributeGroup.java,v $
// Revision 1.41  2012/02/13 21:32:08  wendy
// Reuse EntityGroup if possible to improve performance
//
// Revision 1.40  2009/05/11 15:25:14  wendy
// Support dereference for memory release
//
// Revision 1.39  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.38  2007/05/11 19:32:42  chris
// Make SearchLimit 100,000 by default. 
// can be overidden with System property of UniqueAttributeCheck.limit and SearchActionItem.limit
//
// Revision 1.37  2007/03/22 21:04:50  chris
// add limit to gbl9000 call
//
// Revision 1.36  2005/03/31 22:50:57  gregg
// fixing parent references for cached UniqueAttribtueGroup within EntityGroup
//
// Revision 1.35  2005/03/01 01:29:10  dave
// removing readObject
//
// Revision 1.34  2005/03/01 00:30:55  dave
// Jtest and TIR testing
//
// Revision 1.33  2005/02/23 21:38:31  gregg
// comments
//
// Revision 1.32  2005/02/23 20:30:16  gregg
// workin on more for CR7137 (UniqueAttribute)
//
// Revision 1.31  2005/02/01 18:53:04  gregg
// cleanup
//
// Revision 1.30  2005/02/01 18:38:09  gregg
// lets use Evaluator sooner for UniqueAttributeGroup... before we sned off to database
//
// Revision 1.29  2005/02/01 18:19:33  gregg
// some more Evaluating (local values included) for UniqueAttributes
//
// Revision 1.28  2005/02/01 17:52:38  gregg
// slite logik phix fer validayte
//
// Revision 1.27  2005/02/01 17:39:30  gregg
// conditional value for UniqueAttribute check
//
// Revision 1.26  2005/01/18 21:46:51  dave
// more parm debug cleanup
//
// Revision 1.25  2005/01/17 23:45:26  gregg
// change key for PartNo
//
// Revision 1.24  2005/01/17 22:53:11  gregg
// s'more fixes for UniqueAttributeGroup
//
// Revision 1.23  2005/01/17 22:51:20  gregg
// sum fixes
//
// Revision 1.22  2005/01/17 22:30:08  gregg
// fix for UniqueAttributeGroups...
//
// Revision 1.21  2005/01/12 23:18:20  gregg
// serial
//
// Revision 1.20  2005/01/11 23:35:49  gregg
// some PartNo memory storing stuff
//
// Revision 1.19  2005/01/11 19:39:34  gregg
// fixing
//
// Revision 1.18  2005/01/11 19:27:03  gregg
// fix
//
// Revision 1.17  2005/01/11 19:12:32  gregg
// compile fix
//
// Revision 1.16  2005/01/11 19:06:29  gregg
// more UniqueAttributes
//
// Revision 1.15  2005/01/10 17:35:03  gregg
// some debugs
//
// Revision 1.14  2005/01/07 20:57:56  gregg
// ensure all attributes exist in a UniqueAttributeGroup if we're gonna perform checks
//
// Revision 1.13  2005/01/06 22:37:08  gregg
// fix validate logic to skip items being deactivated
//
// Revision 1.12  2005/01/06 22:15:27  gregg
// one more Exc message
//
// Revision 1.11  2005/01/06 22:04:31  gregg
// improve message
//
// Revision 1.10  2005/01/06 21:53:28  gregg
// fixin up Exception message
//
// Revision 1.9  2005/01/06 18:33:34  gregg
// fix import
//
// Revision 1.8  2005/01/06 18:28:44  gregg
// Serializable
//
// Revision 1.7  2005/01/06 00:52:02  gregg
// fix
//
// Revision 1.6  2005/01/06 00:46:58  gregg
// fix
//
// Revision 1.5  2005/01/06 00:41:00  gregg
// validate() method
//
// Revision 1.4  2005/01/06 00:16:51  gregg
// add Enterprise to UniqueAttributeGroup constructor
//
// Revision 1.3  2005/01/05 23:57:41  gregg
// add EntityType to UniqueAttributeGroup constructor
//
// Revision 1.2  2005/01/05 23:27:37  gregg
// s'more for UniqueAttributeGroup
//
// Revision 1.1  2005/01/05 22:34:25  gregg
// setting up UniqueAttributeGroup on EntityGroup
//
//

package COM.ibm.eannounce.objects;

import java.util.Vector;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 * Let us store a set of attributes - the combination of which are unique according to some predefined rules.
 */
public class UniqueAttributeGroup extends EANMetaFoundation {

    /**
     *@serial
     */
    final static long serialVersionUID = 1L;

    private Vector m_vctAttributeCodes = null; // lets store as Strings fer now...
    private String m_strEntityType = null;
    private Vector m_vctAttributeVals = null; // lets keep these in sync w/ attributecodes.
    private Vector m_vctAttributeTypes = null; // lets keep these in sync w/ attributecodes.
    private String m_strEval = null;
    
    protected void dereference(){
    	m_strEntityType = null;
    	m_strEval = null;
    	if (m_vctAttributeCodes != null){
    		m_vctAttributeCodes.clear();
    		m_vctAttributeCodes = null;
    	}
    	if (m_vctAttributeVals != null){
    		m_vctAttributeVals.clear();
    		m_vctAttributeVals = null;
    	}
    	if (m_vctAttributeTypes != null){
    		m_vctAttributeTypes.clear();
    		m_vctAttributeTypes = null;
    	}   	
    	super.dereference();
    }

    /**
     * UniqueAttributeGroup
     *
     * @param _egParent
     * @param _prof
     * @param _strEntityType
     * @param _strGroupKey
     * @param _strEval
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected UniqueAttributeGroup(Profile _prof, String _strEntityType, String _strGroupKey, String _strEval) throws MiddlewareRequestException {
        super(null, _prof, _strGroupKey);
        m_vctAttributeCodes = new Vector();
        m_vctAttributeVals = new Vector();
        m_vctAttributeTypes = new Vector();
        m_strEntityType = _strEntityType;
        m_strEval = _strEval;
    }

    private UniqueAttributeGroup(UniqueAttributeGroup _uag) throws MiddlewareRequestException {
        this(_uag.getProfile(), _uag.m_strEntityType, _uag.getKey(), _uag.getEvaluationString());
        if (_uag.m_vctAttributeCodes != null) {
            this.m_vctAttributeCodes = new Vector(_uag.m_vctAttributeCodes.size());
            for (int i = 0; i < _uag.m_vctAttributeCodes.size(); i++) {
                this.m_vctAttributeCodes.addElement(_uag.m_vctAttributeCodes.elementAt(i));
            }
        }
        if (_uag.m_vctAttributeVals != null) {
            this.m_vctAttributeVals = new Vector(_uag.m_vctAttributeVals.size());
            for (int i = 0; i < _uag.m_vctAttributeVals.size(); i++) {
                this.m_vctAttributeVals.addElement(_uag.m_vctAttributeVals.elementAt(i));
            }
        }
        if (_uag.m_vctAttributeTypes != null) {
            this.m_vctAttributeTypes = new Vector(_uag.m_vctAttributeTypes.size());
            for (int i = 0; i < _uag.m_vctAttributeTypes.size(); i++) {
                this.m_vctAttributeTypes.addElement(_uag.m_vctAttributeTypes.elementAt(i));
            }
        }
    }

    /**
     * getEntityGroup
     *
     * @return
     *  @author David Bigelow
     */
    //public EntityGroup getEntityGroup() {
    //    return (EntityGroup) getParent();
    //}

    /**
     * addAttributeCode
     *
     * @param _strAttributeCode
     *  @author David Bigelow
     */
    public final void addAttributeCode(String _strAttributeCode) {
        m_vctAttributeCodes.addElement(_strAttributeCode);
    }

    /**
     * getAttributeCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getAttributeCount() {
        return m_vctAttributeCodes.size();
    }

    /**
     * getAttributeCode
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeCode(int _i) {
        return (String) m_vctAttributeCodes.elementAt(_i);
    }

    /**
     * addAttributeVal
     *
     * @param _strAttributeVal
     *  @author David Bigelow
     */
    public final void addAttributeVal(String _strAttributeVal) {
        m_vctAttributeVals.addElement(_strAttributeVal);
    }

    /**
     * getAttributeVal
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeVal(int _i) {
        return (String) m_vctAttributeVals.elementAt(_i);
    }

    /**
     * addAttributeType
     *
     * @param _strAttributeType
     *  @author David Bigelow
     */
    public final void addAttributeType(String _strAttributeType) {
        m_vctAttributeTypes.addElement(_strAttributeType);
    }

    /**
     * getAttributeType
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final String getAttributeType(int _i) {
        return (String) m_vctAttributeTypes.elementAt(_i);
    }

    /**
     * getExceptionMessage
     *
     * @return
     *  @author David Bigelow
     */
    public final String getExceptionMessage() {
        StringBuffer sb = new StringBuffer("Unique Attribute Rule failed for: ");
        for (int i = 0; i < getAttributeCount(); i++) {
            String strAttCode = getAttributeCode(i);
            String strAttVal = getAttributeVal(i);
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(strAttCode);
            sb.append("::");
            sb.append(strAttVal);
        }
        return sb.toString();
    }

    /**
     * Get a unique PartNo String based on all attributecodes, vals.
     *
     * @return String
     */
    public final String getPartNo() {
        StringBuffer sbPartNo = new StringBuffer(getKey() + ":");
        // outer loop represents atts we care about in terms of uniqueness...
        for (int i = 0; i < getAttributeCount(); i++) {
            String strAttCode = getAttributeCode(i);
            String strAttVal = getAttributeVal(i);
            sbPartNo.append(strAttCode + ":" + strAttVal);
        }
        return sbPartNo.toString();
    }
    /**
     * validate
     *
     * @param _db
     * @param _iEntityID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public final boolean validate(Database _db, int _iEntityID) throws SQLException, MiddlewareException {
        Profile prof = getProfile();
        EntityGroup eg = new EntityGroup(null,_db,prof,getEntityType(),"Edit");
        return validate(_db, _iEntityID, eg);
    }

    /**
     * attempt to improve performance by reusing the same entitygroup
     * @param _db
     * @param _iEntityID
     * @param eg
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    public final boolean validate(Database _db, int _iEntityID, EntityGroup eg) throws SQLException, MiddlewareException {

    	boolean bUnique = true;
    	int iSessionID = _db.getNewSessionID();
    	int iStep = 0;
    	ReturnStatus returnStatus = new ReturnStatus(-1);
    	String strNow = _db.getDates().getNow();
    	Profile prof = getProfile();
    	//EntityGroup eg = null;

    	//eg = new EntityGroup(null,_db,prof,getEntityType(),"Edit");

    	String strEnterprise = prof.getEnterprise();

    	ResultSet rs = null;
    	ReturnDataResultSet rdrs = null;

    	try{
    		// outer loop represents atts we care about in terms of uniqueness...
    		for (int i = 0; i < getAttributeCount(); i++) {

    			String strAttCode = getAttributeCode(i);
    			String strAttVal = getAttributeVal(i);
    			String strAttType = getAttributeType(i);
    			_db.debug(D.EBUG_DETAIL, "UniqueAttributeGroup:in validate, att val is:" + strAttCode + ":" + strAttType + ":\"" + strAttVal + "\"");

    			iStep++;
    			_db.callGBL8119(returnStatus, iSessionID, iStep, strEnterprise, m_strEntityType, strAttCode, strAttVal);
    			_db.commit();
    			_db.freeStatement();
    			_db.isPending();
    		}

    		try {
    			rs = _db.callGBL9200(returnStatus, iSessionID, strEnterprise, "UniqueAttributeCheck", 0, strNow, strNow, Integer.parseInt(System.getProperty("AniqueAttributeCheck.limit","100000")));
    			rdrs = new ReturnDataResultSet(rs);
    		} finally {
    			if(rs!=null){
    				rs.close();
    				rs = null;
    			}
    			_db.commit();
    			_db.freeStatement();
    			_db.isPending();
    		}

    		_db.debug(D.EBUG_DETAIL, "UniqueAttributeGroup.validate:9200 count:" + rdrs.size());

    		for (int i = 0; i < rdrs.getRowCount(); i++) {
    			String strCheckEntityType = rdrs.getColumn(i, 0);
    			int iCheckID = rdrs.getColumnInt(i, 1);
    			// check it -- if the one and only record is ourself, then we are fine
    			if (iCheckID != _iEntityID) {
    				// ok, we are not looking at ourself, so continue
    				// lets build an entityitem to validate...
    				// RE: later let's speed this up by putting values first into search above and skipping this step!
    				EntityItem eiCheck = new EntityItem(eg, getProfile(), _db, strCheckEntityType, iCheckID);
    				if (evaluate(eiCheck)) {
    					bUnique = false;
    					eg.removeEntityItem(eiCheck); // attempt to reuse entitygroup
    					_db.debug(D.EBUG_DETAIL, "UniqueAttributeGroup.validate:found:"+strCheckEntityType+iCheckID);
    					break;
    				}

    				eg.removeEntityItem(eiCheck); // attempt to reuse entitygroup
    			}
    		}
    	} finally {
    		//moved to finally block so it always cleans up
    		_db.callGBL8105(returnStatus, iSessionID);
    		_db.commit();
    		_db.freeStatement();
    		_db.isPending();
    	}
        
        return bUnique;
    }

    /**
     * Run a simple validation check to ensure all values are present in the relevant attributes...
     *
     * @return boolean
     * @param _ei
     */
    public final boolean allUniqueValuesExist(EntityItem _ei) {
        System.out.println("UniqueAttributeGroup.allUniqueValuesExist checking " + getKey());
        for (int i = 0; i < getAttributeCount(); i++) {
            EANAttribute att = _ei.getAttribute(getAttributeCode(i));
            if (att == null || att.toString() == null || att.toString().equals("")) {
                System.out.println("UniqueAttributeGroup.allUniqueValuesExist:false");
                return false;
            }
        }
        System.out.println("UniqueAttributeGroup.allUniqueValuesExist:true");
        return true;
    }

    /**
     * populateAttributeVals
     *
     * @param _ei
     *  @author David Bigelow
     */
    public final void populateAttributeVals(EntityItem _ei) {
        for (int i = 0; i < getAttributeCount(); i++) {
            String strAttCode = getAttributeCode(i);
            EANAttribute att = _ei.getAttribute(strAttCode);
            String strAttVal = (att != null ? att.toString() : "");
            String strAttType = "TEXT";
            if (att instanceof EANFlagAttribute) {
                strAttType = "FLAG";
            }
            addAttributeVal(strAttVal);
            addAttributeType(strAttType);
        }
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _b) {
        StringBuffer sb = new StringBuffer(getKey() + "\n");
        for (int i = 0; i < getAttributeCount(); i++) {
            sb.append(getAttributeCode(i) + "\n");
        }
        sb.append(getEvaluationString());
        return sb.toString();
    }

    private String getEvaluationString() {
        return m_strEval;
    }

    /**
     * getEntityType
     *
     * @return
     *  @author David Bigelow
     */
    public String getEntityType() {
        return m_strEntityType;
    }

    /**
     * getCopy
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @return
     *  @author David Bigelow
     */
    public UniqueAttributeGroup getCopy() throws MiddlewareRequestException {
        return new UniqueAttributeGroup(this);
    }

    /**
     * evaluate
     * given an Entity Item.  Does this evaluate to true
     * for the given state of the Entity Item.
     *
     * @param _ei
     * @return
     *  @author David Bigelow
     */
    public boolean evaluate(EntityItem _ei) {
        return EvaluatorII.test(_ei, getEvaluationString());
    }

}
