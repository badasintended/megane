plugins {
    java
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.fabricmc.net")
    maven("https://jitpack.io")
}

dependencies {
    implementation("fabric-loom:fabric-loom.gradle.plugin:0.12.+")
    implementation("com.modrinth.minotaur:Minotaur:2.2.1")
    implementation("com.github.deirn:CurseForgeGradle:c693018f92")
}
