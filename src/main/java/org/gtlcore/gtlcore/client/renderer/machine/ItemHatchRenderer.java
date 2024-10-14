package org.gtlcore.gtlcore.client.renderer.machine;

import org.gtlcore.gtlcore.client.ClientUtil;
import org.gtlcore.gtlcore.common.machine.multiblock.part.ItemHatchPartMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.client.renderer.machine.TieredHullMachineRenderer;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import com.mojang.blaze3d.vertex.PoseStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHatchRenderer extends TieredHullMachineRenderer {

    public ItemHatchRenderer() {
        super(1, GTCEu.id("block/machine/hull_machine"));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        if (blockEntity instanceof IMachineBlockEntity machineBlockEntity && machineBlockEntity.getMetaMachine() instanceof ItemHatchPartMachine partMachine) {
            ItemStack itemStack = partMachine.getInventory().storage.getStackInSlot(0);
            if (itemStack.isEmpty()) return;
            stack.pushPose();
            stack.translate(0, 1.5, 0);
            BakedModel model = ClientUtil.getVanillaModel(partMachine.getInventory().storage.getStackInSlot(0), null, null);

            ClientUtil.modelRenderer().renderModel(stack.last(), buffer.getBuffer(RenderType.cutoutMipped()), null, model, 1.0F, 1.0F, 1.0F, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, RenderType.cutoutMipped());

            stack.popPose();
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
}
