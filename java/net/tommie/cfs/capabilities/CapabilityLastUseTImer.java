package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityLastUseTImer {
    @CapabilityInject(ILastUseTimer.class)
    public static Capability<ILastUseTimer> PLAYER_LAST_USE_FLAG_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(ILastUseTimer.class, new CapabilityLastUseTImer.Storage(), DefaultLastUseTime::new);
    }
    public static class Storage implements Capability.IStorage<ILastUseTimer>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<ILastUseTimer> capability, ILastUseTimer instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putDouble("ltime", instance.getLastUseTimer());
            tag.putDouble("ftime", instance.getFirstUseTimer());
            tag.putInt("cx", instance.getOverclaimingChunk().x);
            tag.putInt("cz", instance.getOverclaimingChunk().z);
            return tag;
        }
        @Override
        public void readNBT(Capability<ILastUseTimer> capability, ILastUseTimer instance, Direction side, INBT nbt)
        {
            Double ltime = ((CompoundNBT) nbt).getDouble("ltime");
            Double ftime = ((CompoundNBT) nbt).getDouble("ftime");
            Integer cx = ((CompoundNBT) nbt).getInt("cx");
            Integer cz = ((CompoundNBT) nbt).getInt("cz");
            instance.setLastUseTimer(ltime);
            instance.setFirstUseTImer(ftime);
            instance.setOverclaimingChunk(new ChunkPos(cx,cz));
        }
    }
}
