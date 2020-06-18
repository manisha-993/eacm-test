//{{{ Log
//$Log: QueueGroup.java,v $
//Revision 1.2  2005/03/10 20:42:47  dave
//JTest daily ritual
//
//Revision 1.1  2003/02/05 01:08:59  gregg
//added interval start/end dates AND changed class name to QueueGroup (was QueueList)
//
//Revision 1.6  2002/08/07 00:09:04  gregg
//fix in secondary constructor
//
//Revision 1.5  2002/08/06 23:46:22  gregg
//added logic for retrieiving various sets of Queue items
//
//Revision 1.4  2002/08/06 00:14:14  gregg
//setStatusDesc on Queue objects
//
//Revision 1.3  2002/08/05 20:10:19  gregg
//minor formatting
//
//Revision 1.2  2002/08/05 20:06:00  gregg
//some commenting
//
//Revision 1.1  2002/08/05 20:02:27  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.util.Hashtable;
import java.sql.ResultSet;
import java.sql.SQLException;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/**
 * Represents a list of Queue records for one QueueType/OPWGID 
 */
public class QueueGroup extends EANMetaEntity {

    //also store Queues by status
    private Hashtable m_hashReady = null;
    private Hashtable m_hashComplete = null;
    private Hashtable m_hashFailed = null;
    private IntervalItem m_intervalItem = null;

    /**
     * FIELD
     */
    public static final int READY_STATUS = 0;
    /**
     * FIELD
     */
    public static final int COMPLETE_STATUS = 1;
    /**
     * FIELD
     */
    public static final int FAILED_STATUS = 2;

