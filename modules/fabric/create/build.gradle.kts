metadata {
    waila("lol.bai.megane.module.create.MeganeCreate")
    mixin("lol.bai.megane.module.create.mixin")

    fmj {
        mixin(true)
        depends(
            "create" to "*"
        )
    }
}

repositories {
    devos()
    tterrag()
    shedaniel()
    jamieswhiteshirt()
    cafeteria()
    ladysnake()
    jitpack()
}

dependencies {
    modImplementation(deps.fabric.create) {
        exclude("dev.emi:emi")
        exclude("com.github.LlamaLad7:MixinExtras")
        exclude("com.github.llamalad7.mixinextras:mixinextras-fabric")
        exclude("me.luligabi:NoIndium")
    }

    modImplementation(deps.fabric.mixinExtras)
    modImplementation(deps.fabric.noIndium)
}
