package com.example.holychurch;

import com.mojang.serialization.Codec;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.gen.GenerationStage;

@Mod.EventBusSubscriber(modid = HolyChurchMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ChurchWorldGen {
    private static final Feature<NoFeatureConfig> CHURCH = new ChurchFeature(Codec.unit(NoFeatureConfig.INSTANCE));
    private static final ConfiguredFeature<?, ?> CONFIGURED_CHURCH = CHURCH.withConfiguration(NoFeatureConfig.INSTANCE);

    private ChurchWorldGen() {}

    public static void register() {
        Registry.register(Registry.FEATURE, new ResourceLocation(HolyChurchMod.MODID, "church"), CHURCH);
        Registry.register(Registry.CONFIGURED_FEATURE, new ResourceLocation(HolyChurchMod.MODID, "church"), CONFIGURED_CHURCH);
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        event.getGeneration().withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, CONFIGURED_CHURCH);
    }
}
