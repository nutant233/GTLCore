package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import org.gtlcore.gtlcore.common.machine.trait.MultipleRecipesLogic;
import org.jetbrains.annotations.NotNull;

public class MultipleRecipesMachine extends WorkableElectricMultiblockMachine {

    public MultipleRecipesMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    protected @NotNull RecipeLogic createRecipeLogic(Object @NotNull ... args) {
        return new MultipleRecipesLogic(this);
    }

    @NotNull
    @Override
    public MultipleRecipesLogic getRecipeLogic() {
        return (MultipleRecipesLogic) super.getRecipeLogic();
    }
}
