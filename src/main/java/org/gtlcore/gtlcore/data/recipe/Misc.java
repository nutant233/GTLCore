package org.gtlcore.gtlcore.data.recipe;

import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.recipe.condition.GravityCondition;
import org.gtlcore.gtlcore.common.recipe.condition.VacuumCondition;
import org.gtlcore.gtlcore.config.GTLConfigHolder;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.WaterAgarMix;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.machines.MultiBlockMachineB.PRIMITIVE_VOID_ORE;

public class Misc {

    public static void init(Consumer<FinishedRecipe> provider) {
        if (GTLConfigHolder.INSTANCE.enablePrimitiveVoidOre) {
            VanillaRecipeHelper.addShapedRecipe(provider, true, "primitive_void_ore_recipes",
                    PRIMITIVE_VOID_ORE.asStack(), "DCD", "CGC", "DCD",
                    'D', Blocks.DIRT.asItem(),
                    'C', Items.STONE_PICKAXE.asItem(),
                    'G', new UnificationEntry(TagPrefix.block, GTMaterials.Iron));
            PRIMITIVE_VOID_ORE_RECIPES.recipeBuilder("primitive_void_ore_recipes")
                    .inputFluids(GTMaterials.Steam.getFluid(1000))
                    .duration(200)
                    .save(provider);
        }

        VanillaRecipeHelper.addBlastingRecipe(provider, "hot_iron_ingot", ChemicalHelper.getTag(ingot, Iron), GTLItems.HOT_IRON_INGOT.asStack(), 0);

        VanillaRecipeHelper.addShapedRecipe(provider, "wrought_iron_ingot", ChemicalHelper.get(ingot, WroughtIron), "h", "H", 'H', GTLItems.HOT_IRON_INGOT.asStack());

        VanillaRecipeHelper.addShapedRecipe(provider, "raw_vacuum_tube", GTLItems.RAW_VACUUM_TUBE.asStack(),
                "PTP", "WWW",
                'P', new UnificationEntry(bolt, Steel),
                'T', GLASS_TUBE.asStack(),
                'W', new UnificationEntry(wireGtSingle, Copper));

        ASSEMBLER_RECIPES.recipeBuilder("vacuum_tube_plain")
                .inputItems(GLASS_TUBE)
                .inputItems(bolt, Steel)
                .inputItems(wireGtSingle, Copper, 2)
                .circuitMeta(1)
                .outputItems(VACUUM_TUBE, 2)
                .addCondition(new VacuumCondition(1))
                .duration(120).EUt(VA[ULV]).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("vacuum_tube_red_alloy")
                .inputItems(GLASS_TUBE)
                .inputItems(bolt, Steel)
                .inputItems(wireGtSingle, Copper, 2)
                .inputFluids(RedAlloy.getFluid(18))
                .outputItems(VACUUM_TUBE, 4)
                .addCondition(new VacuumCondition(2))
                .duration(40).EUt(16).save(provider);

        ASSEMBLER_RECIPES.recipeBuilder("vacuum_tube_red_alloy_annealed")
                .inputItems(GLASS_TUBE)
                .inputItems(bolt, Steel)
                .inputItems(wireGtSingle, AnnealedCopper, 2)
                .inputFluids(RedAlloy.getFluid(18))
                .outputItems(VACUUM_TUBE, 8)
                .addCondition(new VacuumCondition(3))
                .duration(40).EUt(VA[LV]).save(provider);

        VACUUM_PUMP_RECIPES.recipeBuilder("a")
                .notConsumable(pipeHugeFluid, Bronze)
                .EUt(7).duration(200)
                .addData("tier", 0)
                .save(provider);

        VACUUM_PUMP_RECIPES.recipeBuilder("b")
                .notConsumable(FLUID_REGULATOR_LV)
                .EUt(30).duration(200)
                .addData("tier", 1)
                .save(provider);

        VACUUM_PUMP_RECIPES.recipeBuilder("c")
                .notConsumable(FLUID_REGULATOR_MV)
                .EUt(120).duration(200)
                .addData("tier", 2)
                .save(provider);

        VACUUM_PUMP_RECIPES.recipeBuilder("d")
                .notConsumable(FLUID_REGULATOR_HV)
                .EUt(480).duration(200)
                .addData("tier", 3)
                .save(provider);

        WOOD_DISTILLATION_RECIPES.recipeBuilder("wood_distillation_recipes")
                .inputItems(ItemTags.LOGS, 16)
                .inputFluids(Nitrogen.getFluid(1000))
                .outputItems(dust, DarkAsh, 8)
                .outputFluids(Water.getFluid(800))
                .outputFluids(Methanol.getFluid(480))
                .outputFluids(Benzene.getFluid(350))
                .outputFluids(CarbonMonoxide.getFluid(340))
                .outputFluids(Creosote.getFluid(300))
                .outputFluids(Dimethylbenzene.getFluid(240))
                .outputFluids(AceticAcid.getFluid(160))
                .outputFluids(Methane.getFluid(130))
                .outputFluids(Acetone.getFluid(80))
                .outputFluids(Phenol.getFluid(75))
                .outputFluids(Toluene.getFluid(75))
                .outputFluids(Ethylene.getFluid(20))
                .outputFluids(Hydrogen.getFluid(20))
                .outputFluids(MethylAcetate.getFluid(16))
                .outputFluids(Ethanol.getFluid(16))
                .duration(200).EUt(VA[MV])
                .save(provider);

        AUTOCLAVE_RECIPES.recipeBuilder("water_agar_mix").EUt(VA[HV]).duration(600)
                .inputItems(dust, Gelatin)
                .inputFluids(DistilledWater.getFluid(1000))
                .outputFluids(WaterAgarMix.getFluid(1000))
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .save(provider);

        DEHYDRATOR_RECIPES.recipeBuilder("agar")
                .inputFluids(WaterAgarMix.getFluid(1000))
                .outputItems(dust, Agar, 1)
                .duration(420).EUt(VA[MV])
                .cleanroom(CleanroomType.STERILE_CLEANROOM)
                .save(provider);

        CHEMICAL_RECIPES.recipeBuilder("soda_ash_from_carbon_dioxide")
                .circuitMeta(2)
                .inputItems(dust, SodiumHydroxide, 6)
                .inputFluids(CarbonDioxide.getFluid(1000))
                .outputItems(dust, SodaAsh, 6)
                .outputFluids(Water.getFluid(1000))
                .duration(80).EUt(VA[HV])
                .save(provider);

        BLAST_RECIPES.recipeBuilder("engraved_crystal_chip_from_emerald")
                .inputItems(plate, Emerald)
                .inputItems(RAW_CRYSTAL_CHIP)
                .inputFluids(Helium.getFluid(1000))
                .outputItems(ENGRAVED_CRYSTAL_CHIP)
                .blastFurnaceTemp(5000)
                .duration(900).EUt(VA[HV])
                .addCondition(new GravityCondition(true))
                .save(provider);

        BLAST_RECIPES.recipeBuilder("engraved_crystal_chip_from_olivine")
                .inputItems(plate, Olivine)
                .inputItems(RAW_CRYSTAL_CHIP)
                .inputFluids(Helium.getFluid(1000))
                .outputItems(ENGRAVED_CRYSTAL_CHIP)
                .blastFurnaceTemp(5000)
                .duration(900).EUt(VA[HV])
                .addCondition(new GravityCondition(true))
                .save(provider);

        CHEMICAL_BATH_RECIPES.recipeBuilder("quantum_star")
                .inputItems(gem, NetherStar)
                .inputFluids(Radon.getFluid(1250))
                .outputItems(QUANTUM_STAR)
                .duration(1920).EUt(VA[HV])
                .addCondition(new GravityCondition(true))
                .save(provider);

        AUTOCLAVE_RECIPES.recipeBuilder("gravi_star")
                .inputItems(QUANTUM_STAR)
                .inputFluids(Neutronium.getFluid(L * 2))
                .outputItems(GRAVI_STAR)
                .duration(480).EUt(VA[IV])
                .addCondition(new GravityCondition(true))
                .save(provider);

        CHEMICAL_BATH_RECIPES.recipeBuilder("quantum_eye")
                .inputItems(gem, EnderEye)
                .inputFluids(Radon.getFluid(250))
                .outputItems(QUANTUM_EYE)
                .duration(480).EUt(VA[HV])
                .addCondition(new GravityCondition(true))
                .save(provider);

        LIGHTNING_PROCESSOR_RECIPES.recipeBuilder("ender_pearl_dust").duration(400).EUt(VA[LV])
                .inputItems(dust, Beryllium)
                .inputItems(dust, Potassium, 4)
                .inputFluids(Nitrogen.getFluid(5000))
                .circuitMeta(1)
                .outputItems(dust, EnderPearl, 10)
                .save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_lv_cadmium_battery").inputItems(BATTERY_LV_CADMIUM)
                .outputItems(BATTERY_HULL_LV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_lv_lithium_battery").inputItems(BATTERY_LV_LITHIUM)
                .outputItems(BATTERY_HULL_LV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_lv_sodium_battery").inputItems(BATTERY_LV_SODIUM)
                .outputItems(BATTERY_HULL_LV).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_mv_cadmium_battery").inputItems(BATTERY_MV_CADMIUM)
                .outputItems(BATTERY_HULL_MV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_mv_lithium_battery").inputItems(BATTERY_MV_LITHIUM)
                .outputItems(BATTERY_HULL_MV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_mv_sodium_battery").inputItems(BATTERY_MV_SODIUM)
                .outputItems(BATTERY_HULL_MV).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_hv_cadmium_battery").inputItems(BATTERY_HV_CADMIUM)
                .outputItems(BATTERY_HULL_HV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_hv_lithium_battery").inputItems(BATTERY_HV_LITHIUM)
                .outputItems(BATTERY_HULL_HV).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_hv_sodium_battery").inputItems(BATTERY_HV_SODIUM)
                .outputItems(BATTERY_HULL_HV).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_ev_vanadium_battery").inputItems(BATTERY_EV_VANADIUM)
                .outputItems(BATTERY_HULL_SMALL_VANADIUM).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_iv_vanadium_battery").inputItems(BATTERY_IV_VANADIUM)
                .outputItems(BATTERY_HULL_MEDIUM_VANADIUM).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_luv_vanadium_battery").inputItems(BATTERY_LUV_VANADIUM)
                .outputItems(BATTERY_HULL_LARGE_VANADIUM).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_zpm_naquadria_battery").inputItems(BATTERY_ZPM_NAQUADRIA)
                .outputItems(BATTERY_HULL_MEDIUM_NAQUADRIA).save(provider);
        UNPACKER_RECIPES.recipeBuilder("unpackage_uv_naquadria_battery").inputItems(BATTERY_UV_NAQUADRIA)
                .outputItems(BATTERY_HULL_LARGE_NAQUADRIA).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_ev_lapotronic_battery")
                .inputItems(GTBlocks.BATTERY_LAPOTRONIC_EV.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_I.asStack(1))
                .outputItems(LAPOTRON_CRYSTAL)
                .duration(200).EUt(VA[LV]).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_iv_lapotronic_battery")
                .inputItems(GTBlocks.BATTERY_LAPOTRONIC_IV.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_I)
                .outputItems(ENERGY_LAPOTRONIC_ORB)
                .duration(200).EUt(VA[LV]).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_luv_lapotronic_battery")
                .inputItems(GTBlocks.BATTERY_LAPOTRONIC_LuV.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_II)
                .outputItems(ENERGY_LAPOTRONIC_ORB_CLUSTER)
                .duration(200).EUt(VA[LV]).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_zpm_lapotronic_battery")
                .inputItems(GTBlocks.BATTERY_LAPOTRONIC_ZPM.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_II)
                .outputItems(ENERGY_MODULE)
                .duration(200).EUt(VA[LV]).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_uv_lapotronic_battery")
                .inputItems(GTBlocks.BATTERY_LAPOTRONIC_UV.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_III)
                .outputItems(ENERGY_CLUSTER)
                .duration(200).EUt(VA[LV]).save(provider);

        UNPACKER_RECIPES.recipeBuilder("unpackage_uhv_ultimate_battery")
                .inputItems(GTBlocks.BATTERY_ULTIMATE_UHV.asStack(1))
                .outputItems(GTBlocks.BATTERY_EMPTY_TIER_III)
                .outputItems(ULTIMATE_BATTERY)
                .duration(200).EUt(VA[LV]).save(provider);

        LOOM_RECIPES.recipeBuilder("wool_from_string")
                .inputItems(new ItemStack(Items.STRING, 4))
                .circuitMeta(4)
                .outputItems(new ItemStack(Blocks.WHITE_WOOL))
                .duration(100).EUt(4).save(provider);
    }
}
