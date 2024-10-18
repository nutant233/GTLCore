package org.gtlcore.gtlcore.api.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TierCasingMachine extends WorkableElectricMultiblockMachine {

    private final String tierType;

    @Getter
    private int casingTier = 0;

    public TierCasingMachine(IMachineBlockEntity holder, String tierType, Object... args) {
        super(holder, args);
        this.tierType = tierType;
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        casingTier = getMultiblockState().getMatchContext().get(tierType);
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        casingTier = 0;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (recipe != null && recipe.data.contains(tierType) && recipe.data.getInt(tierType) > casingTier) {
            return false;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        textList.add(Component.translatable("gtceu.casings.tier", casingTier));
    }
}
