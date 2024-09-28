package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.*;
import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.*;

import net.minecraft.network.chat.Component;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

public class LazyMachine {

    public static void init() {}

    public final static MultiblockMachineDefinition WOOD_DISTILLATION = REGISTRATE
            .multiblock("wood_distillation", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTLLazyRecipeTypes.WOOD_DISTILLATION_RECIPES)
            .tooltips(Component.literal("先进的原始人分馏工艺¿"))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK))
            .appearanceBlock(GTBlocks.CASING_INVAR_HEATPROOF)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("ABBBA IIIII IIIII ABBBA", "AAAAA IIIII IIIII AAAAA", "CAAAC CIIIC CIIIC CAAAC", "C   C C   C C   C CCCCC", "CCCCC C   C C   C CAAAC", "CAAAC C   C CCCCC C   C", "C   C CCCCC CJJJC CAAAC", "CAAAC CJJJC CJJJC C   C", "C   C CJJJC CJJJC CAAAC", "CAAAC CJJJC CBBBC C   C", "C   C CBBBC C   C CBBBC", "CAAAC CCCCC CCCCC CDDDC", "C   C C   C CDDDC CDDDC", "CBBBC CCCCC CDDDC CDDDC", "CDDDC CDDDC CDDDC CCCCC", "CDDDC CDDDC CCCCC      ", "CCCCC CDDDC            ", "      CDDDC            ", "      CCCCC            ", "                       ")
                    .aisle("BBBBB IKKKI IKKKI BBBBB", "AAAAA IKKKI IKKKI AAAAA", "ADDDA IDJDI IDJDI ADDDA", " DDD   DJD   DJD  CDDDC", "CDDDC  DJD   DJDC A   A", "A   A  DJD  CDJDC  EEE ", " EEE  CDJDC J   J A   A", "A   A J   J J   J  EEE ", " EEE  J   J J   J A   A", "A   A J   J B   B  EEE ", " EEE  B   B  DDD  B   B", "A   A CDDDC CDDDC D   D", " EEE   DDD  D   D D   D", "B   B CEEEC D   D D   D", "D   D D   D D   D CDDDC", "D   D D   D CDDDC  DDD ", "CDDDC D   D  DDD       ", " DDD  D   D            ", "      CDDDC            ", "       DDD             ")
                    .aisle("BBBBB IKKKI IKKKI BBBBB", "AAGAA IKGKI IKGKI AAGAA", "ADGDA IJGJI IJGJI ADGDA", " DGD   JGJ   JGJ  CDGDC", "CDGDC  JGJ   JGJC A G A", "A G A  JGJ  CJGJC  EGE ", " EGE  CJGJC J   J A G A", "A G A J   J J   J  EGE ", " EGE  J   J J   J A G A", "A G A J   J B   B  EGE ", " EGE  B   B  DGD  B   B", "A G A CDGDC CDGDC D   D", " EEE   DGD  D   GGD   D", "B   B CEEEC D   D D   D", "D   GGG   D D   D CD DC", "D   D D   D CD DC  DDD ", "CD DC D   D  DDD       ", " DDD  D   D            ", "      CD DC            ", "       DDD             ")
                    .aisle("BBBBB IKKKI IKKKI BBBBB", "AAAAA IKGKI IKGKI AAAAA", "ADDDA IDJDI IDJDI ADDDA", " DDD   DJD   DGD  CDDDC", "CDDDC  DJD   DJDC A   A", "A   A  DJD  CDJDC  EEE ", " EEE  CDJDC J   J A   A", "A   A J   J J   J  EEE ", " EEE  J   J J   J A   A", "A   A J   J B   B  EEE ", " EEE  B   B  DDD  B   B", "A   A CDDDC CDDDC D   D", " EEE   DDD  D   D D   D", "B   B CEEEC D   D D   D", "D   D D   D D   D CDDDC", "D   D D   D CDDDC  DDD ", "CDDDC D   D  DDD       ", " DDD  D   D            ", "      CDDDC            ", "       DDD             ")
                    .aisle("ABBBA IKKKI IKKKI ABBBA", "AAAAA IIGII IIGII AAAAA", "CAAAC CIIIC CIIIC CAAAC", "C   C C   C C   C CCCCC", "CCCCC C   C C   C CAAAC", "CAAAC C   C CCCCC C   C", "C   C CCCCC CJJJC CAAAC", "CAAAC CJJJC CJJJC C   C", "C   C CJJJC CJJJC CAAAC", "CAAAC CJJJC CBBBC C   C", "C   C CBBBC C   C CBBBC", "CAAAC CCCCC CCCCC CDDDC", "C   C C   C CDDDC CDDDC", "CBBBC CCCCC CDDDC CDDDC", "CDDDC CDDDC CDDDC CCCCC", "CDDDC CDDDC CCCCC      ", "CCCCC CDDDC            ", "      CDDDC            ", "      CCCCC            ", "                       ")
                    .aisle("                       ", "        G     G        ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("                       ", "        G     G        ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("                       ", "        G     G        ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("C   C  IIIIIIIII       ", "CCCCC  IGIIIIIGI       ", "CDDDC  IIIIIIIII       ", "CDDDC  IIIIIIIII       ", "CDDDC  IIIIIIIII       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("       IKKKKKKKI       ", "CDDDC  I       I       ", "D   D  I       I       ", "D   D  I       I       ", "D   D  IIIIIIIII       ", " DDD     I   I         ", "         I   I         ", "         I   I         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("       IKKKKKKKI       ", "CDDDC  I       I       ", "D   D  I       I       ", "D   D  I       I       ", "D   D  IIEIIIEII       ", " DDD    IEI IEI        ", "        IEI IEI        ", "        IHI IHI        ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("       IKKKKKKKI       ", "CDDDC  I       I       ", "D   D  I       I       ", "D   D  I       I       ", "D   D  IIIIIIIII       ", " DDD     I   I         ", "         I   I         ", "         I   I         ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("       IILLLLLII       ", "CDDDC  IILLMLLII       ", "D   D  IILLLLLII       ", "D   D  IIIIIIIII       ", "D   D  IIIIIIIII       ", " DDD                   ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("                       ", "CDDDC                  ", "D   D                  ", "D   D                  ", "D   D                  ", " DDD                   ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .aisle("C   C                  ", "CCCCC                  ", "CDNDC                  ", "CDNDC                  ", "CDDDC                  ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ", "                       ")
                    .where("M", controller(blocks(definition.get())))
                    .where("I", blocks(GTBlocks.CASING_INVAR_HEATPROOF.get()))
                    .where("N", blocks(GTBlocks.HERMETIC_CASING_HV.get()))
                    .where("J", blocks(GTBlocks.CASING_STAINLESS_EVAPORATION.get()))
                    .where("E", blocks(GTBlocks.FILTER_CASING.get()))
                    .where("L", blocks(GTBlocks.CASING_INVAR_HEATPROOF.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(6))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(Registries.getBlock("gtceu:heat_vent")))
                    .where("H", Predicates.abilities(PartAbility.MUFFLER))
                    .where("D", blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where("G", blocks(GTBlocks.CASING_STEEL_PIPE.get()))
                    .where("K", blocks(GTBlocks.FIREBOX_STEEL.get()))
                    .where("C", Predicates.blocks(Registries.getBlock("gtceu:stainless_steel_frame")))
                    .where("A", blocks(GTBlocks.CASING_ALUMINIUM_FROSTPROOF.get()))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_heatproof"), GTCEu.id("block/multiblock/electric_blast_furnace"))
            .register();
}
