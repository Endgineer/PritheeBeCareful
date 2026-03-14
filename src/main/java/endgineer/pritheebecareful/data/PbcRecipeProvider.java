package endgineer.pritheebecareful.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;

import java.util.function.Consumer;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;

import endgineer.pritheebecareful.PritheeBeCareful;
import endgineer.pritheebecareful.core.PbcBlocks;
import endgineer.pritheebecareful.core.PbcItems;
import endgineer.pritheebecareful.util.ModUtil;

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
        ResourceLocation quenchingBasinId = ModUtil.getResource("quenching_basin");
        
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

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.QUENCHING_BASIN.get())
                .define('b', AllItems.BRASS_INGOT.get())
                .define('t', PbcItems.TITANITE_SHARD.get())
                .define('s', TinkerTags.Items.SEARED_BRICKS)
                .pattern("s s")
                .pattern("btb")
                .pattern("sss")
                .unlockedBy("has_item", has(PbcItems.TITANITE_SHARD.get()))
                .save(consumer, prefix(quenchingBasinId, blocksFolder));
    }
    
    @Override
    public String getModId() {
        return PritheeBeCareful.MOD_ID;
    }
}
