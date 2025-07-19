package pyre.tinkerslevellingaddon.data;

import java.util.List;
import java.util.Set;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class PbcLootTableProvider extends LootTableProvider {
    public PbcLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(
            new SubProviderEntry(PbcBlockLootSubProvider::new, LootContextParamSets.BLOCK)
        ));
    }
}
