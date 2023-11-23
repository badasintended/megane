metadata {
    waila("lol.bai.megane.module.luggage.MeganeLuggage")

    fmj {
        depends(
            "luggage" to "*"
        )
    }
}

dependencies {
    modImplementation(deps.fabric.luggage)
}
