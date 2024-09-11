package org.gtlcore.gtlcore.common.item;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import com.glodblock.github.extendedae.common.EPPItemAndBlock;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.AETextInputButtonWidget;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import lombok.Setter;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.Ae2BaseProcessingPattern;
import org.gtlcore.gtlcore.config.ConfigHolder;

import java.util.HashMap;

@Setter
public class PatternModifier implements IItemUIFactory {
    public static final PatternModifier INSTANCE = new PatternModifier();
    private int Ae2PatternGeneratorScale = 1;
    private long Ae2PatternGeneratorMaxStack = 1000000L;
    private int Ae2PatternGeneratorDivScale = 1;

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder heldItemHolder, Player player) {
        return new ModularUI(176,124,heldItemHolder,player).widget(
                new WidgetGroup(8,8,176,124)
                        .addWidget(new ImageWidget(8,8,152,100, GuiTextures.DISPLAY))
                        .addWidget(new LabelWidget(6, 6, "AE样板倍乘器"))
                        .addWidget(new LabelWidget(6, 16, "设置倍数后，shift右键样板供应器方块使用"))
                        .addWidget(new LabelWidget(6, 24, "先做乘法，后做除法"))
                        .addWidget(new AETextInputButtonWidget(82, 34, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorScale))
                                .setOnConfirm(this::setAe2PatternGeneratorScale)
                                .setButtonTooltips(Component.literal("设置模板乘数")))
                        .addWidget(new AETextInputButtonWidget(82, 48, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorMaxStack))
                                .setOnConfirm(this::setAe2PatternGeneratorMaxStack)
                                .setButtonTooltips(Component.literal("设置乘法后最大物品或流体数量 个或B")))
                        .addWidget(new AETextInputButtonWidget(82, 62, 72, 12)
                                .setText(String.valueOf(Ae2PatternGeneratorDivScale))
                                .setOnConfirm(this::setAe2PatternGeneratorDivScale)
                                .setButtonTooltips(Component.literal("设置模板除数")))
        ).background(GuiTextures.BACKGROUND);
    }

    private void setAe2PatternGeneratorMaxStack(String s) {
        Ae2PatternGeneratorMaxStack= Integer.parseInt(s);
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
                shift右键乘法逻辑
             */
            if (context.getPlayer().isShiftKeyDown()){
                BlockPos clickedPos = context.getClickedPos();
                Level level = context.getLevel();
                BlockEntity blockEntityBlock = level.getBlockEntity(clickedPos);
                if (
                        !level.getBlockState(clickedPos).getBlock().equals(AEBlocks.PATTERN_PROVIDER.block()) &&
                        !level.getBlockState(clickedPos).getBlock().equals(EPPItemAndBlock.EX_PATTERN_PROVIDER)
                ) {
                    serverPlayer.displayClientMessage(Component.literal("只能对着块状样板供应器使用"),true);
                    return InteractionResult.FAIL;
                }
                int soltNumber=0;

                if(
                        level.getBlockState(clickedPos).getBlock().equals(AEBlocks.PATTERN_PROVIDER.block())
                ){
                    soltNumber=9;
                }
                if(level.getBlockState(clickedPos).getBlock().equals(EPPItemAndBlock.EX_PATTERN_PROVIDER)){
                    soltNumber= ConfigHolder.INSTANCE.exPatternProvider;
                }
                InternalInventory internalInventory;
                if (blockEntityBlock != null) {
                    internalInventory = ((PatternProviderBlockEntity) blockEntityBlock).getLogic().getPatternInv();
                } else {
                    internalInventory = null;
                }
                if (internalInventory == null) {
                    serverPlayer.displayClientMessage(Component.literal("未能成功获得样板供应器物品栏"),true);
                    return InteractionResult.FAIL;
                }

                int i=0;
                HashMap<Integer, ItemStack> newItemStackHashMap=new HashMap<>();
                while (i<soltNumber){
                    ItemStack itemStack = internalInventory.getStackInSlot(i);
                    if (!itemStack.isEmpty()) {
                        Ae2BaseProcessingPattern ae2BaseProcessingPattern = new Ae2BaseProcessingPattern(itemStack, serverPlayer);
                        ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorScale,false,Ae2PatternGeneratorMaxStack);
                        ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorDivScale,true);
                        ItemStack patternItemStack = ae2BaseProcessingPattern.getPatternItemStack();
                        newItemStackHashMap.put(i, patternItemStack);
                    }
                    i++;
                }
                newItemStackHashMap.forEach((integer, itemStack) -> {
                    if (itemStack.is(AEItems.PROCESSING_PATTERN.asItem())){
                        internalInventory.extractItem(integer,1,false);
                        internalInventory.insertItem(integer, itemStack, false);
                    }
                });
                serverPlayer.displayClientMessage(Component.literal("已更新内部的样板"),true);
            }
            if(!context.getPlayer().isShiftKeyDown()){
                serverPlayer.displayClientMessage(Component.literal("右键空气打开GUI"),true);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
