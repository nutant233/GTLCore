package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.client.renderer.machine.MaintenanceHatchPartRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.data.machines.GTResearchMachines;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FluidDrillMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.gtlcore.gtlcore.api.machine.multiblock.GTLPartAbility;
import org.gtlcore.gtlcore.api.pattern.GTLPredicates;
import org.gtlcore.gtlcore.common.machine.generator.LightningRodMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.*;
import org.gtlcore.gtlcore.common.machine.multiblock.generator.ChemicalEnergyDevourerMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.generator.MegaTurbineMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.HeatExchangerMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.NeutronActivatorMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.PrimitiveOreMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.part.*;
import org.gtlcore.gtlcore.config.ConfigHolder;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.machine.multiblock.PartAbility.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.api.pattern.util.RelativeDirection.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.DUMMY_RECIPES;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static org.gtlcore.gtlcore.api.pattern.GTLPredicates.countBlock;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.*;
import static org.gtlcore.gtlcore.utils.Registries.getBlock;

public class GTLMachines {

    public static void init() {
        MachineDefinition hpca_computation_component = GTResearchMachines.HPCA_COMPUTATION_COMPONENT;
        hpca_computation_component.setTooltipBuilder(hpca_computation_component.getTooltipBuilder().andThen((itemStack, components) -> components.set(3, Component.translatable("gtceu.machine.hpca.component_type.computation_cwut", 8))));
        MachineDefinition hpca_advanced_computation_component = GTResearchMachines.HPCA_ADVANCED_COMPUTATION_COMPONENT;
        hpca_advanced_computation_component.setTooltipBuilder(hpca_advanced_computation_component.getTooltipBuilder().andThen((itemStack, components) -> components.set(3, Component.translatable("gtceu.machine.hpca.component_type.computation_cwut", 32))));
        MachineDefinition electric_blast_furnace = GTMachines.ELECTRIC_BLAST_FURNACE;
        electric_blast_furnace.setTooltipBuilder(electric_blast_furnace.getTooltipBuilder().andThen((itemStack, components) -> {
            components.add(1, Component.translatable("gtceu.machine.electric_blast_furnace.tooltip.a"));
            components.set(3, Component.translatable("gtceu.machine.perfect_oc"));
        }));
    }

    static {
        REGISTRATE.creativeModeTab(() -> GTCreativeModeTabs.MACHINE);
    }

    //////////////////////////////////////
    // *** Simple Machine ***//
    //////////////////////////////////////
    public static final MachineDefinition[] SEMI_FLUID_GENERATOR = registerSimpleGenerator("semi_fluid",
            SEMI_FLUID_GENERATOR_FUELS, genericGeneratorTankSizeFunction, 0.1f, GTValues.LV, GTValues.MV,
            GTValues.HV);

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

    //////////////////////////////////////
    // ********** Part **********//
    //////////////////////////////////////
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

