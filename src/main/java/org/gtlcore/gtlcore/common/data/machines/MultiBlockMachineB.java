package org.gtlcore.gtlcore.common.data.machines;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLMachines;
import org.gtlcore.gtlcore.common.data.GTLRecipeTypes;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.PrimitiveOreMachine;
import org.gtlcore.gtlcore.config.ConfigHolder;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import committee.nova.mods.avaritia.init.registry.ModBlocks;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

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
                            .where('S', controller(blocks(definition.get())))
                            .where('X',
                                    blocks(Blocks.DIRT)
                                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where('A', air())
                            .build())
                    .workableCasingRenderer(new ResourceLocation("minecraft:block/dirt"),
                            GTCEu.id("block/multiblock/gcym/large_extractor"))
                    .register() :
            null;
}
