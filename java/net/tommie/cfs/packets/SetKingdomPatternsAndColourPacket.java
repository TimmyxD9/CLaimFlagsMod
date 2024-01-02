package net.tommie.cfs.packets;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tommie.cfs.capabilities.CapabilityPatterns;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.function.Supplier;

public class SetKingdomPatternsAndColourPacket {
    private final BlockPos kingdomPos;
    private final ListNBT patterns;
    private final String color;
    public SetKingdomPatternsAndColourPacket(PacketBuffer buf) {
        this.kingdomPos = buf.readBlockPos();
        this.patterns=(buf.readCompoundTag()).getList("patterns",10);
        this.color=buf.readString().toUpperCase();
    }
    public SetKingdomPatternsAndColourPacket(BlockPos pos, ListNBT patterns, String color) {
        this.kingdomPos = pos;
        this.patterns=patterns;
        this.color = color;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(kingdomPos);
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("patterns", patterns);
        buf.writeCompoundTag(nbt);
        buf.writeString(color);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().getEntityWorld();
            if(world.getTileEntity(kingdomPos) instanceof KingdomBlockTileEntity)
            {
                KingdomBlockTileEntity kingdom = (KingdomBlockTileEntity) world.getTileEntity(kingdomPos);
                kingdom.getCapability(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY).ifPresent(p->{
                    p.setPatterns(patterns);
                    p.setColor(color);
                });
                kingdom.markDirty();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
