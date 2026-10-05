package com.example.holychurch;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class HolyWaterItem extends Item {
    public HolyWaterItem(Properties properties) { super(properties); }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (!world.isRemote) {
                AxisAlignedBB box = player.getBoundingBox().grow(2.5D);
                for (LivingEntity target : world.getEntitiesWithinAABB(LivingEntity.class, box, e -> e != player)) {
                    if (target instanceof MonsterEntity) {
                        target.attackEntityFrom(net.minecraft.util.DamageSource.MAGIC, 10.0F);
                        target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 100, 1));
                    }
                }
                player.sendStatusMessage(new StringTextComponent("§bHoly water purifies the area."), true);
                if (!player.isCreative()) stack.shrink(1);
            }
            return ActionResult.resultSuccess(stack);
        }
        player.setActiveHand(hand);
        return ActionResult.resultConsume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) { return 32; }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) entity;
            for (EffectInstance effect : new java.util.ArrayList<>(p.getActivePotionEffects())) p.removePotionEffect(effect.getPotion());
            p.addPotionEffect(new EffectInstance(Effects.REGENERATION, 6000, 1));
            p.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 6000, 1));
            p.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 6000));
            p.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 6000));
            p.heal(20.0F);
            if (!world.isRemote) p.sendStatusMessage(new StringTextComponent("§bYou have been purified."), true);
        }
        if (stack.isEmpty()) return new ItemStack(net.minecraft.item.Items.GLASS_BOTTLE);
        if (entity instanceof PlayerEntity && !((PlayerEntity)entity).isCreative()) stack.shrink(1);
        return stack;
    }
}
