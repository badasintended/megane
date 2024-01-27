import com.fasterxml.jackson.dataformat.toml.TomlMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.modsToml(fn: GenModsTomlTask.() -> Unit = {}) {
    val genModsToml by project.tasks.creating(GenModsTomlTask::class) {
        id.set(this@modsToml.id)

        fn(this)
    }

    task.dependsOn(genModsToml)
}

@Suppress("LeakingThis")
abstract class GenModsTomlTask : DefaultTask() {

    @get:Internal
    val any = "[0,)"

    @get:Input
    abstract val id: Property<String>

    @get:OutputFile
    abstract val output: Property<File>

    @get:Input
    abstract val contributors: ListProperty<String>

    @get:Input
    abstract val depends: MapProperty<String, String>

    init {
        group = "megane"

        output.convention(project.file("src/generated/resources/META-INF/mods.toml"))
        contributors.convention(listOf())
        depends.convention(mapOf())
    }

    fun contributors(vararg v: String) = contributors.addAll(*v)
    fun depends(vararg v: Pair<String, String>) = v.forEach { depends.put(it.first, it.second) }

    @TaskAction
    fun generate() {
        val id = this.id.get().replace("-", "_")

        val mapper = TomlMapper()
        val node = mapper.createObjectNode().apply {
            put("modLoader", "javafml")
            put("loaderVersion", "[1,)")
            put("license", "All Rights Reserved")

            putArray("mods").addObject().apply {
                put("modId", id)
                put("version", "${project.version}")
                put("authors", "deirn")
                put("logoFile", "megane.jpg")

                if (contributors.get().isNotEmpty()) {
                    put("credits", contributors.get().joinToString(separator = ", "))
                }
            }

            putObject("dependencies").putArray(id).apply {
                addObject().apply {
                    put("modId", "wthit")
                    put("mandatory", true)
                    put("versionRange", "[0,)")
                }

                depends.get().forEach { (k, v) ->
                    addObject().apply {
                        put("modId", k)
                        put("mandatory", true)
                        put("versionRange", v)
                    }
                }
            }
        }

        mapper.writeValue(output.get(), node)
    }

}

