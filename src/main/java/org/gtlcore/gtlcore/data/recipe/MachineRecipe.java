package org.gtlcore.gtlcore.data.recipe;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLMaterials;
import org.gtlcore.gtlcore.common.data.machines.*;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.Tags;

import org.apache.commons.lang3.ArrayUtils;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.CraftingComponent.*;
import static com.gregtechceu.gtceu.data.recipe.misc.MetaTileEntityLoader.registerMachineRecipe;

public class MachineRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        HatchRecipe.init(provider);
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("casing_uev"), GTBlocks.MACHINE_CASING_UEV.asStack(),
                "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, GTLMaterials.Quantanium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("casing_uiv"), GTBlocks.MACHINE_CASING_UIV.asStack(),
                "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, GTLMaterials.Adamantium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("casing_uxv"), GTBlocks.MACHINE_CASING_UXV.asStack(),
                "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, GTLMaterials.Vibranium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("casing_opv"), GTBlocks.MACHINE_CASING_OpV.asStack(),
                "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, GTLMaterials.Draconium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("casing_max"), GTBlocks.MACHINE_CASING_MAX.asStack(),
                "PPP",
                "PwP", "PPP", 'P', new UnificationEntry(TagPrefix.plate, GTLMaterials.Chaos));
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("casing_uev")).EUt(16).inputItems(plate, GTLMaterials.Quantanium, 8)
                .outputItems(GTBlocks.MACHINE_CASING_UEV.asStack()).circuitMeta(8).duration(50)
                .save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("casing_uiv")).EUt(16).inputItems(plate, GTLMaterials.Adamantium, 8)
                .outputItems(GTBlocks.MACHINE_CASING_UIV.asStack()).circuitMeta(8).duration(50)
                .save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("casing_uxv")).EUt(16).inputItems(plate, GTLMaterials.Vibranium, 8)
                .outputItems(GTBlocks.MACHINE_CASING_UXV.asStack()).circuitMeta(8).duration(50)
                .save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("casing_opv")).EUt(16).inputItems(plate, GTLMaterials.Draconium, 8)
                .outputItems(GTBlocks.MACHINE_CASING_OpV.asStack()).circuitMeta(8).duration(50)
                .save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("casing_max")).EUt(16).inputItems(plate, GTLMaterials.Chaos, 8)
                .outputItems(GTBlocks.MACHINE_CASING_MAX.asStack()).circuitMeta(8).duration(50)
                .save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_uhv")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UHV.asStack())
                .inputItems(cableGtSingle, Europium, 2)
                .inputFluids(GTLMaterials.Polyetheretherketone.getFluid(L * 2))
                .outputItems(GTMachines.HULL[9]).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_uev")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UEV.asStack())
                .inputItems(cableGtSingle, GTLMaterials.Mithril, 2)
                .inputFluids(GTLMaterials.Polyetheretherketone.getFluid(L * 2))
                .outputItems(GTMachines.HULL[10]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_uiv")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UIV.asStack())
                .inputItems(cableGtSingle, GTMaterials.Neutronium, 2)
                .inputFluids(GTLMaterials.Zylon.getFluid(L * 2))
                .outputItems(GTMachines.HULL[11]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_uxv")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_UXV.asStack())
                .inputItems(cableGtSingle, GTLMaterials.Taranium, 2)
                .inputFluids(GTLMaterials.Zylon.getFluid(L * 2))
                .outputItems(GTMachines.HULL[12]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_opv")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_OpV.asStack())
                .inputItems(cableGtSingle, GTLMaterials.Crystalmatrix, 2)
                .inputFluids(GTLMaterials.FullerenePolymerMatrixPulp.getFluid(L * 2))
                .outputItems(GTMachines.HULL[13]).save(provider);
        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("hull_max")).duration(50).EUt(16)
                .inputItems(GTBlocks.MACHINE_CASING_MAX.asStack())
                .inputItems(cableGtSingle, GTLMaterials.CosmicNeutronium, 2)
                .inputFluids(GTLMaterials.Radox.getFluid(L * 2))
                .outputItems(GTMachines.HULL[14]).save(provider);

        registerMachineRecipe(provider, ArrayUtils.subarray(GTMachines.TRANSFORMER, GTValues.UHV, GTValues.MAX),
                "WCC",
                "TH ", "WCC", 'W', POWER_COMPONENT, 'C', CABLE, 'T', CABLE_TIER_UP, 'H', HULL);
        registerMachineRecipe(provider,
                ArrayUtils.subarray(GTMachines.HI_AMP_TRANSFORMER_2A, GTValues.UHV, GTValues.MAX),
                "WCC", "TH ", "WCC",
                'W', POWER_COMPONENT, 'C', CABLE_DOUBLE, 'T', CABLE_TIER_UP_DOUBLE, 'H', HULL);
        registerMachineRecipe(provider,
                ArrayUtils.subarray(GTMachines.HI_AMP_TRANSFORMER_4A, GTValues.UHV, GTValues.MAX),
                "WCC", "TH ", "WCC",
                'W', POWER_COMPONENT, 'C', CABLE_QUAD, 'T', CABLE_TIER_UP_QUAD, 'H', HULL);
        registerMachineRecipe(provider, GTLMachines.DEHYDRATOR, "WCW", "AMA", "PRP", 'M', HULL, 'P', PLATE, 'C',
                CIRCUIT, 'W', WIRE_QUAD, 'R', ROBOT_ARM, 'A', CABLE_QUAD);
        registerMachineRecipe(provider, GTLMachines.LIGHTNING_PROCESSOR, "WEW", "AMA", "WSW", 'M', HULL, 'E',
                EMITTER, 'W', WIRE_HEX, 'S', SENSOR, 'A', CABLE_TIER_UP);
        registerMachineRecipe(provider, GTLMachines.UNPACKER, "WCW", "VMR", "BCB", 'M', HULL, 'R', ROBOT_ARM, 'V',
                CONVEYOR, 'C', CIRCUIT, 'W', CABLE, 'B', Tags.Items.CHESTS_WOODEN);
        registerMachineRecipe(provider, GTLMachines.CLUSTER, "MMM", "CHC", "MMM", 'H', HULL, 'M', MOTOR, 'C', CIRCUIT);
        registerMachineRecipe(provider, GTLMachines.ROLLING, "EWE", "CMC", "PWP", 'M', HULL, 'E', MOTOR, 'P', PISTON, 'C',
                CIRCUIT, 'W', CABLE);
        registerMachineRecipe(provider, GTLMachines.LAMINATOR, "WPW", "CMC", "GGG", 'M', HULL, 'P', PUMP, 'C', CIRCUIT, 'W',
                CABLE, 'G', CONVEYOR);
        registerMachineRecipe(provider, GTLMachines.LOOM, "CWC", "EME", "EWE", 'M', HULL, 'E', MOTOR, 'C', CIRCUIT,
                'W', CABLE);
        registerMachineRecipe(provider, GTLMachines.VACUUM_PUMP, "CLC", "LML", "PLP", 'M', HULL, 'P', PUMP, 'C', CIRCUIT, 'L', PIPE_LARGE);

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("rotor_holder_uhv"), GTLMachines.ROTOR_HOLDER[UHV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[GTValues.UHV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTLMaterials.Orichalcum), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTMaterials.Neutronium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("rotor_holder_uev"), GTLMachines.ROTOR_HOLDER[UEV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[GTValues.UEV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTLMaterials.AstralTitanium), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTLMaterials.Quantanium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("thermal_generator"), GTLMachines.THERMAL_GENERATOR[0].asStack(),
                "PVP", "CMC", "WBW", 'M', GTBlocks.MACHINE_CASING_ULV.asStack(), 'P', new UnificationEntry(plate, Steel), 'V',
                new UnificationEntry(rotor, Tin), 'C', CustomTags.ULV_CIRCUITS, 'W', new UnificationEntry(wireGtSingle, RedAlloy), 'B', GTMachines.STEAM_SOLID_BOILER.first().asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("ulv_wind_mill_turbine"), GTLMachines.WIND_MILL_TURBINE[0].asStack(),
                "RGR", "MHM", "WCW", 'H', GTLMachines.THERMAL_GENERATOR[0].asStack(), 'G', new UnificationEntry(gear, Bronze), 'R', new UnificationEntry(rod, WroughtIron),
                'C', CustomTags.ULV_CIRCUITS, 'W', new UnificationEntry(cableGtSingle, Lead), 'M', new UnificationEntry(rod, IronMagnetic));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("lv_wind_mill_turbine"), GTLMachines.WIND_MILL_TURBINE[1].asStack(),
                "RGR", "MHM", "WCW", 'H', GTMachines.HULL[1].asStack(), 'G', new UnificationEntry(gear, Steel), 'R', new UnificationEntry(rod, Invar),
                'C', CustomTags.LV_CIRCUITS, 'W', new UnificationEntry(cableGtSingle, Tin), 'M', GTItems.ELECTRIC_MOTOR_LV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("mv_wind_mill_turbine"), GTLMachines.WIND_MILL_TURBINE[2].asStack(),
                "RGR", "MHM", "WCW", 'H', GTMachines.HULL[2].asStack(), 'G', new UnificationEntry(gear, Aluminium), 'R', new UnificationEntry(rod, VanadiumSteel),
                'C', CustomTags.MV_CIRCUITS, 'W', new UnificationEntry(cableGtSingle, Copper), 'M', GTItems.ELECTRIC_MOTOR_MV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("hv_wind_mill_turbine"), GTLMachines.WIND_MILL_TURBINE[3].asStack(),
                "RGR", "MHM", "WCW", 'H', GTMachines.HULL[3].asStack(), 'G', new UnificationEntry(gear, StainlessSteel), 'R', new UnificationEntry(rod, BlackSteel),
                'C', CustomTags.HV_CIRCUITS, 'W', new UnificationEntry(cableGtSingle, Gold), 'M', GTItems.ELECTRIC_MOTOR_HV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("ulv_packer"), GTLMachines.ULV_PACKER[0].asStack(),
                "BCB", "RMV", "WCW", 'M', GTBlocks.MACHINE_CASING_ULV.asStack(), 'R', GTItems.RESISTOR.asStack(), 'V',
                new UnificationEntry(rod, IronMagnetic), 'C', CustomTags.ULV_CIRCUITS, 'W', new UnificationEntry(wireGtSingle, GTMaterials.Lead), 'B', GTLItems.PRIMITIVE_ROBOT_ARM.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("ulv_unpacker"), GTLMachines.ULV_UNPACKER[0].asStack(),
                "BCB", "VMR", "WCW", 'M', GTBlocks.MACHINE_CASING_ULV.asStack(), 'R', GTItems.RESISTOR.asStack(), 'V',
                new UnificationEntry(rod, IronMagnetic), 'C', CustomTags.ULV_CIRCUITS, 'W', new UnificationEntry(wireGtSingle, GTMaterials.Lead), 'B', GTLItems.PRIMITIVE_ROBOT_ARM.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("large_bender_and_forming"), MultiBlockMachineB.LARGE_BENDER_AND_FORMING.asStack(),
                "PKP", "BZB", "FKH", 'Z', CustomTags.IV_CIRCUITS, 'B', GTLMachines.ROLLING[IV].asStack(), 'P',
                GTItems.ELECTRIC_PISTON_IV.asStack(), 'H', GTMachines.BENDER[IV].asStack(),
                'F', GTLMachines.CLUSTER[IV].asStack(), 'K', new UnificationEntry(cableGtSingle, Platinum));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("generator_array"),
                GeneratorMachine.GENERATOR_ARRAY.asStack(),
                "ABA", "BCB", "ABA", 'A', new UnificationEntry(plate, Steel),
                'B', CustomTags.LV_CIRCUITS, 'C', GTItems.EMITTER_LV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("hermetic_casing_uev"),
                GTLBlocks.HERMETIC_CASING_UEV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Quantanium), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("hermetic_casing_uiv"),
                GTLBlocks.HERMETIC_CASING_UIV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Adamantium), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, GTMaterials.Neutronium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("hermetic_casing_uxv"),
                GTLBlocks.HERMETIC_CASING_UXV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Vibranium), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid, GTLMaterials.Enderium));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("hermetic_casing_opv"),
                GTLBlocks.HERMETIC_CASING_OpV.asStack(), "PPP", "PFP", "PPP", 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Draconium), 'F',
                new UnificationEntry(TagPrefix.pipeLargeFluid,
                        GTLMaterials.HeavyQuarkDegenerateMatter));

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_tank_uev"),
                GTMachines.QUANTUM_TANK[UEV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UEV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Quantanium), 'U',
                GTItems.ELECTRIC_PUMP_UHV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UV.asStack(), 'H',
                GTLBlocks.HERMETIC_CASING_UEV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_tank_uiv"),
                GTMachines.QUANTUM_TANK[UIV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UIV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Adamantium), 'U',
                GTItems.ELECTRIC_PUMP_UEV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UHV.asStack(), 'H',
                GTLBlocks.HERMETIC_CASING_UIV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_tank_uxv"),
                GTMachines.QUANTUM_TANK[UXV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.UXV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Vibranium), 'U',
                GTItems.ELECTRIC_PUMP_UIV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UEV.asStack(), 'H',
                GTLBlocks.HERMETIC_CASING_UXV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_tank_opv"),
                GTMachines.QUANTUM_TANK[OpV].asStack(),
                "CGC", "PHP", "CUC", 'C', CustomTags.OpV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Draconium), 'U',
                GTItems.ELECTRIC_PUMP_UXV.asStack(),
                'G', GTItems.FIELD_GENERATOR_UIV.asStack(), 'H',
                GTLBlocks.HERMETIC_CASING_OpV.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_chest_uev"),
                GTMachines.QUANTUM_CHEST[UEV].asStack(), "CPC", "PHP", "CFC", 'C',
                CustomTags.UEV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Quantanium), 'F',
                GTItems.FIELD_GENERATOR_UV.asStack(), 'H', GTMachines.HULL[10].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_chest_uiv"),
                GTMachines.QUANTUM_CHEST[UIV].asStack(), "CPC", "PHP", "CFC", 'C',
                CustomTags.UIV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Adamantium), 'F',
                GTItems.FIELD_GENERATOR_UHV.asStack(), 'H', GTMachines.HULL[11].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_chest_uxv"),
                GTMachines.QUANTUM_CHEST[UXV].asStack(), "CPC", "PHP", "CFC", 'C',
                CustomTags.UXV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Vibranium), 'F',
                GTItems.FIELD_GENERATOR_UEV.asStack(), 'H', GTMachines.HULL[12].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("quantum_chest_opv"),
                GTMachines.QUANTUM_CHEST[OpV].asStack(), "CPC", "PHP", "CFC", 'C',
                CustomTags.OpV_CIRCUITS, 'P',
                new UnificationEntry(TagPrefix.plate, GTLMaterials.Draconium), 'F',
                GTItems.FIELD_GENERATOR_UIV.asStack(), 'H', GTMachines.HULL[13].asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("large_block_conversion_room"),
                AdvancedMultiBlockMachine.LARGE_BLOCK_CONVERSION_ROOM.asStack(), "SES", "EHE", "SES",
                'S', GTItems.SENSOR_ZPM.asStack(), 'E', GTItems.EMITTER_ZPM.asStack(), 'H',
                AdvancedMultiBlockMachine.BLOCK_CONVERSION_ROOM.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, true, GTLCore.id("steam_vacuum_pump"), GTLMachines.STEAM_VACUUM_PUMP.first().asStack(), "DSD",
                "SMS", "GSG", 'M', GTBlocks.BRONZE_BRICKS_HULL.asStack(), 'S', new UnificationEntry(TagPrefix.pipeNormalFluid, GTMaterials.Bronze), 'D', GTMachines.BRONZE_DRUM.asStack(), 'G', new UnificationEntry(TagPrefix.gearSmall, GTMaterials.Bronze));

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("zpm_fluid_drilling_rig"))
                .inputItems(GTMachines.HULL[UV])
                .inputItems(frameGt, Ruridit, 4)
                .inputItems(CustomTags.UV_CIRCUITS, 4)
                .inputItems(GTItems.ELECTRIC_MOTOR_UV, 4)
                .inputItems(GTItems.ELECTRIC_PUMP_UV, 4)
                .inputItems(gear, Neutronium, 4)
                .circuitMeta(2)
                .outputItems(AdvancedMultiBlockMachine.FLUID_DRILLING_RIG[ZPM])
                .duration(400).EUt(VA[UV]).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder(GTLCore.id("wood_distillation"))
                .inputItems(MultiBlockMachineA.LARGE_PYROLYSE_OVEN.asStack(), 2)
                .inputItems(GCyMMachines.LARGE_DISTILLERY.asStack(), 4)
                .inputItems(CustomTags.LuV_CIRCUITS, 16)
                .inputItems(GTItems.EMITTER_LuV.asStack(), 4)
                .inputItems(pipeHugeFluid, StainlessSteel, 8)
                .inputItems(GTItems.ELECTRIC_PUMP_IV.asStack(), 8)
                .inputItems(plate, WatertightSteel, 16)
                .inputItems(plateDouble, StainlessSteel, 32)
                .inputFluids(SolderingAlloy.getFluid(1296))
                .outputItems(MultiBlockMachineB.WOOD_DISTILLATION)
                .duration(400).EUt(VA[LuV])
                .save(provider);
    }
}
