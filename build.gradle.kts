import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import java.nio.charset.StandardCharsets

plugins {
    java
    id("lol.bai.explosion") version "0.2.0" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.4.5" apply false
}

group = "lol.bai.megane"
version = System.getenv("MOD_VERSION") ?: "999999-local"

rootProject.initializeDependencies()

allprojects {
    apply(plugin = "java")

    version = rootProject.version
    group = rootProject.group

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.name()
        options.release.set(17)
    }

    repositories {
        mavenCentral {
            content {
                excludeGroupByRegex("org.lwjgl")
            }
        }

        maven("https://libraries.minecraft.net")
        maven("https://maven.bai.lol")
        maven("https://server.bbkr.space/artifactory/libs-release")
        maven("https://maven.shedaniel.me/")
        maven("https://maven.jamieswhiteshirt.com/libs-release")
        maven("https://maven.blamejared.com")
        maven("https://mod-buildcraft.com/maven")
        maven("https://maven.cafeteria.dev/releases")
        maven("https://jitpack.io")
        maven("https://maven.ladysnake.org/releases")
        maven("https://maven.wispforest.io")
        maven("https://mvn.devos.one/snapshots")
        maven("https://maven.tterrag.com")
        maven("https://maven.terraformersmc.com/releases")
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")

        maven("https://repo.spongepowered.org/repository/maven-public") {
            content {
                includeGroup("org.spongepowered")
            }
        }

        maven("https://cursemaven.com") {
            content {
                includeGroup("curse.maven")
            }
        }

        maven("https://api.modrinth.com/maven") {
            content {
                includeGroup("maven.modrinth")
            }
        }

        ivy("https://www.curseforge.com/api/v1/mods") {
            patternLayout {
                artifact("[module]/files/[revision]/download")
            }

            metadataSources {
                artifact()
            }

            content {
                includeGroup("curse.api")
            }
        }

        maven("https://modmaven.dev")
    }

    tasks.withType<ProcessResources> {
        doLast {
            val slurper = JsonSlurper()
            val json = JsonGenerator.Options()
                .disableUnicodeEscaping()
                .build()
            fileTree(outputs.files.asPath) {
                include("**/*.json")
                forEach {
                    val mini = json.toJson(slurper.parse(it, StandardCharsets.UTF_8.name()))
                    it.writeText(mini)
                }
            }
        }
    }
}

sourceSets.configureEach {
    java.setSrcDirs(emptyList<Any>())
    resources.setSrcDirs(emptyList<Any>())
}

subprojects {
    base {
        archivesName.set("megane-${project.name}")
    }

    sourceSets {
        main {
            resources.srcDir(rootProject.file("src/main/resources"))
        }
    }
}

tasks {
    val fetchLatestVersions by creating(FetchLatestVersionsTask::class)
}
