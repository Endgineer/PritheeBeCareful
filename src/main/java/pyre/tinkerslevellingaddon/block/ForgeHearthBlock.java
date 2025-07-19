package pyre.tinkerslevellingaddon.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ForgeHearthBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    
    public ForgeHearthBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.METAL)
                .mapColor(MapColor.COLOR_GRAY)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(8.0F, 28F)
                .emissiveRendering((state, level, pos) -> state.getValue(ACTIVE))
                .lightLevel(s -> s.getValue(ACTIVE) ? 8 : 0)
                .noOcclusion());

        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
        if (state.getValue(ACTIVE)) {
            double x = pos.getX() + 0.5D;
            double y = (double) pos.getY() + (rand.nextFloat() * 6F + 2F) / 16F;
            double z = pos.getZ() + 0.5D;
            double frontOffset = 0.52D;
            double sideOffset = rand.nextDouble() * 0.6D - 0.3D;
            spawnFireParticles(level, state, x, y, z, frontOffset, sideOffset);
        }
    }
    
    protected void spawnFireParticles(LevelAccessor levelAccessor, BlockState state, double x, double y, double z, double front, double side) {
        spawnFireParticles(levelAccessor, state, x, y, z, front, side, ParticleTypes.FLAME);
    }
    
    protected void spawnFireParticles(LevelAccessor levelAccessor, BlockState state, double x, double y, double z, double front, double side, ParticleOptions particle) {
        switch (state.getValue(FACING)) {
            case WEST -> {
                levelAccessor.addParticle(ParticleTypes.SMOKE, x - front, y, z + side, 0.0D, 0.0D, 0.0D);
                levelAccessor.addParticle(particle, x - front, y, z + side, 0.0D, 0.0D, 0.0D);
            }
            case EAST -> {
                levelAccessor.addParticle(ParticleTypes.SMOKE, x + front, y, z + side, 0.0D, 0.0D, 0.0D);
                levelAccessor.addParticle(particle, x + front, y, z + side, 0.0D, 0.0D, 0.0D);
            }
            case NORTH -> {
                levelAccessor.addParticle(ParticleTypes.SMOKE, x + side, y, z - front, 0.0D, 0.0D, 0.0D);
                levelAccessor.addParticle(particle, x + side, y, z - front, 0.0D, 0.0D, 0.0D);
            }
            case SOUTH -> {
                levelAccessor.addParticle(ParticleTypes.SMOKE, x + side, y, z + front, 0.0D, 0.0D, 0.0D);
                levelAccessor.addParticle(particle, x + side, y, z + front, 0.0D, 0.0D, 0.0D);
            }
            default -> {}
        }
    }
}
