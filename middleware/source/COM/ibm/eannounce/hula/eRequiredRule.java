//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eRequiredRule.java,v $
// Revision 1.6  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.5  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.4  2004/08/30 18:40:55  dave
// adding rollup rule
//
// Revision 1.3  2004/08/27 20:59:00  dave
// adding Valfrom to all objects
//
// Revision 1.2  2004/08/23 21:53:44  dave
// syntax
//
// Revision 1.1  2004/08/23 21:39:36  dave
// syntax
//
// Revision 1.3  2004/08/23 19:50:59  dave
// super
//
// Revision 1.2  2004/08/23 18:02:38  dave
// syntax issues
//
// Revision 1.1  2004/08/23 17:50:00  dave
// new Concat basic rule
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;

/*
* This is a basic rule..
*/
public class eRequiredRule extends eBasicRule {

  static final long serialVersionUID = 20011106L;

  /*
  * Version info
  */
  public String getVersion() {
    return new String("$Id: eRequiredRule.java,v 1.6 2008/01/31 21:05:17 wendy Exp $");
  }

    protected eRequiredRule (EANFoundation _ef, String _strEntityType, String _strHeritage, String _strAttributeCode,String _strDesc, String _strTest, String _strPass, String _strFail, String _strValFrom, String _strTriggerFC) throws SQLException, MiddlewareRequestException, MiddlewareException {
      super(_ef, _strEntityType, _strHeritage, _strAttributeCode, _strDesc, _strTest, _strPass, _strFail, _strValFrom, _strTriggerFC);
      m_iClassType = REQUIREDRULE_TYPE;
    }

}
