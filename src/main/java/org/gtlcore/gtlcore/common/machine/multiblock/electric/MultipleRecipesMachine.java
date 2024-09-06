package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import org.gtlcore.gtlcore.common.machine.trait.MultipleRecipesLogic;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MultipleRecipesMachine extends WorkableElectricMultiblockMachine {

    public MultipleRecipesMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    public Set<ItemBusPartMachine> busMachines;

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {
            if (part instanceof ItemBusPartMachine itemBusPartMachine) {
                busMachines = Objects.requireNonNullElseGet(busMachines, HashSet::new);
                busMachines.add(itemBusPartMachine);
            }
        }
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        busMachines = null;
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
