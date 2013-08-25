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
::                BUILD SCRIPT FOR GRADLE BASED ANDROID LIBRARY PROJECT
:: ------------------------------------------------------------------------------------
@ECHO OFF

:: ====================================================================================
:: Gradle wrapper parameters.
@SET GRADLE_WRAPPER=gradlew
@SET GRADLE_TASK_CLEAN=clean
@SET GRADLE_TASK_ASSEMBLE=assemble
@SET GRADLE_TASK_CONNECTED_CHECK=connectedCheck
@SET GRADLE_DAEMON=--daemon
:: ------------------------------------------------------------------------------------
:: Library module parameters.
@SET LIBRARY_PROJECT_NAME=android_fragments
:: ------------------------------------------------------------------------------------
:: Examples (sub-module) parameters.
@SET EXAMPLES_APK=examples\build\apk\examples-debug-unaligned.apk
@SET EXAMPLES_PACKAGE=com.wit.and.examples.fragments
@SET EXAMPLES_LAUNCH_ACTIVITY=%EXAMPLES_PACKAGE%/.ScreenSplash
:: ------------------------------------------------------------------------------------
:: Save input parameter.
@SET PARAM=%1
:: ------------------------------------------------------------------------------------
:: Go up to project root directory
:: cd ..
:: Check action to perform.
IF [%PARAM%]==[-buildType] GOTO ListBuildTypes
IF [%PARAM%]==[clean] GOTO Clean
IF [%PARAM%]==[build] GOTO Build
IF [%PARAM%]==[rebuild] GOTO ReBuild
IF [%PARAM%]==[test] GOTO Test
IF [%PARAM%]==[examples] GOTO Examples
IF [%PARAM%]==[] GOTO UnknownParameter
:: ------------------------------------------------------------------------------------
:: ====================================================================================
:: Script actions:
:: ------------------------------------------------------------------------------------
:ListBuildTypes:
ECHO.These Gradle build types are allowed:
ECHO.- clean
ECHO.- build
ECHO.- rebuild
ECHO.- test
ECHO.- examples
GOTO Finish
:: ------------------------------------------------------------------------------------
:UnknownBuildType
ECHO.Unknown build type '%PARAM%'
GOTO Finish
:: ------------------------------------------------------------------------------------
:: Build types:
:: ====================================================================================
:Clean
ECHO.Cleaning Gradle library project (%LIBRARY_PROJECT_NAME%) ...
%GRADLE_WRAPPER% %GRADLE_TASK_CLEAN% %GRADLE_DAEMON%
ECHO.Finished Gradle library project cleaning.
GOTO Finish
:: ------------------------------------------------------------------------------------
:Build
ECHO.Building Gradle library project (%LIBRARY_PROJECT_NAME%) ...
%GRADLE_WRAPPER% %GRADLE_TASK_ASSEMBLE% %GRADLE_DAEMON%
ECHO.Finished Gradle library project building.
GOTO Finish
:: ------------------------------------------------------------------------------------
:ReBuild
ECHO.Re-building Gradle library project (%LIBRARY_PROJECT_NAME%) ...
%GRADLE_WRAPPER% %GRADLE_TASK_CLEAN% %GRADLE_TASK_ASSEMBLE% %GRADLE_DAEMON%
ECHO.Finished Gradle library project re-building.
GOTO Finish
:: ------------------------------------------------------------------------------------
:Test
ECHO.Running connected test for Gradle library project (%LIBRARY_PROJECT_NAME%) ...
adb devices
%GRADLE_WRAPPER% %GRADLE_TASK_CONNECTED_CHECK% %GRADLE_DAEMON%
ECHO.Finished connected test for Gradle library project (%LIBRARY_PROJECT_NAME%).
GOTO Finish
:: ------------------------------------------------------------------------------------
:Examples
ECHO.Launching UI test for Gradle library project (%LIBRARY_PROJECT_NAME%) ...
:: Build project.
:: %GRADLE_WRAPPER% assemble %GRADLE_DAEMON%
:: Install APK on Android powered device.
adb devices
adb install -r %EXAMPLES_APK%
adb shell am start -n %EXAMPLES_LAUNCH_ACTIVITY%
GOTO Finish
:: ------------------------------------------------------------------------------------
:UnknownParameter
ECHO.Unknow build type '%PARAM%'. Please se allowed build types by 'project -buildType'
GOTO Finish
:: ------------------------------------------------------------------------------------
:Finish
:: Go down to project script directory
:: cd script
:: ------------------------------------------------------------------------------------
ECHO ON