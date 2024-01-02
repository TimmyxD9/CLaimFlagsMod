package net.tommie.cfs.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.tommie.cfs.block.ModBlocks;
import net.tommie.cfs.capabilities.CapabilityPatterns;
import net.tommie.cfs.packeting.Networking;
import net.tommie.cfs.packets.ReplaceFlagPacket;
import net.tommie.cfs.packets.SetKingdomPatternsAndColourPacket;

import java.util.ArrayList;
import java.util.List;

public class KingdomBlockContainer extends Container {

    private final TileEntity tileEntity;
    private final PlayerEntity playerEntity;
    private final IItemHandler playerInventory;
    public List<String> members = new ArrayList<>();
    public List<String> memberNames = new ArrayList<>();
    private List<Slot> slots = new ArrayList<>();
    private String owner = "";
    private Slot bannerSLot;
    private List<BlockPos> claimsList = new ArrayList<>();
    public KingdomBlockContainer(int windowid, World worldIn, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainers.KINGDOM_BLOCK_CONTAINER.get(), windowid);
        this.tileEntity = worldIn.getTileEntity(pos);
        this.playerEntity=player;
        this.playerInventory= new InvWrapper(playerInventory);
        layoutPlayerInventorySlots(8, 86);

        if(tileEntity!=null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->{
                bannerSLot = addSlot(new SlotItemHandler(h, 0 , 8, 21){
                    @Override
                    public void onSlotChanged(){
                        super.onSlotChanged();
                        if(this.getHasStack())
                        {
                            if(claimsList.size()>1)
                            {
                                for(BlockPos claim: claimsList.subList(1,claimsList.size()))
                                {
                                    Networking.INSTANCE.sendToServer(new ReplaceFlagPacket(claim,bannerSLot.getStack()));
                                }
                                tileEntity.getCapability(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY).ifPresent(c->{
                                        CompoundNBT tag = new CompoundNBT();
                                        ItemStack stack = bannerSLot.getStack();
                                        ListNBT list = new ListNBT();
                                        if(stack.hasTag())
                                        {
                                            tag = stack.getTag();
                                        }
                                        if(tag.contains("BlockEntityTag")) {
                                            CompoundNBT nbt = tag.getCompound("BlockEntityTag");
                                            if(nbt.contains("Patterns"))
                                                list = nbt.getList("Patterns", 10);
                                        }
                                        Networking.INSTANCE.sendToServer(new SetKingdomPatternsAndColourPacket(pos, list,((BannerItem) bannerSLot.getStack().getItem()).getColor().toString()));
                                });
                            }
                        }
                    }});
                slots.add(bannerSLot);
                slots.add(addSlot(new SlotItemHandler(h,1,8,55)));
                slots.add(addSlot(new SlotItemHandler(h,2,26,55)));
                slots.add(addSlot(new SlotItemHandler(h,3,44,55)));
                slots.add(addSlot(new SlotItemHandler(h,4,62,55)));
                slots.add(addSlot(new SlotItemHandler(h,5,80,55)));
                slots.add(addSlot(new SlotItemHandler(h,6,98,55)));
                slots.add(addSlot(new SlotItemHandler(h,7,116,55)));
                slots.add(addSlot(new SlotItemHandler(h,8,134,55)));
                slots.add(addSlot(new SlotItemHandler(h,9,152,55)));

                addSlot(new SlotItemHandler(h, 10 , 116, 12));
            });


        }
    }


    public KingdomBlockContainer(int windowid, World worldIn, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, List<String> members, List<BlockPos> claims, List<String> memberNames) {
        super(ModContainers.KINGDOM_BLOCK_CONTAINER.get(), windowid);
        this.tileEntity = worldIn.getTileEntity(pos);
        this.playerEntity=player;
        this.playerInventory= new InvWrapper(playerInventory);
        this.members = members;
        this.memberNames = memberNames;
        this.claimsList = claims;
        this.owner = members.get(0);
        layoutPlayerInventorySlots(8, 86);

        if(tileEntity!=null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->{
                bannerSLot = addSlot(new SlotItemHandler(h, 0 , 8, 22){
                    @Override
                    public void onSlotChanged(){
                        super.onSlotChanged();
                        if(this.getHasStack())
                        {
                            if(claimsList.size()>1)
                                for(BlockPos claim:claimsList.subList(1,claimsList.size())){
                                    Networking.INSTANCE.sendToServer(new ReplaceFlagPacket(claim,bannerSLot.getStack()));
                                }
                            tileEntity.getCapability(CapabilityPatterns.FLAG_PATTERNS_CAPABILITY).ifPresent(c->{
                                CompoundNBT tag = new CompoundNBT();
                                ItemStack stack = bannerSLot.getStack();
                                ListNBT list = new ListNBT();
                                if(stack.hasTag())
                                {
                                    tag = stack.getTag();
                                }
                                if(tag.contains("BlockEntityTag")) {
                                    CompoundNBT nbt = tag.getCompound("BlockEntityTag");
                                    if(nbt.contains("Patterns"))
                                        list = nbt.getList("Patterns", 10);
                                }
                                Networking.INSTANCE.sendToServer(new SetKingdomPatternsAndColourPacket(pos, list,((BannerItem) bannerSLot.getStack().getItem()).getColor().toString()));
                            });
                        }
                    }
                });
                slots.add(bannerSLot);
                slots.add(addSlot(new SlotItemHandler(h,1,8,55)));
                slots.add(addSlot(new SlotItemHandler(h,2,26,55)));
                slots.add(addSlot(new SlotItemHandler(h,3,44,55)));
                slots.add(addSlot(new SlotItemHandler(h,4,62,55)));
                slots.add(addSlot(new SlotItemHandler(h,5,80,55)));
                slots.add(addSlot(new SlotItemHandler(h,6,98,55)));
                slots.add(addSlot(new SlotItemHandler(h,7,116,55)));
                slots.add(addSlot(new SlotItemHandler(h,8,134,55)));
                slots.add(addSlot(new SlotItemHandler(h,9,152,55)));

                addSlot(new SlotItemHandler(h, 10 , 116, 9));
            });
        }
    }

    public int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx){
        for(int i = 0; i < amount; i++)
        {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x+=dx;
            index++;
        }
        return index;
    }

    public int addSlotBox(IItemHandler handler, int index, int x, int y, int horamount, int dx, int veramount, int dy){
        for(int j = 0; j < veramount; j++)
        {
            index = addSlotRange(handler, index, x, y, horamount, dx);
            y+=dy;
        }
        return index;
    }

    public List<Slot> getSlots(){
        return this.slots;
    }

    public void layoutPlayerInventorySlots(int leftCol, int topRow){
        addSlotBox(playerInventory, 9 , leftCol, topRow, 9, 18, 3, 18);
        topRow+=58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9,18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, ModBlocks.KINGDOM_BLOCK.get());
    }
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 11;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot sourceSlot = inventorySlots.get(index);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }
        sourceSlot.onTake(playerEntity, sourceStack);
        return copyOfSourceStack;
    }

    public TileEntity getTe (){
        return this.tileEntity;
    }

    public String getOwner(){return this.owner;}

    public void setOwner(String owner){this.owner = owner;}

    public PlayerEntity getPlayer(){return this.playerEntity;}
}