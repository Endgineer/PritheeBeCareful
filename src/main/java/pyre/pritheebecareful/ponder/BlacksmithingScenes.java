package pyre.pritheebecareful.ponder;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;

import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class BlacksmithingScenes {
    public static void scene1(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("reinforcement_anvil", "Using the reinforcement anvil");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.world().showSection(util.select().layers(1, 3), Direction.DOWN);
        scene.idle(10);

        BlockPos anvilPos = new BlockPos(2,1,2);
        
        scene.overlay().showText(160)
            .text("The Reinforcement Anvil is used for metalworking, which includes initiating, hammering, folding, and applying Reinforce Items to gear")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(180);
        
        scene.overlay().showText(120)
            .text("Place gear primary parts onto the anvil and right click with a Titanite Shard to initiate a Reinforce Item")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(140);
        scene.overlay().showText(120)
            .text("While the Reinforce Item is still unfinished, you can right click it with successively higher titanites to increase its reinforce level")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(140);
        
        scene.overlay().showText(120)
            .text("When a Reinforce Item is heated to sufficient malleability, right click it with a Wrench to hammer xp into it")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(140);
        
        scene.overlay().showText(160)
            .text("When a Reinforce Item has hit a progress wall, right click it with a Titanite Shard to fold it, provided you have enough of the gear's material ingots in your inventory")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(180);
        
        scene.overlay().showText(160)
            .text("With a finished Reinforce Item in hand, place the gear item onto the anvil and right click the anvil with the Reinforce Item to apply it")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(anvilPos));
        scene.idle(180);
    }
    
    public static void scene2(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("blacksmithing_forge", "Using the blacksmithing forge");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layers(3, 3), Direction.DOWN);
        scene.world().showSection(util.select().layers(0, 3), Direction.UP);
        scene.idle(10);
    }
    
    public static void scene3(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("quenching_basin", "Using the quenching basin");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.world().showSection(util.select().layers(1, 3), Direction.DOWN);
        scene.idle(10);

        BlockPos basinPos = new BlockPos(2,1,2);
        
        scene.overlay().showText(80)
            .text("The Quenching Basin is used for quenching Reinforce Items")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(basinPos));
        scene.idle(100);
        
        scene.overlay().showText(120)
            .text("When a Reinforce Item has been depleted of progress, right click it into a Quenching Basin containing water to quench and finish it")
            .placeNearTarget()
            .attachKeyFrame()
            .pointAt(util.vector().topOf(basinPos));
        scene.idle(140);
    }
}
