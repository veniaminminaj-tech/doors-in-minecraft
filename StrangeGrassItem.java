package com.example.holychurch;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class StrangeGrassItem extends Item {
    public StrangeGrassItem(Properties properties) { super(properties); }
    @Override public ActionResult<ItemStack> onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (!world.isRemote && entity instanceof PlayerEntity) {
            PlayerEntity p=(PlayerEntity)entity;
            p.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 600, 0));
            if (world.rand.nextBoolean()) p.addPotionEffect(new EffectInstance(Effects.NAUSEA, 160, 0));
            if (world.rand.nextInt(5)==0) p.addPotionEffect(new EffectInstance(Effects.GLOWING, 200, 0));
            p.sendStatusMessage(new net.minecraft.util.text.StringTextComponent("§5The strange grass tastes... wrong."), true);
        }
        return super.onItemUseFinish(stack, world, entity);
    }
}
