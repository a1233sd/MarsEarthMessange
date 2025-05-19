@echo off
set MAVEN_WRAPPER_DISABLE=0

if "%MAVEN_SKIP_WRAPPER" == "true" goto end
if "%MAVEN_WRAPPER_DISABLE%" == "1" goto end

setlocal
set DIR=%~dp0
set WRAPPER_JAR="%DIR%\.mvn\wrapper\maven-wrapper.jar"
set MVNW_REPOURL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.1.1/maven-wrapper-3.1.1.jar"

rem Make sure the wrapper jar exists
if not exist %WRAPPER_JAR% (
  echo [INFO] Downloading Maven Wrapper...
  mkdir "%DIR%\.mvn\wrapper" > nul 2>&1
  powershell -Command "Invoke-WebRequest -Uri %MVNW_REPOURL% -OutFile %WRAPPER_JAR%" > nul 2>&1
)

java -jar %WRAPPER_JAR% %*

:end
