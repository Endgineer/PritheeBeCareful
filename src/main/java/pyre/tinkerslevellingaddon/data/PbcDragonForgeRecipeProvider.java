package pyre.tinkerslevellingaddon.data;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class PbcDragonForgeRecipeProvider implements DataProvider {
    private static final Map<String, DragonForgeRecipeRecord> RECORDS = new LinkedHashMap<>();
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput output;
    
    public record DragonForgeRecipeRecord(String type, String dragon_type, int cook_time, DragonForgeRecipeInputItemRecord input, DragonForgeRecipeBloodItemRecord blood, DragonForgeRecipeResultItemRecord result) {}
    
    public record DragonForgeRecipeInputItemRecord(String item) {}

    public record DragonForgeRecipeBloodItemRecord(String item) {}

    public record DragonForgeRecipeResultItemRecord(String item) {}
    
    public PbcDragonForgeRecipeProvider(PackOutput output) {
        this.output = output;
    }
    
    public static void register(String name, String dragon_type, int cook_time, String input_item, String blood_item, String result_item) {
        RECORDS.put(name, new DragonForgeRecipeRecord("iceandfire:dragonforge", dragon_type, cook_time, new DragonForgeRecipeInputItemRecord(input_item), new DragonForgeRecipeBloodItemRecord(blood_item), new DragonForgeRecipeResultItemRecord(result_item)));
    }
    
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        var futures = RECORDS.entrySet().stream()
            .map(entry -> {
                DragonForgeRecipeRecord record = entry.getValue();
                Path path = output.getOutputFolder().resolve("data/"+TinkersLevellingAddon.MOD_ID+"/recipes/" + entry.getKey() +".json");
                return DataProvider.saveStable(cachedOutput, GSON.toJsonTree(record), path);
            }).toList();
        
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
    
    @Override
    public String getName() {
        return TinkersLevellingAddon.NAME+" "+"Dragon Forge Recipe Provider";
    }
}
