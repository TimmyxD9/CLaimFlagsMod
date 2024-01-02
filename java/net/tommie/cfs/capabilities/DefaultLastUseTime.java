package net.tommie.cfs.capabilities;

import net.minecraft.util.math.ChunkPos;

public class DefaultLastUseTime implements ILastUseTimer {
    private Double fTime = (double) 0;
    private Double lTime = (double) 0;
    private ChunkPos overclaimedChunk = new ChunkPos(0,0);

    @Override
    public ChunkPos getOverclaimingChunk() {
        return this.overclaimedChunk;
    }

    @Override
    public void setOverclaimingChunk(ChunkPos chunk){this.overclaimedChunk=chunk;}
    @Override
    public double getLastUseTimer() {
        return this.lTime;
    }

    @Override
    public void setLastUseTimer(double time) {
        this.lTime=time;
    }

    @Override
    public double getFirstUseTimer() {
        return this.fTime;
    }

    @Override
    public void setFirstUseTImer(double time) {
        this.fTime = time;
    }
}
