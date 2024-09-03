package org.gtlcore.gtlcore.common.machine.generator;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.TieredEnergyMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LightningRodMachine extends TieredEnergyMachine {

    @Nullable
    protected TickableSubscription energySubs;

    public LightningRodMachine(IMachineBlockEntity holder, int tier, Object... args) {
        super(holder, tier, args);
        this.energySubs = subscribeServerTick(energySubs, this::checkEnergy);
    }

    protected void checkEnergy() {
        if (getOffsetTimer() % 10 == 0) {
            BlockState state = getLevel().getBlockState(getPos().offset(0, 1, 0));
            if (state.getBlock() == Blocks.LIGHTNING_ROD &&
                    state.getValue(BlockStateProperties.FACING) == Direction.UP &&
                    state.getValue(LightningRodBlock.POWERED)) {
                if (energyContainer.getEnergyStored() == getCharge()) {
                    doExplosion(getTier());
                } else {
                    energyContainer.addEnergy(getCharge() / 4);
                }
            }
        }
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    protected long getMaxInputOutputAmperage() {
        return 512L;
    }

    private long getCharge() {
        return (long) (48828 * Math.pow(4, getTier()));
    }

    @Override
    protected NotifiableEnergyContainer createEnergyContainer(Object... args) {
        return NotifiableEnergyContainer.emitterContainer(this, getCharge(),
                GTValues.V[getTier() - 1], getMaxInputOutputAmperage());
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }
}
