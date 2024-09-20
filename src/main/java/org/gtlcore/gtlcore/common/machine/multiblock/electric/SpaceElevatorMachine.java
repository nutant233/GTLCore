package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import earth.terrarium.adastra.common.menus.base.PlanetsMenuProvider;
import earth.terrarium.botarium.common.menu.MenuHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SpaceElevatorMachine extends TierCasingMachine {

    public SpaceElevatorMachine(IMachineBlockEntity holder) {
        super(holder, "SEPMTier");
    }

    private int mam = 0;

    private BlockPos getPowerCore(BlockPos pos, Level level) {
        BlockPos[] coordinates = new BlockPos[] { pos.offset(3, -2, 0),
                pos.offset(-3, -2, 0),
                pos.offset(0, -2, 3),
                pos.offset(0, -2, -3) };
        for (BlockPos blockPos : coordinates) {
            if (Objects.equals(level.kjs$getBlock(blockPos).getId(), "gtlcore:power_core")) {
                return blockPos;
            }
        }
        return null;
    }

    private int getMAM() {
        if (getOffsetTimer() % 20 == 0) {
            final Level level = getLevel();
            final BlockPos blockPos = getPowerCore(getPos(), level);
            if (blockPos != null) {
                BlockPos[] coordinatess = new BlockPos[] { blockPos.offset(8, 2, 3),
                        blockPos.offset(8, 2, -3),
                        blockPos.offset(-8, 2, 3),
                        blockPos.offset(-8, 2, -3),
                        blockPos.offset(3, 2, 8),
                        blockPos.offset(-3, 2, 8),
                        blockPos.offset(3, 2, -8),
                        blockPos.offset(-3, 2, -8) };
                mam = 0;
                for (BlockPos blockPoss : coordinatess) {
                    RecipeLogic logic = GTCapabilityHelper.getRecipeLogic(level, blockPoss, null);
                    if (logic != null && logic.machine instanceof WorkableElectricMultiblockMachine mbmachine &&
                            mbmachine.isFormed()) {
                        String bid = mbmachine.getBlockState().getBlock().kjs$getId();
                        if (bid.equals("gtceu:assembler_module") || bid.equals("gtceu:resource_collection")) {
                            mam++;
                        }
                    }
                }
                return mam;
            }
        }
        return mam;
    }

    @Override
    public boolean onWorking() {
        boolean value = super.onWorking();
        if (getOffsetTimer() % 20 == 0) {
            if (getRecipeLogic().getProgress() > 240) {
                getRecipeLogic().setProgress(120);
            }
        }
        return value;
    }

    @Override
    public void addDisplayText(@NotNull List<Component> textList) {
        super.addDisplayText(textList);
        if (!this.isFormed) return;
        textList.add(Component.translatable("gtceu.machine.module", getMAM()));
        textList.add(
                ComponentPanelWidget.withButton(Component.translatable("gtceu.machine.space_elevator.set_out"),
                        "set_out"));
    }

    @Override
    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (componentData.equals("set_out") && getRecipeLogic().isWorking()) {
            final BlockPos pos = getPos();
            List<ServerPlayer> entities = Objects.requireNonNull(getLevel()).getEntitiesOfClass(ServerPlayer.class, new AABB(pos.getX() - 2,
                    pos.getY() - 2,
                    pos.getZ() - 2,
                    pos.getX() + 2,
                    pos.getY() + 2,
                    pos.getZ() + 2));
            for (ServerPlayer pr : entities) {
                if (pr != null) {
                    pr.addTag("spaceelevatorst");
                    MenuHooks.openMenu(pr, new PlanetsMenuProvider());
                }
            }
        }
    }
}
