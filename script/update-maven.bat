::/*
:: * ===============================================================================================
:: *                 Copyright (C) 2013 - 2014 Martin Albedinsky [Wolf-ITechnologies]
:: * ===============================================================================================
:: *         Licensed under the Apache License, Version 2.0 or later (further "License" only).
:: * -----------------------------------------------------------------------------------------------
:: * You may use this file only in compliance with the License. More details and copy of this License
:: * you may obtain at
:: *
:: * 		http://www.apache.org/licenses/LICENSE-2.0
:: *
:: * You can redistribute, modify or publish any part of the code written within this file but as it
:: * is described in the License, the software distributed under the License is distributed on an
:: * "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF ANY KIND.
:: *
:: * See the License for the specific language governing permissions and limitations under the License.
:: * ===============================================================================================
:: */
:: -------------------------------------------------------------------------------------------------
::                    LIBRARY ARTIFACTS UPDATE SCRIPT FOR MAVEN LOCAL REPOSITORY
:: -------------------------------------------------------------------------------------------------
@ECHO OFF

:: =================================================================================================
:: Library specific parameters.
:: -------------------------------------------------------------------------------------------------
@SET LIBRARY_NAME=fragments
@SET LIBRARY_VERSION=3.6
:: =================================================================================================
:: Script global parameters.
:: -------------------------------------------------------------------------------------------------
:: Maven repository parameters.
@SET MAVEN_LOCAL_PATH=C:\Users\Martin\.m2\repository\
@SET MAVEN_LIBRARY_GROUP_PATH=%MAVEN_LOCAL_PATH%com\wit\android\support\
@SET MAVEN_LIBRARY_GROUP_ID=com.wit.android.support
@SET MAVEN_LIBRARY_JAR_SOURCES=%MAVEN_LIBRARY_GROUP_PATH%%LIBRARY_NAME%\%LIBRARY_VERSION%\%LIBRARY_NAME%-%LIBRARY_VERSION%-sources.jar
@SET MAVEN_LIBRARY_JAR_JAVADOC=%MAVEN_LIBRARY_GROUP_PATH%%LIBRARY_NAME%\%LIBRARY_VERSION%\%LIBRARY_NAME%-%LIBRARY_VERSION%-javadoc.jar
:: Library parameters
@SET LIBRARY_ARTIFACTS_DIR=..\artifacts\
:: Build the names of artifact files.
@SET LIBRARY_AAR=%LIBRARY_ARTIFACTS_DIR%%LIBRARY_NAME%-%LIBRARY_VERSION%.aar
@SET LIBRARY_JAR_SOURCES=%LIBRARY_ARTIFACTS_DIR%%LIBRARY_NAME%-%LIBRARY_VERSION%-sources.jar
@SET LIBRARY_JAR_JAVADOC=%LIBRARY_ARTIFACTS_DIR%%LIBRARY_NAME%-%LIBRARY_VERSION%-javadoc.jar
:: -------------------------------------------------------------------------------------------------
:: Save input parameter.
@SET PARAM=%1
:: -------------------------------------------------------------------------------------------------
:: Check action to perform.
IF [%PARAM%]==[-type] GOTO ListTypes
IF [%PARAM%]==[aar] GOTO Aar
IF [%PARAM%]==[support] GOTO Support
GOTO UnknownParameter
:ListTypes
ECHO.Use one of the types listed below:
ECHO.- aar (to load an aar file with compiled code)
ECHO.- support (to load all support artifacts: sources + documentation)
GOTO Finish
:: -------------------------------------------------------------------------------------------------
:: Load requested artifact file into the local MAVEN repository.
:: -------------------------------------------------------------------------------------------------
:Aar
ECHO.Loading the main library artifact file with compiled code into the MAVEN local repository ...
:: Aar with compiled source code and bundled resources.
IF EXIST %LIBRARY_AAR% (
    mvn install:install-file^
    -DgroupId=%MAVEN_LIBRARY_GROUP_ID%^
    -DartifactId=%LIBRARY_NAME%^
    -Dversion=%LIBRARY_VERSION%^
    -Dfile=%LIBRARY_AAR%^
    -Dpackaging=aar^
    -DgeneratePom=true
)
GOTO Finish
:: -------------------------------------------------------------------------------------------------
:Support
ECHO.Loading support artifact files (sources + documentation) into the MAVEN local repository ...
:: This actually only copies these jar files into the local MAVEN repository's directory.
:: Jar with documentation.
IF EXIST %LIBRARY_JAR_JAVADOC% (
    COPY %LIBRARY_JAR_JAVADOC% %MAVEN_LIBRARY_JAR_JAVADOC% /Y
)
:: Jar with sources.
IF EXIST %LIBRARY_JAR_SOURCES% (
    COPY %LIBRARY_JAR_SOURCES% %MAVEN_LIBRARY_JAR_SOURCES% /Y
)
ECHO.Loading of support artifact files successfully finished.
GOTO Finish
:: -------------------------------------------------------------------------------------------------
:UnknownParameter
ECHO.Unknown parameter type '%PARAM%'
ECHO.Use "-type" as parameter to list possible types of artifacts which can be laoded into the MAVEN local repository.
GOTO Finish
:: -------------------------------------------------------------------------------------------------
:Finish
:: -------------------------------------------------------------------------------------------------
ECHO ON