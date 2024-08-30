package org.gtlcore.gtlcore.utils;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

public class MachineIO {
    public static boolean inputItem(WorkableMultiblockMachine machine, ItemStack item) {
        return GTRecipeBuilder.ofRaw().inputItems(item).buildRawRecipe()
                .handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
    }

    public static boolean outputItem(WorkableMultiblockMachine machine, ItemStack item) {
        if (!item.isEmpty()) {
            GTRecipe recipe = GTRecipeBuilder.ofRaw().outputItems(item).buildRawRecipe();
            if (recipe.matchRecipe(machine).isSuccess()) {
                return recipe.handleRecipeIO(IO.OUT, machine, machine.recipeLogic.getChanceCaches());
            }
        }
        return false;
    }

    public static boolean notConsumableItem(WorkableMultiblockMachine machine, ItemStack item) {
        return GTRecipeBuilder.ofRaw().inputItems(item).buildRawRecipe().matchRecipe(machine).isSuccess();
    }

    public static boolean notConsumableCircuit(WorkableMultiblockMachine machine, int configuration) {
        return GTRecipeBuilder.ofRaw().inputItems(IntCircuitIngredient.circuitInput(configuration)).buildRawRecipe()
                .matchRecipe(machine).isSuccess();
    }

    public static boolean inputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        return GTRecipeBuilder.ofRaw().inputFluids(fluid).buildRawRecipe()
                .handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
    }

    public static boolean outputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        if (!fluid.isEmpty()) {
            GTRecipe recipe = GTRecipeBuilder.ofRaw().outputFluids(fluid).buildRawRecipe();
            if (recipe.matchRecipe(machine).isSuccess()) {
                return recipe.handleRecipeIO(IO.OUT, machine, machine.recipeLogic.getChanceCaches());
            }
        }
        return false;
    }

    public static boolean inputEU(WorkableMultiblockMachine machine, long eu) {
        return GTRecipeBuilder.ofRaw().inputEU(eu).buildRawRecipe()
                .handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
    }
}
