package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPatterns {
    @CapabilityInject(IPatterns.class)
    public static Capability<IPatterns> FLAG_PATTERNS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPatterns.class, new Storage(), DefaultPatterns::new);
    }
    public static class Storage implements Capability.IStorage<IPatterns>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IPatterns> capability, IPatterns instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            ListNBT list = instance.getPatterns();
            String color = instance.getColor();
            tag.put("patterns",list);
            tag.putString("color",color);
            return tag;
        }
        @Override
        public void readNBT(Capability<IPatterns> capability, IPatterns instance, Direction side, INBT nbt)
        {
            ListNBT patterns = ((CompoundNBT) nbt).getList("patterns", 10);
            String color = ((CompoundNBT) nbt).getString("color");
            instance.setColor(color);
            instance.setPatterns(patterns);
        }
    }
}
