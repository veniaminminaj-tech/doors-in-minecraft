package com.example.holychurch;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CrucifixItem extends Item {
    private static final int USE_TICKS = 30;
    private static final double SAFE_RADIUS = 2.5D;
    private static final int BARRIER_TICKS = 20 * 60;
    private static final int COOLDOWN_TICKS = 20 * 60 * 5;

    public CrucifixItem(Properties properties) { super(properties); }
    @Override public UseAction getUseAction(ItemStack stack) { return UseAction.BLOCK; }
    @Override public int getUseDuration(ItemStack stack) { return USE_TICKS; }
    @Override public boolean hasEffect(ItemStack stack) { return true; }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.getCooldownTracker().hasCooldown(this)) return ActionResult.resultFail(stack);
            player.setActiveHand(hand);
        }
        return ActionResult.resultConsume(stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity entity, int count) {
        if (!(entity instanceof PlayerEntity) || !(entity.world instanceof ServerWorld)) return;
        PlayerEntity player = (PlayerEntity) entity;
        if (count > 0 && count < USE_TICKS) return;
        ServerWorld world = (ServerWorld) entity.world;
        AxisAlignedBB box = player.getBoundingBox().grow(SAFE_RADIUS);
        for (LivingEntity target : world.getEntitiesWithinAABB(LivingEntity.class, box, e -> e != player)) {
            if (target instanceof MonsterEntity) target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 4, false, true));
        }
        player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, BARRIER_TICKS, 1, false, true));
        player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100, 1, false, true));
        player.sendStatusMessage(new StringTextComponent("The Crucifix protects you."), true);
        player.getCooldownTracker().setCooldown(this, COOLDOWN_TICKS);
        player.stopActiveHand();
    }
}
