import com.fasterxml.jackson.databind.json.JsonMapper
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import java.io.File

fun Metadata.fmj(fn: GenFmjTask.() -> Unit) {
    val genFmj by project.tasks.creating(GenFmjTask::class) {
        id.set(this@fmj.id)
        mixin.set(prop[GenMixinTask.JSON] as? String)

        fn(this)
    }

    task.dependsOn(genFmj)
}

@Suppress("LeakingThis")
abstract class GenFmjTask : DefaultTask() {

    @get:Internal
    val any = "*"

    @get:Input
    abstract val id: Property<String>

    @get:Input
    @get:Optional
    abstract val mixin: Property<String>

    @get:Input
    abstract val contributors: ListProperty<String>

    @get:Input
    abstract val depends: MapProperty<String, String>

    @get:OutputFile
    abstract val output: Property<File>

    init {
        group = "megane"

        contributors.convention(listOf())
        depends.convention(mapOf())

        output.convention(project.file("src/generated/resources/fabric.mod.json"))
    }

    fun contributors(vararg v: String) = contributors.addAll(*v)
    fun depends(vararg v: Pair<String, String>) = v.forEach { depends.put(it.first, it.second) }

    @TaskAction
    fun generate() {
        val mapper = JsonMapper()
        val node = mapper.createObjectNode().apply {
            put("schemaVersion", 1)
            put("id", id.get())
            put("version", "${project.version}")

            putArray("authors").apply {
                add("deirn")
            }

            if (contributors.get().isNotEmpty()) putArray("contributors").apply {
                contributors.get().forEach(::add)
            }

            put("license", "All Rights Reserved")
            put("icon", "megane.png")

            if (mixin.isPresent) putArray("mixins").apply {
                add(mixin.get())
            }

            putObject("depends").apply {
                put("wthit", "*")
                depends.get().forEach(::put)
            }

            putObject("custom").apply {
                putObject("modmenu").apply {
                    put("parent", "megane")
                }
            }
        }

        mapper.writeValue(output.get(), node)
    }

}
