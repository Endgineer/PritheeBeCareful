package pyre.tinkerslevellingaddon.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class PbcSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, TinkersLevellingAddon.MOD_ID);
    
    public static final RegistryObject<SoundEvent> RELIC_WHISPERS = SOUND_EVENTS.register("relic_whispers", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(TinkersLevellingAddon.MOD_ID, "relic_whispers")));
    
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
