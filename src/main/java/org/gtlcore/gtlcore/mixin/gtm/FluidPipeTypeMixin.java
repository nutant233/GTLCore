package org.gtlcore.gtlcore.mixin.gtm;

import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.client.model.PipeModel;
import com.gregtechceu.gtceu.common.pipelike.fluidpipe.FluidPipeType;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidPipeType.class)
public class FluidPipeTypeMixin {

    @Shadow(remap = false)
    @Final
    public float thickness;

    @Shadow(remap = false)
    @Final
    public String name;

    @Inject(method = "createPipeModel", at = @At("HEAD"), remap = false, cancellable = true)
    public void createPipeModel(Material material, CallbackInfoReturnable<PipeModel> cir) {
        if (material == GTLMaterials.SpaceTime) {
            cir.setReturnValue(new PipeModel(thickness, () -> GTCEu.id("block/material_sets/spacetime/pipe_side"),
                    () -> GTCEu.id("block/material_sets/spacetime/pipe_%s_in".formatted(name)), null, null));
        }
    }
}
