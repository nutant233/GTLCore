package org.gtlcore.gtlcore.common.machine.multiblock.part.maintenance;

import org.gtlcore.gtlcore.api.machine.IVacuumMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CVCHatchPartMachine extends ACMHatchPartMachine implements IVacuumMachine {

    public CVCHatchPartMachine(IMachineBlockEntity metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public int getVacuumTier() {
        return 4;
    }
}
