import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.language() {
    val genLanguage by project.tasks.creating(GenLanguageTask::class) {
        input.set(project.rootProject.file("src/translation/resources/lang"))
        output.set(project.file("src/generated/resources/assets/${id}/lang"))
    }

    task.dependsOn(genLanguage)
}

abstract class GenLanguageTask : DefaultTask() {

    @get:InputDirectory
    abstract val input: Property<File>

    @get:OutputDirectory
    abstract val output: Property<File>

    init {
        group = "megane"
    }

    @TaskAction
    fun generate() {
        val languages = input.get().listFiles { it -> it.extension == "json" } ?: arrayOf()

        val mapper = ObjectMapper()

        for (language in languages) {
            val out = mapper.createObjectNode()

            fun writeFields(entry: JsonNode) {
                entry.fields().forEach { (k, v) ->
                    out.put(k, v.asText())
                }
            }

            val json = mapper.readTree(language)
            writeFields(json["base"])
            json[project.parent!!.name][project.name]?.let(::writeFields)

            mapper.writeValue(output.get().resolve(language.relativeTo(input.get())), out)
        }
    }

}
