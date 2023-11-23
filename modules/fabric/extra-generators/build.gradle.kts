metadata {
    waila("lol.bai.megane.module.extragenerators.MeganeExtraGenerators")

    fmj {
        depends(
            "extragenerators" to "*"
        )
    }
}

dependencies {
    modImplementation(deps.fabric.extraGen)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.flk)
}
