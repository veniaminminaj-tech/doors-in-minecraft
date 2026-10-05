package com.example.holychurch;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;

@Mod(HolyChurchMod.MODID)
public class HolyChurchMod {
    public static final String MODID = "holychurch";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    private static Item.Properties holyProps() {
        return new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1).rarity(Rarity.EPIC);
    }

    public static final RegistryObject<Item> CRUCIFIX = ITEMS.register("crucifix", () -> new CrucifixItem(holyProps()));
    public static final RegistryObject<Item> BIBLE = ITEMS.register("bible", () -> new BibleItem(holyProps()));
    public static final RegistryObject<Item> HOLY_WATER = ITEMS.register("holy_water", () -> new HolyWaterItem(new Item.Properties().group(ItemGroup.BREWING).maxStackSize(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MASTER_KEY = ITEMS.register("master_key", () -> new MasterKeyItem(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> STRANGE_GRASS = ITEMS.register("strange_grass", () ->
            new StrangeGrassItem(new Item.Properties().group(ItemGroup.FOOD).maxStackSize(16)
                    .food(new net.minecraft.item.Food.Builder().hunger(2).saturation(0.3F).build())));

    public HolyChurchMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModBlocks.ITEMS.register(bus);
        ModVillagers.POIS.register(bus);
        ModVillagers.PROFESSIONS.register(bus);
        MinecraftForge.EVENT_BUS.register(HolyEvents.class);
        ChurchWorldGen.register();
    }
}
