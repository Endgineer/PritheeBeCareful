package pyre.pritheebecareful.loader.reinforcing;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import pyre.pritheebecareful.PritheeBeCareful;

public class ReinforcingGearSpecProvider implements DataProvider {
    private static final Map<String, ReinforcingGearRecord> RECORDS = new LinkedHashMap<>();
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput output;
    
    public record ReinforcingGearRecord(int material_cost, List<String> part_list) {}
    
    public ReinforcingGearSpecProvider(PackOutput output) {
        this.output = output;
    }
    
    public static void register(String name, ReinforcingGearRecord record) {
        RECORDS.put(name, record);
    }
    
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        var futures = RECORDS.entrySet().stream()
            .map(entry -> {
                String name = entry.getKey();
                ReinforcingGearRecord record = entry.getValue();
                Path path = output.getOutputFolder().resolve("data/"+PritheeBeCareful.MOD_ID+"/reinforcing_gearspec/" + name + ".json");
                return DataProvider.saveStable(cachedOutput, GSON.toJsonTree(record), path);
            }).toList();
        
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
    
    @Override
    public String getName() {
        return "Reinforcing Gear Specs";
    }
}
