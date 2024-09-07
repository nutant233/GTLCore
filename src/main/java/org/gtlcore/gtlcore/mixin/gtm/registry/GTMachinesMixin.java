package org.gtlcore.gtlcore.mixin.gtm.registry;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.renderer.machine.RotorHolderMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.machines.GTAEMachines;
import com.gregtechceu.gtceu.common.data.machines.GTCreateMachines;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.integration.kjs.GTRegistryInfo;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModLoader;
import org.gtlcore.gtlcore.common.data.machines.GCyMMachines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;
import java.util.function.BiFunction;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;


@Mixin(GTMachines.class)
public class GTMachinesMixin {
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

    @Unique
    private static MachineDefinition[] gTLCore$registerTieredMachines(String name,
                                                                      BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory,
                                                                      BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder,
                                                                      int... tiers) {
        MachineDefinition[] definitions = new MachineDefinition[GTValues.TIER_COUNT];
        for (int tier : tiers) {
            var register = REGISTRATE
                    .machine(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + name,
                            holder -> factory.apply(holder, tier))
                    .tier(tier);
            definitions[tier] = builder.apply(tier, register);
        }
        return definitions;
    }

    @Inject(method = "registerTieredMachines", at = @At("HEAD"), remap = false, cancellable = true)
    private static void registerTieredMachines(String name, BiFunction<IMachineBlockEntity, Integer, MetaMachine> factory, BiFunction<Integer, MachineBuilder<MachineDefinition>, MachineDefinition> builder, int[] tiers, CallbackInfoReturnable<MachineDefinition[]> cir) {
        switch (name) {
            case "input_bus" :
                cir.setReturnValue(gTLCore$registerTieredMachines("input_bus",
                        (holder, tier) -> new ItemBusPartMachine(holder, tier, IO.IN),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " Input Bus")
                                .rotationState(RotationState.ALL)
                                .abilities(
                                        tier == 0 ? new PartAbility[] { PartAbility.IMPORT_ITEMS, PartAbility.STEAM_IMPORT_ITEMS } :
                                                new PartAbility[] { PartAbility.IMPORT_ITEMS })
                                .overlayTieredHullRenderer("item_bus.import")
                                .tooltips(Component.translatable("gtceu.machine.item_bus.import.tooltip"),
                                        Component.translatable("gtceu.universal.tooltip.item_storage_capacity",
                                                (1 + tier) * (1 + tier)))
                                .compassNode("item_bus")
                                .register(),
                        ALL_TIERS));
                break;
            case "output_bus" :
                cir.setReturnValue(gTLCore$registerTieredMachines("output_bus",
                        (holder, tier) -> new ItemBusPartMachine(holder, tier, IO.OUT),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " Output Bus")
                                .rotationState(RotationState.ALL)
                                .abilities(
                                        tier == 0 ? new PartAbility[] { PartAbility.EXPORT_ITEMS, PartAbility.STEAM_EXPORT_ITEMS } :
                                                new PartAbility[] { PartAbility.EXPORT_ITEMS })
                                .overlayTieredHullRenderer("item_bus.export")
                                .tooltips(Component.translatable("gtceu.machine.item_bus.export.tooltip"),
                                        Component.translatable("gtceu.universal.tooltip.item_storage_capacity",
                                                (1 + tier) * (1 + tier)))
                                .compassNode("item_bus")
                                .register(),
                        ALL_TIERS));
                break;
            case "energy_input_hatch_4a" :
                cir.setReturnValue(gTLCore$registerTieredMachines("energy_input_hatch_4a",
                        (holder, tier) -> new EnergyHatchPartMachine(holder, tier, IO.IN, 4),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " 4A Energy Hatch")
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.INPUT_ENERGY)
                                .tooltips(Component.translatable("gtceu.machine.energy_hatch.input_hi_amp.tooltip"))
                                .overlayTieredHullRenderer("energy_hatch.input_4a")
                                .compassNode("energy_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "energy_output_hatch_4a" :
                cir.setReturnValue(gTLCore$registerTieredMachines("energy_output_hatch_4a",
                        (holder, tier) -> new EnergyHatchPartMachine(holder, tier, IO.OUT, 4),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " 4A Dynamo Hatch")
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.OUTPUT_ENERGY)
                                .tooltips(Component.translatable("gtceu.machine.energy_hatch.output_hi_amp.tooltip"))
                                .overlayTieredHullRenderer("energy_hatch.output_4a")
                                .compassNode("energy_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "energy_input_hatch_16a" :
                cir.setReturnValue(gTLCore$registerTieredMachines("energy_input_hatch_16a",
                        (holder, tier) -> new EnergyHatchPartMachine(holder, tier, IO.IN, 16),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " 16A Energy Hatch")
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.INPUT_ENERGY)
                                .tooltips(Component.translatable("gtceu.machine.energy_hatch.input_hi_amp.tooltip"))
                                .overlayTieredHullRenderer("energy_hatch.input_16a")
                                .compassNode("energy_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "energy_output_hatch_16a" :
                cir.setReturnValue(gTLCore$registerTieredMachines("energy_output_hatch_16a",
                        (holder, tier) -> new EnergyHatchPartMachine(holder, tier, IO.OUT, 16),
                        (tier, builder1) -> builder1
                                .langValue(VNF[tier] + " 16A Dynamo Hatch")
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.OUTPUT_ENERGY)
                                .tooltips(Component.translatable("gtceu.machine.energy_hatch.output_hi_amp.tooltip"))
                                .overlayTieredHullRenderer("energy_hatch.output_16a")
                                .compassNode("energy_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "dual_input_hatch" :
                cir.setReturnValue(gTLCore$registerTieredMachines(
                        "dual_input_hatch",
                        (holder, tier) -> new DualHatchPartMachine(holder, tier, IO.IN),
                        (tier, builder1) -> builder1
                                .langValue("%s Dual Input Hatch".formatted(VNF[tier]))
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.IMPORT_ITEMS)
                                .overlayTieredHullRenderer("dual_hatch.import")
                                .tooltips(
                                        Component.translatable("gtceu.machine.buffer.import.tooltip"),
                                        Component.translatable(
                                                "gtceu.universal.tooltip.item_storage_capacity",
                                                (1 + tier) * (1 + tier)),
                                        Component.translatable(
                                                "gtceu.universal.tooltip.fluid_storage_capacity_mult",
                                                1 + tier,
                                                FluidHatchPartMachine.getTankCapacity(
                                                        DualHatchPartMachine.INITIAL_TANK_CAPACITY, tier)),
                                        Component.translatable("gtceu.universal.enabled"))
                                .compassNode("dual_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "dual_output_hatch" :
                cir.setReturnValue(gTLCore$registerTieredMachines(
                        "dual_output_hatch",
                        (holder, tier) -> new DualHatchPartMachine(holder, tier, IO.OUT),
                        (tier, builder1) -> builder1
                                .langValue("%s Dual Output Hatch".formatted(VNF[tier]))
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.EXPORT_ITEMS)
                                .overlayTieredHullRenderer("dual_hatch.export")
                                .tooltips(
                                        Component.translatable("gtceu.machine.buffer.export.tooltip"),
                                        Component.translatable(
                                                "gtceu.universal.tooltip.item_storage_capacity",
                                                (1 + tier) * (1 + tier)),
                                        Component.translatable(
                                                "gtceu.universal.tooltip.fluid_storage_capacity_mult",
                                                1 + tier,
                                                FluidHatchPartMachine.getTankCapacity(
                                                        DualHatchPartMachine.INITIAL_TANK_CAPACITY, tier)),
                                        Component.translatable("gtceu.universal.enabled"))
                                .compassNode("dual_hatch")
                                .register(),
                        GTValues.tiersBetween(LV, GTCEuAPI.isHighTier() ? MAX : UHV)));
                break;
            case "rotor_holder" :
                cir.setReturnValue(gTLCore$registerTieredMachines("rotor_holder",
                        RotorHolderPartMachine::new,
                        (tier, builder1) -> builder1
                                .langValue("%s Rotor Holder".formatted(VNF[tier]))
                                .rotationState(RotationState.ALL)
                                .abilities(PartAbility.ROTOR_HOLDER)
                                .renderer(() -> new RotorHolderMachineRenderer(tier))
                                .tooltips(LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 0),
                                        LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 1),
                                        Component.translatable("gtceu.universal.disabled"))
                                .compassNode("rotor_holder")
                                .register(),
                        HV, EV, IV, LuV, ZPM, UV ,UHV, UEV));
        }
    }

    @Inject(method = "registerLaserHatch", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void registerLaserHatch(IO io, int amperage, PartAbility ability, CallbackInfoReturnable<MachineDefinition[]> cir) {
        String name = io == IO.IN ? "target" : "source";
        cir.setReturnValue(gTLCore$registerTieredMachines(amperage + "a_laser_" + name + "_hatch",
                (holder, tier) -> new LaserHatchPartMachine(holder, io, tier, amperage), (tier, builder) -> builder
                        .langValue(VNF[tier] + " " + FormattingUtil.formatNumbers(amperage) + "A Laser " +
                                FormattingUtil.toEnglishName(name) + " Hatch")
                        .rotationState(RotationState.ALL)
                        .tooltips(Component.translatable("gtceu.machine.laser_hatch." + name + ".tooltip"),
                                Component.translatable("gtceu.machine.laser_hatch.both.tooltip"),
                                Component.translatable("gtceu.universal.disabled"))
                        .abilities(ability)
                        .overlayTieredHullRenderer("laser_hatch." + name)
                        .register(),
                GTValues.tiersBetween(IV, GTCEuAPI.isHighTier() ? MAX : UHV)));
    }
}
