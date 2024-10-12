package org.gtlcore.gtlcore.data;

import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CraftingComponent;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;

public final class CraftingComponentAddition {

    public static Component BUFFER;

    public static void init() {
        CraftingComponent.PUMP.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_PUMP_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.CONVEYOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.CONVEYOR_MODULE_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.MOTOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_MOTOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.PISTON.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ELECTRIC_PISTON_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.EMITTER.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.EMITTER_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.SENSOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.SENSOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.FIELD_GENERATOR.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.FIELD_GENERATOR_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CraftingComponent.ROBOT_ARM.appendIngredients(Stream.of(new Object[][] {
                { 14, GTLItems.ROBOT_ARM_MAX.asStack() },
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_ELECTRIC.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtSingle, GTMaterials.Mendelevium) },
                { 11, new UnificationEntry(TagPrefix.wireGtSingle, GTMaterials.Mendelevium) },
                { 12, new UnificationEntry(TagPrefix.wireGtSingle, GTMaterials.Mendelevium) },
                { 13, new UnificationEntry(TagPrefix.wireGtSingle, GTLMaterials.Uruium) },
                { 14, new UnificationEntry(TagPrefix.wireGtSingle, GTLMaterials.Uruium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.wireGtQuadruple, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_OCT.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.wireGtOctal, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_HEX.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.wireGtHex, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_ELECTRIC.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_OCT.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtOctal, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_HEX.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.cableGtHex, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Taranium) },
                { 12, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.Crystalmatrix) },
                { 13, new UnificationEntry(TagPrefix.cableGtSingle, GTLMaterials.CosmicNeutronium) },
                { 14, new UnificationEntry(TagPrefix.wireGtSingle, GTLMaterials.SpaceTime) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Taranium) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.Crystalmatrix) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, GTLMaterials.CosmicNeutronium) },
                { 14, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.SpaceTime) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Taranium) },
                { 12, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.Crystalmatrix) },
                { 13, new UnificationEntry(TagPrefix.cableGtQuadruple, GTLMaterials.CosmicNeutronium) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.SpaceTime) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_OCT.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.cableGtOctal, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Taranium) },
                { 12, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.Crystalmatrix) },
                { 13, new UnificationEntry(TagPrefix.cableGtOctal, GTLMaterials.CosmicNeutronium) },
                { 14, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.SpaceTime) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_HEX.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.cableGtHex, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Taranium) },
                { 12, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.Crystalmatrix) },
                { 13, new UnificationEntry(TagPrefix.cableGtHex, GTLMaterials.CosmicNeutronium) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.SpaceTime) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NORMAL.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeNormalFluid, GTLMaterials.Enderium) },
                { 12, new UnificationEntry(TagPrefix.pipeNormalFluid, GTLMaterials.Enderium) },
                { 13, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },
                { 14, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_LARGE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeLargeFluid, GTLMaterials.Enderium) },
                { 12, new UnificationEntry(TagPrefix.pipeLargeFluid, GTLMaterials.Enderium) },
                { 13, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },
                { 14, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NONUPLE.appendIngredients(Stream.of(new Object[][] {
                { 0, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Bronze) },
                { 1, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Bronze) },
                { 2, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Steel) },
                { 3, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.StainlessSteel) },
                { 9, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTLMaterials.Enderium) },
                { 12, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTLMaterials.Enderium) },
                { 13, new UnificationEntry(TagPrefix.pipeNonupleFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },
                { 14, new UnificationEntry(TagPrefix.pipeNonupleFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GLASS.appendIngredients(Stream.of(new Object[][] {
                { UHV, GTBlocks.FUSION_GLASS.asStack() },
                { UEV, GTBlocks.FUSION_GLASS.asStack() },
                { UIV, GTLBlocks.FORCE_FIELD_GLASS.asStack() },
                { UXV, GTLBlocks.FORCE_FIELD_GLASS.asStack() },
                { OpV, GTLBlocks.FORCE_FIELD_GLASS.asStack() },
                { MAX, GTLBlocks.FORCE_FIELD_GLASS.asStack() },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PLATE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.plate, GTLMaterials.Quantanium) },
                { 11, new UnificationEntry(TagPrefix.plate, GTLMaterials.Adamantium) },
                { 12, new UnificationEntry(TagPrefix.plate, GTLMaterials.Vibranium) },
                { 13, new UnificationEntry(TagPrefix.plate, GTLMaterials.Draconium) },
                { 14, new UnificationEntry(TagPrefix.plate, GTLMaterials.Chaos) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        HULL_PLATE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.plate, GTLMaterials.Polyetheretherketone) },
                { 10, new UnificationEntry(TagPrefix.plate, GTLMaterials.Polyetheretherketone) },
                { 11, new UnificationEntry(TagPrefix.plate, GTLMaterials.Zylon) },
                { 12, new UnificationEntry(TagPrefix.plate, GTLMaterials.Zylon) },
                { 13, new UnificationEntry(TagPrefix.plate,
                        GTLMaterials.FullerenePolymerMatrixPulp) },
                { 14, new UnificationEntry(TagPrefix.plate, GTLMaterials.Radox) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        ROTOR.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.rotor, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.rotor, GTLMaterials.Quantanium) },
                { 11, new UnificationEntry(TagPrefix.rotor, GTLMaterials.Adamantium) },
                { 12, new UnificationEntry(TagPrefix.rotor, GTLMaterials.Vibranium) },
                { 13, new UnificationEntry(TagPrefix.rotor, GTLMaterials.Draconium) },
                { 14, new UnificationEntry(TagPrefix.rotor, GTLMaterials.TranscendentMetal) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.AbyssalAlloy) },
                { 10, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.TitanSteel) },
                { 11, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.Adamantine) },
                { 12, new UnificationEntry(TagPrefix.wireGtDouble,
                        GTLMaterials.NaquadriaticTaranium) },
                { 13, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.Starmetal) },
                { 14, new UnificationEntry(TagPrefix.wireGtDouble, GTLMaterials.Hypogen) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.AbyssalAlloy) },
                { 10, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.TitanSteel) },
                { 11, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Adamantine) },
                { 12, new UnificationEntry(TagPrefix.wireGtQuadruple,
                        GTLMaterials.NaquadriaticTaranium) },
                { 13, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Starmetal) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, GTLMaterials.Hypogen) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_ELECTRIC.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Mithril) },
                { 10, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Mithril) },
                { 12, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Mithril) },
                { 13, new UnificationEntry(TagPrefix.wireGtOctal, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, GTLMaterials.Crystalmatrix) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_DISTILLATION.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.spring, GTMaterials.Europium) },
                { 10, new UnificationEntry(TagPrefix.spring, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.spring, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.spring, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.spring, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.spring, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_ELECTROMAGNETIC.appendIngredients(Stream.of(new Object[][] {
                { 5, new UnificationEntry(TagPrefix.rod, GTMaterials.VanadiumGallium) },
                { 6, new UnificationEntry(TagPrefix.rod, GTMaterials.VanadiumGallium) },
                { 7, new UnificationEntry(TagPrefix.rod, GTMaterials.NiobiumTitanium) },
                { 8, new UnificationEntry(TagPrefix.rod, GTMaterials.NiobiumTitanium) },
                { 9, GTLItems.NETHERITE_ROD.asStack() },
                { 10, GTLItems.NETHERITE_ROD.asStack() },
                { 11, new UnificationEntry(TagPrefix.rod, GTLMaterials.Mithril) },
                { 12, new UnificationEntry(TagPrefix.rod, GTLMaterials.Mithril) },
                { 13, new UnificationEntry(TagPrefix.rod, GTLMaterials.Echoite) },
                { 14, new UnificationEntry(TagPrefix.rod, GTLMaterials.Echoite) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_REACTOR.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Polybenzimidazole) },
                { 10, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Polybenzimidazole) },
                { 11, new UnificationEntry(TagPrefix.pipeHugeFluid, GTMaterials.Polybenzimidazole) },
                { 12, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        GTLMaterials.FullerenePolymerMatrixPulp) },
                { 13, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        GTLMaterials.FullerenePolymerMatrixPulp) },
                { 14, new UnificationEntry(TagPrefix.pipeHugeFluid,
                        GTLMaterials.FullerenePolymerMatrixPulp) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        POWER_COMPONENT.appendIngredients(Stream.of(new Object[][] {
                { 10, GTLItems.NM_CHIP.asStack() },
                { 11, GTLItems.PM_CHIP.asStack() },
                { 12, GTLItems.PM_CHIP.asStack() },
                { 13, GTLItems.FM_CHIP.asStack() },
                { 14, GTLItems.FM_CHIP.asStack() },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        VOLTAGE_COIL.appendIngredients(Stream.of(new Object[][] {
                { 9, GTLItems.UHV_VOLTAGE_COIL.asStack() },
                { 10, GTLItems.UEV_VOLTAGE_COIL.asStack() },
                { 11, GTLItems.UIV_VOLTAGE_COIL.asStack() },
                { 12, GTLItems.UXV_VOLTAGE_COIL.asStack() },
                { 13, GTLItems.OPV_VOLTAGE_COIL.asStack() },
                { 14, GTLItems.MAX_VOLTAGE_COIL.asStack() },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SPRING.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.spring, GTLMaterials.Mithril) },
                { 11, new UnificationEntry(TagPrefix.spring, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.spring, GTLMaterials.Taranium) },
                { 13, new UnificationEntry(TagPrefix.spring, GTLMaterials.Crystalmatrix) },
                { 14, new UnificationEntry(TagPrefix.spring, GTLMaterials.CosmicNeutronium) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        BUFFER = new Component(Stream.of(new Object[][] {

                { 1, GTMachines.BUFFER[1].asStack() },
                { 2, GTMachines.BUFFER[2].asStack() },
                { 3, GTMachines.BUFFER[3].asStack() },
                { 4, GTMachines.BUFFER[4].asStack() },
                { 5, GTMachines.BUFFER[5].asStack() },
                { 6, GTMachines.BUFFER[6].asStack() },
                { 7, GTMachines.BUFFER[7].asStack() },
                { 8, GTMachines.BUFFER[8].asStack() },
                { 9, GTMachines.BUFFER[9].asStack() },
                { 10, GTMachines.BUFFER[10].asStack() },
                { 11, GTMachines.BUFFER[11].asStack() },
                { 12, GTMachines.BUFFER[12].asStack() },
                { 13, GTMachines.BUFFER[13].asStack() },
                { 14, GTMachines.BUFFER[14].asStack() },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}
