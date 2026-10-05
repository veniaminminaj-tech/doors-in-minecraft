package com.example.holychurch;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.MerchantOffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.village.VillagerTrades;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import java.util.List;

public final class HolyEvents {
    private HolyEvents() {}

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.world.isRemote) return;
        PlayerEntity p = event.player;
        if (p.getHeldItemMainhand().getItem() == HolyChurchMod.BIBLE.get() || p.getHeldItemOffhand().getItem() == HolyChurchMod.BIBLE.get()) {
            if (p.ticksExisted % 40 == 0) p.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 50, 0, false, false));
        }
        if (p.ticksExisted % 10 == 0 && (p.getHeldItemMainhand().getItem() == HolyChurchMod.CRUCIFIX.get() || p.getHeldItemOffhand().getItem() == HolyChurchMod.CRUCIFIX.get())) {
            AxisAlignedBB box = p.getBoundingBox().grow(2.5D);
            for (MonsterEntity mob : p.world.getEntitiesWithinAABB(MonsterEntity.class, box)) {
                mob.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 1, false, true));
            }
        }
    }

    @SubscribeEvent
    public static void livingAttack(LivingAttackEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity p = (PlayerEntity) event.getEntityLiving();
        if ((p.getHeldItemMainhand().getItem() == HolyChurchMod.CRUCIFIX.get() || p.getHeldItemOffhand().getItem() == HolyChurchMod.CRUCIFIX.get())
                && event.getSource().getTrueSource() instanceof MonsterEntity) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void lockedChestBreak(BlockEvent.BreakEvent event) {
        if (event.getState().getBlock() != ModBlocks.LOCKED_CHEST.get()) return;
        PlayerEntity p = event.getPlayer();
        if (p.isCreative() || p.getHeldItemMainhand().getItem() == HolyChurchMod.MASTER_KEY.get()) return;
        event.setCanceled(true);
        p.sendStatusMessage(new net.minecraft.util.text.StringTextComponent("§cLocked. Use the Master Key."), true);
    }

    @SubscribeEvent
    public static void priestTrades(VillagerTradesEvent event) {
        if (event.getType() != ModVillagers.PRIEST.get()) return;
        event.getTrades().get(1).add(new SimpleTrade(new ItemStack(Items.EMERALD, 8), new ItemStack(HolyChurchMod.BIBLE.get()), 8, 1, 0.05F));
        event.getTrades().get(1).add(new SimpleTrade(new ItemStack(Items.EMERALD, 6), new ItemStack(HolyChurchMod.HOLY_WATER.get()), 12, 2, 0.05F));
        event.getTrades().get(2).add(new SimpleTrade(new ItemStack(Items.EMERALD, 16), new ItemStack(HolyChurchMod.MASTER_KEY.get()), 4, 10, 0.05F));
    }

    private static final class SimpleTrade implements VillagerTrades.ITrade {
        private final ItemStack price; private final ItemStack result; private final int maxUses, xp; private final float multiplier;
        SimpleTrade(ItemStack price, ItemStack result, int maxUses, int xp, float multiplier) { this.price=price; this.result=result; this.maxUses=maxUses; this.xp=xp; this.multiplier=multiplier; }
        @Override public MerchantOffer getOffer(net.minecraft.entity.merchant.villager.AbstractVillagerEntity villager, java.util.Random random) {
            return new MerchantOffer(price.copy(), result.copy(), maxUses, xp, multiplier);
        }
    }
}
