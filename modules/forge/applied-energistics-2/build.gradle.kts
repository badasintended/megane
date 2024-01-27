metadata("lol.bai.megane.module.ae2") {
    waila("MeganeAppliedEnergistics2") {
        required("ae2")
    }

    mixin {
        callback(mixin::add, mixin::config)
        require(forgeMod("ae2"))
    }

    forgeFml()
    packMcmeta()

    modsToml {
        depends("ae2" to any)
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
