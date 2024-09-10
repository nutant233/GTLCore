package org.gtlcore.gtlcore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.DualHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DualHatchPartMachine.class)
public class DualHatchPartMachineMixin extends ItemBusPartMachine {

    public DualHatchPartMachineMixin(IMachineBlockEntity holder, int tier, IO io, Object... args) {
        super(holder, tier, io, args);
    }

    /**
     * @author mod_author
     * @reason 原版太小了
     */
    @Overwrite(remap = false)
    public static long getTankCapacity(long initialCapacity, int tier) {
        return initialCapacity * (1L << tier);
    }

    /**
     * @author mod_author
     * @reason 如上
     */
    @Overwrite(remap = false)
    public int getInventorySize() {
        return super.getInventorySize();
    }

    /**
     * @author mod_author
     * @reason 不需要
     */
    @Overwrite(remap = false)
    public boolean isDistinct() {
        return super.isDistinct();
    }

    /**
     * @author mod_author
     * @reason 不需要
     */
    @Overwrite(remap = false)
    public void setDistinct(boolean isDistinct) {
        super.setDistinct(isDistinct);
    }
}
