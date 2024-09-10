package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import appeng.api.crafting.PatternDetailsHelper;
import appeng.api.stacks.GenericStack;
import appeng.crafting.pattern.AEProcessingPattern;
import appeng.crafting.pattern.EncodedPatternItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ae2BaseProcessingPatternHelper {
    public static ItemStack multiplyScale(int scale, boolean div, AEProcessingPattern patternDetail){
        var input = patternDetail.getSparseInputs();
        var output = patternDetail.getOutputs();
        if (checkModify(input, scale, div) && checkModify(output, scale, div)) {
            var mulInput = new GenericStack[input.length];
            var mulOutput = new GenericStack[output.length];
            modifyStacks(input, mulInput, scale, div);
            modifyStacks(output, mulOutput, scale, div);
            return PatternDetailsHelper.encodeProcessingPattern(mulInput, mulOutput);
        }
        throw new Error("内部错误：无法整除 或 乘数过大");
    }

    public static AEProcessingPattern decodeToAEProcessingPattern(ItemStack patternStack, ServerPlayer serverPlayer){
        if (patternStack.getItem() instanceof EncodedPatternItem patternItem_1){
            if(patternItem_1.decode(patternStack,serverPlayer.level(),false)instanceof AEProcessingPattern processStack ){
                return processStack;
            }else {
                throw new Error("Ae2BaseProcessingPattern requires a EncodedPatternItem 意外之内的输入：非处理样板");
            }
        }else {
            throw new Error("Ae2BaseProcessingPattern requires a EncodedPatternItem 意外之内的输入：非AE样板");
        }
    }

    public static List<GenericStack> transGenericStackArrayToList(GenericStack[] genericStackArray){
        return new ArrayList<>(Arrays.asList(genericStackArray));
    }

    public static GenericStack[] transGenericStackListToArray(List<GenericStack> genericStackList) {
        GenericStack[] genericStackArrayList = new GenericStack[genericStackList.size()];
        return genericStackList.toArray(genericStackArrayList);
    }

    private static boolean checkModify(GenericStack[] stacks, int scale, boolean div) {
        if (div) {
            for (var stack : stacks) {
                if (stack != null) {
                    if (stack.amount() % scale != 0) {
                        return false;
                    }
                }
            }
        } else {
            for (var stack : stacks) {
                if (stack != null) {
                    long upper = 99999999L * stack.what().getAmountPerUnit();
                    if (stack.amount() * scale > upper) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void modifyStacks(GenericStack[] stacks, GenericStack[] des, int scale, boolean div) {
        for (int i = 0; i < stacks.length; i ++) {
            if (stacks[i] != null) {
                long amt = div ? stacks[i].amount() / scale : stacks[i].amount() * scale;
                des[i] = new GenericStack(stacks[i].what(), amt);
            }
        }
    }
}
