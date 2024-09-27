package org.gtlcore.gtlcore.mixin.gtm.gui;

import com.gregtechceu.gtceu.integration.ae2.gui.widget.slot.AEFluidConfigSlotWidget;

import appeng.api.stacks.AmountFormat;
import appeng.api.stacks.GenericStack;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * @author EasterFG on 2024/9/26
 */
@Mixin(AEFluidConfigSlotWidget.class)
public class AEFluidConfigSlotWidgetMixin {

    @ModifyArg(method = "drawInBackground",
               at = @At(value = "INVOKE", target = "Lcom/lowdragmc/lowdraglib/gui/util/DrawerHelper;drawStringFixedCorner(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/String;FFIZF)V", ordinal = 0),
               index = 1,
               remap = false)
    public String configFormat(String str, @Local(ordinal = 0) GenericStack stack) {
        return stack.what().formatAmount(stack.amount(), AmountFormat.FULL);
    }

    @ModifyArg(method = "drawInBackground",
               at = @At(value = "INVOKE", target = "Lcom/lowdragmc/lowdraglib/gui/util/DrawerHelper;drawStringFixedCorner(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/String;FFIZF)V", ordinal = 1),
               index = 1,
               remap = false)
    public String stockFormat(String str, @Local(ordinal = 1) GenericStack stack) {
        return stack.what().formatAmount(stack.amount(), AmountFormat.FULL);
    }
}
