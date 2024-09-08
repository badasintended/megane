@file:Suppress("ClassName", "ConstPropertyName")

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.exclude
import kotlin.reflect.KProperty

object versions {
    const val minecraft = "1.19.2"
}

object deps {
    const val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    const val mixinAp = "org.spongepowered:mixin:0.8.5:processor"

    object common : DependencyPath {
        override val prefix = "common"

        object wthit : DependencyPath {
            override val prefix = "common.wthit"

            val api by json()
            val runtime by json()
        }

        const val mixin = "org.spongepowered:mixin:0.8.5"
        const val asmTree = "org.ow2.asm:asm-tree:9.6"
        const val fabricLoaderEnv = "lol.bai:fabric-loader-environment:0.0.1"
    }

    object fabric : DependencyPath {
        override val prefix = "fabric"

        const val loader = "net.fabricmc:fabric-loader:0.15.10"

        object wthit : DependencyPath {
            override val prefix = "fabric.wthit"

            val api by json()
            val runtime by json()
        }

        val badpackets by json()

        val ae2 by json()
        val alloyForge by json()
        val architectury by json()
        val create by json()
        val clothConfig by json()
        val extraGen by json()
        val fabricApi by json()
        val flk by json()
        val kibe by json()
        val modernDynamics by json()
        val modmenu by json()
        val noIndium by json()
        val owo by json()
        val patchouli by json()
        val powah by json()

        val dml by json()
        val indrev by json()
        val luggage by json()
        val pal by json()
        val rebornCore by json()
        val techReborn by json()
        val wirelessNet by json()
        val lapisReserve by json()
        val resourceChickens by json()

        const val trEnergy = "teamreborn:energy:2.2.0"
        const val libgui = "io.github.cottonmc:LibGui:6.0.1+1.19"
        const val magna = "com.github.GabrielOlvH:magna:0.5.2"
        const val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
        const val fakePlayer = "dev.cafeteria:fake-player-api:0.5.0"
        const val mixinExtras = "io.github.llamalad7:mixinextras-fabric:0.2.1"
    }

    object forge : DependencyPath {
        override val prefix = "forge"

        const val forge = "net.minecraftforge:forge:${versions.minecraft}-43.3.5"

        object wthit : DependencyPath {
            override val prefix = "forge.wthit"

            val api by json()
            val runtime by json()
        }

        val badpackets by json()

        val ae2 by json()
        val create by json()
        val ie by json()
        val rs by json()
        val jei by json()
        val lapisReserve by json()
        val resourceChickens by json()
        val productiveBees by json()
        val top by json()

        object mekanism : DependencyPath {
            override val prefix = "forge.mekanism"

            val core by json()
            val generators by json()
        }

        object thermal : DependencyPath {
            override val prefix = "forge.thermal"

            val cofhCore by json()
            val foundation by json()
            val expansion by json()
        }
    }
}

fun ExternalModuleDependency.exclude(dep: String) {
    val (group, module) = dep.split(':', limit = 2)
    exclude(group, module)
}

fun Project.initializeDependencies() {
    dependenciesJson = JsonMapper().readTree(file("dependencies.json"))
}

private lateinit var dependenciesJson: JsonNode

interface DependencyPath {
    val prefix: String

    fun json() = DependencyDelegate()
}

class DependencyDelegate {
    operator fun getValue(self: DependencyPath, property: KProperty<*>): String {
        return dependenciesJson["${self.prefix}.${property.name}"].asText()
    }
}
