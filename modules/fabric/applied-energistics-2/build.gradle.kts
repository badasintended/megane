metadata {
    waila("lol.bai.megane.module.ae2.MeganeAppliedEnergistics2")
    mixin("lol.bai.megane.module.ae2.mixin")

    fmj {
        mixin(true)
        depends(
            "ae2" to "*"
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
