package org.gtlcore.gtlcore.data.recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import net.minecraft.data.recipes.FinishedRecipe;
import org.apache.commons.lang3.ArrayUtils;
import org.gtlcore.gtlcore.common.data.GTLMachines;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.ROTOR_HOLDER;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader.registerMachineRecipe;
import static org.gtlcore.gtlcore.common.data.GTLMachines.DEHYDRATOR;
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
    }
}
