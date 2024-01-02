package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClaimsProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultClaims claims = new DefaultClaims();
    private final LazyOptional<IClaims> claimsOptional = LazyOptional.of(() -> claims);

    public void invalidate(){ claimsOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY)
            return claimsOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY.writeNBT(claims, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY != null){
            CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY.readNBT(claims, null, nbt);
        }
    }
}
