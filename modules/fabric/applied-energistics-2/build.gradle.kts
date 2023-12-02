metadata("lol.bai.megane.module.ae2") {
    waila("MeganeAppliedEnergistics2")
    mixin()

    fmj {
        depends(
            "ae2" to any
        )
    }
}

repositories {
    shedaniel()
    modmaven()
}

dependencies {
    modImplementation(deps.fabric.ae2) {
        exclude("curse.maven:jade-324717")
        exclude("mezz.jei:jei-${versions.minecraft}-fabric")
    }
}
