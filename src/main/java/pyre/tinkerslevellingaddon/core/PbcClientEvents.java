package pyre.tinkerslevellingaddon.core;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.client.render.ReinforcementAnvilBlockEntityRenderer;
import pyre.tinkerslevellingaddon.screen.ForgeScreen;

@EventBusSubscriber(modid = TinkersLevellingAddon.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class PbcClientEvents {
    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(PbcBlockEntities.REINFORCEMENT_ANVIL_BLOCK_ENTITY.get(), ReinforcementAnvilBlockEntityRenderer::new);
    }

    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(PbcMenus.FORGE_MENU.get(), ForgeScreen::new);
    }
}
