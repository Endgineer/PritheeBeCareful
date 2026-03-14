package endgineer.pritheebecareful.loader.forging.models;

import com.simibubi.create.infrastructure.config.AllConfigs;

import endgineer.pritheebecareful.config.Config;
import endgineer.pritheebecareful.item.ReinforceItem;
import endgineer.pritheebecareful.loader.forging.ForgingMaterialSpec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ThermalModel {
    public static final double AMBIENT_TEMPERATURE = 20;
    public static final double FANNED_TEMPERATURE_LIMIT = 4000;
    public static final double UNFANNED_TEMPERATURE_LIMIT = 700;
    private static final double FORGE_THERMAL_SLOPE = Math.exp(-Config.thermalRelaxation.get());
    private static final double HEAT_TRANSFER_CONST = Config.heatTransfer.get();
    
    public static double getForgeTemperature(double temperaturePreviousSecond, boolean isForgeOn, double blastingSpeed) {
        if (isForgeOn) {
            final double ACTUAL_TEMPERATURE_LIMIT = (FANNED_TEMPERATURE_LIMIT-UNFANNED_TEMPERATURE_LIMIT) * Math.max(0, blastingSpeed/AllConfigs.server().kinetics.maxRotationSpeed.get()) + UNFANNED_TEMPERATURE_LIMIT;
            return ACTUAL_TEMPERATURE_LIMIT - (ACTUAL_TEMPERATURE_LIMIT - temperaturePreviousSecond) * FORGE_THERMAL_SLOPE;
        } else {
            return AMBIENT_TEMPERATURE + (temperaturePreviousSecond - AMBIENT_TEMPERATURE) * FORGE_THERMAL_SLOPE;
        }
    }

    public static double getReinforceItemTemperature(ItemStack reinforceItemStack, double ambientTemperature) {
        if (!ReinforceItem.isValidReinforceItem(reinforceItemStack)) return Integer.MIN_VALUE;
        
        CompoundTag tag = reinforceItemStack.getTag();
        double reinforceItemTemperaturePreviousSecond = tag.getDouble(ReinforceItem.TEMPERATURE);
        Double volumetricHeatCapacity = ForgingMaterialSpec.getVolumetricHeatCapacity(tag.getString(ReinforceItem.MATERIAL), tag.getInt(ReinforceItem.REINFORCE));
        
        if (volumetricHeatCapacity == null) return Integer.MIN_VALUE;
        
        return reinforceItemTemperaturePreviousSecond + (HEAT_TRANSFER_CONST / volumetricHeatCapacity.doubleValue()) * (ambientTemperature - reinforceItemTemperaturePreviousSecond);
    }
}
