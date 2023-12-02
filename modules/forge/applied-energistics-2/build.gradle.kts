metadata("lol.bai.megane.module.ae2") {
    waila("MeganeAppliedEnergistics2")
    mixin(mixin::add, mixin::config)
    forgeFml()
    packMcmeta()

    modsToml {
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
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.ae2))
}
