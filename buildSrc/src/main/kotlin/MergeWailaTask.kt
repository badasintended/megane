import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class MergeWailaTask : DefaultTask() {

    @get:InputFiles
    abstract val input: ListProperty<File>

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun execute() {
        val mapper = JsonMapper()
        val outputJson = mapper.createObjectNode()

        input.get().forEach { file ->
            mapper.readTree(file).fields().forEach { (k, v) ->
                outputJson.set<JsonNode>(k, v)
            }
        }

        mapper.writeValue(output.get().asFile, outputJson)
    }

}
