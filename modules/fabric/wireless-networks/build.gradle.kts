metadata {
    waila("lol.bai.megane.module.wirelessnetworks.MeganeWirelessNetworks")

    fmj {
        depends(
            "wirelessnetworks" to "*"
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
