repositories {
    shedaniel()
}

dependencies {
    runtimeOnly(namedProject("reborn-core"))

    modImpl(deps.techReborn)
    modImpl(deps.fabricApi)
}
