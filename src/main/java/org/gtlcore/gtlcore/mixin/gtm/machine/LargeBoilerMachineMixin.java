package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.LargeBoilerMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LargeBoilerMachine.class)
public class LargeBoilerMachineMixin {

    @Inject(method = "recipeModifier", at = @At("HEAD"), remap = false, cancellable = true)
    private static void recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result, CallbackInfoReturnable<GTRecipe> cir) {
        if (machine instanceof LargeBoilerMachine largeBoilerMachine) {
            GTRecipe recipe1 = recipe.copy();
            recipe1.duration = recipe.duration * 6400 / largeBoilerMachine.maxTemperature;
            if (largeBoilerMachine.getThrottle() < 100) {
                recipe1.duration = recipe1.duration * 100 / largeBoilerMachine.getThrottle();
                cir.setReturnValue(recipe1);
            }
            cir.setReturnValue(recipe1);
        }
    }
}
