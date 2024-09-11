package org.gtlcore.gtlcore.mixin.extendedae;

import com.glodblock.github.extendedae.container.ContainerPatternModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ContainerPatternModifier.class)
public class ContainerPatternModifierMixin {

    @ModifyConstant(method = "checkModify", remap = false, constant = @Constant(longValue = 999999L))
    private long modifyContainer(long constant) {
        return Integer.MAX_VALUE;
    }
}
