//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eRollupRule.java,v $
// Revision 1.3  2008/01/31 21:05:17  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2004/10/15 19:40:19  gregg
// switch'n it up w/ class type checks
//
// Revision 1.1  2004/08/30 18:40:55  dave
// adding rollup rule
//
//

package COM.ibm.eannounce.hula;

import java.sql.SQLException;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;


/*
* This is a basic rule..
*/
public class eRollupRule extends eBasicRule {

  static final long serialVersionUID = 20011106L;

  /*
  * Version info
  */
  public String getVersion() {
    return new String("$Id: eRollupRule.java,v 1.3 2008/01/31 21:05:17 wendy Exp $");
  }

    protected eRollupRule (EANFoundation _ef, String _strEntityType, String _strHeritage, String _strAttributeCode,String _strDesc, String _strTest, String _strPass, String _strFail, String _strValFrom, String _strTriggerFC) throws SQLException, MiddlewareRequestException, MiddlewareException {
      super(_ef, _strEntityType, _strHeritage, _strAttributeCode, _strDesc, _strTest, _strPass, _strFail, _strValFrom, _strTriggerFC);
      m_iClassType = ROLLUPRULE_TYPE;
    }

}
