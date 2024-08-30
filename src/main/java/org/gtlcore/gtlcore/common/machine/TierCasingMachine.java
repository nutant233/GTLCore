package org.gtlcore.gtlcore.common.machine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TierCasingMachine extends WorkableElectricMultiblockMachine {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            TierCasingMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    private final String tierType;

    @Persisted
    private int tier = 0;

    public TierCasingMachine(IMachineBlockEntity holder, String tierType, Object... args) {
        super(holder, args);
        this.tierType = tierType;
    }

    public int getCasingTier() {
        return tier;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        tier = getMultiblockState().getMatchContext().get(tierType);
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        tier = 0;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (recipe != null && recipe.data.contains(tierType) && recipe.data.getInt(tierType) > tier) {
            getRecipeLogic().interruptRecipe();
            return false;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        textList.add(Component.translatable("gtceu.casings.tier", tier));
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
