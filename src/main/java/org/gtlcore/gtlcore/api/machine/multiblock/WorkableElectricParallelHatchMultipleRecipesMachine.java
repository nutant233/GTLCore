package org.gtlcore.gtlcore.api.machine.multiblock;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import static org.gtlcore.gtlcore.common.data.GTLRecipeModifiers.getHatchParallel;

public class WorkableElectricParallelHatchMultipleRecipesMachine extends WorkableElectricMultipleRecipesMachine {

    public WorkableElectricParallelHatchMultipleRecipesMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public int getParallel() {
        return isFormed() ? getHatchParallel(this) : 0;
    }
}
