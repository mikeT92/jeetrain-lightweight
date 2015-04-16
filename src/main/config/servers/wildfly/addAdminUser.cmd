@echo off
rem addAdminUser.cmd
rem -----------------------------------------------------------------
rem Setzt den Admin-User fuer eine bestehende WildFly-Instanz. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd %1
%WILDFLY_HOME%\bin\add-user.bat -sc %WILDFLY_INSTANCE_PATH%\configuration -u wfadmin -p wildfly82 -e
 

