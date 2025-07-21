package pyre.tinkerslevellingaddon.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcBlocks;

public class PbcBlockTagsProvider extends BlockTagsProvider {
    public PbcBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, TinkersLevellingAddon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.REINFORCEMENT_ANVIL.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.REINFORCEMENT_ANVIL.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.FORGE_CHAMBER.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.FORGE_CHAMBER.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.FORGE_HEARTH.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.FORGE_HEARTH.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.FORGE_THROAT.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.FORGE_THROAT.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.QUENCHING_BASIN.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.QUENCHING_BASIN.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.DEEPSLATE_TITANITE_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.DEEPSLATE_TITANITE_ORE.get());
        
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PbcBlocks.DEEPSLATE_ANCIENT_RUBBLE.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(PbcBlocks.DEEPSLATE_ANCIENT_RUBBLE.get());
    }
}
