Proguard
===============

This file describes, which proguard rules **should** be used to preserve *proper working* of the
source code of this library project when a proguard process is applied to a project which uses this 
library.

### Proguard-Rules ###

> Use below rules to **obfuscate as much** source code of this library project **as possible**.

    # Keep members with @FactoryFragment annotation within fragment factories.
    -keepclassmembers public class * extends com.wit.android.support.fragment.manage.BaseFragmentFactory {
        @com.wit.android.support.fragment.annotation.FactoryFragment *;
    }

> Use below rules to **not obfuscate** any source code of this library project.

