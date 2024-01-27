metadata("lol.bai.megane.module.ie") {
    waila("MeganeImmersiveEngineering") {
        required("immersiveengineering")
    }

    mixin {
        callback(mixin::add, mixin::config)
        require(forgeMod("immersiveengineering"))
    }

    forgeFml()
    packMcmeta()
    language()
    modsToml()
}

dependencies {
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.ie))
}
