import org.gradle.api.Project
import org.gradle.api.Task

fun Project.metadata(fn: GenMetadata.() -> Unit) {
    fn(GenMetadata(project))
}

class GenMetadata(
    internal val project: Project
) {
    fun depend(task: Task) {
        project.tasks.getByName("compileJava").dependsOn(task)
        project.tasks.getByName("processResources").dependsOn(task)
        project.tasks.getByName("sourcesJar").dependsOn(task)
    }
}
