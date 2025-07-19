package pyre.tinkerslevellingaddon.entity;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.ForgeChamberBlock;
import pyre.tinkerslevellingaddon.block.ForgeHearthBlock;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import slimeknights.mantle.block.entity.NameableBlockEntity;

public class ForgeHearthBlockEntity extends NameableBlockEntity {
    public static final BlockEntityTicker<ForgeHearthBlockEntity> SERVER_TICKER = (blockLevel, blockPos, blockState, blockEntity) -> blockEntity.tick(blockLevel, blockPos, blockState);
    
    private final LazyOptional<IItemHandler> itemCapability = LazyOptional.of(this::proxyHandler);
    
    public ForgeHearthBlockEntity(BlockPos pos, BlockState state) {
        super(PbcBlockEntities.FORGE_HEARTH_BLOCK_ENTITY.get(), pos, state, Component.translatable("gui."+TinkersLevellingAddon.MOD_ID+".forge_hearth"));
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            Direction back = this.getBlockState().getValue(ForgeChamberBlock.FACING).getOpposite();
            if (side == back) {
                return this.itemCapability.cast();
            }
        }
        
        return super.getCapability(cap, side);
    }
    
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemCapability.invalidate();
    }
    
    private IItemHandler proxyHandler() {
        return new IItemHandler() {
            @Override
            public int getSlots() {
                return 1;
            }
            
            @Override
            public ItemStack getStackInSlot(int slot) {
                return ItemStack.EMPTY;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }
            
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }
            
            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return stack.is(Items.COAL) || stack.is(Items.CHARCOAL) || stack.is(Items.COAL_BLOCK);
            }
            
            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (stack.isEmpty() || level == null) return stack;
                
                BlockEntity master = level.getBlockEntity(worldPosition.above());
                if (master instanceof ForgeChamberBlockEntity masterEntity) {
                    return masterEntity.acceptFuelStack(stack, simulate);
                }
                
                return stack;
            }
        };
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
