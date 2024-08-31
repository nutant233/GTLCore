package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.capability.IHPCAComputationProvider;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.hpca.HPCAComponentPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.hpca.HPCAComputationPartMachine;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HPCAComputationPartMachine.class)
public abstract class HPCAComputationPartMachineMixin extends HPCAComponentPartMachine implements IHPCAComputationProvider {

    public HPCAComputationPartMachineMixin(IMachineBlockEntity holder) {
        super(holder);
    }

    @Shadow @Final private boolean advanced;

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public int getCWUPerTick() {
        if (isDamaged()) return 0;
        return this.advanced ? 32 : 8;
    }
}
