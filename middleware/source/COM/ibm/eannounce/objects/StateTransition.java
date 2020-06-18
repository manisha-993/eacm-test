// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: StateTransition.java,v $
// Revision 1.19  2009/05/14 15:18:51  wendy
// Support dereference for memory release
//
// Revision 1.18  2003/04/15 23:31:29  dave
// changed .process to .test
//
// Revision 1.17  2003/04/15 23:23:17  dave
// EvaluatorII implementation
//
// Revision 1.16  2002/09/17 17:23:35  gregg
// moved entityType update (for MLA table) up into DepFlagItem class
//
// Revision 1.15  2002/09/17 00:33:37  gregg
// EntityType property on StateTransition
//
// Revision 1.14  2002/09/13 18:03:45  gregg
// updatePdhMeta, expirePdhMeta methods + database Constructor
//
// Revision 1.13  2002/09/11 22:14:00  gregg
// getEval(), removeSetItem() methods
//
// Revision 1.12  2002/04/18 19:17:41  dave
// syntax fixes
//
// Revision 1.11  2002/04/18 18:57:45  dave
// first attempt at fixing the set in StateTransition
//
// Revision 1.10  2002/04/17 17:54:09  dave
// changes to test set login on state transition
//
// Revision 1.9  2002/04/12 23:04:21  joan
// syntax
//
// Revision 1.8  2002/04/10 16:58:16  dave
// better logging during a dump for StateTransitions stuff
//
// Revision 1.7  2002/04/09 23:57:05  dave
// syntax
//
// Revision 1.6  2002/04/09 23:47:27  dave
// correcting syntax
//
// Revision 1.5  2002/04/09 23:29:05  dave
// first attempt at making the state transition compile
//
// Revision 1.4  2002/02/27 20:28:40  dave
// needs to throw throws MiddlewareRequestException
//
// Revision 1.3  2002/02/27 20:15:06  dave
// syntax
//
// Revision 1.2  2002/02/27 20:06:26  dave
// syntax
//
// Revision 1.1  2002/02/27 19:54:23  dave
// new objects to manage state transition
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;
import java.sql.ResultSet;


/**
 * This Stores and manages a single PDHTransitionItem
 *
 *
 * @author  David Bigelow
 * @version @date
 */

public class StateTransition extends EANMetaEntity {

  // Instance variables
  /**
  * @serial
  */
  static final long serialVersionUID = 1L;
  private String m_strTransitionID = null;
	private String m_strEval = "1 == 1";
	private String m_strFlag1 = null;
	private String m_strFlag2 = null;

    protected void dereference() {
    	super.dereference();
    	m_strEval = null;
    	m_strFlag1 = null;
    	m_strFlag2 = null;
    }
  /**
  * Main method which performs a simple test of this class
  */
  public static void main(String arg[]) {
  }

  public String getVersion() {
    return "$Id: StateTransition.java,v 1.19 2009/05/14 15:18:51 wendy Exp $";
  }

