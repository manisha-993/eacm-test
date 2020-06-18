/*******************************************************
**
** Licensed Materials - Property of IBM
**
** (C) COPYRIGHT International Business Machines Corp. 1995, 1997
** All Rights Reserved.
**
** US Government Users Restricted Rights - Use, duplication or
** disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
**
** file = samputil.h
** Utility functions used in CLI examples
**
********************************************************/

#define MAX_UID_LENGTH 18
#define MAX_PWD_LENGTH 30

/* Macro for common Error Checking using check_error from samputil.c */
#define CHECK_HANDLE( htype, hndl, RC ) if ( RC != SQL_SUCCESS ) \
   { check_error( htype, hndl, RC, __LINE__, __FILE__ ) ; }

#define INIT_UID_PWD if ( argc == 4 ) { \
       strncpy( ( char * ) server, ( const char * ) argv[1], SQL_MAX_DSN_LENGTH ); \
       strncpy( ( char * ) uid, ( const char * ) argv[2], MAX_UID_LENGTH ) ; \
       strncpy( ( char * ) pwd, ( const char * ) argv[3], MAX_PWD_LENGTH ) ; \
     } \
     else { \
         printf( ">Enter Server Name:\n" ) ; gets( ( char * ) server ) ; \
         printf( ">Enter User Name:\n" ) ; gets( ( char * ) uid ) ; \
         printf( ">Enter Password:\n" ) ; gets( ( char * ) pwd ) ; \
     }

SQLRETURN check_error( SQLSMALLINT, SQLHANDLE, SQLRETURN, int, char * ) ;

SQLRETURN DBconnect( SQLHANDLE, SQLHANDLE * ) ;

SQLRETURN print_connect_info( SQLHANDLE ) ;

SQLRETURN print_error( SQLSMALLINT, SQLHANDLE, SQLRETURN, int, char * ) ;

SQLRETURN print_results( SQLHANDLE ) ;

SQLRETURN prompted_connect( SQLHANDLE, SQLHANDLE * ) ;

SQLRETURN terminate( SQLHANDLE, SQLRETURN ) ;

