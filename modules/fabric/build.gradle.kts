import me.modmuss50.mpp.ReleaseType

plugins {
    id("fabric-loom") version "1.6.12"
    id("me.modmuss50.mod-publish-plugin")
    id("lol.bai.explosion")
}

publishMods {
    changelog.set("https://github.com/badasintended/megane/releases/tag/${project.version}")
    type.set(ReleaseType.STABLE)
    modLoaders.add("fabric")

    val curseForgeApi = providers.environmentVariable("CURSEFORGE_API")
    val modrinthToken = providers.environmentVariable("MODRINTH_TOKEN")
    dryRun.set(!(curseForgeApi.isPresent && modrinthToken.isPresent))

    curseforge {
        projectId.set("408118")
        accessToken.set(curseForgeApi)
        minecraftVersions.add("1.19.2")

        requires(cfSlugs.wthitFabric)
        optional(
            cfSlugs.alloyForge,
            cfSlugs.ae2,
            cfSlugs.createFabric,
            cfSlugs.dml,
            cfSlugs.extraGen,
            cfSlugs.indrev,
            cfSlugs.kibe,
            cfSlugs.luggage,
            cfSlugs.modernDynamics,
            cfSlugs.powah,
            cfSlugs.rebornCore,
            cfSlugs.techReborn,
            cfSlugs.wirelessNet,
            cfSlugs.lapisReserve,
            cfSlugs.resourceChickens,
        )
    }

    modrinth {
        projectId.set("ZNk5S5U6")
        accessToken.set(modrinthToken)
        minecraftVersions.add("1.19.2")

        requires(mrIds.wthit)
        optional(
            mrIds.alloyForge,
            mrIds.ae2,
            mrIds.createFabric,
            mrIds.dml,
            mrIds.extraGen,
            mrIds.kibe,
            mrIds.modernDynamics,
            mrIds.powah,
        )
    }
}

configurations.all {
    resolutionStrategy {
        force(deps.fabric.loader)
    }
}

dependencies {
    minecraft(deps.minecraft)
    mappings(loom.officialMojangMappings())

    modImplementation(deps.fabric.loader)
    modImplementation(deps.fabric.fabricApi)
    modImplementation(deps.fabric.modmenu)
    modImplementation(deps.fabric.wthit.runtime)
    modRuntimeOnly(deps.fabric.badpackets)

    modImplementation(deps.fabric.alloyForge)
    modImplementation(deps.fabric.owo)

    modImplementation(deps.fabric.ae2) {
        exclude("curse.maven:jade-324717")
        exclude("mezz.jei:jei-${versions.minecraft}-fabric")
    }

    modImplementation(deps.fabric.create) {
        exclude("dev.emi:emi")
        exclude("com.github.LlamaLad7:MixinExtras")
        exclude("com.github.llamalad7.mixinextras:mixinextras-fabric")
        exclude("me.luligabi:NoIndium")
    }
    modImplementation(deps.fabric.mixinExtras)
    modImplementation(deps.fabric.noIndium)

    modImplementation(explosion.fabric(deps.fabric.dml))
    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.clothConfig)

    modImplementation(deps.fabric.extraGen)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.flk)

    modImplementation(deps.fabric.indrev)
    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.libgui)
    modImplementation(deps.fabric.patchouli)
    modImplementation(deps.fabric.magna)
    modImplementation(deps.fabric.stepAttr)
    modImplementation(deps.fabric.fakePlayer)
    modImplementation(deps.fabric.noIndium)

    modImplementation(deps.fabric.kibe)
    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.pal)

    modImplementation(deps.fabric.lapisReserve)

    modImplementation(deps.fabric.luggage)

    modImplementation(deps.fabric.modernDynamics)
    modImplementation(deps.fabric.trEnergy)

    modImplementation(deps.fabric.powah)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.architectury)
    modImplementation(deps.fabric.clothConfig)

    modImplementation(deps.fabric.rebornCore)

    modImplementation(deps.fabric.resourceChickens)

    modImplementation(deps.fabric.techReborn)

    modImplementation(deps.fabric.wirelessNet)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.libgui)
}

loom {
    runs.configureEach {
        ideConfigGenerated(true)
        vmArgs("-XX:+AllowEnhancedClassRedefinition")
    }
}

tasks.processResources {
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

afterEvaluate {
    publishMods {
        file.set(tasks.remapJar.get().archiveFile)
    }
}
