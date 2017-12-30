rem setup-domain.cmd
rem --------------------------------------------------------------------------
rem Creates a new glassfish server domain (i.e. instance) in the specified 
rem domain folder with the specified domain name starting with the given
rem port base.
rem --------------------------------------------------------------------------
@echo off
setlocal 
call asadmin create-domain --domaindir z:\data\glassfish\domains --portbase 8000 --checkports true %1
copy z:\tools\lib\jdbc\mysql*.jar z:\data\glassfish\domains\%1\lib
call asadmin start-domain --domaindir z:\data\glassfish\domains %1
call asadmin --port 8048 create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlDataSource --restype javax.sql.DataSource --ping --wrapjdbcobjects true --isolationlevel read-committed --property portNumber=3306:password=fwpss2018:user=jeetrain:serverName=192.168.99.100:databaseName=jeetrain_db JEETRAIN_POOL
call asadmin --port 8048 create-jdbc-resource --connectionpoolid JEETRAIN_POOL jdbc/JEETRAIN_DATASOURCE
call asadmin --port 8048 create-auth-realm --classname=com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm --property=jaas-context=jdbcRealm:datasource-jndi=jdbc\/JEETRAIN_DATASOURCE:user-table=T_USER:user-name-column=USER_ID:password-column=PASSWORD:group-table=T_ROLE:group-table-user-name-column=USER_ID:group-name-column=ROLE_NAME:digest-algorithm=SHA-256:encoding=Base64:charset=UTF-8: --target=server-config JEETRAIN_REALM
call asadmin stop-domain --domaindir z:\data\glassfish\domains %1
endlocal