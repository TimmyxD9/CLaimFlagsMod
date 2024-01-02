package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MembersProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultMembers members = new DefaultMembers();
    private final LazyOptional<IMembers> membersOptional = LazyOptional.of(() -> members);

    public void invalidate(){ membersOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY)
            return membersOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY.writeNBT(members, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY != null){
            CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY.readNBT(members, null, nbt);
        }
    }
}
