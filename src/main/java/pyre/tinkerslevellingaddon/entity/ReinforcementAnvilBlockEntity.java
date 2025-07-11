package pyre.tinkerslevellingaddon.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;
import pyre.tinkerslevellingaddon.core.PbcBlockEntities;
import pyre.tinkerslevellingaddon.network.Messages;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.shared.block.entity.TableBlockEntity;

import javax.annotation.Nullable;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.equipment.goggles.IHaveHoveringInformation;

public class ReinforcementAnvilBlockEntity extends TableBlockEntity implements IHaveGoggleInformation, IHaveHoveringInformation {
    public static final int SLOT = 0;
    
    public ReinforcementAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(PbcBlockEntities.REINFORCEMENT_ANVIL_BLOCK_ENTITY.get(), pos, state, Component.literal("reinforcement_anvil"), 1);
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
        ItemStack slotstack = this.getItem(SLOT);
        
        boolean empty_slot = !isStackInSlot(SLOT);
        
        if (player.isShiftKeyDown()) {
            if (!empty_slot && player.getInventory().canPlaceItem(1, slotstack)) {
                this.setItem(SLOT, ItemStack.EMPTY);
                player.getInventory().add(slotstack);
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
        
        if (empty_slot) {
            if (handstack.is(TinkerTags.Items.MODIFIABLE)) {
                this.setItem(SLOT, handstack);
                player.setItemInHand(hand, ItemStack.EMPTY);
                return true;
            }
            
            return false;
        }

        return false;
    }

    private void hammerItem(Player player) {
        Messages.sendAnvilClang(level, worldPosition, false);
        player.getCooldowns().addCooldown(AllItems.WRENCH.get(), 20);
    }
}
