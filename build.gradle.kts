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

        badasintended()
        cursemaven()
        modrinth()
        mavenLocal()
        minecraft()
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
