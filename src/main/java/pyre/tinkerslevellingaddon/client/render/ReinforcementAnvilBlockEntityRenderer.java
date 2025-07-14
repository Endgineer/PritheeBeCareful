package pyre.tinkerslevellingaddon.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import pyre.tinkerslevellingaddon.block.ReinforcementAnvilBlock;
import pyre.tinkerslevellingaddon.entity.ReinforcementAnvilBlockEntity;

public class ReinforcementAnvilBlockEntityRenderer implements BlockEntityRenderer<ReinforcementAnvilBlockEntity> {
    public ReinforcementAnvilBlockEntityRenderer(Context context) {}

    @Override
    public void render(ReinforcementAnvilBlockEntity blockEntity, float partialTicks, PoseStack matrices, MultiBufferSource buffer, int light, int combinedOverlayIn) {
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos();
        
        int blocklight = level.getBrightness(LightLayer.BLOCK, pos);
        int skylight = level.getBrightness(LightLayer.SKY, pos);
        
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        
        ItemStack slotstack_a = blockEntity.getItem(ReinforcementAnvilBlockEntity.SLOT_A);
        ItemStack slotstack_b = blockEntity.getItem(ReinforcementAnvilBlockEntity.SLOT_B);
        ItemStack slotstack_c = blockEntity.getItem(ReinforcementAnvilBlockEntity.SLOT_C);
        
        matrices.pushPose();
        matrices.translate(0.5F, 1.015625, 0.5F);
        matrices.scale(0.5F, 0.5F, 0.5F);
        matrices.mulPose(Axis.XP.rotationDegrees(90));

        switch(blockEntity.getBlockState().getValue(ReinforcementAnvilBlock.getFacing())) {
            case NORTH -> matrices.mulPose(Axis.ZP.rotationDegrees(0));
            case EAST -> matrices.mulPose(Axis.ZP.rotationDegrees(90));
            case SOUTH -> matrices.mulPose(Axis.ZP.rotationDegrees(180));
            case WEST -> matrices.mulPose(Axis.ZP.rotationDegrees(270));
            default -> {}
        }
        
        matrices.pushPose();
        renderer.renderStatic(slotstack_b, ItemDisplayContext.FIXED, LightTexture.pack(blocklight, skylight), OverlayTexture.NO_OVERLAY, matrices, buffer, level, 1);
        matrices.popPose();
        
        matrices.pushPose();
        matrices.translate(-0.5F, 0.0F, 0.0F);
        renderer.renderStatic(slotstack_a, ItemDisplayContext.FIXED, LightTexture.pack(blocklight, skylight), OverlayTexture.NO_OVERLAY, matrices, buffer, level, 1);
        matrices.popPose();
        
        matrices.pushPose();
        matrices.translate(0.5F, 0.0F, 0.0F);
        renderer.renderStatic(slotstack_c, ItemDisplayContext.FIXED, LightTexture.pack(blocklight, skylight), OverlayTexture.NO_OVERLAY, matrices, buffer, level, 1);
        matrices.popPose();
        
        matrices.popPose();
    }
}
