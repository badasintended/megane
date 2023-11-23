metadata {
    waila("lol.bai.megane.module.alloyforgery.MeganeAlloyForgery")
    mixin("lol.bai.megane.module.alloyforgery.mixin")

    fmj {
        mixin(true)
        contributors("StarskyXIII")
        depends(
            "alloy_forgery" to "*"
        )
    }
}

repositories {
    wispforest()
}

dependencies {
    modImplementation(deps.fabric.alloyForge)
    modImplementation(deps.fabric.owo)
}
