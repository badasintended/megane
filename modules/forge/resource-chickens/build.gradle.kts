metadata("lol.bai.megane.module.resourcechickens") {
    waila("MeganeResourceChickens") {
        required("resourcechickens")
    }

    forgeFml()
    packMcmeta()
    language()

    modsToml{
        depends("resourcechickens" to any)
    }
}

repositories {
    curseApi()
}

dependencies {
    implementation(fg.deobf(deps.forge.resourceChickens))
    runtimeOnly(fg.deobf(deps.forge.top))
}
