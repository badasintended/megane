metadata("lol.bai.megane.module.rs") {
    waila("MeganeRefinedStorage")
    forgeFml()
    packMcmeta()

    modsToml {
        depends(
            "refinedstorage" to any
        )
    }
}

dependencies {
    implementation(fg.deobf(deps.forge.rs))
}
