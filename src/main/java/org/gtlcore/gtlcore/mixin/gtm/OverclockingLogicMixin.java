package org.gtlcore.gtlcore.mixin.gtm;

import org.gtlcore.gtlcore.utils.NumberUtils;

import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.*;

@Mixin(OverclockingLogic.class)
public class OverclockingLogicMixin {

    @Inject(method = "heatingCoilOC", at = @At("HEAD"), remap = false, cancellable = true)
    private static void heatingCoilOC(OCParams params, OCResult result, long maxVoltage, int providedTemp, int requiredTemp, CallbackInfo ci) {
        double duration = params.getDuration() * Math.max(0.5, (double) requiredTemp / providedTemp);
        double eut = params.getEut();
        int ocAmount = params.getOcAmount();
        double parallel = 1;
        int parallelIterAmount = 0;
        boolean shouldParallel = false;
        int ocLevel = 0;

        while (ocAmount-- > 0) {
            double potentialEUt = eut * STD_VOLTAGE_FACTOR;
            if (potentialEUt > maxVoltage) break;
            eut = potentialEUt;
            if (shouldParallel) {
                parallel *= PERFECT_DURATION_FACTOR_INV;
                parallelIterAmount++;
            } else {
                double potentialDuration = duration * PERFECT_DURATION_FACTOR;
                if (potentialDuration < 1) {
                    parallel *= PERFECT_DURATION_FACTOR_INV;
                    parallelIterAmount++;
                    shouldParallel = true;
                } else {
                    duration = potentialDuration;
                }
            }
            ocLevel++;
        }

        result.init((long) (eut / Math.pow(STD_VOLTAGE_FACTOR, parallelIterAmount)), (int) duration, (int) parallel,
                (long) eut, ocLevel);
        ci.cancel();
    }

    @Inject(method = "getOverclockForTier", at = @At("HEAD"), remap = false, cancellable = true)
    protected void getOverclockForTier(long voltage, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NumberUtils.getFakeVoltageTier(voltage));
    }
}
