package pyre.tinkerslevellingaddon.core;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.item.LargeTitaniteShardItem;
import pyre.tinkerslevellingaddon.item.ReinforceItem;
import pyre.tinkerslevellingaddon.item.TitaniteChunkItem;
import pyre.tinkerslevellingaddon.item.TitaniteScaleItem;
import pyre.tinkerslevellingaddon.item.TitaniteShardItem;
import pyre.tinkerslevellingaddon.item.TitaniteSlabItem;

public class PbcItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<ReinforceItem> REINFORCE_ITEM = ITEMS.register("reinforce_item", () -> new ReinforceItem());
    public static final RegistryObject<TitaniteShardItem> TITANITE_SHARD = ITEMS.register("titanite_shard", () -> new TitaniteShardItem());
    public static final RegistryObject<LargeTitaniteShardItem> LARGE_TITANITE_SHARD = ITEMS.register("large_titanite_shard", () -> new LargeTitaniteShardItem());
    public static final RegistryObject<TitaniteChunkItem> TITANITE_CHUNK = ITEMS.register("titanite_chunk", () -> new TitaniteChunkItem());
    public static final RegistryObject<TitaniteScaleItem> TITANITE_SCALE = ITEMS.register("titanite_scale", () -> new TitaniteScaleItem());
    public static final RegistryObject<TitaniteSlabItem> TITANITE_SLAB = ITEMS.register("titanite_slab", () -> new TitaniteSlabItem());
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
