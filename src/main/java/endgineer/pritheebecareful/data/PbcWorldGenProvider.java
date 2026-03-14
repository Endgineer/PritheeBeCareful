package endgineer.pritheebecareful.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.worldgen.PbcBiomeModifiers;
import endgineer.pritheebecareful.worldgen.PbcConfiguredFeatures;
import endgineer.pritheebecareful.worldgen.PbcPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class PbcWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, PbcConfiguredFeatures::bootstrap)
        .add(Registries.PLACED_FEATURE, PbcPlacedFeatures::bootstrap)
        .add(ForgeRegistries.Keys.BIOME_MODIFIERS, PbcBiomeModifiers::bootstrap);
    
    public PbcWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(PritheeBeCareful.MOD_ID));
    }
}
