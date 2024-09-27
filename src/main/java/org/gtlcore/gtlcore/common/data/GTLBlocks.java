package org.gtlcore.gtlcore.common.data;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.block.GTLFusionCasingBlock;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.IFilterType;
import com.gregtechceu.gtceu.api.block.IFusionCasingType;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.block.FusionCasingBlock;
import com.gregtechceu.gtceu.common.data.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.block.crafting.CraftingUnitBlock;
import appeng.block.crafting.ICraftingUnitType;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.crafting.CraftingBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.common.data.GTBlocks.ALL_FUSION_CASINGS;
import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class GTLBlocks {

    public static Map<Integer, Supplier<Block>> scmap = new HashMap<>();
    public static Map<Integer, Supplier<ActiveBlock>> sepmmap = new HashMap<>();
    public static Map<Integer, Supplier<Block>> calmap = new HashMap<>();

    public static void init() {
        for (int i = 1; i < 15; i++) {
            GTLBlocks.createTierCasings("component_assembly_line_casing_" + GTValues.VN[i].toLowerCase(),
                    GTLCore.id("block/casings/component_assembly_line/component_assembly_line_casing_" + GTValues.VN[i].toLowerCase()), calmap, i);
        }
    }

    static {
        REGISTRATE.creativeModeTab(() -> GTLCreativeModeTabs.GTL_CORE);
    }

    private static BlockEntry<CraftingUnitBlock> registerCraftingUnitBlock(int tier, CraftingUnitType Type) {
        return REGISTRATE
                .block(tier == -1 ? "max_storage" : tier + "m_storage",
                        p -> new CraftingUnitBlock(Type))
                .blockstate((ctx, provider) -> {
                    String formed = "block/crafting/" + ctx.getName() + "_formed";
                    String unformed = "block/crafting/" + ctx.getName();
                    provider.models().cubeAll(unformed, provider.modLoc("block/crafting/" + ctx.getName()));
                    provider.models().getBuilder(formed);
                    provider.getVariantBuilder(ctx.get())
                            .forAllStatesExcept(state -> {
                                boolean b = state.getValue(AbstractCraftingUnitBlock.FORMED);
                                return ConfiguredModel.builder()
                                        .modelFile(provider.models()
                                                .getExistingFile(provider.modLoc(b ? formed : unformed)))
                                        .build();
                            }, AbstractCraftingUnitBlock.POWERED);
                })
                .defaultLoot()
                .item(BlockItem::new)
                .model((ctx, provider) -> provider.withExistingParent(ctx.getName(),
                        provider.modLoc("block/crafting/" + ctx.getName())))
                .build()
                .register();
    }

    public enum CraftingUnitType implements ICraftingUnitType {

        STORAGE_1M(1, "1m_storage"),
        STORAGE_4M(4, "4m_storage"),
        STORAGE_16M(16, "16m_storage"),
        STORAGE_64M(64, "64m_storage"),
        STORAGE_256M(256, "256m_storage"),
        STORAGE_MAX(-1, "max_storage");

        private final int storageMb;

        @Getter
        private final String affix;

        CraftingUnitType(int storageMb, String affix) {
            this.storageMb = storageMb;
            this.affix = affix;
        }

        @Override
        public long getStorageBytes() {
            return storageMb == -1 ? Long.MAX_VALUE : 1024L * 1024 * storageMb;
        }

        @Override
        public int getAcceleratorThreads() {
            return 0;
        }

        public BlockEntry<CraftingUnitBlock> getDefinition() {
            return switch (this) {
                case STORAGE_1M -> CRAFTING_STORAGE_1M;
                case STORAGE_4M -> CRAFTING_STORAGE_4M;
                case STORAGE_16M -> CRAFTING_STORAGE_16M;
                case STORAGE_64M -> CRAFTING_STORAGE_64M;
                case STORAGE_256M -> CRAFTING_STORAGE_256M;
                case STORAGE_MAX -> CRAFTING_STORAGE_MAX;
            };
        }

        @Override
        public Item getItemFromType() {
            return getDefinition().asItem();
        }
    }

    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_1M = registerCraftingUnitBlock(1,
            GTLBlocks.CraftingUnitType.STORAGE_1M);
    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_4M = registerCraftingUnitBlock(4,
            GTLBlocks.CraftingUnitType.STORAGE_4M);
    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_16M = registerCraftingUnitBlock(16,
            GTLBlocks.CraftingUnitType.STORAGE_16M);
    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_64M = registerCraftingUnitBlock(64,
            GTLBlocks.CraftingUnitType.STORAGE_64M);
    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_256M = registerCraftingUnitBlock(256,
            GTLBlocks.CraftingUnitType.STORAGE_256M);
    public static final BlockEntry<CraftingUnitBlock> CRAFTING_STORAGE_MAX = registerCraftingUnitBlock(-1,
            GTLBlocks.CraftingUnitType.STORAGE_MAX);

    public static BlockEntityEntry<CraftingBlockEntity> CRAFTING_STORAGE = REGISTRATE
            .blockEntity("crafting_storage", CraftingBlockEntity::new)
            .validBlocks(
                    CRAFTING_STORAGE_1M,
                    CRAFTING_STORAGE_4M,
                    CRAFTING_STORAGE_16M,
                    CRAFTING_STORAGE_64M,
                    CRAFTING_STORAGE_256M,
                    CRAFTING_STORAGE_MAX)
            .onRegister(type -> {
                for (GTLBlocks.CraftingUnitType craftingUnitType : GTLBlocks.CraftingUnitType.values()) {
                    AEBaseBlockEntity.registerBlockEntityItem(type, craftingUnitType.getItemFromType());
                    craftingUnitType.getDefinition().get().setBlockEntity(CraftingBlockEntity.class, type, null, null);
                }
            })
            .register();

    @SuppressWarnings("all")
    public static BlockEntry<ActiveBlock> createActiveCasing(String name, String baseModelPath) {
        return REGISTRATE.block(name, ActiveBlock::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createActiveModel(GTLCore.id(baseModelPath)))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(prov.name(ctx), GTLCore.id(baseModelPath)))
                .build()
                .register();
    }

    @SuppressWarnings("all")
    public static BlockEntry<Block> createTierCasings(String name, ResourceLocation texture,
                                                      Map<Integer, Supplier<Block>> map, int tier) {
        BlockEntry<Block> Block = REGISTRATE.block(name, p -> (Block) new Block(p) {

            @Override
            public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level,
                                        @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                tooltip.add(Component.translatable("gtceu.casings.tier", tier));
            }
        })
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.cubeAllModel(name, texture))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
        REGISTRATE.setCreativeTab(Block, GTLCreativeModeTabs.GTL_CORE);
        map.put(tier, Block);
        return Block;
    }

    @SuppressWarnings("all")
    public static BlockEntry<ActiveBlock> createActiveTierCasing(String name, String baseModelPath,
                                                                 Map<Integer, Supplier<ActiveBlock>> map, int tier) {
        BlockEntry<ActiveBlock> Block = REGISTRATE.block("%s".formatted(name), p -> (ActiveBlock) new ActiveBlock(p) {

            @Override
            public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level,
                                        @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                tooltip.add(Component.translatable("gtceu.casings.tier", tier));
            }
        })
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.createActiveModel(GTLCore.id(baseModelPath)))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .model((ctx, prov) -> prov.withExistingParent(prov.name(ctx), GTLCore.id(baseModelPath)))
                .build()
                .register();
        REGISTRATE.setCreativeTab(Block, GTLCreativeModeTabs.GTL_CORE);
        map.put(tier, Block);
        return Block;
    }

    @SuppressWarnings("all")
    private static BlockEntry<Block> createCleanroomFilter(IFilterType filterType) {
        var filterBlock = REGISTRATE.block(filterType.getSerializedName(), Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(properties -> properties.strength(2.0f, 8.0f).sound(SoundType.METAL)
                        .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), CustomTags.TOOL_TIERS[1])
                .item(BlockItem::new)
                .build()
                .register();
        GTCEuAPI.CLEANROOM_FILTERS.put(filterType, filterBlock);
        return filterBlock;
    }

    @SuppressWarnings("all")
    public static BlockEntry<Block> createCasingBlock(String name, ResourceLocation texture) {
        return REGISTRATE.block(name, Block::new)
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(GTModels.cubeAllModel(name, texture))
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    @SuppressWarnings("all")
    private static BlockEntry<FusionCasingBlock> createFusionCasing(IFusionCasingType casingType) {
        BlockEntry<FusionCasingBlock> casingBlock = REGISTRATE
                .block(casingType.getSerializedName(), p -> (FusionCasingBlock) new GTLFusionCasingBlock(p, casingType))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(properties -> properties.strength(5.0f, 10.0f).sound(SoundType.METAL))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate((ctx, prov) -> {
                    ActiveBlock block = ctx.getEntry();
                    ModelFile inactive = prov.models().getExistingFile(GTLCore.id(casingType.getSerializedName()));
                    ModelFile active = prov.models().getExistingFile(GTLCore.id(casingType.getSerializedName()).withSuffix("_active"));
                    prov.getVariantBuilder(block).partialState().with(ActiveBlock.ACTIVE, false).modelForState().modelFile(inactive).addModel().partialState().with(ActiveBlock.ACTIVE, true).modelForState().modelFile(active).addModel();
                })
                .tag(GTToolType.WRENCH.harvestTags.get(0), CustomTags.TOOL_TIERS[casingType.getHarvestLevel()])
                .item(BlockItem::new)
                .build()
                .register();
        ALL_FUSION_CASINGS.put(casingType, casingBlock);
        return casingBlock;
    }

    @SuppressWarnings("all")
    private static BlockEntry<Block> createHermeticCasing(int tier) {
        String tierName = GTValues.VN[tier].toLowerCase(Locale.ROOT);
        return REGISTRATE
                .block("%s_hermetic_casing".formatted(tierName), Block::new)
                .lang("Hermetic Casing %s".formatted(GTValues.LVT[tier]))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(BlockItem::new)
                .build()
                .register();
    }

    public static final BlockEntry<Block> HERMETIC_CASING_UEV = createHermeticCasing(GTValues.UEV);
    public static final BlockEntry<Block> HERMETIC_CASING_UIV = createHermeticCasing(GTValues.UIV);
    public static final BlockEntry<Block> HERMETIC_CASING_UXV = createHermeticCasing(GTValues.UXV);
    public static final BlockEntry<Block> HERMETIC_CASING_OpV = createHermeticCasing(GTValues.OpV);

    public static final BlockEntry<FusionCasingBlock> FUSION_CASING_MK4 = createFusionCasing(
            GTLFusionCasingBlock.CasingType.FUSION_CASING_MK4);
    public static final BlockEntry<FusionCasingBlock> FUSION_CASING_MK5 = createFusionCasing(
            GTLFusionCasingBlock.CasingType.FUSION_CASING_MK5);

    public static final BlockEntry<ActiveBlock> ADVANCED_FUSION_COIL = createActiveCasing("advanced_fusion_coil",
            "block/variant/advanced_fusion_coil");
    public static final BlockEntry<ActiveBlock> FUSION_COIL_MK2 = createActiveCasing("fusion_coil_mk2",
            "block/variant/fusion_coil_mk2");
    public static final BlockEntry<ActiveBlock> IMPROVED_SUPERCONDUCTOR_COIL = createActiveCasing(
            "improved_superconductor_coil", "block/variant/improved_superconductor_coil");
    public static final BlockEntry<ActiveBlock> COMPRESSED_FUSION_COIL = createActiveCasing("compressed_fusion_coil",
            "block/variant/compressed_fusion_coil");
    public static final BlockEntry<ActiveBlock> ADVANCED_COMPRESSED_FUSION_COIL = createActiveCasing(
            "advanced_compressed_fusion_coil", "block/variant/advanced_compressed_fusion_coil");
    public static final BlockEntry<ActiveBlock> COMPRESSED_FUSION_COIL_MK2_PROTOTYPE = createActiveCasing(
            "compressed_fusion_coil_mk2_prototype", "block/variant/compressed_fusion_coil_mk2_prototype");
    public static final BlockEntry<ActiveBlock> COMPRESSED_FUSION_COIL_MK2 = createActiveCasing(
            "compressed_fusion_coil_mk2", "block/variant/compressed_fusion_coil_mk2");

    public static final BlockEntry<Block> FILTER_CASING_LAW = createCleanroomFilter(
            GTLCleanroomFilterType.FILTER_CASING_LAW);

    public static final BlockEntry<Block> CASING_SUPERCRITICAL_TURBINE = createCasingBlock(
            "supercritical_turbine_casing", GTLCore.id("block/supercritical_turbine_casing"));

    public static final BlockEntry<ActiveBlock> POWER_CORE = createActiveCasing("power_core",
            "block/variant/hyper_core");
    public static final BlockEntry<ActiveBlock> HYPER_CORE = createActiveCasing("hyper_core",
            "block/variant/hyper_core");
    public static final BlockEntry<ActiveBlock> SUPER_COMPUTATION_COMPONENT = createActiveCasing(
            "super_computation_component", "block/variant/super_computation_component");
    public static final BlockEntry<ActiveBlock> SUPER_COOLER_COMPONENT = createActiveCasing("super_cooler_component",
            "block/variant/super_cooler_component");
    public static final BlockEntry<ActiveBlock> SPACETIMECONTINUUMRIPPER = createActiveCasing(
            "spacetimecontinuumripper", "block/variant/spacetimecontinuumripper");
    public static final BlockEntry<ActiveBlock> SPACETIMEBENDINGCORE = createActiveCasing("spacetimebendingcore",
            "block/variant/spacetimebendingcore");
    public static final BlockEntry<ActiveBlock> QFT_COIL = createActiveCasing("qft_coil", "block/variant/qft_coil");
    public static final BlockEntry<ActiveBlock> FISSION_FUEL_ASSEMBLY = createActiveCasing("fission_fuel_assembly",
            "block/variant/fission_fuel_assembly");
    public static final BlockEntry<ActiveBlock> COOLER = createActiveCasing("cooler", "block/variant/cooler");
    public static final BlockEntry<ActiveBlock> ADVANCED_ASSEMBLY_LINE_UNIT = createActiveCasing(
            "advanced_assembly_line_unit", "block/variant/advanced_assembly_line_unit");
    public static final BlockEntry<ActiveBlock> SPACE_ELEVATOR_SUPPORT = createActiveCasing("space_elevator_support",
            "block/variant/space_elevator_support");

    public static final BlockEntry<Block> STELLAR_CONTAINMENT_CASING = GTLBlocks.createTierCasings(
            "stellar_containment_casing", GTLCore.id("block/stellar_containment_casing"), scmap, 1);
    public static final BlockEntry<Block> ADVANCED_STELLAR_CONTAINMENT_CASING = GTLBlocks.createTierCasings(
            "advanced_stellar_containment_casing", GTLCore.id("block/stellar_containment_casing"),
            scmap, 2);
    public static final BlockEntry<Block> ULTIMATE_STELLAR_CONTAINMENT_CASING = GTLBlocks.createTierCasings(
            "ultimate_stellar_containment_casing", GTLCore.id("block/stellar_containment_casing"),
            scmap, 3);

    public static final BlockEntry<ActiveBlock> POWER_MODULE = GTLBlocks.createActiveTierCasing("power_module",
            "block/variant/power_module", sepmmap, 1);
    public static final BlockEntry<ActiveBlock> POWER_MODULE_2 = GTLBlocks.createActiveTierCasing("power_module_2",
            "block/variant/power_module", sepmmap, 2);
    public static final BlockEntry<ActiveBlock> POWER_MODULE_3 = GTLBlocks.createActiveTierCasing("power_module_3",
            "block/variant/power_module", sepmmap, 3);
    public static final BlockEntry<ActiveBlock> POWER_MODULE_4 = GTLBlocks.createActiveTierCasing("power_module_4",
            "block/variant/power_module", sepmmap, 4);
    public static final BlockEntry<ActiveBlock> POWER_MODULE_5 = GTLBlocks.createActiveTierCasing("power_module_5",
            "block/variant/power_module", sepmmap, 5);
}
