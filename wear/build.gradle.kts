plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    wearApp(project(":wear"))
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("com.google.android.gms:play-services-wearable:17.1.0")
    implementation("androidx.percentlayout:percentlayout:1.0.0")
    implementation("androidx.wear:wear:1.2.0")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")
    implementation("androidx.wear.compose:compose-material:1.0.0-alpha16")
    implementation("androidx.wear.compose:compose-foundation:1.0.0-alpha16")
    implementation("androidx.wear.compose:compose-navigation:1.0.0-alpha16")
}

android {
    compileSdkVersion(32)
    defaultConfig {
        applicationId = "com.thunderstorm.app.wear"
        minSdkVersion(26)
        targetSdkVersion(32)
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as String
        kotlinCompilerVersion = "1.5.10"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}