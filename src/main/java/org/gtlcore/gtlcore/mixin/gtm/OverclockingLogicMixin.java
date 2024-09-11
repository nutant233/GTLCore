package org.gtlcore.gtlcore.mixin.gtm;

import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.*;

@Mixin(OverclockingLogic.class)
public class OverclockingLogicMixin {

    /**
     * @author mod_author
     * @reason 原版的高炉太慢
     */
    @Overwrite(remap = false)
    public static void heatingCoilOC(@NotNull OCParams params, @NotNull OCResult result, long maxVoltage,
                                     int providedTemp, int requiredTemp) {
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
    }
}
