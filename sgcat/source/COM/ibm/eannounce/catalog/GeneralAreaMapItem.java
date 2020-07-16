/*
* $Log: GeneralAreaMapItem.java,v $
* Revision 1.2  2011/05/05 11:21:33  wendy
* src from IBMCHINA
*
* Revision 1.1.1.1  2007/06/05 02:09:20  jingb
* no message
*
* Revision 1.1.1.1  2006/03/30 17:36:29  gregg
* Moving catalog module from middleware to
* its own module.
*
* Revision 1.8  2005/06/21 17:58:51  joan
* add CountryList in gamap table
*
* Revision 1.7  2005/05/22 23:04:36  dave
* Added CollectionId
* addind Catalog Interval
* Placed enterprise in the Gami
*
* Revision 1.6  2005/05/19 03:20:48  dave
* adding getReference concept and changing DDL abit
* to remove the not nulls
*
* Revision 1.5  2005/03/25 17:33:26  dave
* adding GeneralAreaMapItem to contructors
*
* Revision 1.4  2005/03/24 21:38:03  roger
* make them pretty
*
* Revision 1.3  2005/03/24 20:25:21  roger
* s/b close
*
* Revision 1.2  2005/03/24 20:06:06  roger
* Basic
*
* Revision 1.1  2005/03/24 18:24:48  roger
* New classes
*
*/
package COM.ibm.eannounce.catalog;

public class GeneralAreaMapItem {
  String m_strEnterprise = null;
  String m_strGeneralArea = null;
  String m_strCountry = null;
  String m_strLanguage = null;
  int m_iNLSID = -1;
  String m_strCountryList = null;

  public GeneralAreaMapItem(String _strEnterprise,  String _strGeneralArea, String _strCountry, String _strLanguage, int _iNLSID, String _strCountryList) {
  	m_strEnterprise = _strEnterprise;
  	m_strGeneralArea = _strGeneralArea;
    m_strCountry = _strCountry;
    m_strLanguage = _strLanguage;
    m_iNLSID = _iNLSID;
    m_strCountryList = _strCountryList;
  }
  public String getEnterprise() {
	return m_strEnterprise;
  }
  public String getGeneralArea() {
    return m_strGeneralArea;
  }
  public String getCountry() {
    return m_strCountry;
  }
  public String getLanguage() {
    return m_strLanguage;
  }
  public int getNLSID() {
    return m_iNLSID;
  }
  public String getCountryList() {
    return m_strCountryList;
  }
  public final String toString() {
      return m_strEnterprise + ":" + m_strGeneralArea + ":" + m_strCountry + ":" + m_strLanguage + ":" + m_iNLSID + ":" + m_strCountryList;
  }
}
