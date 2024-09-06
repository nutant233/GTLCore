package org.gtlcore.gtlcore.common.machine.trait;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.mojang.datafixers.util.Pair;
import lombok.Getter;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.MultipleRecipesMachine;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MultipleRecipesLogic extends RecipeLogic {

    public MultipleRecipesLogic(MultipleRecipesMachine machine) {
        super(machine);
    }

    @Override
    public MultipleRecipesMachine getMachine() {
        return (MultipleRecipesMachine) super.getMachine();
    }

    @Override
    public void findAndHandleRecipe() {
        lastRecipe = null;
        var match = getRecipe();
        if (match != null) {
            var copied = match.copy(new ContentModifier(match.duration, 0));
            if (match.matchRecipe(this.machine).isSuccess() && copied.matchTickRecipe(this.machine).isSuccess()) {
                setupRecipe(match);
            }
        }
    }

    @Nullable
    private GTRecipe getRecipe() {
        GTRecipe recipe = GTRecipeBuilder.ofRaw().buildRawRecipe();
        recipe.outputs.put(ItemRecipeCapability.CAP, new ArrayList<>());
        if (!machine.hasProxies()) return null;
        long eu = 0;
        for (int i = 0; i < 64; i++) {
            GTRecipe match = machine.getRecipeType().getLookup().findRecipe(machine);
            if (match == null) break;
            GTRecipe input = GTRecipeBuilder.ofRaw().buildRawRecipe();
            Pair<GTRecipe, Integer> pair = GTRecipeModifiers.fastParallel(getMachine(), match, Integer.MAX_VALUE, false);
            input.inputs.putAll(pair.getFirst().inputs);
            input.handleRecipeIO(IO.IN, machine, getChanceCaches());
            eu += pair.getFirst().duration * RecipeHelper.getInputEUt(pair.getFirst());
            recipe.outputs.get(ItemRecipeCapability.CAP).addAll(pair.getFirst().outputs.get(ItemRecipeCapability.CAP));
        }
        if (recipe.outputs.get(ItemRecipeCapability.CAP).equals(new ArrayList<>())) return null;
        long maxeut = getMachine().getOverclockVoltage();
        double d = (double) eu / maxeut;
        long eut = d > 20 ? maxeut : (long) (maxeut * d / 20);
        recipe.tickInputs.put(EURecipeCapability.CAP, List.of(new Content(eut, ChanceLogic.getMaxChancedValue(), ChanceLogic.getMaxChancedValue(), 0, null, null)));
        recipe.duration = (int) Math.max(d, 20);
        return recipe;
    }

    @Override
    public void onRecipeFinish() {
        machine.afterWorking();
        if (lastRecipe != null) {
            lastRecipe.postWorking(this.machine);
            lastRecipe.handleRecipeIO(IO.OUT, this.machine, this.chanceCaches);
        }
        var match = getRecipe();
        if (match != null) {
            var copied = match.copy(new ContentModifier(match.duration, 0));
            if (match.matchRecipe(this.machine).isSuccess() && copied.matchTickRecipe(this.machine).isSuccess()) {
                setupRecipe(match);
                return;
            }
        }
        setStatus(Status.IDLE);
        progress = 0;
        duration = 0;
    }

}
