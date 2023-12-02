metadata("lol.bai.megane.module.moderndynamics") {
    waila("MeganeModernDynamics")
    mixin()

    fmj {
        depends(
            "moderndynamics" to any
        )
    }
}

dependencies {
    modImplementation(deps.fabric.modernDynamics)
    modImplementation(deps.fabric.trEnergy)
}
