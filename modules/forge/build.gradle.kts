import net.minecraftforge.gradle.common.util.RunConfig

plugins {
    id("net.minecraftforge.gradle") version "[6.0.16, 6.2)"
    id("org.spongepowered.mixin") version "0.7.+"
}

allprojects {
    apply(plugin = "net.minecraftforge.gradle")
    apply(plugin = "org.spongepowered.mixin")

    minecraft {
        mappings("official", versions.minecraft)
    }

    dependencies {
        minecraft(deps.forge.forge)

        compileOnly(fg.deobf(deps.forge.wthit.api))
        runtimeOnly(fg.deobf(deps.forge.wthit.runtime))
        runtimeOnly(fg.deobf(deps.forge.badpackets))

        runtimeOnly(fg.deobf(deps.forge.jei))
    }

    sourceSets {
        main {
            java.srcDir("src/generated/java")
            resources.srcDir("src/generated/resources")
        }
    }

    tasks.jar {
        finalizedBy("reobfJar")
    }

    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand("version" to project.version)
        }
    }

}

jarJar.enable()

minecraft {
    runs {
        val runConfig = Action<RunConfig> {
            workingDirectory(file("run"))

            mods.create("megane") {
                source(sourceSets["main"])
            }
        }

        create("client", runConfig)
        create("server", runConfig)
    }
}


afterEvaluate {
    subprojects.forEach { sub ->
        dependencies {
            compileOnly(sub)

            jarJar(project(sub.path)) {
                isTransitive = false
                jarJar.ranged(this, "[0, 999999)")
            }
        }

        sourceSets {
            main {
                runtimeClasspath += sub.sourceSets.main.get().runtimeClasspath
            }
        }
    }
}

val thisProject = project
subprojects.forEach { sub ->
    sub.afterEvaluate {
        thisProject.minecraft.runs.configureEach {
            mods.create("megane-${sub.name}".replace("-", "_")) {
                source(sourceSets["main"])
            }

            val mixin = metadata?.let { it.prop[GenMixinTask.JSON] as? String }
            if (mixin != null) args("--mixin.config", mixin)
        }
    }
}
