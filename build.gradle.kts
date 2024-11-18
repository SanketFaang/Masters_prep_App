// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
// Project-level build.gradle.kts
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0")
        classpath("com.google.gms:google-services:4.3.15") // Google Services plugin
    }
}
