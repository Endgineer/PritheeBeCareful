package pyre.tinkerslevellingaddon.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pyre.tinkerslevellingaddon.entity.ReinforcementAnvilBlockEntity;
import slimeknights.mantle.block.RetexturedBlock;
import slimeknights.mantle.util.RetexturedHelper;
import slimeknights.tconstruct.shared.block.TableBlock;

public class ReinforcementAnvilBlock extends TableBlock {
    private static final VoxelShape PART_BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(
        PART_BASE,
        Block.box(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D),
        Block.box(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D),
        Block.box(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D));
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(
        PART_BASE,
        Block.box(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D),
        Block.box(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D),
        Block.box(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D));

    public ReinforcementAnvilBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.ANVIL)
                .mapColor(MapColor.COLOR_GRAY)
                .pushReaction(PushReaction.BLOCK)
                .requiresCorrectToolForDrops()
                .strength(5.0F, 1200.0F)
                .noOcclusion());
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            boolean performedAction = ((ReinforcementAnvilBlockEntity) level.getBlockEntity(pos)).interact(player, hand);
            return performedAction ? InteractionResult.sidedSuccess(true) : InteractionResult.PASS;
        }
        
        return InteractionResult.PASS;
    }
    
    @Override
    @Deprecated
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ReinforcementAnvilBlockEntity(pPos, pState);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter pLevel, List<Component> tooltip, TooltipFlag pFlag) {
        RetexturedHelper.addTooltip(stack, tooltip);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);
        RetexturedBlock.updateTextureBlock(world, pos, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return RetexturedBlock.getPickBlock(world, pos, state);
    }
    
    public static final DirectionProperty getFacing() {
        return FACING;
    }
}
