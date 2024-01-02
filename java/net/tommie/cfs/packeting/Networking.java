package net.tommie.cfs.packeting;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.tommie.cfs.packets.*;
import net.tommie.cfs.ClaimFlags;

public class Networking {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ClaimFlags.MOD_ID, "itemcond"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(),
                PurchasePacket.class,
                PurchasePacket::toBytes,
                PurchasePacket::new,
                PurchasePacket::handle);

        INSTANCE.registerMessage(nextID(),
                GetClaimsPacket.class,
                GetClaimsPacket::toBytes,
                GetClaimsPacket::new,
                GetClaimsPacket::handle);

        INSTANCE.registerMessage(nextID(),
                KickMemberPacket.class,
                KickMemberPacket::toBytes,
                KickMemberPacket::new,
                KickMemberPacket::handle);

        INSTANCE.registerMessage(nextID(),
                SetKingdomPatternsAndColourPacket.class,
                SetKingdomPatternsAndColourPacket::toBytes,
                SetKingdomPatternsAndColourPacket::new,
                SetKingdomPatternsAndColourPacket::handle);

        INSTANCE.registerMessage(nextID(),
                ReplaceFlagPacket.class,
                ReplaceFlagPacket::toBytes,
                ReplaceFlagPacket::new,
                ReplaceFlagPacket::handle);
    }
}