package com.example.holychurch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ChurchAltarBlock extends Block {
    public ChurchAltarBlock() {
        super(Properties.from(Blocks.GOLD_BLOCK).hardnessAndResistance(3.0F, 6.0F).lightValue(10));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand,
                                             net.minecraft.util.math.BlockRayTraceResult hit) {
        if (world.isRemote) return ActionResultType.SUCCESS;

        if (player.getHeldItem(hand).getItem() == HolyChurchMod.CRUCIFIX.get()) {
            player.sendStatusMessage(new StringTextComponent("§6The altar recognizes the Crucifix."), true);
            return ActionResultType.SUCCESS;
        }

        boolean alreadyHas = player.inventory.hasItemStack(new ItemStack(HolyChurchMod.CRUCIFIX.get()));
        if (!alreadyHas && !player.getPersistentData().getBoolean("HolyChurchCrucifixClaimed")) {
            ItemStack crucifix = new ItemStack(HolyChurchMod.CRUCIFIX.get());
            if (!player.inventory.addItemStackToInventory(crucifix)) {
                player.entityDropItem(crucifix, 0.5F);
            }
            player.getPersistentData().putBoolean("HolyChurchCrucifixClaimed", true);
            player.sendStatusMessage(new StringTextComponent("§eThe altar gives you a Crucifix."), true);
        } else {
            player.sendStatusMessage(new StringTextComponent("§7The altar is silent. You have already received its relic."), true);
        }
        return ActionResultType.SUCCESS;
    }
}
