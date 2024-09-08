import groovy.lang.GroovyObject
import me.modmuss50.mpp.ReleaseType
import net.minecraftforge.gradle.common.util.RunConfig

plugins {
    id("net.minecraftforge.gradle") version "6.0.25"
    id("org.spongepowered.mixin") version "0.7.38"
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
            cfSlugs.ie,
            cfSlugs.lapisReserve,
            cfSlugs.resourceChickens,
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
            mrIds.ie,
        )
    }
}

dependencies {
    minecraft(deps.forge.forge)

    compileOnly(fg.deobf(deps.forge.wthit.api))
    runtimeOnly(fg.deobf(deps.forge.wthit.runtime))
    runtimeOnly(fg.deobf(deps.forge.badpackets))
    runtimeOnly(fg.deobf(deps.forge.jei))
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.ae2))

    implementation(fg.deobf(deps.forge.create))

    implementation(fg.deobf(deps.forge.ie))

    implementation(fg.deobf(deps.forge.lapisReserve))

    implementation(fg.deobf(deps.forge.mekanism.core))

    implementation(fg.deobf(deps.forge.productiveBees))

    implementation(fg.deobf(deps.forge.resourceChickens))
    runtimeOnly(fg.deobf(deps.forge.top))
}

minecraft {
    mappings("official", versions.minecraft)

    runs {
        val runConfig = Action<RunConfig> {
            ideaModule(rootProject.name + project.path.replace(':', '.') + ".main")
            workingDirectory(file("run"))
            jvmArgs("-XX:+AllowEnhancedClassRedefinition")

            mods.create("megane") {
                source(sourceSets["main"])
            }
        }

        create("client", runConfig)
        create("server", runConfig)
    }
}

mixin {
    add(sourceSets.main.get(), "megane.refmap.json")
    config("megane.mixins.json")

    debug.apply {
        this as GroovyObject
        setProperty("export", true)
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
