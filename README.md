# megane <img src="https://user-images.githubusercontent.com/21150434/122664496-ff7ed300-d1cb-11eb-871a-6671514f01ed.png" align="right"/>
[![Fabric Downloads](http://cf.way2muchnoise.eu/full_408118.svg?badge_style=for_the_badge)](https://www.curseforge.com/minecraft/mc-mods/megane)
[![Discord](https://img.shields.io/discord/711835441376264242?color=5865F2&logo=discord&logoColor=FFFFFF&style=for-the-badge)](https://bai.lol/discord)

Megane is a [WTHIT](https://www.curseforge.com/minecraft/mc-mods/wthit) plugin that adds useful tooltips such as energy, fluid volumes, and inventory contents.

### Building from Source
Run the `setup.sh` script before importing/building the project.

### Configuration
Configuration can be done with editing `.minecraft/config/waila/megane.json` directly or via the config screen.    
For energy bar colors and units, `megane` value determines what's the default value.    
**Config will get overloaded by the server**, so if the server disabled inventory tooltip, you won't see inventory tooltip regardless wether you
enabled it or not.

### Adding Megane Compatibility to Your Mod
First, add megane on your build script:
```gradle
repositories {
    maven { url "https://maven.bai.lol" }
}

dependencies {
    modImplementation "lol.bai.megane:megane-api:${megane_version}"
    modRuntimeOnly "lol.bai.megane:megane-runtime:${megane_version}"
    
    // Optional: add other module to runtime classpath
    // see the release page to see available modules
    modRuntimeOnly "lol.bai.megane:megane-${module_name}:${megane_version}"
}
```

Implement `MeganeModule` interface, register your providers there
```java
class MyModMeganeModule implements MeganeModule {
    @Override
    public void registerCommon(CommonRegistrar registrar) {
    }

    @Override
    public void registerClient(ClientRegistrar registrar) {
    }
}
```

In `fabric.mod.json`, add your module to the custom block:
```json
{
    "custom": {
        "megane:modules": [
            "path.to.MyModMeganeModule"
        ]
    }
}
```
### Culture

Glasses are really versatile. First, you can have glasses-wearing girls take them off and suddenly become beautiful, or have girls wearing glasses
flashing those cute grins, or have girls stealing the protagonist's glasses and putting them on like, "Haha, got your glasses!' That's just way too
cute! Also, boys with glasses! I really like when their glasses have that suspicious looking gleam, and it's amazing how it can look really cool or
just be a joke. I really like how it can fulfill all those abstract needs. Being able to switch up the styles and colors of glasses based on your mood
is a lot of fun too! It's actually so much fun! You have those half rim glasses, or the thick frame glasses, everything! It's like you're enjoying all
these kinds of glasses at a buffet. I really want Luna to try some on or Marine to try some on to replace her eyepatch. We really need glasses to
become a thing in hololive and start selling them for HoloComi. Don't. You. Think. We. Really. Need. To. Officially. Give. Everyone. Glasses?
