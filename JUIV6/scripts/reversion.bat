@echo off
SET VERSIONFILE=.\Version.java
echo // Licensed Materials -- Property of IBM > %VERSIONFILE%
echo // >> %VERSIONFILE%
echo // (C) Copyright IBM Corp. 2005, 2013  All Rights Reserved. >> %VERSIONFILE%
echo // The source code for this program is not published or otherwise divested of >> %VERSIONFILE%
echo // its trade secrets, irrespective of what has been deposited with the U.S. Copyright office. >> %VERSIONFILE%
echo // >> %VERSIONFILE%
echo /* This is a generated file.  Do not modify. */ >> %VERSIONFILE%
echo package com.ibm.eannounce.version; >> %VERSIONFILE%
echo public class Version { >> %VERSIONFILE%
echo public static final String BUILD_DATE = "%DATE%%TIME%"; >> %VERSIONFILE%
echo public static String getVersion() { >> %VERSIONFILE%
echo return "Built on %DATE% at %TIME%"; >> %VERSIONFILE%
echo } >> %VERSIONFILE%
echo public static String getDate() { >> %VERSIONFILE%
echo return BUILD_DATE.substring(4); >> %VERSIONFILE%
echo } >> %VERSIONFILE%
echo } >> %VERSIONFILE%
