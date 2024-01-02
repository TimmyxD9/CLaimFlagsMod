package net.tommie.cfs.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tommie.cfs.capabilities.ClaimsManager;
import net.tommie.cfs.capabilities.PlayerNameOwnerCache;

public class KingdomBlockItem extends BlockItem {
    public KingdomBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if(!world.isRemote()) {
            ClaimsManager manager = ClaimsManager.get(world.getServer());
            PlayerEntity player = context.getPlayer();
            if (manager.checkClaimed(new ChunkPos(context.getPos()))) {
                player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.kingdom_other_territory"), true);
                return ActionResultType.PASS;
            }
            if(PlayerNameOwnerCache.getCache(world.getServer()).checkOwner(player.getUniqueID().toString()))
            {
                player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.kingdom_ruler"), true);
                return ActionResultType.PASS;
            }
            return super.onItemUse(context);
        }
        return ActionResultType.SUCCESS;
    }
}
