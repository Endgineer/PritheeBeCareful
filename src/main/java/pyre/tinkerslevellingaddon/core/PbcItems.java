package pyre.tinkerslevellingaddon.core;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.item.AbyssRelicItem;
import pyre.tinkerslevellingaddon.item.AmphithereBrushItem;
import pyre.tinkerslevellingaddon.item.FireTitaniteShardItem;
import pyre.tinkerslevellingaddon.item.IceTitaniteShardItem;
import pyre.tinkerslevellingaddon.item.LightningTitaniteShardItem;
import pyre.tinkerslevellingaddon.item.ReinforceItem;
import pyre.tinkerslevellingaddon.item.TitaniteShardItem;

public class PbcItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<ReinforceItem> REINFORCE_ITEM = ITEMS.register("reinforce_item", () -> new ReinforceItem());
    public static final RegistryObject<TitaniteShardItem> TITANITE_SHARD = ITEMS.register("titanite_shard", () -> new TitaniteShardItem());
    public static final RegistryObject<FireTitaniteShardItem> FIRE_TITANITE_SHARD = ITEMS.register("fire_titanite_shard", () -> new FireTitaniteShardItem());
    public static final RegistryObject<IceTitaniteShardItem> ICE_TITANITE_SHARD = ITEMS.register("ice_titanite_shard", () -> new IceTitaniteShardItem());
    public static final RegistryObject<LightningTitaniteShardItem> LIGHTNING_TITANITE_SHARD = ITEMS.register("lightning_titanite_shard", () -> new LightningTitaniteShardItem());
    public static final RegistryObject<AbyssRelicItem> ABYSS_RELIC = ITEMS.register("abyss_relic", () -> new AbyssRelicItem());
    public static final RegistryObject<AmphithereBrushItem> AMPHITHERE_BRUSH = ITEMS.register("amphithere_brush", () -> new AmphithereBrushItem());
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
