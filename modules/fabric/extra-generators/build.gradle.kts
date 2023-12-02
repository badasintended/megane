metadata("lol.bai.megane.module.extragenerators") {
    waila("MeganeExtraGenerators")

    fmj {
        depends(
            "extragenerators" to any
        )
    }
}

dependencies {
    modImplementation(deps.fabric.extraGen)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.flk)
}
