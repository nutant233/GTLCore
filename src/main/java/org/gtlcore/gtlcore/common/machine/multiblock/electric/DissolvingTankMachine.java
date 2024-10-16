package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class DissolvingTankMachine extends WorkableElectricMultiblockMachine {

    public DissolvingTankMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    public static GTRecipe dissolvingTankOverclock(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                                   @NotNull OCResult result) {
        if (machine instanceof DissolvingTankMachine dissolvingTankMachine) {
            List<Content> fluidList = recipe.inputs.getOrDefault(FluidRecipeCapability.CAP, null);
            FluidStack fluidStack1 = FluidRecipeCapability.CAP.of(fluidList.get(0).getContent()).getStacks()[0];
            FluidStack fluidStack2 = FluidRecipeCapability.CAP.of(fluidList.get(1).getContent()).getStacks()[0];
            double a = 0, b = 0;
            for (IRecipeHandler<?> handler : Objects.requireNonNullElseGet(dissolvingTankMachine.getCapabilitiesProxy().get(IO.IN, FluidRecipeCapability.CAP), Collections::<IRecipeHandler<?>>emptyList)) {
                for (Object contents : handler.getContents()) {
                    if (contents instanceof FluidStack fluidStack) {
                        if (fluidStack.getFluid() == fluidStack1.getFluid()) {
                            a += fluidStack.getAmount();
                        }
                        if (fluidStack.getFluid() == fluidStack2.getFluid()) {
                            b += fluidStack.getAmount();
                        }

                    }
                }
            }
            if (b == 0) return null;
            GTRecipe recipe1 = GTRecipeModifiers.hatchParallel(machine, recipe, false, params, result);
            if (recipe1 != null) {
                GTRecipe recipe2 = RecipeHelper.applyOverclock(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK, recipe1, dissolvingTankMachine.getOverclockVoltage(), params, result);
                if (a / b != ((double) fluidStack1.getAmount()) / fluidStack2.getAmount()) {
                    recipe2.outputs.clear();
                }
                return recipe2;
            }
        }
        return null;
    }
}
