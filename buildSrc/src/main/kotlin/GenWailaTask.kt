import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun GenMetadata.waila(initializer: String) {
    val genWailaTask by project.tasks.creating(GenWailaTask::class) {
        this.initializer.set(initializer)
    }

    depend(genWailaTask)
}

@Suppress("LeakingThis")
abstract class GenWailaTask : DefaultTask() {

    @get:Input
    abstract val initializer: Property<String>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        output.convention(project.file("src/generated/resources/waila_plugins.json"))
    }

    @TaskAction
    fun generate() {
        val json = JsonObject().apply {
            add("megane:${project.name}", JsonObject().apply {
                addProperty("initializer", initializer.get())
            })
        }

        val out = GsonBuilder().setPrettyPrinting().create().toJson(json)
        output.get().writeText(out)
    }

}
