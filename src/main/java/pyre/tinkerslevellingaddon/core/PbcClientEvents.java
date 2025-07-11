package pyre.tinkerslevellingaddon.core;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.client.render.ReinforcementAnvilBlockEntityRenderer;

@EventBusSubscriber(modid = TinkersLevellingAddon.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public class PbcClientEvents {
    @SubscribeEvent
    static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(PbcBlockEntities.REINFORCEMENT_ANVIL_BLOCK_ENTITY.get(), ReinforcementAnvilBlockEntityRenderer::new);
    }
}
