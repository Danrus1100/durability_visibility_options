pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.parchmentmc.org")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
        maven ("https://maven.terraformersmc.com/releases/")
        maven("https://maven.isxander.dev/releases/")

    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.1-beta.4"
}

stonecutter {
    create(rootProject) {
        versions("1.21.8", "26.1")
        vcsVersion = "1.21.8"
    }
}

rootProject.name = "DVO"