metadata("lol.bai.megane.module.powah") {
    waila("MeganePowah")

    fmj {
        depends(
            "powah" to any
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
