import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

object versions {
    val minecraft = "1.19.3"
}

object deps {
    val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    val yarn = "net.fabricmc:yarn:1.19.3+build.5:v2"
    val fabricLoader = "net.fabricmc:fabric-loader:0.14.18"

    val wthit = "mcp.mobius.waila:wthit:fabric-6.4.1"

    val ae2 = "appeng:appliedenergistics2-fabric:13.0.6-beta"
    val alloyForge = "maven.modrinth:jhl28YkY:2.0.19+1.19.3"
    val architectury = "dev.architectury:architectury-fabric:7.1.78"
    val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:9.0.94"
    val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.76.1+1.19.3"
    val flk = "net.fabricmc:fabric-language-kotlin:1.9.3+kotlin.1.8.20"
    val modmenu = "com.terraformersmc:modmenu:5.1.0-beta.4"
    val noIndium = "me.luligabi:NoIndium:1.1.0+1.19.3"
    val owo = "io.wispforest:owo-lib:0.10.3+1.19.3"
    val patchouli = "vazkii.patchouli:Patchouli:1.19.3-78-FABRIC"

    val pal = "io.github.ladysnake:PlayerAbilityLib:1.7.0"
    val rebornCore = "RebornCore:RebornCore-1.19:5.5.0"
    val techReborn = "TechReborn:TechReborn-1.19:5.5.0"

    object lba {
        val core = "alexiil.mc.lib:libblockattributes-core:0.12.0"
        val fluids = "alexiil.mc.lib:libblockattributes-fluids:0.12.0"
        val items = "alexiil.mc.lib:libblockattributes-items:0.12.0"
    }

    val trEnergy = "teamreborn:energy:3.0.0"
    val libgui = "io.github.cottonmc:LibGui:6.5.3+1.19.3"
    val magna = "com.github.GabrielOlvH:magna:0.5.2"
    val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.1.0"
    val fakePlayer = "dev.cafeteria:fake-player-api:0.5.0"
    val mixinExtras = "com.github.LlamaLad7:MixinExtras:0.2.0-beta.6"
    val portingLib = "io.github.fabricators_of_create.Porting-Lib:porting-lib:2.1.852+1.19.3"
    val registrate = "com.tterrag.registrate_fabric:Registrate:1.1.50-MC1.19.2"

//    val create = "com.simibubi.create:create-fabric-1.19.2:0.5.0.i-961+1.19.2"
//    val extraGen = "maven.modrinth:VXtwLg17:1.2.1-BETA+1.19"
//    val kibe = "maven.modrinth:OvlwmUdC:1.9.11-BETA+1.19"
//    val modernDynamics = "maven.modrinth:fMpvLrnF:0.4.0-alpha"
//    val powah = "maven.modrinth:KZO4S4DO:4.0.6-fabric"
//    val indrev = "curse.maven:cursemod-391708:4020957"
//    val luggage = "curse.maven:cursemod-594709:4284469"
//    val wirelessNet = "curse.maven:cursemod-461316:3876132"
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
