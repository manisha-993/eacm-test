@echo off
REM script to get files needed for autoupdate.  It generates a new version.jar and Opicm.jar using the latest 
REM middleware.jar.  Version.java should be committed to cvs after generation
REM Pass in anything to rebuild the eaServer.jar file.

REM setup variables

SET OLDDIR=%CD%

SET BLD_EASERVER=%1%
SET JUICVS=C:\dev\JUI
SET AUTOUPDATE=C:\autoUpdate
SET RSA="C:\RSA Workspaces\main\middleware\source"
SET JAVACMD="C:\Program Files\IBM\Java142\bin\javac"
SET JARCMD="C:\Program Files\IBM\Java142\bin\jar"
SET JUI_CP=.;%AUTOUPDATE%\jre\lib\ext\version.jar;%RSA%\jsse.jar;%AUTOUPDATE%\jre\lib\ext\middleware.jar;%JUICVS%\jars\jspell2n_java2.jar;%JUICVS%\jars\jspell2s_java2.jar;%JUICVS%\jars\xerces.jar;%JUICVS%\jars\CommRes.jar;%JUICVS%\jars\STComm.jar;%JUICVS%\jars\STMeeting.jar;%JUICVS%\jars\poi-3.0.1-FINAL-20070705.jar

SET OPICM_SRC=com\ibm\transform\oim\eacm\xml\editor com\ibm\eannounce com\ibm\eannounce\dialogpanels
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\eforms com\ibm\eannounce\eforms\action com\ibm\eannounce\eforms\edit com\ibm\eannounce\eforms\editform
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\eforms\editor com\ibm\eannounce\eforms\editor\simple com\ibm\eannounce\eforms\navigate
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\eforms\navigate\tree com\ibm\eannounce\eforms\table com\ibm\eannounce\eforms\toolbar
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\einterface com\ibm\eannounce\eobjects com\ibm\eannounce\epanels com\ibm\eannounce\erend
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\eresource com\ibm\eannounce\eserver com\ibm\eannounce\exception com\ibm\eannounce\progress
SET OPICM_SRC=%OPICM_SRC% com\ibm\eannounce\sametime com\ibm\eannounce\ui com\elogin


SET EASERVER=com\ibm\eannounce\eserver\ChatAction*.class com\ibm\eannounce\eserver\Deployer*.class com\ibm\eannounce\eserver\EChatClientIO*.class
SET EASERVER=%EASERVER% com\ibm\eannounce\eserver\EComparator*.class com\ibm\eannounce\eserver\EServerProperties*.class
SET EASERVER=%EASERVER% com\ibm\eannounce\eserver\RemoteControl*.class com\ibm\eannounce\eserver\Updater*.class 
SET EASERVER=%EASERVER% com\ibm\eannounce\eserver\UpdateStatus*.class com\ibm\eannounce\eserver\Worker*.class com\ibm\eannounce\progress\EProgress*.class
SET EASERVER=%EASERVER% com\ibm\eannounce\sametime\AbstractClientUI*.class com\ibm\eannounce\sametime\EancChatFactory*.class com\ibm\eannounce\sametime\EChatFactory*.class com\ibm\eannounce\sametime\MyChatFactory*.class com\ibm\eannounce\sametime\SametimeClientUI*.class
SET EASERVER=%EASERVER% com\elogin\EAccess.class com\elogin\EAccess$*.class com\elogin\EAccessConstants*.class 


SET EXIT=0

REM get latest middleware.jar
echo Getting new middleware.jar...
ftp -s:.\ftp_script_getmw
rem IF %ERRORLEVEL% NEQ 0 goto error


REM copy source files here
cd %AUTOUPDATE%
echo Copying dtd files...
xcopy /q %JUICVS%\eacmsrc\*.dtd
echo Copying ent files...
xcopy /q %JUICVS%\eacmsrc\*.ent 
echo Copying properties files...
xcopy /q %JUICVS%\eacmsrc\*.properties 
echo Copying xml editor image files...
mkdir images
xcopy /q %JUICVS%\eacmsrc\images\*.gif images\*

mkdir com
cd com
echo Copying source files...
xcopy /s /q %JUICVS%\eacmsrc\com .
IF %ERRORLEVEL% NEQ 0 goto error

cd %AUTOUPDATE%

REM delete all old files
if exist Updates\eaServer.tmp del Updates\eaServer.tmp
if exist jars\Opicm.jar del jars\Opicm.jar
if exist jre\lib\ext\version.jar del jre\lib\ext\version.jar
for /D %%i in (%OPICM_SRC%) do call :deleteclass "%%i"

REM build version.jar
echo Creating new Version file and jar...
cd com\ibm\eannounce\version
copy Version.java %JUICVS%\eacmsrc\com\ibm\eannounce\version\Version.java.old
copy %JUICVS%\scripts\reversion.bat .
call .\reversion
IF %ERRORLEVEL% NEQ 0 goto error

%JAVACMD% Version.java

REM put this new file into cvs
copy Version.java %JUICVS%\eacmsrc\com\ibm\eannounce\version
cd %AUTOUPDATE%

%JARCMD% -cf version.jar com\ibm\eannounce\version\Version.class
move version.jar %AUTOUPDATE%\jre\lib\ext

REM compile the classes
echo Compiling source...
for /D %%i in (%OPICM_SRC%) do call :compilejava "%%i"
IF %EXIT% NEQ 0 goto done

REM build the Opicm.jar file
echo Building Opicm.jar file...
%JARCMD%  -cf Opicm.jar *.dtd *.ent *.properties images\*.gif com\ibm\eannounce\eresource\*.gif com\ibm\eannounce\eresource\*.jpg com\ibm\eannounce\eresource\*.html
IF %ERRORLEVEL% NEQ 0 goto error
for /D %%i in (%OPICM_SRC%) do call :buildeacmsrc "%%i"
move Opicm.jar %AUTOUPDATE%\jars


REM build the eaServer.jar file
if defined BLD_EASERVER goto bldeajar

:CLEANUP

cd %AUTOUPDATE%

echo Cleaning up build directories...
REM delete the com dir
if exist %AUTOUPDATE%\com rd /s /q com

REM delete the files needed for Opicm.jar
if exist %AUTOUPDATE%\*.dtd del %AUTOUPDATE%\*.dtd
if exist %AUTOUPDATE%\*.ent del %AUTOUPDATE%\*.ent 
if exist %AUTOUPDATE%\*.properties del %AUTOUPDATE%\*.properties 
REM delete the images dir (this is used by xmleditor)
if exist %AUTOUPDATE%\images rd /s /q images

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
%JARCMD% -uf Opicm.jar %~1\*.class
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

