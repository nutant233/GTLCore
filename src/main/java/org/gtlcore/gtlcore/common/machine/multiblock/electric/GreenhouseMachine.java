package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GreenhouseMachine extends WorkableElectricMultipleRecipesMachine {

    public GreenhouseMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    private int SkyLight = 15;

    private void getGreenhouseLight() {
        Level level = getLevel();
        BlockPos pos = getPos();
        BlockPos[] coordinates = new BlockPos[] { pos.offset(1, 2, 0), pos.offset(1, 2, 1), pos.offset(1, 2, -1), pos.offset(0, 2, 1), pos.offset(0, 2, -1), pos.offset(-1, 2, 0), pos.offset(-1, 2, 1), pos.offset(-1, 2, -1), pos.offset(2, 2, 0), pos.offset(2, 2, -1), pos.offset(2, 2, 1), pos.offset(3, 2, 0), pos.offset(3, 2, -1), pos.offset(3, 2, 1), pos.offset(-2, 2, 0), pos.offset(-2, 2, -1), pos.offset(-2, 2, 1), pos.offset(-3, 2, 0), pos.offset(-3, 2, -1), pos.offset(-3, 2, 1), pos.offset(-1, 2, 2), pos.offset(0, 2, 2), pos.offset(1, 2, 2), pos.offset(-1, 2, 3), pos.offset(0, 2, 3), pos.offset(1, 2, 3), pos.offset(-1, 2, -2), pos.offset(0, 2, -2), pos.offset(1, 2, -2), pos.offset(-1, 2, - 3), pos.offset(0, 2, -3), pos.offset(1, 2, -3)};
        for(BlockPos i : coordinates) {
            if (level != null && level.getBlockState(i).getBlock() == GTBlocks.CASING_TEMPERED_GLASS.get()) {
                int l = level.getBrightness(LightLayer.SKY, i.offset(0, 1, 0));
                if (l < SkyLight) {
                    SkyLight = l;
                }
            }
        }
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        getGreenhouseLight();
        if (SkyLight == 0) {
            getRecipeLogic().interruptRecipe();
            return false;
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getOffsetTimer() % 20 == 0) {
            getGreenhouseLight();
            if (SkyLight == 0) {
                getRecipeLogic().setProgress(0);
            }
            if (SkyLight < 15) {
                getRecipeLogic().setProgress(getRecipeLogic().getProgress() - 10);
            }
        }
        return value;
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        if (getOffsetTimer() % 10 == 0) {
            getGreenhouseLight();
        }
        textList.add(Component.literal("当前光照：" + SkyLight));
    }
}
