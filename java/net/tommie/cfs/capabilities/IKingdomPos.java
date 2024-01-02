package net.tommie.cfs.capabilities;

import net.minecraft.util.math.BlockPos;

public interface IKingdomPos {
    int getX();
    int getY();
    int getZ();
    void setX(int x);
    void setY(int y);
    void setZ(int z);
    BlockPos getPos();
    void setPos(BlockPos pos);

}
