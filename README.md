# Holy Church — Forge 1.16.5

Restored and expanded from the user-provided `holychurch_crucifix_bible_1.16.5(1).jar`.

## Current systems
- Holy Crucifix: passive protection, monster slowing, temporary resistance barrier and cooldown.
- Holy Bible: glint + protective resistance while held.
- Holy Water: drink to cleanse potion effects and receive regeneration/resistance/fire resistance/night vision; sneak-right-click creates a small holy purification burst that damages monsters.
- Master Key: opens Locked Chests.
- Locked Chest: cannot be broken in survival without the Master Key.
- Holy Ground block.
- Priest profession + trades for Bible, Holy Water and Master Key.
- A safe server-side ChurchFeature generator class is included for the next world-generation integration step.

## Removed legacy systems
The restored project does not intentionally include Red Slime Essence, Sift/Candy/Infinite Water dimensions, Portal Gun/Portal GUI, Admin Gun, Knockback Stick, Jail Hammer or the old Crucifix ban/admin behavior.

## Build
Target: Minecraft 1.16.5, Forge 36.2.34, Java 8.

Run from the project directory after installing a compatible Gradle/ForgeGradle environment:
`gradle build`

The compiled mod will be in `build/libs/`.


## Added in the Church expansion
- 25x25 vanilla-block church with altar, library, crypt area and bell tower.
- Strange Grass: edible fantasy food item. Eating it grants 30 seconds of Night Vision and 8 seconds of Nausea.
- Church generation is attached to biome surface structures and is intentionally rare.


## v3 additions
- Church altar that grants the Crucifix once per player.
- Three persistent Church Guardians spawn around the altar.
- Crypt chamber beneath the altar.
- Church altar block/model/lang resources.


## v4 additions
- Church Priest is spawned inside generated churches and uses the custom profession.
- Strange Grass has random supernatural side effects when eaten.
- Church Guardians remain persistent around the altar.

Build with Forge 1.16.5 MDK and Java 8.
