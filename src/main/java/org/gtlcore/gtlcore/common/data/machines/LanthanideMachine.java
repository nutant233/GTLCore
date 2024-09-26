package org.gtlcore.gtlcore.common.data.machines;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.*;

import net.minecraft.network.chat.Component;
import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GCyMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.ELECTRIC_OVERCLOCK;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.*;

public class LanthanideMachine {


    public static void init() {
        GeneratorMachine.init();
        AdvancedMultiBlockMachine.init();
    }

    /**溶解罐*/
    public final static MultiblockMachineDefinition DISSOLVING_TANK = REGISTRATE
            .multiblock("dissolving_tank", WorkableElectricMultiblockMachine::new)
            .langValue("Dissolving Tank")
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("block.gtceu.dissolving_tank")))
            .rotationState(RotationState.ALL)
            .recipeTypes(DISSOLUTION_TREATMENT)
            .recipeModifiers(
                    GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK)
            )
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("X###X", "OOOOO", "XGGGX", "XGGGX", "#XXX#")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("X###X", "OOSOO", "XGGGX", "XGGGX", "#XXX#")
                    .where('S', Predicates.controller(Predicates.blocks(definition.get())))
                    .where('X', blocks(CASING_STAINLESS_CLEAN.get()))
                    .where('K', blocks(CASING_INVAR_HEATPROOF.get()))
                    .where('O', blocks(CASING_STAINLESS_CLEAN.get())
                            .or(autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where('G', blocks(CASING_TEMPERED_GLASS.get()))
                    .where('A', air())
                    .where('#', Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel")
                    , GTCEu.id("block/multiblock/generator/large_gas_turbine"))
            .compassNodeSelf()
            .register();

    /**煮解池*/
    public final static MultiblockMachineDefinition DIGESTION_TANK = REGISTRATE
            .multiblock("digestion_tank", WorkableElectricMultiblockMachine::new)
            .langValue("Digestion Tank")
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("block.gtceu.digestion_tank")))
            .rotationState(RotationState.ALL)
            .recipeTypes(DIGESTION_TREATMENT)
            .recipeModifiers(
                    GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK)
            )
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("#OOOOO#", "#YMMMY#", "##YYY##", "#######")
                    .aisle("OXXXXXO", "YMAAAMY", "#YAAAY#", "#YYYYY#")
                    .aisle("OXKKKXO", "MAAAAAM", "YAAAAAY", "#YAAAY#")
                    .aisle("OXKKKXO", "MAAAAAM", "YAAAAAY", "#YAAAY#")
                    .aisle("OXKKKXO", "MAAAAAM", "YAAAAAY", "#YAAAY#")
                    .aisle("OXXXXXO", "YMAAAMY", "#YAAAY#", "#YYYYY#")
                    .aisle("#OOSOO#", "#YMMMY#", "##YYY##", "#######")
                    .where('S', Predicates.controller(Predicates.blocks(definition.get())))
                    .where('X', blocks(CASING_STAINLESS_CLEAN.get()))
                    .where('K', blocks(CASING_INVAR_HEATPROOF.get()))
                    .where('Y', blocks(CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .where('M', heatingCoils())
                    .where('O', blocks(CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true))
                            .or(Predicates.abilities(PartAbility.MUFFLER)))
                    .where('A', air())
                    .where('#', Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel")
                    , GTCEu.id("block/multiblock/gcym/large_maceration_tower"))
            .compassNodeSelf()
            .register();

}
