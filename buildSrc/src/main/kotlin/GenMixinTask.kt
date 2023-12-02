import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.mixin(
    refmapFn: (SourceSet, String) -> Unit = { _, _ -> },
    configFn: (String) -> Unit = { _ -> }
) {
    val sourceSets = project.extensions.getByType<SourceSetContainer>()
    refmapFn(sourceSets["main"], "megane-${project.name}.refmap.json")
    configFn("megane-${project.name}.mixins.json")

    val mixinPkg = "${pkg}.mixin"
    val mixinJson = "megane-${project.name}.mixins.json"
    val genMixin by project.tasks.creating(GenMixinTask::class) {
        id.set(this@mixin.id)
        pkg.set(mixinPkg)
        pkgDir.set(project.file("src/main/java/" + mixinPkg.replace(".", "/")))
        output.set(project.file("src/generated/resources/${mixinJson}"))
    }

    prop[GenMixinTask.JSON] = mixinJson
    task.dependsOn(genMixin)
}

abstract class GenMixinTask : DefaultTask() {

    companion object {
        const val JSON = "mixin.json"
    }

    @get:Input
    abstract val id: Property<String>

    @get:Input
    abstract val pkg: Property<String>

    @get:InputDirectory
    abstract val pkgDir: Property<File>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"
    }

    @TaskAction
    fun generate() {
        val main = pkgDir.get().listFiles { it -> it.extension == "java" } ?: arrayOf()
        val client = pkgDir.get().resolve("client").listFiles { it -> it.extension == "java" } ?: arrayOf()

        val mapper = JsonMapper()
        val node = mapper.createObjectNode().apply {
            put("required", true)
            put("minVersion", "0.8")
            put("package", pkg.get())
            put("compatibilityLevel", "JAVA_8")

            putObject("injectors").apply {
                put("defaultRequire", 1)
            }

            putArray("mixins").apply {
                main.forEach { add(it.nameWithoutExtension) }
            }

            putArray("client").apply {
                client.forEach { add("client.${it.nameWithoutExtension}") }
            }

            put("refmap", "${id.get()}.refmap.json")
        }

        mapper.writeValue(output.get(), node)
    }

}
