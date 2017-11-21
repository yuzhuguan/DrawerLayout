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


