import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.5.20"
}

version = "1.0"

kotlin {

    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    iosTarget("ios") {

    }

    cocoapods {
        summary = "Thunderstorm shared common logic"
        homepage = "null"
        ios.deploymentTarget = "14.1"
        frameworkName = "shared"
        podfile = project.file("../ios/Podfile")
    }

    sourceSets {

        val ktorVersion = "1.5.2"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
            }
        }
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(26)
        targetSdkVersion(31)
    }
}
