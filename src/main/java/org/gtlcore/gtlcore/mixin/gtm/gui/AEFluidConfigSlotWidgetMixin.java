package org.gtlcore.gtlcore.mixin.gtm.gui;

import appeng.api.stacks.GenericStack;
import com.gregtechceu.gtceu.api.gui.misc.IGhostFluidTarget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.ConfigWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEFluidConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.slot.IConfigurableSlot;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
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

    @Override
    public void drawInForeground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawInForeground(graphics, mouseX, mouseY, partialTicks);
        IConfigurableSlot slot = this.parentWidget.getDisplay(index);
        GenericStack genericStack = null;
        if (mouseOverConfig(mouseX, mouseY)) {
            genericStack = slot.getConfig();
        } else if (mouseOverStock(mouseX, mouseY)) {
            genericStack = slot.getStock();
        }
        if (genericStack != null) {
            graphics.renderTooltip(Minecraft.getInstance().font, GenericStack.wrapInItemStack(genericStack), mouseX,
                    mouseY);
        }
    }
}
