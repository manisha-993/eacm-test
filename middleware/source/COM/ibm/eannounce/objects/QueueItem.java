//{{{ Log
//$Log: QueueItem.java,v $
//Revision 1.7  2005/03/10 23:21:25  dave
//do not throw column conversion in int/long
//
//Revision 1.6  2005/03/10 20:42:47  dave
//JTest daily ritual
//
//Revision 1.5  2005/01/18 21:46:51  dave
//more parm debug cleanup
//
//Revision 1.4  2003/10/29 20:22:22  dave
//trace
//
//Revision 1.3  2003/09/04 19:36:28  dave
//syntax fixes
//
//Revision 1.2  2003/09/04 18:55:13  dave
//adding Enterprise and OPID negativity
//
//Revision 1.1  2003/02/05 00:59:04  gregg
//added use of IntervalItem AND changed class name to QueueItem (was Queue)
//
//Revision 1.7  2002/08/08 22:17:03  gregg
//getCompareField(), setCompareField(String) methods required by EANComparable inteface
//
//Revision 1.6  2002/08/06 22:18:48  gregg
//getHashKey() method
//
//Revision 1.5  2002/08/06 00:11:23  gregg
//statusDesc
//
//Revision 1.4  2002/08/05 23:26:44  gregg
//key must include status to ensure uniqueness
//
//Revision 1.3  2002/08/05 20:10:19  gregg
//minor formatting
//
//Revision 1.2  2002/08/05 19:48:58  gregg
//this class now extends EANDataFoundation
//
//Revision 1.1  2002/08/05 18:08:13  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/**
 * Represents one record in the QUEUE table
 * Only Status, PullDate may be publically set/updated
 */
public class QueueItem extends EANMetaFoundation {

    private String m_strEntityType = null;
    private String m_strPushDate = null;
    private String m_strPullDate = null;
    private int m_iEntityID = -1;
    private int m_iStatus = -1;
    private int m_iCurrSortType = -1;
    private IntervalItem m_intervalItem = null;

    /**
     * FIELD
     */
    public static final int SORT_BY_ENTTYPE = 1;
    /**
     * FIELD
     */
    public static final int SORT_BY_ENTID = 2;
    /**
     * FIELD
     */
    public static final int SORT_BY_STATUS = 3;
    /**
     * FIELD
     */
    public static final int SORT_BY_PUSHDATE = 4;
    /**
     * FIELD
     */
    public static final int SORT_BY_PULLDATE = 5;
    /**
     * FIELD
     */
    public static final int SORT_BY_QUEUETYPE = 6;
    /**
     * FIELD
     */
    public static final int SORT_BY_INTERVALSTART = 7;
    /**
     * FIELD
     */
    public static final int SORT_BY_INTERVALEND = 8;

    /**
     * Create a Queue object
     *
     * @param _em
     * @param _oProfile
     * @param _intervalItem
     * @param _iStatus
     * @param _strEntityType
     * @param _iEntityID
     * @param _strPushDate
     * @param _strPullDate
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public QueueItem(EANMetaEntity _em, Profile _oProfile, IntervalItem _intervalItem, int _iStatus, String _strEntityType, int _iEntityID, String _strPushDate, String _strPullDate) throws SQLException, MiddlewareException, MiddlewareRequestException {

        super(_em, _oProfile, _oProfile.getOPWGID() + _intervalItem.getQueueType() + _iStatus + _strEntityType + _iEntityID);
        setStatus(_iStatus);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
        setPushDate(_strPushDate);
        setPullDate(_strPullDate);
        setIntervalItem(_intervalItem);
        return;
    }

    /**
     * Perform a simple test of this class
     *
     * @param args 
     */
    public static void main(String[] args) {
    }

    /**
     * get the sort key
     *
     * @return String
     */
    public String toCompareString() {
        if (getSortType() == SORT_BY_ENTTYPE) {
            return getEntityType();
        }
        if (getSortType() == SORT_BY_ENTID) {
            return String.valueOf(getEntityID());
        }
        if (getSortType() == SORT_BY_STATUS) {
            return String.valueOf(getStatus());
        }
        if (getSortType() == SORT_BY_QUEUETYPE) {
            return getQueueType();
        }
        if (getSortType() == SORT_BY_PUSHDATE) {
            return getPushDate();
        }
        if (getSortType() == SORT_BY_PULLDATE) {
            return getPullDate();
        }
        if (getSortType() == SORT_BY_INTERVALSTART) {
            return getIntervalItem().getStartDate();
        }
        if (getSortType() == SORT_BY_INTERVALEND) {
            return getIntervalItem().getEndDate();
        }
        //this shouldn't occur
        return toString();
    }

