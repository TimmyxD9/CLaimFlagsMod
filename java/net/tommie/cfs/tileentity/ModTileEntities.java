package net.tommie.cfs.tileentity;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.tommie.cfs.ClaimFlags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tommie.cfs.block.ModBlocks;

public class ModTileEntities {
    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ClaimFlags.MOD_ID);

    public static final RegistryObject<TileEntityType<ClaimFlagTileEntity>> CLAIM_FLAGS_TILE_ENTITIES =
            TILE_ENTITIES.register("flag", () -> TileEntityType.Builder.create(ClaimFlagTileEntity::new,
                    ModBlocks.WHITE_CLAIM_FLAG.get(),
                    ModBlocks.WHITE_WALL_CLAIM_FLAG.get(),
                    ModBlocks.BLACK_CLAIM_FLAG.get(),
                    ModBlocks.BLACK_WALL_CLAIM_FLAG.get(),
                    ModBlocks.RED_CLAIM_FLAG.get(),
                    ModBlocks.RED_WALL_CLAIM_FLAG.get(),
                    ModBlocks.YELLOW_CLAIM_FLAG.get(),
                    ModBlocks.YELLOW_WALL_CLAIM_FLAG.get(),
                    ModBlocks.ORANGE_CLAIM_FLAG.get(),
                    ModBlocks.ORANGE_WALL_CLAIM_FLAG.get(),
                    ModBlocks.BROWN_CLAIM_FLAG.get(),
                    ModBlocks.BROWN_WALL_CLAIM_FLAG.get(),
                    ModBlocks.GRAY_CLAIM_FLAG.get(),
                    ModBlocks.GRAY_WALL_CLAIM_FLAG.get(),
                    ModBlocks.LIGHT_GRAY_CLAIM_FLAG.get(),
                    ModBlocks.LIGHT_GRAY_WALL_CLAIM_FLAG.get(),
                    ModBlocks.BLUE_CLAIM_FLAG.get(),
                    ModBlocks.BLUE_WALL_CLAIM_FLAG.get(),
                    ModBlocks.LIGHT_BLUE_CLAIM_FLAG.get(),
                    ModBlocks.LIGHT_BLUE_WALL_CLAIM_FLAG.get(),
                    ModBlocks.CYAN_CLAIM_FLAG.get(),
                    ModBlocks.CYAN_WALL_CLAIM_FLAG.get(),
                    ModBlocks.PURPLE_CLAIM_FLAG.get(),
                    ModBlocks.PURPLE_WALL_CLAIM_FLAG.get(),
                    ModBlocks.PINK_CLAIM_FLAG.get(),
                    ModBlocks.PINK_WALL_CLAIM_FLAG.get(),
                    ModBlocks.MAGENTA_CLAIM_FLAG.get(),
                    ModBlocks.MAGENTA_WALL_CLAIM_FLAG.get(),
                    ModBlocks.GREEN_CLAIM_FLAG.get(),
                    ModBlocks.GREEN_WALL_CLAIM_FLAG.get(),
                    ModBlocks.LIME_CLAIM_FLAG.get(),
                    ModBlocks.LIME_WALL_CLAIM_FLAG.get()
            ).build(null));

    public static final RegistryObject<TileEntityType<KingdomBlockTileEntity>> KINGDOM_BLOCK_TILE_ENTITY =
            TILE_ENTITIES.register("kingdom_block_tile", () -> TileEntityType.Builder.create(KingdomBlockTileEntity::new,
                    ModBlocks.KINGDOM_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register(eventBus);
    }
}
