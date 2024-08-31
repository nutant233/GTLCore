package org.gtlcore.gtlcore.mixin.gtm;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.FluidPipeProperties;
import com.gregtechceu.gtceu.api.pipenet.IMaterialPipeType;
import com.gregtechceu.gtceu.client.model.PipeModel;
import com.gregtechceu.gtceu.common.data.GTElements;
import com.gregtechceu.gtceu.common.pipelike.fluidpipe.FluidPipeType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidPipeType.class)
public abstract class FluidPipeTypeMixin implements IMaterialPipeType<FluidPipeProperties> {

    @Shadow @Final public float thickness;

    @Shadow @Final public String name;

    @Inject(method = "createPipeModel", at = @At("HEAD"), remap = false, cancellable = true)
    public void createPipeModel(Material material, CallbackInfoReturnable<PipeModel> cir) {
        if (material.getElement() == GTElements.get("spacetime")) {
            cir.setReturnValue(new PipeModel(thickness, () -> GTCEu.id("block/material_sets/spacetime/pipe_side"),
                    () -> GTCEu.id("block/material_sets/spacetime/pipe_%s_in".formatted(name)), null, null));
        }
    }
}
