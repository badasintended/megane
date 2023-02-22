import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

object versions {
    val minecraft = "1.19.2"
}

object deps {
    val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    val yarn = "net.fabricmc:yarn:1.19.2+build.8:v2"
    val fabricLoader = "net.fabricmc:fabric-loader:0.14.15"

    val wthit = "mcp.mobius.waila:wthit:fabric-5.13.5"

    val ae2 = "appeng:appliedenergistics2-fabric:12.9.2"
    val alloyForge = "maven.modrinth:jhl28YkY:2.0.19+1.19"
    val architectury = "dev.architectury:architectury-fabric:6.5.69"
    val create = "com.simibubi.create:create-fabric-1.19.2:0.5.0.i-961+1.19.2"
    val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:8.2.88"
    val extraGen = "maven.modrinth:VXtwLg17:1.2.1-BETA+1.19"
    val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.73.2+1.19.2"
    val flk = "net.fabricmc:fabric-language-kotlin:1.9.1+kotlin.1.8.10"
    val kibe = "maven.modrinth:OvlwmUdC:1.9.11-BETA+1.19"
    val modernDynamics = "maven.modrinth:fMpvLrnF:0.4.0-alpha"
    val modmenu = "com.terraformersmc:modmenu:4.2.0-beta.2"
    val noIndium = "me.luligabi:NoIndium:1.1.0+1.19"
    val owo = "io.wispforest:owo-lib:0.9.3+1.19"
    val patchouli = "vazkii.patchouli:Patchouli:1.19.2-77-FABRIC"
    val powah = "maven.modrinth:KZO4S4DO:4.0.6-fabric"

    val indrev = "curse.maven:cursemod-391708:4020957"
    val luggage = "curse.maven:cursemod-594709:4284469"
    val pal = "io.github.ladysnake:PlayerAbilityLib:1.6.0"
    val rebornCore = "RebornCore:RebornCore-1.19:5.4.0"
    val techReborn = "TechReborn:TechReborn-1.19:5.4.0"
    val wirelessNet = "curse.maven:cursemod-461316:3876132"

    object lba {
        val core = "alexiil.mc.lib:libblockattributes-core:0.11.1"
        val fluids = "alexiil.mc.lib:libblockattributes-fluids:0.11.1"
        val items = "alexiil.mc.lib:libblockattributes-items:0.11.1"
    }

    val trEnergy = "teamreborn:energy:2.2.0"
    val libgui = "io.github.cottonmc:LibGui:6.0.1+1.19"
    val magna = "com.github.GabrielOlvH:magna:0.5.2"
    val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
    val fakePlayer = "dev.cafeteria:fake-player-api:0.5.0"
    val mixinExtras = "com.github.LlamaLad7:MixinExtras:0.1.1-rc.4"
    val portingLib = "io.github.fabricators_of_create.Porting-Lib:porting-lib:2.1.811+1.19.2"
    val registrate = "com.tterrag.registrate_fabric:Registrate:1.1.50-MC1.19.2"
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
