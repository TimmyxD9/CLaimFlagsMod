package net.tommie.cfs.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.tommie.cfs.ClaimFlags;

public class SubscribeEvents {
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event){
        if(!event.getMap().getTextureLocation().equals("texutres"))
            return;
        event.addSprite(new ResourceLocation(ClaimFlags.MOD_ID,"block/claim_flag"));
    }
}
