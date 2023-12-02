metadata("lol.bai.megane.module.ie") {
    waila("MeganeImmersiveEngineering")
    forgeFml()
    packMcmeta()

    modsToml {
        depends(
            "immersiveengineering" to any
        )
    }
}

dependencies {
    implementation(fg.deobf(deps.forge.ie))
}
