package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProviderMemberNames implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultMemberNames memberNames = new DefaultMemberNames();
    private final LazyOptional<IMemberNames> memberNamesOptional = LazyOptional.of(() -> memberNames);

    public void invalidate(){ memberNamesOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY)
            return memberNamesOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY.writeNBT(memberNames, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY != null){
            CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY.readNBT(memberNames, null, nbt);
        }
    }
}
