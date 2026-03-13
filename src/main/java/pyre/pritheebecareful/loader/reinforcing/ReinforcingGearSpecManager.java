package pyre.pritheebecareful.loader.reinforcing;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import pyre.pritheebecareful.PritheeBeCareful;

public class ReinforcingGearSpecManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    
    public ReinforcingGearSpecManager() {
        super(GSON, "reinforcing_gearspec");
    }
    
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager manager, ProfilerFiller profiler) {
        data.forEach((resourcelocation, jsonelement) -> {
            String gear = resourcelocation.getPath();
            try {
                ReinforcingGearSpec.registerReinforcingGearSpec(gear, jsonelement);
            } catch (Exception e) {
                PritheeBeCareful.LOGGER.error("ReinforcingGearSpecManager.apply("+gear+")", e);
            }
        });
    }
}
