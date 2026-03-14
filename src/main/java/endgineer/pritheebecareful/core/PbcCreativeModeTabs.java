package endgineer.pritheebecareful.core;

import endgineer.pritheebecareful.PritheeBeCareful;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PbcCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PritheeBeCareful.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup."+PritheeBeCareful.MOD_ID+".main"))
            .withTabsBefore(new ResourceLocation("create", "palettes"))
            .icon(() -> new ItemStack(PbcBlocks.REINFORCEMENT_ANVIL.get()))
            .displayItems((parameters, output) -> {
                output.accept(PbcBlocks.REINFORCEMENT_ANVIL.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcBlocks.FORGE_CHAMBER.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcBlocks.FORGE_HEARTH.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcBlocks.FORGE_THROAT.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcBlocks.QUENCHING_BASIN.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.REINFORCE_ITEM.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcBlocks.DEEPSLATE_TITANITE_ORE.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.TITANITE_SHARD.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.LARGE_TITANITE_SHARD.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.TITANITE_CHUNK.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.TITANITE_SCALE.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
                output.accept(PbcItems.TITANITE_SLAB.get(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
            }).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
