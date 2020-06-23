@echo off
REM script to get files needed for autoupdate.  It generates a new version.jar and EACM.jar using the latest 
REM middleware.jar.  Version.java should be committed to cvs after generation
REM Pass in anything to rebuild the eaServer.jar file.

REM setup variables

SET OLDDIR=%CD%

SET BLD_EASERVER=%1%
SET JUICVS="C:\dev32\workspace\JUIV6"
SET AUTOUPDATE=C:\autoUpdate
SET JAVACMD="C:\utils\Java60\sdk\bin\javac"
REM Java1.4.2 must be used to compile Version.class because server is not Java6
SET JAVA142CMD="C:\utils\Java142\sdk\bin\javac"
SET JARCMD="C:\utils\Java60\sdk\bin\jar"
SET JUI_CP=.;%AUTOUPDATE%\jre\lib\ext\version.jar;%AUTOUPDATE%\jre\lib\ext\middleware.jar;%JUICVS%\jars\jspell2n_java2.jar;%JUICVS%\jars\jspell2s_java2.jar;%JUICVS%\jars\xerces.jar;%JUICVS%\jars\poi-3.0.1-FINAL-20070705.jar;%JUICVS%\jars\jOpenDocument-1.3.jar

SET EACM_SRC=com\ibm\transform\oim\eacm\xml\editor com\ibm\eacm
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\actions com\ibm\eacm\nav com\ibm\eacm\tree com\ibm\eacm\mtrx
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\wused com\ibm\eacm\edit
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\preference com\ibm\eacm\table com\ibm\eacm\toolbar
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\tabs com\ibm\eacm\mw com\ibm\eacm\cart com\ibm\eacm\ui
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\rend com\ibm\eacm\editor com\ibm\eacm\objects
SET EACM_SRC=%EACM_SRC% com\ibm\eacm\navform com\ibm\eacm\edit\form com\ibm\eacm\edit\formgen


SET EASERVER_SRC=com\ibm\eannounce\eserver 

SET EASERVER=com\ibm\eannounce\eserver\Deployer*.class com\ibm\eannounce\eserver\Updater*.class
SET EASERVER=%EASERVER% com\ibm\eannounce\eserver\UpdateStatus*.class com\ibm\eannounce\eserver\JarResources*.class
SET EASERVER=%EASERVER% com\ibm\eannounce\eserver\JarClassLoader*.class com\ibm\eacm\objects\EACMGlobals.class
      

SET EXIT=0

REM get latest middleware.jar
echo Getting new middleware.jar...
ftp -s:.\ftp_script_getmw
rem IF %ERRORLEVEL% NEQ 0 goto error


REM copy source files here
cd %AUTOUPDATE%
echo Copying dtd files...
xcopy /q /y %JUICVS%\eacmsrc\*.dtd
echo Copying ent files...
xcopy /q /y %JUICVS%\eacmsrc\*.ent 
echo Copying properties files...
xcopy /q /y %JUICVS%\eacmsrc\*.properties 
echo Copying image files...
mkdir images
xcopy /q /y %JUICVS%\eacmsrc\images\* images\*
echo Copying resources...
mkdir resources
xcopy /q /y %JUICVS%\eacmsrc\resources\* resources\*

REM copy properties files here
echo Copying Resource...
xcopy /q /y %JUICVS%\Resource\* Resource\*

mkdir com
cd com
echo Copying source files...
xcopy /s /q /y %JUICVS%\eacmsrc\com .
IF %ERRORLEVEL% NEQ 0 goto error

cd %AUTOUPDATE%

REM delete all old files
if exist Updates\eaServer.tmp del Updates\eaServer.tmp
if exist jars\EACM.jar del jars\EACM.jar
if exist jre\lib\ext\version.jar del jre\lib\ext\version.jar
for /D %%i in (%EACM_SRC%) do call :deleteclass "%%i"
for /D %%i in (%EASERVER_SRC%) do call :deleteclass "%%i"

REM build version.jar
echo Creating new Version file and jar...
cd com\ibm\eannounce\version
copy Version.java %JUICVS%\eacmsrc\com\ibm\eannounce\version\Version.java.old
copy %JUICVS%\scripts\reversion.bat .
call .\reversion
IF %ERRORLEVEL% NEQ 0 goto error

%JAVA142CMD% Version.java

REM put this new file into cvs
copy Version.java %JUICVS%\eacmsrc\com\ibm\eannounce\version
cd %AUTOUPDATE%

%JARCMD% -cf version.jar com\ibm\eannounce\version\Version.class
move version.jar %AUTOUPDATE%\jre\lib\ext

REM compile the classes
echo Compiling source...
for /D %%i in (%EACM_SRC%) do call :compilejava "%%i"
IF %EXIT% NEQ 0 goto done
for /D %%i in (%EASERVER_SRC%) do call :compilejava "%%i"
IF %EXIT% NEQ 0 goto done

REM build the EACM.jar file
echo Building EACM.jar file...
%JARCMD%  -cf EACM.jar *.dtd *.ent *.properties images\*.gif images\*.png images\*.jpg  resources\*.html resources\*.xml
IF %ERRORLEVEL% NEQ 0 goto error
for /D %%i in (%EACM_SRC%) do call :buildeacmsrc "%%i"
move EACM.jar %AUTOUPDATE%\jars


REM build the eaServer.jar file
if defined BLD_EASERVER goto bldeajar

:CLEANUP

cd %AUTOUPDATE%

echo Cleaning up build directories...
REM delete the com dir
if exist %AUTOUPDATE%\com rd /s /q com

REM delete the files needed for EACM.jar
if exist %AUTOUPDATE%\*.dtd del %AUTOUPDATE%\*.dtd
if exist %AUTOUPDATE%\*.ent del %AUTOUPDATE%\*.ent 
if exist %AUTOUPDATE%\*.properties del %AUTOUPDATE%\*.properties 
REM delete the images dir 
if exist %AUTOUPDATE%\images rd /s /q images
if exist %AUTOUPDATE%\resources rd /s /q resources

echo Remember to commit Version.java in cvs...
chdir /d %OLDDIR%
goto done


:deleteclass
if exist %~1\*.class del %~1\*.class
goto done

:compilejava
IF %EXIT% NEQ 0 goto done
echo Compiling %~1\*.java...
%JAVACMD% -classpath %JUI_CP% %~1\*.java
IF %ERRORLEVEL% NEQ 0 goto error
goto done

:buildeacmsrc
IF %EXIT% NEQ 0 goto done
%JARCMD% -uf EACM.jar %~1\*.class
IF %ERRORLEVEL% NEQ 0 goto error
goto done

:buildeaServerjar
IF %EXIT% NEQ 0 goto done
%JARCMD% -uf eaServer.jar %~1
IF %ERRORLEVEL% NEQ 0 goto error
goto done

:error
echo Error occurred, exiting
set EXIT=1
goto CLEANUP

:bldeajar
echo Building eaServer.jar file...
%JARCMD% -cf eaServer.jar ea-server.properties
IF %ERRORLEVEL% NEQ 0 goto error
for %%i in (%EASERVER%) do call :buildeaServerjar "%%i"
move eaServer.jar %AUTOUPDATE%\Updates\eaServer.tmp
goto CLEANUP

:DONE

