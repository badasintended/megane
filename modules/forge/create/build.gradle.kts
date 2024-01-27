metadata("lol.bai.megane.module.create") {
    waila("MeganeCreate") {
        required("create")
    }

    mixin {
        callback(mixin::add, mixin::config)
        require(forgeMod("create"))
    }

    forgeFml()
    packMcmeta()
    language()

    modsToml{
        depends("create" to any)
    }
}

dependencies {
    annotationProcessor(deps.mixinAp)

    implementation(fg.deobf(deps.forge.create))
}
