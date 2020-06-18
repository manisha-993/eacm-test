package COM.ibm.opicmpdh.middleware;

import swat.*;

public class TestLDAP {
  public static void main(String args[]) {
    if (args.length != 3) {
      System.out.println("USAGE:\n");
      System.out.println("  java TestLDAP <ldap_server> <email> <pw>\n");
      System.out.println("  <ldap_server> = swat.bluepages.ibm.com for testing");
      System.out.println("                  and bluepages.ibm.com for production");
    } else {
      ReturnCode rc = cwa.authenticate(args[0], args[1], args[2]);
      System.out.println("code: " + rc.getCode());
      System.out.println("message: " + rc.getMessage());
    }
  }
}
