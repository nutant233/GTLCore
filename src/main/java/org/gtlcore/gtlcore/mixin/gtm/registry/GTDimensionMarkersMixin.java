package org.gtlcore.gtlcore.mixin.gtm.registry;

import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.common.data.GTDimensionMarkers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.END_MARKER;
import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.NETHER_MARKER;

@Mixin(GTDimensionMarkers.class)
public class GTDimensionMarkersMixin {

    @Shadow(remap = false)
    public static DimensionMarker createAndRegister(ResourceLocation dim, int tier, ResourceLocation itemKey, @Nullable String overrideName) {
        return null;
    }

    @Unique
    private static DimensionMarker gTLCore$createAndRegister(ResourceLocation dim, int tier, Supplier<ItemLike> supplier,
                                                             @Nullable String overrideName) {
        DimensionMarker marker = new DimensionMarker(tier, supplier, overrideName);
        marker.register(dim);
        return marker;
    }

    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci) {
        createAndRegister(new ResourceLocation("ad_astra:moon"),
                1, new ResourceLocation("ad_astra:moon_stone"), "dimension.ad_astra.moon");
        createAndRegister(new ResourceLocation("ad_astra:mars"),
                2, new ResourceLocation("ad_astra:mars_stone"), "dimension.ad_astra.mars");
        createAndRegister(new ResourceLocation("ad_astra:venus"),
                3, new ResourceLocation("ad_astra:venus_stone"), "dimension.ad_astra.venus");
        createAndRegister(new ResourceLocation("ad_astra:mercury"),
                3, new ResourceLocation("ad_astra:mercury_stone"), "dimension.ad_astra.mercury");
        createAndRegister(new ResourceLocation("ad_astra:glacio"),
                7, new ResourceLocation("ad_astra:glacio_stone"), "dimension.ad_astra.glacio");
        createAndRegister(new ResourceLocation("kubejs:ancient_world"),
                0, new ResourceLocation("gtlcore:reactor_core"), "dimension.kubejs.ancient_world");
        createAndRegister(new ResourceLocation("kubejs:titan"),
                6, new ResourceLocation("gtlcore:titanstone"), "dimension.kubejs.titan");
        createAndRegister(new ResourceLocation("kubejs:pluto"),
                6, new ResourceLocation("gtlcore:plutostone"), "dimension.kubejs.pluto");
        createAndRegister(new ResourceLocation("kubejs:io"),
                5, new ResourceLocation("gtlcore:iostone"), "dimension.kubejs.io");
        createAndRegister(new ResourceLocation("kubejs:ganymede"),
                5, new ResourceLocation("gtlcore:ganymedestone"), "dimension.kubejs.ganymede");
        createAndRegister(new ResourceLocation("kubejs:enceladus"),
                6, new ResourceLocation("gtlcore:enceladusstone"), "dimension.kubejs.enceladus");
        createAndRegister(new ResourceLocation("kubejs:ceres"),
                4, new ResourceLocation("gtlcore:ceresstone"), "dimension.kubejs.ceres");
        createAndRegister(new ResourceLocation("kubejs:barnarda"),
                8, new ResourceLocation("gtlcore:barnarda_wood"), "dimension.kubejs.barnarda");
        createAndRegister(new ResourceLocation("kubejs:create"),
                10, new ResourceLocation("gtlcore:dimension_creation_casing"), "dimension.kubejs.create");
        createAndRegister(new ResourceLocation("kubejs:void"),
                0, new ResourceLocation("minecraft:obsidian"), "dimension.kubejs.void");
        createAndRegister(new ResourceLocation("kubejs:flat"),
                0, new ResourceLocation("minecraft:crying_obsidian"), "dimension.kubejs.flat");
    }

    @Inject(method = "createAndRegister(Lnet/minecraft/resources/ResourceLocation;ILjava/util/function/Supplier;Ljava/lang/String;)Lcom/gregtechceu/gtceu/api/data/DimensionMarker;", at = @At("HEAD"), remap = false, cancellable = true)
    private static void createAndRegister(ResourceLocation dim, int tier, Supplier<ItemLike> supplier, @Nullable String overrideName, CallbackInfoReturnable<DimensionMarker> cir) {
        if (dim == Level.NETHER.location()) {
            cir.setReturnValue(gTLCore$createAndRegister(Level.NETHER.location(), 3,
                    () -> NETHER_MARKER, null));
        }
        if (dim == Level.END.location()) {
            cir.setReturnValue(gTLCore$createAndRegister(Level.END.location(), 6,
                    () -> END_MARKER, null));
        }
    }
}
