import com.fasterxml.jackson.databind.json.JsonMapper
import deps.fabric
import deps.forge
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import version.CurseForgeVersionFetcher
import version.ModrinthVersionFetcher
import version.VersionFetcher
import java.io.File
import java.net.http.HttpClient
import java.util.*
import kotlin.reflect.KProperty

@Suppress("LeakingThis", "DuplicatedCode")
abstract class FetchLatestVersionsTask : DefaultTask() {
    @get:InputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        output.convention(project.file("dependencies.json"))
    }

    @TaskAction
    fun run() {
        val mapper = JsonMapper()
        val output = mapper.createObjectNode()
        val http = HttpClient.newHttpClient()

        class VersionFetcherDsl<T>(
            val versionFetcher: VersionFetcher<T>,
            val loader: String,
            val path: DependencyPath
        ) {
            fun fetch(property: KProperty<*>, project: String, mc: String = versions.minecraft, parser: (T) -> String) {
                val key = "${path.prefix}.${property.name}"
                val res = versionFetcher.getLatestVersionFor(http, project, mc, loader)

                if (res != null) {
                    output.put(key, parser(res))
                } else {
                    throw GradleException("Failed to get version for $key")
                }
            }
        }

        fun <T> fetcher(versionFetcher: VersionFetcher<T>, loader: String, path: DependencyPath, action: VersionFetcherDsl<T>.() -> Unit) {
            action(VersionFetcherDsl(versionFetcher, loader, path))
        }

        fetcher(ModrinthVersionFetcher, "fabric", fabric.wthit) {
            fetch(fabric.wthit::api, mrIds.wthit) { "mcp.mobius.waila:wthit-api:${it.version_number}" }
            fetch(fabric.wthit::runtime, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
        }

        fetcher(ModrinthVersionFetcher, "fabric", fabric) {
            fetch(fabric::badpackets, mrIds.badpackets) { "lol.bai:badpackets:${it.version_number}" }

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
            fetch(fabric::patchouli, mrIds.patchouli) { "vazkii.patchouli:Patchouli:${it.version_number.uppercase(Locale.ROOT)}" }
            fetch(fabric::powah, mrIds.powah) { it.maven }
        }

        fetcher(CurseForgeVersionFetcher, "fabric", fabric) {
            fetch(fabric::dml, cfIds.dml) { it.maven }
            fetch(fabric::indrev, cfIds.indrev) { it.maven }
            fetch(fabric::luggage, cfIds.luggage) { it.maven }
            fetch(fabric::pal, cfIds.pal) { "io.github.ladysnake:PlayerAbilityLib:${it.download.fileName.removePrefix("pal-")}" }
            fetch(fabric::rebornCore, cfIds.rebornCore) { "RebornCore:RebornCore-1.19:${it.download.fileName.removePrefix("RebornCore-")}" }
            fetch(fabric::techReborn, cfIds.techReborn) { "TechReborn:TechReborn-1.19:${it.download.fileName.removePrefix("TechReborn-")}" }
            fetch(fabric::wirelessNet, cfIds.wirelessNet, "1.19") { it.maven }
            fetch(fabric::lapisReserve, cfIds.lapisReserve) { it.maven }
            fetch(fabric::resourceChickens, cfIds.resourceChickens) { it.api }
        }

        fetcher(ModrinthVersionFetcher, "forge", forge.wthit) {
            fetch(forge.wthit::api, mrIds.wthit) { "mcp.mobius.waila:wthit-api:${it.version_number}" }
            fetch(forge.wthit::runtime, mrIds.wthit) { "mcp.mobius.waila:wthit:${it.version_number}" }
        }

        fetcher(ModrinthVersionFetcher, "forge", forge) {
            fetch(forge::badpackets, mrIds.badpackets) { "lol.bai:badpackets:${it.version_number}" }

            fetch(forge::ae2, mrIds.ae2) { "appeng:appliedenergistics2-forge:${it.version_number.removePrefix("forge-")}" }
            fetch(forge::create, mrIds.createForge) { it.maven }
            fetch(forge::ie, mrIds.ie) { it.maven }
            fetch(forge::rs, mrIds.rs) { it.maven }
            fetch(forge::jei, mrIds.jei) { it.maven }
        }

        fetcher(ModrinthVersionFetcher, "forge", forge.mekanism) {
            fetch(forge.mekanism::core, mrIds.mekCore) { "mekanism:Mekanism:${versions.minecraft}-${it.version_number}" }
            fetch(forge.mekanism::generators, mrIds.mekCore) { "mekanism:Mekanism:${versions.minecraft}-${it.version_number}:generators" }
        }

        fetcher(ModrinthVersionFetcher, "forge", forge.thermal) {
            fetch(forge.thermal::cofhCore, mrIds.cofhCore) { it.maven }
            fetch(forge.thermal::foundation, mrIds.thermalFoundation) { it.maven }
            fetch(forge.thermal::expansion, mrIds.thermalExpansion) { it.maven }
        }

        fetcher(CurseForgeVersionFetcher, "forge", forge) {
            fetch(forge::lapisReserve, cfIds.lapisReserve) { it.maven }
            fetch(forge::resourceChickens, cfIds.resourceChickens) { it.api }
            fetch(forge::productiveBees, cfIds.productiveBees) { it.maven }
            fetch(forge::top, cfIds.top) { it.maven }
        }

        mapper
            .writer()
            .withDefaultPrettyPrinter()
            .writeValue(this.output.get(), output)
    }
}

