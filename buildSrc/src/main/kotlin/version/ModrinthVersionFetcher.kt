package version

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.net.http.HttpClient

object ModrinthVersionFetcher : VersionFetcher<ModrinthVersionFetcher.Version> {

    override fun getLatestVersionFor(http: HttpClient, project: String, minecraft: String, loader: String): Version? {
        val res = http.getString(
            "https://api.modrinth.com/v2/project/${project}/version",
            "loaders" to "[\"${loader}\"]",
            "game_versions" to "[\"${minecraft}\"]",
            "featured" to "true"
        )

        val json = ObjectMapper().readTree(res.body())
        if (!json.isArray) return null

        val array = json as ArrayNode
        return if (array.isEmpty) null else Version(array[0] as ObjectNode)
    }

    class Version(json: ObjectNode) : JsonFacade(json) {
        val version_number by string()
        val project_id by string()
        val id by string()

        val maven get() = "maven.modrinth:${project_id}:${id}"
    }

}
