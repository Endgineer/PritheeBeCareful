package pyre.tinkerslevellingaddon.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.fan.EncasedFanBlockEntity;
import com.simibubi.create.content.kinetics.fan.processing.AllFanProcessingTypes;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.wrapper.InvWrapper;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.ForgeChamberBlock;
import pyre.tinkerslevellingaddon.block.ForgeHearthBlock;
import pyre.tinkerslevellingaddon.block.ForgeThroatBlock;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.core.PbcBlocks;
import pyre.tinkerslevellingaddon.item.ReinforceItem;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec;
import pyre.tinkerslevellingaddon.loader.forging.models.ThermalModel;
import pyre.tinkerslevellingaddon.menu.ForgeContainerMenu;
import slimeknights.mantle.block.entity.InventoryBlockEntity;

public class ForgeChamberBlockEntity extends InventoryBlockEntity implements IHaveGoggleInformation, IHaveHoveringInformation {
    public static final int ITEM_SLOT = 0;
    public static final int FUEL_SLOT = 1;
    
    public static final BlockEntityTicker<ForgeChamberBlockEntity> SERVER_TICKER = (blockLevel, blockPos, blockState, blockEntity) -> blockEntity.tick(blockLevel, blockPos, blockState);

    public static final String NBT_CLOCK = "clock";
    public static final String NBT_FUEL = "fuel";
    public static final String NBT_FUEL_MAXIMUM = "fuel_maximum";
    public static final String NBT_TEMPERATURE = "temperature";
    
    private int clock = 0;
    private int fuel = 0;
    private double fuelMaximum = 0;
    private double temperature = ThermalModel.AMBIENT_TEMPERATURE;
    
