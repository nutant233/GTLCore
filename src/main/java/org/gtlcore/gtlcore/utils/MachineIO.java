package org.gtlcore.gtlcore.utils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class MachineIO {
    public static boolean inputItem(WorkableMultiblockMachine machine, ItemStack item) {
        GTRecipe recipe = new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).inputItems(item).buildRawRecipe();
        if (recipe.matchRecipe(machine).isSuccess()) {
            return recipe.handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
        }
        return false;
    }

    public static boolean outputItem(WorkableMultiblockMachine machine, ItemStack item) {
        if (!item.isEmpty()) {
            GTRecipe recipe = new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).outputItems(item).buildRawRecipe();
            if (recipe.matchRecipe(machine).isSuccess()) {
                return recipe.handleRecipeIO(IO.OUT, machine, machine.recipeLogic.getChanceCaches());
            }
        }
        return false;
    }

    public static boolean notConsumableItem(WorkableMultiblockMachine machine, ItemStack item) {
        return new GTRecipeBuilder(item.kjs$getIdLocation(), GTRecipeTypes.DUMMY_RECIPES).inputItems(item).buildRawRecipe().matchRecipe(machine).isSuccess();
    }

    public static boolean notConsumableCircuit(WorkableMultiblockMachine machine, int configuration) {
        return new GTRecipeBuilder(GTCEu.id(String.valueOf(configuration)), GTRecipeTypes.DUMMY_RECIPES).inputItems(IntCircuitIngredient.circuitInput(configuration)).buildRawRecipe()
                .matchRecipe(machine).isSuccess();
    }

    public static boolean inputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        GTRecipe recipe =  new GTRecipeBuilder(Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid.getFluid())), GTRecipeTypes.DUMMY_RECIPES).inputFluids(fluid).buildRawRecipe();
        if (recipe.matchRecipe(machine).isSuccess()) {
            return recipe.handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
        }
        return false;
    }

    public static boolean outputFluid(WorkableMultiblockMachine machine, FluidStack fluid) {
        if (!fluid.isEmpty()) {
            GTRecipe recipe = new GTRecipeBuilder(Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid.getFluid())), GTRecipeTypes.DUMMY_RECIPES).outputFluids(fluid).buildRawRecipe();
            if (recipe.matchRecipe(machine).isSuccess()) {
                return recipe.handleRecipeIO(IO.OUT, machine, machine.recipeLogic.getChanceCaches());
            }
        }
        return false;
    }

    public static boolean inputEU(WorkableMultiblockMachine machine, long eu) {
        GTRecipe recipe = new GTRecipeBuilder(GTCEu.id(String.valueOf(eu)), GTRecipeTypes.DUMMY_RECIPES).inputEU(eu).buildRawRecipe();
        if (recipe.matchRecipe(machine).isSuccess()) {
            return recipe.handleRecipeIO(IO.IN, machine, machine.recipeLogic.getChanceCaches());
        }
        return false;
    }
}
