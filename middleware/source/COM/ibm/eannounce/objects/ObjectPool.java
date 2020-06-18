//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: ObjectPool.java,v $
// Revision 1.8  2005/03/08 23:15:47  dave
// Jtest checkins from today and last ngith
//
// Revision 1.7  2004/04/13 16:24:03  gregg
// clear() method; some D.ebugs (sync w/ v1.2)
//
// Revision 1.6  2004/01/12 21:06:31  dave
// i command you to compile!
//
// Revision 1.5  2004/01/12 20:56:11  dave
// some more syntax
//
// Revision 1.4  2004/01/12 20:26:15  dave
// breaking for performance Where Used I
//
// Revision 1.3  2004/01/08 20:36:47  dave
// syntax
//
// Revision 1.2  2004/01/08 19:41:41  dave
// syntax
//
// Revision 1.1  2004/01/08 19:17:31  dave
// new object pool technology
//
//

package COM.ibm.eannounce.objects;

import java.util.Hashtable;
import COM.ibm.opicmpdh.middleware.D;

/**
 * ObjectPool
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class ObjectPool {

    private static Hashtable c_hsh = null;
    private static ObjectPool c_pool = null;

    private ObjectPool() {
        c_hsh = new Hashtable();
        System.out.println("DWB: New Local Object Pool Cache");
    }

    /**
     * getInstance
     *
     * @return
     *  @author David Bigelow
     */
    public static ObjectPool getInstance() {
        if (c_pool == null) {
            c_pool = new ObjectPool();
        }
        return c_pool;
    }

    /**
     * clear
     *
     *  @author David Bigelow
     */
    public void clear() {
        D.ebug(D.EBUG_DETAIL, "ObjectPool.clear() called... (hash size = " + c_hsh.size() + ")...");
        c_hsh = new Hashtable();
        D.ebug(D.EBUG_DETAIL, "...ObjectPool.clear() done... (hash size = " + c_hsh.size() + ").");
    }

    /**
     * putActionList
     *
     * @param _al
     * @return
     *  @author David Bigelow
     */
    public ActionList putActionList(ActionList _al) {
        return (ActionList) c_hsh.put("ACTIONLIST:" + _al.getKey(), _al);
    }

    /**
     * containsActionList
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public boolean containsActionList(String _strKey) {
        return c_hsh.containsKey("ACTIONLIST:" + _strKey);
    }

    /**
     * getActionList
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public ActionList getActionList(String _strKey) {
        ActionList al = (ActionList) c_hsh.get("ACTIONLIST:" + _strKey);
        D.ebug(D.EBUG_DETAIL, "ObjectPool.getActionList(): request for \"" + _strKey + "\"...");
        D.ebug(D.EBUG_DETAIL, (al != null ? "ObjectPool.getActionList found w/ key of \"" + al.getKey() + "\"." : "ObjectPool.getActionList: No Object found w/ key of \"" + _strKey + "\"."));
        return al;
    }

    /**
     * putActionItem
     *
     * @param _ai
     * @return
     *  @author David Bigelow
     */
    public EANActionItem putActionItem(EANActionItem _ai) {
        return (EANActionItem) c_hsh.put("ACTIONITEM:" + _ai.getKey(), _ai);
    }

    /**
     * containsActionItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public boolean containsActionItem(String _strKey) {
        return c_hsh.containsKey("ACTIONITEM:" + _strKey);
    }

    /**
     * getActionItem
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public EANActionItem getActionItem(String _strKey) {
        EANActionItem ai = (EANActionItem) c_hsh.get("ACTIONITEM:" + _strKey);
        D.ebug(D.EBUG_DETAIL, "ObjectPool.getActionItem(): request for \"" + _strKey + "\"...");
        D.ebug(D.EBUG_DETAIL, (ai != null ? "ObjectPool.getActionItem found w/ key of \"" + ai.getKey() + "\"." : "ObjectPool.getActionItem: No Object found w/ key of \"" + _strKey + "\"."));
        return ai;
    }

}
