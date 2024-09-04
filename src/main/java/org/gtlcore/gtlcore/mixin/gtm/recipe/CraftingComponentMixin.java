package org.gtlcore.gtlcore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import com.lowdragmc.lowdraglib.Platform;
import org.gtlcore.gtlcore.data.recipe.CraftingComponentAddition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CraftingComponent.class, remap = false)
public class CraftingComponentMixin {

    @Inject(method = "initializeComponents()V", at = @At(value = "TAIL"))
    private static void initializeAddition(CallbackInfo ci) {
        if (!Platform.isDevEnv()) {
            CraftingComponentAddition.init();
        }
    }
}
