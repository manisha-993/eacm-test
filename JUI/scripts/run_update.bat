setLocal
set /p VAR="Please enter name of update file:  "
.\jre\bin\java.exe -Djava.compiler=NONE -classpath .;.\eaServer.jar COM.ibm.eannounce.eServer.eUpdateObject "%VAR%"
endlocal
