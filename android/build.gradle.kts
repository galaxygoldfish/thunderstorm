plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
}

dependencies {
    implementation(project(":shared"))

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.material:material:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.runtime:runtime:${rootProject.extra["compose_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.5.0-beta01")
    implementation("androidx.glance:glance-appwidget:1.0.0-SNAPSHOT")

    implementation("com.google.android.material:material:1.5.0")
    implementation("com.google.accompanist:accompanist-pager:0.16.1")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.19.0")

    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.0")
    implementation("com.airbnb.android:lottie:5.0.3")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.thunderstorm.app.android"
        minSdk = 26
        targetSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // kotlinCompilerExtensionVersion = rootProject.extra["compose_version"] as
        kotlinCompilerExtensionVersion = "1.1.0-beta03"
        kotlinCompilerVersion = "1.5.10"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}