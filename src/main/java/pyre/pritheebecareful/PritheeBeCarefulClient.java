package pyre.pritheebecareful;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import pyre.pritheebecareful.core.PbcPonders;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PritheeBeCarefulClient {
    public static void onClientSetup(IEventBus modEventBus, IEventBus forgeEventBus) {
        modEventBus.addListener(PritheeBeCarefulClient::init);
    }
    
    public static void init(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new PbcPonders());
    }
}
