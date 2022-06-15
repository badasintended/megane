repositories {
    jamieswhiteshirt()
    blamejared()
    buildcraft()
    cafeteria()
    jitpack()
}

dependencies {
    runtimeOnly(namedProject("reborn-energy"))
    runtimeOnly(namedProject("fabric-transfer"))
    runtimeOnly(namedProject("lib-block-attributes"))

    modImpl(deps.indrev)
    modImpl(deps.fabricApi)
    modImpl(deps.flk)
    modImpl(deps.trEnergy)
    modImpl(deps.libgui)
    modImpl(deps.patchouli)
    modImpl(deps.lba.core)
    modImpl(deps.lba.fluids)
    modImpl(deps.lba.items)
    modImpl(deps.magna)
    modImpl(deps.stepAttr)
    modImpl(deps.fakePlayer)
    modImpl(deps.noIndium)
}
