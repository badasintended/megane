metadata("lol.bai.megane.module.alloyforgery") {
    waila("MeganeAlloyForgery")
    mixin()

    fmj {
        contributors("StarskyXIII")
        depends(
            "alloy_forgery" to any
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
