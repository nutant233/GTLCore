package org.gtlcore.gtlcore.common.recipe.condition;

import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import lombok.NoArgsConstructor;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@NoArgsConstructor
public class OrbitCondition extends RecipeCondition {
    public final static OrbitCondition INSTANCE = new OrbitCondition();

    @Override
    public String getType() {
        return "orbit";
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("gtlcore.condition.space");
    }

    @Override
    public boolean test(@NotNull GTRecipe recipe, @NotNull RecipeLogic recipeLogic) {
        return Objects.requireNonNull(recipeLogic.getMachine().getLevel()).kjs$getDimension().toString().contains("_orbit");
    }

    @Override
    public RecipeCondition createTemplate() {
        return new OrbitCondition();
    }
}
