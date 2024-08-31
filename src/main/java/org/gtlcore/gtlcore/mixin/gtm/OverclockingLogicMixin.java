package org.gtlcore.gtlcore.mixin.gtm;

import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import it.unimi.dsi.fastutil.longs.LongIntPair;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.standardOverclockingLogic;

@Mixin(OverclockingLogic.class)
public abstract class OverclockingLogicMixin {

    /**
     * @author
     * @reason
     */
    @Nonnull
    @Overwrite(remap = false)
    public static @NotNull LongIntPair heatingCoilOverclockingLogic(long recipeEUt, long maximumVoltage, int recipeDuration,
                                                                    int maxOverclocks, int currentTemp, int recipeRequiredTemp) {
        int amountEUDiscount = Math.max(0, (currentTemp - recipeRequiredTemp) / 900);
        double reductionDuration = Math.max(0.5, (double) recipeRequiredTemp / currentTemp);
        // apply a multiplicative 95% energy multiplier for every 900k over recipe temperature
        recipeEUt *= Math.min(1, Math.pow(0.95, amountEUDiscount));

        return standardOverclockingLogic(recipeEUt, maximumVoltage, (int) (reductionDuration * recipeDuration),
                maxOverclocks, 4, 4);
    }
}
