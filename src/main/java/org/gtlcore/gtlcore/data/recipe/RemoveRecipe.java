package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.resources.ResourceLocation;

import appeng.core.AppEng;
import committee.nova.mods.avaritia.Static;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.VN;
import static org.gtlcore.gtlcore.common.data.GTLRecipes.DISASSEMBLY_RECORD;

public class RemoveRecipe {

    public static void init(Consumer<ResourceLocation> consumer) {
        DISASSEMBLY_RECORD.clear();
        consumer.accept(new ResourceLocation("minecraft", "infinity"));
        consumer.accept(new ResourceLocation("minecraft", "crystal_matrix_ingot"));
        consumer.accept(Static.rl("crystal_matrix_ingot"));
        consumer.accept(AppEng.makeId("decorative/quartz_block"));
        consumer.accept(AppEng.makeId("decorative/fluix_block"));
        consumer.accept(AppEng.makeId("misc/deconstruction_certus_quartz_block"));
        consumer.accept(AppEng.makeId("misc/deconstruction_fluix_block"));
        consumer.accept(AppEng.makeId("misc/fluixpearl"));
        consumer.accept(AppEng.makeId("network/cables/glass_fluix"));
        consumer.accept(AppEng.makeId("network/blocks/controller"));
        consumer.accept(AppEng.makeId("network/crafting/patterns_blank"));
        consumer.accept(AppEng.makeId("network/parts/export_bus"));
        consumer.accept(AppEng.makeId("network/parts/import_bus"));
        consumer.accept(AppEng.makeId("network/wireless_part"));
        consumer.accept(AppEng.makeId("network/crafting/cpu_crafting_unit"));
        consumer.accept(AppEng.makeId("materials/annihilationcore"));
        consumer.accept(AppEng.makeId("materials/formationcore"));
        consumer.accept(AppEng.makeId("materials/advancedcard"));
        consumer.accept(AppEng.makeId("materials/basiccard"));

        consumer.accept(GTCEu.id("shaped/maintenance_hatch_cleaning"));
        consumer.accept(GTCEu.id("shaped/vacuum_tube"));
        consumer.accept(GTCEu.id("packer/unpackage_lv_cadmium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_lv_lithium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_lv_sodium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_mv_cadmium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_mv_lithium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_mv_sodium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_hv_cadmium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_hv_lithium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_hv_sodium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_ev_vanadium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_iv_vanadium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_luv_vanadium_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_zpm_naquadria_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_uv_naquadria_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_ev_lapotronic_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_iv_lapotronic_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_luv_lapotronic_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_zpm_lapotronic_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_uv_lapotronic_battery"));
        consumer.accept(GTCEu.id("packer/unpackage_uhv_ultimate_battery"));
        consumer.accept(GTCEu.id("macerator/macerate_wheat"));
        consumer.accept(GTCEu.id("autoclave/agar"));
        consumer.accept(GTCEu.id("assembler/wool_from_string"));
        consumer.accept(GTCEu.id("assembly_line/dynamo_hatch_uhv"));
        consumer.accept(GTCEu.id("assembly_line/energy_hatch_uhv"));
        consumer.accept(GTCEu.id("research_station/1_x_gtceu_uv_energy_input_hatch"));
        consumer.accept(GTCEu.id("research_station/1_x_gtceu_uv_energy_output_hatch"));
        consumer.accept(GTCEu.id("mixer/ender_pearl_dust"));
        consumer.accept(GTCEu.id("mixer/rocket_fuel_from_dinitrogen_tetroxide"));
        consumer.accept(GTCEu.id("chemical_reactor/stem_cells"));
        consumer.accept(GTCEu.id("large_chemical_reactor/stem_cells"));
        consumer.accept(GTCEu.id("forming_press/credit_cupronickel"));
        consumer.accept(GTCEu.id("extruder/nan_certificate"));
        consumer.accept(GTCEu.id("centrifuge/rare_earth_separation"));
        consumer.accept(GTCEu.id("electrolyzer/bone_meal_electrolysis"));

        Material[] fluidMap = new Material[] { GTMaterials.Glue, GTMaterials.Polyethylene,
                GTMaterials.Polytetrafluoroethylene, GTMaterials.Polybenzimidazole };

        for (var machine : GTMachines.FLUID_IMPORT_HATCH) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "fluid_hatch_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }

        for (var machine : GTMachines.FLUID_EXPORT_HATCH) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "fluid_export_hatch_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }

        for (var machine : GTMachines.ITEM_IMPORT_BUS) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "item_import_bus_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }

        for (var machine : GTMachines.ITEM_EXPORT_BUS) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "item_export_bus_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }

        for (var machine : GTMachines.DUAL_IMPORT_HATCH) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "dual_import_bus_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }

        for (var machine : GTMachines.DUAL_EXPORT_HATCH) {
            if (machine == null) continue;
            int tier = machine.getTier();
            int j = Math.min(fluidMap.length - 1, tier / 2);
            for (; j < fluidMap.length; j++) {
                consumer.accept(GTCEu.id("assembler/" + "dual_export_bus_" + VN[tier].toLowerCase() + "_" + fluidMap[j].getName()));
            }
        }
    }
}
