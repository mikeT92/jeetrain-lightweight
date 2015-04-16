@echo off
rem cleanDataModel.cmd
rem -----------------------------------------------------------------
rem Loescht das komplette Datenmodell aus dem jeetrain_db.
rem -----------------------------------------------------------------
setlocal
call setEnvironment.cmd
echo Loeschen nun das Datenmodell aus dem Schema jeetrain_db
pushd
cd ..\..\..\..\..
%MAVEN_HOME%\bin\mvn org.flywaydb:flyway-maven-plugin:clean --batch-mode --errors --fail-fast 
popd
endlocal

