package pyre.pritheebecareful.core;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import pyre.pritheebecareful.PritheeBeCareful;
import pyre.pritheebecareful.client.render.ReinforcementAnvilBlockEntityRenderer;
import pyre.pritheebecareful.screen.ForgeScreen;

@EventBusSubscriber(modid = PritheeBeCareful.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class PbcClientEvents {
    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(PbcBlockEntities.REINFORCEMENT_ANVIL_BLOCK_ENTITY.get(), ReinforcementAnvilBlockEntityRenderer::new);
    }

    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(PbcMenus.FORGE_MENU.get(), ForgeScreen::new);
    }
    
    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
            (state, world, pos, index) -> {
                if (index == 0 && world != null && pos != null) {
                    return BiomeColors.getAverageWaterColor(world, pos);
                }
                
                return -1;
            },
            PbcBlocks.QUENCHING_BASIN.get()
        );
    }
}
