metadata("lol.bai.megane.module.ie") {
    waila("MeganeImmersiveEngineering")
    mixin(mixin::add, mixin::config)
    forgeFml()
    packMcmeta()
    language()

    modsToml {
        depends(
            "immersiveengineering" to any
        )
    }
}

dependencies {
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.ie))
}
