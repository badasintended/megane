import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun GenMetadata.fmj(fn: GenFmjTask.() -> Unit) {
    val genFmjTask by project.tasks.creating(GenFmjTask::class) {
        fn(this)
    }

    depend(genFmjTask)
}

@Suppress("LeakingThis")
abstract class GenFmjTask : DefaultTask() {

    @get:Input
    abstract val mixin: Property<Boolean>

    @get:Input
    abstract val contributors: ListProperty<String>

    @get:Input
    abstract val depends: MapProperty<String, String>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        mixin.convention(false)
        contributors.convention(listOf())
        depends.convention(mapOf())

        output.convention(project.file("src/generated/resources/fabric.mod.json"))
    }

    fun mixin(v: Boolean) = mixin.set(v)
    fun contributors(vararg v: String) = contributors.addAll(*v)
    fun depends(vararg v: Pair<String, String>) = v.forEach { depends.put(it.first, it.second) }

    @TaskAction
    fun generate() {
        val json = JsonObject().apply {
            addProperty("schemaVersion", 1)
            addProperty("id", "megane-${project.name}")
            addProperty("version", "${project.version}")

            add("authors", JsonArray().apply {
                add("deirn")
            })

            if (contributors.get().isNotEmpty()) add("contributors", JsonArray().apply {
                contributors.get().forEach(::add)
            })

            addProperty("license", "MIT")
            addProperty("icon", "megane.png")

            if (mixin.get()) add("mixins", JsonArray().apply {
                add("megane-${project.name}.mixins.json")
            })

            add("depends", JsonObject().apply {
                addProperty("wthit", "*")
                depends.get().forEach(::addProperty)
            })

            add("custom", JsonObject().apply {
                add("modmenu", JsonObject().apply {
                    addProperty("parent", "megane")
                })
            })
        }

        val out = GsonBuilder().setPrettyPrinting().create().toJson(json)
        output.get().writeText(out)
    }

}
