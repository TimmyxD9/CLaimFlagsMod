package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityHome {
    @CapabilityInject(IHome.class)
    public static Capability<DefaultHome> CLAIM_HOME_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IHome.class, new CapabilityHome.Storage(), DefaultHome::new);
    }
    public static class Storage implements Capability.IStorage<IHome>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IHome> capability, IHome instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putString("home", instance.getHome());
            return tag;
        }
        @Override
        public void readNBT(Capability<IHome> capability, IHome instance, Direction side, INBT nbt)
        {
            String home = ((CompoundNBT) nbt).getString("home");
            instance.setHome(home);
        }
    }
}
