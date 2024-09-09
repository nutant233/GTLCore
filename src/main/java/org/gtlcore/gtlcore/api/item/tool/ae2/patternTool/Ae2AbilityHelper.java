package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import net.minecraft.world.item.ItemStack;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class Ae2AbilityHelper {
   final GTRecipe recipe;

    protected Ae2AbilityHelper(GTRecipe recipe) {
        this.recipe = recipe;
    }

    public List<ItemStack> getInputItemStacks(){
        return getInputItemStacksFromRecipe(recipe);
    }
    public List<FluidStack> getInputFluidStacks(){
        return getInputFluidStacksFromRecipe(recipe);
    }

    public List<ItemStack> getOutputItemStacks(){
        return getOutputItemStacksFromRecipe(recipe);
    }
    public List<FluidStack> getOutputFluidStacks(){
        return getOutputFluidStacksFromRecipe(recipe);
    }

    public static List<ItemStack> getInputItemStacksFromRecipe(GTRecipe recipe) {
        if (recipe == null) {
            return Collections.emptyList();
        }
        return recipe.getInputContents(ItemRecipeCapability.CAP).stream()
                .map(getContentItemStackFunction())
                .toList();
    }
    public static List<FluidStack> getInputFluidStacksFromRecipe(GTRecipe recipe) {
        if (recipe == null) {
            return Collections.emptyList();
        }
        return recipe.getInputContents(FluidRecipeCapability.CAP).stream()
                .map(getContentFluidStackFunction())
                .toList();
    }
    public static List<ItemStack> getOutputItemStacksFromRecipe(GTRecipe recipe) {
        if (recipe == null) {
            return Collections.emptyList();
        }
        return recipe.getOutputContents(ItemRecipeCapability.CAP).stream()
                .map(getContentItemStackFunction())
                .toList();
    }
    public static List<FluidStack> getOutputFluidStacksFromRecipe(GTRecipe recipe) {
        if (recipe == null) {
            return Collections.emptyList();
        }
        return recipe.getOutputContents(FluidRecipeCapability.CAP).stream()
                .map(getContentFluidStackFunction())
                .toList();
    }


    public static String getItemTranslatedName(ItemStack itemStack) {
        return itemStack.getItem().getName(itemStack).getString();
    }
    public static String getFluidTranslatedName(FluidStack fluidStack) {
        return fluidStack.getDisplayName().getString();
    }


    private static @NotNull Function<Content, FluidStack> getContentFluidStackFunction() {
        return content -> {
            if (content == null || content.getContent() == null) {
                return FluidStack.empty();
            }
            FluidStack[] stacks = FluidRecipeCapability.CAP.of(content.getContent()).getStacks();
            return (stacks != null && stacks.length > 0) ? stacks[0] : FluidStack.empty();
        };
    }
    private static @NotNull Function<Content, ItemStack> getContentItemStackFunction() {
        return content -> {
            if (content == null || content.getContent() == null) {
                return ItemStack.EMPTY;
            }
            ItemStack stack = ItemRecipeCapability.CAP.of(content.getContent()).kjs$getFirst();
            return (stack != null) ? stack : ItemStack.EMPTY;
        };
    }
}
