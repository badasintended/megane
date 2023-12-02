metadata("lol.bai.megane.module.kibe") {
    waila("MeganeKibe")

    fmj {
        depends(
            "kibe" to any
        )
    }
}

repositories {
    ladysnake()
}

dependencies {
    modImplementation(deps.fabric.kibe)
    modImplementation(deps.fabric.flk)
    modImplementation(deps.fabric.pal)
}
