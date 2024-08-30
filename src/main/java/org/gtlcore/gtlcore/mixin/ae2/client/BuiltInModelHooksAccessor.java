package org.gtlcore.gtlcore.mixin.ae2.client;

import appeng.hooks.BuiltInModelHooks;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BuiltInModelHooks.class)
public interface BuiltInModelHooksAccessor {

    @Accessor(value = "builtInModels", remap = false)
    static Map<ResourceLocation, UnbakedModel> getBuiltInModels() {
        throw new AssertionError();
    }
}
