package pyre.tinkerslevellingaddon.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import pyre.tinkerslevellingaddon.core.PbcItems;
import pyre.tinkerslevellingaddon.util.ModUtil;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;

import java.util.function.Consumer;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;

public class PbcRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public PbcRecipeProvider(PackOutput generator) {
        super(generator);
    }
    
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        
        ModifierId reinforceId = new ModifierId(ModUtil.getResource("reinforce"));
        
        ModifierRecipeBuilder.modifier(reinforceId)
                .addInput(PbcItems.TITANITE_SHARD.get())
                .setMaxLevel(1)
                .disallowCrystal()
                .save(consumer, prefix(reinforceId, abilityFolder));
        
        String blocksFolder = "blocks/";
        
        ResourceLocation reinforcementAnvilId = ModUtil.getResource("reinforcement_anvil");
        ResourceLocation forgeChamberId = ModUtil.getResource("forge_chamber");
        ResourceLocation forgeHearthId = ModUtil.getResource("forge_hearth");
        ResourceLocation forgeThroatId = ModUtil.getResource("forge_throat");
        
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.REINFORCEMENT_ANVIL.get())
                .define('c', AllBlocks.RAILWAY_CASING.get())
                .define('t', PbcItems.TITANITE_SHARD.get())
                .define('s', TinkerTags.Items.SEARED_BLOCKS)
                .pattern("ccc")
                .pattern("tst")
                .pattern("sss")
                .unlockedBy("has_item", has(PbcItems.TITANITE_SHARD.get()))
                .save(consumer, prefix(reinforcementAnvilId, blocksFolder));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.FORGE_CHAMBER.get())
                .define('b', AllItems.BRASS_INGOT.get())
                .define('t', PbcItems.TITANITE_SHARD.get())
                .define('s', TinkerTags.Items.SEARED_BRICKS)
                .pattern("sbs")
                .pattern("sts")
                .pattern("sbs")
                .unlockedBy("has_item", has(PbcItems.TITANITE_SHARD.get()))
                .save(consumer, prefix(forgeChamberId, blocksFolder));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.FORGE_HEARTH.get())
                .define('b', AllItems.BRASS_INGOT.get())
                .define('t', PbcItems.TITANITE_SHARD.get())
                .define('s', TinkerTags.Items.SEARED_BRICKS)
                .pattern("s s")
                .pattern("sts")
                .pattern("sbs")
                .unlockedBy("has_item", has(PbcItems.TITANITE_SHARD.get()))
                .save(consumer, prefix(forgeHearthId, blocksFolder));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.FORGE_THROAT.get())
                .define('b', AllItems.BRASS_INGOT.get())
                .define('t', PbcItems.TITANITE_SHARD.get())
                .define('s', TinkerTags.Items.SEARED_BRICKS)
                .pattern("sbs")
                .pattern("sts")
                .pattern("s s")
                .unlockedBy("has_item", has(PbcItems.TITANITE_SHARD.get()))
                .save(consumer, prefix(forgeThroatId, blocksFolder));
    }
    
    @Override
    public String getModId() {
        return TinkersLevellingAddon.MOD_ID;
    }
}