    /**
     * setCompareField
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setCompareField(String _s) {
        try {
            m_iCurrSortType = Integer.parseInt(_s);
        } catch (NumberFormatException nfe) {
           // nfe.printStackTrace();
        }
    }

    /**
     * getCompareField
     *
     * @return
     *  @author David Bigelow
     */
    public String getCompareField() {
        return String.valueOf(m_iCurrSortType);
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return getKey();
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        return toString();
    }

    /**
     * updateToPdh
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
     * @throws COM.ibm.eannounce.objects.WorkflowException
     *  @author David Bigelow
     */
    public void updateToPdh(Database _db) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, WorkflowException {
        int iOPWGID = getProfile().getOPWGID();
        int iOPID = getProfile().getOPID();
        String strEnterprise = getProfile().getEnterprise();
        String strQueueType = getQueueType();
        String strEntityType = getEntityType();
        int iEntityID = getEntityID();
        int iStatus = getStatus();
        String strPullDate = getPullDate();
        try {
            _db.callGBL7522(new ReturnStatus(-1), strEnterprise, iOPWGID, iOPID, strQueueType, iStatus, strEntityType, iEntityID, strPullDate, getIntervalItem().getStartDate(), getIntervalItem().getEndDate());
            _db.freeStatement();
            _db.isPending();
        } catch (Exception exc) {
            _db.debug(D.EBUG_ERR, "Queue.updateToPdh method: " + exc.toString());
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    /**
     * EntityType + EntityID
     *
     * @return String
     */
    protected String getHashKey() {
        return getEntityType() + ":" + getEntityID();
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
     * getPushDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getPushDate() {
        return m_strPushDate;
    }

    /**
     * getPullDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getPullDate() {
        return m_strPullDate;
    }

    /**
     * getEntityID
     *
     * @return
     *  @author David Bigelow
     */
    public int getEntityID() {
        return m_iEntityID;
    }

    /**
     * getStatus
     *
     * @return
     *  @author David Bigelow
     */
    public int getStatus() {
        return m_iStatus;
    }

    /**
     * getQueueType
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueType() {
        return getIntervalItem().getQueueType();
    }

    /**
     * getSortType
     *
     * @return
     *  @author David Bigelow
     */
    public int getSortType() {
        return m_iCurrSortType;
    }

    /**
     * getStatusDesc
     *
     * @return
     *  @author David Bigelow
     */
    public String getStatusDesc() {
        return getLongDescription();
    }

    /**
     * getIntervalItem
     *
     * @return
     *  @author David Bigelow
     */
    public IntervalItem getIntervalItem() {
        return m_intervalItem;
    }

    /**
     * setEntityType
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setEntityType(String _s) {
        m_strEntityType = _s;
    }

    /**
     * setPushDate
     *
     * @param _s
     *  @author David Bigelow
     */
    protected void setPushDate(String _s) {
        m_strPushDate = _s;
    }

    /**
     * setPullDate
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setPullDate(String _s) {
        m_strPullDate = _s;
    }

    /**
     * setEntityID
     *
     * @param _i
     *  @author David Bigelow
     */
    protected void setEntityID(int _i) {
        m_iEntityID = _i;
    }

    /**
     * setStatus
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setStatus(int _i) {
        m_iStatus = _i;
    }

    /**
     * setSortType
     *
     * @param _i
     *  @author David Bigelow
     */
    public void setSortType(int _i) {
        m_iCurrSortType = _i;
    }

    /**
     * setStatusDesc
     *
     * @param _s
     *  @author David Bigelow
     */
    public void setStatusDesc(String _s) {
        putLongDescription(_s);
    }

    /**
     * setIntervalItem
     *
     * @param _intervalItem
     *  @author David Bigelow
     */
    protected void setIntervalItem(IntervalItem _intervalItem) {
        m_intervalItem = _intervalItem;
    }

}
