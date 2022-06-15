repositories {
    wispforest()
}

dependencies {
    runtimeOnly(namedProject("vanilla"))

    modImpl(deps.alloyForgery)
    modImpl(deps.fabricApi)
    modImpl(deps.owo)
}
