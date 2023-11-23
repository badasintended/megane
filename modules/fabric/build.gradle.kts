import groovy.json.JsonGenerator
import groovy.json.JsonSlurper
import java.nio.charset.StandardCharsets

plugins {
    id("fabric-loom") version "1.3.+"
}

allprojects {
    apply(plugin = "fabric-loom")

    dependencies {
        minecraft(deps.minecraft)
        mappings(loom.officialMojangMappings())

        modImplementation(deps.fabricLoader)
        modImplementation(deps.fabric.wthit)
        modImplementation(deps.fabric.fabricApi)
    }

    loom {
        interfaceInjection.enableDependencyInterfaceInjection.set(false)

        mixin {
            defaultRefmapName.set("megane-${project.name}.refmap.json")
        }
    }

    sourceSets {
        main {
            resources.srcDir("src/generated/resources")
        }
    }

    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
}

repositories {
    terraformers()
}

dependencies {
    modImplementation(deps.fabric.modmenu)
}

loom {
    runs.configureEach {
        ideConfigGenerated(true)
    }
}

afterEvaluate {
    subprojects.forEach {
        dependencies {
            implementation(project(path = it.path, configuration = "namedElements"))
            include(it)
        }

        sourceSets {
            main {
                runtimeClasspath += it.sourceSets.main.get().runtimeClasspath
            }
        }
    }
}
