package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProviderLastUseTimer implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultLastUseTime lastUse = new DefaultLastUseTime();
    private final LazyOptional<ILastUseTimer> lastUseOptional = LazyOptional.of(() -> lastUse);

    public void invalidate(){ lastUseOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY)
            return lastUseOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY.writeNBT(lastUse, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY != null){
            CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY.readNBT(lastUse, null, nbt);
        }
    }
}
