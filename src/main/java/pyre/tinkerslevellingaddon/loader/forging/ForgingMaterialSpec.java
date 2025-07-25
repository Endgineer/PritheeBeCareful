package pyre.tinkerslevellingaddon.loader.forging;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import pyre.tinkerslevellingaddon.exception.InvalidForgingMaterialSpecException;
import pyre.tinkerslevellingaddon.loader.forging.models.MalleabilityModel;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.part.IMaterialItem;

public class ForgingMaterialSpec {
    private static HashMap<String, ForgingMaterialSpec> MATERIAL_SPECS = new HashMap<>();
    
    public static Set<String> getAllRegisteredMaterials() {
        return MATERIAL_SPECS.keySet();
    }
    
    public static boolean isRegisteredMaterial(String material) {
        return MATERIAL_SPECS.containsKey(material);
    }
    
    public static void registerForgingMaterialSpec(String material, JsonElement jsonelement) throws Exception {
        JsonObject jsonobject = jsonelement.getAsJsonObject();
        
        JsonArray jsonarray = jsonobject.get("reinforces").getAsJsonArray();
        if (jsonarray.size() != 5) {
            throw new InvalidForgingMaterialSpecException("Must specify exactly 5 reinforce specs.");
        }
        
        ForgingMaterialSpec.MATERIAL_SPECS.put(material, new ForgingMaterialSpec(jsonobject.get("ingot_tag").getAsString(), jsonarray));
    }
    
    @Nullable
    public static String getRegisteredReinforceMaterial(ItemStack stack) {
        if (stack.isEmpty()) return null;
        
        MaterialVariantId materialVariantId = IMaterialItem.getMaterialFromStack(stack);
        if (materialVariantId.equals(IMaterial.UNKNOWN_ID)) return null;
        
        String material = materialVariantId.getId().getPath();
        return MATERIAL_SPECS.containsKey(material) ? material : null;
    }
    
    @Nullable
    public static MaterialReinforceSpec getMaterialReinforceSpec(String material, int reinforce) {
        if (reinforce < 1 || reinforce > 5) return null;
        
        ForgingMaterialSpec materialSpec = ForgingMaterialSpec.MATERIAL_SPECS.get(material);
        if (materialSpec == null) return null;

        return materialSpec.materialReinforceSpecs[reinforce-1];
    }
    
