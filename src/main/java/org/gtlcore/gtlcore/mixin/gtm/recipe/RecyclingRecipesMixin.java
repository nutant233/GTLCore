package org.gtlcore.gtlcore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.misc.RecyclingRecipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.M;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.IS_MAGNETIC;

@Mixin(RecyclingRecipes.class)
public abstract class RecyclingRecipesMixin {

    @Shadow(remap = false)
    private static int calculateVoltageMultiplier(List<MaterialStack> materials) {
        return 0;
    }

    @Shadow(remap = false)
    private static void registerArcRecycling(Consumer<FinishedRecipe> provider, ItemStack input, List<MaterialStack> materials, @Nullable TagPrefix prefix) {}

    @Shadow(remap = false)
    private static void registerMaceratorRecycling(Consumer<FinishedRecipe> provider, ItemStack input, List<MaterialStack> materials, int multiplier) {}

    @Shadow(remap = false)
    private static void registerExtractorRecycling(Consumer<FinishedRecipe> provider, ItemStack input, List<MaterialStack> materials, int multiplier, @Nullable TagPrefix prefix) {}

    @Inject(method = "registerRecyclingRecipes", at = @At("HEAD"), remap = false, cancellable = true)
    private static void registerRecyclingRecipes(Consumer<FinishedRecipe> provider, ItemStack input, List<MaterialStack> components, boolean ignoreArcSmelting, @Nullable TagPrefix prefix, CallbackInfo ci) {
        List<MaterialStack> materials = components.stream()
                .filter(stack -> stack.material().hasProperty(PropertyKey.DUST))
                .filter(stack -> stack.amount() >= M / 9)
                .sorted(Comparator.comparingLong(ms -> -ms.amount()))
                .toList();

        if (materials.isEmpty()) return;

        int voltageMultiplier = calculateVoltageMultiplier(components);

        if (prefix == TagPrefix.ingot || prefix == TagPrefix.gem) {
            registerMaceratorRecycling(provider, input, components, voltageMultiplier);
        }
        if (prefix == TagPrefix.ingot || prefix == TagPrefix.dust) {
            registerExtractorRecycling(provider, input, components, voltageMultiplier, prefix);
        }
        if (ignoreArcSmelting) return;

        if (components.size() == 1) {
            Material m = components.get(0).material();

            if (!m.hasProperty(PropertyKey.INGOT)) {
                return;
            }

            if (ChemicalHelper.getPrefix(input.getItem()) == TagPrefix.ingot &&
                    m.getProperty(PropertyKey.INGOT).getArcSmeltingInto() == m) {
                return;
            }

            if (prefix == TagPrefix.dust && m.hasFlag(IS_MAGNETIC)) {
                return;
            }
        }
        registerArcRecycling(provider, input, components, prefix);
        ci.cancel();
    }
}
