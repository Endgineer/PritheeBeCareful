package pyre.tinkerslevellingaddon.worldgen;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class PbcPlacedFeatures {
    public static final ResourceKey<PlacedFeature> DEEPSLATE_TITANITE_ORE_KEY = registerKey("deepslate_titanite_ore_placed");
    public static final ResourceKey<PlacedFeature> DEEPSLATE_ANCIENT_RUBBLE_KEY = registerKey("deepslate_ancient_rubble_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        
        register(context, DEEPSLATE_TITANITE_ORE_KEY, configuredFeatures.getOrThrow(PbcConfiguredFeatures.DEEPSLATE_TITANITE_ORE_KEY), PbcOrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(0))));
        register(context, DEEPSLATE_ANCIENT_RUBBLE_KEY, configuredFeatures.getOrThrow(PbcConfiguredFeatures.DEEPSLATE_ANCIENT_RUBBLE_KEY), PbcOrePlacement.commonOrePlacement(64, HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(0))));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TinkersLevellingAddon.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
