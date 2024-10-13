package org.gtlcore.gtlcore.common.machine.multiblock.part.maintenance;

import org.gtlcore.gtlcore.api.machine.IVacuumMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CVMHatchPartMachine extends CMHatchPartMachine implements IVacuumMachine {

    public CVMHatchPartMachine(IMachineBlockEntity metaTileEntityId, ICleanroomProvider cleanroomTypes) {
        super(metaTileEntityId, cleanroomTypes);
    }

    @Override
    public int getVacuumTier() {
        return 8;
    }
}
