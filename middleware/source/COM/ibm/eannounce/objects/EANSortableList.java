//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
//{{{ Log
//$Log: EANSortableList.java,v $
//Revision 1.9  2005/02/15 18:42:24  dave
//More JTest cleanup
//
//Revision 1.8  2004/10/21 16:49:54  dave
//trying to share compartor
//
//Revision 1.7  2002/11/18 18:28:10  gregg
//allow use of wildcards on performFilter()
//
//Revision 1.6  2002/09/03 19:53:36  gregg
//putObject, removeObject methods
//
//Revision 1.5  2002/08/08 23:37:56  gregg
//setObjectFiltered(), isObjectFiltered() methods
//
//Revision 1.4  2002/08/08 21:57:04  gregg
//some commenting..
//
//Revision 1.3  2002/08/08 21:56:01  gregg
//getObjectCount(), getObject(int) methods
//
//Revision 1.2  2002/08/08 20:10:34  gregg
//getFilterTypesArray(), getSortTypesArray()
//
//Revision 1.1  2002/08/08 19:31:20  gregg
//initial load
//
//}}}

package COM.ibm.eannounce.objects;

/**
* provide an interface for lists of EANComparable objects which can be sorted and filtered by attributes
*/
public interface EANSortableList {

    //Accessor to sort & filter info
    /**
     * getSFInfo
     *
     * @return
     *  @author David Bigelow
     */
    SortFilterInfo getSFInfo();

    //perform filter on the list
    /**
     * performFilter
     *
     * @param _bUseWildcards
     *  @author David Bigelow
     */
    void performFilter(boolean _bUseWildcards);

    //perform sort on the list
    /**
     * performSort
     *
     *  @author David Bigelow
     */
    void performSort();

    //get the array of filter types
    /**
     * getFilterTypesArray
     *
     * @return
     *  @author David Bigelow
     */
    String[] getFilterTypesArray();

    //get the array of sort types
    /**
     * getSortTypesArray
     *
     * @return
     *  @author David Bigelow
     */
    String[] getSortTypesArray();

    // is the object filtered out?
    /**
     * isObjectFiltered
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    boolean isObjectFiltered(int _i);

    // set filter on an indexed item
    /**
     * setObjectFiltered
     *
     * @param _i
     * @param _b
     *  @author David Bigelow
     */
    void setObjectFiltered(int _i, boolean _b);

    // === these allow access to List elements ===

    //get the number of items in the list
    /**
     * getObjectCount
     *
     * @return
     *  @author David Bigelow
     */
    int getObjectCount();

    //get the EANComparable object at (i)
    /**
     * getObject
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    EANComparable getObject(int _i);

    //put an EANComparable object in the list
    /**
     * putObject
     *
     * @param _ec
     *  @author David Bigelow
     */
    void putObject(EANComparable _ec);

    //remove an EANComparable object from the list
    /**
     * removeObject
     *
     * @param _ec
     *  @author David Bigelow
     */
    void removeObject(EANComparable _ec);
}
