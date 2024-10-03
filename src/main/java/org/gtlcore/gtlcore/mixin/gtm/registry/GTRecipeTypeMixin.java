package org.gtlcore.gtlcore.mixin.gtm.registry;

import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.GTUtil;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(GTRecipeType.class)
public class GTRecipeTypeMixin {

    @Shadow(remap = false)
    private GTRecipeBuilder recipeBuilder;

    @Shadow(remap = false)
    @Final
    public ResourceLocation registryName;

    @Inject(method = "onRecipeBuild", at = @At("HEAD"), remap = false, cancellable = true)
    public void onRecipeBuild(BiConsumer<GTRecipeBuilder, Consumer<FinishedRecipe>> onBuild, CallbackInfoReturnable<GTRecipeType> cir) {
        if (Objects.equals(registryName, GTCEu.id("cutter"))) {
            recipeBuilder.onSave((recipeBuilder, provider) -> {
                if (recipeBuilder.input.getOrDefault(FluidRecipeCapability.CAP, Collections.emptyList()).isEmpty() &&
                        recipeBuilder.tickInput.getOrDefault(FluidRecipeCapability.CAP, Collections.emptyList()).isEmpty()) {
                    recipeBuilder.copy(recipeBuilder.id);
                    if (recipeBuilder.EUt() < GTValues.VA[GTValues.MV]) {
                        recipeBuilder.inputFluids(GTMaterials.Water.getFluid((int) Math.max(4,
                                recipeBuilder.duration * recipeBuilder.EUt() / 80)))
                                .save(provider);
                    } else if (recipeBuilder.EUt() < GTValues.VA[GTValues.IV]) {
                        recipeBuilder.inputFluids(GTMaterials.DistilledWater.getFluid((int) Math.max(3,
                                recipeBuilder.duration * recipeBuilder.EUt() / 640)))
                                .save(provider);
                    } else if (recipeBuilder.EUt() < GTValues.VA[GTValues.UHV]) {
                        recipeBuilder.inputFluids(GTMaterials.Lubricant.getFluid((int) Math.max(1,
                                recipeBuilder.duration * recipeBuilder.EUt() / 81920)))
                                .save(provider);
                    } else if (recipeBuilder.EUt() < GTValues.VA[GTValues.UEV]) {
                        recipeBuilder.inputFluids(GTLMaterials.GradePurifiedWater8.getFluid((int) Math.max(1,
                                recipeBuilder.duration * recipeBuilder.EUt() / 1310720)))
                                .save(provider);
                    } else {
                        recipeBuilder.inputFluids(GTLMaterials.GradePurifiedWater16.getFluid((int) Math.max(1,
                                recipeBuilder.duration * recipeBuilder.EUt() / 5242880)))
                                .save(provider);
                    }
                }
            });
            cir.setReturnValue(recipeBuilder.recipeType);
        }
        if (Objects.equals(registryName, GTCEu.id("circuit_assembler"))) {
            recipeBuilder.onSave((recipeBuilder, provider) -> {
                if (recipeBuilder.input.getOrDefault(FluidRecipeCapability.CAP, Collections.emptyList()).isEmpty() &&
                        recipeBuilder.tickInput.getOrDefault(FluidRecipeCapability.CAP, Collections.emptyList())
                                .isEmpty()) {
                    recipeBuilder.copy(recipeBuilder.id);
                    if (recipeBuilder.EUt() < GTValues.VA[GTValues.EV]) {
                        recipeBuilder.inputFluids(GTMaterials.Tin.getFluid(Math.max(1, 144 * recipeBuilder.getSolderMultiplier())))
                                .save(provider);
                    } else if (recipeBuilder.EUt() < GTValues.VA[GTValues.UV]) {
                        recipeBuilder.inputFluids(GTMaterials.SolderingAlloy.getFluid(Math.max(1, 144 * recipeBuilder.getSolderMultiplier())))
                                .save(provider);
                    } else if (recipeBuilder.EUt() < GTValues.VA[GTValues.UIV]) {
                        recipeBuilder.inputFluids(GTLMaterials.MutatedLivingSolder.getFluid(Math.max(1, 144 * (GTUtil.getFloorTierByVoltage(recipeBuilder.EUt()) - 6))))
                                .save(provider);
                    } else {
                        recipeBuilder.inputFluids(GTLMaterials.SuperMutatedLivingSolder.getFluid(Math.max(1, 144 * (GTUtil.getFloorTierByVoltage(recipeBuilder.EUt()) - 8))))
                                .save(provider);
                    }
                }
            });
            cir.setReturnValue(recipeBuilder.recipeType);
        }
    }
}
