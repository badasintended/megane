import org.gradle.api.artifacts.ProjectDependency
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.namedProject(name: String): ProjectDependency {
    return project(path = ":${name}", configuration = "namedElements")
}
