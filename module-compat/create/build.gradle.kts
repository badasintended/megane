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
        exclude("me.luligabi:NoIndium")
    }
    modImpl(deps.fabricApi)
    modImpl(deps.mixinExtras)
    modImpl(deps.noIndium)
    modImpl(deps.portingLib)
    modImpl(deps.registrate)
}
