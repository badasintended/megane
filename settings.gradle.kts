pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven("https://maven.fabricmc.net")
        maven("https://maven.neoforged.net/releases")
        maven("https://repo.spongepowered.org/repository/maven-public")

        maven("https://maven2.bai.lol")
    }

    resolutionStrategy.eachPlugin {
        when (requested.id.id) {
            "org.spongepowered.mixin" -> useModule("org.spongepowered:mixingradle:${requested.version}")
        }
    }
}

rootProject.name = "megane"

include("modules")
include("modules:fabric")
include("modules:forge")

fun fabric(name: String) = include("modules:fabric:$name")
fun forge(name: String) = include("modules:forge:$name")

fabric("alloy-forgery")
fabric("applied-energistics-2")
fabric("create")
fabric("deep-mob-learning-simulacrum")
fabric("extra-generators")
fabric("industrial-revolution")
fabric("kibe")
fabric("luggage")
fabric("modern-dynamics")
fabric("powah")
fabric("reborn-core")
fabric("tech-reborn")
fabric("wireless-networks")

forge("applied-energistics-2")
forge("create")
forge("immersive-engineering")
//forge("mekanism")
//forge("refined-storage")
//forge("thermal-expansion")
