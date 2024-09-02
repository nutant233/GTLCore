package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.IEnvironmentalHazardEmitter;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleGeneratorMachine.class)
public abstract class SimpleGeneratorMachineMinxin extends WorkableTieredMachine implements IFancyUIMachine, IEnvironmentalHazardEmitter {

    public SimpleGeneratorMachineMinxin(IMachineBlockEntity holder, int tier, Int2LongFunction tankScalingFunction, Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    @Inject(method = "getMaxInputOutputAmperage", at = @At(value = "HEAD"), remap = false, cancellable = true)
    protected void getMaxInputOutputAmperage(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue((long) gTLCore$getAmperage(this));
    }

    @Inject(method = "recipeModifier", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private static void recipeModifier(MetaMachine machine, GTRecipe recipe, OCParams params, OCResult result, CallbackInfoReturnable<GTRecipe> cir) {
        GTRecipe recipe1 = cir.getReturnValue();
        if (recipe1 == null) return;
        recipe1 = recipe.copy(ContentModifier.multiplier(gTLCore$getAmperage(machine)), false);
        cir.setReturnValue(recipe1);
    }

    @Unique
    private static int gTLCore$getAmperage(MetaMachine machine) {
        return (int) Math.max(1, Math.pow(2, 5 - ((SimpleGeneratorMachineMinxin) machine).getTier()));
    }
}
