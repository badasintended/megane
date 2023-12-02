import deps.fabric
import deps.forge
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import version.CurseForgeVersionFetcher
import version.ModrinthVersionFetcher
import version.VersionFetcher
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.net.http.HttpClient
import java.util.*
import kotlin.reflect.KProperty

abstract class FetchLatestVersionsTask : DefaultTask() {
    init {
        group = "megane"
    }

    @TaskAction
    fun run() {
        val output = StringBuilder()
        val http = HttpClient.newHttpClient()

        fun out(line: String = "") {
            output.appendLine(line)
            println(line)
        }

        class VersionFetcherDsl<T>(
            val versionFetcher: VersionFetcher<T>,
            val loader: String
        ) {
            fun fetch(name: KProperty<*>, project: String, mc: String = versions.minecraft, parser: (T) -> String) {
                val res = versionFetcher.getLatestVersionFor(http, project, mc, loader)
                if (res != null) {
                    // language=kotlin
                    out("const val ${name.name} = \"${parser(res)}\"")
                } else {
                    throw GradleException("Failed to get version for ${name}")
                }
            }
        }

        fun <T> fetcher(versionFetcher: VersionFetcher<T>, loader: String, action: VersionFetcherDsl<T>.() -> Unit) {
            action(VersionFetcherDsl(versionFetcher, loader))
        }

        out("\nobject fabric {")
        fetcher(ModrinthVersionFetcher, "fabric") {
            out("\nobject wthit {")
            fetch(fabric.wthit::api, mrIds.wthit) { "mcp.mobius.waila:wthit-api:${it.version_number}" }
            fetch(fabric.wthit::runtime, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
            out("}\n")

            fetch(fabric::badpackets, mrIds.badpackets) { "lol.bai:badpackets:${it.version_number}" }
            out()

            fetch(fabric::ae2, mrIds.ae2) { "appeng:appliedenergistics2-fabric:${it.version_number.removePrefix("fabric-")}" }
            fetch(fabric::alloyForge, mrIds.alloyForge) { it.maven }
            fetch(fabric::architectury, mrIds.architectury) { "dev.architectury:architectury-fabric:${it.version_number.removeSuffix("+fabric")}" }
            fetch(fabric::create, mrIds.createFabric) { "com.simibubi.create:create-fabric-1.19.2:${it.version_number}" }
            fetch(fabric::clothConfig, mrIds.clothConfig) { "me.shedaniel.cloth:cloth-config-fabric:${it.version_number.removeSuffix("+fabric")}" }
            fetch(fabric::extraGen, mrIds.extraGen, "1.19") { it.maven }
            fetch(fabric::fabricApi, mrIds.fabricApi) { "net.fabricmc.fabric-api:fabric-api:${it.version_number}" }
            fetch(fabric::flk, mrIds.flk) { "net.fabricmc:fabric-language-kotlin:${it.version_number}" }
            fetch(fabric::kibe, mrIds.kibe) { it.maven }
            fetch(fabric::modernDynamics, mrIds.modernDynamics) { it.maven }
            fetch(fabric::modmenu, mrIds.modmenu) { "com.terraformersmc:modmenu:${it.version_number}" }
            fetch(fabric::noIndium, mrIds.noIndium, "1.19") { "me.luligabi:NoIndium:${it.version_number}" }
            fetch(fabric::owo, mrIds.owo, "1.19") { "io.wispforest:owo-lib:${it.version_number}" }
            fetch(fabric::patchouli, mrIds.patchouli) { "vazkii.patchouli:Patchouli:${it.version_number.toUpperCase(Locale.ROOT)}" }
            fetch(fabric::powah, mrIds.powah) { it.maven }
        }
        out()
        fetcher(CurseForgeVersionFetcher, "fabric") {
            fetch(fabric::dml, cfIds.dml) { it.maven }
            fetch(fabric::indrev, cfIds.indrev) { it.maven }
            fetch(fabric::luggage, cfIds.luggage) { it.maven }
            fetch(fabric::pal, cfIds.pal) { "io.github.ladysnake:PlayerAbilityLib:${it.download.fileName.removePrefix("pal-")}" }
            fetch(fabric::rebornCore, cfIds.rebornCore) { "RebornCore:RebornCore-1.19:${it.download.fileName.removePrefix("RebornCore-")}" }
            fetch(fabric::techReborn, cfIds.techReborn) { "TechReborn:TechReborn-1.19:${it.download.fileName.removePrefix("TechReborn-")}" }
            fetch(fabric::wirelessNet, cfIds.wirelessNet, "1.19") { it.maven }
        }
        out("}")

        out("\nobject forge {")
        fetcher(ModrinthVersionFetcher, "forge") {
            out("\nobject wthit {")
            fetch(forge.wthit::api, mrIds.wthit) { "mcp.mobius.waila:wthit-api:${it.version_number}" }
            fetch(forge.wthit::runtime, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
            out("}\n")

            fetch(forge::badpackets, mrIds.badpackets) { "lol.bai:badpackets:${it.version_number}" }
            out()

            fetch(forge::ae2, mrIds.ae2) { "appeng:appliedenergistics2-forge:${it.version_number.removePrefix("forge-")}" }
            fetch(forge::create, mrIds.createForge) { it.maven }
            fetch(forge::ie, mrIds.ie) { it.maven }
            fetch(forge::rs, mrIds.rs) { it.maven }
            fetch(forge::jei, mrIds.jei) { it.maven }

            out("\nobject mekanism {")
            fetch(forge.mekanism::core, mrIds.mekCore) { "mekanism:Mekanism:1.19.2-${it.version_number}" }
            fetch(forge.mekanism::generators, mrIds.mekCore) { "mekanism:Mekanism:1.19.2-${it.version_number}:generators" }
            out("}")

            out("\nobject thermal {")
            fetch(forge.thermal::cofhCore, mrIds.cofhCore) { it.maven }
            fetch(forge.thermal::foundation, mrIds.thermalFoundation) { it.maven }
            fetch(forge.thermal::expansion, mrIds.thermalExpansion) { it.maven }
            out("}")
        }
        out("}")

        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(output.toString()), null)
    }
}

