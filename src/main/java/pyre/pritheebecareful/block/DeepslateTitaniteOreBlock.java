package pyre.pritheebecareful.block;

import net.endgineer.curseoftheabyss.common.Abyss;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;

public class DeepslateTitaniteOreBlock extends Block {
    private static final DustParticleOptions TWINKLE = new DustParticleOptions(Vec3.fromRGB24(16777215).toVector3f(), 1.0F);
    
    private final UniformInt xpRange;
  
    public DeepslateTitaniteOreBlock() {
        super(BlockBehaviour.Properties.of()
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.DEEPSLATE)
            .strength(4.5F, 3.0F)
            .sound(SoundType.DEEPSLATE));
    
        this.xpRange = UniformInt.of(3, 7);
    }
  
    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource random, BlockPos pos, int fortune, int silktouch) {
        return this.xpRange.sample(random);
    }
    
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double chance = random.nextDouble();
        if(!ModList.get().isLoaded("curseoftheabyss") || chance <= Abyss.expected_field(pos.getY())) {
            for(Direction direction : Direction.values()) {
                BlockPos blockpos = pos.relative(direction);
                if(!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                    Direction.Axis direction$axis = direction.getAxis();
                    double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double) direction.getStepX() : (double) random.nextFloat();
                    double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double) direction.getStepY() : (double) random.nextFloat();
                    double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double) direction.getStepZ() : (double) random.nextFloat();
                    level.addParticle(TWINKLE, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
