import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

object versions {
    val minecraft = "1.18.2"
}

object deps {
    val minecraft = "com.mojang:minecraft:${versions.minecraft}"
    val yarn = "net.fabricmc:yarn:1.18.2+build.3:v2"
    val fabricLoader = "net.fabricmc:fabric-loader:0.14.7"

    val wthit = "mcp.mobius.waila:wthit:fabric-4.10.4"
    val fabricApi = "net.fabricmc.fabric-api:fabric-api:0.53.0+1.18.2"
    val modmenu = "com.terraformersmc:modmenu:3.0.1"

    val ae2 = "appeng:appliedenergistics2-fabric:11.1.2"
    val dmlSim = "curse.maven:deep-mob-learning-simulacrum-508931:3785658"
    val clothConfig = "me.shedaniel.cloth:cloth-config-fabric:6.2.62"
    val extraGen = "curse.maven:extra-generators-475623:3770936"
    val flk = "net.fabricmc:fabric-language-kotlin:1.8.0+kotlin.1.7.0"
    val trEnergy = "teamreborn:energy:2.2.0"
    val indrev = "curse.maven:industrial-revolution-391708:3759591"
    val libgui = "io.github.cottonmc:LibGui:5.4.2+1.18.2"
    val patchouli = "vazkii.patchouli:Patchouli:1.18.2-70-FABRIC"
    val magna = "com.github.Draylar:magna:1.7.1-1.18"
    val stepAttr = "com.github.emilyploszaj:step-height-entity-attribute:v1.0.1"
    val fakePlayer = "dev.cafeteria:fake-player-api:0.3.0"
    val noIndium = "me.luligabi:NoIndium:1.0.2+1.18.2"
    val kibe = "curse.maven:kibe-388832:3671408"
    val pal = "io.github.ladysnake:PlayerAbilityLib:1.5.1"
    val rebornCore = "RebornCore:RebornCore-1.18:5.2.0"
    val techReborn = "TechReborn:TechReborn-1.18:5.2.0"
    val wirelessNet = "curse.maven:wireless-networks-461316:3759595"

    object lba {
        val core = "alexiil.mc.lib:libblockattributes-core:0.10.2"
        val fluids = "alexiil.mc.lib:libblockattributes-fluids:0.10.2"
        val items = "alexiil.mc.lib:libblockattributes-items:0.10.2"
    }
}

fun DependencyHandlerScope.modImpl(dep: String) {
    "modImplementation"(dep) {
        if (!dep.startsWith("net.fabricmc.fabric-api")) exclude(group = "net.fabricmc.fabric-api")
    }
}
