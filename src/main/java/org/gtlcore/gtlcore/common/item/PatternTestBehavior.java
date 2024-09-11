package org.gtlcore.gtlcore.common.item;

import appeng.api.inventories.InternalInventory;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.helpers.patternprovider.PatternProviderLogic;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.integration.ae2.gui.widget.AETextInputButtonWidget;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.ButtonWidget;
import com.lowdragmc.lowdraglib.gui.widget.ImageWidget;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.*;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.Ae2GtmProcessingPattern;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.ConflictAnalysisManager;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.ConflictAnalysisResult;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.GTRecipeManager;

import java.util.*;

@Setter
public class PatternTestBehavior implements IItemUIFactory {
    public static final PatternTestBehavior INSTANCE = new PatternTestBehavior();

    private String ConflictAnalysisType = "";
    private int ConflictAnalysisCircuit = 0;

    private String Ae2PatternGeneratorType = "";
    private int Ae2PatternGeneratorCircuit = 0;
    private int Ae2PatternGeneratorScale = 1;

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder heldItemHolder, Player player) {
        var containerPatternAnalysis=new WidgetGroup(8,8,160,50)
                .addWidget(new ImageWidget(4, 4, 152, 42, GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(6, 6, "AE样板冲突分析"))
                .addWidget(new AETextInputButtonWidget(82, 6, 72, 12)
                        .setText(ConflictAnalysisType)
                        .setOnConfirm(this::setConflictAnalysisType)
                        .setButtonTooltips(Component.literal("设置配方类型")))
                .addWidget(new AETextInputButtonWidget(82, 20, 72, 12)
                        .setText(String.valueOf(ConflictAnalysisCircuit))
                        .setOnConfirm(s -> setConflictAnalysisCircuit(Integer.parseInt(s)))
                        .setButtonTooltips(Component.literal("设置编程电路")))
                .addWidget(new ButtonWidget(6, 24, 64, 20,
                        new GuiTextureGroup(GuiTextures.BUTTON, new TextTexture("开始分析")),
                        clickData -> useAnalysisRecipesBaby(heldItemHolder))
                        .setHoverTooltips(Component.literal("当前配方类型：")
                                .append(Component.translatable("gtceu." + ConflictAnalysisType)).append(" 电路：" + ConflictAnalysisCircuit)));

        var containerPatternGenerator = new WidgetGroup(8, 58, 160, 50)
                .addWidget(new ImageWidget(4, 4, 152, 42, GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(6, 6, "AE样板生成器"))
                .addWidget(new AETextInputButtonWidget(82, 6, 72, 12)
                        .setText(Ae2PatternGeneratorType)
                        .setOnConfirm(this::setAe2PatternGeneratorType)
                        .setButtonTooltips(Component.literal("设置配方类型")))
                .addWidget(new AETextInputButtonWidget(82, 20, 72, 12)
                        .setText(String.valueOf(Ae2PatternGeneratorCircuit))
                        .setOnConfirm(s -> setAe2PatternGeneratorCircuit(Integer.parseInt(s)))
                        .setButtonTooltips(Component.literal("设置编程电路")))
                .addWidget(new AETextInputButtonWidget(82, 34, 72, 12)
                        .setText(String.valueOf(Ae2PatternGeneratorScale))
                        .setOnConfirm(s -> setAe2PatternGeneratorScale(Integer.parseInt(s)))
                        .setButtonTooltips(Component.literal("设置模板倍数")))
                .addWidget(new ButtonWidget(6, 24, 64, 20,
                        new GuiTextureGroup(GuiTextures.BUTTON, new TextTexture("获取样板")),
                        clickData -> useAe2PatternGenerator(heldItemHolder))
                        .setHoverTooltips(Component.literal("当前配方类型：")
                        .append(Component.translatable("gtceu." + Ae2PatternGeneratorType))
                                .append(" 电路：" + Ae2PatternGeneratorCircuit)
                                .append(" 尺寸：" + Ae2PatternGeneratorScale)
                        )
                );

        return new ModularUI(176, 124, heldItemHolder, player)
                .widget(containerPatternAnalysis)
                .widget(containerPatternGenerator)
                .background(GuiTextures.BACKGROUND);
    }

    public void useAe2PatternGenerator(HeldItemUIFactory.HeldItemHolder playerInventoryHolder){
        if (playerInventoryHolder.getPlayer() instanceof ServerPlayer serverPlayer) {
            boolean allowUsing=false;
            if(!allowUsing){
                return;
            }
            /*
                获取样板 入口
             */
            GTRecipeType recipeType = GTRecipeTypes.get("gtceu:" + Ae2PatternGeneratorType);
            GTRecipeManager gtRecipeManager = new GTRecipeManager();
            gtRecipeManager.filterRecipesByType(recipeType);
            gtRecipeManager.filterRecipesByCircuit(Ae2PatternGeneratorCircuit);
            List<GTRecipe> recipes = gtRecipeManager.getRecipes();
            for(GTRecipe recipe : recipes){
                Ae2GtmProcessingPattern ae2GtmProcessingPattern = Ae2GtmProcessingPattern.of(1, recipe, serverPlayer);
                ae2GtmProcessingPattern.setScale(Ae2PatternGeneratorScale);
                ae2GtmProcessingPattern.setDefaultFilter();
                ItemStack patternItemStack = ae2GtmProcessingPattern.getPatternItemStack();
                serverPlayer.kjs$give(patternItemStack);
            }
        }
    }
    public void useAnalysisRecipesBaby(HeldItemUIFactory.HeldItemHolder playerInventoryHolder) {
        if (playerInventoryHolder.getPlayer() instanceof ServerPlayer) {
            /*
                分析配方冲突 入口
             */
            analysisRecipesBaby(GTRecipeTypes.get("gtceu:" + ConflictAnalysisType), ConflictAnalysisCircuit);
        }
    }

    public void analysisRecipesBaby(GTRecipeType recipeType, int CIRCUIT) {
        // 筛选出指定机器的配方
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        RecipeManager recipeManager = currentServer.getRecipeManager();
        Set<GTRecipe> recipes=new HashSet<>();
        for(Recipe<?> recipe : recipeManager.getRecipes()){
            if(recipe instanceof GTRecipe && recipe.getType().equals(recipeType)){
                recipes.add((GTRecipe) recipe);
            }
        }
        // 构造输入，调用类，对符合指定电路的配方分析
        ConflictAnalysisManager conflictAnalysisManager = new ConflictAnalysisManager(recipes.stream().toList());
        List<ConflictAnalysisResult> conflictAnalysisResults = conflictAnalysisManager.useFindConflictForAll(CIRCUIT);

        // 对获得的冲突样板集 遍历输出
        conflictAnalysisResults.forEach(ConflictAnalysisResult::exportToPrint);
        GTLCore.LOGGER.info("可能被错误合成配方数量{} / 所有此电路配方数{}", conflictAnalysisResults.toArray().length, recipes.toArray().length);
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
                BlockEntity blockEntitylock = level.getBlockEntity(clickedPos);
                if (
                        !level.getBlockState(clickedPos).getBlock().equals(AEBlocks.PATTERN_PROVIDER.block()) &&
                        !level.getBlockState(clickedPos).getBlock().kjs$getId().equals("ex_pattern_provider")
                ){
                    serverPlayer.displayClientMessage(Component.literal("只能对着样板供应器使用"),true);
                    return InteractionResult.FAIL;
                }
                int soltNumber=0;

                if(level.getBlockState(clickedPos).getBlock().equals(AEBlocks.PATTERN_PROVIDER.block())){
                    soltNumber=9;
                }
                if(level.getBlockState(clickedPos).getBlock().kjs$getId().equals("ex_pattern_provider")){
                    soltNumber=36;
                }
                InternalInventory internalInventory;
                if (blockEntitylock != null) {
                    internalInventory = ((PatternProviderBlockEntity) blockEntitylock).getLogic().getPatternInv();
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
                        Ae2BaseProcessingPattern ae2BaseProcessingPattern = new Ae2BaseProcessingPattern(1, itemStack, serverPlayer);
                        ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorScale);
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
//                Iterator<ItemStack> iterator = internalInventory.iterator();
//                while(iterator.hasNext()){
//                    ItemStack itemStack = iterator.next();
//                    Ae2BaseProcessingPattern ae2BaseProcessingPattern = new Ae2BaseProcessingPattern(1, itemStack, serverPlayer);
//                    ae2BaseProcessingPattern.setScale(Ae2PatternGeneratorScale);
//                    serverPlayer.kjs$give(ae2BaseProcessingPattern.getPatternItemStack());
//                }



            }
            serverPlayer.displayClientMessage(Component.literal("右键空气打开GUI"),true);
        }
        return InteractionResult.SUCCESS;
    }
}
