package pyre.tinkerslevellingaddon.setup;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pyre.tinkerslevellingaddon.ReinforceModifier;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.config.Config;
import pyre.tinkerslevellingaddon.util.ModUtil;
import pyre.tinkerslevellingaddon.util.ToolLevellingUtil;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.helper.TooltipUtil;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static pyre.tinkerslevellingaddon.util.ToolLevellingUtil.NONE;

import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = TinkersLevellingAddon.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TooltipEventHandler {
    
    private static final Component TOOLTIP_HOLD_ALT =  ModUtil.makeTranslation("tooltip", "hold_alt",
            ModUtil.makeTranslation("key", "alt", ReinforceModifier.REINFORCE_MODIFIER_COLOR)
                    .withStyle(s -> s.withItalic(true)));
    private static final Component TOOLTIP_MODIFIERS_GAINED =
            ModUtil.makeTranslation("tooltip", "info.slots", ReinforceModifier.REINFORCE_MODIFIER_COLOR)
                    .withStyle(s -> s.withUnderlined(true));
    private static final Component TOOLTIP_STATS_GAINED =
            ModUtil.makeTranslation("tooltip", "info.stats", ReinforceModifier.REINFORCE_MODIFIER_COLOR)
                    .withStyle(s -> s.withUnderlined(true));
    private static final Component TOOLTIP_NEXT_LEVEL =
            ModUtil.makeTranslation("tooltip", "info.next_level", ReinforceModifier.REINFORCE_MODIFIER_COLOR)
                    .withStyle(s -> s.withUnderlined(true));

    public static void prepareTooltipInfo(Player player, ItemStack stack, List<Component> tooltip) {
        KeyModifier activeModifierKey = KeyModifier.getActiveModifier();
        if (player == null || activeModifierKey == KeyModifier.CONTROL || activeModifierKey == KeyModifier.SHIFT) {
            return;
        }

        if (ModifierUtil.getModifierLevel(stack, Registration.REINFORCE.get().getId()) <= 0) {
            return;
        }

        for (int i = tooltip.size() - 1; i >= 0; i--) {
            if (tooltip.get(i) == TooltipUtil.TOOLTIP_HOLD_SHIFT ||
                tooltip.get(i) == TooltipUtil.TOOLTIP_HOLD_CTRL) {
                tooltip.add(i + 1, TOOLTIP_HOLD_ALT);
                break;
            }
        }

        List<Component> infoEntries = new ArrayList<>();
        ToolStack tool = ToolStack.from(stack);
        if (activeModifierKey == KeyModifier.ALT) {
            infoEntries.add(tooltip.get(0));
            infoEntries.addAll(prepareLevelInfo(tool));
            tooltip.clear();
            tooltip.addAll(infoEntries);
        } else {
            infoEntries = prepareGeneralInfo(tool);
            //add tooltips under tool durability
            for (int i = 2; i < infoEntries.size() + 2; i++) {
                tooltip.add(i, infoEntries.get(i - 2));
            }
        }
    }
    
    @SubscribeEvent
    static void onTooltipEvent(ItemTooltipEvent event) {
        prepareTooltipInfo(event.getEntity(), event.getItemStack(), event.getToolTip());
    }

    private static List<Component> prepareGeneralInfo(ToolStack tool) {
        List<Component> infoEntries = new ArrayList<>();
        int level = tool.getPersistentData().getInt(ReinforceModifier.LEVEL_KEY);
        int reinforce = tool.getPersistentData().getInt(ReinforceModifier.REINFORCE_KEY);
        
        boolean limited = !ToolLevellingUtil.canLevelUp(level, reinforce);
        
        MutableComponent reinforceValue = ModUtil.makeText("+"+String.valueOf(reinforce), ReinforceModifier.REINFORCE_MODIFIER_COLOR);
        infoEntries.add(ModUtil.makeTranslation("tooltip","reinforce", reinforce > 0 ? reinforceValue : ModUtil.makeText("-", TextColor.fromLegacyFormat(ChatFormatting.DARK_GRAY))));
        
        MutableComponent fullLevelName = ModUtil.makeTranslation("tooltip", "level.name", ChatFormatting.GRAY,
                getLevelName(level), ModUtil.makeText(level, ChatFormatting.GRAY));
        infoEntries.add(ModUtil.makeTranslation("tooltip", "level", fullLevelName));
        
        if (level == 0) {
            infoEntries.add(ModUtil.makeTranslation("tooltip","xp", ModUtil.makeTranslation("tooltip", "xp.unused", ChatFormatting.DARK_GRAY)));
        } else if (level == Config.maxLevel.get()) {
            infoEntries.add(ModUtil.makeTranslation("tooltip","xp", ModUtil.makeTranslation("tooltip", "xp.maxed", ChatFormatting.DARK_GRAY)));
        } else {
            MutableComponent xp = ModUtil.makeText(tool.getPersistentData().getInt(ReinforceModifier.EXPERIENCE_KEY), limited ? ChatFormatting.DARK_GRAY : ChatFormatting.GOLD);
            MutableComponent xpNeeded = ModUtil.makeText(ToolLevellingUtil.getXpNeededForLevel(level + 1, ToolLevellingUtil.isBroadTool(tool)), limited ? ChatFormatting.DARK_GRAY : ChatFormatting.GOLD);
            MutableComponent xpValue = ModUtil.makeTranslation("tooltip", "xp.value", ChatFormatting.GRAY, xp, xpNeeded);
            infoEntries.add(ModUtil.makeTranslation("tooltip","xp", xpValue));
        }
        
        return infoEntries;
    }

    private static List<Component> prepareLevelInfo(ToolStack tool) {
        List<Component> infoEntries = new ArrayList<>();

        String modifierHistory = tool.getPersistentData().getString(ReinforceModifier.SLOT_HISTORY_KEY);
        if (!modifierHistory.isBlank()) {
            Map<String, Long> gainedModifiers = Arrays.stream(modifierHistory.split(";"))
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            infoEntries.add(TOOLTIP_MODIFIERS_GAINED);
            for (Map.Entry<String, Long> entry : gainedModifiers.entrySet()) {
                MutableComponent value = ModUtil.makeText(entry.getValue(), ToolLevellingUtil.getSlotColor(entry.getKey()));
                infoEntries.add(ModUtil.makeTranslation("tooltip", "info.slots." + entry.getKey(), value));
            }
        }

        String statHistory = tool.getPersistentData().getString(ReinforceModifier.STAT_HISTORY_KEY);
        if (!statHistory.isBlank()) {
            if (!infoEntries.isEmpty()) {
                infoEntries.add(Component.empty());
            }
            Map<String, Double> gainedStats = new LinkedHashMap<>();
            Arrays.stream(statHistory.split(";"))
                    .sorted()
                    .forEach(s -> gainedStats.merge(s, ToolLevellingUtil.getStatValue(tool, s), Double::sum));
            //vanilla multiplies knockback resistance by 10
            gainedStats.computeIfPresent(ToolLevellingUtil.KNOCKBACK_RESISTANCE, (k, v) -> v * 10);
            infoEntries.add(TOOLTIP_STATS_GAINED);
            for (Map.Entry<String, Double> entry : gainedStats.entrySet()) {
                MutableComponent value = ModUtil.makeText(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(entry.getValue()),
                        ToolLevellingUtil.getStatColor(entry.getKey()));
                infoEntries.add(ModUtil.makeTranslation("tooltip", "info.stats." + entry.getKey(), value));
            }
        }

        List<Component> nextLevelInfo = prepareNextLevelInfo(tool);
        if (!infoEntries.isEmpty() && !nextLevelInfo.isEmpty()) {
            infoEntries.add(Component.empty());
        }
        infoEntries.addAll(nextLevelInfo);

        return infoEntries;
    }

    private static List<Component> prepareNextLevelInfo(ToolStack tool) {
        List<Component> infoEntries = new ArrayList<>();
        int level = tool.getPersistentData().getInt(ReinforceModifier.LEVEL_KEY);
        int reinforce = tool.getPersistentData().getInt(ReinforceModifier.REINFORCE_KEY);
        boolean canLevelUp = ToolLevellingUtil.canLevelUp(level, reinforce);
        boolean knowNextSlot = ToolLevellingUtil.canPredictNextSlot(tool);
        boolean knowNextStat = ToolLevellingUtil.canPredictNextStat(tool);

        if (canLevelUp && (knowNextSlot || knowNextStat)) {
            boolean noNextLevelInfo = true;
            infoEntries.add(TOOLTIP_NEXT_LEVEL);
            
            if (knowNextSlot) {
                String nextSlot = ToolLevellingUtil.getSlot(tool, level + 1);
                if (!nextSlot.equals(NONE)) {
                    noNextLevelInfo = false;
                    MutableComponent slot = ModUtil.makeTranslation("tooltip", "slot." + nextSlot,
                        ToolLevellingUtil.getSlotColor(nextSlot));
                    infoEntries.add(ModUtil.makeTranslation("tooltip", "info.next_level.slot", slot));
                }
            }
            
            if (knowNextStat) {
                noNextLevelInfo = false;
                String nextStat = ToolLevellingUtil.getStat(tool, level + 1);
                double statValue = ToolLevellingUtil.getStatValue(tool, nextStat);
                //vanilla multiplies knockback resistance by 10
                if (nextStat.equals(ToolLevellingUtil.KNOCKBACK_RESISTANCE)) {
                    statValue *= 10;
                }
                MutableComponent value = ModUtil.makeText(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(statValue),
                        ToolLevellingUtil.getStatColor(nextStat));
                MutableComponent name = ModUtil.makeTranslation("tooltip", "stat." + nextStat);
                infoEntries.add(ModUtil.makeTranslation("tooltip", "info.next_level.stat", value, name));
            }

            if (noNextLevelInfo) {
                infoEntries.clear();
            }
        }

        return infoEntries;
    }

    private static MutableComponent getLevelName(int level) {
        int tier = level / 10;
        
        TextColor tierColor = getTierColor(tier);
        return ModUtil.makeTranslation("tooltip", "level." + tier, tierColor);
    }

    private static TextColor getTierColor(int tier) {
        int tierColors[] = { 6441256, 6182737, 9602941, 4474470, 4808516, 4810605, 12130566, 815416, 3224912, 3385291, 14645571, 10195645, 10129883 };
        return TextColor.fromRgb(tierColors[tier]);
    }
}
