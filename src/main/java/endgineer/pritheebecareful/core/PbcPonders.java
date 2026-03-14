package endgineer.pritheebecareful.core;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.ponder.BlacksmithingScenes;
import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.IndexExclusionHelper;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.registration.SharedTextRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PbcPonders implements PonderPlugin {
    public static ResourceLocation BLACKSMITHING = new ResourceLocation(PritheeBeCareful.MOD_ID, "blacksmithing");
    
    @Override
    public String getModId() {
        return PritheeBeCareful.MOD_ID;
    }
    
    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        register(helper);
    }
    
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        register(helper);
    }
    
    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {}
    
    @Override
    public void onPonderLevelRestore(PonderLevel ponderLevel) {}

    @Override
    public void indexExclusions(IndexExclusionHelper helper) {}
    
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        
        HELPER.forComponents(PbcBlocks.FORGE_CHAMBER, PbcBlocks.FORGE_HEARTH, PbcBlocks.FORGE_THROAT, PbcBlocks.QUENCHING_BASIN, PbcBlocks.REINFORCEMENT_ANVIL)
            .addStoryBoard("blacksmithing/scene1", BlacksmithingScenes::scene1, PbcPonders.BLACKSMITHING)
            .addStoryBoard("blacksmithing/scene2", BlacksmithingScenes::scene2, PbcPonders.BLACKSMITHING)
            .addStoryBoard("blacksmithing/scene3", BlacksmithingScenes::scene3, PbcPonders.BLACKSMITHING);
    }
    
    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.registerTag(PbcPonders.BLACKSMITHING)
            .addToIndex()
            .item(PbcBlocks.REINFORCEMENT_ANVIL, true, false)
            .title("Blacksmithing")
            .description("Blocks involved in reinforcing gear with titanite")
            .register();
        
        HELPER.addToTag(PbcPonders.BLACKSMITHING)
            .add(PbcBlocks.FORGE_CHAMBER)
            .add(PbcBlocks.FORGE_HEARTH)
            .add(PbcBlocks.FORGE_THROAT)
            .add(PbcBlocks.QUENCHING_BASIN)
            .add(PbcBlocks.REINFORCEMENT_ANVIL);
    }
}
