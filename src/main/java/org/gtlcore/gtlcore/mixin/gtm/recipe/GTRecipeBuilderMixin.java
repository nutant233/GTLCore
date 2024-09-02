package org.gtlcore.gtlcore.mixin.gtm.recipe;

import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import lombok.experimental.Accessors;
import org.gtlcore.gtlcore.config.ConfigHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GTRecipeBuilder.class)
@Accessors(chain = true, fluent = true)
public abstract class GTRecipeBuilderMixin {

    @Shadow(remap = false)
    public GTRecipeType recipeType;

    @Shadow(remap = false)
    public int duration;

    @Shadow(remap = false)
    public abstract GTRecipeBuilder duration(int duration);

    @Unique
    private long gTLCore$eut = 0;

    @Inject(method = "EUt(J)Lcom/gregtechceu/gtceu/data/recipe/builder/GTRecipeBuilder;", at = @At("HEAD"), remap = false)
    private void eu(long eu, CallbackInfoReturnable<GTRecipeBuilder> cir) {
        gTLCore$eut = eu;
    }

    @Unique
    private int gTLCore$getDuration() {
        if (ConfigHolder.INSTANCE.durationMultiplier == 1 || gTLCore$eut < 0 ||
                recipeType == GTRecipeTypes.get("primitive_void_ore") ||
                recipeType == GTRecipeTypes.get("large_boiler") ||
                recipeType == GTRecipeTypes.get("steam_boiler") ||
                recipeType == GTRecipeTypes.get("slaughterhouse") ||
                recipeType == GTRecipeTypes.get("naquadah_reactor") ||
                recipeType == GTRecipeTypes.get("large_naquadah_reactor") ||
                recipeType == GTRecipeTypes.get("hyper_reactor") ||
                recipeType == GTRecipeTypes.get("dyson_sphere") ||
                recipeType == GTRecipeTypes.get("rocket_engine") ||
                recipeType == GTRecipeTypes.get("space_elevator") ||
                recipeType == GTRecipeTypes.get("annihilate_generator")) {
            return Math.abs(duration);
        }
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1, Math.abs(duration * ConfigHolder.INSTANCE.durationMultiplier)));
    }

    @Inject(method = "toJson", at = @At("TAIL"), remap = false)
    public void toJson(JsonObject json, CallbackInfo ci) {
        json.addProperty("duration",gTLCore$getDuration());
    }
}
