package org.gtlcore.gtlcore.common.item;

import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.Ae2BaseProcessingPattern;
import org.gtlcore.gtlcore.config.ConfigHolder;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.AETextInputButtonWidget;

import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import appeng.api.inventories.InternalInventory;
import appeng.api.parts.IPart;
import appeng.api.parts.PartHelper;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.helpers.patternprovider.PatternProviderLogicHost;
import appeng.parts.crafting.PatternProviderPart;
import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.glodblock.github.extendedae.common.parts.PartExPatternProvider;
import lombok.Setter;

import java.util.HashMap;

@Setter
public class PatternModifier implements IItemUIFactory {

    public static final PatternModifier INSTANCE = new PatternModifier();
    private int Ae2PatternGeneratorScale = 1;
    private long Ae2PatternGeneratorMaxItemStack = 1000000L;
    private long Ae2PatternGeneratorMaxFluidStack = 1000000L;
    private int Ae2PatternGeneratorDivScale = 1;
    private int Ae2PatternGeneratorAppliedNumber = 1;

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder heldItemHolder, Player player) {
        return new ModularUI(206, 124, heldItemHolder, player).widget(
                new WidgetGroup(0, 0, 206, 124)
                        .addWidget(new ImageWidget(8, 8, 190, 108, GuiTextures.DISPLAY))
                        .addWidget(new LabelWidget(12, 12, "AE样板倍乘器"))
                        .addWidget(new LabelWidget(12, 22, "设置倍数后，shift右键样板供应器方块使用"))
                        .addWidget(new LabelWidget(12, 32, "先做乘法，后做除法，数量限制对成品不生效"))
                        .addWidget(new LabelWidget(12, 42, "若应用次数为3，则代表执行三次应用次数为1的操作"))
                        .addWidget(new AETextInputButtonWidget(120, 46 + 4, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorScale))
                                .setOnConfirm(this::setAe2PatternGeneratorScale)
                                .setButtonTooltips(Component.literal("设置模板乘数")))
                        .addWidget(new AETextInputButtonWidget(120, 60 + 4, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorDivScale))
                                .setOnConfirm(this::setAe2PatternGeneratorDivScale)
                                .setButtonTooltips(Component.literal("设置模板除数")))
                        .addWidget(new AETextInputButtonWidget(120, 74 + 4, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorMaxItemStack))
                                .setOnConfirm(this::setAe2PatternGeneratorMaxItemStack)
                                .setButtonTooltips(Component.literal("设置乘法后最大物品/个")))
                        .addWidget(new AETextInputButtonWidget(120, 88 + 4, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorMaxFluidStack))
                                .setOnConfirm(this::setAe2PatternGeneratorMaxFluidStack)
                                .setButtonTooltips(Component.literal("设置乘法后最大流体/桶")))
                        .addWidget(new AETextInputButtonWidget(120, 102 + 4, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorAppliedNumber))
                                .setOnConfirm(this::setAe2PatternGeneratorAppliedNumber)
                                .setButtonTooltips(Component.literal("一次使用的应用次数(<=16)"))))
                .background(GuiTextures.BACKGROUND);
    }

    private void setAe2PatternGeneratorAppliedNumber(String s) {
        Ae2PatternGeneratorAppliedNumber = Math.min(16, Integer.parseInt(s));
    }

    private void setAe2PatternGeneratorMaxFluidStack(String s) {
        Ae2PatternGeneratorMaxFluidStack = Integer.parseInt(s);
    }

    private void setAe2PatternGeneratorMaxItemStack(String s) {
        Ae2PatternGeneratorMaxItemStack = Integer.parseInt(s);
    }

    private void setAe2PatternGeneratorDivScale(String s) {
        Ae2PatternGeneratorDivScale = Math.min(Integer.MAX_VALUE, Integer.parseInt(s));
    }

    private void setAe2PatternGeneratorScale(String s) {
        Ae2PatternGeneratorScale = Math.min(Integer.MAX_VALUE, Integer.parseInt(s));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            HeldItemUIFactory.INSTANCE.openUI(serverPlayer, usedHand);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() instanceof ServerPlayer serverPlayer) {
            /*
             * shift右键乘法逻辑
             */
            if (context.getPlayer().isShiftKeyDown()) {
                int i_6wertg = 0;
                while (i_6wertg < Ae2PatternGeneratorAppliedNumber) {
                    i_6wertg++;
                    BlockPos clickedPos = context.getClickedPos();
                    Level level = context.getLevel();
                    BlockEntity blockEntityBlock = level.getBlockEntity(clickedPos);
                    Block block = level.getBlockState(clickedPos).getBlock();
                    IPart Part = PartHelper.getPart(level, clickedPos, context.getClickedFace());
                    int soltNumber = 0;
                    boolean isPart = Part != null;
                    if (block.equals(AEBlocks.PATTERN_PROVIDER.block()) || Part instanceof PatternProviderPart) {
                        soltNumber = 9;
                    }
                    if (block.equals(EPPItemAndBlock.EX_PATTERN_PROVIDER) || Part instanceof PartExPatternProvider) {
                        soltNumber = ConfigHolder.INSTANCE.exPatternProvider;
                    }
                    if (soltNumber == 0) {
                        serverPlayer.displayClientMessage(Component.literal("只能对着块状样板供应器使用"), true);
                        return InteractionResult.FAIL;
                    }
                    InternalInventory internalInventory;
                    if (blockEntityBlock != null) {
                        internalInventory = ((PatternProviderLogicHost) (isPart ? Part : blockEntityBlock)).getLogic().getPatternInv();
                    } else {
                        internalInventory = null;
                    }
                    if (internalInventory == null) {
                        serverPlayer.displayClientMessage(Component.literal("未能成功获得样板供应器物品栏"), true);
                        return InteractionResult.FAIL;
                    }

                    int i = 0;
                    HashMap<Integer, ItemStack> newItemStackHashMap = new HashMap<>();
                    while (i < soltNumber) {
                        ItemStack itemStack = internalInventory.getStackInSlot(i);
                        if (!itemStack.isEmpty()) {
                            ItemStack patternItemStack = getNewPatternItemStack(serverPlayer, itemStack);
                            newItemStackHashMap.put(i, patternItemStack);
                        }
                        i++;
                    }
                    newItemStackHashMap.forEach((integer, itemStack) -> {
                        if (itemStack.is(AEItems.PROCESSING_PATTERN.asItem())) {
                            internalInventory.extractItem(integer, 1, false);
                            internalInventory.insertItem(integer, itemStack, false);
                        }
                    });
                }
                serverPlayer.displayClientMessage(Component.literal("已更新内部的样板，应用了%s次".formatted(Ae2PatternGeneratorAppliedNumber)), true);
            }
            if (!context.getPlayer().isShiftKeyDown()) {
                serverPlayer.displayClientMessage(Component.literal("右键空气打开GUI"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private ItemStack getNewPatternItemStack(ServerPlayer serverPlayer, ItemStack itemStack) {
        Ae2BaseProcessingPattern ae2BaseProcessingPattern = new Ae2BaseProcessingPattern(itemStack, serverPlayer);
        ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorScale,
                false,
                Ae2PatternGeneratorMaxItemStack,
                Ae2PatternGeneratorMaxFluidStack);
        ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorDivScale, true);
        return ae2BaseProcessingPattern.getPatternItemStack();
    }
}
