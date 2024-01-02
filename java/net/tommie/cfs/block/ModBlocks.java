package net.tommie.cfs.block;


import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tommie.cfs.ClaimFlags;
import net.tommie.cfs.block.custom.ClaimFlagBlock;
import net.tommie.cfs.block.custom.ClaimFlagWallBlock;
import net.tommie.cfs.block.custom.KingdomBlock;
import net.tommie.cfs.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, ClaimFlags.MOD_ID);

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return(toReturn);
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MISC)));
    }
    public static final RegistryObject<Block> WHITE_CLAIM_FLAG = BLOCKS.register("white_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.WHITE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> WHITE_WALL_CLAIM_FLAG = BLOCKS.register("white_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.WHITE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> BLACK_CLAIM_FLAG = BLOCKS.register("black_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.BLACK, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> BLACK_WALL_CLAIM_FLAG = BLOCKS.register("black_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.BLACK, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> RED_CLAIM_FLAG = BLOCKS.register("red_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.RED, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> RED_WALL_CLAIM_FLAG = BLOCKS.register("red_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.RED, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> YELLOW_CLAIM_FLAG = BLOCKS.register("yellow_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.YELLOW, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> YELLOW_WALL_CLAIM_FLAG = BLOCKS.register("yellow_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.YELLOW, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> ORANGE_CLAIM_FLAG = BLOCKS.register("orange_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.ORANGE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> ORANGE_WALL_CLAIM_FLAG = BLOCKS.register("orange_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.ORANGE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> BROWN_CLAIM_FLAG = BLOCKS.register("brown_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.BROWN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> BROWN_WALL_CLAIM_FLAG = BLOCKS.register("brown_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.BROWN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> BLUE_CLAIM_FLAG = BLOCKS.register("blue_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.BLUE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> BLUE_WALL_CLAIM_FLAG = BLOCKS.register("blue_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.BLUE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> LIGHT_BLUE_CLAIM_FLAG = BLOCKS.register("light_blue_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.LIGHT_BLUE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> LIGHT_BLUE_WALL_CLAIM_FLAG = BLOCKS.register("light_blue_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.LIGHT_BLUE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> CYAN_CLAIM_FLAG = BLOCKS.register("cyan_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.CYAN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> CYAN_WALL_CLAIM_FLAG = BLOCKS.register("cyan_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.CYAN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> GRAY_CLAIM_FLAG = BLOCKS.register("gray_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.GRAY, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> GRAY_WALL_CLAIM_FLAG = BLOCKS.register("gray_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.GRAY, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> LIGHT_GRAY_CLAIM_FLAG = BLOCKS.register("light_gray_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.LIGHT_GRAY, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> LIGHT_GRAY_WALL_CLAIM_FLAG = BLOCKS.register("light_gray_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.LIGHT_GRAY, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> PURPLE_CLAIM_FLAG = BLOCKS.register("purple_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.PURPLE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> PURPLE_WALL_CLAIM_FLAG = BLOCKS.register("purple_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.PURPLE, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> PINK_CLAIM_FLAG = BLOCKS.register("pink_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.PINK, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> PINK_WALL_CLAIM_FLAG = BLOCKS.register("pink_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.PINK, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> MAGENTA_CLAIM_FLAG = BLOCKS.register("magenta_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.MAGENTA, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> MAGENTA_WALL_CLAIM_FLAG = BLOCKS.register("magenta_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.MAGENTA, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> GREEN_CLAIM_FLAG = BLOCKS.register("green_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.GREEN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> GREEN_WALL_CLAIM_FLAG = BLOCKS.register("green_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.GREEN, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> LIME_CLAIM_FLAG = BLOCKS.register("lime_claim_flag", () ->
            new ClaimFlagBlock(DyeColor.LIME, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));
    public static final RegistryObject<Block> LIME_WALL_CLAIM_FLAG = BLOCKS.register("lime_wall_claim_flag", () ->
            new ClaimFlagWallBlock(DyeColor.LIME, AbstractBannerBlock.Properties.create(Material.WOOL).doesNotBlockMovement()));

    public static final RegistryObject<Block> KINGDOM_BLOCK = BLOCKS.register("kingdom_block", () ->
            new KingdomBlock(AbstractBlock.Properties.create(Material.WOOD).notSolid()));

}
