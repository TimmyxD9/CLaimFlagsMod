package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.List;

public class CapabilityClaims {
    @CapabilityInject(IClaims.class)
    public static Capability<IClaims> KINGDOM_CLAIMS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IClaims.class, new CapabilityClaims.Storage(), DefaultClaims::new);
    }
    public static class Storage implements Capability.IStorage<IClaims>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IClaims> capability, IClaims instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            List<BlockPos> claims = instance.getFlags();
            CompoundNBT tmp = new CompoundNBT();
            ListNBT nbt = new ListNBT();
            for (BlockPos pos: claims) {
                tmp.putInt("flagX", pos.getX());
                tmp.putInt("flagY", pos.getY());
                tmp.putInt("flagZ", pos.getZ());
                nbt.add(tmp.copy());
            }
            tag.put("claims", nbt);
            return tag;
        }
        @Override
        public void readNBT(Capability<IClaims> capability, IClaims instance, Direction side, INBT nbt)
        {
            ListNBT claims = ((CompoundNBT) nbt).getList("claims", 10);
            for (INBT pos: claims) {
                CompoundNBT tmp = (CompoundNBT) pos;
                instance.addFlag(new BlockPos(tmp.getInt("flagX"), tmp.getInt("flagY"), tmp.getInt("flagZ")));
            }
        }
    }
}