    public ForgeChamberBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PbcBlockEntities.FORGE_CHAMBER_BLOCK_ENTITY.get(), blockPos, blockState, Component.translatable("gui."+TinkersLevellingAddon.MOD_ID+".forge_chamber"), false, 2);
        this.itemHandler = new InvWrapper(this);
    }

    public ItemStack acceptFuelStack(ItemStack stack, boolean simulate) {
        if (!this.getBlockState().getValue(ForgeChamberBlock.CONNECTED)) return stack;
        
        ItemStack fuelstack = this.getItem(FUEL_SLOT);
        
        if (fuelstack.isEmpty()) {
            if (!simulate) {
                this.setItem(FUEL_SLOT, stack);
            }
            
            return ItemStack.EMPTY;
        } else if (fuelstack.is(stack.getItem())) {
            int stackFuelCount = stack.getCount();
            int currentFuelCount = fuelstack.getCount();
            int remainingFuelAllowed = this.stackSizeLimit - currentFuelCount;
            int actualFuelAdded = Math.min(stackFuelCount, remainingFuelAllowed);
            
            if (!simulate) {
                this.setItem(FUEL_SLOT, fuelstack.copyWithCount(currentFuelCount + actualFuelAdded));
            }
            
            return stack.copyWithCount(stackFuelCount - actualFuelAdded);
        }
        
        return stack;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ForgeContainerMenu(id, inventory, this);
    }
    
    public static List<Component> getMultiblockErrors(Level level, BlockPos pos, BlockState state, boolean simulate) {
        BlockPos above1Pos = pos.above();
        BlockPos below1Pos = pos.below();
        BlockState above1State = level.getBlockState(above1Pos);
        BlockState below1State = level.getBlockState(below1Pos);

        List<Component> errors = new ArrayList<>();

        boolean isThroatAbove = above1State.is(PbcBlocks.FORGE_THROAT.get());
        boolean isHearthBelow = below1State.is(PbcBlocks.FORGE_HEARTH.get());
        
        if (!isThroatAbove) {
            errors.add(Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.throat_missing").withStyle(ChatFormatting.GRAY));
        }

        if (!isHearthBelow) {
            errors.add(Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.hearth_missing").withStyle(ChatFormatting.GRAY));
        } else if (below1State.getValue(ForgeHearthBlock.FACING) != state.getValue(ForgeChamberBlock.FACING)) {
            errors.add(Component.translatable("multiblock."+TinkersLevellingAddon.MOD_ID+".forge.hearth_direction").withStyle(ChatFormatting.GRAY));
        }
        
        if (simulate) return errors;
        
        if (errors.isEmpty()) {
            if (!state.getValue(ForgeChamberBlock.CONNECTED)) {
                level.setBlock(pos, state.setValue(ForgeChamberBlock.CONNECTED, true), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            return errors;
        }
        
        level.setBlock(pos, state.setValue(ForgeChamberBlock.ACTIVE, false).setValue(ForgeChamberBlock.CONNECTED, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        
        if (isThroatAbove) {
            level.setBlock(above1Pos, above1State.setValue(ForgeThroatBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        }
        
        if (isHearthBelow) {
            level.setBlock(below1Pos, below1State.setValue(ForgeHearthBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        }
        
        return errors;
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (ForgeChamberBlockEntity.getMultiblockErrors(level, pos, state, false).isEmpty()) {
            this.tickFuelAndTemperature(level, pos, state);
            this.tickItemHeating();
            this.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        } else if (this.temperature > ThermalModel.AMBIENT_TEMPERATURE || this.clock > 0) {
            this.temperature = ThermalModel.AMBIENT_TEMPERATURE;
            this.fuelMaximum = 0;
            this.fuel = 0;
            this.clock = 0;
            this.setChanged();
            level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
        }
    }
    
    private void tickFuelAndTemperature(Level level, BlockPos pos, BlockState state) {
        ItemStack fuelstack = this.getItem(FUEL_SLOT);
        if (fuel == 0 && !fuelstack.isEmpty()) {
            this.fuel = ForgeHooks.getBurnTime(fuelstack, RecipeType.BLASTING);
            this.fuelMaximum = this.fuel;
            if (this.fuel > 0) {
                this.setItem(FUEL_SLOT, fuelstack.copyWithCount(fuelstack.getCount()-1));
            }
        }

        boolean isForgeOn = this.fuel > 0;
        
        BlockState below3State = level.getBlockState(pos.below(3));
        if (this.clock == 0) {
            double blastingSpeed = 0;
            if (below3State.is(AllBlocks.ENCASED_FAN.get()) && below3State.getValue(DirectionalKineticBlock.FACING) == Direction.UP) {
                EncasedFanBlockEntity encasedFanBlockEntity = (EncasedFanBlockEntity) level.getBlockEntity(pos.below(3));
                FanProcessingType fanProcessingType = encasedFanBlockEntity.getAirCurrent().getTypeAt(0);
                if (fanProcessingType.equals(AllFanProcessingTypes.BLASTING)) {
                    blastingSpeed = encasedFanBlockEntity.getSpeed();
                } else if (!fanProcessingType.equals(AllFanProcessingTypes.SPLASHING)) {
                    blastingSpeed = 0.42*encasedFanBlockEntity.getSpeed();
                }
            }
            
            this.temperature = ThermalModel.getForgeTemperature(temperature, isForgeOn, blastingSpeed);
        }
        
        BlockPos above1Pos = pos.above();
        BlockPos below1Pos = pos.below();
        
        BlockState above1State = level.getBlockState(above1Pos);
        BlockState below1State = level.getBlockState(below1Pos);
        
        if (isForgeOn) {
            if (!state.getValue(ForgeChamberBlock.ACTIVE)) {
                level.setBlock(pos, state.setValue(ForgeChamberBlock.ACTIVE, true), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            if (!above1State.getValue(ForgeThroatBlock.ACTIVE)) {
                level.setBlock(above1Pos, above1State.setValue(ForgeThroatBlock.ACTIVE, true), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            if (!below1State.getValue(ForgeHearthBlock.ACTIVE)) {
                level.setBlock(below1Pos, below1State.setValue(ForgeHearthBlock.ACTIVE, true), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            this.fuel -= 1;
        } else {
            if (state.getValue(ForgeChamberBlock.ACTIVE)) {
                level.setBlock(pos, state.setValue(ForgeChamberBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            if (above1State.getValue(ForgeThroatBlock.ACTIVE)) {
                level.setBlock(above1Pos, above1State.setValue(ForgeThroatBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
            
            if (below1State.getValue(ForgeHearthBlock.ACTIVE)) {
                level.setBlock(below1Pos, below1State.setValue(ForgeHearthBlock.ACTIVE, false), Block.UPDATE_CLIENTS | Block.UPDATE_NEIGHBORS);
            }
        }
        
        this.clock = (this.clock + 1) % 20;
    }
    
    private void tickItemHeating() {
        ItemStack itemstack = this.getItem(ITEM_SLOT);
        if (!ReinforceItem.isValidReinforceItem(itemstack)) return;
        
        CompoundTag tag = itemstack.getTag();
        int clock = tag.getInt(ReinforceItem.CLOCK);
        String material = tag.getString(ReinforceItem.MATERIAL);
        int reinforce = tag.getInt(ReinforceItem.REINFORCE);
        double temperature = tag.getDouble(ReinforceItem.TEMPERATURE);
        double meltingTemperature = ForgingMaterialSpec.getMeltingPoint(material, reinforce);
        
        if (clock == 0) {
            double newTemperature = Math.min(ThermalModel.getReinforceItemTemperature(itemstack, this.temperature), meltingTemperature);
            if (newTemperature != temperature) {
                tag.putInt(ReinforceItem.STATUS, ReinforceItem.ReinforceStatus.FORGING.ordinal());
                tag.putDouble(ReinforceItem.TEMPERATURE, newTemperature);
                tag.putInt("CustomModelData", ForgingMaterialSpec.getCustomModelData(material, reinforce, newTemperature));
            }
        }
        
        tag.putInt(ReinforceItem.CLOCK, (clock+1) % 20);
        itemstack.setTag(tag);
        this.setItem(ITEM_SLOT, itemstack);
    }

    public double getFuelPercentage() {
        return this.fuelMaximum > 0 ? this.fuel/this.fuelMaximum : 0;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate(TinkersLevellingAddon.MOD_ID+".forge_chamber").forGoggles(tooltip);
        
        if (this.level.getBlockState(this.getBlockPos()).getValue(ForgeChamberBlock.CONNECTED)) {
            tooltip.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".forge.forge_temperature", String.valueOf((int) this.temperature)).withStyle(ChatFormatting.GRAY));

            ItemStack fuelstack = this.getItem(FUEL_SLOT);
            int totalFuel = this.fuel + fuelstack.getCount()*ForgeHooks.getBurnTime(fuelstack, RecipeType.BLASTING);
            
            int fueltimeSeconds = (int) Math.ceil(totalFuel / 20.0);
            int ss = fueltimeSeconds % 60;
            int mm = (fueltimeSeconds / 60) % 60;
            int hh = fueltimeSeconds / 3600;
            
            tooltip.add(Component.translatable("tooltip."+TinkersLevellingAddon.MOD_ID+".forge.fuel_time", String.format("%02d", hh), String.format("%02d", mm), String.format("%02d", ss)).withStyle(ChatFormatting.GRAY));
            
            ItemStack itemstack = this.getItem(ITEM_SLOT);
            ReinforceItem.addMetalStats(tooltip, itemstack);
        } else {
            List<Component> errors = ForgeChamberBlockEntity.getMultiblockErrors(level, this.getBlockPos(), this.getBlockState(), true);
            for (Component error : errors) {
                tooltip.add(error);
            }
        }

        return true;
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
        tag.putDouble(NBT_TEMPERATURE, this.temperature);
        tag.putInt(NBT_CLOCK, this.clock);
        tag.putInt(NBT_FUEL, this.fuel);
        tag.putDouble(NBT_FUEL_MAXIMUM, this.fuelMaximum);
    }
    
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble(NBT_TEMPERATURE, this.temperature);
        tag.putInt(NBT_CLOCK, this.clock);
        tag.putInt(NBT_FUEL, this.fuel);
        tag.putDouble(NBT_FUEL_MAXIMUM, this.fuelMaximum);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.temperature = tag.getDouble(NBT_TEMPERATURE);
        this.clock = tag.getInt(NBT_CLOCK);
        this.fuel = tag.getInt(NBT_FUEL);
        this.fuelMaximum = tag.getDouble(NBT_FUEL_MAXIMUM);
    }
}
