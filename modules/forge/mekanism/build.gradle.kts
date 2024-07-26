metadata("lol.bai.megane.module.mekanism") {
    waila("MeganeMekaninsm") {
        required("mekanism")
    }

    mixin {
        callback(mixin::add, mixin::config)
        require(forgeMod("mekanism"))
    }

    forgeFml()
    packMcmeta()
    language()

    modsToml {
        depends(
            "mekanism" to any
        )
    }
}

repositories {
    modmaven()
}

dependencies {
    implementation(fg.deobf(deps.forge.mekanism.core))
}
