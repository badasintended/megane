repositories {
    shedaniel()
    cafeteria()
    ladysnake()
    jitpack()
    flatDir {
        dirs("dml_modules")
    }
}

dependencies {
    modImpl(deps.dmlSim)
    modImpl(deps.fabricApi)
    modImpl(deps.trEnergy)
    modImpl(deps.flk)
    modImpl(deps.pal)
    modImpl(deps.libgui)
    modImpl(deps.clothConfig)

    fileTree("dml_modules") {
        include("*.jar")
    }.visit {
        modImplementation(group = "", name = name.replace(Regex("[.]jar$"), ""))
    }
}
