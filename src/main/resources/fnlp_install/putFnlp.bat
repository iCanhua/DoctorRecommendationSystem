E:
cd BaiduNetdiskDownload
pause
mvn install:install-file -Dfile=fnlp-core-2.1-SNAPSHOT.jar -DgroupId=com.fan -DartifactId=native_fnlp_core -Dversion=1.0 -Dpackaging=jar
pause
mvn install:install-file -Dfile=commons-cli-1.2.jar -DgroupId=com.fan -DartifactId=native_fnlp_commons -Dversion=1.0 -Dpackaging=jar
pause
mvn install:install-file -Dfile=fnlp-app-2.1-SNAPSHOT.jar -DgroupId=com.fan -DartifactId=native_fnlp_app -Dversion=1.0 -Dpackaging=jar
pause
mvn install:install-file -Dfile=trove4j-3.0.3.jar -DgroupId=com.fan -DartifactId=native_fnlp_trove4j -Dversion=1.0 -Dpackaging=jar