package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.hepdd.gtmthings.GTMThings;
import com.hepdd.gtmthings.data.CustomItems;
import com.hepdd.gtmthings.data.WirelessMachines;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.FinishedRecipe;
import org.apache.commons.lang3.ArrayUtils;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.data.GTLMachines;

import java.util.List;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.ROTOR_HOLDER;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader.registerMachineRecipe;
import static org.gtlcore.gtlcore.common.data.GTLMachines.DEHYDRATOR;
import static org.gtlcore.gtlcore.data.recipe.CraftingComponentAddition.BUFFER;
import static org.gtlcore.gtlcore.utils.Registries.getMaterial;

public class MachineRecipe {

    private MachineRecipe() {}

    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_uev", GTBlocks.MACHINE_CASING_UEV.asStack(), "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, getMaterial("quantanium")));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_uiv", GTBlocks.MACHINE_CASING_UIV.asStack(), "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, getMaterial("adamantium")));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_uxv", GTBlocks.MACHINE_CASING_UXV.asStack(), "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, getMaterial("vibranium")));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_opv", GTBlocks.MACHINE_CASING_OpV.asStack(), "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, getMaterial("draconium")));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "casing_max", GTBlocks.MACHINE_CASING_MAX.asStack(), "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, getMaterial("chaos")));
        ASSEMBLER_RECIPES.recipeBuilder("casing_uev").EUt(16).inputItems(plate, getMaterial("quantanium"), 8)
                .outputItems(GTBlocks.MACHINE_CASING_UEV.asStack()).circuitMeta(8).duration(50).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("casing_uiv").EUt(16).inputItems(plate, getMaterial("adamantium"), 8)
                .outputItems(GTBlocks.MACHINE_CASING_UIV.asStack()).circuitMeta(8).duration(50).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("casing_uxv").EUt(16).inputItems(plate, getMaterial("vibranium"), 8)
                .outputItems(GTBlocks.MACHINE_CASING_UXV.asStack()).circuitMeta(8).duration(50).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("casing_opv").EUt(16).inputItems(plate, getMaterial("draconium"), 8)
                .outputItems(GTBlocks.MACHINE_CASING_OpV.asStack()).circuitMeta(8).duration(50).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("casing_max").EUt(16).inputItems(plate, getMaterial("chaos"), 8)
                .outputItems(GTBlocks.MACHINE_CASING_MAX.asStack()).circuitMeta(8).duration(50).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("hull_uhv").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UHV.asStack()).inputItems(cableGtSingle, Europium, 2)
                .inputFluids(getMaterial("polyetheretherketone").getFluid(L * 2))
                .outputItems(GTMachines.HULL[9]).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("hull_uev").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UEV.asStack())
                .inputItems(cableGtSingle, getMaterial("mithril"), 2)
                .inputFluids(getMaterial("polyetheretherketone").getFluid(L * 2))
                .outputItems(GTMachines.HULL[10]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("hull_uiv").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UIV.asStack())
                .inputItems(cableGtSingle, GTMaterials.Neutronium, 2)
                .inputFluids(getMaterial("zylon").getFluid(L * 2))
                .outputItems(GTMachines.HULL[11]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("hull_uxv").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UXV.asStack())
                .inputItems(cableGtSingle, getMaterial("taranium"), 2)
                .inputFluids(getMaterial("zylon").getFluid(L * 2))
                .outputItems(GTMachines.HULL[12]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("hull_opv").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_OpV.asStack())
                .inputItems(cableGtSingle, getMaterial("crystalmatrix"), 2)
                .inputFluids(getMaterial("fullerene_polymer_matrix_pulp").getFluid(L * 2))
                .outputItems(GTMachines.HULL[13]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder("hull_max").duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_MAX.asStack())
                .inputItems(cableGtSingle, getMaterial("cosmicneutronium"), 2)
                .inputFluids(getMaterial("radox").getFluid(L * 2))
                .outputItems(GTMachines.HULL[14]).save(provider);

        var multiHatchMaterials = new Material[] {
                GTMaterials.Neutronium, getMaterial("enderium"), getMaterial("enderium"),
                getMaterial("heavy_quark_degenerate_matter"), getMaterial("heavy_quark_degenerate_matter"),
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
                    provider, true, "fluid_import_hatch_4x_" + tierName,
                    importHatch4x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, "fluid_export_hatch_4x_" + tierName,
                    exportHatch4x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, "fluid_import_hatch_9x_" + tierName,
                    importHatch9x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, "fluid_export_hatch_9x_" + tierName,
                    exportHatch9x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
        }
        VanillaRecipeHelper.addShapedRecipe(provider, true, "rotor_holder_uhv", ROTOR_HOLDER[UHV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[GTValues.UHV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, getMaterial("orichalcum")), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTMaterials.Neutronium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, "rotor_holder_uev", ROTOR_HOLDER[UEV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[GTValues.UEV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, getMaterial("astraltitanium")), 'S',
                new UnificationEntry(TagPrefix.gearSmall, getMaterial("quantanium")));
        registerMachineRecipe(provider, ArrayUtils.subarray(GTMachines.TRANSFORMER, GTValues.UHV, GTValues.MAX), "WCC",
                "TH ", "WCC", 'W', POWER_COMPONENT, 'C', CABLE, 'T', CABLE_TIER_UP, 'H', HULL);
        registerMachineRecipe(provider, ArrayUtils.subarray(GTMachines.HI_AMP_TRANSFORMER_2A, GTValues.UHV, GTValues.MAX), "WCC", "TH ", "WCC",
                'W', POWER_COMPONENT, 'C', CABLE_DOUBLE, 'T', CABLE_TIER_UP_DOUBLE, 'H', HULL);
        registerMachineRecipe(provider, ArrayUtils.subarray(GTMachines.HI_AMP_TRANSFORMER_4A, GTValues.UHV, GTValues.MAX), "WCC", "TH ", "WCC",
                'W', POWER_COMPONENT, 'C', CABLE_QUAD, 'T', CABLE_TIER_UP_QUAD, 'H', HULL);
        registerMachineRecipe(provider, DEHYDRATOR, "WCW", "AMA", "PRP", 'M', HULL, 'P', PLATE, 'C',
                CIRCUIT, 'W', WIRE_QUAD, 'R', ROBOT_ARM, 'A', CABLE_QUAD);

        ASSEMBLER_RECIPES.recipeBuilder("zpm_fluid_drilling_rig")
                .inputItems(GTMachines.HULL[UV])
                .inputItems(frameGt, Ruridit, 4)
                .inputItems(CustomTags.UV_CIRCUITS, 4)
                .inputItems(GTItems.ELECTRIC_MOTOR_UV, 4)
                .inputItems(GTItems.ELECTRIC_PUMP_UV, 4)
                .inputItems(gear, Neutronium, 4)
                .circuitMeta(2)
                .outputItems(GTLMachines.FLUID_DRILLING_RIG[ZPM])
                .duration(400).EUt(VA[UV]).save(provider);

        VanillaRecipeHelper.addShapedRecipe(provider, true, "generator_array", GTLMachines.GENERATOR_ARRAY.asStack(),
                "ABA", "BCB", "ABA", 'A', new UnificationEntry(plate, Steel),
                'B', CustomTags.LV_CIRCUITS, 'C', GTItems.EMITTER_LV.asStack());

        registerMachineRecipe(provider, GTMachines.FLUID_IMPORT_HATCH, " G", " M", 'M', HULL, 'G', GLASS);
        registerMachineRecipe(provider, GTMachines.FLUID_EXPORT_HATCH, " M", " G", 'M', HULL, 'G', GLASS);

        registerMachineRecipe(provider, GTMachines.ITEM_IMPORT_BUS, " C", " M", 'M', HULL, 'C', TagUtil.createItemTag("chests/wooden"));
        registerMachineRecipe(provider, GTMachines.ITEM_EXPORT_BUS, " M", " C", 'M', HULL, 'C', TagUtil.createItemTag("chests/wooden"));

        registerMachineRecipe(provider, GTMachines.DUAL_IMPORT_HATCH, "PG", "CM", 'P', PIPE_NONUPLE, 'M', HULL, 'G', GLASS, 'C', BUFFER);
        registerMachineRecipe(provider, GTMachines.DUAL_EXPORT_HATCH, "MG", "CP", 'P', PIPE_NONUPLE, 'M', HULL, 'G', GLASS, 'C', BUFFER);

        VanillaRecipeHelper.addShapedRecipe(provider, true, "hermetic_casing_uev",
                GTLBlocks.HERMETIC_CASING_UEV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("quantanium")), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, "hermetic_casing_uiv",
                GTLBlocks.HERMETIC_CASING_UIV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("adamantium")), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, "hermetic_casing_uxv",
                GTLBlocks.HERMETIC_CASING_UXV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("vibranium")), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, getMaterial("enderium")));

        VanillaRecipeHelper.addShapedRecipe(provider, true, "hermetic_casing_opv",
                GTLBlocks.HERMETIC_CASING_OpV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("draconium")), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, getMaterial("heavy_quark_degenerate_matter")));

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_tank_uev", GTMachines.QUANTUM_TANK[UEV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UEV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("quantanium")), 'U', GTItems.ELECTRIC_PUMP_UHV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UV.asStack(), 'H', GTLBlocks.HERMETIC_CASING_UEV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_tank_uiv", GTMachines.QUANTUM_TANK[UIV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UIV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("adamantium")), 'U', GTItems.ELECTRIC_PUMP_UEV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UHV.asStack(), 'H', GTLBlocks.HERMETIC_CASING_UIV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_tank_uxv", GTMachines.QUANTUM_TANK[UXV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UXV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("vibranium")), 'U', GTItems.ELECTRIC_PUMP_UIV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UEV.asStack(), 'H', GTLBlocks.HERMETIC_CASING_UXV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_tank_opv", GTMachines.QUANTUM_TANK[OpV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.OpV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("draconium")), 'U', GTItems.ELECTRIC_PUMP_UXV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UIV.asStack(), 'H', GTLBlocks.HERMETIC_CASING_OpV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_chest_uev",
                GTMachines.QUANTUM_CHEST[UEV].asStack(), "CPC", "PHP", "CFC", 'C', CustomTags.UEV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("quantanium")), 'F',
                GTItems.FIELD_GENERATOR_UV.asStack(), 'H', GTMachines.HULL[10].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_chest_uiv",
                GTMachines.QUANTUM_CHEST[UIV].asStack(), "CPC", "PHP", "CFC", 'C', CustomTags.UIV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("adamantium")), 'F',
                GTItems.FIELD_GENERATOR_UHV.asStack(), 'H', GTMachines.HULL[11].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_chest_uxv",
                GTMachines.QUANTUM_CHEST[UXV].asStack(), "CPC", "PHP", "CFC", 'C', CustomTags.UXV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("vibranium")), 'F',
                GTItems.FIELD_GENERATOR_UEV.asStack(), 'H', GTMachines.HULL[12].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, "quantum_chest_opv",
                GTMachines.QUANTUM_CHEST[OpV].asStack(), "CPC", "PHP", "CFC", 'C', CustomTags.OpV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, getMaterial("draconium")), 'F',
                GTItems.FIELD_GENERATOR_UIV.asStack(), 'H', GTMachines.HULL[13].asStack());

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
            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_4a"))
                    .inputItems(GTMachines.ENERGY_INPUT_HATCH_4A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_4A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_16a"))
                    .inputItems(GTMachines.ENERGY_INPUT_HATCH_16A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_16A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_4a"))
                    .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_4A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_4A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_16a"))
                    .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_16A[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(2))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_16A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);
        }

        for (int tier : GTValues.tiersBetween(GTValues.EV, GTValues.MAX)) {
            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[tier].toLowerCase() + "_64a"))
                    .inputItems(GTMachines.SUBSTATION_ENERGY_INPUT_HATCH[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(4))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(GTLMachines.WIRELESS_ENERGY_INPUT_HATCH_64A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[tier].toLowerCase() + "_64a"))
                    .inputItems(GTMachines.SUBSTATION_ENERGY_OUTPUT_HATCH[tier].asStack())
                    .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(tier - 1).asStack(4))
                    .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(GTLMachines.WIRELESS_ENERGY_OUTPUT_HATCH_64A[tier].asStack())
                    .duration(200)
                    .EUt(GTValues.VA[tier])
                    .save(provider);
        }

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase()))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH[MAX].asStack())
                .inputItems(GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack())
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase()))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH[MAX].asStack())
                .inputItems(GTLItems.WIRELESS_ENERGY_RECEIVE_COVER_MAX.asStack())
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack())
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4a"))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH_4A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_4A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_16a"))
                .inputItems(GTMachines.ENERGY_INPUT_HATCH_16A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_INPUT_HATCH_16A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4a"))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_4A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_4A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_16a"))
                .inputItems(GTMachines.ENERGY_OUTPUT_HATCH_16A[MAX].asStack())
                .inputItems(WIRELESS_ENERGY_RECEIVE_COVER_4A.get(MAX - 1).asStack(2))
                .inputItems(GTItems.COVER_ENERGY_DETECTOR_ADVANCED.asStack(1))
                .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                .outputItems(WirelessMachines.WIRELESS_ENERGY_OUTPUT_HATCH_16A[MAX].asStack())
                .duration(200)
                .EUt(GTValues.VA[MAX])
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_256a"))
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

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_1024a"))
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

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_input_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4096a"))
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

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_256a"))
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

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_1024a"))
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

        ASSEMBLER_RECIPES.recipeBuilder(GTMThings.id("wireless_energy_output_hatch_" + GTValues.VN[MAX].toLowerCase() + "_4096a"))
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
    }
}
