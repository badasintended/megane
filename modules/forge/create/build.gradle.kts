metadata("lol.bai.megane.module.create") {
    waila("MeganeCreate")
    mixin(mixin::add, mixin::config)
    forgeFml()
    packMcmeta()
    language()

    modsToml {
        depends(
            "create" to any
        )
    }
}

dependencies {
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.create))
}
