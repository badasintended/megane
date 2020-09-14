# megane [![1][1]][6]  [![2][2]][4]  [![3][3]][5] <img src="src/main/resources/assets/megane/icon.png" align="right"/>

megane is a [HWYLA][11] plugin that add useful tooltips such as energy, fluid volumes, and inventory contents.    
megane also add an API for other mod creator to add that tooltip.

- Team Reborn's [Energy][12] support
- Partial [LibBlockAttributes][13] fluid support*

\*uses `FLUID_INV_VIEV` getter, which some mods may not implement it

## For players
### Configuration
Configuration can be done with editing `.minecraft/config/waila/megane.json` directly or via the config screen.    
For energy bar colors and units, `megane` value determine what's the default value.    
**Config will get overloaded by the server**, so if the server disabled inventory tooltip, you won't see inventory tooltip regardless wether you enabled it or not.


## For mod devs
###  How to add a tooltip for your mod
1. First of all, add megane as your dependency
   ```gradle
   repositories {
       maven { url "https://jitpack.io" }
       maven { url "https://maven.tehnut.info" } // HWYLA maven
   }

   dependencies {
       modImplementation("com.github.badasintended:megane:<VERSION>") { transitive = false }
       modRuntime "mcp.mobius.waila:Hwyla:<HWYLA>" // Optional
   }
   ```
   [![](https://jitpack.io/v/badasintended/megane.svg)](https://jitpack.io/#badasintended/megane)    
   you can get HWYLA version from it's [curseforge][11]

2. Make a class that implements `MeganeEntrypoint`
   ```java
   public class MyModMegane implements MeganeEntrypoint {
   
       @Override
       public void initialize(){
           // Register your tooltips here
       }
   
   }
   ```
   There is `EnergyTooltipRegistry`, `FluidTooltipRegistry`, and `ProgressTooltipRegistry`.    
   There is also `dependencies` method that you can override if your mod is a plugin for the tech mod.
   ```java
       @Override
       public String[] dependencies(){
           return new String[]{"astromine", "techreborn"};
       }
    ```
3. Add your class to your `fabric.mod.json`
   ```json
   "entrypoints": {
       "megane": ["com.example.mymod.MyModMegane"]
   }
   ```



[1]: https://img.shields.io/badge/minecraft-1.16+-brightgreen
[2]: https://img.shields.io/badge/loader-Fabric-blue
[3]: https://img.shields.io/badge/code_quality-F-red
[4]: https://fabricmc.net
[5]: https://git.io/code-quality
[6]: https://minecraft.net
[8]: https://www.curseforge.com/minecraft/mc-mods/fabric-api
[9]: https://www.curseforge.com/minecraft/mc-mods/fabric-language-kotlin
[10]: http://cf.way2muchnoise.eu/full_slotlink_downloads.svg
[11]: https://www.curseforge.com/minecraft/mc-mods/hwyla
[12]: https://github.com/TechReborn/Energy
[13]: https://github.com/AlexIIL/LibBlockAttributes
