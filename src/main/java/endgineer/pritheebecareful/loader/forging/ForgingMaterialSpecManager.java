package endgineer.pritheebecareful.loader.forging;

import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import endgineer.pritheebecareful.PritheeBeCareful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class ForgingMaterialSpecManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    
    public ForgingMaterialSpecManager() {
        super(GSON, "forging_materialspec");
    }
    
    protected void apply(Map<ResourceLocation, JsonElement> data, ResourceManager manager, ProfilerFiller profiler) {
        data.forEach((resourcelocation, jsonelement) -> {
            String material = resourcelocation.getPath();
            try {
                ForgingMaterialSpec.registerForgingMaterialSpec(material, jsonelement);
            } catch (Exception e) {
                PritheeBeCareful.LOGGER.error("ForgingMaterialSpecManager.apply("+material+")", e);
            }
        });
    }
}
