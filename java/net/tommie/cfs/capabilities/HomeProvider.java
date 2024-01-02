package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HomeProvider implements ICapabilitySerializable<CompoundNBT> {
    private final DefaultHome home = new DefaultHome();
    private final LazyOptional<IHome> homeOptional = LazyOptional.of(() -> home);

    public void invalidate(){ homeOptional.invalidate();}


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityHome.CLAIM_HOME_CAPABILITY)
            return homeOptional.cast();
        else
            return LazyOptional.empty();
    }
    @Override
    public CompoundNBT serializeNBT() {
        if(CapabilityHome.CLAIM_HOME_CAPABILITY == null){
            return new CompoundNBT();
        } else {
            return (CompoundNBT) CapabilityHome.CLAIM_HOME_CAPABILITY.writeNBT( home, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(CapabilityHome.CLAIM_HOME_CAPABILITY != null){
            CapabilityHome.CLAIM_HOME_CAPABILITY.readNBT(home, null, nbt);
        }
    }
}
