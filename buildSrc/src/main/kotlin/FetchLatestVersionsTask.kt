import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import version.CurseForgeVersionFetcher
import version.ModrinthVersionFetcher
import version.VersionFetcher
import java.net.http.HttpClient
import java.util.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.memberProperties

abstract class FetchLatestVersionsTask : DefaultTask() {
    @TaskAction
    fun run() {
        val http = HttpClient.newHttpClient()

        class VersionFetcherDsl<T>(
            val versionFetcher: VersionFetcher<T>
        ) {
            fun fetch(name: KProperty<*>, project: String, parser: (T) -> String) {
                val res = versionFetcher.getLatestVersionFor(http, project, versions.minecraft)
                if (res != null) {
                    println("val ${name.name} = \"${parser(res)}\"")
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
            println()

            fetch(deps::ae2, mrIds.ae2) { "appeng:appliedenergistics2-fabric:${it.version_number.removePrefix("fabric-")}" }
            fetch(deps::alloyForge, mrIds.alloyForge) { it.maven }
            fetch(deps::create, mrIds.create) { "com.simibubi:Create:${it.version_number.removePrefix("create-1.18.2-")}" }
            fetch(deps::clothConfig, mrIds.clothConfig) { "me.shedaniel.cloth:cloth-config-fabric:${it.version_number}" }
            fetch(deps::extraGen, mrIds.extraGen) { it.maven }
            fetch(deps::fabricApi, mrIds.fabricApi) { "net.fabricmc.fabric-api:fabric-api:${it.version_number}" }
            fetch(deps::flk, mrIds.flk) { "net.fabricmc:fabric-language-kotlin:${it.version_number}" }
            fetch(deps::kibe, mrIds.kibe) { it.maven }
            fetch(deps::modernDynamics, mrIds.modernDynamics) { it.maven }
            fetch(deps::modmenu, mrIds.modmenu) { "com.terraformersmc:modmenu:${it.version_number}" }
            fetch(deps::noIndium, mrIds.noIndium) { "me.luligabi:NoIndium:${it.version_number}" }
            fetch(deps::owo, mrIds.owo) { "io.wispforest:owo-lib:${it.version_number}" }
            fetch(deps::patchouli, mrIds.patchouli) { "vazkii.patchouli:Patchouli:${it.version_number.toUpperCase(Locale.ROOT)}" }
        }

        println()

        fetcher(CurseForgeVersionFetcher) {
            fetch(deps::dmlSim, cfIds.dmlSim) { it.maven }
            fetch(deps::indrev, cfIds.indrev) { it.maven }
            fetch(deps::luggage, cfIds.luggage) { it.maven }
            fetch(deps::pal, cfIds.pal) { "io.github.ladysnake:PlayerAbilityLib:${it.download.fileName.removePrefix("pal-")}" }
            fetch(deps::rebornCore, cfIds.rebornCore) { "RebornCore:RebornCore-1.18:${it.download.fileName.removePrefix("RebornCore-")}" }
            fetch(deps::techReborn, cfIds.techReborn) { "TechReborn:TechReborn-1.18:${it.download.fileName.removePrefix("TechReborn-")}" }
            fetch(deps::wirelessNet, cfIds.wirelessNet) { it.maven }

            println("\nobject lba {")
            deps.lba::class.memberProperties.forEach { module ->
                @Suppress("UNCHECKED_CAST")
                fetch(module as KProperty<String>, cfIds.lba) {
                    "alexiil.mc.lib:libblockattributes-${module.name}:${it.download.fileName.removePrefix("libblockattributes-all-")}"
                }
            }
            println("}")
        }
    }
}
