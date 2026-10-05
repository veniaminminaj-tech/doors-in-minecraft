package com.example.holychurch;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModVillagers {
    public static final DeferredRegister<PointOfInterestType> POIS = DeferredRegister.create(ForgeRegistries.POI_TYPES, HolyChurchMod.MODID);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, HolyChurchMod.MODID);

    public static final RegistryObject<PointOfInterestType> PRIEST_POI = POIS.register("priest", () -> new PointOfInterestType("holychurch_priest", PointOfInterestType.getAllStates(ModBlocks.HOLY_GROUND.get()), 1, 1));
    public static final RegistryObject<VillagerProfession> PRIEST = PROFESSIONS.register("priest", () -> new VillagerProfession(
            "holychurch_priest", PRIEST_POI.get(), ImmutableSet.<Item>of(), ImmutableSet.of(ModBlocks.HOLY_GROUND.get()), ImmutableSet.of(SoundEvents.BLOCK_BELL_USE)));

    private ModVillagers() {}
}
