metadata("lol.bai.megane.module.thermal") {
    waila("MeganeThermalExpansion")
    forgeFml()
    packMcmeta()

    modsToml {
        depends(
            "thermal_expansion" to any
        )
    }
}

dependencies {
    implementation(fg.deobf(deps.forge.thermal.cofhCore))
    implementation(fg.deobf(deps.forge.thermal.foundation))
    implementation(fg.deobf(deps.forge.thermal.expansion))
}
