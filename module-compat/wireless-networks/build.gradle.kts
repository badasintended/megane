repositories {
    bbkr()
}

dependencies {
    runtimeOnly(namedProject("tech-reborn"))

    modImpl(deps.wirelessNet)
    modImpl(deps.fabricApi)
    modImpl(deps.trEnergy)
    modImpl(deps.libgui)
}
