@echo off
rem startStandaloneInstance.cmd
rem -----------------------------------------------------------------
rem Startet eine Wildfly-Instanz. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd %1
rem SET JBOSS_BASE_DIR=%WILDFLY_INSTANCE_PATH%
pushd
cd %WILDFLY_INSTANCE_PATH%
%WILDFLY_HOME%\bin\standalone.bat -Djboss.server.base.dir=%WILDFLY_INSTANCE_PATH%
popd
endlocal
exit 0