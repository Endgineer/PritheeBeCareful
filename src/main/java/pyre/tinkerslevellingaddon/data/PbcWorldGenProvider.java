package pyre.tinkerslevellingaddon.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.worldgen.PbcBiomeModifiers;
import pyre.tinkerslevellingaddon.worldgen.PbcConfiguredFeatures;
import pyre.tinkerslevellingaddon.worldgen.PbcPlacedFeatures;

public class PbcWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, PbcConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, PbcPlacedFeatures::bootstrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, PbcBiomeModifiers::bootstrap);
    
    public PbcWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(TinkersLevellingAddon.MOD_ID));
    }
}
