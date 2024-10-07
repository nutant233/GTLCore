package org.gtlcore.gtlcore.common.machine.multiblock.electric;

import org.gtlcore.gtlcore.api.machine.multiblock.TierCasingMachine;
import org.gtlcore.gtlcore.common.data.machines.AdvancedMultiBlockMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;

import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

import earth.terrarium.adastra.common.menus.base.PlanetsMenuProvider;
import earth.terrarium.botarium.common.menu.MenuHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SpaceElevatorMachine extends TierCasingMachine {

    public SpaceElevatorMachine(IMachineBlockEntity holder) {
        super(holder, "SEPMTier");
    }

    private int mam = 0;

    private int getMAM() {
        if (getOffsetTimer() % 20 == 0) {
            final Level level = getLevel();
            int x = 0, y = -2, z = 0;
            switch (getFrontFacing()) {
                case NORTH -> z = 3;
                case SOUTH -> z = -3;
                case WEST -> x = 3;
                case EAST -> x = -3;
            }
            final BlockPos blockPos = getPos().offset(x, y, z);
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
                MetaMachine metaMachine = null;
                if (level != null) {
                    metaMachine = MetaMachine.getMachine(level, blockPoss);
                }
                if (metaMachine instanceof WorkableElectricMultiblockMachine mbmachine &&
                        mbmachine.isFormed()) {
                    Block block = mbmachine.getBlockState().getBlock();
                    if (block == AdvancedMultiBlockMachine.ASSEMBLER_MODULE.getBlock() || block == AdvancedMultiBlockMachine.RESOURCE_COLLECTION.getBlock()) {
                        mam++;
                    }
                }
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
