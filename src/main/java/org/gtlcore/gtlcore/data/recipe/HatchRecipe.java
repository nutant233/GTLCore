package org.gtlcore.gtlcore.data.recipe;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.data.CraftingComponentAddition;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import com.hepdd.gtmthings.data.CustomItems;
import com.hepdd.gtmthings.data.WirelessMachines;
import com.tterrag.registrate.util.entry.ItemEntry;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.GTValues.VA;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader.registerMachineRecipe;

public class HatchRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        registerMachineRecipe(provider, GTMachines.FLUID_IMPORT_HATCH, " G", " M", 'M', HULL, 'G', GLASS);
        registerMachineRecipe(provider, GTMachines.FLUID_EXPORT_HATCH, " M", " G", 'M', HULL, 'G', GLASS);

        registerMachineRecipe(provider, GTMachines.ITEM_IMPORT_BUS, " C", " M", 'M', HULL, 'C',
                TagUtil.createItemTag("chests/wooden"));
        registerMachineRecipe(provider, GTMachines.ITEM_EXPORT_BUS, " M", " C", 'M', HULL, 'C',
                TagUtil.createItemTag("chests/wooden"));

        registerMachineRecipe(provider, GTMachines.DUAL_IMPORT_HATCH, "PG", "CM", 'P', PIPE_NONUPLE, 'M', HULL,
                'G', GLASS, 'C', CraftingComponentAddition.BUFFER);
        registerMachineRecipe(provider, GTMachines.DUAL_EXPORT_HATCH, "MG", "CP", 'P', PIPE_NONUPLE, 'M', HULL,
                'G', GLASS, 'C', CraftingComponentAddition.BUFFER);

        Material[] multiHatchMaterials = new Material[] {
                GTMaterials.Neutronium, GTLMaterials.Enderium, GTLMaterials.Enderium,
                GTLMaterials.HeavyQuarkDegenerateMatter,
                GTLMaterials.HeavyQuarkDegenerateMatter,
        };
        for (int i = 0; i < multiHatchMaterials.length; i++) {
            var tier = GTMachines.MULTI_HATCH_TIERS[i + 6];
            var tierName = VN[tier].toLowerCase();

            var material = multiHatchMaterials[i];

            var importHatch = GTMachines.FLUID_IMPORT_HATCH[tier];
            var exportHatch = GTMachines.FLUID_EXPORT_HATCH[tier];

            var importHatch4x = GTMachines.FLUID_IMPORT_HATCH_4X[tier];
            var exportHatch4x = GTMachines.FLUID_EXPORT_HATCH_4X[tier];
            var importHatch9x = GTMachines.FLUID_IMPORT_HATCH_9X[tier];
            var exportHatch9x = GTMachines.FLUID_EXPORT_HATCH_9X[tier];

            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTLCore.id("fluid_import_hatch_4x_" + tierName),
                    importHatch4x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTLCore.id("fluid_export_hatch_4x_" + tierName),
                    exportHatch4x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTLCore.id("fluid_import_hatch_9x_" + tierName),
                    importHatch9x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTLCore.id("fluid_export_hatch_9x_" + tierName),
                    exportHatch9x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
        }

        List<ItemEntry<ComponentItem>> WIRELESS_ENERGY_RECEIVE_COVER = List.of(
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_LV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_MV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_HV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_EV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_IV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_LUV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_ZPM,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UHV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UEV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UIV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UXV,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_OPV,
                GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX);

        List<ItemEntry<ComponentItem>> WIRELESS_ENERGY_RECEIVE_COVER_4A = List.of(
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_LV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_MV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_HV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_EV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_IV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_LUV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_ZPM_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UHV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UEV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UIV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_UXV_4A,
                CustomItems.WIRELESS_ENERGY_RECEIVE_COVER_OPV_4A,
                GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX_4A);

        for (int tier : GTValues.tiersBetween(GTValues.LV, GTValues.HV)) {
            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_4a"))
                    .inputItems(GTLMachines.ENERGY_INPUT_HATCH_4A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_4A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_16a"))
                    .inputItems(GTLMachines.ENERGY_INPUT_HATCH_16A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_16A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_4a"))
                    .inputItems(GTLMachines.ENERGY_OUTPUT_HATCH_4A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_4A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_16a"))
                    .inputItems(GTLMachines.ENERGY_OUTPUT_HATCH_16A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_16A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);
        }

        for (int tier : GTValues.tiersBetween(GTValues.EV, GTValues.MAX)) {
            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_64a"))
                    .inputItems(GTMachines.SUBSTATION_ENERGY_INPUT_HATCH[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(4))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(GTLMachines.WIRELESS_ENERGY_INPUT_HATCH_64A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_64a"))
                    .inputItems(GTMachines.SUBSTATION_ENERGY_OUTPUT_HATCH[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(4))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(GTLMachines.WIRELESS_ENERGY_OUTPUT_HATCH_64A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);
        }

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase()))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH[MAX].asStack())
                .inputItems(GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack())
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase()))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH[MAX].asStack())
                .inputItems(GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack())
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4a"))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH_4A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_4A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_16a"))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH_16A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_16A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4a"))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_4A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_4A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_16a"))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_16A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_16A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_256a"))
                .inputItems(GTMachines.LASER_INPUT_HATCH_256[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(4))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_256A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_1024a"))
                .inputItems(GTMachines.LASER_INPUT_HATCH_1024[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(8))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_1024A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4096a"))
                .inputItems(GTMachines.LASER_INPUT_HATCH_4096[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(16))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_4096A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_256a"))
                .inputItems(GTMachines.LASER_OUTPUT_HATCH_256[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(4))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_256A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_1024a"))
                .inputItems(GTMachines.LASER_OUTPUT_HATCH_1024[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(8))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_1024A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4096a"))
                .inputItems(GTMachines.LASER_OUTPUT_HATCH_4096[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(16))
                .inputItems(GTMachines.ACTIVE_TRANSFORMER.asStack())
                .inputItems(GTBlocks.SUPERCONDUCTING_COIL.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_4096A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        for (int tier = 1; tier < 4; tier++) {
            var hatch = GTLMachines.ENERGY_INPUT_HATCH_4A[tier];

            ASSEMBLER_RECIPES.recipeBuilder("energy_hatch_4a_" + GTValues.VN[tier].toLowerCase())
                    .inputItems(GTMachines.ENERGY_INPUT_HATCH[tier])
                    .inputItems(WIRE_QUAD.getIngredient(tier), 2)
                    .inputItems(PLATE.getIngredient(tier), 2)
                    .outputItems(hatch)
                    .duration(100).EUt(VA[tier]).save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            MachineDefinition hatch = GTLMachines.ENERGY_INPUT_HATCH_16A[tier];
            MachineDefinition transformer;
            transformer = GTMachines.TRANSFORMER[tier];
            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("energy_hatch_16a_" + GTValues.VN[tier].toLowerCase()))
                    .inputItems(transformer)
                    .inputItems(GTLMachines.ENERGY_INPUT_HATCH_4A[tier])
                    .inputItems(WIRE_OCT.getIngredient(tier), 2)
                    .inputItems(PLATE.getIngredient(tier), 4)
                    .outputItems(hatch)
                    .duration(200).EUt(VA[tier]).save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            var hatch = GTLMachines.ENERGY_OUTPUT_HATCH_4A[tier];
            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("dynamo_hatch_4a_" + GTValues.VN[tier].toLowerCase()))
                    .inputItems(GTMachines.ENERGY_OUTPUT_HATCH[tier])
                    .inputItems(WIRE_QUAD.getIngredient(tier), 2)
                    .inputItems(PLATE.getIngredient(tier), 2)
                    .outputItems(hatch)
                    .duration(100)
                    .EUt(VA[tier])
                    .save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            MachineDefinition hatch = GTLMachines.ENERGY_OUTPUT_HATCH_16A[tier];

            MachineDefinition transformer;
            transformer = GTMachines.TRANSFORMER[tier];

            ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("dynamo_hatch_16a_" + GTValues.VN[tier].toLowerCase()))
                    .inputItems(transformer)
                    .inputItems(GTLMachines.ENERGY_OUTPUT_HATCH_4A[tier])
                    .inputItems(WIRE_OCT.getIngredient(tier), 2)
                    .inputItems(PLATE.getIngredient(tier), 4)
                    .outputItems(hatch)
                    .duration(200).EUt(VA[tier]).save(provider);
        }
    }
}
