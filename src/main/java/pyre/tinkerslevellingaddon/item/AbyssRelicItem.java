package pyre.tinkerslevellingaddon.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.endgineer.curseoftheabyss.common.Abyss;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcItems;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec;
import pyre.tinkerslevellingaddon.loader.reinforcing.ReinforcingGearSpec;

public class AbyssRelicItem extends Item {
    public static final String SIZE = "size";
    public static final String RESONANCE = "resonance";
    public static final String COMPLEXITY = "complexity";
    public static final String INSTABILITY = "instability";
    public static final String SUBSTATS = "substats";
    
    public static final String ATTACK_PERCENTAGE = "attack_percentage";
    public static final String HEALTH_PERCENTAGE = "health_percentage";
    public static final String DEFENSE_PERCENTAGE = "defense_percentage";
    public static final String CRITICAL_RATE = "critical_rate";
    public static final String CRITICAL_DAMAGE = "critical_damage";
    public static final String ENERGY_REGENERATION = "energy_regeneration";
    public static final String DAMAGE_BONUS = "damage_bonus";
    
    private static final List<String> ALL_SUBSTATS = List.of(ATTACK_PERCENTAGE, HEALTH_PERCENTAGE, DEFENSE_PERCENTAGE, CRITICAL_RATE, CRITICAL_DAMAGE, ENERGY_REGENERATION, DAMAGE_BONUS, DAMAGE_BONUS, DAMAGE_BONUS, DAMAGE_BONUS);
    
    private static final int[] BASE_EXPERIENCE_AT_LEVEL = {
           66,    74,    82,    91,   100,   111,   132,   136,   154,   164,
          182,   201,   219,   245,   270,   280,   290,   300,   311,   322,
          335,   348,   362,   376,   391,   408,   425,   443,   462,   482,
          503,   526,   549,   574,   601,   629,   658,   689,   722,   756,
          792,   831,   871,   913,   958,  1005,  1054,  1106,  1161,  1218,
         1278,  1342,  1408,  1478,  1550,  1627,  1707,  1791,  1878,  1970,
         2066,  2166,  2270,  2379,  2492,  2610,  2732,  2859,  2992,  3129,
         3271,  3418,  3569,  3726,  3888,  4055,  4227,  4403,  4584,  4770,
         4960,  5155,  5353,  5556,  5762,  5972,  6185,  6400,  6619,  6840,
         7062,  7286,  7512,  7739,  7966,  8193,  8420,  8646,  8872,  9096,
         9319,  9540,  9758,  9974, 10187, 10397, 10603, 10806, 11005, 11200,
        11390, 11576, 11757, 11934, 12106, 12273, 12435, 12592, 12744, 12892,
    };
    
