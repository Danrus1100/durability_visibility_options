plugins {
    base
    java
    `java-library`
    id("net.fabricmc.fabric-loom-remap") version "1.17.9" apply false
    id("net.fabricmc.fabric-loom") version "1.17.9" apply false
    id("maven-publish")
    id("me.modmuss50.mod-publish-plugin") version "0.8.4"
}

if (stonecutter.current.version < "26.1") {
    println("using net.fabricmc.fabric-loom-remap")
    apply(plugin = "net.fabricmc.fabric-loom-remap")
} else {
    println("using net.fabricmc.fabric-loom")
    apply(plugin = "net.fabricmc.fabric-loom")
}

val modVersion = providers.gradleProperty("mod_version").get()
val projectArchivesName = providers.gradleProperty("archives_base_name").get()

version = modVersion
group = providers.gradleProperty("maven_group").get()

base {
    archivesName = projectArchivesName
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.

    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    gradlePluginPortal()
    mavenCentral()

    maven { url = uri("https://maven.shedaniel.me/") }
    maven { url = uri("https://maven.terraformersmc.com/releases/") }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
    }

    maven { url = uri("https://maven.quiltmc.org/repository/release") }

    if (stonecutter.current.version >= "1.20.4") {
        maven {
            url = uri("https://maven.nucleoid.xyz/")
            name = "Nucleoid"
        }
    }
}

println("Dependency versions:")
println("  - Minecraft: ${stonecutter.current.version}")
println("  - Stonecutter target: ${stonecutter.current.version} (project ${stonecutter.current.project})")
println("  - Fabric Loader: ${property("loader_version")}")
println("  - Fabric API: ${property("fabric_version")}")
println("  - YACL: ${property("yacl_version")}")
println("  - Mod Menu: ${property("mod_menu")}")

dependencies {
    "minecraft"("com.mojang:minecraft:${stonecutter.current.version}")
    if (stonecutter.current.version < "26.1") {
        val loom = project.extensions.getByName("loom") as net.fabricmc.loom.api.LoomGradleExtensionAPI
        "mappings"(loom.officialMojangMappings())
    }

    val fabricLoaderString = "net.fabricmc:fabric-loader:${property("loader_version")}"
    val fabricApiString = "net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}"
    val yaclString = "dev.isxander:yet-another-config-lib:${property("yacl_version")}"
    val modMenuString = "com.terraformersmc:modmenu:${property("mod_menu")}"

    if (stonecutter.current.version < "26.1") {
        "modImplementation"(fabricLoaderString)
        "modImplementation"(fabricApiString)

        "modImplementation"(yaclString)
        "modApi"(modMenuString)
    } else {
        implementation(fabricLoaderString)
        implementation(fabricApiString)

        implementation(yaclString)
        api(modMenuString)
    }
    implementation("com.google.code.gson:gson:2.10.1")
}

stonecutter {
    replacements.string(current.parsed >= "1.20.4") {
        replace(
            "SystemToast.SystemToastIds",
            "SystemToast.SystemToastId"
        )
    }

    replacements.string(current.parsed >= "1.21.2") {
        replace(
            "ToastComponent",
            "ToastManager"
        )
    }

    replacements.string(current.parsed >= "1.21.11") {
        replace(
            "net.minecraft.Util",
            "net.minecraft.util.Util"
        )
    }

    replacements.string(current.parsed >= "26.1") {
        replace(
            "GuiGraphics",
            "GuiGraphicsExtractor"
        )
    }
}

val jarArchiveFileName = "${projectArchivesName}-${modVersion}-mc-${stonecutter.current.project}.jar"

if (stonecutter.current.version < "26.1") {
    tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
        archiveFileName = jarArchiveFileName
    }
} else {
    tasks.jar {
        archiveFileName = jarArchiveFileName
    }
}

val mcDep = property("mod.mc_dep").toString()

tasks.processResources {
    inputs.property("version", modVersion)
    inputs.property("minecraft_version", stonecutter.current.version)
    inputs.property("loader_version", providers.gradleProperty("loader_version").get())
    inputs.property("mcdep", mcDep)
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to modVersion,
            "minecraft_version" to stonecutter.current.version,
            "loader_version" to providers.gradleProperty("loader_version").get(),
            "mcdep" to mcDep,
        )
    }
}

