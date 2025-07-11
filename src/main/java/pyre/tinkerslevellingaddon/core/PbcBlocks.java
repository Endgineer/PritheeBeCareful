package pyre.tinkerslevellingaddon.core;

import java.util.function.Supplier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.ReinforcementAnvilBlock;

public class PbcBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<ReinforcementAnvilBlock> REINFORCEMENT_ANVIL = registerBlock("reinforcement_anvil", () -> new ReinforcementAnvilBlock());
    
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        PbcItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties()));
        return object;
    }
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
