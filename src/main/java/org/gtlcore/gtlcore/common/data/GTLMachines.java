package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.CleanroomType;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.client.renderer.machine.FusionReactorRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.MaintenanceHatchPartRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.SimpleGeneratorMachineRenderer;
import com.gregtechceu.gtceu.client.util.TooltipHelper;
import com.gregtechceu.gtceu.common.data.*;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.FusionReactorMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.steam.LargeBoilerMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.hepdd.gtmthings.common.registry.GTMTRegistration;
import com.hepdd.gtmthings.data.CreativeModeTabs;
import com.hepdd.gtmthings.data.CustomMachines;
import com.hepdd.gtmthings.data.WirelessMachines;
import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.machine.multiblock.GTLPartAbility;
import org.gtlcore.gtlcore.api.pattern.GTLPredicates;
import org.gtlcore.gtlcore.client.renderer.machine.SpaceElevatorRenderer;
import org.gtlcore.gtlcore.common.block.GTLFusionCasingBlock;
import org.gtlcore.gtlcore.common.data.machines.TootipsModify;
import org.gtlcore.gtlcore.common.machine.generator.LightningRodMachine;
import org.gtlcore.gtlcore.common.machine.generator.MagicEnergyMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.*;
import org.gtlcore.gtlcore.common.machine.multiblock.generator.ChemicalEnergyDevourerMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.generator.GeneratorArrayMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.generator.MegaTurbineMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.HeatExchangerMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.NeutronActivatorMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.noenergy.PrimitiveOreMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.part.*;
import org.gtlcore.gtlcore.common.machine.multiblock.steam.LargeSteamParallelMultiblockMachine;
import org.gtlcore.gtlcore.config.ConfigHolder;
import org.gtlcore.gtlcore.utils.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.api.pattern.util.RelativeDirection.*;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

public class GTLMachines {

    public static final BiConsumer<ItemStack, List<Component>> GTL_MODIFY = (stack, components) -> components
            .add(Component.translatable("gtlcore.registry.modify")
                    .withStyle(style -> style.withColor(TooltipHelper.RAINBOW.getCurrent())));

    public static final BiConsumer<ItemStack, List<Component>> GTL_ADD = (stack, components) -> components
            .add(Component.translatable("gtlcore.registry.add")
                    .withStyle(style -> style.withColor(TooltipHelper.RAINBOW_SLOW.getCurrent())));

    public static final BiConsumer<IMultiController, List<Component>> CHEMICAL_PLANT_DISPLAY = (controller, components) -> {
        if (controller.isFormed()) {
            double value = 1 - ((CoilWorkableElectricMultiblockMachine) controller).getCoilTier() * 0.05;
            components.add(Component.translatable("gtceu.machine.eut_multiplier.tooltip", value));
            components.add(Component.translatable("gtceu.machine.duration_multiplier.tooltip", value));
        }
    };

