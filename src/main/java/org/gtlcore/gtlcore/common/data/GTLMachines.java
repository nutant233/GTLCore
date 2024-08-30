package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.client.renderer.machine.MaintenanceHatchPartRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.RotorHolderMachineRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.common.data.GTCompassSections;
import com.gregtechceu.gtceu.common.machine.multiblock.part.RotorHolderPartMachine;
import com.gregtechceu.gtceu.data.lang.LangHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.gtlcore.gtlcore.common.machine.LightningRodMachine;
import org.gtlcore.gtlcore.common.machine.part.AutoConfigurationMaintenanceHatchPartMachine;
import org.gtlcore.gtlcore.common.machine.part.CleaningConfigurationMaintenanceHatchPartMachine;
import org.gtlcore.gtlcore.common.machine.part.GTLCleaningMaintenanceHatchPartMachine;
import org.gtlcore.gtlcore.common.machine.part.GTLSteamHatchPartMachine;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class GTLMachines {

    public static void init() {}

    static {
        REGISTRATE.creativeModeTab(() -> GTLCreativeModeTabs.GTL_CORE);
    }

    public static final MachineDefinition LARGE_STEAM_HATCH = REGISTRATE
            .machine("large_steam_input_hatch", holder -> new GTLSteamHatchPartMachine(holder, IO.IN, 8192, false))
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.STEAM)
            .overlaySteamHullRenderer("steam_hatch")
            .tooltips(Component.translatable("gtceu.machine.large_steam_input_hatch.tooltip.0"),
                    Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            8192 * FluidHelper.getBucket()),
                    Component.translatable("gtceu.machine.steam.steam_hatch.tooltip"))
            .compassSections(GTCompassSections.STEAM)
            .compassNode("steam_hatch")
            .register();

    public static final MachineDefinition MEGA_STEAM_INPUT_HATCH = REGISTRATE
            .machine("mega_steam_input_hatch", holder -> new GTLSteamHatchPartMachine(holder, IO.IN, 4096000, true))
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.IMPORT_FLUIDS)
            .overlaySteamHullRenderer("fluid_hatch.import")
            .tooltips(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            4096000 * FluidHelper.getBucket()),
                    Component.translatable("gtceu.machine.steam.steam_hatch.tooltip"))
            .compassSections(GTCompassSections.STEAM)
            .compassNode("steam_hatch")
            .register();

    public static final MachineDefinition MEGA_STEAM_OUTPUT_HATCH = REGISTRATE
            .machine("mega_steam_output_hatch", holder -> new GTLSteamHatchPartMachine(holder, IO.OUT, 4096000, true))
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.EXPORT_FLUIDS)
            .overlaySteamHullRenderer("fluid_hatch.export")
            .tooltips(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            4096000 * FluidHelper.getBucket()),
                    Component.translatable("gtceu.machine.steam.steam_hatch.tooltip"))
            .compassSections(GTCompassSections.STEAM)
            .compassNode("steam_hatch")
            .register();

    public static final MachineDefinition STERILE_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("sterile_cleaning_maintenance_hatch", GTLCleaningMaintenanceHatchPartMachine::SterileCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : GTLCleaningMaintenanceHatchPartMachine
                        .getCleanroomTypes(GTLCleaningMaintenanceHatchPartMachine.STERILE_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .renderer(() -> new MaintenanceHatchPartRenderer(7,
                    GTCEu.id("block/machine/part/maintenance.sterile_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition LAW_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("law_cleaning_maintenance_hatch", GTLCleaningMaintenanceHatchPartMachine::LawCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : GTLCleaningMaintenanceHatchPartMachine
                        .getCleanroomTypes(GTLCleaningMaintenanceHatchPartMachine.LAW_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .renderer(
                    () -> new MaintenanceHatchPartRenderer(10, GTCEu.id("block/machine/part/maintenance.law_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition AUTO_CONFIGURATION_MAINTENANCE_HATCH = REGISTRATE
            .machine("auto_configuration_maintenance_hatch", AutoConfigurationMaintenanceHatchPartMachine::new)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"))
            .renderer(() -> new MaintenanceHatchPartRenderer(5, GTCEu.id("block/machine/part/maintenance.full_auto")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition CLEANING_CONFIGURATION_MAINTENANCE_HATCH = REGISTRATE
            .machine("cleaning_configuration_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::Cleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .renderer(() -> new MaintenanceHatchPartRenderer(5, GTCEu.id("block/machine/part/maintenance.cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition STERILE_CONFIGURATION_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("sterile_configuration_cleaning_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::SterileCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.STERILE_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .renderer(() -> new MaintenanceHatchPartRenderer(9,
                    GTCEu.id("block/machine/part/maintenance.sterile_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition LAW_CONFIGURATION_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("law_configuration_cleaning_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::LawCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.LAW_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .renderer(
                    () -> new MaintenanceHatchPartRenderer(12, GTCEu.id("block/machine/part/maintenance.law_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition[] ROTOR_HOLDER = registerTieredMachines("rotor_holder",
            RotorHolderPartMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Rotor Holder".formatted(VNF[tier]))
                    .rotationState(RotationState.ALL)
                    .abilities(PartAbility.ROTOR_HOLDER)
                    .renderer(() -> new RotorHolderMachineRenderer(tier))
                    .tooltips(LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 0),
                            LangHandler.getFromMultiLang("gtceu.machine.rotor_holder.tooltip", 1),
                            Component.translatable("gtceu.universal.disabled"))
                    .compassNode("rotor_holder")
                    .register(),
            UHV, UEV);

    public static final MachineDefinition[] LASER_INPUT_HATCH_16384 = registerLaserHatch(IO.IN, 16384,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_16384 = registerLaserHatch(IO.OUT, 16384,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_65536 = registerLaserHatch(IO.IN, 65536,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_65536 = registerLaserHatch(IO.OUT, 65536,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_262144 = registerLaserHatch(IO.IN, 262144,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_262144 = registerLaserHatch(IO.OUT, 262144,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_1048576 = registerLaserHatch(IO.IN, 1048576,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_1048576 = registerLaserHatch(IO.OUT, 1048576,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_4194304 = registerLaserHatch(IO.IN, 4194304,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_4194304 = registerLaserHatch(IO.OUT, 4194304,
            PartAbility.OUTPUT_LASER);


    public static final MachineDefinition[] DEHYDRATOR = registerSimpleMachines("dehydrator",
            GTLRecipeTypes.DEHYDRATOR_RECIPES, defaultTankSizeFunction);

    public static final MachineDefinition[] LIGHTNING_ROD = registerTieredMachines(
            "lightning_rod",
            LightningRodMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Lightning Rod %s".formatted(VLVH[tier], VLVT[tier]))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .renderer(() -> new SimpleGeneratorMachineRenderer(tier,
                            GTCEu.id("block/generators/lightning_rod")))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.0"))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.1"))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.2"))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.ampere_out", 512))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                            FormattingUtil.formatNumbers(GTValues.V[tier - 1]), GTValues.VNF[tier - 1]))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                            FormattingUtil.formatNumbers((long) (48828 * Math.pow(4, tier)))))
                    .register(),
            EV, IV, LuV);
}
