import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.waila(initializer: String) {
    val genWaila by project.tasks.creating(GenWailaTask::class) {
        this.initializer.set("${pkg}.${initializer}")
    }

    task.dependsOn(genWaila)
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
        val mapper = JsonMapper()
        val node = mapper.createObjectNode().apply {
            putObject("megane:${project.name}").apply {
                put("initializer", initializer.get())
            }
        }

        mapper.writeValue(output.get(), node)
    }

}