    public AbyssRelicItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(1));
    }
    
    public static boolean isValidAbyssRelicItem(ItemStack stack) {
        if (!(stack.getItem() instanceof AbyssRelicItem)) return false;
        if (!stack.hasTag()) return false;
        
        CompoundTag tag = stack.getTag();
        if (!tag.contains(SIZE)) return false;
        if (!tag.contains(RESONANCE)) return false;
        if (!tag.contains(COMPLEXITY)) return false;
        if (!tag.contains(INSTABILITY)) return false;
        if (!tag.contains(SUBSTATS)) return false;
        
        return true;
    }
    
    @Nullable
    public static RelicApplyResult getApplyResult(ItemStack relic, int skillLevel, String gear) {
        if (!isValidAbyssRelicItem(relic)) return null;
        
        CompoundTag tag = relic.getTag();
        int size = tag.getInt(SIZE);
        String resonance = tag.getString(RESONANCE);
        double complexity = tag.getDouble(COMPLEXITY);
        double instability = tag.getDouble(INSTABILITY);
        CompoundTag substats = tag.getCompound(SUBSTATS);
        double attack_percentage = substats.getDouble(ATTACK_PERCENTAGE);
        double health_percentage = substats.getDouble(HEALTH_PERCENTAGE);
        double defense_percentage = substats.getDouble(DEFENSE_PERCENTAGE);
        double critical_rate = substats.getDouble(CRITICAL_RATE);
        double critical_damage = substats.getDouble(CRITICAL_DAMAGE);
        double energy_regeneration = substats.getDouble(ENERGY_REGENERATION);
        CompoundTag damage_bonus = substats.getCompound(DAMAGE_BONUS);
        
        ThreadLocalRandom random = ThreadLocalRandom.current();
        
        int baseExperience = (int) (BASE_EXPERIENCE_AT_LEVEL[skillLevel-1]*Math.pow(2, size));
        double actualExperience = attack_percentage*baseExperience;
        
        if (random.nextDouble() <= critical_rate) {
            actualExperience *= (1.0+critical_damage);
        }

        Set<String> bonuses = damage_bonus.getAllKeys();
        for (String bonus : bonuses) {
            if (bonus.equals(gear)) {
                actualExperience *= (1.0+damage_bonus.getDouble(bonus));
            }
        }
        
        double mitigatedInstability = instability / (1.0 + 9.0*defense_percentage);
        double mitigatedComplexity = complexity * (1.0 - energy_regeneration);
        double salvageExperience = actualExperience * health_percentage;
        
        boolean succeeded = random.nextDouble() > mitigatedInstability;
        
        return new RelicApplyResult(succeeded, (int) (succeeded ? actualExperience : salvageExperience), (int) (mitigatedComplexity), resonance);
    }
    
    public static ItemStack rollAbyssRelic(double y) {
        ItemStack stack = new ItemStack(PbcItems.ABYSS_RELIC.get());
        
        int layer = ModList.get().isLoaded("curseoftheabyss") ? Abyss.layer(y) : 7;
        Set<String> materials = ForgingMaterialSpec.getAllRegisteredMaterials();
        
        CompoundTag tag = new CompoundTag();
        tag.putInt(AbyssRelicItem.SIZE, AbyssRelicItem.rollSize(layer));
        tag.putString(AbyssRelicItem.RESONANCE, materials.stream().collect(Collectors.toList()).get(ThreadLocalRandom.current().nextInt(materials.size())));
        tag.putDouble(AbyssRelicItem.COMPLEXITY, AbyssRelicItem.rollComplexity());
        tag.putDouble(AbyssRelicItem.INSTABILITY, AbyssRelicItem.rollInstability(layer));
        
        CompoundTag substats_tag = new CompoundTag();
        CompoundTag bonusesTag = new CompoundTag();
        
        Map<String, Double> substats = rollSubstats(5, 5);
        for (Map.Entry<String, Double> entry : substats.entrySet()) {
            if (ALL_SUBSTATS.contains(entry.getKey())) {
                substats_tag.putDouble(entry.getKey(), entry.getValue());
            } else {
                bonusesTag.putDouble(entry.getKey(), entry.getValue());
            }
        }
        
        substats_tag.put(DAMAGE_BONUS, bonusesTag);
        tag.put(AbyssRelicItem.SUBSTATS, substats_tag);
        stack.setTag(tag);
        
        return stack;
    }
    
    private static Map<String, Double> rollSubstats(int numberOfEchos, int substatsPerEcho) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Map<String, Double> setSubstats = new HashMap<String, Double>();
        
        for (int i = 0; i < numberOfEchos; i++) {
            Map<String, Double> echoSubstats = new HashMap<String, Double>();
            List<String> substatSelection = new ArrayList<>(ALL_SUBSTATS);
            List<String> bonusSelection = new ArrayList<>(ReinforcingGearSpec.getAllRegisteredGear());
            
            for (int j = 0; j < substatsPerEcho; j++) {
                int selection = random.nextInt(substatSelection.size());
                String substat = substatSelection.remove(selection);
                double roll = random.nextDouble();
                
                if (substat.equals(ENERGY_REGENERATION)) {
                    if (roll <= 0.0723)      roll = 0.068;
                    else if (roll <= 0.1478) roll = 0.076;
                    else if (roll <= 0.3451) roll = 0.084;
                    else if (roll <= 0.5956) roll = 0.092;
                    else if (roll <= 0.7737) roll = 0.100;
                    else if (roll <= 0.9229) roll = 0.108;
                    else if (roll <= 0.9846) roll = 0.116;
                    else                     roll = 0.124;
                } else if (substat.equals(CRITICAL_DAMAGE)) {
                    if (roll <= 0.2258)      roll = 0.126;
                    else if (roll <= 0.4463) roll = 0.138;
                    else if (roll <= 0.6860) roll = 0.150;
                    else if (roll <= 0.7762) roll = 0.162;
                    else if (roll <= 0.8486) roll = 0.174;
                    else if (roll <= 0.9258) roll = 0.186;
                    else if (roll <= 0.9632) roll = 0.198;
                    else                     roll = 0.210;
                } else if (substat.equals(CRITICAL_RATE)) {
                    if (roll <= 0.2258)      roll = 0.063;
                    else if (roll <= 0.4463) roll = 0.069;
                    else if (roll <= 0.6860) roll = 0.075;
                    else if (roll <= 0.7762) roll = 0.081;
                    else if (roll <= 0.8486) roll = 0.087;
                    else if (roll <= 0.9258) roll = 0.093;
                    else if (roll <= 0.9632) roll = 0.099;
                    else                     roll = 0.105;
                } else if (substat.equals(DEFENSE_PERCENTAGE)) {
                    if (roll <= 0.0723)      roll = 0.081;
                    else if (roll <= 0.1478) roll = 0.090;
                    else if (roll <= 0.3451) roll = 0.100;
                    else if (roll <= 0.5956) roll = 0.109;
                    else if (roll <= 0.7737) roll = 0.118;
                    else if (roll <= 0.9229) roll = 0.128;
                    else if (roll <= 0.9846) roll = 0.138;
                    else                     roll = 0.147;
                } else {
                    if (roll <= 0.0723)      roll = 0.064;
                    else if (roll <= 0.1478) roll = 0.071;
                    else if (roll <= 0.3451) roll = 0.079;
                    else if (roll <= 0.5956) roll = 0.086;
                    else if (roll <= 0.7737) roll = 0.094;
                    else if (roll <= 0.9229) roll = 0.101;
                    else if (roll <= 0.9846) roll = 0.109;
                    else                     roll = 0.116;
                }
                
                echoSubstats.put(substat.equals(DAMAGE_BONUS) ? bonusSelection.remove(random.nextInt(bonusSelection.size())) : substat, Double.valueOf(roll));
            }
            
            for (Map.Entry<String, Double> entry : echoSubstats.entrySet()) {
                setSubstats.put(entry.getKey(), entry.getValue() + setSubstats.getOrDefault(entry.getKey(), 0.0));
            }
        }
        
        return setSubstats;
    }
    
    private static double rollInstability(int layer) {
        return Math.exp((layer-8)*ThreadLocalRandom.current().nextDouble());
    }
    
    private static double rollComplexity() {
        return ThreadLocalRandom.current().nextDouble();
    }
    
    private static int rollSize(double layer) {
        double commonChance = 0.58684 / Math.min(1, 3-layer);
        double normalChance = 0.24564 / Math.min(1, 4-layer);
        double uncommonChance = 0.10364 / Math.min(1, 5-layer);
        double rareChance = 0.06234 / Math.min(1, 6-layer);
        double superrareChance = 0.00154 / Math.min(1, 7-layer);
        
        double total = commonChance + normalChance + uncommonChance + rareChance + superrareChance;
        commonChance /= total;
        normalChance /= total;
        uncommonChance /= total;
        rareChance /= total;
        superrareChance /= total;
        total = 0;
        
        double chance = ThreadLocalRandom.current().nextDouble();
        
        total += superrareChance;
        if (chance <= total) return 4;
        
        total += rareChance;
        if (chance <= total) return 3;
        
        total += uncommonChance;
        if (chance <= total) return 2;
        
        total += normalChance;
        if (chance <= total) return 1;
        
        return 0;
    }
    
    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (isValidAbyssRelicItem(itemstack)) {
            CompoundTag tag = itemstack.getTag();
            String resonance = tag.getString(RESONANCE);
            double complexity = tag.getDouble(COMPLEXITY);
            double instability = tag.getDouble(INSTABILITY);

            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+RESONANCE, Component.translatable("forging."+TinkersLevellingAddon.MOD_ID+".material."+resonance).getString())
            );

            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+COMPLEXITY, String.format("%.1f", complexity*100)+"%")
                .withStyle(Style.EMPTY.withColor((0 << 16) | (255 << 8) | 255))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+INSTABILITY, String.format("%.1f", instability*100)+"%")
                .withStyle(Style.EMPTY.withColor((255 << 16) | (0 << 8) | 255))
            );
            
            CompoundTag substats = tag.getCompound(SUBSTATS);
            double attack_percentage = substats.getDouble(ATTACK_PERCENTAGE);
            double health_percentage = substats.getDouble(HEALTH_PERCENTAGE);
            double defense_percentage = substats.getDouble(DEFENSE_PERCENTAGE);
            double critical_rate = substats.getDouble(CRITICAL_RATE);
            double critical_damage = substats.getDouble(CRITICAL_DAMAGE);
            double energy_regeneration = substats.getDouble(ENERGY_REGENERATION);
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+ATTACK_PERCENTAGE, String.format("%.1f", attack_percentage*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+HEALTH_PERCENTAGE, String.format("%.1f", health_percentage*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+DEFENSE_PERCENTAGE, String.format("%.1f", defense_percentage*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+CRITICAL_RATE, String.format("%.1f", critical_rate*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+CRITICAL_DAMAGE, String.format("%.1f", critical_damage*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            components.add(
                Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+ENERGY_REGENERATION, String.format("%.1f", energy_regeneration*100)+"%")
                .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
            );
            
            CompoundTag damage_bonus = substats.getCompound(DAMAGE_BONUS);
            Set<String> bonuses = damage_bonus.getAllKeys();
            for (String bonus : bonuses) {
                components.add(
                    Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic."+DAMAGE_BONUS, Component.translatable("forging."+TinkersLevellingAddon.MOD_ID+".gear."+bonus).getString(), String.format("%.1f", damage_bonus.getDouble(bonus)*100)+"%")
                    .withStyle(Style.EMPTY.withColor((236 << 16) | (230 << 8) | 216))
                );
            }
        }
        
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.description").withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.usage").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemstack, level, components, flag);
    }
    
    @Override
    public Component getName(ItemStack itemstack) {
        CompoundTag tag = itemstack.getTag();

        if (tag == null) {
            return Component.translatable(("item."+TinkersLevellingAddon.MOD_ID+".abyss_relic"));
        }
        
        return Component.translatable("item."+TinkersLevellingAddon.MOD_ID+".abyss_relic." + tag.getInt(SIZE));
    }
    
    public record RelicApplyResult(boolean success, int experience, int cost, String resonance) {}
}
