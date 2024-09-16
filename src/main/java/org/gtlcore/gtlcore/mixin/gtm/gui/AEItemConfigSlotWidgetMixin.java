package org.gtlcore.gtlcore.mixin.gtm.gui;


import com.gregtechceu.gtceu.api.gui.misc.IGhostItemTarget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.ConfigWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEConfigSlotWidget;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEItemConfigSlotWidget;
import com.lowdragmc.lowdraglib.LDLib;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import mezz.jei.api.ingredients.ITypedIngredient;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author EasterFG on 2024/9/17
 */
@Mixin(AEItemConfigSlotWidget.class)
public abstract class AEItemConfigSlotWidgetMixin extends AEConfigSlotWidget implements IGhostItemTarget {

    public AEItemConfigSlotWidgetMixin(Position pos, Size size, ConfigWidget widget, int index) {
        super(pos, size, widget, index);
    }

    @Override
    public Object convertIngredient(Object ingredient) {
        if (LDLib.isJeiLoaded() && ingredient instanceof ITypedIngredient<?> itemJeiStack) {
            return itemJeiStack.getItemStack().orElse(null);
        }
        return IGhostItemTarget.super.convertIngredient(ingredient);
    }
}
