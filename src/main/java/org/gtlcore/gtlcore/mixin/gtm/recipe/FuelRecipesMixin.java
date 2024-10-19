package org.gtlcore.gtlcore.mixin.gtm.recipe;

import org.gtlcore.gtlcore.data.recipe.Fuel;

import com.gregtechceu.gtceu.data.recipe.misc.FuelRecipes;

import net.minecraft.data.recipes.FinishedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(FuelRecipes.class)
public class FuelRecipesMixin {

    @Inject(method = "init", at = @At("HEAD"), remap = false, cancellable = true)
    private static void init(Consumer<FinishedRecipe> provider, CallbackInfo ci) {
        Fuel.init(provider);
        ci.cancel();
    }
}
