package org.gtlcore.gtlcore.forge;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.machine.IVacuumMachine;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.config.GTLConfigHolder;
import org.gtlcore.gtlcore.utils.GTLExplosion;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.common.data.GTItems;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = GTLCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level == null) return;
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (item == Items.ENDER_EYE && level.getBlockState(pos).getBlock() == Blocks.END_PORTAL_FRAME) {
            if (Objects.equals(item.kjs$getId(), "kubejs:end_data")) {
                player.setItemInHand(hand, itemStack.copyWithCount(itemStack.getCount() - 1));
                return;
            }
            event.setCanceled(true);
            return;
        }
        if (item == GTLItems.RAW_VACUUM_TUBE.get() && player.isShiftKeyDown() && MetaMachine.getMachine(level, pos) instanceof IVacuumMachine vacuumMachine && vacuumMachine.getVacuumTier() > 0) {
            player.setItemInHand(hand, itemStack.copyWithCount(itemStack.getCount() - 1));
            level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 1, pos.getZ(), GTItems.VACUUM_TUBE.asStack()));
            return;
        }
        if (item == GTItems.QUANTUM_STAR.get() && level.getBlockState(pos).getBlock() == GTLBlocks.NAQUADRIA_CHARGE.get()) {
            GTLExplosion nukeExplosion = new GTLExplosion(pos, level, 80);
            nukeExplosion.finalizeExplosion(true);
            return;
        }
        if (item == GTItems.GRAVI_STAR.get() && level.getBlockState(pos).getBlock() == GTLBlocks.LEPTONIC_CHARGE.get()) {
            GTLExplosion nukeExplosion = new GTLExplosion(pos, level, 200);
            nukeExplosion.finalizeExplosion(true);
            return;
        }
        if (item == GTLItems.UNSTABLE_STAR.get() && level.getBlockState(pos).getBlock() == GTLBlocks.QUANTUM_CHROMODYNAMIC_CHARGE.get()) {
            GTLExplosion nukeExplosion = new GTLExplosion(pos, level, 1000);
            nukeExplosion.finalizeExplosion(false);
        }
    }

    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEnderTeleportEvent(EntityTeleportEvent.EnderEntity event) {
        if (event.getEntity() instanceof EnderMan || event.getEntity() instanceof Shulker) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (GTLConfigHolder.INSTANCE.disableDrift && event.phase == TickEvent.Phase.END &&
                event.side == LogicalSide.CLIENT && event.player.xxa == 0 && event.player.zza == 0) {
            event.player.setDeltaMovement(event.player.getDeltaMovement().multiply(0.5, 1, 0.5));
        }
    }
}
