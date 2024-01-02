package net.tommie.cfs.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;
import javax.annotation.Nullable;

public class ClaimFlagWallBlock extends ClaimFlagBaseBlock {

    public ClaimFlagWallBlock(){
        super(DyeColor.WHITE, AbstractBannerBlock.Properties.create(Material.WOOL));
    }
    public ClaimFlagWallBlock(DyeColor color, Properties properties) {
        super(color, properties);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new ClaimFlagTileEntity();
    }
}
