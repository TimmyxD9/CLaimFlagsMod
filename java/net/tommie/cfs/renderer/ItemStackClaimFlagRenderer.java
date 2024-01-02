package net.tommie.cfs.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.tommie.cfs.item.ClaimFlagItem;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;

public class ItemStackClaimFlagRenderer extends ItemStackTileEntityRenderer {
    public static ItemStackTileEntityRenderer instance = new ItemStackClaimFlagRenderer();
    private final ClaimFlagTileEntity claimFlag = new ClaimFlagTileEntity();

        @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        claimFlag.loadFromItemStack(stack, ((ClaimFlagItem) stack.getItem()).getColor());
        TileEntityRendererDispatcher.instance.renderItem(claimFlag, matrixStack, buffer, combinedLight, combinedOverlay);
    }
}
