package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityUpkeep {
    @CapabilityInject(IUpkeep.class)
    public static Capability<IUpkeep> TERRITORY_UPKEEP_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IUpkeep.class, new CapabilityUpkeep.Storage(), DefaultUpkeep::new);
    }
    public static class Storage implements Capability.IStorage<IUpkeep>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IUpkeep> capability, IUpkeep instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("upkeep", instance.getUpkeep());
            return tag;
        }
        @Override
        public void readNBT(Capability<IUpkeep> capability, IUpkeep instance, Direction side, INBT nbt)
        {
            instance.setUpkeep(((CompoundNBT) nbt).getInt("upkeep"));
        }
    }
}
