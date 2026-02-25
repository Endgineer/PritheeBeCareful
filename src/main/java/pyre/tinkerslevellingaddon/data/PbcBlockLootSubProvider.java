package pyre.tinkerslevellingaddon.data;

import java.util.Collections;
import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import pyre.tinkerslevellingaddon.block.DeepslateTitaniteOreBlock;
import pyre.tinkerslevellingaddon.core.PbcBlocks;

public class PbcBlockLootSubProvider extends BlockLootSubProvider {
    public PbcBlockLootSubProvider() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(PbcBlocks.REINFORCEMENT_ANVIL.get());
        this.dropSelf(PbcBlocks.FORGE_CHAMBER.get());
        this.dropSelf(PbcBlocks.FORGE_HEARTH.get());
        this.dropSelf(PbcBlocks.FORGE_THROAT.get());
        this.dropSelf(PbcBlocks.QUENCHING_BASIN.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PbcBlocks.BLOCKS.getEntries().stream().map(entry -> entry.get()).filter(block -> !(block instanceof DeepslateTitaniteOreBlock)).collect(Collectors.toList());
    }
}
