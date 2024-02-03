metadata("lol.bai.megane.module.indrev") {
    waila("MeganeIndustrialRevolution")

    fmj {
        depends(
            "indrev" to any
        )
    }
}

repositories {
    bbkr()
    jamieswhiteshirt()
    blamejared()
    buildcraft()
    cafeteria()
    draylar()
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
    modImplementation(deps.fabric.noIndium)
}
