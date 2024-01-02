package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ProviderPatterns implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultPatterns patterns = new DefaultPatterns();
    private final LazyOptional<IPatterns> patternsOptional = LazyOptional.of(() -> patterns);

    public void invalidate(){ patternsOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityPatterns.FLAG_PATTERNS_CAPABILITY)
            return patternsOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityPatterns.FLAG_PATTERNS_CAPABILITY.writeNBT(patterns, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY != null){
            CapabilityPatterns.FLAG_PATTERNS_CAPABILITY.readNBT(patterns, null, nbt);
        }
    }
}
