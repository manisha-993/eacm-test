//
//$Log
//
package COM.ibm.eannounce.pmsync;

/**
 * The Level0Comp class represents a BOM component
 * from ProductManager (PM) that is a Level 1 part number
 * in OPICM. This class contains the attributes item number,
 * basic name, and m_strDescription.
 *
 * @author Tom Nelson
 * @version 1.0
 */

public class Level0Comp {
   public String m_strItemNumber;
   public String m_strBasicName;
   public String m_strDescription;


   /**
    * This constructor sets the Item number, basic name, and
    * m_strDescription to the values passed in.
    *
    * @param m_strItemNumber         Item number
    * @param m_strBasicName          Basic name
    * @param m_strDescription        Item m_strDescription
    */
   public Level0Comp(String _strItemNumber, String _strBasicName, String _strDescription) {
       m_strItemNumber = _strItemNumber;
       m_strBasicName = _strBasicName;
       m_strDescription = _strDescription;
   }


   /**
    * This method is used for debugging purposes and when called
    * will print the contents of the Level0Comp object.
    */
   public void print() {
     System.out.println("Level0 Comp: m_strItemNumber=" + m_strItemNumber + " m_strBasicName=" +
                                      m_strBasicName + " m_strDescription=" + m_strDescription);
   } // print method


} // Level0Comp
