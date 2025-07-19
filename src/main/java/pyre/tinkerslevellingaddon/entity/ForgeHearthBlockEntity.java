package pyre.tinkerslevellingaddon.entity;

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
import net.minecraft.world.level.block.state.BlockState;
import pyre.tinkerslevellingaddon.block.ForgeHearthBlock;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import slimeknights.mantle.block.entity.NameableBlockEntity;

public class ForgeHearthBlockEntity extends NameableBlockEntity {
    public static final BlockEntityTicker<ForgeHearthBlockEntity> SERVER_TICKER = (blockLevel, blockPos, blockState, blockEntity) -> blockEntity.tick(blockLevel, blockPos, blockState);
    
    public ForgeHearthBlockEntity(BlockPos pos, BlockState state) {
        super(PbcBlockEntities.FORGE_HEARTH_BLOCK_ENTITY.get(), pos, state, Component.literal("forge_hearth"));
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return null;
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        BlockState master = level.getBlockState(pos.above());
        if (master.is(PbcBlocks.FORGE_CHAMBER.get()) || !state.getValue(ForgeHearthBlock.ACTIVE)) return;
        
        level.setBlock(pos, state.setValue(ForgeHearthBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
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
