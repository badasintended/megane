import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

object versions {
    val minecraft = "1.18.2"
}

object deps {
    val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    val yarn = "net.fabricmc:yarn:1.18.2+build.3:v2"
    val fabricLoader = "net.fabricmc:fabric-loader:0.14.15"

    val wthit = "mcp.mobius.waila:wthit:fabric-4.13.6"

    val ae2 = "appeng:appliedenergistics2-fabric:11.7.0"
    val alloyForge = "maven.modrinth:jhl28YkY:2.0.16+1.18.2"
    val architectury = "dev.architectury:architectury-fabric:4.11.89"
    val create = "com.simibubi.create:create-fabric-1.18.2:0.5.0.i-963+1.18.2"
    val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:6.3.81"
    val extraGen = "maven.modrinth:VXtwLg17:1.2.1-BETA+1.18"
    val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.67.1+1.18.2"
    val flk = "net.fabricmc:fabric-language-kotlin:1.9.1+kotlin.1.8.10"
    val kibe = "maven.modrinth:OvlwmUdC:1.9.11-BETA+1.18"
    val modernDynamics = "maven.modrinth:fMpvLrnF:0.2.6-beta"
    val modmenu = "com.terraformersmc:modmenu:3.2.5"
    val noIndium = "me.luligabi:NoIndium:1.0.2+1.18.2"
    val owo = "io.wispforest:owo-lib:0.7.2-no-cme+1.18"
    val patchouli = "vazkii.patchouli:Patchouli:1.18.2-71.1-FABRIC"
    val powah = "maven.modrinth:KZO4S4DO:3.0.7-fabric"

    val dmlSim = "curse.maven:cursemod-508931:3785658"
    val indrev = "curse.maven:cursemod-391708:4195667"
    val luggage = "curse.maven:cursemod-594709:3870818"
    val pal = "io.github.ladysnake:PlayerAbilityLib:1.5.1"
    val rebornCore = "RebornCore:RebornCore-1.18:5.2.1"
    val techReborn = "TechReborn:TechReborn-1.18:5.2.1"
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
    val mixinExtras = "com.github.LlamaLad7:MixinExtras:0.1.1-rc.4"
    val portingLib = "io.github.fabricators_of_create:Porting-Lib:1.2.783-beta+1.18.2"
    val registrate = "com.tterrag.registrate_fabric:Registrate:MC1.18.2-1.1.10"
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
