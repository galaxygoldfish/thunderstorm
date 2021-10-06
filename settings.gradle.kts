
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://jetbrains.bintray.com/intellij-third-party-dependencies")
        maven("https://jitpack.io")
    }
}

rootProject.name = "Thunderstorm"
include(":android")
include(":shared")