package net.tommie.cfs.capabilities;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.List;

public class CapabilityMembers {
    @CapabilityInject(IMembers.class)
    public static Capability<IMembers> KINGDOM_MEMBERS_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IMembers.class, new Storage(), DefaultMembers::new);
    }
    public static class Storage implements Capability.IStorage<IMembers>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IMembers> capability, IMembers instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            List<String> members = instance.getMembers();
            CompoundNBT tmp = new CompoundNBT();
            ListNBT nbt = new ListNBT();
            for (String member: members) {
                tmp.putString("Member", member);
                nbt.add(tmp.copy());
            }
            tag.put("members", nbt);
            return tag;
        }
        @Override
        public void readNBT(Capability<IMembers> capability, IMembers instance, Direction side, INBT nbt)
        {
            ListNBT members = ((CompoundNBT) nbt).getList("members", 10);
            for (INBT member: members) {
                instance.addMember(((CompoundNBT) member).getString("Member"));
            }
        }
    }
}
