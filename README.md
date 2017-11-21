# DrawerLayout
This guide explains how to setup a basic material design style drawer filled with navigation items that switch different fragments into the content area

## 添加项目依赖
`
dependencies {
  compile 'com.android.support:design:25.2.0'
}
`

## 创建Drawer Resources
在res下创建menu/drawer_view.xml文件:
`
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_first_fragment"
            android:icon="@drawable/ic_one"
            android:title="First" />
        <item
            android:id="@+id/nav_second_fragment"
            android:icon="@drawable/ic_two"
            android:title="Second" />
        <item
            android:id="@+id/nav_third_fragment"
            android:icon="@drawable/ic_three"
            android:title="Third" />
    </group>
</menu>
`
## 创建Toolbar
`
<android.support.v7.widget.Toolbar
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/toolbar"
    android:layout_height="wrap_content"
    android:layout_width="match_parent"
    android:fitsSystemWindows="true"
    android:minHeight="?attr/actionBarSize"
    app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"
    android:background="?attr/colorPrimaryDark">
</android.support.v7.widget.Toolbar>
`

## 在Activity里创建Drawer
res/layout/activity_main.xml
`
<!-- This DrawerLayout has two children at the root  -->
<android.support.v4.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <!-- This LinearLayout represents the contents of the screen  -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <!-- The ActionBar displayed at the top -->
        <include
            layout="@layout/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <!-- The main content view where fragments are loaded -->
        <FrameLayout
            android:id="@+id/flContent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>

    <!-- The navigation drawer that comes from the left -->
    <!-- Note that `android:layout_gravity` needs to be set to 'start' -->
    <android.support.design.widget.NavigationView
        android:id="@+id/nvView"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        app:menu="@menu/drawer_view" />
</android.support.v4.widget.DrawerLayout>
`

`
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
 
    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
`

## Navigating between Menu Items
`
public class MainActivity extends AppCompatActivity {
   
    // ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...From section above...
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = FirstFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecondFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ThirdFragment.class;
                break;
            default:
                fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // ...
}
`

## Add Navigation Header
`
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="192dp"
    android:background="?attr/colorPrimaryDark"
    android:padding="16dp"
    android:theme="@style/ThemeOverlay.AppCompat.Dark"
    android:orientation="vertical"
    android:gravity="bottom">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Header"
        android:textColor="@android:color/white"
        android:textAppearance="@style/TextAppearance.AppCompat.Body1"/>

</LinearLayout>

<!-- res/layout/activity_main.xml -->

 <!-- The navigation drawer -->
    <android.support.design.widget.NavigationView
        ...
        app:headerLayout="@layout/nav_header">

    </android.support.design.widget.NavigationView>
`

or

`
// Lookup navigation view
NavigationView navigationView = (NavigationView) findViewById(R.id.nav_draw);
// Inflate the header view at runtime
View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
// We can now look up items within the header if needed
ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);
`

## Animate the Hamburger Icon(ActionBarDrawerToggle)
`<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="drawer_open">Open navigation drawer</string>
    <string name="drawer_close">Close navigation drawer</string>
</resources>

protected void onCreate(Bundle savedInstanceState) { 
// Set a Toolbar to replace the ActionBar.
	toolbar = (Toolbar) findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);

	// Find our drawer view
	mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
	drawerToggle = setupDrawerToggle();

	// Tie DrawerLayout events to the ActionBarToggle
	mDrawer.addDrawerListener(drawerToggle);
 }

 private ActionBarDrawerToggle setupDrawerToggle() {
      // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
      // and will not render the hamburger icon without it.  
      return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
 }

   // `onPostCreate` called when activity start-up is complete after `onStart()`
  // NOTE 1: Make sure to override the method with only a single `Bundle` argument
  // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method. 
  // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      // Sync the toggle state after onRestoreInstanceState has occurred.
      drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      // Pass any configuration change to the drawer toggles
      drawerToggle.onConfigurationChanged(newConfig);
  }
 
    @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      if (drawerToggle.onOptionsItemSelected(item)) {
          return true;
      }
      return super.onOptionsItemSelected(item);
  }
`

## Making Status Bar Translucent
`
<resources>
  <!-- Base application theme. -->
  <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
    <!-- Customize your theme here. -->
    <item name="android:windowTranslucentStatus">true</item>
  </style>
</resources>
`
