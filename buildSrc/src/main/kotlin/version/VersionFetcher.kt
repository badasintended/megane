package version

import com.google.gson.JsonObject
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.reflect.KProperty

fun HttpClient.getString(url: String, vararg queries: Pair<String, String>): HttpResponse<String> {
    return send(HttpRequest.newBuilder().uri(url, *queries).build(), HttpResponse.BodyHandlers.ofString(Charsets.UTF_8))
}

fun HttpRequest.Builder.uri(url: String, vararg queries: Pair<String, String>): HttpRequest.Builder {
    val sb = StringBuilder(url)
    if (queries.isNotEmpty()) {
        sb.append("?")
        queries.forEachIndexed { index, (key, value) ->
            sb.append(key, "=", URLEncoder.encode(value, Charsets.UTF_8))
            if (index < queries.size - 1) {
                sb.append("&")
            }
        }
    }
    return uri(URI.create(sb.toString()))
}

interface VersionFetcher<T> {

    fun getLatestVersionFor(http: HttpClient, project: String, minecraft: String): T?

}

abstract class JsonFacade(
    private val json: JsonObject
) {

    class int {
        operator fun getValue(facade: JsonFacade, property: KProperty<*>): Int {
            return facade.json[property.name].asInt
        }
    }

    class string {
        operator fun getValue(facade: JsonFacade, property: KProperty<*>): String {
            return facade.json[property.name].asString
        }
    }

    class nested<T : JsonFacade>(
        val factory: (JsonObject) -> T
    ) {
        operator fun getValue(facade: JsonFacade, property: KProperty<*>): T {
            return factory(facade.json[property.name].asJsonObject)
        }
    }

}
