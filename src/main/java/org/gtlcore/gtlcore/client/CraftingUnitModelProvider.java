package org.gtlcore.gtlcore.client;

import appeng.client.render.crafting.AbstractCraftingUnitModelProvider;
import appeng.client.render.crafting.CraftingCubeModel;
import appeng.client.render.crafting.LightBakedModel;
import appeng.core.AppEng;
import appeng.hooks.BuiltInModelHooks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class CraftingUnitModelProvider extends AbstractCraftingUnitModelProvider<GTLBlocks.CraftingUnitType> {

    private static final List<Material> MATERIALS = new ArrayList<>();

    private static final Material RING_CORNER = aeTexture("ring_corner");
    private static final Material RING_SIDE_HOR = aeTexture("ring_side_hor");
    private static final Material RING_SIDE_VER = aeTexture("ring_side_ver");
    private static final Material LIGHT_BASE = aeTexture("light_base");
    protected static final Material STORAGE_1M_LIGHT = texture("1m_storage_light");
    protected static final Material STORAGE_4M_LIGHT = texture("4m_storage_light");
    protected static final Material STORAGE_16M_LIGHT = texture("16m_storage_light");
    protected static final Material STORAGE_64M_LIGHT = texture("64m_storage_light");
    protected static final Material STORAGE_256M_LIGHT = texture("256m_storage_light");
    protected static final Material STORAGE_256G_LIGHT = texture("256g_storage_light");

    public CraftingUnitModelProvider(GTLBlocks.CraftingUnitType type) {
        super(type);
    }

    public TextureAtlasSprite getLightMaterial(Function<Material, TextureAtlasSprite> textureGetter) {
        return switch (this.type) {
            case STORAGE_1M -> textureGetter.apply(STORAGE_1M_LIGHT);
            case STORAGE_4M -> textureGetter.apply(STORAGE_4M_LIGHT);
            case STORAGE_16M -> textureGetter.apply(STORAGE_16M_LIGHT);
            case STORAGE_64M -> textureGetter.apply(STORAGE_64M_LIGHT);
            case STORAGE_256M -> textureGetter.apply(STORAGE_256M_LIGHT);
            case STORAGE_256G -> textureGetter.apply(STORAGE_256G_LIGHT);
        };
    }

    @Override
    public List<Material> getMaterials() {
        return Collections.unmodifiableList(MATERIALS);
    }

    @Override
    public BakedModel getBakedModel(Function<Material, TextureAtlasSprite> spriteGetter) {
        TextureAtlasSprite ringCorner = spriteGetter.apply(RING_CORNER);
        TextureAtlasSprite ringSideHor = spriteGetter.apply(RING_SIDE_HOR);
        TextureAtlasSprite ringSideVer = spriteGetter.apply(RING_SIDE_VER);
        TextureAtlasSprite lightBase = spriteGetter.apply(LIGHT_BASE);
        return new LightBakedModel(
                ringCorner,
                ringSideHor,
                ringSideVer,
                lightBase,
                getLightMaterial(spriteGetter));
    }

    private static Material texture(String name) {
        var material = new Material(InventoryMenu.BLOCK_ATLAS, GTLCore.id("block/crafting/" + name));
        MATERIALS.add(material);
        return material;
    }

    private static Material aeTexture(String name) {
        var material = new Material(InventoryMenu.BLOCK_ATLAS, AppEng.makeId("block/crafting/" + name));
        MATERIALS.add(material);
        return material;
    }

    public static void initCraftingUnitModels() {
        for (GTLBlocks.CraftingUnitType type : GTLBlocks.CraftingUnitType.values()) {
            BuiltInModelHooks.addBuiltInModel(
                    GTLCore.id("block/crafting/" + type.getAffix() + "_formed"),
                    new CraftingCubeModel(new CraftingUnitModelProvider(type)));
        }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(CraftingUnitModelProvider::setRenderLayer);
    }

    private static void setRenderLayer(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_1M.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_4M.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_16M.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_64M.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_256M.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GTLBlocks.CRAFTING_STORAGE_256G.get(), RenderType.cutout());
    }
}
