@echo off
rem addDatabaseWithUsers.cmd
rem -----------------------------------------------------------------
rem Erstellt eine neue Datenbank fuer jeetrain inklusive aller Nutzer.
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
echo Erstelle nun die Datenbank
%MYSQL_HOME%\bin\mysql -v -P 13306 -u root -p < sql\addDatabaseWithUsers.sql
endlocal

