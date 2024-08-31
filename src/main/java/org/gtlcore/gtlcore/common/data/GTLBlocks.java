package org.gtlcore.gtlcore.common.data;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.block.crafting.CraftingUnitBlock;
import appeng.block.crafting.ICraftingUnitType;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.crafting.CraftingBlockEntity;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.ActiveBlock;
import com.gregtechceu.gtceu.api.block.IFilterType;
import com.gregtechceu.gtceu.api.block.RendererBlock;
import com.gregtechceu.gtceu.api.item.RendererBlockItem;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.client.renderer.block.CTMModelRenderer;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.lowdragmc.lowdraglib.Platform;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import lombok.Getter;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.gtlcore.gtlcore.api.registries.GTLRegistration.REGISTRATE;

public class GTLBlocks {

    public static void init() {}

    static {
        REGISTRATE.creativeModeTab(() -> GTLCreativeModeTabs.GTL_CORE);
    }

    public enum CraftingUnitType implements ICraftingUnitType {

        STORAGE_1M(1, "1m_storage"),
        STORAGE_4M(4, "4m_storage"),
        STORAGE_16M(16, "16m_storage"),
        STORAGE_64M(64, "64m_storage"),
        STORAGE_256M(256, "256m_storage"),
        STORAGE_MAX(Integer.MAX_VALUE, "max_storage");

        private final int storageMb;

        @Getter
        private final String affix;

        CraftingUnitType(int storageMb, String affix) {
            this.storageMb = storageMb;
            this.affix = affix;
        }

        @Override
        public long getStorageBytes() {
            return 1024L * 1024 * storageMb;
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

    @SuppressWarnings("all")
    public static BlockEntry<ActiveBlock> createActiveCasing(String name, String baseModelPath) {
        String finalName = "%s".formatted(name);
        return REGISTRATE.block(finalName, p -> new ActiveBlock(p,
                        Platform.isClient() ? new CTMModelRenderer(GTCEu.id(baseModelPath)) : null,
                        Platform.isClient() ? new CTMModelRenderer(GTCEu.id("%s_active".formatted(baseModelPath))) : null))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    @SuppressWarnings("all")
    public static BlockEntry<Block> createTierCasings(String name, ResourceLocation texture,
                                                      Map<Integer, Supplier<Block>> map, int tier) {
        BlockEntry<Block> Block = REGISTRATE.block(name, p -> (Block) new RendererBlock(p,
                        Platform.isClient() ? new TextureOverrideRenderer(new ResourceLocation("block/cube_all"),
                                Map.of("all", texture)) : null) {

                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level,
                                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                        tooltip.add(Component.translatable("gtceu.casings.tier", tier));
                    }
                })
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
        REGISTRATE.setCreativeTab(Block, GTLCreativeModeTabs.GTL_CORE);
        map.put(tier, Block);
        return Block;
    }

    @SuppressWarnings("all")
    public static BlockEntry<Block> createActiveTierCasing(String name, String baseModelPath,
                                                           Map<Integer, Supplier<Block>> map, int tier) {
        BlockEntry<Block> Block = REGISTRATE.block("%s".formatted(name), p -> (Block) new ActiveBlock(p,
                        Platform.isClient() ? new CTMModelRenderer(GTCEu.id(baseModelPath)) : null,
                        Platform.isClient() ? new CTMModelRenderer(GTCEu.id("%s_active".formatted(baseModelPath))) : null) {

                    @Override
                    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level,
                                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
                        tooltip.add(Component.translatable("gtceu.casings.tier", tier));
                    }
                })
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_PICKAXE)
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
        REGISTRATE.setCreativeTab(Block, GTLCreativeModeTabs.GTL_CORE);
        map.put(tier, Block);
        return Block;
    }

    @SuppressWarnings("all")
    private static BlockEntry<Block> createCleanroomFilter() {
        var filterBlock = REGISTRATE.block(((IFilterType) GTLCleanroomFilterType.FILTER_CASING_LAW).getSerializedName(), p -> (Block) new RendererBlock(p,
                        Platform.isClient() ? new TextureOverrideRenderer(new ResourceLocation("block/cube_all"),
                                Map.of("all", GTCEu.id("block/casings/cleanroom/" + GTLCleanroomFilterType.FILTER_CASING_LAW))) : null))
                .initialProperties(() -> Blocks.IRON_BLOCK)
                .properties(properties -> properties.strength(2.0f, 8.0f).sound(SoundType.METAL)
                        .isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .tag(GTToolType.WRENCH.harvestTags.get(0), CustomTags.TOOL_TIERS[1])
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
        GTCEuAPI.CLEANROOM_FILTERS.put(GTLCleanroomFilterType.FILTER_CASING_LAW, filterBlock);
        return filterBlock;
    }

    public static final BlockEntry<Block> FILTER_CASING_LAW = createCleanroomFilter();

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
}