    //////////////////////////////////////
    // ******* Multiblock *******//
    //////////////////////////////////////
    public final static MultiblockMachineDefinition ELECTRIC_IMPLOSION_COMPRESSOR = REGISTRATE
            .multiblock("electric_implosion_compressor", WorkableElectricMultiblockMachine::new)
            .langValue("Electric Implosion Compressor")
            .tooltips(Component.translatable("gtceu.machine.eut_multiplier.tooltip", 0.8))
            .tooltips(Component.translatable("gtceu.machine.duration_multiplier.tooltip", 0.6))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.electric_implosion_compressor")))
            .rotationState(RotationState.ALL)
            .recipeType(ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXX", "F###F", "F###F", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                    .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                    .aisle("XXXXX", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "XXMXX")
                    .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                    .aisle("XXSXX", "F###F", "F###F", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                    .where('S', controller(blocks(definition.get())))
                    .where('X',
                            blocks(CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(40)
                                    .or(autoAbilities(definition.getRecipeTypes()))
                                    .or(Predicates.autoAbilities(true, false, true)))
                    .where('P', blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where('G', blocks(FUSION_GLASS.get()))
                    .where('F', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel)))
                    .where('A', air())
                    .where('#', any())
                    .where('M', blocks(MUFFLER_HATCH[LuV].get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
            .compassSections(GTCompassSections.TIER[IV])
            .compassNodeSelf()
            .register();

    public static final MachineDefinition[] NEUTRON_ACCELERATOR = registerTieredMachines("neutron_accelerator",
            NeutronAcceleratorPartMachine::new,
            (tier, builder) -> builder
                    .langValue(VNF[tier] + "Neutron Accelerator")
                    .rotationState(RotationState.ALL)
                    .abilities(GTLPartAbility.NEUTRON_ACCELERATOR)
                    .tooltips(Component.translatable("gtceu.universal.tooltip.max_voltage_in", V[tier], VNF[tier]),
                            Component.translatable("gtceu.machine.neutron_accelerator.tooltip.0", V[tier] * 8 / 10),
                            Component.translatable("gtceu.machine.neutron_accelerator.tooltip.1"),
                            Component.translatable("gtceu.universal.tooltip.energy_storage_capacity", 2 * V[tier]))
                    .overlayTieredHullRenderer("neutron_accelerator")
                    .compassNode("neutron_accelerator")
                    .register(),
            GTMachines.ALL_TIERS);

    public final static MachineDefinition NEUTRON_SENSOR = REGISTRATE
            .machine("neutron_sensor", NeutronSensorPartMachine::new)
            .langValue("Neutron Sensor")
            .tier(GTValues.IV)
            .rotationState(RotationState.ALL)
            .tooltips(Component.translatable("gtceu.machine.neutron_sensor.tooltip.0"))
            .overlayTieredHullRenderer("neutron_sensor")
            .register();

    public static final MultiblockMachineDefinition NEUTRON_ACTIVATOR = REGISTRATE
            .multiblock("neutron_activator", NeutronActivatorMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.4"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.neutron_activator")))
            .recipeTypes(NEUTRON_ACTIVATOR_RECIPES)
            .recipeModifiers(((machine, recipe, params, result) -> NeutronActivatorMachine.recipeModifier(machine, recipe)))
            .appearanceBlock(CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start(RIGHT, BACK, UP)
                    .aisle("AAGAA", "ADDDA", "ADDDA", "ADDDA", "AAAAA")
                    .aisle("B   B", " EEE ", " EFE ", " EEE ", "B   B").setRepeatable(4, 200)
                    .aisle("CCCCC", "CDDDC", "CDDDC", "CDDDC", "CCCCC")
                    .where('G', controller(blocks(definition.getBlock())))
                    .where('A', blocks(CASING_STAINLESS_CLEAN.get())
                            .or(blocks(NEUTRON_SENSOR.get()).setMaxGlobalLimited(1))
                            .or(abilities(EXPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(abilities(EXPORT_ITEMS).setMaxGlobalLimited(2))
                            .or(abilities(GTLPartAbility.NEUTRON_ACCELERATOR).setMaxGlobalLimited(2))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(MAINTENANCE).setExactLimit(1)))
                    .where('B', frames(GTMaterials.Tungsten))
                    .where('C', blocks(CASING_STAINLESS_CLEAN.get())
                            .or(abilities(IMPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(abilities(IMPORT_ITEMS).setMaxGlobalLimited(2)))
                    .where('D', blocks(ForgeRegistries.BLOCKS
                            .getValue(new ResourceLocation("kubejs:process_machine_casing"))))
                    .where('E', blocks(CASING_LAMINATED_GLASS.get()))
                    .where('F', countBlock("SpeedPipe",
                            ForgeRegistries.BLOCKS.getValue(new ResourceLocation("kubejs:speeding_pipe"))))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                    GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition HEAT_EXCHANGER = REGISTRATE
            .multiblock("heat_exchanger", HeatExchangerMachine::new)
            .langValue("Heat Exchanger")
            .tooltips(Component.translatable("gtceu.machine.heat_exchanger.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.heat_exchanger.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.heat_exchanger")))
            .rotationState(RotationState.ALL)
            .recipeType(HEAT_EXCHANGER_RECIPES)
            .recipeModifiers((machine, recipe, params, result) -> HeatExchangerMachine.recipeModifier(machine, recipe))
            .appearanceBlock(CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle(" ASA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ")
                    .where('S', controller(blocks(definition.get())))
                    .where('A',
                            blocks(CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(98)
                                    .or(autoAbilities(definition.getRecipeTypes()))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where('C', blocks(CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where('B', blocks(CASING_LAMINATED_GLASS.get()))
                    .where('D', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.HSSG)))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
            .compassSections(GTCompassSections.TIER[IV])
            .compassNodeSelf()
            .register();

    public final static MultiblockMachineDefinition PRIMITIVE_VOID_ORE = ConfigHolder.INSTANCE.enablePrimitiveVoidOre ?
            REGISTRATE.multiblock("primitive_void_ore", PrimitiveOreMachine::new)
                    .langValue("Primitive Void Ore")
                    .tooltips(Component.literal("运行时根据维度每tick随机产出一组任意粗矿"))
                    .tooltips(Component.literal("支持主世界,下界,末地"))
                    .rotationState(RotationState.ALL)
                    .recipeType(PRIMITIVE_VOID_ORE_RECIPES)
                    .appearanceBlock(() -> Blocks.DIRT)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("XXX", "XXX", "XXX")
                            .aisle("XXX", "XAX", "XXX")
                            .aisle("XXX", "XSX", "XXX")
                            .where('S', controller(blocks(definition.get())))
                            .where('X',
                                    blocks(Blocks.DIRT)
                                            .or(Predicates.abilities(EXPORT_ITEMS))
                                            .or(Predicates.abilities(IMPORT_FLUIDS)))
                            .where('A', air())
                            .build())
                    .workableCasingRenderer(new ResourceLocation("minecraft:block/dirt"),
                            GTCEu.id("block/multiblock/gcym/large_extractor"))
                    .compassSections(GTCompassSections.TIER[LV])
                    .compassNodeSelf()
                    .register() :
            null;

    public static final MultiblockMachineDefinition[] FLUID_DRILLING_RIG = registerTieredMultis(
            "fluid_drilling_rig", FluidDrillMachine::new, (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("%s Fluid Drilling Rig %s".formatted(VLVH[tier], VLVT[tier]))
                    .recipeType(DUMMY_RECIPES)
                    .tooltips(
                            Component.translatable("gtceu.machine.fluid_drilling_rig.description"),
                            Component.translatable("gtceu.machine.fluid_drilling_rig.depletion",
                                    FormattingUtil.formatNumbers(100.0 / FluidDrillMachine.getDepletionChance(tier))),
                            Component.translatable("gtceu.universal.tooltip.energy_tier_range", GTValues.VNF[tier],
                                    GTValues.VNF[tier + 1]),
                            Component.translatable("gtceu.machine.fluid_drilling_rig.production",
                                    FluidDrillMachine.getRigMultiplier(tier),
                                    FormattingUtil.formatNumbers(FluidDrillMachine.getRigMultiplier(tier) * 1.5)))
                    .appearanceBlock(() -> FluidDrillMachine.getCasingState(tier))
                    .pattern((definition) -> FactoryBlockPattern.start()
                            .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                            .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                            .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                            .where('S', controller(blocks(definition.get())))
                            .where('X', blocks(FluidDrillMachine.getCasingState(tier)).setMinGlobalLimited(3)
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                            .setMaxGlobalLimited(2))
                                    .or(abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1)))
                            .where('C', blocks(FluidDrillMachine.getCasingState(tier)))
                            .where('F', blocks(FluidDrillMachine.getFrameState(tier)))
                            .where('#', any())
                            .build())
                    .workableCasingRenderer(FluidDrillMachine.getBaseTexture(tier),
                            GTCEu.id("block/multiblock/fluid_drilling_rig"))
                    .compassSections(GTCompassSections.TIER[MV])
                    .compassNode("fluid_drilling_rig")
                    .register(),
            ZPM);

    public static final MultiblockMachineDefinition LARGE_SEMI_FLUID_GENERATOR = registerLargeCombustionEngine(
            "large_semi_fluid_generator", EV,
            CASING_TITANIUM_STABLE, CASING_STEEL_GEARBOX, CASING_ENGINE_INTAKE,
            GTCEu.id("block/casings/solid/machine_casing_stable_titanium"),
            GTCEu.id("block/multiblock/generator/large_combustion_engine"));

    public final static MultiblockMachineDefinition CHEMICAL_ENERGY_DEVOURER = REGISTRATE
            .multiblock("chemical_energy_devourer", holder -> new ChemicalEnergyDevourerMachine(holder, IV))
            .rotationState(RotationState.ALL)
            .recipeTypes(GTRecipeTypes.COMBUSTION_GENERATOR_FUELS, SEMI_FLUID_GENERATOR_FUELS,
                    GTRecipeTypes.GAS_TURBINE_FUELS)
            .generator(true)
            .tooltips(Component.translatable(
                            "gtceu.universal.tooltip.base_production_eut", 2 * GTValues.V[GTValues.ZPM]),
                    Component.translatable(
                            "gtceu.universal.tooltip.uses_per_hour_lubricant", 2000),
                    Component.literal(
                            "提供§f120L/s§7的液态氧，并消耗§f双倍§7燃料以产生高达§f" + (2 * GTValues.V[GTValues.UV]) + "§7EU/t的功率。"),
                    Component.literal(
                            "再额外提供§f80L/s§7的四氧化二氮，并消耗§f四倍§7燃料以产生高达§f" + (2 * GTValues.V[GTValues.UHV]) + "§7EU/t的功率。"))
            .recipeModifier((machine, recipe, params, result) -> ChemicalEnergyDevourerMachine.recipeModifier(machine, recipe), true)
            .appearanceBlock(CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBBBBBB", "BBBBBBB", "BBPBPBB", "BBBBBBB", "BBPBPBB", "BBBBBBB", "BBBBBBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "BGHEHGB", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "CGHEHGC", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "CGHEHGC", "BEHHHEB", "BDDDDDB", "BBIBIBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "BGHEHGB", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("AAAAAAA", "AAAAAAA", "AABBBAA", "AABSBAA", "AABBBAA", "AAAAAAA", "AAAAAAA")
                    .where("S", controller(blocks(definition.get())))
                    .where("A", blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                    .where("B", blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .where("F", blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4)))
                    .where("C", blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("G", blocks(GCyMBlocks.ELECTROLYTIC_CELL.get()))
                    .where("D", blocks(GTBlocks.FIREBOX_TITANIUM.get()))
                    .where("E", blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("H", blocks(GTBlocks.CASING_TITANIUM_GEARBOX.get()))
                    .where("P", abilities(PartAbility.OUTPUT_ENERGY))
                    .where("I", abilities(PartAbility.MUFFLER))
                    .build())
            .recoveryItems(
                    () -> new ItemLike[]{GTItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/generator/extreme_combustion_engine"), false)
            .compassSections(GTCompassSections.TIER[IV])
            .compassNode("large_combustion")
            .register();

    public final static MultiblockMachineDefinition ADVANCED_ASSEMBLY_LINE = REGISTRATE
            .multiblock("advanced_assembly_line", GTLAssemblyLineMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ASSEMBLY_LINE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.advanced_assembly_line.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.advanced_assembly_line.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.assembly_line.tooltip.0"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.assembly_line")))
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, (machine, recipe, params, result) -> GTLAssemblyLineMachine.recipeModifier(machine, recipe))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition ->
                    FactoryBlockPattern.start(BACK, UP, RIGHT)
                            .aisle("FIF", "RTR", "SAG", "#Y#")
                            .aisle("FIF", "RTR", "DAG", "#Y#").setRepeatable(7, 63)
                            .aisle("FOF", "RTR", "DAG", "#Y#")
                            .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("F", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .where("O", Predicates.abilities(PartAbility.EXPORT_ITEMS).addTooltips(Component.translatable("gtceu.multiblock.pattern.location_end")))
                            .where("Y", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)))
                            .where("I", Predicates.blocks(ITEM_IMPORT_BUS[0].get()).or(Predicates.blocks(getBlock("gtmthings:ulv_huge_item_import_bus"))))
                            .where("G", Predicates.blocks(CASING_GRATE.get()))
                            .where("D", Predicates.blocks(CASING_GRATE.get())
                                    .or(Predicates.abilities(PartAbility.OPTICAL_DATA_RECEPTION).setExactLimit(1)))
                            .where("A", Predicates.blocks(CASING_ASSEMBLY_CONTROL.get()))
                            .where("R", Predicates.blocks(CASING_ASSEMBLY_LINE.get()))
                            .where("T", GTLPredicates.countBlock("Unit", getBlock("kubejs:advanced_assembly_line_unit")))
                            .where("#", Predicates.any())
                            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public static MultiblockMachineDefinition registerMegaTurbine(String name, int tier, int value, GTRecipeType recipeType,
                                                                  Supplier<Block> casing, Supplier<Block> gear, ResourceLocation baseCasing,
                                                                  ResourceLocation overlayModel) {
        return REGISTRATE.multiblock(name, holder -> new MegaTurbineMachine(holder, tier, value))
                .rotationState(RotationState.ALL)
                .recipeType(recipeType)
                .generator(true)
                .tooltips(Component.literal("可使用变电动力仓"))
                .tooltips(Component.translatable("gtceu.universal.tooltip.base_production_eut", FormattingUtil.formatNumbers(GTValues.V[tier] * value)))
                .tooltips(Component.translatable("gtceu.multiblock.turbine.efficiency_tooltip", GTValues.VNF[tier]))
                .recipeModifier(MegaTurbineMachine::recipeModifier)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAAAAAA", "AAAAAAA", "AAEAEAA", "AAAAAAA", "AAEAEAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAMAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAASAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                        .where("A", Predicates.blocks(casing.get())
                                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8))
                                .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2)))
                        .where("B", GTLPredicates.RotorBlock())
                        .where("D", Predicates.blocks(gear.get()))
                        .where("E", Predicates.abilities(PartAbility.OUTPUT_ENERGY).or(Predicates.abilities(PartAbility.SUBSTATION_OUTPUT_ENERGY)))
                        .where("M", Predicates.abilities(PartAbility.MUFFLER))
                        .build())
                .workableCasingRenderer(baseCasing, overlayModel)
                .register();
    }

    public final static MultiblockMachineDefinition ROCKET_LARGE_TURBINE = GTMachines.registerLargeTurbine("rocket_large_turbine", GTValues.EV,
            GTLRecipeTypes.ROCKET_ENGINE_FUELS,
            GTBlocks.CASING_TITANIUM_TURBINE, GTBlocks.CASING_TITANIUM_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"),
            GTCEu.id("block/multiblock/generator/large_gas_turbine"));

    public final static MultiblockMachineDefinition SUPERCRITICAL_STEAM_TURBINE = GTMachines.registerLargeTurbine("supercritical_steam_turbine", GTValues.LuV,
            GTLRecipeTypes.SUPERCRITICAL_STEAM_TURBINE_FUELS,
            GTLBlocks.CASING_SUPERCRITICAL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            new ResourceLocation("kubejs:block/supercritical_turbine_casing"),
            GTCEu.id("block/multiblock/generator/large_plasma_turbine"));

    public final static MultiblockMachineDefinition STEAM_MEGA_TURBINE = registerMegaTurbine("steam_mega_turbine", GTValues.EV, 32, GTRecipeTypes.STEAM_TURBINE_FUELS, GTBlocks.CASING_STEEL_TURBINE, GTBlocks.CASING_STEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_steel"), GTCEu.id("block/multiblock/generator/large_steam_turbine"));
    public final static MultiblockMachineDefinition GAS_MEGA_TURBINE = registerMegaTurbine("gas_mega_turbine", GTValues.IV, 32, GTRecipeTypes.GAS_TURBINE_FUELS, GTBlocks.CASING_STAINLESS_TURBINE, GTBlocks.CASING_STAINLESS_STEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_stainless_steel"), GTCEu.id("block/multiblock/generator/large_gas_turbine"));
    public final static MultiblockMachineDefinition ROCKET_MEGA_TURBINE = registerMegaTurbine("rocket_mega_turbine", GTValues.IV, 64, GTLRecipeTypes.ROCKET_ENGINE_FUELS, GTBlocks.CASING_TITANIUM_TURBINE, GTBlocks.CASING_STAINLESS_STEEL_GEARBOX,
            GTCEu.id("gblock/casings/mechanic/machine_casing_turbine_titanium"), GTCEu.id("block/multiblock/generator/large_gas_turbine"));
    public final static MultiblockMachineDefinition PLASMA_MEGA_TURBINE = registerMegaTurbine("plasma_mega_turbine", GTValues.LuV, 64, GTRecipeTypes.PLASMA_GENERATOR_FUELS, GTBlocks.CASING_TUNGSTENSTEEL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_tungstensteel"), GTCEu.id("block/multiblock/generator/large_plasma_turbine"));
    public final static MultiblockMachineDefinition SUPERCRITICAL_MEGA_STEAM_TURBINE = registerMegaTurbine("supercritical_mega_steam_turbine", GTValues.ZPM, 128, GTLRecipeTypes.SUPERCRITICAL_STEAM_TURBINE_FUELS, GTLBlocks.CASING_SUPERCRITICAL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            new ResourceLocation("kubejs:block/supercritical_turbine_casing"), GTCEu.id("block/multiblock/generator/large_plasma_turbine"));

    public final static MultiblockMachineDefinition FISSION_REACTOR = REGISTRATE.multiblock("fission_reactor", FissionReactorMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.FISSION_REACTOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.4"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.5"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.6"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.7"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.8"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.9"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.10"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.11"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.12"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.13"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.14"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.15"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.fission_reactor")))
            .recipeModifier((machine, recipe, params, result) -> FissionReactorMachine.recipeModifier(machine, recipe))
            .appearanceBlock(() -> getBlock("kubejs:fission_reactor_casing"))
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("AAAAAAAAA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAA~AAAA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "AAAAAAAAA")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("A", Predicates.blocks(getBlock("kubejs:fission_reactor_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1)))
                            .where("B", Predicates.blocks(CASING_LAMINATED_GLASS.get()).or(Predicates.blocks(getBlock("kubejs:fission_reactor_casing"))))
                            .where("C", Predicates.air().or(GTLPredicates.countBlock("FuelAssembly", getBlock("gtlcore:fission_fuel_assembly")))
                                    .or(GTLPredicates.countBlock("Cooler", getBlock("gtlcore:cooler"))))
                            .build())
            .workableCasingRenderer(new ResourceLocation("kubejs:block/fission_reactor_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition STELLAR_FORGE = REGISTRATE.multiblock("stellar_forge", (holder) -> new TierCasingMachine(holder, "SCTier"))
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(STELLAR_FORGE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.stellar_forge")))
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK))
            .appearanceBlock(GCyMBlocks.CASING_ATOMIC)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("               ", "      bbb      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      bbb      ", "               ")
                    .aisle("      b b      ", "     ccccc     ", "               ", "               ", "               ", "               ", "               ", "     ccccc     ", "      b b      ")
                    .aisle("      b b      ", "   cc     cc   ", "               ", "               ", "               ", "               ", "               ", "   cc     cc   ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "     ccccc     ", "               ", "               ", "               ", "     ccccc     ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "    c ddd c    ", "      ddd      ", "      ddd      ", "      ddd      ", "    c ddd c    ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", " c    ddd    c ", "   c d   d c   ", "     d   d     ", "     d   d     ", "     d   d     ", "   c d   d c   ", " c    ddd    c ", "      b b      ")
                    .aisle(" bbbbbbbbbbbbb ", "bc   ddddd   cb", "b  cd     dc  b", "b   d     d   b", "b   d     d   b", "b   d     d   b", "b  cd     dc  b", "bc   ddddd   cb", " bbbbbbbbbbbbb ")
                    .aisle("      bbb      ", "bc   ddddd   cb", "   cd     dc   ", "    d     d    ", "    d     d    ", "    d     d    ", "   cd     dc   ", "bc   ddddd   cb", "      bbb      ")
                    .aisle(" bbbbbbbbbbbbb ", "bc   ddddd   cb", "b  cd     dc  b", "b   d     d   b", "b   d     d   b", "b   d     d   b", "b  cd     dc  b", "bc   ddddd   cb", " bbbbbbbbbbbbb ")
                    .aisle("      b b      ", " c    ddd    c ", "   c d   d c   ", "     d   d     ", "     d   d     ", "     d   d     ", "   c d   d c   ", " c    ddd    c ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "    c ddd c    ", "      ddd      ", "      ddd      ", "      ddd      ", "    c ddd c    ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "     ccccc     ", "               ", "               ", "               ", "     ccccc     ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "   cc     cc   ", "               ", "               ", "               ", "               ", "               ", "   cc     cc   ", "      b b      ")
                    .aisle("      b b      ", "     ccccc     ", "               ", "               ", "               ", "               ", "               ", "     ccccc     ", "      b b      ")
                    .aisle("               ", "      bab      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      bbb      ", "               ")
                    .where("a", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("b", Predicates.blocks(GCyMBlocks.CASING_ATOMIC.get())
                            .setMinGlobalLimited(150)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("c", Predicates.blocks(FUSION_COIL.get()))
                    .where("d", GTLPredicates.tierCasings(GTLBlocks.scmap, "SCTier"))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/atomic_casing"), GTCEu.id("block/multiblock/electric_blast_furnace"))
            .register();

    public final static MultiblockMachineDefinition SPACE_ELEVATOR = REGISTRATE.multiblock("space_elevator", SpaceElevator::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(SPACE_ELEVATOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.space_elevator")))
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(new OverclockingLogic(1, 4, false)))
            .appearanceBlock(() -> getBlock("kubejs:space_elevator_mechanical_casing"))
            .pattern(definition ->
                    FactoryBlockPattern.start(RIGHT, DOWN, FRONT)
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                XXX                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                            .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("              FD - DF              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "             F C - C F             ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              XC - CX              ", "              XC - CX              ", "              XC - CX              ", "           FFFFDDMDDFFFF           ", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                            .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                X~X                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("X", Predicates.blocks(getBlock("kubejs:space_elevator_mechanical_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where("E", Predicates.blocks(getBlock("kubejs:space_elevator_support")))
                            .where("H", Predicates.blocks(getBlock("gtceu:neutronium_frame")))
                            .where("F", Predicates.blocks(getBlock("kubejs:space_elevator_internal_support")))
                            .where("C", GTLPredicates.tierActiveCasings(GTLBlocks.sepmmap, "SEPMTier"))
                            .where("A", Predicates.blocks(getBlock("kubejs:high_strength_concrete")))
                            .where("D", Predicates.blocks(getBlock("kubejs:space_elevator_mechanical_casing")))
                            .where("M", Predicates.blocks(getBlock("gtlcore:power_core")))
                            .where("G", Predicates.blocks(getBlock("kubejs:module_base")))
                            .where("V", Predicates.any().or(Predicates.blocks(getBlock("kubejs:module_connector")).setPreviewCount(1)))
                            .where("-", Predicates.air())
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(new ResourceLocation("kubejs:block/space_elevator_mechanical_casing"), GTCEu.id("block/space_elevator"))
            .register();

    public final static MultiblockMachineDefinition COMPONENT_ASSEMBLY_LINE = REGISTRATE.multiblock("component_assembly_line", (holder) -> new TierCasingMachine(holder, "CATier"))
            .rotationState(RotationState.ALL)
            .recipeType(COMPONENT_ASSEMBLY_LINE_RECIPES)
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.component_assembly_line")))
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
            .appearanceBlock(() -> getBlock("kubejs:iridium_casing"))
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("AAAAAAAAA", "A  NNN  A", "A       A", "A       A", "A       A", "A       A", "AA     AA", " AAAAAAA ", "         ", "         ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GHAAAHG ", "         ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F  M M  F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GHAAAHG ", "         ")
                            .aisle("AAAAAAAAA", "A  B B  A", "A  CCC  A", "A  CCC  A", "A       A", "A       A", "AA DDD AA", " AAD~DAA ", "   DDD   ", "         ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("A", Predicates.blocks(getBlock("kubejs:iridium_casing")))
                            .where("B", Predicates.blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where("C", Predicates.blocks(getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where("D", Predicates.blocks(getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where("F", Predicates.blocks(getBlock("kubejs:hsss_reinforced_borosilicate_glass")))
                            .where("G", Predicates.blocks(FILTER_CASING.get()))
                            .where("H", Predicates.blocks(getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1)))
                            .where("I", Predicates.blocks(getBlock("gtceu:hastelloy_n_frame")))
                            .where("J", Predicates.blocks(getBlock("kubejs:advanced_assembly_line_unit")))
                            .where("K", GTLPredicates.tierCasings(GTLBlocks.calmap, "CATier"))
                            .where("L", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                            .where("M", Predicates.blocks(getBlock("gtceu:tungsten_steel_frame")))
                            .where("N", Predicates.blocks(getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS)))
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(new ResourceLocation("kubejs:block/iridium_casing"), GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public final static MultiblockMachineDefinition SLAUGHTERHOUSE = REGISTRATE.multiblock("slaughterhouse", Slaughterhouse::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(new OverclockingLogic(1, 4, false)))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .recipeType(SLAUGHTERHOUSE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.4"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.5"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.slaughterhouse")))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "AAAAAAA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "AAA~AAA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "AAAAAAA")
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(4))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.blocks(CASING_STEEL_GEARBOX.get()))
                    .where("D", Predicates.blocks(Blocks.IRON_BARS))
                    .where("E", Predicates.blocks(FIREBOX_STEEL.get()))
                    .where(" ", Predicates.air())
                    .build())
            .workableCasingRenderer( GTCEu.id("block/casings/solid/machine_casing_solid_steel"),  GTCEu.id("block/multiblock/gcym/large_cutter"))
            .register();
}
