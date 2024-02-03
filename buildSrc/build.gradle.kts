plugins {
    java
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.16.0"))
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.16.0")

    implementation("com.squareup:javapoet:1.13.0")
    implementation("org.vineflower:vineflower:1.9.3")
}
