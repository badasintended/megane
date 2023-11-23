import deps.fabric
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
            val versionFetcher: VersionFetcher<T>
        ) {
            fun fetch(name: KProperty<*>, project: String, mc: String = versions.minecraft, parser: (T) -> String) {
                val res = versionFetcher.getLatestVersionFor(http, project, mc)
                if (res != null) {
                    // language=kotlin
                    out("const val ${name.name} = \"${parser(res)}\"")
                } else {
                    throw GradleException("Failed to get version for ${name}")
                }
            }
        }

        fun <T> fetcher(versionFetcher: VersionFetcher<T>, action: VersionFetcherDsl<T>.() -> Unit) {
            action(VersionFetcherDsl(versionFetcher))
        }

        out("\nobject fabric {")
        fetcher(ModrinthVersionFetcher) {
            fetch(fabric::wthit, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
            out()

            fetch(fabric::ae2, mrIds.ae2) { "appeng:appliedenergistics2-fabric:${it.version_number.removePrefix("fabric-")}" }
            fetch(fabric::alloyForge, mrIds.alloyForge) { it.maven }
            fetch(fabric::architectury, mrIds.architectury) { "dev.architectury:architectury-fabric:${it.version_number.removeSuffix("+fabric")}" }
            fetch(fabric::create, mrIds.create) { "com.simibubi.create:create-fabric-1.19.2:${it.version_number}" }
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

        fetcher(CurseForgeVersionFetcher) {
            fetch(fabric::dml, cfIds.dml) { it.maven }
            fetch(fabric::indrev, cfIds.indrev) { it.maven }
            fetch(fabric::luggage, cfIds.luggage) { it.maven }
            fetch(fabric::pal, cfIds.pal) { "io.github.ladysnake:PlayerAbilityLib:${it.download.fileName.removePrefix("pal-")}" }
            fetch(fabric::rebornCore, cfIds.rebornCore) { "RebornCore:RebornCore-1.19:${it.download.fileName.removePrefix("RebornCore-")}" }
            fetch(fabric::techReborn, cfIds.techReborn) { "TechReborn:TechReborn-1.19:${it.download.fileName.removePrefix("TechReborn-")}" }
            fetch(fabric::wirelessNet, cfIds.wirelessNet, "1.19") { it.maven }
        }
        out("}")

        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(output.toString()), null)
    }
}

