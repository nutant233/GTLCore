package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;
import java.util.Set;

import static net.minecraft.network.chat.Component.literal;

@Mixin(MetaMachine.class)
public abstract class MetaMachineMixin {

    @Shadow
    public static @Nullable MetaMachine getMachine(BlockGetter level, BlockPos pos) {
        return null;
    }

    @Shadow
    public abstract BlockPos getPos();

    @Shadow
    public abstract @Nullable Level getLevel();

    @Shadow
    public abstract boolean isRemote();

    @Unique
    @Persisted
    protected boolean gTLCore$isAllFacing = false;

    @Inject(method = "onToolClick", at = @At("TAIL"), remap = false, cancellable = true)
    public void onToolClick(Set<@NotNull GTToolType> toolType, ItemStack itemStack, UseOnContext context, CallbackInfoReturnable<Pair<GTToolType, InteractionResult>> cir) {
        MetaMachine machine = getMachine(getLevel(), context.getClickedPos());
        var playerIn = context.getPlayer();
        var hand = context.getHand();
        if (machine instanceof IGridConnectedMachine) {
            if (toolType.contains(GTToolType.WIRE_CUTTER)) {
                cir.setReturnValue(Pair.of(GTToolType.SCREWDRIVER, gTLCore$onWireCutterClick(playerIn, hand, (IGridConnectedMachine) machine)));
            }
        }
    }

    @Inject(method = "onRotated", at = @At("TAIL"), remap = false)
    public void onRotated(Direction oldFacing, Direction newFacing, CallbackInfo ci) {
        gTLCore$isAllFacing = false;
    }

    @Inject(method = "shouldRenderGrid", at = @At("HEAD"), remap = false, cancellable = true)
    public void shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, Set<GTToolType> toolTypes, CallbackInfoReturnable<Boolean> cir) {
        MetaMachine machine = getMachine(getLevel(), getPos());
        if (machine instanceof IGridConnectedMachine) {
            if (toolTypes.contains(GTToolType.WIRE_CUTTER)) cir.setReturnValue(true);
        }
    }

    @Unique
    protected <T extends IGridConnectedMachine> InteractionResult gTLCore$onWireCutterClick(Player playerIn, InteractionHand hand, T machine) {
        playerIn.swing(hand);
        if (gTLCore$isAllFacing) {
            machine.getMainNode().setExposedOnSides(EnumSet.of(((MetaMachine) machine).getFrontFacing()));
            if (!isRemote()) {
                playerIn.displayClientMessage(literal("ME仓只允许正面连接"), true);
            }
            gTLCore$isAllFacing = false;
        } else {
            machine.getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
            if (!isRemote()) {
                playerIn.displayClientMessage(literal("ME仓允许任意面连接"), true);
            }
            gTLCore$isAllFacing = true;
        }
        return InteractionResult.CONSUME;
    }
}
