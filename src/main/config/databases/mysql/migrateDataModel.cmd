@echo off
rem migrateDataModel.cmd
rem -----------------------------------------------------------------
rem Migriert das Schema jeetrain_db auf die aktuellste Version.
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
echo Migriere nun das Schema jeetrain_db
pushd
cd ..\..\..\..\..
%MAVEN_HOME%\bin\mvn org.flywaydb:flyway-maven-plugin:migrate --batch-mode --errors --fail-fast 
popd
endlocal

