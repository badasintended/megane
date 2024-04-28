metadata("lol.bai.megane.module.resourcechickens") {
    waila("MeganeResourceChickens")
    language()

    fmj {
        depends(
            "resourcechickens" to any
        )
    }
}

repositories {
    curseApi()
}

dependencies {
    modImplementation(deps.fabric.resourceChickens)
}
