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

    modsToml {
        depends("immersiveengineering" to any)
    }
}

dependencies {
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.ie))
    decompile(fg.deobf(deps.forge.ie))
}
