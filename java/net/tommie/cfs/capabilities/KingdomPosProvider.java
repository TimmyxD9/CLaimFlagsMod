package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KingdomPosProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultKingdomPos kpos = new DefaultKingdomPos();
    private final LazyOptional<IKingdomPos> kposOptional = LazyOptional.of(() -> kpos);

    public void invalidate(){ kposOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityKingdomPos.KINGDOM_POS_CAPABILITY)
            return kposOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityKingdomPos.KINGDOM_POS_CAPABILITY.writeNBT(kpos, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY != null){
            CapabilityKingdomPos.KINGDOM_POS_CAPABILITY.readNBT(kpos, null, nbt);
        }
    }
}
