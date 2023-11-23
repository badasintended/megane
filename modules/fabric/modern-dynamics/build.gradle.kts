metadata {
    waila("lol.bai.megane.module.moderndynamics.MeganeModernDynamics")
    mixin("lol.bai.megane.module.moderndynamics.mixin")

    fmj {
        mixin(true)
        depends(
            "moderndynamics" to "*"
        )
    }
}

dependencies {
    modImplementation(deps.fabric.modernDynamics)
    modImplementation(deps.fabric.trEnergy)
}
