/*
 * Created on Feb 14, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.objects;


/**
 * EANComparable
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface EANComparable {

    //enforce a separate key for compare - this way objects can have different sort vals!
    /**
     * toCompareString
     *
     * @return
     *  @author David Bigelow
     */
    String toCompareString();

    //set the String representing the field to compare by
    /**
     * setCompareField
     *
     * @param _s
     *  @author David Bigelow
     */
    void setCompareField(String _s);

    //get the String representing the field to compare by
    /**
     * getCompareField
     *
     * @return
     *  @author David Bigelow
     */
    String getCompareField();

    //get the String representing the UniqueKey -- this will probably already be defined per EANObject
    /**
     * getKey
     *
     * @return
     *  @author David Bigelow
     */
    String getKey();
}
