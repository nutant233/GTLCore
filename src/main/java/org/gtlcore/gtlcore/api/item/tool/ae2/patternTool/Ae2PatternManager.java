package org.gtlcore.gtlcore.api.item.tool.ae2.patternTool;

import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import org.gtlcore.gtlcore.GTLCore;

import java.util.*;
import java.util.stream.Stream;

public class Ae2PatternManager {
    ////////////////////////////////////////////////
    //  使用配方recipe自动生成的样板对象(工具类)的集
    //  一个Manager所管理的配方是一个机器的类型所能制造的配方
    ////////////////////////////////////////////////
    public List<Ae2Pattern> ae2Patterns;

    public Ae2PatternManager(List<GTRecipe> recipes) {
        this.ae2Patterns = new ArrayList<Ae2Pattern>();
        recipes.forEach(recipe -> ae2Patterns.add(new Ae2Pattern(recipe)));
    }

    public Ae2PatternManager(List<Ae2Pattern> ae2Patterns, boolean dummy) {
        this.ae2Patterns = ae2Patterns;
    }

    public static Ae2PatternConflict findConflict(Ae2Pattern ae2Pattern, List<Ae2Pattern> ae2Patterns){
        boolean conflict_k_14223 = false;
        ae2Patterns = ae2Patterns.stream().filter(ae2Pattern1 -> !ae2Pattern1.equals(ae2Pattern)).toList();
        List<String> matchPool= Stream.of(ae2Pattern.inputFluidIds, ae2Pattern.inputItemIds).flatMap(Collection::stream).toList();
        List<String> storagePool=new ArrayList<>();
        ae2Patterns
                .forEach(ae2Pattern1 -> {
                    storagePool.addAll(ae2Pattern1.inputFluidIds);
                    storagePool.addAll(ae2Pattern1.inputItemIds);
                });
        if(new HashSet<>(storagePool).containsAll(matchPool)){
            conflict_k_14223=true;
        }
        return new Ae2PatternConflict(conflict_k_14223,matchPool,storagePool,ae2Pattern);



    }

    public List<Ae2PatternConflict> useFindConflictForAll(Number circuit){
        //initialize
        List<Ae2PatternConflict> ae2PatternConflictList = new ArrayList<>();

        //preprocessData
        List<Ae2Pattern> ae2PatternList = this.ae2Patterns.stream()
                .filter(ae2Pattern -> (Objects.equals(ae2Pattern.CIRCUIT, circuit) || ae2Pattern.CIRCUIT.equals(0))) // 任何电路的机器都可以运行0号电路
                .toList();

        //useConflictProcessLogic
        ae2PatternList.forEach(ae2Pattern -> {
            Ae2PatternConflict ae2PatternConflict = findConflict(ae2Pattern, ae2PatternList);
            if(ae2PatternConflict.conflict){
                ae2PatternConflictList.add(ae2PatternConflict);
            }
        });

        return ae2PatternConflictList;
    }

    public void printAllObject(){
        this.ae2Patterns.forEach(ae2Pattern -> GTLCore.LOGGER.info(ae2Pattern.toString()));
    }
}
