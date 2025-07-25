package pyre.tinkerslevellingaddon.worldgen;

import java.util.function.Function;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class ExposedOreFeature extends OreFeature {
    public ExposedOreFeature(Codec<OreConfiguration> codec) {
        super(codec);
    }
    
    @Override
    protected boolean doPlace(WorldGenLevel level, RandomSource random, OreConfiguration config, double minX, double maxX, double minZ, double maxZ, double minY, double maxY, int baseX, int baseY, int baseZ, int width, int height) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(baseX, baseY, baseZ);
        BlockState state = level.getBlockState(pos);
        
        for (OreConfiguration.TargetBlockState target : config.targetStates) {
            if (target.target.test(state, random)) {
                if (canPlaceOre(state, level::getBlockState, random, config, target, pos)) {
                    level.setBlock(pos, target.state, 2);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static boolean canPlaceOre(BlockState state, Function<BlockPos, BlockState> adjacentStateAccessor, RandomSource random, OreConfiguration config, OreConfiguration.TargetBlockState targetState, BlockPos.MutableBlockPos pos) {
        if (!targetState.target.test(state, random)) {
            return false;
        } else {
            return isAdjacentToAir(adjacentStateAccessor, pos);
        }
    }
}
