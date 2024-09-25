package org.gtlcore.gtlcore.mixin.gtmt;

import com.hepdd.gtmthings.common.block.machine.multiblock.part.HugeBusPartMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HugeBusPartMachine.class)
public class HugeBusPartMachineMixin {

    @Inject(method = "getInventorySize", at = @At("RETURN"), remap = false, cancellable = true)
    protected void getInventorySize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() - 1);
    }
}
