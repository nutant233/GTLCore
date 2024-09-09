package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import lombok.Data;
import org.gtlcore.gtlcore.GTLCore;

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

    public void exportToPrint() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n配方类型：").append(ae2Pattern.recipe.getType()).append(" --------- 电路：").append(ae2Pattern.CIRCUIT).append("\n>>材料：\n");
        Ae2AbilityHelper.getInputItemStacksFromRecipe(ae2Pattern.recipe).forEach(itemStack -> sb.append("物品：").append(Ae2AbilityHelper.getItemTranslatedName(itemStack)));
        Ae2AbilityHelper.getInputFluidStacksFromRecipe(ae2Pattern.recipe).forEach(fluidStack -> sb.append("流体：").append(Ae2AbilityHelper.getFluidTranslatedName(fluidStack)));
        sb.append("\n>>成品：\n");
        Ae2AbilityHelper.getOutputItemStacksFromRecipe(ae2Pattern.recipe).forEach(itemStack -> sb.append("物品：").append(Ae2AbilityHelper.getItemTranslatedName(itemStack)));
        Ae2AbilityHelper.getOutputFluidStacksFromRecipe(ae2Pattern.recipe).forEach(fluidStack -> sb.append("流体：").append(Ae2AbilityHelper.getFluidTranslatedName(fluidStack)));
        GTLCore.LOGGER.info(sb);
    }
}
