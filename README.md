# TerraFirmaCraft: Glass Workshop

TerraFirmaCraft: Glass Workshop is a small NeoForge add-on for TerraFirmaCraft that adds a TFC-style glass press for batch production of glass products.

The mod keeps the normal TFC glass workflow intact: glass batches still need to be heated with TFC mechanics, molds still matter, and the press needs mechanical power from above. It simply adds a focused machine for turning hot glass batches into blocks, panes, bottles, jars, lamp glass, lenses, and dyed glass in a more repeatable way.

## Features

- Adds the Glass Press, a TFC mechanical-power machine for batch glass production.
- Uses hot TFC glass batches and TFC heat data. The press does not heat glass by itself.
- Requires TFC mechanical power from the top. Faster rotation presses faster.
- Supports reusable wrought iron and steel press molds.
- Supports cheaper ceramic press molds that can break during use.
- Adds 6 mold shapes:
  - Plate
  - Block
  - Bottle
  - Jar
  - Lamp Glass
  - Lens
- Supports TFC-style glass powder dyeing through three powder slots.
- Adds JEI display support for glass pressing recipes.
- Adds entries to the TFC Field Guide instead of a separate book.
- Allows bottom hoppers to extract only finished output, so automation will not pull out the input batch, mold, or powders.

## Basic Workflow

1. Heat a TFC glass batch to at least 480 C (Faint Red) for the default recipes, and keep it at or above that temperature until pressing finishes.
2. Place the hot glass batch in the input slot.
3. Place a press mold in the mold slot.
4. For dyed glass blocks or panes, place the required TFC glassworking powders in the three powder slots.
5. Connect TFC mechanical power to the top of the Glass Press.
6. Take the finished glass product from the output slot.

## Molds

Metal molds are permanent and are made through TFC anvil recipes.

- Wrought iron molds unlock at the wrought iron stage.
- Steel molds use steel sheets and a higher anvil tier.

Ceramic molds are made through TFC-style clay knapping and firing. They are cheaper, but each press operation has a chance to break the mold.

## Glass Outputs

The default glass batch colors follow TFC's standard glass behavior:

| Glass batch | Block output | Pane output |
| --- | --- | --- |
| Silica | Glass | Glass Pane |
| Hematitic | Orange Stained Glass | Orange Stained Glass Pane |
| Olivine | Green Stained Glass | Green Stained Glass Pane |
| Volcanic | Blue Stained Glass | Blue Stained Glass Pane |

With powders, the press supports the same TFC glassworking color combinations for dyed glass blocks and panes. Plate mold recipes output final Minecraft glass panes directly instead of TFC poured-glass intermediate items.

## Automation Notes

- The top face is reserved for TFC mechanical power input.
- The bottom face exposes only the output slot to item handlers such as hoppers.
- Other sides expose the machine inventory for automation.

## Compatibility

Current NeoForge build (`0.0.3`):

- Minecraft `1.21.1`
- NeoForge `21.1.234+` on the `21.1.x` line
- TerraFirmaCraft `1.21.1-4.2.12+`
- Java `21`

Version `0.0.3` uses TFC's reorganized Field Guide entries and config-aware temperature display. Earlier Glass Workshop releases retain their own dependency requirements.

The Glass Press also has redesigned 32x32 cast-iron, steel, and brass block textures in `0.0.3`, without changing its model or gameplay.

Legacy Forge line:

- Minecraft `1.20.1`
- Forge `47.x`
- TerraFirmaCraft `1.20.1-3.1.18+`
- Java `17`

The 1.21.1 NeoForge jar and the 1.20.1 Forge jar are separate files. Do not use one jar across both Minecraft versions.

Optional integration:

- JEI `19.x` on 1.21.1 NeoForge
- JEI `15.x` on 1.20.1 Forge

## Development

The current port branch uses NeoGradle for Minecraft 1.21.1 + NeoForge.

Common local commands:

```powershell
$env:JAVA_HOME='C:\Program Files\Java\jdk-21'
$env:PATH="$env:JAVA_HOME\bin;$env:PATH"
$env:GRADLE_USER_HOME='D:\java\TFC-Glass-Workshop\.gradle-home'
.\gradlew.bat processResources compileJava
.\gradlew.bat runClient
.\gradlew.bat build
```

The build produces `build/libs/tfc_glass_workshop-0.0.3+1.21.1-neoforge.jar`.

## Project Status

The 1.21.1 NeoForge port has passed core gameplay regression testing:

- Glass Press block, block entity, menu, and GUI
- Mechanical power requirement
- Heat checks for glass batches
- Metal and ceramic molds
- Default and dyed glass pressing recipes
- JEI recipe display
- TFC Field Guide integration
- Bottom hopper output behavior

The TFC `4.2.12` / NeoForge `21.1.234` compatibility check passed 173 server-side GameTests covering all 57 pressing recipes with iron, steel, and ceramic molds, plus cold-input and unpowered-machine checks. These tests set temperatures through TFC's heat API; they do not replace client-side GUI and Field Guide checks.

An isolated development-client smoke test also verified the updated English and Chinese Field Guide pages, heating and glassworking links, 480 C / 896 F display, the Glass Press GUI, cold-input and unpowered-machine checks, and plain and red stained glass output. Heat was initialized through TFC's heat component for this test.

Before publishing `0.0.3`, perform a final packaged-jar smoke test in a clean client instance and prepare platform metadata.

## License

The mod is currently declared as All Rights Reserved in `gradle.properties`.

Forge MDK notice files are retained for the legacy Forge development history.
