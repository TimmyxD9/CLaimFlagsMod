package net.tommie.cfs.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityKingdomPos {
    @CapabilityInject(IKingdomPos.class)
    public static Capability<IKingdomPos> KINGDOM_POS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IKingdomPos.class, new CapabilityKingdomPos.Storage(), DefaultKingdomPos::new);
    }
    public static class Storage implements Capability.IStorage<IKingdomPos>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IKingdomPos> capability, IKingdomPos instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            tag.putInt("kingdomX", instance.getX());
            tag.putInt("kingdomY", instance.getY());
            tag.putInt("kingdomZ", instance.getZ());
            return tag;
        }
        @Override
        public void readNBT(Capability<IKingdomPos> capability, IKingdomPos instance, Direction side, INBT nbt)
        {
            BlockPos pos = new BlockPos(((CompoundNBT) nbt).getInt("kingdomX"), ((CompoundNBT) nbt).getInt("kingdomY"), ((CompoundNBT) nbt).getInt("kingdomZ"));
            instance.setPos(pos);
        }
    }
}
