metadata("lol.bai.megane.module.lapisreserve") {
    waila("MeganeLapisReserve") {
        required("lapisreserve")
    }

    forgeFml()
    packMcmeta()

    modsToml{
        depends("lapisreserve" to any)
    }
}

dependencies {
    implementation(fg.deobf(deps.forge.lapisReserve))
}
