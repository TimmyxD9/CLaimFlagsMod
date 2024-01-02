package net.tommie.cfs.capabilities;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.List;

public class CapabilityMemberNames {
    @CapabilityInject(IMemberNames.class)
    public static Capability<IMemberNames> KINGDOM_MEMBER_NAMES_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IMemberNames.class, new Storage(), DefaultMemberNames::new);
    }
    public static class Storage implements Capability.IStorage<IMemberNames>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IMemberNames> capability, IMemberNames instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            List<String> members = instance.getMemberNames();
            CompoundNBT tmp = new CompoundNBT();
            ListNBT nbt = new ListNBT();
            for (String member: members) {
                tmp.putString("MemberName", member);
                nbt.add(tmp.copy());
            }
            tag.put("membernames", nbt);
            return tag;
        }
        @Override
        public void readNBT(Capability<IMemberNames> capability, IMemberNames instance, Direction side, INBT nbt)
        {
            ListNBT members = ((CompoundNBT) nbt).getList("membernames", 10);
            for (INBT member: members) {
                instance.addMemberName(((CompoundNBT) member).getString("MemberName"));
            }
        }
    }
}
