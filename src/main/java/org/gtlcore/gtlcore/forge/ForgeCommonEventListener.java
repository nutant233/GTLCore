package org.gtlcore.gtlcore.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.gtlcore.gtlcore.GTLCore;

import java.util.Objects;


@Mod.EventBusSubscriber(modid = GTLCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeCommonEventListener {

    @SubscribeEvent
    public static void remapIds(MissingMappingsEvent event) {
        event.getMappings(Registries.BLOCK, "kubejs").forEach(mapping -> {
            if (mapping.getKey().equals(new ResourceLocation("kubejs:stellar_containment_casing"))) {
                mapping.remap(ForgeRegistries.BLOCKS
                        .getValue(new ResourceLocation("gtceu:stellar_containment_casing")));
            }
        });
        event.getMappings(Registries.ITEM, "kubejs").forEach(mapping -> {
            if (mapping.getKey().equals(new ResourceLocation("kubejs:stellar_containment_casing"))) {
                mapping.remap(ForgeRegistries.ITEMS
                        .getValue(new ResourceLocation("gtceu:stellar_containment_casing")));
            }
        });
    }

    @SubscribeEvent
    public static void onPortalSpawnEvent(BlockEvent.PortalSpawnEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity().level().getBlockState(event.getPos()).getBlock() == Blocks.END_PORTAL_FRAME &&
                event.getEntity().getItemInHand(event.getHand()).getItem() == Items.ENDER_EYE) {
            if (event.getEntity() instanceof ServerPlayer player &&
                    Objects.equals(player.getOffhandItem().kjs$getId(), "kubejs:end_data")) {
                player.getOffhandItem().setCount(player.getOffhandItem().getCount() - 1);
                return;
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFarmlandTrampleEvent(BlockEvent.FarmlandTrampleEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getEntity() instanceof Player && event.getTarget() instanceof Villager) {
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onEnderTeleportEvent(EntityTeleportEvent.EnderEntity event) {
        if (event.getEntity() instanceof EnderMan || event.getEntity() instanceof Shulker) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END &&
                event.side == LogicalSide.CLIENT && event.player.xxa == 0 && event.player.zza == 0) {
            event.player.setDeltaMovement(event.player.getDeltaMovement().multiply(0.5, 1, 0.5));
        }
    }
}
