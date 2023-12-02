metadata("lol.bai.megane.module.luggage") {
    waila("MeganeLuggage")

    fmj {
        depends(
            "luggage" to any
        )
    }
}

dependencies {
    modImplementation(deps.fabric.luggage)
}
