package pyre.tinkerslevellingaddon.item;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class AbyssRelicItem extends Item {
    private static final double[] BASE_EXPERIENCE_AT_LEVEL = {
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
    
    public static double getExperience(int relicSize, int skillLevel) {
        return BASE_EXPERIENCE_AT_LEVEL[skillLevel-1]*Math.pow(2, relicSize);
    }
    
    public static int rollSize(double depthPressure) {
        double raritiesIncluded = 1 + 4 * depthPressure;
        
        double commonChance = 0.58684;
        double normalChance = 0.24564 * Math.max(0, Math.min(raritiesIncluded, 2) - 1);
        double uncommonChance = 0.10364 * Math.max(0, Math.min(raritiesIncluded, 3) - 2);
        double rareChance = 0.06234 * Math.max(0, Math.min(raritiesIncluded, 4) - 3);
        double superrareChance = 0.00154 * Math.max(0, Math.min(raritiesIncluded, 5) - 4);

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
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.description").withStyle(ChatFormatting.DARK_GRAY));
        components.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".abyss_relic.usage").withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(itemstack, level, components, flag);
    }
}
