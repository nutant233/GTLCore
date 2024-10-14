package org.gtlcore.gtlcore.config;

import com.gregtechceu.gtceu.config.ConfigHolder;

public class GTConfigModify {

    public static void init() {
        ConfigHolder.INSTANCE.recipes.generateLowQualityGems = false;
        ConfigHolder.INSTANCE.recipes.disableManualCompression = true;
        ConfigHolder.INSTANCE.recipes.harderRods = false;
        ConfigHolder.INSTANCE.recipes.harderBrickRecipes = false;
        ConfigHolder.INSTANCE.recipes.nerfWoodCrafting = true;
        ConfigHolder.INSTANCE.recipes.hardWoodRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardIronRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardRedstoneRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardMiscRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardGlassRecipes = true;
        ConfigHolder.INSTANCE.recipes.nerfPaperCrafting = true;
        ConfigHolder.INSTANCE.recipes.hardAdvancedIronRecipes = true;
        ConfigHolder.INSTANCE.recipes.hardDyeRecipes = true;
        ConfigHolder.INSTANCE.recipes.harderCharcoalRecipe = true;
        ConfigHolder.INSTANCE.recipes.flintAndSteelRequireSteel = true;
        ConfigHolder.INSTANCE.recipes.removeVanillaBlockRecipes = true;
        ConfigHolder.INSTANCE.recipes.removeVanillaTNTRecipe = true;
        ConfigHolder.INSTANCE.recipes.casingsPerCraft = 2;
        ConfigHolder.INSTANCE.recipes.harderCircuitRecipes = false;
        ConfigHolder.INSTANCE.recipes.hardMultiRecipes = true;
        ConfigHolder.INSTANCE.recipes.enchantedTools = true;

        ConfigHolder.INSTANCE.worldgen.allUniqueStoneTypes = true;

        ConfigHolder.INSTANCE.machines.prospectorEnergyUseMultiplier = 1;
        ConfigHolder.INSTANCE.machines.steelSteamMultiblocks = false;
        ConfigHolder.INSTANCE.machines.enableCleanroom = true;
        ConfigHolder.INSTANCE.machines.cleanMultiblocks = false;
        ConfigHolder.INSTANCE.machines.enableResearch = true;
        ConfigHolder.INSTANCE.machines.enableMaintenance = true;
        ConfigHolder.INSTANCE.machines.enableWorldAccelerators = true;
        ConfigHolder.INSTANCE.machines.ghostCircuit = true;
        ConfigHolder.INSTANCE.machines.doBedrockOres = false;
        ConfigHolder.INSTANCE.machines.orderedAssemblyLineFluids = true;
        ConfigHolder.INSTANCE.machines.enableMoreDualHatchAbility = false;

        ConfigHolder.INSTANCE.machines.smallBoilers.solidBoilerBaseOutput = 240;
        ConfigHolder.INSTANCE.machines.smallBoilers.hpSolidBoilerBaseOutput = 600;
        ConfigHolder.INSTANCE.machines.smallBoilers.liquidBoilerBaseOutput = 480;
        ConfigHolder.INSTANCE.machines.smallBoilers.hpLiquidBoilerBaseOutput = 1200;
        ConfigHolder.INSTANCE.machines.smallBoilers.solarBoilerBaseOutput = 240;
        ConfigHolder.INSTANCE.machines.smallBoilers.hpSolarBoilerBaseOutput = 720;

        ConfigHolder.INSTANCE.machines.largeBoilers.bronzeBoilerMaxTemperature = 1600;
        ConfigHolder.INSTANCE.machines.largeBoilers.steelBoilerMaxTemperature = 3600;
        ConfigHolder.INSTANCE.machines.largeBoilers.titaniumBoilerMaxTemperature = 6400;
        ConfigHolder.INSTANCE.machines.largeBoilers.tungstensteelBoilerMaxTemperature = 12800;

        ConfigHolder.INSTANCE.gameplay.hazardsEnabled = false;
        ConfigHolder.INSTANCE.gameplay.universalHazards = false;
        ConfigHolder.INSTANCE.gameplay.environmentalHazards = false;

        ConfigHolder.INSTANCE.compat.energy.nativeEUToPlatformNative = true;
        ConfigHolder.INSTANCE.compat.energy.enablePlatformConverters = true;
        ConfigHolder.INSTANCE.compat.energy.platformToEuRatio = 16;
        ConfigHolder.INSTANCE.compat.energy.euToPlatformRatio = 1;
        ConfigHolder.INSTANCE.compat.ae2.meHatchEnergyUsage = 1920;
        ConfigHolder.INSTANCE.compat.showDimensionTier = true;
        ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams = true;
    }
}
