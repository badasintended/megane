repositories {
    ladysnake()
}

dependencies {
    runtimeOnly(namedProject("vanilla"))

    modImpl(deps.kibe)
    modImpl(deps.fabricApi)
    modImpl(deps.flk)
    modImpl(deps.pal)
}
