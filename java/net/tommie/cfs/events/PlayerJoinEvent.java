package net.tommie.cfs.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.tommie.cfs.capabilities.PlayerNameOwnerCache;

public class PlayerJoinEvent {
    public static void onPlayerJoinEvent(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof PlayerEntity)
        {
            if(!event.getWorld().isRemote()) {
                PlayerNameOwnerCache cache = PlayerNameOwnerCache.getCache(event.getWorld().getServer());
                PlayerEntity player = (PlayerEntity) event.getEntity();
                cache.addPlayer(player.getUniqueID().toString(), player.getName().getString());
            }
        }
    }
}
