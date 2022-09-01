repositories {
    wispforest()
}

dependencies {
    runtimeOnly(namedProject("vanilla"))

    modImpl(deps.alloyForge)
    modImpl(deps.fabricApi)
    modImpl(deps.owo)
}
