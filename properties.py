version = "7.2.0"
minecraft_version = "1.18.2"

dependencies = {
    "wthit": ">=4.7",
    "minecraft": f">={minecraft_version}",
    "fabricloader": ">=0.12.6"
}

modules = {
    "api": "7.1.0",
    "runtime": "7.2.0",

    # "applied-energistics-2": "7.0.1",
    # "deep-mob-learning-simulacrum": "7.0.0",
    # "extra-generators": "7.0.0",
    "fabric-transfer": "7.1.0",
    # "industrial-revolution": "7.0.0",
    "kibe": "7.1.0",
    # "lib-block-attributes": "7.0.0",
    "reborn-core": "7.1.0",
    "reborn-energy": "7.0.0",
    "tech-reborn": "7.1.0",
    "vanilla": "7.1.0",
    # "wireless-networks": "7.0.0"
}


class CurseForge:
    project_id = "408118"
    release_type = "release"
    game_versions = ["Fabric", "1.18.2"]
    required_dependencies = ["wthit"]
    optional_dependencies = [
        # "applied-energistics-2",
        # "deep-mob-learning-simulacrum",
        # "extra-generators",
        # "industrial-revolution",
        "kibe",
        # "libblockattributes",
        "reborncore",
        "techreborn",
        # "wireless-networks",
    ]
