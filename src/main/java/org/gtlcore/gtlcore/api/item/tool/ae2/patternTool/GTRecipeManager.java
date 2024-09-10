package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GTRecipeManager {
    private List<GTRecipe> recipes = new ArrayList<GTRecipe>();
    public GTRecipeManager() {
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        RecipeManager recipeManager = currentServer.getRecipeManager();
        recipeManager.getRecipes().forEach(recipe -> {
            if(recipe instanceof GTRecipe gtRecipe) {
                recipes.add(gtRecipe);
            }
        });
    }

    public void filterRecipesByType(GTRecipeType recipeType) {
        recipes= recipes.stream().filter(gtRecipe ->
            gtRecipe.getType().equals(recipeType)
        ).toList();
    }

    public void filterRecipesByType(String recipeTypeString) {
        GTRecipeType recipeType = GTRecipeTypes.get(recipeTypeString);
        recipes = recipes.stream().filter(gtRecipe ->
                gtRecipe.getType().equals(recipeType)
        ).toList();
    }

    public void filterRecipesByCircuit(int circuit) {
        List<GTRecipe> filedRecipes = new ArrayList<GTRecipe>();
        for (GTRecipe recipe : recipes) {
            int circuitConfiguration =0;
            for (Content inputContent : recipe.getInputContents(ItemRecipeCapability.CAP)) {
                ItemStack itemStack = ItemRecipeCapability.CAP.of(inputContent.getContent()).kjs$getFirst();
                if (itemStack.getItem().equals(GTItems.INTEGRATED_CIRCUIT.asItem())) {
                    circuitConfiguration = IntCircuitBehaviour.getCircuitConfiguration(itemStack);
                }
            }
            if (circuitConfiguration == circuit) {
                filedRecipes.add(recipe);
            }
        }
        recipes= filedRecipes;
    }
}
