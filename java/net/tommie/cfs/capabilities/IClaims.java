package net.tommie.cfs.capabilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.List;

public interface IClaims {
    void addFlag(BlockPos pos);

    List<BlockPos> getFlags();
    List<ChunkPos> getChunks();

    void removeFlag(BlockPos pos);

}
