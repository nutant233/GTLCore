package org.gtlcore.gtlcore.client.forge;

import org.gtlcore.gtlcore.GTLCore;
import org.gtlcore.gtlcore.common.data.GTLBlocks;
import org.gtlcore.gtlcore.common.data.GTLItems;
import org.gtlcore.gtlcore.common.item.StructureWriteBehavior;
import org.gtlcore.gtlcore.utils.TextUtil;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTMachines;

import com.lowdragmc.lowdraglib.client.utils.RenderBufferUtils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = GTLCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ForgeClientEventListener {

    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event) {
        Item item = event.getItemStack().getItem();

        Map<Item, String[]> tooltipMap = new HashMap<>();
        tooltipMap.put(GTLItems.WARPED_ENDER_PEAL.get(), new String[] { "潜行右键可设置个人传送点，右键传送到传送点" });
        tooltipMap.put(GTLBlocks.COIL_URUIUM.asItem(), new String[] { "可为超维度等离子锻炉提供32000K炉温", "恒星锻炉模式仅可使用该线圈" });
        tooltipMap.put(GTLBlocks.ESSENCE_BLOCK.asItem(), new String[] { "将骨块放置在转换室获得" });
        tooltipMap.put(GTLBlocks.DRACONIUM_BLOCK_CHARGED.asItem(), new String[] { "将注入龙力的黑曜石放置在转换室获得" });
        tooltipMap.put(GTLItems.HYPER_STABLE_SELF_HEALING_ADHESIVE.get(), new String[] { "§7选择性完全粘合，即使在撕裂或损坏时也有效" });
        tooltipMap.put(GTLItems.BLACK_BODY_NAQUADRIA_SUPERSOLID.get(), new String[] { "§7如液体般流动，不反射任何电磁波，完美地将其吸收与传递" });
        tooltipMap.put(GTLItems.HUI_CIRCUIT_1.get(), new String[] { "§793015-T浮点运算/秒" });
        tooltipMap.put(GTLItems.HUI_CIRCUIT_2.get(), new String[] { "§776M处理单元" });
        tooltipMap.put(GTLItems.HUI_CIRCUIT_3.get(), new String[] { "§7无效RSA算法" });
        tooltipMap.put(GTLItems.HUI_CIRCUIT_4.get(), new String[] { "§7第56梅森素数" });
        tooltipMap.put(GTLItems.HUI_CIRCUIT_5.get(), new String[] { "§7佯谬" });
        tooltipMap.put(GTLItems.BIOWARE_PRINTED_CIRCUIT_BOARD.get(), new String[] { "§7生物基因突变的电路基板" });
        tooltipMap.put(GTLItems.OPTICAL_PRINTED_CIRCUIT_BOARD.get(), new String[] { "§7光学注入的电路基板" });
        tooltipMap.put(GTLItems.EXOTIC_PRINTED_CIRCUIT_BOARD.get(), new String[] { "§7量子电路基板" });
        tooltipMap.put(GTLItems.COSMIC_PRINTED_CIRCUIT_BOARD.get(), new String[] { "§7承载宇宙的电路基板" });
        tooltipMap.put(GTLItems.SUPRACAUSAL_PRINTED_CIRCUIT_BOARD.get(), new String[] { "§7最终的电路基板" });
        tooltipMap.put(GTLItems.CREATE_ULTIMATE_BATTERY.get(), new String[] { "§7能凭空产生能量", "§2等级-", TextUtil.white_blue("未知") });
        tooltipMap.put(GTLItems.SUPRACHRONAL_MAINFRAME_COMPLEX.get(), new String[] { "§7能凭空产生算力", "§2等级-", TextUtil.white_blue("未知") });
        tooltipMap.put(GTLItems.SUPRACAUSAL_MAINFRAME.get(), new String[] { "§7未卜先知", TextUtil.full_color("MAX级电路") });
        tooltipMap.put(GTLItems.SUPRACAUSAL_COMPUTER.get(), new String[] { "§7利用虫洞的优势", TextUtil.full_color("OpV级电路") });
        tooltipMap.put(GTLItems.SUPRACAUSAL_ASSEMBLY.get(), new String[] { "§7巨量的奇点", TextUtil.full_color("UXV级电路") });
        tooltipMap.put(GTLItems.SUPRACAUSAL_PROCESSOR.get(), new String[] { "§7黑洞之力", TextUtil.full_color("UIV级电路") });
        tooltipMap.put(GTLItems.COSMIC_ASSEMBLY.get(), new String[] { "§7于握揽微微转动", TextUtil.dark_purplish_red("UIV级电路") });
        tooltipMap.put(GTLItems.COSMIC_COMPUTER.get(), new String[] { "§7密度趋近于奇点的小东西", TextUtil.dark_purplish_red("UXV级电路") });
        tooltipMap.put(GTLItems.COSMIC_MAINFRAME.get(), new String[] { "§7寰宇之力，震慑古今！", TextUtil.dark_purplish_red("OpV级电路") });
        tooltipMap.put(GTLItems.COSMIC_PROCESSOR.get(), new String[] { "§7手握星辰", TextUtil.dark_purplish_red("UEV级电路") });
        tooltipMap.put(GTLItems.EXOTIC_ASSEMBLY.get(), new String[] { "§7量子随机游走", TextUtil.purplish_red("UEV级电路") });
        tooltipMap.put(GTLItems.EXOTIC_COMPUTER.get(), new String[] { "§7以自旋控制一切", TextUtil.purplish_red("UIV级电路") });
        tooltipMap.put(GTLItems.EXOTIC_MAINFRAME.get(), new String[] { "§7来自未来的电路", TextUtil.purplish_red("UXV级电路") });
        tooltipMap.put(GTLItems.EXOTIC_PROCESSOR.get(), new String[] { "§7超级磁性半导体电路", TextUtil.purplish_red("UHV级电路") });
        tooltipMap.put(GTLItems.OPTICAL_ASSEMBLY.get(), new String[] { "§7激光之力！", TextUtil.golden("UHV级电路") });
        tooltipMap.put(GTLItems.OPTICAL_COMPUTER.get(), new String[] { "§7就在眨眼之间", TextUtil.golden("UEV级电路") });
        tooltipMap.put(GTLItems.OPTICAL_MAINFRAME.get(), new String[] { "§7还能更快吗？", TextUtil.golden("UIV级电路") });
        tooltipMap.put(GTLItems.OPTICAL_PROCESSOR.get(), new String[] { "§7光速计算", TextUtil.golden("UV级电路") });
        tooltipMap.put(GTLItems.BIOWARE_ASSEMBLY.get(), new String[] { "§7似乎能听到窃窃私语", TextUtil.dark_green("UV级电路") });
        tooltipMap.put(GTLItems.BIOWARE_COMPUTER.get(), new String[] { "§7金属之间布满了黏菌", TextUtil.dark_green("UHV级电路") });
        tooltipMap.put(GTLItems.BIOWARE_MAINFRAME.get(), new String[] { "§7菌群意识网络", TextUtil.dark_green("UEV级电路") });
        tooltipMap.put(GTLItems.BIOWARE_PROCESSOR.get(), new String[] { "§7粘稠的有机浆液附着于表面", TextUtil.dark_green("ZPM级电路") });

        if (tooltipMap.containsKey(item)) {
            String[] tooltipInfo = tooltipMap.get(item);
            for (String text : tooltipInfo) {
                event.getToolTip().add(Component.literal(text));
            }
        } else {
            for (int tier : GTMachines.ALL_TIERS) {
                if (event.getItemStack().is(GTLItems.SUPRACHRONAL_CIRCUIT[tier].get())) {
                    event.getToolTip().add(Component.literal("运行在已知时空之外").withStyle(ChatFormatting.GRAY));
                    event.getToolTip().add(Component.literal(TextUtil.white_blue(GTValues.VN[tier] + "级电路")));
                    break;
                } else if (tier < GTValues.UXV && event.getItemStack().is(GTLItems.MAGNETO_RESONATIC_CIRCUIT[tier].get())) {
                    event.getToolTip().add(Component.literal("利用磁共振仪器产生的强大磁场来运行").withStyle(ChatFormatting.GRAY));
                    event.getToolTip().add(Component.literal(GTValues.VN[tier] + "级电路").withStyle(ChatFormatting.LIGHT_PURPLE));
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        RenderLevelStageEvent.Stage stage = event.getStage();
        if (stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            Minecraft mc = Minecraft.getInstance();
            ClientLevel level = mc.level;
            LocalPlayer player = mc.player;
            if (level == null || player == null) return;
            PoseStack poseStack = event.getPoseStack();
            Camera camera = event.getCamera();
            ItemStack held = player.getMainHandItem();
            if (StructureWriteBehavior.isItemStructureWriter(held)) {
                BlockPos[] poses = StructureWriteBehavior.getPos(held);
                if (poses == null) return;
                Vec3 pos = camera.getPosition();

                poseStack.pushPose();
                poseStack.translate(-pos.x, -pos.y, -pos.z);

                RenderSystem.disableDepthTest();
                RenderSystem.enableBlend();
                RenderSystem.disableCull();
                RenderSystem.blendFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder buffer = tesselator.getBuilder();

                buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                RenderSystem.setShader(GameRenderer::getPositionColorShader);

                RenderBufferUtils.renderCubeFace(
                        poseStack,
                        buffer,
                        poses[0].getX(),
                        poses[0].getY(),
                        poses[0].getZ(),
                        poses[1].getX() + 1,
                        poses[1].getY() + 1,
                        poses[1].getZ() + 1,
                        0.2f,
                        0.2f,
                        1f,
                        0.25f,
                        true);

                tesselator.end();

                buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
                RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
                RenderSystem.lineWidth(3);

                RenderBufferUtils.drawCubeFrame(
                        poseStack,
                        buffer,
                        poses[0].getX(),
                        poses[0].getY(),
                        poses[0].getZ(),
                        poses[1].getX() + 1,
                        poses[1].getY() + 1,
                        poses[1].getZ() + 1,
                        0.0f,
                        0.0f,
                        1f,
                        0.5f);

                tesselator.end();

                RenderSystem.enableCull();

                RenderSystem.disableBlend();
                RenderSystem.enableDepthTest();
                poseStack.popPose();
            }
        }
    }
}
