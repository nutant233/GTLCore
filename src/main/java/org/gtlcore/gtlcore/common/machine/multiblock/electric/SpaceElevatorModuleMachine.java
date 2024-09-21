package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.logic.OCParams;
import com.gregtechceu.gtceu.api.recipe.logic.OCResult;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;
import org.gtlcore.gtlcore.common.data.machines.AdvancedMultiBlockMachine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpaceElevatorModuleMachine extends WorkableElectricMultiblockMachine {

    public SpaceElevatorModuleMachine(IMachineBlockEntity holder, boolean SEPMTier, Object... args) {
        super(holder, args);
        this.SEPMTier = SEPMTier;
    }

    private int SpaceElevatorTier = 0, ModuleTier = 0;

    private final boolean SEPMTier;

    private void getSpaceElevatorTier() {
        Level level = getLevel();
        BlockPos pos = getPos();
        BlockPos[] coordinates = new BlockPos[] { pos.offset(8, -2, 3),
                pos.offset(8, -2, -3),
                pos.offset(-8, -2, 3),
                pos.offset(-8, -2, -3),
                pos.offset(3, -2, 8),
                pos.offset(-3, -2, 8),
                pos.offset(3, -2, -8),
                pos.offset(-3, -2, -8) };
        for (BlockPos i : coordinates) {
            if (level != null && level.getBlockState(i).getBlock() == GTLBlocks.POWER_CORE.get()) {
                BlockPos[] coordinatess = new BlockPos[] { i.offset(3, 2, 0),
                        i.offset(-3, 2, 0),
                        i.offset(0, 2, 3),
                        i.offset(0, 2, -3) };
                for (BlockPos j : coordinatess) {
                    RecipeLogic logic = GTCapabilityHelper.getRecipeLogic(level, j, null);
                    if (logic != null && logic.getMachine().getDefinition() == AdvancedMultiBlockMachine.SPACE_ELEVATOR && logic.isWorking() && logic.getProgress() > 80) {
                        SpaceElevatorTier = ((SpaceElevatorMachine) logic.machine).getTier() - GTValues.ZPM;
                        ModuleTier =  ((SpaceElevatorMachine) logic.machine).getCasingTier();
                    }
                }
            }
        }
    }

    @Nullable
    public static GTRecipe recipeModifier(MetaMachine machine, @NotNull GTRecipe recipe, @NotNull OCParams params,
                                          @NotNull OCResult result) {
        if (machine instanceof SpaceElevatorModuleMachine spaceElevatorModuleMachine) {
            spaceElevatorModuleMachine.getSpaceElevatorTier();
            if (spaceElevatorModuleMachine.SpaceElevatorTier < 1) {
                return null;
            }
            if (spaceElevatorModuleMachine.SEPMTier && recipe.data.getInt("SEPMTier") > spaceElevatorModuleMachine.ModuleTier) {
                return null;
            }
            GTRecipe recipe1 = GTLRecipeModifiers.reduction(machine, recipe, 1, Math.pow(0.8, spaceElevatorModuleMachine.SpaceElevatorTier - 1));
            if (recipe1 != null) {
                return RecipeHelper.applyOverclock(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK, GTRecipeModifiers.accurateParallel(machine, recipe1, (int) Math.pow(4, spaceElevatorModuleMachine.ModuleTier - 1), false).getFirst(), spaceElevatorModuleMachine.getOverclockVoltage(), params, result);
            }

        }
        return null;
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getOffsetTimer() % 20 == 0) {
            getSpaceElevatorTier();
            if (SpaceElevatorTier < 1) {
                getRecipeLogic().interruptRecipe();
                return false;
            }
        }
        return value;
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        if (getOffsetTimer() % 10 == 0) {
            getSpaceElevatorTier();
        }
        textList.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(Math.pow(4, ModuleTier - 1))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
        textList.add(Component.literal((SpaceElevatorTier < 1 ? "未" : "已") + "连接正在运行的太空电梯"));
        textList.add(Component.translatable("gtceu.machine.duration_multiplier.tooltip", Math.pow(0.8, SpaceElevatorTier - 1)));
    }
}
