package net.tommie.cfs.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.tommie.cfs.capabilities.CapabilityClaims;
import net.tommie.cfs.capabilities.CapabilityKingdomPos;
import net.tommie.cfs.capabilities.CapabilityUpkeep;
import net.tommie.cfs.capabilities.ClaimsManager;
import net.tommie.cfs.config.ClaimFlagsConfig;
import net.tommie.cfs.item.ClaimFlagItem;
import net.tommie.cfs.item.ModItems;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.sqrt;
import static net.minecraft.block.BannerBlock.ROTATION;

public class ClaimFlagTileEntity extends BannerTileEntity {

    private static final int cost = ClaimFlagsConfig.rent_cost.get();
    public static final int claimHealth = ClaimFlagsConfig.claim_health.get();

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
    }

    public ClaimFlagTileEntity() {
        super();
    }

    public ClaimFlagTileEntity(DyeColor color) {
        super(color);
    }

    @Nonnull
    @Override
    public TileEntityType<?> getType() {
        return ModTileEntities.CLAIM_FLAGS_TILE_ENTITIES.get();
    }

    public int calculateUpkeep() {
        AtomicReference<BlockPos> kingdomPos = new AtomicReference<>(new BlockPos(0, 0, 0));
        this.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).ifPresent(h -> {
            kingdomPos.set(h.getPos());
        });
        return cost + cost * ((int) sqrt(kingdomPos.get().distanceSq(this.getPos()))) / ClaimFlagsConfig.pay_rent_distance.get();
    }

    public void replaceFlag(ItemStack stack) {
        if (!world.isRemote()) {
            if (stack.getItem() instanceof BannerItem) {
                ClaimFlagItem flagItem;
                DyeColor color = ((BannerItem) stack.getItem()).getColor();
                World world = this.getWorld();
                ClaimsManager manager = ClaimsManager.get(world.getServer());

                switch (color) {
                    case WHITE:
                        flagItem = (ClaimFlagItem) ModItems.WHITE_CLAIM_FLAG.get();
                        break;
                    case BLACK:
                        flagItem = (ClaimFlagItem) ModItems.BLACK_CLAIM_FLAG.get();
                        break;
                    case RED:
                        flagItem = (ClaimFlagItem) ModItems.RED_CLAIM_FLAG.get();
                        break;
                    case YELLOW:
                        flagItem = (ClaimFlagItem) ModItems.YELLOW_CLAIM_FLAG.get();
                        break;
                    case ORANGE:
                        flagItem = (ClaimFlagItem) ModItems.ORANGE_CLAIM_FLAG.get();
                        break;
                    case BROWN:
                        flagItem = (ClaimFlagItem) ModItems.BROWN_CLAIM_FLAG.get();
                        break;
                    case BLUE:
                        flagItem = (ClaimFlagItem) ModItems.BLUE_CLAIM_FLAG.get();
                        break;
                    case LIGHT_BLUE:
                        flagItem = (ClaimFlagItem) ModItems.LIGHT_BLUE_CLAIM_FLAG.get();
                        break;
                    case CYAN:
                        flagItem = (ClaimFlagItem) ModItems.CYAN_CLAIM_FLAG.get();
                        break;
                    case GRAY:
                        flagItem = (ClaimFlagItem) ModItems.GRAY_CLAIM_FLAG.get();
                        break;
                    case LIGHT_GRAY:
                        flagItem = (ClaimFlagItem) ModItems.LIGHT_GRAY_CLAIM_FLAG.get();
                        break;
                    case PURPLE:
                        flagItem = (ClaimFlagItem) ModItems.PURPLE_CLAIM_FLAG.get();
                        break;
                    case PINK:
                        flagItem = (ClaimFlagItem) ModItems.PINK_CLAIM_FLAG.get();
                        break;
                    case MAGENTA:
                        flagItem = (ClaimFlagItem) ModItems.MAGENTA_CLAIM_FLAG.get();
                        break;
                    case GREEN:
                        flagItem = (ClaimFlagItem) ModItems.GREEN_CLAIM_FLAG.get();
                        break;
                    case LIME:
                        flagItem = (ClaimFlagItem) ModItems.LIME_CLAIM_FLAG.get();
                        break;
                    default:
                        flagItem = (ClaimFlagItem) ModItems.WHITE_CLAIM_FLAG.get();
                }

                ClaimFlagTileEntity oldFlagEntity = (ClaimFlagTileEntity) world.getTileEntity(pos);
                oldFlagEntity.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).ifPresent(ofkp -> {
                    BlockPos kpos = ofkp.getPos();

                    int value = world.getBlockState(pos).get(ROTATION);
                    world.removeBlock(pos, true);
                    world.setBlockState(pos, flagItem.getBlock().getDefaultState().with(ROTATION, value));
                    ClaimFlagTileEntity newFlagEntity = (ClaimFlagTileEntity) world.getTileEntity(pos);
                    newFlagEntity.getCapability(CapabilityKingdomPos.KINGDOM_POS_CAPABILITY).ifPresent(c -> {
                        c.setPos(kpos);
                        newFlagEntity.getCapability(CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY).ifPresent(upk -> {
                            upk.setUpkeep(newFlagEntity.calculateUpkeep());
                        });
                        if (stack.hasTag()) {
                            BlockItem.setTileEntityNBT(world, null, pos, stack);
                        }
                        world.getTileEntity(c.getPos()).getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(h -> {
                            h.addFlag(pos);
                        });
                        manager.setClaimed(new ChunkPos(pos), c.getPos());
                    });
                    newFlagEntity.markDirty();
                });
            }
        }
    }

}
