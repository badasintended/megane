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
        exclude("com.github.LlamaLad7:MixinExtras")
    }
    modImpl(deps.fabricApi)
    modImpl(deps.mixinExtras)
}
