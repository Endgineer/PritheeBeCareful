package pyre.tinkerslevellingaddon.loader.forging.models;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import pyre.tinkerslevellingaddon.config.Config;
import pyre.tinkerslevellingaddon.item.ReinforceItem;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec;

public class ThermalModel {
    private static final double TEMPERATURE_LIMIT = Config.temperatureLimit.get();
    private static final double AMBIENT_TEMPERATURE = Config.ambientTemperature.get();
    private static final double FORGE_THERMAL_SLOPE = Math.exp(-Config.thermalRelaxation.get());
    private static final double HEAT_TRANSFER_CONST = Config.heatTransfer.get();
    
    public static double getForgeTemperature(double temperaturePreviousSecond, boolean isForgeOn) {
        if (isForgeOn) {
            return TEMPERATURE_LIMIT - (TEMPERATURE_LIMIT - temperaturePreviousSecond) * FORGE_THERMAL_SLOPE;
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
