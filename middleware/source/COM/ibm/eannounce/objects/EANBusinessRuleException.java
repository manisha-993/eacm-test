// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2002, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: EANBusinessRuleException.java,v $
// Revision 1.20  2013/03/18 20:26:11  wendy
// Added containsKey method
//
// Revision 1.19  2010/06/17 21:51:29  wendy
// Dont deref shared objects
//
// Revision 1.18  2009/12/16 02:21:46  wendy
// prevent duplicate output of entity information
//
// Revision 1.17  2009/11/03 18:45:15  wendy
// add deref for memory cleanup
//
// Revision 1.16  2009/10/22 20:41:59  wendy
// cleanup default constructor
//
// Revision 1.15  2008/02/06 14:00:21  wendy
// Needed to access addException() outside of package, made it public
//
// Revision 1.14  2005/09/08 18:12:25  joan
// add testing rule for meta maintenance
//
// Revision 1.13  2005/03/23 20:20:57  joan
// work on flag maint
//
// Revision 1.12  2005/02/14 17:18:34  dave
// more jtest fixing
//
// Revision 1.11  2004/10/21 16:49:53  dave
// trying to share compartor
//
// Revision 1.10  2003/01/14 23:46:54  joan
// adjust exception
//
// Revision 1.9  2002/08/15 16:02:12  dave
// exception handling cleanup
//
// Revision 1.8  2002/08/15 15:23:23  dave
// error message clean up
//
// Revision 1.7  2002/08/14 22:36:53  dave
// minor fix for eanbusinessruleexp  need getmessage to return tostring
//
// Revision 1.6  2002/05/20 18:47:58  joan
// when adding new row and creating, set up the parent entity item with entityid = -1
// when parent entitygroup has more than one entity item,
// and throw exception when commit
//
// Revision 1.5  2002/04/15 16:31:13  joan
// fixing exception
//
// Revision 1.4  2002/04/11 17:55:06  joan
// working on exception
//
// Revision 1.3  2002/04/10 19:47:07  joan
// working on exception
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * EANBusinessRuleException
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class EANBusinessRuleException extends Exception {
    private EANList m_elFailures = null;
    private OPICMList m_olMessages = null;

    /**
    * Constructs a <code>EANBusinessRuleException</code> with no specified detail message
    */
    public EANBusinessRuleException() {
        this("no detail message");
    }

    /**
     * Constructs a <code>EANBusinessRuleException</code> with the specified detail message
     *
     * @param s
     */
    public EANBusinessRuleException(String s) {
        super(s);
        m_elFailures = new EANList();
        m_olMessages = new OPICMList();
    }

    /**
     * add
     *
     * @param _eanAttr
     * @param _s
     *  @author David Bigelow
     */
    public void add(EANAttribute _eanAttr, String _s) {
        m_elFailures.put(_eanAttr);
        m_olMessages.put(_eanAttr.getKey(), _s);
    }

    /*
    * Adds an Entity Item and Message to the failure list
    */
    /**
     * add
     *
     * @param _ei
     * @param _s
     *  @author David Bigelow
     */
    public void add(EntityItem _ei, String _s) {
        m_elFailures.put(_ei);
        m_olMessages.put(_ei.getKey(), _s);
    }

    /*
    * Adds an EANMetaEntity and Message to the failure list
    */
    /**
     * add
     *
     * @param _ei
     * @param _s
     *  @author David Bigelow
     */
    public void add(EANFoundation _ei, String _s) {
        m_elFailures.put(_ei);
        m_olMessages.put(_ei.getKey(), _s);
    }

    /*
    * Returns the String representation of all errors in this exception
    * @return the String representation of all errors in this exception
    */
    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	StringBuffer sb = new StringBuffer();
        for (int x = 0; x < m_elFailures.size(); x++) {
            EANObject obj = m_elFailures.getAt(x);
            String strObj = "Unknown Object";
            String strDesc = null;
            String type = null;
            if (obj instanceof EANAttribute) {
                strObj = ((EANAttribute) obj).getLongDescription();
            } else if (obj instanceof EntityItem) {
                strObj = ((EntityItem) obj).getLongDescription();
                type = obj.getKey();
            } else if (obj instanceof EANFoundation) {
				strObj = ((EANFoundation) obj).getLongDescription();
			}
            strDesc = (String) m_olMessages.getAt(x);

            if(type!=null){
            	sb.append(type +" - ");
            }
            
            if (strDesc.indexOf(strObj)==-1){
            	sb.append(strObj + " - " + strDesc);
            }else{ // prevent repeat entity info when this exception was created from another
            	sb.append(strDesc.trim()); // remove leading new line
            }
    
            sb.append("\n");
        }

        return sb.toString();
    }

    /*
    * Number of failures in the exception
    */
    /**
     * getErrorCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getErrorCount() {
        return m_elFailures.size();
    }

    /*
    * return the failed PDHAttribute
    */
    /**
     * getObject
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public Object getObject(int _i) {
        if (_i > getErrorCount()) {
            return null;
        }
        return (Object) m_elFailures.getAt(_i);
    }
    
    /**
     * find out if this key already exists in the failures list
     * @param key
     * @return
     */
    public boolean containsKey(String key){
    	return m_elFailures.containsKey(key);
    }

    /**
     * getMessage
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getMessage(int _i) {
        if (_i > getErrorCount()) {
            return null;
        }
        return (String) m_olMessages.getAt(_i);
    }

    /**
     * clearLists
     *
     *  @author David Bigelow
     */
    protected void clearLists() {
        m_elFailures.clear();
        m_olMessages.clear();
    }

    /**
     * addException
     *
     * @param _bre
     *  @author David Bigelow
     */
    public void addException(EANBusinessRuleException _bre) {
        for (int i = 0; i < _bre.getErrorCount(); i++) {
            Object o = _bre.getObject(i);
            String s = _bre.getMessage(i);
            if (o instanceof EntityItem) {
                add((EntityItem) o, s);
            } else if (o instanceof EANAttribute) {
                add((EANAttribute) o, s);
            }
        }
    }

    /**
     * (non-Javadoc)
     * getMessage
     *
     * @see java.lang.Throwable#getMessage()
     */
    public String getMessage() {
        return toString();
    }
    
    public void dereference() {
        m_elFailures.clear();
        m_elFailures = null;
        m_olMessages.clear();
        m_olMessages = null;
        /*some items are shared, so cant deref them here
         for (int x = 0; x < m_elFailures.size(); x++) {
            EANObject obj = m_elFailures.getAt(x);
            if (obj instanceof EANAttribute) {
            	((EANAttribute) obj).dereference();
            } else if (obj instanceof EntityItem) {
            	EntityItem ei = ((EntityItem) obj);
            	if (ei.getEntityGroup()!=null){
            		ei.getEntityGroup().dereference();
            	}else{
            		ei.dereference();
            	}
            } else if (obj instanceof EANFoundation) {
				((EANFoundation) obj).dereference();
			}
        }*/
    }

    /**
     * validate
     *
     * @param _eanAttr
     * @param _strValue
     *  @author David Bigelow
     */
    public abstract void validate(EANAttribute _eanAttr, String _strValue);

    /**
    * Return the date/time this class was generated
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return "$Id: EANBusinessRuleException.java,v 1.20 2013/03/18 20:26:11 wendy Exp $";
    }

    /**
     * validate
     *
     * @param _eanAttr
     * @param _strValue
     *  @author David Bigelow
     */
    public abstract void validate(EANMetaAttribute _eanMA, MetaFlagMaintItem _mi, String _strValue);

}
