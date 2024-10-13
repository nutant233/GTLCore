package org.gtlcore.gtlcore.common.machine.multiblock.part.maintenance;

import org.gtlcore.gtlcore.api.machine.IVacuumMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.ICleanroomProvider;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CVCMHatchPartMachine extends CCMHatchPartMachine implements IVacuumMachine {

    public CVCMHatchPartMachine(IMachineBlockEntity metaTileEntityId, ICleanroomProvider cleanroomTypes) {
        super(metaTileEntityId, cleanroomTypes);
    }

    @Override
    public int getVacuumTier() {
        return 10;
    }
}
