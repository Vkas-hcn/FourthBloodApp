# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-obfuscationdictionary proguard-iol.txt
-classobfuscationdictionary proguard-iol.txt
-packageobfuscationdictionary proguard-iol.txt

-dontwarn com.jeremyliao.liveeventbus.**
-dontwarn androidx.lifecycle.LiveData
-keep class androidx.lifecycle.LiveData { *; }
-keep class androidx.lifecycle.LifecycleRegistry { *; }
-keep class androidx.arch.core.internal.SafeIterableMap { *; }
-keepattributes *Annotation*

# Keep Navigation components
-keep public class androidx.navigation.** { *; }
-keep class androidx.fragment.app.FragmentContainerView { *; }
-keep @androidx.navigation.Navigator.Name class * { *; }
