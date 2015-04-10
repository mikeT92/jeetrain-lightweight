rem @echo off
rem createStandaloneInstance.cmd
rem -----------------------------------------------------------------
rem Erstellt eine neue WildFly-Standalone-Instanz. 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd %1
echo Erstelle nun die Wildfly-Instanz %INSTANCE_NAME%.
xcopy %WILDFLY_HOME%\standalone\*.* %WILDFLY_INSTANCE_ROOT%\%INSTANCE_NAME% /s /v /e 
endlocal

