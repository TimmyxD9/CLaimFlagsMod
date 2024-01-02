package net.tommie.cfs.block.custom;

import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.tommie.cfs.capabilities.CapabilityClaims;
import net.tommie.cfs.capabilities.CapabilityKingdomPos;
import net.tommie.cfs.capabilities.ClaimsManager;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

public class ClaimFlagBaseBlock extends BannerBlock {
    public ClaimFlagBaseBlock(DyeColor color, Properties properties) {
        super(color, properties);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state != newState) {
            TileEntity flagTileEntity = worldIn.getTileEntity(pos);
            if (flagTileEntity instanceof ClaimFlagTileEntity) {
                ClaimsManager manager = ClaimsManager.get(worldIn.getServer());
                if (flagTileEntity.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).isPresent()) {
                    flagTileEntity.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).ifPresent(c -> {
                        if (c.getPos().getY() != -65) {
                            if(worldIn.getBlockState(c.getPos()).getBlock() instanceof KingdomBlock) {
                                if (worldIn.getTileEntity(c.getPos()) instanceof KingdomBlockTileEntity) {
                                    worldIn.getTileEntity(c.getPos()).getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(d -> {
                                        d.removeFlag(pos);
                                    });
                                }
                            }
                        }
                        if(manager.getClaimant(new ChunkPos(pos)).getKingdomPos().equals(c.getPos()))
                            manager.setUnclaimed(new ChunkPos(pos),c.getPos());
                    });
                }
            }
            worldIn.removeTileEntity(pos);
        }
    }
}
