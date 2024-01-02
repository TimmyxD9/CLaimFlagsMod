package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;
import net.tommie.cfs.config.ClaimFlagsConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClaimsManager extends WorldSavedData {
    private Map<ChunkPos, Claimant> claimMap = new HashMap<>();
    private World world;
    public ClaimsManager(String name, World world){
        super(name);
        this.world = world;
    }
    public ClaimsManager(String p_i2141_1_) {
        super(p_i2141_1_);
    }

    private final int claimRadius = validateClaimRadius(ClaimFlagsConfig.claim_radius.get());
    private int validateClaimRadius(int claimRadius){
        if(claimRadius%2!=1)
            claimRadius = 1;
        return claimRadius;
    }

    public static ClaimsManager get(MinecraftServer server){
        return Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getSavedData().getOrCreate(() -> new ClaimsManager("claimsnstuff", server.getWorld(World.OVERWORLD)), "claimsnstuff");
    }

    public void setUnclaimed(ChunkPos pos, BlockPos kingdomPos){
        if(claimRadius == 1) {
            if (claimMap.containsKey(pos)) {
                //claimMap.put(pos,new Claimant(kingdomPos,false));
                claimMap.remove(pos);
                this.markDirty();
            }
        }
        else
            {
                for(int claimerx = -claimRadius/2; claimerx<=claimRadius/2; claimerx++)
                {
                    for(int claimerz = -claimRadius/2; claimerz<=claimRadius/2; claimerz++)
                    {
                        ChunkPos key = new ChunkPos(pos.x + claimerx, pos.z + claimerz);
                        if(this.claimMap.containsKey(key))
                        {
                            if(this.claimMap.get(key).getKingdomPos().equals(kingdomPos))
                                //claimMap.put(key,new Claimant(kingdomPos,false));
                                this.claimMap.remove(key);
                        }
                    }
                }
                this.markDirty();
            }
    }

    public void setClaimed(ChunkPos pos, BlockPos kingdomPos){
        if(claimRadius==1) {
            if(claimMap.containsKey(pos))
            {
                if(!claimMap.get(pos).getKingdomPos().equals(kingdomPos)) {
                    if (claimMap.get(pos).getIsClaimed()) {
                        world.getTileEntity(claimMap.get(pos).getKingdomPos()).getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(claims -> {
                            if (claims.getChunks().contains(pos))
                                world.removeBlock(claims.getFlags().get(claims.getChunks().indexOf(pos)), true);
                        });
                    }
                }
            }
            claimMap.put(pos, new Claimant(kingdomPos, true));
            this.markDirty();
        }
        else {
            for (int claimerx = -claimRadius / 2; claimerx <= claimRadius / 2; claimerx++) {
                for (int claimerz = -claimRadius / 2; claimerz <= claimRadius / 2; claimerz++) {
                    ChunkPos key = new ChunkPos(pos.x + claimerx, pos.z + claimerz);
                    if(claimMap.containsKey(key))
                        if(claimMap.get(key).getIsClaimed())
                        {
                            if(!claimMap.get(key).getKingdomPos().equals(kingdomPos)) {
                                world.getTileEntity(claimMap.get(key).getKingdomPos()).getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(claims -> {
                                    if (claims.getChunks().contains(key))
                                        world.removeBlock(claims.getFlags().get(claims.getChunks().indexOf(key)), true);
                                });
                            }
                        }
                    claimMap.put(key, new Claimant(kingdomPos,true));
                }
            }
            this.markDirty();
        }
    }

    public Claimant getClaimant(ChunkPos pos){
        if(this.checkClaimed(pos))
            return this.claimMap.get(pos);
        else
            return new Claimant(new BlockPos(0,-65,0));
    }
    public boolean checkClaimed(ChunkPos pos){
        if(claimRadius==1)
            return compassClaimer(pos);
        for(int claimerx = -claimRadius/2; claimerx<=claimRadius/2; claimerx++) {
            for (int claimerz = -claimRadius / 2; claimerz <=claimRadius / 2; claimerz++) {
                ChunkPos key = new ChunkPos(pos.x + claimerx, pos.z + claimerz);
                if(this.claimMap.containsKey(key)) {
                    if(this.claimMap.get(key).getIsClaimed())
                        return true;
                }
            }
        }
        return false;
    }
    
    public boolean compassClaimer(ChunkPos pos){
        if(this.claimMap.containsKey(pos))
            return this.claimMap.get(pos).getIsClaimed();
        return false;
    }

    @Override
    public void read(CompoundNBT nbt) {
        claimMap.clear();
        final ListNBT claimList = nbt.getList("claims", 10);
        for(int i = 0, l = claimList.size(); i<l; i++){
            CompoundNBT tmpTag = claimList.getCompound(i);
            claimMap.put(new ChunkPos(tmpTag.getInt("sumx"), tmpTag.getInt("sumz")),
                    new Claimant(new BlockPos(tmpTag.getInt("x"), tmpTag.getInt("y"),
                            tmpTag.getInt("z")), tmpTag.getBoolean("claimed")));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        final ListNBT tagList = new ListNBT();
        for(final Map.Entry<ChunkPos, Claimant> entry: claimMap.entrySet()){
            CompoundNBT entryTag = new CompoundNBT();
            entryTag.putInt("sumx", entry.getKey().x);
            entryTag.putInt("sumz", entry.getKey().z);
            entryTag.putInt("x", entry.getValue().getKingdomPos().getX());
            entryTag.putInt("y", entry.getValue().getKingdomPos().getY());
            entryTag.putInt("z", entry.getValue().getKingdomPos().getZ());
            entryTag.putBoolean("claimed", entry.getValue().getIsClaimed());
            tagList.add(entryTag);
        }
        compound.put("claims", tagList);
        return compound;
    }
}
