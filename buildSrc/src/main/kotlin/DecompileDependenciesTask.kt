import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger
import java.io.File

private class Decompiler(output: File) : ConsoleDecompiler(output, mapOf(), PrintStreamLogger(System.out))

@Suppress("LeakingThis")
abstract class DecompileDependenciesTask : DefaultTask() {

    @get:Input
    abstract val configuration: Property<String>

    @get:OutputDirectory
    abstract val output: RegularFileProperty

    init {
        group = "megane"

        configuration.convention("decompile")
        output.convention(project.layout.buildDirectory.file(name))
    }

    @TaskAction
    fun decompile() {
        val decompiler = Decompiler(output.get().asFile)

        project.configurations[configuration.get()].resolvedConfiguration.resolvedArtifacts.forEach {
            decompiler.addSource(it.file)
        }

        decompiler.decompileContext()
    }

}
