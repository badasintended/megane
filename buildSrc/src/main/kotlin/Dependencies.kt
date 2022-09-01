import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

object versions {
    val minecraft = "1.18.2"
}

object deps {
    val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    val yarn = "net.fabricmc:yarn:1.18.2+build.3:v2"
    val fabricLoader = "net.fabricmc:fabric-loader:0.14.9"

    val wthit = "mcp.mobius.waila:wthit:fabric-4.13.3"

    val ae2 = "appeng:appliedenergistics2-fabric:11.1.6"
    val alloyForge = "maven.modrinth:jhl28YkY:2.0.13+1.18"
    val create = "com.simibubi:Create:0.5.0c-708"
    val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:6.2.62"
    val extraGen = "maven.modrinth:VXtwLg17:1.2.1-BETA+1.18"
    val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.58.0+1.18.2"
    val flk = "net.fabricmc:fabric-language-kotlin:1.8.3+kotlin.1.7.10"
    val kibe = "maven.modrinth:OvlwmUdC:1.9.9-BETA+1.18"
    val modernDynamics = "maven.modrinth:fMpvLrnF:0.2.5-beta"
    val modmenu = "com.terraformersmc:modmenu:3.2.3"
    val noIndium = "me.luligabi:NoIndium:1.0.2+1.18.2"
    val owo = "io.wispforest:owo-lib:0.7.2-no-cme+1.18"
    val patchouli = "vazkii.patchouli:Patchouli:1.18.2-71.1-FABRIC"

    val dmlSim = "curse.maven:cursemod-508931:3785658"
    val indrev = "curse.maven:cursemod-391708:3759591"
    val luggage = "curse.maven:cursemod-594709:3870818"
    val pal = "io.github.ladysnake:PlayerAbilityLib:1.5.1"
    val rebornCore = "RebornCore:RebornCore-1.18:5.2.0"
    val techReborn = "TechReborn:TechReborn-1.18:5.2.0"
    val wirelessNet = "curse.maven:cursemod-461316:3759595"

    object lba {
        val core = "alexiil.mc.lib:libblockattributes-core:0.10.2"
        val fluids = "alexiil.mc.lib:libblockattributes-fluids:0.10.2"
        val items = "alexiil.mc.lib:libblockattributes-items:0.10.2"
    }

    val trEnergy = "teamreborn:energy:2.2.0"
    val libgui = "io.github.cottonmc:LibGui:5.4.2+1.18.2"
    val magna = "com.github.Draylar:magna:1.7.1-1.18"
    val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
    val fakePlayer = "dev.cafeteria:fake-player-api:0.3.0"
}

fun DependencyHandlerScope.modImpl(dep: String, dependencyConfiguration: ExternalModuleDependency.() -> Unit = {}) {
    "modImplementation"(dep) {
        if (!dep.startsWith("net.fabricmc.fabric-api")) exclude(group = "net.fabricmc.fabric-api")
        dependencyConfiguration()
    }
}

fun ExternalModuleDependency.exclude(dep: String) {
    val (group, module) = dep.split(':', limit = 2)
    exclude(group, module)
}
