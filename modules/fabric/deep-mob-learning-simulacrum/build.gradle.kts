plugins {
    id("lol.bai.explosion")
}

metadata("lol.bai.megane.module.dmlsimulacrum") {
    waila("MeganeDmlSimulacrum")

    fmj {
        depends(
            "dmlsimulacrum" to any
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
