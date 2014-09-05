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
    # Keep BaseFragment implementation details:
    # - public empty constructors for proper working of instantiation process using reflection,
    # - view members to inject marked with @InjectView, @InjectView.Last annotations.
    -keepclassmembers public class * extends com.wit.android.support.fragment.BaseFragment {
        public <init>();
        @com.wit.android.support.fragment.annotation.InjectView *;
        @com.wit.android.support.fragment.annotation.InjectView$Last *;
    }

> Use below rules to **not obfuscate** any source code of this library project.

    # Keep all classes within library package.
    -keep class com.wit.android.support.fragment.** { *; }
