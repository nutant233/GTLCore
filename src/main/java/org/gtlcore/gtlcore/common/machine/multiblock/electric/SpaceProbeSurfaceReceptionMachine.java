package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import org.gtlcore.gtlcore.utils.MachineUtil;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

import org.jetbrains.annotations.Nullable;

public class SpaceProbeSurfaceReceptionMachine extends WorkableElectricMultiblockMachine {

    public SpaceProbeSurfaceReceptionMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        Level level = getLevel();
        if (level == null) return false;
        final BlockPos pos = MachineUtil.getOffsetPos(4, 9, getFrontFacing(), getPos());
        for (int i = -5; i < 6; i++) {
            for (int j = -5; j < 6; j++) {
                if (level.getBrightness(LightLayer.SKY, pos.offset(i, 0, j)) == 0) {
                    return false;
                }
            }
        }
        return super.beforeWorking(recipe);
    }
}
