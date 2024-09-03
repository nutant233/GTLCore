package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import org.gtlcore.gtlcore.api.pattern.util.IValueContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class GTLAssemblyLineMachine extends WorkableElectricMultiblockMachine {

    public GTLAssemblyLineMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            GTLAssemblyLineMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    private int speed = 0;

    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe) {
        if (machine instanceof GTLAssemblyLineMachine lineMachine) {
            GTRecipe recipe1 = recipe.copy();
            recipe1.duration = Math.max(lineMachine.speed * recipe.duration / 100, 1);
            if (RecipeHelper.getRecipeEUtTier(recipe) / recipe.parallels > lineMachine.getTier()) {
                return null;
            }
            return RecipeHelper.applyOverclock(OverclockingLogic.NON_PERFECT_OVERCLOCK,
                    recipe1, lineMachine.getOverclockVoltage(), new OCParams(), new OCResult());
        }
        return null;
    }

    @Override
    public void onStructureFormed() {
        IValueContainer<?> container = getMultiblockState().getMatchContext()
                .getOrCreate("UnitValue", IValueContainer::noop);
        if (container.getValue() instanceof Integer integer) {
            speed = 100 - integer;
        }
        getDefinition().setPartSorter(Comparator.comparing(it -> multiblockPartSorter().apply(it.self().getPos())));
        super.onStructureFormed();
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        textList.add(Component.translatable("gtceu.machine.duration_multiplier.tooltip", speed).append("%"));
    }

    private Function<BlockPos, Integer> multiblockPartSorter() {
        return RelativeDirection.RIGHT.getSorter(getFrontFacing(), getUpwardsFacing(), isFlipped());
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
