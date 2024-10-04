package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.MEBusPartMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;

import net.minecraft.core.Direction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.EnumSet;

@Mixin(MEBusPartMachine.class)
public abstract class MEBusPartMachineMixin extends ItemBusPartMachine implements IGridConnectedMachine {

    public MEBusPartMachineMixin(IMachineBlockEntity holder, int tier, IO io, Object... args) {
        super(holder, tier, io, args);
    }

    @Unique
    @Persisted
    public boolean gTLCore$isAllFacing;

    @Unique
    @Override
    public void onLoad() {
        super.onLoad();
        if (gTLCore$isAllFacing) {
            getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
        }
    }
}
