metadata {
    waila("lol.bai.megane.module.powah.MeganePowah")

    fmj {
        depends(
            "powah" to "*"
        )
    }
}

repositories {
    shedaniel()
}

dependencies {
    modImplementation(deps.fabric.powah)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.architectury)
    modImplementation(deps.fabric.clothConfig)
}
