package pyre.tinkerslevellingaddon.core;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.menu.ForgeContainerMenu;
import slimeknights.mantle.registration.deferred.MenuTypeDeferredRegister;

public class PbcMenus {
    public static final MenuTypeDeferredRegister MENUS = new MenuTypeDeferredRegister(TinkersLevellingAddon.MOD_ID);
    
    public static final RegistryObject<MenuType<ForgeContainerMenu>> FORGE_MENU = MENUS.register("forge", ForgeContainerMenu::new);
    
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
