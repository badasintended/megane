package version

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.net.http.HttpClient

object ModrinthVersionFetcher : VersionFetcher<ModrinthVersionFetcher.Version> {

    override fun getLatestVersionFor(http: HttpClient, project: String, minecraft: String): Version? {
        val res = http.getString(
            "https://api.modrinth.com/v2/project/${project}/version",
            "loaders" to "[\"fabric\"]",
            "game_versions" to "[\"${minecraft}\"]",
            "featured" to "true"
        )

        val json = JsonParser.parseString(res.body())
        if (!json.isJsonArray) return null

        val array = JsonParser.parseString(res.body()).asJsonArray
        return if (array.isEmpty) null else Version(array[0].asJsonObject)
    }

    class Version(json: JsonObject) : JsonFacade(json) {
        val version_number by string()
        val project_id by string()

        val maven get() = "maven.modrinth:${project_id}:${version_number}"
    }

}
