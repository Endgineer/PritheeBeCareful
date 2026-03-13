package pyre.pritheebecareful.entity;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import pyre.pritheebecareful.PritheeBeCareful;
import pyre.pritheebecareful.block.ForgeThroatBlock;
import pyre.pritheebecareful.core.PbcBlocks;
import slimeknights.mantle.block.entity.NameableBlockEntity;

public class ForgeThroatBlockEntity extends NameableBlockEntity {
    public static final BlockEntityTicker<ForgeThroatBlockEntity> SERVER_TICKER = (blockLevel, blockPos, blockState, blockEntity) -> blockEntity.tick(blockLevel, blockPos, blockState);
    
    public ForgeThroatBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, Component.translatable("gui."+PritheeBeCareful.MOD_ID+".forge_throat"));
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return null;
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        BlockState master = level.getBlockState(pos.below());
        if (master.is(PbcBlocks.FORGE_CHAMBER.get()) || !state.getValue(ForgeThroatBlock.ACTIVE)) return;
        
        level.setBlock(pos, state.setValue(ForgeThroatBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        this.setChanged();
        level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag) {
        this.load(tag);
    }
    
    @Override
    protected boolean shouldSyncOnUpdate() {
        return true;
    }
    
    @Override
    public void saveSynced(CompoundTag tag) {
        super.saveSynced(tag);
    }
    
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }
}
