@echo off
rem removeDatabaseWithUsers.cmd
rem -----------------------------------------------------------------
rem Löscht die Datenbank fuer jeetrain inklusive aller Nutzer.
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
echo Lösche nun die Datenbank
%MYSQL_HOME%\bin\mysql -v -P 13306 -u root -p < sql\removeDatabaseWithUsers.sql
endlocal