var targetJavaVersion = 25
if (stonecutter.current.version < "1.20.6") {
    targetJavaVersion = 17
} else if (stonecutter.current.version < "26.1") {
    targetJavaVersion = 21
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        options.release.set(targetJavaVersion)
    }
}

tasks.register<Copy>("buildAndCollect") {
    group = "build"
    from(jarArchiveFileName)
    into(rootProject.layout.buildDirectory.file("libs/${modVersion}"))
    dependsOn("build")
}

configure<net.fabricmc.loom.api.LoomGradleExtensionAPI> {
    runConfigs.all {
        val run = this as net.fabricmc.loom.configuration.ide.RunConfigSettings

        // Run configurations are not created for subprojects by default
        run.getGenerateRunConfig().set(true)

        // Use a shared run folder and create separate worlds
        run.getRunDirectory().set(layout.projectDirectory.dir("../../run"))
    }
//    accessWidenerPath = project.rootProject.file("src/main/resources/aws/${stonecutter.current.project}.accesswidener")
//    accessWidenerPath = project.rootProject.file("src/main/resources/aws/1.21.4.accesswidener")
}

java {
    withSourcesJar()

    var javaVersion = JavaVersion.VERSION_25
    if (stonecutter.current.version < "1.20.6") {
        javaVersion = JavaVersion.VERSION_17
    } else if (stonecutter.current.version < "26.1") {
        javaVersion = JavaVersion.VERSION_21
    }

    println("JavaVersion: ${javaVersion.toString()}")
    targetCompatibility = javaVersion
    sourceCompatibility = javaVersion
}


tasks.jar {
    from("LICENSE") {
        rename { "${it}_${projectArchivesName}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = projectArchivesName
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}

//publishMods {
//    file = tasks.remapJar.archiveFile
//    additionalFiles.from(additionalFiles.from(tasks.named<org.gradle.api.tasks.bundling.Jar>("remapSourcesJar").flatMap { it.archiveFile }))
//    displayName = "Durability Visibilty Options ${modVersion} for ${stonecutter.current.project}"
//    version = modVersion
//    changelog = rootProject.file("CHANGELOG.md").readText(
//    type = STABLE
//    modLoaders.add("fabric")
//
//        dryRun = providers.environmentVariable("MODRINTH_API_TOKEN").orNull == null ||
//            providers.environmentVariable("CURSEFORGE_API_TOKEN").orNull == null
//
//    modrinth {
//        projectId = property("publish.modrinth").toString()
//        accessToken = providers.environmentVariable("MODRINTH_API_TOKEN")
//        minecraftVersions.add(stonecutter.current.project)
//        requires {
//            slug = "fabric-api modmenu yacl"
//        }
//    }
//
//    curseforge {
//        projectId = property("publish.curseforge").toString()
//        accessToken = providers.environmentVariable("CURSEFORGE_API_TOKEN")
//        minecraftVersions.add(stonecutter.current.project)
//    }
//}

publishMods {
    file = file(jarArchiveFileName)
    if (stonecutter.current.version < "26.1") {
        additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("remapSourcesJar").flatMap { it.archiveFile })
    }
    version = "${modVersion}-MC-${stonecutter.current.project}"
    displayName = "Durability Visibilty Options ${modVersion}-MC-${stonecutter.current.project}"
    changelog = rootProject.file("CHANGELOG.md").readText()
    type = STABLE
    modLoaders.add("fabric")

    dryRun = providers.environmentVariable("MODRINTH_API_TOKEN").orNull == null ||
            providers.environmentVariable("CURSEFORGE_API_TOKEN").orNull == null

    modrinth {
        projectId = property("publish.modrinth").toString()
        accessToken = providers.environmentVariable("MODRINTH_API_TOKEN")
        minecraftVersions.add(stonecutter.current.project)
        requires {
            slug = "fabric-api modmenu yacl"
        }
    }

    curseforge {
        projectId = property("publish.curseforge").toString()
        accessToken = providers.environmentVariable("CURSEFORGE_API_TOKEN")
        minecraftVersions.add(stonecutter.current.project)
    }
}
