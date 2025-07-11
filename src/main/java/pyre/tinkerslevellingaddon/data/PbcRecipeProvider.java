package pyre.tinkerslevellingaddon.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import pyre.tinkerslevellingaddon.util.ModUtil;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;

import java.util.function.Consumer;

import com.simibubi.create.AllBlocks;

public class PbcRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public PbcRecipeProvider(PackOutput generator) {
        super(generator);
    }
    
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        
        ModifierId reinforceId = new ModifierId(ModUtil.getResource("reinforce"));
        
        ModifierRecipeBuilder.modifier(reinforceId)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(1)
                .disallowCrystal()
                .save(consumer, prefix(reinforceId, abilityFolder));
        
        String blocksFolder = "blocks/";
        
        ResourceLocation reinforcementAnvilId = ModUtil.getResource("reinforcement_anvil");
        
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, PbcBlocks.REINFORCEMENT_ANVIL.get())
                .define('c', AllBlocks.RAILWAY_CASING.get())
                .define('s', TinkerTags.Items.SEARED_BLOCKS)
                .pattern("ccc")
                .pattern(" s ")
                .pattern("sss")
                .unlockedBy("has_item", has(AllBlocks.RAILWAY_CASING.asItem()))
                .save(consumer, prefix(reinforcementAnvilId, blocksFolder));
    }
    
    @Override
    public String getModId() {
        return TinkersLevellingAddon.MOD_ID;
    }
}
