package net.tommie.cfs;

import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tommie.cfs.block.ModBlocks;
import net.tommie.cfs.capabilities.*;
import net.tommie.cfs.config.ClaimFlagsConfig;
import net.tommie.cfs.events.BlockBreakEvent;
import net.tommie.cfs.events.BlockPlaceEvent;
import net.tommie.cfs.events.PlayerJoinEvent;
import net.tommie.cfs.item.ModItems;
import net.tommie.cfs.packeting.Networking;
import net.tommie.cfs.renderer.ClaimFlagRenderer;
import net.tommie.cfs.screen.KingdomBlockScreen;
import net.tommie.cfs.container.ModContainers;
import net.tommie.cfs.tileentity.ModTileEntities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ClaimFlags.MOD_ID)
public class ClaimFlags
{
    public static final String MOD_ID = "cfs";

    private static final Logger LOGGER = LogManager.getLogger();

    public ClaimFlags() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModTileEntities.register(eventBus);
        ModContainers.register(eventBus);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ClaimFlagsConfig.SPEC, "claimflags-server.toml");

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        Networking.registerMessages();
        CapabilityOwner.register();
        CapabilityMembers.register();
        CapabilityKingdomPos.register();
        CapabilityClaims.register();
        CapabilityKingdomIsActive.register();
        CapabilityUpkeep.register();
        CapabilityPatterns.register();
        CapabilityMemberNames.register();

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, AttachCapabilitiesHandler::onAttachCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(TileEntity.class, AttachCapabilitiesHandler::onAttachTileEntityCapabilitiesEvent);
        MinecraftForge.EVENT_BUS.addListener(BlockPlaceEvent::onPlaceBlockEvent);
        MinecraftForge.EVENT_BUS.addListener(BlockBreakEvent::onDestroyBlockEvent);
        MinecraftForge.EVENT_BUS.addListener(PlayerJoinEvent::onPlayerJoinEvent);

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModTileEntities.CLAIM_FLAGS_TILE_ENTITIES.get(), ClaimFlagRenderer::new);
        ScreenManager.registerFactory(ModContainers.KINGDOM_BLOCK_CONTAINER.get(), KingdomBlockScreen::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {

        }
    }
}
