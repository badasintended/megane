import groovy.lang.GroovyObject
import me.modmuss50.mpp.ReleaseType
import net.minecraftforge.gradle.common.util.RunConfig

plugins {
    id("net.minecraftforge.gradle") version "[6.0.16, 6.2)"
    id("org.spongepowered.mixin") version "0.7.+"

    id("me.modmuss50.mod-publish-plugin")
}

publishMods {
    changelog.set("https://github.com/badasintended/megane/releases/tag/${project.version}")
    type.set(ReleaseType.STABLE)
    modLoaders.add("forge")

    val curseForgeApi = providers.environmentVariable("CURSEFORGE_API")
    val modrinthToken = providers.environmentVariable("MODRINTH_TOKEN")
    dryRun.set(!(curseForgeApi.isPresent && modrinthToken.isPresent))

    curseforge {
        projectId.set("965089")
        accessToken.set(curseForgeApi)
        minecraftVersions.add("1.19.2")

        requires(cfSlugs.wthitForge)
        optional(
            cfSlugs.ae2,
            cfSlugs.createForge,
            cfSlugs.ie
        )
    }

    modrinth {
        projectId.set("pcvCiEEP")
        accessToken.set(modrinthToken)
        minecraftVersions.add("1.19.2")

        requires(mrIds.wthit)
        optional(
            mrIds.ae2,
            mrIds.createForge,
            mrIds.ie
        )
    }
}

allprojects {
    apply(plugin = "net.minecraftforge.gradle")
    apply(plugin = "org.spongepowered.mixin")

    repositories {
        fabric()
    }

    minecraft {
        mappings("official", versions.minecraft)
    }

    mixin {
        debug.apply {
            this as GroovyObject
            setProperty("export", true)
            setProperty("verbose", true)
        }
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

tasks {
    val build by getting

    val mergeWaila by creating(MergeWailaTask::class) {
        output.set(layout.buildDirectory.file("mergeWaila/waila_plugins.json"))
    }

    val jar by getting(Jar::class) {
        archiveClassifier.set("dev")
    }

    val fatJar by creating(Jar::class) {
        dependsOn(jar)
        dependsOn(mergeWaila)
        build.dependsOn(this)

        archiveClassifier.set("")

        from(mergeWaila.output)
        from(zipTree(jar.archiveFile))

        subprojects.forEach { sub ->
            val subJar = sub.tasks.getByName<Jar>("jar")
            dependsOn(subJar)

            from(zipTree(subJar.archiveFile)) {
                include("**/*.class")
                exclude("**/Main.class")

                include("*.mixins.json")
                include("*.refmap.json")
                include("assets/**")
            }
        }
    }

    subprojects {
        afterEvaluate {
            val mixinJson = metadata?.let { it.prop[GenMixinTask.JSON] as? String }

            if (mixinJson != null) {
                val otherJson = fatJar.manifest.attributes["MixinConfigs"]?.toString()

                fatJar.manifest.attributes(
                    "MixinConfigs" to if (otherJson == null) mixinJson else "${otherJson},${mixinJson}"
                )
            }

            val genWaila by tasks.getting(GenWailaTask::class)
            mergeWaila.dependsOn(genWaila)
            mergeWaila.input.add(genWaila.output)
        }
    }

    project.publishMods {
        file.set(fatJar.archiveFile)
    }
}

subprojects.forEach { sub ->
    dependencies {
        implementation(project(sub.path)) {
            isTransitive = false
        }
    }

    sourceSets {
        main {
            runtimeClasspath += sub.sourceSets.main.get().runtimeClasspath
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

subprojects {
    base {
        archivesName.set("megane-forge-${project.name}")
    }

    configurations {
        create("decompile")
    }

    tasks {
        create<DecompileDependenciesTask>("decompileDependencies")
    }
}
