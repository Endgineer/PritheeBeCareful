package endgineer.pritheebecareful.setup;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.ReinforceModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class Registration {

    private static final ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(PritheeBeCareful.MOD_ID);
    
    public static final StaticModifier<ReinforceModifier> REINFORCE =
            MODIFIERS.register("reinforce", ReinforceModifier::new);

    public static void init(IEventBus eventBus) {
        MODIFIERS.register(eventBus);
    }
}
