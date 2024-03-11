@file:Suppress("ClassName", "ConstPropertyName")

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.exclude

object versions {
    const val minecraft = "1.20.1"
}

object deps {
    const val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    const val mixinAp = "net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5"

    object fabric {
        const val loader = "net.fabricmc:fabric-loader:0.15.6"

        object wthit {
            const val api = "mcp.mobius.waila:wthit-api:fabric-8.8.0"
            const val runtime = "mcp.mobius.waila:wthit:fabric-8.8.0"
        }

        const val badpackets = "lol.bai:badpackets:fabric-0.4.3"

        const val ae2 = "appeng:appliedenergistics2-fabric:15.0.23"
        const val alloyForge = "maven.modrinth:jhl28YkY:j3GoCoCc"
        const val architectury = "dev.architectury:architectury-fabric:9.2.14"
        const val create = "com.simibubi.create:create-fabric-1.20.1:0.5.1-f-build.1335+mc1.20.1"
        const val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:11.1.118"
        const val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.92.0+1.20.1"
        const val flk = "net.fabricmc:fabric-language-kotlin:1.10.19+kotlin.1.9.23"
        const val kibe = "maven.modrinth:OvlwmUdC:fhSgEP7Z"
        const val modernDynamics = "maven.modrinth:fMpvLrnF:dLMmaJID"
        const val modmenu = "com.terraformersmc:modmenu:7.2.2"
        const val noIndium = "me.luligabi:NoIndium:1.1.0+1.20"
        const val owo = "io.wispforest:owo-lib:0.11.2+1.20"
        const val patchouli = "vazkii.patchouli:Patchouli:1.20.1-84-FABRIC"
        const val powah = "maven.modrinth:KZO4S4DO:zrPCk4ZJ"

        const val dml = "curse.maven:cursemod-398614:5032813"
        const val indrev = "curse.maven:cursemod-391708:4742613"
        const val luggage = "curse.maven:cursemod-594709:4284469"
        const val pal = "io.github.ladysnake:PlayerAbilityLib:1.8.0"
        const val rebornCore = "RebornCore:RebornCore-1.20:5.8.7"
        const val techReborn = "TechReborn:TechReborn-1.20:5.8.7"
        const val wirelessNet = "curse.maven:cursemod-461316:4610810"

        const val trEnergy = "teamreborn:energy:2.2.0"
        const val libgui = "io.github.cottonmc:LibGui:8.1.1+1.20.1"
        const val magna = "dev.draylar:magna:1.10.0+1.20.1"
        const val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.2.0"
    }

    object forge {
        const val forge = "net.neoforged:forge:${versions.minecraft}-47.1.97"

        object wthit {
            const val api = "mcp.mobius.waila:wthit-api:forge-8.8.0"
            const val runtime = "mcp.mobius.waila:wthit:forge-8.8.0"
        }

        const val badpackets = "lol.bai:badpackets:forge-0.4.3"

        const val ae2 = "appeng:appliedenergistics2-forge:15.0.23"
        const val create = "maven.modrinth:LNytGWDc:HNYrbfZZ"
        const val ie = "maven.modrinth:tIm2nV03:uqGFpLXw"
        const val rs = "maven.modrinth:KDvYkUg3:ZITLFjjf"
        const val jei = "maven.modrinth:u6dRKJwZ:PeYsGsQy"

        object mekanism {
            const val core = "mekanism:Mekanism:1.20.1-10.4.6.20"
            const val generators = "mekanism:Mekanism:1.20.1-10.4.6.20:generators"
        }

        object thermal {
            const val cofhCore = "maven.modrinth:OWSRM4vD:C1iOYlLu"
            const val foundation = "maven.modrinth:Xvg6q5Wp:dUiPDb6I"
            const val expansion = "maven.modrinth:hmD6rrUJ:kg4h60cQ"
        }
    }
}

fun ExternalModuleDependency.exclude(dep: String) {
    val (group, module) = dep.split(':', limit = 2)
    exclude(group, module)
}
