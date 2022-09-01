import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import net.darkhax.curseforgegradle.TaskPublishCurseForge
import java.nio.charset.StandardCharsets

plugins {
    id("fabric-loom")
    id("net.darkhax.curseforgegradle")
    id("com.modrinth.minotaur")
    id("maven-publish")
}

version = System.getenv("MOD_VERSION") ?: "local"

allprojects {
    apply(plugin = "fabric-loom")

    version = rootProject.version
    group = "lol.bai.megane"

    repositories {
        badasintended()
        cursemaven()
        modrinth()
    }

    dependencies {
        minecraft(deps.minecraft)
        mappings(loom.layered {
            mappings(deps.yarn)
            mappings(rootProject.file("conflict.mapping")) {
                enigmaMappings()
            }
        })

        modImplementation(deps.fabricLoader)
    }

    sourceSets {
        main {
            resources.srcDirs(rootProject.file("include"))
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        withSourcesJar()
    }

    tasks.withType<JavaCompile> {
        options.encoding = StandardCharsets.UTF_8.name()
        options.release.set(17)
    }

    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }

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

subprojects {
    apply(plugin = "maven-publish")

    base {
        archivesName.set("megane-${project.name}")
    }

    dependencies {
        if (project.name != "api") {
            implementation(namedProject("api"))

            if (project.name != "runtime") {
                runtimeOnly(namedProject("runtime"))
            }
        }
    }

    publishing {
        repositories {
            maven {
                url = uri("https://maven.pkg.github.com/badasintended/megane")
                name = "GitHub"
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }

        publications {
            if (project.name != "test") create<MavenPublication>("maven") {
                artifactId = base.archivesName.get()
                from(components["java"])
            }
        }
    }
}

subprojects.forEach {
    tasks.remapJar {
        dependsOn("${it.path}:remapJar")
    }
}

tasks.create<FetchLatestVersionsTask>("fetchLatestVersions") {
    group = "verification"
}

afterEvaluate {
    subprojects.forEach {
        dependencies {
            implementation(namedProject(it.name))

            if (it.name != "test") {
                include(it)
            }
        }

        sourceSets {
            val subMain = it.sourceSets.main.get()
            main {
                runtimeClasspath += subMain.runtimeClasspath
            }
        }
    }

    System.getenv("MODRINTH_TOKEN")?.let { MODRINTH_TOKEN ->
        modrinth {
            token.set(MODRINTH_TOKEN)
            projectId.set(mrIds.megane)

            versionNumber.set("${project.version}")
            versionType.set("release")
            changelog.set("https://github.com/badasintended/megane/releases/tag/${project.version}")
            uploadFile.set(tasks.remapJar.get())

            loaders.add("fabric")
            gameVersions.add(versions.minecraft)

            required.project(mrIds.wthit)

            optional.project(mrIds.ae2)
            optional.project(mrIds.alloyForge)
//            optional.project(mrIds.create)
            optional.project(mrIds.extraGen)
            optional.project(mrIds.kibe)
            optional.project(mrIds.modernDynamics)
        }
    }

    tasks.create<TaskPublishCurseForge>("curseforge") {
        group = "publishing"
        dependsOn("build")

        apiToken = System.getenv("CURSEFORGE_API")
        apiEndpoint = "https://minecraft.curseforge.com"

        upload(cfIds.megane, tasks.remapJar.get()).apply {
            displayName = "[${versions.minecraft}] ${project.version}"
            releaseType = "release"

            changelogType = "markdown"
            changelog = "https://github.com/badasintended/megane/releases/tag/${project.version}"

            addGameVersion("Fabric")
            addGameVersion(versions.minecraft)

            addRequirement(cfSlugs.wthit)

            addOptional(cfSlugs.ae2)
            addOptional(cfSlugs.alloyForge)
//            addOptional(cfSlugs.create)
//            addOptional(cfSlugs.dmlSim)
            addOptional(cfSlugs.extraGen)
//            addOptional(cfSlugs.indrev)
            addOptional(cfSlugs.kibe)
//            addOptional(cfSlugs.lba)
            addOptional(cfSlugs.luggage)
            addOptional(cfSlugs.modernDynamics)
            addOptional(cfSlugs.rebornCore)
            addOptional(cfSlugs.techReborn)
            addOptional(cfSlugs.wirelessNet)
        }
    }
}
