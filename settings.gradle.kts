rootProject.name = "megane"

fun base(name: String) {
    include(name)
    project(":${name}").projectDir = file("module-base/${name}")
}

fun compat(name: String) {
    include(name)
    project(":${name}").projectDir = file("module-compat/${name}")
}

base("api")
base("runtime")
base("test")

compat("alloy-forgery")
compat("applied-energistics-2")
compat("create")
compat("deep-mob-learning-simulacrum")
compat("extra-generators")
compat("fabric-transfer")
compat("industrial-revolution")
compat("kibe")
compat("lib-block-attributes")
compat("luggage")
compat("modern-dynamics")
compat("powah")
compat("reborn-core")
compat("reborn-energy")
compat("tech-reborn")
compat("vanilla")
compat("wireless-networks")
