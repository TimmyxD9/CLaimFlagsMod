package net.tommie.cfs.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tommie.cfs.ClaimFlags;
import net.tommie.cfs.block.ModBlocks;
import net.tommie.cfs.renderer.ItemStackClaimFlagRenderer;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ClaimFlags.MOD_ID);

    public static final RegistryObject<Item> WHITE_CLAIM_FLAG = ITEMS.register
            ("white_claim_flag", ()-> new ClaimFlagItem(ModBlocks.WHITE_CLAIM_FLAG.get(), ModBlocks.WHITE_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> BLACK_CLAIM_FLAG = ITEMS.register
            ("black_claim_flag", ()-> new ClaimFlagItem(ModBlocks.BLACK_CLAIM_FLAG.get(), ModBlocks.BLACK_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> RED_CLAIM_FLAG = ITEMS.register
            ("red_claim_flag", ()-> new ClaimFlagItem(ModBlocks.RED_CLAIM_FLAG.get(), ModBlocks.RED_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> YELLOW_CLAIM_FLAG = ITEMS.register
            ("yellow_claim_flag", ()-> new ClaimFlagItem(ModBlocks.YELLOW_CLAIM_FLAG.get(), ModBlocks.YELLOW_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> ORANGE_CLAIM_FLAG = ITEMS.register
            ("orange_claim_flag", ()-> new ClaimFlagItem(ModBlocks.ORANGE_CLAIM_FLAG.get(), ModBlocks.ORANGE_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> BROWN_CLAIM_FLAG = ITEMS.register
            ("brown_claim_flag", ()-> new ClaimFlagItem(ModBlocks.BROWN_CLAIM_FLAG.get(), ModBlocks.BROWN_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> GRAY_CLAIM_FLAG = ITEMS.register
            ("gray_claim_flag", ()-> new ClaimFlagItem(ModBlocks.GRAY_CLAIM_FLAG.get(), ModBlocks.GRAY_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> LIGHT_GRAY_CLAIM_FLAG = ITEMS.register
            ("light_gray_claim_flag", ()-> new ClaimFlagItem(ModBlocks.LIGHT_GRAY_CLAIM_FLAG.get(), ModBlocks.LIGHT_GRAY_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> BLUE_CLAIM_FLAG = ITEMS.register
            ("blue_claim_flag", ()-> new ClaimFlagItem(ModBlocks.BLUE_CLAIM_FLAG.get(), ModBlocks.BLUE_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> LIGHT_BLUE_CLAIM_FLAG = ITEMS.register
            ("light_blue_claim_flag", ()-> new ClaimFlagItem(ModBlocks.LIGHT_BLUE_CLAIM_FLAG.get(), ModBlocks.LIGHT_BLUE_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> CYAN_CLAIM_FLAG = ITEMS.register
            ("cyan_claim_flag", ()-> new ClaimFlagItem(ModBlocks.CYAN_CLAIM_FLAG.get(), ModBlocks.CYAN_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> PURPLE_CLAIM_FLAG = ITEMS.register
            ("purple_claim_flag", ()-> new ClaimFlagItem(ModBlocks.PURPLE_CLAIM_FLAG.get(), ModBlocks.PURPLE_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> PINK_CLAIM_FLAG = ITEMS.register
            ("pink_claim_flag", ()-> new ClaimFlagItem(ModBlocks.PINK_CLAIM_FLAG.get(), ModBlocks.PINK_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> MAGENTA_CLAIM_FLAG = ITEMS.register
            ("magenta_claim_flag", ()-> new ClaimFlagItem(ModBlocks.MAGENTA_CLAIM_FLAG.get(), ModBlocks.MAGENTA_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> GREEN_CLAIM_FLAG = ITEMS.register
            ("green_claim_flag", ()-> new ClaimFlagItem(ModBlocks.GREEN_CLAIM_FLAG.get(), ModBlocks.GREEN_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> LIME_CLAIM_FLAG = ITEMS.register
            ("lime_claim_flag", ()-> new ClaimFlagItem(ModBlocks.LIME_CLAIM_FLAG.get(), ModBlocks.LIME_WALL_CLAIM_FLAG.get(),
                    new Item.Properties().maxStackSize(16).group(ItemGroup.MISC).setISTER(() -> () -> ItemStackClaimFlagRenderer.instance)));

    public static final RegistryObject<Item> COMPASS = ITEMS.register("compass", ()-> new CompassItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

    public static final RegistryObject<Item> KINGDOM_BLOCK_ITEM = ITEMS.register("kingdom_block", ()-> new KingdomBlockItem(ModBlocks.KINGDOM_BLOCK.get(), new Item.Properties().group(ItemGroup.MISC).maxStackSize(1)));

    public static void register(IEventBus bus){
        ITEMS.register(bus);

    }

}
