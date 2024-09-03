package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.sound.ExistingSoundEntry;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;
import com.lowdragmc.lowdraglib.utils.LocalizationUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.gtlcore.gtlcore.config.ConfigHolder;
import org.gtlcore.gtlcore.data.recipe.GenerateDisassembly;

import static com.gregtechceu.gtceu.api.GTValues.EV;
import static com.gregtechceu.gtceu.api.GTValues.V;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static com.lowdragmc.lowdraglib.gui.texture.ProgressTexture.FillDirection.LEFT_TO_RIGHT;


public class GTLRecipeTypes {

    public static final GTRecipeType PRIMITIVE_VOID_ORE_RECIPES = ConfigHolder.INSTANCE.enablePrimitiveVoidOre ?
            register("primitive_void_ore", MULTIBLOCK)
                    .setMaxIOSize(0, 0, 1, 0)
                    .setMaxTooltips(1)
                    .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
                    .setSound(GTSoundEntries.MINER) :
            null;

    public final static GTRecipeType SEMI_FLUID_GENERATOR_FUELS = register("semi_fluid_generator", GENERATOR)
            .setMaxIOSize(0, 0, 1, 0).setEUIO(IO.OUT)
            .setSlotOverlay(false, true, true, GuiTextures.FURNACE_OVERLAY_2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.COMBUSTION);

    public final static GTRecipeType SUPERCRITICAL_STEAM_TURBINE_FUELS = register(
            "supercritical_steam_turbine", GENERATOR)
            .setMaxIOSize(0, 0, 1, 1)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, true, true, GuiTextures.CENTRIFUGE_OVERLAY)
            .setProgressBar(GuiTextures.PROGRESS_BAR_GAS_COLLECTOR, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.TURBINE);

    public final static GTRecipeType ROCKET_ENGINE_FUELS = register("rocket_engine", GENERATOR)
            .setEUIO(IO.OUT)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setMaxIOSize(0, 0, 1, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.JET_ENGINE);

    public final static GTRecipeType ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES = register("electric_implosion_compressor",
            MULTIBLOCK)
            .setMaxIOSize(2, 1, 0, 0).setEUIO(IO.IN)
            .prepareBuilder(recipeBuilder -> recipeBuilder.duration(1).EUt(GTValues.VA[GTValues.UV]))
            .setSlotOverlay(false, false, true, GuiTextures.IMPLOSION_OVERLAY_1)
            .setSlotOverlay(false, false, false, GuiTextures.IMPLOSION_OVERLAY_2)
            .setSlotOverlay(true, false, true, GuiTextures.DUST_OVERLAY)
            .setSound(new ExistingSoundEntry(SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS));

    public static final GTRecipeType DISASSEMBLY = register("disassembly", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(1, 16, 0, 4)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ASSEMBLER);

    public static final GTRecipeType NEUTRON_ACTIVATOR_RECIPES = register("neutron_activator", MULTIBLOCK)
            .setMaxIOSize(6, 3, 1, 1)
            .setSound(GTSoundEntries.COOLING)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.ev_min", data.getInt("ev_min")))
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.ev_max", data.getInt("ev_max")))
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.evt", data.getInt("evt")));

    public static final GTRecipeType HEAT_EXCHANGER_RECIPES = register("heat_exchanger", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(0, 0, 2, 2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_MIXER, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setMaxTooltips(1)
            .setSound(GTSoundEntries.COOLING);

    public static final GTRecipeType ELEMENT_COPYING_RECIPES = register("element_copying", GTRecipeTypes.MULTIBLOCK)
            .setMaxIOSize(1, 1, 2, 1)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW_MULTIPLE, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ARC);

    public static final GTRecipeType DEHYDRATOR_RECIPES = register("dehydrator", GTRecipeTypes.ELECTRIC)
            .setMaxIOSize(2, 6, 2, 2)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ARC);

    public static final GTRecipeType INTEGRATED_ORE_PROCESSOR = register("integrated_ore_processor", MULTIBLOCK)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setMaxIOSize(2, 9, 1, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.MACERATOR);

    public static final GTRecipeType FISSION_REACTOR_RECIPES = register("fission_reactor", MULTIBLOCK)
            .setMaxIOSize(1, 1, 0, 0)
        .setEUIO(IO.IN)
        .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
        .setSound(GTSoundEntries.ARC)
        .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.frheat", FormattingUtil.formatNumbers(data.getInt("FRheat"))));

    public static final GTRecipeType SPACE_ELEVATOR_RECIPES = register("space_elevator", MULTIBLOCK)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setMaxIOSize(1, 0, 0, 0)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT);

    public static String getSCTier(int tier) {
        return switch (tier) {
            case 3 -> I18n.get("gtceu.tier.ultimate");
            case 2 -> I18n.get("gtceu.tier.advanced");
            default -> I18n.get("gtceu.tier.base");
        };
    }

    public static final GTRecipeType STELLAR_FORGE_RECIPES = register("stellar_forge", MULTIBLOCK)
            .setEUIO(IO.IN)
            .setSlotOverlay(false, false, GuiTextures.SOLIDIFIER_OVERLAY)
            .setMaxIOSize(3, 2, 9, 2)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARC_FURNACE, LEFT_TO_RIGHT)
            .setSound(GTSoundEntries.ARC)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.stellar_containment_tier", getSCTier(data.getInt("SCTier"))));

    public static final GTRecipeType COMPONENT_ASSEMBLY_LINE_RECIPES = register("component_assembly_line", "multiblock")
            .setMaxIOSize(9, 1, 9, 0)
            .setEUIO(IO.IN)
            .setProgressBar(GuiTextures.PROGRESS_BAR_ARROW, LEFT_TO_RIGHT)
            .addDataInfo(data -> LocalizationUtils.format("gtceu.recipe.ca_tier", GTValues.VN[data.getInt("CATier")]))
            .setSound(GTSoundEntries.ASSEMBLER);

    public static void init() {
        GTRecipeTypes.ASSEMBLER_RECIPES.onRecipeBuild(GenerateDisassembly::generateDisassembly);
        GTRecipeTypes.ASSEMBLER_RECIPES.onRecipeBuild(GenerateDisassembly::generateDisassembly);
        GTRecipeTypes.PLASMA_GENERATOR_FUELS.onRecipeBuild((recipeBuilder, provider) -> {
            long eu = recipeBuilder.duration * V[EV];
            HEAT_EXCHANGER_RECIPES.recipeBuilder(recipeBuilder.id)
                    .inputFluids(FluidRecipeCapability.CAP.of(recipeBuilder.input
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .inputFluids(GTMaterials.DistilledWater.getFluid(eu / 160))
                    .outputFluids(FluidRecipeCapability.CAP.of(recipeBuilder.output
                            .get(FluidRecipeCapability.CAP).get(0).getContent()))
                    .outputFluids(GTLMaterials.SupercriticalSteam.getFluid(eu))
                    .addData("eu", eu)
                    .duration(200)
                    .save(provider);
        });
    }
}
