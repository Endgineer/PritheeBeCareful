package pyre.pritheebecareful.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pyre.pritheebecareful.PritheeBeCareful;
import pyre.pritheebecareful.item.ReinforceItem;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpec;
import pyre.pritheebecareful.loader.forging.ForgingMaterialSpec.MaterialReinforceSpec;

public class QuenchingBasinBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(0, 0, 0, 5, 2, 5),
        Block.box(11, 0, 0, 16, 2, 5),
        Block.box(0, 0, 11, 5, 2, 16),
        Block.box(11, 0, 11, 16, 2, 16),
        Block.box(0, 2, 0, 16, 16, 2),
        Block.box(0, 2, 14, 16, 16, 16),
        Block.box(0, 2, 2, 2, 16, 14),
        Block.box(14, 2, 2, 16, 16, 14),
        Block.box(2, 2, 2, 14, 4, 14)
    );
    
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 4);
    
    public QuenchingBasinBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.METAL)
                .mapColor(MapColor.COLOR_GRAY)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(8.0F, 28F)
                .randomTicks()
                .noOcclusion());
        
        this.registerDefaultState(this.defaultBlockState().setValue(LEVEL, 0));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL, FACING);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    public boolean isFull(BlockState state) {
        return state.getValue(LEVEL) == 4;
    }
    
    protected boolean isEntityInsideContent(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double) pos.getY() + this.getContentHeight(state) && entity.getBoundingBox().maxY > (double) pos.getY() + 0.25D;
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (ReinforceItem.isValidReinforceItem(itemstack)) {
                CompoundTag tag = itemstack.getTag();
                int progress = tag.getInt(ReinforceItem.PROGRESS);
                String material = tag.getString(ReinforceItem.MATERIAL);
                int reinforce = tag.getInt(ReinforceItem.REINFORCE);
                double temperature = tag.getDouble(ReinforceItem.TEMPERATURE);
                
                MaterialReinforceSpec reinforceSpec = ForgingMaterialSpec.getMaterialReinforceSpec(material, reinforce);
                
                int water = state.getValue(LEVEL);
                if (water > 0 && progress == 0) {
                    if (!reinforceSpec.canQuench(temperature)) {
                        player.displayClientMessage(Component.translatable("message."+PritheeBeCareful.MOD_ID+".quenching_basin.outside_quenching_range", (int) reinforceSpec.getQuenchingPoint(), (int) reinforceSpec.getFoldingPoint()), true);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                    
                    tag.putDouble(ReinforceItem.TEMPERATURE, 0);
                    tag.putInt("CustomModelData", 0);
                    tag.putInt(ReinforceItem.STATUS, ReinforceItem.ReinforceStatus.FINISHED.ordinal());
                    itemstack.setTag(tag);
                    player.setItemInHand(hand, itemstack.copy());
                    level.playSound((Player) null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 0.5F);
                    level.playSound((Player) null, pos, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(LEVEL, water-1));
                    for (int i = 0; i < 12; i++) {
                        double x = pos.getX() + 0.3 + level.random.nextDouble() * 0.4;
                        double y = pos.getY() + 0.1875 + state.getValue(LEVEL)*0.1875;
                        double z = pos.getZ() + 0.3 + level.random.nextDouble() * 0.4;
                        ((ServerLevel) level).sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z, 1, 0.0, 0.0, 0.0, 0.0);
                    }
                }
            } else if (itemstack.is(Items.WATER_BUCKET) && !this.isFull(state)) {
                int bucketCount = itemstack.getCount();
                if (bucketCount == 1) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(LEVEL, 4));
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
                } else if (player.getInventory().add(new ItemStack(Items.BUCKET))) {
                    player.setItemInHand(hand, itemstack.copyWithCount(bucketCount-1));
                    level.setBlockAndUpdate(pos, this.defaultBlockState().setValue(LEVEL, 4));
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent((Entity) null, GameEvent.FLUID_PLACE, pos);
                }
            } else if (itemstack.is(Items.BUCKET) && this.isFull(state)) {
                int bucketCount = itemstack.getCount();
                if (bucketCount == 1) {
                    player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                    level.setBlockAndUpdate(pos, this.defaultBlockState());
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent((Entity) null, GameEvent.FLUID_PICKUP, pos);
                } else if (player.getInventory().add(new ItemStack(Items.WATER_BUCKET))) {
                    player.setItemInHand(hand, itemstack.copyWithCount(bucketCount-1));
                    level.setBlockAndUpdate(pos, this.defaultBlockState());
                    level.playSound((Player) null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent((Entity) null, GameEvent.FLUID_PICKUP, pos);
                }
            }
        }
        
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos blockpos = PointedDripstoneBlock.findStalactiteTipAboveCauldron(level, pos);
        if (blockpos != null && PointedDripstoneBlock.getCauldronFillFluidType(level, blockpos) == Fluids.WATER) {
            this.receiveStalactiteDrip(state, level, pos);
        }
    }
    
    protected double getContentHeight(BlockState state) {
        return (3.0D + (double) state.getValue(LEVEL).intValue() * 3.0D) / 16.0D;
    }
    
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity.isOnFire() && this.isEntityInsideContent(state, pos, entity)) {
            entity.clearFire();
            if (entity.mayInteract(level, pos)) {
                this.handleEntityOnFireInside(state, level, pos);
            }
        }
    }
    
    protected void handleEntityOnFireInside(BlockState state, Level level, BlockPos pos) {
        lowerFillLevel(state, level, pos);
    }
    
    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos) {
        int i = state.getValue(LEVEL) - 1;
        state.setValue(LEVEL, Integer.valueOf(i));
        level.setBlockAndUpdate(pos, state);
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
    }
    
    @Override
    public void handlePrecipitation(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN && state.getValue(LEVEL) != 4) {
            BlockState blockstate = state.cycle(LEVEL);
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
        }
    }
    
    protected void receiveStalactiteDrip(BlockState state, Level level, BlockPos pos) {
        if (!this.isFull(state)) {
            BlockState blockstate = state.setValue(LEVEL, Integer.valueOf(state.getValue(LEVEL) + 1));
            level.setBlockAndUpdate(pos, blockstate);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(blockstate));
        }
    }
}
