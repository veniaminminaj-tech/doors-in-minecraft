package com.example.holychurch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LockedChestBlock extends Block {
    public LockedChestBlock(Properties properties) { super(properties); }

    @Override
    public void onBlockClicked(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (!world.isRemote) player.sendStatusMessage(new StringTextComponent("§6Locked chest: find a key or the church's blessing."), true);
        super.onBlockClicked(state, world, pos, player);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isRemote && !player.isCreative() && player.getHeldItemMainhand().getItem() != HolyChurchMod.MASTER_KEY.get()) {
            player.sendStatusMessage(new StringTextComponent("§cThe chest is sealed. A Master Key is required."), true);
            return;
        }
        super.onBlockHarvested(world, pos, state, player);
    }
}
