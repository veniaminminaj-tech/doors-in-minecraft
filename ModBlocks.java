package com.example.holychurch;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.block.Blocks;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HolyChurchMod.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HolyChurchMod.MODID);

    public static final RegistryObject<Block> LOCKED_CHEST = BLOCKS.register("locked_chest", () -> new LockedChestBlock(AbstractBlock.Properties.from(Blocks.CHEST).hardnessAndResistance(2.5F)));
    public static final RegistryObject<Block> CHURCH_ALTAR = BLOCKS.register("church_altar", ChurchAltarBlock::new);
    public static final RegistryObject<Block> HOLY_GROUND = BLOCKS.register("holy_ground", () -> new Block(AbstractBlock.Properties.create(Material.EARTH).hardnessAndResistance(0.6F)));

    static {
        ITEMS.register("locked_chest", () -> new BlockItem(LOCKED_CHEST.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
        ITEMS.register("church_altar", () -> new BlockItem(CHURCH_ALTAR.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
        ITEMS.register("holy_ground", () -> new BlockItem(HOLY_GROUND.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
    }
    private ModBlocks() {}
}
