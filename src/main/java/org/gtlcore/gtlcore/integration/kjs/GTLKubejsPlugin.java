package org.gtlcore.gtlcore.integration.kjs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import org.gtlcore.gtlcore.api.data.chemical.material.info.GTLMaterialFlags;
import org.gtlcore.gtlcore.api.data.chemical.material.info.GTLMaterialIconSet;
import org.gtlcore.gtlcore.api.item.tool.GTLToolType;
import org.gtlcore.gtlcore.api.machine.multiblock.GTLCleanroomType;
import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.CoilWorkableElectricMultipleRecipesMultiblockMachine;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.WorkableElectricParallelHatchMultipleRecipesMachine;
import org.gtlcore.gtlcore.common.recipe.condition.OrbitCondition;
import org.gtlcore.gtlcore.utils.MachineIO;
import org.gtlcore.gtlcore.utils.Registries;
import org.gtlcore.gtlcore.utils.TextUtil;

public class GTLKubejsPlugin extends KubeJSPlugin {
    @Override
    public void registerClasses(ScriptType type, ClassFilter filter) {
        super.registerClasses(type, filter);
        filter.allow("org.gtlcore.gtlcore");
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);

        event.add("GTLMaterials", GTLMaterials.class);
        event.add("GTLBlocks", GTLBlocks.class);
        event.add("GTLItems", GTLItems.class);
        event.add("GTLMachines", GTLMachines.class);
        event.add("GTLRecipeTypes", GTLRecipeTypes.class);
        event.add("GTLRecipeModifiers", GTLRecipeModifiers.class);
        event.add("GTLCleanroomType", GTLCleanroomType.class);
        event.add("GTLToolType", GTLToolType.class);
        event.add("GTLSoundEntries", GTLSoundEntries.class);
        event.add("GTLMaterialIconSet", GTLMaterialIconSet.class);
        event.add("GTLMaterialFlags", GTLMaterialFlags.class);
        event.add("MachineIO", MachineIO.class);
        event.add("TextUtil", TextUtil.class);
        event.add("Registries", Registries.class);
        event.add("CoilWorkableElectricMultipleRecipesMultiblockMachine", CoilWorkableElectricMultipleRecipesMultiblockMachine.class);
        event.add("WorkableElectricParallelHatchMultipleRecipesMachine", WorkableElectricParallelHatchMultipleRecipesMachine.class);
        event.add("OrbitCondition", OrbitCondition.class);
    }
}
