package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import appeng.crafting.pattern.EncodedPatternItem;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.gtlcore.gtlcore.GTLCore;

import java.util.ArrayList;
import java.util.List;

public class Ae2BaseProcessingPattern {
    public ItemStack patternStack; // 样板itemStack
    public int scale; // scale是此样板相对于配方的倍数，不能乱改！！！
    public ServerPlayer serverPlayer;

    public List<Item> PATTERNIGNOREITEMS = new ArrayList<>(List.of(
            GTItems.INTEGRATED_CIRCUIT.asItem()
    ));

    public void useDefaultFilter(){
        AEProcessingPattern aeProcessingPattern = Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer);
        aeProcessingPattern.getSparseInputs();
        GenericStack[] sparseOutputs = aeProcessingPattern.getSparseOutputs();
    }

    public void setScale(int newScale) {
        try{
            int oldScale = this.scale;
            // 先除到一份
            patternStack = Ae2BaseProcessingPatternHelper.multiplyScale(
                    oldScale,
                    true,
                    Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer));
            // 乘到新的份数
            patternStack = Ae2BaseProcessingPatternHelper.multiplyScale(
                    newScale,
                    false,
                    Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer));
            // 更新份数
            this.scale = newScale;
        }catch(Exception e){
            GTLCore.LOGGER.error(e.getMessage());
        }
    }

    public ItemStack getPatternItemStack(){
        return this.patternStack;
    }

    public Ae2BaseProcessingPattern(int scale,
                                    ItemStack patternStack,
                                    ServerPlayer serverPlayer) {
        this.scale = scale;
        this.patternStack = patternStack;
        this.serverPlayer = serverPlayer;
//        Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer);
    }
}
