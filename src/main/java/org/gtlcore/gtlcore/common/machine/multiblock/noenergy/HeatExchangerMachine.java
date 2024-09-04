package org.gtlcore.gtlcore.common.machine.multiblock.noenergy;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.mojang.datafixers.util.Pair;
import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.utils.MachineIO;
import org.jetbrains.annotations.NotNull;

public class HeatExchangerMachine extends WorkableMultiblockMachine {

    public HeatExchangerMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof HeatExchangerMachine hMachine) {
            if (FluidRecipeCapability.CAP.of(recipe.inputs.get(FluidRecipeCapability.CAP)
                    .get(1).getContent()).getStacks()[0].getFluid() == GTMaterials.Water.getFluid()) {
                return GTRecipeModifiers.accurateParallel(machine, recipe, Integer.MAX_VALUE, false).getFirst();
            }
            final Pair<GTRecipe, Integer> result = GTRecipeModifiers.accurateParallel(machine, GTRecipeBuilder.ofRaw()
                    .inputFluids(FluidRecipeCapability.CAP.of(recipe.inputs
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .outputFluids(FluidRecipeCapability.CAP.of(recipe.outputs
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .duration(200)
                    .buildRawRecipe(), Integer.MAX_VALUE, false);
            long count = result.getSecond() * recipe.data.getLong("eu");
            if (MachineIO.inputFluid(hMachine, GTMaterials.DistilledWater.getFluid(count / 160))) {
                GTRecipe recipe1 = result.getFirst();
                recipe1.data.putLong("count", count);
                return recipe1;
            }
        }
        return null;
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getRecipeLogic().getProgress() == 199) {
            if (getRecipeLogic().getLastRecipe() != null) {
                MachineIO.outputFluid(this, GTLMaterials.SupercriticalSteam
                        .getFluid(getRecipeLogic().getLastRecipe().data.getLong("count")));
            }
        }
        return value;
    }
}
