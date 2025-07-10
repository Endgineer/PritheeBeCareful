package pyre.tinkerslevellingaddon.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.util.ModUtil;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;

import java.util.function.Consumer;

public class ModifierRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public ModifierRecipeProvider(PackOutput generator) {
        super(generator);
    }

    /*@Override
    public String getName() {
        return "Tinkers' Levelling Addon Modifier Recipes";
    }*/

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        String abilityFolder = "tools/modifiers/ability/";
        
        ModifierId reinforceId = new ModifierId(ModUtil.getResource("reinforce"));
        
        ModifierRecipeBuilder.modifier(reinforceId)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(1)
                .disallowCrystal()
                .save(consumer, prefix(reinforceId, abilityFolder));
    }

    @Override
    public String getModId() {
        return TinkersLevellingAddon.MOD_ID;
    }
}
