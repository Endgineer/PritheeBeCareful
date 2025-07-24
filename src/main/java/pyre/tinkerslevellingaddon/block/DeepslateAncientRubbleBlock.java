package pyre.tinkerslevellingaddon.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public class DeepslateAncientRubbleBlock extends Block {
    private final UniformInt xpRange;
  
    public DeepslateAncientRubbleBlock() {
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
}
