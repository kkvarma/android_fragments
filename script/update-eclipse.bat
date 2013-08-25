:: /*
::  * =================================================================================
::  * Copyright (C) 2013 Martin Albedinsky [Wolf-ITechnologies]
::  * =================================================================================
::  * Licensed under the Apache License, Version 2.0 or later (further "License" only);
::  * ---------------------------------------------------------------------------------
::  * You may use this file only in compliance with the License. More details and copy
::  * of this License you may obtain at
::  *
::  * 		http://www.apache.org/licenses/LICENSE-2.0
::  *
::  * You can redistribute, modify or publish any part of the code written in this
::  * file but as it is described in the License, the software distributed under the
::  * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF
::  * ANY KIND.
::  *
::  * See the License for the specific language governing permissions and limitations
::  * under the License.
::  * =================================================================================
::  */
:: ------------------------------------------------------------------------------------
::    UPDATE SCRIPT FOR 'ANDROID FRAGMENTS' ECLIPSE BASED LIBRARY MODULE REPOSITORY
:: ------------------------------------------------------------------------------------
@ECHO OFF

:: ====================================================================================
:: Library specific parameters
:: ------------------------------------------------------------------------------------
@SET LIBRARY_NAME=android_fragments
:: ====================================================================================
:: Script global parameters
:: ------------------------------------------------------------------------------------
@SET LIBRARY_MAIN_DIR=..\%LIBRARY_NAME%\src\main
@SET LIBRARY_SRC_DIR=%LIBRARY_MAIN_DIR%\java
@SET LIBRARY_RES_DIR=%LIBRARY_MAIN_DIR%\res
@SET LIBRARY_EXAMPLES_MAIN_DIR=..\examples\src\main
@SET LIBRARY_EXAMPLES_SRC_DIR=%LIBRARY_EXAMPLES_MAIN_DIR%\java
@SET LIBRARY_EXAMPLES_RES_DIR=%LIBRARY_EXAMPLES_MAIN_DIR%\res
:: ------------------------------------------------------------------------------------
@SET ECLIPSE_PATH=..\..\%LIBRARY_NAME%-eclipse
@SET ECLIPSE_SRC_DIR=%ECLIPSE_PATH%\src\
@SET ECLIPSE_RES_DIR=%ECLIPSE_PATH%\res\
@SET ECLIPSE_EXAMPLES_PATH=%ECLIPSE_PATH%\examples
@SET ECLIPSE_EXAMPLES_SRC_DIR=%ECLIPSE_EXAMPLES_PATH%\src\
@SET ECLIPSE_EXAMPLES_RES_DIR=%ECLIPSE_EXAMPLES_PATH%\res\
:: ====================================================================================
:: Clean Eclipse base project structure
:: ------------------------------------------------------------------------------------
ECHO.Cleaning Eclipse project structure (%LIBRARY_NAME%) ...
RD /S /Q %ECLIPSE_SRC_DIR%
RD /S /Q %ECLIPSE_RES_DIR%
DEL %ECLIPSE_PATH%%\AndroidManifest.xml
ECHO.Cleaning finished.
:: ------------------------------------------------------------------------------------
:: Recreate Eclipse base project structure
:: ------------------------------------------------------------------------------------
IF NOT EXIST %ECLIPSE_SRC_DIR% (
	MKDIR %ECLIPSE_SRC_DIR%
)
IF NOT EXIST %ECLIPSE_RES_DIR% (
	MKDIR %ECLIPSE_RES_DIR%
)
:: ------------------------------------------------------------------------------------
:: Copy library sources and resources into Eclipse base project structure
:: ------------------------------------------------------------------------------------
ECHO.Updating Eclipse project structure (%LIBRARY_NAME%) ...
XCOPY %LIBRARY_SRC_DIR% %ECLIPSE_SRC_DIR% /S /Y
XCOPY %LIBRARY_RES_DIR% %ECLIPSE_RES_DIR% /S /Y
COPY %LIBRARY_MAIN_DIR%\AndroidManifest.xml %ECLIPSE_PATH%\AndroidManifest.xml /Y
ECHO.Update process finished.
:: ====================================================================================
:: Clean Eclipse examples base sub-project structure
:: ------------------------------------------------------------------------------------
ECHO.Cleaning Eclipse examples project structure (%LIBRARY_NAME%/examples) ...
RD /S /Q %ECLIPSE_EXAMPLES_SRC_DIR%
RD /S /Q %ECLIPSE_EXAMPLES_RES_DIR%
DEL %ECLIPSE_EXAMPLES_PATH%%\AndroidManifest.xml
ECHO.Cleaning finished.
:: ------------------------------------------------------------------------------------
:: Recreate Eclipse examples base project structure
:: ------------------------------------------------------------------------------------
IF NOT EXIST %ECLIPSE_EXAMPLES_SRC_DIR% (
	MKDIR %ECLIPSE_EXAMPLES_SRC_DIR%
)
IF NOT EXIST %ECLIPSE_EXAMPLES_RES_DIR% (
	MKDIR %ECLIPSE_EXAMPLES_RES_DIR%
)
:: ------------------------------------------------------------------------------------
:: Copy example sources and resources into Eclipse base sub-project structure
:: ------------------------------------------------------------------------------------
ECHO.Updating Eclipse examples project structure (%LIBRARY_NAME%/examples) ...
XCOPY %LIBRARY_EXAMPLES_SRC_DIR% %ECLIPSE_EXAMPLES_SRC_DIR% /S /Y
XCOPY %LIBRARY_EXAMPLES_RES_DIR% %ECLIPSE_EXAMPLES_RES_DIR% /S /Y
COPY %LIBRARY_EXAMPLES_MAIN_DIR%\AndroidManifest.xml %ECLIPSE_EXAMPLES_PATH%\AndroidManifest.xml /Y
ECHO.Update process finished.
:: ------------------------------------------------------------------------------------
ECHO ON