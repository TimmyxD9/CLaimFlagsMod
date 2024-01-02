package net.tommie.cfs.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.IBlockReader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.*;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.container.KingdomBlockContainer;
import net.tommie.cfs.tileentity.KingdomBlockTileEntity;
import net.tommie.cfs.tileentity.ModTileEntities;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;

public class KingdomBlock extends BaseHorizontalBlock {
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(0, 12, 0, 16, 15, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 3, 16),
            Block.makeCuboidShape(1, 3, 1, 2, 12, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public KingdomBlock(Properties p_i48440_1_) {

        super(p_i48440_1_);
        runCalculation(SHAPE);
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModTileEntities.KINGDOM_BLOCK_TILE_ENTITY.get().create();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context){
        return SHAPES.get(state.get(HORIZONTAL_FACING));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        ChunkPos claimed = new ChunkPos(pos);
        if(!worldIn.isRemote()) {
            ClaimsManager manager = ClaimsManager.get(worldIn.getServer());
            PlayerNameOwnerCache ownerCache = PlayerNameOwnerCache.getCache(worldIn.getServer());
                TileEntity te = worldIn.getTileEntity(pos);
                te.getCapability(CapabilityOwner.KINGDOM_OWNER_CAPABILITY).ifPresent(h -> {
                    h.setOwnerId(placer.getUniqueID().toString());
                });
                te.getCapability(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY).ifPresent(c->{
                    c.addMember(placer.getUniqueID().toString());
                });
                te.getCapability(CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY).ifPresent(g->{
                    g.addMemberName(placer.getName().getUnformattedComponentText());
                });
                te.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(iClaims -> {
                    iClaims.addFlag(pos);
                });
                manager.setClaimed(claimed, pos);
                ownerCache.setOwner(placer.getUniqueID().toString(), true);
        }
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.matchesBlock(newState.getBlock())) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if(tileentity instanceof KingdomBlockTileEntity)
            {
                KingdomBlockTileEntity kingdom = (KingdomBlockTileEntity) tileentity;
                ClaimsManager manager = ClaimsManager.get(worldIn.getServer());
                PlayerNameOwnerCache cache = PlayerNameOwnerCache.getCache(kingdom.getWorld().getServer());
                manager.setUnclaimed(new ChunkPos(pos),pos);
                kingdom.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(d->{
                    if(d.getFlags().size()>1) {
                        List<BlockPos> claims = d.getFlags().subList(1, d.getFlags().size());
                        for (BlockPos claim : claims) {
                            manager.setUnclaimed(new ChunkPos(claim),pos);
                            worldIn.removeBlock(claim, true);
                        }
                    }
                });
                kingdom.getCapability(CapabilityOwner.KINGDOM_OWNER_CAPABILITY).ifPresent(h -> {
                    cache.setOwner(h.getOwnerId(),false);
                });
                NonNullList<ItemStack> stacks = NonNullList.create();
                ItemStackHandler handler = kingdom.getItemHandler();
                for(int i=0; i<handler.getSlots(); i++)
                {
                    stacks.add(handler.getStackInSlot(i));
                }
                InventoryHelper.dropItems(worldIn,pos,stacks);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit){
        if(!world.isRemote()){
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof KingdomBlockTileEntity) {
                te.getCapability(CapabilityMembers.KINGDOM_MEMBERS_CAPABILITY).ifPresent(h -> {
                    te.getCapability(CapabilityClaims.KINGDOM_CLAIMS_CAPABILITY).ifPresent(c->{
                        te.getCapability(CapabilityMemberNames.KINGDOM_MEMBER_NAMES_CAPABILITY).ifPresent(g->{
                    if(player.isCrouching())
                    {
                        h.addMember(player.getUniqueID().toString());
                        g.addMemberName(player.getName().getUnformattedComponentText());
                    }
                    else {
                        List<String> tmpMembers = new ArrayList<>();
                        List<String> tmpMemberNames = new ArrayList<>();
                        List<BlockPos> tmpClaims = new ArrayList<>();
                        tmpMembers.addAll(h.getMembers());
                        tmpMemberNames.addAll(g.getMemberNames());
                        tmpClaims.addAll(c.getFlags());
                        NetworkHooks.openGui(((ServerPlayerEntity) player),
                                new SimpleNamedContainerProvider((id, inv, dude) -> new KingdomBlockContainer(id, player.getEntityWorld(), pos, inv, dude), StringTextComponent.EMPTY),
                                buf -> {
                                    buf.writeBlockPos(pos);
                                    CompoundNBT tag = new CompoundNBT();
                                    ListNBT members = new ListNBT();
                                    ListNBT memberNames = new ListNBT();
                                    CompoundNBT newMember = new CompoundNBT();
                                    CompoundNBT newMemberName = new CompoundNBT();
                                    for (String member : tmpMembers) {
                                        newMember.putString("Member", member);
                                        members.add(newMember.copy());
                                    }
                                    for(String name: tmpMemberNames){
                                        newMemberName.putString("MemberName",name);
                                        memberNames.add(newMemberName.copy());
                                    }
                                    ListNBT claims = new ListNBT();
                                    CompoundNBT newClaim = new CompoundNBT();
                                    for(BlockPos claim:tmpClaims){
                                        newClaim.putInt("flagX", claim.getX());
                                        newClaim.putInt("flagY", claim.getY());
                                        newClaim.putInt("flagZ", claim.getZ());
                                        claims.add(newClaim.copy());
                                    }
                                    tag.put("members", members);
                                    tag.put("membernames",memberNames);
                                    tag.put("claims", claims);
                                    buf.writeCompoundTag(tag);
                                }
                        );
                    }
                    });
                    });
                });
            }

        }
        return ActionResultType.SUCCESS;
    }
}
