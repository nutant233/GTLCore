package org.gtlcore.gtlcore.client.renderer.machine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.client.renderer.machine.IControllerRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.lowdragmc.lowdraglib.client.bakedpipeline.FaceQuad;
import com.lowdragmc.lowdraglib.client.model.ModelFactory;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;
import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.client.ClientUtil;
import org.gtlcore.gtlcore.common.machine.multiblock.electric.HarmonyMachine;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.List;
import java.util.function.Consumer;

public class EyeOfHarmonyRenderer extends WorkableCasingMachineRenderer implements IControllerRenderer {

    private static final ResourceLocation SPACE_MODEL = GTLCore.id("obj/space");
    public static final ResourceLocation STAR_MODEL = GTLCore.id("obj/star");

    public EyeOfHarmonyRenderer() {
        super(new ResourceLocation("kubejs:block/dimensionally_transcendent_casing"), GTCEu.id("block/cosmos_simulation"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer,
                       int combinedLight, int combinedOverlay) {
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity &&
                machineBlockEntity.getMetaMachine() instanceof HarmonyMachine machine && machine.isActive()) {
            float tick = machine.getLevel().getGameTime() + partialTicks;
            double x = 0.5, y = 0.5, z = 0.5;
            switch (machine.getFrontFacing()) {
                case NORTH -> z = 16.5;
                case SOUTH -> z = -15.5;
                case WEST -> x = 16.5;
                case EAST -> x = -15.5;
            }
            poseStack.pushPose();
            poseStack.translate(x, y, z);
            renderStar(tick, poseStack, buffer, combinedLight, combinedOverlay);
            renderOuterSpaceShell(poseStack, buffer);
            poseStack.popPose();
        }
    }

    private static void renderStar(float tick, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        poseStack.scale(0.02F, 0.02F, 0.02F);
        poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(0F, 1F, 1F, 130F + tick % 360F));
        PoseStack.Pose pose = poseStack.last();
        VertexConsumer consumer = buffer.getBuffer(RenderType.translucent());
        List<BakedQuad> quads = ClientUtil.getBakedModel(STAR_MODEL).getQuads(null, null, GTValues.RNG);
        for (BakedQuad quad : quads) {
            consumer.putBulkData(pose, quad, 1f, 1f, 1f, combinedLight, combinedOverlay);
        }
        poseStack.popPose();
    }

    private void renderOuterSpaceShell(PoseStack poseStack, MultiBufferSource buffer) {
        float scale = 0.01F * 17.5F;
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        ClientUtil.modelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.solid()), null, ClientUtil.getBakedModel(SPACE_MODEL), 1.0F,1.0F,1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.solid());
        poseStack.popPose();
    }

    @Override
    public void onAdditionalModel(Consumer<ResourceLocation> registry) {
        super.onAdditionalModel(registry);
        registry.accept(SPACE_MODEL);
        registry.accept(STAR_MODEL);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPartModel(List<BakedQuad> list, IMultiController iMultiController, IMultiPart iMultiPart, Direction direction, @Nullable Direction direction1, RandomSource randomSource, Direction direction2, ModelState modelState) {
        if (direction1 != null && direction2 != null) {
            list.add(FaceQuad.bakeFace(direction2, ModelFactory.getBlockSprite(GTCEu.id("block/casings/hpca/high_power_casing")), modelState));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getViewDistance() {
        return 128;
    }
}
