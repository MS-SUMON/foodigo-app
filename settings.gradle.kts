// settings.gradle.kts (Correct Structure)

// 1. Configure Plugin Management (Where to find plugins for your whole build)
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    // 💡 Add the required plugins block with versions here
    plugins {
        // Define standard plugins with versions
        id("com.android.application") version "8.2.2"
        id("org.jetbrains.kotlin.android") version "1.9.23"

        id("com.google.devtools.ksp") version "1.9.23-1.0.20"


        id("androidx.compose.compiler") version "1.5.11" // Use a version compatible with your Kotlin version (1.5.11 is a common pairing for 1.9.x Kotlin)
    }
}

// ---

// 2. Configure Dependency Management (Where to find libraries like Glide, Material, etc.)
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// ---

// 3. Project Configuration
rootProject.name = "Foodigo"
include(":app")