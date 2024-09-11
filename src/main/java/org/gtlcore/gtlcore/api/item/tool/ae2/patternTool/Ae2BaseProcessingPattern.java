package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;
import org.gtlcore.gtlcore.GTLCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ae2BaseProcessingPattern {
    public ItemStack patternStack; // 样板itemStack
    public int scale; // scale是此样板相对于配方的倍数，不能乱改！！！
    public ServerPlayer serverPlayer;
    /*
        此处一个对象就是一个样板，因此过滤器决定某些物品/流体会不会在此样板中出现
     */
    public List<Item> DefaultBlackItem = new ArrayList<>(List.of(
            GTItems.INTEGRATED_CIRCUIT.asItem()
    ));

    public void setDefaultFilter(){
        // 解码，转换为list方便操作
        AEProcessingPattern aeProcessingPattern = Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer);
        List<GenericStack> inputGenericStacks = Ae2BaseProcessingPatternHelper.transGenericStackArrayToList(aeProcessingPattern.getSparseInputs());
        List<GenericStack> outputGenericStacks = Ae2BaseProcessingPatternHelper.transGenericStackArrayToList(aeProcessingPattern.getSparseOutputs());


        inputGenericStacks = Ae2BaseProcessingPatternHelper.GenericStackListBlackFilter(inputGenericStacks, DefaultBlackItem);
        outputGenericStacks = Ae2BaseProcessingPatternHelper.GenericStackListBlackFilter(outputGenericStacks, DefaultBlackItem);


        // 重新编码，并更新此对象
        patternStack = PatternDetailsHelper.encodeProcessingPattern(
                Ae2BaseProcessingPatternHelper.transGenericStackListToArray(inputGenericStacks),
                Ae2BaseProcessingPatternHelper.transGenericStackListToArray(outputGenericStacks)
        );
    }



    public void setScale(int newScale) {
        try{
            int oldScale = this.scale;
            ItemStack oldPatternStack = this.patternStack;
            ItemStack divedPatternStack;
            ItemStack newPatternStack;
            // 先除到一份
            divedPatternStack = Ae2BaseProcessingPatternHelper.multiplyScale(
                    oldScale,
                    true,
                    Objects.requireNonNull(Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(oldPatternStack, serverPlayer)));
            if (divedPatternStack != null && !divedPatternStack.isEmpty()) {
                // 乘到新的份数
                newPatternStack = Ae2BaseProcessingPatternHelper.multiplyScale(
                        newScale,
                        false,
                        Objects.requireNonNull(Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(divedPatternStack, serverPlayer)));
                if (newPatternStack != null && !newPatternStack.isEmpty()) {
                    // 更新份数
                    this.patternStack = newPatternStack;
                    this.scale = newScale;
                }
            }
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

        // 不获取返回值，仅检测是否可解码
        Ae2BaseProcessingPatternHelper.decodeToAEProcessingPattern(patternStack,serverPlayer);

        try {
            for (ItemEntry<Item> shapeMold : GTItems.SHAPE_MOLDS) {
                if(shapeMold!=null) {
                    this.DefaultBlackItem.add(shapeMold.asItem());
                }
            }
            for (ItemEntry<Item> shapeMold : GTItems.SHAPE_EXTRUDERS) {
                if(shapeMold!=null) {
                    this.DefaultBlackItem.add(shapeMold.asItem());
                }
            }
        } catch (Exception e) {
            GTLCore.LOGGER.error(e.getMessage());
        }
    }
}
