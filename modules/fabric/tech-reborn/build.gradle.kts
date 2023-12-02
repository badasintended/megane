metadata("lol.bai.megane.module.techreborn") {
    waila("MeganeTechReborn")

    fmj {
        depends(
            "techreborn" to any
        )
    }
}

repositories {
    shedaniel()
}

dependencies {
    modImplementation(deps.fabric.techReborn)
}
