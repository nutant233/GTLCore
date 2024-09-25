package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.machine.multiblock.NoEnergyMultiblockMachine;
import org.gtlcore.gtlcore.api.pattern.GTLPredicates;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;
import org.gtlcore.gtlcore.common.data.GTLRecipeTypes;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.*;
import org.gtlcore.gtlcore.common.machine.multiblock.steam.LargeSteamParallelMultiblockMachine;
import org.gtlcore.gtlcore.utils.Registries;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.LargeBoilerMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.material.Fluids;

import committee.nova.mods.avaritia.init.registry.ModBlocks;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.CASING_WATERTIGHT;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.ELECTRIC_OVERCLOCK;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DISSOVING;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.DIGESTION;

public class LanthanideMachine {

    public static void init() {
        GeneratorMachine.init();
        AdvancedMultiBlockMachine.init();
    }

    //溶解罐
    public final static MultiblockMachineDefinition DISSOVING_TANK = REGISTRATE
            .multiblock("dissolving_tank", WorkableElectricMultiblockMachine::new)
            .langValue("Dissolving Tank")
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip.tooltip",
                    Component.translatable("gtceu.dissolving_tank")))
            .rotationState(RotationState.ALL)
            .recipeTypes(DISSOVING)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXX", "XXXXX", "XXXXX")
                    .aisle("XXXXX", "XXXXX", "X   X")
                    .aisle("XXXXX", "X   X", "X   X")
                    .aisle("XXXXX", "X   X", "X   X")
                    .aisle("XXXXX", "X   X", "X   X")
                    .aisle("XXXXX", "XXXXX", "X   X")
                    .aisle("XXXXX", "XXSXX", "XXXXX")
                    .where('S', controller(blocks(definition.get())))
                    .where('X', blocks(CASING_WATERTIGHT.get()).setMinGlobalLimited(55)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where(' ', Predicates.air())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/watertight_casing"),
                    GTCEu.id("block/multiblock/gcym/large_chemical_bath"))
            .compassNodeSelf()
            .register();



}
