metadata {
    waila("lol.bai.megane.module.kibe.MeganeKibe")

    fmj {
        depends(
            "kibe" to "*"
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
