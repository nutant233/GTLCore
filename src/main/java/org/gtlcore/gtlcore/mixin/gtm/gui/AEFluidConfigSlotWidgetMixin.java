package org.gtlcore.gtlcore.mixin.gtm.gui;

import com.gregtechceu.gtceu.api.gui.misc.IGhostFluidTarget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.ConfigWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEFluidConfigSlotWidget;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author EasterFG on 2024/9/16
 */
@Mixin(AEFluidConfigSlotWidget.class)
public abstract class AEFluidConfigSlotWidgetMixin extends AEConfigSlotWidget implements IGhostFluidTarget {
    public AEFluidConfigSlotWidgetMixin(Position pos, Size size, ConfigWidget widget, int index) {
        super(pos, size, widget, index);
    }

    @Override
    public Object convertIngredient(Object ingredient) {
        if (LDLib.isJeiLoaded() && ingredient instanceof FluidStack fluidStack) {
            ingredient = com.lowdragmc.lowdraglib.side.fluid.FluidStack.create(fluidStack.getFluid(), fluidStack.getAmount(), fluidStack.getTag());
        }
        return IGhostFluidTarget.super.convertIngredient(ingredient);
    }
}
