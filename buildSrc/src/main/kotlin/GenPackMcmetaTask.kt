import com.fasterxml.jackson.databind.ObjectMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.packMcmeta() {
    val genPackMcmeta by project.tasks.creating(GenPackMcmetaTask::class) {
        id.set(this@packMcmeta.id)
    }

    task.dependsOn(genPackMcmeta)
}

@Suppress("LeakingThis")
abstract class GenPackMcmetaTask : DefaultTask() {

    @get:Input
    abstract val id: Property<String>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        output.convention(project.file("src/generated/resources/pack.mcmeta"))
    }

    @TaskAction
    fun generate() {
        val mapper = ObjectMapper()
        val node = mapper.createObjectNode().apply {
            putObject("pack").apply {
                put("description", id.get())
                put("pack_format", 8)
            }
        }

        mapper.writeValue(output.get(), node)
    }

}
