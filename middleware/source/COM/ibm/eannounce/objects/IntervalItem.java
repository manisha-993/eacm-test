//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: IntervalItem.java,v $
// Revision 1.6  2009/05/14 15:16:22  wendy
// Support dereference for memory release
//
// Revision 1.5  2005/03/11 22:42:53  dave
// removing some auto genned stuff
//
// Revision 1.4  2005/03/03 23:21:52  dave
// fixing small int problem and jtest
//
// Revision 1.3  2003/02/05 20:48:50  gregg
// include properties in dump
//
// Revision 1.2  2003/02/04 19:19:35  gregg
// containsInterval, containsDate methods to protected
//
// Revision 1.1  2003/02/04 00:50:48  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Validate;

/**
 * IntervalItem
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntervalItem extends EANMetaFoundation {

    /**
    * @serial
    */
    static final long serialVersionUID = 1L;

    private String m_strStartDate = null;
    private String m_strEndDate = null;
    private String m_strQueueType = null;

    protected void dereference(){
    	super.dereference();
    	m_strStartDate = null;
        m_strEndDate = null;
        m_strQueueType = null;
    }
    /**
     * IntervalItem
     *
     * @param _ig
     * @param _prof
     * @param _strQueueType
     * @param _strStartDate
     * @param _strEndDate
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    public IntervalItem(IntervalGroup _ig, Profile _prof, String _strQueueType, String _strStartDate, String _strEndDate) throws MiddlewareRequestException {
        super(_ig, _prof, _strQueueType + ":" + _strStartDate + ":" + _strEndDate);
        //let's prevent any problems w/ date 
        if (!Validate.isoDate(_strStartDate)) {
            throw new MiddlewareRequestException("Start Date MUST be in form \"yyyy-MM-dd-HH.mm.ss.SSSSSS\".");
        }
        if (!Validate.isoDate(_strEndDate)) {
            throw new MiddlewareRequestException("End Date MUST be in form \"yyyy-MM-dd-HH.mm.ss.SSSSSS\".");
        }
        setStartDate(_strStartDate);
        setEndDate(_strEndDate);
        setQueueType(_strQueueType);
    }

    /**
     * Start/End dates equal AND QueueType equal
     *
     * @return boolean
     * @param _intervalItem 
     */
    public boolean equals(IntervalItem _intervalItem) {
        return this.getStartDate().equals(_intervalItem.getStartDate()) && this.getEndDate().equals(_intervalItem.getEndDate()) && this.getQueueType().equals(_intervalItem.getQueueType());
    }

    /**
     * Does this Interval encompass the passed Interval?
     * i.e. MUST contain both start AND end dates
     *
     * @return boolean
     * @param _intervalItem 
     */
    protected boolean containsInterval(IntervalItem _intervalItem) {
        return this.getQueueType().equals(_intervalItem.getQueueType()) && this.containsDate(_intervalItem.getStartDate()) && this.containsDate(_intervalItem.getEndDate());
    }

    /**
     * Is the passed date within this interval? (passed date CAN fall ~ON~ start/end date -- ensuring equals passes as well)
     *
     * @return boolean
     * @param _strDate 
     */
    protected boolean containsDate(String _strDate) {
        return getStartDate().compareTo(_strDate) <= 0 && getEndDate().compareTo(_strDate) >= 0;
    }

    ///// MUTATORS ///////
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
     * getStartDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getStartDate() {
        return m_strStartDate;
    }

    /**
     * getEndDate
     *
     * @return
     *  @author David Bigelow
     */
    public String getEndDate() {
        return m_strEndDate;
    }

    /**
     * getQueueType
     *
     * @return
     *  @author David Bigelow
     */
    public String getQueueType() {
        return m_strQueueType;
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer sb = new StringBuffer("IntervalItem:" + getKey() + NEW_LINE);
        sb.append("getQueueType():" + getQueueType() + NEW_LINE);
        sb.append("getStartDate():" + getStartDate() + NEW_LINE);
        sb.append("getEndDate():" + getEndDate() + NEW_LINE);
        return sb.toString();
    }


}
