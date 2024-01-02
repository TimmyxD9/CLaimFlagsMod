package net.tommie.cfs.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tommie.cfs.capabilities.ClaimsManager;

import java.util.Objects;

public class CompassItem extends Item {
    public CompassItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        World world = context.getWorld();
        if(!world.isRemote()) {
            ClaimsManager manager = ClaimsManager.get(Objects.requireNonNull(context.getWorld().getServer()));
            if (!world.isRemote()) {
                PlayerEntity player = Objects.requireNonNull(context.getPlayer());
                if (manager.compassClaimer(new ChunkPos(context.getPlayer().getPosition())))
                    player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.is_claimed"), true);
                else
                    player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.not_claimed"), true);
            }
        }
        return ActionResultType.SUCCESS;
    }

}
