package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityOwner {
    @CapabilityInject(IOwner.class)
    public static Capability<IOwner> KINGDOM_OWNER_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IOwner.class, new Storage(), DefaultOwner::new);
    }
    public static class Storage implements Capability.IStorage<IOwner>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IOwner> capability, IOwner instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("owner", instance.getOwnerId());
            return tag;
        }
        @Override
        public void readNBT(Capability<IOwner> capability, IOwner instance, Direction side, INBT nbt)
        {
            String ownerId = ((CompoundNBT) nbt).getString("owner");
            instance.setOwnerId(ownerId);
        }
    }
}
