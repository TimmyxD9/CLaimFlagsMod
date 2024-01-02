package net.tommie.cfs.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.function.Supplier;

public class PurchasePacket {
    private final BlockPos pos;

    public PurchasePacket(PacketBuffer buf) {
        pos = buf.readBlockPos();
    }
    public PurchasePacket(BlockPos pos) {
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World spawnWorld = ctx.get().getSender().world;
            TileEntity te = spawnWorld.getTileEntity(pos);
            ((KingdomBlockTileEntity) te).purchaseFlag();
        });
        ctx.get().setPacketHandled(true);
    }
}
