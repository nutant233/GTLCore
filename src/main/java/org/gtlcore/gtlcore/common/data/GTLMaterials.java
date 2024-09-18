package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;
import com.gregtechceu.gtceu.api.fluids.FluidState;
import com.gregtechceu.gtceu.api.fluids.attribute.FluidAttributes;
import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.lowdragmc.lowdraglib.Platform;
import committee.nova.mods.avaritia.init.registry.ModBlocks;
import committee.nova.mods.avaritia.init.registry.ModItems;
import org.gtlcore.gtlcore.api.data.chemical.material.info.GTLMaterialFlags;
import org.gtlcore.gtlcore.api.data.chemical.material.info.GTLMaterialIconSet;
import org.gtlcore.gtlcore.config.ConfigHolder;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class GTLMaterials {

    public static Material MutatedLivingSolder;
    public static Material SuperMutatedLivingSolder;
    public static Material Grade8PurifiedWater;
    public static Material Grade16PurifiedWater;
    public static Material PotassiumPyrosulfate;
    public static Material SodiumSulfate;
    public static Material RhodiumNitrate;
    public static Material RoughlyRhodiumMetal;
    public static Material RhodiumFilterCakeSolution;
    public static Material RhodiumSaltSolution;
    public static Material ZincSulfate;
    public static Material ReprecipitatedRhodium;
    public static Material SodiumNitrate;
    public static Material RhodiumSalt;
    public static Material RhodiumFilterCake;
    public static Material SodiumRutheniate;
    public static Material IridiumDioxide;
    public static Material RutheniumTetroxideLQ;
    public static Material SodiumFormate;
    public static Material RhodiumSulfateGas;
    public static Material AcidicIridium;
    public static Material PlatinumSlag;
    public static Material LeachResidue;
    public static Material RutheniumTetroxideHot;
    public static Material HexafluorideEnrichedNaquadahSolution;
    public static Material XenonHexafluoroEnrichedNaquadate;
    public static Material GoldTrifluoride;
    public static Material XenoauricFluoroantimonicAcid;
    public static Material GoldChloride;
    public static Material BromineTrifluoride;
    public static Material HexafluorideNaquadriaSolution;
    public static Material RadonDifluoride;
    public static Material RadonNaquadriaOctafluoride;
    public static Material CaesiumFluoride;
    public static Material XenonTrioxide;
    public static Material CaesiumXenontrioxideFluoride;
    public static Material NaquadriaCaesiumXenonnonfluoride;
    public static Material RadonTrioxide;
    public static Material NaquadriaCaesiumfluoride;
    public static Material NitrosoniumOctafluoroxenate;
    public static Material NitrylFluoride;
    public static Material AcidicNaquadriaCaesiumfluoride;
    public static Material SupercriticalSteam;
    public static Material WaterAgarMix;
    public static Material TungstenTrioxide;
    public static Material SpaceTime;
    public static Material Infinity;
    public static Material CompoundTriniite;
    public static Material FumingNitricAcid;
    public static Material CrystallineNitricAcid;
    public static Material SodiumChlorate;
    public static Material SodiumPerchlorate;
    public static Material NitratedTriniiteCompoundSolution;
    public static Material ResidualTriniiteSolution;
    public static Material ActiniumTriniumHydroxides;
    public static Material ActiniumRadiumHydroxideSolution;
    public static Material ActiniumRadiumNitrateSolution;
    public static Material SeleniumOxide;
    public static Material HeavilyFluorinatedTriniumSolution;
    public static Material Perfluorobenzene;
    public static Material TriniumTetrafluoride;
    public static Material Trimethylsilane;
    public static Material Trimethylchlorosilane;
    public static Material Fluorocarborane;
    public static Material CaesiumNitrate;
    public static Material CesiumCarborane;
    public static Material SilverIodide;
    public static Material SilverNitrate;

    public static void init() {
        Duranium.addFlags(GENERATE_FRAME);
        Naquadria.addFlags(GENERATE_FRAME);
        Trinium.addFlags(GENERATE_FRAME);
        Silver.getProperty(PropertyKey.FLUID).enqueueRegistration(FluidStorageKeys.PLASMA, (new FluidBuilder()).state(FluidState.PLASMA));
        Europium.getProperty(PropertyKey.BLAST).setBlastTemperature(9600);
        Neutronium.addFlags(GENERATE_ROTOR, GENERATE_SPRING, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_SPRING_SMALL);
        Neutronium.setProperty(PropertyKey.WIRE, new WireProperties((int) GTValues.V[GTValues.UIV], 2, 64));
        Mendelevium.setProperty(PropertyKey.WIRE, new WireProperties((int) GTValues.V[GTValues.UHV], 4, 16));
        RutheniumTetroxide.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        RutheniumTetroxide.addFlags(NO_UNIFICATION);
        Actinium.setProperty(PropertyKey.DUST, new DustProperty());
        Actinium.setProperty(PropertyKey.FLUID, new FluidProperty(FluidStorageKeys.LIQUID, new FluidBuilder()));
        Selenium.setProperty(PropertyKey.DUST, new DustProperty());
        PotassiumPyrosulfate = new Material.Builder(GTCEu.id("potassium_pyrosulfate"))
                .dust()
                .components(Potassium, 2, Sulfur, 2, Oxygen, 7)
                .color(0xff9900).iconSet(METALLIC)
                .buildAndRegister();
        PlatinumSlag = new Material.Builder(GTCEu.id("platinum_slag"))
                .dust()
                .color(0x343318).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("IrOsRhRu??");
        LeachResidue = new Material.Builder(GTCEu.id("leach_residue"))
                .dust()
                .color(0x644629).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("IrOsRhRu?");
        ZincSulfate = new Material.Builder(GTCEu.id("zinc_sulfate"))
                .dust()
                .components(Zinc, 1, Sulfur, 1, Oxygen, 4)
                .color(0x533c1b).iconSet(SAND)
                .buildAndRegister();
        RhodiumNitrate = new Material.Builder(GTCEu.id("rhodium_nitrate"))
                .dust()
                .color(0x8C5A0C).iconSet(SAND)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rhodium, 1, Nitrogen, 1, Oxygen, 3, Concrete, 1)
                .buildAndRegister();
        RoughlyRhodiumMetal = new Material.Builder(GTCEu.id("roughly_rhodium_metal"))
                .dust()
                .color(0x594C1A).iconSet(SAND)
                .buildAndRegister().setFormula("?Rh?");
        ReprecipitatedRhodium = new Material.Builder(GTCEu.id("reprecipitated_rhodium"))
                .dust()
                .color(0xD40849).iconSet(SAND)
                .flags(DISABLE_DECOMPOSITION)
                .components(Rhodium, 1, Nitrogen, 1, Hydrogen, 4)
                .buildAndRegister();
        SodiumNitrate = new Material.Builder(GTCEu.id("sodium_nitrate"))
                .dust()
                .color(0x4e2a40).iconSet(SAND)
                .components(Sodium, 1, Nitrogen, 1, Oxygen, 3)
                .buildAndRegister();
        RhodiumSalt = new Material.Builder(GTCEu.id("rhodium_salt"))
                .dust()
                .color(0x61200A).iconSet(SAND)
                .buildAndRegister().setFormula("NaRhCl?");
        RhodiumSaltSolution = new Material.Builder(GTCEu.id("rhodium_salt_solution"))
                .fluid()
                .color(0x61200A).iconSet(SAND)
                .buildAndRegister().setFormula("Rh(NaCl)2Cl");
        RhodiumFilterCake = new Material.Builder(GTCEu.id("rhodium_filter_cake"))
                .dust()
                .color(0x87350C).iconSet(ROUGH)
                .buildAndRegister().setFormula("?Rh?");
        RhodiumFilterCakeSolution = new Material.Builder(GTCEu.id("rhodium_filter_cake_solution"))
                .fluid()
                .color(0x87350C).iconSet(ROUGH)
                .buildAndRegister().setFormula("?Rh?");
        SodiumRutheniate = new Material.Builder(GTCEu.id("sodium_rutheniate"))
                .dust()
                .color(0x282C8C).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Sodium, 2, Oxygen, 4, Ruthenium, 1)
                .buildAndRegister();
        IridiumDioxide = new Material.Builder(GTCEu.id("iridium_dioxide"))
                .dust()
                .color(0xA2BFFF).iconSet(METALLIC)
                .flags(DISABLE_DECOMPOSITION)
                .components(Iridium, 1, Oxygen, 2)
                .buildAndRegister();
        RutheniumTetroxideLQ = new Material.Builder(GTCEu.id("ruthenium_tetroxide_lq"))
                .fluid()
                .color(0xA8A8A8).iconSet(ROUGH)
                .buildAndRegister();
        SodiumFormate = new Material.Builder(GTCEu.id("sodium_formate"))
                .fluid()
                .color(0xf1939c).iconSet(ROUGH)
                .components(Sodium, 1, Carbon, 1, Oxygen, 2, Hydrogen, 1)
                .buildAndRegister();
        RhodiumSulfateGas = new Material.Builder(GTCEu.id("rhodium_sulfate_gas"))
                .gas()
                .color(0xBD8743).iconSet(ROUGH)
                .buildAndRegister();
        AcidicIridium = new Material.Builder(GTCEu.id("acidic_iridium"))
                .gas()
                .color(0x634E3A).iconSet(ROUGH)
                .buildAndRegister().setFormula("???");
        RutheniumTetroxideHot = new Material.Builder(GTCEu.id("ruthenium_tetroxide_hot"))
                .gas()
                .color(0x9B9B9B).iconSet(ROUGH)
                .buildAndRegister();
        SodiumSulfate = new Material.Builder(GTCEu.id("sodium_sulfate"))
                .dust()
                .components(Sodium, 2, Sulfur, 1, Oxygen, 4)
                .color(0xF9F6CF).iconSet(SAND)
                .buildAndRegister();

        MutatedLivingSolder = new Material.Builder(GTCEu.id("mutated_living_solder"))
                .fluid()
                .color(0xC18FCC).iconSet(METALLIC)
                .radioactiveHazard(1)
                .buildAndRegister();
        SuperMutatedLivingSolder = new Material.Builder(GTCEu.id("super_mutated_living_solder"))
                .fluid()
                .color(0xB662FF).iconSet(METALLIC)
                .radioactiveHazard(2)
                .buildAndRegister();
        Grade8PurifiedWater = new Material.Builder(GTCEu.id("grade_8_purified_water"))
                .fluid()
                .color(0x0058CD).iconSet(FLUID)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        Grade16PurifiedWater = new Material.Builder(GTCEu.id("grade_16_purified_water"))
                .fluid()
                .color(0x0058CD).iconSet(FLUID)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        HexafluorideEnrichedNaquadahSolution = new Material.Builder(GTCEu.id("hexafluoride_enriched_naquadah_solution"))
                .fluid()
                .color(0x868D7F)
                .components(NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        XenonHexafluoroEnrichedNaquadate = new Material.Builder(GTCEu.id("xenon_hexafluoro_enriched_naquadate"))
                .fluid()
                .color(0x255A55)
                .components(Xenon, 1, NaquadahEnriched, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        GoldTrifluoride = new Material.Builder(GTCEu.id("gold_trifluoride"))
                .dust()
                .color(0xE8C478)
                .iconSet(BRIGHT)
                .components(Gold, 1, Fluorine, 3)
                .buildAndRegister();
        XenoauricFluoroantimonicAcid = new Material.Builder(GTCEu.id("xenoauric_fluoroantimonic_acid"))
                .fluid(FluidStorageKeys.LIQUID, new FluidBuilder().attribute(FluidAttributes.ACID))
                .color(0xE0BD74)
                .components(Xenon, 1, Gold, 1, Antimony, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        GoldChloride = new Material.Builder(GTCEu.id("gold_chloride"))
                .fluid()
                .color(0xCCCC66)
                .components(Gold, 2, Chlorine, 6)
                .buildAndRegister();
        BromineTrifluoride = new Material.Builder(GTCEu.id("bromine_trifluoride"))
                .fluid()
                .color(0xA88E57)
                .components(Bromine, 1, Fluorine, 3)
                .buildAndRegister();
        HexafluorideNaquadriaSolution = new Material.Builder(GTCEu.id("hexafluoride_naquadria_solution"))
                .fluid()
                .color(0x25C213)
                .components(Naquadria, 1, Fluorine, 6)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        RadonDifluoride = new Material.Builder(GTCEu.id("radon_difluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Radon, 1, Fluorine, 2)
                .buildAndRegister();
        RadonNaquadriaOctafluoride = new Material.Builder(GTCEu.id("radon_naquadria_octafluoride"))
                .fluid()
                .color(0x85F378)
                .components(Radon, 1, Naquadria, 1, Fluorine, 8)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        CaesiumFluoride = new Material.Builder(GTCEu.id("caesium_fluoride"))
                .fluid()
                .color(0xFF7A5F)
                .components(Caesium, 1, Fluorine, 1)
                .buildAndRegister();
        XenonTrioxide = new Material.Builder(GTCEu.id("xenon_trioxide"))
                .fluid()
                .color(0x359FC3)
                .components(Xenon, 1, Oxygen, 3)
                .buildAndRegister();
        CaesiumXenontrioxideFluoride = new Material.Builder(GTCEu.id("caesium_xenontrioxide_fluoride"))
                .fluid()
                .color(0x5067D7)
                .flags(DISABLE_DECOMPOSITION)
                .components(Caesium, 1, Xenon, 1, Oxygen, 3, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        NaquadriaCaesiumXenonnonfluoride = new Material.Builder(GTCEu.id("naquadria_caesium_xenonnonfluoride"))
                .fluid()
                .color(0x54C248)
                .components(Naquadria, 1, Caesium, 1, Xenon, 1, Fluorine, 9)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        RadonTrioxide = new Material.Builder(GTCEu.id("radon_trioxide"))
                .fluid()
                .color(0x9A6DD7)
                .components(Radon, 1, Oxygen, 3)
                .buildAndRegister();
        NaquadriaCaesiumfluoride = new Material.Builder(GTCEu.id("naquadria_caesiumfluoride"))
                .fluid()
                .color(0xAAEB69)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister()
                .setFormula("*Nq*F2CsF", true);
        NitrosoniumOctafluoroxenate = new Material.Builder(GTCEu.id("nitrosonium_octafluoroxenate"))
                .fluid()
                .color(0x953D9F)
                .components(NitrogenDioxide, 2, Xenon, 1, Fluorine, 8)
                .buildAndRegister()
                .setFormula("(NO2)2XeF8", true);
        NitrylFluoride = new Material.Builder(GTCEu.id("nitryl_fluoride"))
                .fluid()
                .color(0x8B7EFF)
                .components(Nitrogen, 1, Oxygen, 2, Fluorine, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        AcidicNaquadriaCaesiumfluoride = new Material.Builder(GTCEu.id("acidic_naquadria_caesiumfluoride"))
                .fluid()
                .color(0x75EB00)
                .components(Naquadria, 1, Fluorine, 3, Caesium, 1, Sulfur, 2, Oxygen, 8)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister()
                .setFormula("*Nq*F2CsF(SO4)2", true);

        SupercriticalSteam = new Material.Builder(GTCEu.id("supercritical_steam"))
                .gas(new FluidBuilder().temperature(1000))
                .color(0xC4C4C4)
                .components(Water, 1)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        WaterAgarMix = new Material.Builder(GTCEu.id("water_agar_mix"))
                .fluid()
                .color(0x88FFC0)
                .buildAndRegister();

        TungstenTrioxide = new Material.Builder(GTCEu.id("tungsten_trioxide"))
                .dust()
                .components(Tungsten, 1, Oxygen, 3)
                .color(0x86FF75).iconSet(ROUGH)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        SpaceTime = new Material.Builder(GTCEu.id("spacetime"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1).customStill())
                .fluidPipeProperties(2147483647, ConfigHolder.INSTANCE.spacetimePip, true, true, true, true)
                .element(GTLElements.SPACETIME)
                .iconSet(new MaterialIconSet("spacetime"))
                .flags(GTLMaterialFlags.GENERATE_NANOSWARM, MaterialFlags.NO_UNIFICATION)
                .cableProperties(GTValues.V[GTValues.MAX], 524288, 0, true)
                .buildAndRegister();

        Infinity = new Material.Builder(GTCEu.id("infinity"))
                .ingot()
                .liquid(new FluidBuilder().temperature(1000000).customStill().translation("fluid.kubejs.infinity"))
                .blastTemp(32000, BlastProperty.GasTier.HIGHEST)
                .element(GTLElements.INFINITY)
                .iconSet(new MaterialIconSet("infinity"))
                .flags(MaterialFlags.GENERATE_FRAME)
                .cableProperties(GTValues.V[GTValues.MAX], 8192, 0, true)
                .buildAndRegister();

        CompoundTriniite = new Material.Builder(GTCEu.id("trinium_compound"))
                .dust()
                .ore()
                .components(Trinium, 3, Actinium, 3, Selenium, 4, Astatine, 4)
                .color(0x675989)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        CrystallineNitricAcid = new Material.Builder(GTCEu.id("crystalline_nitric_acid"))
                .dust()
                .components(Hydrogen, 1, Nitrogen, 1, Oxygen, 3)
                .color(0xCDBD14)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        SodiumChlorate = new Material.Builder(GTCEu.id("sodium_chlorate"))
                .dust()
                .components(Sodium, 1, Chlorine, 1, Oxygen, 3)
                .color(0xA5A5A5)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        SodiumPerchlorate = new Material.Builder(GTCEu.id("sodium_perchlorate"))
                .dust()
                .components(Sodium, 1, Chlorine, 1, Oxygen, 4)
                .color(0xF0F0F0)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        ActiniumTriniumHydroxides = new Material.Builder(GTCEu.id("actinium_trinium_hydroxides"))
                .dust()
                .color(0xAD47CF).iconSet(ROUGH)
                .buildAndRegister().setFormula("Ke3Ac2(OH)12");
        SeleniumOxide = new Material.Builder(GTCEu.id("selenium_oxide"))
                .dust()
                .color(0xFFF71C).iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        TriniumTetrafluoride = new Material.Builder(GTCEu.id("trinium_tetrafluoride"))
                .dust()
                .color(0x529E57)
                .components(Trinium, 1, Fluorine, 4)
                .buildAndRegister();
        Fluorocarborane = new Material.Builder(GTCEu.id("fluorocarborane"))
                .dust()
                .components(Hydrogen, 1, Carbon, 1, Hydrogen, 1, Boron, 11, Fluorine, 11)
                .color(0x00EC80).iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        CaesiumNitrate = new Material.Builder(GTCEu.id("caesium_nitrate"))
                .dust()
                .components(Caesium, 1, Nitrogen, 1, Oxygen, 3)
                .color(0x821EC7).iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        CesiumCarborane = new Material.Builder(GTCEu.id("cesium_carborane"))
                .dust()
                .components(Caesium, 1, Carbon, 1, Boron, 11, Hydrogen, 12)
                .color(0xAE6EDA).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        SilverIodide = new Material.Builder(GTCEu.id("silver_iodide"))
                .dust()
                .components(Silver, 1, Iodine, 1)
                .color(0xACA56A).iconSet(DULL)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        SilverNitrate = new Material.Builder(GTCEu.id("silver_nitrate"))
                .dust()
                .components(Silver, 1, Nitrogen, 1, Oxygen, 3)
                .color(0xFFFCE0).iconSet(BRIGHT)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();

        FumingNitricAcid = new Material.Builder(GTCEu.id("fuming_nitric_acid"))
                .fluid()
                .components(Hydrogen, 1, Nitrogen, 1, Oxygen, 3)
                .color(0xB46800).iconSet(GTLMaterialIconSet.LIMPID)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        Perfluorobenzene = new Material.Builder(GTCEu.id("perfluorobenzene"))
                .fluid()
                .components(Carbon, 6, Fluorine, 6)
                .color(0x15752B).iconSet(GTLMaterialIconSet.LIMPID)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister().setFormula("(CH3)3SiCl");
        Trimethylsilane = new Material.Builder(GTCEu.id("trimethylsilane"))
                .fluid()
                .components(Carbon, 3, Hydrogen, 10, Silicon, 1)
                .color(0x7D2FC3).iconSet(GTLMaterialIconSet.LIMPID)
                .flags(DISABLE_DECOMPOSITION)
                .buildAndRegister();
        Trimethylchlorosilane = new Material.Builder(GTCEu.id("trimethylchlorosilane"))
                .fluid()
                .color(0x591399).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();
        NitratedTriniiteCompoundSolution = new Material.Builder(GTCEu.id("nitrated_triniite_compound_solution"))
                .fluid()
                .color(0x5E9699).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();
        ResidualTriniiteSolution = new Material.Builder(GTCEu.id("residual_triniite_solution"))
                .fluid()
                .color(0x68B59).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();
        ActiniumRadiumHydroxideSolution = new Material.Builder(GTCEu.id("actinium_radium_hydroxide_solution"))
                .fluid()
                .color(0xC3E0DC).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();
        ActiniumRadiumNitrateSolution = new Material.Builder(GTCEu.id("actinium_radium_nitrate_solution"))
                .fluid()
                .color(0x89C0B3).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();
        HeavilyFluorinatedTriniumSolution = new Material.Builder(GTCEu.id("heavily_fluorinated_trinium_solution"))
                .fluid()
                .color(0x169A33).iconSet(GTLMaterialIconSet.LIMPID)
                .buildAndRegister();

        if (!Platform.isDevEnv()) {
            TagPrefix.ingot.setIgnored(Infinity, ModItems.infinity_ingot);
            TagPrefix.block.setIgnored(Infinity, ModBlocks.infinity);
        }
    }
}
