package pyre.tinkerslevellingaddon.loader.forging.models;

public class MalleabilityModel {
    private static final double MALLEABILITY_TOLERANCE = 0.01;
    
    private final double temperature_hammering_point;
    private final double temperature_folding_point;
    private final double temperature_breakdown_point;
    private final double temperature_melting_point;
    private final double temperature_quenching_point;
    
    private final double malleability_recrystallization_temperature;
    private final double malleability_rise_steepness;
    private final double malleability_softening_temperature;
    private final double malleability_fall_steepness;
    
    public MalleabilityModel(double temperature_hammering_point, double temperature_folding_point, double temperature_breakdown_point, double temperature_melting_point, double temperature_quenching_point) {
        this.temperature_hammering_point = temperature_hammering_point;
        this.temperature_folding_point = temperature_folding_point;
        this.temperature_breakdown_point = temperature_breakdown_point;
        this.temperature_melting_point = temperature_melting_point;
        this.temperature_quenching_point = temperature_quenching_point;
        
        double A = Math.log(1.0 / MALLEABILITY_TOLERANCE - 1.0);
        
        this.malleability_recrystallization_temperature = (temperature_folding_point + temperature_hammering_point) / 2.0;
        this.malleability_rise_steepness = -A / (temperature_hammering_point - this.malleability_recrystallization_temperature);
        
        this.malleability_softening_temperature = (temperature_melting_point + temperature_breakdown_point) / 2.0;
        this.malleability_fall_steepness = -A / (temperature_breakdown_point - this.malleability_softening_temperature);
    }

    public double getMalleabilityAt(double temperature) {
        return this.getMalleabilityRise(temperature)*this.getMalleabilityFall(temperature);
    }

    private double getMalleabilityRise(double temperature) {
        return 1.0 / (1.0 + Math.exp(this.malleability_rise_steepness * (this.malleability_recrystallization_temperature - temperature)));
    }

    private double getMalleabilityFall(double temperature) {
        return 1.0 / (1.0 + Math.exp(this.malleability_fall_steepness * (temperature - this.malleability_softening_temperature)));
    }

    public double getHammeringTemperature() {
        return this.temperature_hammering_point;
    }

    public double getFoldingTemperature() {
        return this.temperature_folding_point;
    }

    public double getBreakdownTemperature() {
        return this.temperature_breakdown_point;
    }

    public double getMeltingTemperature() {
        return this.temperature_melting_point;
    }

    public double getQuenchingTemperature() {
        return this.temperature_quenching_point;
    }
}
