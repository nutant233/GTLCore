package org.gtlcore.gtlcore.data.recipe.processing;

import org.gtlcore.gtlcore.common.data.GTLItems;

import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.dust;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.GTLMaterials.*;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DISSOLUTION_TREATMENT;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DIGESTION_TREATMENT;



public class Lanthanidetreatment {

    public static void init(Consumer<FinishedRecipe> provider) {
        DIGESTION_TREATMENT.recipeBuilder("monazite_rare_earth_turbid_liquid_output")
                .inputItems(dust, Monazite, 2)
                .inputFluids(NitricAcid.getFluid(700))
                .outputItems(dust, SiliconDioxide, 1)
                .outputFluids(MonaziteRareEarthTurbidLiquid.getFluid(400))
                .duration(20 * 20)
                .EUt(1920)
                .save(provider);

        DISSOLUTION_TREATMENT.recipeBuilder("diluted_monazite_slurry_output")
                .inputItems(dust, Saltpeter, 9)
                .inputFluids(MonaziteRareEarthTurbidLiquid.getFluid(9000))
                .inputFluids(Water.getFluid(90000))
                .outputItems(dust, HafniumOxideZirconiaMixedPowder, 4)
                .outputItems(dust, ThoritePowder, 9)
                .outputItems(dust, Monazite, 2)
                .outputFluids(DilutedMonaziteSlurry.getFluid(100000))
                .duration(20 * 405)
                .EUt(405)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("diluted_monazite_slurry")
                .inputItems(DilutedMonaziteSlurry.getFluid(1000))
                .chancedOutput(dust, MonaziteSulfatePowder, 1, 9000, 0)
                .chancedOutput(dust, SiliconDioxide, 1, 7500, 0)
                .chancedOutput(dust, Rutile, 1, 2000, 0)
                .chancedOutput(dust, RedZirconPowder, 1, 500, 0)
                .chancedOutput(dust, Ilmenite, 1, 2000, 0)
                .duration(20*20)
                .EUt(240)
                .save(provider);

        CENTRIFUGE_RECIPES.recipeBuilder("red_zircon_powder")
                .inputItems(dust, RedZirconPowder, 4)
                .outputItems(dust, Zircon, 3)
                .outputItems(dust, GraniteRed, 2)
                .duration(20*16)
                .EUt(90)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("monazite_sulfate_powder")
                .inputItems(dust, MonaziteSulfatePowder, 1)
                .inputFluids(Water.getFluid(6000))
                .outputFluids(DilutedMonaziteSulfateSolution.getFluid(7000))
                .duration(20*24)
                .EUt(400)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("diluted_monazite_sulfate_solution")
                .inputFluids(DilutedMonaziteSulfateSolution.getFluid(9000))
                .inputFluids(AmmoniumNitrateSolution.getFluid(1800))
                .outputItems(dust, AcidicMonazitePowder, 3)
                .duration(20 * 216)
                .EUt(480)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("acidic_monazite_powder")
                .inputItems(dust, AcidicMonazitePowder, 1)
                .chancedOutput(dust, MonaziteRareEarthFilterResiduePowder, 1, 9000, 0)
                .chancedOutput(dust, ThoriumPhosphateFilterCakePowder, 1, 7000, 0)
                .duration(20*30)
                .EUt(256)
                .save(provider);

        BLAST_RECIPES.recipeBuilder("thorium_phosphate_filter_cake_powder")
                .inputItems(dust, ThoriumPhosphateFilterCakePowder, 1)
                .outputItems(dust, ThoriumPhosphateRefinedPowder, 1)
                .duration(20*5)
                .EUt(120)
                .blastFurnaceTemp(1200)
                .save(provider);

        THERMAL_CENTRIFUGE_RECIPES.recipeBuilder("thorium_phosphate_refined_powder")
                .inputItems(dust, ThoriumPhosphateRefinedPowder, 1)
                .outputItems(dust, Monazite, 1)
                .outputItems(dust, Phosphate, 1)
                .duration(20*10)
                .EUt(240)
                .save(provider);

        CHEMICAL_BATH_RECIPES.recipeBuilder("monazite_rare_earth_filter_residue_powder")
                .inputItems(dust, MonaziteRareEarthFilterResiduePowder, 1)
                .inputFluids(AmmoniumNitrateSolution.getFluid(320))
                .outputItems(dust, NeutralizedMonaziteRareEarthFilterResiduePowder, 1)
                .duration(20*6)
                .EUt(240)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("neutralized_monazite_rare_earth_filter_residue_powder")
                .inputItems(dust, NeutralizedMonaziteRareEarthFilterResiduePowder, 1)
                .chancedOutput(dust, ConcentratedMonaziteRareEarthHydroxidePowder, 1, 9000, 0)
                .chancedOutput(dust, UraniumFilterResiduePowder, 1, 5000, 0)
                .chancedOutput(dust, UraniumFilterResiduePowder, 1, 4000 , 0)
                .duration(20*40)
                .EUt(480)
                .save(provider);

        CHEMICAL_BATH_RECIPES.recipeBuilder("uranium_filter_residue_powder")
                .inputItems(dust, UraniumFilterResiduePowder, 1)
                .inputFluids(HydrofluoricAcid.getFluid(100))
                .outputItems(dust, NeutralizedUraniumFilterResiduePowder, 1)
                .duration(20*18)
                .EUt(120)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("neutralized_uranium_filter_residue_powder")
                .inputItems(dust, NeutralizedUraniumFilterResiduePowder, 1)
                .chancedOutput(dust, Uranium235, 1, 4500, 0)
                .chancedOutput(dust, Uranium235, 1, 4000, 0)
                .chancedOutput(dust, Uranium235, 1, 3000, 0)
                .chancedOutput(dust, Uranium235, 1, 3000, 0)
                .chancedOutput(dust, Uranium235, 1, 2000, 0)
                .duration(20*50)
                .EUt(30)
                .save(provider);

        BLAST_RECIPES.recipeBuilder("concentrated_monazite_rare_earth_hydroxide_powder")
                .inputItems(dust, ConcentratedMonaziteRareEarthHydroxidePowder, 1)
                .outputItems(dust, DriedConcentratedNitricMonaziteRareEarthPowder, 1)
                .duration(20*15)
                .EUt(120)
                .blastFurnaceTemp(1200)
                .save(provider);

        CHEMICAL_RECIPES.recipeBuilder("dried_concentrated_nitric_monazite_rare_earth_powder")
                .inputItems(dust, DriedConcentratedNitricMonaziteRareEarthPowder, 1)
                .inputFluids(NitricAcid.getFluid(500))
                .outputFluids(ConcentratedNitrideMonaziteRareEarthSolution.getFluid(500))
                .duration(20*25)
                .EUt(480)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("concentrated_nitride_monazite_rare_earth_solution")
                .inputItems(dust, CeriumRichMixturePowder, 3)
                .inputFluids(ConcentratedNitrideMonaziteRareEarthSolution.getFluid(1000))
                .outputFluids(NitricLeachateFromMonazite.getFluid(2000))
                .duration(20*12)
                .EUt(120)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("concentrated_nitride_monazite_rare_earth_solution")
                .inputFluids(Water.getFluid(1000))
                .inputFluids(ConcentratedNitrideMonaziteRareEarthSolution.getFluid(1000))
                .outputFluids(NitricLeachateFromMonazite.getFluid(1000))
                .duration(20*10)
                .EUt(120)
                .save(provider);

        ELECTROLYZER_RECIPES.recipeBuilder("cerium_rich_mixture_powder")
                .inputItems(dust, Bastnasite, 6)
                .outputItems(dust, CeriumRichMixturePowder, 1)
                .outputItems(dust, Carbon, 1)
                .outputFluids(Oxygen.getFluid(3000))
                .outputFluids(Fluorine.getFluid(1000))
                .duration(20 * 10)
                .EUt(90)
                .save(provider);

        CHEMICAL_RECIPES.recipeBuilder("cerium_chloride_powder")
                .inputItems(dust, CeriumRichMixturePowder, 15)
                .inputFluids(HydrofluoricAcid.getFluid(750))
                .outputItems(dust, CeriumChloridePowder, 1)
                .outputItems(dust, Monazite, 1)
                .outputFluids(Water.getFluid(750))
                .duration(20 * 15)
                .EUt(450)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("cerium_oxalate_powder")
                .inputItems(dust, CeriumChloridePowder, 15)
                .inputFluids(OxalicAcid.getFluid(3000))
                .outputItems(dust, CeriumOxalatePowder, 1)
                .outputFluids(HydrochloricAcid.getFluid(6000))
                .duration(20 * 15)
                .EUt(450)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("oxalic_acid")
                .inputItems(dust, CeriumChloridePowder, 15)
                .inputFluids(OxalicAcid.getFluid(3000))
                .outputItems(dust, CeriumOxalatePowder, 1)
                .outputFluids(HydrochloricAcid.getFluid(6000))
                .duration(20 * 15)
                .EUt(450)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("oxalic_acid")
                .inputItems(dust, VanadiumPentoxidePowder, 1)
                .inputFluids(Oxygen.getFluid(40000))
                .inputFluids(Ethanol.getFluid(9000))
                .outputFluids(OxalicAcid.getFluid(9000))
                .outputFluids(Water.getFluid(20000))
                .duration(20 * 200)
                .EUt(240)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("oxalic_acid")
                .inputItems(dust, VanadiumPentoxidePowder, 1)
                .inputFluids(Oxygen.getFluid(27000))
                .inputFluids(Methanol.getFluid(9000))
                .inputFluids(CarbonMonoxide.getFluid(9000))
                .outputFluids(OxalicAcid.getFluid(9000))
                .outputFluids(Water.getFluid(20000))
                .duration(20 * 200)
                .EUt(240)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("oxalic_acid")
                .inputItems(dust, VanadiumPentoxidePowder, 1)
                .inputItems(dust, Sugar, 24)
                .inputFluids(NitricAcid.getFluid(6000))
                .outputFluids(OxalicAcid.getFluid(3000))
                .outputFluids(NitricOxide.getFluid(6000))
                .duration(20 * 30)
                .EUt(120)
                .save(provider);

        BLAST_RECIPES.recipeBuilder("vanadium_pentoxide_powder")
                .inputItems(dust, Vanadium, 2)
                .inputFluids(Oxygen.getFluid(5000))
                .outputItems(dust, VanadiumPentoxidePowder, 7)
                .duration(20 * 10)
                .EUt(120)
                .blastFurnaceTemp(2500)
                .save(provider);

        BLAST_RECIPES.recipeBuilder("cerium_oxalate_powder")
                .inputItems(dust, CeriumOxalatePowder, 5)
                .inputItems(dust, Carbon, 3)
                .outputItems(dust, CeriumOxide, 5)
                .outputFluids(CarbonMonoxide.getFluid(9000))
                .duration(20 * 10)
                .EUt(480)
                .blastFurnaceTemp(800)
                .save(provider);

        DIGESTION_TREATMENT.recipeBuilder("concentrated_cerium_chloride_solution")
                .inputItems(dust, CeriumRichMixturePowder, 1)
                .inputFluids(Chlorine.getFluid(10000))
                .outputItems(dust, SiliconDioxide, 1)
                .outputFluids(ConcentratedCeriumChlorideSolution.getFluid(1000))
                .duration(20 * 2)
                .EUt(122880)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("concentrated_nitric_leachate_from_monazite")
                .inputItems(dust, AcidicMonazitePowder, 1)
                .chancedOutput(dust, CeriumOxide, 1, 1111, 0)
                .outputFluids(ConcentratedNitricLeachateFromMonazite.getFluid(1000))
                .duration(20*20)
                .EUt(240)
                .save(provider);

        VACUUM_RECIPES.recipeBuilder("cooling_concentrated_nitric_monazite_rare_earth_powder")
                .inputFluids(ConcentratedNitricLeachateFromMonazite.getFluid(1000))
                .outputItems(dust, CoolingConcentratedNitricMonaziteRareEarthPowder, 1)
                .duration(20*5)
                .EUt(240)
                .save(provider);

        ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder("monazite_rare_earth_precipitate_powder")
                .inputItems(dust,CoolingConcentratedNitricMonaziteRareEarthPowder, 1)
                .chancedOutput(dust, MonaziteRareEarthPrecipitatePowder, 1, 9000, 0)
                .chancedOutput(dust, EuropiumOxide, 1, 500, 0)
                .duration(20*5)
                .EUt(240)
                .save(provider);

        BLAST_RECIPES.recipeBuilder("heterogeneous_halide_monazite_rare_earth_mixture_powder")
                .inputItems(dust, MonaziteRareEarthPrecipitatePowder, 1)
                .inputFluids(Chlorine.getFluid(1000))
                .outputItems(dust, HeterogeneousHalideMonaziteRareEarthMixturePowder, 1)
                .duration(20 * 25)
                .EUt(480)
                .blastFurnaceTemp(1200)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("saturated_monazite_rare_earth_powder")
                .inputItems(dust, HeterogeneousHalideMonaziteRareEarthMixturePowder, 1)
                .inputItems(dust, SamariumRefinedPowder, 2)
                .inputFluids(Acetone.getFluid(1000))
                .outputItems(dust, SaturatedMonaziteRareEarthPowder, 3)
                .duration(20 * 20)
                .EUt(240)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("saturated_monazite_rare_earth_powder")
                .inputItems(dust, HeterogeneousHalideMonaziteRareEarthMixturePowder, 1)
                .inputItems(dust, Salt, 1)
                .inputFluids(Acetone.getFluid(1000))
                .outputItems(dust, SaturatedMonaziteRareEarthPowder, 1)
                .duration(20 * 10)
                .EUt(240)
                .save(provider);

        CENTRIFUGE_RECIPES.recipeBuilder("samarium_precipitate_powder")
                .inputItems(dust, SaturatedMonaziteRareEarthPowder, 4)
                .outputItems(dust, SamariumPrecipitatePowder, 3)
                .outputFluids(Chloromethane.getFluid(400))
                .duration(20 * 160)
                .EUt(1920)
                .save(provider);

        SIFTER_RECIPES.recipeBuilder("samarium_precipitate_powder")
                .inputItems(dust, SamariumPrecipitatePowder, 3)
                .outputItems(dust, Samarium, 2)
                .outputItems(dust, Gadolinium, 1)
                .duration(20 * 8)
                .EUt(1920)
                .save(provider);

        DIGESTION_TREATMENT.recipeBuilder("samarium_rrare_eearth_turbid_liquid")
                .inputItems(dust, SamariumRefinedPowder, 16)
                .inputFluids(NitricAcid.getFluid(200))
                .outputItems(dust, ThoriumPhosphateRefinedPowder, 1)
                .outputFluids(SamariumRrareEearthTurbidLiquid.getFluid(800))
                .duration(20 * 10)
                .EUt(1920)
                .save(provider);

        DISSOLUTION_TREATMENT.recipeBuilder("samarium_rrare_eearth_turbid_liquid")
                .inputFluids(SamariumRrareEearthTurbidLiquid.getFluid(1000))
                .inputFluids(NitricAcid.getFluid(1000))
                .chancedOutput(dust, CeriumRichMixturePowder, 1, 6000, 0)
                .chancedOutput(dust, CeriumOxide, 1, 8000, 0)
                .outputFluids(SamariumRareEarthSlurry.getFluid(2000))
                .duration(20 * 20)
                .EUt(1920)
                .save(provider);

        DISSOLUTION_TREATMENT.recipeBuilder("samarium_rare_earth_slurry")
                .inputFluids(SamariumRareEarthSlurry.getFluid(1000))
                .inputFluids(Water.getFluid(9000))
                .chancedOutput(dust, NeodymiumRareEarthConcentratePowder, 1, 9000, 0)
                .chancedOutput(dust, NeodymiumRareEarthConcentratePowder, 1, 6000, 0)
                .outputFluids(SamariumRareEarthDilutedSolution.getFluid(10000))
                .duration(20 * 30)
                .EUt(1920)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("lanthanum_chloride")
                .inputItems(dust, NeodymiumRareEarthConcentratePowder, 1)
                .inputFluids(HydrochloricAcid.getFluid(2000))
                .outputItems(dust, LanthanumChloride, 1)
                .outputItems(dust, NeodymiumOxide, 1)
                .duration(20 * 45)
                .EUt(800)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("samarium_oxalate_with_impurities")
                .inputFluids(SamariumRareEarthDilutedSolution.getFluid(2000))
                .inputFluids(OxalicAcid.getFluid(3000))
                .outputItems(dust, SamariumOxalateWithImpurities, 5)
                .chancedOutput(dust, PhosphorusFreeSamariumConcentratePowder, 3, 1000, 0)
                .outputFluids(SamariumRrareEearthTurbidLiquid.getFluid(50))
                .duration(20 * 45)
                .EUt(800)
                .save(provider);

        CENTRIFUGE_RECIPES.recipeBuilder("Samarium")
                .inputItems(dust,PhosphorusFreeSamariumConcentratePowder, 1)
                .chancedOutput(dust, Samarium, 1, 9000, 0)
                .chancedOutput(dust, ThoritePowder, 2, 8000, 0)
                .duration(20*10)
                .EUt(1920)
                .save(provider);

        LARGE_CHEMICAL_RECIPES.recipeBuilder("samarium_chloride_concentrate_solution")
                .inputItems(dust, SamariumOxalateWithImpurities ,5)
                .inputFluids(HydrochloricAcid.getFluid(6000))
                .outputItems(dust, SamariumChlorideWithImpurities, 8)
                .outputFluids(CarbonMonoxide.getFluid(6000))
                .duration(20 * 10)
                .EUt(960)
                .save(provider);

        MIXER_RECIPES.recipeBuilder("samarium_chloride_sodium_chloride_mixture_powder")
                .inputItems(dust, Salt, 1)
                .inputItems(dust, SamariumChlorideWithImpurities, 2)
                .outputItems(dust, SamariumChlorideSodiumChlorideMixturePowder, 3)
                .duration(20 * 5)
                .EUt(30)
                .save(provider);

        ELECTROLYZER_RECIPES.recipeBuilder("Samarium")
                .inputItems(dust, SamariumChlorideSodiumChlorideMixturePowder, 6)
                .outputItems(dust, Samarium, 1)
                .outputItems(dust, Sodium, 1)
                .outputFluids(Chlorine.getFluid(4000))
                .duration(20 * 1)
                .EUt(7680)
                .save(provider);

        DIGESTION_TREATMENT.recipeBuilder("monazite_rare_earth_turbid_liquid_output")
                .inputItems(dust, SamariumRefinedPowder, 1)
                .inputFluids(Chlorine.getFluid(10000))
                .outputItems(dust, SiliconDioxide, 1)
                .outputFluids(SamariumChlorideConcentrateSolution.getFluid(1000))
                .duration(20 * 2)
                .EUt(122880)
                .save(provider);



//熔融含杂氯化钐？



        DISTILLERY_RECIPES.recipeBuilder("lanthanum_chloride_with_impurities")
                .inputItems(dust, Lanthanum, 9)
                .inputFluids(MoltenSamariumChlorideWithImpurities.getFluid(5184))
                .outputItems(dust, LanthanumChlorideWithImpurities, 36)
                .outputFluids(MoltenSamarium.getFluid(2304))
                .duration(20 * 5)
                .EUt(122880)
                .save(provider);
//熔融钐固化？

        CENTRIFUGE_RECIPES.recipeBuilder("lanthanum_chloride_with_impurities")
                .inputItems(dust, LanthanumChlorideWithImpurities, 36)
                .outputItems(dust, LanthanumChloride, 36)
                .duration(20 * 5)
                .EUt(1920)
                .save(provider);



















        //RARE_EARTH_CENTRIFUGAL_RECIPES








    }
}
