package net.tommie.cfs.packets;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;

import java.util.function.Supplier;

public class ReplaceFlagPacket {
    private final BlockPos pos;
    private final ItemStack stack;
    public ReplaceFlagPacket(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.stack = buf.readItemStack();
    }
    public ReplaceFlagPacket(BlockPos pos, ItemStack stack) {
        this.pos = pos;
        this.stack = stack;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeItemStack(stack);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world = ctx.get().getSender().getEntityWorld();
            if(world.getTileEntity(pos) instanceof ClaimFlagTileEntity)
            {
                ClaimFlagTileEntity te = (ClaimFlagTileEntity) world.getTileEntity(pos);
                te.replaceFlag(stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
