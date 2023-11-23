metadata {
    waila("lol.bai.megane.module.techreborn.MeganeTechReborn")

    fmj {
        depends(
            "techreborn" to "*"
        )
    }
}

repositories {
    shedaniel()
}

dependencies {
    modImplementation(deps.fabric.techReborn)
}
