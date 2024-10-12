package org.gtlcore.gtlcore.common.block;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLMaterials;

import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum CoilType implements StringRepresentable, ICoilType {

    URUIUM("uruium", 273, 1, 1, GTLMaterials.Uruium, GTLCore.id("block/coil/uruium_coil_block")),
    ABYSSALALLOY("abyssalalloy", 12600, 16, 8, GTLMaterials.AbyssalAlloy, GTLCore.id("block/coil/abyssalalloy_coil_block")),
    TITANSTEEL("titansteel", 14400, 32, 8, GTLMaterials.TitanSteel, GTLCore.id("block/coil/titansteel_coil_block")),
    ADAMANTINE("adamantine", 16200, 32, 8, GTLMaterials.Adamantine, GTLCore.id("block/coil/adamantine_coil_block")),
    NAQUADRIATICTARANIUM("naquadriatictaranium", 18900, 64, 8, GTLMaterials.NaquadriaticTaranium, GTLCore.id("block/coil/naquadriatictaranium_coil_block")),
    STARMETAL("starmetal", 21600, 64, 8, GTLMaterials.Starmetal, GTLCore.id("block/coil/starmetal_coil_block")),
    INFINITY("infinity", 36000, 128, 9, GTLMaterials.Infinity, GTLCore.id("block/coil/infinity_coil_block")),
    HYPOGEN("hypogen", 62000, 256, 9, GTLMaterials.Hypogen, GTLCore.id("block/coil/hypogen_coil_block")),
    ETERNITY("eternity", 96000, 512, 9, GTLMaterials.Eternity, GTLCore.id("block/coil/eternity_coil_block"));

    private final String name;
    private final int coilTemperature;
    private final int level;
    private final int energyDiscount;
    private final Material material;
    private final ResourceLocation texture;

    CoilType(String name, int coilTemperature, int level, int energyDiscount, Material material,
             ResourceLocation texture) {
        this.name = name;
        this.coilTemperature = coilTemperature;
        this.level = level;
        this.energyDiscount = energyDiscount;
        this.material = material;
        this.texture = texture;
    }

    public int getTier() {
        return this.ordinal();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    @NotNull
    public String getSerializedName() {
        return name;
    }
}
