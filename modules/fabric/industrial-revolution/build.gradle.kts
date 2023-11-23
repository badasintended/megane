metadata {
    waila("lol.bai.megane.module.indrev.MeganeIndustrialRevolution")

    fmj {
        depends(
            "indrev" to "*"
        )
    }
}

repositories {
    bbkr()
    jamieswhiteshirt()
    blamejared()
    buildcraft()
    cafeteria()
    jitpack()
}

dependencies {
    modImplementation(deps.fabric.indrev)
    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.libgui)
    modImplementation(deps.fabric.patchouli)
    modImplementation(deps.fabric.magna)
    modImplementation(deps.fabric.stepAttr)
    modImplementation(deps.fabric.fakePlayer)
    modImplementation(deps.fabric.noIndium)
}
