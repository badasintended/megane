@file:Suppress("ClassName", "ConstPropertyName")

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.exclude

object versions {
    const val minecraft = "1.19.2"
}

object deps {
    const val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    const val mixinAp = "org.spongepowered:mixin:0.8.5:processor"

    object fabric {
        const val loader = "net.fabricmc:fabric-loader:0.14.24"

        object wthit {
            const val api = "mcp.mobius.waila:wthit-api:fabric-5.23.0"
            const val runtime = "mcp.mobius.waila:wthit:fabric-5.23.0"
        }

        const val badpackets = "lol.bai:badpackets:fabric-0.2.3"

        const val ae2 = "appeng:appliedenergistics2-fabric:12.9.9"
        const val alloyForge = "maven.modrinth:jhl28YkY:q1L7hbA8"
        const val architectury = "dev.architectury:architectury-fabric:6.6.92"
        const val create = "com.simibubi.create:create-fabric-1.19.2:0.5.1-f-build.1334+mc1.19.2"
        const val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:8.3.115"
        const val extraGen = "maven.modrinth:VXtwLg17:2p7qWneI"
        const val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.77.0+1.19.2"
        const val flk = "net.fabricmc:fabric-language-kotlin:1.10.19+kotlin.1.9.23"
        const val kibe = "maven.modrinth:OvlwmUdC:6GPdTrdx"
        const val modernDynamics = "maven.modrinth:fMpvLrnF:uSorcoNB"
        const val modmenu = "com.terraformersmc:modmenu:4.2.0-beta.2"
        const val noIndium = "me.luligabi:NoIndium:1.1.0+1.19"
        const val owo = "io.wispforest:owo-lib:0.9.3+1.19"
        const val patchouli = "vazkii.patchouli:Patchouli:1.19.2-77-FABRIC"
        const val powah = "maven.modrinth:KZO4S4DO:uK9hCoDv"

        const val dml = "curse.maven:cursemod-398614:4437241"
        const val indrev = "curse.maven:cursemod-391708:4020957"
        const val luggage = "curse.maven:cursemod-594709:4284469"
        const val pal = "io.github.ladysnake:PlayerAbilityLib:1.6.0"
        const val rebornCore = "RebornCore:RebornCore-1.19:5.4.0"
        const val techReborn = "TechReborn:TechReborn-1.19:5.4.0"
        const val wirelessNet = "curse.maven:cursemod-461316:3876132"

        const val trEnergy = "teamreborn:energy:2.2.0"
        const val libgui = "io.github.cottonmc:LibGui:6.0.1+1.19"
        const val magna = "com.github.GabrielOlvH:magna:0.5.2"
        const val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
        const val fakePlayer = "dev.cafeteria:fake-player-api:0.5.0"
        const val mixinExtras = "io.github.llamalad7:mixinextras-fabric:0.2.1"
    }

    object forge {
        const val forge = "net.minecraftforge:forge:${versions.minecraft}-43.3.5"

        object wthit {
            const val api = "mcp.mobius.waila:wthit-api:forge-5.23.0"
            const val runtime = "mcp.mobius.waila:wthit:forge-5.23.0"
        }

        const val badpackets = "lol.bai:badpackets:forge-0.2.3"

        const val ae2 = "appeng:appliedenergistics2-forge:12.9.9"
        const val create = "maven.modrinth:LNytGWDc:Vfzp1Xaz"
        const val ie = "maven.modrinth:tIm2nV03:7Mm7llLR"
        const val rs = "maven.modrinth:KDvYkUg3:q3LiZwUb"
        const val jei = "maven.modrinth:u6dRKJwZ:Y1en3Fb4"

        object mekanism {
            const val core = "mekanism:Mekanism:1.19.2-10.3.9.13"
            const val generators = "mekanism:Mekanism:1.19.2-10.3.9.13:generators"
        }

        object thermal {
            const val cofhCore = "maven.modrinth:OWSRM4vD:ssRHxD6e"
            const val foundation = "maven.modrinth:Xvg6q5Wp:kaIOIjDc"
            const val expansion = "maven.modrinth:hmD6rrUJ:W9opx6mY"
        }
    }
}

fun ExternalModuleDependency.exclude(dep: String) {
    val (group, module) = dep.split(':', limit = 2)
    exclude(group, module)
}
