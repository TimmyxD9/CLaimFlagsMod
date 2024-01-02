package net.tommie.cfs.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.tommie.cfs.ClaimFlags;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

public class AttachCapabilitiesHandler {
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof PlayerEntity){
            ProviderLastUseTimer lastUseProvider = new ProviderLastUseTimer();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "lastuse"), lastUseProvider);
            event.addListener(lastUseProvider::invalidate);
        }
    }
    public static void onAttachTileEntityCapabilitiesEvent(AttachCapabilitiesEvent<TileEntity> event)
    {
        if(event.getObject() instanceof KingdomBlockTileEntity){
            OwnerProvider provider = new OwnerProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "owner"), provider);
            event.addListener(provider::invalidate);

            MembersProvider membersProvider = new MembersProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "members"), membersProvider);
            event.addListener(membersProvider::invalidate);

            ProviderMemberNames providerMemberNames = new ProviderMemberNames();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "membernames"), providerMemberNames);
            event.addListener(providerMemberNames::invalidate);

            ClaimsProvider claimsProvider = new ClaimsProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "claims"), claimsProvider);
            event.addListener(claimsProvider::invalidate);

            KingdomIsActiveProvider activeProvider = new KingdomIsActiveProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "active"), activeProvider);
            event.addListener(activeProvider::invalidate);

            ProviderPatterns patternsProvider = new ProviderPatterns();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "patterns"), patternsProvider);
            event.addListener(patternsProvider::invalidate);

        }
        if(event.getObject() instanceof ClaimFlagTileEntity)
        {
            KingdomPosProvider kprovider = new KingdomPosProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "kpos"), kprovider);
            event.addListener(kprovider::invalidate);

            UpkeepProvider upkeepProvider = new UpkeepProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "upkeep"), upkeepProvider);
            event.addListener(upkeepProvider::invalidate);

            HomeProvider homeProvider = new HomeProvider();
            event.addCapability(new ResourceLocation(ClaimFlags.MOD_ID, "home"), homeProvider);
            event.addListener(homeProvider::invalidate);
        }
    }
}
