//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: SortUtil.java,v $
// Revision 1.10  2008/01/31 22:55:00  wendy
// Cleanup RSA warnings
//
// Revision 1.9  2003/09/25 18:09:22  bala
// sort relators based on the contents of their E1 or E2
// list of attributes
//
// Revision 1.8  2003/09/25 00:54:24  bala
// Method to sort an array of entityItems...keep this uptodate with
// current object model
//
// Revision 1.7  2001/09/04 17:39:47  bala
// re-committing for Eannounce V1.0
//
// Revision 1.6  2001/03/26 16:33:23  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:10  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:25  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.middleware;

import java.util.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.objects.*;

/**
 *  Various sort methods for PDH objects, arrays, etc.
 *
 *@author     Administrator
 *@created    September 24, 2003
 */
public class SortUtil {

  /**
   *  Sorts an array of integers.
   *
   *@param  a              Description of the Parameter
   *@param  lo0            Description of the Parameter
   *@param  hi0            Description of the Parameter
   *@exception  Exception
   */
  void QuickSort(int a[], int lo0, int hi0) throws Exception {

    int lo = lo0;
    int hi = hi0;
    int mid;

    if (hi0 > lo0) {

      /* Arbitrarily establishing partition element as the midpoint of
* the array.
*/
      mid = a[(lo0 + hi0) / 2];

// loop through the array until indices cross
      while (lo <= hi) {

        /* find the first element that is greater than or equal to
* the partition element starting from the left Index.
*/
        while ((lo < hi0) && (a[lo] < mid)) {
          ++lo;
        }

        /* find an element that is smaller than or equal to
* the partition element starting from the right Index.
*/
        while ((hi > lo0) && (a[hi] > mid)) {
          --hi;
        }

// if the indexes have not crossed, swap
        if (lo <= hi) {
          swap(a, lo, hi);

// pause
// pause();
          ++lo;
          --hi;
        }
      }

      /* If the right index has not reached the left side of array
* must now sort the left partition.
*/
      if (lo0 < hi) {
        QuickSort(a, lo0, hi);
      }

      /* If the left index has not reached the right side of array
* must now sort the right partition.
*/
      if (lo < hi0) {
        QuickSort(a, lo, hi0);
      }
    }
  }


  /**
   *  Description of the Method
   *
   *@param  a  Description of the Parameter
   *@param  i  Description of the Parameter
   *@param  j  Description of the Parameter
   */
  private void swap(int a[], int i, int j) {
    int T;

    T = a[i];
    a[i] = a[j];
    a[j] = T;
  }


  /**
   *  Sorts an array of primitive integers.
   *
   *@param  a              an integer array
   *@exception  Exception  Description of the Exception
   */
  public void sort(int a[]) throws Exception {
    QuickSort(a, 0, a.length - 1);
  }


