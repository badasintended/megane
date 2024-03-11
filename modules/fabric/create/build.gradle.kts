metadata("lol.bai.megane.module.create") {
    waila("MeganeCreate")
    mixin()
    language()

    fmj {
        depends(
            "create" to any
        )
    }
}

repositories {
    devos()
    devosSnapshots()
    tterrag()
    shedaniel()
    jamieswhiteshirt()
    cafeteria()
    ladysnake()
    fuzs()
    jitpack()
}

dependencies {
    modImplementation(deps.fabric.create) {
        exclude("dev.emi:emi")
        exclude("com.github.LlamaLad7:MixinExtras")
        exclude("com.github.llamalad7.mixinextras:mixinextras-fabric")
        exclude("me.luligabi:NoIndium")
    }

    modImplementation(deps.fabric.noIndium)
}
