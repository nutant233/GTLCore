package org.gtlcore.gtlcore.common.block;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.IFusionCasingType;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.block.FusionCasingBlock;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.jetbrains.annotations.NotNull;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;

public class GTLFusionCasingBlock extends FusionCasingBlock {

    public GTLFusionCasingBlock(Properties properties, IFusionCasingType casingType) {
        super(properties, casingType);
    }

    public static Block getCompressedCoilState(int tier) {
        return switch (tier) {
            case LuV -> GTLBlocks.IMPROVED_SUPERCONDUCTOR_COIL.get();
            case ZPM -> GTLBlocks.COMPRESSED_FUSION_COIL.get();
            case UV -> GTLBlocks.ADVANCED_COMPRESSED_FUSION_COIL.get();
            case UHV -> GTLBlocks.COMPRESSED_FUSION_COIL_MK2_PROTOTYPE.get();
            default -> GTLBlocks.COMPRESSED_FUSION_COIL_MK2.get();
        };
    }

    public static Block getCoilState(int tier) {
        return tier == UHV ? GTLBlocks.ADVANCED_FUSION_COIL.get() : GTLBlocks.FUSION_COIL_MK2.get();
    }

    public static Block getFrameState(int tier) {
        return switch (tier) {
            case LuV -> GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.NaquadahAlloy).get();
            case ZPM -> GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Duranium).get();
            case UV -> GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Naquadria).get();
            case UHV -> GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Trinium).get();
            default -> GTBlocks.MATERIAL_BLOCKS.get(TagPrefix.frameGt, GTMaterials.Neutronium).get();
        };
    }

    public static Block getCasingState(int tier) {
        return switch (tier) {
            case LuV -> FUSION_CASING.get();
            case ZPM -> FUSION_CASING_MK2.get();
            case UV -> FUSION_CASING_MK3.get();
            case UHV -> GTLBlocks.FUSION_CASING_MK4.get();
            default -> GTLBlocks.FUSION_CASING_MK5.get();
        };
    }

    public static IFusionCasingType getCasingType(int tier) {
        return switch (tier) {
            case LuV -> FusionCasingBlock.CasingType.FUSION_CASING;
            case ZPM -> FusionCasingBlock.CasingType.FUSION_CASING_MK2;
            case UV -> FusionCasingBlock.CasingType.FUSION_CASING_MK3;
            case UHV -> GTLFusionCasingBlock.CasingType.FUSION_CASING_MK4;
            default -> GTLFusionCasingBlock.CasingType.FUSION_CASING_MK5;
        };
    }

    public enum CasingType implements IFusionCasingType, StringRepresentable {

        FUSION_CASING_MK4("fusion_casing_mk4", 3),
        FUSION_CASING_MK5("fusion_casing_mk5", 3);

        private final String name;
        @Getter
        private final int harvestLevel;

        CasingType(String name, int harvestLevel) {
            this.name = name;
            this.harvestLevel = harvestLevel;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }

        @Override
        public ResourceLocation getTexture() {
            return GTCEu.id("block/casings/fusion/%s".formatted(this.name));
        }
    }
}
