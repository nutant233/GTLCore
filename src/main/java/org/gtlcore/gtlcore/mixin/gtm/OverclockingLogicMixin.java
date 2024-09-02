package org.gtlcore.gtlcore.mixin.gtm;

import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.gregtechceu.gtceu.api.recipe.OverclockingLogic.subTickParallelOC;

@Mixin(OverclockingLogic.class)
public abstract class OverclockingLogicMixin {

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void heatingCoilOC(@NotNull OCParams params, @NotNull OCResult result, long maxVoltage,
                                     int providedTemp, int requiredTemp) {
        subTickParallelOC(
                params,
                result,
                maxVoltage,
                4,
                4);
    }
}
