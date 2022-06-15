import org.gradle.kotlin.dsl.maven
import org.gradle.api.artifacts.dsl.RepositoryHandler as Repo

fun Repo.badasintended() = maven("https://maven.bai.lol")
fun Repo.cursemaven() = maven("https://cursemaven.com")
fun Repo.shedaniel() = maven("https://maven.shedaniel.me/")
fun Repo.modmaven() = maven("https://modmaven.dev")
fun Repo.jamieswhiteshirt() = maven("https://maven.jamieswhiteshirt.com/libs-release")
fun Repo.blamejared() = maven("https://maven.blamejared.com")
fun Repo.buildcraft() = maven("https://mod-buildcraft.com/maven")
fun Repo.cafeteria() = maven("https://maven.cafeteria.dev")
fun Repo.jitpack() = maven("https://jitpack.io")
fun Repo.ladysnake() = maven("https://ladysnake.jfrog.io/artifactory/mods")