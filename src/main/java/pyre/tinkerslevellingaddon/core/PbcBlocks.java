package pyre.tinkerslevellingaddon.core;

import com.simibubi.create.foundation.data.CreateRegistrate;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.DeepslateTitaniteOreBlock;
import pyre.tinkerslevellingaddon.block.ForgeChamberBlock;
import pyre.tinkerslevellingaddon.block.ForgeHearthBlock;
import pyre.tinkerslevellingaddon.block.ForgeThroatBlock;
import pyre.tinkerslevellingaddon.block.QuenchingBasinBlock;
import pyre.tinkerslevellingaddon.block.ReinforcementAnvilBlock;

public class PbcBlocks {
    private static final CreateRegistrate REGISTRATE = TinkersLevellingAddon.getRegistrate();
    
    public static final BlockEntry<ReinforcementAnvilBlock> REINFORCEMENT_ANVIL = REGISTRATE.block("reinforcement_anvil", props -> new ReinforcementAnvilBlock())
        .blockstate((context, provider) -> {})
        .loot((table, block) -> table.dropSelf(block))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();
    
    public static final BlockEntry<ForgeChamberBlock> FORGE_CHAMBER = REGISTRATE.block("forge_chamber", props -> new ForgeChamberBlock())
        .blockstate((context, provider) -> {})
        .loot((table, block) -> table.dropSelf(block))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();
    
    public static final BlockEntry<ForgeHearthBlock> FORGE_HEARTH = REGISTRATE.block("forge_hearth", props -> new ForgeHearthBlock())
        .blockstate((context, provider) -> {})
        .loot((table, block) -> table.dropSelf(block))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();
    
    public static final BlockEntry<ForgeThroatBlock> FORGE_THROAT = REGISTRATE.block("forge_throat", props -> new ForgeThroatBlock())
        .blockstate((context, provider) -> {})
        .loot((table, block) -> table.dropSelf(block))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();

    public static final BlockEntry<QuenchingBasinBlock> QUENCHING_BASIN = REGISTRATE.block("quenching_basin", props -> new QuenchingBasinBlock())
        .blockstate((context, provider) -> {})
        .loot((table, block) -> table.dropSelf(block))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();

    public static final BlockEntry<DeepslateTitaniteOreBlock> DEEPSLATE_TITANITE_ORE = REGISTRATE.block("deepslate_titanite_ore", props -> new DeepslateTitaniteOreBlock())
        .loot((table, block) -> table.add(block, LootTable.lootTable().withPool(LootPool.lootPool())))
        .simpleItem()
        .transform(pickaxeOnly())
        .tag(BlockTags.NEEDS_IRON_TOOL)
        .register();
    
    public static void register() {}
}
