package net.tommie.cfs.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tommie.cfs.capabilities.CapabilityMemberNames;
import net.tommie.cfs.capabilities.CapabilityMembers;

import java.util.function.Supplier;

public class KickMemberPacket {
    private final BlockPos pos;
    private final String id;
    private final String name;
    public KickMemberPacket(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.id = buf.readString();
        this.name = buf.readString();
    }
    public KickMemberPacket(BlockPos pos, String id, String name) {
        this.pos = pos;
        this.id = id;
        this.name = name;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeString(id);
        buf.writeString(name);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().getEntityWorld();
            world.getTileEntity(pos).getCapability(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY).ifPresent(h->{
                h.removeMember(id);
            });
            world.getTileEntity(pos).getCapability(CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY).ifPresent(c->{
                c.removeMemberName(name);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
