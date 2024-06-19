metadata("lol.bai.megane.module.productivebees") {
    waila("MeganeProductiveBees") {
        required("productivebees")
    }

    forgeFml()
    packMcmeta()
    language()

    modsToml{
        depends("productivebees" to any)
    }
}

dependencies {
    implementation(fg.deobf(deps.forge.productiveBees))
}
