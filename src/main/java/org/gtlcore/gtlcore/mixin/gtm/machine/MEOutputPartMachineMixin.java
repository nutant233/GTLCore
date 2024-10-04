package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IInteractedMachine;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.common.machine.multiblock.part.DualHatchPartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.core.Direction;

import com.hepdd.gtmthings.common.block.machine.multiblock.part.appeng.MEOutputPartMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumSet;

@Mixin(MEOutputPartMachine.class)
public abstract class MEOutputPartMachineMixin extends DualHatchPartMachine
                                               implements IMachineLife, IInteractedMachine, IGridConnectedMachine {

    public MEOutputPartMachineMixin(IMachineBlockEntity holder, int tier, IO io, Object... args) {
        super(holder, tier, io, args);
    }

    @Unique
    @Persisted
    public boolean gTLCore$isAllFacing;

    @Inject(method = "onLoad", at = @At("HEAD"), remap = false)
    public void onLoad(CallbackInfo ci) {
        if (gTLCore$isAllFacing) {
            getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
        }
    }
}
