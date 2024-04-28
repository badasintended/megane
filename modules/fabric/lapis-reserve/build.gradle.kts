metadata("lol.bai.megane.module.lapisreserve") {
    waila("MeganeLapisReserve")

    fmj {
        depends(
            "lapisreserve" to any
        )
    }
}

dependencies {
    modImplementation(deps.fabric.lapisReserve)
}
