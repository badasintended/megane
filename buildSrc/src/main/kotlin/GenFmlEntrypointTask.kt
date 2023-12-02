import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File
import javax.lang.model.element.Modifier

fun Metadata.forgeFml() {
    val genFmlEntrypoint by project.tasks.creating(GenFmlEntrypointTask::class) {
        id.set(this@forgeFml.id)
        annotation.set("net.minecraftforge.fml.common.Mod")
        clazz.set("${pkg}.Main")
        output.set(project.file("src/generated/java/"))
    }

    task.dependsOn(genFmlEntrypoint)
}

abstract class GenFmlEntrypointTask : DefaultTask() {
    @get:Input
    abstract val id: Property<String>

    @get:Input
    abstract val annotation: Property<String>

    @get:Input
    abstract val clazz: Property<String>

    @get:OutputDirectory
    abstract val output: Property<File>

    init {
        group = "megane"
    }

    @TaskAction
    fun generate() {
        val pkgName = clazz.get().substringBeforeLast('.')
        val className = clazz.get().substringAfterLast('.')

        val type = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .addAnnotation(
                AnnotationSpec
                    .builder(ClassName.bestGuess(annotation.get()))
                    .addMember("value", "\$S", id.get().replace("-", "_"))
                    .build()
            )
            .build()

        JavaFile.builder(pkgName, type)
            .build()
            .writeTo(output.get())
    }
}
