package endgineer.pritheebecareful.setup;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.util.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = PritheeBeCareful.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum Sounds {
    CHIME("tool_level_up_chime"),
    SNARE_DRUM("tool_level_up_snare_drum"),
    YAY("tool_level_up_yay");
    
    private final SoundEvent sound;
    
    Sounds(String name) {
        sound = SoundEvent.createVariableRangeEvent(ModUtil.getResource(name));
    }
    
    @SubscribeEvent
    public static void registerSounds(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.SOUND_EVENT) {
            for (Sounds sound : values()) {
                ForgeRegistries.SOUND_EVENTS.register(sound.sound.getLocation(), sound.getSound());
            }
        }
    }
    
    public SoundEvent getSound() {
        return sound;
    }
}
