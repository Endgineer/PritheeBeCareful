package pyre.tinkerslevellingaddon.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import pyre.tinkerslevellingaddon.entity.ForgeChamberBlockEntity;
import slimeknights.mantle.block.InventoryBlock;
import slimeknights.mantle.util.BlockEntityHelper;

public class ForgeChamberBlock extends InventoryBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty CONNECTED = BooleanProperty.create("connected");
    
    public ForgeChamberBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.METAL)
                .mapColor(MapColor.COLOR_GRAY)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .strength(8.0F, 28F)
                .emissiveRendering((state, level, pos) -> state.getValue(ACTIVE))
                .lightLevel(s -> s.getValue(ACTIVE) ? 12 : 0)
                .noOcclusion());
        
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, false).setValue(CONNECTED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeChamberBlockEntity(pos, state);
    }
    
    @Override
    protected boolean openGui(Player player, Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == this) {
            return canOpenGui(state) ? super.openGui(player, level, pos) : displayStatus(player, level, pos, state);
        }
        
        return false;
    }

    protected boolean canOpenGui(BlockState state) {
        return state.getValue(CONNECTED);
    }
    
    protected boolean displayStatus(Player player, Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return true;
        
        BlockState above2 = level.getBlockState(pos.above(2));
        BlockState above1 = level.getBlockState(pos.above());
        BlockState below1 = level.getBlockState(pos.below());
        BlockState below2 = level.getBlockState(pos.below(2));

        Component statusMessage = getReasonIfMultiblockInvalid(below2, below1, state.getValue(FACING), above1, above2);
        
        if (statusMessage != null) {
            player.displayClientMessage(statusMessage, true);
        }
        
        return true;
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> check) {
        return level.isClientSide ? null : BlockEntityHelper.castTicker(check, PbcBlockEntities.FORGE_CHAMBER_BLOCK_ENTITY.get(), ForgeChamberBlockEntity.SERVER_TICKER);
    }
    
    //! MOVE TO BLOCKENTITY
    @Nullable
    private Component getReasonIfMultiblockInvalid(BlockState below2, BlockState below1, Direction direction, BlockState above1, BlockState above2) {
        if (!above2.is(Blocks.AIR)) {
            return Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.requires_clearance");
        }
        
        if (!above1.is(PbcBlocks.FORGE_THROAT.get())) {
            return Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.requires_throat");
        }
        
        if (!below1.is(PbcBlocks.FORGE_HEARTH.get())) {
            return Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.requires_hearth");
        } else if (above1.getValue(ForgeHearthBlock.FACING) != direction) {
            return Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.direction_hearth");
        }
        
        return null;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction blockDirection = context.getHorizontalDirection().getOpposite();
        return super.getStateForPlacement(context).setValue(FACING, blockDirection).setValue(CONNECTED, false);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE, CONNECTED);
    }

    @Nullable
    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return state.getValue(CONNECTED) ? BlockPathTypes.DAMAGE_FIRE : BlockPathTypes.OPEN;
    }
}
