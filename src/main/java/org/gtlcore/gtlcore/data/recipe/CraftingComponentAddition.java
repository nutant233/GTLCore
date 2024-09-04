package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CraftingComponent;
import org.gtlcore.gtlcore.common.data.GTLItems;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static org.gtlcore.gtlcore.utils.Registries.getItem;
import static org.gtlcore.gtlcore.utils.Registries.getMaterial;

public final class CraftingComponentAddition {

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
                { 13, new UnificationEntry(TagPrefix.wireGtSingle, getMaterial("uruium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtSingle, getMaterial("uruium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.wireGtQuadruple, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_OCT.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.wireGtOctal, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_HEX.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.wireGtHex, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        WIRE_ELECTRIC.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_OCT.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtOctal, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_HEX.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.cableGtHex, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.cableGtSingle, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("taranium")) },
                { 12, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("crystalmatrix")) },
                { 13, new UnificationEntry(TagPrefix.cableGtSingle, getMaterial("cosmicneutronium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtSingle, getMaterial("spacetime")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.cableGtDouble, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("taranium")) },
                { 12, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("crystalmatrix")) },
                { 13, new UnificationEntry(TagPrefix.cableGtDouble, getMaterial("cosmicneutronium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("spacetime")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_QUAD.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.cableGtQuadruple, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("taranium")) },
                { 12, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("crystalmatrix")) },
                { 13, new UnificationEntry(TagPrefix.cableGtQuadruple, getMaterial("cosmicneutronium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("spacetime")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_OCT.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.cableGtOctal, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("taranium")) },
                { 12, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("crystalmatrix")) },
                { 13, new UnificationEntry(TagPrefix.cableGtOctal, getMaterial("cosmicneutronium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("spacetime")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        CABLE_TIER_UP_HEX.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.cableGtHex, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("taranium")) },
                { 12, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("crystalmatrix")) },
                { 13, new UnificationEntry(TagPrefix.cableGtHex, getMaterial("cosmicneutronium")) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("spacetime")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NORMAL.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeNormalFluid, getMaterial("enderium")) },
                { 12, new UnificationEntry(TagPrefix.pipeNormalFluid, getMaterial("enderium")) },
                { 13, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },
                { 14, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_LARGE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeLargeFluid, getMaterial("enderium")) },
                { 12, new UnificationEntry(TagPrefix.pipeLargeFluid, getMaterial("enderium")) },
                { 13, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },
                { 14, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_NONUPLE.appendIngredients(Stream.of(new Object[][] {
                { 0, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Bronze) },
                { 1, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Bronze) },
                { 2, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Steel) },
                { 3, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.StainlessSteel) },
                { 9, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.pipeNonupleFluid, GTMaterials.Neutronium) },
                { 11, new UnificationEntry(TagPrefix.pipeNonupleFluid, getMaterial("enderium")) },
                { 12, new UnificationEntry(TagPrefix.pipeNonupleFluid, getMaterial("enderium")) },
                { 13, new UnificationEntry(TagPrefix.pipeNonupleFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },
                { 14, new UnificationEntry(TagPrefix.pipeNonupleFluid,
                        getMaterial("heavy_quark_degenerate_matter")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        GLASS.appendIngredients(Stream.of(new Object[][] {
                { UHV, GTBlocks.FUSION_GLASS.asStack() },
                { UEV, GTBlocks.FUSION_GLASS.asStack() },
                { UIV, getItem("kubejs:force_field_glass") },
                { UXV, getItem("kubejs:force_field_glass") },
                { OpV, getItem("kubejs:force_field_glass") },
                { MAX, getItem("kubejs:force_field_glass") },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PLATE.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.plate, getMaterial("quantanium")) },
                { 11, new UnificationEntry(TagPrefix.plate, getMaterial("adamantium")) },
                { 12, new UnificationEntry(TagPrefix.plate, getMaterial("vibranium")) },
                { 13, new UnificationEntry(TagPrefix.plate, getMaterial("draconium")) },
                { 14, new UnificationEntry(TagPrefix.plate, getMaterial("chaos")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        HULL_PLATE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.plate, getMaterial("polyetheretherketone")) },
                { 10, new UnificationEntry(TagPrefix.plate, getMaterial("polyetheretherketone")) },
                { 11, new UnificationEntry(TagPrefix.plate, getMaterial("zylon")) },
                { 12, new UnificationEntry(TagPrefix.plate, getMaterial("zylon")) },
                { 13, new UnificationEntry(TagPrefix.plate, getMaterial("fullerene_polymer_matrix_pulp")) },
                { 14, new UnificationEntry(TagPrefix.plate, getMaterial("radox")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        ROTOR.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.rotor, GTMaterials.Neutronium) },
                { 10, new UnificationEntry(TagPrefix.rotor, getMaterial("quantanium")) },
                { 11, new UnificationEntry(TagPrefix.rotor, getMaterial("adamantium")) },
                { 12, new UnificationEntry(TagPrefix.rotor, getMaterial("vibranium")) },
                { 13, new UnificationEntry(TagPrefix.rotor, getMaterial("draconium")) },
                { 14, new UnificationEntry(TagPrefix.rotor, getMaterial("transcendentmetal")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("abyssalalloy")) },
                { 10, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("titansteel")) },
                { 11, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("adamantine")) },
                { 12, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("naquadriatictaranium")) },
                { 13, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("starmetal")) },
                { 14, new UnificationEntry(TagPrefix.wireGtDouble, getMaterial("hypogen")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_HEATING_DOUBLE.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("abyssalalloy")) },
                { 10, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("titansteel")) },
                { 11, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("adamantine")) },
                { 12, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("naquadriatictaranium")) },
                { 13, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("starmetal")) },
                { 14, new UnificationEntry(TagPrefix.wireGtQuadruple, getMaterial("hypogen")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        COIL_ELECTRIC.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("mithril")) },
                { 10, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("mithril")) },
                { 12, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("mithril")) },
                { 13, new UnificationEntry(TagPrefix.wireGtOctal, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.wireGtHex, getMaterial("crystalmatrix")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_DISTILLATION.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.spring, GTMaterials.Europium) },
                { 10, new UnificationEntry(TagPrefix.spring, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.spring, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.spring, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.spring, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.spring, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        STICK_ELECTROMAGNETIC.appendIngredients(Stream.of(new Object[][] {
                { 5, new UnificationEntry(TagPrefix.rod, GTMaterials.VanadiumGallium) },
                { 6, new UnificationEntry(TagPrefix.rod, GTMaterials.VanadiumGallium) },
                { 7, new UnificationEntry(TagPrefix.rod, GTMaterials.NiobiumTitanium) },
                { 8, new UnificationEntry(TagPrefix.rod, GTMaterials.NiobiumTitanium) },
                { 9, getItem("kubejs:netherite_rod") },
                { 10, getItem("kubejs:netherite_rod") },
                { 11, new UnificationEntry(TagPrefix.rod, getMaterial("mithril")) },
                { 12, new UnificationEntry(TagPrefix.rod, getMaterial("mithril")) },
                { 13, new UnificationEntry(TagPrefix.rod, getMaterial("echoite")) },
                { 14, new UnificationEntry(TagPrefix.rod, getMaterial("echoite")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        PIPE_REACTOR.appendIngredients(Stream.of(new Object[][] {
                { 9, new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Polybenzimidazole) },
                { 10, new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Polybenzimidazole) },
                { 11, new UnificationEntry(TagPrefix.pipeHugeFluid, GTMaterials.Polybenzimidazole) },
                { 12, new UnificationEntry(TagPrefix.pipeNormalFluid,
                        getMaterial("fullerene_polymer_matrix_pulp")) },
                { 13, new UnificationEntry(TagPrefix.pipeLargeFluid,
                        getMaterial("fullerene_polymer_matrix_pulp")) },
                { 14, new UnificationEntry(TagPrefix.pipeHugeFluid,
                        getMaterial("fullerene_polymer_matrix_pulp")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        POWER_COMPONENT.appendIngredients(Stream.of(new Object[][] {
                { 10, getItem("kubejs:nm_chip") },
                { 11, getItem("kubejs:pm_chip") },
                { 12, getItem("kubejs:pm_chip") },
                { 13, getItem("kubejs:fm_chip") },
                { 14, getItem("kubejs:fm_chip") },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        VOLTAGE_COIL.appendIngredients(Stream.of(new Object[][] {
                { 9, getItem("kubejs:uhv_voltage_coil") },
                { 10, getItem("kubejs:uev_voltage_coil") },
                { 11, getItem("kubejs:uiv_voltage_coil") },
                { 12, getItem("kubejs:uxv_voltage_coil") },
                { 13, getItem("kubejs:opv_voltage_coil") },
                { 14, getItem("kubejs:max_voltage_coil") },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));

        SPRING.appendIngredients(Stream.of(new Object[][] {
                { 10, new UnificationEntry(TagPrefix.spring, getMaterial("mithril")) },
                { 11, new UnificationEntry(TagPrefix.spring, GTMaterials.Neutronium) },
                { 12, new UnificationEntry(TagPrefix.spring, getMaterial("taranium")) },
                { 13, new UnificationEntry(TagPrefix.spring, getMaterial("crystalmatrix")) },
                { 14, new UnificationEntry(TagPrefix.spring, getMaterial("cosmicneutronium")) },

        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> data[1])));
    }
}
