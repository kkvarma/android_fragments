<a href="http://www.android.com/">
<img align="left" src="http://github.wolf-itechnologies.com/images/wit/android/global/icons/wit_ic_android_examples_100.png" />
</a>

Android Support Fragments (Examples)
===============

This simple examples project shows how to use the source code provided within this repository.

## Download ##
> **NOT AVAILABLE YET**
<!--<a href="https://play.google.com/store/apps/details?id=com.wit.android.PACKAGE_NAME">
  <img alt="Get it on Google Play" src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>-->

## Developer info ##

If You decide to **download** source code of this repository to run this examples project, according to **dependencies** shown below:

	/**
	 * Used repositories ===========================================================================
	 */
	repositories {
	    mavenLocal()
	}
	
	/**
	 * Examples dependencies =======================================================================
	 */
	dependencies {
	    compile project(':library')
        compile 'com.wit.android.support:support-examples:1.0'
	}

You need to **have downloaded/installed** the latest version of [**com.wit.android.support.examples**](https://github.com/Wolf-ITechnologies/maven_android_repository/tree/master/support/support-examples "Go to download page") library within Your **Maven local repository**.

You can install above downloaded examples dependency into _Your Maven local repository_ using one of the scripts provided below:

* <b>Windows</b> (<i>in single line</i>):

	``mvn install:install-file -DgroupId=com.wit.android.support -DartifactId=examples -Dversion=VERSION``
	``-Dfile=examples-VERSION.aar -Dpackaging=aar -DgeneratePom=true``