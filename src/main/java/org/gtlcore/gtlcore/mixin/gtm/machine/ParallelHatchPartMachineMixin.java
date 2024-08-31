package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParallelHatchPartMachine.class)
public abstract class ParallelHatchPartMachineMixin extends TieredPartMachine implements IFancyUIMachine, IParallelHatch {

    @Mutable
    @Shadow(remap = false)
    @Final
    private int maxParallel;

    public ParallelHatchPartMachineMixin(IMachineBlockEntity holder, int tier) {
        super(holder, tier);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    public void ParallelHatchPartMachine(IMachineBlockEntity holder, int tier, CallbackInfo ci) {
        this.maxParallel = (int) Math.pow(4, tier - 2);
    }
}
