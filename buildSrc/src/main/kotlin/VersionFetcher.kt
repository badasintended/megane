import org.gradle.api.Project

fun Project.fetcher(fn: VersionFetcher.() -> Unit) {

}

class VersionFetcher(
    private val project: Project
) {

    fun modrinth() {

    }

    fun curseforge() {

    }

}
