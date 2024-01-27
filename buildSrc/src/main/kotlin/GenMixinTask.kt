import com.fasterxml.jackson.databind.json.JsonMapper
import com.squareup.javapoet.*
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import java.io.File
import javax.lang.model.element.Modifier

fun Metadata.mixin(fn: GenMixinConfig.() -> Unit = { }) {
    val sourceSets = project.extensions.getByType<SourceSetContainer>()
    var requireExp: String? = null

    fn(object : GenMixinConfig {
        override fun callback(refmapFn: (SourceSet, String) -> Unit, configFn: (String) -> Unit) {
            refmapFn(sourceSets["main"], "megane-${project.name}.refmap.json")
            configFn("megane-${project.name}.mixins.json")
        }

        override fun require(vararg expressions: String) {
            requireExp = expressions.joinToString(separator = " && ")
        }
    })

    val mixinPkg = "${pkg}.mixin"
    val mixinJson = "megane-${project.name}.mixins.json"
    val genMixin by project.tasks.creating(GenMixinTask::class) {
        id.set(this@mixin.id)
        pkg.set(mixinPkg)
        pkgDir.set(project.file("src/main/java/" + mixinPkg.replace(".", "/")))
        mixinJsonOutput.set(project.file("src/generated/resources/${mixinJson}"))

        if (requireExp != null) {
            mixinPluginOutput.set(project.file("src/generated/java/"))
            require.set(requireExp)
        }
    }

    prop[GenMixinTask.JSON] = mixinJson
    task.dependsOn(genMixin)
}

interface GenMixinConfig {
    fun callback(
        refmapFn: (SourceSet, String) -> Unit = { _, _ -> },
        configFn: (String) -> Unit = { _ -> }
    )

    fun require(vararg expressions: String)

    fun forgeMod(id: String) = "net.minecraftforge.fml.loading.FMLLoader.getLoadingModList().getModFileById(\"${id}\") != null"
    fun fabricMod(id: String) = "net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(\"${id}\")"
}

abstract class GenMixinTask : DefaultTask() {

    companion object {
        const val JSON = "mixin.json"
    }

    @get:Input
    abstract val id: Property<String>

    @get:Input
    abstract val pkg: Property<String>

    @get:InputDirectory
    abstract val pkgDir: Property<File>

    @get:OutputFile
    abstract val mixinJsonOutput: Property<File>

    @get:OutputDirectory
    @get:Optional
    abstract val mixinPluginOutput: Property<File>

    @get:Input
    @get:Optional
    abstract val require: Property<String>

    init {
        group = "megane"
    }

    @TaskAction
    fun generate() {
        val main = pkgDir.get().listFiles { it -> it.extension == "java" } ?: arrayOf()
        val client = pkgDir.get().resolve("client").listFiles { it -> it.extension == "java" } ?: arrayOf()

        val mapper = JsonMapper()
        val node = mapper.createObjectNode().apply {
            put("required", true)
            put("minVersion", "0.8")
            put("package", pkg.get())
            put("compatibilityLevel", "JAVA_8")

            putObject("injectors").apply {
                put("defaultRequire", 1)
            }

            putArray("mixins").apply {
                main.forEach { add(it.nameWithoutExtension) }
            }

            putArray("client").apply {
                client.forEach { add("client.${it.nameWithoutExtension}") }
            }

            put("refmap", "${id.get()}.refmap.json")

            if (require.isPresent) {
                put("plugin", "${pkg.get()}.MixinConfigPlugin")
            }
        }

        mapper.writeValue(mixinJsonOutput.get(), node)

        if (require.isPresent) {
            val classNodeName = ClassName.bestGuess("org.objectweb.asm.tree.ClassNode")
            val iMixinInfoName = ClassName.bestGuess("org.spongepowered.asm.mixin.extensibility.IMixinInfo")

            val type = TypeSpec.classBuilder("MixinConfigPlugin")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(ClassName.bestGuess("org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin"))
                .addMethod(MethodSpec.methodBuilder("shouldApplyMixin")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.BOOLEAN)
                    .addParameter(String::class.java, "targetClassName")
                    .addParameter(String::class.java, "mixinClassName")
                    .addStatement("return \$L", require.get())
                    .build())
                .addMethod(MethodSpec.methodBuilder("onLoad")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(String::class.java, "mixinPackage")
                    .build())
                .addMethod(MethodSpec.methodBuilder("getRefMapperConfig")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String::class.java)
                    .addStatement("return null")
                    .build())
                .addMethod(MethodSpec.methodBuilder("acceptTargets")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(ParameterizedTypeName.get(Set::class.java, String::class.java), "myTargets")
                    .addParameter(ParameterizedTypeName.get(Set::class.java, String::class.java), "otherTargets")
                    .build())
                .addMethod(MethodSpec.methodBuilder("getMixins")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ParameterizedTypeName.get(List::class.java, String::class.java))
                    .addStatement("return null")
                    .build())
                .addMethod(MethodSpec.methodBuilder("preApply")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(String::class.java, "targetClassName")
                    .addParameter(classNodeName, "targetClass")
                    .addParameter(String::class.java, "mixinClassName")
                    .addParameter(iMixinInfoName, "mixinInfo")
                    .build())
                .addMethod(MethodSpec.methodBuilder("postApply")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(TypeName.VOID)
                    .addParameter(String::class.java, "targetClassName")
                    .addParameter(classNodeName, "targetClass")
                    .addParameter(String::class.java, "mixinClassName")
                    .addParameter(iMixinInfoName, "mixinInfo")
                    .build())
                .build()

            JavaFile.builder(pkg.get(), type)
                .build()
                .writeTo(mixinPluginOutput.get())
        }
    }

}
