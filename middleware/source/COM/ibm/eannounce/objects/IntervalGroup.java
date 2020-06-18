//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: IntervalGroup.java,v $
// Revision 1.5  2005/03/03 22:39:32  dave
// JTest working and cleanup
//
// Revision 1.4  2003/02/06 17:45:04  gregg
// use getColumnDate in constructor
//
// Revision 1.3  2003/02/05 20:51:54  gregg
// include dump info
//
// Revision 1.2  2003/02/04 00:53:37  gregg
// rs.close() in constructor
//
// Revision 1.1  2003/02/04 00:50:48  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IntervalGroup
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntervalGroup extends EANMetaEntity {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strStartDate = null;
    private String m_strEndDate = null;
    private String m_strQueueType = null;

    /**
     * Default constructor -- look for all Intervals for a given QueueType
     *
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strQueueType 
     */
    public IntervalGroup(EANMetaFoundation _emf, Database _db, Profile _prof, String _strQueueType) throws MiddlewareException, SQLException {
        this(_emf, _db, _prof, _strQueueType, _db.getDates().getEpoch(), _db.getDates().getForever());
    }

    /**
     * Specify start/EndDates for a given QueueType
     *
     * @param _strStartDate
     * @param _strEndDate
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws java.sql.SQLException
     * @param _emf
     * @param _db
     * @param _prof
     * @param _strQueueType 
     */
    public IntervalGroup(EANMetaFoundation _emf, Database _db, Profile _prof, String _strQueueType, String _strStartDate, String _strEndDate) throws MiddlewareException, SQLException {
        super(_emf, _prof, _strQueueType + ":" + _strStartDate + ":" + _strEndDate);
        setStartDate(_strStartDate);
        setEndDate(_strEndDate);
        setQueueType(_strQueueType);
        try {
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            try {
                rs = _db.callGBL7537(new ReturnStatus(-1), _prof.getOPWGID(), _strQueueType, _strStartDate, _strEndDate);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int row = 0; row < rdrs.getRowCount(); row++) {
                String strStartDate = rdrs.getColumnDate(row, 0);
                String strEndDate = rdrs.getColumnDate(row, 1);
                putIntervalItem(new IntervalItem(this, getProfile(), getQueueType(), strStartDate, strEndDate));
            }
        } finally {
            _db.freeStatement();
            _db.isPending();
        }
    }

    ///// MUTATORS ///////
    private void putIntervalItem(IntervalItem _intervalItem) {
        putMeta(_intervalItem);
    }

    private void setStartDate(String _s) {
        m_strStartDate = _s;
    }

    private void setEndDate(String _s) {
        m_strEndDate = _s;
    }

    private void setQueueType(String _s) {
        m_strQueueType = _s;
    }

    ///// ACCESSORS
    /**
     * getIntervalItem
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public IntervalItem getIntervalItem(int _i) {
        return (IntervalItem) getMeta(_i);
    }

    /**
     * getIntervalItem
     *
     * @param _strQueueType
     * @param _strStartDate
     * @param _strEndDate
     * @return
     *  @author David Bigelow
     */
    public IntervalItem getIntervalItem(String _strQueueType, String _strStartDate, String _strEndDate) {
        return (IntervalItem) getMeta(_strQueueType + ":" + _strStartDate + ":" + _strEndDate);
    }

    /**
     * getIntervalItemCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getIntervalItemCount() {
        return getMetaCount();
    }

    private String getStartDate() {
        return m_strStartDate;
    }

    private String getEndDate() {
        return m_strEndDate;
    }

    private String getQueueType() {
        return m_strQueueType;
    }
    ///////////////
    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("IntervalGroup:" + getKey() + NEW_LINE);
        sb.append("getQueueType():" + getQueueType() + NEW_LINE);
        sb.append("getStartDate():" + getStartDate() + NEW_LINE);
        sb.append("getEndDate():" + getEndDate() + NEW_LINE);
        sb.append("getIntervalItemCount():" + getIntervalItemCount() + NEW_LINE);
        if (!_bBrief) {
            sb.append("--->Displaying Children:" + NEW_LINE);
            for (int i = 0; i < getIntervalItemCount(); i++) {
                sb.append(getIntervalItem(i).dump(_bBrief));
            }
        }
        return sb.toString();
    }

}
