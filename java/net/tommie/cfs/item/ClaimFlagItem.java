package net.tommie.cfs.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.config.ClaimFlagsConfig;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.concurrent.atomic.AtomicReference;

public class ClaimFlagItem extends BannerItem {
    public static void invalidFlagHandler(PlayerEntity player){
        player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.invalid_flag"), true);
        player.setHeldItem(Hand.MAIN_HAND,ItemStack.EMPTY);

    }
    public ClaimFlagItem(Block standingBlock, Block wallBlock, Properties properties) {
        super(standingBlock, wallBlock, properties);
    }
    @Override
    public ActionResultType onItemUse(ItemUseContext context){
        World world = context.getWorld();
        AtomicReference<Boolean> skipFlagPlacement = new AtomicReference<>(false);
        PlayerEntity player = context.getPlayer();
        if(!world.isRemote()) {
            if(context.getHand().equals(Hand.MAIN_HAND)) {
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.hasTag()) {
                    if (stack.getTag().contains("BlockEntityTag")) {
                        if (stack.getTag().getCompound("BlockEntityTag").contains("ForgeCaps")) {
                            if (stack.getTag().getCompound("BlockEntityTag").getCompound("ForgeCaps").contains("cfs:kpos")) {
                                CompoundNBT tag = stack.getTag().getCompound("BlockEntityTag").getCompound("ForgeCaps");
                                CompoundNBT tagPos = tag.getCompound("cfs:kpos");
                                BlockPos kingdomPos = new BlockPos(tagPos.getInt("kingdomX"), tagPos.getInt("kingdomY"), tagPos.getInt("kingdomZ"));
                                String owner = tag.getCompound("cfs:home").getString("home");
                                if (world.getTileEntity(kingdomPos) instanceof KingdomBlockTileEntity) {
                                    world.getTileEntity(kingdomPos).getCapability(CapabilityOwner.KINGDOM_OWNER_CAPABILITY).ifPresent(d -> {
                                        if (owner.equals(d.getOwnerId())) {
                                            player.getCapability(CapabilityLastUseTImer.PLAYER_LAST_USE_FLAG_CAPABILITY).ifPresent(h -> {
                                                BlockPos pos = new BlockPos(context.getHitVec());
                                                ClaimsManager manager = ClaimsManager.get(player.getServer());
                                                if (manager.checkClaimed(new ChunkPos(pos))) {
                                                    skipFlagPlacement.set(true);
                                                    if (Double.compare(world.getGameTime() - 8, h.getLastUseTimer()) > 0)
                                                        {
                                                            h.setFirstUseTImer(context.getWorld().getGameTime());
                                                            h.setOverclaimingChunk(new ChunkPos(pos));
                                                        }
                                                    h.setLastUseTimer(context.getWorld().getGameTime());
                                                    if(!(new ChunkPos(pos)).equals(h.getOverclaimingChunk()))
                                                    {
                                                        h.setFirstUseTImer(context.getWorld().getGameTime());
                                                        h.setOverclaimingChunk(new ChunkPos(pos));
                                                    }
                                                    if (Double.compare(h.getLastUseTimer() - h.getFirstUseTimer(), ClaimFlagsConfig.claim_health.get() * 20) > 0) {
                                                        skipFlagPlacement.set(false);
                                                        h.setFirstUseTImer(context.getWorld().getGameTime());
                                                        h.setOverclaimingChunk(new ChunkPos(pos));
                                                    } else {
                                                        player.sendStatusMessage(new TranslationTextComponent(((new TranslationTextComponent("feedbackmsg.cfs.overclaim_progress")).getString() + String.valueOf(((float) (h.getLastUseTimer() - h.getFirstUseTimer())) / ClaimFlagTileEntity.claimHealth / 20 * 100).substring(0,2) + "%")), true);
                                                    }
                                                }
                                                ((ServerPlayerEntity) player).sendSlotContents(player.container, player.inventory.currentItem + 36, stack);
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                player.sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.offhand_claim"), true);
                skipFlagPlacement.set(true);
            }

        }
        if (skipFlagPlacement.get())
            return ActionResultType.PASS;
        else
            return super.onItemUse(context);
    }
}
