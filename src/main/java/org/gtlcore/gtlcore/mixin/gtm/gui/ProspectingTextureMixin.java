package org.gtlcore.gtlcore.mixin.gtm.gui;

import org.gtlcore.gtlcore.client.gui.IProspectingTextureMixin;

import com.gregtechceu.gtceu.api.gui.texture.ProspectingTexture;

import com.lowdragmc.lowdraglib.LDLib;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author EasterFG on 2024/9/16
 */
@Mixin(ProspectingTexture.class)
public abstract class ProspectingTextureMixin extends AbstractTexture implements IProspectingTextureMixin {

    @Shadow(remap = false)
    @Final
    private int playerXGui;

    @Shadow(remap = false)
    @Final
    private int playerYGui;

    @Unique
    @Override
    public void gTLCore$addWayPoint(Player player, double mouseX, double mouseY) {
        if (!LDLib.isModLoaded("ftbchunks")) {
            return;
        }
        double x, z;
        if (playerXGui % 16 > 7 || playerXGui % 16 == 0) {
            x = mouseX - (playerXGui - 1);
        } else {
            x = mouseX - playerXGui;
        }
        if (playerYGui % 16 > 7 || playerYGui % 16 == 0) {
            z = mouseY - (playerYGui - 1);
        } else {
            z = mouseY - playerYGui;
        }
        BlockPos pos = new BlockPos((int) (player.position().x + x), 0, (int) (player.position().z + z));

        var mgr = FTBChunksAPI.clientApi().getWaypointManager(player.level().dimension()).get();
        AtomicInteger index = new AtomicInteger();
        mgr.getAllWaypoints().forEach(waypoint -> {
            if (waypoint.getName().startsWith("prospect_point")) {
                int i = Integer.parseInt(waypoint.getName().replace("prospect_point", ""));
                index.set(Math.max(i, index.get()));
            }
        });
        mgr.addWaypointAt(pos, "prospect_point" + index.incrementAndGet()).setColor(200);
    }
}
