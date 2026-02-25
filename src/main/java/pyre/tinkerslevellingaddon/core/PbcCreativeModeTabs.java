package pyre.tinkerslevellingaddon.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class PbcCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("creative_mode_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup."+TinkersLevellingAddon.MOD_ID+".creative_mode_tab"))
            .icon(() -> new ItemStack(PbcBlocks.REINFORCEMENT_ANVIL.get()))
            .displayItems((parameters, output) -> {
                output.accept(PbcBlocks.REINFORCEMENT_ANVIL.get());
                output.accept(PbcBlocks.FORGE_CHAMBER.get());
                output.accept(PbcBlocks.FORGE_HEARTH.get());
                output.accept(PbcBlocks.FORGE_THROAT.get());
                output.accept(PbcBlocks.QUENCHING_BASIN.get());
                output.accept(PbcItems.REINFORCE_ITEM.get());
                output.accept(PbcBlocks.DEEPSLATE_TITANITE_ORE.get());
                output.accept(PbcItems.TITANITE_SHARD.get());
            }).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
