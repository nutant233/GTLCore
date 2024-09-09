package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import lombok.Data;

import java.util.List;

@Data
public class Ae2PatternConflict {
    boolean conflict;
    List<String> matchPool;
    List<String> storagePool;
    Ae2Pattern ae2Pattern;


    public Ae2PatternConflict(boolean conflict, List<String> matchPool, List<String> storagePool, Ae2Pattern ae2Pattern) {
        this.conflict = conflict; // 程序不出错的情况下一定为true，仅供检验用
        this.matchPool = matchPool; // 冲突配方的材料池，包含物品和流体
        this.storagePool = storagePool; // 同电路同机器类型的所有配方的材料池，包含物品和流体
        this.ae2Pattern = ae2Pattern; // 冲突配方的对象，可以通过这个获取原recipe
    }

    public void exportToPrint(){
        System.out.printf("// Type : %s --------- CIRCUIT : %s //%n", ae2Pattern.recipe.getType(), ae2Pattern.CIRCUIT);
        System.out.println(">>材料:");
        Ae2AbilityHelper.getInputItemStacksFromRecipe(ae2Pattern.recipe).forEach(itemStack -> {
            System.out.println("材料:"+ Ae2AbilityHelper.getItemTranslatedName(itemStack) +"("+ Ae2AbilityHelper.getItemId(itemStack)+")");
        });
        Ae2AbilityHelper.getInputFluidStacksFromRecipe(ae2Pattern.recipe).forEach(fluidStack -> {
            System.out.println("材料:"+ Ae2AbilityHelper.getFluidTranslatedName(fluidStack)+"("+ Ae2AbilityHelper.getFluidId(fluidStack)+")");
        });
        System.out.println(">>成品:");
        Ae2AbilityHelper.getOutputItemStacksFromRecipe(ae2Pattern.recipe).forEach(itemStack -> {
            System.out.println("物品:"+ Ae2AbilityHelper.getItemTranslatedName(itemStack)+"("+ Ae2AbilityHelper.getItemId(itemStack)+")");
        });
        Ae2AbilityHelper.getOutputFluidStacksFromRecipe(ae2Pattern.recipe).forEach(fluidStack -> {
            System.out.println("流体:"+ Ae2AbilityHelper.getFluidTranslatedName(fluidStack)+"("+ Ae2AbilityHelper.getFluidId(fluidStack)+")");
        });
    }
}
