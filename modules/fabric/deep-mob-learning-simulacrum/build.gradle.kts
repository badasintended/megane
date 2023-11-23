plugins {
    id("lol.bai.explosion")
}

metadata {
    waila("lol.bai.megane.module.dmlsimulacrum.MeganeDmlSimulacrum")

    fmj {
        depends(
            "dmlsimulacrum" to "*"
        )
    }
}

repositories {
    shedaniel()
}

dependencies {
    modImplementation(explosion.fabric(deps.fabric.dml))

    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.clothConfig)
}
