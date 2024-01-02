package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityKingdomIsActive {
    @CapabilityInject(IKingdomIsActive.class)
    public static Capability<IKingdomIsActive> KINGDOM_IS_ACTIVE_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IKingdomIsActive.class, new CapabilityKingdomIsActive.Storage(), DefaultKingdomIsActive::new);
    }
    public static class Storage implements Capability.IStorage<IKingdomIsActive>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IKingdomIsActive> capability, IKingdomIsActive instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putBoolean("active", instance.getKingdomIsActive());
            return tag;
        }
        @Override
        public void readNBT(Capability<IKingdomIsActive> capability, IKingdomIsActive instance, Direction side, INBT nbt)
        {
            instance.setKingdomIsActive(((CompoundNBT) nbt).getBoolean("active"));
        }
    }
}