  /**
   *  Description of the Method
   *
   *@param  a  Description of the Parameter
   */
  public void sort(String a[]) {

    if (a == null) {
      return;
    }

    for (int i = a.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
        if (a[j].compareTo(a[j + 1]) > 0) {
          String T = a[j];

          a[j] = a[j + 1];
          a[j + 1] = T;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Sorts a Vector of middleware Attribute objects by their attribute codes.
   *
   *@param  vctAttributes  a vector of middleware attributes
   */
  public void sortAttributes(Vector vctAttributes) {

    Attribute att1 = null;
    Attribute att2 = null;

    if (vctAttributes == null) {
      return;
    }

    for (int i = vctAttributes.size(); --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
        att1 = (Attribute) vctAttributes.elementAt(j);
        att2 = (Attribute) vctAttributes.elementAt(j + 1);

        if (att1.m_strAttributeCode.compareTo(att2.m_strAttributeCode) > 0) {
          vctAttributes.setElementAt(att1, j + 1);
          vctAttributes.setElementAt(att2, j);

          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Sorts a Middleware ReturnDataResultSet.
   *
   *@param  rdRs                 Description of the Parameter
   *@param  sortColumns          Description of the Parameter
   */
  public void sort(ReturnDataResultSet rdRs, int[] sortColumns) {

    ReturnDataRow rdRow1 = null;
    ReturnDataRow rdRow2 = null;
   // ReturnDataRow rdRowTemp = null;
    String str1 = "";
    String str2 = "";

    if (rdRs == null) {
      return;
    }

    for (int i = rdRs.size(); --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
        rdRow1 = rdRs.getRow(j);
        rdRow2 = rdRs.getRow(j + 1);
        str1 = "";
        str2 = "";

// go get the strings to be compared
        for (int k = 0; k < sortColumns.length; k++) {
          str1 += rdRow1.getColumn(sortColumns[k]);
          str2 += rdRow2.getColumn(sortColumns[k]);
        }

        if (str1.compareTo(str2) > 0) {
          rdRs.setElementAt(rdRow1, j + 1);
          rdRs.setElementAt(rdRow2, j);

          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Sorts an array of Entity objects by one or more of the entity properties.
   *
   *@param  e                Description of the Parameter
   *@param  strSortProperty  Description of the Parameter
   */
  public void sort(Entity e[], String[] strSortProperty) {

    Entity T = null;
    String str1 = null;
    String str2 = null;

    if (e == null) {
      return;
    }

    for (int i = e.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {

// go get the strings to be compared
        str1 = getSortProperty(e[j], strSortProperty);
        str2 = getSortProperty(e[j + 1], strSortProperty);

        if (str1.compareTo(str2) > 0) {
          T = e[j];
          e[j] = e[j + 1];
          e[j + 1] = T;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Gets the sortProperty attribute of the SortUtil object
   *
   *@param  e                Description of the Parameter
   *@param  strSortProperty  Description of the Parameter
   *@return                  The sortProperty value
   */
  private String getSortProperty(Entity e, String[] strSortProperty) {

    String strSortValue = "";

    for (int k = 0; k < strSortProperty.length; k++) {
      if (strSortProperty[k].equals("m_strEntityType")) {
        strSortValue += e.m_strEntityType;
      } else if (strSortProperty[k].equals("m_iEntityID")) {
        strSortValue += e.m_iEntityID;
      } else if (strSortProperty[k].equals("m_strDescription")) {
        strSortValue += e.m_strDescription;
      } else if (strSortProperty[k].equals("m_iOPENID")) {
        strSortValue += e.m_cbControlBlock.m_iOPENID;
      }
    }

    return strSortValue;
  }


  /**
   *  Sorts an array of Entity objects by one or more of referenced Attribute
   *  member values.
   *
   *@param  aentity    Description of the Parameter
   *@param  eProperty  Description of the Parameter
   *@param  eAttrCode  Description of the Parameter
   */
  public void sort(Entity aentity[], String eProperty[], String eAttrCode[]) {

   // Object obj = null;
    String s = null;
    String s1 = null;

    if (aentity == null) {
      return;
    }

    for (int i = aentity.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
        s = getSortProperty(aentity[j], eProperty, eAttrCode);
        s1 = getSortProperty(aentity[j + 1], eProperty, eAttrCode);

        if (s.compareTo(s1) > 0) {
          Entity entity = aentity[j];

          aentity[j] = aentity[j + 1];
          aentity[j + 1] = entity;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Gets the sortProperty attribute of the SortUtil object
   *
   *@param  ent        Description of the Parameter
   *@param  eProperty  Description of the Parameter
   *@param  eAttrCode  Description of the Parameter
   *@return            The sortProperty value
   */
  private String getSortProperty(Entity ent, String eProperty[], String eAttrCode[]) {

    String s = "";
    String s1 = "";

    for (int i = 0; i < eProperty.length; i++) {
      if (eProperty[i].equals("m_strAttributeCode")) {
        for (int j = 0; j < eAttrCode.length; j++) {
          for (int k = 0; k < ent.m_vctAttributes.size(); k++) {
            Attribute attribute = (Attribute) ent.m_vctAttributes.elementAt(k);

            s1 = attribute.m_strAttributeCode;

//Cannot sort blobs so weed them out.
            if (eAttrCode[j].equals(s1) && !(ent.m_vctAttributes.elementAt(k) instanceof Blob)) {
              s = s + attribute.m_strAttributeValue;
            }
          }
        }

        s = s + ent.m_strDescription;
      }
    }

    return s;
  }


  /**
   *  Sorts a vector alphabetically
   *
   *@param  unsorted  a Vector of non-alphabetized Strings
   *@return           a sorted Vector.
   */
  public Vector alphabetizeVector(Vector unsorted) {

    Vector sorted = new Vector();
    int size = unsorted.size();//unsorted vector loses elements, so set a constant
    boolean lowest = true;

    while (sorted.size() != size) {
      for (int i = 0; i < unsorted.size(); i++) {
        lowest = true;//reset

        String comp1 = (String) unsorted.elementAt(i);

        for (int j = 0; (j < unsorted.size()) && lowest; j++) {
          String comp2 = (String) unsorted.elementAt(j);

          if (comp1.compareTo(comp2) > 0) {//if there is a lower string -
            lowest = false;//then set to false and break from loop
          }
        }

        if (lowest == true) {//then it is lowest, add it to the end of sorted
          sorted.addElement(comp1);
          unsorted.removeElementAt(i);
        }
      }
    }

    return sorted;
  }


  /**
   *  Description of the Method
   *
   *@param  _e             Array of Entities
   *@param  _strAttrCodes  Array of Attributes whose values will be used to sort the entities 
   */
  public void sort(EntityItem _e[], String[] _strAttrCodes) {

    EntityItem eiTemp = null;
    String str1 = null;
    String str2 = null;

    if (_e == null) {
      return;
    }

    for (int i = _e.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {

        // go get the strings to be compared
        str1 = getAttributeValue(_e[j], _strAttrCodes);
        str2 = getAttributeValue(_e[j + 1], _strAttrCodes);

        if (str1.compareTo(str2) > 0) {
          eiTemp = _e[j];
          _e[j] = _e[j + 1];
          _e[j + 1] = eiTemp;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Gets the attributeValue attribute of the SortUtil object
   *
   *@param  _ei            the EntityItem
   *@param  _strAttrCodes  Array of attributes to get
   *@return                The attributeValue value
   */
  private String getAttributeValue(EntityItem _ei, String[] _strAttrCodes) {
    String strRetval = "";
    String strAttrCode=null;
    EntityGroup eg=_ei.getEntityGroup();
    for (int i = 0; i < _strAttrCodes.length; i++) {
      strAttrCode = _strAttrCodes[i];
      String strAttrValue = null;

      EANAttribute EANAttr = _ei.getAttribute(strAttrCode);

      if (EANAttr == null) {
        System.out.println("SortUtil:getAttributeValue:entityattribute is null");
        return null;
      }
      strAttrValue = EANAttr.toString();

      EANMetaAttribute eanMetaAtt = eg.getMetaAttribute(strAttrCode);
      switch (eanMetaAtt.getAttributeType().charAt(0)) {
          case 'F':
          case 'U':
          case 'S':
            MetaFlag[] mfAttr = (MetaFlag[]) EANAttr.get();
            boolean bFirst = strRetval.equals("") ? true : false;
            for (int j = 0; j < mfAttr.length; j++) {
              if (mfAttr[j].isSelected()) {
                if (bFirst) {
                  bFirst = false;
                  strAttrValue = mfAttr[j].getFlagCode();
                } else {
                  strAttrValue += "," + mfAttr[j].getFlagCode();//Separate multiple values with Commas
                }
              }
            }
            break;
          default:
      }
      strRetval += strAttrValue;        //Add to return value
      
    }
    return strRetval;
  }

  /*
  * Can be used to sort relators based on the contents of their E1 or E2 EntityItems.
  * useful to sort geo related offerings based on their geo's if u pass the geotooffering
  * relator. Although this checks whether each element in the array is a relator, it does
  * not return an error if the element is not, but just sorts it based on the attribute code.
  * could be useful to have an array of mixed relators and entities ?
  */
  public void sort(EntityItem _eiRelator[], String[] _strAttrCodes,boolean _bDownlink) {

    EntityItem eiTemp = null;
    String str1 = null;
    String str2 = null;
    EntityItem eiLink = null;
    EntityGroup eg = null;

    if (_eiRelator == null) {
      return;
    }

    for (int i = _eiRelator.length; --i >= 0; ) {
      boolean swapped = false;

      for (int j = 0; j < i; j++) {
         
        eiTemp = _eiRelator[j];
        eg = eiTemp.getEntityGroup();
        if (eg.isRelator()) {     //Do we really have a relator?
          eiLink = _bDownlink ? (EntityItem) eiTemp.getDownLink(0) : (EntityItem) eiTemp.getUpLink(0); 
        } else {
          eiLink = _eiRelator[j];
        }
        // go get the strings to be compared
        str1 = getAttributeValue(eiLink, _strAttrCodes);

        eiTemp = _eiRelator[j+1];
        eg = eiTemp.getEntityGroup();
        if (eg.isRelator()) {     //Do we really have a relator?
          eiLink = _bDownlink ? (EntityItem) eiTemp.getDownLink(0) : (EntityItem) eiTemp.getUpLink(0); 
        } else {
          eiLink = _eiRelator[j + 1];
        }
        str2 = getAttributeValue(eiLink, _strAttrCodes);

        if (str1.compareTo(str2) > 0) {
          eiTemp = _eiRelator[j];
          _eiRelator[j] = _eiRelator[j + 1];
          _eiRelator[j + 1] = eiTemp;
          swapped = true;
        }
      }

      if (!swapped) {
        return;
      }
    }
  }


  /**
   *  Return the date/time this class was generated
   *
   *@return    the date/time this class was generated
   */
  public final String getVersion() {
    return new String("$Id: SortUtil.java,v 1.10 2008/01/31 22:55:00 wendy Exp $");
  }
}// end of class

