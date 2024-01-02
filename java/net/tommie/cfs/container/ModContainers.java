package net.tommie.cfs.container;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tommie.cfs.ClaimFlags;

import java.util.ArrayList;
import java.util.List;


public class ModContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ClaimFlags.MOD_ID);

    public static final RegistryObject<ContainerType<KingdomBlockContainer>> KINGDOM_BLOCK_CONTAINER = CONTAINERS.register("kingdom_block_container", () -> IForgeContainerType.create(((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        World world = inv.player.getEntityWorld();
        CompoundNBT nbt = data.readCompoundTag();
        ListNBT membersList = nbt.getList("members", 10);
        ListNBT claimsList = nbt.getList("claims",10);
        ListNBT memberNamesList = nbt.getList("membernames", 10);
        List<String> members = new ArrayList<>();
        List<String> memberNames = new ArrayList<>();
        //inv.player.sendMessage(new TranslationTextComponent(nbt.toString()), inv.player.getUniqueID());
        List<BlockPos> claims = new ArrayList<>();
        for(INBT member: membersList)
        {
            members.add(((CompoundNBT) member).getString("Member"));
        }
        for(INBT memberName: memberNamesList)
        {
            memberNames.add(((CompoundNBT) memberName).getString("MemberName"));
        }
        for(INBT claim: claimsList){
            claims.add(new BlockPos(((CompoundNBT) claim).getInt("flagX"),((CompoundNBT) claim).getInt("flagY"),((CompoundNBT) claim).getInt("flagZ")));
        }
        return new KingdomBlockContainer(windowId, world, pos, inv, inv.player, members, claims, memberNames);
    })));
    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }

}
