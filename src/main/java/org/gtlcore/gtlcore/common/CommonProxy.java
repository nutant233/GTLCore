package org.gtlcore.gtlcore.common;

import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.config.GTLConfigHolder;
import org.gtlcore.gtlcore.integration.ae2.InfinityCellGuiHandler;
import org.gtlcore.gtlcore.integration.ae2.storage.InfinityCellHandler;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import appeng.api.storage.StorageCells;
import appeng.core.AELog;

import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class CommonProxy {

    public CommonProxy() {
        CommonProxy.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(eventBus);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::addMaterials);
        eventBus.addGenericListener(RecipeConditionType.class, this::registerRecipeConditions);
        eventBus.addGenericListener(GTRecipeType.class, this::registerRecipeTypes);
        eventBus.addGenericListener(MachineDefinition.class, this::registerMachines);
    }

    public static void init() {
        GTLCreativeModeTabs.init();
        GTLConfigHolder.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        StorageCells.addCellHandler(InfinityCellHandler.INSTANCE);
        StorageCells.addCellGuiHandler(new InfinityCellGuiHandler());
        event.enqueueWork(this::postRegistrationInitialization).whenComplete((res, err) -> {
            if (err != null) {
                AELog.warn(err);
            }
        });
    }

    public void postRegistrationInitialization() {
        GTLItems.InitUpgrades();
    }

    private void addMaterials(MaterialEvent event) {
        GTLMaterials.init();
    }

    private void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        GTLRecipeTypes.init();
    }

    private void registerRecipeConditions(GTCEuAPI.RegisterEvent<ResourceLocation, RecipeConditionType<?>> event) {
        GTLRecipeConditions.init();
    }

    private void registerMachines(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        GTLItems.init();
        GTLBlocks.init();
        GTLMachines.init();
    }
}
