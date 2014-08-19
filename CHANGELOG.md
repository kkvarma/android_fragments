Change-Log
===============

> **RELEASE VERSION** (<i>RELEASE DATE</i>)

### **3.8** (<i>**.08.2014</i>) ###
- Added new @FactoryFragment annotation to mark an int field within FragmentFactory as fragment id.
- Updated @InjectView annotation by `@InjectView.clickable()` flag.
- Added some support methods to create params for Fragment inside BaseFragment class.

### **3.7** (<i>19.08.2014</i>) ###
- Added `setAdapterViewVisible(boolean), setEmptyViewVisible(boolean)` into AdapterFragment.
- Added `runOnUiThread(Runnable)` into BaseFragment.

### **3.6** (<i>05.08.2014</i>) ###
- Renamed all dispatch... methods meant to notify listeners to notify... due to optimizing some naming conventions in all libraries.
- Allowed to create root layout for the AdapterFragment.

### **3.5** (<i>25.07.2014</i>) ###
- Renamed FragmentController.ShowOptions to FragmentController.TransactionOptions to be more accurate.
- Improved documentation.

### **3.4** (<i>25.07.2014</i>) ###
- Added @InjectView.Last annotation to improve views injecting process.
- Added support to set custom class of AdapterView in AdapterFragment implementation.
- Improved documentation.
- Some minor updates and bug fixes.

### **3.3** (<i>08.07.2014</i>) ###
- Added support to start loader from within the context of AdapterFragment.

### **3.2** (<i>11.06.2014</i>) ###
- Correctly named animation durations (renamed, started from  <b>Anim</b> to <b>Config.Anim</b>)
- Added support to inject views into fragment/activity using FragmentAnnotations utils.

### **3.1** (<i>24.05.2014</i>) ###
- Refactored and optimized WebFragment
- Added support to clear fragments back stack by FragmentController.clearBackStack() or FragmentController.clearBackStackImmediate()

### **3.0** (<i>14.05.2014</i>) ###
- @InjectViews annotations is required to run inject views process.
- Removed support for loading view from AdapterFragment implementation.

### **2.0** (<i>14.05.2014</i>) ###
- Added 'Adapter' fragment implementations.
- Added support for annotations.