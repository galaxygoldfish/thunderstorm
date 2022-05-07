buildscript {
    val compose_version by extra("1.1.1")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://androidx.dev/snapshots/latest/artifacts/repository")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://androidx.dev/snapshots/latest/artifacts/repository")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}