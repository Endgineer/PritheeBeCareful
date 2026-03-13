package pyre.pritheebecareful.loader.reinforcing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import pyre.pritheebecareful.exception.InvalidReinforcingGearSpecException;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

public class ReinforcingGearSpec {
    private static HashMap<String, ReinforcingGearSpec> GEAR_SPECS = new HashMap<>();
    
    public static Set<String> getAllRegisteredGear() {
        return GEAR_SPECS.values().stream().map(value -> value.resultingGear).collect(Collectors.toSet());
    }
    
    public static boolean isRegisteredGear(String gear) {
        return GEAR_SPECS.containsKey(gear);
    }
    
    public static void registerReinforcingGearSpec(String gear, JsonElement jsonelement) throws Exception {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        ReinforcingGearSpec gearSpec = new ReinforcingGearSpec(gear, jsonobject);
        ReinforcingGearSpec.GEAR_SPECS.put(gearSpec.specIdentifier, gearSpec);
    }
    
    private static final String getGearSpecIdentifier(Map<String, Integer> partMap) {
        TreeMap<String, Integer> sortedPartMap = new TreeMap<>(partMap);
        
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedPartMap.entrySet()) {
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
        }
        
        return builder.toString();
    }

    @Nullable
    private static final TreeMap<String, Integer> buildPartMap(Item... items) {
        TreeMap<String, Integer> partMap = new TreeMap<>();
        for (Item item : items) {
            if (item instanceof ToolPartItem) {
                String descriptionId = item.getDescriptionId();
                int nameIndex = descriptionId.lastIndexOf('.')+1;
                String name = descriptionId.substring(nameIndex);

                partMap.put(name, partMap.getOrDefault(name, 0) + 1);
            } else if (!(item == Items.AIR)) {
                return null;
            }
        }
        
        return partMap;
    }
    
    @Nullable
    public static final ReinforcingGearSpec getReinforcingGearSpec(Item... items) {
        TreeMap<String, Integer> partMap = ReinforcingGearSpec.buildPartMap(items);
        if (partMap == null) return null;
        return GEAR_SPECS.get(ReinforcingGearSpec.getGearSpecIdentifier(partMap));
    }

    private String specIdentifier;
    private int materialCost;
    private String resultingGear;
    
    public ReinforcingGearSpec(String gear, JsonObject gearSpec) {
        JsonArray partList = gearSpec.get("part_list").getAsJsonArray();
        if (partList.size() > 3) {
            throw new InvalidReinforcingGearSpecException("Part list cannot contain more than 3 identifying parts.");
        }
        
        TreeMap<String, Integer> partMap = new TreeMap<>();
        for (JsonElement part : partList) {
            String partName = part.getAsString();
            partMap.put(partName, partMap.getOrDefault(partName, 0) + 1);
        }

        this.specIdentifier = ReinforcingGearSpec.getGearSpecIdentifier(partMap);
        this.materialCost = gearSpec.get("material_cost").getAsInt();
        this.resultingGear = gear;
    }

    public int getMaterialCost() {
        return this.materialCost;
    }

    public String getResultingGear() {
        return this.resultingGear;
    }
}
