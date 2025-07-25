package pyre.tinkerslevellingaddon.worldgen;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcBlocks;

public class PbcConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANITE_ORE_KEY = registerKey("deepslate_titanite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_ANCIENT_RUBBLE_KEY = registerKey("deepslate_ancient_rubble");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        
        List<OreConfiguration.TargetBlockState> deepslateTitaniteOres = List.of(OreConfiguration.target(deepslateReplaceables, PbcBlocks.DEEPSLATE_TITANITE_ORE.get().defaultBlockState()));
        List<OreConfiguration.TargetBlockState> deepslateAncientRubbles = List.of(OreConfiguration.target(deepslateReplaceables, PbcBlocks.DEEPSLATE_ANCIENT_RUBBLE.get().defaultBlockState()));
        
        register(context, DEEPSLATE_TITANITE_ORE_KEY, Feature.ORE, new OreConfiguration(deepslateTitaniteOres, 9));
        register(context, DEEPSLATE_ANCIENT_RUBBLE_KEY, Feature.ORE, new OreConfiguration(deepslateAncientRubbles, 9));
    }
    
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TinkersLevellingAddon.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
