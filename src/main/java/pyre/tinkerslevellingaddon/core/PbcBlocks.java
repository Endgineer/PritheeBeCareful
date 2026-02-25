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
import pyre.tinkerslevellingaddon.block.DeepslateTitaniteOreBlock;
import pyre.tinkerslevellingaddon.block.ForgeChamberBlock;
import pyre.tinkerslevellingaddon.block.ForgeHearthBlock;
import pyre.tinkerslevellingaddon.block.ForgeThroatBlock;
import pyre.tinkerslevellingaddon.block.QuenchingBasinBlock;
import pyre.tinkerslevellingaddon.block.ReinforcementAnvilBlock;

public class PbcBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TinkersLevellingAddon.MOD_ID);

    public static final RegistryObject<Block> REINFORCEMENT_ANVIL = registerBlock("reinforcement_anvil", () -> new ReinforcementAnvilBlock());

    public static final RegistryObject<Block> FORGE_CHAMBER = registerBlock("forge_chamber", () -> new ForgeChamberBlock());
    public static final RegistryObject<Block> FORGE_HEARTH = registerBlock("forge_hearth", () -> new ForgeHearthBlock());
    public static final RegistryObject<Block> FORGE_THROAT = registerBlock("forge_throat", () -> new ForgeThroatBlock());

    public static final RegistryObject<Block> QUENCHING_BASIN = registerBlock("quenching_basin", () -> new QuenchingBasinBlock());

    public static final RegistryObject<Block> DEEPSLATE_TITANITE_ORE = registerBlock("deepslate_titanite_ore", () -> new DeepslateTitaniteOreBlock());
    
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> object = BLOCKS.register(name, block);
        PbcItems.ITEMS.register(name, () -> new BlockItem(object.get(), new Item.Properties()));
        return object;
    }
    
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
