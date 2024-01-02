package net.tommie.cfs.capabilities;

import net.minecraft.util.math.BlockPos;

public class DefaultKingdomPos implements IKingdomPos {
    private BlockPos pos = new BlockPos(0,-65,0);

    @Override
    public int getX() {
        return this.pos.getX();
    }

    @Override
    public int getY() {
        return this.pos.getY();
    }

    public int getZ() {
        return this.pos.getZ();
    }

    public void setX(int x)
    {
        this.pos = new BlockPos(x, this.pos.getY(), this.pos.getZ());
    }

    public void setY(int y){
        this.pos = new BlockPos(this.pos.getX(), y, this.pos.getZ());
    }

    public void setZ(int z){
        this.pos = new BlockPos(this.pos.getX(), this.pos.getY(), z);
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public void setPos(BlockPos pos){
        this.pos = pos;
    }

}