    @Nullable
    public static Double getVolumetricHeatCapacity(String material, int reinforce) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getVolumetricHeatCapacity();
    }
    
    @Nullable
    public static Integer getExperienceCostTotal(String material, int reinforce, int ingotCount) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getExperienceCostTotal(ingotCount);
    }
    
    @Nullable
    public static Integer getExperienceCostPerTrip(String material, int reinforce, int ingotCount) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getExperienceCostPerTrip(ingotCount);
    }
    
    @Nullable
    public static Double getBreakdownPoint(String material, int reinforce) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getBreakdownPoint();
    }
    
    @Nullable
    public static Double getMeltingPoint(String material, int reinforce) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getMeltingPoint();
    }
    
    @Nullable
    public static Boolean canQuench(String material, int reinforce, double temperature) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.canQuench(temperature);
    }
    
    @Nullable
    public static Integer getCustomModelData(String material, int reinforce, double temperature) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getCustomModelData(temperature);
    }
    
    @Nullable
    public static Double getMalleabilityAt(String material, int reinforce, double temperature) {
        MaterialReinforceSpec materialReinforceSpec = getMaterialReinforceSpec(material, reinforce);
        if (materialReinforceSpec == null) return null;
        
        return materialReinforceSpec.getMalleability(temperature);
    }
    
    public static boolean match(String material, ItemStack stack) {
        ForgingMaterialSpec materialSpec = ForgingMaterialSpec.MATERIAL_SPECS.get(material);
        if (materialSpec == null) return false;
        
        return !stack.isEmpty() && stack.is(materialSpec.materialIngot);
    }
    
    private MaterialReinforceSpec[] materialReinforceSpecs;
    private TagKey<Item> materialIngot;
    
    private ForgingMaterialSpec(String ingotTag, JsonArray materialReinforceSpecs) {
        this.materialIngot = TagKey.create(Registries.ITEM, new ResourceLocation(ingotTag));
        
        this.materialReinforceSpecs = new MaterialReinforceSpec[5];
        for(int i = 0; i < 5; i++) {
            JsonObject materialReinforceSpec = materialReinforceSpecs.get(i).getAsJsonObject();
            JsonObject xpConductance = materialReinforceSpec.getAsJsonObject("xp_conductance");
            JsonObject volumetricHeatCapacity = materialReinforceSpec.getAsJsonObject("volumetric_heat_capacity");
            JsonObject temperatures = materialReinforceSpec.getAsJsonObject("temperatures");
            this.materialReinforceSpecs[i] = new MaterialReinforceSpec(
                i,
                materialReinforceSpec.get("base_xp_cost").getAsInt(),
                xpConductance.get("min").getAsDouble(),
                xpConductance.get("max").getAsDouble(),
                volumetricHeatCapacity.get("density").getAsDouble(),
                volumetricHeatCapacity.get("specific_heat").getAsDouble(),
                temperatures.get("hammering").getAsDouble(),
                temperatures.get("folding").getAsDouble(),
                temperatures.get("quenching").getAsDouble(),
                temperatures.get("breakdown").getAsDouble(),
                temperatures.get("melting").getAsDouble()
            );
        }
    }
    
    public class MaterialReinforceSpec {
        private final int baseXpCostTotal;
        private final int baseXpCostPerTrip;
        private final double minXpConductance;
        private final double maxXpConductance;
        private final double volumetricHeatCapacity;
        
        private final MalleabilityModel malleabilityModel;
        
        public MaterialReinforceSpec(
            int index,
            int baseXpCost,
            double minXpConductance,
            double maxXpConductance,
            double density,
            double specificHeat,
            double hammeringPointTemperature,
            double foldingPointTemperature,
            double quenchingPointTemperature,
            double breakdownPointTemperature,
            double meltingPointTemperature
        ) {
            this.baseXpCostTotal = (int) Math.pow(2, index) * baseXpCost;
            this.baseXpCostPerTrip = baseXpCost;
            this.minXpConductance = minXpConductance;
            this.maxXpConductance = maxXpConductance+1;
            this.volumetricHeatCapacity = density * specificHeat;

            boolean byHammeringPointValid = hammeringPointTemperature < foldingPointTemperature;
            boolean byFoldingPointValid = byHammeringPointValid && (foldingPointTemperature < breakdownPointTemperature);
            boolean byBreakdownPointValid = byFoldingPointValid && (breakdownPointTemperature < meltingPointTemperature);
            boolean allTemperaturePointsValid = byBreakdownPointValid && (quenchingPointTemperature >= hammeringPointTemperature && quenchingPointTemperature <= breakdownPointTemperature);
            
            if (!allTemperaturePointsValid) {
                throw new InvalidForgingMaterialSpecException("Temperatures must follow AMBIENT =< HAMMERING < FOLDING < BREAKDOWN < MELTING <= LIMIT and HAMMERING <= QUENCHING <= BREAKDOWN");
            }
            
            this.malleabilityModel = new MalleabilityModel(hammeringPointTemperature, foldingPointTemperature, breakdownPointTemperature, meltingPointTemperature, quenchingPointTemperature);
        }
        
        public double getXpConductance(double temperature) {
            double conductanceNormalizedByPlayerEffort = ThreadLocalRandom.current().nextDouble(this.minXpConductance, this.maxXpConductance);
            return conductanceNormalizedByPlayerEffort * this.malleabilityModel.getMalleabilityAt(temperature);
        }

        public double getMalleability(double temperature) {
            return this.malleabilityModel.getMalleabilityAt(temperature);
        }
        
        public int getExperienceCostPerTrip(int ingotCount) {
            return this.baseXpCostPerTrip * ingotCount;
        }
        
        public int getExperienceCostTotal(int ingotCount) {
            return this.baseXpCostTotal * ingotCount;
        }

        public int getCustomModelData(double temperature) {
            return (int) Math.min(Math.ceil(32 * temperature / this.malleabilityModel.getBreakdownTemperature()), 32);
        }
        
        public double getVolumetricHeatCapacity() {
            return this.volumetricHeatCapacity;
        }

        public double getHammeringPoint() {
            return this.malleabilityModel.getHammeringTemperature();
        }

        public double getFoldingPoint() {
            return this.malleabilityModel.getFoldingTemperature();
        }

        public double getQuenchingPoint() {
            return this.malleabilityModel.getQuenchingTemperature();
        }

        public double getBreakdownPoint() {
            return this.malleabilityModel.getBreakdownTemperature();
        }

        public double getMeltingPoint() {
            return this.malleabilityModel.getMeltingTemperature();
        }

        public boolean canFold(double temperature) {
            return temperature >= this.malleabilityModel.getFoldingTemperature();
        }

        public boolean canQuench(double temperature) {
            return temperature >= this.malleabilityModel.getQuenchingTemperature();
        }
    }
}
