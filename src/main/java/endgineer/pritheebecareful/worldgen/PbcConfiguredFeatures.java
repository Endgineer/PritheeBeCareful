package endgineer.pritheebecareful.worldgen;

import java.util.List;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.core.PbcBlocks;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PbcConfiguredFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, PritheeBeCareful.MOD_ID);
    
    public static final RegistryObject<Feature<OreConfiguration>> EXPOSED_ORE = FEATURES.register("exposed_ore", () -> new ExposedOreFeature(OreConfiguration.CODEC));
    
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_TITANITE_ORE_KEY = registerKey("deepslate_titanite_ore");
    
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        
        List<OreConfiguration.TargetBlockState> deepslateTitaniteOres = List.of(OreConfiguration.target(deepslateReplaceables, PbcBlocks.DEEPSLATE_TITANITE_ORE.get().defaultBlockState()));
        
        register(context, DEEPSLATE_TITANITE_ORE_KEY, Feature.ORE, new OreConfiguration(deepslateTitaniteOres, 9));
    }
    
    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(PritheeBeCareful.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
    
    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
