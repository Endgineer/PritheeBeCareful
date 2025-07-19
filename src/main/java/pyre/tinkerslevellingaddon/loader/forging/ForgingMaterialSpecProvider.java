package pyre.tinkerslevellingaddon.loader.forging;

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
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;

public class ForgingMaterialSpecProvider implements DataProvider {
    private static final Map<String, ForgingMaterialRecord> RECORDS = new LinkedHashMap<>();
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PackOutput output;
    
    public record ForgingMaterialRecord(String ingot_tag, List<ReinforceRecord> reinforces) {}
    
    public record ReinforceRecord(int base_xp_cost, XPConductanceRecord xp_conductance, VolumetricHeatCapacityRecord volumetric_heat_capacity, TemperaturesRecord temperatures) {}
    
    public record XPConductanceRecord(int min, int max) {}
    
    public record VolumetricHeatCapacityRecord(int density, int specific_heat) {}
    
    public record TemperaturesRecord(int hammering, int folding, int quenching, int breakdown, int melting) {}
    
    public ForgingMaterialSpecProvider(PackOutput output) {
        this.output = output;
    }
    
    public static void register(String name, ForgingMaterialRecord record) {
        RECORDS.put(name, record);
    }
    
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        var futures = RECORDS.entrySet().stream()
            .map(entry -> {
                String name = entry.getKey();
                ForgingMaterialRecord record = entry.getValue();
                Path path = output.getOutputFolder().resolve("data/"+TinkersLevellingAddon.MOD_ID+"/forging_materialspec/" + name + ".json");
                return DataProvider.saveStable(cachedOutput, GSON.toJsonTree(record), path);
            }).toList();
        
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
    
    @Override
    public String getName() {
        return "Forging Material Specs";
    }
}
