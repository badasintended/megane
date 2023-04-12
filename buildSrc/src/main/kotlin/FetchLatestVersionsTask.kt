import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import version.CurseForgeVersionFetcher
import version.ModrinthVersionFetcher
import version.VersionFetcher
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.net.http.HttpClient
import java.util.Locale
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

abstract class FetchLatestVersionsTask : DefaultTask() {
    @TaskAction
    fun run() {
        val output = StringBuilder()
        val http = HttpClient.newHttpClient()

        fun out(line: String = "") {
            output.appendLine(line)
            println(line)
        }

        class VersionFetcherDsl<T>(
            val versionFetcher: VersionFetcher<T>
        ) {
            fun fetch(name: KProperty<*>, project: String, mc: String = versions.minecraft, parser: (T) -> String) {
                val res = versionFetcher.getLatestVersionFor(http, project, mc)
                if (res != null) {
                    out("val ${name.name} = \"${parser(res)}\"")
                } else {
                    throw GradleException("Failed to get version for ${name}")
                }
            }
        }

        fun <T> fetcher(versionFetcher: VersionFetcher<T>, action: VersionFetcherDsl<T>.() -> Unit) {
            action(VersionFetcherDsl(versionFetcher))
        }

        fetcher(ModrinthVersionFetcher) {
            fetch(deps::wthit, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
            out()

            fetch(deps::ae2, mrIds.ae2) { "appeng:appliedenergistics2-fabric:${it.version_number.removePrefix("fabric-")}" }
            fetch(deps::alloyForge, mrIds.alloyForge) { it.maven }
            fetch(deps::architectury, mrIds.architectury) { "dev.architectury:architectury-fabric:${it.version_number.removeSuffix("+fabric")}" }
//            fetch(deps::create, mrIds.create) { "com.simibubi.create:create-fabric-1.19.2:${it.version_number}" }
            fetch(deps::clothConfig, mrIds.clothConfig) { "me.shedaniel.cloth:cloth-config-fabric:${it.version_number.removeSuffix("+fabric")}" }
//            fetch(deps::extraGen, mrIds.extraGen, "1.19") { it.maven }
            fetch(deps::fabricApi, mrIds.fabricApi) { "net.fabricmc.fabric-api:fabric-api:${it.version_number}" }
            fetch(deps::flk, mrIds.flk) { "net.fabricmc:fabric-language-kotlin:${it.version_number}" }
//            fetch(deps::kibe, mrIds.kibe) { it.maven }
//            fetch(deps::modernDynamics, mrIds.modernDynamics) { it.maven }
            fetch(deps::modmenu, mrIds.modmenu) { "com.terraformersmc:modmenu:${it.version_number}" }
            fetch(deps::noIndium, mrIds.noIndium) { "me.luligabi:NoIndium:${it.version_number}" }
            fetch(deps::owo, mrIds.owo) { "io.wispforest:owo-lib:${it.version_number}" }
            fetch(deps::patchouli, mrIds.patchouli) { "vazkii.patchouli:Patchouli:${it.version_number.toUpperCase(Locale.ROOT)}" }
//            fetch(deps::powah, mrIds.powah) { it.maven }
        }

        out()

        fetcher(CurseForgeVersionFetcher) {
//            fetch(deps::indrev, cfIds.indrev) { it.maven }
//            fetch(deps::luggage, cfIds.luggage) { it.maven }
            fetch(deps::pal, cfIds.pal) { "io.github.ladysnake:PlayerAbilityLib:${it.download.fileName.removePrefix("pal-")}" }
            fetch(deps::rebornCore, cfIds.rebornCore) { "RebornCore:RebornCore-1.19:${it.download.fileName.removePrefix("RebornCore-")}" }
            fetch(deps::techReborn, cfIds.techReborn) { "TechReborn:TechReborn-1.19:${it.download.fileName.removePrefix("TechReborn-")}" }
//            fetch(deps::wirelessNet, cfIds.wirelessNet, "1.19") { it.maven }

            out("\nobject lba {")
            deps.lba::class.memberProperties.forEach { module ->
                @Suppress("UNCHECKED_CAST")
                fetch(module as KProperty<String>, cfIds.lba) {
                    "alexiil.mc.lib:libblockattributes-${module.name}:${it.download.fileName.removePrefix("libblockattributes-all-")}"
                }
            }
            out("}")
        }

        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(output.toString()), null)
    }
}

