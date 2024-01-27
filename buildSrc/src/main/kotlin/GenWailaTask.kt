import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.waila(initializer: String, fn: GenWailaTask.() -> Unit = {}) {
    val genWaila by project.tasks.creating(GenWailaTask::class) {
        this.initializer.set("${pkg}.${initializer}")
        fn(this)
    }

    task.dependsOn(genWaila)
}

@Suppress("LeakingThis")
abstract class GenWailaTask : DefaultTask() {

    @get:Input
    abstract val initializer: Property<String>

    @get:Input
    abstract val required: ListProperty<String>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        output.convention(project.file("src/generated/resources/waila_plugins.json"))
        required.convention(listOf())
    }

    fun required(vararg v: String) {
        required.addAll(*v)
    }

    @TaskAction
    fun generate() {
        val mapper = JsonMapper()
        val node = mapper.createObjectNode().apply {
            putObject("megane:${project.name}").apply {
                put("initializer", initializer.get())

                if (required.get().isNotEmpty()) {
                    putArray("required").apply {
                        required.get().forEach { add(it) }
                    }
                }
            }
        }

        mapper.writeValue(output.get(), node)
    }

}
