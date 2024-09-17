package org.gtlcore.gtlcore.mixin.ldlib;

import com.lowdragmc.lowdraglib.gui.ingredient.IRecipeIngredientSlot;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.jei.IGui2IDrawable;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.jei.ModularUIRecipeCategory;
import com.lowdragmc.lowdraglib.jei.ModularWrapper;
import com.lowdragmc.lowdraglib.utils.Position;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

/**
 * @author EasterFG on 2024/9/15
 */
@Mixin(ModularUIRecipeCategory.class)
public abstract class ModularUIRecipeCategoryMixin<T extends ModularWrapper<?>> implements IRecipeCategory<T> {
    @Shadow(remap = false)
    private static IRecipeSlotBuilder addJEISlot(IRecipeLayoutBuilder builder, IRecipeIngredientSlot slot, RecipeIngredientRole role, int x, int y) {
        return null;
    }

    @Shadow(remap = false)
    private RecipeIngredientRole mapToRole(IngredientIO ingredientIO) {
        return null;
    }

    /**
     * @author liansishen
     * @reason 修复多方快结构无法写入样板的问题, 参见
     */
    @SuppressWarnings("all")
    @Overwrite(remap = false)
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, T wrapper, @NotNull IFocusGroup focuses) {
        wrapper.setRecipeWidget(0, 0);
        List<Widget> flatVisibleWidgetCollection = wrapper.modularUI.getFlatWidgetCollection();
        for (Widget widget : flatVisibleWidgetCollection) {
            if (widget instanceof IRecipeIngredientSlot slot) {
                var role = mapToRole(slot.getIngredientIO());
                if (widget.getParent() instanceof DraggableScrollableWidgetGroup) {
                    // don't add the JEI widget at all if we have a draggable group, let the draggable widget handle it instead.
                    if (role == null) {
                        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addIngredientsUnsafe(slot.getXEIIngredients());
                        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredientsUnsafe(slot.getXEIIngredients());
                        // draw in an empty widget
                        builder.addSlotToWidget(RecipeIngredientRole.INPUT, (builder1, recipe, slots) -> {
                        }).addIngredientsUnsafe(slot.getXEIIngredients());
                        builder.addSlotToWidget(RecipeIngredientRole.OUTPUT, (builder1, recipe, slots) -> {
                        }).addIngredientsUnsafe(slot.getXEIIngredients());
                    } else {
                        builder.addInvisibleIngredients(role).addIngredientsUnsafe(slot.getXEIIngredients());
                        builder.addSlotToWidget(RecipeIngredientRole.INPUT, (builder1, recipe, slots) -> {
                        }).addIngredientsUnsafe(slot.getXEIIngredients());
                    }
                    continue;
                }
                Position pos = widget.getPosition();
                IRecipeSlotBuilder slotBuilder;
                if (role == null) { // both
                    addJEISlot(builder, slot, RecipeIngredientRole.INPUT, pos.x, pos.y);
                    slotBuilder = addJEISlot(builder, slot, RecipeIngredientRole.OUTPUT, pos.x, pos.y);
                } else {
                    slotBuilder = addJEISlot(builder, slot, role, pos.x, pos.y);
                }
                int width = widget.getSize().width;
                int height = widget.getSize().height;

                slotBuilder.setBackground(IGui2IDrawable.toDrawable(widget.getBackgroundTexture(), width, height), -1, -1);
                slotBuilder.setOverlay(IGui2IDrawable.toDrawable(widget.getOverlay(), width, height), -1, -1);
                widget.setActive(false);
                widget.setVisible(false);
                if (slot instanceof com.lowdragmc.lowdraglib.gui.widget.SlotWidget slotW) {
                    slotW.setDrawHoverOverlay(false).setDrawHoverTips(false);
                } else if (slot instanceof com.lowdragmc.lowdraglib.gui.widget.TankWidget tankW) {
                    tankW.setDrawHoverOverlay(false).setDrawHoverTips(false);
                    slotBuilder.setFluidRenderer(1, false, width - 2, height - 2);
                }
            }
        }
    }
}
