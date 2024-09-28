package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLRecipeModifiers;
import org.gtlcore.gtlcore.common.data.GTLRecipeTypes;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.PrimitiveOreMachine;
import org.gtlcore.gtlcore.config.ConfigHolder;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import committee.nova.mods.avaritia.init.registry.ModBlocks;

import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

@SuppressWarnings("unused")
public class MultiBlockMachineB {

    public static void init() {}

    public final static MultiblockMachineDefinition GRAVITATION_SHOCKBURST = REGISTRATE.multiblock("gravitation_shockburst", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.GRAVITATION_SHOCKBURST_RECIPES)
            .recipeType(GTLRecipeTypes.ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES)
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_2.tooltip",
                    Component.translatable("gtceu.gravitation_shockburst"), Component.translatable("gtceu.electric_implosion_compressor")))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(GTLBlocks.CREATE_CASING)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("aaaaaaaaa", "         ", "         ", "         ", "         ", "         ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " abbbbba ", " abbbbba ", " abbbbba ", " abbbbba ", " abbbbba ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " b ccc b ", " bcccccb ", " bcccccb ", " bcccccb ", " b ccc b ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " bcccccb ", " bcccccb ", " bcc ccb ", " bcccccb ", " bcccccb ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " bcccccb ", " bcc ccb ", " bc   cb ", " bcc ccb ", " bcccccb ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " bcccccb ", " bcccccb ", " bcc ccb ", " bcccccb ", " bcccccb ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " b ccc b ", " bcccccb ", " bcccccb ", " bcccccb ", " b ccc b ", "aaaaaaaaa")
                    .aisle("aaaaaaaaa", " abbbbba ", " abbbbba ", " abbbbba ", " abbbbba ", " abbbbba ", "aaaaaaaaa")
                    .aisle("aaaa~aaaa", "         ", "         ", "         ", "         ", "         ", "aaaaaaaaa")
                    .where("a", Predicates.blocks(GTLBlocks.CREATE_CASING.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.OPTICAL_DATA_RECEPTION).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1)))
                    .where("b", Predicates.blocks(GTLBlocks.INFINITY_GLASS.get()))
                    .where("c", Predicates.blocks(ModBlocks.infinity.get()))
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTLCore.id("block/create_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition DISSOLVING_TANK = REGISTRATE
            .multiblock("dissolving_tank", WorkableElectricMultiblockMachine::new)
            .langValue("Dissolving Tank")
            .tooltips(Component.translatable("gtceu.multiblock.dissolving_tank.tooltip.0"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.dissolution_treatment")))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .rotationState(RotationState.ALL)
            .recipeTypes(GTLRecipeTypes.DISSOLUTION_TREATMENT)
            .recipeModifier(GTLRecipeModifiers::dissolvingTankOverclock)
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("X###X", "OOOOO", "XGGGX", "XGGGX", "#XXX#")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("#####", "OKKKO", "GAAAG", "GAAAG", "XXXXX")
                    .aisle("X###X", "OOSOO", "XGGGX", "XGGGX", "#XXX#")
                    .where('S', Predicates.controller(Predicates.blocks(definition.get())))
                    .where('X', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where('K', Predicates.blocks(GTBlocks.CASING_INVAR_HEATPROOF.get()))
                    .where('O', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true)))
                    .where('G', Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where('A', Predicates.air())
                    .where('#', Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"), GTCEu.id("block/multiblock/generator/large_gas_turbine"))
            .register();

    public final static MultiblockMachineDefinition DIGESTION_TANK = REGISTRATE
            .multiblock("digestion_tank", CoilWorkableElectricMultiblockMachine::new)
            .langValue("Digestion Tank")
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.digestion_treatment")))
            .tooltipBuilder(GTLMachines.GTL_ADD)
            .rotationState(RotationState.ALL)
            .recipeTypes(GTLRecipeTypes.DIGESTION_TREATMENT)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
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
                    .where('X', Predicates.blocks(GTBlocks.CASING_STAINLESS_CLEAN.get()))
                    .where('K', Predicates.blocks(GTBlocks.CASING_INVAR_HEATPROOF.get()))
                    .where('Y', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .where('M', Predicates.heatingCoils())
                    .where('O', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.autoAbilities(true, false, true))
                            .or(Predicates.abilities(PartAbility.MUFFLER)))
                    .where('A', Predicates.air())
                    .where('#', Predicates.any())
                    .build())
            .beforeWorking((machine, recipe) -> machine instanceof CoilWorkableElectricMultiblockMachine coilMachine && coilMachine.getCoilType().getCoilTemperature() >= recipe.data.getInt("ebf_temp"))
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/gcym/large_maceration_tower"))
            .register();

    public final static MultiblockMachineDefinition PRIMITIVE_VOID_ORE = ConfigHolder.INSTANCE.enablePrimitiveVoidOre ?
            REGISTRATE.multiblock("primitive_void_ore", PrimitiveOreMachine::new)
                    .langValue("Primitive Void Ore")
                    .tooltips(Component.literal("运行时根据维度每tick随机产出一组任意粗矿"))
                    .tooltips(Component.literal("支持主世界,下界,末地"))
                    .tooltipBuilder(GTLMachines.GTL_ADD)
                    .rotationState(RotationState.ALL)
                    .recipeType(GTLRecipeTypes.PRIMITIVE_VOID_ORE_RECIPES)
                    .appearanceBlock(() -> Blocks.DIRT)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("XXX", "XXX", "XXX")
                            .aisle("XXX", "XAX", "XXX")
                            .aisle("XXX", "XSX", "XXX")
                            .where('S', Predicates.controller(Predicates.blocks(definition.get())))
                            .where('X',
                                    Predicates.blocks(Blocks.DIRT)
                                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where('A', Predicates.air())
                            .build())
                    .workableCasingRenderer(new ResourceLocation("minecraft:block/dirt"),
                            GTCEu.id("block/multiblock/gcym/large_extractor"))
                    .register() :
            null;
}
