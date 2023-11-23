import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun GenMetadata.mixin(pkg: String) {
    val genMixinTask by project.tasks.creating(GenMixinTask::class) {
        this.pkg.set(pkg)
        this.pkgDir.set(project.file("src/main/java/" + pkg.replace(".", "/")))
    }

    depend(genMixinTask)
}

@Suppress("LeakingThis")
abstract class GenMixinTask : DefaultTask() {

    @get:Input
    abstract val pkg: Property<String>

    @get:InputDirectory
    abstract val pkgDir: Property<File>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        output.convention(project.file("src/generated/resources/megane-${project.name}.mixins.json"))
    }

    @TaskAction
    fun generate() {
        val main = pkgDir.get().listFiles { it -> it.extension == "java" } ?: arrayOf()
        val client = pkgDir.get().resolve("client").listFiles { it -> it.extension == "java" } ?: arrayOf()

        val json = JsonObject().apply {
            addProperty("required", true)
            addProperty("minVersion", "0.8")
            addProperty("package", pkg.get())
            addProperty("compatibilityLevel", "JAVA_8")

            add("injectors", JsonObject().apply {
                addProperty("defaultRequire", 1)
            })

            add("mixins", JsonArray().apply {
                main.forEach { add(it.nameWithoutExtension) }
            })

            add("client", JsonArray().apply {
                client.forEach { add("client.${it.nameWithoutExtension}") }
            })

            addProperty("refmap", "megane-${project.name}.refmap.json")
        }

        val out = GsonBuilder().setPrettyPrinting().create().toJson(json)
        output.get().writeText(out)
    }

}
