package org.gtlcore.gtlcore.mixin.gtm.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.machines.GTAEMachines;
import com.gregtechceu.gtceu.common.data.machines.GTCreateMachines;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryInfo;
import it.unimi.dsi.fastutil.ints.Int2LongFunction;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModLoader;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLRecipeTypes;
import org.gtlcore.gtlcore.common.data.machines.GCyMMachines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.gregtechceu.gtceu.api.GTValues.VLVH;
import static com.gregtechceu.gtceu.api.GTValues.VLVT;
import static com.gregtechceu.gtceu.common.data.GTMachines.registerTieredMachines;
import static com.gregtechceu.gtceu.common.data.GTMachines.workableTiered;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;


@Mixin(GTMachines.class)
public abstract class GTMachinesMixin {
    @Inject(method = "init", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void init(CallbackInfo ci) {
        GCyMMachines.init();
        GTResearchMachines.init();

        if (GTCEu.isCreateLoaded()) {
            GTCreateMachines.init();
        }
        if (GTCEu.isAE2Loaded()) {
            GTAEMachines.init();
        }
        if (GTCEu.isKubeJSLoaded()) {
            GTRegistryInfo.registerFor(GTRegistries.MACHINES.getRegistryName());
        }
        ModLoader.get().postEvent(new GTCEuAPI.RegisterEvent<>(GTRegistries.MACHINES, MachineDefinition.class));
        GTRegistries.MACHINES.freeze();
        ci.cancel();
    }


    @Inject(method = "registerSimpleGenerator", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void registerSimpleGenerator(String name, GTRecipeType recipeType, Int2LongFunction tankScalingFunction, float hazardStrengthPerOperation, int[] tiers, CallbackInfoReturnable<MachineDefinition[]> cir) {
        cir.setReturnValue(registerTieredMachines(name,
                (holder, tier) -> new SimpleGeneratorMachine(holder, tier, hazardStrengthPerOperation * tier,
                        tankScalingFunction),
                (tier, builder) -> builder
                        .langValue("%s %s Generator %s".formatted(VLVH[tier], toEnglishName(name), VLVT[tier]))
                        .editableUI(SimpleGeneratorMachine.EDITABLE_UI_CREATOR.apply(GTCEu.id(name), recipeType))
                        .rotationState(RotationState.ALL)
                        .recipeType(recipeType)
                        .recipeModifier(SimpleGeneratorMachine::recipeModifier, true)
                        .addOutputLimit(ItemRecipeCapability.CAP, 0)
                        .addOutputLimit(FluidRecipeCapability.CAP, 0)
                        .renderer(() -> new SimpleGeneratorMachineRenderer(tier, GTCEu.id("block/generators/" + name)))
                        .tooltips(Component.translatable("gtceu.machine.efficiency.tooltip",
                                (105 - 5 * tier)).append("%"))
                        .tooltips(Component.translatable("gtceu.universal.tooltip.ampere_out",
                                Math.max(1, (int) Math.pow(2, 5 - tier))))
                        .tooltips(workableTiered(tier, GTValues.V[tier],
                                GTValues.V[tier] * 64 * Math.max(1, (int) Math.pow(2, 5 - tier)), recipeType,
                                tankScalingFunction.apply(tier), false))
                        .compassNode(name)
                        .register(),
                tiers));
    }
}
