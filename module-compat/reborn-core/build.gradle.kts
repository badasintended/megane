dependencies {
    runtimeOnly(namedProject("reborn-energy"))
    runtimeOnly(namedProject("fabric-transfer"))

    modImpl(deps.rebornCore)
    modImpl(deps.fabricApi)
}
