package org.gtlcore.gtlcore.mixin.gtm.cover;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.common.cover.InfiniteWaterCover;

import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import com.lowdragmc.lowdraglib.side.fluid.FluidTransferHelper;

import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluids;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InfiniteWaterCover.class)
public class InfiniteWaterCoverMixin extends CoverBehavior {

    public InfiniteWaterCoverMixin(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void update() {
        if (coverHolder.getOffsetTimer() % 20 == 0) {
            var fluidHandler = FluidTransferHelper.getFluidTransfer(coverHolder.getLevel(), coverHolder.getPos(),
                    attachedSide);
            if (fluidHandler != null)
                fluidHandler.fill(FluidStack.create(Fluids.WATER, Integer.MAX_VALUE), false);
        }
    }
}
