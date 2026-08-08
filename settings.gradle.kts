pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.7"
}

stonecutter {
    create(rootProject) {
        // 1.19.4 - 1.16.5 has different GUI rendering system, so...
        versions(
            "26.2",
            "26.1.2",  // 26.1.1 26.1
            "1.21.11",
            "1.21.10", // 1.21.9
            "1.21.8",  // 1.21.8 1.21.7
            "1.21.5",
            "1.21.4",
            "1.21.3",  // 1.21.2
            "1.21.1",  // 1.21
            "1.20.6",  // 1.20.5
            "1.20.4",  // 1.20.3
            "1.20.1",  // 1.20
        )
        vcsVersion = "1.21.1"
    }
}