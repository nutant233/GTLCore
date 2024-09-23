package org.gtlcore.gtlcore.mixin.ae2;

import net.minecraft.network.chat.MutableComponent;

import appeng.core.localization.Tooltips;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.gtlcore.gtlcore.utils.Number.numberText;

@Mixin(Tooltips.class)
public final class TooltipsMixin {

    @Inject(method = "ofBytes", at = @At("HEAD"), remap = false, cancellable = true)
    private static void ofBytes(long number, CallbackInfoReturnable<MutableComponent> cir) {
        cir.setReturnValue((MutableComponent) numberText(number));
    }
}
