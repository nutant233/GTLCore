package org.gtlcore.gtlcore.common.data;

import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import net.minecraft.resources.ResourceLocation;

import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.createAndRegister;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;

@SuppressWarnings("unused")
public class GTLDimensionMarkers {

    static {
        GTRegistries.DIMENSION_MARKERS.unfreeze();
        REGISTRATE.creativeModeTab(() -> null);
    }

    public static final DimensionMarker MOON = createAndRegister(new ResourceLocation("ad_astra:moon"),
            1, new ResourceLocation("ad_astra:moon_stone"), "dimension.ad_astra.moon");
    public static final DimensionMarker MARS = createAndRegister(new ResourceLocation("ad_astra:mars"),
            2, new ResourceLocation("ad_astra:mars_stone"), "dimension.ad_astra.mars");
    public static final DimensionMarker VENUS = createAndRegister(new ResourceLocation("ad_astra:venus"),
            3, new ResourceLocation("ad_astra:venus_stone"), "dimension.ad_astra.venus");
    public static final DimensionMarker MERCURY = createAndRegister(new ResourceLocation("ad_astra:mercury"),
            3, new ResourceLocation("ad_astra:mercury_stone"), "dimension.ad_astra.mercury");
    public static final DimensionMarker GLACIO = createAndRegister(new ResourceLocation("ad_astra:glacio"),
            7, new ResourceLocation("ad_astra:glacio_stone"), "dimension.ad_astra.glacio");
    public static final DimensionMarker ANCIENT_WORLD = createAndRegister(new ResourceLocation("kubejs:ancient_world"),
            0, new ResourceLocation("kubejs:reactor_core"), "dimension.kubejs.ancient_world");
    public static final DimensionMarker TITAN = createAndRegister(new ResourceLocation("kubejs:titan"),
            6, new ResourceLocation("kubejs:titanstone"), "dimension.kubejs.titan");
    public static final DimensionMarker PLUTO = createAndRegister(new ResourceLocation("kubejs:pluto"),
            6, new ResourceLocation("kubejs:plutostone"), "dimension.kubejs.pluto");
    public static final DimensionMarker IO = createAndRegister(new ResourceLocation("kubejs:io"),
            5, new ResourceLocation("kubejs:iostone"), "dimension.kubejs.io");
    public static final DimensionMarker GANYMEDE = createAndRegister(new ResourceLocation("kubejs:ganymede"),
            5, new ResourceLocation("kubejs:ganymedestone"), "dimension.kubejs.ganymede");
    public static final DimensionMarker ENCELADUS = createAndRegister(new ResourceLocation("kubejs:enceladus"),
            6, new ResourceLocation("kubejs:enceladusstone"), "dimension.kubejs.enceladus");
    public static final DimensionMarker CERES = createAndRegister(new ResourceLocation("kubejs:ceres"),
            4, new ResourceLocation("kubejs:ceresstone"), "dimension.kubejs.ceres");
    public static final DimensionMarker BARNARDA = createAndRegister(new ResourceLocation("kubejs:barnarda"),
            8, new ResourceLocation("kubejs:barnarda_wood"), "dimension.kubejs.barnarda");
    public static final DimensionMarker CREATE = createAndRegister(new ResourceLocation("kubejs:create"),
            10, new ResourceLocation("kubejs:dimension_creation_casing"), "dimension.kubejs.create");
    public static final DimensionMarker VOID = createAndRegister(new ResourceLocation("kubejs:void"),
            0, new ResourceLocation("minecraft:obsidian"), "dimension.kubejs.void");
    public static final DimensionMarker FLAT = createAndRegister(new ResourceLocation("kubejs:flat"),
            0, new ResourceLocation("minecraft:crying_obsidian"), "dimension.kubejs.flat");


    public static void init() {
    }
}
