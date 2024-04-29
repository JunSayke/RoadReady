buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        // START_OF[Safe Args]
        val navVersion = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
        // END_OF[Safe Args]
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
}