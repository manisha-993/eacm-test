#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sqlcli1.h>
#include "samputil.h"

char database[SQL_MAX_DSN_LENGTH + 1] ;
char user[MAX_UID_LENGTH + 1] ;
char password[MAX_PWD_LENGTH + 1] ;

int main( int argc, char * argv[] ) {
   SQLHANDLE henv;
   SQLHANDLE hdbc;
   SQLHANDLE hstmt;
   SQLRETURN rc;
   SQLCHAR * stmt1 = ( SQLCHAR * ) "CALL GBL2028 ( ? )" ;
   SQLCHAR * stmt2 = ( SQLCHAR * ) "SELECT name FROM sysibm.systables" ;

   /* Declare Local Variables for parameters */
   SQLINTEGER ReturnStatus = 0;  /* Bound to parameter marker in stmt1 */
   SQLINTEGER ReturnStatusInd = 0;  /* Indicator variable for ReturnStatus */

   if ( argc != 4 )
      {
      printf( "\nUSAGE: %s database userid passwd\n\n",argv[0]) ;
      return( 1 ) ;
      }

   strcpy(database,argv[1] ) ;
   strcpy(user,argv[2] ) ;
   strcpy(password,argv[3] ) ;

   /* allocate an handles and connect to database */
   rc = SQLAllocHandle( SQL_HANDLE_ENV, SQL_NULL_HANDLE, &henv ) ;
   if ( rc != SQL_SUCCESS ) return( terminate( henv, rc ) ) ;
   SQLAllocHandle(SQL_HANDLE_DBC, henv, &hdbc);
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   SQLConnect(hdbc
             ,(unsigned char *)database
             ,SQL_NTS
             ,(unsigned char *)user
             ,SQL_NTS
             ,(unsigned char *)password
             ,SQL_NTS
             );
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   if ( rc != SQL_SUCCESS ) return( terminate( henv, rc ) ) ;

   /* Execute stored procedure */
   rc = SQLAllocHandle( SQL_HANDLE_STMT, hdbc, &hstmt ) ;
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   printf("CALL Stored Procedure GBL2028\n\n");
   ReturnStatus = 0L;
   ReturnStatusInd = SQL_NO_NULLS;

   rc = SQLPrepare(hstmt, stmt1, SQL_NTS);
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;
   rc = SQLBindParameter(hstmt
                        ,1
                        ,SQL_PARAM_INPUT_OUTPUT
                        ,SQL_INTEGER, SQL_INTEGER
                        ,0
                        ,0
                        ,&ReturnStatus
                        ,0
                        ,&ReturnStatusInd
                        );
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;
   SQLExecute(hstmt);
   if (rc != SQL_SUCCESS & rc != SQL_SUCCESS_WITH_INFO)
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;
   printf("1. ReturnStatus should be 0\n");
   printf("---------------------------\n");
   if (ReturnStatusInd == SQL_NULL_DATA) /* Check for null value */
      printf("ReturnStatus = NULL\n");
   else
      printf("ReturnStatus = %d\n\n", ReturnStatus );
   printf("2. Correct result set contains (3) date values; now, forever, and epoch\n");
   printf("-----------------------------------------------------------------------\n");
   rc = print_results(hstmt);
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;
   rc = SQLFreeHandle( SQL_HANDLE_STMT, hstmt ) ;
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;

   rc = SQLAllocHandle( SQL_HANDLE_STMT, hdbc, &hstmt ) ;
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   printf("\n\nNOTE:\nThis test of GBL028 does not rely upon procedure registration.\n");
   printf("If the VB client is unable to execute stored procs, but this program can,\n");
   printf("the most likely cause is due to failed 'registration'.\n");
   printf("In that case, rerun the 'register' script.\n");
   printf("\n");

   rc = SQLFreeHandle( SQL_HANDLE_STMT, hstmt ) ;
   CHECK_HANDLE( SQL_HANDLE_STMT, hstmt, rc ) ;
   rc = SQLDisconnect( hdbc ) ;
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   rc = SQLFreeHandle( SQL_HANDLE_DBC, hdbc ) ;
   CHECK_HANDLE( SQL_HANDLE_DBC, hdbc, rc ) ;
   rc = SQLFreeHandle( SQL_HANDLE_ENV,  henv ) ;
   if ( rc != SQL_SUCCESS ) return( terminate( henv, rc ) ) ;
   return( SQL_SUCCESS ) ;
}

