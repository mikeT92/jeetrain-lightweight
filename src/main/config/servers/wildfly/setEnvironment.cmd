set WILDFLY_HOME=Z:\tools\wildfly\wildfly-9.0.2.Final
set WILDFlY_INSTANCE_ROOT=Z:\Data\wildfly
set WILDFLY_DEFAULT_INSTANCE_NAME=wf90_jeetrain_dev
if [%1]==[] (
	echo Kein Instanzname angegeben: benutze voreingestellten Instanznamen %WILDFLY_DEFAULT_INSTANCE_NAME%
	set WILDFLY_INSTANCE_NAME=%WILDFLY_DEFAULT_INSTANCE_NAME%
) else (
	set WILDFLY_INSTANCE_NAME=%1
)
set WILDFLY_INSTANCE_PATH=%WILDFLY_INSTANCE_ROOT%\%WILDFLY_INSTANCE_NAME%