    public static final BiConsumer<IMultiController, List<Component>> MAX_TEMPERATURE = (controller, components) -> {
        if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
            components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                    Component.translatable(FormattingUtil.formatNumbers(coilMachine.getCoilType().getCoilTemperature() + 100L * Math.max(0, coilMachine.getTier() - GTValues.MV)) + "K")
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
        }
    };

    public static final BiConsumer<IMultiController, List<Component>> TEMPERATURE = (controller, components) -> {
        if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
            components.add(Component.translatable("gtceu.multiblock.blast_furnace.max_temperature",
                    Component.translatable(FormattingUtil.formatNumbers(coilMachine.getCoilType().getCoilTemperature()) + "K")
                            .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))));
        }
    };

    public static final BiConsumer<IMultiController, List<Component>> COIL_PARALLEL = (controller, components) -> {
        if(controller instanceof CoilWorkableElectricMultiblockMachine machine && controller.isFormed()) {
            components.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(Math.min(2147483647, (int) Math.pow(2, ((double) machine.getCoilType().getCoilTemperature() / 900))))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
        }
    };

    public static final BiConsumer<IMultiController, List<Component>> MULTIPLERECIPES_COIL_PARALLEL = (controller, components) -> {
        if(controller instanceof CoilWorkableElectricMultipleRecipesMultiblockMachine machine && controller.isFormed()) {
            components.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(Math.min(2147483647, (int) Math.pow(2, ((double) machine.getCoilType().getCoilTemperature() / 900))))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
        }
    };

    public static final BiConsumer<IMultiController, List<Component>> PROCESSING_PLANT_PARALLEL = (controller, components) -> {
        if(controller.isFormed() && controller instanceof WorkableElectricMultiblockMachine workableElectricMultiblockMachine) {
            components.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(4 * (workableElectricMultiblockMachine.getTier() - 1))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
        }
    };

    public static void init() {
        TootipsModify.init();
    }

    static {
        REGISTRATE.creativeModeTab(() -> GTCreativeModeTabs.MACHINE);
    }

    public static final FactoryBlockPattern DTPF = FactoryBlockPattern.start()
                .aisle(" ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd   d     d   ddd   ddd ", "         d   d     d   d         ", "         d   d     d   d         ", "                                 ", "                                 ", "                                 ", "         d   d     d   d         ", "         d   d     d   d         ", "         d   d     d   d         ", "                                 ")
                .aisle("dbbbd dbbbd    d d    dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "dbbbd dbbbd           dbbbd dbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbd dbbbd           dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   d     d   CCC   CCC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         d   d     d   d         ", "                                 ", "         d   d     d   d         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbd dbbbdddddsdsdddddbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "  s     s               s     s  ", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   d     d   CbC   CbC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "  d     sbbbbbddsddbbbbbs     d  ", "  d      bCCCb     bCCCb      d  ", "  d      d   d     d   d      d  ", "   s                         s   ", "   s     d   d     d   d     s   ", "    ss   bCCCb     bCCCb   ss    ", "      dddbbbbbddsddbbbbbddd      ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbdddbbbd    ded    dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "dbbbdddbbbd           dbbbdddbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbdddbbbd           dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         dCCCd     dCCCd         ", "  s      dCCCd     dCCCd      s  ", "  s      dCCCd     dCCCd      s  ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   dd    d     d    dd   ddd ", "         d   d     d   d         ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "  s      dCCCd     dCCCd      s  ", "   s     d   d     d   d     s   ", "         d   d     d   d         ", "                                 ")
                .aisle("   d   d       ded       d   d   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   d   d                 d   d   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "   d   d                 d   d   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   d   d                 d   d   ", "                                 ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "  s      dCCCd     dCCCd      s  ", "   s                         s   ", "                                 ", "                                 ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   dd    d     d    dd   ddd ", "         d   d     d   d         ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "         dCCCd     dCCCd         ", "  d      d   d     d   d      d  ", "         d   d     d   d         ", "                                 ")
                .aisle("dbbbdddbbbd    ded    dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "dbbbdddbbbd           dbbbdddbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbdddbbbd           dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         dCCCd     dCCCd         ", "         dCCCd     dCCCd         ", "         dCCCd     dCCCd         ", "         bCCCb     bCCCb         ", "  d      bCCCb     bCCCb      d  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbd dbbbdddddsdsdddddbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "  s     s               s     s  ", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   d     d   CbC   CbC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "  s     sbbbbbddsddbbbbbs     s  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ", "                                 ", "         d   d     d   d         ", "         bCCCb     bCCCb         ", "  d     sbbbbbddsddbbbbbs     d  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbd dbbbd    ded    dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "dbbbd dbbbd           dbbbd dbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbd dbbbd           dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   d     d   CCC   CCC ", "dddd   dddCCCb     bCCCddd   dddd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", " ddd   ddd   d     d   ddd   ddd ", "   d   d                 d   d   ", " ddd   ddd   d     d   ddd   ddd ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", " ddd   ddd   d     d   ddd   ddd ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " CCC   CCC   d     d   CCC   CCC ", " CbC   CbC   d     d   CbC   CbC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC   d     d   CCCCCCCCC ", " CbC   CbC   d     d   CbC   CbC ", " CCC   CCC   d     d   CCC   CCC ", "                                 ")
                .aisle("  d     d      ded      d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  d     d      ded      d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  d     d     dsdsd     d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", "dbbbd dbbbd           dbbbd dbbbd", "dbbbdddbbbd           dbbbdddbbbd", " ddd   ddd             ddd   ddd ", "   d   d                 d   d   ", " ddd   ddd             ddd   ddd ", "dbbbdddbbbd           dbbbdddbbbd", "dbbbd dbbbd           dbbbd dbbbd", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ")
                .aisle("  d     d    deeeeed    d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ")
                .aisle(" dsdddddsddddseedeesddddsdddddsd ", "                d                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ")
                .aisle("  deeeeedeeeededddedeeeedeeeeed  ", "               ddd               ", "                a                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  s     s               s     s  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  s     s               s     s  ", "                                 ", "                                 ")
                .aisle(" dsdddddsddddseedeesddddsdddddsd ", "                d                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ")
                .aisle("  d     d    deeeeed    d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  d     d               d     d  ", "                                 ", "                                 ")
                .aisle("  d     d     dsdsd     d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", "dbbbd dbbbd           dbbbd dbbbd", "dbbbdddbbbd           dbbbdddbbbd", " ddd   ddd             ddd   ddd ", "   d   d                 d   d   ", " ddd   ddd             ddd   ddd ", "dbbbdddbbbd           dbbbdddbbbd", "dbbbd dbbbd           dbbbd dbbbd", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ")
                .aisle("  d     d      ded      d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle("  d     d      ded      d     d  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " CCC   CCC             CCC   CCC ", " CbC   CbC             CbC   CbC ", " CCCCCCCCC             CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC             CCCCCCCCC ", " CbC   CbC             CbC   CbC ", " CCC   CCC             CCC   CCC ", "                                 ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " CCC   CCC   d     d   CCC   CCC ", " CbC   CbC   d     d   CbC   CbC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " CCCCCCCCC   d     d   CCCCCCCCC ", " CbC   CbC   d     d   CbC   CbC ", " CCC   CCC   d     d   CCC   CCC ", "                                 ")
                .aisle("dbbbd dbbbd    ded    dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "dbbbd dbbbd           dbbbd dbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbd dbbbd           dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   d     d   CCC   CCC ", "dddd   dddCCCb     bCCCddd   dddd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", " ddd   ddd   d     d   ddd   ddd ", "   d   d                 d   d   ", " ddd   ddd   d     d   ddd   ddd ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", " ddd   ddd   d     d   ddd   ddd ")
                .aisle("dbbbd dbbbdddddsdsdddddbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "  s     s               s     s  ", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   d     d   CbC   CbC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "  s     sbbbbbddsddbbbbbs     s  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ", "                                 ", "         d   d     d   d         ", "         bCCCb     bCCCb         ", "  d     sbbbbbddsddbbbbbs     d  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbdddbbbd    ded    dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "dbbbdddbbbd           dbbbdddbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbdddbbbd           dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         dCCCd     dCCCd         ", "         dCCCd     dCCCd         ", "         dCCCd     dCCCd         ", "         bCCCb     bCCCb         ", "  d      bCCCb     bCCCb      d  ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   dd    d     d    dd   ddd ", "         d   d     d   d         ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "         dCCCd     dCCCd         ", "  d      d   d     d   d      d  ", "         d   d     d   d         ", "                                 ")
                .aisle("   d   d       ded       d   d   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   d   d                 d   d   ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "   d   d                 d   d   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   d   d                 d   d   ", "                                 ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "  s      dCCCd     dCCCd      s  ", "   s                         s   ", "                                 ", "                                 ")
                .aisle(" ddd   ddd     ded     ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "   C   C                 C   C   ", "   C   C                 C   C   ", "   C   C                 C   C   ", " ddd   dd    d     d    dd   ddd ", "         d   d     d   d         ", "         dCCCd     dCCCd         ", "                                 ", "                                 ", "                                 ", "  s      dCCCd     dCCCd      s  ", "   s     d   d     d   d     s   ", "         d   d     d   d         ", "                                 ")
                .aisle("dbbbdddbbbd    ded    dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", "dbbbdddbbbd           dbbbdddbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbdddbbbd           dbbbdddbbbd", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC             CCCCCCCCC ", " CCCCCCCCC   d     d   CCCCCCCCC ", "dbbbdddbbdCCCb     bCCCdbbdddbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         dCCCd     dCCCd         ", "  s      dCCCd     dCCCd      s  ", "  s      dCCCd     dCCCd      s  ", "         bCCCb     bCCCb         ", "    ss   bCCCb     bCCCb   ss    ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbd dbbbdddddsdsdddddbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", "dbbbd dbbbd           dbbbd dbbbd", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "  s     s               s     s  ", " ddd   ddd             ddd   ddd ", " ddd   ddd             ddd   ddd ", "dbbbd dbbbd           dbbbd dbbbd", " CbC   CbC             CbC   CbC ", " CbC   CbC             CbC   CbC ", " CbC   CbC   d     d   CbC   CbC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "  d     sbbbbbddsddbbbbbs     d  ", "  d      bCCCb     bCCCb      d  ", "  d      d   d     d   d      d  ", "   s                         s   ", "   s     d   d     d   d     s   ", "    ss   bCCCb     bCCCb   ss    ", "      dddbbbbbddsddbbbbbddd      ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle("dbbbd dbbbd    d d    dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", "dbbbd dbbbd           dbbbd dbbbd", "  d     d               d     d  ", "  d     d               d     d  ", "                                 ", "  d     d               d     d  ", "  d     d               d     d  ", "dbbbd dbbbd           dbbbd dbbbd", " CCC   CCC             CCC   CCC ", " CCC   CCC             CCC   CCC ", " CCC   CCC   d     d   CCC   CCC ", "dbbbd dbbdCCCb     bCCCdbbd dbbbd", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         d   d     d   d         ", "                                 ", "         d   d     d   d         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         bCCCb     bCCCb         ", "         d   d     d   d         ")
                .aisle(" ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " ddd   ddd             ddd   ddd ", "                                 ", "                                 ", "                                 ", " ddd   ddd   d     d   ddd   ddd ", "         d   d     d   d         ", "         d   d     d   d         ", "                                 ", "                                 ", "                                 ", "         d   d     d   d         ", "         d   d     d   d         ", "         d   d     d   d         ", "                                 ");

    //////////////////////////////////////
    // *** Simple Machine ***//
    //////////////////////////////////////
    public static final MachineDefinition[] SEMI_FLUID_GENERATOR = GTMachines.registerSimpleGenerator("semi_fluid",
            GTLRecipeTypes.SEMI_FLUID_GENERATOR_FUELS, GTMachines.genericGeneratorTankSizeFunction, 0.1f, GTValues.LV, GTValues.MV,
            GTValues.HV);

    public static final MachineDefinition[] ROCKET_ENGINE_GENERATOR = GTMachines.registerSimpleGenerator("rocket_engine", GTRecipeTypes.get("rocket_engine"),
    GTMachines.genericGeneratorTankSizeFunction, 0.1f, GTValues.EV, GTValues.IV, GTValues.LuV);
    public static final MachineDefinition[] NAQUADAH_REACTOR_GENERATOR = GTMachines.registerSimpleGenerator("naquadah_reactor", GTRecipeTypes.get("naquadah_reactor"),
    GTMachines.genericGeneratorTankSizeFunction, 0.1f, GTValues.IV, GTValues.LuV, GTValues.ZPM);

    public static final MachineDefinition[] DEHYDRATOR = GTMachines.registerSimpleMachines("dehydrator",
            GTLRecipeTypes.DEHYDRATOR_RECIPES, GTMachines.defaultTankSizeFunction);

    public static final MachineDefinition[] WORLD_DATA_SCANNER = GTMachines.registerSimpleMachines("world_data_scanner",
            GTLRecipeTypes.WORLD_DATA_SCANNER_RECIPES, tier -> 64000);

    public static final MachineDefinition[] NEUTRON_COMPRESSOR = GTMachines.registerSimpleMachines("neutron_compressor",
            GTLRecipeTypes.NEUTRON_COMPRESSOR_RECIPES, GTMachines.defaultTankSizeFunction, false, GTValues.MAX);

    public static final MachineDefinition[] LIGHTNING_ROD = GTMachines.registerTieredMachines(
            "lightning_rod",
            LightningRodMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Lightning Rod %s".formatted(GTValues.VLVH[tier], GTValues.VLVT[tier]))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .renderer(() -> new SimpleGeneratorMachineRenderer(tier,
                            GTCEu.id("block/generators/lightning_rod")))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.0"))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.1"))
                    .tooltips(Component.translatable("gtceu.machine.lightning_rod.tooltip.2"))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.ampere_out", 512))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                            FormattingUtil.formatNumbers(GTValues.V[tier - 1]), GTValues.VNF[tier - 1]))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                            FormattingUtil.formatNumbers((long) (48828 * Math.pow(4, tier)))))
                    .tooltipBuilder(GTL_ADD)
                    .register(),
            GTValues.EV, GTValues.IV, GTValues.LuV);

    public static final MachineDefinition[] PRIMITIVE_MAGIC_ENERGY = GTMachines.registerTieredMachines(
            "primitive_magic_energy",
            MagicEnergyMachine::new,
            (tier, builder) -> builder
                    .langValue("%s Primitive Magic Energy %s".formatted(GTValues.VLVH[tier], GTValues.VLVT[tier]))
                    .rotationState(RotationState.NON_Y_AXIS)
                    .renderer(() -> new SimpleGeneratorMachineRenderer(tier,
                            GTCEu.id("block/generators/primitive_magic_energy")))
                    .tooltips(Component.translatable("gtceu.machine.primitive_magic_energy.tooltip.0"))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.ampere_out", 16))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.voltage_out",
                            FormattingUtil.formatNumbers(GTValues.V[tier]), GTValues.VNF[tier]))
                    .tooltips(Component.translatable("gtceu.universal.tooltip.energy_storage_capacity",
                            FormattingUtil.formatNumbers(GTValues.V[tier] * 512L)))
                    .tooltipBuilder(GTL_ADD)
                    .register(),
            GTValues.ULV, GTValues.LV);

    private static MachineDefinition[] registerHugeFluidHatches(String name, String displayname, String model,
                                                            String tooltip, IO io, PartAbility... abilities) {
        return GTMachines.registerTieredMachines(name,
                (holder, tier) -> new HugeFluidHatchPartMachine(holder, tier, io),
                (tier, builder) -> {
                    builder.langValue(GTValues.VNF[tier] + ' ' + displayname)
                            .rotationState(RotationState.ALL)
                            .overlayTieredHullRenderer(model)
                            .abilities(abilities)
                            .compassNode("fluid_hatch")
                            .tooltips(Component.translatable("gtceu.machine." + tooltip + ".tooltip"));
                    builder.tooltips(Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity_mult",
                            tier, FormattingUtil.formatNumbers(Integer.MAX_VALUE)))
                            .tooltipBuilder(GTL_ADD);
                    return builder.register();
                },
                GTValues.tiersBetween(GTValues.LV, GTValues.OpV));
    }

    //////////////////////////////////////
    // ********** Part **********//
    //////////////////////////////////////
    public static final MachineDefinition LARGE_STEAM_HATCH = REGISTRATE
            .machine("large_steam_input_hatch", holder -> new LargeSteamHatchPartMachine(holder, IO.IN, 8192, false))
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.STEAM)
            .overlaySteamHullRenderer("steam_hatch")
            .tooltips(Component.translatable("gtceu.machine.large_steam_input_hatch.tooltip.0"),
                    Component.translatable("gtceu.universal.tooltip.fluid_storage_capacity",
                            8192 * FluidHelper.getBucket()),
                    Component.translatable("gtceu.machine.steam.steam_hatch.tooltip"))
            .tooltipBuilder(GTL_ADD)
            .compassSections(GTCompassSections.STEAM)
            .compassNode("steam_hatch")
            .register();

    public static final MachineDefinition STERILE_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("sterile_cleaning_maintenance_hatch", GTLCleaningMaintenanceHatchPartMachine::SterileCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : GTLCleaningMaintenanceHatchPartMachine
                        .getCleanroomTypes(GTLCleaningMaintenanceHatchPartMachine.STERILE_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .tooltipBuilder(GTL_ADD)
            .renderer(() -> new MaintenanceHatchPartRenderer(7,
                    GTCEu.id("block/machine/part/maintenance.sterile_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition LAW_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("law_cleaning_maintenance_hatch", GTLCleaningMaintenanceHatchPartMachine::LawCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : GTLCleaningMaintenanceHatchPartMachine
                        .getCleanroomTypes(GTLCleaningMaintenanceHatchPartMachine.LAW_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .tooltipBuilder(GTL_ADD)
            .renderer(
                    () -> new MaintenanceHatchPartRenderer(10, GTCEu.id("block/machine/part/maintenance.law_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition AUTO_CONFIGURATION_MAINTENANCE_HATCH = REGISTRATE
            .machine("auto_configuration_maintenance_hatch", AutoConfigurationMaintenanceHatchPartMachine::new)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"))
            .tooltipBuilder(GTL_ADD)
            .renderer(() -> new MaintenanceHatchPartRenderer(5, GTCEu.id("block/machine/part/maintenance.full_auto")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition CLEANING_CONFIGURATION_MAINTENANCE_HATCH = REGISTRATE
            .machine("cleaning_configuration_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::Cleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .tooltipBuilder(GTL_ADD)
            .renderer(() -> new MaintenanceHatchPartRenderer(5, GTCEu.id("block/machine/part/maintenance.cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition STERILE_CONFIGURATION_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("sterile_configuration_cleaning_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::SterileCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.STERILE_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .tooltipBuilder(GTL_ADD)
            .renderer(() -> new MaintenanceHatchPartRenderer(9,
                    GTCEu.id("block/machine/part/maintenance.sterile_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition LAW_CONFIGURATION_CLEANING_MAINTENANCE_HATCH = REGISTRATE
            .machine("law_configuration_cleaning_maintenance_hatch",
                    CleaningConfigurationMaintenanceHatchPartMachine::LawCleaning)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.0"),
                    Component.translatable("gtceu.machine.maintenance_hatch_cleanroom_auto.tooltip.1"))
            .tooltipBuilder((stack, tooltips) -> {
                for (CleanroomType type : CleaningConfigurationMaintenanceHatchPartMachine
                        .getCleanroomTypes(CleaningConfigurationMaintenanceHatchPartMachine.LAW_DUMMY_CLEANROOM)) {
                    tooltips.add(Component.literal(String.format("  %s%s", ChatFormatting.GREEN,
                            Component.translatable(type.getTranslationKey()).getString())));
                }
            })
            .tooltipBuilder(GTL_ADD)
            .renderer(
                    () -> new MaintenanceHatchPartRenderer(12, GTCEu.id("block/machine/part/maintenance.law_cleaning")))
            .compassNodeSelf()
            .register();

    public static final MachineDefinition GRAVITY_HATCH = REGISTRATE
            .machine("gravity_hatch", GravityPartMachine::new)
            .rotationState(RotationState.ALL)
            .abilities(PartAbility.MAINTENANCE)
            .tooltips(Component.translatable("gtceu.universal.disabled"))
            .tooltipBuilder(GTL_ADD)
            .renderer(() -> new MaintenanceHatchPartRenderer(8, GTCEu.id("block/machine/part/maintenance.full_auto")))
            .compassNodeSelf()
            .register();

    public final static MachineDefinition[] HUGE_FLUID_IMPORT_HATCH = registerHugeFluidHatches("huge_input_hatch", "Huge Input Hatch", "fluid_hatch.import", "fluid_hatch.import", IO.IN, PartAbility.IMPORT_FLUIDS);

    public final static MachineDefinition[] HUGE_FLUID_EXPORT_HATCH = registerHugeFluidHatches("huge_output_hatch", "Huge Output Hatch", "fluid_hatch.export", "fluid_hatch.export", IO.OUT, PartAbility.EXPORT_FLUIDS);

    public static final MachineDefinition[] LASER_INPUT_HATCH_16384 = GTMachines.registerLaserHatch(IO.IN, 16384,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_16384 = GTMachines.registerLaserHatch(IO.OUT, 16384,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_65536 = GTMachines.registerLaserHatch(IO.IN, 65536,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_65536 = GTMachines.registerLaserHatch(IO.OUT, 65536,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_262144 = GTMachines.registerLaserHatch(IO.IN, 262144,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_262144 = GTMachines.registerLaserHatch(IO.OUT, 262144,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_1048576 = GTMachines.registerLaserHatch(IO.IN, 1048576,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_1048576 = GTMachines.registerLaserHatch(IO.OUT, 1048576,
            PartAbility.OUTPUT_LASER);
    public static final MachineDefinition[] LASER_INPUT_HATCH_4194304 = GTMachines.registerLaserHatch(IO.IN, 4194304,
            PartAbility.INPUT_LASER);
    public static final MachineDefinition[] LASER_OUTPUT_HATCH_4194304 = GTMachines.registerLaserHatch(IO.OUT, 4194304,
            PartAbility.OUTPUT_LASER);

    static {
        GTMTRegistration.GTMTHINGS_REGISTRATE.creativeModeTab(() -> CreativeModeTabs.WIRELESS_TAB);
    }

    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_64A = WirelessMachines.registerWirelessEnergyHatch(IO.IN,64, PartAbility.INPUT_ENERGY, GTValues.tiersBetween(GTValues.EV, GTValues.MAX));
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_64A = WirelessMachines.registerWirelessEnergyHatch(IO.OUT,64,PartAbility.OUTPUT_ENERGY, GTValues.tiersBetween(GTValues.EV, GTValues.MAX));

    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_262144A = WirelessMachines.registerWirelessLaserHatch(IO.IN,262144, PartAbility.INPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_1048576A = WirelessMachines.registerWirelessLaserHatch(IO.IN,1048576, PartAbility.INPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_INPUT_HATCH_4194304A = WirelessMachines.registerWirelessLaserHatch(IO.IN,4194304, PartAbility.INPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_262144A = WirelessMachines.registerWirelessLaserHatch(IO.OUT,262144, PartAbility.OUTPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_1048576A = WirelessMachines.registerWirelessLaserHatch(IO.OUT,1048576, PartAbility.OUTPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);
    public static final MachineDefinition[] WIRELESS_ENERGY_OUTPUT_HATCH_4194304A = WirelessMachines.registerWirelessLaserHatch(IO.OUT,4194304, PartAbility.OUTPUT_LASER, WirelessMachines.WIRELL_ENERGY_HIGH_TIERS);

    //////////////////////////////////////
    // ******* Multiblock *******//
    //////////////////////////////////////
    public final static MultiblockMachineDefinition ELECTRIC_IMPLOSION_COMPRESSOR = REGISTRATE
            .multiblock("electric_implosion_compressor", WorkableElectricMultiblockMachine::new)
            .langValue("Electric Implosion Compressor")
            .tooltips(Component.translatable("gtceu.machine.eut_multiplier.tooltip", 0.8))
            .tooltips(Component.translatable("gtceu.machine.duration_multiplier.tooltip", 0.6))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.electric_implosion_compressor")))
            .tooltipBuilder(GTL_ADD)
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH,
                    GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXXXX", "F###F", "F###F", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                    .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                    .aisle("XXXXX", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "#GAG#", "XXMXX")
                    .aisle("XXXXX", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "#PGP#", "XXXXX")
                    .aisle("XXSXX", "F###F", "F###F", "F###F", "F###F", "F###F", "F###F", "XXXXX")
                    .where('S', controller(blocks(definition.get())))
                    .where('X',
                            blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(40)
                                    .or(autoAbilities(definition.getRecipeTypes()))
                                    .or(Predicates.autoAbilities(true, false, true)))
                    .where('P', blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where('G', blocks(GTBlocks.FUSION_GLASS.get()))
                    .where('F', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.TungstenSteel)))
                    .where('A', air())
                    .where('#', any())
                    .where('M', blocks(GTMachines.MUFFLER_HATCH[GTValues.LuV].get()))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
            .compassSections(GTCompassSections.TIER[GTValues.IV])
            .compassNodeSelf()
            .register();

    public static final MachineDefinition[] NEUTRON_ACCELERATOR = GTMachines.registerTieredMachines("neutron_accelerator",
            NeutronAcceleratorPartMachine::new,
            (tier, builder) -> builder
                    .langValue(GTValues.VNF[tier] + "Neutron Accelerator")
                    .rotationState(RotationState.ALL)
                    .abilities(GTLPartAbility.NEUTRON_ACCELERATOR)
                    .tooltips(Component.translatable("gtceu.universal.tooltip.max_voltage_in", GTValues.V[tier], GTValues.VNF[tier]),
                            Component.translatable("gtceu.machine.neutron_accelerator.tooltip.0", GTValues.V[tier] * 8 / 10),
                            Component.translatable("gtceu.machine.neutron_accelerator.tooltip.1"),
                            Component.translatable("gtceu.universal.tooltip.energy_storage_capacity", 2 * GTValues.V[tier]))
                    .tooltipBuilder(GTL_ADD)
                    .overlayTieredHullRenderer("neutron_accelerator")
                    .compassNode("neutron_accelerator")
                    .register(),
            GTMachines.ALL_TIERS);

    public final static MachineDefinition NEUTRON_SENSOR = REGISTRATE
            .machine("neutron_sensor", NeutronSensorPartMachine::new)
            .langValue("Neutron Sensor")
            .tier(GTValues.IV)
            .rotationState(RotationState.ALL)
            .tooltips(Component.translatable("gtceu.machine.neutron_sensor.tooltip.0"))
            .tooltipBuilder(GTL_ADD)
            .overlayTieredHullRenderer("neutron_sensor")
            .register();

    public static final MultiblockMachineDefinition NEUTRON_ACTIVATOR = REGISTRATE
            .multiblock("neutron_activator", NeutronActivatorMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.neutron_activator.tooltip.4"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.neutron_activator")))
            .tooltipBuilder(GTL_ADD)
            .recipeTypes(GTLRecipeTypes.NEUTRON_ACTIVATOR_RECIPES)
            .recipeModifiers(((machine, recipe, params, result) -> NeutronActivatorMachine.recipeModifier(machine, recipe)))
            .appearanceBlock(GTBlocks.CASING_STAINLESS_CLEAN)
            .pattern(definition -> FactoryBlockPattern.start(RIGHT, BACK, UP)
                    .aisle("AAGAA", "ADDDA", "ADDDA", "ADDDA", "AAAAA")
                    .aisle("B   B", " EEE ", " EFE ", " EEE ", "B   B").setRepeatable(4, 200)
                    .aisle("CCCCC", "CDDDC", "CDDDC", "CDDDC", "CCCCC")
                    .where('G', controller(blocks(definition.getBlock())))
                    .where('A', blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(blocks(NEUTRON_SENSOR.get()).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(2))
                            .or(abilities(GTLPartAbility.NEUTRON_ACCELERATOR).setMaxGlobalLimited(2))
                            .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where('B', frames(GTMaterials.Tungsten))
                    .where('C', blocks(GTBlocks.CASING_STAINLESS_CLEAN.get())
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(2)))
                    .where('D', blocks(Registries.getBlock("kubejs:process_machine_casing")))
                    .where('E', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where('F', GTLPredicates.countBlock("SpeedPipe",
                           Registries.getBlock("kubejs:speeding_pipe")))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(
                    GTCEu.id("block/casings/solid/machine_casing_clean_stainless_steel"),
                    GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition HEAT_EXCHANGER = REGISTRATE
            .multiblock("heat_exchanger", HeatExchangerMachine::new)
            .langValue("Heat Exchanger")
            .tooltips(Component.translatable("gtceu.machine.heat_exchanger.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.heat_exchanger.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.heat_exchanger")))
            .tooltipBuilder(GTL_ADD)
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.HEAT_EXCHANGER_RECIPES)
            .recipeModifiers((machine, recipe, params, result) -> HeatExchangerMachine.recipeModifier(machine, recipe))
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle(" AAA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle("AAAAA", "BDDDB", "BDCDB", "BDCDB", "BDDDB", "AAAAA")
                    .aisle("AAAAA", "BCCCB", "BCCCB", "BCCCB", "BCCCB", "AAAAA")
                    .aisle(" ASA ", " AAA ", " AAA ", " AAA ", " AAA ", " AAA ")
                    .where('S', controller(blocks(definition.get())))
                    .where('A',
                            blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()).setMinGlobalLimited(98)
                                    .or(autoAbilities(definition.getRecipeTypes()))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where('C', blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                    .where('B', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where('D', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.HSSG)))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/implosion_compressor"))
            .register();

    public final static MultiblockMachineDefinition PRIMITIVE_VOID_ORE = ConfigHolder.INSTANCE.enablePrimitiveVoidOre ?
            REGISTRATE.multiblock("primitive_void_ore", PrimitiveOreMachine::new)
                    .langValue("Primitive Void Ore")
                    .tooltips(Component.literal("运行时根据维度每tick随机产出一组任意粗矿"))
                    .tooltips(Component.literal("支持主世界,下界,末地"))
                    .tooltipBuilder(GTL_ADD)
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

    public static final MultiblockMachineDefinition[] FLUID_DRILLING_RIG = GTMachines.registerTieredMultis(
            "fluid_drilling_rig", INFFluidDrillMachine::new, (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("%s Fluid Drilling Rig %s".formatted(GTValues.VLVH[tier], GTValues.VLVT[tier]))
                    .recipeType(GTRecipeTypes.DUMMY_RECIPES)
                    .tooltips(
                            Component.translatable("gtceu.machine.fluid_drilling_rig.description"),
                            Component.translatable("gtceu.machine.fluid_drilling_rig.depletion", 0),
                            Component.translatable("gtceu.universal.tooltip.energy_tier_range", GTValues.VNF[tier],
                                    GTValues.VNF[tier + 1]),
                            Component.translatable("gtceu.machine.fluid_drilling_rig.production",
                                    INFFluidDrillMachine.getRigMultiplier(tier),
                                    FormattingUtil.formatNumbers(INFFluidDrillMachine.getRigMultiplier(tier) * 1.5)))
                    .tooltipBuilder(GTL_ADD)
                    .appearanceBlock(() -> INFFluidDrillMachine.getCasingState(tier))
                    .pattern((definition) -> FactoryBlockPattern.start()
                            .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                            .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                            .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                            .where('S', controller(blocks(definition.get())))
                            .where('X', blocks(INFFluidDrillMachine.getCasingState(tier)).setMinGlobalLimited(3)
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1)
                                            .setMaxGlobalLimited(2))
                                    .or(abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1)))
                            .where('C', blocks(INFFluidDrillMachine.getCasingState(tier)))
                            .where('F', blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Ruridit).get()))
                            .where('#', any())
                            .build())
                    .workableCasingRenderer(new ResourceLocation("kubejs:block/iridium_casing"),
                            GTCEu.id("block/multiblock/fluid_drilling_rig"))
                    .register(),
            GTValues.ZPM);

    public static final MultiblockMachineDefinition LARGE_SEMI_FLUID_GENERATOR = GTMachines.registerLargeCombustionEngine(
            "large_semi_fluid_generator", GTValues.EV,
            GTBlocks.CASING_TITANIUM_STABLE, GTBlocks.CASING_STEEL_GEARBOX, GTBlocks.CASING_ENGINE_INTAKE,
            GTCEu.id("block/casings/solid/machine_casing_stable_titanium"),
            GTCEu.id("block/multiblock/generator/large_combustion_engine"));

    public final static MultiblockMachineDefinition CHEMICAL_ENERGY_DEVOURER = REGISTRATE
            .multiblock("chemical_energy_devourer", ChemicalEnergyDevourerMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(GTRecipeTypes.COMBUSTION_GENERATOR_FUELS, GTLRecipeTypes.SEMI_FLUID_GENERATOR_FUELS,
                    GTRecipeTypes.GAS_TURBINE_FUELS)
            .generator(true)
            .tooltips(Component.translatable(
                            "gtceu.universal.tooltip.base_production_eut", 2 * GTValues.V[GTValues.ZPM]),
                    Component.translatable(
                            "gtceu.universal.tooltip.uses_per_hour_lubricant", 2000),
                    Component.literal(
                            "提供§f120mB/s§7的液态氧，并消耗§f双倍§7燃料以产生高达§f" + (2 * GTValues.V[GTValues.UV]) + "§7EU/t的功率。"),
                    Component.literal(
                            "再额外提供§f80mB/s§7的四氧化二氮，并消耗§f四倍§7燃料以产生高达§f" + (2 * GTValues.V[GTValues.UHV]) + "§7EU/t的功率。"))
            .tooltipBuilder(GTL_ADD)
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(ChemicalEnergyDevourerMachine::recipeModifier, true)
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("BBBBBBB", "BBBBBBB", "BBPBPBB", "BBBBBBB", "BBPBPBB", "BBBBBBB", "BBBBBBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "BGHEHGB", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "CGHEHGC", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "FDDDDDF", "BEHHHEB", "CGHEHGC", "BEHHHEB", "FDDDDDF", "BBIBIBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "CGHEHGC", "BEHHHEB", "BDDDDDB", "BBIBIBB")
                    .aisle("BBBBBBB", "BDDDDDB", "BEHHHEB", "BGHEHGB", "BEHHHEB", "BDDDDDB", "BBBBBBB")
                    .aisle("AAAAAAA", "AAAAAAA", "AABBBAA", "AABSBAA", "AABBBAA", "AAAAAAA", "AAAAAAA")
                    .where("S", controller(blocks(definition.get())))
                    .where("A", blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                    .where("B", blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
                    .where("F", blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                            .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4)))
                    .where("C", blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                    .where("G", blocks(GCyMBlocks.ELECTROLYTIC_CELL.get()))
                    .where("D", blocks(GTBlocks.FIREBOX_TITANIUM.get()))
                    .where("E", blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                    .where("H", blocks(GTBlocks.CASING_TITANIUM_GEARBOX.get()))
                    .where("P", abilities(PartAbility.OUTPUT_ENERGY))
                    .where("I", abilities(PartAbility.MUFFLER))
                    .build())
            .recoveryItems(
                    () -> new ItemLike[]{GTItems.MATERIAL_ITEMS.get(TagPrefix.dustTiny, GTMaterials.Ash).get()})
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                    GTCEu.id("block/multiblock/generator/extreme_combustion_engine"), false)
            .register();

    public final static MultiblockMachineDefinition ADVANCED_ASSEMBLY_LINE = REGISTRATE
            .multiblock("advanced_assembly_line", AdvancedAssemblyLineMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.ASSEMBLY_LINE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.advanced_assembly_line.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.advanced_assembly_line.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.assembly_line.tooltip.0"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.assembly_line")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(AdvancedAssemblyLineMachine::recipeModifier)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .pattern(definition ->
                    FactoryBlockPattern.start(BACK, UP, RIGHT)
                            .aisle("FIF", "RTR", "SAG", "#Y#")
                            .aisle("FIF", "RTR", "DAG", "#Y#").setRepeatable(7, 63)
                            .aisle("FOF", "RTR", "DAG", "#Y#")
                            .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("F", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(4))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .where("O", Predicates.abilities(PartAbility.EXPORT_ITEMS).addTooltips(Component.translatable("gtceu.multiblock.pattern.location_end")))
                            .where("Y", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2)))
                            .where("I", Predicates.blocks(GTMachines.ITEM_IMPORT_BUS[0].get()).or(Predicates.blocks(CustomMachines.HUGE_ITEM_IMPORT_BUS[0].get())))
                            .where("G", Predicates.blocks(GTBlocks.CASING_GRATE.get()))
                            .where("D", Predicates.blocks(GTBlocks.CASING_GRATE.get())
                                    .or(Predicates.abilities(PartAbility.OPTICAL_DATA_RECEPTION).setExactLimit(1)))
                            .where("A", Predicates.blocks(GTBlocks.CASING_ASSEMBLY_CONTROL.get()))
                            .where("R", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where("T", GTLPredicates.countBlock("Unit", GTLBlocks.ADVANCED_ASSEMBLY_LINE_UNIT.get()))
                            .where("#", Predicates.any())
                            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public static MultiblockMachineDefinition registerMegaTurbine(String name, int tier, int value, GTRecipeType recipeType,
                                                                  Supplier<Block> casing, Supplier<Block> gear, ResourceLocation baseCasing,
                                                                  ResourceLocation overlayModel) {
        return REGISTRATE.multiblock(name, holder -> new MegaTurbineMachine(holder, tier, value))
                .rotationState(RotationState.ALL)
                .recipeType(recipeType)
                .generator(true)
                .tooltips(Component.literal("可使用变电动力仓"))
                .tooltips(Component.translatable("gtceu.universal.tooltip.base_production_eut", FormattingUtil.formatNumbers(GTValues.V[tier] * value)))
                .tooltips(Component.translatable("gtceu.multiblock.turbine.efficiency_tooltip", GTValues.VNF[tier]))
                .tooltipBuilder(GTL_ADD)
                .recipeModifier(MegaTurbineMachine::recipeModifier)
                .appearanceBlock(casing)
                .pattern(definition -> FactoryBlockPattern.start()
                        .aisle("AAAAAAA", "AAAAAAA", "AAEAEAA", "AAAAAAA", "AAEAEAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAMAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .aisle("AAAAAAA", "BDDDDDB", "AAAAAAA", "AAAAAAA", "AAAAAAA", "BDDDDDB", "AAAAAAA")
                        .aisle("AAAAAAA", "AAAAAAA", "AAAAAAA", "AAASAAA", "AAAAAAA", "AAAAAAA", "AAAAAAA")
                        .where("S", Predicates.controller(Predicates.blocks(definition.get())))
                        .where("A", Predicates.blocks(casing.get())
                                .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8))
                                .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(2)))
                        .where("B", GTLPredicates.RotorBlock())
                        .where("D", Predicates.blocks(gear.get()))
                        .where("E", Predicates.abilities(PartAbility.OUTPUT_ENERGY).or(Predicates.abilities(PartAbility.SUBSTATION_OUTPUT_ENERGY)))
                        .where("M", Predicates.abilities(PartAbility.MUFFLER))
                        .build())
                .workableCasingRenderer(baseCasing, overlayModel)
                .register();
    }

    public final static MultiblockMachineDefinition ROCKET_LARGE_TURBINE = GTMachines.registerLargeTurbine("rocket_large_turbine", GTValues.EV,
            GTLRecipeTypes.ROCKET_ENGINE_FUELS,
            GTBlocks.CASING_TITANIUM_TURBINE, GTBlocks.CASING_TITANIUM_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"),
            GTCEu.id("block/multiblock/generator/large_gas_turbine"));

    public final static MultiblockMachineDefinition SUPERCRITICAL_STEAM_TURBINE = GTMachines.registerLargeTurbine("supercritical_steam_turbine", GTValues.IV,
            GTLRecipeTypes.SUPERCRITICAL_STEAM_TURBINE_FUELS,
            GTLBlocks.CASING_SUPERCRITICAL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            GTLCore.id("block/supercritical_turbine_casing"),
            GTCEu.id("block/multiblock/generator/large_plasma_turbine"));

    public final static MultiblockMachineDefinition STEAM_MEGA_TURBINE = registerMegaTurbine("steam_mega_turbine", GTValues.EV, 32, GTRecipeTypes.STEAM_TURBINE_FUELS, GTBlocks.CASING_STEEL_TURBINE, GTBlocks.CASING_STEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_steel"), GTCEu.id("block/multiblock/generator/large_steam_turbine"));
    public final static MultiblockMachineDefinition GAS_MEGA_TURBINE = registerMegaTurbine("gas_mega_turbine", GTValues.IV, 32, GTRecipeTypes.GAS_TURBINE_FUELS, GTBlocks.CASING_STAINLESS_TURBINE, GTBlocks.CASING_STAINLESS_STEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_stainless_steel"), GTCEu.id("block/multiblock/generator/large_gas_turbine"));
    public final static MultiblockMachineDefinition ROCKET_MEGA_TURBINE = registerMegaTurbine("rocket_mega_turbine", GTValues.IV, 48, GTLRecipeTypes.ROCKET_ENGINE_FUELS, GTBlocks.CASING_TITANIUM_TURBINE, GTBlocks.CASING_STAINLESS_STEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_titanium"), GTCEu.id("block/multiblock/generator/large_gas_turbine"));
    public final static MultiblockMachineDefinition PLASMA_MEGA_TURBINE = registerMegaTurbine("plasma_mega_turbine", GTValues.LuV, 64, GTRecipeTypes.PLASMA_GENERATOR_FUELS, GTBlocks.CASING_TUNGSTENSTEEL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            GTCEu.id("block/casings/mechanic/machine_casing_turbine_tungstensteel"), GTCEu.id("block/multiblock/generator/large_plasma_turbine"));
    public final static MultiblockMachineDefinition SUPERCRITICAL_MEGA_STEAM_TURBINE = registerMegaTurbine("supercritical_mega_steam_turbine", GTValues.LuV, 128, GTLRecipeTypes.SUPERCRITICAL_STEAM_TURBINE_FUELS, GTLBlocks.CASING_SUPERCRITICAL_TURBINE, GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX,
            GTLCore.id("block/supercritical_turbine_casing"), GTCEu.id("block/multiblock/generator/large_plasma_turbine"));

    public final static MultiblockMachineDefinition FISSION_REACTOR = REGISTRATE.multiblock("fission_reactor", FissionReactorMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(GTLRecipeTypes.FISSION_REACTOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.4"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.5"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.6"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.7"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.8"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.9"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.10"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.11"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.12"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.13"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.14"))
            .tooltips(Component.translatable("gtceu.machine.fission_reactor.tooltip.15"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.fission_reactor")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier((machine, recipe, params, result) -> FissionReactorMachine.recipeModifier(machine, recipe))
            .appearanceBlock(() -> Registries.getBlock("kubejs:fission_reactor_casing"))
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("AAAAAAAAA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAAAAAAA", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "BCCCCCCCB", "AAAAAAAAA")
                            .aisle("AAAA~AAAA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "ABBBBBBBA", "AAAAAAAAA")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("A", Predicates.blocks(Registries.getBlock("kubejs:fission_reactor_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1)))
                            .where("B", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()).or(Predicates.blocks(Registries.getBlock("kubejs:fission_reactor_casing"))))
                            .where("C", Predicates.air().or(GTLPredicates.countBlock("FuelAssembly", GTLBlocks.FISSION_FUEL_ASSEMBLY.get()))
                                    .or(GTLPredicates.countBlock("Cooler", GTLBlocks.COOLER.get())))
                            .build())
            .workableCasingRenderer(new ResourceLocation("kubejs:block/fission_reactor_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public final static MultiblockMachineDefinition STELLAR_FORGE = REGISTRATE.multiblock("stellar_forge", (holder) -> new TierCasingMachine(holder, "SCTier"))
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(GTLRecipeTypes.STELLAR_FORGE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.perfect_oc"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.stellar_forge")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(GCyMBlocks.CASING_ATOMIC)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("               ", "      bbb      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      bbb      ", "               ")
                    .aisle("      b b      ", "     ccccc     ", "               ", "               ", "               ", "               ", "               ", "     ccccc     ", "      b b      ")
                    .aisle("      b b      ", "   cc     cc   ", "               ", "               ", "               ", "               ", "               ", "   cc     cc   ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "     ccccc     ", "               ", "               ", "               ", "     ccccc     ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "    c ddd c    ", "      ddd      ", "      ddd      ", "      ddd      ", "    c ddd c    ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", " c    ddd    c ", "   c d   d c   ", "     d   d     ", "     d   d     ", "     d   d     ", "   c d   d c   ", " c    ddd    c ", "      b b      ")
                    .aisle(" bbbbbbbbbbbbb ", "bc   ddddd   cb", "b  cd     dc  b", "b   d     d   b", "b   d     d   b", "b   d     d   b", "b  cd     dc  b", "bc   ddddd   cb", " bbbbbbbbbbbbb ")
                    .aisle("      bbb      ", "bc   ddddd   cb", "   cd     dc   ", "    d     d    ", "    d     d    ", "    d     d    ", "   cd     dc   ", "bc   ddddd   cb", "      bbb      ")
                    .aisle(" bbbbbbbbbbbbb ", "bc   ddddd   cb", "b  cd     dc  b", "b   d     d   b", "b   d     d   b", "b   d     d   b", "b  cd     dc  b", "bc   ddddd   cb", " bbbbbbbbbbbbb ")
                    .aisle("      b b      ", " c    ddd    c ", "   c d   d c   ", "     d   d     ", "     d   d     ", "     d   d     ", "   c d   d c   ", " c    ddd    c ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "    c ddd c    ", "      ddd      ", "      ddd      ", "      ddd      ", "    c ddd c    ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "  c         c  ", "     ccccc     ", "               ", "               ", "               ", "     ccccc     ", "  c         c  ", "      b b      ")
                    .aisle("      b b      ", "   cc     cc   ", "               ", "               ", "               ", "               ", "               ", "   cc     cc   ", "      b b      ")
                    .aisle("      b b      ", "     ccccc     ", "               ", "               ", "               ", "               ", "               ", "     ccccc     ", "      b b      ")
                    .aisle("               ", "      bab      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      b b      ", "      bbb      ", "               ")
                    .where("a", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("b", Predicates.blocks(GCyMBlocks.CASING_ATOMIC.get())
                            .setMinGlobalLimited(150)
                            .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("c", Predicates.blocks(GTBlocks.FUSION_COIL.get()))
                    .where("d", GTLPredicates.tierCasings(GTLBlocks.scmap, "SCTier"))
                    .where(" ", Predicates.any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/atomic_casing"), GTCEu.id("block/multiblock/electric_blast_furnace"))
            .register();

    public final static MultiblockMachineDefinition SPACE_ELEVATOR = REGISTRATE.multiblock("space_elevator", SpaceElevatorMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeType(GTLRecipeTypes.SPACE_ELEVATOR_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.space_elevator.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.space_elevator")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(new OverclockingLogic(1, 4, false)))
            .appearanceBlock(() -> Registries.getBlock("kubejs:space_elevator_mechanical_casing"))
            .pattern(definition ->
                    FactoryBlockPattern.start(RIGHT, DOWN, FRONT)
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                XXX                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                            .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("              FD - DF              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "              EC - CE              ", "             F C - C F             ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              FC - CF              ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "               C - C               ", "              XC - CX              ", "              XC - CX              ", "              XC - CX              ", "           FFFFDDMDDFFFF           ", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("              F     F              ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             F       F             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "           EE         EE           ", "           EE         EE           ", "           E           E           ", "           E           E           ", "           E  F     F  E           ", "           E           E           ", "           E           E           ", "           E           E           ", "          EE           EE          ", "          EE           EE          ", "          E             E          ", "          E             E          ", "         EE             EE         ", "         EE             EE         ", "         E    FD   DF    E         ", "        EE     D   D     EE        ", "        EE     D   D     EE        ", "        E      D   D      E        ", "       EE      D   D      EE       ", "       EE      D   D      EE       ", "       E                   E       ", "      EE                   EE      ", "      EE                   EE      ", "     EE                     EE     ", "    EEE       XD   DX       EEE    ", "   EEE        XD   DX        EEE   ", "  EE          XD   DX          EE  ", "FFF        FFFFDDDDDFFFF        FFF", "AAAAAAAAAAAAAAAXXXXXAAAAAAAAAAAAAAA")
                            .aisle("               F D F               ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "                 C                 ", "              F  C  F              ", "                 C                 ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "            D    C    D            ", "           DD    C    DD           ", "           D     C     D           ", "           D     C     D           ", "           D   F C F   D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "           D     C     D           ", "          DD     C     DD          ", "          D      C      D          ", "          D      C      D          ", "          D      C      D          ", "         DD      C      DD         ", "         D     FDCDF     D         ", "         D      DCD      D         ", "        DD      DCD      DD        ", "        D       DCD       D        ", "        D       DCD       D        ", "       DD      DDCDD      DD       ", "       D       D C D       D       ", "       D       D C D       D       ", "      DD       D C D       DD      ", "      D        D C D        D      ", "     DD        XDCDX        DD     ", "    DD         XDCDX         DD    ", " DDDD          XDCDX          DDDD ", "FFFD        FFFDDDDDFFF        DFFF", "AAAAAAAAAGGGAAAXXXXXAAAGGGAAAAAAAAA")
                            .aisle("                FFF                ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "                 E                 ", "               F   F               ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "            H         H            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "       H                   H       ", "                                   ", "                                   ", "                                   ", "                                   ", "                XXX                ", "                X~X                ", "  D             XXX             D  ", " FFF       VFFFFFFFFFFFV       FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                FFF                ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "            HE       EH            ", "             E       E             ", "             E       E             ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF         FFFFFFFFF         FFF ", " AAAAAAAAGGGAAAAAAAAAAAGGGAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "             HH     HH             ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "            E         E            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "        H                 H        ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "  D                             D  ", " FFF          FFFFFFF          FFF ", " AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "           E           E           ", "                                   ", "                                   ", "         H               H         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF         V FFF V         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "          E             E          ", "         HE             EH         ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   D                           D   ", "  FFF                         FFF  ", "  AAAAAAAAAAAGGGAAAGGGAAAAAAAAAAA  ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "         E               E         ", "         EHH           HHE         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "         E               E         ", "   FF    E               E    FF   ", "   AAAAAAAAAAGGGAAAGGGAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "            HH DE ED HH            ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "   AAAAAAAAAAAAAAAAAAAAAAAAAAAAA   ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "              HDE EDH              ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "    AAAAAAAAAAAAAAAAAAAAAAAAAAA    ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "                                   ", "     AAAAAAAAAAAAAAAAAAAAAAAAA     ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               DE ED               ", "                                   ", "                                   ", "      AAAAAAAAAAAAAAAAAAAAAAA      ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "               DE ED               ", "               D   D               ", "         FFF           FFF         ", "       AAAAAAAAAAAAAAAAAAAAA       ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                E E                ", "          DD   DE ED   DD          ", "         FFFFFFD   DFFFFFF         ", "        AAAAAAAAAAAAAAAAAAA        ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "            DDDDE EDDDD            ", "          FFFFFFF FFFFFFF          ", "          AAAAAAAAAAAAAAA          ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               D   D               ", "            FFFFF FFFFF            ", "            AAAAAAAAAAA            ")
                            .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "               FF FF               ", "               AAAAA               ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("X", Predicates.blocks(Registries.getBlock("kubejs:space_elevator_mechanical_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where("E", Predicates.blocks(GTLBlocks.SPACE_ELEVATOR_SUPPORT.get()))
                            .where("H", Predicates.blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Neutronium).get()))
                            .where("F", Predicates.blocks(Registries.getBlock("kubejs:space_elevator_internal_support")))
                            .where("C", GTLPredicates.tierActiveCasings(GTLBlocks.sepmmap, "SEPMTier"))
                            .where("A", Predicates.blocks(Registries.getBlock("kubejs:high_strength_concrete")))
                            .where("D", Predicates.blocks(Registries.getBlock("kubejs:space_elevator_mechanical_casing")))
                            .where("M", Predicates.blocks(Registries.getBlock("gtlcore:power_core")))
                            .where("G", Predicates.blocks(Registries.getBlock("kubejs:module_base")))
                            .where("V", Predicates.any().or(Predicates.blocks(Registries.getBlock("kubejs:module_connector")).setPreviewCount(1)))
                            .where("-", Predicates.air())
                            .where(" ", Predicates.any())
                            .build())
            .renderer(SpaceElevatorRenderer::new)
            .hasTESR(true)
            .register();

    public final static MultiblockMachineDefinition COMPONENT_ASSEMBLY_LINE = REGISTRATE.multiblock("component_assembly_line", (holder) -> new TierCasingMachine(holder, "CATier"))
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.COMPONENT_ASSEMBLY_LINE_RECIPES)
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.component_assembly_line")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .appearanceBlock(() -> Registries.getBlock("kubejs:iridium_casing"))
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("AAAAAAAAA", "A  NNN  A", "A       A", "A       A", "A       A", "A       A", "AA     AA", " AAAAAAA ", "         ", "         ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GHAAAHG ", "         ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F  M M  F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "AL     LA", "AL AAA LA", "AL     LA", "AL     LA", "AL  J  LA", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "FL     LF", "FL AAA LF", "FL     LF", "FL     LF", "FL  J  LF", "ALL I LLA", "G LLILL G", " GA   AG ", "   KKK   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F I   I F", "FJ     JF", "FJ     JF", "AJ     JA", "G       G", " GA   AG ", "   AKA   ")
                            .aisle("AAAAAAAAA", "F       F", "F  AAA  F", "F       F", "F       F", "F       F", "A       A", "G       G", " GHAAAHG ", "         ")
                            .aisle("AAAAAAAAA", "A  B B  A", "A  CCC  A", "A  CCC  A", "A       A", "A       A", "AA DDD AA", " AAD~DAA ", "   DDD   ", "         ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("A", Predicates.blocks(Registries.getBlock("kubejs:iridium_casing")))
                            .where("B", Predicates.blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where("C", Predicates.blocks(Registries.getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where("D", Predicates.blocks(Registries.getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where("F", Predicates.blocks(Registries.getBlock("kubejs:hsss_reinforced_borosilicate_glass")))
                            .where("G", Predicates.blocks(GTBlocks.FILTER_CASING.get()))
                            .where("H", Predicates.blocks(Registries.getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1)))
                            .where("I", Predicates.blocks(Registries.getBlock("gtceu:hastelloy_n_frame")))
                            .where("J", Predicates.blocks(GTLBlocks.ADVANCED_ASSEMBLY_LINE_UNIT.get()))
                            .where("K", GTLPredicates.tierCasings(GTLBlocks.calmap, "CATier"))
                            .where("L", Predicates.blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                            .where("M", Predicates.blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.TungstenSteel).get()))
                            .where("N", Predicates.blocks(Registries.getBlock("kubejs:iridium_casing"))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS)))
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(new ResourceLocation("kubejs:block/iridium_casing"), GTCEu.id("block/multiblock/assembly_line"))
            .register();

    public final static MultiblockMachineDefinition SLAUGHTERHOUSE = REGISTRATE.multiblock("slaughterhouse", SlaughterhouseMachine::new)
            .rotationState(RotationState.NON_Y_AXIS)
            .allowExtendedFacing(false)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(new OverclockingLogic(1, 4, false)))
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .recipeType(GTLRecipeTypes.SLAUGHTERHOUSE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.4"))
            .tooltips(Component.translatable("gtceu.machine.slaughterhouse.tooltip.5"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.slaughterhouse")))
            .tooltipBuilder(GTL_ADD)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("AAAAAAA", "AAAAAAA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BD   DB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "ACCCCCA", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BDDDDDB", "BEEEEEB", "AAAAAAA")
                    .aisle("AAAAAAA", "AAA~AAA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "ABBBBBA", "AAAAAAA")
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("A", Predicates.blocks(GTBlocks.CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                            .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(4))
                            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                    .where("B", Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where("C", Predicates.blocks(GTBlocks.CASING_STEEL_GEARBOX.get()))
                    .where("D", Predicates.blocks(Blocks.IRON_BARS))
                    .where("E", Predicates.blocks(GTBlocks.FIREBOX_STEEL.get()))
                    .where(" ", Predicates.air())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/gcym/large_cutter"))
            .register();

    public static final MultiblockMachineDefinition[] FUSION_REACTOR = GTMachines.registerTieredMultis("fusion_reactor",
            FusionReactorMachine::new, (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("Fusion Reactor Computer MK %s".formatted(FormattingUtil.toRomanNumeral(tier - 5)))
                    .recipeType(GTRecipeTypes.FUSION_RECIPES)
                    .recipeModifiers(GTRecipeModifiers.DEFAULT_ENVIRONMENT_REQUIREMENT,
                            FusionReactorMachine::recipeModifier)
                    .tooltips(
                            Component.translatable("gtceu.machine.fusion_reactor.capacity",
                                    FusionReactorMachine.calculateEnergyStorageFactor(tier, 16) / 1000000L),
                            Component.translatable("gtceu.machine.fusion_reactor.overclocking"),
                            Component.translatable("gtceu.multiblock.%s_fusion_reactor.description"
                                    .formatted(GTValues.VN[tier].toLowerCase(Locale.ROOT))))
                    .tooltipBuilder(GTL_ADD)
                    .appearanceBlock(() -> GTLFusionCasingBlock.getCasingState(tier))
                    .pattern((definition) -> {
                        var casing = blocks(GTLFusionCasingBlock.getCasingState(tier));
                        return FactoryBlockPattern.start()
                                .aisle("###############", "######OGO######", "###############")
                                .aisle("######ICI######", "####GGAAAGG####", "######ICI######")
                                .aisle("####CC###CC####", "###EAAOGOAAE###", "####CC###CC####")
                                .aisle("###C#######C###", "##EKEG###GEKE##", "###C#######C###")
                                .aisle("##C#########C##", "#GAE#######EAG#", "##C#########C##")
                                .aisle("##C#########C##", "#GAG#######GAG#", "##C#########C##")
                                .aisle("#I###########I#", "OAO#########OAO", "#I###########I#")
                                .aisle("#C###########C#", "GAG#########GAG", "#C###########C#")
                                .aisle("#I###########I#", "OAO#########OAO", "#I###########I#")
                                .aisle("##C#########C##", "#GAG#######GAG#", "##C#########C##")
                                .aisle("##C#########C##", "#GAE#######EAG#", "##C#########C##")
                                .aisle("###C#######C###", "##EKEG###GEKE##", "###C#######C###")
                                .aisle("####CC###CC####", "###EAAOGOAAE###", "####CC###CC####")
                                .aisle("######ICI######", "####GGAAAGG####", "######ICI######")
                                .aisle("###############", "######OSO######", "###############")
                                .where('S', controller(blocks(definition.get())))
                                .where('G', blocks(GTBlocks.FUSION_GLASS.get()).or(casing))
                                .where('E', casing.or(
                                        blocks(PartAbility.INPUT_ENERGY.getBlockRange(tier, GTValues.UEV).toArray(Block[]::new))
                                                .setMinGlobalLimited(1).setPreviewCount(16)))
                                .where('C', casing)
                                .where('K', blocks(GTLFusionCasingBlock.getCoilState(tier)))
                                .where('O', casing.or(abilities(PartAbility.EXPORT_FLUIDS)))
                                .where('A', air())
                                .where('I', casing.or(abilities(PartAbility.IMPORT_FLUIDS).setMinGlobalLimited(2)))
                                .where('#', any())
                                .build();
                    })
                    .shapeInfos((controller) -> {
                        List<MultiblockShapeInfo> shapeInfos = new ArrayList<>();

                        MultiblockShapeInfo.ShapeInfoBuilder baseBuilder = MultiblockShapeInfo.builder()
                                .aisle("###############", "######NMN######", "###############")
                                .aisle("######DCD######", "####GG###GG####", "######UCU######")
                                .aisle("####CC###CC####", "###w##SGS##e###", "####CC###CC####")
                                .aisle("###C#######C###", "##nKsG###GsKn##", "###C#######C###")
                                .aisle("##C#########C##", "#G#e#######w#G#", "##C#########C##")
                                .aisle("##C#########C##", "#G#G#######G#G#", "##C#########C##")
                                .aisle("#D###########D#", "W#E#########W#E", "#U###########U#")
                                .aisle("#C###########C#", "G#G#########G#G", "#C###########C#")
                                .aisle("#D###########D#", "W#E#########W#E", "#U###########U#")
                                .aisle("##C#########C##", "#G#G#######G#G#", "##C#########C##")
                                .aisle("##C#########C##", "#G#e#######w#G#", "##C#########C##")
                                .aisle("###C#######C###", "##sKnG###GnKs##", "###C#######C###")
                                .aisle("####CC###CC####", "###w##NGN##e###", "####CC###CC####")
                                .aisle("######DCD######", "####GG###GG####", "######UCU######")
                                .aisle("###############", "######SGS######", "###############")
                                .where('M', controller, Direction.NORTH)
                                .where('C', GTLFusionCasingBlock.getCasingState(tier))
                                .where('G', GTBlocks.FUSION_GLASS.get())
                                .where('K', GTLFusionCasingBlock.getCoilState(tier))
                                .where('W', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.WEST)
                                .where('E', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.EAST)
                                .where('S', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.SOUTH)
                                .where('N', GTMachines.FLUID_EXPORT_HATCH[tier], Direction.NORTH)
                                .where('w', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.WEST)
                                .where('e', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.EAST)
                                .where('s', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.SOUTH)
                                .where('n', GTMachines.ENERGY_INPUT_HATCH[tier], Direction.NORTH)
                                .where('U', GTMachines.FLUID_IMPORT_HATCH[tier], Direction.UP)
                                .where('D', GTMachines.FLUID_IMPORT_HATCH[tier], Direction.DOWN)
                                .where('#', Blocks.AIR.defaultBlockState());

                        shapeInfos.add(baseBuilder.shallowCopy()
                                .where('G', GTLFusionCasingBlock.getCasingState(tier))
                                .build());
                        shapeInfos.add(baseBuilder.build());
                        return shapeInfos;
                    })
                    .renderer(() -> new FusionReactorRenderer(GTLFusionCasingBlock.getCasingType(tier).getTexture(),
                            GTCEu.id("block/multiblock/fusion_reactor")))
                    .hasTESR(true)
                    .register(),
            GTValues.UHV, GTValues.UEV);

    public static final MultiblockMachineDefinition[] COMPRESSED_FUSION_REACTOR = GTMachines.registerTieredMultis("compressed_fusion_reactor",
            (holder, tier) -> new FusionReactorMachine(holder, tier) {

                @Override
                public long getMaxVoltage() {
                    return super.getOverclockVoltage();
                }
            }, (tier, builder) -> builder
                    .rotationState(RotationState.ALL)
                    .langValue("Fusion Reactor Computer MK %s".formatted(FormattingUtil.toRomanNumeral(tier - 5)))
                    .recipeType(GTRecipeTypes.FUSION_RECIPES)
                    .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, FusionReactorMachine::recipeModifier)
                    .tooltips(
                            Component.translatable("gtceu.machine.fusion_reactor.capacity",
                                    FusionReactorMachine.calculateEnergyStorageFactor(tier, 16) / 1000000L),
                            Component.translatable("gtceu.machine.fusion_reactor.overclocking"))
                    .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
                    .tooltips(Component.translatable("gtceu.multiblock.parallelizable.tooltip"))
                    .tooltipBuilder(GTL_ADD)
                    .appearanceBlock(() -> GTLFusionCasingBlock.getCasingState(tier))
                    .pattern((definition) -> {
                        var casing = blocks(GTLFusionCasingBlock.getCasingState(tier));
                        return FactoryBlockPattern.start()
                                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCIBICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      ECHCC                         CCHCE      ", "      CHHHC                         CHHHC      ", "      ECHCC                         CCHCE      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                                .aisle("                                               ", "       CCC                           CCC       ", "      ECHCC                         CCHCE      ", "      CHHHC                         CHHHC      ", "      ECHCC                         CCHCE      ", "       CCC                           CCC       ", "                                               ")
                                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                                .aisle("                                               ", "           CCCCC               CCCCC           ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "           CCCCC               CCCCC           ", "                                               ")
                                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                                .aisle("                                               ", "                                               ", "                    FCPPPCF                    ", "                    FCISICF                    ", "                    FCPPPCF                    ", "                                               ", "                                               ")
                                .where('S', controller(blocks(definition.get())))
                                .where('B', blocks(GTBlocks.FUSION_GLASS.get()))
                                .where('C', casing)
                                .where('P', casing.or(abilities(PartAbility.PARALLEL_HATCH)))
                                .where('I', casing.or(abilities(PartAbility.IMPORT_FLUIDS).setPreviewCount(16))
                                        .or(abilities(PartAbility.EXPORT_FLUIDS).setPreviewCount(16)))
                                .where('F', blocks(GTLFusionCasingBlock.getFrameState(tier)))
                                .where('H', blocks(GTLFusionCasingBlock.getCompressedCoilState(tier)))
                                .where('E', casing.or(abilities(PartAbility.INPUT_ENERGY)).or(abilities(PartAbility.INPUT_LASER).setPreviewCount(16)))
                                .where('#', air())
                                .where(' ', any())
                                .build();
                    })
                    .workableCasingRenderer(GTLFusionCasingBlock.getCasingType(tier).getTexture(), GTCEu.id("block/multiblock/fusion_reactor"))
                    .register(),
            GTValues.LuV, GTValues.ZPM, GTValues.UV, GTValues.UHV, GTValues.UEV);

    public final static MultiblockMachineDefinition ADVANCED_INTEGRATED_ORE_PROCESSOR = REGISTRATE.multiblock("advanced_integrated_ore_processor", WorkableElectricMultipleRecipesMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(GTLRecipeTypes.INTEGRATED_ORE_PROCESSOR)
            .tooltips(Component.translatable("gtceu.machine.integrated_ore_processor.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.advanced_integrated_ore_processor.tooltip.0"))
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.multiple_recipes.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.integrated_ore_processor")))
            .tooltipBuilder(GTL_ADD)
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("    AAAAAAAAAA ", "    AAAGGGGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "   AAAAGHHGAAAA", "     AAGHHGAA  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("   AAAAAAAAAAAA", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "    A        A ", "   AAA      AAA", "     A      A  ", "      AGGGGA   ")
                            .aisle("IIIAAAAAAAAAAAA", "IIIBADEE  EEDAB", "IIIBADEE  EEDAB", "IIIBADEE  EEDAB", "IIIBADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("IIIAAAAAAAAAAAA", "IDIBADEE  EEDAB", "IDIBADEE  EEDAB", "IDIBADEE  EEDAB", "IIIBADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   BADEE  EEDAB", "   AAAFF  FFAAA", "     AFF  FFA  ", "      AGCCGA   ")
                            .aisle("III AAAAAAAAAA ", "III AAAGGGGAAA ", "I~I AAAGHHGAAA ", "III AAAGHHGAAA ", "III AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "    AAAGHHGAAA ", "   AAAAGHHGAAAA", "     AAGHHGAA  ", "      AGGGGA   ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("A", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
                            .where("B", Predicates.blocks(GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.HSSS).get()))
                            .where("C", Predicates.blocks(Registries.getBlock("kubejs:restraint_device")))
                            .where("D", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                            .where("E", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                            .where("F", Predicates.blocks(GTBlocks.CASING_GRATE.get()))
                            .where("G", Predicates.blocks(GTBlocks.CASING_HSSE_STURDY.get()))
                            .where("H", Predicates.blocks(Registries.getBlock("kubejs:hsss_reinforced_borosilicate_glass")))
                            .where("I", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(PartAbility.INPUT_LASER))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS)))
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/gcym/large_maceration_tower"))
            .register();

    public final static MultiblockMachineDefinition SUPER_COMPUTATION = REGISTRATE.multiblock("super_computation", (holder) -> new ComputationProviderMachine(holder, false))
            .rotationState(RotationState.NONE)
            .allowExtendedFacing(false)
            .allowFlip(false)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.2"))
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.3"))
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.4"))
            .tooltips(Component.translatable("gtceu.machine.super_computation.tooltip.5"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.super_computation")))
            .tooltipBuilder(GTL_ADD)
            .appearanceBlock(GTBlocks.COMPUTER_CASING)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .pattern((definition) ->
                    FactoryBlockPattern.start()
                            .aisle("    aaaaaaa    ", "    abbbbba    ", "    abbbbba    ", "    abbbbba    ", "    aaaaaaa    ", "    abbbbba    ", "    abbbbba    ", "    abbbbba    ", "    aaaaaaa    ")
                            .aisle("   aaaaaaaaa   ", "   adedededa   ", "   adedededa   ", "   adedededa   ", "   afffffffa   ", "   adedededa   ", "   adedededa   ", "   adedededa   ", "   aaaaaaaaa   ")
                            .aisle("  aaaaaaaaaaa  ", "  addedededda  ", "  addedededda  ", "  addedededda  ", "  afffffffffa  ", "  addedededda  ", "  addedededda  ", "  addedededda  ", "  aaaaaaaaaaa  ")
                            .aisle(" aaaaaaaaaaaaa ", " adddedededdda ", " adddedededdda ", " adddedededdda ", " afffffffffffa ", " adddedededdda ", " adddedededdda ", " adddedededdda ", " aaaaaaaaaaaaa ")
                            .aisle("aaaaaaaaaaaaaaa", "addd       ddda", "addd       ddda", "addd       ddda", "afff       fffa", "addd       ddda", "addd       ddda", "adddcccccccddda", "aaaagggagggaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "beee       eeeb", "beee       eeeb", "beee       eeeb", "afff       fffa", "beee       eeeb", "beee       eeeb", "beeeccccccceeeb", "aaaagggagggaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "bddd       dddb", "bddd       dddb", "bddd       dddb", "afff       fffa", "bddd       dddb", "bddd       dddb", "bdddcccccccdddb", "aaaagggagggaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "beee       eeeb", "beee       eeeb", "beee       eeeb", "afff       fffa", "beee       eeeb", "beee       eeeb", "beeeccccccceeeb", "aaaaaaa~aaaaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "bddd       dddb", "bddd       dddb", "bddd       dddb", "afff       fffa", "bddd       dddb", "bddd       dddb", "bdddcccccccdddb", "aaaagggagggaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "beee       eeeb", "beee       eeeb", "beee       eeeb", "afff       fffa", "beee       eeeb", "beee       eeeb", "beeeccccccceeeb", "aaaagggagggaaaa")
                            .aisle("aaaaaaaaaaaaaaa", "addd       ddda", "addd       ddda", "addd       ddda", "afff       fffa", "addd       ddda", "addd       ddda", "adddcccccccddda", "aaaagggagggaaaa")
                            .aisle(" aaaaaaaaaaaaa ", " adddedededdda ", " adddedededdda ", " adddedededdda ", " afffffffffffa ", " adddedededdda ", " adddedededdda ", " adddedededdda ", " aaaaaaaaaaaaa ")
                            .aisle("  aaaaaaaaaaa  ", "  addedededda  ", "  addedededda  ", "  addedededda  ", "  afffffffffa  ", "  addedededda  ", "  addedededda  ", "  addedededda  ", "  aaaaaaaaaaa  ")
                            .aisle("   aaaaaaaaa   ", "   adedededa   ", "   adedededa   ", "   adedededa   ", "   afffffffa   ", "   adedededa   ", "   adedededa   ", "   adedededa   ", "   aaaaaaaaa   ")
                            .aisle("    aaaaaaa    ", "    abbbbba    ", "    abbbbba    ", "    abbbbba    ", "    aaaaaaa    ", "    abbbbba    ", "    abbbbba    ", "    abbbbba    ", "    aaaaaaa    ")
                            .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("a", Predicates.blocks(GTBlocks.COMPUTER_CASING.get())
                                    .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_TRANSMISSION).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where("b", Predicates.blocks(GTBlocks.COMPUTER_HEAT_VENT.get()))
                            .where("c", Predicates.fluids(Registries.getFluid("kubejs:gelid_cryotheum")))
                            .where("d", Predicates.blocks(GTLBlocks.SUPER_COOLER_COMPONENT.get()))
                            .where("e", Predicates.blocks(GTLBlocks.SUPER_COMPUTATION_COMPONENT.get()))
                            .where("f", Predicates.blocks(GTBlocks.ADVANCED_COMPUTER_CASING.get()))
                            .where("g", Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/hpca/computer_casing/back"), GTCEu.id("block/super_computation"))
            .register();

    public final static MultiblockMachineDefinition CREATE_COMPUTATION = REGISTRATE.multiblock("create_computation", (holder) -> new ComputationProviderMachine(holder, true))
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.create_computation.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.super_computation")))
            .tooltipBuilder(GTL_ADD)
            .appearanceBlock(GTBlocks.ADVANCED_COMPUTER_CASING)
            .recipeModifier(GTRecipeModifiers.ELECTRIC_OVERCLOCK.apply(OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK))
            .pattern((definition) -> FactoryBlockPattern.start()
                    .aisle("aa", "bb", "bb", "bb", "aa")
                    .aisle("aa", "cc", "cc", "cc", "aa")
                    .aisle("aa", "cc", "cc", "cc", "aa")
                    .aisle("aa", "cc", "cc", "cc", "aa")
                    .aisle("~a", "bb", "bb", "bb", "aa")
                    .where("~", Predicates.controller(Predicates.blocks(definition.get())))
                    .where("b", Predicates.blocks(GTBlocks.ADVANCED_COMPUTER_CASING.get())
                            .or(Predicates.abilities(PartAbility.COMPUTATION_DATA_TRANSMISSION).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(1)))
                    .where("a", Predicates.blocks(GTBlocks.ADVANCED_COMPUTER_CASING.get()))
                    .where("c", Predicates.blocks(Registries.getBlock("kubejs:create_hpca_component")))
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/hpca/advanced_computer_casing/back"),GTCEu.id("block/multiblock/hpca"))
            .register();


    public final static MultiblockMachineDefinition DIMENSIONALLY_TRANSCENDENT_STEAM_BOILER = REGISTRATE.multiblock("dimensionally_transcendent_steam_boiler", holder -> new LargeBoilerMachine(holder, 4096000, 32))
            .rotationState(RotationState.ALL)
        .recipeType(GTRecipeTypes.LARGE_BOILER_RECIPES)
        .tooltips(Component.translatable("gtceu.multiblock.large_boiler.max_temperature", 4096000 + 274.15, 4096000))
            .tooltips(Component.translatable("gtceu.multiblock.large_boiler.heat_time_tooltip", 4096000 / 32 / 20))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                      Component.translatable("gtceu.large_boiler")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(LargeBoilerMachine::recipeModifier)
            .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
        .pattern(definition ->
            DTPF.where("a", Predicates.controller(Predicates.blocks(definition.get())))
            .where("e", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                    .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(16))
            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(2))
            .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1)))
            .where("b", Predicates.blocks(GTBlocks.CASING_INVAR_HEATPROOF.get()))
            .where("C", Predicates.blocks(GCyMBlocks.MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
            .where("d", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get()))
            .where("s", Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
            .where(" ", Predicates.any())
            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"), GTCEu.id("block/multiblock/generator/large_tungstensteel_boiler"))
            .register();

    public final static MultiblockMachineDefinition DIMENSIONALLY_TRANSCENDENT_STEAM_OVEN = REGISTRATE.multiblock("dimensionally_transcendent_steam_oven", holder -> new LargeSteamParallelMultiblockMachine(holder, 524288))
            .rotationState(RotationState.ALL)
            .recipeType(GTRecipeTypes.FURNACE_RECIPES)
            .tooltips(Component.translatable("gtceu.machine.dimensionally_transcendent_dirt_forge.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("compass.node.gtceu.steam/steam_furnace")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifiers((machine, recipe, params, result) -> GTLRecipeModifiers.reduction(machine, recipe, 0.01, 1), (machine, recipe, params, result) -> LargeSteamParallelMultiblockMachine.recipeModifier(machine, recipe, 0))
            .appearanceBlock(GTBlocks.CASING_COKE_BRICKS)
            .pattern(definition ->
                    DTPF.where("a", Predicates.controller(Predicates.blocks(definition.get())))
                            .where("e", Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get())
                                    .or(Predicates.abilities(PartAbility.STEAM).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_IMPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.STEAM_EXPORT_ITEMS).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.EXPORT_ITEMS).setMaxGlobalLimited(4))
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(4)))
                            .where("b", Predicates.blocks(Blocks.BRICKS))
                            .where("C", Predicates.blocks(Blocks.DEEPSLATE))
                            .where("d", Predicates.blocks(Blocks.STONE_BRICKS))
                            .where("s", Predicates.blocks(GTBlocks.CASING_BRONZE_BRICKS.get()))
                            .where(" ", Predicates.any())
                            .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_bronze_plated_bricks"), GTCEu.id("block/multiblock/steam_oven"))
            .register();

    public static final MultiblockMachineDefinition GENERATOR_ARRAY = REGISTRATE.multiblock("generator_array", GeneratorArrayMachine::new)
            .rotationState(RotationState.ALL)
            .appearanceBlock(GTBlocks.CASING_STEEL_SOLID)
            .recipeType(GTRecipeTypes.DUMMY_RECIPES)
            .generator(true)
            .tooltips(Component.translatable("gtceu.machine.generator_array.tooltip.0"))
            .tooltips(Component.translatable("gtceu.machine.generator_array.tooltip.1"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_6.tooltip",
                    Component.translatable("gtceu.steam_turbine"),
                    Component.translatable("gtceu.combustion_generator"),
                    Component.translatable("gtceu.gas_turbine"),
                    Component.translatable("gtceu.semi_fluid_generator"),
                    Component.translatable("gtceu.rocket_engine"),
                    Component.translatable("gtceu.naquadah_reactor")))
            .tooltipBuilder(GTL_ADD)
            .recipeModifier(GeneratorArrayMachine::recipeModifier, true)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "CCC", "XXX")
                    .aisle("XXX", "C#C", "XXX")
                    .aisle("XSX", "CCC", "XXX")
                    .where('S', Predicates.controller(blocks(definition.getBlock())))
                    .where('X', blocks(GTBlocks.CASING_STEEL_SOLID.get())
                            .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                            .or(Predicates.abilities(PartAbility.OUTPUT_ENERGY).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                            .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(1)))
                    .where('C', blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                    .where('#', Predicates.air())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_solid_steel"), GTCEu.id("block/multiblock/processing_array"))
            .register();

    public static final MultiblockMachineDefinition ADVANCED_MULTI_SMELTER = REGISTRATE
            .multiblock("advanced_multi_smelter", CoilWorkableElectricMultipleRecipesMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeTypes(GTRecipeTypes.FURNACE_RECIPES)
            .appearanceBlock(GTBlocks.CASING_INVAR_HEATPROOF)
            .tooltips(Component.translatable("gtceu.multiblock.coil_parallel"))
            .tooltips(Component.translatable("gtceu.multiblock.laser.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.multiple_recipes.tooltip"))
            .tooltips(Component.translatable("gtceu.machine.available_recipe_map_1.tooltip",
                    Component.translatable("gtceu.electric_furnace")))
            .tooltipBuilder(GTL_ADD)
            .pattern(definition -> FactoryBlockPattern.start()
                    .aisle("XXX", "CCC", "XXX")
                    .aisle("XXX", "C#C", "XMX")
                    .aisle("XSX", "CCC", "XXX")
                    .where('S', controller(blocks(definition.get())))
                    .where('X', blocks(GTBlocks.CASING_INVAR_HEATPROOF.get()).setMinGlobalLimited(9)
                            .or(autoAbilities(definition.getRecipeTypes()))
                            .or(Predicates.abilities(PartAbility.INPUT_LASER))
                            .or(autoAbilities(true, false, false)))
                    .where('M', abilities(PartAbility.MUFFLER))
                    .where('C', heatingCoils())
                    .where('#', air())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/solid/machine_casing_heatproof"), GTCEu.id("block/multiblock/multi_furnace"))
            .additionalDisplay((controller, components) -> {
                if (controller.isFormed()) {
                    components.add(Component.translatable("gtceu.multiblock.parallel", Component.literal(FormattingUtil.formatNumbers(Math.min(2147483647, Math.pow(2, (double) ((CoilWorkableElectricMultipleRecipesMultiblockMachine) controller).getCoilType().getCoilTemperature() / 900)))).withStyle(ChatFormatting.DARK_PURPLE)).withStyle(ChatFormatting.GRAY));
                }
            })
            .register();
}
