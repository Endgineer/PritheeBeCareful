package pyre.tinkerslevellingaddon.hook;

import static pyre.tinkerslevellingaddon.ReinforceModifier.LEVEL_KEY;
import static pyre.tinkerslevellingaddon.ReinforceModifier.MATERIAL_KEY;
import static pyre.tinkerslevellingaddon.ReinforceModifier.REINFORCE_KEY;

import java.util.List;
import java.util.stream.Collectors;

import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ReinforcedScalingStatBoostModule implements ToolStatsModifierHook, ModifierModule {
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.defaultHooks(ModifierHooks.TOOL_STATS);

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolDataNBT persistentData = (ToolDataNBT) context.getPersistentData();
        
        int level = persistentData.getInt(LEVEL_KEY);
        if (level == 0) {
            persistentData.putInt(LEVEL_KEY, 1);
        }
        
        String actualMaterials = context.getMaterials().getList().stream().map(variant -> variant.getVariant().toString()).collect(Collectors.joining(","));
        String currentMaterials = persistentData.getString(MATERIAL_KEY);

        if (!currentMaterials.equals(actualMaterials)) {
            persistentData.putInt(REINFORCE_KEY, 0);
            persistentData.putString(MATERIAL_KEY, actualMaterials);
        }
        
        int reinforce = persistentData.getInt(REINFORCE_KEY);
        if (reinforce == 0) return;

        float result = 1.0f + (reinforce * 0.03f);
        
        ToolStats.DURABILITY.multiply(builder, result);
        ToolStats.USE_ITEM_SPEED.multiply(builder, result);
        ToolStats.ATTACK_DAMAGE.multiply(builder, result);
        ToolStats.ATTACK_SPEED.multiply(builder, result);
        ToolStats.MINING_SPEED.multiply(builder, result);
        ToolStats.ARMOR.multiply(builder, result);
        ToolStats.ARMOR_TOUGHNESS.multiply(builder, result);
        ToolStats.KNOCKBACK_RESISTANCE.multiply(builder, result);
        ToolStats.BLOCK_AMOUNT.multiply(builder, result);
        ToolStats.BLOCK_ANGLE.multiply(builder, result);
        ToolStats.DRAW_SPEED.multiply(builder, result);
        ToolStats.VELOCITY.multiply(builder, result);
        ToolStats.ACCURACY.multiply(builder, result);
        ToolStats.PROJECTILE_DAMAGE.multiply(builder, result);
    }
    
    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    @Override
    public RecordLoadable<? extends ModifierModule> getLoader() {
        return null;
    }
}
