package net.tommie.cfs.capabilities;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class DefaultClaims implements IClaims{
    List<BlockPos> claims = new ArrayList<>();

    @Override
    public void addFlag(BlockPos pos) {
        if(!claims.contains(pos))
            claims.add(pos);
    }

    @Override
    public List<BlockPos> getFlags() {
        return claims;
    }

    @Override
    public void removeFlag(BlockPos pos) {
        if(claims.contains(pos))
            claims.remove(pos);
    }

    @Override
    public List<ChunkPos> getChunks(){
        List<ChunkPos> chunks = new ArrayList<>();
        BlockPos claim;
        for(int i=0; i<claims.size();i++)
            chunks.add(new ChunkPos(claims.get(i)));
        return chunks;
    }
}
