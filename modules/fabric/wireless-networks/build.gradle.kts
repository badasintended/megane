metadata("lol.bai.megane.module.wirelessnetworks") {
    waila("MeganeWirelessNetworks")

    fmj {
        depends(
            "wirelessnetworks" to any
        )
    }
}

repositories {
    bbkr()
}

dependencies {
    modImplementation(deps.fabric.wirelessNet)
    modImplementation(deps.fabric.fabricApi)
    modImplementation(deps.fabric.trEnergy)
    modImplementation(deps.fabric.libgui)
}
