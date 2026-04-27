# Changelog

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
