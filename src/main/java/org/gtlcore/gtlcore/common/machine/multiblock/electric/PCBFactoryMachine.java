package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PCBFactoryMachine extends StorageMachine {

    public PCBFactoryMachine(IMachineBlockEntity holder) {
        super(holder, 64);
    }

    private double reductionEUt = 1, reductionDuration = 1;

    private void getPCBReduction() {
        ItemStack itemStack = getMachineStorageItem();
        String item = itemStack.kjs$getId();
        if (Objects.equals(item, "gtceu:vibranium_nanoswarm")) {
            reductionDuration = (double) (100 - itemStack.getCount()) / 100;
            reductionEUt = 0.25;
        } else if (Objects.equals(item, "gtceu:gold_nanoswarm")) {
            reductionDuration = (100 - (itemStack.getCount() * 0.5)) / 100;
        }
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                          @NotNull OCResult result) {
        if (machine instanceof PCBFactoryMachine pcbFactoryMachine) {
            pcbFactoryMachine.getPCBReduction();
            GTRecipe recipe1 = GTLRecipeModifiers.reduction(pcbFactoryMachine, recipe, pcbFactoryMachine.reductionEUt, pcbFactoryMachine.reductionDuration);
            if (recipe1 != null) {
                return RecipeHelper.applyOverclock(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK, GTRecipeModifiers.hatchParallel(machine, recipe1, false, params, result), pcbFactoryMachine.getOverclockVoltage(), params, result);
            }
        }
        return null;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        super.addDisplayText(textList);
        if (this.isFormed()) {
            if (getOffsetTimer() % 10 == 0) {
                getPCBReduction();
            }
            textList.add(Component.translatable("gtceu.machine.eut_multiplier.tooltip", this.reductionEUt));
            textList.add(Component.translatable("gtceu.machine.duration_multiplier.tooltip", this.reductionDuration));
        }
    }
}