    /**
     * Create an empty QueueGroup
     *
     * @param _em
     * @param _oProfile
     * @param _intervalItem
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public QueueGroup(EANMetaEntity _em, Profile _oProfile, IntervalItem _intervalItem) throws MiddlewareRequestException {
        super(_em, _oProfile, _oProfile.getOPWGID() + _intervalItem.getQueueType());
        setIntervalItem(_intervalItem);
        m_hashReady = new Hashtable();
        m_hashComplete = new Hashtable();
        m_hashFailed = new Hashtable();
    }

    /**
     * Create a QueueGroup object
     *
     * @param _em
     * @param _db
     * @param _oProfile
     * @param _intervalItem
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException 
     */
    public QueueGroup(EANMetaEntity _em, Database _db, Profile _oProfile, IntervalItem _intervalItem) throws SQLException, MiddlewareException, MiddlewareRequestException {

        this(_em, _oProfile, _intervalItem);

        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7523(new ReturnStatus(-1), getProfile().getOPWGID(), getQueueType(), getIntervalItem().getStartDate(), getIntervalItem().getEndDate());
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {

                IntervalItem intervalItem = null;
                QueueItem q = null;

                int iOPWGID = rdrs.getColumnInt(row, 0);
                String strQueue = rdrs.getColumn(row, 1);
                int iStatus = rdrs.getColumnInt(row, 2);
                String strStatusDesc = rdrs.getColumn(row, 3);
                String strEntType = rdrs.getColumn(row, 4);
                int iEntID = rdrs.getColumnInt(row, 5);
                String strPushDate = rdrs.getColumn(row, 6);
                String strPullDate = rdrs.getColumn(row, 7);
                String strIntervalStartDate = rdrs.getColumn(row, 8);
                String strIntervalEndDate = rdrs.getColumn(row, 9);
                _db.debug(D.EBUG_SPEW, "gbl7523 answers:" + iOPWGID + ":" + strQueue + ":" + iStatus + ":" + strStatusDesc + ":" + strEntType + ":" + iEntID + ":" + strPushDate + ":" + strPullDate + ":" + strIntervalStartDate + ":" + strIntervalEndDate);

                intervalItem = new IntervalItem(null, getProfile(), getQueueType(), strIntervalStartDate, strIntervalEndDate);
                q = new QueueItem(this, getProfile(), intervalItem, iStatus, strEntType, iEntID, strPushDate, strPullDate);

                q.setStatusDesc(strStatusDesc);
                putQueueItem(q);
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
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
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        return toString();
    }

    /**
     * rearrange the list so that it is sorted alphabetically by the specified type
     *
     * @concurrency $none
     * @param _iSortType
     * @param _bAscending 
     */
    public synchronized void sortList(int _iSortType, boolean _bAscending) {
        QueueItem[] aQ = new QueueItem[getQueueCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getQueueCount(); i++) {
            QueueItem q = getQueueItem(i);
            q.setSortType(_iSortType);
            aQ[i] = q;
        }
        Arrays.sort(aQ, ec);
        resetData();
        for (int i = 0; i < aQ.length; i++) {
            QueueItem q = aQ[i];
            putData(q);
        }
        return;
    }

    /**
     * Retrieve a new QueueGroup containing only queues of a given status.
     * Note that the same Queue'd EntityItem may be in the list under multiple statuses
     *
     * @return QueueGroup
     * @param _iStatus 
     */
    public QueueGroup getQueuesByStatus(int _iStatus) {
        QueueGroup ql = null;
        try {
            ql = new QueueGroup((EANMetaEntity) getParent(), getProfile(), getIntervalItem());
        } catch (MiddlewareRequestException exc) {
            exc.printStackTrace();
        }
        for (int i = 0; i < getQueueCount(); i++) {
            QueueItem q = getQueueItem(i);
            if (q.getStatus() == _iStatus) {
                ql.putData(q);
            }
        }
        return ql;
    }

    /**
     * There can potentially be many records for one entityitem in a queue.
     * Retrieve only the most recent records.
     * Also apply some logic for these EntityItem records w/ multiple status's:
     *  1) display 'Ready' over anything else -- these are 'on queue' regardless of any other states.
     *  2) display most recent pulldate for 'Complete' or 'Failed'
     *
     * @return QueueGroup
     */
    public QueueGroup getMostRecentQueues() {
        QueueGroup ql = null;
        try {
            ql = new QueueGroup((EANMetaEntity) getParent(), getProfile(), getIntervalItem());
        } catch (MiddlewareRequestException exc) {
            exc.printStackTrace();
        }
        for (int i = 0; i < getQueueCount(); i++) {
            QueueItem q = getQueueItem(i);
            String strKey = q.getHashKey();
            QueueItem qReady = (QueueItem) m_hashReady.get(strKey);
            // 1)
            if (qReady != null) {
                ql.putQueueItem(qReady);
                continue;
            } else { // 2)
                QueueItem qComplete = (QueueItem) m_hashComplete.get(strKey);
                QueueItem qFailed = (QueueItem) m_hashFailed.get(strKey);
                if (qComplete != null && qFailed == null) {
                    ql.putQueueItem(qComplete);
                    continue;
                } else if (qFailed != null && qComplete == null) {
                    ql.putQueueItem(qFailed);
                } else if (qComplete != null && qFailed != null) {
                    String strCompletePullDate = qComplete.getPullDate();
                    String strFailedPullDate = qFailed.getPullDate();
                    if (strCompletePullDate.compareTo(strFailedPullDate) > 0) {
                        ql.putQueueItem(qComplete);
                        continue;
                    } else if (strCompletePullDate.compareTo(strFailedPullDate) < 0) {
                        ql.putQueueItem(qFailed);
                        continue;
                    }
                }
            }
        }
        return ql;
    }

    /**
     * getQueueItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public QueueItem getQueueItem(int _i) {
        return (QueueItem) getData(_i);
    }

    /**
     * getQueueCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getQueueCount() {
        return getDataCount();
    }

    private void setIntervalItem(IntervalItem _intervalItem) {
        m_intervalItem = _intervalItem;
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
     * getQueueType
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueType() {
        return getIntervalItem().getQueueType();
    }

    /**
     * putQueueItem
     *
     * @param _q
     *  @author David Bigelow
     */
    protected void putQueueItem(QueueItem _q) {
        putData(_q);
        //store queues by status
        if (_q.getStatus() == READY_STATUS) {
            m_hashReady.put(_q.getHashKey(), _q);
        } else if (_q.getStatus() == COMPLETE_STATUS) {
            m_hashComplete.put(_q.getHashKey(), _q);
        } else if (_q.getStatus() == FAILED_STATUS) {
            m_hashFailed.put(_q.getHashKey(), _q);
        }
    }

}
