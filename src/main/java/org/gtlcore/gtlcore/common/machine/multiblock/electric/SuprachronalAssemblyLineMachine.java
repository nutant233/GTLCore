package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SuprachronalAssemblyLineMachine extends WorkableElectricMultiblockMachine {

    private final boolean isModule;

    private int module = 0;
    private WorkableElectricMultiblockMachine machine = null;

    public SuprachronalAssemblyLineMachine(IMachineBlockEntity holder, boolean isModule, Object... args) {
        super(holder, args);
        this.isModule = isModule;
    }

    private void getSuprachronalAssemblyLineModule() {
        Level level = getLevel();
        if (level != null) {
            BlockPos pos = getPos();
            module = 0;
            BlockPos[] coordinates = new BlockPos[] { pos.offset(3, 0, 0),
                    pos.offset(-3, 0, 0),
                    pos.offset(0, 0, 3),
                    pos.offset(0, 0, -3) };
            for (BlockPos i : coordinates) {
                MetaMachine metaMachine = MetaMachine.getMachine(level, i);
                if (metaMachine != null && Objects.equals(metaMachine.getBlockState().getBlock().kjs$getId(), "gtceu:suprachronal_assembly_line_module") && ((WorkableElectricMultiblockMachine) metaMachine).isFormed()) {
                    module++;
                }
            }
        }
    }

    private void getSuprachronalAssemblyLine() {
        Level level = getLevel();
        if (level != null) {
            BlockPos pos = getPos();
            BlockPos[] coordinates = new BlockPos[] { pos.offset(3, 0, 0),
                    pos.offset(-3, 0, 0),
                    pos.offset(0, 0, 3),
                    pos.offset(0, 0, -3) };
            for (BlockPos i : coordinates) {
                MetaMachine metaMachine = MetaMachine.getMachine(level, i);
                if (metaMachine != null && Objects.equals(metaMachine.getBlockState().getBlock().kjs$getId(), "gtceu:suprachronal_assembly_line") && ((WorkableElectricMultiblockMachine) metaMachine).isFormed()) {
                    machine = (WorkableElectricMultiblockMachine) metaMachine;
                }
            }
        }
    }

    public int getParallel() {
        return GTLRecipeModifiers.getHatchParallel(machine);
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getOffsetTimer() % 20 == 0) {
            if (isModule) {
                getSuprachronalAssemblyLine();
                if (machine == null) {
                    getRecipeLogic().setProgress(0);
                }
            } else {
                getSuprachronalAssemblyLineModule();
                if (module > 2) {
                    getRecipeLogic().setProgress(0);
                }
            }
        }
        return value;
    }

    @Override
    public boolean beforeWorking(@Nullable GTRecipe recipe) {
        if (isModule) {
            getSuprachronalAssemblyLine();
            if (machine == null) {
                getRecipeLogic().interruptRecipe();
                return false;
            }
        } else {
            getSuprachronalAssemblyLineModule();
            if (module > 2) {
                getRecipeLogic().interruptRecipe();
                return false;
            }
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        if (isModule) {
            if (getOffsetTimer() % 10 == 0) {
                getSuprachronalAssemblyLine();
            }
            textList.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(getParallel())).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
            textList.add(Component.literal("该模块" + (machine != null ? "已" : "未") + "成功安装"));
        } else {
            if (getOffsetTimer() % 10 == 0) {
                getSuprachronalAssemblyLineModule();
            }
            textList.add(Component.literal("已安装的模块数：" + module));
        }
    }
}
