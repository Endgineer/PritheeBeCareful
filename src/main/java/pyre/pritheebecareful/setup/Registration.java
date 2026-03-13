package pyre.pritheebecareful.setup;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import pyre.pritheebecareful.ReinforceModifier;
import pyre.pritheebecareful.PritheeBeCareful;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class Registration {

    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(PritheeBeCareful.MOD_ID);
    
    public static final StaticModifier<ReinforceModifier> REINFORCE =
            MODIFIERS.register("reinforce", ReinforceModifier::new);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        MODIFIERS.register(bus);
    }
}
