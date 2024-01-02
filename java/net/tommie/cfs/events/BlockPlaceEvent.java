package net.tommie.cfs.events;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.tommie.cfs.block.custom.ClaimFlagBaseBlock;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.item.ClaimFlagItem;
import net.tommie.cfs.item.ModItems;
import net.tommie.cfs.tileentity.ClaimFlagTileEntity;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;

import java.util.Objects;

public class BlockPlaceEvent {
    private static void handleBlock(BlockEvent event){
        Block placedBlock = ((BlockEvent.EntityPlaceEvent) event).getPlacedBlock().getBlock();
        PlayerEntity player = ((PlayerEntity) ((BlockEvent.EntityPlaceEvent) event).getEntity());
        World world = player.getEntityWorld();
        if(!world.isRemote()) {
            ClaimsManager manager = ClaimsManager.get(player.getServer());
            if (placedBlock instanceof ClaimFlagBaseBlock) {
                ClaimFlagTileEntity te = (ClaimFlagTileEntity) event.getWorld().getTileEntity(event.getPos());
                te.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).ifPresent(h -> {
                    if (h.getPos().getY() != -65) {
                        TileEntity kingdom = event.getWorld().getTileEntity(h.getPos());
                        if (kingdom instanceof KingdomBlockTileEntity) {



                            kingdom.getCapability(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY).ifPresent(g -> {
                                ItemStack stack = ((PlayerEntity) ((BlockEvent.EntityPlaceEvent) event).getEntity()).getHeldItemMainhand();
                                CompoundNBT stackTag = stack.getTag();
                                CompoundNBT flagCopyTag = ((KingdomBlockTileEntity) kingdom).getItemHandler().getStackInSlot(0).copy().getTag();

                                CompoundNBT resultBlockEntityTag;
                                if(((KingdomBlockTileEntity) kingdom).getItemHandler().getStackInSlot(0).hasTag()) {
                                    if(flagCopyTag!=null) {
                                        if (flagCopyTag.contains("BlockEntityTag")) {
                                            resultBlockEntityTag = flagCopyTag.getCompound("BlockEntityTag");
                                        } else
                                            resultBlockEntityTag = new CompoundNBT();
                                    }
                                    else
                                        resultBlockEntityTag = new CompoundNBT();
                                }
                                else
                                    resultBlockEntityTag = new CompoundNBT();

                                resultBlockEntityTag.put("ForgeCaps", stackTag.getCompound("BlockEntityTag").getCompound("ForgeCaps"));

                                ItemStack result;
                                int count = stack.getCount();
                                switch (DyeColor.valueOf(g.getColor().toUpperCase())) {
                                    case WHITE:
                                        result = new ItemStack(ModItems.WHITE_CLAIM_FLAG.get(), count);
                                        break;
                                        case BLACK:
                                            result = new ItemStack(ModItems.BLACK_CLAIM_FLAG.get(), count);
                                            break;
                                        case RED:
                                            result = new ItemStack(ModItems.RED_CLAIM_FLAG.get(), count);
                                            break;
                                        case YELLOW:
                                            result = new ItemStack(ModItems.YELLOW_CLAIM_FLAG.get(), count);
                                            break;
                                        case ORANGE:
                                            result = new ItemStack(ModItems.ORANGE_CLAIM_FLAG.get(), count);
                                            break;
                                        case BROWN:
                                            result = new ItemStack(ModItems.BROWN_CLAIM_FLAG.get(), count);
                                            break;
                                        case BLUE:
                                            result = new ItemStack(ModItems.BLUE_CLAIM_FLAG.get(), count);
                                            break;
                                        case LIGHT_BLUE:
                                            result = new ItemStack(ModItems.LIGHT_BLUE_CLAIM_FLAG.get(), count);
                                            break;
                                        case CYAN:
                                            result = new ItemStack(ModItems.CYAN_CLAIM_FLAG.get(), count);
                                            break;
                                        case GRAY:
                                            result = new ItemStack(ModItems.GRAY_CLAIM_FLAG.get(), count);
                                            break;
                                        case LIGHT_GRAY:
                                            result = new ItemStack(ModItems.LIGHT_GRAY_CLAIM_FLAG.get(), count);
                                            break;
                                        case PURPLE:
                                            result = new ItemStack(ModItems.PURPLE_CLAIM_FLAG.get(), count);
                                            break;
                                        case PINK:
                                            result = new ItemStack(ModItems.PINK_CLAIM_FLAG.get(), count);
                                            break;
                                        case MAGENTA:
                                            result = new ItemStack(ModItems.MAGENTA_CLAIM_FLAG.get(), count);
                                            break;
                                        case GREEN:
                                            result = new ItemStack(ModItems.GREEN_CLAIM_FLAG.get(), count);
                                            break;
                                        case LIME:
                                            result = new ItemStack(ModItems.LIME_CLAIM_FLAG.get(), count);
                                            break;
                                        default:
                                            result = new ItemStack(ModItems.WHITE_CLAIM_FLAG.get(), count);
                                    }
                                    CompoundNBT resultTag = new CompoundNBT();
                                    resultTag.put("BlockEntityTag", resultBlockEntityTag);
                                    result.setTag(resultTag);



                                    player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
                                    te.getCapability(CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY).ifPresent(e -> {
                                        e.setUpkeep(te.calculateUpkeep());
                                    });
                                    manager.setClaimed(new ChunkPos(event.getPos()), h.getPos());
                                    te.replaceFlag(result);
                                    result.shrink(1);
                                    player.addItemStackToInventory(result);
                            });



                            kingdom.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(c -> {
                                c.addFlag(event.getPos());
                                kingdom.markDirty();
                            });
                        } else {
                            event.setCanceled(true);
                            ClaimFlagItem.invalidFlagHandler(player);
                        }
                    } else {
                        event.setCanceled(true);
                        ClaimFlagItem.invalidFlagHandler(player);
                    }
                    te.markDirty();
                });
            }
        }
    }
    public static void onPlaceBlockEvent(BlockEvent event)
    {
        if(event instanceof BlockEvent.EntityPlaceEvent) {
            if(!event.getWorld().isRemote())
            {
                ClaimsManager manager = ClaimsManager.get(Objects.requireNonNull(((ServerWorld)event.getWorld()).getServer()));
                if(((BlockEvent.EntityPlaceEvent) event).getEntity() instanceof PlayerEntity) {
                    ChunkPos claim = new ChunkPos(event.getPos());
                    if (manager.compassClaimer(claim)) {
                        KingdomBlockTileEntity kingdomTile = (KingdomBlockTileEntity) event.getWorld().getTileEntity(manager.getClaimant(claim).getKingdomPos());
                        kingdomTile.getCapability(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY).ifPresent(isactive->{
                            if(isactive.getKingdomIsActive()) {
                                event.getWorld().getTileEntity(manager.getClaimant(claim).getKingdomPos())
                                        .getCapability(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY).ifPresent(h -> {
                                            if(((BlockEvent.EntityPlaceEvent) event).getPlacedBlock().getBlock() instanceof ClaimFlagBaseBlock)
                                                handleBlock(event);
                                            else {
                                                if (!h.checkMember(((BlockEvent.EntityPlaceEvent) event).getEntity().getUniqueID().toString()))
                                                {
                                                    PlayerEntity player = ((PlayerEntity) ((BlockEvent.EntityPlaceEvent) event).getEntity());
                                                    if (player instanceof ServerPlayerEntity && !(player instanceof FakePlayer)) {
                                                        event.setCanceled(true);
                                                        player.sendStatusMessage(
                                                                new TranslationTextComponent("feedbackmsg.cfs.place_other_territory"), true);
                                                        ItemStack stack = player.getHeldItemMainhand();
                                                        ((ServerPlayerEntity) player).sendSlotContents(player.container, player.inventory.currentItem + 36, stack);
                                                    }
                                                }
                                            }
                                        });
                            }
                            else
                                handleBlock(event);
                        });
                    } else
                        handleBlock(event);
                }
            }
        }
    }
}
