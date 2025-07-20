package pyre.tinkerslevellingaddon.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import pyre.tinkerslevellingaddon.ReinforceModifier;
import pyre.tinkerslevellingaddon.TinkersLevellingAddon;
import pyre.tinkerslevellingaddon.block.ReinforcementAnvilBlock;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.item.ReinforceItem;
import pyre.tinkerslevellingaddon.item.TitaniteShardItem;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec;
import pyre.tinkerslevellingaddon.loader.forging.ForgingMaterialSpec.MaterialReinforceSpec;
import pyre.tinkerslevellingaddon.loader.forging.models.ThermalModel;
import pyre.tinkerslevellingaddon.loader.reinforcing.ReinforcingGearSpec;
import pyre.tinkerslevellingaddon.network.Messages;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariant;
import slimeknights.tconstruct.library.tools.definition.module.material.MaterialRepairToolHook;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;
import slimeknights.tconstruct.shared.block.entity.TableBlockEntity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.equipment.goggles.IHaveHoveringInformation;

public class ReinforcementAnvilBlockEntity extends TableBlockEntity implements IHaveGoggleInformation, IHaveHoveringInformation {
    public static final int SLOT_A = 0;
    public static final int SLOT_B = 1;
    public static final int SLOT_C = 2;
    
    public ReinforcementAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(PbcBlockEntities.REINFORCEMENT_ANVIL_BLOCK_ENTITY.get(), pos, state, Component.translatable("gui."+TinkersLevellingAddon.MOD_ID+".reinforcement_anvil"), 3, 1);
        this.itemHandler = new InvWrapper(this);
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return null;
    }
    
    public boolean interact(Player player, InteractionHand hand) {
        if (level == null || level.isClientSide) {
            return false;
        }
        
        ItemStack handstack = player.getItemInHand(hand);
        Item handitem = handstack.getItem();
        
        ItemStack slotstack_a = this.getItem(SLOT_A);
        ItemStack slotstack_b = this.getItem(SLOT_B);
        ItemStack slotstack_c = this.getItem(SLOT_C);

        Item slotitem_a = slotstack_a.getItem();
        Item slotitem_b = slotstack_b.getItem();
        Item slotitem_c = slotstack_c.getItem();
        
        boolean slot_a_empty = !isStackInSlot(SLOT_A);
        boolean slot_b_empty = !isStackInSlot(SLOT_B);
        boolean slot_c_empty = !isStackInSlot(SLOT_C);

        int targetSlot = this.playerRaycastSlotResult(player);
        
        if (player.isShiftKeyDown()) {
            if (targetSlot == SLOT_A && !slot_a_empty && player.getInventory().canPlaceItem(1, slotstack_a)) {
                this.setItem(SLOT_A, ItemStack.EMPTY);
                player.getInventory().add(slotstack_a);
                return true;
            } else if (targetSlot == SLOT_B && !slot_b_empty && player.getInventory().canPlaceItem(1, slotstack_b)) {
                this.setItem(SLOT_B, ItemStack.EMPTY);
                player.getInventory().add(slotstack_b);
                return true;
            } else if (targetSlot == SLOT_C && !slot_c_empty && player.getInventory().canPlaceItem(1, slotstack_c)) {
                this.setItem(SLOT_C, ItemStack.EMPTY);
                player.getInventory().add(slotstack_c);
                return true;
            }
            
            return false;
        }
        
        if (handstack.is(AllItems.WRENCH.get())) {
            if (!player.getCooldowns().isOnCooldown(AllItems.WRENCH.get())) {
                this.hammerItem(player);
                return true;
            }
            
            return false;
        }
        
        boolean isGearItem = handstack.is(TinkerTags.Items.MODIFIABLE);
        boolean isPartItem = handstack.is(TinkerTags.Items.TOOL_PARTS);

        String material = ForgingMaterialSpec.getRegisteredReinforceMaterial(handstack);
        
        if (ReinforceItem.isValidReinforceItem(handstack)) {
            if (slotstack_b.is(TinkerTags.Items.MODIFIABLE)) {
                CompoundTag tag = handstack.getTag();
                if (tag.getInt(ReinforceItem.STATUS) != ReinforceItem.ReinforceStatus.FINISHED.ordinal()) return false;
                
                if (slotstack_b.isDamaged()) return false;
                if (!ForgeRegistries.ITEMS.getKey(slotitem_b).getPath().equals(tag.getString(ReinforceItem.GEAR))) return false;

                boolean materialMatches = false;
                ToolStack toolstack = ToolStack.from(slotstack_b);
                String reinforceMaterial = tag.getString(ReinforceItem.MATERIAL);
                for (MaterialVariant materialVariant : toolstack.getMaterials()) {
                    MaterialId materialId = materialVariant.getId();
                    if (materialId.getPath().equals(reinforceMaterial) && MaterialRepairToolHook.canRepairWith(toolstack, materialId)) {
                        materialMatches = true;
                        break;
                    }
                }
                
                if (!materialMatches) return false;
                
                int targetReinforceLevel = tag.getInt(ReinforceItem.REINFORCE);
                ToolStack gearstack = ReinforceModifier.reinforce(slotstack_b, targetReinforceLevel);
                if (gearstack == null) return false;
                
                player.setItemInHand(hand, ItemStack.EMPTY);
                this.setItem(SLOT_B, gearstack.createStack());
                
                Messages.sendAnvilMulticlang(level, worldPosition);
                return true;
            } else if (slot_a_empty && slot_b_empty && slot_c_empty) {
                CompoundTag tag = handstack.getTag();
                tag.putInt(ReinforceItem.CLOCK, 0);
                handstack.setTag(tag);
                
                player.setItemInHand(hand, ItemStack.EMPTY);
                this.setItem(SLOT_B, handstack);
                return true;
            }
        } else if (isGearItem) {
            if (slot_a_empty && slot_b_empty && slot_c_empty) {
                player.setItemInHand(hand, ItemStack.EMPTY);
                this.setItem(SLOT_B, handstack);
                return true;
            }
        } else if (isPartItem && material != null) {
            if (targetSlot == SLOT_A && slot_a_empty) {
                player.setItemInHand(hand, handstack.copyWithCount(handstack.getCount()-1));
                this.setItem(SLOT_A, handstack.copyWithCount(1));
                return true;
            } else if (targetSlot == SLOT_B && slot_b_empty) {
                player.setItemInHand(hand, handstack.copyWithCount(handstack.getCount()-1));
                this.setItem(SLOT_B, handstack.copyWithCount(1));
                return true;
            } else if (targetSlot == SLOT_C && slot_c_empty) {
                player.setItemInHand(hand, handstack.copyWithCount(handstack.getCount()-1));
                this.setItem(SLOT_C, handstack.copyWithCount(1));
                return true;
            }
        } else if (handitem instanceof TitaniteShardItem) {
            if (ReinforceItem.isValidReinforceItem(slotstack_b)) {
                CompoundTag slottag_b = slotstack_b.getTag();

                if (slottag_b.getInt(ReinforceItem.STATUS) == ReinforceItem.ReinforceStatus.UNTOUCHED.ordinal()) {
                    int reinforce = slottag_b.getInt(ReinforceItem.REINFORCE);
                    if (reinforce < 5) {
                        ItemStack result = slotstack_b.copy();
                        int resultReinforce = reinforce+1;
                        
                        CompoundTag tag = result.getTag();
                        
                        String resultMaterial = tag.getString(ReinforceItem.MATERIAL);
                        int resultCount = tag.getInt(ReinforceItem.COUNT);
                        
                        tag.putInt(ReinforceItem.REINFORCE, resultReinforce);
                        tag.putInt(ReinforceItem.PROGRESS, ForgingMaterialSpec.getExperienceCostTotal(resultMaterial, resultReinforce, resultCount));
                        tag.putDouble(ReinforceItem.EXPERIENCE, ForgingMaterialSpec.getExperienceCostPerTrip(resultMaterial, resultReinforce, resultCount));
                        result.setTag(tag);
                        
                        player.setItemInHand(InteractionHand.MAIN_HAND, handstack.copyWithCount(handstack.getCount()-1));
                        this.setItem(SLOT_B, result);
                        
                        Messages.sendAnvilMulticlang(level, worldPosition);
                        return true;
                    }
                } else if (slottag_b.getDouble(ReinforceItem.EXPERIENCE) == 0 && slottag_b.getInt(ReinforceItem.PROGRESS) > 0) {
                    String ingotMaterial = slottag_b.getString(ReinforceItem.MATERIAL);
                    MaterialReinforceSpec materialReinforceSpec = ForgingMaterialSpec.getMaterialReinforceSpec(ingotMaterial, slottag_b.getInt(ReinforceItem.REINFORCE));
                    if (materialReinforceSpec.canFold(slottag_b.getDouble(ReinforceItem.TEMPERATURE))) {
                        int required = slottag_b.getInt(ReinforceItem.COUNT);
                        int available = 0;
                        
                        Inventory inventory = player.getInventory();
                        
                        List<Integer> materialIngotStackIndices = new ArrayList<>();
                        for (int i = 0; i < Inventory.INVENTORY_SIZE && available < required; i++) {
                            ItemStack stack = inventory.getItem(i);
                            if (ForgingMaterialSpec.match(ingotMaterial, stack)) {
                                materialIngotStackIndices.add(i);
                                available += stack.getCount();
                            }
                        }
                        
                        if(available >= required) {
                            player.getItemInHand(hand).setCount(player.getItemInHand(hand).getCount()-1);
                            
                            for(Integer index : materialIngotStackIndices) {
                                ItemStack stack = inventory.getItem(index);
                                
                                int amount = Math.min(stack.getCount(), required);
                                inventory.getItem(index).setCount(stack.getCount()-amount);
                                required -= amount;
                            }
                            
                            ItemStack result = slotstack_b.copy();
                            slottag_b.putDouble(ReinforceItem.EXPERIENCE, materialReinforceSpec.getExperienceCostPerTrip(slottag_b.getInt(ReinforceItem.COUNT)));
                            result.setTag(slottag_b);
                            this.setItem(SLOT_B, result);
                            
                            Messages.sendAnvilMulticlang(level, worldPosition);
                            return true;
                        }
                    }
                }
            } else {
                ReinforcingGearSpec gearSpec = ReinforcingGearSpec.getReinforcingGearSpec(slotitem_a, slotitem_b, slotitem_c);
                
                String consensusMaterial = null;
                
                if (!slot_b_empty) {
                    consensusMaterial = ForgingMaterialSpec.getRegisteredReinforceMaterial(slotstack_b);
                }
                
                if (!slot_a_empty) {
                    String slotstack_a_material = ForgingMaterialSpec.getRegisteredReinforceMaterial(slotstack_a);
                    if (consensusMaterial == null) {
                        consensusMaterial = slotstack_a_material;
                    } else if (!slotstack_a_material.equals(consensusMaterial)) {
                        return false;
                    }
                }
                
                if (!slot_c_empty) {
                    String slotstack_c_material = ForgingMaterialSpec.getRegisteredReinforceMaterial(slotstack_c);
                    if (consensusMaterial == null) {
                        consensusMaterial = slotstack_c_material;
                    } else if (!slotstack_c_material.equals(consensusMaterial)) {
                        return false;
                    }
                }
                
                if (gearSpec != null) {
                    int materialCost = gearSpec.getMaterialCost();
                    
                    CompoundTag tag = new CompoundTag();
                    tag.putString(ReinforceItem.MATERIAL, consensusMaterial);
                    tag.putInt(ReinforceItem.COUNT, materialCost);
                    tag.putString(ReinforceItem.GEAR, gearSpec.getResultingGear());
                    tag.putInt(ReinforceItem.REINFORCE, 1);
                    tag.putDouble(ReinforceItem.TEMPERATURE, ThermalModel.AMBIENT_TEMPERATURE);
                    tag.putInt(ReinforceItem.PROGRESS, ForgingMaterialSpec.getExperienceCostTotal(consensusMaterial, 1, materialCost));
                    tag.putDouble(ReinforceItem.EXPERIENCE, ForgingMaterialSpec.getExperienceCostPerTrip(consensusMaterial, 1, materialCost));
                    tag.putInt("CustomModelData", 0);
                    tag.putInt(ReinforceItem.STATUS, ReinforceItem.ReinforceStatus.UNTOUCHED.ordinal());
                    tag.putInt(ReinforceItem.CLOCK, 0);
                    
                    ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(TinkersLevellingAddon.MOD_ID+":reinforce_item")));
                    result.setTag(tag);
                    
                    player.setItemInHand(InteractionHand.MAIN_HAND, handstack.copyWithCount(handstack.getCount()-1));
                    this.setItem(SLOT_A, ItemStack.EMPTY);
                    this.setItem(SLOT_C, ItemStack.EMPTY);
                    this.setItem(SLOT_B, result);
                    
                    Messages.sendAnvilMulticlang(level, worldPosition);
                    return true;
                }
            }
        }
        
        return false;
    }

    private void hammerItem(Player player) {
        player.getCooldowns().addCooldown(AllItems.WRENCH.get(), 20);
        
        ItemStack slotstack = this.getItem(SLOT_B);
        
        if (ReinforceItem.isValidReinforceItem(slotstack)) {
            CompoundTag tag = slotstack.getTag();
            String material = tag.getString(ReinforceItem.MATERIAL);
            int reinforce = tag.getInt(ReinforceItem.REINFORCE);
            double temperature = tag.getDouble(ReinforceItem.TEMPERATURE);
            int progress = tag.getInt(ReinforceItem.PROGRESS);
            double experience = tag.getDouble(ReinforceItem.EXPERIENCE);

            MaterialReinforceSpec materialReinforceSpec = ForgingMaterialSpec.getMaterialReinforceSpec(material, reinforce);

            double xpConducted = materialReinforceSpec.getXpConductance(temperature);
            if (experience > 0 && xpConducted > 0) {
                double resultExperience = Math.max(0, experience - xpConducted);
                int wholeXpPoints = (int) (Math.ceil(experience) - Math.ceil(resultExperience));
                
                if (wholeXpPoints > 0) {
                    player.giveExperiencePoints(-wholeXpPoints);
                    progress -= wholeXpPoints;
                    
                    tag.putInt(ReinforceItem.PROGRESS, progress);
                }
                
                tag.putDouble(ReinforceItem.EXPERIENCE, resultExperience);
                
                ItemStack result = slotstack.copy();
                result.setTag(tag);
                
                this.setItem(SLOT_B, result);
                Messages.sendAnvilClang(level, worldPosition, materialReinforceSpec.getCustomModelData(temperature), wholeXpPoints);
                return;
            }
        }
        
        Messages.sendAnvilClang(level, worldPosition, 0, 0);
    }

    public void tick() {
        ItemStack slotstack = this.getItem(SLOT_B);
        double resultTemperature = ThermalModel.getReinforceItemTemperature(slotstack, ThermalModel.AMBIENT_TEMPERATURE);
        
        if (resultTemperature > Integer.MIN_VALUE) {
            ItemStack result = slotstack.copy();
            CompoundTag tag = result.getTag();
            int clock = tag.getInt(ReinforceItem.CLOCK);
            if (clock == 0 && resultTemperature != tag.getDouble(ReinforceItem.TEMPERATURE)) {
                tag.putDouble(ReinforceItem.TEMPERATURE, resultTemperature);
                tag.putInt("CustomModelData", ForgingMaterialSpec.getCustomModelData(tag.getString(ReinforceItem.MATERIAL), tag.getInt(ReinforceItem.REINFORCE), resultTemperature));
            }
            
            tag.putInt(ReinforceItem.CLOCK, (clock+1) % 20);
            result.setTag(tag);
            this.setItem(SLOT_B, result);
        }
    }
    
    private int playerRaycastSlotResult(Player player) {
        HitResult hitResult = player.pick(5.0D, 1.0F, false);
        if (!(hitResult instanceof BlockHitResult blockHitResult)) return -1;
        
        double hitX = blockHitResult.getLocation().x - blockHitResult.getBlockPos().getX();
        double hitZ = blockHitResult.getLocation().z - blockHitResult.getBlockPos().getZ();
        
        Direction facing = this.getBlockState().getValue(ReinforcementAnvilBlock.getFacing());
        if (facing == Direction.NORTH || facing == Direction.SOUTH) {
            return hitX < 0.33 ? SLOT_A : (hitX < 0.67 ? SLOT_B : SLOT_C);
        } else if (facing == Direction.WEST || facing == Direction.EAST) {
            return hitZ < 0.33 ? SLOT_A : (hitZ < 0.67 ? SLOT_B : SLOT_C);
        }

        return -1;
    }

    private void displayTooltipInfo(List<Component> tooltip, ItemStack stack, Item item) {
        boolean isAdvanced = Minecraft.getInstance().options.advancedItemTooltips;
        TooltipFlag flag = isAdvanced ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
        
        item.appendHoverText(stack, level, tooltip, flag);
    }
    
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        ItemStack slotstack_a = this.getItem(SLOT_A);
        ItemStack slotstack_b = this.getItem(SLOT_B);
        ItemStack slotstack_c = this.getItem(SLOT_C);

        Item slotitem_a = slotstack_a.getItem();
        Item slotitem_b = slotstack_b.getItem();
        Item slotitem_c = slotstack_c.getItem();

        if (slotitem_b instanceof ModifiableItem || ReinforceItem.isValidReinforceItem(slotstack_b)) {
            this.displayTooltipInfo(tooltip, slotstack_b, slotitem_b);
            return true;
        }
        
        Player player = Minecraft.getInstance().player;
        int targetSlot = this.playerRaycastSlotResult(player);
        
        if (targetSlot == SLOT_A && slotitem_a instanceof ToolPartItem) {
            this.displayTooltipInfo(tooltip, slotstack_a, slotitem_a);
            return true;
        } else if (targetSlot == SLOT_B && slotitem_b instanceof ToolPartItem) {
            this.displayTooltipInfo(tooltip, slotstack_b, slotitem_b);
            return true;
        } else if (targetSlot == SLOT_C && slotitem_c instanceof ToolPartItem) {
            this.displayTooltipInfo(tooltip, slotstack_c, slotitem_c);
            return true;
        }
        
        return false;
    }
}
