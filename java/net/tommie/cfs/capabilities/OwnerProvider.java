package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OwnerProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultOwner owner = new DefaultOwner();
    private final LazyOptional<IOwner> ownerOptional = LazyOptional.of(() -> owner);

    public void invalidate(){ ownerOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityOwner.KINGDOM_OWNER_CAPABILITY)
            return ownerOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityOwner.KINGDOM_OWNER_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityOwner.KINGDOM_OWNER_CAPABILITY.writeNBT(owner, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityOwner.KINGDOM_OWNER_CAPABILITY != null){
            CapabilityOwner.KINGDOM_OWNER_CAPABILITY.readNBT(owner, null, nbt);
        }
    }
}
