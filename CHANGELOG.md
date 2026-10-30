# Changelog

## 0.0.3 - TFC 4.2.12 Compatibility

- Updated the development and minimum runtime TFC version to `1.21.1-4.2.12`.
- Updated the development and minimum runtime NeoForge version to `21.1.234`.
- Redesigned all six Glass Press block faces with 32x32 cast-iron, steel, and brass pixel textures; the block model and gameplay are unchanged.
- Fixed English and Simplified Chinese Field Guide links to TFC's reorganized heating and glassworking entries.
- Updated Field Guide temperature text to respect TFC's temperature display setting.
- Clarified that default pressing recipes require the batch to remain at or above 480 C throughout processing.
- Unified the internal mod version and manifest version as `0.0.3`, with the generated filename `tfc_glass_workshop-0.0.3+1.21.1-neoforge.jar`.
- Confirmed the TFC / NeoForge combination passes 173 server-side pressing GameTests. The reported heated-glass pressing failure has not been reproduced; this release does not claim a fix for that unconfirmed issue.
- Verified the updated Field Guide pages and links, Celsius / Fahrenheit display, Glass Press GUI, cold-input and unpowered-machine checks, and plain / red stained glass output in an isolated development client.

## 0.0.2 - 1.21.1 NeoForge

- Fixed the Glass Press mold slot accepting stacked molds through item-handler insertion paths. The slot now accepts only one mold.
- Verified the slot limit in game with manual insertion, shift-click, and automation.

## 0.0.1 - 1.21.1 NeoForge Port

- Ported to Minecraft `1.21.1`.
- Ported from Forge to NeoForge `21.1.x`.
- Updated Java target to `21`.
- Updated TerraFirmaCraft target to `1.21.1-4.1.0+`.
- Updated JEI integration for JEI `1.21.1` NeoForge `19.x`.
- Migrated data pack paths to Minecraft 1.21.1 conventions.
- Migrated custom pressing recipe serialization to Minecraft 1.21.1 APIs.
- Migrated mold anvil, clay knapping, and heating recipes to TFC 1.21.1 formats.
- Replaced old Forge common tags with 1.21.1 `c:` tags where needed.
- Fixed NeoForge client startup by removing an empty global event bus registration.
- Verified core gameplay in game: startup, registration, JEI, Field Guide, Glass Press GUI, mechanical power, pressing outputs, powder dyeing, and hopper automation.

## 0.0.1 - 1.20.1 Forge Initial Release

- Added the Glass Press.
- Added the `tfc_glass_workshop:pressing` recipe type.
- Added wrought iron and steel press molds.
- Added ceramic and unfired ceramic press molds.
- Added clay knapping, heating, and anvil recipes for molds.
- Added 6 mold shapes: plate, block, bottle, jar, lamp glass, and lens.
- Added default pressing recipes for TFC glass batches.
- Added TFC powder-dyed glass block and pane pressing recipes.
- Added heat checks for hot glass batches.
- Added TFC mechanical power requirement from the top of the press.
- Added speed scaling based on mechanical rotation speed.
- Added JEI recipe display support.
- Added TFC Field Guide entries in English and Simplified Chinese.
- Added TFC item size data for press molds.
- Added output-only bottom hopper extraction.
