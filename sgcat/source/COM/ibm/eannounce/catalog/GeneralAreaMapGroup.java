/*
* $Log: GeneralAreaMapGroup.java,v $
* Revision 1.2  2011/05/05 11:21:35  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:20  jingb
* no message
*
* Revision 1.1.1.1  2006/03/30 17:36:29  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.4  2005/03/24 21:38:03  roger
* make them pretty
*
* Revision 1.3  2005/03/24 20:32:16  roger
* fix
*
* Revision 1.2  2005/03/24 20:25:21  roger
* s/b close
*
* Revision 1.1  2005/03/24 18:24:48  roger
* New classes
*
*/
package COM.ibm.eannounce.catalog;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

public class GeneralAreaMapGroup {
  private Vector m_vctVector = null;

  public GeneralAreaMapGroup() {
    m_vctVector = new Vector();
  }
  public void add(GeneralAreaMapItem _gami) {
    m_vctVector.add(_gami);
  }
  public int size() {
    return m_vctVector.size();
  }
  public GeneralAreaMapItem elementAt(int _i) {
    return (GeneralAreaMapItem) m_vctVector.elementAt(_i);
  }
  public Iterator iterator() {
    return m_vctVector.iterator();
  }
  public ListIterator listIterator() {
    return m_vctVector.listIterator();
  }
  public boolean isEmpty() {
    return m_vctVector.isEmpty();
  }
  public Enumeration elements() {
    return m_vctVector.elements();
  }
  public void clear() {
    m_vctVector.clear();
  }
}
