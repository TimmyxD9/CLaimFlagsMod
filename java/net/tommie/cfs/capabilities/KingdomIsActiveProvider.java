package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KingdomIsActiveProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultKingdomIsActive active = new DefaultKingdomIsActive();
    private final LazyOptional<IKingdomIsActive> rulerOptional = LazyOptional.of(() -> active);

    public void invalidate(){ rulerOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY)
            return rulerOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY.writeNBT(active, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY != null){
            CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY.readNBT(active, null, nbt);
        }
    }
}

