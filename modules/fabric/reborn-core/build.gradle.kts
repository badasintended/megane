metadata("lol.bai.megane.module.reborncore") {
    waila("MeganeRebornCore")

    fmj {
        depends(
            "reborncore" to any
        )
    }
}

dependencies {
    modImplementation(deps.fabric.rebornCore)
}
