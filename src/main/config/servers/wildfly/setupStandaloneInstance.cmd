@echo off
rem setupStandaloneInstance.cmd
rem -----------------------------------------------------------------
rem Konfigurierte eine nackte Wildfly-Instanz für JEETRAIN. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd %1

set NOPAUSE=true

echo Füge Admin-User hinzu
call %WILDFLY_HOME%\bin\add-user.bat -sc %WILDFLY_INSTANCE_PATH%\configuration -u wfadmin -p wildfly82 -e

echo Starte Server für weitere Konfiguration
start startStandaloneInstance.cmd %1
echo Bitte warten, bis der Wildlfy in der anderen Konsole erfolgreich hochgefahren ist
pause

echo Installiere MySQL JDBC Treiber
call %WILDFLY_HOME%\bin\jboss-cli --connect --file=addMySqlJdbcDriver.cli

echo Stoppe Server
call %WILDFLY_HOME%\bin\jboss-cli --connect command=shutdown

endlocal