  /**
  * Creates the PHDTransitionItem
  * @param str1 The TransitionID
  * @param strFlag1 the from flag
  * @param strFlag2 the to Flag
  * @param strEval the Evaluation String
  */
  public StateTransition(MetaStatusAttribute _msta, String _str1, String _strFlag1, String _strFlag2) throws MiddlewareRequestException  {
    super(_msta, null, _msta.getKey() + _strFlag1 + _strFlag2);
    setTransitionID(_str1);
	setFlag1(_strFlag1);
	setFlag2(_strFlag2);
  }

  
 /**
 * build State Transition from database w/ Check + SetItems
 */
    public StateTransition (Database _db,Profile _profile, MetaStatusAttribute _msta, String _str1, String _strFlag1, String _strFlag2) throws MiddlewareException {
        this(_msta,_str1,_strFlag1,_strFlag2);
		String strAttCode = _msta.getAttributeCode(); 
		try {
		    String strNow = _db.getDates().getNow();
			// 1) get the 'Check' record
			ResultSet rs = _db.callGBL7503(new ReturnStatus(-1)
			                              ,getProfile().getEnterprise()
										  ,"Trans/Attribute"
										  ,_str1
										  ,strAttCode
										  ,"Check"
										  ,strNow
										  ,strNow);
			ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			
			for(int row = 0; row < rdrs.getRowCount(); row++) {
			    //should only be 1!
			    setEval(rdrs.getColumn(row,0));
			}
			// 2) get all the set items
			rs = _db.callGBL7508(new ReturnStatus(-1)
			                     ,getProfile().getEnterprise()
								 ,"Trans/Attribute"
								 ,_str1
								 ,"Set"
								 ,strNow
								 ,strNow); 
			rdrs = new ReturnDataResultSet(rs);
			rs.close();
			rs = null;
			_db.freeStatement();
			_db.isPending();
			for(int row = 0; row < rdrs.getRowCount(); row++) {
			    String strSetAttCode = rdrs.getColumn(row,0);
				String strSetVal  = rdrs.getColumn(row,1);
				EANMetaAttribute setAtt = null;
				try {
				    setAtt = MetaEntityList.getMetaAttributeForRole(_db,getProfile(),strSetAttCode,getProfile().getRoleCode(),getProfile().getReadLanguage().getNLSID());
				} catch(Exception exc) {
				    _db.debug(D.EBUG_ERR,"StateTransition - while building attribute (" + strAttCode + "):" + exc.toString()); 
				}
				if(setAtt == null)
				    _db.debug(D.EBUG_ERR,"StateTransition attribute " + strAttCode + "could not be built"); 		
			    else
				    putSetItem(setAtt,strSetVal);
			}
		} catch(ClassCastException ccExc) {
		    throw new MiddlewareException("State Machine Attribute must be a Status Attribute");
		} catch(java.sql.SQLException sqlExc) {
		    throw new MiddlewareException(sqlExc.toString());
		} finally {
		    _db.freeStatement();
			_db.isPending();
		}
	}
	
	
	//
	// ACCESSORS
	//

	public String getTransitionID() {
		return m_strTransitionID;
	}

  public int getSetItemCount() {
    return getDataCount();
  }

  public SetItem getSetItem(int _i) {
    return (SetItem)getData(_i);
  }

  public String getEval() {
      return m_strEval;
  }

  public String getFlag1() {
      return m_strFlag1;
  }
  
   public String getFlag2() {
      return m_strFlag2;
  }
  
  protected MetaStatusAttribute getMetaStatusAttribute() {
      return (MetaStatusAttribute)getParent();
  }
  //
  // MUTATORS
  //

	/*
	* Sets the Eval String
	*/
  protected void setEval(String _str1) {
  	m_strEval = _str1;
  }

  public void putSetItem(EANMetaAttribute _ma, String _str1) throws MiddlewareRequestException {
  	putData(new SetItem(_ma, _str1));
 	}

  public void removeSetItem(SetItem _setItem) {
      removeData(_setItem);
  }
	
 	protected void setTransitionID(String _str) {
 		m_strTransitionID = _str;
 	}

	private void setFlag1(String _s) {
	    m_strFlag1 = _s;
	}

	private void setFlag2(String _s) {
	    m_strFlag2 = _s;
	}
	
  /**
  * Displays the innards of this object for debugging, etc.
  */
	public String dump(boolean _bBrief) {

    StringBuffer strbResult = new StringBuffer();

    strbResult.append("\nStateTransition:" + getKey() + ":" + getTransitionID());
    strbResult.append("\nEval Statement:" + m_strEval);
    if (!_bBrief) {
      strbResult.append("\nAttributes:\n");
      for (int ii=0; ii < getSetItemCount();ii++) {
				SetItem si = getSetItem(ii);
      	strbResult.append("\n" + ii + ":" + si.dump(_bBrief));
			}
  	}

    return strbResult.toString();

	}

	public String toString() {
		return dump(false);
	}

	/*
	* given an Entity Item.  Does this evaluate to true
	* for the given state of the Entity Item.
	*/
	public boolean evaluate(EntityItem _ei) {
		return EvaluatorII.test(_ei,m_strEval);
	}

