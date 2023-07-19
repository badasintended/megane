repositories {
    shedaniel()
    modmaven()
}

dependencies {
    modImpl(deps.ae2) {
        exclude("curse.maven:jade-324717")
        exclude("mezz.jei:jei-${deps.minecraft}-fabric")
    }
    modImpl(deps.fabricApi)
}
