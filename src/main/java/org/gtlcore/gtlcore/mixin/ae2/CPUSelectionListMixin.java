package org.gtlcore.gtlcore.mixin.ae2;

import appeng.client.gui.ICompositeWidget;
import appeng.client.gui.widgets.CPUSelectionList;
import appeng.menu.me.crafting.CraftingStatusMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.gtlcore.gtlcore.utils.NumberUtils.numberText;

@Mixin(CPUSelectionList.class)
public abstract class CPUSelectionListMixin implements ICompositeWidget {

    @Inject(method = "formatStorage", at = @At("HEAD"), remap = false, cancellable = true)
    private void formatStorage(CraftingStatusMenu.CraftingCpuListEntry cpu, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(numberText(cpu.storage()).getString());
    }
}
