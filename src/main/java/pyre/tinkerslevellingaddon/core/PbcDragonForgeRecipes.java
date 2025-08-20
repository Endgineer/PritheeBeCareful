package pyre.tinkerslevellingaddon.core;

import pyre.tinkerslevellingaddon.data.PbcDragonForgeRecipeProvider;

public class PbcDragonForgeRecipes {
    public static void generate() {
        PbcDragonForgeRecipeProvider.register("fire_titanite_shard", "fire", 1000, "tinkerslevellingaddon:titanite_shard", "iceandfire:fire_dragon_blood", "tinkerslevellingaddon:fire_titanite_shard");
        PbcDragonForgeRecipeProvider.register("ice_titanite_shard", "ice", 1000, "tinkerslevellingaddon:titanite_shard", "iceandfire:ice_dragon_blood", "tinkerslevellingaddon:ice_titanite_shard");
        PbcDragonForgeRecipeProvider.register("lightning_titanite_shard", "lightning", 1000, "tinkerslevellingaddon:titanite_shard", "iceandfire:lightning_dragon_blood", "tinkerslevellingaddon:lightning_titanite_shard");
    }
}
