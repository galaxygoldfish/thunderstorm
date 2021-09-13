import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
    kotlin("plugin.serialization") version "1.5.20"
}

version = "1.0"

kotlin {

    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        else -> ::iosX64
    }

    /** https://github.com/cashapp/sqldelight/issues/1442#issuecomment-615991279 **/
    targets.filterIsInstance<KotlinNativeTarget>().forEach{
        it.binaries.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.Framework>()
            .forEach { lib ->
                lib.isStatic = false
                lib.linkerOpts.add("-lsqlite3")
            }
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
        val sqlDelightVersion = "1.5.0"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

sqldelight {
    database("ThunderstormDatabase") {
        packageName = "com.thunderstorm.app"
    }
    linkSqlite = true
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(26)
        targetSdkVersion(31)
    }
}
