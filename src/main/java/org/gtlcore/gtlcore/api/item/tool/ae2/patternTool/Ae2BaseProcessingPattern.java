package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import appeng.crafting.pattern.AEProcessingPattern;
import appeng.crafting.pattern.EncodedPatternItem;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ae2BaseProcessingPattern {
    public final EncodedPatternItem patternItem;
    public final ItemStack patternStack;
    public final AEProcessingPattern patternDetail;
    public int scale;

    public List<Item> PATTERNIGNOREITEMS = new ArrayList<>(List.of(
            GTItems.INTEGRATED_CIRCUIT.asItem()
    ));

    public Ae2BaseProcessingPattern(int scale,
                                    ItemStack patternStack,
                                    ServerPlayer serverPlayer) {
        this.scale = scale;
        this.patternStack = patternStack;
        if (patternStack.getItem() instanceof EncodedPatternItem patternItem_1){
            this.patternItem = patternItem_1;
            if( patternItem_1.decode(patternStack,serverPlayer.level(),false)instanceof AEProcessingPattern processStack ){
                this.patternDetail = processStack;
            }else {
                throw new Error("Ae2BaseProcessingPattern requires a EncodedPatternItem 意外之内的输入：非处理样板");
            }
        }else {
            throw new Error("Ae2BaseProcessingPattern requires a EncodedPatternItem 意外之内的输入：非AE样板");
        }
    }
}
