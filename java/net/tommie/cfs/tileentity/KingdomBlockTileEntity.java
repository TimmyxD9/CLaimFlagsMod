package net.tommie.cfs.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.config.ClaimFlagsConfig;
import net.tommie.cfs.item.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.util.math.MathHelper.clamp;

public class KingdomBlockTileEntity extends TileEntity implements ITickableTileEntity {
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(()-> itemHandler);
    private final int CRITICAL_GOLD_AMOUNT = 64;
    public KingdomBlockTileEntity(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }
    public KingdomBlockTileEntity(){
        this(ModTileEntities.KINGDOM_BLOCK_TILE_ENTITY.get());
    }

    private int ticker = clamp(ClaimFlagsConfig.pay_rent_period.get(),30,1200)*20;

    public ItemStackHandler getItemHandler(){return this.itemHandler;}

    @Override
    public void read(BlockState state, CompoundNBT nbt){
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound){
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(11){
            @Override
            protected void onContentsChanged(int slot){
                markDirty();
            }
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0:
                            return (stack.getItem() instanceof BannerItem);
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        return stack.getItem() == Items.GOLD_NUGGET;
                    case 10:
                        return false;
                    default:
                        return false;
                }
            }

            @Override
            public int getSlotLimit(int slot)
            {
                switch(slot) {
                    case 0:
                        return 1;
                    default:
                        return 64;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate){
                if(!isItemValid(slot, stack)){
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    };
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public boolean hasGoldInInv(){
        if(getTotalGold()>0)
            return true;
        else
            return false;
    }
    public int getTotalGold(){
        int total = 0;
        for(int i=1; i<10; i++)
            total+= this.itemHandler.getStackInSlot(i).getCount();
        return total;
    }
    public void purchaseFlag(){
        if(!this.itemHandler.getStackInSlot(0).isEmpty()) {
            if (getTotalGold() >= 5) {
                ItemStack bannerStack = this.itemHandler.getStackInSlot(0);
                ItemStack result;
                switch (((BannerItem) bannerStack.getItem()).getColor()){
                    case WHITE:
                        result = new ItemStack(ModItems.WHITE_CLAIM_FLAG.get());
                        break;
                    case BLACK:
                        result = new ItemStack(ModItems.BLACK_CLAIM_FLAG.get());
                        break;
                    case RED:
                        result = new ItemStack(ModItems.RED_CLAIM_FLAG.get());
                        break;
                    case YELLOW:
                        result = new ItemStack(ModItems.YELLOW_CLAIM_FLAG.get());
                        break;
                    case ORANGE:
                        result = new ItemStack(ModItems.ORANGE_CLAIM_FLAG.get());
                        break;
                    case BROWN:
                        result = new ItemStack(ModItems.BROWN_CLAIM_FLAG.get());
                        break;
                    case BLUE:
                        result = new ItemStack(ModItems.BLUE_CLAIM_FLAG.get());
                        break;
                    case LIGHT_BLUE:
                        result = new ItemStack(ModItems.LIGHT_BLUE_CLAIM_FLAG.get());
                        break;
                    case CYAN:
                        result = new ItemStack(ModItems.CYAN_CLAIM_FLAG.get());
                        break;
                    case GRAY:
                        result = new ItemStack(ModItems.GRAY_CLAIM_FLAG.get());
                        break;
                    case LIGHT_GRAY:
                        result = new ItemStack(ModItems.LIGHT_GRAY_CLAIM_FLAG.get());
                        break;
                    case PURPLE:
                        result = new ItemStack(ModItems.PURPLE_CLAIM_FLAG.get());
                        break;
                    case PINK:
                        result = new ItemStack(ModItems.PINK_CLAIM_FLAG.get());
                        break;
                    case MAGENTA:
                        result = new ItemStack(ModItems.MAGENTA_CLAIM_FLAG.get());
                        break;
                    case GREEN:
                        result = new ItemStack(ModItems.GREEN_CLAIM_FLAG.get());
                        break;
                    case LIME:
                        result = new ItemStack(ModItems.LIME_CLAIM_FLAG.get());
                        break;
                    default:
                        result = new ItemStack(ModItems.WHITE_CLAIM_FLAG.get());
                }

                CompoundNBT baseTag = new CompoundNBT();
                if(bannerStack.hasTag())
                    baseTag = bannerStack.getTag().copy().getCompound("BlockEntityTag");
                CompoundNBT kingdomPos = new CompoundNBT();
                CompoundNBT capabilities = new CompoundNBT();
                CompoundNBT resultingTag = new CompoundNBT();
                CompoundNBT home = new CompoundNBT();
                kingdomPos.putInt("kingdomX", this.getPos().getX());
                kingdomPos.putInt("kingdomY", this.getPos().getY());
                kingdomPos.putInt("kingdomZ", this.getPos().getZ());
                capabilities.put("cfs:kpos", kingdomPos);
                this.getCapability(CapabilityOwner.KINGDOM_OWNER_CAPABILITY).ifPresent(h->{
                    home.putString("home", h.getOwnerId());
                    capabilities.put("cfs:home", home);
                });
                baseTag.put("ForgeCaps",capabilities);
                resultingTag.put("BlockEntityTag", baseTag);
                result.setTag(resultingTag);
                if(this.itemHandler.getStackInSlot(10).getCount()<16) {
                    if (this.itemHandler.getStackInSlot(10).isEmpty())
                        this.itemHandler.setStackInSlot(10, result);
                    else
                        this.itemHandler.getStackInSlot(10).grow(1);
                    withdraw(5);
                }
            }
        }
    }

    public void withdraw(int amount) {
        if (getTotalGold() >= amount) {
            int i = 1;
            while (amount > 0) {
                if (amount <= this.itemHandler.getStackInSlot(i).getCount())
                {
                    this.itemHandler.getStackInSlot(i).shrink(amount);
                    break;
                }
                else
                {
                    amount-=this.itemHandler.getStackInSlot(i).getCount();
                    this.itemHandler.getStackInSlot(i).shrink(this.itemHandler.getStackInSlot(i).getCount());
                    i++;
                }
            }
        }
    }

    public int calculateRent(){
        AtomicReference<AtomicInteger> result = new AtomicReference<>(new AtomicInteger(ClaimFlagsConfig.kingdom_upkeep.get()));
        this.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(h -> {
            List<BlockPos> flags = h.getFlags();
            World world = this.getWorld();
            for (BlockPos flag : flags) {
                world.getTileEntity(flag).getCapability(CapabilityUpkeep.TERRITORY_UPKEEP_CAPABILITY).ifPresent(c->{
                    result.set(new AtomicInteger(result.get().intValue() + c.getUpkeep()));
                });
            }
        });
        return result.get().intValue();
    }
    public void payRent(){
        int amount = this.calculateRent();
        if (this.getTotalGold() >= amount) {
            this.withdraw(amount);
            this.getCapability(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY).ifPresent(h -> {
                h.setKingdomIsActive(true);
            });
        } else {
            this.getCapability(CapabilityKingdomIsActive.KINGDOM_IS_ACTIVE_CAPABILITY).ifPresent(h -> {
                h.setKingdomIsActive(false);
            });
        }
        warnOwnerGoldStatus();
    }

    private void warnOwnerGoldStatus(){
        World worldIn = this.getWorld();
        if(!worldIn.isRemote()) {
            if(this.getTotalGold()<=CRITICAL_GOLD_AMOUNT) {
                String[] playerList = worldIn.getServer().getOnlinePlayerNames();
                if (playerList != null) {
                    PlayerNameOwnerCache cache = PlayerNameOwnerCache.getCache(worldIn.getServer());
                    this.getCapability(CapabilityOwner.KINGDOM_OWNER_CAPABILITY).ifPresent(owner -> {
                        for (int i = 0; i < playerList.length; i++) {
                            if (playerList[i].equals(cache.getPlayerName(owner.getOwnerId()))) {
                                worldIn.getPlayerByUuid(UUID.fromString(owner.getOwnerId())).sendStatusMessage(new TranslationTextComponent("feedbackmsg.cfs.lowgoldwarning"), true);
                                break;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void tick() {
        if(!world.isRemote()) {
            if (ticker == 0) {
                ticker = ClaimFlagsConfig.pay_rent_period.get()*20;
                this.payRent();
            } else ticker--;
            this.markDirty();
        }
   }
}
