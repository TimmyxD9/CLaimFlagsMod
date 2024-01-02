package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UpkeepProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultUpkeep upkeep = new DefaultUpkeep();
    private final LazyOptional<IUpkeep> upkeepOptional = LazyOptional.of(() -> upkeep);

    public void invalidate(){ upkeepOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY)
            return upkeepOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY.writeNBT(upkeep, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY != null){
            CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY.readNBT(upkeep, null, nbt);
        }
    }
}
