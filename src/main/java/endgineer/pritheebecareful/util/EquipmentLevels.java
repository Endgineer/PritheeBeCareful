package endgineer.pritheebecareful.util;

public class EquipmentLevels {
    private static int[] XP_AT_LEVEL = {0, 1160, 2607, 5176, 8285, 11760, 15835, 21152, 28761, 40120, 57095, 81960, 117397, 166496, 232755, 320080, 432785, 575592, 753631, 972440};
    public static int MIN_LEVEL = 1;
    public static int MAX_LEVEL = 20;
    
    public static int getLevel(int xp) {
        for(int level = 20; level >= 1; level--) {
            if(xp >= XP_AT_LEVEL[level-1]) return level;
        }

        return 0;
    }

    public static int getMaxLevelAt(int reinforce) {
        return 5*reinforce;
    }
    
    public static int getXpAt(int level) {
        return XP_AT_LEVEL[level-1];
    }
}
