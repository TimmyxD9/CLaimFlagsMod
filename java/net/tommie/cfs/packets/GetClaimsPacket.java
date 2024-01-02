package net.tommie.cfs.packets;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tommie.cfs.capabilities.CapabilityClaims;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class GetClaimsPacket {
    private final BlockPos pos;

    public GetClaimsPacket(PacketBuffer buf) {
        pos = buf.readBlockPos();
    }
    public GetClaimsPacket(BlockPos pos) {
        this.pos = pos;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            TileEntity te = ctx.get().getSender().world.getTileEntity(pos);
            if(te instanceof KingdomBlockTileEntity) {
                KingdomBlockTileEntity tmp = (KingdomBlockTileEntity) te;
                tmp.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(h->{
                    PlayerEntity player = ctx.get().getSender();
                    AtomicReference<String> command = new AtomicReference<>("");
                    TranslationTextComponent text = new TranslationTextComponent("");
                    List<BlockPos> claims = h.getFlags();
                    for (BlockPos claim : claims) {
                        String coords = String.valueOf(claim.getX()) + " " + String.valueOf(claim.getY()) + " " + String.valueOf(claim.getZ());
                        TranslationTextComponent component = new TranslationTextComponent("[" + coords + "]");
                        command.set("/tp " + player.getName().getString() + " " + coords);
                        component.setStyle(Style.EMPTY.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command.toString())).setColor(Color.fromHex("00FF00")));
                        text.appendSibling(component);
                    }
                    player.sendMessage(text, player.getUniqueID());
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
