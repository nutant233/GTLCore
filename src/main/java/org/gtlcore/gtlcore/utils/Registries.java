package org.gtlcore.gtlcore.utils;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class Registries {

    public static Item getItem(String s) {
        return Objects.requireNonNullElse(ForgeRegistries.ITEMS.getValue(new ResourceLocation(s)), Items.BARRIER);
    }

    public static ItemStack getItemStack(String s, int a) {
        return new ItemStack(getItem(s), a);
    }

    public static Block getBlock(String s) {
        return Objects.requireNonNullElse(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(s)), Blocks.BARRIER);
    }

    public static Material getMaterial(String s) {
        return Objects.requireNonNullElse(GTMaterials.get(s), GTMaterials.UUMatter);
    }
}
