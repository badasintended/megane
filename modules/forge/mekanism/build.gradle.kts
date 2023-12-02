metadata("lol.bai.megane.module.mekanism") {
    waila("MeganeMekaninsm")
    forgeFml()
    packMcmeta()

    modsToml {
        depends(
            "mekanism" to any
        )
    }
}

repositories {
    modmaven()
}

dependencies {
    implementation(fg.deobf(deps.forge.mekanism.core))
    implementation(fg.deobf(deps.forge.mekanism.generators))
}
