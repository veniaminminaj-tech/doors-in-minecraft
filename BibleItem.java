package com.example.holychurch;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BibleItem extends Item {
    public BibleItem(Properties properties) { super(properties); }
    @Override public boolean hasEffect(ItemStack stack) { return true; }
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            player.sendStatusMessage(new StringTextComponent("Holy Bible: faith protects the faithful."), false);
        }
        return ActionResult.resultSuccess(stack);
    }
}
