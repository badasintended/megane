package version

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.net.http.HttpClient

object CurseForgeVersionFetcher : VersionFetcher<CurseForgeVersionFetcher.Mod> {

    override fun getLatestVersionFor(http: HttpClient, project: String, minecraft: String, loader: String): Mod? {
        val res = http.getString(
            "https://api.cfwidget.com/minecraft/mc-mods/${project}",
            "version" to minecraft,
            "loader" to loader
        )

        val json = ObjectMapper().readTree(res.body())
        if (!json.isObject) return null

        val obj = json as ObjectNode
        return if (!obj.has("download")) null else Mod(obj)
    }

    class Mod(json: ObjectNode) : JsonFacade(json) {
        val id by int()
        val title by string()
        val download by nested(::Downlaod)

        val maven get() = "curse.maven:cursemod-${id}:${download.id}"
    }

    class Downlaod(json: ObjectNode) : JsonFacade(json) {
        val id by int()
        val name by string()

        val fileName get() = name.removeSuffix(".jar")
    }

}