	/*
	* sets up the EntityItem if the evaluation returns true
	*/
	public void set(EntityItem _ei) throws EANBusinessRuleException {

		if (evaluate(_ei)) {
			for (int ii = 0; ii <  getSetItemCount();ii++) {
				SetItem si = getSetItem(ii);
				EANMetaAttribute ma = si.getMetaAttribute();
				String strAttributeCode = ma.getAttributeCode();
				String strEntityType = _ei.getEntityType();
				String strKey = strEntityType + ":" + strAttributeCode;
				// Make sure an object is there
				EANAttribute att = (EANAttribute)_ei.getEANObject(strKey);
				if (att instanceof EANTextAttribute) {
					att.put(si.getValue());
				}	else if (att instanceof EANFlagAttribute) {
				
					EANFlagAttribute fa = (EANFlagAttribute)att;
					
					// Get the current flag values
					MetaFlag[] amf = (MetaFlag[])fa.get();
				
					// Turn all of them off except for the ones that need to be on
						
					for (int ij = 0;ij < amf.length;ij++) {
						amf[ij].setSelected(false);
						if (si.getValue().equals(amf[ij].getFlagCode())) {
							amf[ij].setSelected(true);
						}	
					}		
					fa.put(amf);
				}
			}
		}
	}

	////
	//Update Pdh Meta 
	////
	
	public void updatePdhMeta(Database _db) throws MiddlewareException {
	    updatePdhMeta(_db,false);
	}
	
	public void expirePdhMeta(Database _db) throws MiddlewareException {
	    updatePdhMeta(_db,true);
	}
	
	private void updatePdhMeta(Database _db, boolean _bExpire) throws MiddlewareException {
        String strNow = _db.getDates().getNow();
		String strForever = _db.getDates().getForever();
	    // first get a clean copy from database
		StateTransition st_db = new StateTransition(_db,getProfile(),getMetaStatusAttribute(),getTransitionID(),getFlag1(),getFlag2());
		// 1) check
		if(_bExpire && st_db != null && st_db.getEval() != null) {
        	new MetaLinkAttrRow(st_db.getProfile()
		    	               ,"Trans/Attribute"
							   ,st_db.getTransitionID()
							   ,st_db.getMetaStatusAttribute().getAttributeCode()
							   ,"Check"
							   ,st_db.getEval()
							   ,strNow
							   ,strNow
							   ,strNow
							   ,strNow
							   ,2).updatePdh(_db);	    
		}
		//if new OR check changed -> update MLA -- this will only need 1 update b/c change is on linkvalue
		else if(st_db == null || st_db.getEval() == null || !st_db.getEval().equals(this.getEval())) {
        	new MetaLinkAttrRow(this.getProfile()
		    	               ,"Trans/Attribute"
							   ,this.getTransitionID()
							   ,this.getMetaStatusAttribute().getAttributeCode()
							   ,"Check"
							   ,this.getEval()
							   ,strNow
							   ,strForever
							   ,strNow
							   ,strForever
							   ,2).updatePdh(_db);		
		}
		// 2) Set Items
		if(_bExpire) {
			for(int i = 0; i < st_db.getSetItemCount(); i++) {
			    st_db.getSetItem(i).expirePdhMeta(_db,st_db.getTransitionID());
		    }
		} else {
		    //a) expire set items that are in db, !in this
			for(int i = 0; i < st_db.getSetItemCount(); i++) {
			    SetItem si_db = st_db.getSetItem(i);
				boolean bFound = false;
				for(int j = 0; j < this.getSetItemCount(); j++) {
				    SetItem si_this = this.getSetItem(j);
					if(si_this.getMetaAttribute().getAttributeCode().equals(si_db.getMetaAttribute().getAttributeCode())) {
					    bFound = true;
						j = this.getSetItemCount(); //break out of 'j' loop
						continue;
					}
				}
				//expire db record
				if(!bFound) {
        			si_db.expirePdhMeta(_db,st_db.getTransitionID());					
				}   
			}
			// b) update items that are not found in db, OR that have changed linkvalues
		    for(int i = 0; i < this.getSetItemCount(); i++) {
			    SetItem si_this = this.getSetItem(i);
				boolean bUpdate = false;
				boolean bFound = false;
				for(int j = 0; j < st_db.getSetItemCount(); j++) {
				    SetItem si_db = st_db.getSetItem(j);
					if(si_this.getMetaAttribute().getAttributeCode().equals(si_db.getMetaAttribute().getAttributeCode())) {
					    bFound = true;
						if(!si_this.getValue().equals(si_db.getValue())) {
						    bUpdate = true;
							j = st_db.getSetItemCount(); //break out of 'j' loop
							continue;
						}
					}
				}
				if(bUpdate || !bFound) {
        			si_this.updatePdhMeta(_db,this.getTransitionID());			    
				}
			}
		}
		
	}
	
}
