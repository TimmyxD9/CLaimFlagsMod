package net.tommie.cfs.capabilities;

import net.minecraft.util.math.BlockPos;

public class Claimant {
    private boolean isClaimed = false;
    private BlockPos kingdomPos = new BlockPos(0,0,-1);

    Claimant(){
    }

    Claimant(BlockPos kingdomPos){
        this.kingdomPos = kingdomPos;
    }
    Claimant(BlockPos kingdomPos, boolean check){
        this.kingdomPos = kingdomPos;
        this.isClaimed = check;
    }
    public boolean getIsClaimed(){
        return this.isClaimed;
    }

    public void setClaimed(BlockPos kingdomPos){
        this.isClaimed = true;
        this.kingdomPos = kingdomPos;
    }

    public void setUnClaimed(){
        this.isClaimed = false;
        this.kingdomPos = new BlockPos(0,0,-1);;
    }

    public BlockPos getKingdomPos(){
        return this.kingdomPos;
    }

    public void setKingdomPos(BlockPos kingdomPos){
        this.kingdomPos = kingdomPos;
    }
}
