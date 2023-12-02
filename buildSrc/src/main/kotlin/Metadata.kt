import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getValue

val Project.metadata get() = if (extra.has(Metadata.KEY)) extra.get(Metadata.KEY) as? Metadata else null

fun Project.metadata(pkg: String, fn: Metadata.() -> Unit) {
    val genMetadata by project.tasks.creating {
        group = "megane"
    }

    project.tasks.getByName("compileJava").dependsOn(genMetadata)
    project.tasks.getByName("processResources").dependsOn(genMetadata)
    project.tasks.getByName("sourcesJar").dependsOn(genMetadata)

    val metadata = Metadata(project, pkg, genMetadata)
    fn(metadata)

    extra.set(Metadata.KEY, metadata)
}

class Metadata(
    val project: Project,
    val pkg: String,
    val task: Task
) {

    companion object {
        const val KEY = "metadata"
    }

    val id = "megane-${project.name}"
    val prop = hashMapOf<String, Any>()

}
