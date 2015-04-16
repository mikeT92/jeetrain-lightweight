@echo off
rem addMySqlJbdcDriver.cmd
rem -----------------------------------------------------------------
rem Fuegt einer bestehenden WildFly-Installation alle 
rem DataSources hinzu. 
rem Achtung: WildFly muss gestartet sein! 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
%WILDFLY_HOME%\bin\jboss-cli --connect --file=addDataSources.cli
 

