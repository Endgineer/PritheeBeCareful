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
import slimeknights.tconstruct.library.tools.SlotType;

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
        String abilitySalvage = "tools/modifiers/salvage/ability/";

        ModifierId improvableId = new ModifierId(ModUtil.getResource("improvable"));
        
        ModifierRecipeBuilder.modifier(improvableId)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(1)
                .setSlots(SlotType.ABILITY, 1)
                .disallowCrystal()
                .saveSalvage(consumer, prefix(improvableId, abilitySalvage))
                .save(consumer, wrap(improvableId, abilityFolder, "_level_1"));

        ModifierRecipeBuilder.modifier(improvableId)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(2)
                .disallowCrystal()
                .save(consumer, wrap(improvableId, abilityFolder, "_level_2"));

        ModifierRecipeBuilder.modifier(improvableId)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(3)
                .disallowCrystal()
                .save(consumer, wrap(improvableId, abilityFolder, "_level_3"));

        ModifierRecipeBuilder.modifier(improvableId)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(4)
                .disallowCrystal()
                .save(consumer, wrap(improvableId, abilityFolder, "_level_4"));

        ModifierRecipeBuilder.modifier(improvableId)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .addInput(Tags.Items.BOOKSHELVES)
                .setMaxLevel(5)
                .disallowCrystal()
                .save(consumer, wrap(improvableId, abilityFolder, "_level_5"));
    }

    @Override
    public String getModId() {
        return TinkersLevellingAddon.MOD_ID;
    }
}
