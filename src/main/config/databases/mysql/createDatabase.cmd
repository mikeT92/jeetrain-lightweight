rem @echo off
rem createDatabase.cmd
rem -----------------------------------------------------------------
rem Erstellt eine neue Datenbakn fuer jeeintro inklusive aller Nutzer.
rem Erwartet das Passwort des MySQL root users als ersten Parameter. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
echo Erstelle nun die Datenbank
%MYSQL_HOME%\mysql
xcopy %WILDFLY_HOME%\standalone\*.* %WILDFLY_INSTANCE_ROOT%\%INSTANCE_NAME% /s /v /e 
endlocal

