package org.gtlcore.gtlcore.common.item;

import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.GuiTextureGroup;
import com.lowdragmc.lowdraglib.gui.texture.TextTexture;
import com.lowdragmc.lowdraglib.gui.widget.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.Ae2PatternConflict;
import org.gtlcore.gtlcore.api.item.tool.ae2.patternTool.Ae2PatternManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatternTestBehavior implements IItemUIFactory {
    public static final PatternTestBehavior INSTANCE = new PatternTestBehavior();

    protected PatternTestBehavior() {
        /**/
    }

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder heldItemHolder, Player player) {
        var containerPatternAnalysis=new WidgetGroup(8,8,160,50)
                .addWidget(new ImageWidget(4,4,152,42,GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(4,4,"AE样板冲突分析"))
                .addWidget(new ButtonWidget(
                        8,8+9+4,64,18,
                        new GuiTextureGroup(
                                GuiTextures.BUTTON,
                                new TextTexture("开始分析")),
                                clickData -> useAnalysisRecipesBaby(heldItemHolder))
                );


        var containerPatternGeneratoe=new WidgetGroup(8,58,160,50)
                .addWidget(new ImageWidget(4,4,152,42,GuiTextures.DISPLAY))
                .addWidget(new LabelWidget(4,4,"AE样板生成器 没开始做"));


        return new ModularUI(176,8+50+8+50+8,heldItemHolder,player)
                .widget(containerPatternAnalysis)
                .widget(containerPatternGeneratoe)
                .background(GuiTextures.BACKGROUND)
                ;
    }

    public void useAnalysisRecipesBaby(HeldItemUIFactory.HeldItemHolder playerInventoryHolder){
        if (playerInventoryHolder.getPlayer() instanceof ServerPlayer serverPlayer) {


//            int[] array = new int[20];
//            for (int i = 1; i < array.length+1; i++) {
                analysisRecipesBaby(GTRecipeTypes.MIXER_RECIPES,1);
//            }
        }
    }

    public void analysisRecipesBaby(GTRecipeType recipeType, int CIRCUIT){
        // 筛选出化学反应釜的配方
        MinecraftServer currentServer = ServerLifecycleHooks.getCurrentServer();
        RecipeManager recipeManager = currentServer.getRecipeManager();
        Set<GTRecipe> recipes=new HashSet<>();
        for(Recipe<?> recipe : recipeManager.getRecipes()){
            if(recipe instanceof GTRecipe && recipe.getType().equals(recipeType)){
                recipes.add((GTRecipe) recipe);
            }
        }
        // 构造输入，调用类，对电路为0的配方分析
        Ae2PatternManager ae2PatternManager =new Ae2PatternManager(recipes.stream().toList());
        List<Ae2PatternConflict> ae2PatternConflicts = ae2PatternManager.useFindConflictForAll(CIRCUIT);

        // 序列化返回结果
        ae2PatternConflicts.forEach(Ae2PatternConflict::exportToPrint);
        System.out.printf("可能冲突配方数量%s / 所有此电路配方数%s%n", ae2PatternConflicts.toArray().length,recipes.toArray().length);

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Item item, Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if(player instanceof ServerPlayer serverPlayer) {
            HeldItemUIFactory.INSTANCE.openUI(serverPlayer, usedHand);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        if(context.getPlayer() instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.literal("右键空气打开GUI"),true);
        }
        return InteractionResult.SUCCESS;
    }
}
