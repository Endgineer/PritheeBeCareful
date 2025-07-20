package pyre.tinkerslevellingaddon;

import com.mojang.logging.LogUtils;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import pyre.tinkerslevellingaddon.command.ModCommands;
import pyre.tinkerslevellingaddon.config.Config;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import pyre.tinkerslevellingaddon.core.PbcCreativeModeTabs;
import pyre.tinkerslevellingaddon.core.PbcItems;
import pyre.tinkerslevellingaddon.core.PbcMenus;
import pyre.tinkerslevellingaddon.core.PbcSpecs;
import pyre.tinkerslevellingaddon.data.PbcBlockTagsProvider;
import pyre.tinkerslevellingaddon.data.PbcLootTableProvider;
import pyre.tinkerslevellingaddon.data.PbcRecipeProvider;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpecManager;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpecProvider;
import pyre.tinkerslevellingaddon.loader.reinforcing.ReinforcingGearSpecManager;
import pyre.tinkerslevellingaddon.loader.reinforcing.ReinforcingGearSpecProvider;
import pyre.tinkerslevellingaddon.network.Messages;
import pyre.tinkerslevellingaddon.setup.Registration;
import pyre.tinkerslevellingaddon.setup.Spouting;

@Mod(TinkersLevellingAddon.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinkersLevellingAddon {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MOD_ID = "tinkerslevellingaddon";

    public TinkersLevellingAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        PbcItems.register(modEventBus);
        PbcBlocks.register(modEventBus);
        PbcBlockEntities.register(modEventBus);
        PbcCreativeModeTabs.register(modEventBus);
        PbcMenus.register(modEventBus);
        
        Config.init();
        Registration.init();
        Messages.register();
        ModCommands.init();
        Spouting.addCustomSpoutInteraction();
        
        MinecraftForge.EVENT_BUS.addListener(TinkersLevellingAddon::onAddReloadListeners);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ForgingMaterialSpecManager());
        event.addListener(new ReinforcingGearSpecManager());
    }

    @SubscribeEvent
    static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        
        generator.addProvider(event.includeServer(), new PbcRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new PbcBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new PbcLootTableProvider(packOutput));
        
        PbcSpecs.generateForgingMaterialSpecs();
        generator.addProvider(event.includeServer(), new ForgingMaterialSpecProvider(packOutput));
        
        PbcSpecs.generateReinforcingGearSpecs();
        generator.addProvider(event.includeServer(), new ReinforcingGearSpecProvider(packOutput));
    }
}
