package pyre.pritheebecareful.core;

import java.util.List;

import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider.ForgingMaterialRecord;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider.ReinforceRecord;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider.TemperaturesRecord;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider.VolumetricHeatCapacityRecord;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpecProvider.XPConductanceRecord;
import pyre.pritheebecareful.loader.reinforcing.ReinforcingGearSpecProvider;
import pyre.pritheebecareful.loader.reinforcing.ReinforcingGearSpecProvider.ReinforcingGearRecord;

public class PbcSpecs {
    public static void generateForgingMaterialSpecs() {
        ForgingMaterialSpecProvider.register("amethyst_bronze", new ForgingMaterialRecord("forge:ingots/amethyst_bronze", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8460, 400), new TemperaturesRecord(945, 1000, 1186, 1262, 1577)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8460, 400), new TemperaturesRecord(945, 1000, 1186, 1262, 1577)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8460, 400), new TemperaturesRecord(945, 1000, 1186, 1262, 1577)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8460, 400), new TemperaturesRecord(945, 1000, 1186, 1262, 1577)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8460, 400), new TemperaturesRecord(945, 1000, 1186, 1262, 1577))
        )));
        ForgingMaterialSpecProvider.register("brass", new ForgingMaterialRecord("forge:ingots/brass", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8500, 380), new TemperaturesRecord(400, 650, 700, 900, 930)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8500, 380), new TemperaturesRecord(400, 650, 700, 900, 930)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8500, 380), new TemperaturesRecord(400, 650, 700, 900, 930)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8500, 380), new TemperaturesRecord(400, 650, 700, 900, 930)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8500, 380), new TemperaturesRecord(400, 650, 700, 900, 930))
        )));
        ForgingMaterialSpecProvider.register("cinderslime", new ForgingMaterialRecord("forge:ingots/cinderslime", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8300, 450), new TemperaturesRecord(807, 1210, 1613, 1674, 2017)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8300, 450), new TemperaturesRecord(807, 1210, 1613, 1674, 2017)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8300, 450), new TemperaturesRecord(807, 1210, 1613, 1674, 2017)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8300, 450), new TemperaturesRecord(807, 1210, 1613, 1674, 2017)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8300, 450), new TemperaturesRecord(807, 1210, 1613, 1674, 2017))
        )));
        ForgingMaterialSpecProvider.register("cobalt", new ForgingMaterialRecord("forge:ingots/cobalt", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 420), new TemperaturesRecord(600, 900, 950, 1200, 1495)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 420), new TemperaturesRecord(600, 900, 950, 1200, 1495)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 420), new TemperaturesRecord(600, 900, 950, 1200, 1495)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 420), new TemperaturesRecord(600, 900, 950, 1200, 1495)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 420), new TemperaturesRecord(600, 900, 950, 1200, 1495))
        )));
        ForgingMaterialSpecProvider.register("copper", new ForgingMaterialRecord("forge:ingots/copper", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8960, 385), new TemperaturesRecord(350, 600, 700, 900, 1085)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8960, 385), new TemperaturesRecord(350, 600, 700, 900, 1085)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8960, 385), new TemperaturesRecord(350, 600, 700, 900, 1085)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8960, 385), new TemperaturesRecord(350, 600, 700, 900, 1085)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8960, 385), new TemperaturesRecord(350, 600, 700, 900, 1085))
        )));
        ForgingMaterialSpecProvider.register("gold", new ForgingMaterialRecord("forge:ingots/gold", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(19300, 129), new TemperaturesRecord(300, 500, 600, 800, 1064)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(19300, 129), new TemperaturesRecord(300, 500, 600, 800, 1064)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(19300, 129), new TemperaturesRecord(300, 500, 600, 800, 1064)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(19300, 129), new TemperaturesRecord(300, 500, 600, 800, 1064)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(19300, 129), new TemperaturesRecord(300, 500, 600, 800, 1064))
        )));
        ForgingMaterialSpecProvider.register("hepatizon", new ForgingMaterialRecord("forge:ingots/hepatizon", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8400, 420), new TemperaturesRecord(1077, 1607, 2140, 2141, 2692)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8400, 420), new TemperaturesRecord(1077, 1607, 2140, 2141, 2692)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8400, 420), new TemperaturesRecord(1077, 1607, 2140, 2141, 2692)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8400, 420), new TemperaturesRecord(1077, 1607, 2140, 2141, 2692)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8400, 420), new TemperaturesRecord(1077, 1607, 2140, 2141, 2692))
        )));
        ForgingMaterialSpecProvider.register("iron", new ForgingMaterialRecord("forge:ingots/iron", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 449), new TemperaturesRecord(750, 912, 1100, 1350, 1538)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 449), new TemperaturesRecord(750, 912, 1100, 1350, 1538)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 449), new TemperaturesRecord(750, 912, 1100, 1350, 1538)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 449), new TemperaturesRecord(750, 912, 1100, 1350, 1538)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 449), new TemperaturesRecord(750, 912, 1100, 1350, 1538))
        )));
        ForgingMaterialSpecProvider.register("manyullyn", new ForgingMaterialRecord("forge:ingots/manyullyn", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 430), new TemperaturesRecord(923, 1385, 1846, 1917, 2307)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 430), new TemperaturesRecord(923, 1385, 1846, 1917, 2307)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 430), new TemperaturesRecord(923, 1385, 1846, 1917, 2307)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 430), new TemperaturesRecord(923, 1385, 1846, 1917, 2307)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(8900, 430), new TemperaturesRecord(923, 1385, 1846, 1917, 2307))
        )));
        ForgingMaterialSpecProvider.register("pig_iron", new ForgingMaterialRecord("forge:ingots/pig_iron", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7200, 460), new TemperaturesRecord(750, 1000, 1050, 1100, 1150)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7200, 460), new TemperaturesRecord(750, 1000, 1050, 1100, 1150)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7200, 460), new TemperaturesRecord(750, 1000, 1050, 1100, 1150)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7200, 460), new TemperaturesRecord(750, 1000, 1050, 1100, 1150)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7200, 460), new TemperaturesRecord(750, 1000, 1050, 1100, 1150))
        )));
        ForgingMaterialSpecProvider.register("queens_slime", new ForgingMaterialRecord("forge:ingots/queens_slime", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(12200, 340), new TemperaturesRecord(884, 1326, 1768, 1809, 2210)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(12200, 340), new TemperaturesRecord(884, 1326, 1768, 1809, 2210)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(12200, 340), new TemperaturesRecord(884, 1326, 1768, 1809, 2210)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(12200, 340), new TemperaturesRecord(884, 1326, 1768, 1809, 2210)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(12200, 340), new TemperaturesRecord(884, 1326, 1768, 1809, 2210))
        )));
        ForgingMaterialSpecProvider.register("rose_gold", new ForgingMaterialRecord("forge:ingots/rose_gold", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(14130, 250), new TemperaturesRecord(423, 634, 846, 1014, 1057)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(14130, 250), new TemperaturesRecord(423, 634, 846, 1014, 1057)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(14130, 250), new TemperaturesRecord(423, 634, 846, 1014, 1057)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(14130, 250), new TemperaturesRecord(423, 634, 846, 1014, 1057)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(14130, 250), new TemperaturesRecord(423, 634, 846, 1014, 1057))
        )));
        ForgingMaterialSpecProvider.register("slimesteel", new ForgingMaterialRecord("forge:ingots/slimesteel", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 440), new TemperaturesRecord(690, 1040, 1380, 1390, 1730)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 440), new TemperaturesRecord(690, 1040, 1380, 1390, 1730)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 440), new TemperaturesRecord(690, 1040, 1380, 1390, 1730)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 440), new TemperaturesRecord(690, 1040, 1380, 1390, 1730)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7870, 440), new TemperaturesRecord(690, 1040, 1380, 1390, 1730))
        )));
        ForgingMaterialSpecProvider.register("steel", new ForgingMaterialRecord("forge:ingots/steel", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7850, 500), new TemperaturesRecord(750, 900, 1100, 1350, 1538)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7850, 500), new TemperaturesRecord(750, 900, 1100, 1350, 1538)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7850, 500), new TemperaturesRecord(750, 900, 1100, 1350, 1538)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7850, 500), new TemperaturesRecord(750, 900, 1100, 1350, 1538)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7850, 500), new TemperaturesRecord(750, 900, 1100, 1350, 1538))
        )));
        ForgingMaterialSpecProvider.register("zinc", new ForgingMaterialRecord("forge:ingots/zinc", List.of(
            new ReinforceRecord(45, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7130, 390), new TemperaturesRecord(200, 300, 350, 400, 420)),
            new ReinforceRecord(30, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7130, 390), new TemperaturesRecord(200, 300, 350, 400, 420)),
            new ReinforceRecord(22, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7130, 390), new TemperaturesRecord(200, 300, 350, 400, 420)),
            new ReinforceRecord(14, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7130, 390), new TemperaturesRecord(200, 300, 350, 400, 420)),
            new ReinforceRecord(10, 7, new XPConductanceRecord(3, 7), new VolumetricHeatCapacityRecord(7130, 390), new TemperaturesRecord(200, 300, 350, 400, 420))
        )));
    }
    
    public static void generateReinforcingGearSpecs() {
        ReinforcingGearSpecProvider.register("axe",                 new ReinforcingGearRecord( 3, List.of("small_axe_head", "tool_binding")));
        ReinforcingGearSpecProvider.register("broad_axe",           new ReinforcingGearRecord(13, List.of("broad_axe_head", "pick_head", "tough_binding")));
        ReinforcingGearSpecProvider.register("cleaver",             new ReinforcingGearRecord(15, List.of("broad_blade", "large_plate", "tough_handle")));
        ReinforcingGearSpecProvider.register("crossbow",            new ReinforcingGearRecord( 2, List.of("bow_limb")));
        ReinforcingGearSpecProvider.register("dagger",              new ReinforcingGearRecord( 2, List.of("small_blade")));
        ReinforcingGearSpecProvider.register("excavator",           new ReinforcingGearRecord(10, List.of("large_plate", "tough_handle", "tough_binding")));
        ReinforcingGearSpecProvider.register("kama",                new ReinforcingGearRecord( 3, List.of("small_blade", "tool_binding")));
        ReinforcingGearSpecProvider.register("longbow",             new ReinforcingGearRecord( 4, List.of("bow_limb", "bow_limb")));
        ReinforcingGearSpecProvider.register("mattock",             new ReinforcingGearRecord( 4, List.of("small_axe_head", "adze_head")));
        ReinforcingGearSpecProvider.register("pickadze",            new ReinforcingGearRecord( 4, List.of("pick_head", "adze_head")));
        ReinforcingGearSpecProvider.register("pickaxe",             new ReinforcingGearRecord( 3, List.of("pick_head", "tool_binding")));
        ReinforcingGearSpecProvider.register("plate_boots",         new ReinforcingGearRecord( 2, List.of("boots_plating")));
        ReinforcingGearSpecProvider.register("plate_chestplate",    new ReinforcingGearRecord( 6, List.of("chestplate_plating")));
        ReinforcingGearSpecProvider.register("plate_helmet",        new ReinforcingGearRecord( 3, List.of("helmet_plating")));
        ReinforcingGearSpecProvider.register("plate_leggings",      new ReinforcingGearRecord( 5, List.of("leggings_plating")));
        ReinforcingGearSpecProvider.register("scythe",              new ReinforcingGearRecord(15, List.of("broad_blade", "tough_binding", "tough_handle")));
        ReinforcingGearSpecProvider.register("sledge_hammer",       new ReinforcingGearRecord(16, List.of("hammer_head", "large_plate", "large_plate")));
        ReinforcingGearSpecProvider.register("sword",               new ReinforcingGearRecord( 3, List.of("small_blade", "tool_handle")));
        ReinforcingGearSpecProvider.register("vein_hammer",         new ReinforcingGearRecord(15, List.of("hammer_head", "large_plate", "tough_binding")));
    }
}
