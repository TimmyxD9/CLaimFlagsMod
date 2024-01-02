package net.tommie.cfs.events;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.Objects;

public class BlockBreakEvent {
    public static void onDestroyBlockEvent(BlockEvent event)
    {
        if(event instanceof BlockEvent.BreakEvent){
            if(!event.getWorld().isRemote())
            {
                    ChunkPos claimed = new ChunkPos(event.getPos());
                    ClaimsManager manager = ClaimsManager.get(Objects.requireNonNull(((ServerWorld) event.getWorld()).getServer()));
                    if (manager.checkClaimed(claimed)) {
                        KingdomBlockTileEntity kingdomTile = (KingdomBlockTileEntity) event.getWorld().getTileEntity(manager.getClaimant(claimed).getKingdomPos());
                        kingdomTile.getCapability(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY).ifPresent(active->{
                            if(active.getKingdomIsActive()) {
                                kingdomTile.getCapability(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY).ifPresent(h -> {
                                    if (!h.checkMember(((BlockEvent.BreakEvent) event).getPlayer().getUniqueID().toString())) {
                                        event.setCanceled(true);
                                        ((BlockEvent.BreakEvent) event).getPlayer().sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.break_other_territory"), true);
                                    }
                                });
                            }
                        });
                }
            }
        }
    }
}
