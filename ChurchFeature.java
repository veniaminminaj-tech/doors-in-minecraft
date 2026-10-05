package com.example.holychurch;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.ISeedReader;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerDataHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.util.math.BlockPos;
import java.util.Random;

/** A vanilla-block church that is safe on dedicated servers. */
public class ChurchFeature extends Feature<NoFeatureConfig> {
    public ChurchFeature(Codec<NoFeatureConfig> codec) { super(codec); }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random random, BlockPos pos, NoFeatureConfig config) {
        // Roughly 1 church per 1000 generated attempts/chunks.
        if (random.nextInt(1000) != 0) return false;
        int groundY = world.getHeight(net.minecraft.world.gen.Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
        BlockPos base = new BlockPos(pos.getX(), groundY, pos.getZ());
        int half = 12;
        if (!world.isAirBlock(base.up())) return false;

        BlockState wall = Blocks.STONE_BRICKS.getDefaultState();
        BlockState cracked = Blocks.CRACKED_STONE_BRICKS.getDefaultState();
        BlockState floor = Blocks.POLISHED_ANDESITE.getDefaultState();
        BlockState glass = Blocks.WHITE_STAINED_GLASS.getDefaultState();

        // 25x25 floor and outer walls.
        for (int x=-half; x<=half; x++) for (int z=-half; z<=half; z++) {
            world.setBlockState(base.add(x,0,z), floor, 2);
            boolean outer = Math.abs(x)==half || Math.abs(z)==half;
            if (outer) {
                for (int y=1; y<=6; y++) world.setBlockState(base.add(x,y,z), wall, 2);
            }
        }

        // Nave ceiling and stained-glass windows.
        for (int x=-half+1; x<=half-1; x++) for (int z=-half+1; z<=half-1; z++) {
            world.setBlockState(base.add(x,7,z), wall, 2);
        }
        for (int z=-8; z<=8; z+=4) {
            world.setBlockState(base.add(-half,3,z), glass, 2);
            world.setBlockState(base.add(half,3,z), glass, 2);
        }

        // Entrance, altar and holy ground.
        for (int y=1; y<=3; y++) for (int x=-1; x<=1; x++) world.setBlockState(base.add(x,y,-half), Blocks.AIR.getDefaultState(), 2);
        for (int x=-3; x<=3; x++) for (int z=8; z<=10; z++) world.setBlockState(base.add(x,1,z), ModBlocks.HOLY_GROUND.get().getDefaultState(), 2);
        world.setBlockState(base.add(0,1,8), ModBlocks.CHURCH_ALTAR.get().getDefaultState(), 2);
        world.setBlockState(base.add(0,2,8), Blocks.GLOWSTONE.getDefaultState(), 2);
        // Small crypt chamber beneath the altar.
        for (int x=-4; x<=4; x++) for (int z=5; z<=11; z++) {
            world.setBlockState(base.add(x,-2,z), Blocks.STONE_BRICKS.getDefaultState(), 2);
        }
        for (int x=-3; x<=3; x++) for (int z=6; z<=10; z++) {
            world.setBlockState(base.add(x,-1,z), Blocks.AIR.getDefaultState(), 2);
        }
        for (int x=-1; x<=1; x++) world.setBlockState(base.add(x,0,10), Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(base.add(0,3,8), Blocks.IRON_BARS.getDefaultState(), 2);

        // Library shelves and crypt entrance.
        for (int x=-10; x<=-6; x++) for (int z=-4; z<=4; z+=2) world.setBlockState(base.add(x,1,z), Blocks.BOOKSHELF.getDefaultState(), 2);
        for (int x=6; x<=10; x++) for (int z=-4; z<=4; z+=2) world.setBlockState(base.add(x,1,z), Blocks.BOOKSHELF.getDefaultState(), 2);
        for (int x=-2; x<=2; x++) for (int z=-10; z<=-8; z++) world.setBlockState(base.add(x,1,z), cracked, 2);

        // Bell tower.
        for (int y=1; y<=12; y++) {
            for (int dx=-2; dx<=2; dx++) for (int dz=-2; dz<=2; dz++) {
                if (Math.abs(dx)==2 || Math.abs(dz)==2 || y>=9) world.setBlockState(base.add(dx,y,half+3+dz), wall, 2);
            }
        }
        world.setBlockState(base.add(0,9,half+3), Blocks.BELL.getDefaultState(), 2);
        world.setBlockState(base.add(0,13,half+3), Blocks.GLOWSTONE.getDefaultState(), 2);

        // Priest stays in the church and keeps its profession.
        VillagerEntity priest = EntityType.VILLAGER.create(world.getWorld());
        if (priest != null) {
            priest.setPosition(base.getX() - 6.0D, base.getY() + 1.0D, base.getZ() + 7.0D);
            priest.setCustomName(new net.minecraft.util.text.StringTextComponent("Priest"));
            priest.setCustomNameVisible(false);
            priest.setVillagerData(priest.getVillagerData().withProfession(ModVillagers.PRIEST.get()));
            priest.enablePersistence();
            world.addEntity(priest);
        }

        // Three Church Guardians protect the altar.
        for (int i = 0; i < 3; i++) {
            SkeletonEntity guardian = EntityType.SKELETON.create(world.getWorld());
            if (guardian != null) {
                guardian.setPosition(base.getX() + (i - 1) * 4.0D, base.getY() + 1.0D, base.getZ() + 5.0D);
                guardian.setCustomName(new net.minecraft.util.text.StringTextComponent("Church Guardian"));
                guardian.setCustomNameVisible(false);
                guardian.enablePersistence();
                guardian.setItemStackToSlot(net.minecraft.inventory.EquipmentSlotType.HEAD,
                        new ItemStack(Blocks.GOLD_BLOCK.asItem()));
                world.addEntity(guardian);
            }
        }
        return true;
    }
}
