rem @echo off
rem addMySqlJbdcDriver.cmd
rem -----------------------------------------------------------------
rem Fuegt einer bestehenden WildFly-Installation den MySQL JDBC 
rem Treiber hinzu. 
rem Achtung: WildFly muss gestartet sein! 
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
set WILDFLY_MYSQL_JDBC_MODULE_PATH=%WILDFLY_HOME%\modules\system\layers\base\com\mysql\main
rmdir /q /s %WILDFLY_MYSQL_JDBC_MODULE_PATH%
mkdir %WILDFLY_MYSQL_JDBC_MODULE_PATH%
copy .\jdbc\mysql\*.* %WILDFLY_MYSQL_JDBC_MODULE_PATH%
rem %WILDFLY_HOME%\bin\jboss-cli --connect --file addMySqlJdbcDriver.cli
%WILDFLY_HOME%\bin\jboss-cli --connect /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.jdbc.Driver)
 

