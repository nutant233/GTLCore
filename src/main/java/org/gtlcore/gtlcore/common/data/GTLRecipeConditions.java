package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.common.recipe.condition.GravityCondition;

import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public final class GTLRecipeConditions {

    private GTLRecipeConditions() {}

    public static final RecipeConditionType<GravityCondition> GRAVITY = GTRegistries.RECIPE_CONDITIONS.register("gravity",
            new RecipeConditionType<>(GravityCondition::new, GravityCondition.CODEC));

    public static void init() {}
}
