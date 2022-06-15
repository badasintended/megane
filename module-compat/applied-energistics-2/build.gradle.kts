repositories {
    shedaniel()
    modmaven()
}

dependencies {
    runtimeOnly(namedProject("reborn-energy"))
    runtimeOnly(namedProject("fabric-transfer"))

    modImpl(deps.ae2)
    modImpl(deps.fabricApi)
}
