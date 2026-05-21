package endgineer.pritheebecareful;

import com.mojang.logging.LogUtils;
import com.simibubi.create.api.behaviour.spouting.BlockSpoutingBehaviour;
import com.simibubi.create.foundation.data.CreateRegistrate;

import endgineer.pritheebecareful.command.ModCommands;
import endgineer.pritheebecareful.config.Config;
import endgineer.pritheebecareful.core.PbcBlockEntities;
import endgineer.pritheebecareful.core.PbcBlocks;
import endgineer.pritheebecareful.core.PbcCreativeModeTabs;
import endgineer.pritheebecareful.core.PbcItems;
import endgineer.pritheebecareful.core.PbcMenus;
import endgineer.pritheebecareful.core.PbcSpecs;
import endgineer.pritheebecareful.data.PbcRecipeProvider;
import endgineer.pritheebecareful.data.PbcWorldGenProvider;
import endgineer.pritheebecareful.loader.forging.ForgingMaterialSpecManager;
import endgineer.pritheebecareful.loader.forging.ForgingMaterialSpecProvider;
import endgineer.pritheebecareful.loader.reinforcing.ReinforcingGearSpecManager;
import endgineer.pritheebecareful.loader.reinforcing.ReinforcingGearSpecProvider;
import endgineer.pritheebecareful.network.Messages;
import endgineer.pritheebecareful.setup.Registration;
import endgineer.pritheebecareful.setup.SpoutFilling;
import endgineer.pritheebecareful.worldgen.PbcConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;

@Mod(PritheeBeCareful.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PritheeBeCareful {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String NAME = "Prithee Be Careful";
    public static final String MOD_ID = "pritheebecareful";

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);
    
    public PritheeBeCareful(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        
        REGISTRATE.registerEventListeners(modEventBus);
        REGISTRATE.setCreativeTab(PbcCreativeModeTabs.MAIN);
        
        PbcItems.register(modEventBus);
        PbcBlockEntities.register(modEventBus);
        PbcCreativeModeTabs.register(modEventBus);
        PbcMenus.register(modEventBus);
        PbcConfiguredFeatures.register(modEventBus);
        
        Config.init(context);
        Registration.init(modEventBus);
        Messages.register();
        ModCommands.init();
        
        modEventBus.addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.addListener(PritheeBeCareful::onAddReloadListeners);
        MinecraftForge.EVENT_BUS.register(this);
        
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PritheeBeCarefulClient.onClientSetup(modEventBus, MinecraftForge.EVENT_BUS));
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BlockSpoutingBehaviour.BY_BLOCK.register(PbcBlocks.QUENCHING_BASIN.get(), SpoutFilling.INSTANCE);
        });
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
        
        generator.addProvider(event.includeServer(), new PbcRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new PbcWorldGenProvider(packOutput, lookupProvider));
        
        PbcSpecs.generateForgingMaterialSpecs();
        generator.addProvider(event.includeServer(), new ForgingMaterialSpecProvider(packOutput));
        
        PbcSpecs.generateReinforcingGearSpecs();
        generator.addProvider(event.includeServer(), new ReinforcingGearSpecProvider(packOutput));
    }

    public static CreateRegistrate getRegistrate() {
        return REGISTRATE;
    }
}
