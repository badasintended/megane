@file:Suppress("ClassName", "ConstPropertyName")

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.exclude

object versions {
    const val minecraft = "1.19.2"
}

object deps {
    const val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    const val fabricLoader = "net.fabricmc:fabric-loader:0.14.24"

    object fabric {
        const val wthit = "mcp.mobius.waila:wthit:fabric-5.19.2"

        const val ae2 = "appeng:appliedenergistics2-fabric:12.9.8"
        const val alloyForge = "maven.modrinth:jhl28YkY:2.0.20+1.19"
        const val architectury = "dev.architectury:architectury-fabric:6.5.85"
        const val create = "com.simibubi.create:create-fabric-1.19.2:0.5.1-c-build.1160+mc1.19.2"
        const val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:8.3.103"
        const val extraGen = "maven.modrinth:VXtwLg17:1.2.1-BETA+1.19"
        const val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.76.1+1.19.2"
        const val flk = "net.fabricmc:fabric-language-kotlin:1.10.14+kotlin.1.9.20"
        const val kibe = "maven.modrinth:OvlwmUdC:1.10.1-BETA+1.19"
        const val modernDynamics = "maven.modrinth:fMpvLrnF:0.6.1-beta"
        const val modmenu = "com.terraformersmc:modmenu:4.2.0-beta.2"
        const val noIndium = "me.luligabi:NoIndium:1.1.0+1.19"
        const val owo = "io.wispforest:owo-lib:0.9.3+1.19"
        const val patchouli = "vazkii.patchouli:Patchouli:1.19.2-77-FABRIC"
        const val powah = "maven.modrinth:KZO4S4DO:4.0.11-fabric"

        const val dml = "curse.maven:cursemod-398614:4437241"
        const val indrev = "curse.maven:cursemod-391708:4020957"
        const val luggage = "curse.maven:cursemod-594709:4284469"
        const val pal = "io.github.ladysnake:PlayerAbilityLib:1.6.0"
        const val rebornCore = "RebornCore:RebornCore-1.19:5.4.0"
        const val techReborn = "TechReborn:TechReborn-1.19:5.4.0"
        const val wirelessNet = "curse.maven:cursemod-461316:3876132"

        object lba {
            const val core = "alexiil.mc.lib:libblockattributes-core:0.11.1"
            const val fluids = "alexiil.mc.lib:libblockattributes-fluids:0.11.1"
            const val items = "alexiil.mc.lib:libblockattributes-items:0.11.1"
        }

        const val trEnergy = "teamreborn:energy:2.2.0"
        const val libgui = "io.github.cottonmc:LibGui:6.0.1+1.19"
        const val magna = "com.github.GabrielOlvH:magna:0.5.2"
        const val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
        const val fakePlayer = "dev.cafeteria:fake-player-api:0.5.0"
        const val mixinExtras = "io.github.llamalad7:mixinextras-fabric:0.2.1"
    }

    object forge {
        const val forge = "net.minecraftforge:forge:${versions.minecraft}-43.2.8"
        const val wthit = "mcp.mobius.waila:wthit:forge-5.17.0"
    }
}

fun ExternalModuleDependency.exclude(dep: String) {
    val (group, module) = dep.split(':', limit = 2)
    exclude(group, module)
}
