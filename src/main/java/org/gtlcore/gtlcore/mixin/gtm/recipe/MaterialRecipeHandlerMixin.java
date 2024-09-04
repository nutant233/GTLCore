package org.gtlcore.gtlcore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.generated.MaterialRecipeHandler;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.gregtechceu.gtceu.utils.GTUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Locale;
import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.DistilledWater;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Water;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.*;
import static org.gtlcore.gtlcore.common.data.GTLRecipeTypes.ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES;

@Mixin(MaterialRecipeHandler.class)
public abstract class MaterialRecipeHandlerMixin {

    @Shadow(remap = false)
    private static void processEBFRecipe(Material material, BlastProperty property, ItemStack output, Consumer<FinishedRecipe> provider) {
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public static void processDust(TagPrefix dustPrefix, Material mat, DustProperty property,
                                   Consumer<FinishedRecipe> provider) {
        String id = "%s_%s_".formatted(FormattingUtil.toLowerCaseUnder(dustPrefix.name),
                mat.getName().toLowerCase(Locale.ROOT));
        ItemStack dustStack = ChemicalHelper.get(dustPrefix, mat);
        OreProperty oreProperty = mat.hasProperty(PropertyKey.ORE) ? mat.getProperty(PropertyKey.ORE) : null;
        if (mat.hasProperty(PropertyKey.GEM)) {
            ItemStack gemStack = ChemicalHelper.get(gem, mat);

            if (mat.hasFlag(CRYSTALLIZABLE)) {
                AUTOCLAVE_RECIPES.recipeBuilder("autoclave_" + id + "_water")
                        .inputItems(dustStack)
                        .inputFluids(Water.getFluid(250))
                        .chancedOutput(gemStack, 7000, 1000)
                        .duration(1200).EUt(24)
                        .save(provider);

                AUTOCLAVE_RECIPES.recipeBuilder("autoclave_" + id + "_distilled")
                        .inputItems(dustStack)
                        .inputFluids(DistilledWater.getFluid(50))
                        .outputItems(gemStack)
                        .duration(600).EUt(24)
                        .save(provider);
            }

            if (!mat.hasFlag(EXPLOSIVE) && !mat.hasFlag(FLAMMABLE)) {
                IMPLOSION_RECIPES.recipeBuilder("implode_" + id + "_powderbarrel")
                        .inputItems(GTUtil.copyAmount(4, dustStack))
                        .outputItems(GTUtil.copyAmount(3, gemStack))
                        .chancedOutput(dust, GTMaterials.DarkAsh, 2500, 0)
                        .explosivesType(new ItemStack(GTBlocks.POWDERBARREL, 8))
                        .save(provider);

                IMPLOSION_RECIPES.recipeBuilder("implode_" + id + "_tnt")
                        .inputItems(GTUtil.copyAmount(4, dustStack))
                        .outputItems(GTUtil.copyAmount(3, gemStack))
                        .chancedOutput(dust, GTMaterials.DarkAsh, 2500, 0)
                        .explosivesAmount(4)
                        .save(provider);

                IMPLOSION_RECIPES.recipeBuilder("implode_" + id + "_dynamite")
                        .inputItems(GTUtil.copyAmount(4, dustStack))
                        .outputItems(GTUtil.copyAmount(3, gemStack))
                        .chancedOutput(dust, GTMaterials.DarkAsh, 2500, 0)
                        .explosivesType(GTItems.DYNAMITE.asStack(2))
                        .save(provider);

                IMPLOSION_RECIPES.recipeBuilder("implode_" + id + "_itnt")
                        .inputItems(GTUtil.copyAmount(4, dustStack))
                        .outputItems(GTUtil.copyAmount(3, gemStack))
                        .chancedOutput(dust, GTMaterials.DarkAsh, 2500, 0)
                        .explosivesType(new ItemStack(GTBlocks.INDUSTRIAL_TNT))
                        .save(provider);

                ELECTRIC_IMPLOSION_COMPRESSOR_RECIPES.recipeBuilder("electric_implode_" + id)
                        .inputItems(GTUtil.copyAmount(4, dustStack))
                        .outputItems(GTUtil.copyAmount(3, gemStack))
                        .save(provider);
            }

            if (oreProperty != null) {
                Material smeltingResult = oreProperty.getDirectSmeltResult();
                if (smeltingResult != null) {
                    VanillaRecipeHelper.addSmeltingRecipe(provider, id + "_ingot",
                            ChemicalHelper.getTag(dustPrefix, mat), ChemicalHelper.get(ingot, smeltingResult));
                }
            }

        } else if (mat.hasProperty(PropertyKey.INGOT)) {
            if (!mat.hasAnyOfFlags(FLAMMABLE, NO_SMELTING)) {

                boolean hasHotIngot = ingotHot.doGenerateItem(mat);
                ItemStack ingotStack = ChemicalHelper.get(hasHotIngot ? ingotHot : ingot, mat);
                if (ingotStack.isEmpty() && oreProperty != null) {
                    Material smeltingResult = oreProperty.getDirectSmeltResult();
                    if (smeltingResult != null) {
                        ingotStack = ChemicalHelper.get(ingot, smeltingResult);
                    }
                }
                int blastTemp = mat.getBlastTemperature();

                if (blastTemp <= 0) {
                    // smelting magnetic dusts is handled elsewhere
                    if (!mat.hasFlag(IS_MAGNETIC)) {
                        // do not register inputs by ore dict here. Let other mods register their own dust -> ingots
                        VanillaRecipeHelper.addSmeltingRecipe(provider, id + "_demagnetize_from_dust",
                                ChemicalHelper.getTag(dustPrefix, mat), ingotStack);
                    }
                } else {
                    IngotProperty ingotProperty = mat.getProperty(PropertyKey.INGOT);
                    BlastProperty blastProperty = mat.getProperty(PropertyKey.BLAST);

                    processEBFRecipe(mat, blastProperty, ingotStack, provider);

                    if (ingotProperty.getMagneticMaterial() != null) {
                        processEBFRecipe(ingotProperty.getMagneticMaterial(), blastProperty, ingotStack, provider);
                    }
                }
            }
        } else {
            if (mat.hasFlag(GENERATE_PLATE) && !mat.hasFlag(EXCLUDE_PLATE_COMPRESSOR_RECIPE)) {
                COMPRESSOR_RECIPES.recipeBuilder("compress_plate_" + id)
                        .inputItems(dustStack)
                        .outputItems(plate, mat)
                        .save(provider);
            }

            // Some Ores with Direct Smelting Results have neither ingot nor gem properties
            if (oreProperty != null) {
                Material smeltingResult = oreProperty.getDirectSmeltResult();
                if (smeltingResult != null) {
                    ItemStack ingotStack = ChemicalHelper.get(ingot, smeltingResult);
                    if (!ingotStack.isEmpty()) {
                        VanillaRecipeHelper.addSmeltingRecipe(provider, id + "_dust_to_ingot",
                                ChemicalHelper.getTag(dustPrefix, mat), ingotStack);
                    }
                }
            }
        }
    }
}
