repositories {
    devos()
    tterrag()
    shedaniel()
    jamieswhiteshirt()
    cafeteria()
    ladysnake()
    modrinth()
    jitpack()
}

dependencies {
    modImpl(deps.create) {
        exclude("dev.emi:emi")
    }
    modImpl(deps.fabricApi)
}
