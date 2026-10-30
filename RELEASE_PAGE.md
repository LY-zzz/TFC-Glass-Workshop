# Release Page Draft

This file contains copy-ready text for Modrinth, CurseForge, or a GitHub release.

## Short Description

Adds a TFC-style mechanical glass press for batch glass production, reusable molds, ceramic molds, and powder-dyed glass recipes.

## Long Description

TerraFirmaCraft: Glass Workshop expands TFC glass production with one focused machine: the Glass Press.

Instead of replacing TerraFirmaCraft's glass systems, this add-on builds on them. You still heat glass batches with TFC mechanics, still work with metal and ceramic molds, and still rely on TFC-style progression. The press simply gives you a batch-production step for turning hot glass batches into finished glass products.

The Glass Press requires mechanical power from above. Faster rotation means faster pressing. It can produce glass blocks, glass panes, TFC glass bottles, jars, lamp glass, and lenses depending on the installed mold.

For colored glass, the press includes three powder slots and supports the same powder combinations used by TFC glassworking. Add the right powders, install a block or plate mold, and press dyed glass blocks or panes directly.

## Features

- Glass Press machine for TFC-style batch glass production
- Requires TFC mechanical power from the top
- Uses hot TFC glass batches and TFC heat data
- 6 mold shapes:
  - Plate
  - Block
  - Bottle
  - Jar
  - Lamp Glass
  - Lens
- Permanent wrought iron and steel molds
- Ceramic molds made with clay knapping and firing
- Ceramic molds have a chance to break during pressing
- Powder dyeing for glass blocks and panes
- JEI recipe category for pressing recipes
- Integrated TFC Field Guide entries
- Bottom hopper extraction only pulls finished output

## Requirements

- Minecraft `1.21.1`
- NeoForge `21.1.234+` on the `21.1.x` line
- TerraFirmaCraft `1.21.1-4.2.12+`
- Java `21`

Recommended:

- JEI `19.x`

This is the 1.21.1 NeoForge build. It is separate from the 1.20.1 Forge build and should not be used on Minecraft 1.20.1.

Release version: `0.0.3`.

File: `tfc_glass_workshop-0.0.3+1.21.1-neoforge.jar`.

## How To Use

1. Heat a TFC glass batch to at least 480 C (Faint Red) for the default recipes. Keep it at or above that temperature until pressing finishes; the press does not add heat.
2. Place it in the Glass Press input slot.
3. Insert a matching press mold.
4. For dyed glass, add the required powders to the three powder slots.
5. Power the Glass Press from the top with TFC mechanical power.
6. Collect the output manually or with a hopper underneath.

## Automation

The Glass Press is automation-friendly:

- Top: mechanical power input
- Bottom: output-only item access
- Other sides: inventory access for automation setups

This prevents a hopper below the press from stealing glass batches, molds, or powders.

## 0.0.3 Changelog

- Updated the supported TFC baseline to `1.21.1-4.2.12+` and NeoForge to `21.1.234+`.
- Redesigned all six Glass Press faces with 32x32 cast-iron, steel, and brass pixel textures; the model and gameplay are unchanged.
- Fixed English and Simplified Chinese Field Guide links for TFC's reorganized heating and glassworking entries.
- Updated Field Guide temperature text to follow TFC's temperature display setting.
- Clarified that glass batches must remain hot throughout pressing.
- Unified the internal mod version, manifest version, and generated filename for `0.0.3`.
- The supported TFC / NeoForge combination passed 173 server-side pressing GameTests.

## 1.21.1 Port History

- Ported the mod to Minecraft 1.21.1 and NeoForge.
- Updated runtime target to Java 21.
- Updated TFC compatibility target to TerraFirmaCraft 1.21.1-4.1.0+.
- Updated JEI integration for JEI 1.21.1 NeoForge 19.x.
- Migrated resources to Minecraft 1.21.1 data pack paths.
- Migrated pressing, mold, heating, knapping, and anvil recipe JSON formats.
- Updated common tags from old Forge tags to NeoForge/TFC 1.21.1 `c:` tags.
- Verified core Glass Press gameplay, JEI, Field Guide, mechanical power, powder dyeing, and automation behavior in game.

## Feature Summary

- Added Glass Press
- Added pressing recipe type
- Added wrought iron, steel, ceramic, and unfired ceramic press molds
- Added clay knapping, heating, and anvil recipes for molds
- Added default glass block, pane, bottle, jar, lamp glass, and lens pressing
- Added TFC powder-dyed glass block and pane pressing
- Added JEI integration
- Added TFC Field Guide entries in English and Simplified Chinese
- Added TFC item size data for press molds
- Added mechanical power scaling
- Added output-only bottom hopper extraction

## Suggested Tags

- TerraFirmaCraft
- TFC
- NeoForge
- Glass
- Survival
- Tech
- Automation
- Mechanical Power
- JEI

## Suggested Screenshots

Add screenshots before publishing:

- Glass Press placed in-world
- Glass Press GUI with hot glass batch, mold, powders, and output
- Mechanical power connected from above
- Metal and ceramic molds in inventory
- JEI pressing recipe page
- TFC Field Guide Glass Workshop entry

## Chinese Description

TerraFirmaCraft: Glass Workshop 为 TFC 添加了一台轻量的玻璃压制机，用于批量生产玻璃制品。

它不替代 TFC 原有玻璃体系：玻璃原料仍然需要通过 TFC 的加热机制烧到足够温度，压制机也需要从顶部接入 TFC 机械动力。玩家可以安装不同形状的模具，将高温玻璃原料压制成玻璃块、玻璃板、TFC 玻璃瓶、空罐、灯罩和透镜。

压制机还提供 3 个粉末槽，兼容 TFC 原版玻璃工艺的粉末染色组合，可直接压制染色玻璃块和染色玻璃板。

核心特性：

- 新机器：玻璃压制机
- 需要顶部机械动力
- 支持 TFC 热量检测
- 6 种模具形状
- 永久使用的锻铁/钢模具
- 便宜但可能破碎的陶瓷模具
- TFC 原版粉末染色兼容
- JEI 配方显示
- 集成进 TFC Field Guide
- 下方漏斗只会抽出成品，不会抽走原料、模具或粉末

需求：

- Minecraft `1.21.1`
- NeoForge `21.1.234+`（`21.1.x` 系列）
- TerraFirmaCraft `1.21.1-4.2.12+`
- Java `21`
- 推荐安装 JEI `19.x`

当前版本为 `0.0.3`。默认压制配方要求玻璃原料在整个加工过程中保持至少 480 C（淡红）；压制机本身不会加热原料。

`0.0.3` 同时更新了压制机六面的 32x32 像素贴图，采用铸铁、钢和黄铜的机械工坊风格；模型与加工逻辑不变。
