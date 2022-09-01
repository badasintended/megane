package version

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.net.http.HttpClient

object CurseForgeVersionFetcher : VersionFetcher<CurseForgeVersionFetcher.Mod> {

    override fun getLatestVersionFor(http: HttpClient, project: String, minecraft: String): Mod? {
        val res = http.getString(
            "https://api.cfwidget.com/${project}",
            "version" to minecraft,
            "loader" to "fabric"
        )

        val json = JsonParser.parseString(res.body())
        if (!json.isJsonObject) return null

        val obj = json.asJsonObject
        return if (!obj.has("download")) null else Mod(obj)
    }

    class Mod(json: JsonObject) : JsonFacade(json) {
        val id by int()
        val title by string()
        val download by nested(::Downlaod)

        val maven get() = "curse.maven:cursemod-${id}:${download.id}"
    }

    class Downlaod(json: JsonObject) : JsonFacade(json) {
        val id by int()
        val name by string()

        val fileName get() = name.removeSuffix(".jar")
    }

}
