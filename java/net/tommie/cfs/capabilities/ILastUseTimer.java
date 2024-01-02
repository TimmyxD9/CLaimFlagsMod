package net.tommie.cfs.capabilities;

import net.minecraft.util.math.ChunkPos;

public interface ILastUseTimer {
        double getLastUseTimer();
        void setLastUseTimer(double time);

        double getFirstUseTimer();
        void setFirstUseTImer(double time);

        ChunkPos getOverclaimingChunk();
        void setOverclaimingChunk(ChunkPos chunk);
}
