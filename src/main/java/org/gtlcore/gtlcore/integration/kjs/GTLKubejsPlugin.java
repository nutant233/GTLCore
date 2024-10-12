package org.gtlcore.gtlcore.integration.kjs;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.api.machine.multiblock.GTLCleanroomType;
import org.gtlcore.gtlcore.common.data.*;
import org.gtlcore.gtlcore.common.recipe.condition.GravityCondition;
import org.gtlcore.gtlcore.utils.Registries;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;

public class GTLKubejsPlugin extends KubeJSPlugin {

    @Override
    public void registerClasses(ScriptType type, ClassFilter filter) {
        super.registerClasses(type, filter);
        filter.allow("org.gtlcore.gtlcore");
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);

        event.add("GTLCore", GTLCore.class);
        event.add("GTLMaterials", GTLMaterials.class);
        event.add("GTLBlocks", GTLBlocks.class);
        event.add("GTLItems", GTLItems.class);
        event.add("GTLMachines", GTLMachines.class);
        event.add("GTLCleanroomType", GTLCleanroomType.class);
        event.add("Registries", Registries.class);
        event.add("GravityCondition", GravityCondition.class);
    }
}
