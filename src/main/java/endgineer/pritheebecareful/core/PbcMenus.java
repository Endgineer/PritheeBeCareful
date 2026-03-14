package endgineer.pritheebecareful.core;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.menu.ForgeContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.deferred.MenuTypeDeferredRegister;

public class PbcMenus {
    public static final MenuTypeDeferredRegister MENUS = new MenuTypeDeferredRegister(PritheeBeCareful.MOD_ID);
    
    public static final RegistryObject<MenuType<ForgeContainerMenu>> FORGE_MENU = MENUS.register("forge", ForgeContainerMenu::new);
    
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
